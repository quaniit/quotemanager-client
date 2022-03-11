package com.monit.quotemanager.controller;

import java.util.List;
import java.util.UUID;

import com.monit.quotemanager.model.QuoteImpl;
import com.monit.quotemanager.model.TradeResultImpl;
import com.monit.quotemanager.service.QuoteManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quotes")
public class QuotemanagerController {
    
    @Autowired
    private QuoteManagerService quoteManagerService;

    @GetMapping()
    public List<QuoteImpl> getAll()
    {
        return quoteManagerService.findAll();
    }

    @GetMapping("/symbol")
    public List<QuoteImpl> getBySymbol(@RequestParam(value = "symbol", required = true) String symbol)
    {
        return quoteManagerService.findBySymbol(symbol);
    }

    @GetMapping("/best")
    public QuoteImpl getBestQuote(@RequestParam(value = "symbol", required = true) String symbol)
    {
        return (QuoteImpl) quoteManagerService.GetBestQuoteWithAvailableVolume(symbol);
    }

    @PostMapping()
    public ResponseEntity<String> addQuote(@RequestBody QuoteImpl quote)
    {
        quoteManagerService.AddOrUpdateQuote(quote);
        return new ResponseEntity<String>(String.format("Quote for %s successfully added", quote.getSymbol()), HttpStatus.CREATED);
    }

    @PostMapping("/trade")
    public TradeResultImpl ExecuteTrade(@RequestParam(value = "symbol", required = true) String symbol,
                                        @RequestParam(value = "volumeRequested", required = true) int volumeRequested)
    {
        return (TradeResultImpl) quoteManagerService.ExecuteTrade(symbol, volumeRequested);
    } 

    @PutMapping()
    public ResponseEntity<String> updateQuote(@RequestBody QuoteImpl quote)
    {
        quoteManagerService.AddOrUpdateQuote(quote);
        return new ResponseEntity<String>(String.format("Quote for %s successfully updated", quote.getSymbol()), HttpStatus.OK);

    }

    @DeleteMapping("/id")
    public ResponseEntity<String> deleteQuote(@RequestParam(value = "id", required = true) String id)
    {
        UUID uuid = UUID.fromString(id);
        quoteManagerService.RemoveQuote(uuid);
        return new ResponseEntity<String>(String.format("Quote for %s successfully deleted", id), HttpStatus.OK);

    }

    @DeleteMapping("/symbol")
    public ResponseEntity<String> deleteQuotesBySymbol(@RequestParam(value = "symbol", required = true) String symbol)
    {
        quoteManagerService.RemoveAllQuotes(symbol);
        return new ResponseEntity<String>(String.format("Quotes for %s successfully deleted", symbol), HttpStatus.OK);

    }

}
