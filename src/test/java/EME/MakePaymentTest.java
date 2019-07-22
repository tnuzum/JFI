package EME;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.LoginPO;
import pageObjects.PaymentPO;
import pageObjects.DashboardPO;
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
		
	@Test (priority = 1)
		public void pageVerifications() throws InterruptedException
		{	
		reusableMethods.activeMember1Login();
			DashboardPO d = new DashboardPO(driver);
			PaymentPO p = new PaymentPO(driver);
		d.getMyAccountPayNow().click();
			Thread.sleep(2000);
		p.getAmountRadioButton3().click();
			Actions a= new Actions(driver);
		a.moveToElement(p.getAmountRadioButton3()).sendKeys(Keys.TAB).sendKeys(Keys.DELETE).build().perform();
		p.getCustomAmountInput().sendKeys("1.00");
		p.getCardNumber().sendKeys(prop.getProperty("MastercardNumber"));
		p.getExpireMonth().sendKeys(prop.getProperty("MastercardExpireMonth"));
		p.getExpireYear().sendKeys(prop.getProperty("MastercardExpireYear"));
		p.getCVC().sendKeys(prop.getProperty("MastercardCVC"));
		p.getSaveCardNoRadio().click();
		p.getIAgreeCheckbox().click();
		p.getSubmitButton().click();
			Thread.sleep(2000);
		p.getPopupConfirmationButton().click();
			Thread.sleep(2000);
		p.getPopupConfirmationButton().click();
		}

	@AfterTest
		public void teardown() throws InterruptedException
		{
			Thread.sleep(2000);
			driver.close();
			driver=null;
		}
}
