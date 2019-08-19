package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.AppointmentsPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentPO;
import resources.base;
import resources.reusableMethods;


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
			 driver.get(prop.getProperty("EMELoginPage"));
		}
		
	@Test (priority = 1)
		public void locatorTestingOnly() throws IOException, InterruptedException
		{

		reusableMethods.activeMember1Login();
		d.getMyAccountPayNow().click();
		/*WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//enterpaymentamount/div/div/div/div/div/h2")));
		Thread.sleep(3000);
		System.out.println(p.getPaymentAmountInput().getAttribute("ng-reflect-model"));

		p.getPaymentAmountInput().clear();
		Thread.sleep(3000);
		System.out.println(p.getPaymentAmountInput().getAttribute("ng-reflect-model"));
		String amount = p.getPaymentAmountInput().getAttribute("ng-reflect-model");
		int amountInt = Integer.parseInt(amount);
		if (amountInt == 0)
		{
			System.out.println("zero");
		}
		else
		{
			System.out.println("not zero");
		}*/
		
//		WebDriverWait wait = new WebDriverWait(driver, 10);
//		WebElement w = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/h2")));
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
