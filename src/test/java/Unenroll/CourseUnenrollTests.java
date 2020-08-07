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

public class CourseUnenrollTests extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String courseToEnroll1 = "UnenrollCourse1";
	private static String courseToEnroll2 = "UnenrollCourse2";
	private static String courseToEnroll3 = "UnenrollCourse3";
	private static String courseToEnroll4 = "UnenrollCourse4";
	private static String courseToEnroll5 = "UnenrollCourse5";
	private static String courseToEnroll6 = "UnenrollCourse6";
	private static String courseToEnroll7 = "UnenrollCourse7";
	private static String courseToEnroll8 = "UnenrollCourse8";
	private static String courseToEnroll9 = "UnenrollCourse9";
	private static String courseToEnroll10 = "UnenrollCourse10";
	private static String courseToEnroll10_1 = "UnenrollCourse10_1";
	private static String courseToEnroll11 = "UnenrollCourse11";
	private static String courseToEnroll12 = "UnenrollCourse12";
	private static String courseToEnroll13 = "UnenrollCourse13";
	private static String courseToEnroll14 = "UnenrollCourse14";
	private static String courseToEnroll15 = "UnenrollCourse15";
	private static String courseToEnroll16 = "UnenrollCourse16";
	private static String courseToEnroll17 = "UnenrollCourse17";

	private static String CourseStartMonth = "Dec";
	private static String dsiredMonthYear = "December 2020";

	private static String paymentOption1 = "Use Existing Package";
	private static String paymentOption2 = "Pay Course Fee";
	// private static String paymentOption3 = "Buy Day Pass";
	private static String payMethod1 = "On Account";
	private static String payMethod2 = "Saved Card";
	private static String YesCancelFee = "There is a cancellation fee for unenrolling from this class.";
	private static String NoCancelFee = "There are no cancellation fees for unenrolling in this class.";
	private static String YesRefund = "You will be refunded:";
	private static String NoRefund = "This class is non-refundable.";
	private static String RefundUnits = "1 Package Visit(s)";
	private static String cannotCancelMsg = "this class is not eligible for unenrollment.";
	private static String youOweMsg = "You Owe:";
	private static String testName = null;

	public reusableWaits rw;
	public reusableMethods rm;

	public CourseUnenrollTests() {
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
			rm.activeMemberLogin("unenrollmbr1", "Testing1!");
			rm.enrollInCourse(courseToEnroll1, paymentOption2, payMethod1, "Not Free", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll1));

			Assert.assertEquals(NoCancelFee, u.getNoCancelFeeMsg().getText());
			Assert.assertEquals(NoRefund, u.getNoRefundMSg().getText());

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

	@Test(priority = 2, description = "Can Unenroll - Cancellation Fee exists even if course is enrolled with punches - Refund Allowed")
	public void Unenroll_Scenario2() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr2", "Testing1!");
			rm.enrollInCourse(courseToEnroll2, paymentOption1, "", "Not Free", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll2));
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

	@Test(priority = 3, description = "Can Unenroll - Cancellation Fee exists even if course is enrolled with with Service D - Refund is not Allowed as it is a free Course")
	public void Unenroll_Scenario3() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr3", "Testing1!");
			rm.enrollInCourse(courseToEnroll3, "", "", "Free", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll3));

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

	@Test(priority = 4, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Course enrolled with Credit Card")
	public void Unenroll_Scenario4() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr4", "Testing1!");
			rm.enrollInCourse(courseToEnroll4, paymentOption2, payMethod2, "Not Free", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll4));

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

	@Test(priority = 5, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Course enrolled with On Account")
	public void Unenroll_Scenario5() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr5", "Testing1!");
			rm.enrollInCourse(courseToEnroll5, paymentOption2, payMethod1, "Not Free", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll5));

			System.out.println(u.getNoCancelFeeMsg().getText());

			System.out.println(u.getRefundMsg().getText());

			Assert.assertTrue(u.getNoCancelFeeMsg().getText().contains(NoCancelFee));

			Assert.assertTrue(u.getRefundMsg().getText().contains(YesRefund));

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

	@Test(priority = 6, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Course enrolled with Punches")
	public void Unenroll_Scenario6() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr6", "Testing1!");
			rm.enrollInCourse(courseToEnroll6, paymentOption1, "", "Free With Punch", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll6));

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

	@Test(priority = 7, description = "Can Unenroll-No Cancellation Fee - Refund Allowed only in units- Course enrolled with CC")
	public void Unenroll_Scenario7() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr7", "Testing1!");
			rm.enrollInCourse(courseToEnroll7, paymentOption2, payMethod2, "Not Free", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll7));

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

	@Test(priority = 8, description = "Can Unenroll-No Cancellation Fee - Refund Allowed only in units- Course enrolled with On Account")
	//
	public void Unenroll_Scenario8() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr8", "Testing1!");
			rm.enrollInCourse(courseToEnroll8, paymentOption2, payMethod1, "Not Free", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll8));

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

	@Test(priority = 9, description = "Can Unenroll-No Cancellation Fee - Refund Allowed to only OA or CC- Course enrolled with Punches")
	public void Unenroll_Scenario9() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr9", "Testing1!");
			rm.enrollInCourse(courseToEnroll9, paymentOption1, "", "Free With Punch", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll9));

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

	@Test(priority = 10, description = "Can Unenroll-With Cancellation Fee - Refund Allowed - Course enrolled with Credit Card")
	public void Unenroll_Scenario10() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr10", "Testing1!");
			rm.enrollInCourse(courseToEnroll10, paymentOption2, payMethod2, "Not Free", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll10));

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
			// rm.activeMemberLogin("unenrollmbr11", "Testing1!");
			rm.enrollInCourse(courseToEnroll10_1, paymentOption2, payMethod2, "Not Free", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll10_1));

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

	@Test(priority = 12, description = "Can Unenroll-With Cancellation Fee - Refund Allowed - Course enrolled with On Account")
	public void Unenroll_Scenario11() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr12", "Testing1!");
			rm.enrollInCourse(courseToEnroll11, paymentOption2, payMethod1, "Not Free", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll11));

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

	@Test(priority = 13, description = "Can Unenroll-With Cancellation Fee - Refund Allowed - Course enrolled with Punches")
	public void Unenroll_Scenario12() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr13", "Testing1!");
			rm.enrollInCourse(courseToEnroll12, paymentOption1, "", "Free With Punch", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll12));

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

	@Test(priority = 14, description = "Can Unenroll-No Cancellation Fee and No Refund set on the course")
	public void Unenroll_Scenario13() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr14", "Testing1!");
			rm.enrollInCourse(courseToEnroll13, paymentOption2, payMethod1, "Not Free", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll13));

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

	@Test(priority = 15, description = "Can Unenroll-With Cancellation Fee and No Refund set on the course")
	public void Unenroll_Scenario14() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr15", "Testing1!");
			rm.enrollInCourse(courseToEnroll14, paymentOption2, payMethod1, "Not Free", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll14));

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

	@Test(priority = 16, description = "Unenroll Free Course - Course-Refund section should be hidden")
	public void Unenroll_Scenario15() throws IOException, InterruptedException {

		try {
			// rm.activeMemberLogin("unenrollmbr16", "Testing1!");
			rm.enrollInCourse(courseToEnroll15, "", "", "Free", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll15));

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

			rm.SelectClassOrCourseToEnroll(courseToEnroll16);

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

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll16));

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

	@Test(priority = 18, description = "course Start time in the future but unenrollment time falls inside the cannot cancel window")
	public void Unenroll_Scenario17() throws IOException, InterruptedException {

		try {

			// rm.activeMemberLogin("unenrollmbr18", "Testing1!");
			rm.enrollInCourse(courseToEnroll17, paymentOption2, payMethod1, "Not Free", CourseStartMonth);

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			UnenrollPO u = new UnenrollPO(driver);

			System.out.println(u.getCancelFeeMsg().getText());

			Assert.assertTrue(u.getCancelFeeMsg().getText().contains(cannotCancelMsg));

			rm.memberLogout();

			rm.deleteEnrollInCourseInCOG(courseToEnroll17, "Jonas Sports-Plex", "Auto, UnenrollMbr18");

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
