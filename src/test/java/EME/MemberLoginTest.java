package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;

public class MemberLoginTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());
	
	@BeforeTest
		public void initialize() throws IOException
		{
		 driver = initializeDriver();
		 log.info("Driver Initialized");
		 driver.get(prop.getProperty("EMEFuture2URL"));
		}
	
	@Test (priority = 10, description = "Login Active Adult HOH Member")
		public void activeMember1Login() throws InterruptedException
		{
		reusableMethods.activeMember1Login();
		DashboardPO d=new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember1_fullname"));
		Assert.assertEquals(d.getMyFamilyMemberCount().getText(),"2");
		reusableMethods.MemberLogout();
		}
	@Test (priority = 15, description = "Login Active Adult non-HOH Member")
		public void activeMember2Login() throws InterruptedException
		{
		reusableMethods.activeMember2Login();
		DashboardPO d=new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember2_fullname"));
		Assert.assertEquals(false, reusableMethods.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));//confirm My Family section is not shown
		reusableMethods.MemberLogout();
		}
	@Test (priority = 20, description = "Login Active Minor Member")
		public void activeMember3Login() throws InterruptedException
		{
		reusableMethods.activeMember3Login();
		DashboardPO d=new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember3_fullname"));
		Assert.assertEquals(false, reusableMethods.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));//confirm My Family section is not shown
		reusableMethods.MemberLogout();
		}
	@Test (priority = 25, description = "Login Active Adult No Family Member")
		public void activeMember4Login() throws InterruptedException
		{
		reusableMethods.activeMember4Login();
		DashboardPO d=new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember4_fullname"));
		Assert.assertEquals(d.getMyFamilyMemberCount().getText(),"0");
		reusableMethods.MemberLogout();
		}
	@Test (priority = 25, description = "Login Inactive Adult")
		public void activeMember5Login() throws InterruptedException
		{
		reusableMethods.activeMember5Login();
		DashboardPO d=new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember5_fullname"));
		Assert.assertEquals(d.getMyFamilyMemberCount().getText(),"0");
		reusableMethods.MemberLogout();
		}

	@AfterTest
		public void teardown() throws InterruptedException
		{
		driver.close();
		driver=null;
		}

}
