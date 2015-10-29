package main.java.ui.webElements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class Autocompleter {
	private static final long MAX_WAIT_TIME = 20000;	
	private SelenideElement acl;
	
	
	public Autocompleter(String id) {
		acl = $(By.id(id)).waitUntil(Condition.visible, MAX_WAIT_TIME);		
	}
	
	
	private List<SelenideElement> items(String itemValue) {
		List<SelenideElement> listItems = new ArrayList<SelenideElement>();
		for (SelenideElement li : acl.$$(By.xpath(".//li"))) {
			String liTitle = li.attr("title");
			if (liTitle.toUpperCase().equals(itemValue.toUpperCase())) {
				listItems.add(li);
			}
		}
		// Additional filter. If Collection contains more than 1 element we remove items with airport attribute from it 
		if (listItems.size() > 1) {			
			Iterator<SelenideElement> iListItems = listItems.iterator();
			while (iListItems.hasNext()) {
				SelenideElement li = (SelenideElement) iListItems.next();
				String itemTitle = li.$(By.xpath("./span/span")).attr("title");
				if (itemTitle.indexOf("airport") > 0) {
					iListItems.remove();
				}				
			}	
		}
				
		return listItems;
	}
	
	public Autocompleter selectItem(String itemValue) {
		//autocompleter.$(By.xpath("*//strong[contains(.,'" + itemValue + "')] | //strong[contains(.,'" + itemValue.toUpperCase() + "')]")).waitUntil(Condition.appear, MAX_WAIT_TIME).click();
		//acl.$(By.xpath(".//strong[contains('" + itemValue + "',text())] | .//strong[contains('" + itemValue.toUpperCase() + "',text())]")).waitUntil(Condition.appear, MAX_WAIT_TIME).click();
		//acl.$(By.xpath(".//li[contains('" + itemValue + "', @title)]/descendant::strong[contains('" + itemValue + "', text())] | .//li[contains('" + itemValue.toUpperCase() + "', @title)]/descendant::strong[contains('" + itemValue.toUpperCase() + "', text())]")).waitUntil(Condition.appear, MAX_WAIT_TIME).click();

		List<SelenideElement> listItems = items(itemValue);
		if (listItems.size() > 0) listItems.get(0).$(By.xpath("./descendant::strong")).click();
		return this;
	}
	
	public Autocompleter selectItem(String itemValue, int index) {
		//acl.$(By.xpath(".//li[contains('" + itemValue + "', @title)]/descendant::strong[contains('" + itemValue + "', text())] | .//li[contains('" + itemValue.toUpperCase() + "', @title)]/descendant::strong[contains('" + itemValue.toUpperCase() + "', text())]")).waitUntil(Condition.appear, MAX_WAIT_TIME).click();
		
		List<SelenideElement> listItems = items(itemValue);
		if (listItems.size() > 0 && listItems.size() > index) { 
			listItems.get(index).$(By.xpath("./descendant::strong")).click();
		}
		else {
			throw new IllegalArgumentException("Argument index is out of range (list size: " + listItems.size() + "");
		}
		return this;
	}
	
}
