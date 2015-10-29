package main.java.ui.pages.refundDetailsPage;

import com.codeborne.selenide.SelenideElement;
import main.java.ApplicationStorage;
import main.java.utils.Utils;
import org.openqa.selenium.By;

import java.io.FileNotFoundException;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by Andrii.Bakhtiozin on 05.08.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public interface CanDownloadRefundReceipt {
    default RefundDetailsPage clickOnDownloadChargesReceipt() {
        SelenideElement element = $(By.xpath(".//a[contains(.,'Загрузить квитанцию разных сборов')]"));
        element.click();
        try {
            Utils.downloadPdf("charges_receipt", element.download());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return (RefundDetailsPage) ApplicationStorage.getInstance().getCurrentPage();
    }
}
