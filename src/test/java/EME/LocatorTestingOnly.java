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
		public void locatorTestingOnly() throws IOException, InterruptedException
		{	
		reusableMethods.activeMemberLogin();
		Thread.sleep(10000);
//		System.out.println(driver.findElement(By.xpath("//div[@class='homeComponent']//appointmentswidget/div/div[3]/a[1]")).getText());
		System.out.println(driver.findElement(By.xpath("//mat-nav-list[@class='mat-nav-list']/a[4]/div")).getText());
		Thread.sleep(5000);
		driver.findElement(By.xpath("//mat-nav-list[@class='mat-nav-list']/a[4]/div")).click();
		Thread.sleep(5000);
		}

	@AfterTest
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
	
	
	
	

}
