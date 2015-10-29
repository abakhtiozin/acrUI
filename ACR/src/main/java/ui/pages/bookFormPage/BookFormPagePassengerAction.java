package main.java.ui.pages.bookFormPage;

import com.codeborne.selenide.SelenideElement;
import db.SQL;
import main.java.ApplicationStorage;
import main.java.model.passenger.Passenger;
import main.java.model.passenger.PassengerDocumentType;
import main.java.model.passenger.PassengerGender;
import main.java.model.passenger.PassengerTariffType;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.testng.Assert;
import util.other.RandomDataGenerator;
import util.ui.JQueryWorker;

import java.util.List;

import static main.java.utils.DateTimeHelper.FORMAT_PATTERN;

/**
 * Created by Andrii.Bakhtiozin on 21.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
class BookFormPagePassengerAction {
    BookFormPassengerFields bookFormPassengerFields = new BookFormPassengerFields();

    void setGender(PassengerGender gender, int passengerIndex) {
        if (gender != null) {
            if (bookFormPassengerFields.getGenderFieldElement(passengerIndex).isDisplayed()) {
                bookFormPassengerFields.getGenderFieldElement(passengerIndex).selectOptionByValue(gender.toString().toLowerCase());
            } else {
                if (bookFormPassengerFields.getSalutationFieldElement(passengerIndex).isDisplayed()) {
                    if (gender.toString().toLowerCase().equals("male")) {
                        bookFormPassengerFields.getSalutationFieldElement(passengerIndex).selectOptionByValue("Mr");
                    }
                    if (gender.toString().toLowerCase().equals("female")) {
                        bookFormPassengerFields.getSalutationFieldElement(passengerIndex).selectOptionByValue("Ms");
                    }
                }
            }
        }
    }

    void setDocumentNumber(String documentNumber, int passengerIndex) {
        if (documentNumber != null) {
            SelenideElement documentNumberFieldElement = bookFormPassengerFields.getDocumentNumberFieldElement(passengerIndex);
            if (documentNumberFieldElement.isDisplayed()) {
                documentNumberFieldElement.setValue(documentNumber);
            }
        }
    }

    void setDocumentType(PassengerDocumentType documentType, int passengerIndex) {
        if (documentType != null) {
            String documentId = SQL.getDocumentIdByNameAndSupplier(documentType.toString(), ApplicationStorage.getInstance().getSupplier().toString().toLowerCase());
            bookFormPassengerFields.getDocumentTypeFieldElement(passengerIndex).selectOptionByValue(documentId);
            Assert.assertEquals(documentType.toString().replaceAll("_", " "), bookFormPassengerFields.getDocumentTypeFieldElement(passengerIndex).getSelectedText());
        }
    }

    void setFathersName(String fathersName, int passengerIndex) {
        if (fathersName != null) bookFormPassengerFields.getFathersFieldElement(passengerIndex).setValue(fathersName);
    }

    boolean isFathersNameFieldExist(int passengerIndex) {
        return bookFormPassengerFields.getFathersFieldElement(passengerIndex).isDisplayed();
    }

    void setSurname(String surname, int passengerIndex) {
        if (surname != null) bookFormPassengerFields.getSurnameFieldElement(passengerIndex).setValue(surname);
    }

    void setRandomLuggage(int passengerIndex) {
        if (bookFormPassengerFields.getLuggageFieldElement(passengerIndex).isEnabled()) {
            List<SelenideElement> luggageOptionElements = bookFormPassengerFields.getLuggageFieldElement(passengerIndex).$$(By.xpath("./option"));
            int luggageOptionsCount = luggageOptionElements.size();
            String optionValue = luggageOptionElements.get(RandomDataGenerator.randomNumber(1, luggageOptionsCount)).getAttribute("value");
            bookFormPassengerFields.getLuggageFieldElement(passengerIndex).selectOptionByValue(optionValue);
        }
    }

    void setRandomSeat(int passengerIndex) {
        List<SelenideElement> seatsOptionElements = bookFormPassengerFields.getSeatFieldElement(passengerIndex).$$(By.xpath("./option"));
        int seatsOptionsCount = seatsOptionElements.size();
        String optionValue = seatsOptionElements.get(RandomDataGenerator.randomNumber(1, seatsOptionsCount)).getAttribute("value");
        bookFormPassengerFields.getSeatFieldElement(passengerIndex).selectOptionByValue(optionValue);
    }

    void setName(String name, int passengerIndex) {
        if (name != null) bookFormPassengerFields.getNameFieldElement(passengerIndex).setValue(name);
    }

    void setTariff(PassengerTariffType tariffType, int passengerIndex) {
        if (tariffType != null)
            bookFormPassengerFields.getTariffFieldElement(passengerIndex).selectOptionByValue(tariffType.toString().toLowerCase());
    }

    void setPassengerBirthDate(Passenger passenger, int passengerIndex) {
        if (passenger.getBirthDate() != null) {
            JQueryWorker.setDatePickerValue("#" + bookFormPassengerFields.getBirthDateFieldElement(passengerIndex).getAttribute("id"), passenger.getBirthDate().toString(FORMAT_PATTERN));
        }
    }

    void setStateIssuedDocument(String stateIssuedDocument, int passengerIndex) {
        if (stateIssuedDocument != null) {
            SelenideElement stateIssueDocumentFieldElement = bookFormPassengerFields.getStateIssueDocumentFieldElement(passengerIndex);
            if (stateIssueDocumentFieldElement.isDisplayed()) {
                stateIssueDocumentFieldElement.selectOptionByValue(stateIssuedDocument);
            }
        }
    }

    void setPlaceOfBirth(String placeOfBirth, int passengerIndex) {
        if (placeOfBirth != null) {
            SelenideElement placeOfBirthFieldElement = bookFormPassengerFields.getPlaceOfBirthFieldElement(passengerIndex);
            if (placeOfBirthFieldElement.isDisplayed()) {
                placeOfBirthFieldElement.setValue(placeOfBirth);
            }
        }
    }

    void setPassengerDocumentExpirationDate(DateTime documentExpiryDate, int passengerIndex) {
        if (documentExpiryDate != null) {
            SelenideElement documentExpirationDateFieldElement = bookFormPassengerFields.getDocumentExpirationDateFieldElement(passengerIndex);
            if (documentExpirationDateFieldElement.isDisplayed()) {
                JQueryWorker.setDatePickerValue("#" + documentExpirationDateFieldElement.getAttribute("id"), documentExpiryDate.toString(FORMAT_PATTERN));
            }
        }
    }
}
