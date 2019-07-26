package resources;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.AppointmentsPO;
import pageObjects.DashboardPO;
import pageObjects.LoginPO;
import resources.base;


public class reusableWaits extends base{

		public static String dashboardMemberName() throws InterruptedException
	{
		DashboardPO d = new DashboardPO(driver);
		WebElement n = d.getMyInfoMemberName();
		while (n.getText().isBlank())
		{
			Thread.sleep(500);
			n.getText();
		}
		return null;
	}
		public static String loginLoginButton() throws InterruptedException
	{
			LoginPO l = new LoginPO(driver);
			WebElement n = l.getLoginButton();
			while (!n.isEnabled())//while button is NOT(!) enabled
			{
				Thread.sleep(500);
			}
		return null;
	}
		public static boolean loadingAvailability() throws InterruptedException
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


}
	
