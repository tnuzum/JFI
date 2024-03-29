package GroupAppointments;

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
import pageObjects.DashboardPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class Bug167779_GroupAppt_MinRequiredMemberCheck extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String startTime;;
	private static int appointmentsCount;
	private static String clubName = "Jonas Fitness";
	private static String productCategory = "Personal Training 1";
	private static String resourceName = "PT.Smith, Andrew-Grp";
	private static String appointmentToBook = "PT Group-ReqMinMbr>1";
	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public Bug167779_GroupAppt_MinRequiredMemberCheck() {
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
			rm.activeMemberLogin("scottauto", "Testing1!");
			rw.waitForDashboardLoaded();

			DashboardPO p = new DashboardPO(driver);
			jse.executeScript("arguments[0].click();", p.getMyApptsScheduleButton());
			Thread.sleep(2000);

			rm.catchErrorMessage();

			WebDriverWait wait = new WebDriverWait(driver, 30);
			AppointmentsPO ap = new AppointmentsPO(driver);

			Select se = new Select(ap.getclubs());
			List<WebElement> Clubs = se.getOptions();

			int x = 0;
			while (!ap.getclubs().isEnabled() && x < 100) {
				System.out.println("Waiting for Clubs drop down to not be blank");
				x++;
			}

			int count0 = Clubs.size();
			System.out.println("1 " + count0);

			for (int i = 0; i < count0; i++) {
				String club = Clubs.get(i).getText();

				if (club.equals(clubName)) {
					se.selectByVisibleText(club);
					break;
				}
			}
			Thread.sleep(2000);
			WebElement bic = ap.getBookableItemCategory();

			Select s = new Select(bic);
			List<WebElement> ProductCategories = s.getOptions();

			int count = ProductCategories.size();
			System.out.println(count);

			for (int i = 0; i < count; i++) {
				String category = ProductCategories.get(i).getText();

				if (category.equals(productCategory)) {
					s.selectByVisibleText(category);
					break;
				}
			}

			Select s1 = new Select(ap.getBookableItem());
			Thread.sleep(2000);
			List<WebElement> Products = s1.getOptions();

			int count1 = Products.size();
			System.out.println(count1);

			for (int j = 0; j < count1; j++) {
				String product = Products.get(j).getText();

				if (product.equals(appointmentToBook)) {
					s1.selectByVisibleText(product);
					break;
				}
			}

			Assert.assertEquals(ap.getGroupApptsHeader().getText(), "Group Appointments");
			Assert.assertEquals(ap.getGroupMinPersons().getText(), "3");
			Assert.assertEquals(ap.getGroupMaxPersons().getText(), "5");
			ap.getGroupMemberSearchInput().sendKeys("Daisy");

			jse.executeScript("arguments[0].click();", ap.getGroupMemberSearchButton());

			Thread.sleep(2000);

			int memberCount = ap.getGroupPopupAddButtons().size();
			for (int i = 0; i < memberCount; i++)

			{
				String text = ap.getGroupPopupMembers().get(i).getText();
				System.out.println(text);
				if (ap.getGroupPopupMembers().get(i).getText().contains("Daisy")) {
					wait.until(ExpectedConditions.elementToBeClickable(ap.getGroupPopupAddButtons().get(i)));
					ap.getGroupPopupAddButtons().get(i).click();
					break;
				}
			}

			WebElement rt = ap.getResourceType();

			Select s2 = new Select(rt);
			Thread.sleep(2000);
			List<WebElement> Resources = s2.getOptions();

			int count2 = Resources.size();
			System.out.println(count2);

			for (int k = 0; k < count2; k++) {
				String resource = Resources.get(k).getText();

				if (resource.equals(resourceName)) {
					s2.selectByVisibleText(resource);
					break;
				}
			}

			Assert.assertEquals(ap.getloadingAvailabilityMessage().size(), 0);

			Assert.assertFalse(ap.getCalendarToday().getAttribute("class").contains("appointmentAvailable-cell"));

			ap.getGroupMemberSearchInput().sendKeys("Adam Auto");
			jse.executeScript("arguments[0].click();", ap.getGroupMemberSearchButton());
			Thread.sleep(2000);
			ap.getGroupPopupAddButtons().get(0).click();

			Assert.assertTrue(ap.getloadingAvailabilityMessage().size() > 0);

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");

			Assert.assertTrue(ap.getCalendarToday().getAttribute("class").contains("appointmentAvailable-cell"));

			rm.calendarTomorrowClick();

			jse.executeScript("arguments[0].click();", ap.getCloseButton());
			// delete a group member after the calendar loads

			jse.executeScript("arguments[0].click();", ap.getDeleteMember().get(0));

			rm.calendarTomorrowClick();

			WebElement st1 = ap.getSelectTimeMorningButton();

			wait.until(ExpectedConditions.elementToBeClickable(st1));
			while (!st1.isEnabled())// while button is NOT(!) enabled
			{
				System.out.println("Waiting for available times");
			}
			jse.executeScript("arguments[0].click();", st1);
			WebElement st2 = ap.getSelectTime1stAvailable();
//					while (!st2.isEnabled())//while button is NOT(!) enabled
//					{
//					Thread.sleep(200);
//					}
			WebDriverWait wait1 = new WebDriverWait(driver, 30);
			wait1.until(ExpectedConditions.elementToBeClickable(st2));
			startTime = st2.getText();
			System.out.println(startTime);
			// st2.click();

			jse.executeScript("arguments[0].click();", st2);
			Thread.sleep(2000);
			WebElement p1 = ap.getPopup1BookButton();
			while (!p1.isEnabled())// while button is NOT(!) enabled
			{
//						Thread.sleep(200); 
			}

			ap.getPopup1BookButton().click();
			rw.waitForAcceptButton();

			wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

			Assert.assertEquals(ap.getPopup2Title().getText(), "Oops!");
			Assert.assertEquals(ap.getPopup2Content().getText(),
					"We apologize, but this appointment time is no longer available.");
			ap.getPopup2OKButton().click();
			Thread.sleep(1000);

			// Add another member again to meet the minimum number of members required
			ap.getGroupMemberSearchInput().sendKeys("Susan Auto");
			jse.executeScript("arguments[0].click();", ap.getGroupMemberSearchButton());
			Thread.sleep(2000);

			ap.getGroupPopupAddButtons().get(0).click();

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			// Book appointment
			rm.calendarTomorrowClick();

			st1 = ap.getSelectTimeMorningButton();

			wait.until(ExpectedConditions.elementToBeClickable(st1));
			while (!st1.isEnabled())// while button is NOT(!) enabled
			{
				System.out.println("Waiting for available times");
			}
			jse.executeScript("arguments[0].click();", st1);
			st2 = ap.getSelectTime1stAvailable();
//					
			wait1.until(ExpectedConditions.elementToBeClickable(st2));
			startTime = st2.getText();
			System.out.println(startTime);
			// st2.click();

			jse.executeScript("arguments[0].click();", st2);
			Thread.sleep(2000);
			p1 = ap.getPopup1BookButton();
			while (!p1.isEnabled())// while button is NOT(!) enabled
			{
//						Thread.sleep(200); 
			}

			ap.getPopup1BookButton().click();
			rw.waitForAcceptButton();

			wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));
			Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
			ap.getPopup2OKButton().click();
			Thread.sleep(1000);

			// Navigate to Dashboard
			ThankYouPO TY = new ThankYouPO(driver);
			jse.executeScript("arguments[0].click();", TY.getDashBoardLink());

			rw.waitForDashboardLoaded();
			// Verifies the link navigates to the right page
			Assert.assertEquals("Dashboard", driver.getTitle());
			Thread.sleep(2000);
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
			ae.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
		}
	}

	@Test(priority = 2)
	public void ConfirmAppointmentIsScheduled() throws IOException, InterruptedException {
		try {
			rw.waitForDashboardLoaded();
			DashboardPO d = new DashboardPO(driver);
			WebDriverWait wait = new WebDriverWait(driver, 20);
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
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			log.error("Appointment is not booked");
			e.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
		} catch (java.lang.AssertionError ae) {
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			ae.printStackTrace();
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		}

	}

	@Test(priority = 3)
	public void CancelAppointment() throws IOException, InterruptedException {
		try {
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

			a.getEditApptCancelYesButton().click();
			rw.waitForAcceptButton();
			a.getPopup2OKButton().click();
			Thread.sleep(2000);
			Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
			rm.memberLogout();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			log.error("Appointment is not booked");
			e.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
		} catch (java.lang.AssertionError ae) {
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			log.error("Appointment is not booked");
			ae.printStackTrace();
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
