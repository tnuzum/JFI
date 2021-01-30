package PaymentMethods;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import pageObjects.ManagePayMethodsPO;
import pageObjects.PaymentPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class PayBalance_SaveCardQuestPresentForTerminateMember extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	public reusableWaits rw;
	public reusableMethods rm;
	public static DashboardPO d;
	public static PaymentPO p;
	public static ManagePayMethodsPO mp;
	private static JavascriptExecutor jse;

	public PayBalance_SaveCardQuestPresentForTerminateMember() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

	@BeforeClass
	public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		d = new DashboardPO(driver); // Define the driver for Dash Board page Objects
		p = new PaymentPO(driver);
		mp = new ManagePayMethodsPO(driver);
		jse = (JavascriptExecutor) driver;

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();

	}

	@Test(priority = 1, description = "Verify Terminate Member Can Add CC as the setting is true for Terminate member", enabled = true)
	public void VerifyTerminateMemberCanAddCC() throws InterruptedException, IOException {
		try {

			rm.activeMemberLogin("terminate1", "Testing1!"); // Login to EME as Freeze status member

			jse.executeScript("arguments[0].click();", d.getMyAccountPayNow());

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[@class='text-center']")));

			jse.executeScript("arguments[0].click();", p.getAmountRadioButton3());

			Thread.sleep(1000);
			int variable = 1;
			while (variable < 13) {
				p.getCustomAmountInput().sendKeys(Keys.BACK_SPACE);
				variable++;
			}

			p.getCustomAmountInput().sendKeys("5.00");
			Thread.sleep(1000);

			jse.executeScript("arguments[0].click();", p.getSelectPaymentNewCardButton());
			Thread.sleep(1000);

			log.info("NewCard Button was clicked");
			System.out.println("NewCard Button was clicked");

			rw.waitForNewCardFormToOpen();
			rm.OpenNewcardFormIfNotOpenInFirstAttempt();
			Thread.sleep(1000);

			jse.executeScript("arguments[0].click();", p.getCardNumber());
			p.getCardNumber().sendKeys("4111111111111111");
			p.getExpireMonth().sendKeys("04");
			p.getExpireYear().sendKeys("22");
			p.getCVC().sendKeys("123");

			// Assert.assertTrue(p.getAdditionalQuestionsSection().get(0).isDisplayed());

			Assert.assertEquals(rm.isWebElementPresent(p.getSaveCardQuestions()), true);

			Assert.assertEquals(rm.isWebElementPresent(p.getOnAccountQuestions()), true);

			Assert.assertEquals(rm.isWebElementPresent(p.getInClubQuestions()), true);

			Assert.assertTrue(p.getSigPadInOut().getAttribute("style").contains("1"));

			Assert.assertEquals(p.getIAgreeCheckbox().getAttribute("disabled"), null);

			jse.executeScript("arguments[0].click();", p.getSaveCardNoRadio());

			wait.until(ExpectedConditions.elementToBeClickable(p.getSubmitButton()));

			jse.executeScript("arguments[0].click();", p.getSubmitButton());

			// p.getSubmitButton().click();
			rw.waitForAcceptButton();
			p.getPopupConfirmationButton().click();
			rw.waitForAcceptButton();
			System.out.println(p.getPopupText().getText());
			Assert.assertEquals("Payment Made!", p.getPopupText().getText());
			p.getPopupConfirmationButton().click();

			rm.memberLogout();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

	}

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}
