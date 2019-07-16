package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.LoginPagePO;
import resources.base;


public class loginPageTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());
@BeforeTest
	public void initialize() throws IOException
	{
		 driver = initializeDriver();
		 log.info("Driver Initialized");
		 driver.get(prop.getProperty("EMEFuture2URL"));
	}
	
	@Test (priority = 1)
	public void pageTitle() throws IOException
	{	
		Assert.assertEquals(driver.getTitle(), "Log In | Empower M.E.");
		log.info("Page Title Verified");
		}
	
	@Test (priority = 2)
	public void pageTextLabels() throws IOException
	{	
		LoginPagePO l=new LoginPagePO(driver);
		Assert.assertEquals(l.getuserNameLabel().getText(), "Username");
		log.info("Username input label text Confirmed");
		Assert.assertEquals(l.getuserPasswordLabel().getText(), "Password");
		log.info("Username input label text Confirmed");
		Assert.assertEquals(l.getForgotUsername().getText(), "FORGOT USERNAME?");
		log.info("Forgot Username link text Confirmed");
		Assert.assertEquals(l.getForgotPassword().getText(), "FORGOT PASSWORD?");
		log.info("Forgot Password link text Confirmed");
		Assert.assertFalse(l.getRememberUsernameCheckbox().isSelected());// confirm check box is not selected
		log.info("Remember Username Checkbox Unchecked Confirmed");
		Assert.assertEquals(l.getRememberUsernameLabel().getText(), "REMEMBER USERNAME");
		log.info("Remember Username label text Confirmed");
		Assert.assertEquals(l.getsigninButton().getText(), "Login");
		log.info("Login button label text Confirmed");
		Assert.assertTrue(l.getsigninButton().isEnabled());
		log.info("Login button isEnabled Confirmed");
		}
	
	
	@Test (priority = 3)
	public void noUserMessages() throws IOException
	{	
		LoginPagePO l=new LoginPagePO(driver);
		l.getsigninButton().click();
		log.info("Log In Button Clicked");
		Assert.assertEquals(l.getusernameRequiredMessage().getText(), prop.getProperty("usernameRequiredMessage"));
		log.info("Username Required Message Confirmed");
		Assert.assertEquals(l.getpasswordRequiredMessage().getText(), prop.getProperty("passwordRequiredMessage"));
		log.info("Password Required Message Confirmed");
		}
	
	@Test (priority = 4)
	public void wrongCredentialsMessages() throws InterruptedException
	{
		LoginPagePO l=new LoginPagePO(driver);
		l.getuserName().sendKeys(prop.getProperty("invalid_username"));
		log.info("User Name Entered");
		l.getuserPassword().sendKeys(prop.getProperty("invalid_password"));
		log.info("Password Entered");
		l.getsigninButton().click();
		log.info("Log In Button Clicked");
		Assert.assertEquals(l.getcredentialsErrorMessage().getText(), prop.getProperty("wrongCredentialsMessage"));
		log.info("Error Message Title Verified");
	}
	@AfterTest
		public void teardown() throws InterruptedException
		{
		Thread.sleep(2000);
		driver.close();
		driver=null;
		}
	
	
	
	

}
