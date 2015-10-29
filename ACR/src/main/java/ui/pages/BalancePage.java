package main.java.ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by Andrii.Bakhtiozin on 16.01.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class BalancePage extends InnerPage {
    public BalancePage() {
        super();
    }

    private double getDigits(String stringToParse) {
        return Double.parseDouble(stringToParse.replaceAll("\\s\\D+", ""));
    }

    public boolean isBookingAllowed() {
        return getBookingAllowanceElement().getText().equals("разрешено");
    }

    private SelenideElement getBookingAllowanceElement() {
        return getBalanceRowElement("Оформление заказов");
    }

    public double getCreditLimitValue() {
        return Double.parseDouble(getBalanceRowElement("Кредитный лимит").getText().replaceAll("\\s.+", ""));
    }

    public double getPaymentBalanceValue() {
        return getDigits(getBalanceRowElement("Платежный баланс").getText());
    }

    public double getDebtValue() {
        return getDigits(getBalanceRowElement("Задолженность").getText());
    }

    public double getAvailableCreditValue() {
        return getDigits(getBalanceRowElement("Доступный кредит").getText());
    }

    private SelenideElement getBalanceRowElement(final String columnName) {
        return $(By.xpath(".//*[contains(.,'" + columnName + "')]/*[contains(@class,'span10')]/strong"));
    }

    @Override
    public boolean onThisPage() {
        return getBookingAllowanceElement().isDisplayed();
    }
}
