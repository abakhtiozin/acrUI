package ui.pages;

import static com.codeborne.selenide.Selenide.*;

/**
 * Created by AA on 07.01.2015.
 */
public abstract class InnerPage implements Page {

//добавить все ссылки хедера и логаут
//$(".navbar-inner")

    public JourneySearchPage openSearchPage() {
        $("a[href*='/ru/search']").followLink();
        return page(JourneySearchPage.class);
    }

    public LoginPage logout() {
        $(".nav.pull-right.userBlock>li>a").click();
        return page(LoginPage.class);
    }

    public OrdersPage openOrdersPage() {
        $("a[href*='/ru/booking/list']").followLink();
        return page(OrdersPage.class);
    }

    public RefundPage openRefundPage() {
        $("a[href*='/ru/refund/form/']").followLink();
        return page(RefundPage.class);
    }

    public BalancePage openBalancePage() {
        $("a[href*='/ru/balance/show/']").followLink();
        return page(BalancePage.class);
    }

    public RulesPage openRulesPage() {
        $("a[href*='/ru/rules_list/']").followLink();
        return page(RulesPage.class);
    }

    public SettingsPage openSettingPage() {
        $("a[href*='/ru/settings/']").followLink();
        return page(SettingsPage.class);
    }
}
