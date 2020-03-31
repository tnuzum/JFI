package EME;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import resources.base;

public class ExampleTest2 extends base{
	
	//testing repository
	//more changes
	
	//soem change

//	public static void main(String[] args) throws InterruptedException
	@BeforeTest
	
	public void initialize() throws IOException, InterruptedException
	{
		
		driver = initializeDriver();
		driver.get(prop.getProperty("EMELoginPage"));
	}
	
	@Test (priority = 1)
	public void Test1() throws IOException, InterruptedException
	{

//		** Initial Driver Open Browser, Navigate to Web Site **
//		System.setProperty("webdriver.gecko.driver", "C:\\Users\\tnuzum\\webdrivers\\geckodriver.exe");
//		WebDriver driver = new FirefoxDriver();
//		driver.navigate().to("https://ourclublogin-future.test-jfisoftware.com:8910/account/login/101");
	
				

//		** Login Member ** 
		driver.findElement(By.id("Username")).sendKeys("rauto");
		driver.findElement(By.id("Password")).sendKeys("Testing1!");
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		driver.findElement(By.linkText("Logout"));
		
//		LoginPO l=new LoginPO(driver);
//		l.getuserName().sendKeys(prop.getProperty("activeMember1_username"));
//		l.getuserPassword().sendKeys(prop.getProperty("activeMember1_password"));
//		l.getLoginButton().click();
		
		
		System.out.println("Bhagya - testing the merge conflicts");

		System.out.println("todd - testing the merge conflicts");


		}
	
	@Test (priority = 2)
	public void Test2() throws IOException, InterruptedException
	{

//		** Initial Driver Open Browser, Navigate to Web Site **
//		System.setProperty("webdriver.gecko.driver", "C:\\Users\\tnuzum\\webdrivers\\geckodriver.exe");
//		WebDriver driver = new FirefoxDriver();
//		driver.navigate().to("https://ourclublogin-future.test-jfisoftware.com:8910/account/login/101");
		
//		driver = initializeDriver();
//		driver.get(prop.getProperty("EMELoginPage"));
				

//		** Login Member ** 
		driver.findElement(By.id("Username")).sendKeys("rauto");
		driver.findElement(By.id("Password")).sendKeys("Testing1!");
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		driver.findElement(By.linkText("Logout"));
		
//		LoginPO l=new LoginPO(driver);
//		l.getuserName().sendKeys(prop.getProperty("activeMember1_username"));
//		l.getuserPassword().sendKeys(prop.getProperty("activeMember1_password"));
//		l.getLoginButton().click();
		
		
		System.out.println("Bhagya - testing the merge conflicts");

		System.out.println("todd - testing the merge conflicts");


		}
	
	
	 @AfterClass
		public void teardown() throws InterruptedException {
			driver.close();
			driver = null;
		}

	}
