package SingleMemberClasses;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

		BT = new BreadcrumbTrailPO(driver);
		d = new DashboardPO(driver);
		c = new ClassSignUpPO(driver);

		driver.get(prop.getProperty("EMELoginPage"));
		rm.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));
		rw.waitForDashboardLoaded();
		d.getMyClassesScheduleButton().click();

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

		df1 = new SimpleDateFormat("d");
		today = Calendar.getInstance();
		String todaysMDate = df1.format(today.getTime());

		c.getCalendarIcon().click();
		int dayCount = c.getCalendarDates().size();

		for (int i = 0; i < dayCount; i++) {

			if (c.getCalendarDates().get(i).getText().equals(todaysMDate)) {
				Assert.assertTrue(c.getCalendarDates().get(i).getAttribute("class").contains("active"));
				break;
			}
		}
		Actions a = new Actions(driver);
		a.moveToElement(c.getCalendarIcon()).click().build().perform();
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

		df3 = new SimpleDateFormat("M/d/YY");
		date = df3.format(today.getTime());
		Assert.assertEquals(c.getDateHeader().size(), 1);
		Assert.assertEquals(c.getDateHeader().get(0).getText(), date);

	}

	@Test(priority = 5)
	public void VerifyWeekOfReturnsClassesForTheWeek() throws IOException, InterruptedException {

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		c.getweekOfButton().click();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		for (int i = 0; i < c.getDayHeader().size(); i++) {

			Assert.assertEquals(c.getDayHeader().get(i).getText(), day);
			Assert.assertEquals(c.getDateHeader().get(i).getText(), date);
			today.add(Calendar.DAY_OF_YEAR, 1);
			day = df2.format(today.getTime());
			date = df3.format(today.getTime());
		}

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
		rm.memberLogout();
	}
//	@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
