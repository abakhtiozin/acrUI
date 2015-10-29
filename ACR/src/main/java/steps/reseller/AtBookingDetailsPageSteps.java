package main.java.steps.reseller;

import db.SQL;
import main.java.core.ACR;
import main.java.model.*;
import main.java.model.price.Commission;
import main.java.model.price.CommissionType;
import main.java.ui.pages.bookingDetailsPage.*;
import main.java.utils.Utils;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Andrii.Bakhtiozin on 23.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class AtBookingDetailsPageSteps extends AtInnerPageSteps {

    private BookingDetailsPage bookingDetailsPage;
    private Trip currentTrip;
    private Booking booking;

    // --------------------------- METHODS ---------------------------

    @Step
    public void clickOnBookOrderButton() {
        validateTripInfo();
        bookingDetailsPage.confirmOrder();
        updateBooking();
        validateTripInfo();
    }

    @Step
    public void clickOnDownloadCouponButton() {
        bookingDetailsPage.clickOnDownloadCoupon();
    }

    @Step
    public void clickOnCheckBookingStatusButton() {
        bookingDetailsPage.checkBooking();
        updateBooking();
        this.isResellerCommissionValid();
    }

    @Step
    public void addCreditCardInfo(CreditCardInfo creditCardInfo) {
        ((BookingDetailsHexPage) bookingDetailsPage).addCreditCardInfo(creditCardInfo);
    }

    @Step
    public void downloadCoupon() {
        Utils.downloadPdf("coupon_pdf", bookingDetailsPage.downloadCouponPdf());
    }

    @Step
    public void sendCouponsOnEmail(String email) {
        bookingDetailsPage.clickOnSendCouponsOnEmailButton();
        bookingDetailsPage.sendCouponsOnEmailModalWindow.setEmail(email);
        bookingDetailsPage.sendCouponsOnEmailModalWindow.clickOnSendButtonInModalWindow();
        Assert.assertTrue("Контрольные купоны успешно отправлены на указанный вами email.".equals(bookingDetailsPage.sendCouponsOnEmailModalWindow.getBodyText()));
        bookingDetailsPage.sendCouponsOnEmailModalWindow.pressClose();
    }

    @Step
    public void shouldBeTextInModalWindowBody(String textToCompare) {
        Assert.assertTrue(textToCompare.equals(bookingDetailsPage.getModalBodyWindowText()), "Text " + textToCompare + " not found, but got " + bookingDetailsPage.getModalBodyWindowText());
    }

    @Step
    public void clickOnBookingDetails() {
        if (bookingDetailsPage instanceof BookingDetailsAcpPage)
            ((BookingDetailsAcpPage) bookingDetailsPage).clickOnBookingDetails();
        if (bookingDetailsPage instanceof BookingDetailsTfPage)
            ((BookingDetailsTfPage) bookingDetailsPage).clickOnBookingDetails();
        if (bookingDetailsPage instanceof BookingDetailsHexPage)
            ((BookingDetailsHexPage) bookingDetailsPage).clickOnBookingDetails();
    }

    @Step
    public void clickOnPreBookingDetails() {
        bookingDetailsPage.clickOnPreBookingDetails();
    }

    @Step
    public void clickOnChangeERegistration() {
        ((BookingDetailsUfsPage) bookingDetailsPage).changeERegistrationStatus();
    }

    @Step
    public void shouldBeDisableChangeERegistration(boolean isDisabled) {
        Assert.assertTrue(isDisabled == ((BookingDetailsUfsPage) bookingDetailsPage).isERegistrationBtnEnabled());
    }

    /**
     * Clicking on 'Request for electronic registration status change' button and closing opened window.
     * Methods required for UFS supplier. There is prohibition to change ER-status without printing
     * request blank.
     */
    @Step
    public void clickOnRequestChangeERegistration() {
        ((BookingDetailsUfsPage) bookingDetailsPage).openRequestChangeERegistration();
    }

    @Step
    public void clickOnCancelBookingButton() {
        validateTripInfo();
        bookingDetailsPage.cancelOrder();
        updateBooking();
    }

    @Step
    public void clickOnRefundBookingButton() {
        if (bookingDetailsPage instanceof BookingDetailsAcpPage || bookingDetailsPage instanceof BookingDetailsTfPage) {
            ((DirectRefund) bookingDetailsPage).clickOnRefundBooking();
            stepManager.setUpResellerAtRefundDetailsPage();
        } else {
            ((RefundThroughForm) bookingDetailsPage).clickOnRefundBooking();
            stepManager.setUpResellerAtRefundPage();
        }
    }

    @Step
    public void goBackToThisPage() {
        int bookingId = applicationStorage.getBooking().getBookingId();
        String url = ACR.getInstance().getSiteURL();
        open(url + "/ru/booking/details/" + bookingId);
        applicationStorage.setCurrentPage(page(BookingDetailsPage.class));
    }


    // --------------------------- VALIDATION STEPS ---------------------------

    @Step
    public void shouldNotSeeDownloadCouponButton() {
        bookingDetailsPage.isDownloadButtonVisable();
    }

    @Step
    @Override
    public void shouldBeOnThisPage() {
        Assert.assertTrue(bookingDetailsPage.onThisPage(), "Not on booking details page");
    }

    @Step
    private void validateTransporter() {
        Assert.assertTrue(bookingDetailsPage.isTextExistInBookInfoBlock(this.currentTrip.getTransporterName()), "Didn't find Transporter name " + this.currentTrip.getTransporterName());
    }

    @Step
    public void validateTransporterNumber() {
        Assert.assertTrue(bookingDetailsPage.isTextExistInBookInfoBlock(this.currentTrip.getTransporterNumber()), "Didn't find Transporter number " + this.currentTrip.getTransporterNumber());
    }

    @Step
    private void validateCarriageType() {
        Assert.assertTrue(bookingDetailsPage.isTextExistInBookInfoBlock(this.currentTrip.getCarriageType()), "Didn't find cu " + this.currentTrip.getCarriageType());
    }

    @Step
    //TODO
    private void validateRouteFrom() {
        Assert.assertTrue(bookingDetailsPage.isTextExistInBookInfoBlock(this.currentTrip.getCarriageType()), "Didn't find cu " + this.currentTrip.getCarriageType());
    }

    @Step
    private void validateRouteTo() {
        Assert.assertTrue(bookingDetailsPage.isTextExistInBookInfoBlock(this.currentTrip.getCarriageType()), "Didn't find cu " + this.currentTrip.getCarriageType());
    }

    @Step
    private void validateDepartureTime() {
        Assert.assertTrue(bookingDetailsPage.isTextExistInBookInfoBlock(this.currentTrip.getDepartTime().toString("HH:mm, dd.MM.yyyy")), "Didn't find departure time " + this.currentTrip.getDepartTime().toString("HH:mm, dd.MM.yyyy"));
    }

    @Step
    private void validateArrivalTime() {
        Assert.assertTrue(bookingDetailsPage.isTextExistInBookInfoBlock(
                        this.currentTrip.getArrivalTime().toString("HH:mm, dd.MM.yyyy")),
                "Didn't find arrival time " + this.currentTrip.getArrivalTime().toString("HH:mm, dd.MM.yyyy")
        );
    }

    @Step
    private void validateTicketType() {
        Assert.assertTrue(bookingDetailsPage.isTextExistInBookInfoBlock(
                        this.currentTrip.getTicketType()),
                "Didn't find ticket type " + this.currentTrip.getTicketType()
        );
    }

    @Step
    public void validateTripInfo() {
        validateTransporter();
//        validateTransporterNumber();
//        validateTicketType();
        if (!applicationStorage.getSupplier().equals(Supplier.HEX)) {
            validateArrivalTime();
            validateDepartureTime();
        }
//        validateCarriageType();
    }

    @Step
    public void isResellerCommissionValid() {
        Map<String, String> map = SQL.getResellerCommissionForSupplier(applicationStorage.getReseller().getResellerCode(), applicationStorage.getBooking().getBookingSupplier().toString(), "order");
        Commission resellerCommission = new Commission(map.get("VALUE"), map.get("CURRENCY"), map.get("COMMISSION_TYPE"));
        double resellerCommissionAmount;
        if (resellerCommission.getCommissionType().equals(CommissionType.FIXED)) {
            resellerCommissionAmount = resellerCommission.getAmount() * applicationStorage.getPassengers().size();
            Assert.assertEquals(resellerCommissionAmount, bookingDetailsPage.getResellerAmount());
        }
    }

    @Override
    public void setUpResellerAtPage() {
        shouldNotSeeErrorAlert();
        this.bookingDetailsPage = (BookingDetailsPage) applicationStorage.getCurrentPage();
        this.currentTrip = applicationStorage.getChosenTrip();
        shouldBeOnThisPage();
        this.booking = new Booking(Integer.parseInt(bookingDetailsPage.getBookingNumber()));
        updateBooking();
        this.booking.setBookingPassengers(applicationStorage.getPassengers());
        applicationStorage.setBooking(booking);
        this.isResellerCommissionValid();
    }


    private void updateBooking() {
        int id = booking.getBookingId();
        booking.setBookingStatus(BookingStatus.valueOf(SQL.getBookingStatusByBookingId(String.valueOf(id))));
        booking.setBackendServiceId(Integer.parseInt(SQL.getBackendServiceIdByBookingId(String.valueOf(id))));
        booking.setBookingSupplier(Supplier.valueOf(SQL.getBookingSupplierByBookingId(String.valueOf(id)).toUpperCase()));
        if (booking.getBookingStatus().equals(BookingStatus.OK)) {
            String supplierConfirmationNumber = SQL.getBookingSupplierConfirmationNumberByBookingId(String.valueOf(id));
            if (supplierConfirmationNumber != null) {
                booking.setSupplierConfirmationNumber(supplierConfirmationNumber);
            }
        }
        Amount amount = new Amount();
        amount.setTransporterAmount(bookingDetailsPage.getTransporterAmount());
        amount.setCommissionsAmount(bookingDetailsPage.getCommissionsAmount());
        amount.setResellerAmount(bookingDetailsPage.getResellerAmount());
        amount.setTotalAmount(bookingDetailsPage.getTotalAmount());
        booking.setAmount(amount);
        applicationStorage.setBooking(booking);
    }
}
