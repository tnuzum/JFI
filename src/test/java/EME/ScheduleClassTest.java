package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import junit.framework.Assert;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentPO;
import pageObjects.PaymentPO;
import pageObjects.ShoppingCartPO;
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
		Assert.assertEquals(c.getPopupClassDesc().getText(),"- CLASS DESCRIPTION -");
		c.getPopupSignUpButton().click();
			Thread.sleep(4000);
		c.getSelectRatesAddSelButton().click();
			Thread.sleep(2000);
		c.getConfirmationCheckout().click();
		Thread.sleep(2000);
			ShoppingCartPO s = new ShoppingCartPO(driver);
		s.getCheckout().click();
			Thread.sleep(2000);
			PaymentPO p = new PaymentPO(driver);
		
		/* Un-comment this section to use New Card	
			p.getNewCardButton().click();
			Thread.sleep(2000);
		p.getCardNumber().sendKeys(prop.getProperty("MastercardNumber"));
		p.getExpireMonth().sendKeys(prop.getProperty("MastercardExpireMonth"));
		p.getExpireYear().sendKeys(prop.getProperty("MastercardExpireYear"));
		p.getCVC().sendKeys(prop.getProperty("MastercardCVC"));
		p.getSaveCardNoRadio().click();
		Thread.sleep(2000);
//		p.getIAgreeCheckbox().click();//might not be shown if getSaveCardNoRadio is used
			Thread.sleep(2000);
		p.getSubmitButton().click();*/
			
		p.getSelectPaymentOnAccountButton().click();	
			Thread.sleep(2000);
		p.getSelectPaymentOnAccountPayWithButton().click();
			Thread.sleep(2000);
		p.getPopupConfirmationButton().click();
			Thread.sleep(2000);
		Assert.assertEquals("THANK YOU FOR YOUR ORDER", p.getConfirmPageThankYou().getText());
		Thread.sleep(2000);
		reusableMethods.returnToDashboard();
		Thread.sleep(5000);
		}
	@Test (priority = 2)
		public void unenrollFromClass() throws IOException, InterruptedException
		{	
		DashboardPO d = new DashboardPO(driver);
		d.getMyClassesClass1GearButton().click();
			Thread.sleep(2000);
		d.getmyClassesUnenrollButton1().click();
			Thread.sleep(2000);
		d.getmyClassesUnenrollButton2().click();
			Thread.sleep(2000);
		d.getMyClassesUnenrollConfirmYesButton().click();
		Thread.sleep(2000);
		Assert.assertEquals("Unenrolled", d.getMyClassesUnenrollConfirmMessage1().getText());
		d.getMyClassesUnenrollConfirmYesButton().click();//using this locator to click OK to Unenrolled message
		
		}

	@AfterTest
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
	
	
	
	

}
