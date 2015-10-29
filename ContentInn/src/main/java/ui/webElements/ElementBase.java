package main.java.ui.webElements;

import com.codeborne.selenide.SelenideElement;

public class ElementBase {
	
	protected void setValue(SelenideElement locator, String value) {
		locator.sendKeys(value);
	}	
	protected void resetValue(SelenideElement locator, String value) {
		locator.clear();
		setValue(locator, value);
	}
	protected void setCheckbox(SelenideElement locator, boolean checked) {
		if (checked) {
			if (!locator.isSelected()) locator.click();
		}
		else {
			if (locator.isSelected()) locator.click();
		}
	}
	
}
