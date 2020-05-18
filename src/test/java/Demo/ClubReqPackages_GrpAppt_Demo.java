package Demo;

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

public class ClubReqPackages_GrpAppt_Demo extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training 1";
	private static String appointmentToBook = "PT Group-Demo";
	private static String resourceName = "FitExpert1";
	private static String additionalResourceName = "Gym";
	private static String clubNameDisplayed = "Club: Studio Jonas";
	private static String mssClubPricing = "$5.15";
	private static String startTime;
	;
	private static int appointmentsCount;
	private static String participant2 = "Auto, Daisy";
	private static String unitsToBeSelected = "1 - $5.00/per";
	private static String membername = "Demomember Auto";

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1)
	public void ScheduleAppointment() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("demo", "Testing1!");
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

			if (product.equals(appointmentToBook)) {
				s2.selectByVisibleText(product);
				break;
			}
		}
		 Assert.assertEquals(ap.getGroupApptsHeader().getText(), "Group Appointments");
			Assert.assertEquals(ap.getGroupMinPersons().getText(), "1");
			Assert.assertEquals(ap.getGroupMaxPersons().getText(), "2");
			ap.getGroupMemberSearchInput().sendKeys("auto");
			ap.getGroupMemberSearchButton().click();
			
			Thread.sleep(2000);
			
			int memberCount = ap.getGroupPopupAddButtons().size();
			for (int i = 0; i<memberCount; i++)
				  
			{
				String text = ap.getGroupPopupMembers().get(i).getText();
				System.out.println(text);
				if (ap.getGroupPopupMembers().get(i).getText().contains("Daisy"))
					{wait.until(ExpectedConditions.elementToBeClickable(ap.getGroupPopupAddButtons().get(i)));
					ap.getGroupPopupAddButtons().get(i).click();
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
		Assert.assertTrue(ap.getGroup().getText().contains(participant2));
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
		
		int additionalResourcesCount = ap.getAdditionalResources().size();

		for (int n = 0; n < additionalResourcesCount; n++) {
			if (ap.getAdditionalResources().get(n).getText().contains(additionalResourceName))
				ap.getAdditionalResources().get(n).click();
		}
		Thread.sleep(1000);
		
		while (ap.getRateBox().getText().isBlank())
		{
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
		Thread.sleep(1000);

		

		// Noting down the total amount
		wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));
		System.out.println(ap.getTotalAmount().getText());

		String[] totalAmt = ap.getTotalAmount().getText().split(": ");
		String FormatTotalAmt = totalAmt[1].trim();
		System.out.println(FormatTotalAmt);
		
		Assert.assertEquals(FormatTotalAmt, mssClubPricing);
		
		/*	PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		
		PM.getNewCardButton().click();
		Thread.sleep(1000);
		
		String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
		while (opacity.contains("1")) {
			PM.getNewCardButton().click();
			opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
		}

		Assert.assertTrue(PM.getCloseButton().isDisplayed());
		Assert.assertFalse(ap.getPaymentButton().isEnabled());
		System.out.println("Pay Button disabled:" + ap.getPaymentButton().getAttribute("disabled"));

//		System.out.println(PM.getNameOnCardField().getAttribute("value"));
 		Assert.assertEquals(membername,PM.getNameOnCardField().getAttribute("value"));
 		
		PM.getCardNumberField().sendKeys("4111111111111111");
		PM.getExpirationMonth().sendKeys("12");
		PM.getExpirationYear().sendKeys("29");
		PM.getSecurityCode().sendKeys("123");
		Thread.sleep(1000);
		PM.getCheckBox().click();
		while (!ap.getPaymentButton().isEnabled()) {
			Thread.sleep(1000);
		}
		ap.getPaymentButton().click();
		Thread.sleep(1000);
		System.out.println(PM.getPopupContent().getText());
		Assert.assertTrue(PM.getPopupContent().getText().contains("A signature is required to continue."));
		PM.getPopupOk().click();
		Thread.sleep(1000);
		PM.getSaveCardNo().click();*/
		
		// Verifies the Pay button contains the total amount

		Assert.assertTrue(ap.getPaymentButton().getText().contains(FormatTotalAmt));

		// Click the Pay button
		while (!ap.getPaymentButton().isEnabled()) {
			Thread.sleep(1000);
		}
		ap.getPaymentButton().click();

		wait.until(ExpectedConditions.stalenessOf(ap.getPopup2OKButton()));

		wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

//Verifies the success message
		Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
		ap.getPopup2OKButton().click();
		Thread.sleep(1000);
		ThankYouPO TY = new ThankYouPO(driver);

//Verifies the text on Thank You page and the links to navigate to Dashboard and other pages are displayed
		reusableMethods.ThankYouPageValidations();

//Note down the Receipt number
		String receiptNumber = TY.getReceiptNumber().getText();
		
		Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
		TY.getPrintReceiptButton().click();
		Thread.sleep(2000);
		Assert.assertTrue(TY.getReceiptPopup().isDisplayed());

//Verifies the buttons on Print Receipt Popup
		reusableMethods.ReceiptPopupValidations();

		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
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

//Note the package units after purchase
		int IntUnitCountAfter = reusableMethods.getPackageUnits(appointmentToBook);
//System.out.println(IntUnitCountAfter);

//Verifies the package units is now incremented by one unit

		Assert.assertEquals(IntUnitCountAfter, 1); // verifies the unit count of the Package

	
	}

	

	@Test(priority = 2)
	public void ConfirmAndCancelAppointment() throws IOException, InterruptedException {
		Thread.sleep(3000);

		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);

		int appointmentsCount = d.getMyAppts().size();
		for (int k = 0; k < appointmentsCount; k++) {
			if (d.getMyAppts().get(k).getText().contains(tomorrowsDate))

			{

				if (d.getMyAppts().get(k).getText().contains(startTime)) {
					wait.until(ExpectedConditions.elementToBeClickable(d.getMyAppts().get(k).findElement(By.tagName("i"))));
					d.getMyAppts().get(k).findElement(By.tagName("i")).click();
					
//					Thread.sleep(5000);
					WebElement EditButton = d.getEditButton().get(k);		
										
				
					
					wait.until(ExpectedConditions.visibilityOf(EditButton));
					wait.until(ExpectedConditions.elementToBeClickable(EditButton));
					
					EditButton.click();
					break;
				}
			}
		}
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
		AppointmentsPO ap = new AppointmentsPO(driver);
		Assert.assertEquals(ap.getEditApptPageHeader().getText(), "Edit Appointment");
		ap.getEditApptCancelButton().click();
		Assert.assertTrue(ap.getCancelFeeSection().getText().contains("There is a fee for cancelling this appointment."));
		Assert.assertTrue(ap.getCancelFeeSection().getText().contains("If you proceed, you will be charged a fee of:"));
		
		
					Thread.sleep(1000);
					
				wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));
				
				System.out.println(ap.getTotalAmount().getText());

				String[] totalAmt = ap.getTotalAmount().getText().split(": ");
				String FormatTotalAmt = totalAmt[1].trim();
				System.out.println(FormatTotalAmt);
				// Verifies the Pay button contains the total amount
				
				PaymentMethodsPO PM = new PaymentMethodsPO(driver);

				Assert.assertTrue(PM.getPaymentButton().getText().contains(FormatTotalAmt));
				
				// Click the Pay button
				while (!PM.getPaymentButton().isEnabled()) {
					Thread.sleep(1000);
				}
				PM.getPaymentButton().click();

				wait.until(ExpectedConditions.stalenessOf(ap.getPopup2OKButton()));

				wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

		//Verifies the success message
				Assert.assertEquals(ap.getPopup2Title().getText(), "Success");
				Assert.assertTrue(ap.getPopup2Content().getText().contains("Your appointment has been cancelled."));
				ap.getPopup2OKButton().click();
				Thread.sleep(1000);
				ThankYouPO TY = new ThankYouPO(driver);

		//Verifies the text on Thank You page and the links to navigate to Dashboard and other pages are displayed
				reusableMethods.ThankYouPageValidations();

		
				Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
				TY.getPrintReceiptButton().click();
				Thread.sleep(2000);
				Assert.assertTrue(TY.getReceiptPopup().isDisplayed());

		//Verifies the buttons on Print Receipt Popup
				reusableMethods.ReceiptPopupValidations();
				Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
						.contains(FormatTotalAmt));

				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
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
	
	// @AfterTest	 
/*	 @AfterClass 
	 public void teardown() throws InterruptedException {
	  driver.close(); driver = null; }*/
	

}