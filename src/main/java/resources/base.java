package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class base {
	public WebDriver driver;
	public static Properties prop; // or, public static Properties prop = new Properties(); this was recommended to
									// resolve an NPE, but I didn't need it
	public static Logger log = LogManager.getLogger(base.class.getName());
	public static String DateTime = null;
	public static String tomorrowsDate = null;
	public static String dayAfter = null;
	public static String TwodaysAfter = null;
	public static String ssTime = null;
	public static String COGLoginPage = null;
	public static String EMELoginPage = null;
	static String testRegion;

	String projectPath = System.getProperty("user.dir");

	public WebDriver initializeDriver() throws IOException {

		DesiredCapabilities dcch = DesiredCapabilities.chrome();
		dcch.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		dcch.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		dcch.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		dcch.setCapability("chrome.switches", Arrays.asList("--incognito"));

//		testRegion = "Future2";
		testRegion = System.getProperty("test_Region");

		prop = new Properties();

		try {

			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\resources\\properties" + testRegion);
			prop.load(fis);

		} catch (NullPointerException e) {
			System.out.println("Error: Properties file could not be loaded");
		}

//		String browserName = prop.getProperty("browser");
//		String executionEnvironment = prop.getProperty("execution_Environment");

		String browserName = System.getProperty("browser");
		String executionEnvironment = System.getProperty("execution_Environment");

		System.out.println(browserName);
		System.out.println(executionEnvironment);

		if (executionEnvironment.equals("grid")) {
			if (browserName.equals("Chrome")) {
				log.info("Chrome Browser: Running Tests on Selenium Grid");
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setBrowserName("chrome");
				dc.setPlatform(Platform.WINDOWS);
//				System.setProperty("webdriver.chrome.driver", "C:\\Automation\\libs\\chromedriver.exe");

				WebDriverManager.chromedriver().setup();

				System.out.println(WebDriverManager.chromedriver().getDownloadedDriverVersion());
				log.info(WebDriverManager.chromedriver().getDownloadedDriverVersion());

				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
			}
			if (browserName.equals("Firefox")) {
				log.info("Firefox Browser: Running Tests on Selenium Grid");
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setBrowserName("firefox");
				dc.setPlatform(Platform.WINDOWS);
				dc.acceptInsecureCerts();
				dc.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
				dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				FirefoxOptions fo = new FirefoxOptions();
				fo.merge(dc);
//				System.setProperty("webdriver.gecko.driver", "C:\\Automation\\libs\\geckodriver.exe");

				WebDriverManager.firefoxdriver().setup();

				System.out.println(WebDriverManager.firefoxdriver().getDownloadedDriverVersion());
				log.info(WebDriverManager.firefoxdriver().getDownloadedDriverVersion());

				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
			}
			if (browserName.equals("Edge")) {
				log.info("Edge Browser: Running Tests on Selenium Grid");
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setBrowserName("MicrosoftEdge");
				dc.setPlatform(Platform.WINDOWS);
//				System.setProperty("webdriver.edge.driver", "C:\\Automation\\libs\\MicrosoftWebDriver.exe");

				WebDriverManager.edgedriver().setup();

				System.out.println(WebDriverManager.edgedriver().getDownloadedDriverVersion());
				log.info(WebDriverManager.edgedriver().getDownloadedDriverVersion());

				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
			}

			if (browserName.equals("Safari")) {
				log.info("Safari Browser: Running Tests on Selenium Grid");
				MutableCapabilities sauceOptions = new MutableCapabilities();

				SafariOptions browserOptions = new SafariOptions();
				browserOptions.setCapability("platformName", "macOS 10.15");
				browserOptions.setCapability("browserVersion", "13.1");
				browserOptions.setCapability("sauce:options", sauceOptions);

				driver = new RemoteWebDriver(new URL(
						"https://JonasEME:f4c10b60-ae6c-4972-a0be-72d5ce4feb96@ondemand.us-west-1.saucelabs.com:443/wd/hub"),
						browserOptions);

				// driver = new RemoteWebDriver(new
				// URL("http://localhost:4444/wd/hub"),browserOptions);
			}

			if (browserName.equals("IE")) {
				log.info("IE Browser: Running Tests on Selenium Grid");
				DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
				dc.setCapability("ignoreZoomSetting", true); //
				dc.setBrowserName("internet explorer");
				dc.setPlatform(Platform.WINDOWS); //
//				System.setProperty("webdriver.ie.driver", "c:\\WebDrivers\\IEDriverServer.exe");

				WebDriverManager.iedriver().setup();

				System.out.println(WebDriverManager.iedriver().getDownloadedDriverVersion());
				log.info(WebDriverManager.iedriver().getDownloadedDriverVersion());

				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
			}

		} else {
			if (executionEnvironment.equals("local")) {

				System.out.println("projectPath = " + projectPath);

				if (browserName.contains("Chrome")) {
					log.info("Chrome Browser: Running Tests on local machine");
					ChromeOptions co = new ChromeOptions();
					co.addArguments("--start-maximized");
					co.addArguments("–no-sandbox");
					co.addArguments("–disable-dev-shm-usage");
					co.addArguments("--disable-gpu");

					// co.addArguments("--window-size=1920, 1080");
					co.merge(dcch);

//					co.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
//					co.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//					co.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
//					co.setCapability("chrome.switches", Arrays.asList("--incognito"));
//
//					System.setProperty("webdriver.chrome.driver",
//							projectPath + "\\src\\main\\java\\webDrivers\\chromedriver.exe");

					WebDriverManager.chromedriver().browserVersion("92").setup();

					System.out.println(WebDriverManager.chromedriver().getDownloadedDriverVersion());
					log.info(WebDriverManager.chromedriver().getDownloadedDriverVersion());

					if (browserName.contains("headless")) {
						co.addArguments("--headless");
					}
					driver = new ChromeDriver(co);
				}
				if (browserName.equals("Firefox")) {
					log.info("Firefox Browser: Running Tests on local machine");
					DesiredCapabilities dc = new DesiredCapabilities();
					dc.setBrowserName("firefox");
					dc.setPlatform(Platform.WINDOWS);
					dc.acceptInsecureCerts();
					dc.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
					dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					FirefoxOptions fo = new FirefoxOptions();
					fo.merge(dc);
//					System.setProperty("webdriver.gecko.driver",
//							projectPath + "\\src\\main\\java\\webDrivers\\geckodriver.exe");

					WebDriverManager.firefoxdriver().setup();

					System.out.println(WebDriverManager.firefoxdriver().getDownloadedDriverVersion());
					log.info(WebDriverManager.firefoxdriver().getDownloadedDriverVersion());

					driver = new FirefoxDriver(fo);
				}
				if (browserName.equals("Edge")) {

					log.info("Edge Browser: Running Tests on local machine");
//					System.setProperty("webdriver.edge.driver",
//							projectPath + "\\src\\main\\java\\webDrivers\\msedgedriver.exe");

					WebDriverManager.edgedriver().setup();

					System.out.println(WebDriverManager.edgedriver().getDownloadedDriverVersion());
					log.info(WebDriverManager.edgedriver().getDownloadedDriverVersion());

					driver = new EdgeDriver();
				}
				if (browserName.equals("IE")) {
					log.info("IE Browser: Running Tests on local machine");
					InternetExplorerOptions options = new InternetExplorerOptions();
//					System.setProperty("webdriver.ie.driver",
//							projectPath + "\\src\\main\\java\\webdrivers\\IEDriverServer.exe");

					WebDriverManager.iedriver().setup();

					System.out.println(WebDriverManager.iedriver().getDownloadedDriverVersion());
					log.info(WebDriverManager.iedriver().getDownloadedDriverVersion());

					options.setCapability("ignoreZoomSetting", true);
					driver = new InternetExplorerDriver(options);
				}
			}
		}

		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		tomorrowsDate = dateFormat1.format(today1.getTime());

		Calendar today2 = Calendar.getInstance();
		today2.add(Calendar.DAY_OF_YEAR, 2);
		dayAfter = dateFormat1.format(today2.getTime());

		Calendar today3 = Calendar.getInstance();
		today3.add(Calendar.DAY_OF_YEAR, 3);
		TwodaysAfter = dateFormat1.format(today3.getTime());

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");// or, DateFormat dateFormat = new
																			// SimpleDateFormat(" HH:mm:ss");
		Date date = new Date();
		DateTime = dateFormat.format(date);
		System.out.println(DateTime + " INFO: WebDriver Initialized");

		DateFormat dateFormat2 = new SimpleDateFormat("MMddyyyy-HHmmss");
		Date date2 = new Date();
		ssTime = dateFormat2.format(date2);

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();

		// Dimension dimension = new Dimension(1920, 1080);
		// driver.manage().window().setSize(dimension);

		return driver;
	}

	public void getEMEURL() {

		String EMELoginPage = prop.getProperty("EMELoginPage");
//		String EMELoginPage = System.getProperty("EMELoginPage");
		System.out.println(EMELoginPage);

		driver.get(EMELoginPage);
	}

	public void getCOGURL() {

		String COGLoginPage = prop.getProperty("COGLoginPage");
//		String COGLoginPage = System.getProperty("COGLoginPage");
		System.out.println(COGLoginPage);

		driver.get(COGLoginPage);
	}

	public String getScreenshot(String result, WebDriver driver) throws IOException {

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "\\screenshots\\" + result + ssTime
				+ "screenshot.png";
		FileUtils.copyFile(src, new File(destinationFile));
		return destinationFile;

	}

}
