package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.PaymentPO;
import pageObjects.AppointmentsPO;
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
		reusableMethods.activeMember1Login();
		DashboardPO d = new DashboardPO(driver);
		System.out.println(d.getMyInfoAddress1().getText());
		
//		Assert.assertEquals((driver.findElement(By.xpath("(//span[@class='class-list-header'])[4]/strong")).getText()), "DRIVING RANGE PACKAGE");
//		driver.findElement(By.xpath("//nav[@class='navbar navbar-static-top']/ul/li/div/button")).click();
//		DashboardPO p = new DashboardPO(driver);

//		p.getMyApptsScheduleButton().click();


//		driver.findElement(By.xpath("(//select[@name='clubs'])[1]")).sendKeys("abcdefghijklmnopqrstuvwxyz");
//		WebElement w = driver.findElement(By.xpath("(//select[@name='clubs'])[1]"));
//		Actions a= new Actions(driver);
//		driver.findElement(By.xpath("(//select[@name='clubs'])[1]")).sendKeys("c");
//		a.moveToElement(driver.findElement(By.xpath("(//select[@name='clubs'])[1]"))).sendKeys(Keys.ENTER).build().perform();
//		a.sendKeys(Keys.ENTER).build().perform();
//		AppointmentsPO ap = new AppointmentsPO(driver);
//		Thread.sleep(2000);
//		ap.getBookableItemCategory().sendKeys("g",Keys.ENTER);
//		driver.findElement(By.xpath("(//select[@name='bookableItemCategory'])")).sendKeys("g");
//		a.moveToElement(driver.findElement(By.xpath("(//select[@name='bookableItemCategory'])"))).sendKeys(Keys.ENTER).build().perform();
//		a.sendKeys(Keys.ENTER).build().perform();
//		Thread.sleep(2000);
/*		driver.findElement(By.xpath("(//select[@name='bookableItem'])")).sendKeys("g");
		a.moveToElement(driver.findElement(By.xpath("(//select[@name='bookableItem'])"))).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//select[@name='primaryResourceType'])")).sendKeys("g");
		a.moveToElement(driver.findElement(By.xpath("(//select[@name='primaryResourceType'])"))).sendKeys("g").sendKeys(Keys.ENTER).build().perform();
		*/
		}
	@AfterTest
		public void teardown() throws InterruptedException
		{
//			Thread.sleep(10000);
//			driver.close();
//			driver=null;
		}
	
	
	
	

}
