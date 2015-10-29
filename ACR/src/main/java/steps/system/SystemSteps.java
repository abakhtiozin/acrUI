package main.java.steps.system;

import db.SQL;
import main.java.ApplicationStorage;
import main.java.core.ACR;
import main.java.db.SQLBooking;
import main.java.db.SqlReseller;
import main.java.model.*;
import main.java.steps.Steps;
import main.java.ui.pages.AnyPage;
import main.java.ui.pages.BalancePage;
import main.java.ui.pages.JourneySearchPage;
import main.java.ui.pages.LoginPage;
import main.java.ui.pages.searchResultPage.SearchResultPage;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static main.java.utils.Utils.makeScreenshot;

/**
 * Created by Andrii.Bakhtiozin on 22.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class SystemSteps extends Steps {

    private SearchResultPage searchResultPage;

    @Step
    public void setUpBalance() {
        String mainWindow = getWebDriver().getWindowHandle();
        AnyPage anyPage = applicationStorage.getCurrentPage();
        $("body").sendKeys(Keys.CONTROL + "t");
        getWebDriver().getWindowHandles().stream().filter(handle -> !handle.equals(mainWindow)).forEach(handle -> {
            getWebDriver().switchTo().window(handle);
            BalancePage balancePage = open(ACR.getInstance().getSiteURL() + "/ru/balance/show/", BalancePage.class);
            makeScreenshot("Balance page");
            Balance balance = new Balance();
            balance.setAvailableCreditValue(balancePage.getAvailableCreditValue());
            balance.setCreditLimitValue(balancePage.getCreditLimitValue());
            balance.setDebtValue(balancePage.getDebtValue());
            balance.setPaymentBalanceValue(balancePage.getPaymentBalanceValue());
            balance.setIsBookingAllowed(balancePage.isBookingAllowed());
            applicationStorage.getReseller().setBalance(balance);
            getWebDriver().switchTo().window(handle).close();
        });
        applicationStorage.setCurrentPage(anyPage);
        getWebDriver().switchTo().window(mainWindow);
    }

    @Step
    public void checkBalance() {
        String mainWindow = getWebDriver().getWindowHandle();
        AnyPage anyPage = applicationStorage.getCurrentPage();
        $("body").sendKeys(Keys.CONTROL + "t");
        getWebDriver().getWindowHandles().stream().filter(handle -> !handle.equals(mainWindow)).forEach(handle -> {
            getWebDriver().switchTo().window(handle);
            BalancePage balancePage = open(ACR.getInstance().getSiteURL() + "/ru/balance/show/", BalancePage.class);
            makeScreenshot("Balance page");
            Balance newBalance = new Balance();
            newBalance.setAvailableCreditValue(balancePage.getAvailableCreditValue());
            newBalance.setCreditLimitValue(balancePage.getCreditLimitValue());
            newBalance.setDebtValue(balancePage.getDebtValue());
            newBalance.setPaymentBalanceValue(balancePage.getPaymentBalanceValue());
            newBalance.setIsBookingAllowed(balancePage.isBookingAllowed());

            Balance oldBalance = applicationStorage.getReseller().getBalance();
            double resellerBookingDebt;
            double calculatedPaymentBalance = 0;
            double calculatedAvailableCredit = 0;
            if (applicationStorage.getBooking().getBookingStatus().equals(BookingStatus.OK)) {
                if (SQLBooking.getBookingBusinessModel(String.valueOf(applicationStorage.getBooking().getBookingId())).equals("merchant")) {
                    resellerBookingDebt = applicationStorage.getBooking().getAmount().getTransporterAmount() + applicationStorage.getBooking().getAmount().getCommissionsAmount();
                    calculatedPaymentBalance = oldBalance.getPaymentBalanceValue() - resellerBookingDebt;
                    calculatedAvailableCredit = oldBalance.getAvailableCreditValue() - resellerBookingDebt;
                } else {
                    calculatedPaymentBalance = oldBalance.getPaymentBalanceValue();
                    calculatedAvailableCredit = oldBalance.getAvailableCreditValue();
                }
            }
            if (applicationStorage.getBooking().getBookingStatus().equals(BookingStatus.XX)) {
                calculatedPaymentBalance = oldBalance.getPaymentBalanceValue();
                calculatedAvailableCredit = oldBalance.getAvailableCreditValue();
            }
            if (applicationStorage.getBooking().getBookingStatus().equals(BookingStatus.RF)) {
                //TODO
            }

            Assert.assertEquals(newBalance.getPaymentBalanceValue(), calculatedPaymentBalance,
                    "Not equal! " + newBalance.getPaymentBalanceValue() + "(actual) != " + calculatedPaymentBalance + "(expected)");
            Assert.assertEquals(newBalance.getAvailableCreditValue(), calculatedAvailableCredit,
                    "Not equal! " + newBalance.getAvailableCreditValue() + "(actual) != " + calculatedAvailableCredit + "(expected)");
            getWebDriver().switchTo().window(handle).close();
            applicationStorage.getReseller().setBalance(newBalance);
        });
        applicationStorage.setCurrentPage(anyPage);
        getWebDriver().switchTo().window(mainWindow);
    }


    @Step
    public void openJourneySearchPageByUrl() {
        open(ACR.getInstance().getSiteURL() + "/ru/search");
        applicationStorage.setCurrentPage(page(JourneySearchPage.class));
    }

    @Step
    public void tripPriceShouldBeInResellerCurrency() {
        searchResultPage = (SearchResultPage) applicationStorage.getCurrentPage();
        Assert.assertTrue(searchResultPage.getTripCurrency().equals(ApplicationStorage.getInstance().getReseller().getCurrency()),
                "Expected currency " + ApplicationStorage.getInstance().getReseller().getCurrency() + " but got " + searchResultPage.getTripCurrency());
    }

    @Step
    public void orderACRStatusShouldBe(BookingStatus status) {
        String bookingId = String.valueOf(applicationStorage.getBooking().getBookingId());
        Assert.assertTrue(SQL.getBookingStatusByBookingId(bookingId).equals(status.toString()),
                "ACR order status isn't " + status + " actual order status is " + SQL.getBookingStatusByBookingId(bookingId));
    }

    @Step
    public void orderBackendStatusShouldBe(String status) {
        String bookingId = String.valueOf(applicationStorage.getBooking().getBookingId());
        Assert.assertTrue(SQL.getBackedBookingStatusByBookingId(bookingId).equals(status),
                "Backend order status isn't " + status + " actual order status is " + SQL.getBackedBookingStatusByBookingId(bookingId));
    }

    @Step
    public void refundAcrStatusShouldBe(RefundStatus status) {
        String bookingId = String.valueOf(applicationStorage.getBooking().getBookingId());
        Assert.assertTrue(SQL.getRefundStatusByBookingId(bookingId).equals(status.toString()),
                "Acr refund status isn't " + status + " actual order status is " + SQL.getRefundStatusByBookingId(bookingId));
    }

    @Step
    public void logoutThroughURL() {
        open(ACR.getInstance().getSiteURL() + "/ru/logout", LoginPage.class);
    }

    @Step
    public void checkIsValidPrice() {
//        PriceManager priceManager = new PriceManager(searchResultPage.baseSingleTripInfoBlock.getDepartDate(), searchResultPage.baseSingleTripInfoBlock.getArrivalDate(), passengers);
//        double expectedPrice = priceManager.constructPrice(searchResultPage.baseSingleTripInfoBlock.getTransporterName());
//        Assert.assertTrue(searchResultPage.baseSingleTripInfoBlock.getPrice() == expectedPrice, "Expected price: " + expectedPrice + " but got " + searchResultPage.baseSingleTripInfoBlock.getPrice());
    }

    @Step
    public void changeResellerPaperTicketsRights(Reseller reseller, int i) {
        SQL.changeResellerPaperTBookingRights(reseller.getResellerCode(), i);
    }

    @Step
    public void setIpEnableForResellerUser(Reseller reseller, boolean enabled) {
        SqlReseller.setIpEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), enabled);
    }

    @Step
    public void setTfEnableForResellerUser(Reseller reseller, boolean enabled) {
        SqlReseller.setTfEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), enabled);
    }

    @Step
    public void setAcpEnableForResellerUser(Reseller reseller, boolean enabled) {
        SqlReseller.setAcpEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), enabled);
    }

    @Step
    public void setUfsEnableForResellerUser(Reseller reseller, boolean enabled) {
        SqlReseller.setUfsEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), enabled);
    }

    @Step
    public void setSupplierOnlyEnableForResellerUser(Reseller reseller, Supplier supplier) {
        switch (supplier) {
            case UFS:
                SqlReseller.setUfsEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), true);
                SqlReseller.setTfEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), false);
                SqlReseller.setAcpEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), false);
                SqlReseller.setIpEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), false);
                break;
            case TF:
                SqlReseller.setUfsEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), false);
                SqlReseller.setTfEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), true);
                SqlReseller.setAcpEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), false);
                SqlReseller.setIpEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), false);
                break;
            case ACP:
                SqlReseller.setUfsEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), false);
                SqlReseller.setTfEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), false);
                SqlReseller.setAcpEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), true);
                SqlReseller.setIpEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), false);
                break;
            case IP:
                SqlReseller.setUfsEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), false);
                SqlReseller.setTfEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), false);
                SqlReseller.setAcpEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), false);
                SqlReseller.setIpEnableForResellerUser(reseller.getResellerCode(), reseller.getUserName(), true);
                break;
        }
    }

    @Step
    public void shouldBookingBeWithBedClothing(boolean expectedResult) {
        String result = SQLBooking.isBeddingInBooking(String.valueOf(ApplicationStorage.getInstance().getBooking().getBookingId()));
        if ((result != null) && (!result.equals("null"))) {
            boolean actual = result.equals("1");
            Assert.assertTrue(actual == expectedResult, "Expected result withBedClothing = '" + expectedResult + "' but got withBedClothing =" + "'" + actual + "'");
        }
    }

    @Step
    public void shouldBeRightTicketType(TicketType ticketType) {
        String ticketTypeActual = SQLBooking.getTicketTypeByBookingId(String.valueOf(ApplicationStorage.getInstance().getBooking().getBookingId()));
        Assert.assertTrue(ticketType.toString().equals(ticketTypeActual));
    }

    @Step
    public void updateBookingConfirmationTime() {
        SQLBooking.updateBookingConfirmationTime(String.valueOf(applicationStorage.getBooking().getBookingId()));
    }

}
