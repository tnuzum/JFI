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
import pageObjects.PayBalancePO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;


public class MakePaymentTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());

// This class is used as a template for making new test case classes.
// Copy this class and paste into src/test/java/EME to create new test.

	@BeforeTest
		public void initialize() throws IOException
		{
			 driver = initializeDriver();
			 log.info("Driver Initialized");
			 driver.get(prop.getProperty("EMEFuture2URL"));
		}
		
	@Test (priority = 1)
		public void pageVerifications() throws IOException, InterruptedException
		{	
		reusableMethods.activeMemberLogin();
		Thread.sleep(7000);
		DashboardPO p = new DashboardPO(driver);
		p.getMyAccountPayNow().click();
		Thread.sleep(4000);	
		PayBalancePO m = new PayBalancePO(driver);
		m.getAmountRadioButton3().click();
		Actions a= new Actions(driver);
		a.moveToElement(driver.findElement(By.xpath("//input[@name='selectedPaymentAmount']"))).doubleClick().sendKeys(Keys.DELETE).build().perform();		
		driver.findElement(By.xpath("//input[@name='selectedPaymentAmount']")).sendKeys("20.00");
		m.getPayWithThisMethodButton1().click();
		Thread.sleep(4000);	
		driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
		
		
		
		
		}
	@Test (priority = 2)
		public void pageValidations() throws IOException
		{	
	
		}

	@AfterTest
		public void teardown() throws InterruptedException
		{
			Thread.sleep(5000);
			driver.close();
			driver=null;
		}
	
	
	
	

}
