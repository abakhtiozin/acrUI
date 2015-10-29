package main.java.ui.pages.bookFormPage;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.NotFoundException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Created by Andrii.Bakhtiozin on 10.08.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public interface PassengerActionsOnBookForm {
    default void clickOnAddPassengerButton() {
        boolean result = false;
        String locator = ".addPassenger.btn.btn-inverse";
        for (SelenideElement button : $$(locator)) {
            if (button.is(visible)) {
                button.click();
                result = true;
                break;
            }
        }
        if (!result) throw new NotFoundException("Element " + locator + " not found");
    }

    default void clickOnRemovePassengerButton() {
        boolean result = false;
        String locator = ".deletePassenger.btn";
        for (SelenideElement button : $$(locator)) {
            if (button.is(visible)) {
                button.click();
                result = true;
                break;
            }
        }
        if (!result) throw new NotFoundException("Element " + locator + " not found");
    }
}
