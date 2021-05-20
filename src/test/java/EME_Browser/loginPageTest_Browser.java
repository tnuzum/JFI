package EME_Browser;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.LoginPO;
import resources.base;
import resources.base2;
import resources.reusableMethods;

public class loginPageTest_Browser extends base2 {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String wrongCredentialsMsg = "WE APOLOGIZE... It seems the credentials you entered are different than what is in our system. Please try again, and if the problem persists, contact your club for additional help.";
	private static String invalid_username = "tpowers";
	private static String invalid_password = "Not@Password1";

	public reusableMethods rm;

	public loginPageTest_Browser() {
		rm = new reusableMethods();

	}

	@BeforeSuite
	public void dockerUp() {
		rm.runTerminalCommand("docker-compose up", "Registered a node");
		System.out.println("docker is up");
	}

	@AfterSuite
	public void dockerDown() throws InterruptedException {

		rm.runTerminalCommand("docker-compose down", "Removing selenium-hub");
		System.out.println("docker is down");
	}

	@BeforeClass
	@Parameters({ "Browser" })
	public void initialize(String Browser) throws IOException, InterruptedException {

		log.info(Browser + " Browser: Running Tests on Selenium Grid");
		System.out.println(Browser);

		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setBrowserName(Browser); // dc.setPlatform(Platform.WINDOWS);
		System.out.println("capabilities set");
		ChromeOptions chrome_options = new ChromeOptions();

		chrome_options.addArguments("--no-sandbox");
		chrome_options.addArguments("--headless");
		chrome_options.addArguments("--disable-dev-shm-usage");
		chrome_options.setExperimentalOption("useAutomationExtension", false);
		System.out.println("going to driver");
		try {
			if (Browser.equals("chrome")) {
				chrome_options.merge(dc);
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chrome_options);
			} else {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
			}

			System.out.println("driver initialized");
			log.error("driver initialized");

		} catch (java.lang.NullPointerException npe) {
			if (Browser.equals("chrome")) {
				chrome_options.merge(dc);
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chrome_options);
			} else {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
			}

			System.out.println("driver initialized again");
			log.error("driver initialized again");
			npe.printStackTrace();
			log.error(npe.getMessage(), npe);

		}

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println(Browser);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();

		driver.get("https://ourclublogin-future2.test-jfisoftware.com:8911/login/236");
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

	/*
	 * @AfterSuite
	 * 
	 * public void deleteTempFolderFiles() {
	 * System.out.println("after suite action"); rm.deleteTempFolderFiles();
	 * System.out.println("files deleted"); }
	 */
}
