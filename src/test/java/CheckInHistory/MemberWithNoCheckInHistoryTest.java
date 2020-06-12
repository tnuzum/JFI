package CheckInHistory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.CheckInHistoryPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class MemberWithNoCheckInHistoryTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	private static DashboardPO d;
	public reusableWaits rw;
	public reusableMethods rm;
	public static CheckInHistoryPO chp;
	public static BreadcrumbTrailPO bt;
	public static WebDriverWait wait;

	public MemberWithNoCheckInHistoryTest() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		d = new DashboardPO(driver);
		chp = new CheckInHistoryPO(driver);
		bt = new BreadcrumbTrailPO(driver);
		wait = new WebDriverWait(driver, 30);

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		driver.get(prop.getProperty("EMELoginPage"));
		rm.activeMemberLogin("hoh", "Testing1!");
		rw.waitForDashboardLoaded();
	}

	@Test(priority = 1)
	public void verifyBreadcrumbTest() throws InterruptedException {
		rm.openSideMenuIfNotOpenedAlready();
		d.getMenuMyAccount().click();
		while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
			Thread.sleep(1000);
		}
		d.getMenuCheckInHistory().click();
		wait.until(ExpectedConditions.textToBePresentInElement(chp.getPageHeader(), "Check-In History"));
		Assert.assertEquals(bt.getBreadcrumb1().getText(), "Dashboard");
		Assert.assertEquals(bt.getBreadcrumb2().getText(), "Check-In History");
	}

	@Test(priority = 2)
	public void verifyDefaultDateSelectionOnEndDateCalendarIconTest() throws InterruptedException {

		wait.until(ExpectedConditions.elementToBeClickable(chp.getSecondCalendarIcon()));
		chp.getSecondCalendarIcon().click();
		List<WebElement> CalendarDates = driver
				.findElements(By.xpath("//div[contains(@class, 'cdk-overlay-pane')] //td"));

		rm.verifyCurrentDateIsSelectedByDefault(CalendarDates);
		Actions a = new Actions(driver);
		a.moveToElement(chp.getFirstCalendarIcon()).click().build().perform();
		Thread.sleep(2000);
	}

	@Test(priority = 3)
	public void verifyDefaultDateSelectionOnStartDateCalendarIconTest() throws InterruptedException {

		wait.until(ExpectedConditions.elementToBeClickable(chp.getFirstCalendarIcon()));
		chp.getFirstCalendarIcon().click();
		List<WebElement> CalendarDates = driver
				.findElements(By.xpath("//div[contains(@class, 'cdk-overlay-pane')] //td"));

		rm.verifyFirstDateOfPreviousMonthIsSelectedByDefault(CalendarDates);
		Actions a = new Actions(driver);
		a.moveToElement(chp.getFirstCalendarIcon()).click().build().perform();
		Thread.sleep(2000);
	}

	@Test(priority = 4)
	public void verifyCheckInHistoryNotPresentTest() throws InterruptedException {

		wait.until(ExpectedConditions.elementToBeClickable(chp.getFirstCalendarIcon()));
		chp.getFirstCalendarIcon().click();
		int dayCount = 0;

		SimpleDateFormat dateFormat = new SimpleDateFormat("d");
		Calendar today = Calendar.getInstance();

		String TodaysDate = dateFormat.format(today.getTime());
		today.add(Calendar.DAY_OF_YEAR, -1);
		String yesterdaysDate = dateFormat.format(today.getTime());

		if (!TodaysDate.equals("1"))

		{
			wait.until(ExpectedConditions.elementToBeClickable(chp.getRightCalendarArrow()));
			chp.getRightCalendarArrow().click();
			dayCount = chp.getFirstCalendarDates().size();
			System.out.println(dayCount);

			for (int i = 0; i < dayCount; i++) {

				if (chp.getFirstCalendarDates().get(i).getText().equals(yesterdaysDate)) {
					chp.getFirstCalendarDates().get(i).click();
					break;
				}
			}
		} else {
			dayCount = chp.getFirstCalendarDates().size();
			for (int i = 0; i < dayCount; i++) {

				if (chp.getFirstCalendarDates().get(i).getText().equals(yesterdaysDate)) {
					chp.getFirstCalendarDates().get(i).click();
					break;
				}
			}
		}

		boolean CheckinHistorySectionPresent = rm.isElementPresent(By.id("checkinHistory"));
		Assert.assertEquals(CheckinHistorySectionPresent, false);
		Assert.assertEquals(chp.getNoEntriesMessage().getText(),
				"There are no check in history entries that match the search criteria.");
	}

	@Test(priority = 5)
	public void verifyVisitsByMonthSectionIsNotPresentTest() {

		boolean checkinChartPresent = rm
				.isElementPresent(By.xpath("//div[@class='ibox-content no-margins no-padding']/div"));
		Assert.assertEquals(checkinChartPresent, false);

	}

	@AfterTest
	public void teardown() {
		driver.close();
		driver = null;
	}
}
