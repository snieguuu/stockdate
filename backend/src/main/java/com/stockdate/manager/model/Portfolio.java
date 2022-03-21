package com.stockdate.manager.model;

import javax.persistence.Entity;
import java.util.List;

/**
 * Class representing shares portfolio entity.
 */
@Entity
public class Portfolio {

    private List<Share> shares;

    private double totalPortfolioValue;

    private double totalPortfolioGainPercentage;

    private double totalPortfolioGainCurrency;

}
