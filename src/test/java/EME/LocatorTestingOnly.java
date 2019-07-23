package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.PaymentPO;
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
//		driver.findElement(By.xpath("//nav[@class='navbar navbar-static-top']/ul/li/div/button")).click();
		DashboardPO p = new DashboardPO(driver);

		p.getMyAccountPayNow().click();
		Thread.sleep(2000);	
		PaymentPO m = new PaymentPO(driver);
		m.getAmountRadioButton3().click();
		Thread.sleep(2000);
//		System.out.println(driver.findElement(By.xpath("//div[@class='payments-method']/div/div/h2/br")).getAttribute("value"));
		WebElement w = driver.findElement(By.xpath("//div[@class='m-signature-pad--body']//canvas"));
		Actions a= new Actions(driver);
		a.moveToElement(driver.findElement(By.xpath("//div[@class='m-signature-pad--body']//canvas"))).click().doubleClick().sendKeys(Keys.DELETE).build().perform();	
		a.dragAndDropBy(w, 1858, 1006).build().perform();
		Thread.sleep(10000);
		}
	@AfterTest
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
	
	
	
	

}
