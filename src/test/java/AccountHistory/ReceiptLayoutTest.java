package AccountHistory;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ReceiptLayoutTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	private static DashboardPO d;
	public reusableWaits rw;
	public reusableMethods rm;
	public static AcctHistoryPO ahp;
	public static BreadcrumbTrailPO bt;
	public static WebDriverWait wait;
	static String receiptNumber;
	public static SoftAssert softAssertion = new SoftAssert();

	public ReceiptLayoutTest() {
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
		ahp = new AcctHistoryPO(driver);
		bt = new BreadcrumbTrailPO(driver);
		wait = new WebDriverWait(driver, 30);

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
		rm.activeMemberLogin("aqmember", "Testing1!");
		rw.waitForDashboardLoaded();

	}

	@Test(priority = 1, enabled = true)
	public void verifyReceiptLayout() throws InterruptedException {

		String receiptNumber = rm.purchasePackage();

		rm.openSideMenuIfNotOpenedAlready();
		d.getMenuMyAccount().click();
		while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
			Thread.sleep(1000);
		}
		d.getMenuAccountHistory().click();

		wait.until(ExpectedConditions.visibilityOf(ahp.getReceiptNumberTable()));

		ahp.getSearchField().sendKeys(receiptNumber);
		Thread.sleep(2000);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", ahp.getReceiptNumbers().get(0));
		Thread.sleep(3000);
		jse.executeScript("arguments[0].scrollIntoView(true);", ahp.getReceiptPopup());

// Add your assertions here//

		ahp.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
		Thread.sleep(1000);
		rm.memberLogout();

	}

	@AfterTest
	public void teardown() {
		driver.quit();
		driver = null;
	}
}
