package main.java.ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Andrii.Bakhtiozin on 16.01.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class SettingsPage extends InnerPage {


    public boolean isUfsSettingsRowExist() {
        return getUfsSettingsRow().isDisplayed();
    }

    public boolean isAcpSettingsRowExist() {
        return getAcpSettingsRow().isDisplayed();
    }

    public boolean isTfSettingsRowExist() {
        return getTfSettingsRow().isDisplayed();
    }

    public boolean isIpSettingsRowExist() {
        return getIpSettingsRow().isDisplayed();
    }

    public SettingsPage clickOnSaveButton() {
        return page(this);
    }

    public boolean isSaveButtonVisable() {
        return saveButtonElement().is(visible);
    }

    private SelenideElement saveButtonElement() {
        return $("reseller_commissions_save");
    }
    private SelenideElement getUfsSettingsRow() {
        return tableSettingsTBody().$(By.xpath("//tr[contains(.,'ЖД билеты') and contains(.,'Российские ЖД')]"));
    }

    private SelenideElement getAcpSettingsRow() {
        return tableSettingsTBody().$(By.xpath("//tr[contains(.,'ЖД билеты') and contains(.,'Международные ЖД')]"));
    }

    private SelenideElement getTfSettingsRow() {
        return tableSettingsTBody().$(By.xpath("//tr[contains(.,'Авиа билеты') and contains(.,'Бюджетные авиакомпании')]"));
    }

    private SelenideElement getIpSettingsRow() {
        return tableSettingsTBody().$(By.xpath("//tr[contains(.,'ЖД билеты') and contains(.,'Казахстанские ЖД')]"));
    }

    /*------------------------------UFS commission block of elements--------------------------*/

    private SelenideElement ufsOrderCommissionTypeElement() {
        return $("#reseller_commissions_orderCommissionRussian_type");
    }

    private SelenideElement ufsOrderCommissionAmountElement() {
        return $("#reseller_commissions_orderCommissionRussian_amount");
    }

    private SelenideElement ufsOrderCommissionAmountMinElement() {
        return $("#reseller_commissions_orderCommissionRussian_amountMin");
    }

    private SelenideElement ufsOrderCommissionAmountMaxElement() {
        return $("#reseller_commissions_orderCommissionRussian_amountMax");
    }

    private SelenideElement ufsReturnCommissionTypeElement() {
        return $("#reseller_commissions_returnCommissionRussian_type");
    }

    private SelenideElement ufsReturnCommissionAmountElement() {
        return $("#reseller_commissions_returnCommissionRussian_amount");
    }

    private SelenideElement ufsReturnCommissionAmountMinElement() {
        return $("#reseller_commissions_returnCommissionRussian_amountMin");
    }

    private SelenideElement ufsReturnCommissionAmountMaxElement() {
        return $("#reseller_commissions_returnCommissionRussian_amountMax");
    }
/*------------------------------ACP commission block of elements--------------------------*/

    private SelenideElement acpOrderCommissionTypeElement() {
        return $("#reseller_commissions_orderCommissionInternational_type");
    }

    private SelenideElement acpOrderCommissionAmountElement() {
        return $("#reseller_commissions_orderCommissionInternational_amount");
    }

    private SelenideElement acpOrderCommissionAmountMinElement() {
        return $("#reseller_commissions_orderCommissionInternational_amountMin");
    }

    private SelenideElement acpOrderCommissionAmountMaxElement() {
        return $("#reseller_commissions_orderCommissionInternational_amountMax");
    }

    private SelenideElement acpReturnCommissionTypeElement() {
        return $("#reseller_commissions_returnCommissionInternational_type");
    }

    private SelenideElement acpReturnCommissionAmountElement() {
        return $("#reseller_commissions_returnCommissionInternational_amount");
    }

    private SelenideElement acpReturnCommissionAmountMinElement() {
        return $("#reseller_commissions_returnCommissionInternational_amountMin");
    }

    private SelenideElement acpReturnCommissionAmountMaxElement() {
        return $("#reseller_commissions_returnCommissionInternational_amountMax");
    }

    /*------------------------------TF commission block of elements--------------------------*/

    private SelenideElement tfOrderCommissionTypeElement() {
        return $("#reseller_commissions_orderCommissionLowcost_type");
    }

    private SelenideElement tfOrderCommissionAmountElement() {
        return $("#reseller_commissions_orderCommissionLowcost_amount");
    }

    private SelenideElement tfOrderCommissionAmountMinElement() {
        return $("#reseller_commissions_orderCommissionLowcost_amountMin");
    }

    private SelenideElement tfOrderCommissionAmountMaxElement() {
        return $("#reseller_commissions_orderCommissionLowcost_amountMax");
    }

    private SelenideElement tfReturnCommissionTypeElement() {
        return $("#reseller_commissions_returnCommissionLowcost_type");
    }

    private SelenideElement tfReturnCommissionAmountElement() {
        return $("#reseller_commissions_returnCommissionLowcost_amount");
    }

    private SelenideElement tfReturnCommissionAmountMinElement() {
        return $("#reseller_commissions_returnCommissionLowcost_amountMin");
    }

    private SelenideElement tfReturnCommissionAmountMaxElement() {
        return $("#reseller_commissions_returnCommissionLowcost_amountMax");
    }
     /*------------------------------IP commission block of elements--------------------------*/

    private SelenideElement ipOrderCommissionTypeElement() {
        return $("#reseller_commissions_orderCommissionKazakh_type");
    }

    private SelenideElement ipOrderCommissionAmountElement() {
        return $("#reseller_commissions_orderCommissionKazakh_amount");
    }

    private SelenideElement ipOrderCommissionAmountMinElement() {
        return $("#reseller_commissions_orderCommissionKazakh_amountMin");
    }

    private SelenideElement ipOrderCommissionAmountMaxElement() {
        return $("#reseller_commissions_orderCommissionKazakh_amountMax");
    }

    private SelenideElement ipReturnCommissionTypeElement() {
        return $("#reseller_commissions_returnCommissionKazakh_type");
    }

    private SelenideElement ipReturnCommissionAmountElement() {
        return $("#reseller_commissions_returnCommissionKazakh_amount");
    }

    private SelenideElement ipReturnCommissionAmountMinElement() {
        return $("#reseller_commissions_returnCommissionKazakh_amountMin");
    }

    private SelenideElement ipReturnCommissionAmountMaxElement() {
        return $("#reseller_commissions_returnCommissionKazakh_amountMax");
    }

    private SelenideElement tableSettingsTBody() {
        return $(By.xpath("//table[@class='table settings']/tbody"));
    }

    @Override
    public boolean onThisPage() {
        return tableSettingsTBody().isDisplayed();
    }
}
