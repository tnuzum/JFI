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

import pageObjects.AppointmentsPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class CancelGrpApptWithFee_NewCard extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training 1";
	private static String appointmentToBook = "PTGrpThreeResourceWithCancelFee";
	private static String resourceName = "FitExpert1-Grp";
	private static String memberName = "Cancelmember5 Auto";
	private static String additionalResourceName = "Gym-Grp";
	private static String clubNameDisplayed = "Club: Studio Jonas";
	private static String appointmentPrice = "$90.00";
	private static String startTime;
	private static String participant2 = "Auto, Daisy";
	private static int appointmentsCount;
	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public CancelGrpApptWithFee_NewCard() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {

		try {

			driver = initializeDriver();

		} catch (java.lang.NullPointerException npe) {

			driver = initializeDriver();

			System.out.println("driver initialized again");
			log.error("driver initialized again");
			npe.printStackTrace();
			log.error(npe.getMessage(), npe);

		}

		catch (org.openqa.selenium.WebDriverException we) {

			driver = initializeDriver();

			System.out.println("driver initialized again");
			log.error("driver initialized again");
			we.printStackTrace();
			log.error(we.getMessage(), we);

		}

		rm.setDriver(driver);
		rw.setDriver(driver);
		jse = (JavascriptExecutor) driver;
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
	}

	@Test(priority = 1, description = "In this test appointment is booked with existing Packages to book the appointment and the cancelled using a cancellation fee")
	public void ScheduleGrpAppointmentWithExistingPackageWithThreeResources() throws IOException, InterruptedException {
		try {
			rm.activeMemberLogin("cancelmember5", "Testing1!");
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

			Assert.assertTrue(ap.getBooksNames().getText().contains(resourceName));

			WebElement st1 = ap.getSelectTimeMorningButton();

			wait.until(ExpectedConditions.elementToBeClickable(st1));
			while (!st1.isEnabled())// while button is NOT(!) enabled
			{
				System.out.println("Waiting for available times");
			}

			st1.click();

			WebElement st2 = ap.getSelectTime1stAvailable();

			wait.until(ExpectedConditions.elementToBeClickable(ap.getSelectTime1stAvailable()));
			startTime = st2.getText();
			// st2.click();

			jse.executeScript("arguments[0].click();", st2);

			Thread.sleep(1000);
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

			Assert.assertTrue(ap.getPopup1Content().getText().contains(clubName));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: " + tomorrowsDate + " " + startTime));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: " + appointmentToBook));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(resourceName));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(appointmentPrice));

			ap.getPopup1BookButton().click();

			int additionalResourcesCount = ap.getAdditionalResources().size();

			for (int n = 0; n < additionalResourcesCount; n++) {
				if (ap.getAdditionalResources().get(n).getText().contains(additionalResourceName))
					jse.executeScript("arguments[0].click();", ap.getAdditionalResources().get(n));
			}

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

			jse.executeScript("arguments[0].click();", ap.getbookButton());

			rw.waitForAcceptButton();

			wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

			// Verifies the success message
			Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
			ap.getPopup2OKButton().click();
			Thread.sleep(1000);

			// Navigate to Dashboard
			ThankYouPO TY = new ThankYouPO(driver);
			jse.executeScript("arguments[0].click();", TY.getDashBoardLink());

			rw.waitForDashboardLoaded();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
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
		try {
			rw.waitForDashboardLoaded();
			DashboardPO d = new DashboardPO(driver);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
					By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));

			appointmentsCount = d.getMyAppts().size();

			for (int i = 0; i < appointmentsCount; i++) {
				if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

				{

					if (d.getMyAppts().get(i).getText().contains(startTime)) {
						System.out.println(startTime);
						Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook.toUpperCase()));
					}
				}
			}
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
	public void CancelAppointmentWithFee() throws IOException, InterruptedException {
		try {

			DashboardPO d = new DashboardPO(driver);
			WebDriverWait wait = new WebDriverWait(driver, 30);

			for (int k = 0; k < appointmentsCount; k++) {
				if (d.getMyAppts().get(k).getText().contains(tomorrowsDate))

				{

					if (d.getMyAppts().get(k).getText().contains(startTime)) {
						wait.until(ExpectedConditions
								.elementToBeClickable(d.getMyAppts().get(k).findElement(By.tagName("i"))));
						jse.executeScript("arguments[0].click();", d.getMyAppts().get(k).findElement(By.tagName("i")));

//					Thread.sleep(5000);
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
			AppointmentsPO ap = new AppointmentsPO(driver);
			Assert.assertEquals(ap.getEditApptPageHeader().getText(), "Edit Appointment");
			wait.until(ExpectedConditions.visibilityOf(ap.getEditApptCancelButton()));
			jse.executeScript("arguments[0].click();", ap.getEditApptCancelButton());
			Assert.assertTrue(ap.getCancelFeeSection().getText().contains("Appointment Cancellation Fee"));
			Assert.assertTrue(ap.getCancelFeeSection().getText()
					.contains("This will cancel the appointment for all participants."));

			wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));
			System.out.println(ap.getTotalAmount().getText());
			String[] totalAmt = ap.getTotalAmount().getText().split(": ");
			String FormatTotalAmt = totalAmt[1].trim();
			System.out.println(FormatTotalAmt);

			Thread.sleep(3000);

			PaymentMethodsPO PM = new PaymentMethodsPO(driver);

			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);

			}

			jse.executeScript("arguments[0].click();", PM.getNewCardButton());
			Thread.sleep(3000);

			String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			while (opacity.contains("1")) {
				jse.executeScript("arguments[0].click();", PM.getNewCardButton());
				Thread.sleep(3000);
				opacity = driver.findElement(By.id("show-saved")).getAttribute("style");

			}

			wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

			Assert.assertTrue(PM.getCloseButton().isDisplayed());
			System.out.println("Pay Button disabled:" + PM.getPaymentButton().getAttribute("disabled"));
			Assert.assertFalse(PM.getPaymentButton().isEnabled());

//				System.out.println(PM.getNameOnCardField().getAttribute("value"));
			Assert.assertEquals(memberName, PM.getNameOnCardField().getAttribute("value"));

			PM.getCardNumberField().sendKeys("4111111111111111");
			PM.getExpirationMonth().sendKeys("04");
			PM.getExpirationYear().sendKeys("29");
			PM.getSecurityCode().sendKeys("123");
			jse.executeScript("arguments[0].click();", PM.getCheckBox());
			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}

			// Clicks on the Pay button without signature
			jse.executeScript("arguments[0].click();", PM.getPaymentButton());
			System.out.println(PM.getPopupContent().getText());
			Assert.assertTrue(PM.getPopupContent().getText().contains("A signature is required to continue."));
			PM.getPopupOk().click();
			Thread.sleep(1000);
			jse.executeScript("arguments[0].click();", PM.getSaveCardNo());
			Thread.sleep(1000);

			// Verifies the Pay button contains the total amount
			Assert.assertTrue(PM.getPaymentButton().getText().contains(FormatTotalAmt));
			// Click the Pay button
			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}
			jse.executeScript("arguments[0].click();", PM.getPaymentButton());

			rw.waitForAcceptButton();

			wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

			// Verifies the success message
			Assert.assertEquals(ap.getPopup2Title().getText(), "Success");
			Assert.assertTrue(ap.getPopup2Content().getText().contains("Your appointment has been cancelled."));
			ap.getPopup2OKButton().click();
			Thread.sleep(1000);
			ThankYouPO TY = new ThankYouPO(driver);

			// Verifies the text on Thank You page and the links to navigate to Dashboard
			// and other pages are displayed
			rm.ThankYouPageValidations();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());

			// Verifies the buttons on Print Receipt Popup
			rm.ReceiptPopupValidations();
			Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-12 text-right']"))
					.getText().contains(FormatTotalAmt));

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
			Thread.sleep(2000);

			// Navigate to Dashboard

			jse.executeScript("arguments[0].click();", TY.getDashBoardLink());

			rw.waitForDashboardLoaded();
			// Verifies the link navigates to the right page
			Assert.assertEquals("Dashboard", driver.getTitle());
			Thread.sleep(2000);

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

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}