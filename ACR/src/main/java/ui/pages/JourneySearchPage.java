package main.java.ui.pages;

import com.codeborne.selenide.SelenideElement;
import main.java.ApplicationStorage;
import main.java.core.SearchJourney;
import main.java.model.Journey;
import main.java.model.passenger.Passenger;
import main.java.ui.pages.searchResultPage.SearchResultPage;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import util.ui.JQueryWorker;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static main.java.utils.DateTimeHelper.FORMAT_PATTERN;

/**
 * Created by Andrii.Bakhtiozin on 07.01.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class JourneySearchPage extends InnerPage implements SearchJourney {

    // --------------------------- CONSTRUCTORS ---------------------------
    // ------------------------------ FIELDS ------------------------------
    // ------------------------------ METHODS -----------------------------


    public JourneySearchPage() {
        super();
    }

    private SelenideElement resetButton() {
        return $("#search_query_reset");
    }

    private SelenideElement searchButton() {
        return $("#search_query_search");
    }

    public SearchResultPage clickOnSearchButton() {
        searchButton().click();
        return page(SearchResultPage.class);
    }

    private SelenideElement departmentDateField() {
        return $("#search_query_departDate");
    }

    private SelenideElement destinationLocationField() {
        return $("#search_query_arrivalLocationName");
    }

    private SelenideElement originLocationField() {
        return $("#search_query_departLocationName");
    }

    private List<SelenideElement> passengerBirthDateFields() {
        return $$("div.control-group input.span12.birthDate.hasDatepicker");
    }

    private List<SelenideElement> deletePassengerButtons() {
        return $$(".deletePassenger.btn");
    }

    public JourneySearchPage deletePassenger(Passenger passenger) {
        for (int i = 1; i < passengerBirthDateFields().size(); i++) {
            if (passengerBirthDateFields().get(i).val().equals(passenger.getBirthDate())) {
                deletePassengerButtons().get(i - 1).shouldBe(visible).click();
                $("#deletePassengerModal>div.modal-footer>button.btn.btn-primary.yes").waitUntil(appear, 5000).click();
            }
        }
        return this;
    }

    public JourneySearchPage pressResetButton() {
        resetButton().click();
        return this;
    }

    public boolean compareFieldsToPassengers(List<Passenger> passengers) {
        for (int i = 1; i < passengers.size(); i++) {
            if (!passengers.get(i).getBirthDate().equals(passengerBirthDateFields().get(i).val())) {
                return false;
            }
        }
        return true;
    }

    public void addJourney(Journey journey) {
        setOriginLocation(journey.getOriginLocation());
        setDestinationLocation(journey.getDestinationLocation());
        if (journey.getOriginTimeTo() > 0) {
            setTime(journey.getOriginTimeFrom(), journey.getOriginTimeTo());
        }
        JQueryWorker.setDatePickerValue("#" + departmentDateField().getAttribute("id"), journey.getOriginDate().toString(FORMAT_PATTERN));
    }

    private void setTime(int originTimeFrom, int originTimeTo) {
        String cssSelector = ".timeSlider";
        $(".ui-slider-handle.ui-state-default.ui-corner-all:nth-of-type(1)").waitUntil(appear, 5000);
        executeJavaScript(String.format("$('%s').slider('option','values',[ '%s', '%s'] )", cssSelector, originTimeFrom, originTimeTo));
        actions().dragAndDropBy($(".ui-slider-handle.ui-state-default.ui-corner-all:nth-of-type(1)"), 13, 0).build().perform();
        actions().dragAndDropBy($(".ui-slider-handle.ui-state-default.ui-corner-all:nth-of-type(1)"), -13, 0).build().perform();
    }

    //inner methods
    private void setOriginLocation(String originLocation) {
        JQueryWorker.setAutoCompleteValue("#" + originLocationField().getAttribute("id"), originLocation);
        $(By.xpath("//*[@class='ui-menu-item' and (*[contains(.,'" + originLocation + "')] or *[contains(.,'(" + originLocation + ")')])]/a")).waitUntil(appear, 5000).click();
    }

    private void setDestinationLocation(String destinationLocation) {
        JQueryWorker.setAutoCompleteValue("#" + destinationLocationField().getAttribute("id"), destinationLocation);
        $(By.xpath("//*[@class='ui-menu-item' and (*[contains(.,'" + destinationLocation + "')] or *[contains(.,'(" + destinationLocation + ")')])]/a")).waitUntil(appear, 5000).click();
    }

    private void addPassenger(DateTime birthdate, String cssSelector) {
        JQueryWorker.setDatePickerValue("#" + cssSelector, birthdate.toString(FORMAT_PATTERN));
    }

    public void addPassenger(Passenger passenger) {
        SelenideElement dateInput;
        ApplicationStorage applicationStorage = ApplicationStorage.getInstance();
        if (applicationStorage.getPassengers() == null) {
            applicationStorage.setPassengers(new ArrayList<>());
            dateInput = $$(By.xpath("//*[@class='birthDate hasDatepicker']")).get(0);
            if (passenger.getBirthDate() != null) {
                addPassenger(passenger.getBirthDate(), dateInput.getAttribute("id"));
            }
        } else {
            $(By.xpath("//li[contains(@class,'passenger-" + (applicationStorage.getPassengers().size() + 1) + "')]")).click();
            if (passenger.getBirthDate() != null) {
                dateInput = $$(By.xpath("//*[@class='birthDate hasDatepicker']")).get(applicationStorage.getPassengers().size());
                addPassenger(passenger.getBirthDate(), dateInput.getAttribute("id"));
            }
        }
        applicationStorage.getPassengers().add(passenger);
    }

    @Override
    public boolean onThisPage() {
        return $(By.xpath(".//form[@name='search_query']")).isDisplayed();
    }
}

