package main.java.util;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.yandex.qatools.allure.annotations.Attachment;

public class LogUtils {
	
	@Attachment(value = "{0}", type = "image/png")
    public static byte[] takeScreenshot(String name) {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
	
	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String attachName, String message) {
	    return message;
	}
	
	@Attachment(value = "{0}", type = "text/html")
	public static String takePageSource(String name) {
		return getWebDriver().getPageSource();
	}
	
	
	
}
