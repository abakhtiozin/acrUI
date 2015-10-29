package main.java.ui.pages.bookingDetailsPage;

import main.java.ui.pages.refundPage.RefundPage;

import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Andrii.Bakhtiozin on 25.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public interface RefundThroughForm extends RefundButtonElement {
    default RefundPage clickOnRefundBooking() {
        refundButtonElement().click();
        return page(RefundPage.class);
    }
}
