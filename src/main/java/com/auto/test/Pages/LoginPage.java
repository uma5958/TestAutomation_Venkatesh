package com.auto.test.Pages;

import static com.auto.test.Core.Action.*;
import static com.auto.test.Core.GC.testData;
import static org.testng.Assert.assertTrue;

import org.testng.asserts.SoftAssert;

public class LoginPage {

	public static void goToLoginPage() throws Exception {
		createNode("Go To Login Page");
		waitForElementToBeDisplayed("Sign in Button", 10);
		assertTrue(verifyElementPresent("Sign in Button"), "Sign in Button is not displaying");
		verifyCurrentUrlWithExpectedUrl(testData.get("Expected Home Page URL"));
		click("Sign in Button");
		waitForElementToBeDisplayed("Email Field", 10);
		assertTrue(verifyElementPresent("Email Field"), "'Email Field' is not displaying");
		verifyCurrentUrlWithExpectedUrl(testData.get("Expected Login Page URL"));
	}

	public static void verifyAllElementsInLoginPage() throws Exception {
		createNode("Verify All Elements In Login Page");
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(verifyElementPresent("Email Field"), "'Email Field' is not displaying");
		softAssert.assertTrue(verifyElementPresent("Password Field"), "'Password Field' is not displaying");
		softAssert.assertTrue(verifyElementPresent("Login Button"), "'Login Button' is not displaying");
		softAssert.assertAll();
	}

	public static void loginToTheApplication() throws Exception {
		createNode("Login To The Application");
		sendKeys("Email Field", testData.get("Email"));
		sendKeys("Password Field", testData.get("Password"));
		click("Login Button");
		waitForElementToBeDisplayed("My Account Header Text", 10);
		assertTrue(verifyElementPresent("My Account Header Text"), "'My Account Header Text' is not displaying");
		verifyCurrentUrlWithExpectedUrl(testData.get("Expected My Account Page URL"));
	}

	public static void logOutFromTheApplication() throws Exception {
		createNode("Log Out From The Application");
		waitForElementToBeDisplayed("Sign Out Button", 10);
		assertTrue(verifyElementPresent("Sign Out Button"), "'Sign Out Button' is not displaying");
		click("Sign Out Button");
		waitForElementToBeDisplayed("Email Field", 10);
		assertTrue(verifyElementPresent("Email Field"),
				"Login Page is not displaying after Sign Out from the application");

	}

}
