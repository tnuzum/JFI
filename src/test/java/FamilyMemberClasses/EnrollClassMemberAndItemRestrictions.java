package FamilyMemberClasses;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

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
import org.testng.asserts.SoftAssert;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollClassMemberAndItemRestrictions extends base {
	private static String classStartMonth = "DEC";
	private static String classStartDate = "22";
	public static SoftAssert softAssertion = new SoftAssert();
	private static DashboardPO d;
	private static BreadcrumbTrailPO BT;
	private static ClassSignUpPO c;
	private static String testName = null;

	public reusableWaits rw;
	public reusableMethods rm;

	public EnrollClassMemberAndItemRestrictions() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {

		try {
			driver = initializeDriver();
		} catch (java.lang.NullPointerException npe) {

			driver = initializeDriver();

			System.out.println("driver initialized again");
			log.error("driver initialized again");
			npe.printStackTrace();
			log.error(npe.getMessage(), npe);

		}

		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();

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

		try {
			rm.activeMemberLogin("freezemember", "Testing1!");
			rw.waitForDashboardLoaded1();

			d.getMyClassesScheduleButton().click();
			softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
			softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectTomorrowDate();

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectClassOrCourseToEnroll("FAMILYENROLLCLASS");

			Thread.sleep(1000);

			System.out.println(c.getPopUpErrorMessage().getText().trim());
			softAssertion.assertEquals("Membership restrictions have limited enrollment into this class.",
					c.getPopUpErrorMessage().getText().trim());
			softAssertion.assertFalse(c.getPopupSignUpButton().isEnabled());

			Thread.sleep(1000);
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(0," + c.getPopupCancelButton().getLocation().x + ")");
			c.getPopupCancelButton().click();

			Thread.sleep(1000);
			softAssertion.assertAll();

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
			rm.memberLogout();
		}
	}

	@Test(priority = 2, description = "Validating that Terminated member cannot enroll When the club settings won't allow")
	public void TerminatedMemberCannotEnroll() throws IOException, InterruptedException {

		rm.activeMemberLogin("terminate", "Testing1!");
		rw.waitForDashboardLoaded1();

		d.getMyClassesScheduleButton().click();
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectTomorrowDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectClassOrCourseToEnroll("FAMILYENROLLCLASS");

		Thread.sleep(1000);
		try {
			System.out.println(c.getPopUpErrorMessage().getText().trim());
			softAssertion.assertEquals("Membership restrictions have limited enrollment into this class.",
					c.getPopUpErrorMessage().getText().trim());
			softAssertion.assertFalse(c.getPopupSignUpButton().isEnabled());

			softAssertion.assertAll();

			Thread.sleep(1000);
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(0," + c.getPopupCancelButton().getLocation().x + ")");
			c.getPopupCancelButton().click();
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

			rm.memberLogout();
		}

	}

	@Test(priority = 3, description = "Validating that the class cannnot be enrolled due to the enrollment window has closed")
	public void ClassEnrollmentWindowClosed() throws IOException, InterruptedException {

		rm.activeMemberLogin("feemember", "Testing1!");
		rw.waitForDashboardLoaded();

		d.getMyClassesScheduleButton().click();
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectTomorrowDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectClassOrCourseToEnroll("CLASSENROLLMENTCLOSED");

		Thread.sleep(1000);
		try {
			System.out.println(c.getPopUpErrorMessage().getText().trim());
			softAssertion.assertEquals("The online enrollment window for this class has closed.",
					c.getPopUpErrorMessage().getText().trim());
			softAssertion.assertFalse(c.getPopupSignUpButton().isEnabled());

			softAssertion.assertAll();

			Thread.sleep(1000);
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(0," + c.getPopupCancelButton().getLocation().x + ")");
			c.getPopupCancelButton().click();

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

			rm.memberLogout();
		}

	}

	@Test(priority = 4, description = "Validating that the class cannnot be enrolled due to Item Restrictions")
	public void ClassCannotBEnrolled() throws IOException, InterruptedException {

		rm.activeMemberLogin("feemember", "Testing1!");
		rw.waitForDashboardLoaded();

		d.getMyClassesScheduleButton().click();
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectTomorrowDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectClassOrCourseToEnroll("CLASSCANNOTBENROLLED");

		Thread.sleep(1000);
		try {
			System.out.println(c.getPopUpErrorMessage().getText().trim());
			softAssertion.assertEquals("Online enrollment for this class is not allowed.",
					c.getPopUpErrorMessage().getText().trim());
			softAssertion.assertFalse(c.getPopupSignUpButton().isEnabled());

			softAssertion.assertAll();

			Thread.sleep(1000);
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(0," + c.getPopupCancelButton().getLocation().x + ")");
			c.getPopupCancelButton().click();
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

			rm.memberLogout();
		}

	}

	@Test(priority = 5, description = "Validating that the class cannnot be enrolled due to  the enrollment window is not open yet")
	public void ClassEnrollmentNotOpened() throws IOException, InterruptedException {

		rm.activeMemberLogin("feemember", "Testing1!");
		rw.waitForDashboardLoaded();

		d.getMyClassesScheduleButton().click();
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		c.getCalendarIcon().click();
		Thread.sleep(2000);

		String monthName = driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]"))
				.getText();

		while (!monthName.contains(classStartMonth)) {
			driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-next-button')]")).click();
			;
			monthName = driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]"))
					.getText();
		}

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		int daycount = driver.findElements(By.tagName("td")).size(); // Get the daycount from the calendar
		for (int i = 0; i < daycount; i++) {
			String date = driver.findElements(By.tagName("td")).get(i).getText();
			if (date.contains(classStartDate)) {
				driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
				break;
			}
		}

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectClassOrCourseToEnroll("CLASSENROLLMENTNOTOPENED");

		Thread.sleep(1000);
		try {
			System.out.println(c.getPopUpErrorMessage().getText().trim());
			softAssertion.assertEquals("Online enrollment for this class is currently closed.",
					c.getPopUpErrorMessage().getText().trim());
			softAssertion.assertFalse(c.getPopupSignUpButton().isEnabled());

			softAssertion.assertAll();

			Thread.sleep(1000);
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(0," + c.getPopupCancelButton().getLocation().x + ")");
			c.getPopupCancelButton().click();

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

			rm.memberLogout();
		}

	}

	@Test(priority = 6, description = "Validating that the class cannnot be enrolled as the class time is passed for the day")
	public void ClassEnrollmentEnded() throws IOException, InterruptedException {

		rm.activeMemberLogin("feemember", "Testing1!");
		rw.waitForDashboardLoaded();

		d.getMyClassesScheduleButton().click();
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectYesterdayDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectClassOrCourseToEnroll("CLASSENROLLMENTENDED");

		// wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
		try {
			System.out.println(c.getPopUpErrorMessage().getText().trim());
			softAssertion.assertEquals("The online enrollment window for this class has closed.",
					c.getPopUpErrorMessage().getText().trim());
			softAssertion.assertFalse(c.getPopupSignUpButton().isEnabled());

			softAssertion.assertAll();

			Thread.sleep(1000);
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(0," + c.getPopupCancelButton().getLocation().x + ")");
			c.getPopupCancelButton().click();
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

			rm.memberLogout();
		}

	}

	@Test(priority = 7, description = "Validating that the class cannnot be enrolled due to Membership Type Time Restrictions")
	public void ClassOutsidePermittedHours() throws IOException, InterruptedException {

		rm.activeMemberLogin("outpermtdhrs", "Testing1!");
		rw.waitForDashboardLoaded();

		d.getMyClassesScheduleButton().click();
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectTomorrowDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectClassOrCourseToEnroll("CLASSWITHINELIGIBLETIME");

		Thread.sleep(1000);
		try {
			System.out.println(c.getPopUpErrorMessage().getText().trim());
			softAssertion.assertEquals("Membership restrictions have limited enrollment into this class.",
					c.getPopUpErrorMessage().getText().trim());
			softAssertion.assertFalse(c.getPopupSignUpButton().isEnabled());

			softAssertion.assertAll();

			Thread.sleep(1000);
			((JavascriptExecutor) driver)
					.executeScript("window.scrollTo(0," + c.getPopupCancelButton().getLocation().x + ")");
			c.getPopupCancelButton().click();

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

			rm.memberLogout();
		}

	}

	@Test(priority = 8, description = "Validating that the class cannnot be enrolled due to Membership Type Restrictions at the club")
	public void ClubAccessDenied() throws IOException, InterruptedException {

		rm.activeMemberLogin("hoh", "Testing1!");
		rw.waitForDashboardLoaded();

		d.getMyClassesScheduleButton().click();
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectTomorrowDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectClassOrCourseToEnroll("FAMILYENROLLCLASS");

		try {
			int memberCount = c.getDetailsPopup().findElements(By.tagName("label")).size();
			for (int i = 0; i < memberCount; i++) {
				if (c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText().contains("Cadmember")) {
					softAssertion.assertTrue(c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText()
							.contains("Membership restrictions have limited class enrollment at this club."));
				}
			}

			softAssertion.assertAll();

			Thread.sleep(1000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", c.getPopupCancelButton());

			Actions actions = new Actions(driver);
			actions.moveToElement(c.getPopupCancelButton()).click().perform();
			// c.getPopupCancelButton().click();

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

			rm.memberLogout();
		}

	}

	@Test(priority = 9, description = "Validating that the class cannnot be enrolled due to Scheduling Conflict")
	public void ClassSchedulingConflict() throws IOException, InterruptedException {
		rm.activeMemberLogin("hoh", "Testing1!");
		rw.waitForDashboardLoaded();

		d.getMyClassesScheduleButton().click();
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectTomorrowDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectClassOrCourseToEnroll("SCHEDULINGCONFLICTCLASS");

		Thread.sleep(2000);
		// ((JavascriptExecutor) driver)
		// .executeScript("window.scrollTo(0," +
		// c.getPopupSignUpButton().getLocation().x + ")");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", c.getPopupSignUpButton());
		Actions actions = new Actions(driver);
		actions.moveToElement(c.getPopupSignUpButton()).click().perform();
//		c.getPopupSignUpButton().click();
		Thread.sleep(2000);
		c.getContinueButton().click();
		wait.until(ExpectedConditions.elementToBeClickable(c.getPopupClose()));
		softAssertion.assertEquals("Success", c.getPopupMessage().getText());
		c.getPopupClose().click();
		Thread.sleep(1000);

		// Navigate to Classes
		int count = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < count; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Classes"))

			{
				// rw.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectTomorrowDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectClassOrCourseToEnroll("BARRE COMBAT FUSION");

		int memberCount = c.getDetailsPopup().findElements(By.tagName("label")).size();
		for (int i = 0; i < memberCount; i++) {
			if (c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText().contains("hoh")) {
				softAssertion.assertTrue(c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText()
						.contains(" Scheduling Conflict"));
			}
		}
		Thread.sleep(1000);
		// ((JavascriptExecutor) driver)
		// .executeScript("window.scrollTo(0," +
		// c.getPopupCancelButton().getLocation().x + ")");

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", c.getPopupCancelButton());

		actions.moveToElement(c.getPopupCancelButton()).click().perform();
//		c.getPopupCancelButton().click();
		Thread.sleep(1000);

		rm.returnToDashboard();
		rm.unenrollFromClass();
		rm.memberLogout();

	}

//	@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
