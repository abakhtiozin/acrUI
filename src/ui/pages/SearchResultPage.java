package ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.Collection;

import static com.codeborne.selenide.Selenide.$$;

/**
 * Created by AA on 07.01.2015.
 */
public class SearchResultPage {

    public Collection<SelenideElement> getTrips(){
        System.out.println("кол-во жд предложений: " + $$(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[@class='trainInfo supplierType-rail']")).size()+1);
        return $$(By.xpath(".//*[@id='replacedContent']/table/tbody/tr[@class='trainInfo supplierType-rail']"));


    }
    public void getTripBaseInfo(SelenideElement element){
//        element.$$(By.xpath("//tr/td")).get();

    }


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
