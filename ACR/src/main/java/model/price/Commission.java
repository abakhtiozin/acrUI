package main.java.model.price;

import main.java.model.Currency;

/**
 * Created by Andrii.Bakhtiozin on 28.02.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class Commission {

    private double amount;
    private CommissionType commissionType;
    private Currency currency;
    private boolean perPassenger;

    public Commission(double amount, Currency currency, CommissionType commissionType) {
        this.amount = amount;
        this.commissionType = commissionType;
        this.currency = currency;
        this.perPassenger = true;
    }

    public Commission(String amount, String currency, String commissionType) {
        this.amount = Double.parseDouble(amount);
        this.currency = Currency.valueOf(currency);
        this.commissionType = CommissionType.valueOf(commissionType.toUpperCase());
        this.perPassenger = true;
    }

    public boolean isPerPassenger() {
        return perPassenger;
    }

    public void setPerPassenger(boolean perPassenger) {
        this.perPassenger = perPassenger;
    }

    public double getAmount() {
        return amount;
    }

    public CommissionType getCommissionType() {
        return commissionType;
    }

    public Currency getCurrency() {
        return currency;
    }


}
