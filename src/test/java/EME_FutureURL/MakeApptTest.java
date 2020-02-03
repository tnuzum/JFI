package EME_FutureURL;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.AppointmentsPO;
import pageObjects.CartPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;


public class MakeApptTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());

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
		reusableMethods.activeMemberLogin("scottauto", "Testing1!");
				DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
				AppointmentsPO ap = new AppointmentsPO(driver);
				WebElement bic = ap.getBookableItemCategory();
				/*Thread.sleep(2000);
				while (!bic.isEnabled())		
				{
					System.out.println("Waiting for Bookable Item Category to not be blank");
				}	*/
		 Select s = new Select(bic);
		 List<WebElement> ProductCategories = s.getOptions();
		 
		 int count = ProductCategories.size();
		 System.out.println(count);
		 
		 for (int i = 0; i<count; i++)
		 {
			 String category = ProductCategories.get(i).getText();
			 
			 if (category.equals("Personal Training"))
			 {
				 s.selectByVisibleText(category);
				 break;
			 }
		 }
//				   s.selectByVisibleText("Personal Training");
				
		 Select s1 = new Select(ap.getBookableItem());
		 Thread.sleep(2000);
	List<WebElement> Products = s1.getOptions();
		 
		 int count1 = Products.size();
		 System.out.println(count1);
		 
		 for (int j = 0; j<count1; j++)
		 {
			 String product = Products.get(j).getText();
			 
			 if (product.equals("PT 60 Mins"))
			 {
				 s1.selectByVisibleText(product);
				 break;
			 }
		 }
//			   s1.selectByVisibleText("PT 60 Mins");
				  
		 WebElement rt = ap.getResourceType();
				  
/*				  while (!rt.isEnabled())//while button is NOT(!) enabled
					{
//					Thread.sleep(200);
					}*/
		 Select s2 = new Select(rt);
		 Thread.sleep(2000);
	 List<WebElement> Resources = s2.getOptions();
		 
		 int count2 = Resources.size();
		 System.out.println(count2);
		 
		 for (int k = 0; k<count2; k++)
		 {
			 String resource = Resources.get(k).getText();
			 
			 if (resource.equals("PT.Smith, Andrew"))
			 {
				 s2.selectByVisibleText(resource);
				 break;
			 }
		 }
//				  s2.selectByVisibleText("PT Smith, Andrew");
				  
				  boolean result1 = reusableWaits.loadingAvailability();
					if (result1 == true)
					{
//						Thread.sleep(500);	
					}
					Boolean TomorrowDatePresent = reusableMethods.isElementPresent(By.xpath("//mwl-calendar-month-cell[contains(@class,'future')])[1]"));
					if (TomorrowDatePresent == false)
					{
						
						 driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();;

					   result1 = reusableWaits.loadingAvailability();
						if (result1 == true)
						{
//							Thread.sleep(500);	
						}
					}
						
		   ap.getCalendarTomorrow().click();
		   Thread.sleep(3000);
					 				  				  
					WebElement st1 = ap.getSelectTimeMorningButton();
					WebDriverWait wait = new WebDriverWait(driver, 30);
					wait.until(ExpectedConditions.elementToBeClickable(st1));
					while (!st1.isEnabled())//while button is NOT(!) enabled
					{
					System.out.println("Waiting for available times");
					}
			ap.getSelectTimeMorningButton().click();	
					WebElement st2 = ap.getSelectTime1stAvailable();
//					while (!st2.isEnabled())//while button is NOT(!) enabled
//					{
//					Thread.sleep(200);
//					}
					WebDriverWait wait1 = new WebDriverWait(driver, 30);
					wait1.until(ExpectedConditions.elementToBeClickable(st2));
			ap.getSelectTime1stAvailable().click();
						WebElement p1 = ap.getPopup1BookButton();
						while (!p1.isEnabled())//while button is NOT(!) enabled
						{
//						Thread.sleep(200); 
					}
					
					
			ap.getPopup1BookButton().click();
			Thread.sleep(2000);

//		ap.getPackageRequiredContinueButton().click();
//		Thread.sleep(2000);
//		CartPO co = new CartPO(driver);
//		co.getCheckoutButton().click();
//		reusableMethods.useNewCard();
//		WebDriverWait wait = new WebDriverWait(driver, 10);
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'swal2-success')]")));
			
			Assert.assertEquals(ap.getPopup2Title().getText(),"Booked!");
			ap.getPopup2OKButton().click();
				    
		}	
	
	@Test (priority = 2)
	public void ConfirmAppointmentIsScheduled() throws IOException, InterruptedException
	{	
		//reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//appointmentswidget/div/div[2]/div[1]/div/div/a/div/div[2]/span/strong")));
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
//			boolean result2 = reusableWaits.popupMessageYesButton();
//			if (result2 == true)
//			{
//				Thread.sleep(500);	
//			}
//		a.getEditApptCanceledOKButton().click();
//		reusableWaits.waitForDashboardLoaded();
		Thread.sleep(2000);
		Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
		reusableMethods.memberLogout();
		}

//	@AfterTest
	@AfterClass
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
	
	
	
	

}
