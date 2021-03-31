package Calendar;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.CalendarPO;
import pageObjects.DashboardPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class CalendarLayout extends base {

	private static Logger log = LogManager.getLogger(base.class.getName());

	private static String classToEnroll = "UnenrollClass1";

	private static String paymentOption = "Pay Single Class Fee";

	private static String payMethod = "On Account";

	private static String testName = null;

	private static DashboardPO d;
	private static CalendarPO cp;
	private static BreadcrumbTrailPO BT;
	private static UnenrollPO u;

	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public CalendarLayout() {
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

		d = new DashboardPO(driver);
		cp = new CalendarPO(driver);
		BT = new BreadcrumbTrailPO(driver);
		u = new UnenrollPO(driver);
		jse = (JavascriptExecutor) driver;

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();

		rm.activeMemberLogin("calhoh", "Testing1!");
		rw.waitForDashboardLoaded();

	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1)
	public void LayoutTest() throws InterruptedException, IOException {

		try {
			rm.enrollFamilyMbrInClass(classToEnroll, paymentOption, payMethod, "Not Free", "Calmember");

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

			Assert.assertEquals("My Calendar", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("My Calendar", BT.getBreadcrumb2().getText());

			Assert.assertTrue(cp.getClubDropdown().isDisplayed());
			Select s = new Select(cp.getClubDropdown());

			Assert.assertTrue(s.getFirstSelectedOption().getText().trim().equals("ALL"));

			Assert.assertTrue(cp.getAdditionalFilters().isDisplayed());

			cp.getAdditionalFilters().click();
			Thread.sleep(1000);
			Assert.assertEquals(cp.getClassCourseApptChkBoxes().size(), 3);

			Assert.assertTrue(cp.getSelectFamilyLabel().isDisplayed());
			Assert.assertEquals(cp.getFamilyMemberNames().size(), 2);

			Assert.assertTrue(cp.getFamilyMemberNames().get(0).getText().contains("CalHOH Auto"));
			Assert.assertTrue(cp.getFamilyMemberNames().get(1).getText().contains("CalMember Auto"));

			Assert.assertTrue(cp.getApplyFiltersLink().isDisplayed());

			Assert.assertTrue(cp.getCalendarViewLink().isDisplayed());
			Assert.assertTrue(cp.getCalendarListViewLink().isDisplayed());
			Assert.assertTrue(cp.getCalendarHistoryLink().isDisplayed());

			cp.getCalendarHistoryLink().click();
			Thread.sleep(1000);
			Assert.assertTrue(cp.getCalendarHistory().isDisplayed());

			cp.getCalendarListViewLink().click();
			Thread.sleep(1000);
			Assert.assertTrue(cp.getCalendarList().isDisplayed());

			Calendar calendar = Calendar.getInstance();

			if (calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DATE)) {

				jse.executeScript("arguments[0].click();",
						driver.findElements(By.xpath("//i[contains(@class, 'right')]")).get(0));
				// driver.findElements(By.xpath("//i[contains(@class,
				// 'right')]")).get(0).click();

				WebDriverWait wait = new WebDriverWait(driver, 50);
				wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

				Thread.sleep(1000);

			}
			Assert.assertTrue(cp.getMemberClassDetails().isDisplayed());
			jse.executeScript("arguments[0].click();", cp.getClassGearButton());

			Assert.assertTrue(cp.getAddToCalButtonListView().isDisplayed());
			Assert.assertTrue(cp.getUnenrollListview().isDisplayed());

			jse.executeScript("arguments[0].click();", cp.getUnenrollListview());

			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll));

			Assert.assertEquals(u.getPageHeader().getText(), "Unenroll");
			Assert.assertTrue(u.getUnenrollButton().isDisplayed());
			// jse.executeScript("arguments[0].click();", u.getCancelButton());
			jse.executeScript("arguments[0].click();", u.getCancelButton());
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

			cp.getCalendarViewLink().click();
			Thread.sleep(1000);
			Assert.assertTrue(cp.getCalendar().isDisplayed());

			Assert.assertTrue(cp.getMonthButton().isDisplayed());
			Assert.assertTrue(cp.getWeekButton().isDisplayed());

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

	@Test(priority = 2, dependsOnMethods = { "LayoutTest" })
	public void UnenrollFromCalendarView() throws InterruptedException, IOException {
		try {

			rm.MyActivitiesTomorrowClick();

			Thread.sleep(2000);

			int eventCount = cp.getCalEventTitles().size();
			System.out.println(eventCount);

			for (int i = 0; i < eventCount; i++) {
				System.out.println(cp.getCalEventTitles().get(i).getText());

				if (cp.getCalEventTitles().get(i).getText().contains(classToEnroll)) {

					jse.executeScript("arguments[0].scrollIntoView(true);", cp.getCalEventTitles().get(i));
					Thread.sleep(1000);
					Actions a = new Actions(driver);
					a.moveToElement(cp.getCalEventTitles().get(i)).click().build().perform();
					break;
				}
			}

			Thread.sleep(1000);
			Assert.assertTrue(cp.getClassDetailPopup().isDisplayed());
			Assert.assertTrue(cp.getClassName().getText().contains(classToEnroll));
			Assert.assertTrue(cp.getEnrolledMemberName().getText().contains("Calmember"));
			Assert.assertTrue(cp.getClassDate().getText().equals(tomorrowsDate));
			Assert.assertTrue(cp.getClassTime().getText().contains("6:00 AM"));
			Assert.assertTrue(cp.getType().getText().contains("Class"));
			Assert.assertTrue(cp.getDuration().getText().contains("30 Min"));
			Assert.assertTrue(cp.getCategory().getText().contains("Tennis Lessons"));
			Assert.assertTrue(cp.getInstructor().getText().contains("Max Gibbs"));
			Assert.assertTrue(cp.getClub().getText().contains("Jonas Sports-Plex"));

			cp.getUnEnrollBtn().click();
			Thread.sleep(1000);

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll));

			jse.executeScript("arguments[0].click();", u.getUnenrollButton());
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

	@Test(priority = 3)
	public void unenrollFromCalendarListView() throws InterruptedException, IOException {

		try {
			rm.enrollInClass(classToEnroll, paymentOption, payMethod, "Not Free");

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

//			cp.getCalendarListViewLink().click();
			Thread.sleep(1000);

			Calendar calendar = Calendar.getInstance();

			if (calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DATE)) {

				driver.findElements(By.xpath("//i[contains(@class, 'right')]")).get(0).click();

				WebDriverWait wait = new WebDriverWait(driver, 50);
				wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

				Thread.sleep(1000);

			}

			jse.executeScript("arguments[0].click();", cp.getClassGearButton());

			jse.executeScript("arguments[0].click();", cp.getUnenrollListview());
			Thread.sleep(1000);

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll));

			Assert.assertEquals(u.getPageHeader().getText(), "Unenroll");
			Assert.assertTrue(u.getUnenrollButton().isDisplayed());
			Assert.assertTrue(u.getCancelButton().isDisplayed());
			Thread.sleep(1000);

			jse.executeScript("arguments[0].click();", u.getUnenrollButton());
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
			rm.memberLogout();
		}
	}
//@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}
