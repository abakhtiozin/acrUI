package test.java.ui;

import main.java.model.*;
import main.java.ui.pages.*;
import test.java.base.TestBase;

import org.testng.annotations.*;



public class AuthenticationTest extends TestBase {
	

	@DataProvider(name = "invalidResellers")
	public Object[][] getInvalidResellers() {
		return new Object[][] {
			{new Reseller("", "", "")},
			{new Reseller("NONEXIST", "AI05", "AI05")},
			{new Reseller("AI05", "AI055", "AI05")},
			{new Reseller("AI05", "AI05", "AI055")},
			{new Reseller("NONE", "NONE", "NONE")},
		};
	}	
	
	@Test(testName="invalidAuthenticationTest", enabled=false, dataProvider = "invalidResellers")
	public void invalidAuthenticationTest(Reseller reseller) {		
		loginPage.invalidLogin(reseller);
		assertTrue(loginPage.onThisPage(), Log.compose(Log.ERROR, "Not a LoginPage is oppened currently;"));
	}
	
	
	
	@DataProvider(name = "validResellers")
	public Object[][] getValidResellers() {
		return new Object[][] {
			{new Reseller("AutoRes1", "AutoRes1", "AutoRes1")}
		};
	}
	
	@Test(testName="validAuthenticationTest", enabled=true, dataProvider = "validResellers")
	public void validAuthenticationTest(Reseller reseller) {
		HomePage homePage = loginPage.validLogin(reseller);
		assertTrue(homePage.onThisPage(), Log.compose(Log.ERROR, "Not a HomePage is oppened currently;"));
		homePage.logout();
	}
	
	
	@Test(enabled = false, dataProvider = "validResellers")
	public void forceLoginTest(Reseller reseller) {
		HomePage homePage = loginPage.forceLogin(reseller, true);
		assertTrue(homePage.onThisPage(), Log.compose(Log.ERROR, "Not a HomePage is oppened currently;"));
		homePage.logout();
	}

}
