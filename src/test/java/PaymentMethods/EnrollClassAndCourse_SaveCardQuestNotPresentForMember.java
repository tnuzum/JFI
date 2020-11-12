package PaymentMethods;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ShopPackagesPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollClassAndCourse_SaveCardQuestNotPresentForMember extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String classToEnroll = "BARRE COMBAT FUSION";
	private static String classNameDisplayed = "Barre Combat Fusion";
	private static String courseToEnroll = "FEECOURSE";
	private static String courseNameDisplayed = "FeeCourse";
	private static String CourseStartMonth = "Jun";
	private static int CourseStartYear = 2021;
	public reusableWaits rw;
	public reusableMethods rm;
	public static DashboardPO d;
	public static PaymentMethodsPO PM;
	public static ShopPackagesPO sp;
	private static PurchaseConfirmationPO PP;
	private static ClassSignUpPO c;

	public EnrollClassAndCourse_SaveCardQuestNotPresentForMember() {
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
		sp = new ShopPackagesPO(driver);
		PP = new PurchaseConfirmationPO(driver);
		c = new ClassSignUpPO(driver);

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();

	}

	@Test(priority = 1)
	public void verifyMemberCannotAddCC() throws InterruptedException, IOException {
		try {

			rm.activeMemberLogin("CannotAddCC", "Testing1!"); // Login to EME
			rw.waitForDashboardLoaded();

			d.getMyCoursesEventsScheduleButton().click();

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartYear(CourseStartYear);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartMonth(CourseStartMonth);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			Select s = new Select(c.getCourseClubDropdown());
			s.selectByVisibleText("Jonas Sports-Plex");

			rm.SelectClassOrCourseToEnroll(courseToEnroll);

			Thread.sleep(2000);
			if (c.getPopupSignupButtonCourse().isEnabled()) {
				c.getPopupSignupButtonCourse().click();

			} else {
				c.getPopupCancelButtonCourse().click();
				Assert.fail("SignUp button not available");

			}
			Thread.sleep(1000);
			wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), courseNameDisplayed));

			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Course Fee")) {
					driver.findElements(By.tagName("label")).get(i).click();
					break;
				}
			}

			c.getContinueButton().click();
			Thread.sleep(5000);

			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			PM.getNewCardButton().click();
			Thread.sleep(3000);

			String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			while (opacity.contains("1")) {
				PM.getNewCardButton().click();
				opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			}

			wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

			wait.until(ExpectedConditions.attributeToBe(PM.getPaymentButton(), "disabled", "true"));

			PM.getCardNumberField().sendKeys("4111111111111111");
			PM.getExpirationMonth().sendKeys("04");
			PM.getExpirationYear().sendKeys("22");
			PM.getSecurityCode().sendKeys("123");

			Assert.assertEquals(rm.isWebElementPresent(PM.getSaveCardQuestions()), false);

			Assert.assertEquals(rm.isWebElementPresent(PM.getOnAccountQuestions()), false);

			Assert.assertEquals(rm.isWebElementPresent(PM.getInClubQuestions()), false);

			Assert.assertTrue(PM.getSigPadInOut().getAttribute("style").contains("0"));

			Assert.assertEquals(PM.getCheckBox().getAttribute("disabled"), "true");

			d.getBreadcrumbDashboard().click();

			d.getMyClassesScheduleButton().click();

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectTomorrowDate();

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			s = new Select(c.getClassClubDropdown());
			s.selectByVisibleText("Jonas Sports-Plex");

			rm.SelectClassOrCourseToEnroll(classToEnroll);

			Thread.sleep(2000);

			if (c.getPopupSignUpButton().isEnabled()) {
				c.getPopupSignUpButton().click();

			} else {
				c.getPopupCancelButton().click();
				Assert.fail("SignUp button not available");

			}
			Thread.sleep(2000);
			wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), classNameDisplayed));

			radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Single Class Fee")) {
					driver.findElements(By.tagName("label")).get(i).click();
					break;
				}
			}

			c.getContinueButton().click();

			Thread.sleep(5000);

			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			PM.getNewCardButton().click();
			Thread.sleep(3000);

			opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			while (opacity.contains("1")) {
				PM.getNewCardButton().click();
				opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			}

			wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

			wait.until(ExpectedConditions.attributeToBe(PM.getPaymentButton(), "disabled", "true"));

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
			executor.executeScript("arguments[0].scrollIntoView(true);", PM.getPaymentButton());

			PM.getPaymentButton().click();

			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));

			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			d.getBreadcrumbDashboard().click();
			rm.unenrollFromClass();
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

	@Test(priority = 2, description = "Verify Card is not saved in COG")
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

	@Test(priority = 3, description = "Verify Freeze Member Can Add CC as the setting is true for Freeze member", enabled = false)
	public void VerifyFreezeMemberCanAddCC() throws InterruptedException, IOException {
		try {

			rm.activeMemberLogin("freezemember3", "Testing1!"); // Login to EME as Freeze status member

			d.getMyCoursesEventsScheduleButton().click();

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartYear(CourseStartYear);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartMonth(CourseStartMonth);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			Select s = new Select(c.getCourseClubDropdown());
			s.selectByVisibleText("Jonas Sports-Plex");

			rm.SelectClassOrCourseToEnroll(courseToEnroll);

			Thread.sleep(2000);
			if (c.getPopupSignupButtonCourse().isEnabled()) {
				c.getPopupSignupButtonCourse().click();

			} else {
				c.getPopupCancelButtonCourse().click();
				Assert.fail("SignUp button not available");

			}
			Thread.sleep(1000);
			wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), courseNameDisplayed));

			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Course Fee")) {
					driver.findElements(By.tagName("label")).get(i).click();
					break;
				}
			}

			c.getContinueButton().click();
			Thread.sleep(5000);

			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			PM.getNewCardButton().click();
			Thread.sleep(3000);

			String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			while (opacity.contains("1")) {
				PM.getNewCardButton().click();
				opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			}

			wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

			wait.until(ExpectedConditions.attributeToBe(PM.getPaymentButton(), "disabled", "true"));

			PM.getCardNumberField().sendKeys("4111111111111111");
			PM.getExpirationMonth().sendKeys("04");
			PM.getExpirationYear().sendKeys("22");
			PM.getSecurityCode().sendKeys("123");

			Assert.assertEquals(rm.isWebElementPresent(PM.getSaveCardQuestions()), false);

			Assert.assertEquals(rm.isWebElementPresent(PM.getOnAccountQuestions()), false);

			Assert.assertEquals(rm.isWebElementPresent(PM.getInClubQuestions()), false);

			Assert.assertTrue(PM.getSigPadInOut().getAttribute("style").contains("0"));

			Assert.assertEquals(PM.getCheckBox().getAttribute("disabled"), "true");

			d.getBreadcrumbDashboard().click();

			d.getMyClassesScheduleButton().click();

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectTomorrowDate();

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			s = new Select(c.getClassClubDropdown());
			s.selectByVisibleText("Jonas Sports-Plex");

			rm.SelectClassOrCourseToEnroll(classToEnroll);

			Thread.sleep(2000);

			if (c.getPopupSignUpButton().isEnabled()) {
				c.getPopupSignUpButton().click();

			} else {
				c.getPopupCancelButton().click();
				Assert.fail("SignUp button not available");

			}
			Thread.sleep(2000);
			wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), classNameDisplayed));

			radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Single Class Fee")) {
					driver.findElements(By.tagName("label")).get(i).click();
					break;
				}
			}

			c.getContinueButton().click();

			Thread.sleep(5000);

			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			PM.getNewCardButton().click();
			Thread.sleep(3000);

			opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			while (opacity.contains("1")) {
				PM.getNewCardButton().click();
				opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			}

			wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

			wait.until(ExpectedConditions.attributeToBe(PM.getPaymentButton(), "disabled", "true"));

			PM.getCardNumberField().sendKeys("4111111111111111");
			PM.getExpirationMonth().sendKeys("04");
			PM.getExpirationYear().sendKeys("22");
			PM.getSecurityCode().sendKeys("123");

			Assert.assertEquals(rm.isWebElementPresent(PM.getSaveCardQuestions()), true);

			Assert.assertEquals(rm.isWebElementPresent(PM.getOnAccountQuestions()), true);

			Assert.assertEquals(rm.isWebElementPresent(PM.getInClubQuestions()), true);

			Assert.assertTrue(PM.getSigPadInOut().getAttribute("style").contains("1"));

			Assert.assertEquals(PM.getCheckBox().getAttribute("disabled"), "false");

			PM.getSaveCardNo().click();

			wait.until(ExpectedConditions.elementToBeClickable(PM.getPaymentButton()));

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView(true);", PM.getPaymentButton());

			PM.getPaymentButton().click();

			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));

			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			d.getBreadcrumbDashboard().click();
			rm.unenrollFromClass();
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
		driver.close();
		driver = null;
	}

}
