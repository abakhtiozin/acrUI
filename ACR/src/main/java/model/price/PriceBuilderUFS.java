package main.java.model.price;

import db.SQL;
import main.java.model.passenger.Passenger;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static main.java.model.Currency.RUB;
import static main.java.model.Supplier.UFS;
import static main.java.model.price.CommissionType.FIXED;

/**
 * Created by Andrii.Bakhtiozin on 20.02.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class PriceBuilderUFS extends PriceBuilder {

    public PriceBuilderUFS(Date departureDate, Date arrivalDate, List<Passenger> passengers) {
        super(departureDate, arrivalDate, passengers);
    }

    @Override
    protected void setSupplier() {
        price.setSupplier(UFS);
    }

    @Override
    protected void setTransporterPrice() {
        price.setTransporterPrice(Double.parseDouble(SQL.getTransporterMinOfferPrice(reseller.getResellerCode(), reseller.getUserName(), this.departureDate, this.arrivalDate)));
        price.setTransporterCurrency(RUB);
    }

    @Override
    protected void setSupplierCommission() {
        Commission supplierCommission = new Commission(80, RUB, FIXED);
        price.setSupplierCommission(supplierCommission);
    }

    @Override
    protected void setAmadeusComminssion() {
        Map<String, String> map = SQL.getInternalSupplierCommission(UFS.toString(), "UA");
        Commission amadeusCommission = new Commission(map.get("VALUE"), map.get("CURRENCY"), map.get("COMMISSION_TYPE"));
        price.setAmadeusCommission(amadeusCommission);
    }

    @Override
    public void setPassengers() {
        price.setPassengers(this.passengers);
    }

    @Override
    protected void setPartnerCommission() {

    }

    @Override
    protected void setResellerCommission() {
        Map<String, String> map = SQL.getResellerCommissionForSupplier(reseller.getResellerCode(), UFS.toString(), "order");
        Commission resellerCommission = new Commission(map.get("VALUE"), map.get("CURRENCY"), map.get("COMMISSION_TYPE"));
        price.setResellerCommission(resellerCommission);
    }
}
