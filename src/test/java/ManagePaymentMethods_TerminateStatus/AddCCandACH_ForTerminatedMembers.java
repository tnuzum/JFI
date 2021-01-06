package ManagePaymentMethods_TerminateStatus;

import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import pageObjects.ManagePayMethodsPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class AddCCandACH_ForTerminatedMembers extends base {
	public reusableWaits rw;
	public reusableMethods rm;

	public static DashboardPO d;
	public static ManagePayMethodsPO mp;
	private static String testName = null;

	public AddCCandACH_ForTerminatedMembers() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

	@BeforeTest
	public void initialize() throws Exception, IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		d = new DashboardPO(driver);
		mp = new ManagePayMethodsPO(driver);
		getEMEURL();
	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 2, description = "Adding new ACH for Terminated members")
	public void AddACH_ForTerminatedMembers() throws IOException, InterruptedException {

		try {
			/*
			 * rm.activeMemberLogin("Seema", "June@123");
			 * rm.openSideMenuIfNotOpenedAlready();
			 * Assert.assertTrue(d.getMenuMyAccount().isDisplayed());
			 * d.getMenuMyAccount().click(); d.getMenuManagePmntMethods().click();
			 */

			JavascriptExecutor jse = (JavascriptExecutor) driver;

			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getBankAccountLink());
			Thread.sleep(2000);
			jse.executeScript("arguments[0].click();", mp.getBankAccountLink());
			// mp.getBankAccountLink().click();
			mp.getAccountHolder().sendKeys("Seema");
			// mp.getUSBankRadio().click();
			mp.getUSRoutingNumber().sendKeys(prop.getProperty("USBankRoutingNumber"));
			mp.getUSAccountNumber().sendKeys(prop.getProperty("USBankAcctNumber"));
			// mp.getCheckingRadio().click();
			// mp.getSavingsradio().click();

			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getSignaturePad().get(0));
			Thread.sleep(2000);
			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(0)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();
			mp.getIAgreeCheckboxACH().click();
			mp.getAddBankAcctButton().click();
			rw.waitForAcceptButton();
			String text = (mp.getPopupConfirmation1().getText());
			log.info(text);
			System.out.println(text);
			mp.getPopupConfirmationButton().click();
			Assert.assertEquals("BANK ACCOUNT ADDED", text);

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

	@Test(priority = 1, description = "Adding a new Credit Card for Terminated members")
	public void AddCC_ForTerminatedMembers() throws IOException, InterruptedException {

		try {

			rm.activeMemberLogin("Seema", "June@123");
			rm.openSideMenuIfNotOpenedAlready();
			Assert.assertTrue(d.getMenuMyAccount().isDisplayed());
			d.getMenuMyAccount().click();
			d.getMenuManagePmntMethods().click();
			mp.getNameOnCard().sendKeys("Seema");
			mp.getCardNumber().sendKeys(prop.getProperty("CCNumber"));
			Thread.sleep(2000);
			mp.getExpireMonth().sendKeys("12");
			Thread.sleep(2000);
			mp.getExpireYear().sendKeys("27");

			JavascriptExecutor jse = (JavascriptExecutor) driver;

			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getSignaturePad().get(1));
			Thread.sleep(2000);
			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(1)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();
			Thread.sleep(2000);
			mp.getIAgreeCheckbox().click();
			mp.getAddCCButton().click();

			rw.waitForAcceptButton();
			System.out.println(mp.getPopupConfirmation1().getText());
			String text = (mp.getPopupConfirmation1().getText());
			log.info(text);
			System.out.println(text);
			mp.getPopupConfirmationButton().click();
			Assert.assertEquals("CREDIT CARD ADDED", text);
			Thread.sleep(2000);

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

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
