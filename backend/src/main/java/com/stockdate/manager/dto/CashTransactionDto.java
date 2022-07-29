package com.stockdate.manager.dto;

import com.stockdate.manager.model.Capital;

import java.util.Date;

/**
 * Cash transaction as a DTO used in {@link Capital}.
 */
public class CashTransactionDto {
    private Date transactionsDate;
    double transactionValue;
    String brokerageAccountName;

    public Date getTransactionsDate() {
        return transactionsDate;
    }

    public void setTransactionsDate(Date transactionsDate) {
        this.transactionsDate = transactionsDate;
    }

    public double getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(double transactionValue) {
        this.transactionValue = transactionValue;
    }

    public String getBrokerageAccountName() {
        return brokerageAccountName;
    }

    public void setBrokerageAccountName(String brokerageAccountName) {
        this.brokerageAccountName = brokerageAccountName;
    }
}
