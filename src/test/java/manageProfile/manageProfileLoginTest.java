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

public class manageProfileLoginTest extends base {
	public reusableMethods rm;
	public reusableWaits rw;

	public manageProfileLoginTest() {

		rm = new reusableMethods();

	}

	@BeforeClass
	public void initialize() throws Exception, IOException {

		driver = initializeDriver();

		rm.setDriver(driver);

		// driver.get(prop.getProperty("EMEFutureURL"));
		getEMEURL();

	}

	@Test(priority = 1)
	public void validLoginTest() throws IOException, InterruptedException {
		ManageProfilePO m = new ManageProfilePO(driver);
		DashboardPO d = new DashboardPO(driver);
		rm.activeMemberLogin("Dandekar1", "Green@123");

		// Thread.sleep(2000);

		d.getMyProfile().click();
		Assert.assertEquals(m.getmanageProfileLoginText().getText(), "Please enter your credentials to continue.");
		m.getusernameField().sendKeys("Dandekar1");
		m.getpasswordField().sendKeys("Green@123");
		m.getcontinueButton().click();
		String pageTitle = driver.getTitle();

		Assert.assertEquals(pageTitle, "Manage Profile");

		rm.memberLogout();

	}

	@Test(priority = 2)
	public void invalidPasswordTest() throws IOException, InterruptedException {
		ManageProfilePO m = new ManageProfilePO(driver);
		DashboardPO d = new DashboardPO(driver);
		rm.activeMemberLogin("Dandekar1", "Green@123");

		// Thread.sleep(1000);

		d.getMyProfile().click();
		Assert.assertEquals(m.getmanageProfileLoginText().getText(), "Please enter your credentials to continue.");
		m.getusernameField().sendKeys("Dandekar1");
		m.getpasswordField().sendKeys("Green@12");
		m.getcontinueButton().click();
		String InvalidpaswwordText = m.getIvalidPwMyProfileText().getText();

		Assert.assertTrue(InvalidpaswwordText.contains("WE APOLOGIZE... It seems the credentials you entered are"));

		rm.memberLogout();

	}

	@Test(priority = 3)
	public void invalidUsernameTest() throws IOException, InterruptedException {
		ManageProfilePO m = new ManageProfilePO(driver);
		DashboardPO d = new DashboardPO(driver);
		rm.activeMemberLogin("Dandekar1", "Green@123");

		// Thread.sleep(1000);

		d.getMyProfile().click();
		Assert.assertEquals(m.getmanageProfileLoginText().getText(), "Please enter your credentials to continue.");
		m.getusernameField().sendKeys("Dandeka");
		m.getpasswordField().sendKeys("Green@123");
		m.getcontinueButton().click();
		// String InvalidusernameText = m.getInvalidUserNameMyProfileText().getText();
		// Assert.assertTrue(InvalidusernameText.contains("Invalid Credentials"));
		Assert.assertEquals(m.getInvalidUserNameMyProfileText().getText(), "Invalid Credentials");
		rm.memberLogout();

	}

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}
}
