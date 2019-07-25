package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class base {
	public static WebDriver driver;
	public static Properties prop;
	public static Logger log =LogManager.getLogger(base.class.getName());
	public WebDriver initializeDriver() throws IOException {

		prop = new Properties();
		FileInputStream fis=new FileInputStream("C:\\Users\\tnuzum\\eclipse-workspace\\JonasFitness\\src\\main\\java\\resources\\properties");

		prop.load(fis);
		String browserName = prop.getProperty("browser");
		String testEnvironment = prop.getProperty("test_environment");

		if (testEnvironment.equals("grid")){
			if (browserName.equals("Chrome")) {
				log.info("Chrome Browser: Running Tests on Selenium Grid");
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setBrowserName("chrome");
				dc.setPlatform(Platform.WINDOWS);
				System.setProperty("webdriver.chrome.driver", "C:\\Users\\tnuzum\\Automation\\libs\\webdrivers\\chromedriver.exe");
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);}
			if (browserName.equals("Firefox")) {
				log.info("Firefox Browser: Running Tests on Selenium Grid");
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setBrowserName("firefox");
				dc.setPlatform(Platform.WINDOWS);
				System.setProperty("webdriver.gecko.driver","C:\\Users\\tnuzum\\Automation\\libs\\webdrivers\\geckodriver.exe");
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);}
			if (browserName.equals("Edge")) {
				log.info("Edge Browser: Running Tests on Selenium Grid");
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setBrowserName("MicrosoftEdge");
				dc.setPlatform(Platform.WINDOWS);
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);}
			if (browserName.equals("ie")) {
				log.info("IE Browser: Running Tests on Selenium Grid");
				DesiredCapabilities dc =
				DesiredCapabilities.internetExplorer();
				dc.setCapability("ignoreZoomSetting", true);
//				dc.setBrowserName("internetexplorer");
				dc.setPlatform(Platform.WINDOWS);
				System.setProperty("webdriver.ie.driver","C:\\Users\\tnuzum\\Automation\\libs\\webdrivers\\IEDriverServer.exe");
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);}
		}
		else {
		if (testEnvironment.equals("local")){
				if (browserName.equals("Chrome")) {
					log.info("Chrome Browser: Running Tests on local machine");
					System.setProperty("webdriver.chrome.driver", "C:\\Users\\tnuzum\\Automation\\libs\\webdrivers\\chromedriver.exe");
					driver = new ChromeDriver();}
				if (browserName.equals("Firefox")) {
					log.info("Firefox Browser: Running Tests on local machine");
					System.setProperty("webdriver.gecko.driver","C:\\Users\\tnuzum\\Automation\\libs\\webdrivers\\geckodriver.exe");
					driver = new FirefoxDriver(); }
				if (browserName.equals("Edge")) {
					log.info("Edge Browser: Running Tests on local machine");
					driver = new EdgeDriver(); }
				if (browserName.equals("ie")) {
					log.info("IE Browser: Running Tests on local machine");
					DesiredCapabilities dc =
					DesiredCapabilities.internetExplorer();
					dc.setCapability("ignoreZoomSetting", true);
					System.setProperty("webdriver.ie.driver","C:\\Users\\tnuzum\\Automation\\webdrivers\\IEDriverServer.exe");
					driver = new InternetExplorerDriver(dc); }
			}
		}
		
//		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy  HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String DateTime= dateFormat.format(date);
		System.out.println(DateTime+": Automated testing started");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		return driver;
	}

	public void getScreenshot(String result) throws IOException {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src,new File("C:\\Users\\tnuzum\\eclipse-workspace\\JonasFitness\\screenshots\\" + result + "screenshot.png"));
		FileUtils.copyFile(src,new File("C:\\screenshots\\screenshot.png"));
	}

}


