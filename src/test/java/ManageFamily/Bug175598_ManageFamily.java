package ManageFamily;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

public class Bug175598_ManageFamily extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	private static DashboardPO d;
	public reusableWaits rw;
	public reusableMethods rm;
	public static ManageFamilyPO mfp;
	public static BreadcrumbTrailPO bt;
	public static WebDriverWait wait;

	public Bug175598_ManageFamily() {
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
		rm.activeMemberLogin("john", "Testing1!");
		rw.waitForDashboardLoaded();
	}

	@Test(priority = 1)
	public void verifyFamilyMemberSection() throws InterruptedException {

		rm.openSideMenuIfNotOpenedAlready();
		d.getMenuMyAccount().click();
		while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
			Thread.sleep(1000);
		}
		d.getMenuManageFamily().click();
		wait.until(ExpectedConditions.textToBePresentInElement(mfp.getPageHeader(), "Manage Family"));

		int count = mfp.getFamilyMemberNames().size();
		for (int i = 0; i < count; i++) {
			if (mfp.getFamilyMemberNames().get(i).getText().contains("Jessica Auto")) {
				mfp.getFamilyMemberNames().get(i).click();
				break;
			}
		}

		Assert.assertTrue(mfp.getPayNowButtons().get(0).isDisplayed());
		Assert.assertTrue(mfp.getMemberOptionsLabels().get(0).isDisplayed());
		Assert.assertTrue(mfp.getHohOnOffSwitchLabels().get(0).isDisplayed());
	}

	@AfterClass(enabled = true)
	public void teardown() {
		driver.quit();
		driver = null;
	}
}
