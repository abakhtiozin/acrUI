package main.java.model.price;

/**
 * Created by Andrii.Bakhtiozin on 02.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class PriceDirector {
    private PriceBuilder priceBuilder;

    public void setPriceBuilder(PriceBuilder priceBuilder) {
        this.priceBuilder = priceBuilder;
    }
    public Price getPrice(){
        return priceBuilder.getPrice();
    }
    public void constructPrice(){
        priceBuilder.createNewPrice();
        priceBuilder.setPassengers();
        priceBuilder.setTransporterPrice();
        priceBuilder.setSupplierCommission();
        priceBuilder.setAmadeusComminssion();
        priceBuilder.setPartnerCommission();
        priceBuilder.setResellerCommission();
        priceBuilder.setSupplier();
    }
}
