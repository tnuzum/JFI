package ManageFamily;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.DashboardPO;
import pageObjects.ManageFamilyPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ManageFamilyLayoutTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	private static DashboardPO d;
	public reusableWaits rw;
	public reusableMethods rm;
	public static ManageFamilyPO mfp;
	public static BreadcrumbTrailPO bt;
	public static WebDriverWait wait;

	public ManageFamilyLayoutTest() {
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
		mfp = new ManageFamilyPO(driver);
		bt = new BreadcrumbTrailPO(driver);
		wait = new WebDriverWait(driver, 30);

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
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
		d.getMenuManageFamily().click();
		wait.until(ExpectedConditions.textToBePresentInElement(mfp.getPageHeader(), "Manage Family"));
		Assert.assertEquals(bt.getBreadcrumb1().getText(), "Dashboard");
		Assert.assertEquals(bt.getBreadcrumb2().getText(), "Manage Family");
	}

	@Test(priority = 2)
	public void verifyPageTitleText() {

		Assert.assertTrue(mfp.getPageText().isDisplayed());
	}

	@Test(priority = 3)
	public void verifyFamilyMembersNamesArePresent() {

		Assert.assertTrue(mfp.getFamilyMemberNames().size() > 0);
	}

	@Test(priority = 4)
	public void verifyFamilyMemberSection() {

		int count = mfp.getFamilyMemberNames().size();
		for (int i = 0; i < count; i++) {
			if (mfp.getFamilyMemberNames().get(i).getText().contains("FreeMember Auto"))
				mfp.getFamilyMemberNames().get(i).click();
		}

		Assert.assertTrue(mfp.getMemberName().isDisplayed());
		Assert.assertTrue(mfp.getPayNowButtons().get(1).isDisplayed());
		Assert.assertTrue(mfp.getMemberOptionsLabels().get(1).isDisplayed());
		Assert.assertTrue(mfp.getHohOnOffSwitchLabels().get(1).isDisplayed());
	}

	@Test(priority = 4)
	public void verifyPayNowFunctionality() throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", mfp.getPayNowButtons().get(1));
		wait.until(ExpectedConditions.textToBePresentInElement(mfp.getPageHeader(), "Pay Balance"));

		Select s = new Select(
				driver.findElement(By.xpath("//select[contains(@class, 'at-paybalance-dropdown-user')]")));
		Assert.assertEquals(s.getFirstSelectedOption().getText(), "FreeMember Auto");
		rm.memberLogout();

	}

	@AfterClass(enabled = true)
	public void teardown() {
		driver.quit();
		driver = null;
	}
}
