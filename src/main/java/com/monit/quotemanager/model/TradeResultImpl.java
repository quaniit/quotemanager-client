package com.monit.quotemanager.model;

import java.util.UUID;

public class TradeResultImpl implements TradeResult {
    private UUID id;
    private String symbol;
    private double volumeWeightedAveragePrice;
    private int volumeRequested;
    
    public TradeResultImpl(UUID id, String symbol, double volumeWeightedAveragePrice, int volumeRequested) {
        this.id = id;
        this.symbol = symbol;
        this.volumeWeightedAveragePrice = volumeWeightedAveragePrice;
        this.volumeRequested = volumeRequested;
    }

    @Override
    public String toString() {
        return "TradeResultImpl [id=" + id + ", symbol=" + symbol + ", volumeRequested=" + volumeRequested
                + ", volumeWeightedAveragePrice=" + volumeWeightedAveragePrice + "]";
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public double getVolumeWeightedAveragePrice() {
        return volumeWeightedAveragePrice;
    }

    @Override
    public void setVolumeWeightedAveragePrice(double avgPrice) {
        this.volumeWeightedAveragePrice = avgPrice;        
    }

    @Override
    public int getVolumeRequested() {
        return volumeRequested;
    }

    @Override
    public void setVolumeRequested() {
    }

    public void setVolumeRequested(int volumeRequested) {
        this.volumeRequested = volumeRequested;
    }
    
}
