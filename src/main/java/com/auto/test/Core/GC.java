package com.auto.test.Core;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class GC {
	public static ThreadLocal<WebDriver> threadLocalWebDriver = new ThreadLocal<WebDriver>();
	public static ExtentReports extent;
	public static ThreadLocal<ExtentTest> threadLocalExtentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> threadLocalExtentTestNode = new ThreadLocal<ExtentTest>();
	public static String userDirectory = System.getProperty("user.dir");
	public static String testDataFileName = "Test_Data.xlsx";
	public static String testDataSheet = "Test_Data";
	public static HashMap<String, String> testData = new HashMap<String, String>();
	public static HashMap<String, String> objectRepository = new HashMap<String, String>();
	public static String objectRepositoryFileName = "Object_Repository.xlsx";
	public static String objectRepositorySheet = "ObjectRepository";
	public static final String calendarTimeFormat = "dd/MM/yyyy, EEE";
}
