package com.monit.quotemanager.model;

import java.util.Date;
import java.util.UUID;

public interface Quote {
    UUID getId();
    void setId();
    void setSymbol(String symbol);
    String getSymbol(String symbol);
    double getPrice();
    void setPrice(double price);
    int getAvailableVolume();
    void setAvailableVolume();
    Date getExpiration();
    void setExpiration(Date expiration);
}
