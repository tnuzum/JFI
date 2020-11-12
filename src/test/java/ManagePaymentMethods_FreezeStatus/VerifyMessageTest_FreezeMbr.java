package ManagePaymentMethods_FreezeStatus;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import pageObjects.ManagePayMethodsPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class VerifyMessageTest_FreezeMbr extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	public reusableWaits rw;
	public reusableMethods rm;

	public VerifyMessageTest_FreezeMbr() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

	@BeforeClass
	public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
		rm.activeMemberLogin("freezemember", "Testing1!"); // Login to EME

	}

	@Test(priority = 1)
	public void VerifyMessageWhenNoSavedCardsExist() throws InterruptedException {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects
		ManagePayMethodsPO mp = new ManagePayMethodsPO(driver);
		rm.openSideMenuIfNotOpenedAlready();

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
