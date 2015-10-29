package main.java.steps.reseller;

import main.java.model.Journey;
import main.java.model.Trip;
import main.java.steps.StepManager;
import main.java.ui.pages.bookFormPage.BookFormPage;
import main.java.ui.pages.searchResultPage.BaseSingleTripInfoBlock;
import main.java.ui.pages.searchResultPage.SearchResultPage;
import main.java.utils.Utils;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static main.java.model.Supplier.HEX;

/**
 * Created by Andrii.Bakhtiozin on 23.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class AtSearchResultPageSteps extends AtInnerPageSteps {

    private SearchResultPage searchResultPage;
    private BaseSingleTripInfoBlock baseSingleTripInfoBlock;
    private BookFormPage bookFormPage;
    private Journey journey;
    private Trip desireTrip;
    private DateTime tripDepartDate;
    private DateTime tripArrivalDate;

    // --------------------------- CONSTRUCTORS ---------------------------


    @Override
    public void setUpResellerAtPage() {
        searchResultPage = (SearchResultPage) applicationStorage.getCurrentPage();
        this.journey = applicationStorage.getJourney();
    }

    @Step
    public void findTripByDesireOptions(Trip trip) {
        this.desireTrip = trip;
        this.shouldBeOnThisPage();
        this.shouldNotSeeErrorMessage();
        this.baseSingleTripInfoBlock = searchResultPage.findTripByDesireOptions(this.desireTrip);
    }

    @Step
    public void downloadRoutes() {
        Utils.downloadPdf("routes_pdf", searchResultPage.downloadRoutes());
    }


    @Step
    public void checkBaseTripInfo() {
        this.tripDepartureDateShouldBeAsInJourney();
        this.tripDepartureDateShouldBeLessThenArrival();
        StepManager.getInstance().getSystemSteps().tripPriceShouldBeInResellerCurrency();
    }

    @Step
    public void checkAllTrips() {
        this.shouldBeOnThisPage();
        this.shouldNotSeeErrorMessage();
        List<BaseSingleTripInfoBlock> listOfTrips = searchResultPage.getListOfTrips();
        for (BaseSingleTripInfoBlock trip : listOfTrips) {
            searchResultPage.baseSingleTripInfoBlock = trip;
            this.tripDepartureDateShouldBeAsInJourney();
            this.tripDepartureDateShouldBeLessThenArrival();
        }
    }

    @Step
    public void tripDepartureDateShouldBeAsInJourney() {
        DateTime originJourneyDepartDate = applicationStorage.getJourney().getOriginDate();
        tripDepartDate = new DateTime(searchResultPage.baseSingleTripInfoBlock.getDepartDate());
        long tripDepartDateMillis = tripDepartDate.getMillis();
        Assert.assertTrue(originJourneyDepartDate.getMillis() < tripDepartDateMillis && tripDepartDateMillis < originJourneyDepartDate.plusDays(1).getMillis()
                , "Trip Departure date bigger then origin Journey Departure Date, journey departure date = " + originJourneyDepartDate + " and trip departure date = " + tripDepartDate
                + ", train number " + searchResultPage.baseSingleTripInfoBlock.getTransporterNumber());
    }

    @Step
    public void tripDepartureDateShouldBeLessThenArrival() {
        tripDepartDate = new DateTime(searchResultPage.baseSingleTripInfoBlock.getDepartDate());
        tripArrivalDate = new DateTime(searchResultPage.baseSingleTripInfoBlock.getArrivalDate());
        Assert.assertTrue(tripArrivalDate.isAfter(tripDepartDate)
                , "Trip Departure date bigger then trip Arrival Date, trip departure date = " + tripDepartDate + " and trip arrival date = " + tripArrivalDate
                + ", train number " + searchResultPage.baseSingleTripInfoBlock.getTransporterNumber());
    }

    @Step
    @Deprecated
    public void tripTravellingTimeShouldBeRightCalculated() {
        if (this.desireTrip.getTransporterName() != null && this.desireTrip.getTransporterName().equals("EUROSTAR"))
            return;
        tripDepartDate = new DateTime(searchResultPage.baseSingleTripInfoBlock.getDepartDate());
        tripArrivalDate = new DateTime(searchResultPage.baseSingleTripInfoBlock.getArrivalDate());
        DateTime tripTravellingTime = new DateTime(searchResultPage.baseSingleTripInfoBlock.getTravellingTime());
        int tripTimeDifference = Minutes.minutesBetween(tripDepartDate, tripArrivalDate).getMinutes();
        Assert.assertTrue(tripTimeDifference == tripTravellingTime.getMinuteOfDay(),
                "Trip difference between arrival and departure time is " + tripTimeDifference + " but actual result is " + tripTravellingTime.getMinuteOfDay()
                        + ", train number " + searchResultPage.baseSingleTripInfoBlock.getTransporterNumber());
    }

    @Step
    public void chooseTripAndGoToBookForm() {
        clickOnTrainNumberButtonToExpand();
        clickOnMoreInfoButtonToLookOnOffers();
        chooseCarriageType();
        findSingleTripOffer();
        orderOffer();
    }

    @Step
    public void clickOnTrainNumberButtonToExpand() {
        searchResultPage.detailedSingleTripInfoBlock = searchResultPage.baseSingleTripInfoBlock.chooseTrip();
    }

    @Step
    public void clickOnMoreInfoButtonToLookOnOffers() {
        searchResultPage.singleTripCarriagesInfoBlock = searchResultPage.detailedSingleTripInfoBlock.getMoreInfo();
    }

    @Step
    public void chooseCarriageType() {
        shouldNotSeeErrorMessage();
        searchResultPage.singleTripCarriagesInfoBlock = searchResultPage.singleTripCarriagesInfoBlock.chooseCarriageType();
    }

    @Step
    public void findSingleTripOffer() {
        searchResultPage.singleTripOffer = searchResultPage.singleTripCarriagesInfoBlock.getSingleOffer();
    }

    @Step
    public void orderOffer() {
        String transporterNumber = searchResultPage.baseSingleTripInfoBlock.getTransporterNumber();
        Trip trip = new Trip()
                .withTrainNumber(transporterNumber)
                .withCarriageType(searchResultPage.singleTripCarriagesInfoBlock.getCarriageType())
                .withDepartTime((!transporterNumber.equals(HEX.toString())) ? searchResultPage.baseSingleTripInfoBlock.getDepartDate() : null)
                .withArrivalTime((!transporterNumber.equals(HEX.toString())) ? searchResultPage.baseSingleTripInfoBlock.getArrivalDate() : null)
                .withTransporterName(searchResultPage.baseSingleTripInfoBlock.getTransporterName())
                .withTicketType(searchResultPage.singleTripOffer.getTicketType());
        applicationStorage.setChosenTrip(trip);
        bookFormPage = searchResultPage.singleTripOffer.order();
        stepManager.setUpResellerAtBookFormPage();
    }

    @Step
    @Override
    public void shouldBeOnThisPage() {
        Assert.assertTrue(searchResultPage.onThisPage(), "Not on search results page");
    }

    @Step
    private void shouldNotSeeErrorMessage() {
        Assert.assertTrue(searchResultPage.getErrorText().isEmpty(), "ERROR FOUND " + searchResultPage.getErrorText());
    }

    @Step
    public void shouldSeeDisabledOrderButton() {
        clickOnTrainNumberButtonToExpand();
        clickOnMoreInfoButtonToLookOnOffers();
        chooseCarriageType();
        findSingleTripOffer();
        Assert.assertTrue(searchResultPage.singleTripOffer.isOrderButtonDisabled());
    }

    @Step
    @Deprecated
    public void shouldSeeEnableOrderButton() {
        clickOnTrainNumberButtonToExpand();
        clickOnMoreInfoButtonToLookOnOffers();
        chooseCarriageType();
        findSingleTripOffer();
        Assert.assertFalse(searchResultPage.singleTripOffer.isOrderButtonDisabled());
    }

    @Step
    public void shouldBeDoubleDeckCarriage() {
        Assert.assertTrue(searchResultPage.singleTripOffer.isDoubleDecksCarriage(), "Double deck label was not found");
    }


}
