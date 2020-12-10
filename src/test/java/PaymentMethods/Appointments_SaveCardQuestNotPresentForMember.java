package PaymentMethods;

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
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class Appointments_SaveCardQuestNotPresentForMember extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook = "PT 60 Mins-AQTest";
	private static String startTime1;
	private static String startTime2 = "12:15 PM";
	private static int appointmentsCount = 1;
	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;
	public static DashboardPO d;
	public static PaymentMethodsPO PM;
	public static AppointmentsPO ap;

	public Appointments_SaveCardQuestNotPresentForMember() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

	@BeforeClass
	public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		d = new DashboardPO(driver); // Define the driver for Dash Board page Objects
		PM = new PaymentMethodsPO(driver);
		ap = new AppointmentsPO(driver);
		jse = (JavascriptExecutor) driver;

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();

	}

	@Test(priority = 1, description = "verify Member Cannot Add CC on Book Appointment")
	public void verifyMemberCannotAddCC_BookAppointment() throws InterruptedException, IOException {
		try {
			rm.activeMemberLogin("cannotaddcc", "Testing1!");
			rw.waitForDashboardLoaded();

			DashboardPO p = new DashboardPO(driver);
			jse.executeScript("arguments[0].click();", p.getMyApptsScheduleButton());
			Thread.sleep(2000);

			rm.catchErrorMessage();

			WebDriverWait wait = new WebDriverWait(driver, 30);

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

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");

			rm.calendarTomorrowClick();

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
			startTime1 = st2.getText();
			// st2.click();

			jse.executeScript("arguments[0].click();", st2);
			Thread.sleep(2000);

			Assert.assertEquals(ap.getPopup1Title().getText(), "Package Required");

			ap.getPopup1BookButton().click();
			Thread.sleep(3000);

			while (ap.getRateBox().getText().isBlank()) {
				System.out.println("Waiting");
			}
			wait.until(ExpectedConditions.textToBePresentInElement(ap.getRateBox(), appointmentToBook.toUpperCase()));

			Thread.sleep(1000);

			// Noting down the total amount
			wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));

			PaymentMethodsPO PM = new PaymentMethodsPO(driver);
			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			jse.executeScript("arguments[0].click();", PM.getNewCardButton());
			Thread.sleep(3000);

			String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			while (opacity.contains("1")) {
				PM.getNewCardButton().click();
				opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			}

			wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

			wait.until(ExpectedConditions.attributeToBe(ap.getPaymentButton(), "disabled", "true"));

			PM.getCardNumberField().sendKeys("4111111111111111");
			PM.getExpirationMonth().sendKeys("04");
			PM.getExpirationYear().sendKeys("22");
			PM.getSecurityCode().sendKeys("123");

			Assert.assertEquals(rm.isWebElementPresent(PM.getSaveCardQuestions()), false);

			Assert.assertEquals(rm.isWebElementPresent(PM.getOnAccountQuestions()), false);

			Assert.assertEquals(rm.isWebElementPresent(PM.getInClubQuestions()), false);

			Assert.assertTrue(PM.getSigPadInOut().getAttribute("style").contains("0"));

			Assert.assertEquals(PM.getCheckBox().getAttribute("disabled"), "true");

			wait.until(ExpectedConditions.elementToBeClickable(ap.getPaymentButton()));

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", ap.getPaymentButton());

			// ap.getPaymentButton().click();
			rw.waitForAcceptButton();

			// rw.waitForAcceptButton();

			wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

//Verifies the success message
			Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
			ap.getPopup2OKButton().click();
			Thread.sleep(1000);
			rm.memberLogout();
			rm.VerifyFOPNotSavedInCOG("1147744", "Jonas Sports-Plex", "1111");

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

	}

	@Test(priority = 2, description = " verify Member Cannot Add CC on Change Appointment")
	public void verifyMemberCannotAddCC_ChangeAppointment() throws InterruptedException, IOException {
		try {
			rm.activeMemberLogin("cannotaddcc", "Testing1!");

			rw.waitForDashboardLoaded();
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
					By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
			appointmentsCount = d.getMyAppts().size();

			for (int i = 0; i < appointmentsCount; i++) {
				if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

				{

					if (d.getMyAppts().get(i).getText().contains(startTime1)) {

						Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook.toUpperCase()));
						wait.until(ExpectedConditions
								.elementToBeClickable(d.getMyAppts().get(i).findElement(By.tagName("i"))));
						d.getMyAppts().get(i).findElement(By.tagName("i")).click();

						WebElement EditButton = d.getEditButton().get(i);

						wait.until(ExpectedConditions.visibilityOf(EditButton));
						wait.until(ExpectedConditions.elementToBeClickable(EditButton));

						jse.executeScript("arguments[0].click();", EditButton);
						break;
					}
				}
			}

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
			Thread.sleep(2000);

			jse.executeScript("arguments[0].click();", ap.getEditApptChangeButton());
			Thread.sleep(1000);
			jse.executeScript("arguments[0].click();", ap.getEditApptProceedButton1());

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");
			Thread.sleep(2000);

			rm.calendarDayAfterTomorrowClick();

			List<WebElement> TimeSlots = ap.getTimeSlotContainers().get(0).findElements(By.tagName("a"));
			WebElement AftrnunSlot = TimeSlots.get(1);
			wait.until(ExpectedConditions.elementToBeClickable(AftrnunSlot));
			while (!AftrnunSlot.isEnabled())// while button is NOT(!) enabled
			{
				System.out.println("Waiting for available times");
			}

			// AftrnunSlot.click();

			jse.executeScript("arguments[0].click();", AftrnunSlot);
			Thread.sleep(1000);
			WebElement AftrenoonAvailableTimeContainer = ap.getTimeSlotContainers().get(0)
					.findElement(By.id("tab-2-0"));
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
			jse.executeScript("arguments[0].click();", secondAvailableTimeAfternoon);

			Thread.sleep(2000);

			Assert.assertEquals(ap.getPopup1Title().getText(), "Package Required");

			ap.getPopup1BookButton().click();
			Thread.sleep(3000);

			while (ap.getRateBox().getText().isBlank()) {
				System.out.println("Waiting");
			}
			wait.until(ExpectedConditions.textToBePresentInElement(ap.getRateBox(), appointmentToBook.toUpperCase()));
			Thread.sleep(1000);
			wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));

			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);

			}

			jse.executeScript("arguments[0].click();", PM.getNewCardButton());
			Thread.sleep(3000);

			String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			while (opacity.contains("1")) {
				PM.getNewCardButton().click();
				opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			}

			wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

			PM.getCardNumberField().sendKeys("4111111111111111");
			PM.getExpirationMonth().sendKeys("04");
			PM.getExpirationYear().sendKeys("22");
			PM.getSecurityCode().sendKeys("123");

			Assert.assertEquals(rm.isWebElementPresent(PM.getSaveCardQuestions()), false);

			Assert.assertEquals(rm.isWebElementPresent(PM.getOnAccountQuestions()), false);

			Assert.assertEquals(rm.isWebElementPresent(PM.getInClubQuestions()), false);

			Assert.assertTrue(PM.getSigPadInOut().getAttribute("style").contains("0"));

			Assert.assertEquals(PM.getCheckBox().getAttribute("disabled"), "true");

			wait.until(ExpectedConditions.elementToBeClickable(ap.getPaymentButton()));

			// Click the Pay button
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", ap.getPaymentButton());
//			ap.getPaymentButton().click();
			rw.waitForAcceptButton();

			// rw.waitForAcceptButton();

			wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

//Verifies the success message
			Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
			ap.getPopup2OKButton().click();
			Thread.sleep(1000);
			rm.memberLogout();
			rm.VerifyFOPNotSavedInCOG("1147744", "Jonas Sports-Plex", "1111");

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

	}

	@Test(priority = 3, description = " verify Member Cannot Add CC on Cancel Appointment")
	public void verifyMemberCannotAddCC_CancelAppointment() throws InterruptedException, IOException {
		try {
			rm.ApptCheckinInCOG("Auto, cannotaddcc", appointmentToBook, "cannotaddcc", "2");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			for (int k = 0; k < appointmentsCount; k++) {
				if (d.getMyAppts().get(k).getText().contains(dayAfter))

				{

					if (d.getMyAppts().get(k).getText().contains(startTime2)) {
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

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
			Thread.sleep(2000);
			AppointmentsPO ap = new AppointmentsPO(driver);
			Assert.assertEquals(ap.getEditApptPageHeader().getText(), "Edit Appointment");
			wait.until(ExpectedConditions.visibilityOf(ap.getEditApptCancelButton()));
			ap.getEditApptCancelButton().click();

			wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));
			Thread.sleep(4000);
			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);

			}

			jse.executeScript("arguments[0].click();", PM.getNewCardButton());
			Thread.sleep(3000);

			String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			while (opacity.contains("1")) {
				PM.getNewCardButton().click();
				opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			}

			wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

			PM.getCardNumberField().sendKeys("4111111111111111");
			PM.getExpirationMonth().sendKeys("04");
			PM.getExpirationYear().sendKeys("22");
			PM.getSecurityCode().sendKeys("123");

			Assert.assertEquals(rm.isWebElementPresent(PM.getSaveCardQuestions()), false);

			Assert.assertEquals(rm.isWebElementPresent(PM.getOnAccountQuestions()), false);

			Assert.assertEquals(rm.isWebElementPresent(PM.getInClubQuestions()), false);

			Assert.assertTrue(PM.getSigPadInOut().getAttribute("style").contains("0"));

			Assert.assertEquals(PM.getCheckBox().getAttribute("disabled"), "true");

			wait.until(ExpectedConditions.elementToBeClickable(PM.getPaymentButton()));

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", PM.getPaymentButton());

			// PM.getPaymentButton().click();

			rw.waitForAcceptButton();

			// rw.waitForAcceptButton();

			wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

//Verifies the success message
			Assert.assertEquals(ap.getPopup2Title().getText(), "Success");
			ap.getPopup2OKButton().click();
			Thread.sleep(1000);
			rm.memberLogout();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

	}

	@Test(priority = 4, description = "Verify Card is not saved in COG")
	public void VerifyCardNotSavedInCOG() throws InterruptedException, IOException {
		try {

			rm.VerifyFOPNotSavedInCOG("1147744", "Jonas Sports-Plex", "1111");

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

	}

	@Test(priority = 4, description = "Verify Freeze Member Can Add CC as the setting is true for Freeze member", enabled = true)
	public void VerifyMemberCanAddCC() throws InterruptedException, IOException {
		try {

			rm.activeMemberLogin("bauto", "Testing1!"); // Login to EME as Freeze status member

			rw.waitForDashboardLoaded();

			DashboardPO p = new DashboardPO(driver);
			jse.executeScript("arguments[0].click();", p.getMyApptsScheduleButton());
			Thread.sleep(2000);

			rm.catchErrorMessage();

			WebDriverWait wait = new WebDriverWait(driver, 30);

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

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");

			rm.calendarTomorrowClick();

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
			startTime1 = st2.getText();
			// st2.click();

			jse.executeScript("arguments[0].click();", st2);
			Thread.sleep(2000);

			Assert.assertEquals(ap.getPopup1Title().getText(), "Package Required");

			ap.getPopup1BookButton().click();
			Thread.sleep(3000);

			while (ap.getRateBox().getText().isBlank()) {
				System.out.println("Waiting");
			}
			wait.until(ExpectedConditions.textToBePresentInElement(ap.getRateBox(), appointmentToBook.toUpperCase()));

			Thread.sleep(1000);

			// Noting down the total amount
			wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));

			PaymentMethodsPO PM = new PaymentMethodsPO(driver);
			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			jse.executeScript("arguments[0].click();", PM.getNewCardButton());
			Thread.sleep(3000);

			String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			while (opacity.contains("1")) {
				PM.getNewCardButton().click();
				opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			}

			wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

			wait.until(ExpectedConditions.attributeToBe(ap.getPaymentButton(), "disabled", "true"));

			PM.getCardNumberField().sendKeys("4111111111111111");
			PM.getExpirationMonth().sendKeys("04");
			PM.getExpirationYear().sendKeys("22");
			PM.getSecurityCode().sendKeys("123");

			Assert.assertEquals(rm.isWebElementPresent(PM.getSaveCardQuestions()), true);

			Assert.assertEquals(rm.isWebElementPresent(PM.getOnAccountQuestions()), true);

			Assert.assertEquals(rm.isWebElementPresent(PM.getInClubQuestions()), true);

			Assert.assertTrue(PM.getSigPadInOut().getAttribute("style").contains("1"));

			Assert.assertEquals(PM.getCheckBox().getAttribute("disabled"), null);

			rm.memberLogout();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}
