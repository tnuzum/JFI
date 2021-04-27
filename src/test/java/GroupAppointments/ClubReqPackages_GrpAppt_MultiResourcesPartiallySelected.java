package GroupAppointments;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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

public class ClubReqPackages_GrpAppt_MultiResourcesPartiallySelected extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training 1";
	private static String appointmentToBook = "PT Group-MultiResourcPartially";
	private static String resourceName = "PT Smith, Andrew-Grp";
	private static String clubNameDisplayed = "Club: Studio Jonas";
	private static String startTime;
	private static int appointmentsCount;
	private static String participant2 = "Auto, Daisy";
	private static String unitsToBeSelected = "1 - $5.00/per";
	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public ClubReqPackages_GrpAppt_MultiResourcesPartiallySelected() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		jse = (JavascriptExecutor) driver;
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
	}

	@Test(priority = 1)
	public void ScheduleAppointment() throws IOException, InterruptedException {
		try {
			rm.activeMemberLogin("apptmember14", "Testing1!");
			rw.waitForDashboardLoaded();

			DashboardPO p = new DashboardPO(driver);
			jse.executeScript("arguments[0].click();", p.getMyApptsScheduleButton());
			Thread.sleep(2000);

			rm.catchErrorMessage();

			WebDriverWait wait = new WebDriverWait(driver, 30);
			AppointmentsPO ap = new AppointmentsPO(driver);

			rm.selectClub(clubName);

			rm.selectProductCategory(productCategory);

			rm.makeNewGrpAppointmentSelections("Daisy", appointmentToBook, resourceName);

			rm.calendarTomorrowClick();
			getScreenshot(this.getClass().getSimpleName() + "_CalendarClicked", driver);

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

			wait.until(ExpectedConditions.elementToBeClickable(ap.getSelectTime1stAvailable()));
			startTime = st2.getText();
			// st2.click();

			jse.executeScript("arguments[0].click();", st2);
			Thread.sleep(2000);

			System.out.println("popupSize = " + ap.getPopup1().size());
			log.info("popupSize = " + ap.getPopup1().size());

			int k = 0;

			while (ap.getPopup1().size() == 0 && k < 2)

			{
				if (ap.getSelectATimeDrawer().getAttribute("ng-reflect-opened").equals("true")) {
					ap.getCloseButton().click();
				}
				rm.calendarTomorrowClick();

				ap.getSelectTimeMorningButton().click();

				wait.until(ExpectedConditions.elementToBeClickable(ap.getSelectTime1stAvailable()));
				startTime = ap.getSelectTime1stAvailable().getText();

				jse.executeScript("arguments[0].click();", ap.getSelectTime1stAvailable());
				Thread.sleep(1000);

				ap.getPopup1().size();
				k++;
			}

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
			Assert.assertTrue(ap.getGroup().getText().contains(participant2));
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
			// Noting down the total amount
			wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));
			System.out.println(ap.getTotalAmount().getText());

			String[] totalAmt = ap.getTotalAmount().getText().split(": ");
			String FormatTotalAmt = totalAmt[1].trim();
			System.out.println(FormatTotalAmt);
			// Verifies the Pay button contains the total amount

			Assert.assertTrue(ap.getPaymentButton().getText().contains(FormatTotalAmt));

			PaymentMethodsPO PM = new PaymentMethodsPO(driver);
			while (!PM.getOnAccountAndSavedCards().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			// Click the Pay button
			while (!ap.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}
			jse.executeScript("arguments[0].click();", ap.getPaymentButton());

			rw.waitForAcceptButton();

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

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
			Thread.sleep(2000);

//Navigate to Dashboard			
			jse.executeScript("arguments[0].click();", TY.getDashBoardLink());

//			int linkcount = driver.findElements(By.tagName("a")).size();
//			for (int i = 0; i < linkcount; i++) {
//				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))
//
//				{
//					// rw.linksToBeClickable();
//					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
//					break;
//				}
//
//			}
			rw.waitForDashboardLoaded();
//Verifies the link navigates to the right page
			Assert.assertEquals("Dashboard", driver.getTitle());
			Thread.sleep(2000);

//Note the package units after purchase
			int IntUnitCountAfter = rm.getPackageUnits(appointmentToBook);
//System.out.println(IntUnitCountAfter);

//Verifies the package units is now incremented by one unit

			Assert.assertEquals(IntUnitCountAfter, 1); // verifies the unit count of the Package

			DashboardPO dp = new DashboardPO(driver);
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.xpath("//div[(contains@class, 'swal2-center')]")));

			jse.executeScript("arguments[0].click();", dp.getMyAccountAccountHistory());

			AcctHistoryPO ahp = new AcctHistoryPO(driver);

			while (ahp.getSearchingAcctHistMessage().size() != 0) {
				System.out.println("waiting for account history to display");
				Thread.sleep(1000);
			}

			while (!ahp.getReceiptNumberTable().isDisplayed()) {
				Thread.sleep(2000);
				System.out.println("waiting");
			}

//Clicks on the Receiptnumber in Account History 

			ahp.getSearchField().sendKeys(receiptNumber);
			Thread.sleep(2000);
			wait.until(ExpectedConditions.textToBePresentInElement(ahp.getReceiptNumber(), receiptNumber));
			jse.executeScript("arguments[0].click();", ahp.getReceiptNumber());
			Thread.sleep(3000);
			jse.executeScript("arguments[0].scrollIntoView(true);",
					TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-12 text-right']")));

			/*
			 * while (!ahp.getReceiptNumberTable().isDisplayed()) { Thread.sleep(2000);
			 * System.out.println("waiting"); } for (int k = 0; k <
			 * ahp.getReceiptNumbers().size(); k++) { receiptNumber1 =
			 * ahp.getReceiptNumbers().get(k).getText().trim();
			 * 
			 * if (receiptNumber1.equals(receiptNumber)) {
			 * ahp.getReceiptNumbers().get(k).click(); break; } }
			 */

//Verifies the amount in the receipt is the same as it was displayed on the Purchase Packages page

			while (TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-12 text-right']")).getText()
					.isBlank()) {
				Thread.sleep(500);
			}
			System.out.println(
					TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-12 text-right']")).getText());
			Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-12 text-right']"))
					.getText().contains(FormatTotalAmt));
			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
			Thread.sleep(2000);
			rm.returnToDashboard();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		} catch (java.lang.AssertionError ae) {
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		}
	}

	@Test(priority = 2)
	public void ConfirmAppointmentIsScheduled() throws IOException, InterruptedException {
		// rw.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		try {
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
			e.printStackTrace();
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		} catch (java.lang.AssertionError ae) {
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		}

	}

	@Test(priority = 3)
	public void CancelAppointment() throws IOException, InterruptedException {

		try {
			rm.ApptCheckinInCOG("Auto, apptmember14", appointmentToBook, "apptmember14", "1"); // Check In the Member

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
						jse.executeScript("arguments[0].click();", d.getMyAppts().get(k).findElement(By.tagName("i")));

						WebElement EditButton = d.getEditButton().get(k);

						wait.until(ExpectedConditions.visibilityOf(EditButton));
						wait.until(ExpectedConditions.elementToBeClickable(EditButton));

						jse.executeScript("arguments[0].click();", EditButton);
						break;
					}
				}
			}
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-md-12']/h2")));
			Thread.sleep(2000);
			AppointmentsPO a = new AppointmentsPO(driver);
			Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
			wait.until(ExpectedConditions.visibilityOf(a.getEditApptCancelButton()));
			jse.executeScript("arguments[0].click();", a.getEditApptCancelButton());
			Thread.sleep(1000);
			a.getEditApptCancelYesButton().click();
			Thread.sleep(2000);
			rw.waitForAcceptButton();
			a.getPopup2OKButton().click();
			Thread.sleep(2000);
			Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
			rm.memberLogout();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		} catch (java.lang.AssertionError ae) {
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		}
	}

	// @AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}