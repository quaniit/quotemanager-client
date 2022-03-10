package com.monit.quotemanager.controller;

import java.util.List;
import java.util.UUID;

import com.monit.quotemanager.model.QuoteImpl;
import com.monit.quotemanager.model.TradeResultImpl;
import com.monit.quotemanager.service.QuoteManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuotemanagerController {
    
    @Autowired
    private QuoteManagerService quoteManagerService;

    @GetMapping("/")
	public String index() {
		return "Index";
	}

    @GetMapping("/quotes")
    public List<QuoteImpl> findAll()
    {
        return quoteManagerService.findAll();
    }

    @GetMapping("/quotes/symbol/{symbol}")
    public List<QuoteImpl> findBySymbol(@PathVariable String symbol)
    {
        return quoteManagerService.findBySymbol(symbol);
    }

    @GetMapping("/quotes/best/{symbol}")
    public QuoteImpl findBestQuote(@PathVariable String symbol)
    {
        return (QuoteImpl) quoteManagerService.GetBestQuoteWithAvailableVolume(symbol);
    }

    @PostMapping("/quotes")
    public void addQuote(@RequestBody QuoteImpl quote)
    {
        quoteManagerService.AddOrUpdateQuote(quote);
    }

    @PostMapping("/quotes/trade")
    public TradeResultImpl ExecuteTrade(@RequestParam(value = "symbol") String symbol,
                                        @RequestParam(value = "volumeRequested") int volumeRequested)
    {
        return (TradeResultImpl) quoteManagerService.ExecuteTrade(symbol, volumeRequested);
    } 

    @PutMapping("/quotes")
    public void updateQuote(@RequestBody QuoteImpl quote)
    {
        quoteManagerService.AddOrUpdateQuote(quote);
    }

    @DeleteMapping("quotes/id/{id}")
    public void deleteQuote(@PathVariable String id)
    {
        UUID uuid = UUID.fromString(id);
        quoteManagerService.RemoveQuote(uuid);
    }

    @DeleteMapping("quotes/symbol/{symbol}")
    public void deleteQuotesBySymbol(@PathVariable String symbol)
    {
        quoteManagerService.RemoveAllQuotes(symbol);
    }

}
