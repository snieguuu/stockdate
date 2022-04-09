package com.stockdate.manager.services;

import com.stockdate.manager.model.SharesPacket;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;

/**
 * Service for calculating fields in {@link SharesPacket} which can be calculated from {@link NotEmpty} fields.
 */
@Service
public class SharesPacketService {

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
     * Method setting shares packet values after recalculation.
     *
     * @param sharesPacket    shares packet
     * @param resultValuesMap {@link HashMap} with recalculated values
     */
    public void setSharesPacketValuesAfterChange(SharesPacket sharesPacket, HashMap<String, Double> resultValuesMap) {
        sharesPacket.setPurchaseValue(resultValuesMap.get("purchaseValue"));
        sharesPacket.setCurrentValue(resultValuesMap.get("currentSharesPacketValue"));
        sharesPacket.setGainPercent(resultValuesMap.get("gainPercentage"));
        sharesPacket.setGainInCurrency(resultValuesMap.get("gainInCurrency"));
        sharesPacket.setSharesPacketInPortfolioPercentage(resultValuesMap.get("sharesPacketInPortfolioPercentage"));
        sharesPacket.setSharesPacketInCapitalPercentage(resultValuesMap.get("sharesPacketInCapitalPercentage"));
    }
}
