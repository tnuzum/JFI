package ManagePaymentMethods_FreezeStatus;

import java.io.IOException;
import java.lang.reflect.Method;

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
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class CheckAgrmnt_AgrmntWithBadFOP_AddEditCreditCard_FreezeMbr extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String testName = null;
	private static String memberName = "BadFopMbr1 Auto";
	private static String agreement = "Balance Weight Loss 12 Week";

	public reusableWaits rw;
	public reusableMethods rm;

	public static DashboardPO d;
	public static ManagePayMethodsPO mp;
	public static BreadcrumbTrailPO bt;
	private static JavascriptExecutor jse;

	public CheckAgrmnt_AgrmntWithBadFOP_AddEditCreditCard_FreezeMbr() {
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

	@Test(priority = 1, description = "Adding a new Credit Card and linking the card to agreement with Bad FOP")
	public void AddNewCard_SelectAgreementWithBadFOP() throws InterruptedException, IOException {

		try {
			rm.activeMemberLogin("badfopmbr1", "Testing1!");

			rm.openSideMenuIfNotOpenedAlready();

			d.getMenuMyAccount().click();
			while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
				Thread.sleep(1000);
			}

			d.getMenuManagePmntMethods().click();
			Thread.sleep(2000);

			mp.getNameOnCard().sendKeys(memberName);
			jse.executeScript("arguments[0].click();", mp.getCardNumber());
			mp.getCardNumber().sendKeys("4111111111111111");
			mp.getExpireMonth().sendKeys("04");
			mp.getExpireYear().sendKeys("22");
			jse.executeScript("arguments[0].click();", mp.getHouseAcctNoRadioButton().get(1));
			jse.executeScript("arguments[0].click();", mp.getInClubPurchaseNoRadio());
			Thread.sleep(3000);

			Assert.assertTrue(mp.getLinkAgreementsHeader().get(1).isDisplayed());
			Assert.assertTrue(mp.getLabelText().get(1).isDisplayed());

			Assert.assertTrue(!mp.getAddCCButton().isEnabled());

			for (int i = 0; i < mp.getAgreementLabel().size(); i++) {
				if (mp.getAgreementLabel().get(i).getText().contains(agreement)) {
					System.out.println(mp.getAgreementLabel().get(i).getText());
					Assert.assertTrue(mp.getAgreementCheckBox().get(i).isSelected());
					break;
				}
			}

			Assert.assertEquals(rm.isElementPresent(By.xpath("//div[contains(text(),'A selection is required')]")),
					false);

			Thread.sleep(1000);
			jse.executeScript("arguments[0].click();", mp.getIAgreeCheckbox());
			Thread.sleep(2000);

			Assert.assertTrue(mp.getAddCCButton().isEnabled());

			jse.executeScript("arguments[0].click();", mp.getAddCCButton());

			Assert.assertTrue(mp.getPopupContent().getText().contains("A signature is required to continue."));
			Thread.sleep(1000);
			mp.getPopupConfirmationButton().click();
			Thread.sleep(1000);
			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getSignaturePad().get(1));
			Thread.sleep(2000);
			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(1)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();

			Thread.sleep(1000);

			jse.executeScript("arguments[0].click();", mp.getAddCCButton());
			rw.waitForAcceptButton();
			System.out.println(mp.getPopupConfirmation1().getText());
			Assert.assertEquals("CREDIT CARD ADDED", mp.getPopupConfirmation1().getText());
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

	@Test(priority = 2, description = "Editing  a Credit Card ")
	public void EditCard() throws InterruptedException, IOException {

		try {
			int FopCount = mp.getCardNumbers().size();
			for (int i = 0; i < FopCount; i++) {

				if (mp.getCardNumbers().get(i).getText().contains(prop.getProperty("CCLast4Digits"))) {
					jse.executeScript("arguments[0].click();", mp.getEditPaymentMethodsButton().get(i));
					break;
				}
			}
			Thread.sleep(1000);
			Assert.assertTrue(bt.getBreadcrumb3().getText().contains("Edit Card"));
			String text = mp.getEditNameOnCard().getAttribute("ng-reflect-model");
			System.out.println(text);

			Assert.assertEquals(text, memberName);

			jse.executeScript("arguments[0].click();", mp.getHouseAcctNoRadioButton().get(0));
			jse.executeScript("arguments[0].click();", mp.getInClubPurchaseNoRadio());
			Thread.sleep(1000);

			Assert.assertTrue(mp.getLinkAgreementsHeader().get(0).isDisplayed());
			Assert.assertTrue(mp.getLabelText().get(0).isDisplayed());

			Assert.assertTrue(!mp.getSaveChangesButtonCC().isEnabled());

			for (int i = 0; i < mp.getAgreementLabel().size(); i++) {
				if (mp.getAgreementLabel().get(i).getText().contains(agreement)) {

					jse.executeScript("arguments[0].scrollIntoView(true);", mp.getAgreementCheckBox().get(i));
					Assert.assertTrue(mp.getAgreementCheckBox().get(i).isSelected());
//					getScreenshot(testName + "agreementclicked", driver);
					break;

				}
			}

			Assert.assertEquals(rm.isElementPresent(By.xpath("//div[contains(text(),'A selection is required')]")),
					false);

			Thread.sleep(1000);
			jse.executeScript("arguments[0].click();", mp.getEditIAgreeCheckbox());
			Thread.sleep(2000);

			Assert.assertTrue(mp.getSaveChangesButtonCC().isEnabled());

			jse.executeScript("arguments[0].click();", mp.getSaveChangesButtonCC());

			Assert.assertTrue(mp.getPopupContent().getText().contains("A signature is required to continue."));
			Thread.sleep(1000);
			mp.getPopupConfirmationButton().click();
			Thread.sleep(1000);

			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(0)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();

			Thread.sleep(1000);

			jse.executeScript("arguments[0].click();", mp.getSaveChangesButtonCC());
			rw.waitForAcceptButton();
			System.out.println(mp.getPopupConfirmation1().getText());
			Assert.assertEquals("CARD UPDATED", mp.getPopupConfirmation1().getText());
			mp.getPopupConfirmationButton().click();

			Thread.sleep(3000);

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

	@Test(priority = 3, description = "Unlink the agreement and Delete the Card in COG")
	public void deleteCardInCOG() throws InterruptedException, IOException {
		try {

			rm.deleteFOPInCOG("1147808", "Jonas Health and Wellness", "1111", "Yes", agreement);

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
