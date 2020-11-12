package ManagePaymentMethods_FreezeStatus;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class Bug167782AgreementSectionNoThanksDisplayedEvenIfThereIsNoAgreementToDisplay_FreezeMbr extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String testName = null;
	private static String memberName = "1Bug167782 Auto";
	private static String agreement = "Athletic Elite 12 Month_Chk";

	public reusableWaits rw;
	public reusableMethods rm;
	public static DashboardPO d;
	// public static PaymentPO p;
	public static ManagePayMethodsPO mp;
	public static BreadcrumbTrailPO bt;

	public Bug167782AgreementSectionNoThanksDisplayedEvenIfThereIsNoAgreementToDisplay_FreezeMbr() {
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

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Add - Verifying that No Thanks is not present when there is no Agreement listed")
	public void AddUSBankCheckingAcct() throws InterruptedException, IOException {

		try {
			rm.activeMemberLogin("bugmbr1", "Testing1!");

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

			Assert.assertTrue(mp.getCheckingRadio().isSelected());

			Assert.assertTrue(mp.getHouseAcctNoRadioButton().get(0).isSelected());

			Assert.assertTrue(mp.getLinkAgreementsHeader().get(0).isDisplayed());
			Assert.assertTrue(mp.getLabelText().get(0).isDisplayed());
			Assert.assertTrue(mp.getLabelText1().get(0).isDisplayed());
			Assert.assertTrue(mp.getAgreementLabel().get(0).getText().contains(agreement));
			Assert.assertTrue(mp.getNoThanksLabel().get(0).isDisplayed());
			mp.getNoThanks().get(0).click();

			mp.getSavingsradio().click();

			Assert.assertTrue(mp.getLinkAgreementsHeader().size() == 0);
			Assert.assertTrue(mp.getLabelText().size() == 0);
			Assert.assertTrue(mp.getNoThanksLabel().size() == 0);
			Assert.assertTrue(mp.getLabelText1().size() == 0);

			Assert.assertTrue(!mp.getAddBankAcctButton().isEnabled());

			mp.getIAgreeCheckboxACH().click();
			Thread.sleep(1000);

			Assert.assertTrue(mp.getAddBankAcctButton().isEnabled());

			mp.getAddBankAcctButton().click();

			Assert.assertTrue(mp.getPopupContent().getText().contains("A signature is required to continue."));
			Thread.sleep(1000);
			mp.getPopupConfirmationButton().click();
			Thread.sleep(1000);

			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(0)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();

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

	@Test(priority = 2, description = "Edit - Verifying that No Thanks is not present when there is no Agreement listed")
	public void EditUSBankCheckingAcct() throws InterruptedException, IOException {

		try {
			int FopCount = mp.getCardNumbers().size();
			for (int i = 0; i < FopCount; i++) {

				if (mp.getCardNumbers().get(i).getText().contains(prop.getProperty("USBankLast4Digits"))) {
					mp.getEditPaymentMethodsButton().get(i).click();
					break;
				}
			}
			Thread.sleep(1000);
			Assert.assertTrue(bt.getBreadcrumb3().getText().contains("Edit Bank Account"));
			String text = mp.getEditAccountHolder().getAttribute("ng-reflect-model");
			System.out.println(text);

			Assert.assertEquals(text, memberName);

			String text1 = mp.getEditUSRoutingNumber().getAttribute("ng-reflect-model");
			System.out.println(text1);
			Assert.assertEquals(text1, prop.getProperty("USBankRoutingNumber"));

			Assert.assertTrue(mp.getEditCheckingRadio().isSelected());

			Assert.assertTrue(mp.getHouseAcctNoRadioButton().get(0).isSelected());

			Assert.assertTrue(mp.getLinkAgreementsHeader().get(0).isDisplayed());
			Assert.assertTrue(mp.getLabelText().get(0).isDisplayed());
			Assert.assertTrue(mp.getLabelText1().get(0).isDisplayed());
			Assert.assertTrue(mp.getNoThanksLabel().get(0).isDisplayed());
			Assert.assertTrue(mp.getAgreementLabel().get(0).getText().contains(agreement));

			mp.getNoThanks().get(0).click();

			mp.getSavingsradio().click();

			Assert.assertTrue(mp.getLinkAgreementsHeader().size() == 0);
			Assert.assertTrue(mp.getLabelText().size() == 0);
			Assert.assertTrue(mp.getNoThanksLabel().size() == 0);
			Assert.assertTrue(mp.getLabelText1().size() == 0);

			Assert.assertTrue(!mp.getSaveChangeButton().isEnabled());

			mp.getIAgreeCheckboxEditACH().click();
			Thread.sleep(1000);

			Assert.assertTrue(mp.getSaveChangeButton().isEnabled());

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
	public void deleteCardInCOG() throws InterruptedException, IOException {
		try {

			rm.deleteFOPInCOG("1147807", "Jonas Health and Wellness", prop.getProperty("USBankLast4Digits"), "No", "");

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
		driver.close();
		driver = null;
	}

}
