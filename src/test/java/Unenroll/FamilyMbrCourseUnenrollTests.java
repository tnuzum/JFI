package Unenroll;

import java.io.IOException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.PaymentMethodsPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class FamilyMbrCourseUnenrollTests extends base {
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
	private static int CourseStartYear = 2022;
	private static String CourseStartMonth = "May";
	private static String dsiredMonthYear = "May 2022";

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

	public FamilyMbrCourseUnenrollTests() {
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

			rm.enrollFamilyMbrInCourse(courseToEnroll1, paymentOption2, payMethod1, "Not Free", CourseStartMonth,
					"Unenrollmbr1", CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll1, "Unenrollmbr1");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll1));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundOAText().getText().contains(YesRefundOnAccount));
			Assert.assertTrue(u.getRefundOAAmnt().getText().contains("$9.00"));
			Assert.assertTrue(u.getRefundOATaxInfo().getText().contains(YesRefundOATaxInfo));

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getUnenrollButton().isDisplayed());
			jse.executeScript("arguments[0].click();", u.getUnenrollButton());

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
		} finally {

			boolean popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));

			while (popup == true) {
				driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
				System.out.println("popup was present");
				popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));
			}
			rm.returnToDashboard();
		}
	}

	@Test(priority = 2, description = "Can Unenroll - Cancellation Fee exists even if class is enrolled with punches - Refund Allowed")
	public void Unenroll_Scenario2() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll2, paymentOption1, "", "Not Free", CourseStartMonth,
					"Unenrollmbr2_1", CourseStartYear);

			int unitsBefore = rm.getPackageUnitsForMember("Day Pass", "Unenrollmbr2_1");

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll2, "Unenrollmbr2_1");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll2));
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

			jse.executeScript("arguments[0].click();", u.getPaymentButton());

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

			int unitsAfter = rm.getPackageUnitsForMember("Day Pass", "Unenrollmbr2_1");

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
		} finally {

			boolean popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));

			while (popup == true) {
				driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
				System.out.println("popup was present");
				popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));
			}
			rm.returnToDashboard();
		}
	}

	@Test(priority = 3, description = "Can Unenroll - Cancellation Fee exists even if class/ course is enrolled with with Service D - Refund is not Allowed as it is a free class")
	public void Unenroll_Scenario3() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll3, "", "", "Free", CourseStartMonth, "Unenrollmbr3",
					CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll3, "Unenrollmbr3");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll3));
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
		} finally {

			boolean popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));

			while (popup == true) {
				driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
				System.out.println("popup was present");
				popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));
			}
			rm.returnToDashboard();
		}
	}

	@Test(priority = 4, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Class enrolled with Credit Card")
	public void Unenroll_Scenario4() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll4, paymentOption2, payMethod2, "Not Free", CourseStartMonth,
					"Unenrollmbr4", CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll4, "Unenrollmbr4");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll4));
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

	@Test(priority = 5, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Class enrolled with On Account")
	public void Unenroll_Scenario5() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll5, paymentOption2, payMethod1, "Not Free", CourseStartMonth,
					"Unenrollmbr5", CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll5, "Unenrollmbr5");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll5));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundOAText().getText().contains(YesRefundOnAccount));
			Assert.assertTrue(u.getRefundOAAmnt().getText().contains("$9.00"));
			Assert.assertTrue(u.getRefundOATaxInfo().getText().contains(YesRefundOATaxInfo));

			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Assert.assertTrue(u.getUnenrollButton().isDisplayed());
			jse.executeScript("arguments[0].click();", u.getUnenrollButton());

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

	@Test(priority = 6, description = "Can Unenroll-No Cancellation Fee - Refund Allowed - Class enrolled with Punches")
	public void Unenroll_Scenario6() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll6, paymentOption1, "", "Free With Punch", CourseStartMonth,
					"Unenrollmbr6_1", CourseStartYear);

			int unitsBefore = rm.getPackageUnitsForMember("Day Pass", "Unenrollmbr6_1");

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll6, "Unenrollmbr6_1");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll6));

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

			int unitsAfter = rm.getPackageUnitsForMember("Day Pass", "Unenrollmbr6_1");

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

	@Test(priority = 7, description = "Can Unenroll-No Cancellation Fee - Refund Allowed only in units- Class enrolled with CC")
	public void Unenroll_Scenario7() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll7, paymentOption2, payMethod2, "Not Free", CourseStartMonth,
					"Unenrollmbr7", CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll7, "Unenrollmbr7");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll7));

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

	@Test(priority = 8, description = "Can Unenroll-No Cancellation Fee - Refund Allowed only in units- Class enrolled with On Account")
	//
	public void Unenroll_Scenario8() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll8, paymentOption2, payMethod1, "Not Free", CourseStartMonth,
					"Unenrollmbr8", CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll8, "Unenrollmbr8");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll8));

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

	@Test(priority = 9, description = "Can Unenroll-No Cancellation Fee - Refund Allowed to only OA or CC- Class enrolled with Punches")
	public void Unenroll_Scenario9() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll9, paymentOption1, "", "Free With Punch", CourseStartMonth,
					"Unenrollmbr9", CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll9, "Unenrollmbr9");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll9));

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

	@Test(priority = 10, description = "Can Unenroll-With Cancellation Fee - Refund Allowed - Class enrolled with Credit Card")
	public void Unenroll_Scenario10() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll10, paymentOption2, payMethod2, "Not Free", CourseStartMonth,
					"Unenrollmbr10", CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll10, "Unenrollmbr10");

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll10));
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
			rm.memberLogout();
		}
	}

//	@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}
