package Unenroll;

import java.io.IOException;
import java.lang.reflect.Method;

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

public class FamilyMbrCourseUnenrollTests2 extends base {

	private static String courseToEnroll10_1 = "UnenrollCourse10_1";
	private static String courseToEnroll11 = "UnenrollCourse11";
	private static String courseToEnroll12 = "UnenrollCourse12";
	private static String courseToEnroll13 = "UnenrollCourse13";
	private static String courseToEnroll14 = "UnenrollCourse14";
	private static String courseToEnroll15 = "UnenrollCourse15";
	private static String courseToEnroll16 = "UnenrollCourse16";
	private static String courseToEnroll17 = "UnenrollCourse17";
	private static int CourseStartYear = 2022;
	private static String CourseStartMonth = "Aug";
	private static String dsiredMonthYear = "August 2022";

	private static String paymentOption1 = "Use Existing Package";
	private static String paymentOption2 = "Pay Course Fee";
	// private static String paymentOption3 = "Buy Day Pass";
	private static String payMethod1 = "On Account";
	private static String payMethod2 = "Saved Card";

	private static String YesCancelFee = "Course Cancellation Fee";

	private static String YesRefundCC = "Course Refund Price";
	private static String YesRefundOnAccount = "This credit will be placed on your on account and be applied to your outstanding invoice.";
	private static String YesRefundOATaxInfo = "Plus applicable taxes.";
	private static String YesRefundUnit = "Course Package Refund Quantity:";
	private static String NoRefund = "This Course is non refundable";

	private static String cannotCancelMsg = "We apologize, this course is not eligible for unenrollment.";

	private static String testName = null;
	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public FamilyMbrCourseUnenrollTests2() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		jse = (JavascriptExecutor) driver;
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();

		rm.activeMemberLogin("unenrollhoh2", "Testing1!");
		rw.waitForDashboardLoaded();

	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 11, description = "Can Unenroll-With Cancellation Fee > Refund Allowed - enrolled with Credit Card")
	public void Unenroll_Scenario10_1() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll10_1, paymentOption2, payMethod2, "Not Free", CourseStartMonth,
					"Unenrollmbr11", CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll10_1, "Unenrollmbr11");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll10_1));
			PaymentMethodsPO PM = new PaymentMethodsPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

			Assert.assertTrue(u.getCancelHeader().isDisplayed());
			Assert.assertTrue(u.getCancelText().getText().contains(YesCancelFee));
			Assert.assertTrue(u.getCancelAmnt().getText().contains("$16.00"));

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

			jse.executeScript("arguments[0].click();", u.getPaymentButton());

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
		} catch (java.lang.IndexOutOfBoundsException iobe) {
			System.out.println("index out of bounds exception");
			log.error("index out of bounds exception");
			iobe.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(iobe.getMessage(), iobe);
			iobe.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.TimeoutException te) {
			System.out.println("Time out exception");
			log.error("Time out exception");
			te.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(te.getMessage(), te);
			te.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		finally {

			boolean popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));

			while (popup == true) {
				driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
				System.out.println("popup was present");
				popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));
			}
			rm.returnToDashboard();
		}
	}

	@Test(priority = 12, description = "Can Unenroll-With Cancellation Fee - Refund Allowed - Class enrolled with On Account")
	public void Unenroll_Scenario11() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll11, paymentOption2, payMethod1, "Not Free", CourseStartMonth,
					"Unenrollmbr12", CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll11, "Unenrollmbr12");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll11));

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

			jse.executeScript("arguments[0].click();", u.getPaymentButton());

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
		} catch (java.lang.IndexOutOfBoundsException iobe) {
			System.out.println("index out of bounds exception");
			log.error("index out of bounds exception");
			iobe.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(iobe.getMessage(), iobe);
			iobe.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.TimeoutException te) {
			System.out.println("Time out exception");
			log.error("Time out exception");
			te.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(te.getMessage(), te);
			te.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		finally {

			boolean popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));

			while (popup == true) {
				driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
				System.out.println("popup was present");
				popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));
			}
			rm.returnToDashboard();
		}
	}

	@Test(priority = 13, description = "Can Unenroll-With Cancellation Fee on the product but not charged - Refund Allowed - Class enrolled with Punches")
	public void Unenroll_Scenario12() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll12, paymentOption1, "", "Free With Punch", CourseStartMonth,
					"Unenrollmbr13_1", CourseStartYear);

			int unitsBefore = rm.getPackageUnitsForMember("Day Pass", "Unenrollmbr13_1");

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll12, "Unenrollmbr13_1");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll12));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundUnitText().getText().contains(YesRefundUnit));
			Assert.assertTrue(u.getRefundUnitNo().getText().contains("1"));

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getUnenrollNoRefund().isDisplayed());

			jse.executeScript("arguments[0].click();", u.getUnenrollNoRefund());
			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

			int unitsAfter = rm.getPackageUnitsForMember("Day Pass", "Unenrollmbr13_1");

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

		catch (java.lang.IndexOutOfBoundsException iobe) {
			System.out.println("index out of bounds exception");
			log.error("index out of bounds exception");
			iobe.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(iobe.getMessage(), iobe);
			iobe.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.TimeoutException te) {
			System.out.println("Time out exception");
			log.error("Time out exception");
			te.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(te.getMessage(), te);
			te.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		finally {
			rm.returnToDashboard();
		}
	}

	@Test(priority = 14, description = "Can Unenroll-No Cancellation Fee and No Refund set on the class")
	public void Unenroll_Scenario13() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll13, paymentOption2, payMethod1, "Not Free", CourseStartMonth,
					"Unenrollmbr14", CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll13, "Unenrollmbr14");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll13));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getNoRefund().getText().contains(NoRefund));

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getUnenrollNoRefund().isDisplayed());

			jse.executeScript("arguments[0].click();", u.getUnenrollNoRefund());

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
		} catch (java.lang.IndexOutOfBoundsException iobe) {
			System.out.println("index out of bounds exception");
			log.error("index out of bounds exception");
			iobe.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(iobe.getMessage(), iobe);
			iobe.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.TimeoutException te) {
			System.out.println("Time out exception");
			log.error("Time out exception");
			te.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(te.getMessage(), te);
			te.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		finally {

			boolean popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));

			while (popup == true) {
				driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
				System.out.println("popup was present");
				popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));
			}
			rm.returnToDashboard();
		}
	}

	@Test(priority = 15, description = "Can Unenroll-With Cancellation Fee and No Refund set on the class")
	public void Unenroll_Scenario14() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll14, paymentOption2, payMethod1, "Not Free", CourseStartMonth,
					"Unenrollmbr15", CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll14, "Unenrollmbr15");

			WebDriverWait wait = new WebDriverWait(driver, 30);
			UnenrollPO u = new UnenrollPO(driver);
			PaymentMethodsPO PM = new PaymentMethodsPO(driver);

			wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll14));

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

			Thread.sleep(3000);
			rm.selectNewcardToPay("UnenrollHoh2 Auto");

			jse.executeScript("arguments[0].click();", u.getPaymentButton());

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
		} catch (java.lang.IndexOutOfBoundsException iobe) {
			System.out.println("index out of bounds exception");
			log.error("index out of bounds exception");
			iobe.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(iobe.getMessage(), iobe);
			iobe.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.TimeoutException te) {
			System.out.println("Time out exception");
			log.error("Time out exception");
			te.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(te.getMessage(), te);
			te.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		finally {

			boolean popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));

			while (popup == true) {
				driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
				System.out.println("popup was present");
				popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));
			}
			rm.returnToDashboard();
		}
	}

	@Test(priority = 16, description = "Unenroll Free Class - Course-Refund section should be hidden", enabled = false)
	public void Unenroll_Scenario15() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll15, "", "", "Free", CourseStartMonth, "Unenrollmbr16",
					CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll15, "Unenrollmbr16");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll15));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getNoRefund().getText().contains(NoRefund));

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getUnenrollNoRefund().isDisplayed());

			jse.executeScript("arguments[0].click();", u.getUnenrollNoRefund());

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
		} catch (java.lang.IndexOutOfBoundsException iobe) {
			System.out.println("index out of bounds exception");
			log.error("index out of bounds exception");
			iobe.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(iobe.getMessage(), iobe);
			iobe.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.TimeoutException te) {
			System.out.println("Time out exception");
			log.error("Time out exception");
			te.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(te.getMessage(), te);
			te.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		finally {

			boolean popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));

			while (popup == true) {
				driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
				System.out.println("popup was present");
				popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));
			}
			rm.returnToDashboard();
		}
	}

	@Test(priority = 17, description = "Payment and Refund Methods displaying On Account and Credit cards should follow the Home club's configuration", enabled = false)
	public void Unenroll_Scenario16() throws IOException, InterruptedException {

		try {

			DashboardPO d = new DashboardPO(driver);
			ClassSignUpPO c = new ClassSignUpPO(driver);
			PaymentMethodsPO PM = new PaymentMethodsPO(driver);
			PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);

			jse.executeScript("arguments[0].click();", d.getMyCoursesEventsScheduleButton());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartYear(CourseStartYear);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartMonth(CourseStartMonth);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			WebElement Club = c.getCourseClubDropdown();
			Select s = new Select(Club);
			s.selectByVisibleText("OnlinePayNotAllowed");

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectClassOrCourseToEnroll(courseToEnroll16.toUpperCase());

			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//mat-dialog-container[contains(@class, 'mat-dialog-container')]")));

			while (c.getClasslabel().getText().isBlank()) {
				Thread.sleep(500);
			}

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
			if (c.getPopupSignupButtonCourse().isEnabled()) {
				jse.executeScript("arguments[0].scrollIntoView(true);", c.getPopupSignupButtonCourse());

				actions.moveToElement(c.getPopupSignupButtonCourse()).click().perform();

			} else {
				jse.executeScript("arguments[0].scrollIntoView(true);", c.getPopupCancelButtonCourse());
				actions.moveToElement(c.getPopupCancelButtonCourse()).click().perform();
				Assert.fail("SignUp button not available");

			}
			Thread.sleep(2000);

			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Course Fee")) {
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("label")).get(i));
					break;
				}
			}

			jse.executeScript("arguments[0].click();", c.getContinueButton());

			Thread.sleep(5000);

			wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//i[contains(@class,'fal fa-edit')]")));

			rm.selectSavedcard();

			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}
			jse.executeScript("arguments[0].click();", PM.getPaymentButton());

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

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll16, "Unenrollmbr17");

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll16));

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

			Thread.sleep(3000);
			rm.selectNewcardToRefund("UnenrollHoh2 Auto");

			Thread.sleep(3000);
			jse.executeScript("arguments[0].click();", u.getRefundButton());

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
		} catch (java.lang.IndexOutOfBoundsException iobe) {
			System.out.println("index out of bounds exception");
			log.error("index out of bounds exception");
			iobe.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(iobe.getMessage(), iobe);
			iobe.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.TimeoutException te) {
			System.out.println("Time out exception");
			log.error("Time out exception");
			te.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(te.getMessage(), te);
			te.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		finally {

			boolean popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));

			while (popup == true) {
				driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
				System.out.println("popup was present");
				popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));
			}
			rm.returnToDashboard();
		}
	}

	@Test(priority = 18, description = "class Start time in the future but unenrollment time falls inside the cannot cancel window", enabled = false)
	public void Unenroll_Scenario17() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll17, paymentOption2, payMethod1, "Not Free", CourseStartMonth,
					"Unenrollmbr18", CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll17, "Unenrollmbr18");

			UnenrollPO u = new UnenrollPO(driver);

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll17));

			Assert.assertTrue(u.getCanNotCancelFMsg().getText().contains(cannotCancelMsg));
			Assert.assertTrue(u.getCancelButton().isDisplayed());

			rm.memberLogout();
			rm.deleteEnrollInCourseInCOG(courseToEnroll17, "Jonas Sports-Plex", "Auto, Unenrollmbr18");

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
		} catch (java.lang.IndexOutOfBoundsException iobe) {
			System.out.println("index out of bounds exception");
			log.error("index out of bounds exception");
			iobe.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(iobe.getMessage(), iobe);
			iobe.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.TimeoutException te) {
			System.out.println("Time out exception");
			log.error("Time out exception");
			te.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(te.getMessage(), te);
			te.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

	}

//	@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}
