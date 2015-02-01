package ui.pages;

import com.codeborne.selenide.SelenideElement;
import model.Journey;
import model.passenger.Passenger;
import model.SearchMode;
import org.openqa.selenium.By;
import ui.util.JQueryWorker;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;

import java.util.List;

/**
 * Created by AA on 07.01.2015.
 */
public class JourneySearchPage extends InnerPage {

    // --------------------------- CONSTRUCTORS ---------------------------
    // ------------------------------ FIELDS ------------------------------
    // ------------------------------ METHODS -----------------------------


    private JQueryWorker jQueryWorker;

    public JourneySearchPage() {
        jQueryWorker = new JQueryWorker();
    }

    public SelenideElement resetButton() {
        return $("#search_query_reset");
    }

    private SelenideElement searchButton() {
        return $("#search_query_search");
    }

    private SelenideElement departmentDateField() {
        return $("#search_query_departDate");
    }

    private SelenideElement destinationLocationField() {
        return $("#search_query_arrivalLocationName");
    }

    private SelenideElement originLocationField() {
        return $("#search_query_departLocationName");
    }

    private List<SelenideElement> passengerBirthDateFields() {
        return $$("div.control-group input.span12.birthDate.hasDatepicker");
    }

    private SelenideElement toRussianCheckBox() {
        return $("#search_query_toRussianSystem");
    }

    private SelenideElement toInternationalCheckBox() {
        return $("#search_query_toInternationalSystem");
    }

    private SelenideElement toLowCostCheckBox() {
        return $("#search_query_toLowCostSystem");
    }

    private List<SelenideElement> deletePassengerButtons() {
        return $$(".deletePassenger.btn");
    }

    public JourneySearchPage deletePassenger(Passenger passenger) {
        for (int i = 1; i < passengerBirthDateFields().size(); i++) {
            if (passengerBirthDateFields().get(i).val().equals(passenger.getBirthDate())) {
                deletePassengerButtons().get(i - 1).click();
                $("#deletePassengerModal>div.modal-footer>button.btn.btn-primary.yes").waitUntil(appear, 5000).click();
            }
        }
        return page(JourneySearchPage.class);
    }

    public JourneySearchPage pressResetButton() {
        resetButton().click();
        return page(JourneySearchPage.class);
    }

    public boolean compareFieldsToPassengers(List<Passenger> passengers) {
        for (int i = 1; i < passengers.size(); i++) {
            if (!passengers.get(i).getBirthDate().equals(passengerBirthDateFields().get(i).val())) {
                return false;
            }
        }
        return true;
    }

    /*
    TODO Тест сохранения предыдущего поиска
    Сценарий:
    1) Сделать сёрч
    2) вернутся назад через главное меню
    Ожидаемый результат: данные на форме сёрча остались
     */

    /*
    TODO тест кнопки сброс
    Сценарий:
    1) Сделать сёрч
    2) вернутся назад через главное меню
    3) нажать на кнопку Сброс
    Ожидаемый результат: все поля пустые
     */

     /*
    TODO тест удаление пассажира на форме
    Сценарий:
    1) Добавить пассажиров
    2) удалить пассажира(ов)
    Ожидаемый результат: удалились те пассажиры на которых была нажата кнопка Удалить
     */

         /*
    TODO тест удаление пассажира на форме
    Сценарий:
    1) Добавить пассажиров
    2) удалить пассажира(ов)
    3) сделать поиск
    4) сделать заказ
    Ожидаемый результат: кол-во пассажиров и их данные соответствуют тем данным что были при поиске
     */

    public SearchResultPage search(Journey journey, List<Passenger> passengers, SearchMode searchMode) {

        resetButton().click();
        setSearchMode(searchMode);
        setOriginLocation(journey.getOriginLocation());
        setDestinationLocation(journey.getDestinationLocation());
        setTime(journey.getOriginTimeFrom(), journey.getOriginTimeTo());

        jQueryWorker.setDatepicker("#" + departmentDateField().getAttribute("id"), journey.getOriginDate());
        addPassengers(passengers);

        searchButton().click();
        return page(SearchResultPage.class);
    }

    //set checkboxs
    private void setSearchMode(SearchMode searchMode) {
        if (searchMode.isRussianSystem()) {
            jQueryWorker.setCheckBoxValue("#" + toRussianCheckBox().getAttribute("id"), true);
        } else jQueryWorker.setCheckBoxValue("#" + toRussianCheckBox().getAttribute("id"), false);
        if (searchMode.isInternationalSystem()) {
            jQueryWorker.setCheckBoxValue("#" + toInternationalCheckBox().getAttribute("id"), true);
        } else jQueryWorker.setCheckBoxValue("#" + toInternationalCheckBox().getAttribute("id"), false);
        if (searchMode.isLowCostSystem()) {
            jQueryWorker.setCheckBoxValue("#" + toLowCostCheckBox().getAttribute("id"), true);
        } else jQueryWorker.setCheckBoxValue("#" + toLowCostCheckBox().getAttribute("id"), false);
    }

    private void setTime(int originTimeFrom, int originTimeTo) {
        String cssSelector = ".timeSlider";
        $(".ui-slider-handle.ui-state-default.ui-corner-all:nth-of-type(1)").waitUntil(appear, 5000);
        executeJavaScript(String.format("$('%s').slider('option','values',[ '%s', '%s'] )", cssSelector, originTimeFrom, originTimeTo));
        actions().dragAndDropBy($(".ui-slider-handle.ui-state-default.ui-corner-all:nth-of-type(1)"), 8, 0).build().perform();
        actions().dragAndDropBy($(".ui-slider-handle.ui-state-default.ui-corner-all:nth-of-type(1)"), -8, 0).build().perform();
    }

    //inner methods
    private void setOriginLocation(String originLocation) {
        originLocationField().setValue(originLocation);
        $(By.xpath(".//*[@class='ui-menu-item'][1]//*[contains(.,'" + originLocation + "')]")).waitUntil(appear, 5000).click();
    }

    private void setDestinationLocation(String destinationLocation) {
        destinationLocationField().setValue(destinationLocation);
        $(By.xpath(".//*[@class='ui-menu-item'][1]//*[contains(.,'" + destinationLocation + "')]")).waitUntil(appear, 5000).click();
    }

    private void addPassenger(Passenger passenger, String cssSelector) {
        jQueryWorker.setDatepicker("#" + cssSelector, passenger.getBirthDate());
    }

    public void addPassengers(List<Passenger> passengers) {
        // локатор на элементы birthDate
        // если пассажиров в поездке больше чем один то
        if (passengers.size() > 1) {
            for (int i = 0; i < passengers.size(); i++) {
                if (i != 0) {
                    //если это не первый пассажир то нажимаем кнопку добавить
                    $(".addPassenger.btn.btn-inverse").click();
                }
                // Первый аргумент - берём данные пассажира из колекции
                // Второй аргумент - после нажатия на кнопку добавить, появился ещё один элемент датапикер, находим этот элемент и берём атрибут Id
                addPassenger(passengers.get(i), passengerBirthDateFields().get(i).getAttribute("id"));
            }
        } else {
            addPassenger(passengers.get(0), passengerBirthDateFields().get(0).getAttribute("id"));
        }
    }

    @Override
    public boolean onThisPage() {
        return $(By.xpath(".//*[@id='replacedContent']/form[@name='search_query']")).isDisplayed();
    }
}

