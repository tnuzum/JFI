package SingleMemberAppointments;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
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
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ClubReqPackages_BookAppt_CancelTransaction extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook = "PT 60 Mins-MultiResourcesSelected";
	private static String resourceName = "PT Smith, Andrew";
	private static String clubNameDisplayed = "Club: Studio Jonas";
	private static String startTime;
	private static String tomorrowsDate;
	private static String unitsToBeSelected = "1 - $90.00/per";

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1)
	public void CancelTransaction() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("apptmember3", "Testing1!");
		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);
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

			if (product.equals(appointmentToBook)) {
				s2.selectByVisibleText(product);
				break;
			}
		}

		/*
		 * WebElement rt = ap.getResourceType();
		 * 
		 * while (!rt.isEnabled())// while button is NOT(!) enabled {
		 * System.out.println("Waiting for Resource drop down to not be blank"); }
		 * Select s3 = new Select(rt); // Thread.sleep(2000); List<WebElement> Resources
		 * = s3.getOptions();
		 * 
		 * int count2 = Resources.size(); System.out.println(count2);
		 * 
		 * for (int k = 0; k < count2; k++) { String resource =
		 * Resources.get(k).getText();
		 * 
		 * if (resource.equals(resourceName)) { s3.selectByVisibleText(resource); break;
		 * } }
		 */
		while (ap.getloadingAvailabilityMessage().size()!=0)
		{
			System.out.println("waiting1");
			Thread.sleep(1000);
		}
		
		System.out.println("came out of the loop");
		
		String classtext = ap.getCalendarTomorrow().getAttribute("class");

		if (classtext.contains("cal-out-month"))
		{
			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();
			while (ap.getloadingAvailabilityMessage().size()!=0)
			{
				System.out.println("waiting1");
				Thread.sleep(1000);
			}
			
			System.out.println("came out of the loop");
		}

		ap.getCalendarTomorrow().click();
		Thread.sleep(1000);

		Assert.assertTrue(ap.getBooksNames().getText().contains(resourceName));

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
		
		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		tomorrowsDate = dateFormat1.format(today1.getTime());
		
		Assert.assertTrue(ap.getPopup1Content().getText().contains(clubNameDisplayed));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: "+tomorrowsDate+" " +startTime));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: "+appointmentToBook ));
		Assert.assertTrue(ap.getPopup1Content().getText().contains( resourceName));
		
		Assert.assertEquals(ap.getPopup1Title().getText(),
				"Package Required");
		
		Assert.assertTrue(ap.getPopup1Content().getText().contains("This appointment requires a package purchase."));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Would you like to continue?"));
		
		ap.getPopup1BookButton().click();
		Thread.sleep(3000);

		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		Assert.assertEquals("Appointments", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Book Appointment", BT.getBreadcrumb2().getText());
		Assert.assertEquals(ap.getAppointmentName().getText(), appointmentToBook);
		Assert.assertEquals(ap.getClubName().getText(), clubNameDisplayed);
		Assert.assertEquals(ap.getAppointmentTime().getText(), "Start Time: " + startTime);
		Assert.assertEquals(ap.getAppointmentName().getText(), appointmentToBook);
		Assert.assertEquals("Date: " + tomorrowsDate, ap.getAppointmentDate().getText());
		
		for (int i = 0; i< ap.getReviewSection().size(); i++)
		{
			if (ap.getReviewSection().get(i).getText().contains("REVIEW"))
				
			{
				Assert.assertTrue(ap.getReviewSection().get(i).getText().contains("PACKAGE REQUIRED"));
				Assert.assertTrue(ap.getReviewSection().get(i).getText().contains("This appointment requires a package."));
				Assert.assertTrue(ap.getReviewSection().get(i).getText().contains(
				"We noticed you do not have an existing package that satisfies this appointment so we have included the correct package for you."));
			}
		}
		while (ap.getRateBox().getText().isBlank()) {
			System.out.println("Waiting");
		}
		Assert.assertTrue(ap.getRateBox().getText().contains(appointmentToBook.toUpperCase()));

		Select s4 = new Select(
				driver.findElement(By.xpath("//select[contains(@class, 'at-appointments-checkout-dropdown')]")));
		List<WebElement> UnitRates = s4.getOptions();

		int count4 = UnitRates.size();
		System.out.println("4 " + count4);

		for (int i = 0; i < count4; i++) {
			String unitRate = UnitRates.get(i).getText();
			System.out.println(unitRate);

			if (unitRate.contains(unitsToBeSelected)) {
				s4.selectByVisibleText(unitRate);
				break;
			}
		}

		wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));
		System.out.println(ap.getTotalAmount().getText());

		String[] totalAmt = ap.getTotalAmount().getText().split(": ");
		String FormatTotalAmt = totalAmt[1].trim();
		System.out.println(FormatTotalAmt);
		// Verifies the Pay button contains the total amount

		Assert.assertTrue(ap.getPaymentButton().getText().contains(FormatTotalAmt));
		
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		int paymentMethodscount = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < paymentMethodscount; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
					.contains("5454"))
				{
				
					PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).click();
					break;
				}
		}

		// Click the Cancel button
		
		ap.getCancelButton().click();
		Thread.sleep(2000);
		Boolean ApptCheckout = reusableMethods.isElementPresent(By.xpath("//div[@class='row ng-star-inserted']"));
		
		Assert.assertTrue(ApptCheckout == false);
		reusableMethods.returnToDashboard();
	}

	
	
	 // @AfterTest
	 
	 @AfterClass 
	 public void teardown() throws InterruptedException {
	  driver.close(); driver = null; }
	

}