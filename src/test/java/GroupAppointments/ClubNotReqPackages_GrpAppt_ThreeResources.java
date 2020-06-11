package GroupAppointments;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ClubNotReqPackages_GrpAppt_ThreeResources extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Jonas Fitness";
	private static String productCategory = "Personal Training 1";
	private static String appointmentToBook = "PT Group Appt 3 Resources";
	private static String resourceName = "FitExpert2-Grp";
	private static String additionalResourceName = "Gym-Grp";
	private static String clubNameDisplayed = "Club: Jonas Fitness";
	private static String appointmentPrice = "$10.00";
	private static String startTime;
	private static int appointmentsCount;

	public reusableWaits rw;
	public reusableMethods rm;

	public ClubNotReqPackages_GrpAppt_ThreeResources() {
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
	public void ScheduleAppointmentWithThreeResources() throws IOException, InterruptedException {
		try {
			rm.activeMemberLogin("scottauto", "Testing1!");

			rw.waitForDashboardLoaded();

			DashboardPO p = new DashboardPO(driver);
			p.getMyApptsScheduleButton().click();
			Thread.sleep(2000);

			rm.catchErrorMessage();

			WebDriverWait wait = new WebDriverWait(driver, 30);
			AppointmentsPO ap = new AppointmentsPO(driver);

			Select se = new Select(ap.getclubs());
			List<WebElement> Clubs = se.getOptions();

			while (!ap.getclubs().isEnabled()) {
				System.out.println("Waiting for Clubs drop down to not be blank");
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

			Thread.sleep(1000);
			Assert.assertEquals(ap.getGroupApptsHeader().getText(), "Group Appointments");
			Assert.assertEquals(ap.getGroupMinPersons().getText(), "1");
			Assert.assertEquals(ap.getGroupMaxPersons().getText(), "2");
			ap.getGroupMemberSearchInput().sendKeys("auto");
			ap.getGroupMemberSearchButton().click();

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

			Actions a = new Actions(driver);
			a.click(ap.getCalendarTomorrow()).build().perform();
			System.out.println("Calendar date clicked for " + this.getClass().getSimpleName());
			log.info("Calendar Date Clicked for " + this.getClass().getSimpleName());
			// ap.getCalendarTomorrow().click();
			Thread.sleep(1000);
			rw.waitForSelectATimeToOpen();

			rm.OpenSelectATimeDrawerIfNotOpenedInFirstAttempt(ap.getCalendarTomorrow());

			WebElement st1 = ap.getSelectTimeMorningButton();
			wait.until(ExpectedConditions.elementToBeClickable(st1));
			while (!st1.isEnabled())// while button is NOT(!) enabled
			{
				System.out.println("Waiting for available times");
			}
			ap.getSelectTimeMorningButton().click();
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
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", st2);
			Thread.sleep(2000);

			Assert.assertTrue(ap.getPopup1Content().getText().contains(clubName));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: " + tomorrowsDate + " " + startTime));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: " + appointmentToBook));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(resourceName));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(appointmentPrice));

			ap.getPopup1BookButton().click();

			BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
			Assert.assertEquals("Appointments", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Book Appointment", BT.getBreadcrumb2().getText());
			Assert.assertEquals(ap.getAppointmentName().getText(), appointmentToBook);
			Assert.assertEquals(ap.getClubName().getText(), clubNameDisplayed);
			Assert.assertEquals(ap.getAppointmentTime().getText(), "Start Time: " + startTime);
			Assert.assertEquals(ap.getAppointmentName().getText(), appointmentToBook);
			Assert.assertEquals("Date: " + tomorrowsDate, ap.getAppointmentDate().getText());

			int additionalResourcesCount = ap.getAdditionalResources().size();

			for (int n = 0; n < additionalResourcesCount; n++) {
				if (ap.getAdditionalResources().get(n).getText().contains(additionalResourceName))
					ap.getAdditionalResources().get(n).click();
			}

			ap.getbookButton().click();

			wait.until(ExpectedConditions.stalenessOf(ap.getPopup2OKButton()));
			wait.until(ExpectedConditions.visibilityOf(ap.getPopup2OKButton()));

			Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
			ap.getPopup2OKButton().click();
			Thread.sleep(1000);

			// Navigate to Dashboard
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
			// Verifies the link navigates to the right page
			Assert.assertEquals("Dashboard", driver.getTitle());
			Thread.sleep(2000);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("Appointment is not booked");
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
						d.getMyAppts().get(k).findElement(By.tagName("i")).click();

//				Thread.sleep(5000);
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
//			
			Thread.sleep(2000);
			Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
			rm.memberLogout();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		}
	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
