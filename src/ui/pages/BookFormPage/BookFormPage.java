package ui.pages.BookFormPage;

import com.codeborne.selenide.SelenideElement;
import model.Passenger;
import org.openqa.selenium.By;
import ui.pages.InnerPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Created by AA on 07.01.2015.
 */
public class BookFormPage extends InnerPage {

    private Map<String,SelenideElement> passengerFieldsLocators;

    protected void initBasePassengerFieldsLocators(){
        passengerFieldsLocators = new HashMap<String, SelenideElement>();
        this.passengerFieldsLocators.put("birthDate",passengerBirthDate());
        this.passengerFieldsLocators.put("surname",passengerSurname());
        this.passengerFieldsLocators.put("name",passengerName());
        this.passengerFieldsLocators.put("document",passengerDocument());
    }

    private SelenideElement passengerBirthDate(){
        return $(By.xpath(".//input[@class='span12 birthDate hasDatepicker']"));
    }
    private SelenideElement passengerSurname(){
        return $(By.xpath(".//input[@class='span12 surname']"));
    }
    private SelenideElement passengerName(){
        return $(By.xpath(".//input[@class='span12 name']"));
    }
    private SelenideElement passengerDocument(){
        return $(By.xpath(".//input[@class='span12 documentSeriesAndNumber']"));
    }


    private String locatorPlacesSelectionContent = ".//*[@id='placesSelectionContent']";

    private List<SelenideElement> passengers(){
        return $$(By.xpath(".//div[@class='span3 passengerContainer']"));
    }

    protected void addPassengerFieldsLocators(Map<String, SelenideElement> passengerFieldsLocators){
        for (Map.Entry<String,SelenideElement> pair : passengerFieldsLocators.entrySet()){
            this.passengerFieldsLocators.put(pair.getKey(),pair.getValue());
        }
    }

    public void addPassenger(Passenger passenger){
        this.passengerFieldsLocators.get("surname").setValue(passenger.getSurname());
    }

    private SelenideElement bookInfo(){
        return $(By.xpath(".//*[@id='replacedContent']/div[@class='row-fluid']"));
    }

    public SelenideElement bookingButton(){
        return $(By.xpath(".//*[@id='book_request_doBooking']"));
    }

    @Override
    public boolean onThisPage() {
        return bookInfo().isDisplayed();
    }
}
