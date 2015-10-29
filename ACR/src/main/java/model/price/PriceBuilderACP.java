package main.java.model.price;

import db.SQL;
import main.java.model.passenger.Passenger;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static main.java.model.Currency.EUR;
import static main.java.model.Supplier.ACP;
import static main.java.model.price.CommissionType.FIXED;

/**
 * Created by Andrii.Bakhtiozin on 03.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class PriceBuilderACP extends PriceBuilder {

    public PriceBuilderACP(Date departureDate, Date arrivalDate, List<Passenger> passengers) {
        super(departureDate, arrivalDate, passengers);
    }

    @Override
    protected void setSupplier() {
        price.setSupplier(ACP);
    }

    @Override
    protected void setTransporterPrice() {
        price.setTransporterPrice(Double.parseDouble(SQL.getTransporterMinOfferPrice(reseller.getResellerCode(), reseller.getUserName(), this.departureDate, this.arrivalDate)));
        price.setTransporterCurrency(EUR);
    }

    @Override
    protected void setSupplierCommission() {
        Commission supplierCommission = new Commission(0, EUR, FIXED);
        price.setSupplierCommission(supplierCommission);
    }

    @Override
    protected void setAmadeusComminssion() {
        Map<String, String> map = SQL.getInternalSupplierCommission(ACP.toString(), "UA");
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
        Map<String, String> map = SQL.getResellerCommissionForSupplier(reseller.getResellerCode(), ACP.toString(), "order");
        Commission resellerCommission = new Commission(map.get("VALUE"), map.get("CURRENCY"), map.get("COMMISSION_TYPE"));
        price.setResellerCommission(resellerCommission);
    }
}
