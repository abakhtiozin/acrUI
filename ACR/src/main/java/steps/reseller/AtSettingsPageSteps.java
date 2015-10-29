package main.java.steps.reseller;

import main.java.ApplicationStorage;
import main.java.db.SqlReseller;
import main.java.model.Reseller;
import main.java.ui.pages.SettingsPage;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Map;

/**
 * Created by Andrii.Bakhtiozin on 13.05.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class AtSettingsPageSteps extends AtInnerPageSteps {

    private SettingsPage settingsPage;

    // --------------------------- CONSTRUCTORS ---------------------------

    @Override
    public void shouldBeOnThisPage() {
        settingsPage.onThisPage();
    }

    @Override
    public void setUpResellerAtPage() {
        settingsPage = (SettingsPage) applicationStorage.getCurrentPage();
    }

    @Step
    public void validateSupplierCommissionRows() {
        Reseller reseller = ApplicationStorage.getInstance().getReseller();
        Map<String, String> map = SqlReseller.getUserServicesAccess(reseller.getResellerCode(), reseller.getUserName());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            switch (entry.getKey()) {
                case "enable_russian_railway":
                    isRussianRailwaySettingsDisplayed(entry.getValue());
                    break;
                case "enable_international_railway":
                    isInternationalRailwaySettingsDisplayed(entry.getValue());
                    break;
                case "enable_lowcost_flight":
                    isLccSettingsDisplayed(entry.getValue());
                    break;
                case "enable_kazakh_railway":
                    isIpSettingsDisplayed(entry.getValue());
                    break;
            }
        }
    }

    @Step
    private void isInternationalRailwaySettingsDisplayed(String enable) {
        Assert.assertEquals(Boolean.parseBoolean(enable), settingsPage.isAcpSettingsRowExist()
                , "enable_international_railway = " + enable + " but isDisplayed =" + settingsPage.isAcpSettingsRowExist());
    }

    @Step
    private void isRussianRailwaySettingsDisplayed(String enable) {
        Assert.assertEquals(Boolean.parseBoolean(enable), settingsPage.isUfsSettingsRowExist()
                , "enable_russian_railway = " + enable + " but isDisplayed =" + settingsPage.isUfsSettingsRowExist());
    }

    @Step
    private void isLccSettingsDisplayed(String enable) {
        Assert.assertEquals(Boolean.parseBoolean(enable), settingsPage.isTfSettingsRowExist()
                , "enable_lowcost_flight = " + enable + " but isDisplayed =" + settingsPage.isTfSettingsRowExist());
    }

    @Step
    private void isIpSettingsDisplayed(String enable) {
        Assert.assertEquals(Boolean.parseBoolean(enable), settingsPage.isIpSettingsRowExist()
                , "enable_kazakh_railway = " + enable + " but isDisplayed =" + settingsPage.isIpSettingsRowExist());
    }
}
