package EME;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.LoginPO;
import resources.base;
import resources.reusableMethods;

public class loginPageTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	public reusableMethods rm;

	public loginPageTest() {

		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {

		try {

			driver = initializeDriver();
		}

		catch (java.lang.NullPointerException npe) {

			driver = initializeDriver();

			System.out.println("driver initialized again");
			log.error("driver initialized again");
			npe.printStackTrace();
			log.error(npe.getMessage(), npe);

		}

		catch (org.openqa.selenium.WebDriverException we) {

			driver = initializeDriver();

			System.out.println("driver initialized again");
			log.error("driver initialized again");
			we.printStackTrace();
			log.error(we.getMessage(), we);

		}

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		rm.setDriver(driver);
		getEMEURL();
	}

	@Test(priority = 1)
	public void pageTitle() throws IOException {
		// Assert.assertEquals(driver.getTitle(), "Log In | Empower M.E.");
		Assert.assertEquals(driver.getTitle(), "EMEWeb");// pagetitle Changed by seema
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
		// Assert.assertEquals(l.getRememberUsernameLabel().getText(), "REMEMBER
		// USERNAME");//commented by seema
		log.info("Remember Username label text Confirmed");
		Assert.assertEquals(l.getLoginButton().getText(), " Login");// Changed by seema
		log.info("Login button label text Confirmed");
		Assert.assertFalse(l.getLoginButton().isEnabled()); // commented temporary by
		// seema
		// log.info("Login button isEnabled Confirmed");//commented temporary by seema
	}

	@Test(priority = 3, enabled = false)
	public void noUserMessages() throws IOException {
		LoginPO l = new LoginPO(driver);
		l.getLoginButton().click();
		log.info("Log In Button Clicked");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='Username-error']")));
		Assert.assertEquals(l.getusernameRequiredMessage().getText(), prop.getProperty("usernameRequiredMessage"));
		log.info("Username Required Message Confirmed");
		Assert.assertEquals(l.getpasswordRequiredMessage().getText(), prop.getProperty("passwordRequiredMessage"));
		log.info("Password Required Message Confirmed");
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
		WebDriverWait wait = new WebDriverWait(driver, 10);
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

	/*
	 * @AfterSuite
	 * 
	 * public void deleteTempFolderFiles() {
	 * System.out.println("after suite action"); rm.deleteTempFolderFiles();
	 * System.out.println("files deleted"); }
	 */
}
