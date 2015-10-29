package test.java.ui.tests.other;

import main.java.core.Language;
import main.resources.data.BaseReseller;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import test.java.base.StepInitializer;

import static main.java.model.Supplier.TF;

/**
 * Created by Andrii.Bakhtiozin on 14.08.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class TestDownloadGuide extends StepInitializer {

    @Features("Download Guide")
    @Test
    public void downloadUserManualGuides() {
        atLoginPageSteps.loginAs(BaseReseller.getReseller(TF));
        atJourneySearchPageSteps.downloadUserGuide();
        atJourneySearchPageSteps.changeApplicationLanguageTo(Language.EN);
        atJourneySearchPageSteps.downloadUserGuide();
    }
}
