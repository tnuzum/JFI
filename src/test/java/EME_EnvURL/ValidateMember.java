package EME_EnvURL;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;

public class ValidateMember extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	public reusableMethods rm;

	public ValidateMember() {

		rm = new reusableMethods();

	}

// This class is used as a template for making new test case classes.
// Copy this class and paste into src/test/java/EME to create new test.

//	@BeforeTest
	@BeforeClass
	@Parameters({ "EMELoginPage" })
	public void initialize(String EMELoginPage) throws InterruptedException, IOException {
//		public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		log.info("Driver Initialized");
		// String EMELoginPage = prop.getProperty("EMELoginPage");
		driver.get(EMELoginPage);
	}

	@Test(priority = 1, description = "...")
	public void ValidateMemberName() throws IOException, InterruptedException {
		rm.activeMember1Login();
		DashboardPO d = new DashboardPO(driver);
		Thread.sleep(3000);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember1_fullname"));
		Assert.assertEquals("2", d.getMyFamilyMemberCount().getText());
		rm.memberLogout();
		log.info("...");
	}

	@Test(priority = 2, description = "...")
	public void pageValidations() throws IOException {

		log.info("...");
	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		Thread.sleep(3000);
		driver.close();
		driver = null;
	}

}
