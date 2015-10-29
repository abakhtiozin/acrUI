package main.java.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static main.java.ui.pages.SelenideWait.MIDDLE_WAIT_TIME;

/**
 * Created by Andrii.Bakhtiozin on 24.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class ModalWindow {
    private SelenideElement modalWindow;

    public ModalWindow(SelenideElement modalWindowId) {
        this.modalWindow = $(".modal#" + modalWindowId.getAttribute("id")).waitUntil(visible, MIDDLE_WAIT_TIME);
    }

    public void pressYes() {
        this.modalWindow.$(" .btn.yes").click();
    }

    public void pressNo() {
        this.modalWindow.$(" .btn.no").click();
    }

    public void pressClose() {
        this.modalWindow.$(" .close").click();
    }

    public String getTitleText() {
        return this.modalWindow.$(" .modal-header-title").getText();
    }

    public String getBodyText() {
        waitUntilLoaderDisappear();
        this.modalWindow.$(" .modal-loader").shouldNotBe(visible);
        sleep(3000);//I don't know how to avoid browse/driver/js error in modal window when I'm trying to get text
        // immediately after modal window have appeared. Seems like it's a selenium bug or something
        this.modalWindow.$(" .modal-loader").shouldNotBe(visible);
        return this.modalWindow.$(" .modal-body").getText();
    }

    private void waitUntilLoaderDisappear() {
        modalWindow.$(" .modal-loader").waitUntil(disappear, MIDDLE_WAIT_TIME);
    }
}
