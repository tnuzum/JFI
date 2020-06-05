package SingleMemberAppointments;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AppointmentsPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ClubReqPackages_BookAppt_FreeAppointment extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook1 = "Free Training Auto";
	private static String appointmentToBook2 = "Free ServiceV Auto";
	private static String appointmentToBook3 = "Free MSSTraining Auto";
	private static String appointmentToBook4 = "Free MSSDiscountServiceV Auto";
	private static String appointmentToBook5 = "Free Training Auto 3 Res";
	private static String appointmentToBook6 = "Free Training Due 2 Discount";
	private static String additionalResourceName = "Gym";
	private static String resourceName1 = "Holmes, Jeff";
	private static String resourceName2 = "PT Smith, Andrew";
	private static String resourceName3 = "FitExpert2";
	private static String clubNameDisplayed = "Club: Studio Jonas";

	public reusableWaits rw;

	public reusableMethods rm;

	public ClubReqPackages_BookAppt_FreeAppointment() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

	private static String startTime;

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1)
	public void ScheduleFreeTraining() throws IOException, InterruptedException {
		rm.activeMemberLogin("bauto", "Testing1!");
		rw.waitForDashboardLoaded();

		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);

		rm.catchErrorMessage();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		AppointmentsPO ap = new AppointmentsPO(driver);

		Select s = new Select(ap.getclubs());
		List<WebElement> Clubs = s.getOptions();

		while (!ap.getclubs().isEnabled()) {
			System.out.println("Waiting for Clubs drop down to not be blank");
		}

		int count0 = Clubs.size();
		System.out.println("1 " + count0);

		for (int i = 0; i < count0; i++) {
			String club = Clubs.get(i).getText();

			if (club.equals(clubName)) {
				s.selectByVisibleText(club);
				break;
			}
		}

		WebElement bic = ap.getBookableItemCategory();

		Thread.sleep(2000);

		Select s1 = new Select(bic);
		List<WebElement> ProductCategories = s1.getOptions();

		int count = ProductCategories.size();
		System.out.println("2 " + count);

		for (int i = 0; i < count; i++) {
			String category = ProductCategories.get(i).getText();

			if (category.equals(productCategory)) {
				s1.selectByVisibleText(category);
				break;
			}
		}

		Select s2 = new Select(ap.getBookableItem());
		// Thread.sleep(2000);

		while (!ap.getBookableItem().isEnabled()) {
			System.out.println("Waiting for Product drop down to not be blank");
		}
		List<WebElement> Products = s2.getOptions();

		int count1 = Products.size();
		System.out.println(count1);

		for (int j = 0; j < count1; j++) {
			String product = Products.get(j).getText();

			if (product.equals(appointmentToBook1)) {
				s2.selectByVisibleText(product);
				break;
			}
		}

		WebElement rt = ap.getResourceType();

		while (!rt.isEnabled())// while button is NOT(!) enabled
		{
			System.out.println("Waiting for Resource drop down to not be blank");
		}
		Select s3 = new Select(rt);
//		Thread.sleep(2000);
		List<WebElement> Resources = s3.getOptions();

		int count2 = Resources.size();
		System.out.println(count2);

		for (int k = 0; k < count2; k++) {
			String resource = Resources.get(k).getText();

			if (resource.equals(resourceName1)) {
				s3.selectByVisibleText(resource);
				break;
			}
		}
		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");

		String classtext = ap.getCalendarTomorrow().getAttribute("class");

		if (classtext.contains("cal-out-month")) {

			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");
		}

		ap.getCalendarTomorrow().click();
		Thread.sleep(1000);

		Assert.assertEquals(ap.getBooksNames().getText(), resourceName1);

		WebElement st1 = ap.getSelectTimeMorningButton();

		wait.until(ExpectedConditions.elementToBeClickable(st1));
		while (!st1.isEnabled())// while button is NOT(!) enabled
		{
			System.out.println("Waiting for available times");
		}

		st1.click();
		WebElement st2 = ap.getSelectTime1stAvailable();
//					while (!st2.isEnabled())//while button is NOT(!) enabled
//					{
//					Thread.sleep(200);
//					}

		wait.until(ExpectedConditions.elementToBeClickable(st2));
		startTime = st2.getText();
		// st2.click();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", st2);
		Thread.sleep(2000);

		System.out.println(ap.getPopup1Content().getText());
		System.out.println("Time: " + tomorrowsDate + " " + startTime);
		System.out.println("Product: " + appointmentToBook1);
		System.out.println("Resource: " + resourceName1);
		Assert.assertTrue(ap.getPopup1Content().getText().contains("This appointment is free!"));
		Assert.assertTrue(ap.getPopup1Content().getText().contains(clubNameDisplayed));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: " + tomorrowsDate + " " + startTime));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: " + appointmentToBook1));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Resource: " + resourceName1));

		ap.getPopup1BookButton().click();

		wait.until(ExpectedConditions.stalenessOf(ap.getPopup2OKButton()));

		wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

//Verifies the success message
		Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
		ap.getPopup2OKButton().click();
		Thread.sleep(2000);

//Navigate to Dashboard
		int linkcount = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < linkcount; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

			{
				// rw.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}
		rw.waitForDashboardLoaded();
//Verifies the link navigates to the right page
		Assert.assertEquals("Dashboard", driver.getTitle());
		Thread.sleep(2000);
		rm.ConfirmAndCancelAppointmentNoFee(tomorrowsDate, startTime, appointmentToBook1);

		rm.memberLogout();

	}

	@Test(priority = 2)
	public void ScheduleFreeServiceV() throws IOException, InterruptedException {
		rm.activeMemberLogin("bauto", "Testing1!");
		rw.waitForDashboardLoaded();

		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);

		rm.catchErrorMessage();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		AppointmentsPO ap = new AppointmentsPO(driver);

		Select s = new Select(ap.getclubs());
		List<WebElement> Clubs = s.getOptions();

		while (!ap.getclubs().isEnabled()) {
			System.out.println("Waiting for Clubs drop down to not be blank");
		}

		int count0 = Clubs.size();
		System.out.println("1 " + count0);

		for (int i = 0; i < count0; i++) {
			String club = Clubs.get(i).getText();

			if (club.equals(clubName)) {
				s.selectByVisibleText(club);
				break;
			}
		}

		WebElement bic = ap.getBookableItemCategory();

		Thread.sleep(2000);

		Select s1 = new Select(bic);
		List<WebElement> ProductCategories = s1.getOptions();

		int count = ProductCategories.size();
		System.out.println("2 " + count);

		for (int i = 0; i < count; i++) {
			String category = ProductCategories.get(i).getText();

			if (category.equals(productCategory)) {
				s1.selectByVisibleText(category);
				break;
			}
		}

		Select s2 = new Select(ap.getBookableItem());
		// Thread.sleep(2000);

		while (!ap.getBookableItem().isEnabled()) {
			System.out.println("Waiting for Product drop down to not be blank");
		}
		List<WebElement> Products = s2.getOptions();

		int count1 = Products.size();
		System.out.println(count1);

		for (int j = 0; j < count1; j++) {
			String product = Products.get(j).getText();

			if (product.equals(appointmentToBook2)) {
				s2.selectByVisibleText(product);
				break;
			}
		}

		WebElement rt = ap.getResourceType();

		while (!rt.isEnabled())// while button is NOT(!) enabled
		{
			System.out.println("Waiting for Resource drop down to not be blank");
		}
		Select s3 = new Select(rt);
//		Thread.sleep(2000);
		List<WebElement> Resources = s3.getOptions();

		int count2 = Resources.size();
		System.out.println(count2);

		for (int k = 0; k < count2; k++) {
			String resource = Resources.get(k).getText();

			if (resource.equals(resourceName2)) {
				s3.selectByVisibleText(resource);
				break;
			}
		}
		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");

		String classtext = ap.getCalendarTomorrow().getAttribute("class");

		if (classtext.contains("cal-out-month")) {

			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();
			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");
		}

		ap.getCalendarTomorrow().click();
		Thread.sleep(1000);

		Assert.assertEquals(ap.getBooksNames().getText(), resourceName2);

		WebElement st1 = ap.getSelectTimeMorningButton();

		wait.until(ExpectedConditions.elementToBeClickable(st1));
		while (!st1.isEnabled())// while button is NOT(!) enabled
		{
			System.out.println("Waiting for available times");
		}

		st1.click();
		WebElement st2 = ap.getSelectTime1stAvailable();
//					while (!st2.isEnabled())//while button is NOT(!) enabled
//					{
//					Thread.sleep(200);
//					}

		wait.until(ExpectedConditions.elementToBeClickable(st2));
		startTime = st2.getText();
		// st2.click();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", st2);
		Thread.sleep(1000);

		System.out.println(ap.getPopup1Content().getText());
		System.out.println("Time: " + tomorrowsDate + " " + startTime);
		System.out.println("Product: " + appointmentToBook2);
		System.out.println("Resource: " + resourceName2);
		Assert.assertTrue(ap.getPopup1Content().getText().contains("This appointment is free!"));
		Assert.assertTrue(ap.getPopup1Content().getText().contains(clubNameDisplayed));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: " + tomorrowsDate + " " + startTime));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: " + appointmentToBook2));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Resource: " + resourceName2));

		ap.getPopup1BookButton().click();

		wait.until(ExpectedConditions.stalenessOf(ap.getPopup2OKButton()));

		wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

//Verifies the success message
		Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
		ap.getPopup2OKButton().click();
		Thread.sleep(2000);

//Navigate to Dashboard
		int linkcount = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < linkcount; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

			{
				// rw.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}
		rw.waitForDashboardLoaded();
//Verifies the link navigates to the right page
		Assert.assertEquals("Dashboard", driver.getTitle());
		Thread.sleep(2000);
		rm.ConfirmAndCancelAppointmentNoFee(tomorrowsDate, startTime, appointmentToBook2);

		rm.memberLogout();

	}

	@Test(priority = 3)
	public void ScheduleFreeTrainingWithThreeResources() throws IOException, InterruptedException {
		rm.activeMemberLogin("bauto", "Testing1!");
		rw.waitForDashboardLoaded();

		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);

		rm.catchErrorMessage();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		AppointmentsPO ap = new AppointmentsPO(driver);

		Select s = new Select(ap.getclubs());
		List<WebElement> Clubs = s.getOptions();

		while (!ap.getclubs().isEnabled()) {
			System.out.println("Waiting for Clubs drop down to not be blank");
		}

		int count0 = Clubs.size();
		System.out.println("1 " + count0);

		for (int i = 0; i < count0; i++) {
			String club = Clubs.get(i).getText();

			if (club.equals(clubName)) {
				s.selectByVisibleText(club);
				break;
			}
		}

		WebElement bic = ap.getBookableItemCategory();

		Thread.sleep(2000);

		Select s1 = new Select(bic);
		List<WebElement> ProductCategories = s1.getOptions();

		int count = ProductCategories.size();
		System.out.println("2 " + count);

		for (int i = 0; i < count; i++) {
			String category = ProductCategories.get(i).getText();

			if (category.equals(productCategory)) {
				s1.selectByVisibleText(category);
				break;
			}
		}

		Select s2 = new Select(ap.getBookableItem());
		// Thread.sleep(2000);

		while (!ap.getBookableItem().isEnabled()) {
			System.out.println("Waiting for Product drop down to not be blank");
		}
		List<WebElement> Products = s2.getOptions();

		int count1 = Products.size();
		System.out.println(count1);

		for (int j = 0; j < count1; j++) {
			String product = Products.get(j).getText();

			if (product.equals(appointmentToBook5)) {
				s2.selectByVisibleText(product);
				break;
			}
		}

		WebElement rt = ap.getResourceType();

		while (!rt.isEnabled())// while button is NOT(!) enabled
		{
			System.out.println("Waiting for Resource drop down to not be blank");
		}
		Select s3 = new Select(rt);
//		Thread.sleep(2000);
		List<WebElement> Resources = s3.getOptions();

		int count2 = Resources.size();
		System.out.println(count2);

		for (int k = 0; k < count2; k++) {
			String resource = Resources.get(k).getText();

			if (resource.equals(resourceName3)) {
				s3.selectByVisibleText(resource);
				break;
			}
		}
		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");

		String classtext = ap.getCalendarTomorrow().getAttribute("class");

		if (classtext.contains("cal-out-month")) {

			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");
		}

		ap.getCalendarTomorrow().click();
		Thread.sleep(1000);

		Assert.assertTrue(ap.getBooksNames().getText().contains(resourceName3));

		WebElement st1 = ap.getSelectTimeMorningButton();

		wait.until(ExpectedConditions.elementToBeClickable(st1));
		while (!st1.isEnabled())// while button is NOT(!) enabled
		{
			System.out.println("Waiting for available times");
		}

		st1.click();
		WebElement st2 = ap.getSelectTime1stAvailable();
//					while (!st2.isEnabled())//while button is NOT(!) enabled
//					{
//					Thread.sleep(200);
//					}

		wait.until(ExpectedConditions.elementToBeClickable(st2));
		startTime = st2.getText();
		// st2.click();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", st2);
		Thread.sleep(2000);

		Assert.assertTrue(ap.getPopup1Content().getText().contains(clubName));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: " + tomorrowsDate + " " + startTime));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: " + appointmentToBook5));
		Assert.assertTrue(ap.getPopup1Content().getText().contains(resourceName3));

		ap.getPopup1BookButton().click();

		int additionalResourcesCount = ap.getAdditionalResources().size();

		for (int n = 0; n < additionalResourcesCount; n++) {
			if (ap.getAdditionalResources().get(n).getText().contains(additionalResourceName))
				ap.getAdditionalResources().get(n).click();
		}

		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		Assert.assertEquals("Appointments", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Book Appointment", BT.getBreadcrumb2().getText());
		Assert.assertEquals(ap.getAppointmentName().getText(), appointmentToBook5);
		Assert.assertEquals(ap.getClubName().getText(), clubNameDisplayed);
		Assert.assertEquals(ap.getAppointmentTime().getText(), "Start Time: " + startTime);
		Assert.assertEquals("Date: " + tomorrowsDate, ap.getAppointmentDate().getText());

		ap.getbookButton().click();

		wait.until(ExpectedConditions.stalenessOf(ap.getPopup2OKButton()));

		wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

//Verifies the success message
		Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
		ap.getPopup2OKButton().click();
		Thread.sleep(2000);

//Navigate to Dashboard
		int linkcount = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < linkcount; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

			{
				// rw.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}
		rw.waitForDashboardLoaded();
//Verifies the link navigates to the right page
		Assert.assertEquals("Dashboard", driver.getTitle());
		Thread.sleep(2000);

		rm.ConfirmAndCancelAppointmentNoFee(tomorrowsDate, startTime, appointmentToBook5);

		rm.memberLogout();

	}

	@Test(priority = 4, description = "Package price is 0 dollars due to MSS club pricing discount 100% to the membership type")
	public void CannotScheduleZeroDollarMSSTraining() throws IOException, InterruptedException {
		rm.activeMemberLogin("bauto", "Testing1!");
		rw.waitForDashboardLoaded();

		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);

		rm.catchErrorMessage();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		AppointmentsPO ap = new AppointmentsPO(driver);

		Select s = new Select(ap.getclubs());
		List<WebElement> Clubs = s.getOptions();

		while (!ap.getclubs().isEnabled()) {
			System.out.println("Waiting for Clubs drop down to not be blank");
		}

		int count0 = Clubs.size();
		System.out.println("1 " + count0);

		for (int i = 0; i < count0; i++) {
			String club = Clubs.get(i).getText();

			if (club.equals(clubName)) {
				s.selectByVisibleText(club);
				break;
			}
		}

		WebElement bic = ap.getBookableItemCategory();

		Thread.sleep(2000);

		Select s1 = new Select(bic);
		List<WebElement> ProductCategories = s1.getOptions();

		int count = ProductCategories.size();
		System.out.println("2 " + count);

		for (int i = 0; i < count; i++) {
			String category = ProductCategories.get(i).getText();

			if (category.equals(productCategory)) {
				s1.selectByVisibleText(category);
				break;
			}
		}

		Select s2 = new Select(ap.getBookableItem());
		// Thread.sleep(2000);

		while (!ap.getBookableItem().isEnabled()) {
			System.out.println("Waiting for Product drop down to not be blank");
		}
		List<WebElement> Products = s2.getOptions();

		int count1 = Products.size();
		System.out.println(count1);

		for (int j = 0; j < count1; j++) {
			String product = Products.get(j).getText();

			if (product.equals(appointmentToBook3)) {
				s2.selectByVisibleText(product);
				break;
			}
		}

		WebElement rt = ap.getResourceType();

		while (!rt.isEnabled())// while button is NOT(!) enabled
		{
			System.out.println("Waiting for Resource drop down to not be blank");
		}
		Select s3 = new Select(rt);
//		Thread.sleep(2000);
		List<WebElement> Resources = s3.getOptions();

		int count2 = Resources.size();
		System.out.println(count2);

		for (int k = 0; k < count2; k++) {
			String resource = Resources.get(k).getText();

			if (resource.equals(resourceName1)) {
				s3.selectByVisibleText(resource);
				break;
			}
		}

		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");

		String classtext = ap.getCalendarTomorrow().getAttribute("class");

		if (classtext.contains("cal-out-month")) {

			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();
			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");
		}

		ap.getCalendarTomorrow().click();
		Thread.sleep(1000);

		Assert.assertEquals(ap.getBooksNames().getText(), resourceName1);

		WebElement st1 = ap.getSelectTimeMorningButton();

		wait.until(ExpectedConditions.elementToBeClickable(st1));
		while (!st1.isEnabled())// while button is NOT(!) enabled
		{
			System.out.println("Waiting for available times");
		}

		st1.click();
		WebElement st2 = ap.getSelectTime1stAvailable();
//					while (!st2.isEnabled())//while button is NOT(!) enabled
//					{
//					Thread.sleep(200);
//					}

		wait.until(ExpectedConditions.elementToBeClickable(st2));
		startTime = st2.getText();
		// st2.click();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", st2);
		Thread.sleep(1000);

		ap.getPopup1BookButton().click();
//		Thread.sleep(3000);
		wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));

//Verifies that the Total amount is zero dollars
		System.out.println(ap.getTotalAmount().getText());

		Assert.assertTrue(ap.getTotalAmount().getText().contains("$0.00"));

//Verifies that the Pay button is disabled	
		Assert.assertFalse(ap.getPaymentButton().isEnabled());

		rm.memberLogout();

	}

	@Test(priority = 5, description = "Package price is 0 dollars due to MSS club pricing discount 100% to the membership type")
	public void CannotScheduleFreeDiscountMSSServiceV() throws IOException, InterruptedException {
		rm.activeMemberLogin("bauto", "Testing1!");
		rw.waitForDashboardLoaded();

		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);

		rm.catchErrorMessage();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		AppointmentsPO ap = new AppointmentsPO(driver);

		Select s = new Select(ap.getclubs());
		List<WebElement> Clubs = s.getOptions();

		while (!ap.getclubs().isEnabled()) {
			System.out.println("Waiting for Clubs drop down to not be blank");
		}

		int count0 = Clubs.size();
		System.out.println("1 " + count0);

		for (int i = 0; i < count0; i++) {
			String club = Clubs.get(i).getText();

			if (club.equals(clubName)) {
				s.selectByVisibleText(club);
				break;
			}
		}

		WebElement bic = ap.getBookableItemCategory();

		Thread.sleep(3000);

		Select s1 = new Select(bic);
		List<WebElement> ProductCategories = s1.getOptions();

		int count = ProductCategories.size();
		System.out.println("2 " + count);

		for (int i = 0; i < count; i++) {
			String category = ProductCategories.get(i).getText();

			if (category.equals(productCategory)) {
				s1.selectByVisibleText(category);
				break;
			}
		}

		Select s2 = new Select(ap.getBookableItem());
		// Thread.sleep(2000);

		while (!ap.getBookableItem().isEnabled()) {
			System.out.println("Waiting for Product drop down to not be blank");
		}
		List<WebElement> Products = s2.getOptions();

		int count1 = Products.size();
		System.out.println(count1);

		for (int j = 0; j < count1; j++) {
			String product = Products.get(j).getText();

			if (product.equals(appointmentToBook4)) {
				s2.selectByVisibleText(product);
				break;
			}
		}

		WebElement rt = ap.getResourceType();

		while (!rt.isEnabled())// while button is NOT(!) enabled
		{
			System.out.println("Waiting for Resource drop down to not be blank");
		}
		Select s3 = new Select(rt);
//		Thread.sleep(2000);
		List<WebElement> Resources = s3.getOptions();

		int count2 = Resources.size();
		System.out.println(count2);

		for (int k = 0; k < count2; k++) {
			String resource = Resources.get(k).getText();

			if (resource.equals(resourceName2)) {
				s3.selectByVisibleText(resource);
				break;
			}
		}
		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");

		String classtext = ap.getCalendarTomorrow().getAttribute("class");

		if (classtext.contains("cal-out-month")) {
			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");
		}

		ap.getCalendarTomorrow().click();
		Thread.sleep(1000);

		Assert.assertEquals(ap.getBooksNames().getText(), resourceName2);

		WebElement st1 = ap.getSelectTimeMorningButton();

		wait.until(ExpectedConditions.elementToBeClickable(st1));
		while (!st1.isEnabled())// while button is NOT(!) enabled
		{
			System.out.println("Waiting for available times");
		}

		st1.click();
		WebElement st2 = ap.getSelectTime1stAvailable();
//					while (!st2.isEnabled())//while button is NOT(!) enabled
//					{
//					Thread.sleep(200);
//					}

		wait.until(ExpectedConditions.elementToBeClickable(st2));
		startTime = st2.getText();
		// st2.click();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", st2);
		Thread.sleep(1000);

		ap.getPopup1BookButton().click();
//		Thread.sleep(3000);
		wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));

//Verifies that the Total amount is zero dollars
		System.out.println(ap.getTotalAmount().getText());

		Assert.assertTrue(ap.getTotalAmount().getText().contains("$0.00"));

//Verifies that the Pay button is disabled	
		Assert.assertFalse(ap.getPaymentButton().isEnabled());

		rm.memberLogout();
	}

	@Test(priority = 6, description = "Schedule a free appointment due to membership type discount")
	public void ScheduleFreeTrainingDueToDiscount() throws IOException, InterruptedException {
		rm.activeMemberLogin("freemember", "Testing1!");
		rw.waitForDashboardLoaded();

		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);

		rm.catchErrorMessage();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		AppointmentsPO ap = new AppointmentsPO(driver);

		Select s = new Select(ap.getclubs());
		List<WebElement> Clubs = s.getOptions();

		while (!ap.getclubs().isEnabled()) {
			System.out.println("Waiting for Clubs drop down to not be blank");
		}

		int count0 = Clubs.size();
		System.out.println("1 " + count0);

		for (int i = 0; i < count0; i++) {
			String club = Clubs.get(i).getText();

			if (club.equals(clubName)) {
				s.selectByVisibleText(club);
				break;
			}
		}

		WebElement bic = ap.getBookableItemCategory();

		Thread.sleep(2000);

		Select s1 = new Select(bic);
		List<WebElement> ProductCategories = s1.getOptions();

		int count = ProductCategories.size();
		System.out.println("2 " + count);

		for (int i = 0; i < count; i++) {
			String category = ProductCategories.get(i).getText();

			if (category.equals(productCategory)) {
				s1.selectByVisibleText(category);
				break;
			}
		}

		Select s2 = new Select(ap.getBookableItem());
		// Thread.sleep(2000);

		while (!ap.getBookableItem().isEnabled()) {
			System.out.println("Waiting for Product drop down to not be blank");
		}
		List<WebElement> Products = s2.getOptions();

		int count1 = Products.size();
		System.out.println(count1);

		for (int j = 0; j < count1; j++) {
			String product = Products.get(j).getText();

			if (product.equals(appointmentToBook6)) {
				s2.selectByVisibleText(product);
				break;
			}
		}

		WebElement rt = ap.getResourceType();

		while (!rt.isEnabled())// while button is NOT(!) enabled
		{
			System.out.println("Waiting for Resource drop down to not be blank");
		}
		Select s3 = new Select(rt);
//		Thread.sleep(2000);
		List<WebElement> Resources = s3.getOptions();

		int count2 = Resources.size();
		System.out.println(count2);

		for (int k = 0; k < count2; k++) {
			String resource = Resources.get(k).getText();

			if (resource.equals(resourceName3)) {
				s3.selectByVisibleText(resource);
				break;
			}
		}

		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");

		String classtext = ap.getCalendarTomorrow().getAttribute("class");

		if (classtext.contains("cal-out-month")) {
			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");
		}

		ap.getCalendarTomorrow().click();
		Thread.sleep(1000);

		Assert.assertTrue(ap.getBooksNames().getText().contains(resourceName3));

		WebElement st1 = ap.getSelectTimeMorningButton();

		wait.until(ExpectedConditions.elementToBeClickable(st1));
		while (!st1.isEnabled())// while button is NOT(!) enabled
		{
			System.out.println("Waiting for available times");
		}

		st1.click();
		WebElement st2 = ap.getSelectTime1stAvailable();
//					while (!st2.isEnabled())//while button is NOT(!) enabled
//					{
//					Thread.sleep(200);
//					}

		wait.until(ExpectedConditions.elementToBeClickable(st2));
		startTime = st2.getText();
		// st2.click();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", st2);
		Thread.sleep(1000);

		System.out.println(ap.getPopup1Content().getText());
		System.out.println("Time: " + tomorrowsDate + " " + startTime);
		System.out.println("Product: " + appointmentToBook1);
		System.out.println("Resource: " + resourceName3);
		Assert.assertTrue(ap.getPopup1Content().getText().contains("This appointment is free!"));
		Assert.assertTrue(ap.getPopup1Content().getText().contains(clubNameDisplayed));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: " + tomorrowsDate + " " + startTime));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: " + appointmentToBook6));
		Assert.assertTrue(ap.getPopup1Content().getText().contains(resourceName3));

		ap.getPopup1BookButton().click();

		wait.until(ExpectedConditions.stalenessOf(ap.getPopup2OKButton()));

		wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

//Verifies the success message
		Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
		ap.getPopup2OKButton().click();
		Thread.sleep(2000);

//Navigate to Dashboard
		int linkcount = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < linkcount; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

			{
				// rw.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}
		rw.waitForDashboardLoaded();
//Verifies the link navigates to the right page
		Assert.assertEquals("Dashboard", driver.getTitle());
		Thread.sleep(2000);
		rm.ConfirmAndCancelAppointmentNoFee(tomorrowsDate, startTime, appointmentToBook6);

		rm.memberLogout();

	}

	// @AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}