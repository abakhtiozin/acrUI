package main.java.ui.pages.searchResultPage;


import com.codeborne.selenide.SelenideElement;
import main.java.model.Currency;
import main.java.model.Trip;
import main.java.ui.pages.InnerPage;
import org.openqa.selenium.By;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Created by Andrii.Bakhtiozin on 07.01.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class SearchResultPage extends InnerPage {


    // ------------------------------ FIELDS ------------------------------
    public BaseSingleTripInfoBlock baseSingleTripInfoBlock;
    public DetailedSingleTripInfoBlock detailedSingleTripInfoBlock;
    public SingleTripCarriagesInfoBlock singleTripCarriagesInfoBlock;
    public SingleTripOffer singleTripOffer;
    protected Trip desireTrip;
    private List<SelenideElement> blocksOfTrips;
    private List<SelenideElement> tripsList;


    // ------------------------------ CONSTRUCTOR ------------------------------
    public SearchResultPage() {
        super();
    }

    public BaseSingleTripInfoBlock findTripByDesireOptions(Trip desireTrip) {
        this.desireTrip = desireTrip;
        $("table.tripList").waitUntil(visible, LONG_WAIT_TIME);
        this.blocksOfTrips = $$("table.tripList");
        for (SelenideElement blockOfTrip : blocksOfTrips) {
            this.tripsList = blockOfTrip.$$("tr.trainInfo");
            for (SelenideElement aTripsList : this.tripsList) {
                this.baseSingleTripInfoBlock = new BaseSingleTripInfoBlock(aTripsList, this);
                if ((desireTrip.getTransporterNumber() == null && !baseSingleTripInfoBlock.isNoFreePlaces() && baseSingleTripInfoBlock.getTransporterName().equals(desireTrip.getTransporterName())) ||
                        (baseSingleTripInfoBlock.getTransporterNumber().equals(desireTrip.getTransporterNumber()) && baseSingleTripInfoBlock.getTransporterName().equals(desireTrip.getTransporterName()))) {
                    return this.baseSingleTripInfoBlock;
                }
                baseSingleTripInfoBlock = null;
            }
        }
        if (baseSingleTripInfoBlock == null) {
            throw new NullPointerException("Trips not found");
        }
        return this.baseSingleTripInfoBlock;
    }

    public List<BaseSingleTripInfoBlock> getListOfTrips() {
        List<BaseSingleTripInfoBlock> arrayOfTrips = new ArrayList<>();
        $("table.tripList").waitUntil(visible, LONG_WAIT_TIME);
        this.blocksOfTrips = $$("table.tripList");
        for (SelenideElement blockOfTrip : blocksOfTrips) {
            this.tripsList = blockOfTrip.$$("tr.trainInfo");
            arrayOfTrips.addAll(this.tripsList.stream().map(aTripsList -> new BaseSingleTripInfoBlock(aTripsList, this)).collect(Collectors.toList()));
        }
        return arrayOfTrips;
    }

    public Currency getTripCurrency() {
        return baseSingleTripInfoBlock.getCurrency();
    }

    private SelenideElement alertBlock() {
        return $(".alert.alert-error.alert-block");
    }

    // ------------------------------ PUBLIC METHODS -----------------------------
    public File downloadRoutes() {
        File file = null;
        try {
            file = $(By.xpath(".//a[contains(.,'Сохранить маршруты')]")).waitUntil(visible, WAIT_TIME).download();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }


    @Override
    public boolean onThisPage() {
        return $("#tripListContainer").isDisplayed();
    }

    public String getErrorText() {
        SelenideElement errorBlock = $(".tripListBlockContainerError");
        if (errorBlock.isDisplayed()) {
            return errorBlock.getText();
        }
        if (alertBlock().isDisplayed()) {
            return alertBlock().getText();
        }
        return "";
    }

}
