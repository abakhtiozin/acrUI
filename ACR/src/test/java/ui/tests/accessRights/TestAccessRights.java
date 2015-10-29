package test.java.ui.tests.accessRights;

import main.java.model.Journey;
import main.java.model.Reseller;
import main.java.model.Trip;
import main.java.model.passenger.Passenger;
import main.resources.data.BaseReseller;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.java.base.StepInitializer;

import static main.java.model.Supplier.*;
import static main.java.utils.DateTimeHelper.currentDatePlusNDays;

/**
 * Created by Andrii.Bakhtiozin on 08.05.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class TestAccessRights extends StepInitializer {

    @Test
    public void resellerWithoutAccessCantBuyPaperTickets() {
        systemSteps.changeResellerPaperTicketsRights(BaseReseller.getReseller(ACP), 0);
        atLoginPageSteps.loginAs(BaseReseller.getReseller(ACP));
        atJourneySearchPageSteps.openSearchPage();
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.addPassengerToJourney(new Passenger("12.12.1990"));
        atJourneySearchPageSteps.fillInJourneyData(new Journey("MUC", "BER", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("DB").withCarriageType("Standart").withTariffType(new String[]{"TCV PAPER TICKET ONLY"}));
        atSearchResultPageSteps.shouldSeeDisabledOrderButton();
    }

    @Test
    public void resellerWithAccessCanBuyPaperTickets() {
        systemSteps.changeResellerPaperTicketsRights(BaseReseller.getReseller(ACP), 1);
        atLoginPageSteps.loginAs(BaseReseller.getReseller(ACP));
        atJourneySearchPageSteps.openSearchPage();
        atJourneySearchPageSteps.clearLastSearchData();
        atJourneySearchPageSteps.addPassengerToJourney(new Passenger("12.12.1990"));
        atJourneySearchPageSteps.fillInJourneyData(new Journey("OSL", "STO", currentDatePlusNDays(25)));
        atJourneySearchPageSteps.searchJourney();
        atSearchResultPageSteps.findTripByDesireOptions(new Trip().withTransporterName("SJ"));
        atSearchResultPageSteps.chooseTripAndGoToBookForm();
        atBookFormPageSteps.shouldBeOnThisPage();
    }

    @Test
    public void resellerWithWSAccessCantLoginToB2b() {
        atLoginPageSteps.invalidLoginAs(new Reseller("and-prt-ua", "andrew", "1234"));
    }

    @Test
    public void resellerWithAcpOnlyCanSeeAcpSettingsRowOnly() {
        systemSteps.setSupplierOnlyEnableForResellerUser(BaseReseller.getReseller(ACP), ACP);
        atLoginPageSteps.loginAs(BaseReseller.getReseller(ACP));
        atJourneySearchPageSteps.openSettingsPage();
        atSettingsPageSteps.shouldBeOnThisPage();
        atSettingsPageSteps.validateSupplierCommissionRows();
    }

    @Test
    public void resellerWithTfOnlyCanSeeTfSettingsRowOnly() {
        systemSteps.setSupplierOnlyEnableForResellerUser(BaseReseller.getReseller(TF), TF);
        atLoginPageSteps.loginAs(BaseReseller.getReseller(TF));
        atJourneySearchPageSteps.openSettingsPage();
        atSettingsPageSteps.shouldBeOnThisPage();
        atSettingsPageSteps.validateSupplierCommissionRows();
    }

    @Test
    public void resellerWithUfsOnlyCanSeeUfsSettingsRowOnly() {
        systemSteps.setSupplierOnlyEnableForResellerUser(BaseReseller.getReseller(UFS), UFS);
        atLoginPageSteps.loginAs(BaseReseller.getReseller(UFS));
        atJourneySearchPageSteps.openSettingsPage();
        atSettingsPageSteps.shouldBeOnThisPage();
        atSettingsPageSteps.validateSupplierCommissionRows();
    }

    @Test
    public void resellerWithIpOnlyCanSeeIpSettingsRowOnly() {
        systemSteps.setSupplierOnlyEnableForResellerUser(BaseReseller.getReseller(IP), IP);
        atLoginPageSteps.loginAs(BaseReseller.getReseller(IP));
        atJourneySearchPageSteps.openSettingsPage();
        atSettingsPageSteps.shouldBeOnThisPage();
        atSettingsPageSteps.validateSupplierCommissionRows();
    }

    @Test
    public void resellerWithIpAndAcpCanSeeIpAndAcpSettingsRowOnly() {
        systemSteps.setAcpEnableForResellerUser(BaseReseller.getReseller(IP), true);
        systemSteps.setIpEnableForResellerUser(BaseReseller.getReseller(IP), true);
        systemSteps.setTfEnableForResellerUser(BaseReseller.getReseller(IP), false);
        systemSteps.setUfsEnableForResellerUser(BaseReseller.getReseller(IP), false);
        atLoginPageSteps.loginAs(BaseReseller.getReseller(IP));
        atJourneySearchPageSteps.openSettingsPage();
        atSettingsPageSteps.shouldBeOnThisPage();
        atSettingsPageSteps.validateSupplierCommissionRows();
    }

    @Test
    public void resellerWithTfAndAcpCanSeeTfAndAcpSettingsRowOnly() {
        systemSteps.setAcpEnableForResellerUser(BaseReseller.getReseller(TF), true);
        systemSteps.setIpEnableForResellerUser(BaseReseller.getReseller(TF), false);
        systemSteps.setTfEnableForResellerUser(BaseReseller.getReseller(TF), true);
        systemSteps.setUfsEnableForResellerUser(BaseReseller.getReseller(TF), false);
        atLoginPageSteps.loginAs(BaseReseller.getReseller(TF));
        atJourneySearchPageSteps.openSettingsPage();
        atSettingsPageSteps.shouldBeOnThisPage();
        atSettingsPageSteps.validateSupplierCommissionRows();
    }

    @Test
    public void resellerWithTfAndUfsCanSeeTfAndUfsSettingsRowOnly() {
        systemSteps.setAcpEnableForResellerUser(BaseReseller.getReseller(TF), false);
        systemSteps.setIpEnableForResellerUser(BaseReseller.getReseller(TF), false);
        systemSteps.setTfEnableForResellerUser(BaseReseller.getReseller(TF), true);
        systemSteps.setUfsEnableForResellerUser(BaseReseller.getReseller(TF), true);
        atLoginPageSteps.loginAs(BaseReseller.getReseller(TF));
        atJourneySearchPageSteps.openSettingsPage();
        atSettingsPageSteps.shouldBeOnThisPage();
        atSettingsPageSteps.validateSupplierCommissionRows();
    }

    @Test
    public void resellerWithAcpAndUfsCanSeeAcpAndUfsSettingsRowOnly() {
        systemSteps.setAcpEnableForResellerUser(BaseReseller.getReseller(ACP), true);
        systemSteps.setIpEnableForResellerUser(BaseReseller.getReseller(ACP), false);
        systemSteps.setTfEnableForResellerUser(BaseReseller.getReseller(ACP), false);
        systemSteps.setUfsEnableForResellerUser(BaseReseller.getReseller(ACP), true);
        atLoginPageSteps.loginAs(BaseReseller.getReseller(ACP));
        atJourneySearchPageSteps.openSettingsPage();
        atSettingsPageSteps.shouldBeOnThisPage();
        atSettingsPageSteps.validateSupplierCommissionRows();
    }

    @Test
    public void resellerWithAcpAndUfsAndTfCanSeeAcpAndUfsAndTfSettingsRowOnly() {
        systemSteps.setAcpEnableForResellerUser(BaseReseller.getReseller(ACP), true);
        systemSteps.setIpEnableForResellerUser(BaseReseller.getReseller(ACP), false);
        systemSteps.setTfEnableForResellerUser(BaseReseller.getReseller(ACP), true);
        systemSteps.setUfsEnableForResellerUser(BaseReseller.getReseller(ACP), true);
        atLoginPageSteps.loginAs(BaseReseller.getReseller(ACP));
        atJourneySearchPageSteps.openSettingsPage();
        atSettingsPageSteps.shouldBeOnThisPage();
        atSettingsPageSteps.validateSupplierCommissionRows();
    }

    @Test
    public void linksCheck() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(ACP));
        atJourneySearchPageSteps.openSettingsPage();
        atSettingsPageSteps.shouldBeOnThisPage();
        atSettingsPageSteps.openSearchPage();
        atJourneySearchPageSteps.shouldBeOnThisPage();
    }

    @BeforeClass
    public void setUp() {
        systemSteps.setSupplierOnlyEnableForResellerUser(BaseReseller.getReseller(ACP), ACP);
        systemSteps.setSupplierOnlyEnableForResellerUser(BaseReseller.getReseller(TF), TF);
        systemSteps.setSupplierOnlyEnableForResellerUser(BaseReseller.getReseller(IP), IP);
        systemSteps.setSupplierOnlyEnableForResellerUser(BaseReseller.getReseller(UFS), UFS);
    }

    @AfterMethod
    public void sysLogout() {
        systemSteps.logoutThroughURL();
    }

    @AfterClass
    private void cleanUp() {
        systemSteps.changeResellerPaperTicketsRights(BaseReseller.getReseller(ACP), 1);
        systemSteps.setSupplierOnlyEnableForResellerUser(BaseReseller.getReseller(ACP), ACP);
        systemSteps.setSupplierOnlyEnableForResellerUser(BaseReseller.getReseller(TF), TF);
        systemSteps.setSupplierOnlyEnableForResellerUser(BaseReseller.getReseller(IP), IP);
        systemSteps.setSupplierOnlyEnableForResellerUser(BaseReseller.getReseller(UFS), UFS);
    }
}
