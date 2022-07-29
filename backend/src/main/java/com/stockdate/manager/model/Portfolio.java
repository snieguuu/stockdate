package com.stockdate.manager.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * Class representing shares portfolio entity.
 */
@Entity
public class Portfolio {

    @Id
    @GeneratedValue
    private Long id;

    //todo: move to Capital
    @NotEmpty
    private String username;

    @OneToOne
    private Capital capital;

    @OneToMany(/*targetEntity = SharesPacket.class*/)
    private List<SharesPacket> sharesPacketList;

    private Map<String, Double> sharesPacketInPortfolioPercentageMap;

    private Map<String, Double> sharesPacketInCapitalPercentageMap;

    private double totalPurchasesValue;

    private double totalPortfolioValue;

    private double totalPortfolioGainPercentage;

    private double totalPortfolioGainInCurrency;

    private double capitalInCurrency;

    //Value of cash on all brokerage accounts - set manually
    private double cashValue;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<SharesPacket> getSharesPacketList() {
        return sharesPacketList;
    }

    public void setSharesPacketList(List<SharesPacket> shares) {
        this.sharesPacketList = shares;
    }

    public Capital getCapital() {
        return capital;
    }

    public void setCapital(Capital capital) {
        this.capital = capital;
    }

    public double getTotalPurchasesValue() {
        return totalPurchasesValue;
    }

    public void setTotalPurchasesValue(double totalPurchasesValue) {
        this.totalPurchasesValue = totalPurchasesValue;
    }

    public double getTotalPortfolioValue() {
        return totalPortfolioValue;
    }

    public void setTotalPortfolioValue(double totalPortfolioValue) {
        this.totalPortfolioValue = totalPortfolioValue;
    }

    public double getTotalPortfolioGainPercentage() {
        return totalPortfolioGainPercentage;
    }

    public void setTotalPortfolioGainPercentage(double totalPortfolioGainPercentage) {
        this.totalPortfolioGainPercentage = totalPortfolioGainPercentage;
    }

    public double getTotalPortfolioGainInCurrency() {
        return totalPortfolioGainInCurrency;
    }

    public void setTotalPortfolioGainInCurrency(double totalPortfolioGainInCurrency) {
        this.totalPortfolioGainInCurrency = totalPortfolioGainInCurrency;
    }

    public double getCapitalInCurrency() {
        return capitalInCurrency;
    }

    public void setCapitalInCurrency(double capitalInCurrency) {
        this.capitalInCurrency = capitalInCurrency;
    }

    public Map<String, Double> getSharesPacketInPortfolioPercentageMap() {
        return sharesPacketInPortfolioPercentageMap;
    }

    public void setSharesPacketInPortfolioPercentageMap(Map<String, Double> sharesPacketInPortfolioPercentageMap) {
        this.sharesPacketInPortfolioPercentageMap = sharesPacketInPortfolioPercentageMap;
    }

    public void putPercentageToSharesPacketInPortfolioPercentageMap(String ticker, double percentage) {
        this.sharesPacketInPortfolioPercentageMap.put(ticker, percentage);
    }

    public Map<String, Double> getSharesPacketInCapitalPercentageMap() {
        return sharesPacketInCapitalPercentageMap;
    }

    public void setSharesPacketInCapitalPercentageMap(Map<String, Double> sharesPacketInCapitalPercentageMap) {
        this.sharesPacketInCapitalPercentageMap = sharesPacketInCapitalPercentageMap;
    }

    public double getCashValue() {
        return cashValue;
    }

    public void setCashValue(double cashValue) {
        this.cashValue = cashValue;
    }
}
