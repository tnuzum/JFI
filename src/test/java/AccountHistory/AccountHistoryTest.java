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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.DashboardPO;
import pageObjects.ThankYouPO;
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

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
		rm.activeMemberLogin("bhagya", "111");
		rw.waitForDashboardLoaded();
	}

	@Test(priority = 0)
	public void verifyBreadcrumbTest() throws InterruptedException {
		rm.openSideMenuIfNotOpenedAlready();
		d.getMenuMyAccount().click();
		while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
			Thread.sleep(1000);
		}
		d.getMenuAccountHistory().click();
		rm.catchErrorMessage();
		wait.until(ExpectedConditions.textToBePresentInElement(ahp.getPageHeader(), "Account History"));
		Assert.assertEquals(bt.getBreadcrumb1().getText(), "Dashboard");
		Assert.assertEquals(bt.getBreadcrumb2().getText(), "Account History");
	}

	@Test(priority = 1)
	public void verifyAcctSummaryBoxTest() throws InterruptedException, ParseException {
		Thread.sleep(2000);
		Assert.assertTrue(ahp.getAcctSummaryBox().isDisplayed());
		Assert.assertTrue(ahp.getUnPaidInvoices().isDisplayed());
		Assert.assertTrue(ahp.getCreditOnFile().isDisplayed());
		Assert.assertTrue(ahp.getBalance().isDisplayed());

		Assert.assertTrue(ahp.getAcctSummaryBox().getText().contains("Current Account Summary"));
		Assert.assertTrue(ahp.getAcctSummaryBox().getText().contains("Unpaid Invoices"));
		Assert.assertTrue(ahp.getAcctSummaryBox().getText().contains("Credit On File"));
		Assert.assertTrue(ahp.getAcctSummaryBox().getText().contains("Balance"));

		System.out.println(ahp.getUnPaidInvoices().getText());
		System.out.println(ahp.getCreditOnFile().getText());
		System.out.println(ahp.getBalance().getText());

		// Remove extra characters from the string amount

		String strUnPaidInvoices = ahp.getUnPaidInvoices().getText().replaceAll("[^\\d.]+", "");
		String strCreditOnFile = ahp.getCreditOnFile().getText().replaceAll("[^\\d.]+", "");
		String strBalance = ahp.getBalance().getText().replaceAll("[^\\d.]+", "");

		System.out.println(strUnPaidInvoices);
		System.out.println(strCreditOnFile);
		System.out.println(strBalance);

		// Format the string value to a float value
		DecimalFormat parser = new DecimalFormat("#.##");

		float unPaidInvoices = parser.parse(strUnPaidInvoices).floatValue();
		float creditOnFile = parser.parse(strCreditOnFile).floatValue();
		float balance = parser.parse(strBalance).floatValue();

		System.out.println(unPaidInvoices);
		System.out.println(creditOnFile);
		System.out.println(balance);

		Assert.assertEquals(Math.abs(unPaidInvoices - creditOnFile), Math.abs(balance));
	}

	@Test(priority = 2, enabled = true)
	public void verifyDefaultDateSelectionOnEndDateCalendarIconTest() throws InterruptedException {

		wait.until(ExpectedConditions.elementToBeClickable(ahp.getSecondCalendarIcon()));

		ahp.getSecondCalendarIcon().click();
		Thread.sleep(1000);

		rm.verifyNextMonthLastDateIsSelectedByDefault(ahp.getCalendarDates());

		Thread.sleep(2000);
	}

	@Test(priority = 3)
	public void verifyDefaultDateSelectionOnStartDateCalendarIconTest() throws InterruptedException {

		wait.until(ExpectedConditions.elementToBeClickable(ahp.getFirstCalendarIcon()));

		ahp.getFirstCalendarIcon().click();
		Thread.sleep(1000);
		rm.verifyFirstDateOfPreviousMonthIsSelectedByDefault(ahp.getCalendarDates());
		ahp.getCalendarDates().get(0).click();

		Thread.sleep(2000);
	}

	@Test(priority = 4, enabled = true)
	public void verifyAccountTransactionTable() throws InterruptedException {

		wait.until(ExpectedConditions.visibilityOf(ahp.getReceiptNumberTable()));

		Assert.assertTrue(ahp.getReceiptNumberTable().isDisplayed());

		Assert.assertTrue(ahp.getColumnNames().get(0).getText().contains("Due Date"));
		Assert.assertTrue(ahp.getColumnNames().get(1).getText().contains("Transaction Date"));
		Assert.assertTrue(ahp.getColumnNames().get(2).getText().contains("Payment"));
		Assert.assertTrue(ahp.getColumnNames().get(3).getText().contains("Invoice Amount"));
		Assert.assertTrue(ahp.getColumnNames().get(4).getText().contains("Balance"));
		Assert.assertTrue(ahp.getColumnNames().get(5).getText().contains("Transaction ID. #"));
		Assert.assertTrue(ahp.getDescriptionColumn().getText().contains("Description"));
	}

	@Test(priority = 5, enabled = true)
	public void searchForAReceiptNumberTest() throws InterruptedException {

		receiptNumber = ahp.getReceiptNumber().getText().trim();

		ahp.getSearchField().sendKeys(receiptNumber);

		Thread.sleep(2000);

		wait.until(ExpectedConditions.textToBePresentInElement(ahp.getReceiptNumber(), receiptNumber));

		int count = ahp.getReceiptNumbers().size();

		for (int i = 0; i < count; i++) {
			Assert.assertTrue(ahp.getReceiptNumbers().get(i).getText().contains(receiptNumber));
		}
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", ahp.getReceiptNumber());
		Thread.sleep(2000);
		ThankYouPO TY = new ThankYouPO(driver);
		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
		Thread.sleep(1000);
		rm.memberLogout();

	}

	@AfterTest
	public void teardown() {
		driver.quit();
		driver = null;
	}
}
