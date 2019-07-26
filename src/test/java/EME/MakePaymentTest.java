package EME;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import junit.framework.Assert;
import pageObjects.PaymentPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentPO;
import resources.base;
import resources.reusableMethods;


public class MakePaymentTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());

	@BeforeTest
		public void initialize() throws IOException
		{
			 driver = initializeDriver();
			 log.info("Driver Initialized");
			 driver.get(prop.getProperty("EMEFuture2URL"));
		}
		
	@Test (priority = 1, description = "Adding $1.00 to member's account")
		public void MakePayment() throws InterruptedException
		{	
		reusableMethods.activeMember1Login();
			DashboardPO d = new DashboardPO(driver);
			PaymentPO p = new PaymentPO(driver);
		d.getMyAccountPayNow().click();
			Thread.sleep(3000);
		p.getAmountRadioButton3().click();
			Actions a= new Actions(driver);
		a.moveToElement(p.getAmountRadioButton3()).sendKeys(Keys.TAB).sendKeys(Keys.DELETE).build().perform();
		p.getCustomAmountInput().sendKeys("1.00");
		p.getCardNumber().sendKeys(prop.getProperty("MastercardNumber"));
		p.getExpireMonth().sendKeys(prop.getProperty("MastercardExpireMonth"));
		p.getExpireYear().sendKeys(prop.getProperty("MastercardExpireYear"));
		p.getCVC().sendKeys(prop.getProperty("MastercardCVC"));
		p.getSaveCardNoRadio().click();
		Thread.sleep(2000);
//		p.getIAgreeCheckbox().click();//might not be shown if getSaveCardNoRadio is used
			Thread.sleep(2000);
		p.getSubmitButton().click();
			Thread.sleep(2000);
		p.getPopupConfirmationButton().click();
			Thread.sleep(2000);
		p.getPopupConfirmationButton().click();
		Thread.sleep(2000);
		reusableMethods.returnToDashboard();
		}
	@Test (priority = 2, description = "Confirming Last Payment date is updated after payment is made")
	public void ConfirmLastPaymentDateUpdated() throws InterruptedException
	{	
		DashboardPO d = new DashboardPO(driver);
		DateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy");
		Date date = new Date();
		String DateTime= dateFormat.format(date);
		Thread.sleep(3000);
		Assert.assertEquals("Last Payment: "+DateTime, d.getMyAccountLastPaymentDate().getText());
		
	}


	@AfterTest
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
}
