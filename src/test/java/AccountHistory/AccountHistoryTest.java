package AccountHistory;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class AccountHistoryTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	private static DashboardPO d;
	public reusableWaits rw;
	public reusableMethods rm;
	public static AcctHistoryPO ahp;
	public static BreadcrumbTrailPO bt;
	public static WebDriverWait wait;
	static String receiptNumber;
	private static JavascriptExecutor jse;

	public AccountHistoryTest() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		d = new DashboardPO(driver);
		ahp = new AcctHistoryPO(driver);
		bt = new BreadcrumbTrailPO(driver);
		wait = new WebDriverWait(driver, 30);
		jse = (JavascriptExecutor) driver;

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
		rm.activeMemberLogin("bhagya", "111");
		rw.waitForDashboardLoaded();
	}

	@Test(priority = 0)
	public void verifyBreadcrumbTest() throws InterruptedException, IOException {
		try {
			rm.openSideMenuIfNotOpenedAlready();
			d.getMenuMyAccount().click();
			while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
				Thread.sleep(1000);
			}
			d.getMenuAccountHistory().click();
			// rm.myProfileLogin("bhagya", "111");
			wait.until(ExpectedConditions.textToBePresentInElement(ahp.getPageHeader(), "Account History"));
			Assert.assertEquals(bt.getBreadcrumb1().getText(), "Dashboard");
			Assert.assertEquals(bt.getBreadcrumb2().getText(), "Account History");
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

	@Test(priority = 1)
	public void verifyAcctSummaryBoxTest() throws InterruptedException, ParseException, IOException {

		try {
			Thread.sleep(2000);
			Assert.assertTrue(ahp.getAcctSummaryBox().isDisplayed());
			Assert.assertTrue(ahp.getUnPaidInvoices().isDisplayed());
			Assert.assertTrue(ahp.getCreditOnFile().isDisplayed());
			Assert.assertTrue(ahp.getBalance().isDisplayed());

			Assert.assertTrue(ahp.getAcctSummaryBox().getText().contains("Current Account Summary"));
			Assert.assertTrue(ahp.getAcctSummaryBox().getText().contains("Unpaid Invoices"));
			Assert.assertTrue(ahp.getAcctSummaryBox().getText().contains("Credit On File"));
			Assert.assertTrue(ahp.getAcctSummaryBox().getText().contains("Balance"));

			// Remove extra characters from the string amount

			String strUnPaidInvoices = ahp.getUnPaidInvoices().getText().replaceAll("[^\\d.]+", "");
			String strCreditOnFile = ahp.getCreditOnFile().getText().replaceAll("[^\\d.]+", "");
			String strBalance = ahp.getBalance().getText().replaceAll("[^\\d.]+", "");

			// Format the string value to a float value
			DecimalFormat parser = new DecimalFormat("#.##");

			float unPaidInvoices = parser.parse(strUnPaidInvoices).floatValue();
			float creditOnFile = parser.parse(strCreditOnFile).floatValue();
			float balance = parser.parse(strBalance).floatValue();

			System.out.println(unPaidInvoices);
			System.out.println(creditOnFile);
			System.out.println(balance);

			System.out.println(Math.round(balance));

			System.out.println(Math.round(Math.abs(unPaidInvoices - creditOnFile)));
			System.out.println(parser.format(Math.abs(unPaidInvoices - creditOnFile)));

			Assert.assertEquals(parser.parse(parser.format(Math.abs(unPaidInvoices - creditOnFile))).floatValue(),
					balance);
			Assert.assertEquals(Math.round(Math.abs(unPaidInvoices - creditOnFile)), Math.round(balance));

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

	@Test(priority = 2, enabled = true)
	public void verifyDefaultDateSelectionOnEndDateCalendarIconTest() throws InterruptedException, IOException {

		try {

			wait.until(ExpectedConditions.elementToBeClickable(ahp.getSecondCalendarIcon()));

			rm.scrollIntoView(driver, ahp.getSecondCalendarIcon());
			Thread.sleep(1000);
			rm.moveToElementAndClick(driver, ahp.getSecondCalendarIcon());

			Thread.sleep(1000);

			rm.verifyNextMonthLastDateIsSelectedByDefault(ahp.getCalendarDates());

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

	@Test(priority = 3)
	public void verifyDefaultDateSelectionOnStartDateCalendarIconTest() throws InterruptedException, IOException {
		try {

			wait.until(ExpectedConditions.elementToBeClickable(ahp.getFirstCalendarIcon()));

			rm.scrollIntoView(driver, ahp.getFirstCalendarIcon());
			Thread.sleep(100);
			rm.moveToElementAndClick(driver, ahp.getFirstCalendarIcon());

			Thread.sleep(1000);

			rm.verifyFirstDateOfPreviousMonthIsSelectedByDefault(ahp.getCalendarDates());
			ahp.getCalendarDates().get(0).click();

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

	@Test(priority = 4, enabled = true)
	public void verifyAccountTransactionTable() throws InterruptedException, IOException {
		try {

			while (ahp.getSearchingAcctHistMessage().size() != 0) {
				System.out.println("waiting for account history to display");
				Thread.sleep(1000);
			}
			wait.until(ExpectedConditions.visibilityOf(ahp.getReceiptNumberTable()));

			Assert.assertTrue(ahp.getReceiptNumberTable().isDisplayed());

			Assert.assertTrue(ahp.getColumnNames().get(0).getText().contains("Due Date"));
			Assert.assertTrue(ahp.getColumnNames().get(1).getText().contains("Transaction Date"));
			Assert.assertTrue(ahp.getColumnNames().get(2).getText().contains("Payment"));
			Assert.assertTrue(ahp.getColumnNames().get(3).getText().contains("Invoice Amount"));
			Assert.assertTrue(ahp.getColumnNames().get(4).getText().contains("Balance"));
			Assert.assertTrue(ahp.getColumnNames().get(5).getText().contains("Transaction ID"));
			Assert.assertTrue(ahp.getDescriptionColumn().getText().contains("Description"));

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

	@Test(priority = 5, enabled = true)
	public void searchForAReceiptNumberTest() throws InterruptedException, IOException {
		try {

			receiptNumber = ahp.getReceiptNumber().getText().trim();

			ahp.getSearchField().sendKeys(receiptNumber);

			Thread.sleep(2000);

			wait.until(ExpectedConditions.textToBePresentInElement(ahp.getReceiptNumber(), receiptNumber));

			int count = ahp.getReceiptNumbers().size();

			for (int i = 0; i < count; i++) {
				Assert.assertTrue(count > 0);
			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", ahp.getReceiptNumbers().get(0));
			Thread.sleep(2000);
			jse.executeScript("arguments[0].scrollIntoView(true);", ahp.getReceiptPopup());

			ahp.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
			Thread.sleep(1000);
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
	public void teardown() {
		driver.quit();
		driver = null;
	}
}
