package EME_EnvURL;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import pageObjects.PaymentPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class MakePaymentTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	public reusableWaits rw;
	public reusableMethods rm;

	public MakePaymentTest() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	@Parameters({ "EMELoginPage" })
	public void initialize(String EMELoginPage) throws InterruptedException, IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		driver.get(EMELoginPage);
	}

	@Test(priority = 1, description = "Adding $1.00 to member's account")
	public void MakePayment() throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		rm.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));
		DashboardPO d = new DashboardPO(driver);
		PaymentPO p = new PaymentPO(driver);
		jse.executeScript("arguments[0].click();", d.getMyAccountPayNow());
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[@class='text-center']")));

		jse.executeScript("arguments[0].click();", p.getAmountRadioButton3());
//  	Thread.sleep(5000);
//  	p.getAmountRadioButton3().click();

		Thread.sleep(500);
		int variable = 1;
		while (variable < 13) {
			p.getCustomAmountInput().sendKeys(Keys.BACK_SPACE);
			variable++;
		}
		p.getCustomAmountInput().sendKeys("1.00");
		Thread.sleep(300);

		p.getPayWithThisMethodButton1().click();

		rw.waitForAcceptButton();
		p.getPopupConfirmationButton().click();
		rw.waitForAcceptButton();
		System.out.println(p.getPopupText().getText());
		Assert.assertEquals("Payment Made!", p.getPopupText().getText());
		p.getPopupConfirmationButton().click();
//		rm.returnToDashboard();
		Thread.sleep(3000);
		d.getBreadcrumbDashboard().click();

	}

	@Test(priority = 2, description = "Confirming payment is applied", dependsOnMethods = { "MakePayment" })
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

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}
}
