package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.LoginPO;
import pageObjects.DashboardPO;
import pageObjects.ForgotUsernamePO;
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
		public void locatorTestingOnly() throws IOException, InterruptedException
		{	
//		reusableMethods.activeMemberLogin();
//		Thread.sleep(10000);
//		driver.findElement(By.xpath("//nav[@class='navbar navbar-static-top']/ul/li/div/button")).click();

		LoginPO l = new LoginPO(driver);
		l.getForgotUsername().click();
		Thread.sleep(4000);	
		ForgotUsernamePO f = new ForgotUsernamePO(driver);
		f.getRecoverUsernameButton().click();
		System.out.println(driver.findElement(By.xpath("//div[@id='loginForm']/form/div/div/div[1]/span/span")).getText());
		System.out.println(driver.findElement(By.xpath("//div[@id='loginForm']/form/div/div/div[2]/span/span")).getText());
		System.out.println(driver.findElement(By.xpath("//div[@id='loginForm']/form/div/div/div[3]/span/span")).getText());
		System.out.println(driver.findElement(By.xpath("//div[@id='loginForm']/form/div/div/div[4]/span/span")).getText());

//		Thread.sleep(2000);
//		driver.findElement(By.xpath("//div[@id='loginForm']/form/h2")).click();
//		Thread.sleep(3000);
		}

	@AfterTest
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
	
	
	
	

}
