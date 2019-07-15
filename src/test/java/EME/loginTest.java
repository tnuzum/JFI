package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.LoginPagePO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;


public class loginTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());
@BeforeTest
	public void initialize() throws IOException
	{
		 driver = initializeDriver();
		 log.info("Driver Initialized");
		 driver.get(prop.getProperty("EMEFuture2URL"));
	}
	
	@Test (priority = 1)
	public void pageVerifications() throws IOException
	{	
		Assert.assertEquals(driver.getTitle(), "Log In | Empower M.E.");
		log.info("Page Title Verified");
		}
	@Test (priority = 2) // Used to verify page elements are displayed as expected
	public void pageValidations() throws IOException
	{	
		LoginPagePO l=new LoginPagePO(driver);
		l.getsigninButton().click();
		log.info("Log In Button Clicked");
		Assert.assertEquals(l.getusernameRequiredMessage().getText(), prop.getProperty("usernameRequiredMessage"));
		log.info("Username Required Message Confirmed");
		Assert.assertEquals(l.getpasswordRequiredMessage().getText(), prop.getProperty("passwordRequiredMessage"));
		log.info("Password Required Message Confirmed");
		Assert.assertEquals(l.getForgotUsername().getText(), "FORGOT USERNAME?");
		log.info("Forgot Username link text Confirmed");
		Assert.assertEquals(l.getForgotPassword().getText(), "FORGOT PASSWORD?");
		log.info("Forgot Password link text Confirmed");
		}
	@Test (priority = 10)
	public void activeUserLogin() throws InterruptedException
	{
		reusableMethods.activeUserLogin();
		Thread.sleep(7000);
		Assert.assertEquals(driver.getTitle(), "Dashboard");// Confirming Dashboard page is shown
		log.info("Dashboard Title Verified");
		DashboardPO d=new DashboardPO(driver);
		Assert.assertEquals(d.getMemberName().getText(), prop.getProperty("activeMember1_fullname"));// Confirming correct member is logged in
		log.info("Member Name Verified");
		
	}
	@Test (priority = 11, dependsOnMethods = "activeUserLogin")
	public void activeUserLogout() throws InterruptedException
	{
		DashboardPO d=new DashboardPO(driver);
		d.getLogoutButton().click();
		log.info("User Logged Out");
	}
	@Test (priority = 12)
	public void invalidUserLogin() throws InterruptedException
	{
		LoginPagePO l=new LoginPagePO(driver);
		l.getuserEmail().sendKeys(prop.getProperty("invalid_username"));
		log.info("User Name Entered");
		l.getuserPassword().sendKeys(prop.getProperty("invalid_password"));
		log.info("Password Entered");
		l.getsigninButton().click();
		log.info("Log In Button Clicked");
		Thread.sleep(5000);
		Assert.assertEquals(l.getcredentialsErrorMessage().getText(), prop.getProperty("wrongCredentialsMessage"));
		log.info("Error Message Title Verified");
	}
	@AfterTest
		public void teardown() throws InterruptedException
		{
		Thread.sleep(5000);
		driver.close();
		driver=null;
		}
	
	
	
	

}
