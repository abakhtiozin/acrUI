package main.java.steps;


import main.java.steps.reseller.*;
import main.java.steps.system.SystemSteps;

/**
 * Created by Andrii.Bakhtiozin on 20.04.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class StepManager {
    private static StepManager instance;
    private AtBookFormPageSteps atBookFormPageSteps = null;
    private AtBookingDetailsPageSteps atBookingDetailsPageSteps = null;
    private AtJourneySearchPageSteps atJourneySearchPageSteps = null;
    private AtSearchResultPageSteps atSearchResultPageSteps = null;
    private AtLoginPageSteps atLoginPageSteps = null;
    private AtRefundPageSteps atRefundPageSteps = null;
    private AtSettingsPageSteps atSettingsPageSteps = null;
    private AtRefundDetailsPageSteps atRefundDetailsPageSteps = null;
    private SystemSteps systemSteps = null;

    private StepManager() {
    }

    public static StepManager getInstance() {
        if (instance == null) {
            instance = new StepManager();
        }
        return instance;
    }

    public AtBookFormPageSteps getResellerAtBookFormPage() {
        if (atBookFormPageSteps == null) {
            atBookFormPageSteps = new AtBookFormPageSteps();
        }
        return atBookFormPageSteps;
    }

    public AtBookingDetailsPageSteps getResellerAtBookingDetailsPage() {
        if (atBookingDetailsPageSteps == null) {
            atBookingDetailsPageSteps = new AtBookingDetailsPageSteps();
        }
        return atBookingDetailsPageSteps;
    }

    public AtJourneySearchPageSteps getResellerAtJourneySearchPage() {
        if (atJourneySearchPageSteps == null) {
            atJourneySearchPageSteps = new AtJourneySearchPageSteps();
        }
        return atJourneySearchPageSteps;
    }

    public AtSearchResultPageSteps getResellerAtSearchResultPage() {
        if (atSearchResultPageSteps == null) {
            atSearchResultPageSteps = new AtSearchResultPageSteps();
        }
        return atSearchResultPageSteps;
    }

    public AtSettingsPageSteps getResellerAtSettingsPage() {
        if (atSettingsPageSteps == null) {
            atSettingsPageSteps = new AtSettingsPageSteps();
        }
        return atSettingsPageSteps;
    }

    public AtLoginPageSteps getResellerAtLoginPage() {
        if (atLoginPageSteps == null) {
            atLoginPageSteps = new AtLoginPageSteps();
        }
        return atLoginPageSteps;
    }

    public AtRefundPageSteps getResellerAtRefundPage() {
        if (atRefundPageSteps == null) {
            atRefundPageSteps = new AtRefundPageSteps();
        }
        return atRefundPageSteps;
    }

    public SystemSteps getSystemSteps() {
        if (systemSteps == null) {
            systemSteps = new SystemSteps();
        }
        return systemSteps;
    }

    public AtRefundDetailsPageSteps getResellerAtRefundDetailsPage() {
        if (atRefundDetailsPageSteps == null) {
            atRefundDetailsPageSteps = new AtRefundDetailsPageSteps();
        }
        return atRefundDetailsPageSteps;
    }

    public void setUpResellerAtLoginPage() {
        getResellerAtLoginPage().setUpResellerAtPage();
    }

    public void setUpResellerAtJourneySearchPage() {
        getResellerAtJourneySearchPage().setUpResellerAtPage();
    }

    public void setUpResellerAtSearchResultPage() {
        getResellerAtSearchResultPage().setUpResellerAtPage();
    }

    public void setUpResellerAtBookFormPage() {
        getResellerAtBookFormPage().setUpResellerAtPage();
    }

    public void setUpResellerAtSettingsPage() {
        getResellerAtSettingsPage().setUpResellerAtPage();
    }

    public void setUpResellerAtBookingDetailsPage() {
        getResellerAtBookingDetailsPage().setUpResellerAtPage();
    }

    public void setUpResellerAtRefundPage() {
        getResellerAtRefundPage().setUpResellerAtPage();
    }

    public void setUpResellerAtRefundDetailsPage() {
        getResellerAtRefundDetailsPage().setUpResellerAtPage();
    }
}
