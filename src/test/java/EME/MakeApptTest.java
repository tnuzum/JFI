package EME;
import java.io.IOException;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.AppointmentsPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;


public class MakeApptTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());

	@BeforeTest
		public void initialize() throws IOException
		{
			 driver = initializeDriver();
			 log.info("Driver Initialized");
			 driver.get(prop.getProperty("EMEFuture2URL"));
		}
		
	@Test (priority = 1)
		public void ScheduleAppointment() throws IOException, InterruptedException
		{	
		reusableMethods.activeMember1Login();
			DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
			AppointmentsPO ap = new AppointmentsPO(driver);
			Thread.sleep(2000);
		ap.getBookableItemCategory().sendKeys("g");
			Thread.sleep(2000);
		ap.getBookableItemCategory().sendKeys(Keys.ENTER);
			Thread.sleep(2000);
		ap.getBookableItem().sendKeys("d",Keys.ENTER);
			Thread.sleep(2000);
		ap.getResourceType().sendKeys("d",Keys.ENTER);
			Thread.sleep(2000);	
		ap.getMonthSelectForwardButton().click();
			Thread.sleep(5000);	
		ap.getCalendarMonday1stFullWeek().click();
			Thread.sleep(2000);	
		ap.getSelectTimeMorningButton().click();	
			Thread.sleep(2000);
		ap.getSelectTime1stAvailable().click();
			Thread.sleep(2000);
		ap.getPopup1BookButton().click();
			Thread.sleep(2000);
		Assert.assertEquals(ap.getPopup2Title().getText(),"Booked!");
		ap.getPopup2OKButton().click();
		}
	@Test (priority = 2)
	public void ConfirmAppointmentIsScheduled() throws IOException, InterruptedException
	{	
			DashboardPO p = new DashboardPO(driver);
			Thread.sleep(3000);
		Assert.assertEquals(p.getMyApptsAppt1Title().getText(), "DRIVING RANGE PACKAGE");
	}

	@AfterTest
		public void teardown() throws InterruptedException
		{
			Thread.sleep(1000);
			driver.close();
			driver=null;
		}
	
	
	
	

}
