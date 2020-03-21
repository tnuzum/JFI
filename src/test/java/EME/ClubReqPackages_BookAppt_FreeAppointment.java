package EME;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.AppointmentsPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.CartPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ThankYouPO;
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
	private static String appointmentToBook4 = "Free MSSServiceV Auto";
	private static String appointmentToBook5 = "Free Training Auto 3 Res";
	private static String additionalResourceName = "Gym";
	private static String resourceName1 = "Holmes, Jeff";
	private static String resourceName2 = "PT Smith, Andrew";
	private static String resourceName3 = "Fit Expert2";
	private static String clubNameDisplayed = "Club: Studio Jonas";
	private static String startTime;
	private static String tomorrowsDate;
	

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1)
	public void ScheduleFreeTraining() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("bauto", "Testing1!");
		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
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
			String category = Clubs.get(i).getText();

			if (category.equals(clubName)) {
				s.selectByVisibleText(category);
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

		boolean result1 = reusableWaits.loadingAvailability();
		while (result1 == true) {
//						Thread.sleep(500);	
		}
		Boolean TomorrowDatePresent = reusableMethods
				.isElementPresent(By.xpath("(//mwl-calendar-month-cell[contains(@class,'future')])[1]"));
		if (TomorrowDatePresent == false) {

			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();

			result1 = reusableWaits.loadingAvailability();
			while (result1 == true) {
//							Thread.sleep(500);	
			}
		}

		ap.getCalendarTomorrow().click();
		Thread.sleep(3000);

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
		st2.click();
		
		DateFormat dateFormat1 = new SimpleDateFormat("M/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		tomorrowsDate = dateFormat1.format(today1.getTime());
		System.out.println(ap.getPopup1Content().getText());
		System.out.println("Time: "+tomorrowsDate+", " +startTime);
		System.out.println("Product: "+appointmentToBook1 );
		System.out.println("Resource: "+ resourceName1);
		Assert.assertTrue(ap.getPopup1Content().getText().contains("This appointment is free!"));
		Assert.assertTrue(ap.getPopup1Content().getText().contains(clubNameDisplayed));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: "+tomorrowsDate+", " +startTime));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: "+appointmentToBook1 ));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Resource: "+ resourceName1));
		
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
				// reusableWaits.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}
		reusableWaits.waitForDashboardLoaded();
//Verifies the link navigates to the right page
		Assert.assertEquals("Dashboard", driver.getTitle());
		Thread.sleep(2000);

	}

	@Test(priority = 2, dependsOnMethods = "ScheduleFreeTraining")
	public void ConfirmAndCancelTraining() throws IOException, InterruptedException {
		// reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
		int appointmentsCount = d.getMyAppts().size();

		for (int i = 0; i < appointmentsCount; i++) {
			if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

			{

				if (d.getMyAppts().get(i).getText().contains(startTime)) {
					Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook1.toUpperCase()));
				}
			}
		}
		
		
		d.getMyApptsAppt1GearButton().click();
		WebElement wait1 = d.getMyApptsEditButton();
		while (!wait1.isEnabled())// while button is NOT(!) enabled
		{
//			Thread.sleep(200);
		}
		d.getMyApptsEditButton().click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
		AppointmentsPO a = new AppointmentsPO(driver);
		Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
		a.getEditApptCancelButton().click();
		WebElement wait2 = a.getEditApptProceedButton();
		while (!wait2.isEnabled())// while button is NOT(!) enabled
		{
//			Thread.sleep(200);
		}
		a.getEditApptProceedButton().click();
		boolean result1 = reusableWaits.popupMessageYesButton();
		if (result1 == true) {
//				Thread.sleep(500);	
		}
		a.getEditApptCancelYesButton().click();
//		
		Thread.sleep(2000);
		Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
		reusableMethods.memberLogout();
	}
	
	@Test(priority = 3)
	public void ScheduleFreeServiceV() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("bauto", "Testing1!");
		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
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
			String category = Clubs.get(i).getText();

			if (category.equals(clubName)) {
				s.selectByVisibleText(category);
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

		boolean result1 = reusableWaits.loadingAvailability();
		while (result1 == true) {
//						Thread.sleep(500);	
		}
		Boolean TomorrowDatePresent = reusableMethods
				.isElementPresent(By.xpath("(//mwl-calendar-month-cell[contains(@class,'future')])[1]"));
		if (TomorrowDatePresent == false) {

			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();

			result1 = reusableWaits.loadingAvailability();
			while (result1 == true) {
//							Thread.sleep(500);	
			}
		}

		ap.getCalendarTomorrow().click();
		Thread.sleep(3000);

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
		st2.click();
		
		DateFormat dateFormat1 = new SimpleDateFormat("M/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		tomorrowsDate = dateFormat1.format(today1.getTime());
		System.out.println(ap.getPopup1Content().getText());
		System.out.println("Time: "+tomorrowsDate+", " +startTime);
		System.out.println("Product: "+appointmentToBook2 );
		System.out.println("Resource: "+ resourceName2);
		Assert.assertTrue(ap.getPopup1Content().getText().contains("This appointment is free!"));
		Assert.assertTrue(ap.getPopup1Content().getText().contains(clubNameDisplayed));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: "+tomorrowsDate+", " +startTime));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: "+appointmentToBook2 ));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Resource: "+ resourceName2));
		
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
				// reusableWaits.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}
		reusableWaits.waitForDashboardLoaded();
//Verifies the link navigates to the right page
		Assert.assertEquals("Dashboard", driver.getTitle());
		Thread.sleep(2000);

	}

	@Test(priority = 4, dependsOnMethods = "ScheduleFreeServiceV" )
	public void ConfirmAndCancelServiceV() throws IOException, InterruptedException {
		// reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
		int appointmentsCount = d.getMyAppts().size();

		for (int i = 0; i < appointmentsCount; i++) {
			if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

			{

				if (d.getMyAppts().get(i).getText().contains(startTime)) {
					Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook2.toUpperCase()));
				}
			}
		}
		
		
		d.getMyApptsAppt1GearButton().click();
		WebElement wait1 = d.getMyApptsEditButton();
		while (!wait1.isEnabled())// while button is NOT(!) enabled
		{
//			Thread.sleep(200);
		}
		d.getMyApptsEditButton().click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
		AppointmentsPO a = new AppointmentsPO(driver);
		Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
		a.getEditApptCancelButton().click();
		WebElement wait2 = a.getEditApptProceedButton();
		while (!wait2.isEnabled())// while button is NOT(!) enabled
		{
//			Thread.sleep(200);
		}
		a.getEditApptProceedButton().click();
		boolean result1 = reusableWaits.popupMessageYesButton();
		if (result1 == true) {
//				Thread.sleep(500);	
		}
		a.getEditApptCancelYesButton().click();
//		
		Thread.sleep(2000);
		Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
		reusableMethods.memberLogout();
	}

	@Test (priority = 5)
	public void ScheduleFreeMSSTraining() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("bauto", "Testing1!");
		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
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
			String category = Clubs.get(i).getText();

			if (category.equals(clubName)) {
				s.selectByVisibleText(category);
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

		boolean result1 = reusableWaits.loadingAvailability();
		while (result1 == true) {
//						Thread.sleep(500);	
		}
		Boolean TomorrowDatePresent = reusableMethods
				.isElementPresent(By.xpath("(//mwl-calendar-month-cell[contains(@class,'future')])[1]"));
		if (TomorrowDatePresent == false) {

			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();

			result1 = reusableWaits.loadingAvailability();
			while (result1 == true) {
//							Thread.sleep(500);	
			}
		}

		ap.getCalendarTomorrow().click();
		Thread.sleep(3000);

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
		st2.click();
		
		DateFormat dateFormat1 = new SimpleDateFormat("M/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		tomorrowsDate = dateFormat1.format(today1.getTime());
		System.out.println(ap.getPopup1Content().getText());
		System.out.println("Time: "+tomorrowsDate+", " +startTime);
		System.out.println("Product: "+appointmentToBook3 );
		System.out.println("Resource: "+ resourceName1);
		Assert.assertTrue(ap.getPopup1Content().getText().contains("This appointment is free!"));
		Assert.assertTrue(ap.getPopup1Content().getText().contains(clubNameDisplayed));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: "+tomorrowsDate+", " +startTime));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: "+appointmentToBook3 ));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Resource: "+ resourceName1));
		
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
				// reusableWaits.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}
		reusableWaits.waitForDashboardLoaded();
//Verifies the link navigates to the right page
		Assert.assertEquals("Dashboard", driver.getTitle());
		Thread.sleep(2000);

	}

	@Test(priority = 6, dependsOnMethods = "ScheduleFreeMSSTraining")
	public void ConfirmAndCancelFreeMSSTraining() throws IOException, InterruptedException {
		// reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
		int appointmentsCount = d.getMyAppts().size();

		for (int i = 0; i < appointmentsCount; i++) {
			if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

			{

				if (d.getMyAppts().get(i).getText().contains(startTime)) {
					Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook3.toUpperCase()));
				}
			}
		}
		
		
		d.getMyApptsAppt1GearButton().click();
		WebElement wait1 = d.getMyApptsEditButton();
		while (!wait1.isEnabled())// while button is NOT(!) enabled
		{
//			Thread.sleep(200);
		}
		d.getMyApptsEditButton().click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
		AppointmentsPO a = new AppointmentsPO(driver);
		Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
		a.getEditApptCancelButton().click();
		WebElement wait2 = a.getEditApptProceedButton();
		while (!wait2.isEnabled())// while button is NOT(!) enabled
		{
//			Thread.sleep(200);
		}
		a.getEditApptProceedButton().click();
		boolean result1 = reusableWaits.popupMessageYesButton();
		if (result1 == true) {
//				Thread.sleep(500);	
		}
		a.getEditApptCancelYesButton().click();
//		
		Thread.sleep(2000);
		Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
		reusableMethods.memberLogout();
	}
	
	@Test(priority = 7)
	public void ScheduleFreeMSSServiceV() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("bauto", "Testing1!");
		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
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
			String category = Clubs.get(i).getText();

			if (category.equals(clubName)) {
				s.selectByVisibleText(category);
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

		boolean result1 = reusableWaits.loadingAvailability();
		while (result1 == true) {
//						Thread.sleep(500);	
		}
		Boolean TomorrowDatePresent = reusableMethods
				.isElementPresent(By.xpath("(//mwl-calendar-month-cell[contains(@class,'future')])[1]"));
		if (TomorrowDatePresent == false) {

			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();

			result1 = reusableWaits.loadingAvailability();
			while (result1 == true) {
//							Thread.sleep(500);	
			}
		}

		ap.getCalendarTomorrow().click();
		Thread.sleep(3000);

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
		st2.click();
		
		DateFormat dateFormat1 = new SimpleDateFormat("M/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		tomorrowsDate = dateFormat1.format(today1.getTime());
		System.out.println(ap.getPopup1Content().getText());
		System.out.println("Time: "+tomorrowsDate+", " +startTime);
		System.out.println("Product: "+appointmentToBook4 );
		System.out.println("Resource: "+ resourceName2);
		Assert.assertTrue(ap.getPopup1Content().getText().contains("This appointment is free!"));
		Assert.assertTrue(ap.getPopup1Content().getText().contains(clubNameDisplayed));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: "+tomorrowsDate+", " +startTime));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: "+appointmentToBook4 ));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Resource: "+ resourceName2));
		
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
				// reusableWaits.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}
		reusableWaits.waitForDashboardLoaded();
//Verifies the link navigates to the right page
		Assert.assertEquals("Dashboard", driver.getTitle());
		Thread.sleep(2000);

	}

	@Test(priority = 8, dependsOnMethods = "ScheduleFreeMSSServiceV")
	public void ConfirmAndCancelFreeMSSServiceV() throws IOException, InterruptedException {
		// reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
		int appointmentsCount = d.getMyAppts().size();

		for (int i = 0; i < appointmentsCount; i++) {
			if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

			{

				if (d.getMyAppts().get(i).getText().contains(startTime)) {
					Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook4.toUpperCase()));
				}
			}
		}
		
		
		d.getMyApptsAppt1GearButton().click();
		WebElement wait1 = d.getMyApptsEditButton();
		while (!wait1.isEnabled())// while button is NOT(!) enabled
		{
//			Thread.sleep(200);
		}
		d.getMyApptsEditButton().click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
		AppointmentsPO a = new AppointmentsPO(driver);
		Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
		a.getEditApptCancelButton().click();
		WebElement wait2 = a.getEditApptProceedButton();
		while (!wait2.isEnabled())// while button is NOT(!) enabled
		{
//			Thread.sleep(200);
		}
		a.getEditApptProceedButton().click();
		boolean result1 = reusableWaits.popupMessageYesButton();
		if (result1 == true) {
//				Thread.sleep(500);	
		}
		a.getEditApptCancelYesButton().click();
//		
		Thread.sleep(2000);
		Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
		reusableMethods.memberLogout();
	}
	

	@Test (priority = 9)
	public void ScheduleFreeTrainingWithThreeResources() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("bauto", "Testing1!");
		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
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
			String category = Clubs.get(i).getText();

			if (category.equals(clubName)) {
				s.selectByVisibleText(category);
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

		boolean result1 = reusableWaits.loadingAvailability();
		while (result1 == true) {
//						Thread.sleep(500);	
		}
		Boolean TomorrowDatePresent = reusableMethods
				.isElementPresent(By.xpath("(//mwl-calendar-month-cell[contains(@class,'future')])[1]"));
		if (TomorrowDatePresent == false) {

			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();

			result1 = reusableWaits.loadingAvailability();
			while (result1 == true) {
//							Thread.sleep(500);	
			}
		}

		ap.getCalendarTomorrow().click();
		Thread.sleep(3000);

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
		st2.click();
		Thread.sleep(1000);
		int additionalResourcesCount = ap.getAdditionalResources().size();

		for (int n = 0; n < additionalResourcesCount; n++) {
			if (ap.getAdditionalResources().get(n).getText().contains(additionalResourceName))
				ap.getAdditionalResources().get(n).click();
		}
		
		ap.getbookButton().click();
		Thread.sleep(2000);
		
		DateFormat dateFormat1 = new SimpleDateFormat("M/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		tomorrowsDate = dateFormat1.format(today1.getTime());
		
		System.out.println(ap.getPopup1Content().getText());
		System.out.println("Time: "+tomorrowsDate+", " +startTime);
		System.out.println("Product: "+appointmentToBook3 );
		System.out.println("Resource: "+ resourceName1);
		Assert.assertTrue(ap.getPopup1Content().getText().contains("This appointment is free!"));
		Assert.assertTrue(ap.getPopup1Content().getText().contains(clubNameDisplayed));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: "+tomorrowsDate+", " +startTime));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: "+appointmentToBook3 ));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Resource: "+ resourceName1));
		
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
				// reusableWaits.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}
		reusableWaits.waitForDashboardLoaded();
//Verifies the link navigates to the right page
		Assert.assertEquals("Dashboard", driver.getTitle());
		Thread.sleep(2000);

	}

	@Test(priority = 10, dependsOnMethods = "ScheduleFreeTrainingWithThreeResources")
	public void ConfirmAndCancelFreeTrainingWithThreeResources() throws IOException, InterruptedException {
		// reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
		int appointmentsCount = d.getMyAppts().size();

		for (int i = 0; i < appointmentsCount; i++) {
			if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

			{

				if (d.getMyAppts().get(i).getText().contains(startTime)) {
					Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook5.toUpperCase()));
				}
			}
		}
		
		
		d.getMyApptsAppt1GearButton().click();
		WebElement wait1 = d.getMyApptsEditButton();
		while (!wait1.isEnabled())// while button is NOT(!) enabled
		{
//			Thread.sleep(200);
		}
		d.getMyApptsEditButton().click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
		AppointmentsPO a = new AppointmentsPO(driver);
		Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
		a.getEditApptCancelButton().click();
		WebElement wait2 = a.getEditApptProceedButton();
		while (!wait2.isEnabled())// while button is NOT(!) enabled
		{
//			Thread.sleep(200);
		}
		a.getEditApptProceedButton().click();
		boolean result1 = reusableWaits.popupMessageYesButton();
		if (result1 == true) {
//				Thread.sleep(500);	
		}
		a.getEditApptCancelYesButton().click();
//		
		Thread.sleep(2000);
		Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
		reusableMethods.memberLogout();
	}
	

	// @AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}