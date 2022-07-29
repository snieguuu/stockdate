package com.stockdate.manager.services;

import com.stockdate.manager.dao.PortfolioRepository;
import com.stockdate.manager.model.Portfolio;
import com.stockdate.manager.model.SharesPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service for calculating fields in {@link Portfolio} which can be calculated from {@link SharesPacket} {@link NotEmpty} fields.
 */
@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private SharesPacketService sharesPacketService;

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
     * Method calculating sum of values in a list. Can be used for calculating total portfolio value, total purchases value or
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
     * Method calculating total portfolio gain in currency based on total portfolio value and total purchases value.
     *
     * @param totalPortfolioValue total portfolio value
     * @param totalPurchasesValue total purchases value
     * @return total portfolio gain in currency
     */
    public double calculateTotalPortfolioGainInCurrency(double totalPortfolioValue, double totalPurchasesValue) {
        return totalPortfolioValue - totalPurchasesValue;
    }

    /**
     * Method calculating total portfolio gain percentage.
     *
     * @param totalPortfolioValue total portfolio value in currency
     * @param purchaseValueList   list of purchase values in currency for every stock packet in portfolio
     * @return total portfolio gain percentage value
     */
    public double calculateTotalPortfolioGainPercentage(double totalPortfolioValue, List<Double> purchaseValueList) {
        double purchaseValuesSum = purchaseValueList.stream().mapToDouble(Double::doubleValue).sum();
        return (totalPortfolioValue - purchaseValuesSum) / purchaseValuesSum * 100;
    }

    /**
     * Method adding shares packet to the portfolio.
     *
     * @param portfolio    portfolio which shares packet should be added to
     * @param sharesPacket shares packet being added to the portfolio
     */
    public void addSharesPacket(Portfolio portfolio, SharesPacket sharesPacket) {
        portfolio.getSharesPacketList().add(sharesPacket);
        recalculatePortfolio(portfolio);
    }

    /**
     * Get portfolio for the user or create new portfolio if it doesn't exist.
     *
     * @param username name of the user whom portfolio is added for
     * @return portfolio for the user
     */
    public Portfolio getPortfolioCreateIfDoesNotExist(String username) {
        Portfolio portfolio = portfolioRepository.findByUsername(username);
        if (portfolio == null) {
            portfolio = new Portfolio();
            portfolio.setUsername(username);
            portfolioRepository.save(portfolio);
        }
        return portfolio;
    }

    /**
     * Method recalculating values in {@link SharesPacket} and {@link Portfolio} after change.
     *
     * @param portfolio portfolio
     */
    //todo: not finished
    public void recalculatePortfolio(Portfolio portfolio) {
        List<SharesPacket> sharesPacketList = portfolio.getSharesPacketList();


        List<Double> currentValues = portfolio.getSharesPacketList().stream().map(SharesPacket::getCurrentValue).collect(Collectors.toList());
        List<Double> gainInCurrencyList = portfolio.getSharesPacketList().stream().map(SharesPacket::getGainInCurrency).collect(Collectors.toList());
        List<Double> purchaseValueList = portfolio.getSharesPacketList().stream().map(SharesPacket::getPurchaseValue).collect(Collectors.toList());
        double totalPortfolioValue = calculateTotalValue(currentValues);
        double totalPortfolioGainInCurrency = calculateTotalValue(gainInCurrencyList);

        double totalPortfolioGainPercent = calculateTotalPortfolioGainPercentage(totalPortfolioValue, purchaseValueList);

        List<Double> sharesPacketPercentageList = currentValues.stream().map(currentValue -> calculateSharesPacketPercentage(currentValue, totalPortfolioValue))
                .collect(Collectors.toList());
//        portfolio.getSharesPacketList().forEach(sp ->);

        portfolioRepository.save(portfolio);
    }

    /**
     * Method getting {@link SharesPacket} with particular ticker for particular user.
     *
     * @param portfolio particular portfolio
     * @param ticker    ticker of {@link SharesPacket}
     * @return {@link SharesPacket}
     */
    public SharesPacket getSharesPacket(Portfolio portfolio, String ticker) {
        return portfolio.getSharesPacketList().stream().filter(sharesPacket -> sharesPacket.getTicker().equals(ticker))
                .findFirst().orElseGet(SharesPacket::new);
    }

    /**
     * Method updates {@link SharesPacket} in the {@link Portfolio} sharesPacketList.
     *
     * @param portfolio    portfolio
     * @param sharesPacket shares packet to update
     * @return true if shares packet was updated, false otherwise
     */
    public boolean updateSharesPacketInPortfolio(Portfolio portfolio, SharesPacket sharesPacket) {
        SharesPacket foundSharesPacket = portfolio.getSharesPacketList().stream().filter(sp -> sp.getId().equals(sharesPacket.getId()))
                .findFirst().orElseGet(SharesPacket::new);
        int index = portfolio.getSharesPacketList().indexOf(foundSharesPacket);
        if (index != -1) {
            portfolio.getSharesPacketList().set(index, sharesPacket);
            recalculatePortfolio(portfolio);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method removes {@link SharesPacket} with particular ticker from sharesPacketList.
     *
     * @param portfolio portfolio
     * @param ticker    ticker of shares packet
     * @return true if shares packet was removed, false otherwise
     */
    public boolean deleteSharesPacketFromPortfolio(Portfolio portfolio, String ticker) {
        SharesPacket foundSharesPacket = portfolio.getSharesPacketList().stream().filter(sp -> sp.getTicker().equals(ticker))
                .findFirst().orElseGet(SharesPacket::new);
        int index = portfolio.getSharesPacketList().indexOf(foundSharesPacket);
        if (index != -1) {
            portfolio.getSharesPacketList().remove(index);
            recalculatePortfolio(portfolio);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method refreshes shares packet in the portfolio percentage value.
     *
     * @param portfolio portfolio to update
     */
    public void refreshSharesPacketsInPortfolioPercentage(Portfolio portfolio) {
        double totalPortfolioValue = portfolio.getTotalPortfolioValue();
        Map<String, Double> tickerToCurrentValueMap = portfolio.getSharesPacketList().stream().collect(Collectors.toMap(SharesPacket::getTicker, SharesPacket::getCurrentValue));

        tickerToCurrentValueMap.entrySet().stream().map(entry -> calculateSharesPacketInPortfolioPercentage(entry.getKey(), entry.getValue(), totalPortfolioValue))
                .forEach(pair -> portfolio.putPercentageToSharesPacketInPortfolioPercentageMap(pair.getFirst(), pair.getSecond()));
    }

    private Pair<String, Double> calculateSharesPacketInPortfolioPercentage(String ticker, double currentValue, double totalPortfolioValue) {
        return Pair.of(ticker, currentValue / totalPortfolioValue * 100);
    }
}
