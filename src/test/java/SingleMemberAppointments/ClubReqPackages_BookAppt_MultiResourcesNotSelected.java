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
import pageObjects.PaymentMethodsPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ClubReqPackages_BookAppt_MultiResourcesNotSelected extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook = "PT 60 Mins-MultiResourcesNotSelected";
	private static String resourceName = "FitExpert1";
	private static String additionalResourceName = "Gym";
	private static String clubNameDisplayed = "Club: Studio Jonas";
	private static String startTime;
	private static int appointmentsCount;
	private static String unitsToBeSelected = "1 - $5.00/per";
	private static String membername = "ApptMember1 Auto";

	public reusableWaits rw;

	public reusableMethods rm;

	public ClubReqPackages_BookAppt_MultiResourcesNotSelected() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

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
	public void ScheduleAppointment() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("apptmember1", "Testing1!");
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

			// Actions a = new Actions(driver);
			// a.click(ap.getCalendarTomorrow()).build().perform();
			ap.getCalendarTomorrow().click();
			System.out.println("Calendar date clicked for " + this.getClass().getSimpleName());
			log.info("Calendar Date Clicked for " + this.getClass().getSimpleName());

			Thread.sleep(1000);

			rw.waitForSelectATimeToOpen();

			rm.OpenSelectATimeDrawerIfNotOpenedInFirstAttempt(ap.getCalendarTomorrow());

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
			// st2.click();
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", st2);
			Thread.sleep(2000);

			Assert.assertTrue(ap.getPopup1Content().getText().contains(clubNameDisplayed));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: " + tomorrowsDate + " " + startTime));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: " + appointmentToBook));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(resourceName));

			Assert.assertEquals(ap.getPopup1Title().getText(), "Package Required");

			Assert.assertTrue(
					ap.getPopup1Content().getText().contains("This appointment requires a package purchase."));
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

			for (int i = 0; i < ap.getReviewSection().size(); i++) {
				if (ap.getReviewSection().get(i).getText().contains("REVIEW"))

				{
					Assert.assertTrue(ap.getReviewSection().get(i).getText().contains("PACKAGE REQUIRED"));
					Assert.assertTrue(
							ap.getReviewSection().get(i).getText().contains("This appointment requires a package."));
					Assert.assertTrue(ap.getReviewSection().get(i).getText().contains(
							"We noticed you do not have an existing package that satisfies this appointment so we have included the correct package for you."));
				}
			}
			while (ap.getRateBox().getText().isBlank()) {
				System.out.println("Waiting");
			}
			wait.until(ExpectedConditions.textToBePresentInElement(ap.getRateBox(), appointmentToBook.toUpperCase()));

			rm.verifyLowestNumberOfUnitsIsSelectedByDefault(unitsToBeSelected);
			Thread.sleep(1000);

			int additionalResourcesCount = ap.getAdditionalResources().size();

			for (int n = 0; n < additionalResourcesCount; n++) {
				if (ap.getAdditionalResources().get(n).getText().contains(additionalResourceName))
					ap.getAdditionalResources().get(n).click();
			}

			// Noting down the total amount
			wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));
			System.out.println(ap.getTotalAmount().getText());

			String[] totalAmt = ap.getTotalAmount().getText().split(": ");
			String FormatTotalAmt = totalAmt[1].trim();
			System.out.println(FormatTotalAmt);

			PaymentMethodsPO PM = new PaymentMethodsPO(driver);

			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			PM.getNewCardButton().click();
			Thread.sleep(1000);

			String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			while (opacity.contains("1")) {
				PM.getNewCardButton().click();
				opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			}

			wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

			Assert.assertTrue(PM.getCloseButton().isDisplayed());
			Assert.assertFalse(ap.getPaymentButton().isEnabled());
			System.out.println("Pay Button disabled:" + ap.getPaymentButton().getAttribute("disabled"));

//		System.out.println(PM.getNameOnCardField().getAttribute("value"));
			Assert.assertEquals(membername, PM.getNameOnCardField().getAttribute("value"));

			PM.getCardNumberField().sendKeys("4111111111111111");
			PM.getExpirationMonth().sendKeys("04");
			PM.getExpirationYear().sendKeys("22");
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
			PM.getSaveCardNo().click();
			Thread.sleep(1000);
			// Verifies the Pay button contains the total amount

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
			rm.ThankYouPageValidations();

//Note down the Receipt number
			String receiptNumber = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber));

//Verifies the buttons on Print Receipt Popup
			rm.ReceiptPopupValidations();

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
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

//Note the package units after purchase
			int IntUnitCountAfter = rm.getPackageUnits(appointmentToBook);
//System.out.println(IntUnitCountAfter);

//Verifies the package units is now incremented by one unit

			Assert.assertEquals(IntUnitCountAfter, 1); // verifies the unit count of the Package

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		} catch (java.lang.AssertionError ae) {
			log.error(ae.getMessage(), ae);
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		}

	}

	@Test(priority = 2)
	public void ConfirmAppointmentIsScheduled() throws IOException, InterruptedException {

		try {
			// rw.waitForDashboardLoaded();
			DashboardPO d = new DashboardPO(driver);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
					By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
			int appointmentsCount = d.getMyAppts().size();

			for (int i = 0; i < appointmentsCount; i++) {
				if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

				{

					if (d.getMyAppts().get(i).getText().contains(startTime)) {
						Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook.toUpperCase()));
					}
				}
			}
			rm.memberLogout();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		} catch (java.lang.AssertionError ae) {
			log.error(ae.getMessage(), ae);
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		}

	}

	@Test(priority = 3)
	public void CancelAppointment() throws IOException, InterruptedException {

		try {

			rm.ApptCheckinInCOG("Auto, Apptmember1", appointmentToBook, "apptmember1"); // Check In the Member
																						// to the
																						// appointment
			DashboardPO d = new DashboardPO(driver);
			WebDriverWait wait = new WebDriverWait(driver, 30);

			appointmentsCount = d.getMyAppts().size();

			for (int k = 0; k < appointmentsCount; k++) {
				if (d.getMyAppts().get(k).getText().contains(tomorrowsDate))

				{

					if (d.getMyAppts().get(k).getText().contains(startTime)) {
						wait.until(ExpectedConditions
								.elementToBeClickable(d.getMyAppts().get(k).findElement(By.tagName("i"))));
						d.getMyAppts().get(k).findElement(By.tagName("i")).click();

						WebElement EditButton = d.getEditButton().get(k);

						wait.until(ExpectedConditions.visibilityOf(EditButton));
						wait.until(ExpectedConditions.elementToBeClickable(EditButton));

						EditButton.click();
						break;
					}
				}
			}
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
			Thread.sleep(2000);
			AppointmentsPO a = new AppointmentsPO(driver);
			Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
			wait.until(ExpectedConditions.visibilityOf(a.getEditApptCancelButton()));
			a.getEditApptCancelButton().click();
			WebElement wait2 = a.getEditApptProceedButton();
			while (!wait2.isEnabled())// while button is NOT(!) enabled
			{
//			Thread.sleep(200);
			}
			a.getEditApptProceedButton().click();
			Thread.sleep(1000);
			boolean result1 = rw.popupMessageYesButton();
			if (result1 == true) {
//				Thread.sleep(500);	
			}
			a.getEditApptCancelYesButton().click();
//			boolean result2 = rw.popupMessageYesButton();
//			if (result2 == true)
//			{
//				Thread.sleep(500);	
//			}
//		a.getEditApptCanceledOKButton().click();
//		rw.waitForDashboardLoaded();
			Thread.sleep(2000);
			Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
			rm.memberLogout();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		} catch (java.lang.AssertionError ae) {
			log.error(ae.getMessage(), ae);
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		}

	}

	// @AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}