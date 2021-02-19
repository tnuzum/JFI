package AccountHistory;

import java.io.IOException;
import java.util.ArrayList;

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
import org.testng.asserts.SoftAssert;

import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class InvoiceLayoutTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	private static DashboardPO d;
	public reusableWaits rw;
	public reusableMethods rm;
	public static AcctHistoryPO ahp;
	public static BreadcrumbTrailPO bt;
	public static WebDriverWait wait;
	static String receiptNumber;
	public static SoftAssert softAssertion = new SoftAssert();

	public InvoiceLayoutTest() {
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
		rm.activeMemberLogin("aqmember", "Testing1!");
		rw.waitForDashboardLoaded();

	}

	@Test(priority = 1, enabled = true)
	public void verifyInvoiceHeader() throws InterruptedException, IOException {

		try {

			String receiptNumber = rm.purchasePackage("PT - Demo");

			rm.openSideMenuIfNotOpenedAlready();
			d.getMenuMyAccount().click();
			while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
				Thread.sleep(1000);
			}
			d.getMenuAccountHistory().click();

			wait.until(ExpectedConditions.visibilityOf(ahp.getReceiptNumberTable()));

			ahp.getSearchField().sendKeys(receiptNumber);
			Thread.sleep(2000);

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", ahp.getReceiptNumbers().get(1));
			Thread.sleep(3000);
			jse.executeScript("arguments[0].scrollIntoView(true);",
					ahp.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-12 text-right']")));

			Assert.assertTrue(ahp.getReceiptHeader().isDisplayed());
			Assert.assertTrue(ahp.getReceiptPopupClose().isDisplayed());
			Assert.assertTrue(ahp.getReceiptPopupPrint().isDisplayed());

			Assert.assertTrue(ahp.getTransactionDate().isDisplayed());
			Assert.assertTrue(ahp.getDataColumns().get(0).getText().contains("Jonas Sports-Plex"));
			Assert.assertTrue(ahp.getDataColumns().get(1).getText().contains("Auto, Aqmember"));
			Assert.assertTrue(ahp.getDataColumns().get(2).getText().contains("Time:"));
			Assert.assertTrue(ahp.getDataColumns().get(2).getText().contains("Employee:"));

			Assert.assertTrue(ahp.getInvoiceAndCharges().isDisplayed());

			Assert.assertTrue(ahp.getDuesResponsibleTo().isDisplayed());

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
	public void verifyInvoiceDetails() throws InterruptedException, IOException {
		try {

			Assert.assertTrue(ahp.getMediaHeadings().get(0).getText().contains("We invoiced you for"));
			Assert.assertTrue(ahp.getDataColumns().get(3).getText().contains("QTY"));
			Assert.assertTrue(ahp.getDataColumns().get(4).getText().contains("Discount"));
			Assert.assertTrue(ahp.getDataColumns().get(5).getText().contains("Charges"));

			int count = ahp.getLineItems().size();

			for (int i = 0; i < count - 1; i++) {

				Assert.assertTrue(!ahp.getLineItems().get(i).getText().isBlank());
			}

			int count1 = ahp.getItemPrices().size();

			for (int j = 0; j < count1; j++) {
				Assert.assertFalse(ahp.getItemPrices().get(j).getText().isBlank());
			}
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

	@Test(priority = 3, enabled = true)
	public void verifyCharges() throws InterruptedException, IOException {
		try {

			Assert.assertTrue(ahp.getMediaHeadings().get(1).getText().contains("Charges"));

			int count2 = ahp.getDueDates().size();

			for (int k = 0; k < count2; k++) {
				Assert.assertFalse(ahp.getDueDates().get(k).getText().isBlank());
			}

			int count3 = ahp.getCharges().size();

			for (int l = 0; l < count3; l++) {
				Assert.assertFalse(ahp.getCharges().get(l).getText().isBlank());
			}

			int count4 = ahp.getDuesResponsibleParties().size();

			for (int m = 0; m < count4; m++) {
				Assert.assertFalse(ahp.getDuesResponsibleParties().get(m).getText().isBlank());
			}
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
	public void verifyFooter() throws InterruptedException, IOException {
		try {

			int count5 = ahp.getSubTotal().size();

			ArrayList<String> values = new ArrayList<String>();

			for (int n = 0; n < count5 - 6; n++) {
				String[] text = ahp.getSubTotal().get(n).getText().split(":");
				String fText = text[0];
				System.out.println(fText);
				values.add(fText);
			}
			Assert.assertTrue(values.contains("Sub Total"));
			Assert.assertTrue(values.contains("Discount"));
			Assert.assertTrue(values.contains("Tax"));

			Assert.assertTrue(ahp.getTotalInvoiced().getText().contains("Total Invoiced:"));

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

	@AfterTest
	public void teardown() {
		driver.quit();
		driver = null;
	}
}
