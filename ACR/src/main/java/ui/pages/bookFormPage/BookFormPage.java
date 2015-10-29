package main.java.ui.pages.bookFormPage;

import com.codeborne.selenide.SelenideElement;
import main.java.ApplicationStorage;
import main.java.model.passenger.Passenger;
import main.java.ui.pages.InnerPage;
import main.java.ui.pages.SupplierPageFactory;
import main.java.ui.pages.bookingDetailsPage.BookingDetailsPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by Andrii.Bakhtiozin on 07.01.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public abstract class BookFormPage extends InnerPage {

    // ------------------------------ FIELDS ------------------------------
    protected BookFormPassengerFields bookFormPassengerFields;
    protected BookFormPagePassengerAction bookFormPagePassengerAction;

    // --------------------------- CONSTRUCTORS ---------------------------
    public BookFormPage() {
        super();
        this.bookFormPassengerFields = new BookFormPassengerFields();
        this.bookFormPagePassengerAction = new BookFormPagePassengerAction();
    }

    // ------------------------------ Locators ------------------------------
    private SelenideElement bookingButton() {
        return $(By.xpath(".//*[@id='book_request_doBooking']"));
    }

    private SelenideElement bookInfo() {
        return $(By.xpath(".//div[@class='row-fluid']"));
    }

    private SelenideElement departureBlock() {
        return $(By.xpath("//div[@class='row-fluid']/div[1]"));
    }

    private SelenideElement arrivalBlock() {
        return $(By.xpath("//div[@class='row-fluid']/div[2]"));
    }

    private SelenideElement categoryBlock() {
        return $(By.xpath("//div[@class='row-fluid']/div[4]"));
    }

    private SelenideElement transporterInfoBlock() {
        return $(By.xpath("//div[@class='row-fluid']/div[3]"));
    }

    // ------------------------------ METHODS -----------------------------
    public abstract BookFormPage addPassenger(Passenger passenger, int passengerNumber);

    public BookingDetailsPage bookOrder() {
        bookingButton().click();
        return SupplierPageFactory.getInstance().createBookingDetailsPage(ApplicationStorage.getInstance().getSupplier());
    }

    public BookFormPage invalidBooking() {
        bookingButton().click();
        return this;
    }

    public String getPassengerBirthDate(int passengerNumber) {
        return bookFormPassengerFields.getBirthDateFieldElement(passengerNumber).getAttribute("value");
    }

    public boolean isEqualDataInDepartureBlock(String data) {
        return departureBlock().$(By.xpath("/*[contains(.,'" + data + "')]")).exists();
    }

    public boolean isEqualDataInArrivalBlock(String data) {
        return arrivalBlock().$(By.xpath("/*[contains(.,'" + data + "')]")).exists();
    }

    public boolean isEqualDataInTransporterBlock(String data) {
        return transporterInfoBlock().$(By.xpath("/*[contains(.,'" + data + "')]")).exists();
    }

    public boolean isEqualDataInCategoryBlock(String data) {
        return categoryBlock().$(By.xpath("/*[contains(.,'" + data + "')]")).exists();
    }

    @Override
    public boolean onThisPage() {
        return bookInfo().isDisplayed();
    }
}
