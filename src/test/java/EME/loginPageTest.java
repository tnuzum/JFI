package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.LoginPO;
import resources.base;


public class LoginPageTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());
@BeforeTest
	public void initialize() throws IOException
	{
		 driver = initializeDriver();
		 log.info("Driver Initialized");
		 driver.get(prop.getProperty("URL"));
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
		LoginPO l=new LoginPO(driver);
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
		Assert.assertEquals(l.getLoginButton().getText(), "Login");
		log.info("Login button label text Confirmed");
		Assert.assertTrue(l.getLoginButton().isEnabled());
		log.info("Login button isEnabled Confirmed");
		}
	
	@Test (priority = 3)
	public void noUserMessages() throws IOException
	{	
		LoginPO l=new LoginPO(driver);
		l.getLoginButton().click();
			log.info("Log In Button Clicked");
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='Username-error']")));
		Assert.assertEquals(l.getusernameRequiredMessage().getText(), prop.getProperty("usernameRequiredMessage"));
			log.info("Username Required Message Confirmed");
		Assert.assertEquals(l.getpasswordRequiredMessage().getText(), prop.getProperty("passwordRequiredMessage"));
			log.info("Password Required Message Confirmed");
		}
	
	@Test (priority = 4)
	public void wrongCredentialsMessages() throws InterruptedException
	{
		LoginPO l=new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("invalid_username"));
			log.info("User Name Entered");
		l.getuserPassword().sendKeys(prop.getProperty("invalid_password"));
			log.info("Password Entered");
		l.getLoginButton().click();
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='loginForm']/form/div[1]/ul/li")));
			log.info("Log In Button Clicked");
		Assert.assertEquals(l.getcredentialsErrorMessage().getText(), prop.getProperty("wrongCredentialsMessage"));
			log.info("Error Message Title Verified");
	}
	@AfterTest
		public void teardown() throws InterruptedException
		{
		driver.close();
		driver=null;
		}
	
	
	
	

}
