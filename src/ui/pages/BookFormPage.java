package ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Created by AA on 07.01.2015.
 */
public  class BookFormPage extends InnerPage {
    /*
    TODO тест удаление пассажира на форме
    Сценарий:
    1) Добавить пассажиров
    2) сделать поиск
    3) удалить пассажира(ов) на форме пребукинга
    4) сделать заказ
    Ожидаемый результат: кол-во пассажиров и их данные соответствуют тем данным что были при поиске
    */
    private String locatorBookInfo = ".//*[@id='replacedContent']/div[@class='row-fluid']";
    private String locatorPlacesSelectionContent = ".//*[@id='placesSelectionContent']";

    private List<SelenideElement> passengers(){
        return $$(By.xpath(".//div[@class='span3 passengerContainer']"));
    }

    private SelenideElement bookInfo(){
        return $(this.locatorBookInfo);
    }

    public SelenideElement bookingButton(){
        return $(By.xpath(".//*[@id='book_request_doBooking']"));
    }

    @Override
    public boolean onThisPage() {
        return bookInfo().isDisplayed();
    }
}
