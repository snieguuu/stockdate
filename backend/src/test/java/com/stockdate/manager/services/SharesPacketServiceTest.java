package com.stockdate.manager.services;

import com.stockdate.manager.model.SharesPacket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class SharesPacketServiceTest {

    private final static int SHARES_AMOUNT = 25;
    private final static double SHARE_PRICE = 100;
    private final static double CURRENT_PRICE = 200;

    @Spy
    SharesPacket sharesPacket;

    @InjectMocks
    SharesPacketService sharesPacketService;

    @Test
    void should_CalculateSharesValue() {
        //given
        //when
        double sharesValue = sharesPacketService.calculateSharesValue(SHARES_AMOUNT, SHARE_PRICE);
        //then
        assertEquals(2500, sharesValue);
    }

    @Test
    void should_CalculateGainPercentage() {
        //given
        //when
        double gain = sharesPacketService.calculateGainPercentage(SHARE_PRICE, CURRENT_PRICE);
        //then
        assertEquals(100, gain);
    }

    @Test
    void should_CalculateGainInCurrency() {
        //given
        //when
        double gain = sharesPacketService.calculateGainInCurrency(SHARE_PRICE, CURRENT_PRICE);
        //then
        assertEquals(100, gain);
    }

    @Test
    void should_RecalculateSharesPacket() {
        //given
        sharesPacket.setSharesAmount(100);
        sharesPacket.setPurchasePrice(20);
        sharesPacket.setCurrentPrice(30);
        //when
        sharesPacketService.recalculateSharesPacket(sharesPacket);
        //then
        assertEquals(2000, sharesPacket.getPurchaseValue());
        assertEquals(3000, sharesPacket.getCurrentValue());
        assertEquals(50, sharesPacket.getGainPercent());
        assertEquals(1000, sharesPacket.getGainInCurrency());

        verify(sharesPacket, times(1)).setPurchaseValue(2000);
        verify(sharesPacket, times(1)).setCurrentValue(3000);
        verify(sharesPacket, times(1)).setGainPercent(50);
        verify(sharesPacket, times(1)).setGainInCurrency(1000);
    }

    @Test
    void should_UpdateSharesPacketAfterBuy() {
        //given
        sharesPacket.setSharesAmount(100);
        sharesPacket.setPurchasePrice(20);
        sharesPacket.setCurrentPrice(30);
        //when
        sharesPacketService.updateSharesPacketAfterBuy(sharesPacket, 100, 40);
        //then
        verify(sharesPacket, times(1)).setSharesAmount(200);
        verify(sharesPacket, times(1)).setPurchasePrice(30);
        assertEquals(200, sharesPacket.getSharesAmount());
        assertEquals(30, sharesPacket.getPurchasePrice());
    }

    @Test
    void should_UpdateSharesPacketAfterSell() {
        //given
        sharesPacket.setSharesAmount(100);
        //when
        sharesPacketService.updateSharesPacketAfterSell(sharesPacket, 20);
        //then
        verify(sharesPacket, times(1)).setSharesAmount(80);
        assertEquals(80, sharesPacket.getSharesAmount());
    }
}