package com.monit.quotemanager.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import javax.persistence.Entity;

@Entity
public class QuoteImpl implements Quote{

    @Id
    private UUID id;

    @NotBlank
    private String symbol;

    @NotNull
    @Positive
    private double price;

    @NotNull
    @PositiveOrZero
    private int availableVolume;

    @NotNull
    private Date expiration;

    public QuoteImpl()
    {
        id = UUID.randomUUID();
    }

    public QuoteImpl(UUID id, String symbol, double price, int availableVolume, Date expiration) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
        this.availableVolume = availableVolume;
        this.expiration = expiration;
    }

    public static QuoteImpl from(Quote quote)
    {
        return new QuoteImpl(quote.getId(), quote.getSymbol(""), quote.getPrice(), quote.getAvailableVolume(), quote.getExpiration());
    }

    @Override
    public String toString() {
        return "Quote [availableVolume=" + availableVolume + ", expiration=" + expiration + ", id=" + id + ", price="
                + price + ", symbol=" + symbol + "]";
    }
    
    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId() {   
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getSymbol(String symbol) {
        return this.symbol;
    }

    public String getSymbol()
    {
        return symbol;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;      
    }

    @Override
    public int getAvailableVolume() {
        return availableVolume;
    }

    @Override
    public void setAvailableVolume() {        
    }

    public void setAvailableVolume(int availableVolume) {  
        this.availableVolume = availableVolume;      
    }

    @Override
    public Date getExpiration() {
        return expiration;
    }

    @Override
    public void setExpiration(Date expiration) {
        this.expiration = expiration;   
    }
    
}
