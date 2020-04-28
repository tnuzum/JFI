package EME;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class DashBoard_Menu extends base {
//	@BeforeTest
	@BeforeClass
	public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
		reusableMethods.activeMember1Login(); // Login to EME
		reusableWaits.waitForDashboardLoaded();

	}

	@Test(priority = 1)
	public void VerifyMenuDashboardButton() throws IOException, InterruptedException {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects
//			d.getDashboardButton().click();

		String leftMenuOpen = d.getLeftMenu().getAttribute("ng-reflect-opened");
		while (!leftMenuOpen.equals("true")) {
			d.getMenuButton().click();
			leftMenuOpen = d.getLeftMenu().getAttribute("ng-reflect-opened");
		}
		// Verify the menuDashboardButton text value
		Assert.assertTrue(d.getDashboardButton().isDisplayed());
		String dashboardButtonText = d.getDashboardButton().getText();
		Assert.assertEquals(dashboardButtonText, "  Dashboard");

	}

	@Test(priority = 2)
	public void VerifyMyActivitiesMenu() throws InterruptedException {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects

		Assert.assertTrue(d.getMenuMyActivies().isDisplayed());
		String myActivitiesMenuLabel = d.getMenuMyActivies().getText();
		Assert.assertEquals(myActivitiesMenuLabel, "  My Activities");
		d.getMenuMyActivies().click();

		while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1")) {
			Thread.sleep(1000);
		}
		Assert.assertTrue(d.getMenuClassSchedule().isDisplayed());
		Assert.assertTrue(d.getMenuCourseEventSchedule().isDisplayed());
		Assert.assertTrue(d.getMenuBookAppointment().isDisplayed());
		Assert.assertTrue(d.getMenuMyCalendar().isDisplayed());

	}

	@Test(priority = 3)
	public void VerifyMyAccountMenu() throws InterruptedException {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects

		Assert.assertTrue(d.getMenuMyAccount().isDisplayed());
		String myAccountMenuLabel = d.getMenuMyAccount().getText();
		Assert.assertEquals(myAccountMenuLabel, "  My Account");
		d.getMenuMyAccount().click();
		while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
			Thread.sleep(1000);
		}

		Assert.assertTrue(d.getMenuPayBalance().isDisplayed());
		Assert.assertTrue(d.getMenuManagePmntMethods().isDisplayed());
		Assert.assertTrue(d.getMenuManageProfile().isDisplayed());
		Assert.assertTrue(d.getMenuManageFamily().isDisplayed());
		Assert.assertTrue(d.getMenuAccountHistory().isDisplayed());
		Assert.assertTrue(d.getMenuPackages().isDisplayed());
		Assert.assertTrue(d.getMenuCheckInHistory().isDisplayed());
	}

	@Test(priority = 4)
	public void VerifyMenuShopPackages() {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects

		Assert.assertTrue(d.getMenuShopPackages().isDisplayed());
		String menuShopPackagesLabel = d.getMenuShopPackages().getText();
		Assert.assertEquals(menuShopPackagesLabel, "  Shop Packages");
		d.getMenuPackages().click();

	}

	@Test(priority = 5)
	public void VerifyMenuCartIsNotPresent() {

		boolean menuCartPresent = reusableMethods
				.isElementPresent(By.xpath("//a[contains(@class, 'at-mainnav-cart')]"));
		Assert.assertEquals(menuCartPresent, false);

	}

	@Test(priority = 6)
	public void VerifyMenuLogout() {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects

		// My Info Section
		Assert.assertTrue(d.getMenuLogOut().isDisplayed());
		String menuLogoutLabel = d.getMenuLogOut().getText();
		Assert.assertEquals(menuLogoutLabel, "  Log Out");

	}

//			@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
