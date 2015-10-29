package main.java.ui.pages.refundDetailsPage;

import com.codeborne.selenide.SelenideElement;
import main.java.ui.pages.InnerPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static main.java.utils.Utils.switchDriverToMainWindow;

/**
 * Created by Andrii.Bakhtiozin on 03.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class RefundDetailsPage extends InnerPage {
    public RefundDetailsPage() {
        super();
    }

    private SelenideElement approveRefundButton() {
        return $(".confirmRefund").waitUntil(appear, MIDDLE_WAIT_TIME);
    }

    public RefundDetailsPage clickOnOpenClaim() {

        $(".refundBlank").click();
        switchDriverToMainWindow();
        return this;
    }

    public RefundDetailsPage confirmRefund() {
        approveRefundButton().click();
        return this;
    }

    public boolean isRefundButtonDisabled() {
        return approveRefundButton().getAttribute("data-original-title").equals("Перед подверждением возврата необходимо распечатать Заявление на возврат");
    }

    public RefundDetailsPage checkRefund() {
        clickOnCheckRefundButton();
        return this;
    }

    private void clickOnCheckRefundButton() {
        $(".alert.alert-block").waitUntil(appear, WAIT_TIME).shouldHave(text("Требуется проверка статуса.")).click();
        shouldBeHiddenLoader();
        checkRefundButtonElement().shouldBe(visible).click();
    }

    private SelenideElement checkRefundButtonElement() {
        return $("#checkRefund");
    }

    public boolean checkRefundButtonShouldNotBeVisible() {
        return checkRefundButtonElement().isDisplayed();
    }

    public boolean shouldBeHiddenLoader() {
        $("#checkLoader").waitUntil(hidden, WAIT_TIME);
        return true;
    }

    @Override
    public boolean onThisPage() {
        return $(By.xpath(".//legend[contains(.,'Возврат')]")).isDisplayed();
    }

}
