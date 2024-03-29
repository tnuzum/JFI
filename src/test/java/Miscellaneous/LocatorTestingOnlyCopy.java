package Miscellaneous;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.CartPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class LocatorTestingOnlyCopy extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	public reusableWaits rw;
	public reusableMethods rm;

	public LocatorTestingOnlyCopy() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

// This class is used to test locators only.
// Only used to output the object to the console, or send click/sendkeys commands.
// This class is not executed with Test Suite (not contained in testng.xml)

	@BeforeTest
	public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();

	}

//Test
	@Test(priority = 1)
	public void locatorTestingOnly() throws IOException, InterruptedException {

		rm.activeMember1Login(); // Login to EME

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects

		d.getCartButton().click(); // Click on cart button on Dash Board

		CartPO c = new CartPO(driver); // Define the driver for Cart Page Objects

		Assert.assertTrue(c.getCartSymbol().isDisplayed());
		System.out.println(c.getCartSymbol().isDisplayed()); // returns a true value for the 'cart symbol is displayed'

		if (c.getMyCartLabel().getText().contains("MY CART"))

		{
			System.out.println("MY CART Label Present"); // Confirms the "MY CART" label is present
		} else {
			System.out.println("MY CART Label not Present");
		}

		Assert.assertTrue(c.getPackagesSymboll().isDisplayed());
		System.out.println(c.getPackagesSymboll().isDisplayed()); // returns a true value for the 'PACKAGE symbol is
																	// displayed'

		if (c.getPackagesLabel().getText().contains("PACKAGES")) {
			System.out.println("Packages Label Present"); // Confirms the "PACKAGES" label is present
		} else {
			System.out.println("Packages Label not Present");
		}

		if (c.getSubTotalLabel().getText().contains("SUB-TOTAL:")) {
			System.out.println("SubTotal Label Present"); // Confirms the "Sub-Total" label is present
		} else {
			System.out.println("SubTotal Label not Present");
		}

		if (c.getTaxLabel().getText().contains("TAX:")) {
			System.out.println("Tax Label Present"); // Confirms the "Tax" label is present
		} else {
			System.out.println("Tax Label not Present");
		}

		c.getClearCart().click();
	}

	@AfterTest
	public void teardown() throws InterruptedException {

	}

}
