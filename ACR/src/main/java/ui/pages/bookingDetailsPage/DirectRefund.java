package main.java.ui.pages.bookingDetailsPage;

import main.java.ui.pages.refundDetailsPage.RefundDetailsAcpPage;
import main.java.ui.pages.refundDetailsPage.RefundDetailsPage;

import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Andrii.Bakhtiozin on 25.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public interface DirectRefund extends RefundButtonElement {
    default RefundDetailsPage clickOnRefundBooking() {
        refundButtonElement().click();
        return page(RefundDetailsAcpPage.class);
    }
}
