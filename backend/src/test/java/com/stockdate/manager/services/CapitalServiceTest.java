package com.stockdate.manager.services;

import com.stockdate.manager.dto.CashTransactionDto;
import com.stockdate.manager.model.Capital;
import com.stockdate.manager.model.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CapitalServiceTest {

    @Spy
    private Capital capital = new Capital();

    @InjectMocks
    private CapitalService capitalService;

    @BeforeEach
    void setUp() {
        CashTransactionDto cashTransactionDto1 = new CashTransactionDto();
        CashTransactionDto cashTransactionDto2 = new CashTransactionDto();
        CashTransactionDto cashTransactionDto3 = new CashTransactionDto();
        cashTransactionDto1.setTransactionValue(2000);
        cashTransactionDto2.setTransactionValue(3000);
        cashTransactionDto3.setTransactionValue(5000);
        capitalService.addTransaction(capital, cashTransactionDto1);
        capitalService.addTransaction(capital, cashTransactionDto2);
        capitalService.addTransaction(capital, cashTransactionDto3);
    }

    @Test
    void should_RefreshTransactionsSum_When_TransactionListNotEmpty() {
        //given
        //when
        capitalService.refreshTransactionsSum(capital);
        //then
        assertEquals(5000, capital.getPaidCash(), 10000);
        verify(capital, times(1)).setPaidCash(5000);
    }

    @Test
    void should_RefreshTransactionSumAsZeroWhenTransactionListIsEmpty() {
        //when
        capital.setTransactions(new ArrayList<>());
        capitalService.refreshTransactionsSum(capital);
        //then
        assertEquals(0, capital.getPaidCash());
        verify(capital, times(1)).setPaidCash(0);
    }

    @Test
    void should_RefreshBuffer_When_sumIntendedForInvestingGraterThanTransactionsSum() {
        //given
        capital.setSumIntendedForInvesting(12000);
        //when
        boolean bufferRefreshResult = capitalService.refreshBuffer(capital);
        //then
        assertEquals(2000, capital.getBuffer());
        assertTrue(bufferRefreshResult);
        verify(capital, times(1)).setBuffer(2000);
    }

    @Test
    void should_RefreshBuffer_When_sumIntendedForInvestingIsTheSameAsTransactionsSum() {
        //given
        capital.setSumIntendedForInvesting(10000);
        //when
        boolean bufferRefreshResult = capitalService.refreshBuffer(capital);
        //then
        assertEquals(0, capital.getBuffer());
        assertTrue(bufferRefreshResult);
        verify(capital, times(1)).setBuffer(0);
    }

    @Test
    void shouldNot_RefreshBuffer_When_sumIntendedForInvestingLowerThanTransactionsSum() {
        //given
        capital.setSumIntendedForInvesting(9000);
        //when
        boolean bufferRefreshResult = capitalService.refreshBuffer(capital);
        //then
        assertFalse(bufferRefreshResult);
        verify(capital, never()).setBuffer(-1000);
    }

    @Test
    void should_RefreshGainInCurrency() {
        //given
        capital.setBrokerageAccountStatus(9000);
        capital.setPaidCash(6000);
        //when
        capitalService.refreshGainInCurrency(capital);
        //then
        assertEquals(3000, capital.getGainInCurrency());
        verify(capital, times(1)).setGainInCurrency(3000);
    }

    @Test
    void should_RefreshCapitalValue() {
        //given
        capital.setBuffer(3000);
        capital.setBrokerageAccountStatus(6000);
        //when
        capitalService.refreshCapitalValue(capital);
        //then
        assertEquals(9000, capital.getCapitalValue());
        verify(capital, times(1)).setCapitalValue(9000);
    }

    @Test
    void should_RefreshCapitalGainPercentage() {
        //given
        capital.setGainInCurrency(2000);
        capital.setPaidCash(2000);
        capital.setBuffer(8000);
        //when
        capitalService.refreshCapitalGainPercentage(capital);
        //then
        assertEquals(20, capital.getCapitalGainPercentage());
        verify(capital, times(1)).setCapitalGainPercentage(20);
    }

    @Test
    void should_RefreshCashValue_When_CapitalValueIsGraterThanTotalPortfolioValue() {
        //given
        Portfolio portfolio = new Portfolio();
        portfolio.setTotalPortfolioValue(8000);
        capital.setPortfolio(portfolio);
        capital.setCapitalValue(10000);
        //when
        boolean refreshmentResult = capitalService.refreshCashValue(capital);
        //then
        assertTrue(refreshmentResult);
        assertEquals(2000, capital.getCashValue());
        verify(capital, times(1)).setCashValue(2000);
    }

    @Test
    void should_RefreshCashValue_When_CapitalValueIsEqualToTotalPortfolioValue() {
        //given
        Portfolio portfolio = new Portfolio();
        portfolio.setTotalPortfolioValue(10000);
        capital.setPortfolio(portfolio);
        capital.setCapitalValue(10000);
        //when
        boolean refreshmentResult = capitalService.refreshCashValue(capital);
        //then
        assertTrue(refreshmentResult);
        assertEquals(0, capital.getCashValue());
        verify(capital, times(1)).setCashValue(0);
    }

    @Test
    void shouldNot_RefreshCashValue_When_CapitalValueIsSmallerThanTotalPortfolioValue() {
        //given
        Portfolio portfolio = new Portfolio();
        portfolio.setTotalPortfolioValue(10000);
        capital.setPortfolio(portfolio);
        capital.setCapitalValue(8000);
        //when
        boolean refreshmentResult = capitalService.refreshCashValue(capital);
        //then
        assertFalse(refreshmentResult);
        verify(capital, never()).setCashValue(-2000);
    }

    @Test
    void should_RefreshSharesInCapitalPercentage_When_CapitalValueIsNotEqualZero() {
        //given
        Portfolio portfolio = new Portfolio();
        portfolio.setTotalPortfolioValue(10000);
        capital.setPortfolio(portfolio);
        capital.setCapitalValue(20000);
        //when
        boolean refreshmentResult = capitalService.refreshSharesInCapitalPercentage(capital);
        //then
        assertTrue(refreshmentResult);
        assertEquals(50, capital.getSharesInCapitalPercentage());
        verify(capital, times(1)).setSharesInCapitalPercentage(50);
    }

    @Test
    void shouldNot_RefreshSharesInCapitalPercentage_When_CapitalValueEqualsZero() {
        //given
        Portfolio portfolio = new Portfolio();
        portfolio.setTotalPortfolioValue(10000);
        capital.setPortfolio(portfolio);
        capital.setCapitalValue(0);
        //when
        boolean refreshmentResult = capitalService.refreshSharesInCapitalPercentage(capital);
        //then
        assertFalse(refreshmentResult);
        verify(capital, never()).setSharesInCapitalPercentage(50);
    }

    @Test
    void should_RefreshCashInCapitalPercentage_When_CapitalValueIsNotEqualZero() {
        //given
        capital.setCapitalValue(20000);
        capital.setCashValue(10000);
        //when
        boolean refreshmentResult = capitalService.refreshCashInCapitalPercentage(capital);
        //then
        assertTrue(refreshmentResult);
        assertEquals(50, capital.getCashInCapitalPercentage());
        verify(capital, times(1)).setCashInCapitalPercentage(50);
    }

    @Test
    void shouldNot_RefreshCashInCapitalPercentage_When_CapitalValueEqualsZero() {
        //given
        capital.setCapitalValue(0);
        capital.setCashValue(10000);
        //when
        boolean refreshmentResult = capitalService.refreshCashInCapitalPercentage(capital);
        //then
        assertFalse(refreshmentResult);
        verify(capital, never()).setCashInCapitalPercentage(50);
    }
}