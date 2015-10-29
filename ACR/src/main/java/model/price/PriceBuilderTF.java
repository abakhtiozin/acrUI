package main.java.model.price;

import main.java.model.passenger.Passenger;

import java.util.Date;
import java.util.List;

/**
 * Created by Andrii.Bakhtiozin on 11.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class PriceBuilderTF extends PriceBuilder{
    public PriceBuilderTF(Date departureDate, Date arrivalDate, List<Passenger> passengers) {
        super(departureDate, arrivalDate, passengers);
    }

    @Override
    protected void setPassengers() {

    }

    @Override
    protected void setTransporterPrice() {

    }

    @Override
    protected void setSupplierCommission() {

    }

    @Override
    protected void setAmadeusComminssion() {

    }

    @Override
    protected void setPartnerCommission() {

    }

    @Override
    protected void setResellerCommission() {

    }

    @Override
    protected void setSupplier() {

    }
}
