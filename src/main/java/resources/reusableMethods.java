package resources;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.DashboardPO;
import pageObjects.LoginPO;

import resources.base;


public class reusableMethods extends base{

	public static String activeMember1Login() throws InterruptedException
	{
		LoginPO l=new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember1_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember1_password"));
		l.getsigninButton().click();
		log.info("Active Member 1 Logged In");
		Thread.sleep(8000);
		return null;
	}
	public static String activeMember2Login() throws InterruptedException
	{
		LoginPO l=new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember2_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember2_password"));
		l.getsigninButton().click();
		log.info("Active Member 2 Logged In");
		Thread.sleep(8000);
		return null;
	}
	public static String activeMember3Login() throws InterruptedException
	{
		LoginPO l=new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember3_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember3_password"));
		l.getsigninButton().click();
		log.info("Active Member 3 Logged In");
		Thread.sleep(8000);
		return null;
	}
	public static String activeMember4Login() throws InterruptedException
	{
		LoginPO l=new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember4_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember4_password"));
		l.getsigninButton().click();
		Thread.sleep(8000);
		return null;
	}
	public static String activeMember5Login() throws InterruptedException
	{
		LoginPO l=new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember5_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember5_password"));
		l.getsigninButton().click();
		Thread.sleep(8000);
		return null;
	}
	
	public static String MemberLogout() throws InterruptedException
	{
		DashboardPO d=new DashboardPO(driver);
		d.getLogoutButton().click();
		Thread.sleep(2000);
		return null;
	}
	public static String returnToDashboard() throws InterruptedException
	{
		DashboardPO d=new DashboardPO(driver);
		d.getDashboardButton().click();
		WebDriverWait w = new WebDriverWait(driver, 10);
		w.until(ExpectedConditions.visibilityOf(d.getMyAccountTotChargesAmount()));
		return null;
	}
	public static boolean isElementPresent(By by){
	        try{
	            driver.findElement(by);
	            return true;
	        }
	        catch(NoSuchElementException e){
	            return false;
	        }
	}
	
	
	
	
	}
	
