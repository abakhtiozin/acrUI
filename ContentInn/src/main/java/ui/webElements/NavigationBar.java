package main.java.ui.webElements;

import com.codeborne.selenide.SelenideElement;
import main.java.CustomConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Keyboard;
import test.java.base.TestHelper;

import javax.naming.TimeLimitExceededException;

import java.awt.event.KeyEvent;
import java.security.Key;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by trofimenko on 16.10.2015.
 */
public class NavigationBar extends ElementBase {
    private boolean checkNavigationButtonAvailability(SelenideElement locator){
        if (locator.$(By.xpath("./*")).getTagName().equals("a")) {
            return true;
        }else {
            String str = locator.$(By.xpath("./*")).getAttribute("class");
            TestHelper.Log.info("Button " + str.substring(str.indexOf(" ") + 1,str.lastIndexOf("-"))+ " is disabled.");
            return false;
        }
    }

    public void clickButton(SelenideElement locator){
        locator.click();
    }

    public void clickNextPage(){
        SelenideElement locator = $(By.xpath(".//*[@id='grid-data-reservations']/tfoot/tr/th/table/tbody/tr/td[1]/table/tbody/tr/td[4]"));
        //                                    .//*[@id='datalist']/table/tfoot/tr/td/table/tbody/tr/td[4]/a  search resalt
        if(checkNavigationButtonAvailability(locator)){
            clickButton(locator.$(By.xpath("./a")));
            try {
                CustomConditions.waitFor(CustomConditions.ajaxCompleted);
            } catch (TimeLimitExceededException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickPreviousPage(){
        SelenideElement locator = $(By.xpath(".//*[@id='grid-data-reservations']/tfoot/tr/th/table/tbody/tr/td[1]/table/tbody/tr/td[2]"));
        if(checkNavigationButtonAvailability(locator)){
            clickButton(locator.$(By.xpath("./a")));
            try {
                CustomConditions.waitFor(CustomConditions.ajaxCompleted);
            } catch (TimeLimitExceededException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickLastPage(){
        SelenideElement locator = $(By.xpath(".//*[@id='grid-data-reservations']/tfoot/tr/th/table/tbody/tr/td[1]/table/tbody/tr/td[5]"));
        if(checkNavigationButtonAvailability(locator)){
            clickButton(locator.$(By.xpath("./a")));
            try {
                CustomConditions.waitFor(CustomConditions.ajaxCompleted);
            } catch (TimeLimitExceededException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickFirstPage(){
        SelenideElement locator = $(By.xpath(".//*[@id='grid-data-reservations']/tfoot/tr/th/table/tbody/tr/td[1]/table/tbody/tr/td[1]"));
        if(checkNavigationButtonAvailability(locator)){
            clickButton(locator.$(By.xpath("./a")));
            try {
                CustomConditions.waitFor(CustomConditions.ajaxCompleted);
            } catch (TimeLimitExceededException e) {
                e.printStackTrace();
            }
        }
    }

    public void refresh(){
        SelenideElement locator = $(By.xpath(".//*[@id='grid-data-reservations']/tfoot/tr/th/table/tbody/tr/td[4]"));
        if(checkNavigationButtonAvailability(locator)){
            clickButton(locator.$(By.xpath("./a")));
            try {
                CustomConditions.waitFor(CustomConditions.ajaxCompleted);
            } catch (TimeLimitExceededException e) {
                e.printStackTrace();
            }
        }
    }

    public void goToPage(String numberOfPage){
        SelenideElement locator = $(By.xpath(".//*[@id='reservations_page']"));
            resetValue(locator, numberOfPage + Keys.RETURN );
            try {
                CustomConditions.waitFor(CustomConditions.ajaxCompleted);
            } catch (TimeLimitExceededException e) {
                e.printStackTrace();
            }

    }

}
