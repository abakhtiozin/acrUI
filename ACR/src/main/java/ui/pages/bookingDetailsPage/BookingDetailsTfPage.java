package main.java.ui.pages.bookingDetailsPage;

import main.java.ui.pages.refundDetailsPage.RefundDetailsPage;
import main.java.ui.pages.refundDetailsPage.RefundDetailsTfPage;

import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Andrii.Bakhtiozin on 22.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class BookingDetailsTfPage extends BookingDetailsPage implements DirectRefund, PrintableBookingDetails {
    public BookingDetailsTfPage() {
        super();
    }

    @Override
    public RefundDetailsPage clickOnRefundBooking() {
        refundButtonElement().click();
        return page(RefundDetailsTfPage.class);
    }


}
