package EME;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.DashboardPO;
import resources.base;


public class LocatorTestingOnly extends base{
private static Logger log =LogManager.getLogger(base.class.getName());
DashboardPO d = new DashboardPO(driver);

// This class is used to test locators only.
// Only used to output the object to the console, or send click/sendkeys commands.
// This class is not executed with Test Suite (not contained in testng.xml)

	@BeforeTest
		public void initialize() throws InterruptedException, IOException
		{
			 driver = initializeDriver();
			 log.info("Driver Initialized");
//			 driver.get(prop.getProperty("EMELoginPage"));
		}
		
	@Test (priority = 1)
		public void locatorTestingOnly() throws IOException, InterruptedException
		{
		
		String up = System.getenv("USERPROFILE");
		String projectPath = System.getenv("ECLIPSE_HOME");
		System.out.println(projectPath);
		System.out.println(up);
		
		SoftAssert sa = new SoftAssert();
		sa.assertTrue(false);
		sa.assertTrue(true);
		sa.assertTrue(false);
		
		sa.assertAll();
		}

	
	

	@AfterTest
		public void teardown() throws InterruptedException
		{
//			Thread.sleep(10000);
//			driver.close();
//			driver=null;
		}
	
	
	
	

}
