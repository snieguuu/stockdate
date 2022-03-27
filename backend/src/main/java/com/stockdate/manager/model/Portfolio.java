package com.stockdate.manager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * Class representing shares portfolio entity.
 */
@Entity
public class Portfolio {

    @Id
    @GeneratedValue
    private Long id;

    private List<SharesPacket> shares;

    private double totalPortfolioValue;

    private double totalPortfolioGainPercentage;

    private double totalPortfolioGainCurrency;

    private double capitalInCurrency;

}
