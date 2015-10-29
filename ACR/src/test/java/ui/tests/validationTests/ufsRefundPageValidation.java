package test.java.ui.tests.validationTests;

import main.java.db.SQLBooking;
import main.resources.data.BaseReseller;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.java.base.StepInitializer;
import util.other.RandomDataGenerator;

import static main.java.model.BookingStatus.OK;
import static main.java.model.BookingStatus.RF;
import static main.java.model.Supplier.UFS;

/**
 * Created by Andrii.Bakhtiozin on 10.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class ufsRefundPageValidation extends StepInitializer {

    @BeforeClass
    public void setUp() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(UFS));
        atJourneySearchPageSteps.openRefundPage();
    }

    @Test(dataProvider = "DP for all tickets refund")
    public void ufsRefundPageValidationAllTicketsRefund(String confirmationNumber, String doctypeNumber) {
        atRefundPageSteps.closeAlertBlock();
        atRefundPageSteps.clickOnRefundAllTicketsOption();
        atRefundPageSteps.addSupplierBookingConfirmationNumber(confirmationNumber);
        atRefundPageSteps.addPassengerDoctypeNumber(doctypeNumber);
        atRefundPageSteps.refundTickets(false);
        atRefundPageSteps.shouldSeeErrorAlert();
    }

    @Test(dataProvider = "DP for one ticket refund")
    public void ufsRefundPageValidationOneTicketRefund(String ticketConfirmationNumber, String doctypeNumber) {
        atRefundPageSteps.closeAlertBlock();
        atRefundPageSteps.clickOnRefundOneTicketOption();
        atRefundPageSteps.addSupplierTicketConfirmationNumber(ticketConfirmationNumber);
        atRefundPageSteps.addPassengerDoctypeNumber(doctypeNumber);
        atRefundPageSteps.refundTickets(false);
        atRefundPageSteps.shouldSeeErrorAlert();
    }

    @DataProvider(name = "DP for all tickets refund")
    public Object[][] createDataForAllTicketsRefundTest() {
        return new Object[][]{
                {"", ""},
                {"", RandomDataGenerator.getRandomLiteralsString()},
                {RandomDataGenerator.getRandomNumericString(8), ""},
                {RandomDataGenerator.getRandomNumericString(8), RandomDataGenerator.getRandomLiteralsString()},
                {SQLBooking.getBookingSupplierConfirmationNumber(BaseReseller.getReseller(UFS), OK), RandomDataGenerator.getRandomLiteralsString()},
                {SQLBooking.getBookingSupplierConfirmationNumber(BaseReseller.getReseller(UFS), RF), RandomDataGenerator.getRandomLiteralsString()},
                {SQLBooking.getBookingSupplierConfirmationNumber(BaseReseller.getReseller("DM"), OK), SQLBooking.getFirstBookingPassengerDocumentNumber(BaseReseller.getReseller("DM"), OK)},
                {SQLBooking.getBookingSupplierConfirmationNumber(BaseReseller.getReseller(UFS), OK), ""},
                {SQLBooking.getBookingSupplierConfirmationNumber(BaseReseller.getReseller(UFS), RF), SQLBooking.getFirstBookingPassengerDocumentNumber(BaseReseller.getReseller(UFS), RF)}
        };
    }

    @DataProvider(name = "DP for one ticket refund")
    public Object[][] createDataForOneTicketRefundTest() {
        return new Object[][]{
                {"", ""},
                {"", RandomDataGenerator.getRandomLiteralsString()},
                {RandomDataGenerator.getRandomNumericString(8), ""},
                {RandomDataGenerator.getRandomNumericString(8), RandomDataGenerator.getRandomLiteralsString()}
        };
    }

    @AfterClass
    public void sysLogout() {
        systemSteps.logoutThroughURL();
    }
}
