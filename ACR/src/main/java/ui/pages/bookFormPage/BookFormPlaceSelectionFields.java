package main.java.ui.pages.bookFormPage;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by Andrii.Bakhtiozin on 14.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
class BookFormPlaceSelectionFields {

    public SelenideElement getDeckFieldLocator() {
        return $(By.xpath(".//*[@id='book_request_placesSettings_deck']"));
    }

    public SelenideElement getInOneCoupeFieldLocator() {
        return $(By.xpath(".//*[@id='book_request_placesSettings_inOneCoupe']"));
    }

    public SelenideElement getPlacesRangeStartFieldLocator() {
        return $(By.xpath(".//*[@id='book_request_placesSettings_placesRangeStart']"));
    }

    public SelenideElement getPlacesRangeEndFieldLocator() {
        return $(By.xpath(".//*[@id='book_request_placesSettings_placesRangeEnd']"));
    }

    public SelenideElement getGenderCoupeFieldLocator() {
        return $(By.xpath(".//*[@id='book_request_placesSettings_coupeType']"));
    }

}
