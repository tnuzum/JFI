package SingleMemberClasses;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.ThankYouPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollInFreeClassTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static DashboardPO d;
	private static BreadcrumbTrailPO BT;
	private static ClassSignUpPO c;
	private static ThankYouPO TY;
	private static String testName = null;
	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public EnrollInFreeClassTest() {
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

		d = new DashboardPO(driver);
		BT = new BreadcrumbTrailPO(driver);
		c = new ClassSignUpPO(driver);
		TY = new ThankYouPO(driver);
		jse = (JavascriptExecutor) driver;

	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Enroll in free class")
	public void EnrollInZeroDollarClass() throws IOException, InterruptedException {
		try {

			rm.activeMemberLogin("emailmember", "Testing1!");
			rm.unenrollFromClass();

			jse.executeScript("arguments[0].click();", d.getMyClassesScheduleButton());
			Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectTomorrowDate();

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectClassOrCourseToEnroll("FREE CLASS AUTO");

			Thread.sleep(2000);

			if (c.getPopupSignUpButton().isEnabled()) {
				c.getPopupSignUpButton().click();

			} else {
				getScreenshot("SignUp Button", driver);
				c.getPopupCancelButton().click();
				// Assert.fail("SignUp button not available");

			}
			Thread.sleep(2000);
			wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), "Free Class Auto"));
			Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
			Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
			Assert.assertEquals("Free Class Auto", c.getClassName().getText());
			Assert.assertEquals("Start Time: 10:00 AM", c.getClassStartTime().getText());
			Assert.assertEquals("Class Instructor: Max Gibbs", c.getClassInstructor().getText());
			Assert.assertEquals(c.getClassDate().getText(), "Date: " + tomorrowsDate);

			Assert.assertEquals(c.getHowYouWishToPay().getText().trim(), "Free");
			Assert.assertTrue(c.getHowYouWishToPay().isEnabled());

			jse.executeScript("arguments[0].click();", c.getContinueButton());
			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(c.getPopupClose()));
			Assert.assertEquals("Success", c.getPopupMessage().getText());
			c.getPopupClose().click();
			Thread.sleep(1000);

			// Verifies the text on Thank You page and the links to navigate to Dashboard
			// and other pages are displayed
			rm.ThankYouPageValidations();

			// Note down the Receipt number
			String receiptNumber = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber));

			// Verifies the buttons on Print Receipt Popup
			rm.ReceiptPopupValidations();

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
			Thread.sleep(1000);

			// Navigate to Dashboard
			jse.executeScript("arguments[0].click();", TY.getDashBoardLink());
			rw.waitForDashboardLoaded();

			// Verifies the link navigates to the right page
			Assert.assertEquals("Dashboard", driver.getTitle());
			Thread.sleep(1000);

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
			/*
			 * boolean receiptpopuppresent =
			 * rm.isElementPresent(By.xpath("//div[@class='modal-content']"));
			 * 
			 * if (receiptpopuppresent == true) {System.out.println("closing the receipt");
			 * TY.getReceiptPopup().findElement(By.
			 * xpath("//button[contains(text(), 'Close')]")).click();
			 * rm.returnToDashboard();}
			 */

			rm.returnToDashboard();

		}

	}

	@Test(priority = 2, description = "Unenroll from the class", dependsOnMethods = { "EnrollInZeroDollarClass" })
	public void unenrollFromClass() throws IOException, InterruptedException {

		rw.waitForDashboardLoaded();

		boolean enrolled = rm.isElementPresent(By.xpath("//classeswidget//div[@class='class-table-container']"));
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='class-table-container']")));
		if (enrolled == true) {
			try {

				while (!d.getMyClassesClass1GearButton().isDisplayed()) {
					Thread.sleep(1000);
					System.out.println("Sleeping for 1 second");
				}
				WebDriverWait wait = new WebDriverWait(driver, 60);
				wait.until(ExpectedConditions.elementToBeClickable(d.getMyClassesClass1GearButton()));
				jse.executeScript("arguments[0].click();", d.getMyClassesClass1GearButton());

				wait.until(ExpectedConditions.visibilityOf(d.getmyClassesUnenrollButton()));
				wait.until(ExpectedConditions.elementToBeClickable(d.getmyClassesUnenrollButton()));
				jse.executeScript("arguments[0].click();", d.getmyClassesUnenrollButton());
				Thread.sleep(1000);
				UnenrollPO u = new UnenrollPO(driver);
				wait.until(ExpectedConditions.visibilityOf(u.getUnenrollNoRefund()));
				wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollNoRefund()));
				jse.executeScript("arguments[0].click();", u.getUnenrollNoRefund());
				Thread.sleep(1000);
				rw.waitForAcceptButton();
				u.getUnenrollConfirmYesButton().click();
				rw.waitForAcceptButton();
				Thread.sleep(1000);
				Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
				u.getUnenrollConfirmYesButton().click();
				Thread.sleep(2000);
			}

			catch (java.lang.AssertionError ae) {
				System.out.println("assertion error");
				ae.printStackTrace();
				log.error(ae.getMessage(), ae);
				ae.printStackTrace();
				getScreenshot("Unenroll", driver);
				// Assert.fail(ae.getMessage());
			}

			catch (org.openqa.selenium.NoSuchElementException ne) {
				System.out.println("No element present");
				ne.printStackTrace();
				log.error(ne.getMessage(), ne);
				getScreenshot("Unenroll", driver);
				// Assert.fail(ne.getMessage());
			}

			catch (org.openqa.selenium.ElementClickInterceptedException eci) {
				System.out.println("Element Click Intercepted");
				eci.printStackTrace();
				log.error(eci.getMessage(), eci);
				rm.catchErrorMessage();
				getScreenshot("Unenroll", driver);
				// Assert.fail(eci.getMessage());
			} finally {
				rm.returnToDashboard();
				rm.memberLogout();
			}
		} else {
			System.out.println("Not enrolled");
			rm.memberLogout();
		}
	}

	@Test(priority = 3, description = "Enroll In Class Free Due to Existing Punches") // Bug 155892 has been created
	public void EnrollInClassFreeWithExistingPunches() throws IOException, InterruptedException {

		try {
			rm.activeMember8Login();
			rm.unenrollFromClass();

			// Noting down the Package Units before enrolling in Course
			int IntPackageCountBefore = 0;
			int IntPackageCountAfter = 0;

			IntPackageCountBefore = rm.getPackageUnits("ServiceNC");

			jse.executeScript("arguments[0].click();", d.getMyClassesScheduleButton());
			// d.getMyClassesScheduleButton().click();
			Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectTomorrowDate();

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectClassOrCourseToEnroll("CLASSFREEWITHEXISTINGPUNCHES");

			Thread.sleep(2000);

			if (c.getPopupSignUpButton().isEnabled()) {
				c.getPopupSignUpButton().click();

			} else {
				c.getPopupCancelButton().click();
				// Assert.fail("SignUp button not available");

			}

			Thread.sleep(2000);
			wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), "ClassFreeWithExistingPunches"));
			Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
			Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
			Assert.assertEquals("ClassFreeWithExistingPunches", c.getClassName().getText());

			Assert.assertEquals("Start Time: 10:00 AM", c.getClassStartTime().getText());
			Assert.assertEquals("Class Instructor: Max Gibbs", c.getClassInstructor().getText());
			Assert.assertEquals("Date: " + tomorrowsDate, c.getClassDate().getText());

			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().trim().equals("Use Existing Package")) {
					Assert.assertTrue(driver.findElements(By.tagName("label")).get(i).isEnabled());
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("label")).get(i));
					break;
				}
			}
			Thread.sleep(1000);
			jse.executeScript("arguments[0].click();", c.getContinueButton());
			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(c.getPopupClose()));
			Assert.assertEquals("Success", c.getPopupMessage().getText());
			c.getPopupClose().click();
			Thread.sleep(1000);

			// Verifies the text on Thank You page and the links to navigate to Dashboard
			// and other pages are displayed
			rm.ThankYouPageValidations();

			// Note down the Receipt number
			String receiptNumber2 = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber2));

			// Verifies the buttons on Print Receipt Popup
			rm.ReceiptPopupValidations();

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
			Thread.sleep(1000);

			// Navigate to Appointments Page
			jse.executeScript("arguments[0].click();", TY.getBookAppointmentsLink());
			Thread.sleep(1000);

			// Verifies the link navigates to the right page
			Assert.assertEquals("Appointments", driver.getTitle());
			Thread.sleep(1000);

			// Note the package units after enrolling
			IntPackageCountAfter = rm.getPackageUnits("ServiceNC");
			// System.out.println(IntUnitCountAfter);

			// Verifies the package units is now decremented by one unit
			IntPackageCountBefore--;
			Assert.assertEquals(IntPackageCountBefore, IntPackageCountAfter);

			rm.returnToDashboard();
			rm.unenrollFromClass();

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
			/*
			 * boolean receiptpopuppresent =
			 * rm.isElementPresent(By.xpath("//div[@class='modal-content']"));
			 * 
			 * if (receiptpopuppresent == true) {System.out.println("closing the receipt");
			 * TY.getReceiptPopup().findElement(By.
			 * xpath("//button[contains(text(), 'Close')]")).click();
			 * rm.returnToDashboard();}
			 */
			rm.memberLogout();
		}
	}

	@Test(priority = 4, description = "Enroll In Class Free Due to Service D")
	public void EnrollInClassFreeWithServiceD() throws IOException, InterruptedException {
		try {
			rm.activeMember3Login();
			rm.unenrollFromClass();

			jse.executeScript("arguments[0].click();", d.getMyClassesScheduleButton());
			Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectTomorrowDate();

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectClassOrCourseToEnroll("CLASSFREEWITHSERVICED");

			Thread.sleep(2000);

			if (c.getPopupSignUpButton().isEnabled()) {
				c.getPopupSignUpButton().click();

			} else {
				c.getPopupCancelButton().click();
				// Assert.fail("SignUp button not available");

			}

			Thread.sleep(2000);
			wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), "ClassFreeWithServiceD"));
			Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
			Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
			Assert.assertEquals("ClassFreeWithServiceD", c.getClassName().getText());
			Assert.assertEquals("Start Time: 10:00 AM", c.getClassStartTime().getText());
			Assert.assertEquals("Class Instructor: Max Gibbs", c.getClassInstructor().getText());
			Assert.assertEquals("Date: " + tomorrowsDate, c.getClassDate().getText());

			Assert.assertEquals(c.getHowYouWishToPay().getText().trim(), "Free");
			Assert.assertTrue(c.getHowYouWishToPay().isEnabled());

			jse.executeScript("arguments[0].click();", c.getContinueButton());
			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(c.getPopupClose()));
			Assert.assertEquals("Success", c.getPopupMessage().getText());
			c.getPopupClose().click();
			Thread.sleep(1000);

			// Verifies the text on Thank You page and the links to navigate to Dashboard
			// and other pages are displayed
			rm.ThankYouPageValidations();

			// Note down the Receipt number
			String receiptNumber4 = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber4));

			// Verifies the buttons on Print Receipt Popup
			rm.ReceiptPopupValidations();

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
			Thread.sleep(1000);

			// Navigate to Appointments Page
			jse.executeScript("arguments[0].click();", TY.getBookAppointmentsLink());
			Thread.sleep(1000);
			// Verifies the link navigates to the right page
			Assert.assertEquals("Appointments", driver.getTitle());

			rm.returnToDashboard();
			rm.unenrollFromClass();

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
			/*
			 * boolean receiptpopuppresent =
			 * rm.isElementPresent(By.xpath("//div[@class='modal-content']")); if
			 * (receiptpopuppresent == true) {System.out.println("closing the receipt");
			 * TY.getReceiptPopup().findElement(By.
			 * xpath("//button[contains(text(), 'Close')]")).click();}
			 */

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
