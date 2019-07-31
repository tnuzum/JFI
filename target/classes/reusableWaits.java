package resources;

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

		public static String dashboardMemberName() throws InterruptedException
	{
		DashboardPO d = new DashboardPO(driver);
		/*WebElement wait1 = d.getMyInfoMemberName();
		while (wait1.getText().isBlank())
		{
			Thread.sleep(500);
			wait1.getText();
		}
		WebElement wait2 = d.getMyAccountTotChargesAmount();
		while (wait2.getText().isBlank())
		{
			Thread.sleep(500);
			wait2.getText();
		}*/
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/h2")));
		return null;
		

	}
		public static String loginLoginButton() throws InterruptedException
	{
			LoginPO l = new LoginPO(driver);
			WebElement n = l.getLoginButton();
			while (!n.isEnabled())//while button is NOT(!) enabled
			{
				Thread.sleep(1000);
				System.out.println("waiting for login page to load");
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
	
