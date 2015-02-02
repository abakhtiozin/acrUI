package ui.pages.BookFormPage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bakhtiozin on 23.01.2015.
 */
public class BookFormPageACPBuilder extends BookFormPageBuilder {

    // --------------------------- CONSTRUCTORS ---------------------------
    public BookFormPageACPBuilder() {
        passengerFieldsLocators = new HashMap<BookFormPageFieldsName, String>();
        this.passengerFieldsLocators.put(BookFormPageFieldsName.SALUTATION, ".//input[@class='span12 tariff']");
        this.passengerFieldsLocators.put(BookFormPageFieldsName.TARIFF, ".//input[@class='span12 tariff']");
        this.passengerFieldsLocators.put(BookFormPageFieldsName.COUNTRY, ".//input[@class='span12 gender']");
    }

    // ------------------------------ FIELDS ------------------------------
    private Map<BookFormPageFieldsName, String> passengerFieldsLocators;

    @Override
    protected void setPassengerLocators() {

    }
}
