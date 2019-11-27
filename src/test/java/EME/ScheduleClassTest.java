package EME;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import junit.framework.Assert;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentPO;
import pageObjects.ShoppingCartPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;


public class ScheduleClassTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());


//	@BeforeTest
	@BeforeClass
		public void initialize() throws IOException, InterruptedException
		{
			 driver = initializeDriver();
			 log.info("Driver Initialized");
			 driver.get(prop.getProperty("EMELoginPage"));
		}
		
	@Test (priority = 1)
		public void scheduleClass() throws IOException, InterruptedException
		{	
		reusableMethods.activeMember5Login();
			DashboardPO d = new DashboardPO(driver);
		
		 d.getMyClassesScheduleButton().click();
			Thread.sleep(4000);
			ClassSignUpPO c = new ClassSignUpPO(driver);
/* working on selecting specific class			
		c.getSelectCategory().click();
		Actions a = new Actions(driver);
		a.click(c.getSelectCategory().sendKeys("g", Keys.ENTER));
		*/	
	c.getSelectDateThisWeekButton().click();		
			Thread.sleep(2000);
		c.getfirstAvailClassNextDayButton().click();
/*		if (reusableMethods.catchErrorMessage())
		{
			System.out.println("An error has occurred");
			ErrorMessagesPO e = new ErrorMessagesPO(driver);
//			e.getOKButton().click();
			Actions a= new Actions(driver);
			  a.moveToElement(e.getOKButton()).click().build().perform();
			reusableWaits.dashboardLoaded();
			reusableMethods.returnToDashboard();
		}
		else{}*/
			
			Thread.sleep(2000);
		c.getPopupSignUpButton().click();
			WebElement n = c.getSelectRatesAddSelButton();
			while (!n.isEnabled())
			{
				Thread.sleep(1000);
				System.out.println("sleeping");
				n.getText();
			}
			Thread.sleep(3000);
		c.getSelectRatesAddSelButton().click();
			Thread.sleep(3000);
		WebElement ErrorMsg = c.getPopupErrorMessage();
		while (!ErrorMsg.isDisplayed())
			{
		c.getConfirmationCheckout().click();
		Thread.sleep(3000);
			ShoppingCartPO s = new ShoppingCartPO(driver);
		s.getCheckout().click();
			Thread.sleep(2000);
			PaymentPO p = new PaymentPO(driver);
			
		p.getSelectPaymentOnAccountButton().click();	
			Thread.sleep(3000);
		p.getSelectPaymentOnAccountPayWithButton().click();
			Thread.sleep(3000);
		p.getPopupConfirmationButton().click();
			Thread.sleep(3000);
		Assert.assertEquals("Thank You For Your Order", p.getConfirmPageThankYou().getText());
		Thread.sleep(3000);
		reusableMethods.returnToDashboard();
		}
		c.getPopupClose().click();
		Thread.sleep(3000);
		reusableMethods.returnToDashboard();
		}
	@Test (priority = 2, dependsOnMethods = {"ScheduleClassTest"})
		public void unenrollFromClass() throws IOException, InterruptedException
		{	
		DashboardPO d = new DashboardPO(driver);

			WebDriverWait wait = new WebDriverWait(driver, 10);
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//classescourses/div[1]/div[2]/div[1]/div[1]/a[1]/div[1]/div[3]/i[1]")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='class-table-container']")));
			while (!d.getMyClassesClass1GearButton().isDisplayed())
			{
				Thread.sleep(1000);
				System.out.println("Sleeping for 1 second");
			}
		d.getMyClassesClass1GearButton().click();
			Thread.sleep(2000);
		d.getmyClassesUnenrollButton().click();
			Thread.sleep(2000);
			UnenrollPO u = new UnenrollPO(driver);
				u.getUnenrollButton().click();
					Thread.sleep(2000);
				u.getUnenrollConfirmYesButton().click();
				Thread.sleep(2000);
				Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
				u.getUnenrollConfirmYesButton().click();
				}

//	@AfterTest
	@AfterClass
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
}
