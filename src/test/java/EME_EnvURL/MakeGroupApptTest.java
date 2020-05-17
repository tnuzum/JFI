package EME_EnvURL;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.AppointmentsPO;
import pageObjects.DashboardPO;
import resources.Base;
import resources.reusableMethods;
import resources.reusableWaits;


public class MakeGroupApptTest extends Base{
private static Logger log =LogManager.getLogger(Base.class.getName());

//	@BeforeTest
    @BeforeClass
    @Parameters({"EMELoginPage"})
	public void initialize(String EMELoginPage) throws InterruptedException, IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(EMELoginPage);
		}
		
	@Test (priority = 1)
		public void ScheduleAppointment() throws IOException, InterruptedException
		{	
		reusableMethods.activeMember1Login();
				DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
				AppointmentsPO ap = new AppointmentsPO(driver);
				while (ap.getBookableItemCategory().getText().isEmpty())		
				{
					System.out.println("Waiting for Bookable Item Category to not be blank");
				}	
		ap.getBookableItemCategory().sendKeys("g",Keys.ENTER);
				WebElement bi = ap.getBookableItem();
				while (!bi.isEnabled())//while button is NOT(!) enabled
				{
//				Thread.sleep(200);
				}
		ap.getBookableItem().sendKeys("g",Keys.ENTER);
				WebElement rt = ap.getResourceType();
				while (!rt.isEnabled())//while button is NOT(!) enabled
				{
//				Thread.sleep(200);
				}
				Assert.assertEquals(ap.getGroupApptsHeader().getText(), "Group Appointments");
				Assert.assertEquals(ap.getGroupMinPersons().getText(), "1");
				Assert.assertEquals(ap.getGroupMaxPersons().getText(), "2");
				ap.getGroupMemberSearchInput().sendKeys("auto");
				Thread.sleep(8000);
//				Actions a= new Actions(driver);
//				a.moveToElement(ap.getGroupMemberSearchButton()).click();
				ap.getGroupMemberSearchButton().click();
					Thread.sleep(4000);
				ap.getGroupPopupAddButton1().click();
					WebElement ct = ap.getCalendarTomorrow();
					while (!ct.isDisplayed())//while button is NOT(!) displayed
					{
						System.out.println("Waiting for calendar day buttons");
					}
					Thread.sleep(4000);
				ap.getCalendarTomorrow().click();
					WebElement st1 = ap.getSelectTimeMorningButton();
					while (!st1.isDisplayed())//while button is NOT(!) displayed
					{
						System.out.println("Waiting for available times");
					}
				ap.getSelectTimeMorningButton().click();		
					WebElement st2 = ap.getSelectTime1stAvailable();
					while (!st2.isEnabled())//while button is NOT(!) enabled
					{
					Thread.sleep(500);
					}
				ap.getSelectTime1stAvailable().click();
					Thread.sleep(4000);
				ap.getAddlResourcesBookButton().click();
		Thread.sleep(4000);		
		
		WebElement p1 = ap.getPopup1BookButton();
				while (!p1.isEnabled())//while button is NOT(!) enabled
				{
//				Thread.sleep(200);
				}
				
		ap.getPopup1BookButton().click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'swal2-success')]")));
		Thread.sleep(1000);
		Assert.assertEquals(ap.getPopup2Title().getText(),"Booked!");
		ap.getPopup2OKButton().click();
		}
	@Test (priority = 2)
	public void ConfirmAppointmentIsScheduled() throws IOException, InterruptedException
	{	
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//appointmentswidget/div/div[2]/div[1]/div/a/div/div[2]/span/strong")));
		Assert.assertFalse(d.getMyApptsAppt1Title().getText().isBlank());
	}
	
	@Test (priority = 3)
	public void CancelAppointment() throws IOException, InterruptedException
	{	
			DashboardPO d = new DashboardPO(driver);
		d.getMyApptsAppt1GearButton().click();
			WebElement wait1 = d.getMyApptsEditButton();
			while (!wait1.isEnabled())//while button is NOT(!) enabled
			{
//			Thread.sleep(200);
			}
		d.getMyApptsEditButton().click();
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
			AppointmentsPO a = new AppointmentsPO(driver);
			Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
		a.getEditApptCancelButton().click();
			Thread.sleep(1000);
			WebElement wait2 = a.getEditApptProceedButton();
			while (!wait2.isEnabled())//while button is NOT(!) enabled
			{
//			Thread.sleep(200);
			}
		a.getEditApptProceedButton().click();
			boolean result1 = reusableWaits.popupMessageYesButton();
			if (result1 == true)
			{
//				Thread.sleep(500);	
			}
		a.getEditApptCancelYesButton().click();
			boolean result2 = reusableWaits.popupMessageYesButton();
			if (result2 == true)
			{
//				Thread.sleep(500);	
			}
		a.getEditApptCanceledOKButton().click();
		reusableWaits.waitForDashboardLoaded();
		Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
		}

//	@AfterTest
	@AfterClass
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
	
	
	
	

}
