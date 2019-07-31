package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import junit.framework.Assert;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentPO;
import pageObjects.PaymentPO;
import pageObjects.ShoppingCartPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;


public class ScheduleClassTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());


	@BeforeTest
		public void initialize() throws IOException
		{
			 driver = initializeDriver();
			 log.info("Driver Initialized");
			 driver.get(prop.getProperty("EMEFuture2URL"));
		}
		
	@Test (priority = 1)
		public void scheduleClass() throws IOException, InterruptedException
		{	
		reusableMethods.activeMember1Login();
			DashboardPO d = new DashboardPO(driver);
		
		 d.getMyClassesScheduleButton().click();
			Thread.sleep(4000);
			ClassSignUpPO c = new ClassSignUpPO(driver);
		c.getSelectDateThisWeekButton().click();		
			Thread.sleep(2000);
		c.getfirstAvailClassNextDayButton().click();
			Thread.sleep(2000);
		c.getPopupSignUpButton().click();
			WebElement n = c.getSelectRatesAddSelButton();
			while (!n.isEnabled())
			{
				Thread.sleep(1000);
				System.out.println("sleeping");
				n.getText();
			}
			Thread.sleep(1000);
		c.getSelectRatesAddSelButton().click();
			Thread.sleep(2000);
		c.getConfirmationCheckout().click();
		Thread.sleep(2000);
			ShoppingCartPO s = new ShoppingCartPO(driver);
		s.getCheckout().click();
			Thread.sleep(2000);
			PaymentPO p = new PaymentPO(driver);
			
		p.getSelectPaymentOnAccountButton().click();	
			Thread.sleep(2000);
		p.getSelectPaymentOnAccountPayWithButton().click();
			Thread.sleep(2000);
		p.getPopupConfirmationButton().click();
			Thread.sleep(2000);
		Assert.assertEquals("THANK YOU FOR YOUR ORDER", p.getConfirmPageThankYou().getText());
		Thread.sleep(2000);
		reusableMethods.returnToDashboard();
		}
	@Test (priority = 2)
		public void unenrollFromClass() throws IOException, InterruptedException
		{	
		Thread.sleep(3000);
		DashboardPO d = new DashboardPO(driver);
		if (!d.getMyClassesClass1GearButton().isDisplayed())
				{
			Thread.sleep(1000);
			System.out.println("sleeping");
				}
		System.out.println("looking for gear button");
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

	@AfterTest
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
}
