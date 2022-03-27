package com.stockdate.manager.services;

import com.stockdate.manager.model.SharesPacket;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Service for calculating fields in {@link SharesPacket} which can be calculated from {@link NotEmpty} fields.
 */
@Service
public class PortfolioService {

    /**
     * Method calculating shares value. Can be used for calculating purchase value and current value.
     *
     * @param sharesAmount shares amount
     * @param sharePrice   one share price
     * @return shares value result
     */
    public double calculateSharesValue(int sharesAmount, double sharePrice) {
        return sharesAmount * sharePrice;
    }

    /**
     * Method calculating gain percentage for shares packet.
     *
     * @param purchasePrice purchase share price
     * @param currentPrice  current share price
     * @return gain percentage for shares packet
     */
    public double calculateGainPercentage(double purchasePrice, double currentPrice) {
        return (currentPrice - purchasePrice) / purchasePrice * 100;
    }

    /**
     * Method calculating gain in currency for share packet.
     *
     * @param purchasePrice purchase share price
     * @param currentPrice  current share price
     * @return gain in currency for share packet
     */
    public double calculateGainInCurrency(double purchasePrice, double currentPrice) {
        return currentPrice - purchasePrice;
    }

    /**
     * Method calculating shares in portfolio and shares in capital percentage.
     *
     * @param currentValue current value of share packet
     * @param totalValue   can be total portfolio value or whole capital value
     * @return shares packet in the portfolio or in the whole capital percentage
     */
    public double calculateSharesPacketPercentage(double currentValue, double totalValue) {
        return currentValue / totalValue * 100;
    }


    /**
     * Method calculating sum of values in a list. Can be used for calculating total portfolio value or
     * total portfolio gain in currency.
     *
     * @param doubleValues current values or current gains for particular shares packet
     * @return total portfolio value or total portfolio gain in currency
     */
    public double calculateTotalValue(List<Double> doubleValues) {
        //return currentVaules.stream().reduce(Double::sum).orElseGet(() -> 0.0);
        return doubleValues.stream().mapToDouble(Double::doubleValue).sum();
    }

    /**
     * Method calculating total portfolio gain percentage.
     *
     * @param totalPortfolioValue total portfolio value in currency
     * @param purchaseValuesList  list of purchase values in currency for every stock packet in portfolio
     * @return total portfolio gain percentage value
     */
    public double calculateTotalPortfolioGainPercentage(double totalPortfolioValue, List<Double> purchaseValuesList) {
        double purchaseValuesSum = purchaseValuesList.stream().mapToDouble(Double::doubleValue).sum();
        return (totalPortfolioValue - purchaseValuesSum) / purchaseValuesSum * 100;
    }


}
