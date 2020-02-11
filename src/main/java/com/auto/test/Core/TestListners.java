package com.auto.test.Core;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.auto.test.Core.Action.encodeFileToBase64Binary;
import static com.auto.test.Core.GC.extent;
import static com.auto.test.Core.GC.threadLocalExtentTest;
import static com.auto.test.Core.GC.threadLocalExtentTestNode;
import static com.auto.test.Core.GC.threadLocalWebDriver;

import java.io.File;

public class TestListners extends TestListenerAdapter {

	public void onStart(ITestContext context) {
	}

	public void onFinish(ITestContext context) {
		extent.flush();
	}

	public void onTestStart(ITestResult result) {
		String className = result.getTestClass().getName();
		String[] array = className.split("[.]");
		className = array[array.length - 1];
		String testName = result.getMethod().getMethodName();
		ExtentTest test = extent.createTest(testName).assignCategory(className);
		threadLocalExtentTest.set(test);
	}

	public void onTestSuccess(ITestResult result) {
		threadLocalExtentTestNode.get().pass("Test case has been executed successfully....!");
	}

	public void onTestFailure(ITestResult result) {
		String failureReason = result.getThrowable().toString();
		failureReason = failureReason.replace("java.lang.AssertionError:", "")
				.replace("expected [true] but found [false]", "").trim();
		File srcFile = ((TakesScreenshot) threadLocalWebDriver.get()).getScreenshotAs(OutputType.FILE);
		String base64String = encodeFileToBase64Binary(srcFile);
		try {
			threadLocalExtentTestNode.get().fail(failureReason,
					MediaEntityBuilder.createScreenCaptureFromBase64String(base64String).build());
		} catch (Exception e) {

		}

	}

}
