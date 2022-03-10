package com.monit.quotemanager.service;

import java.util.UUID;

import com.monit.quotemanager.model.Quote;
import com.monit.quotemanager.model.TradeResult;

public interface QuoteManager {
    void AddOrUpdateQuote(Quote quote);
    void RemoveQuote(UUID id);
    void RemoveAllQuotes(String symbol);
    Quote GetBestQuoteWithAvailableVolume(String symbol);
    TradeResult ExecuteTrade(String symbol, int volumeRequested);
}
