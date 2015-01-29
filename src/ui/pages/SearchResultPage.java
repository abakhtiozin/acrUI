package ui.pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.SelenideElement;
import model.Trip;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AA on 07.01.2015.
 */
public class SearchResultPage extends InnerPage {

    private List<SelenideElement> railTripsList;
    private List<SelenideElement> offersList;

    private SelenideElement orderButton(int tripIndex, String tariffType) {
        SelenideElement orderButton = null;
        for (SelenideElement element : offersList) {
            if (element.find(By.xpath("//td/span[@class='modal-tooltip withTooltip badge']")).getText().equals(tariffType)){
                actions().moveToElement(element.find(By.xpath("//td/span[@class='modal-tooltip withTooltip badge']"))).build().perform();
                orderButton = element.find(By.xpath("//td[@class='orderCell']/a"));
                break;
            }
        }
        return orderButton;
    }

    private void setTripOffersList(int tripIndex){
        this.offersList = $$(By.xpath("//*[@id='replacedContent']/table/tbody/tr[" + tripIndex + "]/td/div/table//*[@class='tab-content']/div[contains(@class,'active')]//tbody/tr"));
    }

    private void waitUntilTripDetailsLoaderDisappear(int tripIndex){
        $(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[" + tripIndex + "]/td/div/table/tbody//img[@class='loader']")).waitUntil(disappear,10000);
    }

    private SelenideElement tripButton(int tripIndex){
        return $(By.xpath(".//tr[@class='trainInfo supplierType-rail'][" + tripIndex + "]//*[@class='changesCell']//button"));
    }

    private String transporterName(int tripIndex){
        return $(By.xpath(".//tr[@class='trainInfo supplierType-rail'][" + tripIndex + "]//*[@class='transporterLogoCell']/img")).getAttribute("alt");
    }

    private SelenideElement moreTripDetailsButton(int tripIndex){
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

    public BookFormPageUFSBuilder chooseTripByDesireTripOptions(Trip desireTrip){
        for (int i = 1; i <= $$(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[@class='trainInfo supplierType-rail']")).size(); i++) {
            if (tripButton(i).getText().equals(desireTrip.getTrainNumber()) && transporterName(i).equals(desireTrip.getTransporterName())){
                tripButton(i).click();
                moreTripDetailsButton(i).click();
                waitUntilTripDetailsLoaderDisappear(i);
                setTripOffersList(i);
                orderButton(i, desireTrip.getTariffType()).followLink();
            }
        }
        return page(BookFormPageUFSBuilder.class);
    }

    @Override
    public boolean onThisPage() {
        return false;
    }

//    private Trip convertRowToTrip(SelenideElement tripElement){
//
//    }

    /*
    private User convertRowToUser(WebElement row) {
List<WebElement> cells = row.findElements(By.tagName("td"));
return new User()
.withName(cells.get(1).getText())
.withEmail(cells.get(2).getText())
.withRole(cells.get(3).getText());
}
     */

//#replacedContent>table.tripList>tbody>tr.trainInfo.supplierType-rail
//.changesCell>*>button>.caret
    /*
        $(By.xpath(".//*[@id='replacedContent']/table/tbody//tr//button[contains(.,'91')]")).click();
        $(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[1]//tr[@class='groupMore']//button")).click();
        $(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[1]/td/div/table//a[contains(.,'1 class')]")).click();
        Data_test data_test = new Data_test(arrivalCity, originCity);
        $(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[1]/td/table/tbody/tr/td[@class='priceCell priceText']//strong")).shouldHave(exactText(data_test.getPrice()));
        System.out.println("Актуальный результат B2B " + data_test.getPrice());

        actions().moveToElement($(By.xpath("//*[@id='replacedContent']/table/tbody/tr[1]/td/div/table//*[@class='tab-content']//tbody/tr/*[contains(.,'"+data_test.getOfferName()+"')]"))).build().perform();
        $(By.xpath("//*[@id='replacedContent']/table/tbody/tr[1]/td/div/table//*[@class='tab-content']//tbody/tr/*[contains(.,'"+data_test.getOfferName()+"')]/parent::tr/td[@class='orderCell']/a")).click();
        $(By.xpath(".//*[@id='book_request_doBooking']")).click();
        $(By.xpath(".//*[@id='replacedContent']/div/h2[text()='Заказ:']")).waitUntil(appear,15000);
     */

}
