package EME;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import resources.base;

public class ExampleTest2 extends base{

//	public static void main(String[] args) throws InterruptedException
	@Test
	public void initialize() throws IOException, InterruptedException
	{

//		** Initial Driver Open Browser, Navigate to Web Site **
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\tnuzum\\webdrivers\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.navigate().to("https://ourclublogin-future.test-jfisoftware.com:8910/account/login/101");
		
//		driver = initializeDriver();
//		driver.get(prop.getProperty("EMELoginPage"));
				

//		** Login Member ** 
		driver.findElement(By.id("Username")).sendKeys("rauto");
		driver.findElement(By.id("Password")).sendKeys("Testing1!");
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		
//		LoginPO l=new LoginPO(driver);
//		l.getuserName().sendKeys(prop.getProperty("activeMember1_username"));
//		l.getuserPassword().sendKeys(prop.getProperty("activeMember1_password"));
//		l.getLoginButton().click();
		
		
		Thread.sleep(10000);		
		driver.close();
		driver=null;		

		}

	}
