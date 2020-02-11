package com.auto.test.Core;

import static com.auto.test.Core.Action.getCurrentDateAndTime;
import static com.auto.test.Core.Action.loadTheUrl;
import static com.auto.test.Core.GC.extent;
import static com.auto.test.Core.GC.objectRepository;
import static com.auto.test.Core.GC.objectRepositoryFileName;
import static com.auto.test.Core.GC.objectRepositorySheet;
import static com.auto.test.Core.GC.testData;
import static com.auto.test.Core.GC.testDataFileName;
import static com.auto.test.Core.GC.testDataSheet;
import static com.auto.test.Core.GC.threadLocalWebDriver;
import static com.auto.test.Core.ReadDataFromExcelSheets.getDataFromExcelSheets;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Base {

	@BeforeSuite
	public void beforeSuite() throws Exception {
		initilizeTheExtentReportAndObjectRepository();
	}

	@BeforeMethod
	public void beforeMethod() throws Exception {
		launchChromeBrowser();
		loadTheUrl(testData.get("QA URL"));
	}

	public static void launchChromeBrowser() throws Exception {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//Drivers//chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		WebDriver driver = new ChromeDriver(options);
		threadLocalWebDriver.set(driver);
		threadLocalWebDriver.get().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void afterMethod() throws Exception {
		threadLocalWebDriver.get().quit();
	}

	@AfterSuite
	public static void afterSuite() throws Exception {
	}

	public static void initilizeTheExtentReportAndObjectRepository() throws Exception {
		objectRepository = getDataFromExcelSheets(objectRepositoryFileName, objectRepositorySheet);
		testData = getDataFromExcelSheets(testDataFileName, testDataSheet);
		String timeStamp = getCurrentDateAndTime();
		extent = new ExtentReports();
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "\\ExtentReports\\Reports\\Automation_Report-" + timeStamp + ".html");
		htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "\\ExtentReports\\ConfigFile\\html-config.xml");
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("OS", "Windows 10");
		extent.setSystemInfo("Environment", testData.get("Environment"));
		extent.setSystemInfo("Browser", testData.get("Browser"));
	}

}
