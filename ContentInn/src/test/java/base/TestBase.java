package test.java.base;

import static com.codeborne.selenide.Selenide.open;

import java.sql.SQLException;

import main.java.model.Country;
import main.java.model.Reseller;
import main.java.model.environment.*;
import main.java.ui.pages.LocationPage;
import main.java.ui.pages.LoginPage;

import org.testng.annotations.*;

import db.DataBase;


@Listeners(TestExecutionListener.class)
public class TestBase extends TestHelper {
	
	
	protected static Reseller reseller;
	protected static String baseUrl;
	protected static LocationPage locationPage;
	protected static LoginPage loginPage;
	
	
	@BeforeSuite
	public void setUp() {
		// Setup and check DataBase connection
		try {
			DataBase.getInstance("jdbc:mysql://172.19.5.222:3306/", "contentinn", "contentinn", "7887");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Set up base URL and reseller credentials		
		baseUrl = Environment.getFronendUrl(EnvType.TEST, EnvMode.DEV);
		if (null == locationPage) {
			locationPage = open(baseUrl, LocationPage.class);
			loginPage = locationPage.setLocation(Country.POLAND);
		}
				
		
	}
	    
    
	
}
