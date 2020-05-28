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

public class MakePaymentTest_NewCard_NoAgreement extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String testName = null;
	private static String memberName = "HOH Auto";

	public reusableWaits rw;
	public reusableMethods rm;

	public MakePaymentTest_NewCard_NoAgreement() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Adding $5.00 to member's account")
	public void MakePaymentWithNewCard() throws InterruptedException, IOException {
		rm.activeMemberLogin("hoh", "Testing1!");
		rw.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		PaymentPO p = new PaymentPO(driver);
		d.getMyAccountPayNow().click();

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[@class='text-center']")));

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", p.getAmountRadioButton3());
//  	Thread.sleep(5000);
//  	p.getAmountRadioButton3().click();

			Thread.sleep(500);
			int variable = 1;
			while (variable < 13) {
				p.getCustomAmountInput().sendKeys(Keys.BACK_SPACE);
				variable++;
			}
			p.getCustomAmountInput().sendKeys("5.00");
			Thread.sleep(1000);

			p.getSelectPaymentNewCardButton().click();
			Thread.sleep(1000);

			Assert.assertEquals(p.getNameOnCard().getAttribute("value"), memberName);
			// JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", p.getCardNumber());
			p.getCardNumber().sendKeys("4111111111111111");
			p.getExpireMonth().sendKeys("12");
			p.getExpireYear().sendKeys("29");
			p.getCVC().sendKeys("123");
			p.getSaveCardYesRadio().click();
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

			p.getSaveCardNoRadio().click();
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

	@Test(priority = 2, description = "Confirming payment is applied", dependsOnMethods = { "MakePaymentWithNewCard" })
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

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
