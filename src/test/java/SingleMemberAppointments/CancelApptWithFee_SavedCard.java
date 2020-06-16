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
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class CancelApptWithFee_SavedCard extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook = "PT 60 Mins-CancelWithFee";
	private static String resourceName = "FitExpert1";
	private static String clubNameDisplayed = "Club: Studio Jonas";
	private static String clubSpecifiPrice = "$5.00";
	private static String startTime;
	private static int appointmentsCount;

	public reusableWaits rw;
	public reusableMethods rm;

	public CancelApptWithFee_SavedCard() {
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

	@Test(priority = 1, description = "In this test appointment is booked with existing Packages to book the appointment and the cancelled using a cancellation fee")
	public void ScheduleAppointmentWithExistingPackageWithTwoResources() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("cancelmember3", "Testing1!");
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

			wait.until(ExpectedConditions.elementToBeClickable(st2));

			startTime = st2.getText();
			// st2.click();
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", st2);
			Thread.sleep(1000);

			System.out.println(ap.getPopup1Content().getText());
			System.out.println("Time: " + tomorrowsDate + " " + startTime);
			System.out.println("Product: " + appointmentToBook);
			System.out.println("Resource: " + resourceName);

			Assert.assertTrue(ap.getPopup1Content().getText().contains(clubNameDisplayed));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: " + tomorrowsDate + " " + startTime));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: " + appointmentToBook));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(resourceName));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(clubSpecifiPrice));

			ap.getPopup1BookButton().click();

			wait.until(ExpectedConditions.stalenessOf(ap.getPopup2OKButton()));

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
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		} catch (java.lang.AssertionError ae) {
			log.error(ae.getMessage(), ae);
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		}

	}

	@Test(priority = 3)
	public void CancelTransactionOfCancelling() throws IOException, InterruptedException {

		try {

			DashboardPO d = new DashboardPO(driver);

			for (int k = 0; k < appointmentsCount; k++) {
				if (d.getMyAppts().get(k).getText().contains(tomorrowsDate))

				{

					if (d.getMyAppts().get(k).getText().contains(startTime)) {
						d.getMyAppts().get(k).findElement(By.tagName("i")).click();

//					Thread.sleep(5000);
						WebElement EditButton = d.getEditButton().get(k);

						WebDriverWait wait = new WebDriverWait(driver, 30);
						wait.until(ExpectedConditions.visibilityOf(EditButton));
						wait.until(ExpectedConditions.elementToBeClickable(EditButton));

						EditButton.click();
						break;
					}
				}
			}
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
			Thread.sleep(2000);
			AppointmentsPO ap = new AppointmentsPO(driver);
			Assert.assertEquals(ap.getEditApptPageHeader().getText(), "Edit Appointment");
			wait.until(ExpectedConditions.visibilityOf(ap.getEditApptCancelButton()));
			ap.getEditApptCancelButton().click();
			Assert.assertTrue(
					ap.getCancelFeeSection().getText().contains("There is a fee for cancelling this appointment."));
			Assert.assertTrue(
					ap.getCancelFeeSection().getText().contains("If you proceed, you will be charged a fee of:"));

			wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));

			System.out.println(ap.getTotalAmount().getText());

			String[] totalAmt = ap.getTotalAmount().getText().split(": ");
			String FormatTotalAmt = totalAmt[1].trim();
			System.out.println(FormatTotalAmt);
			// Verifies the Pay button contains the total amount

			PaymentMethodsPO PM = new PaymentMethodsPO(driver);

			Assert.assertTrue(PM.getPaymentButton().getText().contains(FormatTotalAmt));

			int paymentMethodscount = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
			for (int i = 0; i < paymentMethodscount; i++) {
				if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
						.contains("1111")) {

					PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).click();
					break;
				}
			}

			// Click the Cancel button

			PM.getCancelButton().click();
			Thread.sleep(2000);
			rw.waitForDashboardLoaded();

			// Verifies the user is navigated to Dashboard
			Assert.assertEquals("Dashboard", driver.getTitle());

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

	@Test(priority = 4)
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
			Thread.sleep(2000);
			AppointmentsPO ap = new AppointmentsPO(driver);
			Assert.assertEquals(ap.getEditApptPageHeader().getText(), "Edit Appointment");
			ap.getEditApptCancelButton().click();
			Assert.assertTrue(
					ap.getCancelFeeSection().getText().contains("There is a fee for cancelling this appointment."));
			Assert.assertTrue(
					ap.getCancelFeeSection().getText().contains("If you proceed, you will be charged a fee of:"));

			wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));
			Thread.sleep(1000);

			System.out.println(ap.getTotalAmount().getText());

			String[] totalAmt = ap.getTotalAmount().getText().split(": ");
			String FormatTotalAmt = totalAmt[1].trim();
			System.out.println(FormatTotalAmt);
			// Verifies the Pay button contains the total amount

			PaymentMethodsPO PM = new PaymentMethodsPO(driver);

			Assert.assertTrue(PM.getPaymentButton().getText().contains(FormatTotalAmt));

			while (!PM.getOnAccountAndSavedCards().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			int paymentMethodscount = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
			for (int i = 0; i < paymentMethodscount; i++) {
				if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
						.contains("1111")) {

					PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).click();
					break;
				}
			}

			// Click the Pay button
			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}
			PM.getPaymentButton().click();

			wait.until(ExpectedConditions.stalenessOf(ap.getPopup2OKButton()));

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
			Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']"))
					.getText().contains(FormatTotalAmt));

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
			Thread.sleep(2000);

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