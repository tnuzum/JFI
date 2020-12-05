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
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ClubReqPackages_GrpAppt_FreeAppointment extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training 1";
	private static String appointmentToBook1 = "Free GrpServiceV Auto";
	private static String appointmentToBook2 = "Free GrpTraining Due 2 Discount";
	private static String additionalResourceName = "Mind Body-Grp";
	private static String resourceName1 = "PT Smith, Andrew-Grp";
	private static String resourceName2 = "FitExpert2-Grp";
	private static String clubNameDisplayed = "Club: Studio Jonas";
	private static String startTime;
	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public ClubReqPackages_GrpAppt_FreeAppointment() {
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
	public void ScheduleFreeServiceV() throws IOException, InterruptedException {

		AppointmentsPO ap = new AppointmentsPO(driver);
		DashboardPO p = new DashboardPO(driver);
		try {
			rm.activeMemberLogin("freemember", "Testing1!");
			rw.waitForDashboardLoaded();

			jse.executeScript("arguments[0].click();", p.getMyApptsScheduleButton());
			Thread.sleep(2000);

			rm.catchErrorMessage();

			WebDriverWait wait = new WebDriverWait(driver, 30);

			rm.selectClub(clubName);

			rm.selectProductCategory(productCategory);

			rm.makeNewGrpAppointmentSelections("Scott", appointmentToBook1, resourceName1);

			rm.calendarTomorrowClick();

			getScreenshot(this.getClass().getSimpleName() + "_CalendarClicked", driver);

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

			System.out.println(ap.getPopup1Content().getText());
			System.out.println("Time: " + tomorrowsDate + ", " + startTime);
			System.out.println("Product: " + appointmentToBook1);
			System.out.println("Resource: " + resourceName1);
			Assert.assertTrue(ap.getPopup1Content().getText().contains("This appointment is free!"));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(clubNameDisplayed));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: " + tomorrowsDate + " " + startTime));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: " + appointmentToBook1));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Resource: " + resourceName1));

			ap.getPopup1BookButton().click();

			rw.waitForAcceptButton();

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
		} finally {

			boolean popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));

			while (popup == true) {
				ap.getPopup2OKButton().click();
				System.out.println("popup was present");
				popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));
			}
			rm.memberLogout();
		}

	}

	@Test(priority = 2, description = "Schedule a free appointment due to membership type discount")
	public void ScheduleFreeTrainingDueToDiscount() throws IOException, InterruptedException {

		DashboardPO p = new DashboardPO(driver);
		AppointmentsPO ap = new AppointmentsPO(driver);
		try {
			rm.activeMemberLogin("freemember", "Testing1!");
			rw.waitForDashboardLoaded();

			jse.executeScript("arguments[0].click();", p.getMyApptsScheduleButton());
			Thread.sleep(2000);

			rm.catchErrorMessage();

			WebDriverWait wait = new WebDriverWait(driver, 30);

			rm.selectClub(clubName);

			rm.selectProductCategory(productCategory);

			rm.makeNewGrpAppointmentSelections("Scott", appointmentToBook2, resourceName2);

			rm.calendarTomorrowClick();

			getScreenshot(this.getClass().getSimpleName() + "_CalendarClicked", driver);

			Assert.assertTrue(ap.getBooksNames().getText().contains(resourceName2));

			WebElement st1 = ap.getSelectTimeMorningButton();

			wait.until(ExpectedConditions.elementToBeClickable(st1));
			while (!st1.isEnabled())// while button is NOT(!) enabled
			{
				System.out.println("Waiting for available times");
			}

			st1.click();
			getScreenshot(this.getClass().getSimpleName() + "_MorningSlotClicked", driver);
			WebElement st2 = ap.getSelectTime1stAvailable();
//					while (!st2.isEnabled())//while button is NOT(!) enabled
//					{
//					Thread.sleep(200);
//					}

			wait.until(ExpectedConditions.elementToBeClickable(ap.getSelectTime1stAvailable()));
			startTime = st2.getText();
			// st2.click();

			jse.executeScript("arguments[0].click();", st2);
			getScreenshot(this.getClass().getSimpleName() + "_MorningTimeClicked", driver);
			Thread.sleep(5000);

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
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: " + appointmentToBook2));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(resourceName2));

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
			Assert.assertEquals(ap.getAppointmentName().getText(), appointmentToBook2);
			Assert.assertEquals(ap.getClubName().getText(), clubNameDisplayed);
			Assert.assertEquals(ap.getAppointmentTime().getText(), "Start Time: " + startTime);
			Assert.assertEquals("Date: " + tomorrowsDate, ap.getAppointmentDate().getText());

			ap.getbookButton().click();

			rw.waitForAcceptButton();

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
		} finally {

			boolean popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));

			while (popup == true) {
				ap.getPopup2OKButton().click();
				System.out.println("popup was present");
				popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));
			}
			rm.memberLogout();
		}

	}

	// @AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}