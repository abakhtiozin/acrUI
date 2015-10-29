package main.java.model;

/**
 * Created by Andrii.Bakhtiozin on 10.09.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class Balance {
    private boolean isBookingAllowed;
    private double creditLimitValue;
    private double paymentBalanceValue;
    private double debtValue;
    private double availableCreditValue;

    public boolean isBookingAllowed() {
        return isBookingAllowed;
    }

    public void setIsBookingAllowed(boolean isBookingAllowed) {
        this.isBookingAllowed = isBookingAllowed;
    }

    public double getCreditLimitValue() {
        return creditLimitValue;
    }

    public void setCreditLimitValue(double creditLimitValue) {
        this.creditLimitValue = creditLimitValue;
    }

    public double getPaymentBalanceValue() {
        return paymentBalanceValue;
    }

    public void setPaymentBalanceValue(double paymentBalanceValue) {
        this.paymentBalanceValue = paymentBalanceValue;
    }

    public double getDebtValue() {
        return debtValue;
    }

    public void setDebtValue(double debtValue) {
        this.debtValue = debtValue;
    }

    public double getAvailableCreditValue() {
        return availableCreditValue;
    }

    public void setAvailableCreditValue(double availableCreditValue) {
        this.availableCreditValue = availableCreditValue;
    }
}
