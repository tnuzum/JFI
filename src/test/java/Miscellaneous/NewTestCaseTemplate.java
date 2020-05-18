package Miscellaneous;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import resources.base;


public class NewTestCaseTemplate extends base{
private static Logger log =LogManager.getLogger(base.class.getName());

// This class is used as a template for making new test case classes.
// Copy this class and paste into src/test/java/EME to create new test.

	@BeforeTest
		public void initialize() throws IOException
		{
			 driver = initializeDriver();
			 driver.get(prop.getProperty("EMELoginPage"));
		}
		
	@Test (priority = 1, description="...")
		public void pageVerifications() throws IOException
		{
		
		log.info("...");
		}
	@Test (priority = 2, description="...")
		public void pageValidations() throws IOException
		{
		
		log.info("...");
		}

	@AfterTest
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
	
	
	
	

}
