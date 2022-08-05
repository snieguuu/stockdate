package com.stockdate.manager.dto;

import com.stockdate.manager.model.SharesPacket;
import com.stockdate.manager.model.SharesPacketUpdateType;

import java.util.Date;

/**
 * DTO with {@link SharesPacket} information updated:
 * - purchase date
 * - update type (buy or sell: {@link SharesPacketUpdateType})
 * - amount of shares bought or sold
 * - purchase or selling price
 */
public class SharesPacketInfoUpdateDto {

    private Date purchaseDate;
    private SharesPacketUpdateType sharesPacketUpdateType;
    private int sharesAmount;
    private double purchaseOrSellingPrice;

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public SharesPacketUpdateType getUpdateType() {
        return sharesPacketUpdateType;
    }

    public void setUpdateType(SharesPacketUpdateType sharesPacketUpdateType) {
        this.sharesPacketUpdateType = sharesPacketUpdateType;
    }

    public int getSharesAmount() {
        return sharesAmount;
    }

    public void setSharesAmount(int sharesAmount) {
        this.sharesAmount = sharesAmount;
    }

    public double getPurchaseOrSellingPrice() {
        return purchaseOrSellingPrice;
    }

    public void setPurchaseOrSellingPrice(double purchaseOrSellingPrice) {
        this.purchaseOrSellingPrice = purchaseOrSellingPrice;
    }
}
