package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.AppointmentsPO;
import pageObjects.CartPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.ForgotPasswordPO;
import pageObjects.ForgotUsernamePO;
import pageObjects.LoginPO;
import pageObjects.ManageFamilyPO;
import pageObjects.ManageProfilePO;
import pageObjects.PackagesPO;
import pageObjects.PaymentPO;
import resources.base;
import resources.reusableMethods;


public class PageLaunchTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());

	@BeforeTest
	public void initialize() throws IOException, InterruptedException
	{
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMEFuture2URL"));
		reusableMethods.activeMember1Login();
	}
	
	@Test (priority = 20)
	public void MyPackagesButtonTest() throws InterruptedException
	{
		DashboardPO d=new DashboardPO(driver);
		d.getMyPackagesButton().click();
		d.getMyPackagesShopPackages().click();
		PackagesPO p = new PackagesPO(driver);
		Assert.assertEquals(p.getPageHeader().getText(),"Shop Packages");
		log.info("Shop Packages Page Header Verified");
		reusableMethods.returnToDashboard();
	}
	@Test (priority = 25)
	public void CartButtonTest() throws InterruptedException
	{
		DashboardPO d=new DashboardPO(driver);
		d.getCartButton().click();
		CartPO c = new CartPO(driver);
		Assert.assertEquals(c.getPageHeader().getText(),"Shopping Cart");
		log.info("Shopping Cart Page Header Verified");
		reusableMethods.returnToDashboard();
	}
	@Test (priority = 30)
	public void AcctHistoryButtonTest() throws InterruptedException
	{
		DashboardPO d=new DashboardPO(driver);
		d.getMyAccountAccountHistory().click();
		AcctHistoryPO a = new AcctHistoryPO(driver);
		Assert.assertEquals(a.getPageHeader().getText(),"Account History");
		log.info("Account History Page Header Verified");
		reusableMethods.returnToDashboard();
	}
	@Test (priority = 35)
	public void PayNowButtonTest() throws InterruptedException
	{
		DashboardPO d=new DashboardPO(driver);
		d.getMyAccountPayNow().click();
		PaymentPO pb = new PaymentPO(driver);
		Assert.assertEquals(pb.getPageHeader().getText(),"Pay Balance");
		log.info("Pay Balance Page Header Verified");
		reusableMethods.returnToDashboard();
	}
	@Test (priority = 40)
	public void ScheduleClassesButtonTest() throws InterruptedException
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
		reusableMethods.returnToDashboard();
	}
	@Test (priority = 45)
	public void ScheduleApptsButtonTest() throws InterruptedException
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
		reusableMethods.returnToDashboard();
	}
	@Test (priority = 50)
	public void ManageFamilyButtonTest() throws InterruptedException
	{
		DashboardPO d=new DashboardPO(driver);
		d.getMyFamilyManageButton().click();
		ManageFamilyPO a = new ManageFamilyPO(driver);
		Assert.assertEquals(a.getPageHeader().getText(),"Manage Family");
		log.info("Manage Family Page Header Verified");
		reusableMethods.returnToDashboard();
	}
	@Test (priority = 55)
	public void EditMyInfoButtonTest() throws InterruptedException
	{
		DashboardPO d=new DashboardPO(driver);
		d.getMyInfoEditButton().click();
		ManageProfilePO a = new ManageProfilePO(driver);
		Assert.assertEquals(a.getPageHeader().getText(),"Manage Profile");
		log.info("Manage Profile Page Header Verified");
		reusableMethods.returnToDashboard();
	}
		@Test (priority = 60)
	public void ForgotUsernameButtonTest() throws InterruptedException
	{
		DashboardPO d=new DashboardPO(driver);
		d.getLogoutButton().click();
		Thread.sleep(2000);
		LoginPO l = new LoginPO(driver);
		l.getForgotUsername().click();
		Thread.sleep(2000);
		ForgotUsernamePO f = new ForgotUsernamePO(driver);
		Assert.assertEquals(f.getPageHeader().getText(),"Forgot your Username?");
		log.info("Forgot Username Page Header Verified");
		f.getCancelButton().click();
	}
	@Test (priority = 65)
	public void ForgotPasswordButtonTest() throws InterruptedException
	{
		LoginPO l = new LoginPO(driver);
		l.getForgotPassword().click();
		Thread.sleep(2000);
		ForgotPasswordPO f = new ForgotPasswordPO(driver);
		Assert.assertEquals(f.getPageHeader().getText(),"Forgot your Password?");
		log.info("Forgot Password Page Header Verified");
		f.getCancelButton().click();
	}

	
	@AfterTest
		public void teardown() throws InterruptedException
		{
		Thread.sleep(2000);
		driver.close();
		driver=null;
		}

}
