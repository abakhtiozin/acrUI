package main.java.ui.pages.searchResultPage;

import com.codeborne.selenide.SelenideElement;
import main.java.model.Currency;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.cssClass;

/**
 * Created by Andrii.Bakhtiozin on 06.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class BaseSingleTripInfoBlock {

    private SearchResultPage searchResultPage;
    private SelenideElement singleTrip;

    protected BaseSingleTripInfoBlock(SelenideElement singleTrip, SearchResultPage instance) {
        this.searchResultPage = instance;
        this.singleTrip = singleTrip;
    }

    private SelenideElement tripElementsBlock() {
        return singleTrip.find("td>table>tbody>tr");
    }

    private SelenideElement priceRowElement() {
        return tripElementsBlock().find(".priceCell.priceText");
    }

    private SelenideElement tripButton() {
        return tripElementsBlock().find(".btn.changes.btn");
    }

    public String getTransporterNumber() {
        return tripButton().getText();
    }

    public String getTransporterName() {
        return this.tripElementsBlock().find("td.transporterLogoCell *").getAttribute("data-original-title");
    }

    public boolean isNoFreePlaces() {
        return priceRowElement().$("div").has(cssClass("no-places"));
    }

    public double getPrice() {
        String priceRow = this.priceRowElement().$(".price strong").getText();
        return Double.parseDouble(priceRow.substring(0, priceRow.length() - 4).replaceAll("\\s", ""));
    }

    public Currency getCurrency() {
        String priceRow = this.priceRowElement().$(".price strong").getText();
        return Currency.valueOf(priceRow.substring(priceRow.length() - 3));
    }

    public DateTime getDepartDate() {
        SelenideElement dateCell = this.tripElementsBlock().find(".departureCell");
        DateTimeFormatter format = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");
        return format.parseDateTime(dateCell.$("div:nth-of-type(2)").getText() + " " + dateCell.$("div>span>strong").getText());
    }

    public DateTime getTravellingTime() {
        SelenideElement travelTimeCell = this.tripElementsBlock().find(".travelTimeCell.priceText");
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");
        return fmt.parseDateTime(travelTimeCell.getText());
    }

    public DateTime getArrivalDate() {
        SelenideElement dateCell = this.tripElementsBlock().find(".arrivalCell");
        DateTimeFormatter format = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");
        return format.parseDateTime(dateCell.$("div:nth-of-type(2)").getText() + " " + dateCell.$("div>span>strong").getText());
    }

    public DetailedSingleTripInfoBlock chooseTrip() {
        tripButton().click();
        searchResultPage.detailedSingleTripInfoBlock = new DetailedSingleTripInfoBlock(singleTrip, searchResultPage);
        return searchResultPage.detailedSingleTripInfoBlock;
    }
}
