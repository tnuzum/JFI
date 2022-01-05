package manageProfile;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import pageObjects.ManageProfilePO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ChangeUserName extends base {

	public reusableMethods rm;
	public reusableWaits rw;

	public ChangeUserName() {
		rm = new reusableMethods();
		rw = new reusableWaits();
	}

	@BeforeClass
	public void initialize() throws Exception, IOException {

		driver = initializeDriver();

		rm.setDriver(driver);

		// driver.get(prop.getProperty("EMEFutureURL"));
		getEMEURL();

	}

	@Test(priority = 1)
	public void changeUserName() throws IOException, InterruptedException {

		rm.activeMemberLogin("Seema", "June@123");
		// rw.waitForDashboardLoaded();
		rm.openSideMenuIfNotOpenedAlready();
		ManageProfilePO m = new ManageProfilePO(driver);
		DashboardPO d = new DashboardPO(driver);
		// Thread.sleep(2000);
		// d.getmenuDashboardButton().click();
		// Thread.sleep(1000);
		Assert.assertTrue(d.getMenuMyAccount().isDisplayed());
		d.getMenuMyAccount().click();
		// Assert.assertTrue(d.getMenuManageProfile().isDisplayed());
		d.getMenuManageProfile().click();
		Thread.sleep(1000);
		// rm.myProfileLogin("Seema", "June@123");
		m.getuserName().click();
		m.getcurrentUSerName().sendKeys("Seema");
		m.getnewUSerName().sendKeys("Seema1");
		m.getconfirmUSerName().sendKeys("Seema1");
		rm.scrollIntoViewAndClick(driver, m.getchangeUSerName());
		rm.scrollIntoViewAndClick(driver, m.getsaveUsernmae());
//		m.getchangeUSerName().click();
//		m.getsaveUsernmae().click();

		rm.memberLogout();
	}

	@Test(priority = 2)
	public void changeUserNameBack() throws InterruptedException {
		rm.activeMemberLogin("Seema1", "June@123");
		// rw.waitForDashboardLoaded();
		rm.openSideMenuIfNotOpenedAlready();
		ManageProfilePO m = new ManageProfilePO(driver);
		DashboardPO d = new DashboardPO(driver); //
		Thread.sleep(2000);
		// d.getmenuDashboardButton().click();
		Thread.sleep(1000);
		d.getMenuMyAccount().click();
		Thread.sleep(2000);
		d.getMenuManageProfile().click();
		Thread.sleep(1000);
		// rm.myProfileLogin("Seema1", "June@123");
		m.getuserName().click();
		m.getcurrentUSerName().sendKeys("Seema1");
		m.getnewUSerName().sendKeys("Seema");
		m.getconfirmUSerName().sendKeys("Seema");
		rm.scrollIntoViewAndClick(driver, m.getchangeUSerName());
		rm.scrollIntoViewAndClick(driver, m.getsaveUsernmae());
//		m.getchangeUSerName().click();
//		m.getsaveUsernmae().click();
		rm.memberLogout();
	}

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}
}
