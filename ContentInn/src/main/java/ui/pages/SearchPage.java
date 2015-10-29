package main.java.ui.pages;

import static com.codeborne.selenide.Selenide.*;
import main.java.ui.webElements.SearchForm;

import org.openqa.selenium.By;

public class SearchPage extends CommonPage {
	
	public SearchPage() {
		super();
	}
	
	public SearchForm searchForm()  {
		return onThisPage() ? page(SearchForm.class) : getSearchForm();
	}
	
	
	@SuppressWarnings("finally")
	@Override
	public boolean onThisPage() {
		boolean isThisPage = false;
		try {						
			isThisPage = $(By.id(SearchForm.containerId())).isDisplayed() ? true : false;			
		}
		catch (org.openqa.selenium.NoSuchElementException e) {
			isThisPage = false;
		}
		finally {
			return isThisPage;
		}
	}
	
}
