package EME;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
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
	DashboardPO d=new DashboardPO(driver);
	@BeforeTest
	public void initialize() throws IOException, InterruptedException
	{
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
		reusableMethods.activeMember1Login();
	}
	
	@Test (priority = 20)
	public void MyPackagesButtonTest() throws InterruptedException
	{
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
		d.getCartButton().click();
		CartPO c = new CartPO(driver);
		Assert.assertEquals(c.getPageHeader().getText(),"Shopping Cart");
		log.info("Shopping Cart Page Header Verified");
		reusableMethods.returnToDashboard();
	}
	@Test (priority = 30)
	public void AcctHistoryButtonTest() throws InterruptedException
	{
		d.getMyAccountAccountHistory().click();
		AcctHistoryPO a = new AcctHistoryPO(driver);
		Assert.assertEquals(a.getPageHeader().getText(),"Account History");
		log.info("Account History Page Header Verified");
		reusableMethods.returnToDashboard();
	}
	@Test (priority = 35)
	public void PayNowButtonTest() throws InterruptedException
	{
		d.getMyAccountPayNow().click();
		PaymentPO pb = new PaymentPO(driver);
		Assert.assertEquals(pb.getPageHeader().getText(),"Pay Balance");
		log.info("Pay Balance Page Header Verified");
		reusableMethods.returnToDashboard();
	}
	@Test (priority = 40)
	public void ScheduleClassesButtonTest() throws InterruptedException
	{
		d.getMyClassesScheduleButton().click();//Accessing from Dashboard
		ClassSignUpPO cs = new ClassSignUpPO(driver);
		Assert.assertEquals(cs.getPageHeader().getText(),"Select Classes");
		log.info("Select Classes Page Header Verified");
		d.getDashboardButton().click();
		d.getMenuMyActivies().click();//Accessing from left pane menu
		d.getMenuClassSignup().click();
		Assert.assertEquals(cs.getPageHeader().getText(),"Select Classes");
		log.info("Manage Profile Page Header Verified");
		reusableMethods.returnToDashboard();
	}
	@Test (priority = 45)
	public void ScheduleApptsButtonTest() throws InterruptedException
	{
		d.getMyApptsScheduleButton().click();//Accessing from Dashboard
			reusableMethods.catchErrorMessage();
		AppointmentsPO a = new AppointmentsPO(driver);
		Assert.assertEquals(a.getPageHeader().getText(),"Appointments");
		log.info("Appointments Page Header Verified");
		d.getDashboardButton().click();
		d.getMenuMyActivies().click();//Accessing from left pane menu
		d.getMenuBookAppointment().click();
		Assert.assertEquals(a.getPageHeader().getText(),"Appointments");
		log.info("Appointments Page Header Verified");
		reusableMethods.returnToDashboard();
	}
	@Test (priority = 50)
	public void ManageFamilyButtonTest() throws InterruptedException
	{
		d.getMyFamilyManageButton().click();
		ManageFamilyPO a = new ManageFamilyPO(driver);
		Assert.assertEquals(a.getPageHeader().getText(),"Manage Family");
		log.info("Manage Family Page Header Verified");
		reusableMethods.returnToDashboard();
	}
	@Test (priority = 55)
	public void EditMyInfoButtonTest() throws InterruptedException
	{
		d.getMyInfoEditButton().click();
		ManageProfilePO a = new ManageProfilePO(driver);
		Assert.assertEquals(a.getPageHeader().getText(),"Manage Profile");
		log.info("Manage Profile Page Header Verified");
		reusableMethods.returnToDashboard();
	}
		@Test (priority = 60)
	public void ForgotUsernameButtonTest() throws InterruptedException
	{
		d.getLogoutButton().click();
		Thread.sleep(2000);
		LoginPO l = new LoginPO(driver);
		l.getForgotUsername().click();

		ForgotUsernamePO fu = new ForgotUsernamePO(driver);
		WebElement w = fu.getPageHeader();
		while (!w.isDisplayed())
		{
		Thread.sleep(200);
		}
		Assert.assertEquals(fu.getPageHeader().getText(),"Forgot your Username?");
		log.info("Forgot Username Page Header Verified");
		fu.getCancelButton().click();
	}
	@Test (priority = 65)
	public void ForgotPasswordButtonTest() throws InterruptedException
	{
		LoginPO l = new LoginPO(driver);
		l.getForgotPassword().click();
		ForgotPasswordPO fp = new ForgotPasswordPO(driver);
		WebElement w = fp.getPageHeader();
		while (!w.isDisplayed())
		{
		Thread.sleep(200);
		}
		Assert.assertEquals(fp.getPageHeader().getText(),"Forgot your Password?");
		log.info("Forgot Password Page Header Verified");
		fp.getCancelButton().click();
	}
	@AfterTest
		public void teardown() throws InterruptedException
		{
		driver.close();
		driver=null;
		}

}
