package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.LoginPagePO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;


public class LocatorTestingOnly extends base{
private static Logger log =LogManager.getLogger(base.class.getName());

// This class is used to test locators only.
// Only used to output the object to the console, or send click/sendkeys commands.
// This class is not executed with Test Suite (not contained in testng.xml)

	@BeforeTest
		public void initialize() throws IOException
		{
			 driver = initializeDriver();
			 log.info("Driver Initialized");
			 driver.get(prop.getProperty("EMEFuture2URL"));
		}
		
	@Test (priority = 1)
		public void pageVerifications() throws IOException
		{	
//		System.out.println(driver.findElement(By.xpath("//label[@for='RememberUsername']")).getText());
		System.out.println(driver.findElement(By.xpath("//input[@name='RememberUsername']")).isSelected());
		System.out.println(driver.findElement(By.xpath("//input[@name='RememberUsername']")).isEnabled());
		
		}
	@Test (priority = 2)
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
