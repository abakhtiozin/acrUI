package main.java.steps.reseller;

import main.java.core.Language;
import main.java.ui.pages.*;
import main.java.ui.pages.refundPage.RefundPage;
import main.java.utils.Utils;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Andrii.Bakhtiozin on 09.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public abstract class AtInnerPageSteps extends ResellerSteps implements InnerNavigatorBar {
    private InnerPage getInnerPage() {
        return ((InnerPage) applicationStorage.getCurrentPage());
    }

    @Step
    @Override
    public JourneySearchPage openSearchPage() {
        getInnerPage().openSearchPage();
        stepManager.setUpResellerAtJourneySearchPage();
        return page(JourneySearchPage.class);
    }

    @Step
    @Override
    public LoginPage logout() {
        getInnerPage().logout();
        stepManager.setUpResellerAtLoginPage();
        return page(LoginPage.class);
    }

    @Step
    @Override
    public OrdersPage openOrdersPage() {
        getInnerPage().openOrdersPage();
        //TODO
        return null;
    }

    @Step
    @Override
    public RefundPage openRefundPage() {
        getInnerPage().openRefundPage();
        stepManager.setUpResellerAtRefundPage();
        return page(RefundPage.class);
    }

    @Step
    @Override
    public BalancePage openBalancePage() {
        getInnerPage().openBalancePage();
        return page(BalancePage.class);
    }

    @Step
    @Override
    public RulesPage openRulesPage() {
        getInnerPage().openRulesPage();
        return page(RulesPage.class);
    }

    @Step
    @Override
    public SettingsPage openSettingsPage() {
        getInnerPage().openSettingsPage();
        stepManager.setUpResellerAtSettingsPage();
        return page(SettingsPage.class);
    }

    @Step
    public void downloadUserGuide() {
        Utils.downloadPdf("userguide", getInnerPage().downloadUserGuide());
    }

    @Step
    public void changeApplicationLanguageTo(Language language) {
        getInnerPage().changeLanguageTo(language);
    }
}
