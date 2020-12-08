package Unenroll;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.CalendarPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class UnenrollStandbyCourse extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String CourseStartMonth = "Feb";
	private static int CourseStartYear = 2021;
	private static String dsiredMonthYear = "February 2021";
	private static String CourseToEnroll = "UNENRLLSTNDBYCOURSE";
	private static String CourseNameDisplayed = "UnenrllStndByCourse";
	private static String YesCancelFee = "Course Cancellation Fee";
	private static String YesRefundOnAccount = "This credit will be placed on your on account and be applied to your outstanding invoice.";
	private static String YesRefundOATaxInfo = "Plus applicable taxes.";
	private static String standbyUnenrollText = "Select Unenroll to be removed from the standby list for this Course.";

	private static String member2 = "Feemember";
	private static String member5 = "Hoh";

	private static String testName = null;

	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public UnenrollStandbyCourse() {
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
		getEMEURL();
	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Family Member Enrollment")
	public void FamilyMemberEnrollment() throws IOException, InterruptedException {
		try {
			rm.activeMemberLogin("hoh", "Testing1!");

			rw.waitForDashboardLoaded();
			DashboardPO d = new DashboardPO(driver);

			jse.executeScript("arguments[0].click();", d.getMyCoursesEventsScheduleButton());

			ClassSignUpPO c = new ClassSignUpPO(driver);
			WebDriverWait wait = new WebDriverWait(driver, 50);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartYear(CourseStartYear);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartMonth(CourseStartMonth);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			c.getCourseFilter().click();
			c.getCourseKeyword().click();
			c.getSearchField().sendKeys("unen");
			Thread.sleep(2000);
			c.getCourseApplyFilters().click();
			Thread.sleep(2000);

			int CourseCount = c.getClassTable().size();
			for (int j = 0; j < CourseCount; j++) {

				WebElement w = c.getClassTable().get(j);

				String CourseName = w.getText();

				if (CourseName.contains(CourseToEnroll)) {
					jse.executeScript("arguments[0].click();", w);// Click on the specific Class
					break;
				}
			}

			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'modal-content')]")));
			while (c.getClasslabel().getText().isBlank()) {
				Thread.sleep(500);
			}

			Assert.assertEquals(c.getClasslabel().getText(), CourseNameDisplayed); // Verifies the Class name
			int count = c.getFmlyMemberLabel().size();

			// Selects members
			for (int i = 0; i < count; i++) {

				WebElement fml = c.getFmlyMemberLabel().get(i);
				WebElement fmc = c.getFmlyMemberCheckBox().get(i);

				if (fml.getText().contains(member2))
					fml.click(); // Selects the member

				if (fml.getText().contains(member5))
					Assert.assertTrue(fmc.isSelected());

			}
			Thread.sleep(1000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", c.getPopupSignUpButton());
			Actions actions = new Actions(driver);
			actions.moveToElement(c.getPopupSignUpButton()).click().perform();

			while (c.getClassName().getText().isBlank()) {
				Thread.sleep(500);
			}

			Assert.assertTrue(c.getstandbyMessage().getText().contains("1 spot(s) left"));
			Assert.assertTrue(c.getstandbyMessage().getText().contains("Who should get it?"));

			for (int i = 0; i < c.getstandbySection().findElements(By.tagName("label")).size(); i++) {

				List<WebElement> Labels = c.getstandbySection().findElements(By.tagName("label"));
				List<WebElement> Chkbxs = c.getstandbySection().findElements(By.tagName("input"));

				if (Labels.get(i).getText().contains(member5))

					Chkbxs.get(i).click();

			}

			c.getRestOnStandby().click();

			for (int i = 0; i < c.getMemberSections().size(); i++) {

				List<WebElement> Labels = c.getMemberSections().get(i).findElements(By.tagName("label"));

				if (c.getMemberSections().get(i).getText().contains(member5)) {

					for (int j = 0; j < Labels.size(); j++) {
						if (Labels.get(j).getText().contains("Pay Course Fee")) {
							jse.executeScript("arguments[0].click();", Labels.get(j));
							break;
						}
					}
				}
			}

			jse.executeScript("arguments[0].click();", c.getContinueButton());

			Thread.sleep(3000);

			PaymentMethodsPO PM = new PaymentMethodsPO(driver);

			wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));
			jse.executeScript("arguments[0].click();", PM.getPaymentButton());

			PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);
			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			// Navigate to Dashboard
			int count2 = driver.findElements(By.tagName("a")).size();
			for (int j = 0; j < count2; j++) {
				if (driver.findElements(By.tagName("a")).get(j).getText().equals("Dashboard"))

				{
					// rw.linksToBeClickable();
					driver.findElements(By.tagName("a")).get(j).click();
					break;
				}

			}
			rw.waitForDashboardLoaded();

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

	}

	@Test(priority = 2, dependsOnMethods = { "FamilyMemberEnrollment" })
	public void Unenrollself() throws InterruptedException, IOException {
		try {

			DashboardPO d = new DashboardPO(driver);
			CalendarPO cp = new CalendarPO(driver);
			rm.openSideMenuIfNotOpenedAlready();

			while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1")) {

				d.getMenuMyActivies().click();
				Thread.sleep(1000);
				d.getmenuMyActivitiesSubMenu().getAttribute("style");
				System.out.println(d.getmenuMyActivitiesSubMenu().getAttribute("style"));
			}

			WebDriverWait wait1 = new WebDriverWait(driver, 50);
			wait1.until(ExpectedConditions.elementToBeClickable(d.getMenuMyCalendar()));

			d.getMenuMyCalendar().click();
			wait1.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

			String monthYear = cp.getMonthYearListView().getText();
			while (!monthYear.equals(dsiredMonthYear)) {
				cp.getRightArrowListView().click();
				wait1.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[@class = 'btn-group']//div[contains(@class, 'btn-white')][2]")));
				monthYear = cp.getMonthYearListView().getText();
			}

			Thread.sleep(1000);

			int count = cp.getMemberSections().size();

			for (int i = 0; i < count; i++) {
				if (cp.getMemberSections().get(i).getText().contains("HOH")) {
					jse.executeScript("arguments[0].click();",
							cp.getMemberSections().get(i).findElement(By.tagName("i")));
					break;
				}

			}

			jse.executeScript("arguments[0].click();", cp.getUnenrollListview());
			Thread.sleep(1000);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), CourseNameDisplayed));

			Assert.assertTrue(u.getCancelHeader().isDisplayed());
			Assert.assertTrue(u.getCancelText().getText().contains(YesCancelFee));
			Assert.assertTrue(u.getCancelAmnt().getText().contains("$6.00"));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundOAText().getText().contains(YesRefundOnAccount));
			Assert.assertTrue(u.getRefundOAAmnt().getText().contains("$9.00"));
			Assert.assertTrue(u.getRefundOATaxInfo().getText().contains(YesRefundOATaxInfo));

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
	}

	@Test(priority = 3, dependsOnMethods = { "FamilyMemberEnrollment" })
	public void FamilyMemberUnenrollStandby() throws InterruptedException, IOException {
		try {

			CalendarPO cp = new CalendarPO(driver);

			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

			cp.getCalendarViewLink().click();
			Thread.sleep(1000);

			String monthYear = cp.getMonthYear().getText();
			while (!monthYear.equals(dsiredMonthYear)) {
				cp.getRightArrow().click();
				wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[@class = 'btn-group']//div[contains(@class, 'btn-white')][2]")));
				monthYear = cp.getMonthYear().getText();
			}

			jse.executeScript("arguments[0].click();", cp.getCalDayBadge());

			int eventCount = cp.getCalEventTitles().size();

			for (int i = 0; i < eventCount; i++) {

				if (cp.getCalEventTitles().get(i).getText().contains(CourseNameDisplayed)) {

					jse.executeScript("arguments[0].scrollIntoView(true);", cp.getCalEventTitles().get(i));
					Thread.sleep(1000);
					cp.getCalEventTitles().get(i).click();
					break;
				}
			}

			Thread.sleep(1000);

			Assert.assertEquals("Status: ON STANDBY", cp.getStatus().getText());

			cp.getUnEnrollBtn().click();
			Thread.sleep(1000);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), CourseNameDisplayed));

			Assert.assertEquals("ON STANDBY", u.getStatus().getText());

			Assert.assertTrue(u.getStanbyHeader().isDisplayed());

			Assert.assertEquals(u.getStanbyUnenrollText().getText(), standbyUnenrollText);

			rm.returnToDashboard();

			rm.memberLogout();

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
	}

	@Test(priority = 4, description = "Member Can Unenroll from Standby")
	public void SelfUnenrollStandby() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("feemember", "Testing1!");

			rm.myCourseClickToUnenroll(dsiredMonthYear);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), CourseNameDisplayed));

			Assert.assertEquals("ON STANDBY", u.getStatus().getText());

			Assert.assertTrue(u.getStanbyHeader().isDisplayed());

			Assert.assertEquals(u.getStanbyUnenrollText().getText(), standbyUnenrollText);

			jse.executeScript("arguments[0].click();", u.getUnenrollNoRefund());

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);
			rm.memberLogout();

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

	// @AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}