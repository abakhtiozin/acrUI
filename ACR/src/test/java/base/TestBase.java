package test.java.base;

import main.java.ApplicationStorage;
import main.java.core.ACR;
import main.java.core.EnvironmentController;
import main.java.ui.pages.LoginPage;
import main.java.utils.Cleaner;
import org.testng.annotations.*;

import java.io.File;

import static com.codeborne.selenide.Selenide.open;
import static main.java.utils.PropertiesHelper.getProjectProperties;
import static main.java.utils.Utils.writeEnvironmentXml;

/**
 * Created by Andrii.Bakhtiozin on 05.02.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
@Listeners(AllureListener.class)
public abstract class TestBase {

    private void clean() {
        Cleaner.deleteFolders(new File(System.getProperty("java.io.tmpdir")), "webdriver-profile");
        Cleaner.deleteFilesInFolder(new File(System.getProperty("java.io.tmpdir")), "tmp");
        Cleaner.deleteFilesInFolder(new File(System.getProperty("java.io.tmpdir")), "out");
    }

    @BeforeSuite
    protected void start() {
        new EnvironmentController().setUp();
        ACR.getInstance().setSiteURL("http://" + getProjectProperties("environment.properties").getProperty("url"));
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\chromedriver.exe");
        System.setProperty("browser", "chrome");
    }

    @BeforeClass
    public void setup() {
        open(ACR.getInstance().getSiteURL(), LoginPage.class);
    }

    @AfterClass
    protected void logout() {
        open(ACR.getInstance().getSiteURL() + "/ru/logout", LoginPage.class);
    }

    @AfterSuite
    protected void writeEnvironment() {
        writeEnvironmentXml();
    }

    @AfterMethod
    protected void cleanStorage() {
        ApplicationStorage.getInstance().setPassengers(null);
    }

}
