package EME;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;


public class loginMemberTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());
@BeforeTest
	public void initialize() throws IOException
	{
		 driver = initializeDriver();
		 log.info("Driver Initialized");
		 driver.get(prop.getProperty("EMEFuture2URL"));
	}
	
	@Test (priority = 10)
	public void activeMemberLogin() throws InterruptedException
	{
		reusableMethods.activeMemberLogin();

//** explicit wait is not working, so I'm still using sleep **
//		WebDriverWait w = new WebDriverWait(driver, 20); // waiting to confirm page has finished loading
//		w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/h2")));
		Thread.sleep(10000);
		Assert.assertEquals(driver.getTitle(), "Dashboard");// Confirming Dashboard page is shown
		log.info("Dashboard Title Verified");
		DashboardPO d=new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember1_fullname"));// Confirming correct member is logged in
		log.info("Member Name Verified");
		
	}
	@Test (priority = 20, dependsOnMethods = "activeMemberLogin")
	public void activeMemberLogout() throws InterruptedException
	{
		DashboardPO d=new DashboardPO(driver);
		d.getLogoutButton().click();
		log.info("User Logged Out");
		Thread.sleep(2000);
	}
	@AfterTest
		public void teardown() throws InterruptedException
		{
		Thread.sleep(5000);
		driver.close();
		driver=null;
		}

}
