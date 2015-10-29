package main.java.ui.pages.bookFormPage;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by Andrii.Bakhtiozin on 14.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
class BookFormContactInfoFields {
    SelenideElement getContactEmailFieldLocator() {
        return $(By.xpath(".//*[@id='book_request_contactDetails_email']"));
    }

    SelenideElement getContactCountryFieldLocator() {
        return $(By.xpath(".//select[@id='book_request_contactDetails_country']"));
    }

    SelenideElement getContactCityFieldLocator() {
        return $(By.xpath(".//*[@id='book_request_contactDetails_city']"));
    }

    SelenideElement getContactIndexFieldLocator() {
        return $(By.xpath(".//*[@id='book_request_contactDetails_postcode']"));
    }

    SelenideElement getContactStreetFieldLocator() {
        return $(By.xpath(".//*[@id='book_request_contactDetails_street']"));
    }

    SelenideElement getContactHouseFieldLocator() {
        return $(By.xpath(".//*[@id='book_request_contactDetails_buildingNumber']"));
    }

    SelenideElement getContactAppartmentFieldLocator() {
        return $(By.xpath(".//*[@id='book_request_contactDetails_flat']"));
    }

    SelenideElement getContactPhoneCodeFieldLocator() {
        return $(By.xpath(".//*[@id='book_request_contactDetails_phoneAreaCode']"));
    }

    SelenideElement getContactPhoneNumberFieldLocator() {
        return $(By.xpath(".//*[@id='book_request_contactDetails_phoneNumber']"));
    }

    SelenideElement getContactPhoneExtensionFieldLocator() {
        return $(By.xpath(".//*[@id='book_request_contactDetails_phoneExtension']"));
    }
}
