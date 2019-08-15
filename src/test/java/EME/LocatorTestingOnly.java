package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
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
			 driver.get(prop.getProperty("EMEFutureURL"));
		}
		
	@Test (priority = 1)
		public void locatorTestingOnly() throws IOException, InterruptedException
		{	
		reusableMethods.activeMember1Login();
		DashboardPO d = new DashboardPO(driver);
//		WebDriverWait wait = new WebDriverWait(driver, 10);
//		WebElement w = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/h2")));
		System.out.println(d.getMyInfoMemberName().getText());
		
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
