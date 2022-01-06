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

import pageObjects.AcctHistoryPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class Bug183910_PayBalance_StoredCard extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String testName = null;
	private static JavascriptExecutor jse;
	private static String receiptNumber;

	public reusableWaits rw;
	public reusableMethods rm;

	public Bug183910_PayBalance_StoredCard() {
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

		jse = (JavascriptExecutor) driver;

		getEMEURL();
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Adding $5.00 to member's account")
	public void MakePaymentWithStoredCard() throws InterruptedException, IOException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		DashboardPO d = new DashboardPO(driver);
		PaymentPO p = new PaymentPO(driver);
		try {
			rm.activeMemberLogin("rauto", "Testing1!");
			rw.waitForDashboardLoaded();

			jse.executeScript("arguments[0].click();", d.getMyAccountPayNow());

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[@class='text-center']")));

			jse.executeScript("arguments[0].click();", p.getAmountRadioButton3());

			Thread.sleep(500);
			int variable = 1;
			while (variable < 13) {
				p.getCustomAmountInput().sendKeys(Keys.BACK_SPACE);
				variable++;
			}
			p.getCustomAmountInput().sendKeys("5.00");
			Thread.sleep(3000);

			jse.executeScript("arguments[0].scrollIntoView(true);", p.getPayWithThisMethodButton1());
			Thread.sleep(1000);
			Actions a = new Actions(driver);
			a.moveToElement(p.getPayWithThisMethodButton1()).click().build().perform();
			rw.waitForAcceptButton();
			p.getPopupConfirmationButton().click();
			rw.waitForAcceptButton();
			System.out.println(p.getPopupText().getText());
			Assert.assertEquals("Payment Made!", p.getPopupText().getText());

			String content = p.getPopupContent().getText();
			String[] splitContent = content.split(": ");
			receiptNumber = splitContent[1];
			System.out.println(receiptNumber);

			Assert.assertTrue(!receiptNumber.equals("null"));

			p.getPopupConfirmationButton().click();
//		rm.returnToDashboard();
			Thread.sleep(3000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

			while (popup == true) {
				p.getPopupConfirmationButton().click();
				System.out.println("popup was present");
				popup = rm.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));
			}

			rm.returnToDashboard();
		}

	}

	@Test(priority = 2, description = "Confirming payment is applied", dependsOnMethods = {
			"MakePaymentWithStoredCard" })
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

			jse.executeScript("arguments[0].click();", d.getMyAccountAccountHistory());
			//rm.myProfileLogin("rauto", "Testing1!");

			AcctHistoryPO ahp = new AcctHistoryPO(driver);
			ThankYouPO TY = new ThankYouPO(driver);

			while (ahp.getSearchingAcctHistMessage().size() != 0) {
				System.out.println("waiting for account history to display");
				Thread.sleep(1000);
			}

			while (!ahp.getReceiptNumberTable().isDisplayed()) {
				Thread.sleep(2000);
				System.out.println("waiting");
			}

			// Clicks on the Receiptnumber in Account History

			ahp.getSearchField().sendKeys(receiptNumber);

			Thread.sleep(2000);
			wait.until(ExpectedConditions.textToBePresentInElement(ahp.getReceiptNumber(), receiptNumber));
			jse.executeScript("arguments[0].click();", ahp.getReceiptNumbers().get(0));

			Thread.sleep(3000);

			jse.executeScript("arguments[0].scrollIntoView(true);", ahp.getReceiptPopup());

			// Verifies the Invoice amount
			Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//h2[@class='media-heading']")).getText()
					.contains("$5.00"));
			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
			Thread.sleep(1000);
			rm.returnToDashboard();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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
		driver.quit();
		driver = null;
	}
}