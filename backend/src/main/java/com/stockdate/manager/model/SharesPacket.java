package com.stockdate.manager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Objects;

/**
 * Class representing stock market shares packet.
 */
@Entity
public class SharesPacket {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private Date purchaseDate;

    @NotEmpty
    private String name;

    @NotEmpty
    private String ticker;

    @NotEmpty
    private int sharesAmount;

    @NotEmpty
    private double purchasePrice;

    private double purchaseValue;

    @NotEmpty
    private double currentPrice;

    private double currentValue;

    private double gainPercent;

    private double gainInCurrency;

    private double shareInPortfolioPercentage;

    private double shareInCapitalPercentage;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
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

    public double getShareInPortfolioPercentage() {
        return shareInPortfolioPercentage;
    }

    public void setShareInPortfolioPercentage(double shareInPortfolioPercentage) {
        this.shareInPortfolioPercentage = shareInPortfolioPercentage;
    }

    public double getShareInCapitalPercentage() {
        return shareInCapitalPercentage;
    }

    public void setShareInCapitalPercentage(double shareInCapitalPercentage) {
        this.shareInCapitalPercentage = shareInCapitalPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SharesPacket that = (SharesPacket) o;
        return sharesAmount == that.sharesAmount && Double.compare(that.purchasePrice, purchasePrice) == 0 && Double.compare(that.purchaseValue, purchaseValue) == 0 && Double.compare(that.currentPrice, currentPrice) == 0 && Double.compare(that.currentValue, currentValue) == 0 && Double.compare(that.gainPercent, gainPercent) == 0 && Double.compare(that.gainInCurrency, gainInCurrency) == 0 && Double.compare(that.shareInPortfolioPercentage, shareInPortfolioPercentage) == 0 && Double.compare(that.shareInCapitalPercentage, shareInCapitalPercentage) == 0 && id.equals(that.id) && username.equals(that.username) && purchaseDate.equals(that.purchaseDate) && name.equals(that.name) && ticker.equals(that.ticker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, purchaseDate, name, ticker, sharesAmount, purchasePrice, purchaseValue, currentPrice, currentValue, gainPercent, gainInCurrency, shareInPortfolioPercentage, shareInCapitalPercentage);
    }
}
