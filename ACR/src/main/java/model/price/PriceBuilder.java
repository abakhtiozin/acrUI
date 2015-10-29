package main.java.model.price;

import main.java.ApplicationStorage;
import main.java.model.Currency;
import main.java.model.Reseller;
import main.java.model.passenger.Passenger;

import java.util.Date;
import java.util.List;

/**
 * Created by Andrii.Bakhtiozin on 20.02.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public abstract class PriceBuilder {

    // ------------------------------ FIELDS ------------------------------
    protected Date departureDate;
    protected Date arrivalDate;
    protected List<Passenger> passengers;
    protected Reseller reseller;
    protected Price price;

    public PriceBuilder(Date departureDate, Date arrivalDate, List<Passenger> passengers) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.passengers = passengers;
        this.reseller = ApplicationStorage.getInstance().getReseller();
    }

    public Price getPrice() {
        return price;
    }

    // ------------------------------ METHODS -----------------------------

    protected void createNewPrice() {
        price = new Price(Currency.UAH);
    }

    protected abstract void setPassengers();

    protected abstract void setTransporterPrice();

    protected abstract void setSupplierCommission();

    protected abstract void setAmadeusComminssion();

    protected abstract void setPartnerCommission();

    protected abstract void setResellerCommission();

    protected abstract void setSupplier();

}
