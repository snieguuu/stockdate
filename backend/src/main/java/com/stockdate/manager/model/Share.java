package com.stockdate.manager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Class representing stock market share.
 */
@Entity
public class Share {

    @Id
    @GeneratedValue
    private Long id;


    private double currentPrice;

    private double currentValue;

    private double gainPercent;

    private double gainCurrency;

    private double shareInPortfolioPercentage;

    private double shareInCapitalPercentage;


}
