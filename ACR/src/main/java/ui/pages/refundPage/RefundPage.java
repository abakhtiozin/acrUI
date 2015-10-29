package main.java.ui.pages.refundPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import main.java.ApplicationStorage;
import main.java.ui.pages.InnerPage;
import main.java.ui.pages.SupplierPageFactory;
import main.java.ui.pages.refundDetailsPage.RefundDetailsPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by Andrii.Bakhtiozin on 16.01.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class RefundPage extends InnerPage {
    public RefundPage() {
        super();
    }

    private SelenideElement refundBlock() {
        return $(By.xpath(".//form[@name='refund_request']"));
    }

    private SelenideElement bookingIdField() {
        return $("#refund_request_booking");
    }

    private SelenideElement passengerDocNumberField() {
        return $("#refund_request_documentSeriesAndNumber");
    }

    private SelenideElement passengerTicketField() {
        return $("#refund_request_passengerService");
    }

    private SelenideElement refundAllTicketsRadioButton() {
        return $("#refund_request_refundType_0");
    }

    private SelenideElement refundOneTicketRadioButton() {
        return $("#refund_request_refundType_1");
    }

    private SelenideElement refundRequestButton() {
        return $("#refund_request_returnTickets");
    }

    public boolean isRefundTicketBlockDisplayed() {
        return $(By.xpath("//*[@name='refund_request']")).isDisplayed();
    }

    public boolean isRefundBookingBlockDisplayed() {
        return $(".refundBooking").isDisplayed();
    }

    public RefundDetailsPage clickOnRefundTicketsButton() {
        refundRequestButton().click();
        return SupplierPageFactory.getInstance().createRefundDetailsPage(ApplicationStorage.getInstance().getSupplier());
    }

    public RefundPage clickOnRefundTicketsButtonFalse() {
        refundRequestButton().click();
        return this;
    }

    public RefundPage setBookingSupplierConfirmationNumber(String bookingSupplierConfirmationNumber) {
        bookingIdField().shouldBe(Condition.visible).setValue(bookingSupplierConfirmationNumber);
        return this;
    }

    public RefundPage setPassengerDocumentNumber(String passengerDocumentNumber) {
        passengerDocNumberField().shouldBe(Condition.visible).setValue(passengerDocumentNumber);
        return this;
    }

    public RefundPage setPassengerTicketNumber(String passengerTicketNumber) {
        passengerTicketField().shouldBe(Condition.visible).setValue(passengerTicketNumber);
        return this;
    }

    public RefundPage setRefundAllTicketsOption() {
        refundAllTicketsRadioButton().shouldBe(Condition.visible).click();
        return this;
    }

    public RefundPage setRefundOneTicketOption() {
        refundOneTicketRadioButton().shouldBe(Condition.visible).click();
        return this;
    }

    @Override
    public boolean onThisPage() {
        return refundBlock().isDisplayed();
    }
}
