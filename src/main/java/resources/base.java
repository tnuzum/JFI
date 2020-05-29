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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class base {
	public WebDriver driver;
	public static Properties prop; // or, public static Properties prop = new Properties(); this was recommended to
									// resolve an NPE, but I didn't need it
	public static Logger log = LogManager.getLogger(base.class.getName());
	public static String DateTime = null;
	public static String tomorrowsDate = null;
	public static String dayAfter = null;
	public static String ssTime = null;

	String projectPath = System.getenv("EME_HOME");

	public WebDriver initializeDriver() throws IOException {

		DesiredCapabilities dcch = DesiredCapabilities.chrome();
		dcch.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		dcch.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		dcch.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		dcch.setCapability("chrome.switches", Arrays.asList("--incognito"));

		prop = new Properties();
//		FileInputStream fis=new FileInputStream(projectPath + "\\src\\main\\java\\resources\\properties");
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\resources\\properties");

		prop.load(fis);
		String browserName = prop.getProperty("browser");
		String testEnvironment = prop.getProperty("test_environment");

		if (testEnvironment.equals("grid")) {
			if (browserName.equals("Chrome")) {
				log.info("Chrome Browser: Running Tests on Selenium Grid");
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setBrowserName("chrome");
				dc.setPlatform(Platform.WINDOWS);
				System.setProperty("webdriver.chrome.driver", "c:\\WebDrivers\\chromedriver.exe");
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
			}
			if (browserName.equals("Firefox")) {
				log.info("Firefox Browser: Running Tests on Selenium Grid");
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setBrowserName("firefox");
				dc.setPlatform(Platform.WINDOWS);
				System.setProperty("webdriver.gecko.driver", "c:\\WebDrivers\\geckodriver.exe");
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
			}
			if (browserName.equals("Edge")) {
				log.info("Edge Browser: Running Tests on Selenium Grid");
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setBrowserName("MicrosoftEdge");
				dc.setPlatform(Platform.WINDOWS);
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
			}
			/*
			 * if (browserName.equals("IE")) {
			 * log.info("IE Browser: Running Tests on Selenium Grid"); DesiredCapabilities
			 * dc = DesiredCapabilities.internetExplorer();
			 * dc.setCapability("ignoreZoomSetting", true); //
			 * dc.setBrowserName("internetexplorer"); dc.setPlatform(Platform.WINDOWS); //
			 * System.setProperty("webdriver.ie.driver","c:\\WebDrivers\\IEDriverServer.exe"
			 * ); driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),
			 * dc);}
			 */
		} else {
			if (testEnvironment.equals("local")) {
				if (browserName.contains("Chrome")) {
					log.info("Chrome Browser: Running Tests on local machine");
					ChromeOptions co = new ChromeOptions();
					co.addArguments("--start-maximized");
					// co.addArguments("--window-size=1920, 1080");
					co.merge(dcch);
					System.setProperty("webdriver.chrome.driver", "C:\\Automation\\libs\\webdrivers\\chromedriver.exe");
					if (browserName.contains("headless")) {
						co.addArguments("--headless");
					}
					driver = new ChromeDriver(co);
				}
				if (browserName.equals("Firefox")) {
					log.info("Firefox Browser: Running Tests on local machine");
					System.setProperty("webdriver.gecko.driver", "C:\\Automation\\libs\\webdrivers\\geckodriver.exe");
					driver = new FirefoxDriver();
				}
				if (browserName.equals("Edge")) {
					log.info("Edge Browser: Running Tests on local machine");
					System.setProperty("webdriver.edge.driver", "C:\\Automation\\libs\\webdrivers\\msedgedriver.exe");
					driver = new EdgeDriver();
				}
				if (browserName.equals("IE")) {
					log.info("IE Browser: Running Tests on local machine");
					InternetExplorerOptions options = new InternetExplorerOptions();
					System.setProperty("webdriver.ie.driver",
							"C:\\Automation\\libs\\webdrivers\\MicrosoftWebDriver.exe");
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

	public String getScreenshot(String result, WebDriver driver) throws IOException {

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "\\screenshots\\" + result + ssTime
				+ "screenshot.png";
		FileUtils.copyFile(src, new File(destinationFile));
		return destinationFile;
	}

}
