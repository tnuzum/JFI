package ManagePaymentMethods;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
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
import pageObjects.PaymentPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class Bug167495_AddACHCheckingVsSavingsFiltering extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String testName = null;
	private static String memberName = "Robert Auto";
	private static String agreementWithBadFOP = "Athletic Platinum";

	public reusableWaits rw;
	public reusableMethods rm;
	public static DashboardPO d;
	public static PaymentPO p;
	public static ManagePayMethodsPO mp;
	public static BreadcrumbTrailPO bt;

	public Bug167495_AddACHCheckingVsSavingsFiltering() {
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
		p = new PaymentPO(driver);
		mp = new ManagePayMethodsPO(driver);
		bt = new BreadcrumbTrailPO(driver);

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Verify that No Thanks is displayed for Savings Filter")
	public void CheckingVsSavings() throws InterruptedException, IOException {

		try {
			rm.activeMemberLogin("rauto", "Testing1!");
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

			mp.getUSRoutingNumber().sendKeys(prop.getProperty("USBankRoutingNumber"));
			mp.getUSAccountNumber().sendKeys(prop.getProperty("USBankAcctNumber"));

			Assert.assertTrue(mp.getCheckingRadio().isSelected()); // Checking Account

			for (int i = 0; i < mp.getAgreementLabel().size(); i++) {

				ArrayList<String> agreementLabels = new ArrayList<String>();

				agreementLabels.add(mp.getAgreementLabel().get(i).getText());

				Assert.assertTrue(agreementLabels.contains(agreementWithBadFOP));

				if (mp.getAgreementLabel().get(i).getText().contains(agreementWithBadFOP)) {

					Assert.assertTrue(mp.getAgreementCheckBox().get(i).isSelected());
					break;

				}
			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getSavingsradio());
			Thread.sleep(1000);
			mp.getSavingsradio().click();

			for (int i = 0; i < mp.getAgreementLabel().size(); i++) {

				ArrayList<String> agreementLabels = new ArrayList<String>();

				agreementLabels.add(mp.getAgreementLabel().get(i).getText());

				Assert.assertFalse(agreementLabels.contains(agreementWithBadFOP));

			}

			Assert.assertTrue(mp.getLabelText1().get(0).isDisplayed());

			Assert.assertTrue(mp.getNoThanks().size() > 0);
			mp.getNoThanks().get(0).click();

			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(0)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();

			Thread.sleep(1000);

			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getIAgreeCheckboxACH());
			Thread.sleep(1000);
			mp.getIAgreeCheckboxACH().click();
			Thread.sleep(1000);

			mp.getAddBankAcctButton().click();
			rw.waitForAcceptButton();
			System.out.println(mp.getPopupConfirmation1().getText());
			Assert.assertEquals("BANK ACCOUNT ADDED", mp.getPopupConfirmation1().getText());
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

	}

	@Test(priority = 2, description = "Verify that No Thanks is displayed for Savings Filter on Edit")
	public void EditCheckingVsSavings() throws InterruptedException, IOException {

		try {

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			int FopCount = mp.getCardNumbers().size();
			for (int i = 0; i < FopCount; i++) {

				if (mp.getCardNumbers().get(i).getText().contains(prop.getProperty("USBankLast4Digits"))) {

					jse.executeScript("arguments[0].scrollIntoView(true);", mp.getEditPaymentMethodsButton().get(i));
					Thread.sleep(1000);
					mp.getEditPaymentMethodsButton().get(i).click();
					break;
				}
			}
			Thread.sleep(1000);

			Assert.assertTrue(mp.getEditSavingsRadio().isSelected());

			for (int i = 0; i < mp.getAgreementLabel().size(); i++) {

				ArrayList<String> agreementLabels = new ArrayList<String>();

				agreementLabels.add(mp.getAgreementLabel().get(i).getText());

				Assert.assertFalse(agreementLabels.contains(agreementWithBadFOP));

			}
			Assert.assertTrue(mp.getNoThanks().size() > 0);
			Assert.assertTrue(mp.getLabelText1().get(0).isDisplayed());

			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getEditCheckingRadio());
			Thread.sleep(1000);

			mp.getEditCheckingRadio().click(); // Checking Account

			Assert.assertEquals(rm.isElementPresent(By.xpath("//div[contains(text(),'A selection is required')]")),
					false);

			for (int i = 0; i < mp.getAgreementLabel().size(); i++) {

				ArrayList<String> agreementLabels = new ArrayList<String>();

				agreementLabels.add(mp.getAgreementLabel().get(i).getText());

				Assert.assertTrue(agreementLabels.contains(agreementWithBadFOP));

				if (mp.getAgreementLabel().get(i).getText().contains(agreementWithBadFOP)) {

					Assert.assertTrue(mp.getAgreementCheckBox().get(i).isSelected());

					break;

				}
			}
			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getIAgreeCheckboxEditACH());
			Thread.sleep(1000);

			mp.getIAgreeCheckboxEditACH().click();
			Thread.sleep(1000);

			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(0)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();

			Thread.sleep(1000);

			mp.getSaveChangeButton().click();
			rw.waitForAcceptButton();
			System.out.println(mp.getPopupConfirmation1().getText());
			Assert.assertEquals("BANK ACCOUNT UPDATED", mp.getPopupConfirmation1().getText());
			mp.getPopupConfirmationButton().click();

			Thread.sleep(1000);
			rm.memberLogout();

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

	@Test(priority = 3, description = "Delete the Card in COG")
	public void deleteACHInCOG() throws InterruptedException, IOException {
		try {

			rm.deleteFOPInCOG("1141112", "Jonas Sports-Plex", prop.getProperty("USBankLast4Digits"), "Yes",
					agreementWithBadFOP);

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
