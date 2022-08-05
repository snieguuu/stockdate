package com.stockdate.manager.services;

import com.stockdate.manager.dto.CashTransactionDto;
import com.stockdate.manager.model.Capital;
import com.stockdate.manager.model.Portfolio;
import org.springframework.stereotype.Service;

/**
 * Service for calculating fields in {@link Capital} which can be calculated from {@link Portfolio} fields.
 */
@Service
public class CapitalService {


    /**
     * Method adds {@link CashTransactionDto} to the transactions.
     *
     * @param capital            capital
     * @param cashTransactionDto cash transaction to add
     */
    public void addTransaction(Capital capital, CashTransactionDto cashTransactionDto) {
        capital.getTransactions().add(cashTransactionDto);
        refreshTransactionsSum(capital);
    }

    /**
     * Method refreshing field transactionsSum in {@link Capital}.
     *
     * @param capital which needs transactionsSum field refreshment
     */
    public void refreshTransactionsSum(Capital capital) {
        double transactionsSum = capital.getTransactions().stream().mapToDouble(CashTransactionDto::getTransactionValue).sum();
        capital.setPaidCash(transactionsSum);
    }


    /**
     * Method refreshing field buffer in {@link Capital}.
     *
     * @param capital which needs buffer field refreshment
     * @return true if buffer refreshing succeeded, false otherwise
     */
    public boolean refreshBuffer(Capital capital) {
        boolean bufferRefreshResult = false;
        double sumIntendedForInvesting = capital.getSumIntendedForInvesting();
        double transactionsSum = capital.getPaidCash();
        if (sumIntendedForInvesting >= transactionsSum) {
            double buffer = sumIntendedForInvesting - transactionsSum;
            capital.setBuffer(buffer);
            bufferRefreshResult = true;
        }
        return bufferRefreshResult;
    }

    /**
     * Method refreshing field gainInCurrency in {@link Capital}.
     *
     * @param capital which needs gainInCurrency field refreshment
     */
    public void refreshGainInCurrency(Capital capital) {
        double gainInCurrency = capital.getBrokerageAccountStatus() - capital.getPaidCash();
        capital.setGainInCurrency(gainInCurrency);
    }

    /**
     * Method refreshing field capitalValue in {@link Capital}.
     *
     * @param capital which needs capitalValue field refreshment
     */
    public void refreshCapitalValue(Capital capital) {
        double capitalValue = capital.getBuffer() + capital.getBrokerageAccountStatus();
        capital.setCapitalValue(capitalValue);
    }

    /**
     * Method refreshing field capitalGainPercentage in {@link Capital}.
     *
     * @param capital which needs capitalGainPercentage field refreshment
     */
    public void refreshCapitalGainPercentage(Capital capital) {
        double gainInCurrency = capital.getGainInCurrency();
        double transactionsSum = capital.getPaidCash();
        double buffer = capital.getBuffer();
        double capitalGainPercentage = gainInCurrency / (transactionsSum + buffer) * 100;
        capital.setCapitalGainPercentage(capitalGainPercentage);
    }

    /**
     * Method refreshing field cashValue in {@link Capital}.
     *
     * @param capital which needs cashValue field refreshment
     * @return result of refreshing cash value
     */
    public boolean refreshCashValue(Capital capital) {
        boolean cashValueRefreshResult = false;
        double capitalValue = capital.getCapitalValue();
        double totalPortfolioValue = capital.getPortfolio().getTotalPortfolioValue();
        if (capitalValue >= totalPortfolioValue) {
            double cashValue = capitalValue - totalPortfolioValue;
            capital.setCashValue(cashValue);
            cashValueRefreshResult = true;
        }
        return cashValueRefreshResult;
    }

    /**
     * Method refreshing field sharesInCapitalPercentage in {@link Capital}.
     *
     * @param capital which needs sharesInCapitalPercentage field refreshment
     * @return sharesInCapitalPercentage refreshing result
     */
    public boolean refreshSharesInCapitalPercentage(Capital capital) {
        boolean sharesInCapitalPercentageResult = false;
        double capitalValue = capital.getCapitalValue();
        if (capitalValue != 0) {
            double sharesValue = capital.getPortfolio().getTotalPortfolioValue();
            double sharesInCapitalPercentage = sharesValue / capitalValue * 100;
            capital.setSharesInCapitalPercentage(sharesInCapitalPercentage);
            sharesInCapitalPercentageResult = true;
        }
        return sharesInCapitalPercentageResult;
    }

    /**
     * Method refreshing field cashInCapitalPercentage in {@link Capital}.
     *
     * @param capital which needs cashInCapitalPercentage field refreshment
     * @return cashInCapitalPercentage refresh result
     */
    public boolean refreshCashInCapitalPercentage(Capital capital) {
        boolean cashInCapitalPercentageRefreshResult = false;
        double capitalValue = capital.getCapitalValue();
        if (capitalValue != 0) {
            double cashInCapitalPercentage = capital.getCashValue() / capitalValue * 100;
            capital.setCashInCapitalPercentage(cashInCapitalPercentage);
            cashInCapitalPercentageRefreshResult = true;
        }
        return cashInCapitalPercentageRefreshResult;
    }
}
