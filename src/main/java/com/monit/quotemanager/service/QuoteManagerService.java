package com.monit.quotemanager.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;

import com.monit.quotemanager.model.Quote;
import com.monit.quotemanager.model.QuoteImpl;
import com.monit.quotemanager.model.TradeResult;
import com.monit.quotemanager.model.TradeResultImpl;
import com.monit.quotemanager.repo.QuoteRepository;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteManagerService implements QuoteManager {

    @Autowired
    private final QuoteRepository repository;

    public QuoteManagerService(QuoteRepository repository) {
        this.repository = repository;
    }

    public List<QuoteImpl> findAll() {
        return IterableUtils.toList(repository.findAll());
    }

    public List<QuoteImpl> findBySymbol(String symbol) {
        return IterableUtils.toList(repository.findBySymbol(symbol));
    }

    public Optional<QuoteImpl> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public void AddOrUpdateQuote(Quote quote) {
        repository.findById(quote.getId())
                .map(quoteImpl -> {
                    quoteImpl.setPrice(quote.getPrice());
                    quoteImpl.setSymbol(quote.getSymbol(""));
                    quoteImpl.setAvailableVolume(quote.getAvailableVolume());
                    return repository.save(quoteImpl);
                })
                .orElseGet(() -> {
                    quote.setId();
                    return repository.save((QuoteImpl) quote);
                });
    }

    @Override
    public void RemoveQuote(UUID id) {
        if (findById(id).isPresent())
            repository.deleteById(id);
    }

    @Override
    public void RemoveAllQuotes(String symbol) {
        repository.deleteBySymbol(symbol);
    }

    @Override
    public Quote GetBestQuoteWithAvailableVolume(String symbol) {
        Quote quote = null;
        List<QuoteImpl> qList = findBySymbol(symbol);

        if (qList != null) {
            qList = getActiveAndSort(qList);
            if (!qList.isEmpty())
                quote = qList.get(0);
        }
        return quote;
    }

    @Override
    public TradeResult ExecuteTrade(String symbol, int volumeRequested) {
        TradeResultImpl result = new TradeResultImpl(UUID.randomUUID(), symbol, 0, volumeRequested);
        List<QuoteImpl> qList = findBySymbol(symbol);

        if (qList != null) {
            qList = getActiveAndSort(qList);
            if (!qList.isEmpty()) {
                Map<Double, Integer> tradeMap = new HashMap<>();
                ListIterator<QuoteImpl> iter = qList.listIterator();
                int volumeLeft = volumeRequested;
                double weightedAverage;

                do {
                    QuoteImpl quote = iter.next();
                    volumeLeft = volumeLeft - quote.getAvailableVolume();

                    if (volumeLeft >= 0) {
                        tradeMap.put(quote.getPrice(), quote.getAvailableVolume());
                        quote.setAvailableVolume(0);
                    } else {
                        tradeMap.put(quote.getPrice(), volumeLeft);
                        quote.setAvailableVolume(Math.abs(volumeLeft));
                    }

                    repository.save(quote);

                } while (volumeLeft > 0 && iter.hasNext());

                weightedAverage =
                tradeMap.entrySet().stream().collect(averagingWeighted(Map.Entry::getKey, Map.Entry::getValue));
                result.setVolumeWeightedAveragePrice(weightedAverage);
            }
        }
        return result;
    }

    private List<QuoteImpl> getActiveAndSort(List<QuoteImpl> qList) {
        List<QuoteImpl> activeList = qList;
        Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        activeList.removeIf(q -> q.getExpiration().after(today) == false || q.getAvailableVolume() <= 0);
        activeList.sort(Comparator.comparing(Quote::getPrice));

        return activeList;
    }

    private static <T> Collector<T, ?, Double> averagingWeighted(ToDoubleFunction<T> valueFunction,
            ToIntFunction<T> weightFunction) {
        class Box {
            double num = 0;
            long denom = 0;
        }
        return Collector.of(
                Box::new,
                (b, e) -> {
                    b.num += valueFunction.applyAsDouble(e) * weightFunction.applyAsInt(e);
                    b.denom += weightFunction.applyAsInt(e);
                },
                (b1, b2) -> {
                    b1.num += b2.num;
                    b1.denom += b2.denom;
                    return b1;
                },
                b -> b.num / b.denom);
    }

}
