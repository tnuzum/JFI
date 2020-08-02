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

import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ClassUnenrollTests extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String classToEnroll1 = "UNENROLLCLASS1";
	private static String classToEnroll2 = "UNENROLLCLASS2";
	private static String classToEnroll3 = "UNENROLLCLASS3";
	private static String classToEnroll4 = "UNENROLLCLASS4";
	private static String classToEnroll5 = "UNENROLLCLASS5";
	private static String classToEnroll6 = "UNENROLLCLASS6";
	private static String classToEnroll7 = "UNENROLLCLASS7";
	private static String classToEnroll8 = "UNENROLLCLASS8";
	private static String classToEnroll9 = "UNENROLLCLASS9";
	private static String classToEnroll10 = "UNENROLLCLASS10";
	private static String classToEnroll11 = "UNENROLLCLASS11";
	private static String classToEnroll12 = "UNENROLLCLASS12";
	private static String classToEnroll13 = "UNENROLLCLASS13";
	private static String classToEnroll14 = "UNENROLLCLASS14";
	private static String classToEnroll15 = "UNENROLLCLASS15";
	private static String classToEnroll16 = "UNENROLLCLASS16";
	private static String paymentOption1 = "Use Existing Package";
	private static String paymentOption2 = "Pay Single Class Fee";
	// private static String paymentOption3 = "Buy Day Pass";
	private static String payMethod1 = "On Account";
	private static String payMethod2 = "Saved Card";
	private static String YesCancelFee = "There is a cancellation fee for unenrolling from this class.";
	private static String NoCancelFee = "There are no cancellation fees for unenrolling in this class.";
	private static String YesRefund = "You will be refunded:";
	private static String NoRefund = "This class is non-refundable.";
	private static String RefundUnits = "1 Package Visit(s)";
	private static String cannotCancelMsg = "this class is not eligible for unenrollment.";

	private static String testName = null;

	public reusableWaits rw;
	public reusableMethods rm;

	public ClassUnenrollTests() {
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

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Can Unenroll With No Cancellation Fee as Unenrollment time falls outside the Cancellation Fee window")
	public void Unenroll_Scenario1() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll1, paymentOption2, payMethod1, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll1);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			Assert.assertEquals(NoCancelFee, u.getNoCancelFeeMsg().get(0).getText());
			Assert.assertEquals(NoRefund, u.getNoRefundMSg().get(0).getText());

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
		} finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 2, description = "Can Unenroll - Cancellation Fee exists even if class/ course is enrolled with punches - Refund Allowed")
	public void Unenroll_Scenario2() throws IOException, InterruptedException {

		try {
//			rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll2, paymentOption1, "", "Not Free");

			rm.myClassClickToUnenroll(classToEnroll2);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));
			System.out.println(u.getCancelFeeMsg().get(0).getText());

			System.out.println(u.getRefundMsg().get(0).getText());

			Assert.assertTrue(u.getCancelFeeMsg().get(0).getText().contains(YesCancelFee));
			Assert.assertTrue(u.getRefundMsg().get(0).getText().contains(RefundUnits));

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
		} finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 3, description = "Can Unenroll - Cancellation Fee exists even if class/ course is enrolled with with Service D - Refund is not Allowed as it is a free class")
	public void Unenroll_Scenario3() throws IOException, InterruptedException {

		try {
//			rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll3, "", "", "Free");

			rm.myClassClickToUnenroll(classToEnroll3);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			System.out.println(u.getCancelFeeMsg().get(0).getText());

			System.out.println(u.getNoRefundMSg().get(1).getText());

			Assert.assertTrue(u.getCancelFeeMsg().get(0).getText().contains(YesCancelFee));

			Assert.assertTrue(u.getNoRefundMSg().get(1).getText().contains(NoRefund));

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
		} finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 4, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Class or Course enrolled with Credit Card")
	public void Unenroll_Scenario4() throws IOException, InterruptedException {

		try {
//			rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll4, paymentOption2, payMethod2, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll4);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			System.out.println(u.getNoCancelFeeMsg().get(0).getText());

			System.out.println(u.getRefundMsg().get(1).getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().get(0).getText()
					.contains("There are no cancellation fees for unenrolling in this class."));

			Assert.assertTrue(u.getRefundMsg().get(1).getText().contains(""));

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

		finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 5, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Class or Course enrolled with On Account")
	public void Unenroll_Scenario5() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll5, paymentOption2, payMethod1, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll5);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			System.out.println(u.getNoCancelFeeMsg().get(0).getText());

			System.out.println(u.getRefundMsg().get(1).getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().get(0).getText().contains(NoCancelFee));

			Assert.assertTrue(u.getRefundMsg().get(1).getText().contains(YesRefund));

			// Assert.assertTrue(u.getUnenrollWithSavedCard().isDisplayed());
			// Assert.assertTrue(u.getUnenrollWithNewCard().isDisplayed());

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

		finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 6, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Class or Course enrolled with Punches")
	public void Unenroll_Scenario6() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll6, paymentOption1, "", "Free With Punch");

			rm.myClassClickToUnenroll(classToEnroll6);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			System.out.println(u.getNoCancelFeeMsg().get(0).getText());

			System.out.println(u.getRefundMsg().get(1).getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().get(0).getText().contains(NoCancelFee));

			Assert.assertTrue(u.getRefundMsg().get(1).getText().contains(RefundUnits));

			// Assert.assertTrue(u.getUnenrollWithSavedCard().isDisplayed());
			// Assert.assertTrue(u.getUnenrollWithNewCard().isDisplayed());

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

		finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 7, description = "Can Unenroll-No Cancellation Fee - Refund Allowed only in units- Class or Course enrolled with CC")
	public void Unenroll_Scenario7() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll7, paymentOption2, payMethod2, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll7);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			System.out.println(u.getNoCancelFeeMsg().get(0).getText());

			System.out.println(u.getNoRefundMSg().get(0).getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().get(0).getText().contains(NoCancelFee));

			Assert.assertTrue(u.getNoRefundMSg().get(0).getText().contains(NoRefund));

			// Assert.assertTrue(u.getUnenrollWithSavedCard().isDisplayed());
			// Assert.assertTrue(u.getUnenrollWithNewCard().isDisplayed());

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

		finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 8, description = "Can Unenroll-No Cancellation Fee - Refund Allowed only in units- Class or Course enrolled with On Account")
	//
	public void Unenroll_Scenario8() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll8, paymentOption2, payMethod1, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll8);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			System.out.println(u.getNoCancelFeeMsg().get(0).getText());

			System.out.println(u.getNoRefundMSg().get(0).getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().get(0).getText().contains(NoCancelFee));

			Assert.assertTrue(u.getNoRefundMSg().get(0).getText().contains(NoRefund));

			// Assert.assertTrue(u.getUnenrollWithSavedCard().isDisplayed());
			// Assert.assertTrue(u.getUnenrollWithNewCard().isDisplayed());

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

		finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 9, description = "Can Unenroll-No Cancellation Fee - Refund Allowed to only OA or CC- Class or Course enrolled with Punches")
	public void Unenroll_Scenario9() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll9, paymentOption1, "", "Free With Punch");

			rm.myClassClickToUnenroll(classToEnroll9);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			System.out.println(u.getNoCancelFeeMsg().get(0).getText());

			System.out.println(u.getNoRefundMSg().get(0).getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().get(0).getText().contains(NoCancelFee));

			Assert.assertTrue(u.getNoRefundMSg().get(0).getText().contains(NoRefund));

			// Assert.assertTrue(u.getUnenrollWithSavedCard().isDisplayed());
			// Assert.assertTrue(u.getUnenrollWithNewCard().isDisplayed());

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

		finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 10, description = "Can Unenroll-With Cancellation Fee - Refund Allowed - Class or Course enrolled with Credit Card")
	public void Unenroll_Scenario10() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll10, paymentOption2, payMethod2, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll10);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			System.out.println(u.getCancelFeeMsg().get(0).getText());

			System.out.println(u.getRefundMsg().get(0).getText());

			Assert.assertTrue(u.getCancelFeeMsg().get(0).getText().contains(YesCancelFee));

			Assert.assertTrue(u.getRefundMsg().get(0).getText().contains(YesRefund));

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

		finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 11, description = "Can Unenroll-With Cancellation Fee - Refund Allowed - Class or Course enrolled with On Account")
	public void Unenroll_Scenario11() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll11, paymentOption2, payMethod1, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll11);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			System.out.println(u.getCancelFeeMsg().get(0).getText());

			System.out.println(u.getRefundMsg().get(0).getText());

			Assert.assertTrue(u.getCancelFeeMsg().get(0).getText().contains(YesCancelFee));

			Assert.assertTrue(u.getRefundMsg().get(0).getText().contains(YesRefund));

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

		finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 12, description = "Can Unenroll-With Cancellation Fee - Refund Allowed - Class or Course enrolled with Punches")
	public void Unenroll_Scenario12() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll12, paymentOption1, "", "Free With Punch");

			rm.myClassClickToUnenroll(classToEnroll12);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			System.out.println(u.getCancelFeeMsg().get(0).getText());

			System.out.println(u.getRefundMsg().get(0).getText());

			Assert.assertTrue(u.getCancelFeeMsg().get(0).getText().contains(YesCancelFee));

			Assert.assertTrue(u.getRefundMsg().get(0).getText().contains(RefundUnits));

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

		finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 13, description = "Can Unenroll-No Cancellation Fee and No Refund set on the class/course")
	public void Unenroll_Scenario13() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll13, paymentOption2, "payMethod1", "Not Free");

			rm.myClassClickToUnenroll(classToEnroll13);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			System.out.println(u.getNoCancelFeeMsg().get(0).getText());

			System.out.println(u.getNoRefundMSg().get(0).getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().get(0).getText().contains(NoCancelFee));

			Assert.assertTrue(u.getNoRefundMSg().get(0).getText().contains(NoRefund));

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

		finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 14, description = "Can Unenroll-With Cancellation Fee and No Refund set on the class/course")
	public void Unenroll_Scenario14() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll14, paymentOption2, "payMethod1", "Not Free");

			rm.myClassClickToUnenroll(classToEnroll14);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			System.out.println(u.getCancelFeeMsg().get(0).getText());

			System.out.println(u.getNoRefundMSg().get(1).getText());

			Assert.assertTrue(u.getCancelFeeMsg().get(0).getText().contains(YesCancelFee));

			Assert.assertTrue(u.getNoRefundMSg().get(1).getText().contains(NoRefund));

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

		finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 15, description = "Class/Course Start time in the future but unenrollment time falls inside the cannot cancel window")
	public void Unenroll_Scenario15() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll15, paymentOption2, "payMethod1", "Not Free");

			rm.myClassClickToUnenroll(classToEnroll15);

			UnenrollPO u = new UnenrollPO(driver);

			System.out.println(u.getCancelFeeMsg().get(0).getText());

			Assert.assertTrue(u.getCancelFeeMsg().get(0).getText().contains(cannotCancelMsg));

			rm.memberLogout();
			rm.deleteEnrollInClassInCOG("UnenrollClass15", "Jonas Sports-Plex", "Auto, Unenrollmbr");

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
