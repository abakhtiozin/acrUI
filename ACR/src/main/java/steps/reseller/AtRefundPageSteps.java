package main.java.steps.reseller;

import main.java.db.SQLBooking;
import main.java.model.Supplier;
import main.java.model.passenger.Passenger;
import main.java.ui.pages.refundPage.RefundPage;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

/**
 * Created by Andrii.Bakhtiozin on 10.04.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class AtRefundPageSteps extends AtInnerPageSteps {

    private RefundPage refundPage;

    @Step
    public void addSupplierBookingConfirmationNumber() {
        if (getRefundPage().isRefundBookingBlockDisplayed()) {
            getRefundPage().setBookingSupplierConfirmationNumber(applicationStorage.getBooking().getSupplierConfirmationNumber());
        }
    }

    @Step
    public void addSupplierBookingConfirmationNumber(String confirmationNumber) {
        if (getRefundPage().isRefundBookingBlockDisplayed()) {
            getRefundPage().setBookingSupplierConfirmationNumber(confirmationNumber);
        }
    }

    @Step
    public void addSupplierTicketConfirmationNumber(String confirmNumber) {
        getRefundPage().setPassengerTicketNumber(confirmNumber);
    }

    @Step
    public void addSupplierTicketConfirmationNumber(Passenger passenger) {
        isRefundTicketBlockDisplayed();
        List<Passenger> passengers = applicationStorage.getPassengers();
        int passengerIndex = -1;
        for (int i = 0; i < passengers.size(); i++) {
            if (passengers.get(i).equals(passenger)) {
                passengerIndex = i;
                break;
            }
        }
        getRefundPage().setPassengerTicketNumber(getSupplierTicketConfirmationNumber(passengerIndex));
    }

    @Step
    private void isRefundTicketBlockDisplayed() {
        Assert.assertTrue(getRefundPage().isRefundTicketBlockDisplayed());
    }

    @Step
    private String getSupplierTicketConfirmationNumber(int passengerIndex) {
        String number = SQLBooking.getSupplierTicketConfirmationNumber(applicationStorage.getBooking().getBookingId(), passengerIndex);
        Assert.assertTrue(number != null);
        return number;
    }

    @Step
    public void clickOnRefundOneTicketOption() {
        getRefundPage().setRefundOneTicketOption();
    }

    @Step
    public void clickOnRefundAllTicketsOption() {
        getRefundPage().setRefundAllTicketsOption();
    }

    @Step
    public void addPassengerDoctypeNumber(String documentNumber) {
        String docNum = documentNumber;
        if (applicationStorage.getSupplier().equals(Supplier.IP)) {
            docNum = documentNumber.toUpperCase();
        }
        getRefundPage().setPassengerDocumentNumber(docNum);
    }

    @Step
    public void refundTickets(boolean refund) {
        if (refund) {
            getRefundPage().clickOnRefundTicketsButton();
            shouldNotSeeErrorAlert();
            stepManager.setUpResellerAtRefundDetailsPage();
        } else getRefundPage().clickOnRefundTicketsButtonFalse();
    }

    @Override
    public void setUpResellerAtPage() {
        refundPage = getRefundPage();
    }

    public RefundPage getRefundPage() {
        return (RefundPage) applicationStorage.getCurrentPage();
    }

    @Step
    @Override
    public void shouldBeOnThisPage() {
        Assert.assertTrue(refundPage.onThisPage(), "Not on refund page!");
    }
}
