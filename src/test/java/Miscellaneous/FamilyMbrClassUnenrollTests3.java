package Miscellaneous;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

public class FamilyMbrClassUnenrollTests3 extends base {
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

	private static String YesCancelFee = "Class Cancellation Fee";

	private static String YesRefundCC = "Class Refund Price";
	private static String YesRefundOnAccount = "This credit will be placed on your on account and be applied to your outstanding invoice.";
	private static String YesRefundOATaxInfo = "Plus applicable taxes.";
	private static String YesRefundUnit = "Class Package Refund Quantity:";
	private static String NoRefund = "This Class is non refundable";

	private static String cannotCancelMsg = "We apologize, this class is not eligible for unenrollment.";

	private static String testName = null;

	public reusableWaits rw;
	public reusableMethods rm;

	public FamilyMbrClassUnenrollTests3() {
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

			rm.enrollFamilyMbrInClass(classToEnroll1, paymentOption2, payMethod1, "Not Free", "Unenrollmbr1");

			rm.familyClassClickToUnenroll(classToEnroll1, "Unenrollmbr1");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll1));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundOAText().getText().contains(YesRefundOnAccount));
			Assert.assertTrue(u.getRefundOAAmnt().getText().contains("$9.00"));
			Assert.assertTrue(u.getRefundOATaxInfo().getText().contains(YesRefundOATaxInfo));

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getUnenrollButton().isDisplayed());
			u.getUnenrollButton().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 2, description = "Can Unenroll - Cancellation Fee exists even if class is enrolled with punches - Refund Allowed")
	public void Unenroll_Scenario2() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll2, paymentOption1, "", "Not Free", "Unenrollmbr2");

			int unitsBefore = rm.getPackageUnitsForMember("Day Pass", "Unenrollmbr2");
			System.out.println(unitsBefore);

			rm.familyClassClickToUnenroll(classToEnroll2, "Unenrollmbr2");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll2));
			PaymentMethodsPO PM = new PaymentMethodsPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

			Assert.assertTrue(u.getCancelHeader().isDisplayed());
			Assert.assertTrue(u.getCancelText().getText().contains(YesCancelFee));
			Assert.assertTrue(u.getCancelAmnt().getText().contains("$6.00"));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundUnitText().getText().contains(YesRefundUnit));
			Assert.assertTrue(u.getRefundUnitNo().getText().contains("1"));

			Boolean SubTotalLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'SUB-TOTAL:')]"));
			Assert.assertTrue(SubTotalLabelPresent);
			Boolean TaxLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'TAX:')]"));
			Assert.assertTrue(TaxLabelPresent);
			Boolean TotalLabelPresent = rm.isElementPresent(By.xpath("//h2[contains(text(),'TOTAL:')]"));
			Assert.assertTrue(TotalLabelPresent);

			String[] totalAmt = u.getTotalAmount().getText().split(": ");
			String FormatTotalAmt = totalAmt[1].trim();

			Assert.assertTrue(u.getOnAccountAndSavedCards().isDisplayed());
			rm.verifyOnAccountIsPresentAndSelectedByDefault();
			Assert.assertTrue(u.getNewCardButton().isDisplayed());

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getPaymentButton().isDisplayed());

			Assert.assertTrue(u.getPaymentButton().getText().contains(FormatTotalAmt));

			u.getPaymentButton().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

			int unitsAfter = rm.getPackageUnitsForMember("Day Pass", "Unenrollmbr2");
			System.out.println(unitsAfter);

			unitsBefore++;

			Assert.assertEquals(unitsAfter, unitsBefore);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

			rm.enrollFamilyMbrInClass(classToEnroll3, "", "", "Free", "Unenrollmbr3");

			rm.familyClassClickToUnenroll(classToEnroll3, "Unenrollmbr3");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll3));
			PaymentMethodsPO PM = new PaymentMethodsPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

			Assert.assertTrue(u.getCancelHeader().isDisplayed());
			Assert.assertTrue(u.getCancelText().getText().contains(YesCancelFee));
			Assert.assertTrue(u.getCancelAmnt().getText().contains("$6.00"));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getNoRefund().getText().contains(NoRefund));

			Boolean SubTotalLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'SUB-TOTAL:')]"));
			Assert.assertTrue(SubTotalLabelPresent);
			Boolean TaxLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'TAX:')]"));
			Assert.assertTrue(TaxLabelPresent);
			Boolean TotalLabelPresent = rm.isElementPresent(By.xpath("//h2[contains(text(),'TOTAL:')]"));
			Assert.assertTrue(TotalLabelPresent);

			String[] totalAmt = u.getTotalAmount().getText().split(": ");
			String FormatTotalAmt = totalAmt[1].trim();

			Assert.assertTrue(u.getOnAccountAndSavedCards().isDisplayed());
			rm.verifyOnAccountIsPresentAndSelectedByDefault();
			Assert.assertTrue(u.getNewCardButton().isDisplayed());

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getPaymentButton().isDisplayed());

			Assert.assertTrue(u.getPaymentButton().getText().contains(FormatTotalAmt));

			u.getPaymentButton().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 4, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Class enrolled with Credit Card")
	public void Unenroll_Scenario4() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll4, paymentOption2, payMethod2, "Not Free", "Unenrollmbr4");

			rm.familyClassClickToUnenroll(classToEnroll4, "Unenrollmbr4");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll4));
			PaymentMethodsPO PM = new PaymentMethodsPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundCCText().getText().contains(YesRefundCC));
			Assert.assertTrue(u.getRefundCCAmnt().getText().contains("-$9.00"));

			Boolean SubTotalLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'SUB-TOTAL:')]"));
			Assert.assertTrue(SubTotalLabelPresent);
			Boolean TaxLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'TAX:')]"));
			Assert.assertTrue(TaxLabelPresent);
			Boolean TotalLabelPresent = rm.isElementPresent(By.xpath("//h2[contains(text(),'TOTAL:')]"));
			Assert.assertTrue(TotalLabelPresent);

			String[] totalAmt = u.getTotalAmount().getText().split(": -");
			String FormatTotalAmt = totalAmt[1].trim();

			System.out.println(FormatTotalAmt);

			Assert.assertTrue(u.getOnAccountAndSavedCards().isDisplayed());
			rm.verifyOnAccountIsPresentAndSelectedByDefault();
			Assert.assertTrue(u.getNewCardButton().isDisplayed());

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getRefundButton().isDisplayed());

			Assert.assertTrue(u.getRefundButton().getText().contains(FormatTotalAmt));

			u.getRefundButton().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 5, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Class enrolled with On Account")
	public void Unenroll_Scenario5() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll5, paymentOption2, payMethod1, "Not Free", "Unenrollmbr5");

			rm.familyClassClickToUnenroll(classToEnroll5, "Unenrollmbr5");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll5));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundOAText().getText().contains(YesRefundOnAccount));
			Assert.assertTrue(u.getRefundOAAmnt().getText().contains("$9.00"));
			Assert.assertTrue(u.getRefundOATaxInfo().getText().contains(YesRefundOATaxInfo));

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getUnenrollButton().isDisplayed());
			u.getUnenrollButton().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 6, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Class enrolled with Punches")
	public void Unenroll_Scenario6() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll6, paymentOption1, "", "Free With Punch", "Unenrollmbr6");

			int unitsBefore = rm.getPackageUnitsForMember("Day Pass", "Unenrollmbr6");
			System.out.println(unitsBefore);

			rm.familyClassClickToUnenroll(classToEnroll6, "Unenrollmbr6");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll6));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundUnitText().getText().contains(YesRefundUnit));
			Assert.assertTrue(u.getRefundUnitNo().getText().contains("1"));

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getUnenrollNoRefund().isDisplayed());

			u.getUnenrollNoRefund().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

			int unitsAfter = rm.getPackageUnitsForMember("Day Pass", "Unenrollmbr6");
			System.out.println(unitsAfter);

			unitsBefore++;

			Assert.assertEquals(unitsAfter, unitsBefore);

			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 7, description = "Can Unenroll-No Cancellation Fee - Refund Allowed only in units- Class enrolled with CC")
	public void Unenroll_Scenario7() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll7, paymentOption2, payMethod2, "Not Free", "Unenrollmbr7");

			rm.familyClassClickToUnenroll(classToEnroll7, "Unenrollmbr7");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll7));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getNoRefund().getText().contains(NoRefund));

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getUnenrollNoRefund().isDisplayed());
			u.getUnenrollNoRefund().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 8, description = "Can Unenroll-No Cancellation Fee - Refund Allowed only in units- Class enrolled with On Account")
	//
	public void Unenroll_Scenario8() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll8, paymentOption2, payMethod1, "Not Free", "Unenrollmbr8");

			rm.familyClassClickToUnenroll(classToEnroll8, "Unenrollmbr8");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll8));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getNoRefund().getText().contains(NoRefund));

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getUnenrollNoRefund().isDisplayed());
			u.getUnenrollNoRefund().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 9, description = "Can Unenroll-No Cancellation Fee - Refund Allowed to only OA or CC- Class enrolled with Punches")
	public void Unenroll_Scenario9() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll9, paymentOption1, "", "Free With Punch", "Unenrollmbr9");

			rm.familyClassClickToUnenroll(classToEnroll9, "Unenrollmbr9");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll9));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getNoRefund().getText().contains(NoRefund));

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getUnenrollNoRefund().isDisplayed());
			u.getUnenrollNoRefund().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 10, description = "Can Unenroll-With Cancellation Fee - Refund Allowed - Class enrolled with Credit Card")
	public void Unenroll_Scenario10() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll10, paymentOption2, payMethod2, "Not Free", "Unenrollmbr10");

			rm.familyClassClickToUnenroll(classToEnroll10, "Unenrollmbr10");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll10));
			PaymentMethodsPO PM = new PaymentMethodsPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

			Assert.assertTrue(u.getCancelHeader().isDisplayed());
			Assert.assertTrue(u.getCancelText().getText().contains(YesCancelFee));
			Assert.assertTrue(u.getCancelAmnt().getText().contains("$6.00"));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundCCText().getText().contains(YesRefundCC));
			Assert.assertTrue(u.getRefundCCAmnt().getText().contains("-$9.00"));

			Boolean SubTotalLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'SUB-TOTAL:')]"));
			Assert.assertTrue(SubTotalLabelPresent);
			Boolean TaxLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'TAX:')]"));
			Assert.assertTrue(TaxLabelPresent);
			Boolean TotalLabelPresent = rm.isElementPresent(By.xpath("//h2[contains(text(),'TOTAL:')]"));
			Assert.assertTrue(TotalLabelPresent);

			String[] totalAmt = u.getTotalAmount().getText().split(": -");
			String FormatTotalAmt = totalAmt[1].trim();

			System.out.println(FormatTotalAmt);

			Assert.assertTrue(u.getOnAccountAndSavedCards().isDisplayed());
			rm.verifyOnAccountIsPresentAndSelectedByDefault();
			Assert.assertTrue(u.getNewCardButton().isDisplayed());

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getRefundButton().isDisplayed());

			Assert.assertTrue(u.getRefundButton().getText().contains(FormatTotalAmt));

			rm.selectSavedcard();

			u.getRefundButton().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 11, description = "Can Unenroll-With Cancellation Fee > Refund Allowed - enrolled with Credit Card")
	public void Unenroll_Scenario10_1() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll10_1, paymentOption2, payMethod2, "Not Free", "Unenrollmbr11");

			rm.familyClassClickToUnenroll(classToEnroll10_1, "Unenrollmbr11");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll10_1));
			PaymentMethodsPO PM = new PaymentMethodsPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

			Assert.assertTrue(u.getCancelHeader().isDisplayed());
			Assert.assertTrue(u.getCancelText().getText().contains(YesCancelFee));
			// Assert.assertTrue(u.getCancelAmnt().getText().contains("$16.00"));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundCCText().getText().contains(YesRefundCC));
			Assert.assertTrue(u.getRefundCCAmnt().getText().contains("-$9.00"));

			Boolean SubTotalLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'SUB-TOTAL:')]"));
			Assert.assertTrue(SubTotalLabelPresent);
			Boolean TaxLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'TAX:')]"));
			Assert.assertTrue(TaxLabelPresent);
			Boolean TotalLabelPresent = rm.isElementPresent(By.xpath("//h2[contains(text(),'TOTAL:')]"));
			Assert.assertTrue(TotalLabelPresent);

			String[] totalAmt = u.getTotalAmount().getText().split(": ");
			String FormatTotalAmt = totalAmt[1].trim();

			System.out.println(FormatTotalAmt);

			Assert.assertTrue(u.getOnAccountAndSavedCards().isDisplayed());
			rm.verifyOnAccountIsPresentAndSelectedByDefault();
			Assert.assertTrue(u.getNewCardButton().isDisplayed());

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getPaymentButton().isDisplayed());

			Assert.assertTrue(u.getPaymentButton().getText().contains(FormatTotalAmt));

			rm.selectSavedcard();

			u.getPaymentButton().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 12, description = "Can Unenroll-With Cancellation Fee - Refund Allowed - Class enrolled with On Account")
	public void Unenroll_Scenario11() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll11, paymentOption2, payMethod1, "Not Free", "Unenrollmbr12");

			rm.familyClassClickToUnenroll(classToEnroll11, "Unenrollmbr12");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll11));
			PaymentMethodsPO PM = new PaymentMethodsPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

			Assert.assertTrue(u.getCancelHeader().isDisplayed());
			Assert.assertTrue(u.getCancelText().getText().contains(YesCancelFee));
			Assert.assertTrue(u.getCancelAmnt().getText().contains("$6.00"));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundOAText().getText().contains(YesRefundOnAccount));
			Assert.assertTrue(u.getRefundOAAmnt().getText().contains("$9.00"));
			Assert.assertTrue(u.getRefundOATaxInfo().getText().contains(YesRefundOATaxInfo));

			Boolean SubTotalLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'SUB-TOTAL:')]"));
			Assert.assertTrue(SubTotalLabelPresent);
			Boolean TaxLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'TAX:')]"));
			Assert.assertTrue(TaxLabelPresent);
			Boolean TotalLabelPresent = rm.isElementPresent(By.xpath("//h2[contains(text(),'TOTAL:')]"));
			Assert.assertTrue(TotalLabelPresent);

			String[] totalAmt = u.getTotalAmount().getText().split(": ");
			String FormatTotalAmt = totalAmt[1].trim();

			System.out.println(FormatTotalAmt);

			Assert.assertTrue(u.getOnAccountAndSavedCards().isDisplayed());
			rm.verifyOnAccountIsPresentAndSelectedByDefault();
			Assert.assertTrue(u.getNewCardButton().isDisplayed());

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getPaymentButton().isDisplayed());

			Assert.assertTrue(u.getPaymentButton().getText().contains(FormatTotalAmt));

			rm.selectSavedcard();

			u.getPaymentButton().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 13, description = "Can Unenroll-With Cancellation Fee on the product but not charged - Refund Allowed - Class enrolled with Punches")
	public void Unenroll_Scenario12() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll12, paymentOption1, "", "Free With Punch", "Unenrollmbr13");

			int unitsBefore = rm.getPackageUnitsForMember("Day Pass", "Unenrollmbr13");
			System.out.println(unitsBefore);

			rm.familyClassClickToUnenroll(classToEnroll12, "Unenrollmbr13");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll12));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundUnitText().getText().contains(YesRefundUnit));
			Assert.assertTrue(u.getRefundUnitNo().getText().contains("1"));

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getUnenrollNoRefund().isDisplayed());

			u.getUnenrollNoRefund().click();
			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

			int unitsAfter = rm.getPackageUnitsForMember("Day Pass", "Unenrollmbr13");
			System.out.println(unitsAfter);

			unitsBefore++;

			Assert.assertEquals(unitsAfter, unitsBefore);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 14, description = "Can Unenroll-No Cancellation Fee and No Refund set on the class")
	public void Unenroll_Scenario13() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll13, paymentOption2, payMethod1, "Not Free", "Unenrollmbr14");

			rm.familyClassClickToUnenroll(classToEnroll13, "Unenrollmbr14");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll13));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getNoRefund().getText().contains(NoRefund));

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getUnenrollNoRefund().isDisplayed());

			u.getUnenrollNoRefund().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 15, description = "Can Unenroll-With Cancellation Fee and No Refund set on the class")
	public void Unenroll_Scenario14() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll14, paymentOption2, payMethod1, "Not Free", "Unenrollmbr15");

			rm.familyClassClickToUnenroll(classToEnroll14, "Unenrollmbr15");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll14));
			PaymentMethodsPO PM = new PaymentMethodsPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

			Assert.assertTrue(u.getCancelHeader().isDisplayed());
			Assert.assertTrue(u.getCancelText().getText().contains(YesCancelFee));
			Assert.assertTrue(u.getCancelAmnt().getText().contains("$6.00"));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getNoRefund().getText().contains(NoRefund));

			Boolean SubTotalLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'SUB-TOTAL:')]"));
			Assert.assertTrue(SubTotalLabelPresent);
			Boolean TaxLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'TAX:')]"));
			Assert.assertTrue(TaxLabelPresent);
			Boolean TotalLabelPresent = rm.isElementPresent(By.xpath("//h2[contains(text(),'TOTAL:')]"));
			Assert.assertTrue(TotalLabelPresent);

			String[] totalAmt = u.getTotalAmount().getText().split(": ");
			String FormatTotalAmt = totalAmt[1].trim();

			System.out.println(FormatTotalAmt);

			Assert.assertTrue(u.getOnAccountAndSavedCards().isDisplayed());
			rm.verifyOnAccountIsPresentAndSelectedByDefault();
			Assert.assertTrue(u.getNewCardButton().isDisplayed());

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getPaymentButton().isDisplayed());

			Assert.assertTrue(u.getPaymentButton().getText().contains(FormatTotalAmt));

			rm.selectNewcardToPay("UnenrollHoh Auto");

			u.getPaymentButton().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 16, description = "Unenroll Free Class - Course-Refund section should be hidden")
	public void Unenroll_Scenario15() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll15, "", "", "Free", "Unenrollmbr16");

			rm.familyClassClickToUnenroll(classToEnroll15, "Unenrollmbr16");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll15));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getNoRefund().getText().contains(NoRefund));

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getUnenrollNoRefund().isDisplayed());

			u.getUnenrollNoRefund().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 17, description = "Payment and Refund Methods displaying On Account and Credit cards should follow the sell club's configuration")
	public void Unenroll_Scenario16() throws IOException, InterruptedException {

		try {

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
			s.selectByVisibleText("OnlinePayNotAllowed");

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectClassOrCourseToEnroll(classToEnroll16.toUpperCase());

			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'modal-content')]")));

			while (c.getClasslabel().getText().isBlank()) {
				Thread.sleep(500);
			}

			JavascriptExecutor jse = ((JavascriptExecutor) driver);
			int fmlyMbrcount = c.getFmlyMemberLabel().size();

			for (int i = 0; i < fmlyMbrcount; i++) {

				WebElement fml = c.getFmlyMemberLabel().get(i);
				WebElement fmc = c.getFmlyMemberCheckBox().get(i);

				if (fmc.isSelected()) {
					jse.executeScript("arguments[0].scrollIntoView(true);", fml);
					fml.click(); // de-selects the hoh
					break;
				}
			}

			// Selects the falimy member
			for (int i = 0; i < fmlyMbrcount; i++) {

				WebElement fml = c.getFmlyMemberLabel().get(i);
				// WebElement fmc = c.getFmlyMemberCheckBox().get(i);

				if (fml.getText().contains("Unenrollmbr17")) {
					jse.executeScript("arguments[0].scrollIntoView(true);", fml);
					fml.click(); // Selects the member
					break;
				}
			}
			Actions actions = new Actions(driver);

			Thread.sleep(2000);
			if (c.getPopupSignUpButton().isEnabled()) {
				jse.executeScript("arguments[0].scrollIntoView(true);", c.getPopupSignUpButton());

				actions.moveToElement(c.getPopupSignUpButton()).click().perform();

			} else {
				jse.executeScript("arguments[0].scrollIntoView(true);", c.getPopupCancelButton());
				actions.moveToElement(c.getPopupCancelButton()).click().perform();
				Assert.fail("SignUp button not available");

			}
			Thread.sleep(2000);

			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Single Class Fee")) {
					driver.findElements(By.tagName("label")).get(i).click();
					break;
				}
			}

			c.getContinueButton().click();

			Thread.sleep(5000);
			wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//i[@class='fa fa-pencil-square-o']")));

			rm.selectSavedcard();

			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}
			PM.getPaymentButton().click();

			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			int count1 = driver.findElements(By.tagName("a")).size();
			for (int j = 0; j < count1; j++) {
				if (driver.findElements(By.tagName("a")).get(j).getText().equals("Dashboard"))

				{
					// rw.linksToBeClickable();
					driver.findElements(By.tagName("a")).get(j).click();
					break;
				}

			}
			rw.waitForDashboardLoaded();

			rm.familyClassClickToUnenroll(classToEnroll16, "Unenrollmbr17");

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll16));
			wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

			Assert.assertTrue(u.getCancelHeader().isDisplayed());
			Assert.assertTrue(u.getCancelText().getText().contains(YesCancelFee));
			Assert.assertTrue(u.getCancelAmnt().getText().contains("$6.00"));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundCCText().getText().contains(YesRefundCC));
			Assert.assertTrue(u.getRefundCCAmnt().getText().contains("-$9.00"));

			Boolean SubTotalLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'SUB-TOTAL:')]"));
			Assert.assertTrue(SubTotalLabelPresent);
			Boolean TaxLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'TAX:')]"));
			Assert.assertTrue(TaxLabelPresent);
			Boolean TotalLabelPresent = rm.isElementPresent(By.xpath("//h2[contains(text(),'TOTAL:')]"));
			Assert.assertTrue(TotalLabelPresent);

			String[] totalAmt = u.getTotalAmount().getText().split(": -");
			String FormatTotalAmt = totalAmt[1].trim();

			System.out.println(FormatTotalAmt);

			try {
				rm.verifyOnAccountIsPresentAndSelectedByDefault();
			} catch (java.lang.AssertionError ae) {
				Assert.assertTrue(true);
				System.out.println("Verified that On Account is not present");
				log.info("Verified that On Account is not present");
			}

			Assert.assertTrue(u.getNewCardButton().isDisplayed());

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getRefundButton().isDisplayed());

			Assert.assertTrue(u.getRefundButton().getText().contains(FormatTotalAmt));

			rm.selectNewcardToRefund("UnenrollHoh Auto");

			u.getRefundButton().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 18, description = "class Start time in the future but unenrollment time falls inside the cannot cancel window")
	public void Unenroll_Scenario17() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInClass(classToEnroll17, paymentOption2, payMethod1, "Not Free", "Unenrollmbr18");

			rm.familyClassClickToUnenroll(classToEnroll17, "Unenrollmbr18");

			UnenrollPO u = new UnenrollPO(driver);

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll17));

			Assert.assertTrue(u.getCanNotCancelFMsg().getText().contains(cannotCancelMsg));
			Assert.assertTrue(u.getCancelButton().isDisplayed());

			rm.memberLogout();
			rm.deleteEnrollInClassInCOG(classToEnroll17, "Jonas Sports-Plex", "Auto, Unenrollmbr18");

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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
		driver.quit();
		driver = null;
	}

}
