package EME_EnvURL;

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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.CalendarPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.ThankYouPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollInFreeCourseTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	private static String CourseStartMonth = "Dec";
	private static String dsiredMonthYear = "December 2020";
	private static DashboardPO d;
	private static BreadcrumbTrailPO BT;
	private static ClassSignUpPO c;
	private static ThankYouPO TY;
	private static String testName = null;

	public reusableWaits rw;
	public reusableMethods rm;
	private static JavascriptExecutor jse;

	public EnrollInFreeCourseTest() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	@Parameters({ "EMELoginPage" })
	public void initialize(String EMELoginPage) throws InterruptedException, IOException {
//		public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		// String EMELoginPage = prop.getProperty("EMELoginPage");
		driver.get(EMELoginPage);

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

	@Test(priority = 1, description = "Enroll in free course")

	public void EnrollInZeroDollarCourse() throws IOException, InterruptedException {
		try {
			rm.activeMemberLogin("emailmember", "Testing1!");
			// rm.unenrollFromCourse(dsiredMonthYear);
			// Thread.sleep(1000);
			// rm.returnToDashboard();
			rw.waitForDashboardLoaded();

			jse.executeScript("arguments[0].click();", d.getMyCoursesEventsScheduleButton());
			Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			/*
			 * c.getCourseFilter().click(); c.getCourseKeyword().click();
			 * c.getSearchField().sendKeys("FREE COURSE AUTO"); c.getApplyFilters().click();
			 */
			rm.SelectCourseStartMonth(CourseStartMonth);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectClassOrCourseToEnroll("FREE COURSE AUTO");

			Thread.sleep(2000);

			if (c.getPopupSignupButtonCourse().isEnabled()) {
				c.getPopupSignupButtonCourse().click();

			} else {
				c.getPopupCancelButtonCourse().click();
				Assert.fail("SignUp button not available");

			}
			Thread.sleep(2000);
			Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
			Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
			Assert.assertEquals("Free Course Auto", c.getClassName().getText());
			Assert.assertEquals("Start Time: 4:30 PM", c.getClassStartTime().getText());
			Assert.assertEquals("Course Instructor: Jillian S", c.getCourseInstructor().getText());

			Assert.assertEquals(c.getHowYouWishToPay().getText(), "Free");
			Assert.assertTrue(c.getHowYouWishToPay().isEnabled());

			c.getContinueButton().click();
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
			int count = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

				{
					// rw.linksToBeClickable();
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
					break;
				}

			}
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
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			Assert.fail(eci.getMessage());
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

	@Test(priority = 2, description = "Unenroll from the course")

	public void unenrollFromCourse() throws IOException, InterruptedException {
		try {

			CalendarPO cp = new CalendarPO(driver);

			Thread.sleep(2000);
			rw.waitForDashboardLoaded();
			d.getMenuMyActivies().click();

			while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1")) {
				Thread.sleep(500);
			}

			WebDriverWait wait1 = new WebDriverWait(driver, 30);
			wait1.until(ExpectedConditions.elementToBeClickable(d.getMenuMyCalendar()));

			d.getMenuMyCalendar().click();
			String monthYear = cp.getMonthYear().getText();
			while (!monthYear.equals(dsiredMonthYear)) {
				cp.getRightArrow().click();
				monthYear = cp.getMonthYear().getText();
			}
			Thread.sleep(1000);
			cp.getCalDayBadge().click();
			Thread.sleep(1000);
			cp.getCalEventTitle().click();
			Thread.sleep(1000);
			cp.getUnEnrollBtn().click();
			Thread.sleep(1000);
			UnenrollPO u = new UnenrollPO(driver);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));
			u.getUnenrollButton().click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			u.getUnenrollConfirmYesButton().click();
			wait.until(ExpectedConditions.stalenessOf(u.getUnenrollConfirmYesButton()));
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);
		}

		catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			Assert.fail(eci.getMessage());
		} finally {
			rm.returnToDashboard();
			rm.memberLogout();
		}

	}

	@Test(priority = 3, description = "Enroll In course Free Due to Existing Punches") // Bug 155892 has been created

	public void EnrollInCourseFreeWithExistingPunches() throws IOException, InterruptedException {
		try {
			rm.activeMember6Login();
//	rm.unenrollFromCourse(dsiredMonthYear);
//	Thread.sleep(1000);
//	rm.returnToDashboard();
			rw.waitForDashboardLoaded();

			// Noting down the Package Units before enrolling in Course
			int IntPackageCountBefore = 0;
			int IntPackageCountAfter = 0;

			IntPackageCountBefore = rm.getPackageUnits("ServiceOA");

			jse.executeScript("arguments[0].click();", d.getMyCoursesEventsScheduleButton());
			Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartMonth(CourseStartMonth);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectClassOrCourseToEnroll("COURSEFREEWITHEXISTINGPUNCHES");

			Thread.sleep(2000);

			if (c.getPopupSignupButtonCourse().isEnabled()) {
				c.getPopupSignupButtonCourse().click();

			} else {
				c.getPopupCancelButtonCourse().click();
				Assert.fail("SignUp button not available");

			}
			Thread.sleep(2000);
			Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
			Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
			Assert.assertEquals("CourseFreeWithExistingPunches", c.getClassName().getText());
			Assert.assertEquals("Start Time: 8:00 PM", c.getClassStartTime().getText());
			Assert.assertEquals("Course Instructor: Andrea", c.getCourseInstructor().getText());

			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().equals("Use Existing Package")) {
					Assert.assertTrue(driver.findElements(By.tagName("label")).get(i).isEnabled());
					driver.findElements(By.tagName("label")).get(i).click();
					break;
				}
			}

			c.getContinueButton().click();
			wait.until(ExpectedConditions.elementToBeClickable(c.getPopupClose()));
			Assert.assertEquals("Success", c.getPopupMessage().getText());
			c.getPopupClose().click();
			Thread.sleep(2000);

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
			int count = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Appointments"))

				{
					// rw.linksToBeClickable();
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
					break;
				}

			}

			// Verifies the link navigates to the right page
			Assert.assertEquals("Appointments", driver.getTitle());
			Thread.sleep(1000);

			// Note the package units after enrolling
			IntPackageCountAfter = rm.getPackageUnits("ServiceOA");
//			System.out.println(IntUnitCountAfter);

			// Verifies the package units is now decremented by one unit
			IntPackageCountBefore--;
			Assert.assertEquals(IntPackageCountBefore, IntPackageCountAfter);
		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			Assert.fail(eci.getMessage());
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
			rm.unenrollFromCourse(dsiredMonthYear);
			rm.memberLogout();
		}

	}

	@Test(priority = 4, description = "Enroll In Course Free Due to Service D")

	public void EnrollInCourseFreeWithServiceD() throws IOException, InterruptedException {
		try {

			rm.activeMember3Login();
//	rm.unenrollFromCourse(dsiredMonthYear);
//	Thread.sleep(1000);
//	rm.returnToDashboard();
			rw.waitForDashboardLoaded();

			jse.executeScript("arguments[0].click();", d.getMyCoursesEventsScheduleButton());
			Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartMonth(CourseStartMonth);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectClassOrCourseToEnroll("COURSEFREEWITHSERVICED");

			Thread.sleep(2000);

			if (c.getPopupSignupButtonCourse().isEnabled()) {
				c.getPopupSignupButtonCourse().click();

			} else {
				c.getPopupCancelButtonCourse().click();
				Assert.fail("SignUp button not available");

			}
			Thread.sleep(2000);
			Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
			Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
			Assert.assertEquals("CourseFreeWithServiceD", c.getClassName().getText());
			Assert.assertEquals("Start Time: 6:30 PM", c.getClassStartTime().getText());
			Assert.assertEquals("Course Instructor: Andrea", c.getCourseInstructor().getText());

			Assert.assertEquals(c.getHowYouWishToPay().getText(), "Free");
			Assert.assertTrue(c.getHowYouWishToPay().isEnabled());

			c.getContinueButton().click();
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
			int count = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Appointments"))

				{
					// rw.linksToBeClickable();
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
					break;
				}

			}

			// Verifies the link navigates to the right page
			Assert.assertEquals("Appointments", driver.getTitle());
			Thread.sleep(1000);
		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			Assert.fail(ae.getMessage());

		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}

		finally {
			/*
			 * boolean receiptpopuppresent =
			 * rm.isElementPresent(By.xpath("//div[@class='modal-content']")); if
			 * (receiptpopuppresent == true) {System.out.println("closing the receipt");
			 * TY.getReceiptPopup().findElement(By.
			 * xpath("//button[contains(text(), 'Close')]")).click();}
			 */

			rm.returnToDashboard();
			rm.unenrollFromCourse(dsiredMonthYear);
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
