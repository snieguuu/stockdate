package com.stockdate.manager.services;

import com.stockdate.manager.model.SharesPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;

/**
 * Service for calculating fields in {@link SharesPacket} which can be calculated from {@link NotEmpty} fields.
 */
@Service
public class SharesPacketService {

    private static final Logger logger = LoggerFactory.getLogger(SharesPacketService.class);

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
     * @param purchaseValue shares purchase value
     * @param currentValue  shares current value
     * @return gain percentage for shares packet
     */
    public double calculateGainPercentage(double purchaseValue, double currentValue) {
        return (currentValue - purchaseValue) / purchaseValue * 100;
    }

    /**
     * Method calculating gain in currency for share packet.
     *
     * @param purchaseValue shares purchase value
     * @param currentValue  shares current value
     * @return gain in currency for share packet
     */
    public double calculateGainInCurrency(double purchaseValue, double currentValue) {
        return currentValue - purchaseValue;
    }

    /**
     * Method recalculating below fields:
     * purchase value, current value, gain percentage, gain in currency
     * in {@link SharesPacket} after changing below fields:
     * shares amount, purchase price or current price.
     *
     * @param sharesPacket shares packet which needed recalculation
     */
    public void recalculateSharesPacket(SharesPacket sharesPacket) {
        int sharesAmount = sharesPacket.getSharesAmount();
        double purchasePrice = sharesPacket.getPurchasePrice();
        double currentPrice = sharesPacket.getCurrentPrice();

        double newPurchaseValue = calculateSharesValue(sharesAmount, purchasePrice);
        double newCurrentValue = calculateSharesValue(sharesAmount, currentPrice);
        double newGainPercent = calculateGainPercentage(newPurchaseValue, newCurrentValue);
        double newGainInCurrency = calculateGainInCurrency(newPurchaseValue, newCurrentValue);

        sharesPacket.setPurchaseValue(newPurchaseValue);
        sharesPacket.setCurrentValue(newCurrentValue);
        sharesPacket.setGainPercent(newGainPercent);
        sharesPacket.setGainInCurrency(newGainInCurrency);
    }

    /**
     * Method updating shares packet after buying more shares.
     *
     * @param sharesPacket  shares packet that were bought more
     * @param sharesAmount  amount of new shares
     * @param purchasePrice price of new shares
     */
    public void updateSharesPacketAfterBuy(SharesPacket sharesPacket, int sharesAmount, double purchasePrice) {
        int previousSharesAmount = sharesPacket.getSharesAmount();
        double previousPurchasePrice = sharesPacket.getPurchasePrice();
        int newSharesAmount = previousSharesAmount + sharesAmount;
        //todo: check if data are valid, eg. newSharesAmount not equal to zero, return value or throw an exception
        double newPurchasePrice = (previousSharesAmount * previousPurchasePrice + sharesAmount * purchasePrice) / newSharesAmount;
        sharesPacket.setSharesAmount(newSharesAmount);
        sharesPacket.setPurchasePrice(newPurchasePrice);
        recalculateSharesPacket(sharesPacket);
        //Todo: whole capital tracking
//        recalculateCapital(sharesPacket.getTicker(), sharesAmount, sellingPrice);
    }

    /**
     * Method updating shares packet after sell some shares.
     *
     * @param sharesPacket shares packet which part was sold of
     * @param sharesAmount amount of sold shares
     */
    public void updateSharesPacketAfterSell(SharesPacket sharesPacket, int sharesAmount) {
        int previousSharesAmount = sharesPacket.getSharesAmount();
        int newSharesAmount = previousSharesAmount - sharesAmount;
        if (newSharesAmount < 0) {
            //todo: return value or throw an exception instead of logging an error
            logger.error("You can't sell more actions than you have");
            return;
        }
        if (newSharesAmount == 0) {
            logger.info("You sold all your " + sharesPacket.getTicker() + " shares");
        }
        sharesPacket.setSharesAmount(newSharesAmount);
        recalculateSharesPacket(sharesPacket);
        //Todo: whole capital tracking
//        recalculateCapital(sharesPacket.getTicker(), sharesAmount, sellingPrice);
    }
}
