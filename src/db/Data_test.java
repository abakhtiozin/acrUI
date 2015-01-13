package db;


/**
 * Created by bakhtiozin on 08.01.2015.
 */
public class Data_test {
    private String price;
    private String offerName;

    public String getOfferName() {
        return offerName;
    }

    public String getPrice() {
        return price;
    }

    public Data_test(String arrivalCity, String destCity){
        DbItem dbItem = DbItem.get_instance();
        double usd_to_eur = dbItem.getCurrencyRate("EUR") / dbItem.getCurrencyRate("USD");
        double transporterMinPrice = dbItem.getTransporterMinPrice(arrivalCity, destCity);
        System.out.println("Минимальная цена от поставщика: " + transporterMinPrice + " EUR");
        System.out.println("Курс доллара к евро: " + usd_to_eur);
        double amadeusCommision = 4;
        System.out.println("Коммисия амадеус: " + amadeusCommision + " EUR");
        double displayedMinPrice = formatDouble(transporterMinPrice * usd_to_eur + amadeusCommision * usd_to_eur);
        String offerName = dbItem.getOfferName(String.valueOf(displayedMinPrice));
        price = String.format("%.2f", displayedMinPrice).replace(",",".") + " USD";
        System.out.println("Расчет " + transporterMinPrice + " * " + usd_to_eur + " + " + amadeusCommision + " * " + usd_to_eur + " = " + price);


    }
    public static Double formatDouble(Double valueToFormat) {
        long rounded = Math.round(valueToFormat*100);
        return rounded/100.0;
    }

}
