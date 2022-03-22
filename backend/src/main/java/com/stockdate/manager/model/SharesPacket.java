package com.stockdate.manager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * Class representing stock market share.
 */
@Entity
public class SharesPacket {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private Date purchaseDate;

    @NotEmpty
    private String name;

    @NotEmpty
    private String ticker;

    @NotEmpty
    private int sharesAmount;

    @NotEmpty
    private int sharePrice;

    private double purchaseValue;
    @NotEmpty
    private double currentPrice;

    private double currentValue;

    private double gainPercent;

    private double gainCurrency;

    private double shareInPortfolioPercentage;

    private double shareInCapitalPercentage;


}
