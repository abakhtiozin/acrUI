package test.java.base;

import main.java.steps.StepManager;
import main.java.steps.reseller.*;
import main.java.steps.system.SystemSteps;

/**
 * Created by Andrii.Bakhtiozin on 23.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class StepInitializer extends TestBase {

    protected AtBookFormPageSteps atBookFormPageSteps;
    protected AtBookingDetailsPageSteps atBookingDetailsPageSteps;
    protected AtJourneySearchPageSteps atJourneySearchPageSteps;
    protected AtSearchResultPageSteps atSearchResultPageSteps;
    protected AtLoginPageSteps atLoginPageSteps;
    protected AtRefundPageSteps atRefundPageSteps;
    protected AtSettingsPageSteps atSettingsPageSteps;
    protected AtRefundDetailsPageSteps atRefundDetailsPageSteps;
    protected SystemSteps systemSteps;

    public StepInitializer() {
        StepManager stepManager = StepManager.getInstance();
        this.systemSteps = stepManager.getSystemSteps();
        this.atLoginPageSteps = stepManager.getResellerAtLoginPage();
        this.atJourneySearchPageSteps = stepManager.getResellerAtJourneySearchPage();
        this.atSearchResultPageSteps = stepManager.getResellerAtSearchResultPage();
        this.atBookFormPageSteps = stepManager.getResellerAtBookFormPage();
        this.atBookingDetailsPageSteps = stepManager.getResellerAtBookingDetailsPage();
        this.atRefundPageSteps = stepManager.getResellerAtRefundPage();
        this.atSettingsPageSteps = stepManager.getResellerAtSettingsPage();
        this.atRefundDetailsPageSteps = stepManager.getResellerAtRefundDetailsPage();
    }
}
