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
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ChangeGrpApptWithOutFee_ClubNotReqPackages extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Jonas Fitness";
	private static String productCategory = "Personal Training 1";
	private static String appointmentToBook1 = "PT Group 60 Mins1";
	private static String appointmentToBook2 = "PT Group 30 Mins";
	private static String resourceName1 = "Holmes, JeffCA-Grp";
	private static String resourceName2 = "|Mind BodyCA-Grp";
	private static String resourceName3 = "FitExpert2CA-Grp";
	private static String resourceName4 = "PT.Smith, AndrewCA-Grp";
	private static String appointmentPrice = "$60.00";
	private static String startTime1;
	private static String startTime2;

	public reusableWaits rw;
	public reusableMethods rm;

	public ChangeGrpApptWithOutFee_ClubNotReqPackages() {
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
		getEMEURL();
	}

	@Test(priority = 1)
	public void ChangeAppointmentWitouthFee() throws IOException, InterruptedException {
		try {
			rm.activeMemberLogin("apptmember8", "Testing1!");

			rw.waitForDashboardLoaded();
			DashboardPO d = new DashboardPO(driver);
			d.getMyApptsScheduleButton().click();
			Thread.sleep(2000);

			// Book an appointment and get the start time for the appointment
			startTime1 = rm.BookGrpApptWith2Resources(clubName, productCategory, appointmentToBook1, resourceName1,
					resourceName2, "Robert");

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
					By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
			int appointmentsCount = d.getMyAppts().size();

			for (int i = 0; i < appointmentsCount; i++) {
				if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

				{

					if (d.getMyAppts().get(i).getText().contains(startTime1)) {

						Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook1.toUpperCase()));
						wait.until(ExpectedConditions
								.elementToBeClickable(d.getMyAppts().get(i).findElement(By.tagName("i"))));
						d.getMyAppts().get(i).findElement(By.tagName("i")).click();

						WebElement EditButton = d.getEditButton().get(i);

						wait.until(ExpectedConditions.visibilityOf(EditButton));
						wait.until(ExpectedConditions.elementToBeClickable(EditButton));

						EditButton.click();
						break;
					}
				}
			}

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
			Thread.sleep(2000);
			AppointmentsPO ap = new AppointmentsPO(driver);
			ap.getEditApptChangeButton().click();
			Thread.sleep(1000);
			Assert.assertTrue(
					ap.getNoFeeSection().getText().contains("There are no fees for changing this appointment."));

			ap.getEditApptProceedButton1().click();

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");

			/*
			 * Select se = new Select(ap.getclubs()); List<WebElement> Clubs =
			 * se.getOptions();
			 * 
			 * while (!ap.getclubs().isEnabled()) {
			 * System.out.println("Waiting for Clubs drop down to not be blank"); }
			 * 
			 * int count0 = Clubs.size(); System.out.println("1 " + count0);
			 * 
			 * for (int i = 0; i < count0; i++) { String category = Clubs.get(i).getText();
			 * 
			 * if (category.equals(clubName)) { se.selectByVisibleText(category); break; } }
			 * Thread.sleep(2000);
			 * 
			 * WebElement bic = ap.getBookableItemCategory();
			 * 
			 * Select s = new Select(bic); List<WebElement> ProductCategories =
			 * s.getOptions();
			 * 
			 * int count = ProductCategories.size(); System.out.println(count);
			 * 
			 * for (int i = 0; i < count; i++) { String category =
			 * ProductCategories.get(i).getText();
			 * 
			 * if (category.equals(productCategory)) { s.selectByVisibleText(category);
			 * break; } }
			 */

			Select s1 = new Select(ap.getBookableItem());
			Thread.sleep(2000);
			List<WebElement> Products = s1.getOptions();

			int count1 = Products.size();
			System.out.println(count1);

			for (int j = 0; j < count1; j++) {
				String product = Products.get(j).getText();

				if (product.equals(appointmentToBook2)) {
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
				if (ap.getGroupPopupMembers().get(i).getText().contains("Robert")) {
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

				if (resource.equals(resourceName3)) {
					s2.selectByVisibleText(resource);
					break;
				}
			}
			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");

			rm.calendarDayAfterTomorrowClick();

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
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("arguments[0].click();", AftrnunSlot);
					Thread.sleep(1000);
					WebElement AftrenoonAvailableTimeContainer = ap.getTimeSlotContainers().get(m)
							.findElement(By.id("tab-2-2"));
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
			System.out.println("Time: " + dayAfter + " " + startTime2);
			System.out.println("Product: " + appointmentToBook2);
			System.out.println("Resource: " + resourceName3);

			Assert.assertTrue(ap.getPopup1Content().getText().contains(clubName));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: " + dayAfter + " " + startTime2));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: " + appointmentToBook2));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(resourceName3));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(resourceName4));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(appointmentPrice));

			ap.getPopup1BookButton().click();

			rw.waitForAcceptButton();

			wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

			// Verifies the success message
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
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("Appointment is not changed");
			getScreenshot(this.getClass().getSimpleName(), driver);
		} catch (java.lang.AssertionError ae) {
			log.error(ae.getMessage(), ae);
			log.error("Appointment is not changed");
			getScreenshot(this.getClass().getSimpleName(), driver);
		}

	}

	@Test(priority = 2)
	public void ConfirmNewAppointmentIsScheduled() throws IOException, InterruptedException {
		try {

			rm.ConfirmAndCancelAppointmentNoFee(dayAfter, startTime2, appointmentToBook2);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("Appointment is not changed");
			getScreenshot(this.getClass().getSimpleName(), driver);
		} catch (java.lang.AssertionError ae) {
			log.error(ae.getMessage(), ae);
			log.error("Appointment is not changed");
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