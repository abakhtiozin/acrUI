import model.Reseller;
import model.Journey;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by AA on 21.12.2014.
 */
public class Tests {
    SoapAPI soapAPI;
    Reseller reseller;
    Journey journey;


    @BeforeSuite
    public void setSoap(){
        soapAPI = new SoapAPI();
//        soapAPI.setUrl("");
//        reseller = new Reseller();
    }

    public static void main(String[] args) {



//        String s = "1 123.36 USD";
//        Double d = Double.parseDouble(s.replaceAll("[a-zA-Z]+", ""));
//        System.out.println(d);

//        DbItem dbItem = DbItem.get_instance();
//        System.out.println("UAH: " + dbItem.getCurrencyRate("UAH"));
//        System.out.println("EUR: " + dbItem.getCurrencyRate("EUR"));
//        System.out.println("USD: " + dbItem.getCurrencyRate("USD"));

        List<String> assigners = new ArrayList<String>();
        Collections.addAll(assigners,"Igor", "Vitalii", "Yura","Lesha","Edgar","Matros","Bakhtiozin");
        Random randomGenerator = new Random();
        int size = assigners.size();
//        for (int i = 0; i < size; i++) {
//            randomGenerator.nextInt(assigners.get(i));
//        }
//        int randomInt = randomGenerator.nextInt(assigners.length);
//        System.out.println(assigners[randomInt]);




    }

//    @Test
//    public void responseJourneySearchTest() {
//        journey = new Journey().withOriginCode("2000000").withOriginDate("2015.01.25").withOriginTimeFrom("00:00").withOriginTimeTo("23:00").withDestinationCode("2004000");
//        Passenger passenger = new Passenger().withBirthDate(new Date("1990/12/12"));
//        System.out.println(passenger);
//        List<Passenger> passengers = new ArrayList<Passenger>();
//        passengers.add(passenger);
//        soapAPI.setAccess(access);
//        soapAPI.setJourney(journey);
//        soapAPI.setPassengers(passengers);
//        soapAPI.sendRequest(SoapOperations.JourneySearch);
//        Assert.assertEquals(soapAPI.getResponseValueByTagName("faultstring", 0), "3002: Доступ запрещен");
//    }

//    @Test
//    public void responseJourneySearchTestWrong() {
//        soapAPI.sendRequest(SoapOperations.JourneySearch);
//        String expectedResult = "3002: Доступ запрещен!";
//        String actualResult = soapAPI.getResponseValueByTagName("faultstring", 0);
//        Assert.assertTrue(actualResult.equals(expectedResult), "Expected : " + expectedResult + " but got " + actualResult);
//    }

    @AfterSuite
    public void tearDown(){
        soapAPI.closeConnection();
    }
}
