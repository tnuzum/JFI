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

import pageObjects.AcctHistoryPO;
import pageObjects.AppointmentsPO;
import pageObjects.CartPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.ForgotUsernamePO;
import pageObjects.LoginPO;
import pageObjects.ManageFamilyPO;
import pageObjects.ManageProfilePO;
import pageObjects.PackagesPO;
import pageObjects.PayBalancePO;
import resources.base;
import resources.reusableMethods;


public class ButtonTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());
@BeforeTest
	public void initialize() throws IOException, InterruptedException
	{
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMEFuture2URL"));
		reusableMethods.activeMemberLogin();
		Thread.sleep(10000);
	}
	
	@Test (priority = 10)
	public void ForgotUsernameButtonTest()
	{
		LoginPO l = new LoginPO(driver);
		l.getForgotPassword().click();
		ForgotUsernamePO f = new ForgotUsernamePO(driver);
		Assert.assertEquals(f.getPageHeader().getText(),"Forgot Your Username?");
		
		System.out.println(driver.findElement(By.xpath("//div[@id='loginForm']/form/h2")).getText());
		
		DashboardPO d=new DashboardPO(driver);
		d.getMyPackagesButton().click();
		d.getMyPackagesShopPackages().click();
		PackagesPO p = new PackagesPO(driver);
		Assert.assertEquals(p.getPageHeader().getText(),"Shop Packages");
		log.info("Shop Packages Page Header Verified");
		d.getDashboardButton().click();
}

	@Test (priority = 20)
	public void myPackagesButtonTest()
	{
		DashboardPO d=new DashboardPO(driver);
		d.getMyPackagesButton().click();
		d.getMyPackagesShopPackages().click();
		PackagesPO p = new PackagesPO(driver);
		Assert.assertEquals(p.getPageHeader().getText(),"Shop Packages");
		log.info("Shop Packages Page Header Verified");
		d.getDashboardButton().click();
	}
	@Test (priority = 25)
	public void CartButtonTest()
	{
		DashboardPO d=new DashboardPO(driver);
		d.getCartButton().click();
		CartPO c = new CartPO(driver);
		Assert.assertEquals(c.getPageHeader().getText(),"Shopping Cart");
		log.info("Shopping Cart Page Header Verified");
		d.getDashboardButton().click();
	}
	@Test (priority = 30)
	public void AcctHistoryButtonTest()
	{
		DashboardPO d=new DashboardPO(driver);
		d.getMyAccountAccountHistory().click();
		AcctHistoryPO a = new AcctHistoryPO(driver);
		Assert.assertEquals(a.getPageHeader().getText(),"Account History");
		log.info("Account History Page Header Verified");
		d.getDashboardButton().click();
	}
	@Test (priority = 35)
	public void PayNowButtonTest()
	{
		DashboardPO d=new DashboardPO(driver);
		d.getMyAccountPayNow().click();
		PayBalancePO pb = new PayBalancePO(driver);
		Assert.assertEquals(pb.getPageHeader().getText(),"Pay Balance");
		log.info("Pay Balance Page Header Verified");
		d.getDashboardButton().click();
	}
	@Test (priority = 40)
	public void ScheduleClassesButtonTest()
	{
		DashboardPO d=new DashboardPO(driver);
		d.getMyClassesScheduleButton().click();//accessing from dashboard
		ClassSignUpPO cs = new ClassSignUpPO(driver);
		Assert.assertEquals(cs.getPageHeader().getText(),"Select Classes");
		log.info("Select Classes Page Header Verified");
		d.getDashboardButton().click();
		d.getMenuMyActivies().click();//accessing from left pane menu
		d.getMenuClassSignup().click();
		Assert.assertEquals(cs.getPageHeader().getText(),"Select Classes");
		log.info("Manage Profile Page Header Verified");
		d.getDashboardButton().click();
	}
	@Test (priority = 45)
	public void ScheduleApptsButtonTest()
	{
		DashboardPO d=new DashboardPO(driver);
		d.getMyApptsScheduleButton().click();//accessing from dashboard
		AppointmentsPO a = new AppointmentsPO(driver);
		Assert.assertEquals(a.getPageHeader().getText(),"Appointments");
		log.info("Appointments Page Header Verified");
		d.getDashboardButton().click();
		d.getMenuMyActivies().click();//accessing from left pane menu
		d.getMenuBookAppointment().click();
		Assert.assertEquals(a.getPageHeader().getText(),"Appointments");
		log.info("Appointments Page Header Verified");
		d.getDashboardButton().click();
	}
	@Test (priority = 50)
	public void ManageFamilyButtonTest()
	{
		DashboardPO d=new DashboardPO(driver);
		d.getmyFamilyManageButton().click();
		ManageFamilyPO a = new ManageFamilyPO(driver);
		Assert.assertEquals(a.getPageHeader().getText(),"Manage Family");
		log.info("Manage Family Page Header Verified");
		d.getDashboardButton().click();
	}
	@Test (priority = 55)
	public void EditMyInfoButtonTest()
	{
		DashboardPO d=new DashboardPO(driver);
		d.getMyInfoEditButton().click();
		ManageProfilePO a = new ManageProfilePO(driver);
		Assert.assertEquals(a.getPageHeader().getText(),"Manage Profile");
		log.info("Manage Profile Page Header Verified");
		d.getDashboardButton().click();
	}

	
	@AfterTest
		public void teardown() throws InterruptedException
		{
		Thread.sleep(5000);
		driver.close();
		driver=null;
		}

}
