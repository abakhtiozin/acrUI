package ui.pages.BookFormPage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bakhtiozin on 23.01.2015.
 */
public class BookFormPageTFBuilder extends BookFormPageBuilder {

    // --------------------------- CONSTRUCTORS ---------------------------
    public BookFormPageTFBuilder() {
        passengerFieldsLocators = new HashMap<BookFormPageFieldsName, String>();
        this.passengerFieldsLocators.put(BookFormPageFieldsName.STATE_ISSUED_DOCUMENT, ".//input[@class='span12 stateWhichIssuedDocument']");
        this.passengerFieldsLocators.put(BookFormPageFieldsName.GENDER, ".//input[@class='span12 gender']");
    }

    // ------------------------------ FIELDS ------------------------------
    private Map<BookFormPageFieldsName, String> passengerFieldsLocators;
    @Override
    protected void setPassengerLocators() {

    }
}
