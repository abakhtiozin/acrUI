package main.java.ui.pages.searchResultPage;

import com.codeborne.selenide.SelenideElement;
import db.SQL;
import main.java.ApplicationStorage;
import main.java.model.Currency;
import main.java.model.Supplier;
import main.java.ui.pages.SupplierPageFactory;
import main.java.ui.pages.bookFormPage.BookFormPage;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;

/**
 * Created by Andrii.Bakhtiozin on 06.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class SingleTripOffer {

    private SelenideElement singleOffer;
    private List<SelenideElement> singleOfferServiceClasses;
    private SearchResultPage searchResultPage;

    protected SingleTripOffer(SelenideElement singleOffer, SearchResultPage instance) {
        this.searchResultPage = instance;
        this.singleOffer = singleOffer;
    }

    public String getOfferPrice() {
        return "";
    }

    public Currency getOfferCurrency() {
        return Currency.valueOf("");
    }

    public String getOfferTariffName() {
        return singleOffer.find("td:nth-of-type(1)>h3").getText();
    }

    public String[] getOfferTariffsName() {
        this.singleOfferServiceClasses = singleOffer.$$(By.xpath(".//td/span[@class='modal-tooltip withTooltip badge']")).filter(visible);
        String[] singleOfferServiceClassesArray = new String[singleOfferServiceClasses.size()];
        for (int i = 0; i < singleOfferServiceClasses.size(); i++) {
            singleOfferServiceClassesArray[i] = singleOfferServiceClasses.get(i).getText();
        }
        return singleOfferServiceClassesArray;
    }

    public String getTicketType() {
        return singleOffer.$(".modal-tooltip.withHtmlTooltip.ticketType24").getAttribute("data-original-title");
    }

    public boolean isDoubleDecksCarriage() {
        return singleOffer.$(".twoDecks").isDisplayed();
    }

    public BookFormPage order() {
        singleOffer.find("td.orderCell>a").followLink();
        String transporterName = this.searchResultPage.desireTrip.getTransporterName();
        if (transporterName.contains(",")) {
            transporterName = transporterName.split(",")[0];
        }
        ApplicationStorage.getInstance().setSupplier(transporterName.equals("HEX") ? Supplier.HEX : Supplier.valueOf(SQL.getSupplierByTransporterName(transporterName).toUpperCase()));
        return SupplierPageFactory.getInstance().createBookFormPage(ApplicationStorage.getInstance().getSupplier());
    }

    public boolean isOrderButtonDisabled() {
        return singleOffer.$("td.orderCell .modal-tooltip.withTooltip.btn.btn-block.btn-nochange.disabled").is(exist);
    }


}
