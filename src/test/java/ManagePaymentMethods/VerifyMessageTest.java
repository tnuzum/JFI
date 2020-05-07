package ManagePaymentMethods;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import pageObjects.ManagePayMethodsPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class VerifyMessageTest extends base {

	@BeforeClass
	public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
		reusableMethods.activeMemberLogin("noccmember", "Testing1!"); // Login to EME
		reusableWaits.waitForDashboardLoaded();

	}

	@Test(priority = 1)
	public void VerifyMessageWhenNoSavedCardsExist() throws InterruptedException {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects
		ManagePayMethodsPO mp = new ManagePayMethodsPO(driver);
		reusableMethods.openSideMenuIfNotOpenedAlready();

		d.getMenuMyAccount().click();
		while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
			Thread.sleep(1000);
		}

		d.getMenuManagePmntMethods().click();
		Thread.sleep(2000);

		Assert.assertTrue(mp.getNoSavedCardMessage().isDisplayed());
	}

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
