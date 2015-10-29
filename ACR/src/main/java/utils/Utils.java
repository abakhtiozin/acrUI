package main.java.utils;

import main.java.ApplicationStorage;
import main.java.db.SqlReseller;
import main.java.model.Booking;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.yandex.qatools.allure.annotations.Attachment;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static main.java.utils.PropertiesHelper.PROJECT_PROPERTIES_PATH;
import static main.java.utils.PropertiesHelper.getProjectProperties;

/**
 * Created by Andrii.Bakhtiozin on 21.05.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class Utils {
    private Utils() {
    }

    private static HashMap<String, String> getHashMapLastResellerLog(String testStartTime) {
        return SqlReseller.getLastResellerCommunicationLog(ApplicationStorage.getInstance().getReseller(), testStartTime);
    }

    @Attachment(value = "{0}", type = "image/png")
    public static byte[] makeScreenshot(String name) {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}")
    public static byte[] response(String name, String testStartTime) {
        byte[] result;
        result = SqlReseller.getLastResellerResponseLog(ApplicationStorage.getInstance().getReseller(), testStartTime);
        if (result != null) {
            return result;
        } else return "nothing to log".getBytes();
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static byte[] addBookingId(String name) {
        return " ".getBytes();
    }

    public static int getBookingId() {
        Booking booking = ApplicationStorage.getInstance().getBooking();
        if (booking != null) {
            int id = booking.getBookingId();
            if (id != 0) {
                return id;
            }
        }
        return 0;
    }

    @Attachment(value = "{0}", type = "application/pdf")
    public static byte[] downloadPdf(String name, File file) {
        try {
            return Files.readAllBytes(Paths.get(file.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Attachment(value = "{0}")
    public static byte[] request(String name, String testStartTime) {
        byte[] result;
        result = SqlReseller.getLastResellerRequestLog(ApplicationStorage.getInstance().getReseller(), testStartTime);
        if (result != null) {
            return result;
        }
        return "nothing to log".getBytes();
    }

    @Attachment(value = "{0}")
    public static String shortInfo(String name, String testStartTime) {
        String newline = System.getProperty("line.separator");
        Map<String, String> map = getHashMapLastResellerLog(testStartTime);
        if (map != null) {
            return "logId: " + (map.get("logId") != null ? map.get("logId") : "") + newline +
                    "url: " + (map.get("url") != null ? map.get("url") : "") + newline +
                    "requestType: " + (map.get("requestType") != null ? map.get("requestType") : "") + newline +
                    "executionTime: " + (map.get("executionTime") != null ? map.get("executionTime") : "") + " sec";
        }
        return "nothing to log";
    }

    public static void switchDriverToMainWindow() {
        String currentWindow = getWebDriver().getWindowHandle();
        Set<String> handles = getWebDriver().getWindowHandles();
        handles.stream().filter(handle -> !handle.equals(currentWindow)).forEach(handle -> {
            getWebDriver().switchTo().window(handle);
            makeScreenshot("window");
            getWebDriver().switchTo().window(handle).close();
        });
        getWebDriver().switchTo().window(currentWindow);
    }

    public static void writeEnvironmentXml() {
        Properties properties = getProjectProperties("environment.properties");
        Document dom;
        Element e;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.newDocument();

            Element rootEle = dom.createElement("qa:environment");
            rootEle.setAttribute("xmlns:qa", "urn:model.commons.qatools.yandex.ru");

            e = dom.createElement("name");
            e.appendChild(dom.createTextNode("Allure"));
            rootEle.appendChild(e);

            e = dom.createElement("parameter");
            Element name = dom.createElement("name");
            name.appendChild(dom.createTextNode("Browser"));
            e.appendChild(name);
            Element key = dom.createElement("key");
            key.appendChild(dom.createTextNode("browser"));
            e.appendChild(key);
            Element value = dom.createElement("value");
            value.appendChild(dom.createTextNode(properties.getProperty("browser")));
            e.appendChild(value);
            rootEle.appendChild(e);

            e = dom.createElement("parameter");
            name = dom.createElement("name");
            name.appendChild(dom.createTextNode("Test enviroment"));
            e.appendChild(name);
            key = dom.createElement("key");
            key.appendChild(dom.createTextNode("url"));
            e.appendChild(key);
            value = dom.createElement("value");
            value.appendChild(dom.createTextNode(properties.getProperty("url")));
            e.appendChild(value);
            rootEle.appendChild(e);

            dom.appendChild(rootEle);

            try {
                TransformerFactory.newInstance().newTransformer().transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream(new File(System.getProperty("user.dir") + "\\target\\allure-results\\environment.xml"))));
            } catch (TransformerException | IOException te) {
                System.out.println(te.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
        try {
            File file = new File(System.getProperty("user.dir") + "\\target\\allure-results\\properties.xml");
            if (file.exists()) {
                file.delete();
            }
            Files.copy(new File(PROJECT_PROPERTIES_PATH + "environment.properties").toPath(), file.toPath());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
