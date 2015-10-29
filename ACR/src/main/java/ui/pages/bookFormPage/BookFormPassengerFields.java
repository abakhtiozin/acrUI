package main.java.ui.pages.bookFormPage;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$;

/**
 * Created by Andrii.Bakhtiozin on 13.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
class BookFormPassengerFields {

    protected List<SelenideElement> passengersContainer;

    public BookFormPassengerFields() {
        this.passengersContainer = $$(By.xpath(".//div[contains(@class,'span3 passengerContainer')]"));
    }

    protected SelenideElement getFathersFieldElement(int passengerIndex) {
        return passengersContainer.get(passengerIndex).$(By.xpath(".//input[@class='span12 fathers']"));
    }

    protected SelenideElement getDocumentTypeFieldElement(int passengerIndex) {
        return passengersContainer.get(passengerIndex).$(By.xpath(".//select[@class='span12 documentType']"));
    }

    protected SelenideElement getPlaceOfBirthFieldElement(int passengerIndex) {
        return passengersContainer.get(passengerIndex).$(By.xpath(".//input[@class='span12 placeOfBirth']"));
    }

    protected SelenideElement getGenderFieldElement(int passengerIndex) {
        return passengersContainer.get(passengerIndex).$(By.xpath(".//select[@class='span12 gender']"));
    }

    protected SelenideElement getTariffFieldElement(int passengerIndex) {
        return passengersContainer.get(passengerIndex).$(By.xpath(".//select[@class='span12 tariff']"));
    }

    protected SelenideElement getBirthDateFieldElement(int passengerIndex) {
        return passengersContainer.get(passengerIndex).$(By.xpath(".//input[@class='span12 birthDate hasDatepicker']"));
    }

    protected SelenideElement getSurnameFieldElement(int passengerIndex) {
        return passengersContainer.get(passengerIndex).$(By.xpath(".//input[@class='span12 surname']"));
    }

    protected SelenideElement getNameFieldElement(int passengerIndex) {
        return passengersContainer.get(passengerIndex).$(By.xpath(".//input[@class='span12 name']"));
    }

    protected SelenideElement getDocumentNumberFieldElement(int passengerIndex) {
        return passengersContainer.get(passengerIndex).$(By.xpath(".//input[@class='span12 documentSeriesAndNumber']"));
    }

    protected SelenideElement getStateIssueDocumentFieldElement(int passengerIndex) {
        return passengersContainer.get(passengerIndex).$(By.xpath(".//select[@class='span12 stateWhichIssuedDocument']"));
    }

    protected SelenideElement getSalutationFieldElement(int passengerIndex) {
        return passengersContainer.get(passengerIndex).$(By.xpath(".//select[@class='span12 salutation']"));
    }

    protected SelenideElement getDocumentExpirationDateFieldElement(int passengerIndex) {
        return passengersContainer.get(passengerIndex).$(By.xpath(".//input[@class='span12 documentExpiryDate hasDatepicker']"));
    }

    protected SelenideElement getSeatFieldElement(int passengerIndex) {
        return passengersContainer.get(passengerIndex).$(By.xpath(".//*[@seats='seats']"));
    }

    protected SelenideElement getLuggageFieldElement(int passengerIndex) {
        return passengersContainer.get(passengerIndex).$(By.xpath(".//*[@luggage or @class='span12 luggage']"));
    }
}
