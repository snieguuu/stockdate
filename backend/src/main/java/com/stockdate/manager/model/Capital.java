package com.stockdate.manager.model;

import com.stockdate.manager.dto.CashTransactionDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing capital entity.
 */
@Entity
public class Capital {

    @Id
    @GeneratedValue
    private Long id;

    //todo: remove username filed from Portfolio class after embedding it into Capital class
    @NotEmpty
    private String username;

    @OneToOne
    private Portfolio portfolio;

    private List<CashTransactionDto> transactions = new ArrayList<>();
    private double paidCash;
    private double buffer;
    //Set manually
    private double brokerageAccountStatus;

    private double gainInCurrency;
    private double capitalValue;
    private double capitalGainPercentage;

    private double cashValue;

    private double sharesInCapitalPercentage;

    private double cashInCapitalPercentage;

    //    @Value("#{sumIntendedForInvesting}")
    //todo: connect it with totalPurchasesValue from portfolio
    private double sumIntendedForInvesting;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public List<CashTransactionDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<CashTransactionDto> transactions) {
        this.transactions = transactions;
    }

    public double getPaidCash() {
        return paidCash;
    }

    public void setPaidCash(double paidCash) {
        this.paidCash = paidCash;
    }

    public double getBuffer() {
        return buffer;
    }

    public void setBuffer(double buffer) {
        this.buffer = buffer;
    }

    public double getBrokerageAccountStatus() {
        return brokerageAccountStatus;
    }

    public void setBrokerageAccountStatus(double brokerageAccountStatus) {
        this.brokerageAccountStatus = brokerageAccountStatus;
    }

    public double getGainInCurrency() {
        return gainInCurrency;
    }

    public void setGainInCurrency(double gainInCurrency) {
        this.gainInCurrency = gainInCurrency;
    }

    public double getCapitalValue() {
        return capitalValue;
    }

    public void setCapitalValue(double capitalValue) {
        this.capitalValue = capitalValue;
    }

    public double getCapitalGainPercentage() {
        return capitalGainPercentage;
    }

    public void setCapitalGainPercentage(double capitalGainPercentage) {
        this.capitalGainPercentage = capitalGainPercentage;
    }

    public double getSumIntendedForInvesting() {
        return sumIntendedForInvesting;
    }

    public void setSumIntendedForInvesting(double sumIntendedForInvesting) {
        this.sumIntendedForInvesting = sumIntendedForInvesting;
    }

    public double getCashValue() {
        return cashValue;
    }

    public void setCashValue(double cashValue) {
        this.cashValue = cashValue;
    }

    public double getSharesInCapitalPercentage() {
        return sharesInCapitalPercentage;
    }

    public void setSharesInCapitalPercentage(double sharesInCapitalPercentage) {
        this.sharesInCapitalPercentage = sharesInCapitalPercentage;
    }

    public double getCashInCapitalPercentage() {
        return cashInCapitalPercentage;
    }

    public void setCashInCapitalPercentage(double cashInCapitalPercentage) {
        this.cashInCapitalPercentage = cashInCapitalPercentage;
    }
}
