package main.java.ui.pages.bookingDetailsPage;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Andrii.Bakhtiozin on 19.10.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public interface PrintableBookingDetails {
    default BookingDetailsPage clickOnBookingDetails() {
        $(By.xpath(".//a[contains(.,'Детали заказа')]")).click();
        return page(BookingDetailsPage.class);
    }
}
