package JOL;


import java.io.IOException;

import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import resources.base;
//import org.openqa.selenium.support.ui.Select;

public class JOL extends base{

	
//	public static void main(String[] args) throws InterruptedException {
	 
		// TODO Auto-generated method stub
		/*
		System.setProperty("webdriver.ie.driver", "C:\\work\\MicrosoftWebDriver.exe");
		WebDriver driver=new InternetExplorerDriver();
		//driver.get("https://jol-web-future2.test-jfisoftware.com:8944/competejol/101");
		driver.get("https://jol-web-future2.test-jfisoftware.com:8944/CompeteJOL/101/1/searchpromo");
		*/
		
		@BeforeTest
		public void initialize() throws IOException
		{
			 driver = initializeDriver();
			 driver.get(prop.getProperty("JOLSearchPromo"));
		}
		@Test (priority = 1, description="...")
		public void pageVerifications() throws InterruptedException
		{
		//Select s= new Select(driver.findElement(By.id("ddlFilter")));
	//	s.selectByValue("Select City");
				//s.selectByIndex(3);
				//s.selectByVisibleText("Dublin, OH");
		
																										// Promo code
		
		//driver.findElement(By.id("txtPromoCode")).sendKeys("Jason");
		//driver.findElement(By.id("btnPromoSubmit")).click();
		//driver.findElement(By.id("AgeRestricted_ContinueButton_2569")).click();
	
																										//dropdown to view plans
	//	Select s= new Select(driver.findElement(By.id("ddlFamilyPlanType")));
		//s.selectByIndex(0);
		
		//Select b= new Select(driver.findElement(By.id("ddlClubAccessType")));
	//	b.selectByValue("1");
	
		//driver.findElement(By.xpath("//a[@href='/CompeteJOL/101/1/2556/1']")).click();
		//driver.findElement(By.cssSelector("a[href='/CompeteJOL/101/1']")).click();
	//	driver.findElement(By.xpath("//a[@href='/CompeteJOL/101/1/2556/1']")).click();
		
		driver.findElement(By.xpath("//a[@href='/CompeteJOL/101/1/2569/1']")).click();
		//driver.findElement(By.xpath("//*[@id*='LinkContinue']")).click();
		//driver.findElement(By.xpath("(//*[text()[contains(.,'Continue')]])[1]")).click();
		
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		
		// js.executeScript("window.scrollBy(0,1000)");
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//h1[contains(text(),'Continue')]")).click();
		//driver.findElement(By.xpath("//a[@href = '/CompeteJOL/101/Profile/6/2556/1']")).click(); 
		

		//driver.findElement(By.id("LinkContinue")).click();	
		//*[text()[contains(.,'Continue')]]
		
		
	}

}
