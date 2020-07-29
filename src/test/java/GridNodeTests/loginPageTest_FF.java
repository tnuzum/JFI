package GridNodeTests;

import java.io.IOException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.LoginPO;
import resources.base;

public class loginPageTest_FF extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {

		log.info("Firefox Browser: Running Tests on Selenium Grid");
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setBrowserName("firefox");
		dc.setPlatform(Platform.WINDOWS);
		System.setProperty("webdriver.gecko.driver", "C:\\\\Automation\\\\libs\\\\webdrivers\\\\geckodriver.exe");

		try {
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
		} catch (java.lang.NullPointerException npe) {

			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);

			System.out.println("driver initialized again");
			log.error("driver initialized again");
			npe.printStackTrace();
			log.error(npe.getMessage(), npe);

		}

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1)
	public void pageTitle() throws IOException {
		Assert.assertEquals(driver.getTitle(), "Log In | Empower M.E.");
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
		Assert.assertEquals(l.getRememberUsernameLabel().getText(), "REMEMBER USERNAME");
		log.info("Remember Username label text Confirmed");
		Assert.assertEquals(l.getLoginButton().getText(), "Login");
		log.info("Login button label text Confirmed");
		Assert.assertTrue(l.getLoginButton().isEnabled());
		log.info("Login button isEnabled Confirmed");
	}

	@Test(priority = 3)
	public void noUserMessages() throws IOException {
		LoginPO l = new LoginPO(driver);
		l.getLoginButton().click();
		log.info("Log In Button Clicked");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='Username-error']")));
		Assert.assertEquals(l.getusernameRequiredMessage().getText(), prop.getProperty("usernameRequiredMessage"));
		log.info("Username Required Message Confirmed");
		Assert.assertEquals(l.getpasswordRequiredMessage().getText(), prop.getProperty("passwordRequiredMessage"));
		log.info("Password Required Message Confirmed");
	}

	@Test(priority = 4)
	public void wrongCredentialsMessages() throws InterruptedException {
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("invalid_username"));
		log.info("User Name Entered");
		l.getuserPassword().sendKeys(prop.getProperty("invalid_password"));
		log.info("Password Entered");
		l.getLoginButton().click();
		log.info("Log In Button Clicked");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='loginForm']/form/div[1]/ul/li")));
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
		driver.close();
		driver = null;
	}

}
