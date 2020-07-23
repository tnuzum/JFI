package Unenroll;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class UnenrollTests extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String classToEnroll1 = "UNENROLLCLASS1";
	private static String classToEnroll2 = "UNENROLLCLASS2";
	private static String classToEnroll3 = "UNENROLLCLASS3";
	private static String paymentOption1 = "Use Existing Package";
	private static String paymentOption2 = "Pay Single Class Fee";
	private static String paymentOption3 = "Buy Day Pass";
	private static String payMethod1 = "On Account";
	private static String payMethod2 = "Saved Card";
	private static DashboardPO d;
	private static String testName = null;

	public reusableWaits rw;
	public reusableMethods rm;

	public UnenrollTests() {
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

		d = new DashboardPO(driver);

	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Can Unenroll With No Cancellation Fee as Unenrollment time falls outside the Cancellation Fee window")
	public void Unenroll_Scenario1() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll1, paymentOption2, payMethod1, "Not Free");

			d.getMyClassesClass1GearButton().click();

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(d.getmyClassesUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(d.getmyClassesUnenrollButton()));
			d.getmyClassesUnenrollButton().click();
			Thread.sleep(1000);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			Assert.assertEquals("There are no cancellation fees for unenrolling in this class.",
					u.getNoCancelFeeMsg().get(0).getText());
			Assert.assertEquals("This class is non-refundable.", u.getNoRefundMSg().get(0).getText());

			u.getUnenrollButton().click();

			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			u.getUnenrollConfirmYesButton().click();

			wait.until(ExpectedConditions.stalenessOf(u.getUnenrollConfirmYesButton()));
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

			rm.returnToDashboard();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}
	}

	@Test(priority = 2, description = "Can Unenroll - Cancellation Fee exists even if class/ course is enrolled with punches - Refund Allowed")
	public void Unenroll_Scenario2() throws IOException, InterruptedException {

		try {
//			rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll2, paymentOption1, "", "Not Free");

			d.getMyClassesClass1GearButton().click();

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(d.getmyClassesUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(d.getmyClassesUnenrollButton()));
			d.getmyClassesUnenrollButton().click();
			Thread.sleep(1000);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));
			System.out.println(u.getCancelFeeMsg().get(0).getText());

			System.out.println(u.getRefundMsg().get(0).getText());

			Assert.assertTrue(u.getCancelFeeMsg().get(0).getText()
					.contains("There is a cancellation fee for unenrolling from this class."));
			Assert.assertTrue(u.getRefundMsg().get(0).getText().contains("1 Package Visit(s)"));

			Assert.assertTrue(u.getUnenrollWithSavedCard().isDisplayed());
			Assert.assertTrue(u.getUnenrollWithNewCard().isDisplayed());

			u.getUnenrollButton().click();

			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			u.getUnenrollConfirmYesButton().click();

			wait.until(ExpectedConditions.stalenessOf(u.getUnenrollConfirmYesButton()));
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

			rm.returnToDashboard();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}
	}

	@Test(priority = 3, description = "Can Unenroll - Cancellation Fee exists even if class/ course is enrolled with with Service D - Refund is not Allowed as it is a free class")
	public void Unenroll_Scenario3() throws IOException, InterruptedException {

		try {
//			rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll3, "", "", "Free");

			d.getMyClassesClass1GearButton().click();

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(d.getmyClassesUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(d.getmyClassesUnenrollButton()));
			d.getmyClassesUnenrollButton().click();
			Thread.sleep(1000);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			System.out.println(u.getCancelFeeMsg().get(0).getText());

			System.out.println(u.getNoRefundMSg().get(1).getText());

			Assert.assertTrue(u.getCancelFeeMsg().get(0).getText()
					.contains("There is a cancellation fee for unenrolling from this class."));

			Assert.assertTrue(u.getNoRefundMSg().get(1).getText().contains("This class is non-refundable."));

			Assert.assertTrue(u.getUnenrollWithSavedCard().isDisplayed());
			Assert.assertTrue(u.getUnenrollWithNewCard().isDisplayed());

			u.getUnenrollButton().click();

			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			u.getUnenrollConfirmYesButton().click();

			wait.until(ExpectedConditions.stalenessOf(u.getUnenrollConfirmYesButton()));
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

			rm.returnToDashboard();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
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
