package SingleMemberAppointments;

import java.io.IOException;
import java.util.List;

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

import pageObjects.AppointmentsPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ChangeApptWithFee_ClubReqPackages_ToGrpAppt extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName1 = "Jonas Fitness";
	private static String clubName2 = "Studio Jonas";
	private static String productCategory1 = "Personal Training";
	private static String productCategory2 = "Personal Training 1";
	private static String appointmentToBook1 = "PT 60 Mins-ChangeWithFee1";
	private static String appointmentToBook2 = "PT Grp-ChangeWithFee2";
	private static String resourceName1 = "FitExpert1CA";
	private static String resourceName2 = "Holmes, JeffCA";
	private static String resourceName3 = "FitExpert1CA-Grp";
	private static String resourceName4 = "PT Smith, AndrewCA-Grp";
	private static String unitsToBeSelected = "1 - $5.00/per";
	private static String participant2 = "Auto, Fred";
	private static String startTime1;
	private static String startTime2;
	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public ChangeApptWithFee_ClubReqPackages_ToGrpAppt() {
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
	public void ChangeAppointmentWithFee() throws IOException, InterruptedException {
		try {
			rm.activeMemberLogin("apptmember11", "Testing1!");

			rw.waitForDashboardLoaded();
			DashboardPO d = new DashboardPO(driver);
			jse.executeScript("arguments[0].click();", d.getMyApptsScheduleButton());
			Thread.sleep(2000);

			// Book an appointment and get the start time for the appointment
			startTime1 = rm.BookApptWith2Resources(clubName1, productCategory1, appointmentToBook1, resourceName1,
					resourceName2);

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
					By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));

			AppointmentsPO ap = new AppointmentsPO(driver);
			rm.ValidatechangeAppointmentScreen(startTime1, appointmentToBook1);

			rm.selectClub(clubName2);

			rm.selectProductCategory(productCategory2);

			rm.makeNewGrpAppointmentSelections("Fred", appointmentToBook2, resourceName3);

			rm.calendarTwoDaysDayAfterClick();

			for (int m = 0; m < ap.getApptBox().size(); m++) {
				String bookName = ap.getApptBox().get(m).getText();
				if (bookName.contains(resourceName4)) {
					List<WebElement> TimeSlots = ap.getTimeSlotContainers().get(m).findElements(By.tagName("a"));
					WebElement AftrnunSlot = TimeSlots.get(1);
					wait.until(ExpectedConditions.elementToBeClickable(AftrnunSlot));
					while (!AftrnunSlot.isEnabled())// while button is NOT(!) enabled
					{
						System.out.println("Waiting for available times");
					}

					// AftrnunSlot.click();

					jse.executeScript("arguments[0].click();", AftrnunSlot);
					Thread.sleep(1000);
					WebElement AftrenoonAvailableTimeContainer = ap.getTimeSlotContainers().get(m)
							.findElement(By.id("tab-2-1"));
					List<WebElement> AftrenoonAvailableTimes = AftrenoonAvailableTimeContainer
							.findElements(By.tagName("button"));
					WebElement secondAvailableTimeAfternoon = AftrenoonAvailableTimes.get(1);
//					while (!st2.isEnabled())//while button is NOT(!) enabled
//					{
//					Thread.sleep(200);
//					}

					wait.until(ExpectedConditions.elementToBeClickable(secondAvailableTimeAfternoon));
					startTime2 = secondAvailableTimeAfternoon.getText();
					System.out.println(startTime2);
					secondAvailableTimeAfternoon.click();
					break;
				}
			}
			Thread.sleep(2000);

			System.out.println(ap.getPopup1Content().getText());
			System.out.println("Time: " + TwodaysAfter + " " + startTime2);
			System.out.println("Product: " + appointmentToBook2);
			System.out.println("Resource: " + resourceName2);

			Assert.assertTrue(ap.getPopup1Content().getText().contains(clubName2));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: " + TwodaysAfter + " " + startTime2));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: " + appointmentToBook2));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(resourceName3));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(resourceName4));

			Assert.assertEquals(ap.getPopup1Title().getText(), "Package Required");

			Assert.assertTrue(
					ap.getPopup1Content().getText().contains("This appointment requires a package purchase."));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Would you like to continue?"));

			ap.getPopup1BookButton().click();
			Thread.sleep(2000);

			System.out.println(ap.getOldAppointmentBanner().getText());
			System.out.println(ap.getNewAppointmentBanner().getText());

			Assert.assertTrue(ap.getOldAppointmentBanner().isDisplayed());
			Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(clubName1));
			Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains("Old Appointment"));
			Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(appointmentToBook1));
			Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(startTime1));
			Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(tomorrowsDate));

			Assert.assertTrue(ap.getNewAppointmentBanner().isDisplayed());
			Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(clubName2));
			Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains("New Appointment"));
			Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(appointmentToBook2));
			Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(startTime2));
			Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(TwodaysAfter));
			Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(participant2));

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

			wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));

			Assert.assertTrue(ap.getChangeFee().getText().contains("CHANGE FEE $6.00"));

			wait.until(ExpectedConditions.textToBePresentInElement(ap.getRateBox(), appointmentToBook2.toUpperCase()));
			System.out.println(ap.getRateBox().getText());

			rm.verifyLowestNumberOfUnitsIsSelectedByDefault(unitsToBeSelected);

			Thread.sleep(2000);

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

			rm.memberLogout();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			log.error("Appointment is not changed");
			getScreenshot(this.getClass().getSimpleName(), driver);
		} catch (java.lang.AssertionError ae) {
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			log.error("Appointment is not changed");
			getScreenshot(this.getClass().getSimpleName(), driver);
		}
	}

	@Test(priority = 2)
	public void ConfirmNewAppointmentIsScheduled() throws IOException, InterruptedException {

		try {

			rm.ApptCheckinInCOG("Auto, apptmember11", appointmentToBook2, "apptmember11", "1"); // Check In the
			// Member
			// to the
			// appointment

			rm.ConfirmAndCancelAppointmentNoFee(TwodaysAfter, startTime2, appointmentToBook2);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			log.error("Appointment is not changed");
			getScreenshot(this.getClass().getSimpleName(), driver);
		} catch (java.lang.AssertionError ae) {
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			log.error("Appointment is not changed");
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