package ui.pages;

import model.Journey;
import model.Passenger;
import model.SearchMode;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;

import java.util.List;

/**
 * Created by AA on 07.01.2015.
 */
public class JourneySearchPage extends InnerPage {

    public SearchResultPage search(Journey journey, List<Passenger> passengers, SearchMode searchMode){

        setSearchMode(searchMode); //на костылях всё что с ним связано

        setOriginLocation(journey.getOriginLocation());
        setDestinationLocation(journey.getDestinationLocation());
        try {
            setTime(journey);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setDatepicker("#search_query_departDate", journey.getOriginDate());
        addPassengers(passengers);

        $("#search_query_search").waitUntil(disappear, 30000);
        return page(SearchResultPage.class);
    }

    private void setSearchMode(SearchMode searchMode) {
        String[] cssSelectors = {"search_query_toRussianSystem", "search_query_toInternationalSystem", "search_query_toLowCostSystem"};
        for (String cssSelector : cssSelectors){
            executeJavaScript("document.getElementById(" + "\"" + cssSelector + "\"" + ").removeAttribute(\"checked\")");
        }
        if (searchMode.isRussianSystem()){
            setCheckedAttribute(cssSelectors[0]);
        }
        if (searchMode.isInternationalSystem()){
            setCheckedAttribute(cssSelectors[1]);
        }
        if (searchMode.isLowCostSystem()){
            setCheckedAttribute(cssSelectors[2]);
        }
    }

    private void setTime(Journey journey) throws InterruptedException {
        String cssSelector = ".timeSlider";
        //$( ".timeSlider" ).slider( "option", "values", [ 0, 21 ] )
        $(".ui-slider-handle.ui-state-default.ui-corner-all:nth-of-type(1)").waitUntil(appear, 5000);
        executeJavaScript(String.format("$('%s').slider('option','values',[ '%s', '%s'] )", cssSelector, journey.getOriginTimeFrom(), journey.getOriginTimeTo()));
        actions().dragAndDropBy($(".ui-slider-handle.ui-state-default.ui-corner-all:nth-of-type(1)"), 8, 0).build().perform();
        actions().dragAndDropBy($(".ui-slider-handle.ui-state-default.ui-corner-all:nth-of-type(1)"), -8, 0).build().perform();
    }

    //inner methods
    private void setOriginLocation(String originLocation) {

        $("#search_query_departLocationName").setValue(originLocation);
        $(By.xpath(".//*[@class='ui-menu-item'][1]//*[contains(.,'" + originLocation + "')]")).waitUntil(appear, 5000).click();
    }

    private void setDestinationLocation(String destinationLocation) {
        $("#search_query_arrivalLocationName").setValue(destinationLocation);
        $(By.xpath(".//*[@class='ui-menu-item'][1]//*[contains(.,'"+destinationLocation+"')]")).waitUntil(appear, 5000).click();
    }

    //TODO Вынести этот медот куда-то вверх, в общий класс, + он не должен быть виден в тестах
    //метод для удобной работы с jQuery datepicker
    private void setDatepicker(String cssSelector, String date) {
        $(cssSelector).waitUntil(appear, 5000);
        executeJavaScript(String.format("$('%s').datepicker('setDate', '%s')", cssSelector, date));
    }

    private void addPassenger(Passenger passenger, String cssSelector ){
        setDatepicker(cssSelector, passenger.getBirthDate());
    }

    private void addPassengers(List<Passenger> passengers){
        // локатор на элементы birthDate
        String cssDatePickerElements = "div.control-group input.span12.birthDate.hasDatepicker";
        // если пассажиров в поездке больше чем один то
        if (passengers.size() > 1){
            for (int i = 0; i < passengers.size(); i++) {
                if (i != 0){
                    //если это не первый пассажир то нажимаем кнопку добавить
                    $(".addPassenger.btn.btn-inverse").click();
                }
                // Первый аргумент - берём данные пассажира из колекции
                // Второй аргумент - после нажатия на кнопку добавить, появился ещё один элемент датапикер, находим этот элемент и берём атрибут Id
                addPassenger(passengers.get(i), "#"+$$(cssDatePickerElements).get(i).getAttribute("id"));
            }
        } else {
            addPassenger(passengers.get(0), "#"+$$(cssDatePickerElements).get(0).getAttribute("id"));
        }
    }

    private void setCheckedAttribute(String cssSelector){
        //метод удаляет заданому элементу атрибут checked, потом кликает на него
        $("#"+cssSelector).click();
        $("#"+cssSelector).shouldHave(attribute("checked"));
    }



}

