package ui.pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import com.codeborne.selenide.SelenideElement;
import model.Trip;
import org.openqa.selenium.By;

import java.util.Collection;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Created by AA on 07.01.2015.
 */
public class SearchResultPage {

    private List<SelenideElement> railTripsList;

    private void setTrips(){
        System.out.println("кол-во жд предложений: " + $$(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[@class='trainInfo supplierType-rail']")).size());
        this.railTripsList = $$(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[@class='trainInfo supplierType-rail']"));
    }

    public void getTripBaseInfo(){
        setTrips();
        System.out.println(railTripsList.size());
        test(railTripsList);
        $(By.xpath(".//*[@id='mainContainer']/fieldset/legend")).waitUntil(disappear,30000);
        System.out.println(railTripsList.get(0).$(By.xpath("//*[@class='changesCell']//button")).getText());
        System.out.println(railTripsList.get(1).$(By.xpath("//*[@class='changesCell']//button")).getText());
        System.out.println(railTripsList.get(2).$(By.xpath("//*[@class='changesCell']//button")).getText());
        System.out.println(railTripsList.get(3).$(By.xpath("//*[@class='changesCell']//button")).getText());

    }

    public void test(List<SelenideElement> trips){
        for (int i = 0; i < trips.size(); i++) {
            System.out.println(trips.get(i).$(By.xpath("//*[@class='transporterLogoCell']/img")).getAttribute("alt"));
            System.out.println(trips.get(i).$(By.xpath("//*[@class='changesCell']//button")).getText());
            System.out.println(trips.get(i).$(By.xpath("//*[@class='departureCell']//strong")).getText() + " " + trips.get(i).$(By.xpath("//*[@class='departureCell']//div[2]")).getText());
            System.out.println(trips.get(i).$(By.xpath("//*[@class='priceCell priceText']//strong")).getText());
        }
//        $$(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[@class='trainInfo supplierType-rail']")).
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
