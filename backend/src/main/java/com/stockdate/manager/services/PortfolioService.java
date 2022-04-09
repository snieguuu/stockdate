package com.stockdate.manager.services;

import com.stockdate.manager.dao.PortfolioRepository;
import com.stockdate.manager.model.Portfolio;
import com.stockdate.manager.model.SharesPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;

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

    /**
     * Method adding shares packet to the portfolio.
     *
     * @param portfolio    portfolio which shares packet should be added to
     * @param sharesPacket shares packet being added to the portfolio
     */
    public void addSharesPacketToPortfolio(Portfolio portfolio, SharesPacket sharesPacket) {
        portfolio.addSharesPacket(sharesPacket);
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
     * @param sharesPacket shares packet
     * @param portfolio    portfolio
     * @return {@link HashMap} with {@link String} as a key and {@link Double} as a value
     */
    public HashMap<String, Double> recalculatePortfolio(SharesPacket sharesPacket, Portfolio portfolio) {
        HashMap<String, Double> resultValuesMap = new HashMap<>();
        int sharesAmount = sharesPacket.getSharesAmount();
        double purchasePrice = sharesPacket.getPurchasePrice();
        double currentPrice = sharesPacket.getCurrentPrice();

        double currentSharesPacketValue = sharesPacketService.calculateSharesValue(sharesAmount, currentPrice);
        double totalPortfolioValue = portfolio.getTotalPortfolioValue();
        double capitalInCurrency = portfolio.getCapitalInCurrency();

        resultValuesMap.put("purchaseValue", sharesPacketService.calculateSharesValue(sharesAmount, purchasePrice));
        resultValuesMap.put("currentSharesPacketValue", currentSharesPacketValue);
        resultValuesMap.put("gainPercentage", sharesPacketService.calculateGainPercentage(purchasePrice, currentPrice));
        resultValuesMap.put("gainInCurrency", sharesPacketService.calculateGainInCurrency(purchasePrice, currentPrice));
        resultValuesMap.put("totalPortfolioValue", totalPortfolioValue);
        resultValuesMap.put("capitalInCurrency", capitalInCurrency);
        resultValuesMap.put("sharesPacketInPortfolioPercentage",
                calculateSharesPacketPercentage(currentSharesPacketValue, totalPortfolioValue));
        resultValuesMap.put("sharesPacketInCapitalPercentage",
                calculateSharesPacketPercentage(currentSharesPacketValue, capitalInCurrency));

        return resultValuesMap;
    }

    /**
     * Method setting portfolio values after recalculation.
     *
     * @param portfolio            portfolio
     * @param sharesPacket         shares packet
     * @param resultValuesMap      {@link HashMap} with recalculated values
     * @param sharesPacketToUpdate indicate is {@link SharesPacket} should be updated (true), or added (false)
     */
    public void setPortfolioValuesAfterChange(Portfolio portfolio, SharesPacket sharesPacket,
                                              HashMap<String, Double> resultValuesMap, boolean sharesPacketToUpdate) {
        if (sharesPacketToUpdate) {
            portfolio.updateSharesPacket(sharesPacket);
        } else {
            portfolio.addSharesPacket(sharesPacket);
        }
        portfolio.setTotalPortfolioValue(resultValuesMap.get("totalPortfolioValue"));
        portfolio.setCapitalInCurrency(resultValuesMap.get("capitalInCurrency"));

    }

    /**
     * Method getting {@link SharesPacket} with particular ticker for particular user.
     * @param username name of the portfolio owner
     * @param ticker ticker of {@link SharesPacket}
     * @return {@link SharesPacket}
     */
    public SharesPacket getSharesPacket(String username, String ticker) {
        Portfolio portfolio = portfolioRepository.findByUsername(username);
        return portfolio.getSharesPacketList().stream().filter(sharesPacket -> sharesPacket.getTicker().equals(ticker))
                .findFirst().orElseGet(SharesPacket::new);
    }


}
