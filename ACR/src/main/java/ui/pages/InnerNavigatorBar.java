package main.java.ui.pages;

import main.java.ui.pages.refundPage.RefundPage;

/**
 * Created by Andrii.Bakhtiozin on 09.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public interface InnerNavigatorBar {
    JourneySearchPage openSearchPage();

    LoginPage logout();

    OrdersPage openOrdersPage();

    RefundPage openRefundPage();

    BalancePage openBalancePage();

    RulesPage openRulesPage();

    SettingsPage openSettingsPage();
}
