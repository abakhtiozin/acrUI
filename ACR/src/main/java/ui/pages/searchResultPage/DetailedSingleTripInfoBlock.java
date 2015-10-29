package main.java.ui.pages.searchResultPage;

import com.codeborne.selenide.SelenideElement;

/**
 * Created by Andrii.Bakhtiozin on 06.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class DetailedSingleTripInfoBlock {

    private SearchResultPage searchResultPage;
    private SelenideElement singleTrip;

    protected DetailedSingleTripInfoBlock(SelenideElement singleTrip, SearchResultPage searchResultPage) {
        this.searchResultPage = searchResultPage;
        this.singleTrip = singleTrip;
    }

    public SelenideElement detailedTripInfoBlock() {
        return singleTrip.find("td>div.segments>table>tbody");
    }

    public SingleTripCarriagesInfoBlock getMoreInfo() {
        this.detailedTripInfoBlock().find(".btn.pull-right.getCarriageList.btn").click();
        searchResultPage.singleTripCarriagesInfoBlock = new SingleTripCarriagesInfoBlock(detailedTripInfoBlock(), searchResultPage);
        return searchResultPage.singleTripCarriagesInfoBlock;
    }
}
