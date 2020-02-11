package com.auto.test.Core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.github.javafaker.Faker;
import static org.testng.Assert.assertTrue;
import static com.auto.test.Core.GC.calendarTimeFormat;
import static com.auto.test.Core.GC.objectRepository;
import static com.auto.test.Core.GC.threadLocalExtentTest;
import static com.auto.test.Core.GC.threadLocalExtentTestNode;
import static com.auto.test.Core.GC.threadLocalWebDriver;
import static java.util.Calendar.*;

public class Action {

	public static int getDifferenceInYearsBetweenTwoDates(String fromDate, String pastDate) throws Exception {
		int differenceInYears = 0;
		String[] fromDateArray = fromDate.split("[-]");
		String[] toDateArray = pastDate.split("[-]");
		int fromYear = Integer.parseInt(fromDateArray[0]);
		int fromMonth = Integer.parseInt(fromDateArray[1]);
		int fromDay = Integer.parseInt(fromDateArray[2]);
		int pastYear = Integer.parseInt(toDateArray[0]);
		int pastMonth = Integer.parseInt(toDateArray[1]);
		int pastDay = Integer.parseInt(toDateArray[2]);
		if (fromMonth == pastMonth) {
			if (fromDay <= pastDay)
				differenceInYears--;
		} else if (fromMonth < pastMonth) {
			differenceInYears--;
		}
		differenceInYears = differenceInYears + fromYear - pastYear;
		return differenceInYears;
	}

	public static HashSet<Integer> getRandomNumbersInGivenRange(int randomNumbersSizeToSelect, int min, int max)
			throws Exception {
		HashSet<Integer> randomNumbers = new HashSet<Integer>();
		int i = 0;
		while (true) {
			int randomNumber = getRandomIntegerValue(min, max);
			randomNumbers.add(randomNumber);
			if (randomNumbers.size() == randomNumbersSizeToSelect)
				break;
			i++;
			if (i > 10000)
				break;
		}
		return randomNumbers;

	}

	/**
	 * 
	 * @param epoch
	 * @return
	 * @throws Exception
	 */
	public static String epochToDateConvertor(long epoch, String requiredFormat) throws Exception {
		Date date = new Date(epoch);
		SimpleDateFormat sdf = new SimpleDateFormat(requiredFormat);
		return sdf.format(date);
	}

	/**
	 * 
	 * @param dateParam
	 * @param actualFormat
	 * @return
	 * @throws Exception
	 */
	public static long getEpochTime(String dateParam, String actualFormat) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(actualFormat);
		Date date = df.parse(dateParam);
		long epoch = date.getTime();
		return epoch;
	}

	/**
	 * 
	 * @param dateParam
	 * @param actualFormat
	 * @param requiredFormat
	 * @return
	 * @throws Exception
	 */
	public static String getDateInRequiredFormat(String dateParam, String actualFormat, String requiredFormat)
			throws Exception {
		Date date = new SimpleDateFormat(actualFormat).parse(dateParam);
		SimpleDateFormat formatter = new SimpleDateFormat(requiredFormat);
		return formatter.format(date);
	}

	/**
	 * 
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static String getNextHourTime(String time) throws Exception {
		Date date = new SimpleDateFormat("h:mm a").parse(time);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, 1);
		date = c.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
		return formatter.format(date);

	}

	public static String getDifferenceBetweenTwoTimes(String fromTime, String tillTime) throws Exception {
		String timeDifference = "";
		Date d1 = new SimpleDateFormat("h:mm a").parse(fromTime);
		Date d2 = new SimpleDateFormat("h:mm a").parse(tillTime);
		long differenceInMilliSeconds = d2.getTime() - d1.getTime();
		long differenceInMinutes = differenceInMilliSeconds / (60 * 1000);
		long hours = differenceInMinutes / 60;
		long minutes = differenceInMinutes - hours * 60;
		if (hours == 0)
			timeDifference = String.valueOf(minutes) + " min";
		else if (minutes == 0)
			timeDifference = String.valueOf(hours) + " hour";
		else
			timeDifference = String.valueOf(hours) + " hour" + " " + String.valueOf(minutes) + " min";
		return timeDifference;
	}

	public static String getRandomTimeOfTheDay() throws Exception {
		String minute = "";
		String period = "";
		int randomHour = getRandomIntegerValue(1, 12);
		int randomMinute = getRandomIntegerValue(1, 59);
		int randomPeriod = getRandomIntegerValue(0, 1);
		if (String.valueOf(randomMinute).length() == 1)
			minute = "0" + randomMinute;
		else
			minute = String.valueOf(randomMinute);
		if (randomPeriod == 0)
			period = "AM";
		else
			period = "PM";
		return String.valueOf(randomHour) + ":" + minute + " " + period;
	}

	public static String getNextValidTimeOfTheDay(String time) throws Exception {
		String minutes = "";
		int currentHour = Integer.parseInt(time.split(":")[0]);
		int currentMinutes = Integer.parseInt(time.split(":")[1].split(" ")[0]);
		if (time.contains("PM")) {
			if (currentHour == 12)
				currentHour = 11;
			int randomNextHour = getRandomIntegerValue(currentHour, 11);
			int randomNextMinute = getRandomIntegerValue(currentMinutes + 1, 59);
			minutes = String.valueOf(randomNextMinute);
			if (String.valueOf(randomNextMinute).length() == 1)
				minutes = "0" + String.valueOf(randomNextMinute);
			time = randomNextHour + ":" + minutes + " PM";
		}
		if (time.contains("AM")) {
			int randomNextHour = getRandomIntegerValue(currentHour, 24);
			int randomNextMinute = getRandomIntegerValue(currentMinutes + 1, 59);
			minutes = String.valueOf(randomNextMinute);
			if (String.valueOf(randomNextMinute).length() == 1)
				minutes = "0" + String.valueOf(randomNextMinute);
			time = randomNextHour + ":" + minutes + " AM";
			if (randomNextHour > 12) {
				randomNextHour = randomNextHour - 12;
				time = randomNextHour + ":" + minutes + " PM";
			} else if (randomNextHour == 12) {
				time = randomNextHour + ":" + minutes + " PM";
			}
		}
		return time;
	}

	public static String GET_Method(String Url, String cookie) {
		String result = "";
		try {
			URL url = new URL(Url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0");
			con.setRequestProperty("Cookie", cookie);
			con.setConnectTimeout(60000);
			con.setReadTimeout(60000);
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
				StringBuffer response = new StringBuffer();
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				result = response.toString();
			} else {
				System.out.println("Something Wrong In API....!");
			}
		} catch (Exception e) {
			System.out.println("Exception Occured While Getting The Response In GET_Method....!");
		}
		return result;
	}

	public static String getCookieValue(String cookieName) throws Exception {
		String cookie = threadLocalWebDriver.get().manage().getCookieNamed(cookieName).toString();
		return cookie;
	}

	public static String getTime(int hour, int minutes) throws Exception {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, hour);
		c.add(Calendar.MINUTE, minutes);
		date = c.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
		return formatter.format(date);
	}

	public static Date convertStringToDate(String dateString, String dateFormat) throws Exception {
		DateFormat format = new SimpleDateFormat(dateFormat);
		Date date = format.parse(dateString);
		return date;
	}

	public static ArrayList<String> getListOfDatesBetweenTheGivenDatesWithRepeatsEveryYearCondition(String startDate,
			String endDate, String repeatsEveryMonth) throws Exception {
		ArrayList<String> listOfDates = new ArrayList<String>();
		listOfDates.add(startDate);
		Date end = convertStringToDate(endDate, calendarTimeFormat);
		while (true) {
			startDate = getNextDateFromGivenDateForAppointmentAndReminder(0, 0, Integer.parseInt(repeatsEveryMonth),
					startDate, calendarTimeFormat);
			Date date = convertStringToDate(startDate, calendarTimeFormat);
			if (date.after(end))
				break;
			listOfDates.add(startDate);
		}
		return listOfDates;
	}

	public static String getStartDateOfTheWeek(String givenDate) throws Exception {
		String startDate = "";
		if (givenDate.contains("Sun"))
			return givenDate;
		int i = -1;
		while (true) {
			String date = getNextDateFromGivenDateForAppointmentAndReminder(i, 0, 0, givenDate, calendarTimeFormat);
			if (date.contains("Sun")) {
				startDate = date;
				break;
			}
			i--;
		}

		return startDate;
	}

	public static ArrayList<String> getAllRequiredDaysInAWeek(String startDate, String weekDate, String endDate,
			String repeatingDays) throws Exception {
		ArrayList<String> listOfDatesInAWeek = new ArrayList<String>();
		String startDateOfWeek = getStartDateOfTheWeek(weekDate);
		Date startDATE = convertStringToDate(startDate, calendarTimeFormat);
		Date end = convertStringToDate(endDate, calendarTimeFormat);
		String currentDate = getCurrentDateInRequiredFormatForAppointmentAndReminder(0, 0, 0, calendarTimeFormat);
		Date todayDATE = convertStringToDate(currentDate, calendarTimeFormat);
		int i = 0;
		boolean haltWhile = false;
		while (true) {
			String nextDate = getNextDateFromGivenDateForAppointmentAndReminder(i, 0, 0, startDateOfWeek,
					calendarTimeFormat);
			for (String repeatingDay : repeatingDays.split(",")) {

				Date date = convertStringToDate(nextDate, calendarTimeFormat);
				if (date.after(end)) {
					haltWhile = true;
					break;
				}
				if (date.before(todayDATE))
					continue;

				if (date.before(startDATE))
					continue;

				if (nextDate.contains(repeatingDay.trim()))
					listOfDatesInAWeek.add(nextDate);

			}
			if (nextDate.contains("Sat")) {
				haltWhile = true;
				break;
			}
			i++;
			if (haltWhile)
				break;
		}

		return listOfDatesInAWeek;
	}

	public static ArrayList<String> getListOfDatesBetweenTheGivenDatesWithRepeatsEveryWeekCondition(String startDate,
			String endDate, String repeatsEveryWeek, String repeatingDays) throws Exception {
		ArrayList<String> listOfDates = new ArrayList<String>();
		ArrayList<String> listOfDatesInAWeek = getAllRequiredDaysInAWeek(startDate, startDate, endDate, repeatingDays);
		listOfDates.addAll(listOfDatesInAWeek);
		int weekDays = 7 * Integer.parseInt(repeatsEveryWeek);
		Date end = convertStringToDate(endDate, calendarTimeFormat);
		String weekDate = startDate;
		while (true) {
			weekDate = getNextDateFromGivenDateForAppointmentAndReminder(weekDays, 0, 0, weekDate, calendarTimeFormat);
			listOfDatesInAWeek = getAllRequiredDaysInAWeek(startDate, weekDate, endDate, repeatingDays);
			listOfDates.addAll(listOfDatesInAWeek);
			Date date = convertStringToDate(weekDate, calendarTimeFormat);
			if (date.after(end))
				break;
		}
		return listOfDates;
	}

	public static ArrayList<String> getListOfDatesBetweenTheGivenDatesWithRepeatsEveryMonthCondition(String startDate,
			String endDate, String repeatsEveryMonth) throws Exception {
		ArrayList<String> listOfDates = new ArrayList<String>();
		listOfDates.add(startDate);
		Date end = convertStringToDate(endDate, calendarTimeFormat);
		while (true) {
			startDate = getNextDateFromGivenDateForAppointmentAndReminder(0, Integer.parseInt(repeatsEveryMonth), 0,
					startDate, calendarTimeFormat);
			Date date = convertStringToDate(startDate, calendarTimeFormat);
			if (date.after(end))
				break;
			listOfDates.add(startDate);
		}
		return listOfDates;
	}

	public static ArrayList<String> getListOfDatesBetweenTheGivenDates(String startDate, String endDate)
			throws Exception {
		ArrayList<String> listOfDates = new ArrayList<String>();
		int i = 0;
		while (true) {
			String date = getNextDateFromGivenDateForAppointmentAndReminder(i, 0, 0, startDate, calendarTimeFormat);
			listOfDates.add(date);
			if (date.equals(endDate))
				break;
			i++;
		}
		return listOfDates;
	}

	public static String getNextDateFromGivenDateForAppointmentAndReminder(int plusDay, int plusMonth, int plusYear,
			String currentDate, String requiredFormat) throws Exception {
		Date givenDate = convertStringToDate(currentDate, calendarTimeFormat);
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(givenDate);
		c.add(Calendar.DATE, plusDay);
		c.add(Calendar.MONTH, plusMonth);
		c.add(Calendar.YEAR, plusYear);
		date = c.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(requiredFormat);
		return formatter.format(date);
	}

	public static String getCurrentDateInRequiredFormatForAppointmentAndReminder(int plusDay, int plusMonth,
			int plusYear, String requiredFormat) throws Exception {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, plusDay);
		c.add(Calendar.MONTH, plusMonth);
		c.add(Calendar.YEAR, plusYear);
		date = c.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(requiredFormat);
		return formatter.format(date);
	}

	public static void pauseTheExecution(int seconds) throws Exception {
		long milliSeconds = seconds * 1000;
		Thread.sleep(milliSeconds);
	}

	public static void refreshTheBrowserPage() throws Exception {
		threadLocalWebDriver.get().navigate().refresh();
	}

	public static String getRandomDateBetweenGivenRange(int start, int end, boolean onlyPast) throws Exception {
		Faker faker = new Faker();
		SimpleDateFormat df = new SimpleDateFormat(calendarTimeFormat);
		Date date = faker.date().birthday(start, end);
		Date currentDate = convertStringToDate(
				getCurrentDateInRequiredFormatForAppointmentAndReminder(0, 0, 0, calendarTimeFormat),
				calendarTimeFormat);
		if (onlyPast) {
			while (date.after(currentDate)) {
				date = faker.date().birthday(start, end);
			}
		}
		return df.format(date);
	}

	public static String getRandomDateOfBirth() throws Exception {
		Faker faker = new Faker();
		SimpleDateFormat df = new SimpleDateFormat(calendarTimeFormat);
		Date date = faker.date().birthday(1, 10);
		return df.format(date);
	}

	public static String searchAndSelectRequiredValueFromDropDown(String clickElement, String inputElement,
			String requiredValue, String listElements) throws Exception {
		String dropDownElementText = "";
		clickMethod(clickElement);
		sendKeys(inputElement, requiredValue);
		waitForElementToBeDisplayed(listElements, 10);
		pauseTheExecution(1);
		List<WebElement> list = getWebElements(listElements);
		for (WebElement ele : list) {
			dropDownElementText = ele.getText();
			if (dropDownElementText.equals(requiredValue)) {
				clickMethod(ele);
				break;
			}
		}
		return requiredValue;
	}

	public static String searchAndSelectRandomValueFromDropDown(String clickElement, String inputElement, String text,
			String listElements) throws Exception {
		clickMethod(clickElement);
		waitForElementToBeDisplayed(inputElement, 10);
		sendKeys(inputElement, text);
		pauseTheExecution(2);
		waitForElementToBeDisplayed(listElements, 10);
		List<WebElement> list = getWebElements(listElements);
		int randomNum = getRandomIntegerValue(0, list.size() - 1);
		String selectedItemText = list.get(randomNum).getText();
		jsClick(list.get(randomNum));
		return selectedItemText;
	}

	public static String selectRequiredValueFromDropDown(String clickElement, String listElements, String requiredValue)
			throws Exception {
		String dropDownElementText = "";
		clickMethod(clickElement);
		waitForElementToBeDisplayed(listElements, 10);
		List<WebElement> list = getWebElements(listElements);
		for (WebElement ele : list) {
			dropDownElementText = ele.getText();
			if (dropDownElementText.equals(requiredValue)) {
				jsClick(ele);
				// ele.click();
				break;
			}
		}
		return dropDownElementText;
	}

	public static String selectRandomValueFromDropDown(String clickElement, String listElements) throws Exception {
		clickMethod(clickElement);
		waitForElementToBeDisplayed(listElements, 10);
		pauseTheExecution(2);
		List<WebElement> list = getWebElements(listElements);
		int randomNum = getRandomIntegerValue(0, list.size() - 1);
		if (clickElement.equals("Add New Doctor Modal - Speciality Drop Down"))
			randomNum = 0;
		String selectedItemText = list.get(randomNum).getText();
		jsClick(list.get(randomNum));
		return selectedItemText;
	}

	public static String getMonthAndYear(int monthPeriod, int yearPeriod) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("MMM yyyy");
		int month = Calendar.getInstance().get(Calendar.MONTH); // month is 0 based on calendar
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Calendar calendar = Calendar.getInstance();
		calendar.set(MONTH, month + monthPeriod);
		calendar.set(YEAR, year + yearPeriod);
		return df.format(calendar.getTime());
	}

	public static ArrayList<Integer> getCurrentCalendarDisabledDayDates(ArrayList<Integer> currentMonthDates)
			throws Exception {
		ArrayList<Integer> blockedDates = new ArrayList<Integer>();
		for (int date : currentMonthDates) {
			if (date != 1) {
				blockedDates.add(date);
			} else
				break;
		}
		int j = 0;
		List<Integer> sublist = null;
		for (int date : currentMonthDates) {
			if (j > 20) {
				if (date == 1) {
					sublist = currentMonthDates.subList(j, currentMonthDates.size());
				}
			}
			j++;
		}
		for (int i : sublist) {
			blockedDates.add(i);
		}
		return blockedDates;
	}

	public static ArrayList<Integer> getPreviousCalendarMonthPastDates() throws Exception {
		ArrayList<Integer> currentMonthPastDates = new ArrayList<Integer>();
		String currentDateString = getCurrentDateInRequiredFormatForAppointmentAndReminder(0, 0, 0, "d");
		int currentDate = Integer.parseInt(currentDateString);
		ArrayList<Integer> currentMonthDates = getCalendarMonthDayDates(0);
		int count = 0;
		for (int i : currentMonthDates) {
			if (i == currentDate && count > 20)
				break;
			else
				currentMonthPastDates.add(i);
			count++;
		}
		return currentMonthPastDates;
	}

	public static ArrayList<Integer> getCurrentCalendarMonthPastDates() throws Exception {
		ArrayList<Integer> currentMonthPastDates = new ArrayList<Integer>();
		String currentDateString = getCurrentDateInRequiredFormatForAppointmentAndReminder(0, 0, 0, "d");
		int currentDate = Integer.parseInt(currentDateString);
		if (currentDate != 1) {
			ArrayList<Integer> currentMonthDates = getCalendarMonthDayDates(1);
			for (int i : currentMonthDates) {
				if (i == currentDate)
					break;
				else
					currentMonthPastDates.add(i);
			}
		}
		return currentMonthPastDates;
	}

	public static ArrayList<Integer> getCalendarMonthDayDates(int requiredMonth) throws Exception {
		ArrayList<Integer> currentMonthDates = new ArrayList<Integer>();
		int count = 0;
		boolean condition = true;
		while (true) {
			currentMonthDates = new ArrayList<Integer>();
			SimpleDateFormat df = new SimpleDateFormat("d");
			int month = Calendar.getInstance().get(Calendar.MONTH) + requiredMonth; // month is 0 based on calendar
			int year = Calendar.getInstance().get(Calendar.YEAR);

			Calendar start = Calendar.getInstance();
			start.set(MONTH, month - 1); // month is 0 based on calendar
			start.set(YEAR, year);
			start.set(DAY_OF_MONTH, 1);
			start.getTime();
			start.set(DAY_OF_WEEK, SUNDAY);

			Calendar end = Calendar.getInstance();
			end.set(MONTH, month); // next month
			end.set(YEAR, year);
			end.set(DAY_OF_MONTH, 1);
			end.getTime();
			end.set(DATE, -1);
			end.set(DAY_OF_WEEK, SATURDAY);
			start.getTime();
			if (end.get(MONTH) != month)
				end.add(DATE, +7);
			if (!condition)
				end.add(DATE, +7);
			while (start.before(end)) {
				String date = df.format(start.getTime());
				currentMonthDates.add(Integer.parseInt(date));
				start.add(DATE, 1);
			}
			String date = df.format(start.getTime());
			currentMonthDates.add(Integer.parseInt(date));
			if (currentMonthDates.size() == 42) {
				break;
			} else {
				if (currentMonthDates.size() > 42) {
					condition = true;
				} else {
					condition = false;
				}
			}

			count++;

			if (count > 10)
				break;
		}

		return currentMonthDates;
	}

	public static int getRandomIntegerValue(int min, int max) throws Exception {
		if (min > max) {
			min = min + max;
			max = min - max;
			min = min - max;
		}
		Random random = new Random();
		int randomNum = random.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static void getWebElementByJavaScriptExecutor() throws Exception {
		String script = "return window.getComputedStyle(document.querySelector(\"label[for='homeAddress.addressLine2']\"),':before').getPropertyValue('content')";
		JavascriptExecutor js = (JavascriptExecutor) threadLocalWebDriver.get();
		String content = (String) js.executeScript(script);
		System.out.println(content);
	}

	public static String getcurrentDayDate(String dayFormat) throws Exception {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(dayFormat);
		return formatter.format(date);
	}

	public static String[] getCurrentWeekDates() throws Exception {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("d");
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		String[] daysOfWeek = new String[7];
		for (int i = 0; i < 7; i++) {
			daysOfWeek[i] = sdf.format(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return daysOfWeek;
	}

	public static void storeLogToReport(String log) throws Exception {
		System.out.println(log);
		threadLocalExtentTestNode.get().log(Status.INFO, log);
	}

	public static void clearTheDataFromTheElement(String element) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Clearing the data from: \"" + element + "\"");
		getWebElement(objectRepository.get(element)).clear();
	}

	public static void scrollToElement(String element) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Scrolling to the :\"" + element + "\"");
		JavascriptExecutor jse = ((JavascriptExecutor) threadLocalWebDriver.get());
		jse.executeScript("arguments[0].scrollIntoView();", getWebElement(objectRepository.get(element)));
	}

	public static void scrollToElement(WebElement element) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Scrolling to the web element:\"" + element + "\"");
		JavascriptExecutor jse = ((JavascriptExecutor) threadLocalWebDriver.get());
		jse.executeScript("arguments[0].scrollIntoView();", element);
	}

	public static void scrollDown(int pixels) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Scrolling Down by " + pixels + " Pixels");
		JavascriptExecutor jse = (JavascriptExecutor) threadLocalWebDriver.get();
		jse.executeScript("window.scrollBy(0," + pixels + ")");
	}

	public static String getCurrentTimeOrDateInRequiredFormat(String requiredFormat) throws Exception {
		Date currentTime = Calendar.getInstance().getTime();
		String timeStamp = new SimpleDateFormat(requiredFormat).format(currentTime);
		return timeStamp;
	}

	public static String getCurrentDateAndTime() {
		Date currentTime = Calendar.getInstance().getTime();
		String timeStamp = new SimpleDateFormat("dd_MM_yyyy-hh_mm_ss_a").format(currentTime);
		return timeStamp;
	}

	public static void verifyCurrentUrlWithExpectedUrl(String expectedURL) throws Exception {
		String currentURL = threadLocalWebDriver.get().getCurrentUrl();
		threadLocalExtentTestNode.get().log(Status.PASS,
				"Verifying the current URL(" + currentURL + ") with expected URL(" + expectedURL + ")");
		if (!currentURL.equals(expectedURL))
			assertTrue(false, "Current Url is not matching with the expected URL, Expected URL = " + expectedURL
					+ " but Current Url = " + currentURL + "");
	}

	public static String getCurrentUrl() throws Exception {
		return threadLocalWebDriver.get().getCurrentUrl();
	}

	public static void navigateBack() throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS,
				"Navigating back(Like, Clicking on back arrow in back button)");
		threadLocalWebDriver.get().navigate().back();
	}

	public static void waitForElementToBeDisplayed(String element, long timeOutInSeconds) throws Exception {
		try {
			threadLocalExtentTestNode.get().log(Status.PASS,
					"Waiting for the \"" + element + "\" to be displayed for maximum " + timeOutInSeconds + " Seconds");
			WebDriverWait wait = new WebDriverWait(threadLocalWebDriver.get(), timeOutInSeconds);
			wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(objectRepository.get(element))));
		} catch (Exception e) {
		}
	}

	public static void waitForEitherOfElementToBeDisplayed(String element1, String element2, long timeOutInSeconds) {
		try {
			threadLocalExtentTestNode.get().log(Status.PASS, "Waiting for either of the elements \"" + element1
					+ "\" (or) \"" + element2 + "\" to be displayed for maximum " + timeOutInSeconds + " Seconds");
			WebDriverWait wait = new WebDriverWait(threadLocalWebDriver.get(), timeOutInSeconds);
			wait.until(ExpectedConditions.or(
					ExpectedConditions.presenceOfElementLocated(getBy(objectRepository.get(element1))),
					ExpectedConditions.presenceOfElementLocated(getBy(objectRepository.get(element2)))));
		} catch (Exception e) {

		}
	}

	public static void waitForElementToBeInvisible(String element, long timeOutInSeconds) throws Exception {
		try {
			threadLocalExtentTestNode.get().log(Status.PASS,
					"Waiting for the \"" + element + "\" to be invisible for maximum " + timeOutInSeconds + " Seconds");
			WebDriverWait wait = new WebDriverWait(threadLocalWebDriver.get(), timeOutInSeconds);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(getBy(objectRepository.get(element))));
		} catch (Exception e) {

		}
	}

	public static void mouseHoverOnElement(String element) throws Exception {
		Actions action = new Actions(threadLocalWebDriver.get());
		action.moveToElement(getWebElement(objectRepository.get(element))).click().build().perform();
	}

	public static void loadTheUrl(String url) throws Exception {
		// if (test != null)
		// threadLocalExtentTestNode.get().log(Status.PASS, "Loading the URL: " + url);
		threadLocalWebDriver.get().get(url);
	}

	public static boolean verifyElementPresent(String element) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Verifying the presence of: \"" + element + "\"");
		try {
			getWebElement(objectRepository.get(element)).isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean verifyEitherOfElementPresent(String element1, String element2) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS,
				"Verifying the presence of either of the :\"" + element1 + "\" (or) \"" + element2 + "\"");
		if (verifyElementPresent(element1))
			return true;
		else if (verifyElementPresent(element2))
			return true;
		return false;
	}

	public static boolean verifyElementNotPresent(String element) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Verifying the non presence of: \"" + element + "\"");
		try {
			getWebElement(element).isDisplayed();
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	public static void actionsClick(String element) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Clicking on the: \"" + element + "\"");
		Actions action = new Actions(threadLocalWebDriver.get());
		action.moveToElement(getWebElement(objectRepository.get(element))).click().build().perform();
	}

	public static void click(String element) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Clicking on the: \"" + element + "\"");
		getWebElement(objectRepository.get(element)).click();
	}

	public static void clickMethod(String element) throws Exception {
		try {
			threadLocalExtentTestNode.get().log(Status.PASS, "Clicking on the: " + element);
			getWebElement(objectRepository.get(element)).click();
		} catch (Exception e) {
			jsClick(getWebElement(objectRepository.get(element)));
		}
	}

	public static void clickMethod(WebElement element) throws Exception {
		try {
			threadLocalExtentTestNode.get().log(Status.PASS, "Clicking on the: " + element.getText());
			element.click();
		} catch (Exception e) {
			jsClick(element);
		}
	}

	public static void jsClick(WebElement element) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Clicking using JavaScript on the: " + element.getText());
		JavascriptExecutor executor = (JavascriptExecutor) threadLocalWebDriver.get();
		executor.executeScript("arguments[0].click();", element);
	}

	public static void sendKeys(String element, String text) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Sending text '" + text + "' to the: \"" + element + "\"");
		getWebElement(objectRepository.get(element)).sendKeys(text);
	}

	public static String getText(String element) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Getting text from the: \"" + element + "\"");
		return getWebElement(objectRepository.get(element)).getText();
	}

	public static void clearUsingSendKeys(String element) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Clearing the value in: \"" + element + "\"");
		getWebElement(objectRepository.get(element)).sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
	}

	public static void clear(String element) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Clearing the value in: \"" + element + "\"");
		getWebElement(objectRepository.get(element)).clear();
	}

	public static String getAttributeValue(String element) throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Getting the attribute value from the: \"" + element + "\"");
		return getWebElement(objectRepository.get(element)).getAttribute(objectRepository.get(element).split("==")[2]);
	}

	public static String encodeFileToBase64Binary(File file) {
		String encodedfile = null;
		try {
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fileInputStreamReader.read(bytes);
			encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
			fileInputStreamReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encodedfile;
	}

	public static WebElement getWebElement(String element) {
		WebElement webElement = null;
		String[] arr = element.split("==");
		String typeOfLocator = arr[0];
		String locator = arr[1];
		if (typeOfLocator.equals("xpath"))
			webElement = threadLocalWebDriver.get().findElement(By.xpath(locator));
		else if (typeOfLocator.equals("css"))
			webElement = threadLocalWebDriver.get().findElement(By.cssSelector(locator));
		else if (typeOfLocator.equals("id"))
			webElement = threadLocalWebDriver.get().findElement(By.id(locator));
		else if (typeOfLocator.equals("className"))
			webElement = threadLocalWebDriver.get().findElement(By.className(locator));
		else if (typeOfLocator.equals("linkText"))
			webElement = threadLocalWebDriver.get().findElement(By.linkText(locator));
		else if (typeOfLocator.equals("partialLinkText"))
			webElement = threadLocalWebDriver.get().findElement(By.partialLinkText(locator));
		else if (typeOfLocator.equals("name"))
			webElement = threadLocalWebDriver.get().findElement(By.name(locator));
		else if (typeOfLocator.equals("tagName"))
			webElement = threadLocalWebDriver.get().findElement(By.tagName(locator));
		return webElement;
	}

	public static List<WebElement> getWebElements(String element) {
		threadLocalExtentTestNode.get().log(Status.PASS, "Getting the elements from the: \"" + element + "\"");
		element = objectRepository.get(element);
		List<WebElement> webElements = null;
		String[] arr = element.split("==");
		String typeOfLocator = arr[0];
		String locator = arr[1];
		if (typeOfLocator.equals("xpath"))
			webElements = threadLocalWebDriver.get().findElements(By.xpath(locator));
		else if (typeOfLocator.equals("css"))
			webElements = threadLocalWebDriver.get().findElements(By.cssSelector(locator));
		else if (typeOfLocator.equals("id"))
			webElements = threadLocalWebDriver.get().findElements(By.id(locator));
		else if (typeOfLocator.equals("className"))
			webElements = threadLocalWebDriver.get().findElements(By.className(locator));
		else if (typeOfLocator.equals("linkText"))
			webElements = threadLocalWebDriver.get().findElements(By.linkText(locator));
		else if (typeOfLocator.equals("partialLinkText"))
			webElements = threadLocalWebDriver.get().findElements(By.partialLinkText(locator));
		else if (typeOfLocator.equals("name"))
			webElements = threadLocalWebDriver.get().findElements(By.name(locator));
		else if (typeOfLocator.equals("tagName"))
			webElements = threadLocalWebDriver.get().findElements(By.tagName(locator));
		return webElements;
	}

	public static By getBy(String element) {
		By by = null;
		String[] arr = element.split("==");
		String typeOfLocator = arr[0];
		String locator = arr[1];
		if (typeOfLocator.equals("xpath"))
			by = By.xpath(locator);
		else if (typeOfLocator.equals("css"))
			by = By.cssSelector(locator);
		else if (typeOfLocator.equals("id"))
			by = By.id(locator);
		else if (typeOfLocator.equals("className"))
			by = By.className(locator);
		else if (typeOfLocator.equals("linkText"))
			by = By.linkText(locator);
		else if (typeOfLocator.equals("partialLinkText"))
			by = By.partialLinkText(locator);
		else if (typeOfLocator.equals("name"))
			by = By.name(locator);
		else if (typeOfLocator.equals("tagName"))
			by = By.tagName(locator);
		return by;
	}

	public static String getDataInBetweenString(String start, String end, String response) {
		String dataInBetweenString = "";
		Matcher matcher = Pattern.compile(start + "(.*?)" + end).matcher(response);
		if (matcher.find()) {
			dataInBetweenString = matcher.group(1);
		}
		return dataInBetweenString;
	}

	public static String getActivePatientsAndDoctors(String start, String end, String string) throws Exception {
		StringBuilder sb = new StringBuilder();
		Matcher matcher = Pattern.compile(start + "(.*?)" + end).matcher(string);
		while (matcher.find()) {
			String match = matcher.group(0);
			System.out.println(match);
			sb.append(match);
		}
		return sb.toString();
	}

	public static ArrayList<String> getAllMatchedValuesInAString(String start, String end, String string) {
		ArrayList<String> allMatchedValues = new ArrayList<String>();
		Matcher matcher = Pattern.compile(start + "(.*?)" + end).matcher(string);
		while (matcher.find()) {
			String match = matcher.group(1);
			allMatchedValues.add(match);
		}
		return allMatchedValues;
	}

	public static void waitforElementToBeDisplayedAndDisappear(String element, String message) throws Exception {
		waitForElementToBeDisplayed(element, 5);
		assertTrue(verifyElementPresent(element), message);
		waitForElementToBeInvisible(element, 10);
	}

	public static void waitForLoadingSymbolToBeDisappeared() throws Exception {
		threadLocalExtentTestNode.get().log(Status.PASS, "Waiting For Loading Sumbol To Be Disappeared");
		waitForElementToBeInvisible(objectRepository.get("Loading Symbol"), 60);
	}

	public static void clearBrowserCookies() throws Exception {
		threadLocalWebDriver.get().manage().deleteAllCookies();
	}

	public static void createNode(String text) throws Exception {
		threadLocalExtentTestNode.remove();
		ExtentTest node = threadLocalExtentTest.get().createNode("\"" + text + "\"");
		threadLocalExtentTestNode.set(node);
	}

}
