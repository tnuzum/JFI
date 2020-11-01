package ManagePaymentMethods;

import java.io.IOException;
import java.time.Duration;

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

public class ManagepaymntMethods_AdditionalQuestionsTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	public reusableWaits rw;
	public reusableMethods rm;
	public static DashboardPO d;
	public static PaymentPO p;
	public static ManagePayMethodsPO mp;

	public ManagepaymntMethods_AdditionalQuestionsTest() {
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

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
		rm.activeMemberLogin("paymember", "Testing1!"); // Login to EME
		rw.waitForDashboardLoaded();

	}

	@Test(priority = 1, description = "Verify Additional Questions on Add Card")
	public void verifyAdditionalQuestionsOnAddCard() throws InterruptedException, IOException {
		try {
			DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects
			PaymentPO p = new PaymentPO(driver);
			rm.openSideMenuIfNotOpenedAlready();

			d.getMenuMyAccount().click();
			while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
				Thread.sleep(1000);
			}

			d.getMenuManagePmntMethods().click();
			Thread.sleep(3000);

			Assert.assertTrue(p.getAdditionalQuestionsSection().get(1).isDisplayed());
			Assert.assertTrue(p.getOnAccountCardQuestion().isDisplayed());
			p.getMoreInfoOnAccount().get(1).click();
			Thread.sleep(1000);
			Assert.assertEquals(p.getAdditionalQuestionPopupTitle().getText(), "On Account Charges");
			p.getAdditionalQuestionPopupClose().click();
			Thread.sleep(1000);
			Assert.assertTrue(p.getInClubQuestion().isDisplayed());
			p.getMoreInfoUseInPos().click();
			Thread.sleep(1000);
			Assert.assertEquals(p.getAdditionalQuestionPopupTitle().getText(), "Card On File");
			p.getAdditionalQuestionPopupClose().click();
			Thread.sleep(1000);

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

	@Test(priority = 2, description = "Verify Additional Questions on Edit Card")
	public void verifyAdditionalQuestionsOnEditCard() throws InterruptedException, IOException {
		try {

			int storedCardCount = p.getStoredCards().size();

			for (int i = 0; i < storedCardCount; i++) {
				if (p.getStoredCards().get(i).getText().contains("5454")) {
					p.getStoredCards().get(i).findElement(By.tagName("a")).click();
					break;
				}

			}

			Assert.assertTrue(p.getAdditionalQuestionsSection().get(0).isDisplayed());
			Assert.assertTrue(p.getOnAccountCardQuestion().isDisplayed());
			p.getMoreInfoOnAccount().get(0).click();
			Thread.sleep(1000);
			Assert.assertEquals(p.getAdditionalQuestionPopupTitle().getText(), "On Account Charges");
			p.getAdditionalQuestionPopupClose().click();
			Thread.sleep(1000);
			Assert.assertTrue(p.getInClubQuestion().isDisplayed());
			p.getMoreInfoUseInPos().click();
			Thread.sleep(1000);
			Assert.assertEquals(p.getAdditionalQuestionPopupTitle().getText(), "Card On File");
			p.getAdditionalQuestionPopupClose().click();
			Thread.sleep(1000);
			mp.getManagePMBreadcrumb().click();

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

	@Test(priority = 3, description = "Verify Additional Questions on Add ACH")
	public void verifyAdditionalQuestionOnAddACH() throws InterruptedException, IOException {
		try {

			mp.getBankAccountLink().click();
			Thread.sleep(1000);
			Assert.assertTrue(p.getAdditionalQuestionsSection().get(0).isDisplayed());
			Assert.assertTrue(p.getOnAccountBankQuestion().isDisplayed());
			p.getMoreInfoOnAccount().get(0).click();
			Thread.sleep(1000);
			Assert.assertEquals(p.getAdditionalQuestionPopupTitle().getText(), "On Account Charges");
			p.getAdditionalQuestionPopupClose().click();
			Thread.sleep(1000);

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

	@Test(priority = 4, description = "Verify Additional Questions on Edit ACH")
	public void verifyAdditionalQuestionOnEditACH() throws InterruptedException, IOException {
		try {

			int storedCardCount = p.getStoredCards().size();

			for (int i = 0; i < storedCardCount; i++) {
				if (p.getStoredCards().get(i).getText().contains("6789")) {
					p.getStoredCards().get(i).findElement(By.tagName("a")).click();
					break;
				}

			}

			Assert.assertTrue(p.getAdditionalQuestionsSection().get(0).isDisplayed());
			Assert.assertTrue(p.getOnAccountBankQuestion().isDisplayed());
			p.getMoreInfoOnAccount().get(0).click();
			Thread.sleep(1000);
			Assert.assertEquals(p.getAdditionalQuestionPopupTitle().getText(), "On Account Charges");
			p.getAdditionalQuestionPopupClose().click();
			Thread.sleep(1000);
			rm.returnToDashboard();

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

	@Test(priority = 5, description = "Verify Additional Questions on Pay Balance")
	public void verifyAdditionalQuestionsOnPayBalance() throws InterruptedException, IOException {
		try {

			d.getMyAccountPayNow().click();

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[@class='text-center']")));

			JavascriptExecutor jse = (JavascriptExecutor) driver;
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
			Assert.assertTrue(p.getAdditionalQuestionsSection().get(0).isDisplayed());

			Assert.assertTrue(p.getSaveCardQuestion().isDisplayed());
			p.getMoreInfoSaveCard().click();
			Thread.sleep(1000);
			Assert.assertEquals(p.getAdditionalQuestionPopupTitle().getText(), "Save Card For Use On Site");
			p.getAdditionalQuestionPopupClose().click();
			Thread.sleep(1000);

			Assert.assertTrue(p.getOnAccountCardQuestion().isDisplayed());
			p.getMoreInfoOnAccount().get(0).click();
			Thread.sleep(1000);
			Assert.assertEquals(p.getAdditionalQuestionPopupTitle().getText(), "On Account Charges");
			p.getAdditionalQuestionPopupClose().click();
			Thread.sleep(1000);

			Assert.assertTrue(p.getInClubQuestion().isDisplayed());
			p.getMoreInfoUseInPos().click();
			Thread.sleep(1000);
			Assert.assertEquals(p.getAdditionalQuestionPopupTitle().getText(), "Card On File");
			p.getAdditionalQuestionPopupClose().click();
			Thread.sleep(1000);

			rm.returnToDashboard();
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
		driver.close();
		driver = null;
	}

}
