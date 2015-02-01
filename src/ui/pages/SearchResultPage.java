package ui.pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.SelenideElement;
import model.Supplier;
import model.Trip;
import org.openqa.selenium.By;
import ui.pages.BookFormPage.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AA on 07.01.2015.
 */
public class SearchResultPage extends InnerPage {

    // ------------------------------ FIELDS ------------------------------
    private BookFormPageUFSBuilder bookFormPageUFSBuilder;
    private BookFormPageACPBuilder bookFormPageACPBuilder;
    private BookFormPageTFBuilder bookFormPageTFBuilder;
    private List<SelenideElement> railTripsList;
    private List<SelenideElement> offersList;

    // ------------------------------ METHODS -----------------------------
    private String transporterName(int tripIndex) {
        return $(By.xpath(".//tr[@class='trainInfo supplierType-rail'][" + tripIndex + "]//*[@class='transporterLogoCell']/img")).getAttribute("alt");
    }

    private SelenideElement orderButton(int tripIndex, String tariffType) {
        SelenideElement orderButton = null;
        for (SelenideElement element : offersList) {
            if (element.find(By.xpath("//td/span[@class='modal-tooltip withTooltip badge']")).getText().equals(tariffType)) {
                actions().moveToElement(element.find(By.xpath("//td/span[@class='modal-tooltip withTooltip badge']"))).build().perform();
                orderButton = element.find(By.xpath("//td[@class='orderCell']/a"));
//                actions().moveToElement(orderButton).build().perform();
                break;
            }
        }
        return orderButton;
    }

    private void setTripOffersList(int tripIndex) {
        this.offersList = $$(By.xpath("//*[@id='replacedContent']/table/tbody/tr[" + tripIndex + "]/td/div/table//*[@class='tab-content']/div[contains(@class,'active')]//tbody/tr"));
    }

    private void waitUntilTripDetailsLoaderDisappear(int tripIndex) {
        $(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[" + tripIndex + "]/td/div/table/tbody//img[@class='loader']")).waitUntil(disappear, 10000);
    }

    private SelenideElement tripButton(int tripIndex) {
        return $(By.xpath(".//tr[@class='trainInfo supplierType-rail'][" + tripIndex + "]//*[@class='changesCell']//button"));
    }

    private SelenideElement moreTripDetailsButton(int tripIndex) {
        return $(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[" + tripIndex + "]//tr[@class='groupMore']//button"));
    }

    private void setTrips() {
        System.out.println("кол-во жд предложений: " + $$(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[@class='trainInfo supplierType-rail']")).size());
        this.railTripsList = $$(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[@class='trainInfo supplierType-rail']"));
    }

    public void getTripBaseInfo() {
        $(By.xpath(".//*[@id='mainContainer']/fieldset/legend")).waitUntil(disappear, 30000);
    }

    public List<Trip> getRailTrips() {
        List<Trip> trips = new ArrayList<Trip>();
        for (int i = 1; i <= $$(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[@class='trainInfo supplierType-rail']")).size(); i++) {
            trips.add(i - 1, new Trip()
                            .withTransporterName($(By.xpath(".//tr[@class='trainInfo supplierType-rail'][" + i + "]//*[@class='transporterLogoCell']/img")).getAttribute("alt"))
                            .withTrainNumber($(By.xpath(".//tr[@class='trainInfo supplierType-rail'][" + i + "]//*[@class='changesCell']//button")).getText())
                            .withDepartTime($(By.xpath(".//tr[@class='trainInfo supplierType-rail'][" + i + "]//*[@class='departureCell']//div[2]")).getText() + " " + $(By.xpath(".//tr[@class='trainInfo supplierType-rail'][" + i + "]//*[@class='departureCell']//strong")).getText())
                            .withMinAmaunt($(By.xpath(".//tr[@class='trainInfo supplierType-rail'][" + i + "]//*[@class='priceCell priceText']//strong")).getText())
            );
        }
        return trips;
    }

    public BookFormPage chooseTripByDesireTripOptions(Trip desireTrip, Supplier supplier) {
        for (int i = 1; i <= $$(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[@class='trainInfo supplierType-rail']")).size(); i++) {
            if (tripButton(i).getText().equals(desireTrip.getTrainNumber()) && transporterName(i).equals(desireTrip.getTransporterName())) {
                tripButton(i).click();
                moreTripDetailsButton(i).click();
                waitUntilTripDetailsLoaderDisappear(i);
                setTripOffersList(i);
//                orderButton(i, desireTrip.getTariffType()).click();
                orderButton(i, desireTrip.getTariffType()).followLink();
            }
        }
        GetBookFormPage pageConstructor = new GetBookFormPage();
        switch (supplier) {
            case UFS:
                this.bookFormPageUFSBuilder = new BookFormPageUFSBuilder();
                pageConstructor.setBookFormBuilder(this.bookFormPageUFSBuilder);
                break;
            case ACP:
                this.bookFormPageACPBuilder = new BookFormPageACPBuilder();
                pageConstructor.setBookFormBuilder(this.bookFormPageACPBuilder);
                break;
            case TF:
                this.bookFormPageTFBuilder = new BookFormPageTFBuilder();
                pageConstructor.setBookFormBuilder(this.bookFormPageTFBuilder);
                break;
        }
        pageConstructor.constructBookFormPage();
        return page(pageConstructor.getBookFormPage());
    }

    @Override
    public boolean onThisPage() {
        return false;
    }


//#replacedContent>table.tripList>tbody>tr.trainInfo.supplierType-rail
//.changesCell>*>button>.caret


}
