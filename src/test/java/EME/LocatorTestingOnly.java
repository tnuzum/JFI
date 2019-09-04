package EME;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.AppointmentsPO;
import pageObjects.CartPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;

public class LocatorTestingOnly extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

// This class is used to test locators only.
// Only used to output the object to the console, or send click/sendkeys commands.
// This class is not executed with Test Suite (not contained in testng.xml)

	@BeforeTest
	public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));

	}

	@Test(priority = 1)
	public void locatorTestingOnly() throws IOException, InterruptedException {

		reusableMethods.activeMember1Login();
			DashboardPO d = new DashboardPO(driver);
		d.getCartButton().click();
		CartPO c = new CartPO(driver);
//		driver.findElement(By.xpath("//*[text()='CLEAR CART']")).click();
//		c.getClearCart().click();
		

	}

	@AfterTest
	public void teardown() throws InterruptedException {

	}

}
