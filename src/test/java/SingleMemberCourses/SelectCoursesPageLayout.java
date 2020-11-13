package SingleMemberCourses;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class SelectCoursesPageLayout extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static DashboardPO d;
	private static BreadcrumbTrailPO BT;
	private static ClassSignUpPO c;
	private static String defaultClubSelection = "Jonas Sports-Plex";
	private static String defaultCategorySelection = "ALL";
	private static SimpleDateFormat df1;
	private static SimpleDateFormat df2;
	private static SimpleDateFormat df3;
	private static Calendar today;
	private static WebDriverWait wait;

	public reusableWaits rw;
	public reusableMethods rm;

	public SelectCoursesPageLayout() {
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

		BT = new BreadcrumbTrailPO(driver);
		d = new DashboardPO(driver);
		c = new ClassSignUpPO(driver);

		getEMEURL();
		rm.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));
		rw.waitForDashboardLoaded();

		d.getMyCoursesEventsScheduleButton().click();

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

	}

	@Test(priority = 1)
	public void VerifyBreadCrumbs() throws IOException, InterruptedException {

		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
	}

	@Test(priority = 2)
	public void VerifyDefaultMonthSelection() throws IOException, InterruptedException {

		df1 = new SimpleDateFormat("MMM");
		today = Calendar.getInstance();
		String currentMonth = df1.format(today.getTime());

		WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
		int monthCount = MonthNames.findElements(By.tagName("label")).size();
		for (int i = 0; i < monthCount; i++) {
			String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
			if (monthName.equals(currentMonth)) {
				Assert.assertTrue(
						MonthNames.findElements(By.tagName("label")).get(i).getAttribute("class").contains("active"));
				break;
			}

		}

	}

	@Test(priority = 3)
	public void VerifyDefaultYearSelection() throws IOException, InterruptedException {

		df2 = new SimpleDateFormat("YYYY");
		today = Calendar.getInstance();
		String currentYear = df2.format(today.getTime());

		Assert.assertTrue(c.getYear().getText().contains(currentYear));

	}

	@Test(priority = 4)
	public void VerifyCoursesReturnedForCurrentMonth() throws IOException, InterruptedException {

		df3 = new SimpleDateFormat("MMM   YYYY");
		today = Calendar.getInstance();
		String monthYear = df3.format(today.getTime()).toUpperCase();

		Assert.assertEquals(c.getmonthHeader().getText(), monthYear);

	}

	@Test(priority = 5)
	public void VerifyDefaultClubSelection() throws IOException, InterruptedException {

		WebElement Club = c.getCourseClubDropdown();
		Select s = new Select(Club);
		Assert.assertEquals(s.getFirstSelectedOption().getText(), defaultClubSelection);
	}

	@Test(priority = 6)
	public void VerifyDefaultCategorySelection() throws IOException, InterruptedException {

		WebElement Category = c.getSelectCourseCategory();
		Select s1 = new Select(Category);
		Assert.assertEquals(s1.getFirstSelectedOption().getText(), defaultCategorySelection);

	}

	@Test(priority = 8)
	public void VerifyVirtualCourseIndicator() throws IOException, InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		c.getCourseFilter().click();
		c.getCourseKeyword().click();
		Thread.sleep(2000);
		c.getSearchField().sendKeys("NEWVIRTUALCOURSE");
		Thread.sleep(2000);
		c.getCourseApplyFilters().click();
		Thread.sleep(2000);

		int ClassCount = c.getClassTable().size();
		for (int j = 0; j < ClassCount; j++) {

			WebElement w = c.getClassTable().get(j);
			WebElement w1 = c.getClassTimeAndDuration().get(j);
			String className = w.getText();
			String classTimeAndDuration = w1.getText();

			if (className.contains("NEWVIRTUALCOURSE"))

			{
				Assert.assertTrue(classTimeAndDuration.contains("Virtual"));
				Assert.assertTrue(c.getVirtualCourseSearch().isDisplayed());
				w.click(); // Click on the specific class
				break;
			}
		}

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'modal-content')]")));
		while (c.getClasslabel().getText().isBlank()) {
			Thread.sleep(500);
		}

		Assert.assertTrue(c.getVirtualDetails().isDisplayed());
		Assert.assertEquals(c.getVirtualDetails().getText().trim(), "Virtual Course");

		c.getPopupSignUpButton().click();
		Thread.sleep(2000);

		Assert.assertTrue(c.getVirtualRates().isDisplayed());
		Assert.assertEquals(c.getVirtualRates().getText().trim(), "Virtual Course");

		c.getContinueButton().click();
		Thread.sleep(2000);

		Assert.assertTrue(c.getVirtualReview().isDisplayed());
		Assert.assertEquals(c.getVirtualReview().getText().trim(), "Virtual Course");

		rm.returnToDashboard();
	}

	@Test(priority = 9)
	public void VerifyVirtualClassIndicatorIsNotPresentForNonVirtualClassWithOverrideURLs()
			throws IOException, InterruptedException {

		d.getMyCoursesEventsScheduleButton().click();

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		c.getCourseFilter().click();
		c.getCourseKeyword().click();
		Thread.sleep(2000);
		c.getSearchField().sendKeys("VIRTUALTEST");
		Thread.sleep(2000);
		c.getCourseApplyFilters().click();
		Thread.sleep(2000);

		int ClassCount = c.getClassTable().size();
		for (int j = 0; j < ClassCount; j++) {

			WebElement w = c.getClassTable().get(j);
			WebElement w1 = c.getClassTimeAndDuration().get(j);
			String className = w.getText();
			String classTimeAndDuration = w1.getText();

			if (className.contains("VIRTUALTEST"))

			{
				Assert.assertFalse(classTimeAndDuration.contains("Virtual"));
				Assert.assertFalse(
						rm.isElementPresent(By.xpath("//small[contains(@class, 'at-course-search-virtual')]")));
				w.click(); // Click on the specific class
				break;
			}
		}

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'modal-content')]")));
		while (c.getClasslabel().getText().isBlank()) {
			Thread.sleep(500);
		}

		Assert.assertFalse(rm.isElementPresent(By.xpath("//div[contains(@class, 'at-class-course-details-virtual')]")));

		c.getPopupSignUpButton().click();
		Thread.sleep(2000);

		Assert.assertFalse(rm.isElementPresent(By.xpath("//div[contains(@class, 'at-class-course-details-virtual')]")));

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", c.getContinueButton());
		Thread.sleep(2000);

		Assert.assertFalse(rm.isElementPresent(By.xpath("//div[contains(@class, 'at-class-course-details-virtual')]")));

		rm.memberLogout();
	}
//	@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
