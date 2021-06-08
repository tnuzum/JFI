package SingleMemberClasses;

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

public class SelectClassesPageLayout extends base {
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
	private static String day;
	private static String date;
	private static WebDriverWait wait;
	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public SelectClassesPageLayout() {
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
		jse = (JavascriptExecutor) driver;

		getEMEURL();
		rm.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));
		rw.waitForDashboardLoaded();
		jse.executeScript("arguments[0].click();", d.getMyClassesScheduleButton());

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

	}

	@Test(priority = 1)
	public void VerifyBreadCrumbs() throws IOException, InterruptedException {

		Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
	}

	@Test(priority = 2)
	public void VerifyDefaultDateSelection() throws IOException, InterruptedException {

		c.getCalendarIcon().click();

		rm.verifyCurrentDateIsSelectedByDefault(c.getCalendarDates());

	}

	@Test(priority = 3)
	public void VerifyDefaultDayOfSelection() throws IOException, InterruptedException {

		Assert.assertTrue(c.getDayOfButton().getAttribute("class").contains("active"));

	}

	@Test(priority = 4)
	public void VerifyDayOfReturnsClassesForTheDay() throws IOException, InterruptedException {

		df2 = new SimpleDateFormat("EEEE");
		today = Calendar.getInstance();
		day = df2.format(today.getTime());
		Assert.assertEquals(c.getDayHeader().size(), 1);
		Assert.assertEquals(c.getDayHeader().get(0).getText(), day);

		df3 = new SimpleDateFormat("M/d/yy");
		date = df3.format(today.getTime());
		Assert.assertEquals(c.getDateHeader().size(), 1);
		Assert.assertEquals(c.getDateHeader().get(0).getText(), date);

	}

	@Test(priority = 5, description = "Bug 167830 - Verification")
	public void VerifyWeekOfReturnsClassesForTheWeek() throws IOException, InterruptedException {

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectTomorrowDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		c.getweekOfButton().click();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		Thread.sleep(2000);

		int n = today.get(Calendar.DAY_OF_WEEK);
		System.out.println(n);
		System.out.println(day);
		System.out.println(date);
		int dayHeaders = c.getDayHeader().size();

		if (n < 7)
			Assert.assertEquals(dayHeaders, 7 - n);
		else
			Assert.assertEquals(dayHeaders, n);

		today.add(Calendar.DAY_OF_YEAR, 1);
		day = df2.format(today.getTime());
		date = df3.format(today.getTime());

		for (int i = 0; i < c.getDayHeader().size(); i++) {

			Assert.assertEquals(c.getDayHeader().get(i).getText(), day);
			Assert.assertEquals(c.getDateHeader().get(i).getText(), date);
			today.add(Calendar.DAY_OF_YEAR, 1);
			day = df2.format(today.getTime());
			date = df3.format(today.getTime());
		}
		c.getDayOfButton().click();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
	}

	@Test(priority = 6)
	public void VerifyDefaultClubSelection() throws IOException, InterruptedException {

		WebElement Club = c.getClassClubDropdown();
		Select s = new Select(Club);
		Assert.assertEquals(s.getFirstSelectedOption().getText(), defaultClubSelection);
	}

	@Test(priority = 7)
	public void VerifyDefaultCategorySelection() throws IOException, InterruptedException {

		WebElement Category = c.getSelectClassCategory();
		Select s1 = new Select(Category);
		Assert.assertEquals(s1.getFirstSelectedOption().getText(), defaultCategorySelection);

	}

	@Test(priority = 8)
	public void VerifyVirtualClassIndicator() throws IOException, InterruptedException {

		rm.SelectTomorrowDate();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		c.getCourseFilter().click();
		c.getCourseKeyword().click();
		Thread.sleep(2000);
		c.getSearchField().sendKeys("NEWVIRTUALCLASS");
		Thread.sleep(2000);
		c.getClassApplyFilters().click();
		Thread.sleep(2000);

		int ClassCount = c.getClassTable().size();
		for (int j = 0; j < ClassCount; j++) {

			WebElement w = c.getClassTable().get(j);
			WebElement w1 = c.getClassTimeAndDuration().get(j);
			String className = w.getText();
			String classTimeAndDuration = w1.getText();

			if (className.contains("NEWVIRTUALCLASS"))

			{
				Assert.assertTrue(classTimeAndDuration.contains("Virtual"));
				Assert.assertTrue(c.getVirtualClassSearch().isDisplayed());
				jse.executeScript("arguments[0].click();", w); // Click on the specific class
				break;
			}
		}

		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//mat-dialog-container[contains(@class, 'mat-dialog-container')]")));
		while (c.getClasslabel().getText().isBlank()) {
			Thread.sleep(500);
		}

		Assert.assertTrue(c.getVirtualDetails().isDisplayed());
		Assert.assertEquals(c.getVirtualDetails().getText().trim(), "Virtual Class");

		c.getPopupSignUpButton().click();
		Thread.sleep(2000);

		Assert.assertTrue(c.getVirtualRates().isDisplayed());
		Assert.assertEquals(c.getVirtualRates().getText().trim(), "Virtual Class");

		jse.executeScript("arguments[0].click();", c.getContinueButton());
		Thread.sleep(2000);

		Assert.assertTrue(c.getVirtualReview().isDisplayed());
		Assert.assertEquals(c.getVirtualReview().getText().trim(), "Virtual Class");

		rm.returnToDashboard();
	}

	@Test(priority = 9)
	public void VerifyVirtualClassIndicatorIsNotPresentForNonVirtualClassWithOverrideURLs()
			throws IOException, InterruptedException {

		jse.executeScript("arguments[0].click();", d.getMyClassesScheduleButton());

		rm.SelectTomorrowDate();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		c.getCourseFilter().click();
		c.getCourseKeyword().click();
		Thread.sleep(2000);
		c.getSearchField().sendKeys("COPY OF BALANCE");
		Thread.sleep(2000);
		c.getClassApplyFilters().click();
		Thread.sleep(2000);

		int ClassCount = c.getClassTable().size();
		for (int j = 0; j < ClassCount; j++) {

			WebElement w = c.getClassTable().get(j);
			WebElement w1 = c.getClassTimeAndDuration().get(j);
			String className = w.getText();
			String classTimeAndDuration = w1.getText();

			if (className.contains("COPY OF BALANCE WEIGHT LOSS & NUTRITION"))

			{
				Assert.assertFalse(classTimeAndDuration.contains("Virtual"));
				Assert.assertFalse(
						rm.isElementPresent(By.xpath("//small[contains(@class, 'at-class-search-virtual')]")));
				jse.executeScript("arguments[0].click();", w); // Click on the specific class
				break;
			}
		}

		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//mat-dialog-container[contains(@class, 'mat-dialog-container')]")));
		while (c.getClasslabel().getText().isBlank()) {
			Thread.sleep(500);
		}

		Assert.assertFalse(rm.isElementPresent(By.xpath("//div[contains(@class, 'at-class-course-details-virtual')]")));

		rm.moveToElementAndClick(driver, c.getPopupSignUpButton());
		Thread.sleep(2000);

		Assert.assertFalse(rm.isElementPresent(By.xpath("//div[contains(@class, 'at-class-course-rates-virtual')]")));

		jse.executeScript("arguments[0].click();", c.getContinueButton());
		Thread.sleep(2000);

		Assert.assertFalse(rm.isElementPresent(By.xpath("//div[contains(@class, 'at-class-course-review-virtual')]")));

		rm.memberLogout();
	}
//	@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}
