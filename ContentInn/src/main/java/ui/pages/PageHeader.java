package main.java.ui.pages;


import main.java.model.*;


public interface PageHeader {

	public LoginPage logout();
	
	public CommonPage chooseCulture(Locale curLocale);
	
	public Locale getChosenCulture();
		
}