package main.java.steps.reseller;

import main.java.model.Trip;
import main.java.model.passenger.Passenger;
import main.java.model.passenger.PassengerContactInfo;
import main.java.ui.pages.bookFormPage.*;
import main.java.utils.DateTimeHelper;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.List;

import static main.java.utils.Utils.makeScreenshot;

/**
 * Created by Andrii.Bakhtiozin on 23.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class AtBookFormPageSteps extends AtInnerPageSteps {

    private List<Passenger> passengers;
    private BookFormPage bookFormPage;
    private Trip currentTrip;

    // --------------------------- CONSTRUCTORS ---------------------------


    @Step
    public void addPassengerInfo(Passenger passenger, int passengerNumber) {
        if (passengerNumber > 1) {
            clickOnAddPassengerButton();
        }
        bookFormPage.addPassenger(passenger, passengerNumber - 1);
        this.passengers.add(passenger);
//        validatePassengerBirthDate(passenger, passengerNumber);
    }

    /**
     * Method for HEX
     * Add first passenger email
     */
    @Step
    public void addPassengerEmail(String email) {
        ((BookFormHexPage) bookFormPage).addEmail(email);
    }

    @Step
    public void refillPassengerInfo(Passenger passenger, int passengerNumber) {
        bookFormPage.addPassenger(passenger, passengerNumber - 1);
    }

    @Step
    public void updatePassengerInfo(Passenger passenger, int passengerNumber) {
        bookFormPage.addPassenger(passenger, passengerNumber - 1);
        this.passengers.add(passenger);
    }

    @Step
    public void setGenderCoupe(String coupeType) {
        ((BookFormUfsPage) bookFormPage).selectGenderCoupe(coupeType);
    }

    @Step
    public void setWithBedClothing(boolean withBedClothing) {
        if (bookFormPage instanceof WithBedClothing) {
            ((WithBedClothing) bookFormPage).setWithBedClothing(withBedClothing);
        } else
            throw new NullPointerException(bookFormPage.getClass().getCanonicalName() + " doesn't has such method like 'setWithBedClothing'");
    }

    @Step
    public void setDeckNum(int deckNum) {
        ((BookFormUfsPage) bookFormPage).selectDeck(String.valueOf(deckNum));
    }

    @Step
    public void setPaymentTypeByCash(boolean byCash) {
        ((BookFormIpPage) bookFormPage).setByCashPaymentType(byCash);
    }

    @Step
    public void pressOnBookAndStayOnBookForm() {
        bookFormPage.invalidBooking();
    }

    @Step
    public void fillContactDetails(PassengerContactInfo passengerContactInfo) {
        ((BookFormTfPage) bookFormPage).addContactInfo(passengerContactInfo);
    }

    @Step
    public void setWithElectronicRegistration(boolean withElectronicRegistration) {
        if (bookFormPage instanceof BookFormUfsPage) {
            ((BookFormUfsPage) bookFormPage).setWithElectronicRegistration(withElectronicRegistration);
        }
    }

    @Step
    public void selectRandomSeatOnBoard(int passengerIndex) {
        if (bookFormPage instanceof BookFormTfPage) {
            ((BookFormTfPage) bookFormPage).selectPassengerSeat(passengerIndex - 1);
        }
    }

    @Step
    public void selectRandomLuggage(int passengerIndex) {
        if (bookFormPage instanceof BookFormTfPage) {
            ((BookFormTfPage) bookFormPage).selectPassengerLuggage(passengerIndex - 1);
        }
    }

    @Step
    public void bookAndGoToBookingPage() {
        makeScreenshot("BookFormPage");
        bookFormPage.bookOrder();
        applicationStorage.setPassengers(passengers);
        stepManager.setUpResellerAtBookingDetailsPage();
        shouldNotSeeErrorAlert();
    }

    @Step
    public void clickOnAddPassengerButton() {
        if (bookFormPage instanceof PassengerActionsOnBookForm) {
            ((PassengerActionsOnBookForm) bookFormPage).clickOnAddPassengerButton();
        }
    }

    /*VALIDATION METHODS*/
    @Step
    private void validatePassengerBirthDate(Passenger passenger, int passengerNumber) {
        String passengerDate = passenger.getBirthDate().toString(DateTimeHelper.FORMAT_PATTERN);
        String passengerDateFromPage = bookFormPage.getPassengerBirthDate(passengerNumber - 1);
        Assert.assertTrue(passengerDate.equals(passengerDateFromPage));
    }

    @Step
    public void validateDataOnPage() {
        departureDateShouldBeAsOnResultPage();
        arrivalDateShouldBeAsOnResultPage();
        transporterNumberShouldBeAsOnResultPage();
    }

    @Step
    public void departureDateShouldBeAsOnResultPage() {
        Assert.assertTrue(bookFormPage.isEqualDataInDepartureBlock(
                        DateTimeHelper.getDateOnly(currentTrip.getDepartTime())),
                "Trip departure date not like on journey search page " + currentTrip.getDepartTime());
        Assert.assertTrue(bookFormPage.isEqualDataInDepartureBlock(
                        DateTimeHelper.getTimeOnly(currentTrip.getDepartTime())),
                "Trip departure time not like on journey search page " + currentTrip.getDepartTime());
    }

    @Step
    public void arrivalDateShouldBeAsOnResultPage() {
        Assert.assertTrue(bookFormPage.isEqualDataInArrivalBlock(
                        DateTimeHelper.getDateOnly(currentTrip.getDepartTime())),
                "Trip arrival date not like on journey search page " + currentTrip.getDepartTime());
        Assert.assertTrue(bookFormPage.isEqualDataInArrivalBlock(
                        DateTimeHelper.getTimeOnly(currentTrip.getDepartTime())),
                "Trip arrival time not like on journey search page " + currentTrip.getDepartTime());
    }

    @Step
    public void transporterNumberShouldBeAsOnResultPage() {
        Assert.assertTrue(bookFormPage.isEqualDataInTransporterBlock(
                        currentTrip.getTransporterNumber()),
                "Trip transporter number not like on journey search page " + currentTrip.getTransporterNumber());
    }

    @Step
    @Override
    public void shouldBeOnThisPage() {
        Assert.assertTrue(bookFormPage.onThisPage(), "Not on book form page");
    }
    /*END OF VALIDATION METHODS*/

    @Override
    public void setUpResellerAtPage() {
        bookFormPage = (BookFormPage) applicationStorage.getCurrentPage();
        currentTrip = applicationStorage.getChosenTrip();
        passengers = new ArrayList<>();
    }
}
