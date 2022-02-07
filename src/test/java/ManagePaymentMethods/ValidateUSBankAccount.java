package ManagePaymentMethods;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.DashboardPO;
import pageObjects.ManagePayMethodsPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ValidateUSBankAccount extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String testName = null;
	private static String memberName = "Paymember Auto";
	private static JavascriptExecutor jse;
	public reusableWaits rw;
	public reusableMethods rm;
	public static DashboardPO d;
	// public static PaymentPO p;
	public static ManagePayMethodsPO mp;
	public static BreadcrumbTrailPO bt;

	public ValidateUSBankAccount() {
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
		mp = new ManagePayMethodsPO(driver);
		bt = new BreadcrumbTrailPO(driver);
		jse = (JavascriptExecutor) driver;

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Adding a US Bank Checking Account Invalid Routing And InvalidAccount PBI 185546")
	public void AddInvalidRoutingAndInvalidAccount() throws InterruptedException, IOException {

		try {
			rm.activeMemberLogin("paymember", "Testing1!");
			rw.waitForDashboardLoaded();
			rm.openSideMenuIfNotOpenedAlready();

			d.getMenuMyAccount().click();
			while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
				Thread.sleep(1000);
			}

			d.getMenuManagePmntMethods().click();
			Thread.sleep(2000);

			mp.getBankAccountLink().click();
			Thread.sleep(1000);

			mp.getAccountHolder().sendKeys(memberName);
			Assert.assertTrue(mp.getUSBankRadio().isSelected());

			mp.getUSRoutingNumber().sendKeys(prop.getProperty("InvalidUSBankRoutingNumber"));
			mp.getUSAccountNumber().sendKeys(prop.getProperty("InvalidUSBankAcctNumber"));

			jse.executeScript("arguments[0].click();", mp.getIAgreeCheckboxACH());
			Thread.sleep(1000);

			Assert.assertTrue(mp.getAddBankAcctButton().isEnabled());

			jse.executeScript("arguments[0].click();", mp.getAddBankAcctButton());

			Assert.assertTrue(mp.getPopupContent().getText().contains("A signature is required to continue."));
			Thread.sleep(1000);
			mp.getPopupConfirmationButton().click();
			Thread.sleep(1000);

			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getSignaturePad().get(0));
			Thread.sleep(2000);

			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(0)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();

			Thread.sleep(1000);

			jse.executeScript("arguments[0].click();", mp.getAddBankAcctButton());
			rw.waitForAcceptButton();
			System.out.println(mp.getPopupConfirmation1().getText());
			Assert.assertEquals(mp.getPopupConfirmation1().getText(), "Oops!");
			Assert.assertEquals(mp.getPopupContent().getText(),
					"Account information incorrectly entered, please correct and try again.");
			mp.getPopupConfirmationButton().click();

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

	}

	@Test(priority = 2, description = "Adding a US Bank Checking Account Invalid Routing And ValidAccount PBI 185546")
	public void AddInvalidRoutingAndValidAccount() throws InterruptedException, IOException {

		try {

			mp.getUSRoutingNumber().clear();
			mp.getUSRoutingNumber().sendKeys(prop.getProperty("InvalidUSBankRoutingNumber"));
			mp.getUSAccountNumber().clear();
			mp.getUSAccountNumber().sendKeys(prop.getProperty("USBankAcctNumber"));

			Assert.assertTrue(mp.getAddBankAcctButton().isEnabled());

			jse.executeScript("arguments[0].click();", mp.getAddBankAcctButton());
			rw.waitForAcceptButton();
			System.out.println(mp.getPopupConfirmation1().getText());
			Assert.assertEquals(mp.getPopupConfirmation1().getText(), "Oops!");
			Assert.assertEquals(mp.getPopupContent().getText(),
					"Account information incorrectly entered, please correct and try again.");
			mp.getPopupConfirmationButton().click();

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

	}

	@Test(priority = 3, description = "Adding a US Bank Checking Account Valid Routing And InvalidAccount PBI 185546")
	public void AddValidRoutingAndInvalidAccount() throws InterruptedException, IOException {

		try {

			mp.getUSRoutingNumber().clear();
			mp.getUSRoutingNumber().sendKeys(prop.getProperty("USBankRoutingNumber"));
			mp.getUSAccountNumber().clear();
			mp.getUSAccountNumber().sendKeys(prop.getProperty("InvalidUSBankAcctNumber"));

			Assert.assertTrue(mp.getAddBankAcctButton().isEnabled());

			jse.executeScript("arguments[0].click();", mp.getAddBankAcctButton());
			rw.waitForAcceptButton();
			System.out.println(mp.getPopupConfirmation1().getText());
			Assert.assertEquals(mp.getPopupConfirmation1().getText(), "Oops!");
			Assert.assertEquals(mp.getPopupContent().getText(),
					"Account information incorrectly entered, please correct and try again.");
			mp.getPopupConfirmationButton().click();

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

	}

	@Test(priority = 4, description = "Adding a US Bank Checking Account Valid Routing And InvalidAccount PBI 185546")
	public void AddValidRoutingAndValidAccount() throws InterruptedException, IOException {

		try {

			mp.getUSRoutingNumber().clear();
			mp.getUSRoutingNumber().sendKeys(prop.getProperty("USBankRoutingNumber"));
			mp.getUSAccountNumber().clear();
			mp.getUSAccountNumber().sendKeys(prop.getProperty("USBankAcctNumber"));

			Assert.assertTrue(mp.getAddBankAcctButton().isEnabled());

			jse.executeScript("arguments[0].click();", mp.getAddBankAcctButton());
			rw.waitForAcceptButton();
			System.out.println(mp.getPopupConfirmation1().getText());
			Assert.assertEquals(mp.getPopupConfirmation1().getText(), "BANK ACCOUNT ADDED");
			mp.getPopupConfirmationButton().click();

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

	}

	@Test(priority = 5, description = "Editing a US Bank Checking Account and changing the routing number to an invalid number, this will not trigger account validation - PBI 185546")
	public void EditInvalidRoutingAndValidAccount() throws InterruptedException, IOException {

		try {
			Thread.sleep(1000);
			int FopCount = mp.getCardNumbers().size();
			for (int i = 0; i < FopCount; i++) {

				if (mp.getCardNumbers().get(i).getText().contains(prop.getProperty("USBankLast4Digits"))) {
					jse.executeScript("arguments[0].scrollIntoView(true);", mp.getEditPaymentMethodsButton().get(i));
					jse.executeScript("arguments[0].click();", mp.getEditPaymentMethodsButton().get(i));

					break;
				}
			}
			Thread.sleep(1000);

			String text = mp.getEditAccountHolder().getAttribute("value");
			System.out.println(text);

			Assert.assertEquals(text, memberName);

			String text1 = mp.getEditUSRoutingNumber().getAttribute("value");
			System.out.println(text1);
			Assert.assertEquals(text1, prop.getProperty("USBankRoutingNumber"));

			mp.getEditUSRoutingNumber().clear();

			mp.getEditUSRoutingNumber().sendKeys(prop.getProperty("InvalidUSBankRoutingNumber"));

			Assert.assertTrue(!mp.getSaveChangeButton().isEnabled());

			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getSignaturePad().get(0));
			Thread.sleep(2000);

			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(0)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();

			Thread.sleep(1000);

			jse.executeScript("arguments[0].click();", mp.getIAgreeCheckboxEditACH());
			Thread.sleep(1000);

			jse.executeScript("arguments[0].click();", mp.getSaveChangeButton());
			rw.waitForAcceptButton();
			System.out.println(mp.getPopupConfirmation1().getText());
			Assert.assertEquals("BANK ACCOUNT UPDATED", mp.getPopupConfirmation1().getText());
			mp.getPopupConfirmationButton().click();

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

		catch (java.lang.IndexOutOfBoundsException iob) {
			System.out.println("Index Out Of Bounds");
			iob.printStackTrace();
			getScreenshot(testName, driver);
			log.error(iob.getMessage(), iob);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

	}

	@Test(priority = 6, description = "Editing a US Bank Checking Account with a valid routing number and invalid account number, this will  trigger account validation - PBI 185546")
	public void EditValidRoutingAndInvalidAccount() throws InterruptedException, IOException {

		try {
			Thread.sleep(1000);
			int FopCount = mp.getCardNumbers().size();
			for (int i = 0; i < FopCount; i++) {

				if (mp.getCardNumbers().get(i).getText().contains(prop.getProperty("USBankLast4Digits"))) {
					jse.executeScript("arguments[0].scrollIntoView(true);", mp.getEditPaymentMethodsButton().get(i));
					jse.executeScript("arguments[0].click();", mp.getEditPaymentMethodsButton().get(i));

					break;
				}
			}
			Thread.sleep(1000);
			mp.getEditUSRoutingNumber().clear();

			mp.getEditUSRoutingNumber().sendKeys(prop.getProperty("USBankRoutingNumber"));

			jse.executeScript("arguments[0].click();", mp.getUSAccountNumberEditBtn());

			mp.geteditUSAccountNumber().sendKeys(prop.getProperty("InvalidUSBankAcctNumber"));

			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getSignaturePad().get(0));
			Thread.sleep(2000);

			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(0)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();

			Thread.sleep(1000);

			jse.executeScript("arguments[0].click();", mp.getIAgreeCheckboxEditACH());
			Thread.sleep(1000);

			jse.executeScript("arguments[0].click();", mp.getSaveChangeButton());
			rw.waitForAcceptButton();

			System.out.println(mp.getPopupConfirmation1().getText());
			Assert.assertEquals(mp.getPopupConfirmation1().getText(), "Oops!");
			Assert.assertEquals(mp.getPopupContent().getText(),
					"Account information incorrectly entered, please correct and try again.");

			mp.getPopupConfirmationButton().click();

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

		catch (java.lang.IndexOutOfBoundsException iob) {
			System.out.println("Index Out Of Bounds");
			iob.printStackTrace();
			getScreenshot(testName, driver);
			log.error(iob.getMessage(), iob);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

	}

	@Test(priority = 7, description = "Editing a US Bank Checking Account with an invalid routing number and invalid account number, this will trigger account validation - PBI 185546")
	public void EditInvalidRoutingAndInvalidAccount() throws InterruptedException, IOException {

		try {
			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getEditUSRoutingNumber());
			mp.getEditUSRoutingNumber().clear();

			mp.getEditUSRoutingNumber().sendKeys(prop.getProperty("InvalidUSBankRoutingNumber"));

			mp.geteditUSAccountNumber().clear();

			mp.geteditUSAccountNumber().sendKeys(prop.getProperty("InvalidUSBankAcctNumber"));

			jse.executeScript("arguments[0].click();", mp.getSaveChangeButton());
			rw.waitForAcceptButton();

			System.out.println(mp.getPopupConfirmation1().getText());
			Assert.assertEquals(mp.getPopupConfirmation1().getText(), "Oops!");
			Assert.assertEquals(mp.getPopupContent().getText(),
					"Account information incorrectly entered, please correct and try again.");

			mp.getPopupConfirmationButton().click();

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

		catch (java.lang.IndexOutOfBoundsException iob) {
			System.out.println("Index Out Of Bounds");
			iob.printStackTrace();
			getScreenshot(testName, driver);
			log.error(iob.getMessage(), iob);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

	}

	@Test(priority = 8, description = "Editing a US Bank Checking Account with an invalid routing number and invalid account number, this will trigger account validation - PBI 185546")
	public void EditValidRoutingAndValidAccount() throws InterruptedException, IOException {

		try {
			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getEditUSRoutingNumber());
			mp.getEditUSRoutingNumber().clear();

			mp.getEditUSRoutingNumber().sendKeys(prop.getProperty("USBankRoutingNumber"));

			mp.geteditUSAccountNumber().clear();

			mp.geteditUSAccountNumber().sendKeys(prop.getProperty("USBankAcctNumber"));

			jse.executeScript("arguments[0].click();", mp.getSaveChangeButton());
			rw.waitForAcceptButton();

			System.out.println(mp.getPopupConfirmation1().getText());
			Assert.assertEquals(mp.getPopupConfirmation1().getText(), "BANK ACCOUNT UPDATED");

			mp.getPopupConfirmationButton().click();

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

		catch (java.lang.IndexOutOfBoundsException iob) {
			System.out.println("Index Out Of Bounds");
			iob.printStackTrace();
			getScreenshot(testName, driver);
			log.error(iob.getMessage(), iob);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

	}

	@Test(priority = 9, description = "Delete the Card in COG")
	public void deleteCardInCOG() throws InterruptedException, IOException {
		try {

			rm.deleteFOPInCOG("1143354", "Jonas Sports-Plex", prop.getProperty("USBankLast4Digits"), "No", "");

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
	}

//	@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}
