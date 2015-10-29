package main.java.ui.pages.bookingDetailsPage;

import com.codeborne.selenide.SelenideElement;
import main.java.ui.pages.InnerPage;
import main.java.ui.pages.ModalWindow;
import org.openqa.selenium.By;

import java.io.File;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static main.java.utils.Utils.switchDriverToMainWindow;

/**
 * Created by Andrii.Bakhtiozin on 07.01.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class BookingDetailsPage extends InnerPage {

    public SendCouponsOnEmailModalWindow sendCouponsOnEmailModalWindow;

    public BookingDetailsPage() {
        super();
    }

    protected ModalWindow waitForModalWindow() {
        return new ModalWindow($("#statusChangeModal").waitUntil(appear, 70000));
    }

    public BookingDetailsPage cancelOrder() {
        $(".btn.invalidateOrder").click();
        waitForModalWindow().pressClose();
        return this;
    }

    public BookingDetailsPage confirmOrder() {
        $(".btn.btn-primary.confirmOrder").shouldHave(visible).click();
        waitForModalWindow().pressClose();
        return this;
    }

    public BookingDetailsPage checkBooking() {
        clickOnCheckButtonWhenAppear();
        waitForModalWindow().pressClose();
        return this;
    }

    public void clickOnPreBookingDetails() {
        preBookingDetailsButton().click();
        switchDriverToMainWindow();
    }

    private void clickOnCheckButtonWhenAppear() {
        $(".alert.alert-block").waitUntil(appear, WAIT_TIME).shouldHave(text("Требуется проверка статуса."));
        $("#checkBooking").shouldBe(visible).click();
    }


    public BookingDetailsPage clickOnDownloadCoupon() {
        downloadCouponButtonElement().click();
        return this;
    }

    public String getModalTitleWindowText() {
        return new ModalWindow($("#emptyCouponModal")).getTitleText();
    }

    public String getModalBodyWindowText() {
        return new ModalWindow($("#emptyCouponModal")).getBodyText();
    }

    private String getModalBodyWindowText(ModalWindow modalWindow) {
        return modalWindow.getBodyText();
    }

    public File downloadCouponPdf() {
        File file = null;
        try {
            file = downloadCouponButtonElement().download();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

    public String getBookingNumber() {
        return bookingNumber().getText();
    }

    public double getTransporterAmount() {
        return getDigits(priceBlock().$(By.xpath("./td[1]")).text());
    }

    public double getCommissionsAmount() {
        return getDigits(priceBlock().$(By.xpath("./td[2]")).text());
    }

    public double getResellerAmount() {
        return Double.parseDouble($("#bookingRcCommissionAmount").getAttribute("value"));
    }

    public double getTotalAmount() {
        return getDigits($("#bookingRcTotalAmount").text());
    }

    private double getDigits(String stringToParse) {
        return Double.parseDouble(stringToParse.replaceAll("[A-Z]|\\s", ""));
    }

    public boolean isTextExistInPassengersBlock(String text, int passengerNumber) {
        return $(By.xpath("//table[@class='table table-hover']/tbody/tr[1]/*[contains(.,'" + text + "')]")).isDisplayed();
    }

    public boolean isDownloadButtonVisable() {
        return downloadCouponButtonElement().isDisplayed();
    }

    public boolean isTextExistInBookPriceBlock(String text) {
        return $(By.xpath("//table[@class='table table-hover order'][2]/tbody/*[contains(.,'" + text + "')]")).isDisplayed();
    }

    public boolean isTextExistInBookInfoBlock(String text) {
        return $(By.xpath("//table[@class='table table-hover order'][1]/tbody/*[contains(normalize-space(.),'" + text + "')]")).isDisplayed();
    }

    // ------------------------------ Locator's METHODS -----------------------------

    private SelenideElement bookingNumber() {
        return $("tr:nth-of-type(1)>td");
    }

    private SelenideElement preBookingDetailsButton() {
        return $(".detailsOrder");
    }

    private SelenideElement priceBlock() {
        return $(By.xpath("//table[@class='table table-hover order'][2]/tbody/tr[2]"));
    }

    private SelenideElement downloadCouponButtonElement() {
        return $(By.xpath(".//a[contains(.,'Загрузить контрольные купоны')]"));
    }

    private SelenideElement sendCouponOnEmailElement() {
        return $(".getSendCouponForm");
    }

    @Override
    public boolean onThisPage() {
        return $(".table.table-hover.order").waitUntil(visible, LONG_WAIT_TIME).isDisplayed();
    }

    public void clickOnSendCouponsOnEmailButton() {
        sendCouponOnEmailElement().click();
        sendCouponsOnEmailModalWindow = new SendCouponsOnEmailModalWindow($("#sendControlCouponsModal"));
    }

    public class SendCouponsOnEmailModalWindow extends ModalWindow {

        public SendCouponsOnEmailModalWindow(SelenideElement modalWindowId) {
            super(modalWindowId);
        }

        public SendCouponsOnEmailModalWindow setEmail(String email) {
            $("#send_storage_email").waitUntil(appear, MIDDLE_WAIT_TIME).setValue(email);
            return this;
        }

        public SendCouponsOnEmailModalWindow clickOnSendButtonInModalWindow() {
            $("#send_storage_doSend").click();
            return this;
        }
    }


}
