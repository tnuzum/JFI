package JOL;
import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import resources.base;


public class _NewTestCaseTemplate extends base{

// This class is used as a template for making new test case classes.
// Copy this class and paste into src/test/java/EME to create new test.

	@BeforeTest
		public void initialize() throws IOException
		{
			 driver = initializeDriver();
			 driver.get(prop.getProperty("JOLLoginPage"));
		}
		
	@Test (priority = 1, description="...")
		public void pageVerifications() throws IOException
		{
		

		}
	@Test (priority = 2, description="...")
		public void pageValidations() throws IOException
		{
		

		}

	@AfterTest
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
	
	
	
	

}
