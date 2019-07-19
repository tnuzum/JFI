package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.LoginPO;
import pageObjects.ManageProfilePO;
import pageObjects.PayBalancePO;
import pageObjects.DashboardPO;
import pageObjects.ForgotPasswordPO;
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
		reusableMethods.activeMemberLogin();
		Thread.sleep(7000);
//		driver.findElement(By.xpath("//nav[@class='navbar navbar-static-top']/ul/li/div/button")).click();
		DashboardPO p = new DashboardPO(driver);
		p.getMyAccountPayNow().click();
		Thread.sleep(4000);	
		PayBalancePO m = new PayBalancePO(driver);
		m.getAmountRadioButton3().click();
//		Thread.sleep(2000);
//		driver.findElement(By.xpath("(//div[@class='payment-card'])[1]/div[3]/div/button"))
//		System.out.println(driver.findElement(By.xpath("//div[@id='swal2-content']")).getText());
		Actions a= new Actions(driver);
		a.moveToElement(driver.findElement(By.xpath("//input[@name='selectedPaymentAmount']"))).doubleClick().sendKeys(Keys.DELETE).build().perform();
				
				
		driver.findElement(By.xpath("//input[@name='selectedPaymentAmount']")).sendKeys("20.00");
		m.getPayWithThisMethodButton1().click();
		System.out.println(driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).getText());
		driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
		Thread.sleep(4000);
		System.out.println(driver.findElement(By.xpath("//div[@class='swal2-header']/h2")).getText());
		driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
		
		
		
		Thread.sleep(10000);
		}

	@AfterTest
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
	
	
	
	

}
