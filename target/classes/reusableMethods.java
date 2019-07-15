package resources;

import pageObjects.LoginPagePO;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import pageObjects.DashboardPO;
import resources.base;


public class reusableMethods extends base{

	public static String activeUserLogin()
	{
		LoginPagePO l=new LoginPagePO(driver);
		l.getuserEmail().sendKeys(prop.getProperty("activeMember1_username"));
		log.info("User Name Entered");
		l.getuserPassword().sendKeys(prop.getProperty("activeMember1_password"));
		log.info("Password Entered");
		l.getsigninButton().click();
		log.info("Log In Button Clicked");
		return null;
	}
	
	
}
