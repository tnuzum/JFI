package resources;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.DashboardPO;
import pageObjects.LoginPO;

import resources.base;


public class reusableMethods extends base{

	public static String activeMemberLogin()
	{
		LoginPO l=new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember1_username"));
		log.info("User Name Entered");
		l.getuserPassword().sendKeys(prop.getProperty("activeMember1_password"));
		log.info("Password Entered");
		l.getsigninButton().click();
		log.info("Log In Button Clicked");
		DashboardPO d=new DashboardPO(driver);
		WebDriverWait a = new WebDriverWait(driver, 10);
		a.until(ExpectedConditions.visibilityOf(d.getPageHeader()));
		return null;
	}
	
	public static String returnToDashboard() throws InterruptedException
	{
		DashboardPO d=new DashboardPO(driver);
		d.getDashboardButton().click();
		WebDriverWait a = new WebDriverWait(driver, 10);
		a.until(ExpectedConditions.visibilityOf(d.getPageHeader()));
		Thread.sleep(2000);
		return null;
	}
	
}
