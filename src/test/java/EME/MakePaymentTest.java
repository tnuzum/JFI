package EME;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import junit.framework.Assert;
import pageObjects.DashboardPO;
import pageObjects.PaymentPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;


public class MakePaymentTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());

	@BeforeTest
		public void initialize() throws IOException, InterruptedException
		{
			 driver = initializeDriver();
			 log.info("Driver Initialized");
			 driver.get(prop.getProperty("EMELoginPage"));
		}
		
	@Test (priority = 1, description = "Adding $1.00 to member's account")
		public void MakePayment() throws InterruptedException
		{	
		reusableMethods.activeMember1Login();
			DashboardPO d = new DashboardPO(driver);
			PaymentPO p = new PaymentPO(driver);
		d.getMyAccountPayNow().click();
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//enterpaymentamount/div/div/div/div/div/h2")));
		p.getPaymentAmountInput().clear();
		p.getPaymentAmountInput().sendKeys("1.00");
		Thread.sleep(500);
		String amount = p.getPaymentAmountInput().getAttribute("ng-reflect-model");
		int amountInt = Integer.parseInt(amount);
		System.out.println(amountInt);
		if (amountInt == 01.00)
		{
			Thread.sleep(500);
			p.getPayWithThisMethodButton1().click();
		}
		else
		{
			System.out.println("Amount not $1.00, not click pay button");
			reusableMethods.returnToDashboard();
			Assert.assertFalse(true);//fails test if this condition is found
		}
		// THIS CODE IS USED FOR ENTERING NEW CREDIT CARD
		/*p.getAmountRadioButton3().click();
			p.getPaymentAmountInput().clear();
			if (!p.getCustomAmountInput().getText().isBlank())
			{
				Thread.sleep(500);
				p.getPaymentAmountInput().clear();
				System.out.println("Info: Waiting 500ms; Amount field is not blank");
			}
		p.getCustomAmountInput().sendKeys("1.00");
			Thread.sleep(500);
		p.getCardNumber().sendKeys(prop.getProperty("MastercardNumber"));
			Thread.sleep(500);
		p.getExpireMonth().sendKeys(prop.getProperty("MastercardExpireMonth"));
			Thread.sleep(500);
		p.getExpireYear().sendKeys(prop.getProperty("MastercardExpireYear"));
			Thread.sleep(500);
		p.getCVC().sendKeys(prop.getProperty("MastercardCVC"));
			Thread.sleep(500);
		p.getSaveCardNoRadio().click();
//		p.getIAgreeCheckbox().click();//might not be shown if getSaveCardNoRadio is used
			reusableWaits.waitForPaymentSubmitButton();
		p.getSubmitButton().click();*/
			
			reusableWaits.waitForAcceptButton();
		p.getPopupConfirmationButton().click();
			reusableWaits.waitForAcceptButton();
		p.getPopupConfirmationButton().click();
			Thread.sleep(2000);
		reusableMethods.returnToDashboard();
		}
	
	@Test (priority = 2, description = "Confirming payment is applied")
	public void ConfirmPaymentApplied() throws InterruptedException
	{	
		DashboardPO d = new DashboardPO(driver);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/small[1]")));
			while (d.getMyAccountLastPaymentDate().getText().equalsIgnoreCase("Last Payment:"))
			{
				Thread.sleep(500);
				System.out.println("Sleeping for 500ms");
			}
		DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
		Date date = new Date();
		String DateTime= dateFormat.format(date);
		Assert.assertEquals("Last Payment: "+DateTime, d.getMyAccountLastPaymentDate().getText());
	}

	@AfterTest
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
}
