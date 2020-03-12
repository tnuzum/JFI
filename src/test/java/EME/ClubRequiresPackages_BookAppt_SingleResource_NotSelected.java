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

import pageObjects.AppointmentsPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.CartPO;
import pageObjects.DashboardPO;
import pageObjects.PurchaseConfirmationPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ClubRequiresPackages_BookAppt_SingleResource_NotSelected extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook = "PT 60 Mins-SingleResourceNotSelect";
	private static String resourceName = "Holmes, Jeff";
	private static String clubNameDisplayed = "ClubName: Studio Jonas";
	private static String startTime = "Start Time: 12:00 AM";
	
	private static String unitsToBeSelected = "1 - $90.00/per"; 
	
//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1)
	public void ScheduleAppointment() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("emailmember", "Testing1!");
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

		System.out.println("1 "+count0);

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

		System.out.println("2 "+count);

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


			if (product.equals(appointmentToBook)) {

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


			if (resource.equals(resourceName)) {

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
		

		Assert.assertEquals(ap.getBooksNames().getText(), "HOLMES, JEFF");

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
		st2.click();
		Assert.assertEquals(ap.getPopup1Content().getText(), "This appointment requires a package purchase. Would you like to continue?");
		
		ap.getPopup1BookButton().click();
		Thread.sleep(3000);
		
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		Assert.assertEquals("Appointments", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Book Appointment", BT.getBreadcrumb2().getText());
		Assert.assertEquals(ap.getAppointmentName().getText(), appointmentToBook);
		Assert.assertEquals(ap.getClubName().getText(), clubNameDisplayed);
		Assert.assertEquals(ap.getAppointmentTime().getText(), startTime);
		Assert.assertEquals(ap.getAppointmentName().getText(), appointmentToBook);
		
		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDayAndDate = dateFormat1.format(today1.getTime());

		Assert.assertEquals("Date: " + tomorrowsDayAndDate, ap.getAppointmentDate().getText());
		Assert.assertTrue(ap.getReviewSection().getText().contains("REVIEW"));
		Assert.assertTrue(ap.getReviewSection().getText().contains("PACKAGE REQUIRED"));
		Assert.assertTrue(ap.getReviewSection().getText().contains("This appointment requires a package."));
		Assert.assertTrue(ap.getReviewSection().getText().contains("We noticed you do not have an existing package that satisfies this appointment so we have included the correct package for you."));
		
		while(ap.getRateBox().getText().isBlank())
				{
			System.out.println("Waiting");
				}
		Assert.assertTrue(ap.getRateBox().getText().contains(appointmentToBook.toUpperCase()));
		
		Select s4 = new Select(driver.findElement(By.xpath("//select[contains(@class, 'form-control')]")));
		List<WebElement> UnitRates = s4.getOptions();
		
		int count4 = UnitRates.size();
		System.out.println("4 "+count4);

		for (int i = 0; i < count4; i++) {
			String unitRate = UnitRates.get(i).getText();
			System.out.println(unitRate);
			
			if (unitRate.contains(unitsToBeSelected)) {
				s4.selectByVisibleText(unitRate);
				break;
			}
		}
		
		
		
		// Noting down the total amount
		while (ap.getTotalAmount().getText().isBlank())
		{
			Thread.sleep(500);
		}
		System.out.println(ap.getTotalAmount().getText());
		
		String[] totalAmt = ap.getTotalAmount().getText().split(": ");
		String FormatTotalAmt = totalAmt[1].trim();
		System.out.println(FormatTotalAmt);
	}

	public void ConfirmAppointmentIsScheduled() throws IOException, InterruptedException {
		// reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//appointmentswidget/div/div[2]/div[1]/div/div/a/div/div[2]/span/strong")));
		Assert.assertFalse(d.getMyApptsAppt1Title().getText().isBlank());
	}

	@Test(priority = 3)
	public void CancelAppointment() throws IOException, InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		d.getMyApptsAppt1GearButton().click();
		WebElement wait1 = d.getMyApptsEditButton();
		while (!wait1.isEnabled())// while button is NOT(!) enabled
		{
//			Thread.sleep(200);
		}
		d.getMyApptsEditButton().click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
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
//			boolean result2 = reusableWaits.popupMessageYesButton();
//			if (result2 == true)
//			{
//				Thread.sleep(500);	
//			}
//		a.getEditApptCanceledOKButton().click();
//		reusableWaits.waitForDashboardLoaded();
		Thread.sleep(2000);
		Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
		reusableMethods.memberLogout();
	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}


}

