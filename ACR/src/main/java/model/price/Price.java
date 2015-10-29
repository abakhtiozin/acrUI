package main.java.model.price;

import db.SQL;
import main.java.ApplicationStorage;
import main.java.model.Currency;
import main.java.model.Supplier;
import main.java.model.passenger.Passenger;
import main.java.model.passenger.PassengerTariffType;
import main.java.ui.pages.bookingDetailsPage.BookingDetailsPage;
import main.java.ui.pages.searchResultPage.SearchResultPage;

import java.math.BigDecimal;
import java.util.List;

import static main.java.model.Supplier.*;

/**
 * Created by Andrii.Bakhtiozin on 20.02.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class Price {
    private double value;
    private double transporterPrice;
    private Currency transporterCurrency;
    private Supplier supplier;
    private Commission supplierCommission;
    private Commission amadeusCommission;
    private Commission partnerCommission;
    private Commission resellerCommission;
    private List<Passenger> passengers;
    private List<Double> passengersPrices;
    private Currency baseMarketCurrency;
    private double currencyRate;

    public Price(Currency baseMarketCurrency) {
        this.baseMarketCurrency = baseMarketCurrency;
    }

    protected void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public double getValue() {
        return value;
    }

    protected void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    protected void setTransporterCurrency(Currency transporterCurrency) {
        this.transporterCurrency = transporterCurrency;
    }

    protected void setTransporterPrice(double transporterPrice) {
        this.transporterPrice = transporterPrice;
    }

    protected void setSupplierCommission(Commission supplierCommission) {
        this.supplierCommission = supplierCommission;
    }

    protected void setAmadeusCommission(Commission amadeusCommission) {
        this.amadeusCommission = amadeusCommission;
    }

    protected void setPartnerCommission(Commission partnerCommission) {
        this.partnerCommission = partnerCommission;
    }

    protected void setResellerCommission(Commission resellerCommission) {
        this.resellerCommission = resellerCommission;
    }

    public void calculatePrice() {

        double resellerCurrencyRate = Double.parseDouble(SQL.getCurrencyRate(resellerCommission.getCurrency().toString(), baseMarketCurrency.toString()));
        double supplierCurrencyRate = Double.parseDouble(SQL.getCurrencyRate(supplierCommission.getCurrency().toString(), baseMarketCurrency.toString()));

        if (resellerCommission.getCurrency().equals(baseMarketCurrency)) {
            this.currencyRate = supplierCurrencyRate;
        } else this.currencyRate = divide(supplierCurrencyRate, resellerCurrencyRate, 6);

        if (ApplicationStorage.getInstance().getCurrentPage() instanceof SearchResultPage) {
            this.value = multiply(transporterPrice, this.currencyRate, 2);
            addSupplierCommission();
            addAmadeusCommission();
            addResellerCommission();
        }
    }

    private void addSupplierCommission() {
        if (supplierCommission.getAmount() == 0) return;
        if (ApplicationStorage.getInstance().getCurrentPage() instanceof SearchResultPage) { //если на странице поиска
            if (supplier.equals(TF)) { //этот поставщик берёт коммиссию за заказrrr
                this.value += multiply(supplierCommission.getAmount(), this.currencyRate, 2);
                return;
            }
            if (supplier.equals(ACP)) { //этот
                for (Passenger passenger : passengers) {
                    if (!passenger.getTariffType().equals(PassengerTariffType.NO_PLACE)) {
                        this.value += multiply(supplierCommission.getAmount(), this.currencyRate, 2);
                    }
                }
                return;
            }
            if (supplier.equals(IP) || supplier.equals(UFS)) { //эти два поставщика отображают цену на этом этапе за один билет
                this.value += multiply(supplierCommission.getAmount(), this.currencyRate, 2);
                return;
            }
        }

        if (ApplicationStorage.getInstance().getCurrentPage() instanceof BookingDetailsPage) {
            if (supplier.equals(IP) || supplier.equals(UFS)) {
                for (Passenger passenger : passengers) {
//                    if (!passenger.getTariffType().equals(PassengerTariffType.NO_PLACE)) {
                    this.value += multiply(supplierCommission.getAmount(), this.currencyRate, 2);
//                    }
                }
            }
        }
    }

    private void addAmadeusCommission() {
        if (amadeusCommission.getAmount() == 0) return;
        if (ApplicationStorage.getInstance().getCurrentPage() instanceof SearchResultPage) {
            if (supplier.equals(TF)) {
                if (amadeusCommission.getCommissionType().equals(CommissionType.FIXED)) {
                    for (int i = 0; i < passengers.size(); i++) {
                        this.value += multiply(amadeusCommission.getAmount(), this.currencyRate, 2);
                    }
                } else {
                    this.value = this.value + multiply(this.value, (amadeusCommission.getAmount() / 100), 2);
                }
            }
            if (supplier.equals(ACP)) {
                if (amadeusCommission.getCommissionType().equals(CommissionType.FIXED)) {
                    for (Passenger passenger : passengers) {
//                        if (!passenger.getTariffType().equals(PassengerTariffType.NO_PLACE)) {
                        this.value += multiply(amadeusCommission.getAmount(), this.currencyRate, 2);
//                        }
                    }
                } else {
                    this.value = this.value + multiply(this.value, (amadeusCommission.getAmount() / 100), 2);
                }
            }
            if (supplier.equals(IP) || supplier.equals(UFS)) { //эти два поставщика отображают цену на этом этапе за один билет
                if (amadeusCommission.getCommissionType().equals(CommissionType.FIXED)) {
                    this.value += multiply(amadeusCommission.getAmount(), this.currencyRate, 2);
                } else {
                    this.value = this.value + multiply(this.value, (amadeusCommission.getAmount() / 100), 2);
                }
            }
        }
        if (ApplicationStorage.getInstance().getCurrentPage() instanceof BookingDetailsPage) {
        }
    }

    private void addPartnerCommission() {

    }

    private void addResellerCommission() {
        if (resellerCommission.getAmount() == 0) return;
        if (ApplicationStorage.getInstance().getCurrentPage() instanceof SearchResultPage) {
            if (supplier.equals(TF)) {
                if (resellerCommission.getCommissionType().equals(CommissionType.FIXED)) {
                    for (int i = 0; i < passengers.size(); i++) {
                        this.value += resellerCommission.getAmount();
                    }
                } else this.value = this.value + multiply(this.value, (resellerCommission.getAmount() / 100), 2);
            }
            if (supplier.equals(ACP)) {
                if (resellerCommission.getCommissionType().equals(CommissionType.FIXED)) {
                    for (int i = 0; i < passengers.size(); i++) {
                        this.value += resellerCommission.getAmount();
                    }
                } else this.value = this.value + multiply(this.value, (resellerCommission.getAmount() / 100), 2);
            }
            if (supplier.equals(IP) || supplier.equals(UFS)) {
                if (resellerCommission.getCommissionType().equals(CommissionType.FIXED)) {
                    this.value += resellerCommission.getAmount();
                } else this.value = this.value + multiply(this.value, (resellerCommission.getAmount() / 100), 2);
            }
        }
        if (ApplicationStorage.getInstance().getCurrentPage() instanceof BookingDetailsPage) {
        }
    }

    @Deprecated
    private void addCommission(Commission commission) {
        if (commission.getAmount() == 0) return;
        if (commission.equals(resellerCommission)) {
            if (commission.getCommissionType().equals(CommissionType.FIXED)) {
                if (supplier.equals(UFS)) { //КТЖ и РЖД на странице результатов поиска отображают цену за 1 билет
                    this.value += commission.getAmount();
                } else { //все остальные за заказ
                    for (int i = 0; i < passengers.size(); i++) {
                        this.value += commission.getAmount();
                    }
                }
            }
            if (commission.getCommissionType().equals(CommissionType.PERCENT))
                this.value = this.value + multiply(this.value, (commission.getAmount() / 100), 2);
        } else {
            if (commission.getCommissionType().equals(CommissionType.FIXED)) {
                if (supplier.equals(UFS)) {
                    this.value += multiply(commission.getAmount(), this.currencyRate, 2);
                } else {
                    for (int i = 0; i < passengers.size(); i++) {
                        this.value += multiply(commission.getAmount(), this.currencyRate, 2);
                        if (supplier.equals(TF)) return; //комиссия для TF действует на один заказ
                    }
                }
            }
            if (commission.getCommissionType().equals(CommissionType.PERCENT))
                this.value = this.value + multiply(this.value, (commission.getAmount() / 100), 2);
        }
    }

    private double multiply(double factorOne, double factorTwo, int digit) {
        return new BigDecimal(factorOne * factorTwo).setScale(digit, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    private double divide(double numerator, double denominator, int digit) {
        return new BigDecimal(numerator / denominator).setScale(digit, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

}
