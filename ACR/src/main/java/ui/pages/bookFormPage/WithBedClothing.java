package main.java.ui.pages.bookFormPage;

import com.codeborne.selenide.Condition;
import util.ui.JQueryWorker;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by Andrii.Bakhtiozin on 11.08.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public interface WithBedClothing {
    default void setWithBedClothing(boolean withBedClothing) {
        String locator = "#book_request_withBedClothing";
        $(locator).shouldBe(Condition.visible);
        JQueryWorker.setCheckBoxValue(locator, withBedClothing);
    }
}
