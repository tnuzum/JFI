package PayBalance;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import pageObjects.PaymentPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class PayBalance_NewCard_UnCheckAgrmntWithBadFOP extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String testName = null;
	private static String memberName = "BadFopMbr Auto";
	private static String agreement = "Balance Weight Loss 12 Week";

	public reusableWaits rw;
	public reusableMethods rm;

	public PayBalance_NewCard_UnCheckAgrmntWithBadFOP() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		driver.get(prop.getProperty("EMELoginPage"));
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Adding $5.00 to member's account")
	public void MakePaymentWithNewCard_SelectAreYouSure() throws InterruptedException, IOException {

		DashboardPO d = new DashboardPO(driver);
		PaymentPO p = new PaymentPO(driver);
		try {
			rm.activeMemberLogin("badfopmbr", "Testing1!");
			rw.waitForDashboardLoaded();

			d.getMyAccountPayNow().click();

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[@class='text-center']")));

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", p.getAmountRadioButton3());

			Thread.sleep(500);
			int variable = 1;
			while (variable < 13) {
				p.getCustomAmountInput().sendKeys(Keys.BACK_SPACE);
				variable++;
			}
			p.getCustomAmountInput().sendKeys("5.00");
			Thread.sleep(2000);

			jse.executeScript("arguments[0].click();", p.getSelectPaymentNewCardButton());
			Thread.sleep(1000);

			log.info("NewCard Button was clicked");
			System.out.println("NewCard Button was clicked");

			rw.waitForNewCardFormToOpen();
			rm.OpenNewcardFormIfNotOpenInFirstAttempt();

			Assert.assertEquals(p.getNameOnCard().getAttribute("value"), memberName);
			// JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", p.getCardNumber());
			p.getCardNumber().sendKeys("4111111111111111");
			p.getExpireMonth().sendKeys("04");
			p.getExpireYear().sendKeys("22");
			p.getCVC().sendKeys("123");
			p.getSaveCardYesRadio().click();
			p.getHouseAcctNoRadioButton().click();
			p.getInClubPurchaseNoRadio().click();
			Thread.sleep(1000);

			// Assert.assertTrue(p.getLinkAgreementsHeader().isDisplayed());
			// Assert.assertTrue(p.getLabelText().isDisplayed());
			// Assert.assertTrue(p.getLabelText1().isDisplayed());

			Assert.assertTrue(!p.getSubmitButton().isEnabled());

			for (int i = 0; i < p.getAgreementLabel().size(); i++) {
				if (p.getAgreementLabel().get(i).getText().contains(agreement)) {
					p.getAgreementCheckBox().get(i).click();
					break;

				}
			}

			Assert.assertTrue(p.getSlideDownBox().isDisplayed());
			// Assert.assertTrue(p.getLabelText1().isDisplayed());
			p.getAreYouSure().click();
			Assert.assertEquals(rm.isElementPresent(By.xpath("//div[contains(text(),'A selection is required')]")),
					false);

			Thread.sleep(1000);
			p.getIAgreeCheckbox().click();
			Thread.sleep(2000);

			Assert.assertTrue(p.getSubmitButton().isEnabled());

			p.getSubmitButton().click();

			Assert.assertTrue(p.getPopupContent().getText().contains("A signature is required to continue."));
			Thread.sleep(1000);
			p.getPopupConfirmationButton().click();
			Thread.sleep(1000);

			Actions a = new Actions(driver);
			a.moveToElement(p.getSignaturePad()).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10).release()
					.build().perform();

			/*
			 * p.getNoThanks().click(); Thread.sleep(1000);
			 * 
			 * p.getSaveCardNoRadio().click();
			 */
			Thread.sleep(1000);

			p.getSubmitButton().click();
			rw.waitForAcceptButton();
			p.getPopupConfirmationButton().click();
			rw.waitForAcceptButton();
			System.out.println(p.getPopupText().getText());
			Assert.assertEquals("Payment Made!", p.getPopupText().getText());
			p.getPopupConfirmationButton().click();

			Thread.sleep(3000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

		finally {
			boolean popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));

			if (popup == true) {
				p.getPopupConfirmationButton().click();
				System.out.println("popup was present");
			}

			d.getBreadcrumbDashboard().click();
		}

	}

	@Test(priority = 2, description = "Confirming payment is applied", dependsOnMethods = {
			"MakePaymentWithNewCard_SelectAreYouSure" })
	public void ConfirmPaymentApplied() throws InterruptedException, IOException {
		try {
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

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

		finally {
			rm.memberLogout();
		}
	}

	@Test(priority = 3, description = "Delete the Card in COG")
	public void deleteCardInCOG() throws InterruptedException, IOException {
		try {

			rm.deleteFOPInCOG("1143412", "Jonas Sports-Plex", "1111", "No");

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}
	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
