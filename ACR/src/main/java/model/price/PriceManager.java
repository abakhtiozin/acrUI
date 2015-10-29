package main.java.model.price;

import db.SQL;
import main.java.model.Supplier;
import main.java.model.passenger.Passenger;

import java.util.Date;
import java.util.List;

/**
 * Created by Andrii.Bakhtiozin on 11.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class PriceManager {
    private Date departureDate;
    private Date arrivalDate;
    private List<Passenger> passengers;

    public PriceManager(Date departureDate, Date arrivalDate, List<Passenger> passengers) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.passengers = passengers;
    }

    public double constructPrice(String transporterName) {
        PriceDirector priceDirector = new PriceDirector();
        switch (Supplier.valueOf(SQL.getSupplierByTransporterName(transporterName).toUpperCase())) {
            case UFS:
                PriceBuilderUFS priceBuilderUFS = new PriceBuilderUFS(departureDate, arrivalDate,passengers);
                priceDirector.setPriceBuilder(priceBuilderUFS);
                break;
            case ACP:
                PriceBuilderACP priceBuilderACP = new PriceBuilderACP(departureDate, arrivalDate,passengers);
                priceDirector.setPriceBuilder(priceBuilderACP);
                break;
            case TF:
                PriceBuilderTF priceBuilderTF = new PriceBuilderTF(departureDate, arrivalDate,passengers);
                priceDirector.setPriceBuilder(priceBuilderTF);
                break;
            case IP:
                PriceBuilderIP priceBuilderIP = new PriceBuilderIP(departureDate, arrivalDate,passengers);
                priceDirector.setPriceBuilder(priceBuilderIP);
                break;
        }
        priceDirector.constructPrice();
        Price price = priceDirector.getPrice();
        price.calculatePrice();
        return price.getValue();
    }

}
