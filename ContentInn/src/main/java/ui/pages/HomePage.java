package main.java.ui.pages;


import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Condition;


public class HomePage extends CommonPage {
	
	public HomePage() {
		super();
	}
	
	@Override
	public boolean onThisPage() {
		//return (executeJavaScript("location.href").toString().indexOf("home")) > -1 ? true : false;
		return $(".statistic_block").waitUntil(Condition.visible, 10000).isDisplayed();
	}
}
