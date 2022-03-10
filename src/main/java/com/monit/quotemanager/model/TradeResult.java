package com.monit.quotemanager.model;

import java.util.UUID;

public interface TradeResult {
    UUID getId();
    void setId(UUID id);
    String getSymbol();
    void setSymbol(String symbol);
    double getVolumeWeightedAveragePrice();
    void setVolumeWeightedAveragePrice(double avgPrice);
    int getVolumeRequested();
    void setVolumeRequested();
}
