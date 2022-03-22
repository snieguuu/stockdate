package com.stockdate.manager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Objects;

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
    private double purchasePrice;

    private double purchaseValue;

    @NotEmpty
    private double currentPrice;

    private double currentValue;

    private double gainPercent;

    private double gainInCurrency;

    private double shareInPortfolioPercentage;

    private double shareInCapitalPercentage;

}
