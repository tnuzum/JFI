package EME;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class MakePaymentTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	@BeforeTest
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1, description = "Adding $1.00 to member's account")
	public void MakePayment() throws InterruptedException {
		reusableMethods.activeMember1Login();
		DashboardPO d = new DashboardPO(driver);
		PaymentPO p = new PaymentPO(driver);
		d.getMyAccountPayNow().click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//enterpaymentamount/div/div/div/div/div/h2")));
		p.getAmountRadioButton3().click();
		Thread.sleep(500);
		int variable = 1;
		while (variable < 13) {
			p.getCustomAmountInput().sendKeys(Keys.BACK_SPACE);
			variable++;
		}
		p.getCustomAmountInput().sendKeys("1.00");
		
		p.getPayWithThisMethodButton1().click();
		reusableWaits.waitForAcceptButton();
		p.getPopupConfirmationButton().click();
		reusableWaits.waitForAcceptButton();
		p.getPopupConfirmationButton().click();
		reusableMethods.returnToDashboard();
	}

	@Test(priority = 2, description = "Confirming payment is applied")
	public void ConfirmPaymentApplied() throws InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/small[1]")));
		while (d.getMyAccountLastPaymentDate().getText().equalsIgnoreCase("Last Payment:")) {
			Thread.sleep(500);
			System.out.println("Sleeping for 500ms");
		}
		DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
		Date date = new Date();
		String DateTime = dateFormat.format(date);
		Assert.assertEquals("Last Payment: " + DateTime, d.getMyAccountLastPaymentDate().getText());
	}

	@AfterTest
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
