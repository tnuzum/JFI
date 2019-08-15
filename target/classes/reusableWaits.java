package resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.AppointmentsPO;
import pageObjects.DashboardPO;
import pageObjects.LoginPO;
import resources.base;


public class reusableWaits extends base{
	
	static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	static Date date = new Date();
	static String DateTime= dateFormat.format(date);
	

		public static String dashboardLoaded() throws InterruptedException
	{
// Check 1: wait for member name element
		WebDriverWait wait1 = new WebDriverWait(driver, 10);
		wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/h2")));
		System.out.println(DateTime+" INFO: Element is now present");
// Check 2: wait for member name element to not be blank	
		DashboardPO d = new DashboardPO(driver);
		WebElement wait2 = d.getMyInfoMemberName();
		while (wait2.getText().isBlank())
		{
			System.out.println(DateTime+" INFO: Waiting 500ms for element to populate");
			Thread.sleep(500);
			wait2.getText();
		}
// Check 3: wait for Total Charges element
		WebDriverWait wait3 = new WebDriverWait(driver, 10);
		wait3.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/h2")));
		System.out.println(DateTime+" INFO: Element is now present");
// Check 4: wait for Total Charges element to not be blank
		WebElement wait4 = d.getMyAccountBalance();
		while (wait4.getText().isBlank())
		{
			System.out.println(DateTime+" INFO: Waiting 500ms for element to populate");
			Thread.sleep(500);
			wait4.getText();
		}
		return null;
		
	}
		public static String loginLoginButton() throws InterruptedException
	{
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[type='submit']")));
			System.out.println(DateTime+" INFO: Element is now present");
			LoginPO l = new LoginPO(driver);
			WebElement n = l.getLoginButton();
			while (!n.isEnabled())//while button is NOT(!) enabled
			{
				Thread.sleep(500);
				System.out.println(DateTime+" INFO: waiting 500ms for element to be enabled");
			}
		return null;
	}
		public static boolean loadingAvailability()
	{
			try {
				AppointmentsPO a = new AppointmentsPO(driver);
				a.getloadingAvailabilityMessage();
				return true;
			}
	        catch(NoSuchElementException e){
	            return false;
	        }	
	}	
		public static boolean popupMessageYesButton()
	{
			try {
				AppointmentsPO a = new AppointmentsPO(driver);
				a.getEditApptCancelYesButton();
				return true;
			}
	        catch(NoSuchElementException e){
	            return false;
	        }	
	}

}
	
