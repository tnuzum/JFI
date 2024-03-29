package EME_EnvURL;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.LoginPO;
import resources.base;

public class loginPageTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

//@BeforeTest
	@BeforeClass
	@Parameters({ "EMELoginPage" })
	public void initialize(String EMELoginPage) throws InterruptedException, IOException {
//	public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		// String EMELoginPage = prop.getProperty("EMELoginPage");
		driver.get(EMELoginPage);
	}

	@Test(priority = 1)
	public void pageTitle() throws IOException {
		// Assert.assertEquals(driver.getTitle(), "Log In | Empower M.E.");
		Assert.assertEquals(driver.getTitle(), "Log In | Empower M.E.");// pagetitle Changed by seema
		log.info("Page Title Verified");
	}

	@Test(priority = 2)
	public void pageTextLabels() throws IOException {
		LoginPO l = new LoginPO(driver);
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
		Assert.assertEquals(l.getRememberUsernameLabel().getText().trim(), "Remember Username");// commented by seema //
																								// uncommented and
																								// changed the text by
																								// Bhagya
		log.info("Remember Username label text Confirmed");
		Assert.assertEquals(l.getLoginButton().getText(), " Login");// Changed by seema
		log.info("Login button label text Confirmed");
		Assert.assertTrue(l.getLoginButton().isEnabled()); // commented temporary by
		// seema // changed to True by Bhagya after Scott's changes on 5/4/2021
		log.info("Login button isEnabled Confirmed");// commented temporary by seema
	}

	@Test(priority = 3, enabled = true)
	public void noUserMessages() throws IOException {
		LoginPO l = new LoginPO(driver);
		l.getLoginButton().click();
		log.info("Log In Button Clicked");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("li")));
		Assert.assertEquals(l.getcredentialsErrorMessage().getText().trim(),
				prop.getProperty("InvalidCredentialsMessage")); // Updated by Bhagya
		log.info("Invalid Credentials Message Confirmed");
//		Assert.assertEquals(l.getpasswordRequiredMessage().getText(), prop.getProperty("passwordRequiredMessage")); //commented by Bhagya
//		log.info("Password Required Message Confirmed"); //commented by Bhagya
	}

	@Test(priority = 4, enabled = true)
	public void wrongCredentialsMessages() throws InterruptedException {
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("invalid_username"));
		log.info("User Name Entered");
		l.getuserPassword().sendKeys(prop.getProperty("invalid_password"));
		log.info("Password Entered");
		l.getLoginButton().click();
		log.info("Log In Button Clicked");
//		WebDriverWait wait = new WebDriverWait(driver, 10);
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='loginForm']/form/div[1]/ul/li")));
		WebElement wait2 = l.getcredentialsErrorMessage();
		while (wait2.getText().isBlank()) {
			System.out.println("INFO: Waiting 500ms for element to populate");
			Thread.sleep(500);
			wait2.getText();
		}

		Assert.assertEquals(l.getcredentialsErrorMessage().getText(), prop.getProperty("wrongCredentialsMessage"));
		log.info("Error Message Title Verified");
	}

//	@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}
}