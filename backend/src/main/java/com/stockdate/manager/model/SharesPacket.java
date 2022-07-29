package com.stockdate.manager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * Class representing stock market shares packet.
 */
@Entity
public class SharesPacket {

    @Id
    @GeneratedValue
    private Long id;

    // Can be updated
    @NotEmpty
    private List<Date> purchaseDates;

    @NotEmpty
    private String name;

    @NotEmpty
    private String ticker;

    // Can be updated and will be calculated after update
    @NotEmpty
    private int sharesAmount;

    // Can be updated and will be calculated after update
    @NotEmpty
    private double purchasePrice;

    // Can be updated
    @NotEmpty
    private double currentPrice;

    // Values calculated:

    private double purchaseValue;

    private double currentValue;

    private double gainPercent;

    private double gainInCurrency;

    public Long getId() {
        return id;
    }

    public List<Date> getPurchaseDates() {
        return purchaseDates;
    }

    public void setPurchaseDates(List<Date> purchaseDates) {
        this.purchaseDates = purchaseDates;
    }

    public void addPurchaseDate(Date purchaseDate) {
        purchaseDates.add(purchaseDate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getSharesAmount() {
        return sharesAmount;
    }

    public void setSharesAmount(int sharesAmount) {
        this.sharesAmount = sharesAmount;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(double purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getGainPercent() {
        return gainPercent;
    }

    public void setGainPercent(double gainPercent) {
        this.gainPercent = gainPercent;
    }

    public double getGainInCurrency() {
        return gainInCurrency;
    }

    public void setGainInCurrency(double gainInCurrency) {
        this.gainInCurrency = gainInCurrency;
    }
}
