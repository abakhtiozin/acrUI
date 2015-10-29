package main.java.ui.pages.refundDetailsPage;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static main.java.utils.Utils.switchDriverToMainWindow;

/**
 * Created by Andrii.Bakhtiozin on 04.08.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public interface HasRefundDetails {

    default RefundDetailsPage clickOnOpenRefundDetails() {
        $(By.xpath(".//a[contains(.,'Подробности возврата')]")).click();
        switchDriverToMainWindow();
        return page(RefundDetailsPage.class);
    }
}
