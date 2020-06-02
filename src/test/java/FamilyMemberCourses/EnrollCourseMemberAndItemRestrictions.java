package FamilyMemberCourses;

import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
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
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollCourseMemberAndItemRestrictions extends base {
	private static String CourseStartYear = "2019";
	private static String CourseStartMonth1 = "Jan";
	private static String CourseStartMonth2 = "Dec";
	private static String dsiredMonthYear = "December 2020";
	private static DashboardPO d;
	private static BreadcrumbTrailPO BT;
	private static ClassSignUpPO c;
	private static String testName = null;

	public reusableWaits rw;
	public reusableMethods rm;

	public EnrollCourseMemberAndItemRestrictions() {
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
		driver.get(prop.getProperty("EMELoginPage"));

		d = new DashboardPO(driver);
		BT = new BreadcrumbTrailPO(driver);
		c = new ClassSignUpPO(driver);

	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Validating that Frozen member cannot enroll When the club settings won't allow")
	public void FrozenMemberCannotEnroll() throws IOException, InterruptedException {

		rm.activeMemberLogin("freezemember", "Testing1!");
		rw.waitForDashboardLoaded1();

		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectCourseStartMonth(CourseStartMonth2);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectClassOrCourseToEnroll("FAMILYENROLLCOURSE");

		Thread.sleep(1000);
		try {
			System.out.println(c.getPopUpErrorMessage().getText().trim());
			Assert.assertEquals("Membership restrictions have limited enrollment into this course.",
					c.getPopUpErrorMessage().getText().trim());
			Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
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
			Thread.sleep(1000);
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(0," + c.getPopupCancelButtonCourse().getLocation().x + ")");
			c.getPopupCancelButtonCourse().click();
			Thread.sleep(1000);

			rm.memberLogout();
		}

	}

	@Test(priority = 2, description = "Validating that Terminated member cannot enroll When the club settings won't allow")
	public void TerminatedMemberCannotEnroll() throws IOException, InterruptedException {

		rm.activeMemberLogin("terminate", "Testing1!");
		rw.waitForDashboardLoaded1();

		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectCourseStartMonth(CourseStartMonth2);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectClassOrCourseToEnroll("FAMILYENROLLCOURSE");

		Thread.sleep(1000);
		try {
			System.out.println(c.getPopUpErrorMessage().getText().trim());
			Assert.assertEquals("Membership restrictions have limited enrollment into this course.",
					c.getPopUpErrorMessage().getText().trim());
			Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
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
			Thread.sleep(1000);
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(0," + c.getPopupCancelButtonCourse().getLocation().x + ")");
			c.getPopupCancelButtonCourse().click();

			Thread.sleep(1000);
			rm.memberLogout();
		}

	}

	@Test(priority = 3, description = "Validating that the course cannnot be enrolled in when the enrollment window has closed")
	public void CourseEnrollmentWindowClosed() throws IOException, InterruptedException {

		rm.activeMemberLogin("feemember", "Testing1!");
		rw.waitForDashboardLoaded();

		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectClassOrCourseToEnroll("COURSEENROLLMENTCLOSED");

		Thread.sleep(1000);
		try {
			System.out.println(c.getPopUpErrorMessage().getText().trim());
			Assert.assertEquals("The online enrollment window for this course has closed.",
					c.getPopUpErrorMessage().getText().trim());
			Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
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
			Thread.sleep(1000);
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(0," + c.getPopupCancelButtonCourse().getLocation().x + ")");

			c.getPopupCancelButtonCourse().click();

			Thread.sleep(1000);
			rm.memberLogout();
		}

	}

	@Test(priority = 4, description = "Validating that the course cannnot be enrolled due to Item Restrictions")
	public void CourseCannotBEnrolled() throws IOException, InterruptedException {

		rm.activeMemberLogin("feemember", "Testing1!");
		rw.waitForDashboardLoaded();

		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectClassOrCourseToEnroll("COURSECANNOTBENROLLED");

		Thread.sleep(1000);
		try {
			System.out.println(c.getPopUpErrorMessage().getText().trim());
			Assert.assertEquals("Online enrollment for this course is not allowed.",
					c.getPopUpErrorMessage().getText().trim());
			Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
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
			Thread.sleep(1000);
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(0," + c.getPopupCancelButtonCourse().getLocation().x + ")");
			c.getPopupCancelButtonCourse().click();
			Thread.sleep(1000);
			rm.memberLogout();
		}

	}

	@Test(priority = 5, description = "Validating that the course cannnot be enrolled due to the enrollment window is not open yet")
	public void CourseEnrollmentNotOpened() throws IOException, InterruptedException {

		rm.activeMemberLogin("feemember", "Testing1!");
		rw.waitForDashboardLoaded();

		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectCourseStartMonth(CourseStartMonth2);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectClassOrCourseToEnroll("COURSEENROLLMENTNOTOPENED");

		Thread.sleep(1000);
		try {
			System.out.println(c.getPopUpErrorMessage().getText().trim());
			Assert.assertEquals("Online enrollment for this course is currently closed.",
					c.getPopUpErrorMessage().getText().trim());
			Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
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
			Thread.sleep(1000);
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(0," + c.getPopupCancelButtonCourse().getLocation().x + ")");
			c.getPopupCancelButtonCourse().click();

			Thread.sleep(1000);
			rm.memberLogout();
		}

	}

	@Test(priority = 6, description = "Validating that the course cannnot be enrolled as the course time is passed")
	public void CourseEnrollmentEnded() throws IOException, InterruptedException {

		rm.activeMemberLogin("feemember", "Testing1!");
		rw.waitForDashboardLoaded();

		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
		String year = c.getYear().getText();
		while (!year.contains(CourseStartYear)) {
			driver.findElement(By.xpath("//i[contains(@class, 'double-left')]")).click();
			year = driver.findElement(By.xpath("//span[contains(@class, 'btn-white')]")).getText();
		}

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectCourseStartMonth(CourseStartMonth1);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectClassOrCourseToEnroll("COURSEENROLLMENTENDED");

		Thread.sleep(1000);
		try {
			System.out.println(c.getPopUpErrorMessage().getText().trim());
			Assert.assertEquals("The online enrollment window for this course has closed.",
					c.getPopUpErrorMessage().getText().trim());
			Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
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
			Thread.sleep(1000);
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(0," + c.getPopupCancelButtonCourse().getLocation().x + ")");
			c.getPopupCancelButtonCourse().click();

			Thread.sleep(1000);
			rm.memberLogout();
		}

	}

	@Test(priority = 7, description = "Validating that the course cannnot be enrolled due to Membership Type Time Restrictions")
	public void CourseOutsidePermittedHours() throws IOException, InterruptedException {

		rm.activeMemberLogin("outpermtdhrs", "Testing1!");
		rw.waitForDashboardLoaded();

		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectClassOrCourseToEnroll("COURSEWITHINELIGIBLETIME");

		Thread.sleep(1000);
		try {
			System.out.println(c.getPopUpErrorMessage().getText().trim());
			Assert.assertEquals("Membership restrictions have limited enrollment into this course.",
					c.getPopUpErrorMessage().getText().trim());
			Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
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
			Thread.sleep(1000);
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(0," + c.getPopupCancelButtonCourse().getLocation().x + ")");
			c.getPopupCancelButtonCourse().click();

			Thread.sleep(1000);
			rm.memberLogout();
		}

	}

	@Test(priority = 8, description = "Validating that the course cannnot be enrolled due to Membership Type Restrictions at the club")
	public void ClubAccessDenied() throws IOException, InterruptedException {

		rm.activeMemberLogin("hoh", "Testing1!");
		rw.waitForDashboardLoaded();

		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectCourseStartMonth(CourseStartMonth2);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectClassOrCourseToEnroll("FAMILYENROLLCOURSE");

		Thread.sleep(1000);
		try {
			int memberCount = c.getDetailsPopup().findElements(By.tagName("label")).size();
			for (int i = 0; i < memberCount; i++) {
				if (c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText().contains("Cadmember")) {
					Assert.assertTrue(c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText()
							.contains("Membership restrictions have limited course enrollment at this club."));
				}
			}

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
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
			Thread.sleep(1000);
			// ((JavascriptExecutor) driver)
			// .executeScript("window.scrollTo(0," +
			// c.getPopupCancelButtonCourse().getLocation().x + ")");
			Actions actions = new Actions(driver);
			actions.moveToElement(c.getPopupCancelButtonCourse()).click().perform();
//			c.getPopupCancelButtonCourse().click();

			Thread.sleep(1000);
			rm.memberLogout();
		}

	}

	@Test(priority = 9, description = "Validating that the course cannnot be enrolled due to Scheduling Conflict")
	public void CourseSchedulingConflict() throws IOException, InterruptedException {
		rm.activeMemberLogin("hoh", "Testing1!");
		rw.waitForDashboardLoaded();

		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectCourseStartMonth(CourseStartMonth2);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectClassOrCourseToEnroll("SCHEDULINGCONFLICTCOURSE");

		Thread.sleep(2000);
		// ((JavascriptExecutor) driver)
		// .executeScript("window.scrollTo(0," +
		// c.getPopupSignupButtonCourse().getLocation().x + ")");
		Actions actions = new Actions(driver);
		actions.moveToElement(c.getPopupSignupButtonCourse()).click().perform();
		c.getPopupSignupButtonCourse().click();
		Thread.sleep(2000);
		c.getContinueButton().click();
		wait.until(ExpectedConditions.elementToBeClickable(c.getPopupClose()));
		Assert.assertEquals("Success", c.getPopupMessage().getText());
		c.getPopupClose().click();
		Thread.sleep(1000);

		// Navigate to Select Courses / Events
		int count1 = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < count1; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Courses / Events"))

			{
				// rw.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectCourseStartMonth(CourseStartMonth2);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectClassOrCourseToEnroll("CONFLICTCOURSE");

		int memberCount = c.getDetailsPopup().findElements(By.tagName("label")).size();
		for (int i = 0; i < memberCount; i++) {
			if (c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText().contains("hoh")) {
				Assert.assertTrue(c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText()
						.contains("Scheduling Conflict"));
			}
		}
		Thread.sleep(1000);
		// ((JavascriptExecutor) driver)
		// .executeScript("window.scrollTo(0," +
		// c.getPopupCancelButtonCourse().getLocation().x + ")");

		actions.moveToElement(c.getPopupCancelButtonCourse()).click().perform();
//		c.getPopupCancelButtonCourse().click();
		Thread.sleep(500);
		rm.returnToDashboard();
		rm.unenrollFromCourse(dsiredMonthYear);
		rm.memberLogout();
	}

	// @AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
