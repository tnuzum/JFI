package Unenroll;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class FamilyMbrClassUnenrollTests extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String classToEnroll1 = "UnenrollClass1";
	private static String classToEnroll2 = "UnenrollClass2";
	private static String classToEnroll3 = "UnenrollClass3";
	private static String classToEnroll4 = "UnenrollClass4";
	private static String classToEnroll5 = "UnenrollClass5";
	private static String classToEnroll6 = "UnenrollClass6";
	private static String classToEnroll7 = "UnenrollClass7";
	private static String classToEnroll8 = "UnenrollClass8";
	private static String classToEnroll9 = "UnenrollClass9";
	private static String classToEnroll10 = "UnenrollClass10";
	private static String classToEnroll10_1 = "UnenrollClass10_1";
	private static String classToEnroll11 = "UnenrollClass11";
	private static String classToEnroll12 = "UnenrollClass12";
	private static String classToEnroll13 = "UnenrollClass13";
	private static String classToEnroll14 = "UnenrollClass14";
	private static String classToEnroll15 = "UnenrollClass15";
	private static String classToEnroll16 = "UnenrollClass16";
	private static String classToEnroll17 = "UnenrollClass17";

	private static String paymentOption1 = "Use Existing Package";
	private static String paymentOption2 = "Pay Single Class Fee";
	// private static String paymentOption3 = "Buy Day Pass";
	private static String payMethod1 = "On Account";
	private static String payMethod2 = "Saved Card";
	private static String YesCancelFee = "There is a cancellation fee for unenrolling from this class.";
	private static String NoCancelFee = "There are no restrictions for unenrolling from this class.";
	private static String YesRefund = "You will be refunded:";
	private static String YesRefundOnAccount = "You will be refunded on account:";
	private static String NoRefund = "This class is non-refundable";
	private static String RefundUnits = "1 Package Visit(s)";
	private static String cannotCancelMsg = "this class is not eligible for unenrollment.";
	private static String youOweMsg = "You Owe:";
	private static String testName = null;

	public reusableWaits rw;
	public reusableMethods rm;

	public FamilyMbrClassUnenrollTests() {
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

		rm.activeMemberLogin("unenrollhoh", "Testing1!");
		rw.waitForDashboardLoaded();

	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Can Unenroll With No Cancellation Fee as Unenrollment time falls outside the Cancellation Fee window")
	public void Unenroll_Scenario1() throws IOException, InterruptedException {

		try {

			// rm.enrollFamilyMbrInClass(classToEnroll1, paymentOption2, payMethod1, "Not
			// Free", "Unenrollmbr1");

			rm.familyClassClickToUnenroll();

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll1));

			Assert.assertEquals(NoCancelFee, u.getNoCancelFeeMsg().getText());
			Assert.assertTrue(u.getRefundMsg().getText().contains(YesRefundOnAccount));

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
			rm.memberLogout();
		}
	}

	@Test(priority = 2, description = "Can Unenroll - Cancellation Fee exists even if class is enrolled with punches - Refund Allowed")
	public void Unenroll_Scenario2() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr2", "Testing1!");
			rm.enrollInClass(classToEnroll2, paymentOption1, "", "Not Free");

			rm.myClassClickToUnenroll(classToEnroll2);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll2));

			System.out.println(u.getCancelFeeMsg().getText());

			System.out.println(u.getRefundMsg().getText());

			Assert.assertTrue(u.getCancelFeeMsg().getText().contains(YesCancelFee));
			Assert.assertTrue(u.getRefundMsg().getText().contains(RefundUnits));

			Assert.assertTrue(u.getOnAccountAndSavedCards().isDisplayed());
			Assert.assertTrue(u.getNewCardButton().isDisplayed());

			u.getPaymentButton().click();

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
			rm.memberLogout();
		}
	}

	@Test(priority = 3, description = "Can Unenroll - Cancellation Fee exists even if class/ course is enrolled with with Service D - Refund is not Allowed as it is a free class")
	public void Unenroll_Scenario3() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr3", "Testing1!");
			rm.enrollInClass(classToEnroll3, "", "", "Free");

			rm.myClassClickToUnenroll(classToEnroll3);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll3));

			System.out.println(u.getCancelFeeMsg().getText());

			System.out.println(u.getNoRefundMSg().getText());

			Assert.assertTrue(u.getCancelFeeMsg().getText().contains(YesCancelFee));

			Assert.assertTrue(u.getNoRefundMSg().getText().contains(NoRefund));

			Assert.assertTrue(u.getOnAccountAndSavedCards().isDisplayed());
			Assert.assertTrue(u.getNewCardButton().isDisplayed());

			u.getPaymentButton().click();

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
			rm.memberLogout();
		}
	}

	@Test(priority = 4, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Class enrolled with Credit Card")
	public void Unenroll_Scenario4() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr4", "Testing1!");
			rm.enrollInClass(classToEnroll4, paymentOption2, payMethod2, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll4);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll4));

			System.out.println(u.getNoCancelFeeMsg().getText());

			System.out.println(u.getRefundMsg().getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().getText().contains(NoCancelFee));

			Assert.assertTrue(u.getRefundMsg().getText().contains(YesRefund));

			Assert.assertTrue(u.getOnAccountAndSavedCards().isDisplayed());
			Assert.assertTrue(u.getNewCardButton().isDisplayed());

			u.getRefundButton().click();

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
			rm.memberLogout();
		}
	}

	@Test(priority = 5, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Class enrolled with On Account")
	public void Unenroll_Scenario5() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr5", "Testing1!");
			// rm.enrollInClass(classToEnroll5, paymentOption2, payMethod1, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll5);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll5));

			System.out.println(u.getNoCancelFeeMsg().getText());

			System.out.println(u.getRefundMsg().getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().getText().contains(NoCancelFee));

			Assert.assertTrue(u.getRefundMsg().getText().contains(YesRefundOnAccount));

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
			rm.memberLogout();
		}
	}

	@Test(priority = 6, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Class enrolled with Punches")
	public void Unenroll_Scenario6() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr6", "Testing1!");
			rm.enrollInClass(classToEnroll6, paymentOption1, "", "Free With Punch");

			rm.myClassClickToUnenroll(classToEnroll6);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll6));
			System.out.println(u.getNoCancelFeeMsg().getText());

			System.out.println(u.getRefundMsg().getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().getText().contains(NoCancelFee));

			Assert.assertTrue(u.getRefundMsg().getText().contains(RefundUnits));

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
			rm.memberLogout();
		}
	}

	@Test(priority = 7, description = "Can Unenroll-No Cancellation Fee - Refund Allowed only in units- Class enrolled with CC")
	public void Unenroll_Scenario7() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr7", "Testing1!");
			rm.enrollInClass(classToEnroll7, paymentOption2, payMethod2, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll7);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll7));

			System.out.println(u.getNoCancelFeeMsg().getText());

			System.out.println(u.getNoRefundMSg().getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().getText().contains(NoCancelFee));

			Assert.assertTrue(u.getNoRefundMSg().getText().contains(NoRefund));

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
			rm.memberLogout();
		}
	}

	@Test(priority = 8, description = "Can Unenroll-No Cancellation Fee - Refund Allowed only in units- Class enrolled with On Account")
	//
	public void Unenroll_Scenario8() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr8", "Testing1!");
			rm.enrollInClass(classToEnroll8, paymentOption2, payMethod1, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll8);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll8));

			System.out.println(u.getNoCancelFeeMsg().getText());

			System.out.println(u.getNoRefundMSg().getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().getText().contains(NoCancelFee));

			Assert.assertTrue(u.getNoRefundMSg().getText().contains(NoRefund));

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
			rm.memberLogout();
		}
	}

	@Test(priority = 9, description = "Can Unenroll-No Cancellation Fee - Refund Allowed to only OA or CC- Class enrolled with Punches")
	public void Unenroll_Scenario9() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr9", "Testing1!");
			rm.enrollInClass(classToEnroll9, paymentOption1, "", "Free With Punch");

			rm.myClassClickToUnenroll(classToEnroll9);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll9));

			System.out.println(u.getNoCancelFeeMsg().getText());

			System.out.println(u.getNoRefundMSg().getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().getText().contains(NoCancelFee));

			Assert.assertTrue(u.getNoRefundMSg().getText().contains(NoRefund));

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
			rm.memberLogout();
		}
	}

	@Test(priority = 10, description = "Can Unenroll-With Cancellation Fee - Refund Allowed - Class enrolled with Credit Card")
	public void Unenroll_Scenario10() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr10", "Testing1!");
			rm.enrollInClass(classToEnroll10, paymentOption2, payMethod2, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll10);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll10));
			System.out.println(u.getCancelFeeMsg().getText());

			System.out.println(u.getRefundMsg().getText());

			Assert.assertTrue(u.getCancelFeeMsg().getText().contains(YesCancelFee));

			Assert.assertTrue(u.getRefundMsg().getText().contains(YesRefund));

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
			rm.memberLogout();
		}
	}

	@Test(priority = 11, description = "Can Unenroll-With Cancellation Fee > Refund Allowed - enrolled with Credit Card")
	public void Unenroll_Scenario10_1() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr11", "Testing1!");
			rm.enrollInClass(classToEnroll10_1, paymentOption2, payMethod2, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll10_1);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll10_1));

			System.out.println(u.getCancelFeeMsg().getText());

			System.out.println(u.getRefundMsg().getText());

			Assert.assertTrue(u.getCancelFeeMsg().getText().contains(YesCancelFee));

			Assert.assertTrue(u.getCancelFeeMsg().getText().contains(youOweMsg));

			// Assert.assertTrue(u.getRefundMsg().getText().contains(YesRefund));

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
			rm.memberLogout();
		}
	}

	@Test(priority = 12, description = "Can Unenroll-With Cancellation Fee - Refund Allowed - Class enrolled with On Account")
	public void Unenroll_Scenario11() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr12", "Testing1!");
			rm.enrollInClass(classToEnroll11, paymentOption2, payMethod1, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll11);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll11));

			System.out.println(u.getCancelFeeMsg().getText());

			System.out.println(u.getRefundMsg().getText());

			Assert.assertTrue(u.getCancelFeeMsg().getText().contains(YesCancelFee));

			Assert.assertTrue(u.getRefundMsg().getText().contains(YesRefund));

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
			rm.memberLogout();
		}
	}

	@Test(priority = 13, description = "Can Unenroll-With Cancellation Fee - Refund Allowed - Class enrolled with Punches")
	public void Unenroll_Scenario12() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr13", "Testing1!");
			rm.enrollInClass(classToEnroll12, paymentOption1, "", "Free With Punch");

			rm.myClassClickToUnenroll(classToEnroll12);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll12));

			System.out.println(u.getCancelFeeMsg().getText());

			System.out.println(u.getRefundMsg().getText());

			Assert.assertTrue(u.getCancelFeeMsg().getText().contains(YesCancelFee));

			Assert.assertTrue(u.getRefundMsg().getText().contains(RefundUnits));

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
			rm.memberLogout();
		}
	}

	@Test(priority = 14, description = "Can Unenroll-No Cancellation Fee and No Refund set on the class")
	public void Unenroll_Scenario13() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr14", "Testing1!");
			rm.enrollInClass(classToEnroll13, paymentOption2, payMethod1, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll13);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll13));

			System.out.println(u.getNoCancelFeeMsg().getText());

			System.out.println(u.getNoRefundMSg().getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().getText().contains(NoCancelFee));

			Assert.assertTrue(u.getNoRefundMSg().getText().contains(NoRefund));

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
			rm.memberLogout();
		}
	}

	@Test(priority = 15, description = "Can Unenroll-With Cancellation Fee and No Refund set on the class")
	public void Unenroll_Scenario14() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr15", "Testing1!");
			rm.enrollInClass(classToEnroll14, paymentOption2, payMethod1, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll14);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll14));

			System.out.println(u.getCancelFeeMsg().getText());

			System.out.println(u.getNoRefundMSg().getText());

			Assert.assertTrue(u.getCancelFeeMsg().getText().contains(YesCancelFee));

			Assert.assertTrue(u.getNoRefundMSg().getText().contains(NoRefund));

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
			rm.memberLogout();
		}
	}

	@Test(priority = 16, description = "Unenroll Free Class - Course-Refund section should be hidden")
	public void Unenroll_Scenario15() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr16", "Testing1!");
			rm.enrollInClass(classToEnroll15, "", "", "Free");

			rm.myClassClickToUnenroll(classToEnroll15);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll15));

			System.out.println(u.getNoCancelFeeMsg().getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().getText().contains(NoCancelFee));

			// Refund section should be hidden. add a step for that
			Assert.assertEquals(rm.isElementPresent(By.xpath("//div[contains(@class, 'alert alert-danger')]")), false);

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
			rm.memberLogout();
		}
	}

	@Test(priority = 17, description = "Payment and Refund Methods displaying On Account and Credit cards should follow the sell club's configuration")
	public void Unenroll_Scenario16() throws IOException, InterruptedException {

		try {

			// rm.activeMemberLogin("unenrollmbr17", "Testing1!");
			DashboardPO d = new DashboardPO(driver);
			ClassSignUpPO c = new ClassSignUpPO(driver);
			PaymentMethodsPO PM = new PaymentMethodsPO(driver);
			PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);

			d.getMyClassesScheduleButton().click();

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectTomorrowDate();

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			WebElement Club = c.getClassClubDropdown();
			Select s = new Select(Club);
			s.deselectByVisibleText("OnlinePayNotAllowed");

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectClassOrCourseToEnroll(classToEnroll16);

			Thread.sleep(2000);
			if (c.getPopupSignUpButton().isEnabled()) {
				c.getPopupSignUpButton().click();

			} else {
				c.getPopupCancelButton().click();
				Assert.fail("SignUp button not available");

			}

			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Single Class Fee")) {
					driver.findElements(By.tagName("label")).get(i).click();
					break;
				}
			}

			c.getContinueButton().click();

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//i[@class='fa fa-pencil-square-o']")));

			int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
			for (int i = 0; i < count; i++) {
				if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
						.contains("5454")) {

					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("arguments[0].scrollIntoView();",
							PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));

					jse.executeScript("arguments[0].click();",
							PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));

					// PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).click();
					break;
				}
			}

			PM.getPaymentButton().click();

			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			int count1 = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count1; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

				{
					// rw.linksToBeClickable();
					driver.findElements(By.tagName("a")).get(i).click();
					break;
				}

			}
			rw.waitForDashboardLoaded();

			rm.myClassClickToUnenroll(classToEnroll16);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll6));

			System.out.println(u.getCancelFeeMsg().getText());

			Assert.assertTrue(u.getCancelFeeMsg().getText().contains(YesCancelFee));

			Assert.assertTrue(u.getRefundMsg().getText().contains(YesRefund));

			// add a step to verify that On Account and Saved cards are not displayed

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
			rm.memberLogout();
		}
	}

	@Test(priority = 18, description = "class Start time in the future but unenrollment time falls inside the cannot cancel window")
	public void Unenroll_Scenario17() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr18", "Testing1!");
			rm.enrollInClass(classToEnroll17, paymentOption2, payMethod1, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll17);

			UnenrollPO u = new UnenrollPO(driver);

			System.out.println(u.getCancelFeeMsg().getText());

			Assert.assertTrue(u.getCancelFeeMsg().getText().contains(cannotCancelMsg));

			rm.memberLogout();
			rm.deleteEnrollInClassInCOG(classToEnroll17, "Jonas Sports-Plex", "Auto, Unenrollmbr18");

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
