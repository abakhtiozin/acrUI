package main.java.ui.pages;

import main.java.core.Language;
import main.java.ui.pages.refundPage.RefundPage;
import main.java.utils.FileDownloaderHelper;
import org.openqa.selenium.By;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Andrii.Bakhtiozin on 07.01.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public abstract class InnerPage extends AnyPage implements InnerNavigatorBar {
    public InnerPage() {
        super();
    }

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

    public SettingsPage openSettingsPage() {
        $("a[href*='/ru/settings/']").followLink();
        return page(SettingsPage.class);
    }

    public File downloadUserGuide() {
        File file = null;
        try {
            String url = $(By.xpath("//*[contains(@href,'userguide-')]")).getAttribute("href");
            url = url.replaceAll("\\?(\\S*)", "");
            file = new FileDownloaderHelper().download(url);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public InnerPage changeLanguageTo(Language language) {
        $(By.xpath("//*[contains(@href,'/" + language.toString().toLowerCase() + "/')]/*[@class='flag']/..")).click();
        return this;
    }

}
