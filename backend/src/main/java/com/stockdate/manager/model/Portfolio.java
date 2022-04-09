package com.stockdate.manager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

/**
 * Class representing shares portfolio entity.
 */
@Entity
public class Portfolio {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String username;

    @OneToOne(targetEntity = SharesPacket.class)
    private List<SharesPacket> sharesPacketList;

    private double totalPortfolioValue;

    private double totalPortfolioGainPercentage;

    private double totalPortfolioGainCurrency;

    private double capitalInCurrency;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<SharesPacket> getSharesPacketList() {
        return sharesPacketList;
    }

    public void setSharesPacketList(List<SharesPacket> shares) {
        this.sharesPacketList = shares;
    }

    /**
     * Method adds {@link SharesPacket} to the sharesPacketList.
     *
     * @param sharesPacket shares packet to add
     */
    public void addSharesPacket(SharesPacket sharesPacket) {
        sharesPacketList.add(sharesPacket);
    }

    /**
     * Method updates {@link SharesPacket} in the sharesPacketList.
     *
     * @param sharesPacket shares packet to update
     * @return true if shares packet was updated, false otherwise
     */
    public boolean updateSharesPacket(SharesPacket sharesPacket) {
        SharesPacket foundSharesPacket = sharesPacketList.stream().filter(sp -> sp.getId().equals(sharesPacket.getId()))
                .findFirst().orElseGet(SharesPacket::new);
        int index = sharesPacketList.indexOf(foundSharesPacket);
        if (index != -1) {
            sharesPacketList.set(index, sharesPacket);
            return true;
        } else {
            return false;
        }
    }

    public double getTotalPortfolioValue() {
        return totalPortfolioValue;
    }

    public void setTotalPortfolioValue(double totalPortfolioValue) {
        this.totalPortfolioValue = totalPortfolioValue;
    }

    public double getTotalPortfolioGainPercentage() {
        return totalPortfolioGainPercentage;
    }

    public void setTotalPortfolioGainPercentage(double totalPortfolioGainPercentage) {
        this.totalPortfolioGainPercentage = totalPortfolioGainPercentage;
    }

    public double getTotalPortfolioGainCurrency() {
        return totalPortfolioGainCurrency;
    }

    public void setTotalPortfolioGainCurrency(double totalPortfolioGainCurrency) {
        this.totalPortfolioGainCurrency = totalPortfolioGainCurrency;
    }

    public double getCapitalInCurrency() {
        return capitalInCurrency;
    }

    public void setCapitalInCurrency(double capitalInCurrency) {
        this.capitalInCurrency = capitalInCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Portfolio portfolio = (Portfolio) o;
        return Double.compare(portfolio.totalPortfolioValue, totalPortfolioValue) == 0 && Double
                .compare(portfolio.totalPortfolioGainPercentage, totalPortfolioGainPercentage) == 0 &&
                Double.compare(portfolio.totalPortfolioGainCurrency, totalPortfolioGainCurrency) == 0 &&
                Double.compare(portfolio.capitalInCurrency, capitalInCurrency) == 0 &&
                id.equals(portfolio.id) && sharesPacketList.equals(portfolio.sharesPacketList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sharesPacketList, totalPortfolioValue, totalPortfolioGainPercentage,
                totalPortfolioGainCurrency, capitalInCurrency);
    }
}
