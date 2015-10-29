package main.java.ui.pages.searchResultPage;

import com.codeborne.selenide.SelenideElement;
import db.SQL;
import main.java.model.Supplier;
import main.java.ui.pages.SelenideWait;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.hasClass;
import static com.codeborne.selenide.Selenide.actions;

/**
 * Created by Andrii.Bakhtiozin on 06.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class SingleTripCarriagesInfoBlock implements SelenideWait {

    private List<SelenideElement> offersList;
    private SearchResultPage searchResultPage;
    private SelenideElement detailedTripInfoBlock;

    protected SingleTripCarriagesInfoBlock(SelenideElement detailedTripInfoBlock, SearchResultPage instance) {
        this.searchResultPage = instance;
        this.detailedTripInfoBlock = detailedTripInfoBlock;
        waitUntilTripDetailsLoaderDisappear();
    }

    private void waitUntilTripDetailsLoaderDisappear() {
        this.singleTripCarriagesInfoBlock().find("img.loader").waitUntil(disappear, LONG_WAIT_TIME);
    }

    private SelenideElement singleTripCarriagesInfoBlock() {
        return detailedTripInfoBlock.find("tr.carriagesInfo");
    }

    public SingleTripCarriagesInfoBlock chooseCarriageType() {
        List<SelenideElement> carriageTabs = this.singleTripCarriagesInfoBlock().findAll(".nav.nav-tabs>li");
        if (this.searchResultPage.desireTrip.getCarriageType() != null) {
            for (int i = 0; i < carriageTabs.size(); i++) {
                if (carriageTabs.get(i).$("a").getText().equals(this.searchResultPage.desireTrip.getCarriageType())) {
                    carriageTabs.get(i).$("a").click();
                    carriageTabs.get(i).waitUntil(hasClass("active"), LONG_WAIT_TIME);
                    carriageTabs.get(i).$("a").click();
                    this.offersList = setTripOffersList(i + 1);
                    return this;
                }
            }
        }
        this.offersList = setTripOffersList(1);
        assert this.offersList.size() > 0;
        return this;
    }

    public String getCarriageType() {
        return this.singleTripCarriagesInfoBlock().$(".nav.nav-tabs>li.active").getText();
    }

    public SingleTripOffer getSingleOffer() {
        for (SelenideElement uiSingleOffer : this.offersList) {
            if (this.searchResultPage.desireTrip.getTariffType() == null) {
                actions().moveToElement(uiSingleOffer.$("td", 1)).build().perform();
                searchResultPage.singleTripOffer = new SingleTripOffer(uiSingleOffer, searchResultPage);
                return searchResultPage.singleTripOffer;
            } else {
                SingleTripOffer singleTripOffer = new SingleTripOffer(uiSingleOffer, searchResultPage);
                if (!SQL.getSupplierByTransporterName(this.searchResultPage.desireTrip.getTransporterName()).toUpperCase().equals(Supplier.UFS.toString())) {
                    if (this.searchResultPage.desireTrip.getTariffType()[0].equals(singleTripOffer.getOfferTariffName())) {
                        actions().moveToElement(uiSingleOffer.$("td", 1)).build().perform();
                        searchResultPage.singleTripOffer = new SingleTripOffer(uiSingleOffer, searchResultPage);
                        return searchResultPage.singleTripOffer;
                    }
                }
                if (Arrays.equals(this.searchResultPage.desireTrip.getTariffType(), singleTripOffer.getOfferTariffsName())) {
                    actions().moveToElement(uiSingleOffer.$("td", 1)).build().perform();
                    searchResultPage.singleTripOffer = new SingleTripOffer(uiSingleOffer, searchResultPage);
                    return searchResultPage.singleTripOffer;
                }
            }
        }
        Assert.assertTrue(searchResultPage.singleTripOffer != null);
        return searchResultPage.singleTripOffer;
    }

    private List<SelenideElement> setTripOffersList(int carriageTypeTabNum) {
        return this.singleTripCarriagesInfoBlock().findAll(".tab-content>div:nth-of-type(" + carriageTypeTabNum + ") tbody>tr");
    }
}
