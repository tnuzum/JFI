package EME;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.DashboardPO;
import pageObjects.PaymentPO;
import resources.base;
import resources.reusableMethods;

public class LocatorTestingOnly extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

// This class is used to test locators only.
// Only used to output the object to the console, or send click/sendkeys commands.
// This class is not executed with Test Suite (not contained in testng.xml)

	@BeforeTest
	public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));

	}

	@Test(priority = 1)
	public void locatorTestingOnly() throws IOException, InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		PaymentPO p = new PaymentPO(driver);
		reusableMethods.activeMember1Login();
		d.getMyAccountPayNow().click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//enterpaymentamount/div/div/div/div/div/h2")));
		p.getAmountRadioButton3().click();
		Thread.sleep(2000);
		int variable = 1;
		while(variable<13)
		{
			p.getCustomAmountInput().sendKeys(Keys.BACK_SPACE);
			variable++;
		}
			p.getCustomAmountInput().sendKeys("1.00");
			Thread.sleep(2000);
//			driver.findElement(By.xpath("//*[text()='PAY WITH THIS METHOD']")).click();
		p.getPayWithThisMethodButton1().click();
		
	}

	@AfterTest
	public void teardown() throws InterruptedException {
//		Thread.sleep(10000);
//		driver.close();
//		driver = null;
	}

}
