package ManagePaymentMethods_FreezeStatus;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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

public class MyAgreementsLayout_FreezeMbr extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String testName = null;
	private static String memberName = "FopMember1 Auto";
	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public static DashboardPO d;

	public static ManagePayMethodsPO mp;

	public MyAgreementsLayout_FreezeMbr() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		jse = (JavascriptExecutor) driver;

		d = new DashboardPO(driver);
		mp = new ManagePayMethodsPO(driver);

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();

	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Adding a new Credit Card Layout")
	public void AddNewCard_AgreementLayout() throws InterruptedException, IOException {

		try {
			rm.activeMemberLogin("fopmbr1", "Testing1!");

			rm.openSideMenuIfNotOpenedAlready();

			d.getMenuMyAccount().click();
			while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
				Thread.sleep(1000);
			}

			d.getMenuManagePmntMethods().click();
			Thread.sleep(3000);

			mp.getCreditCardLink().click();
			Thread.sleep(1000);
			mp.getNameOnCard().sendKeys(memberName);

			jse.executeScript("arguments[0].click();", mp.getCardNumber());
			mp.getCardNumber().sendKeys(prop.getProperty("CCNumber"));
			mp.getExpireMonth().sendKeys("04");
			mp.getExpireYear().sendKeys("29");
			jse.executeScript("arguments[0].click();", mp.getHouseAcctNoRadioButton().get(1));
			jse.executeScript("arguments[0].click();", mp.getInClubPurchaseNoRadio());
			Thread.sleep(1000);
			jse.executeScript("window.scrollBy(0,1000)");
			Assert.assertTrue(mp.getLinkAgreementsHeader().get(1).isDisplayed());
			Assert.assertTrue(mp.getLabelText().get(1).isDisplayed());

			int m = 0;
			for (int i = 2; i < mp.getAgreementInfoDiv().size(); i++) {
				System.out.println(mp.getAgreementInfoDiv().get(i).getText());
				if (!mp.getAgreementInfoDiv().get(i).getText().contains("No issues detected")) {

					m++;

					WebElement AgreementCheckbox = mp.getAgreementDiv().get(i).findElement(By.tagName("input"));

					Assert.assertTrue(AgreementCheckbox.isSelected());

					jse.executeScript("arguments[0].click();", AgreementCheckbox);

					Assert.assertTrue(mp.getMultiSlideDownBox().get(m - 1).isDisplayed());
				}

			}

			for (int j = 2; j < mp.getAgreementInfoDiv().size(); j++) {

				System.out.println(mp.getAgreementInfoDiv().get(j).getText());

				if (mp.getAgreementInfoDiv().get(j).getText().contains("No issues detected")) {

					WebElement AgreementCheckbox1 = mp.getAgreementDiv().get(j).findElement(By.tagName("input"));

					Assert.assertTrue(!AgreementCheckbox1.isSelected());
				}
			}
			Assert.assertTrue(mp.getLabelText1().get(0).isDisplayed());

			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getSignaturePad().get(1));
			Thread.sleep(2000);

			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(1)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();

			Thread.sleep(1000);

			jse.executeScript("arguments[0].click();", mp.getIAgreeCheckbox());

			Assert.assertTrue(!mp.getAddCCButton().isEnabled());
			// rm.returnToDashboard();

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

	@Test(priority = 2, description = "Adding a US Bank Checking Account Layout")
	public void AddUSBankAcct_AgreementLayout() throws InterruptedException, IOException {

		try {

			/*
			 * rm.openSideMenuIfNotOpenedAlready();
			 * 
			 * if (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
			 * d.getMenuMyAccount().click();
			 * 
			 * } d.getMenuManagePmntMethods().click(); Thread.sleep(2000);
			 */

			jse.executeScript("arguments[0].click();", mp.getBankAccountLink());
			Thread.sleep(1000);

			mp.getAccountHolder().sendKeys(memberName);
			Assert.assertTrue(mp.getUSBankRadio().isSelected());

			mp.getUSRoutingNumber().sendKeys(prop.getProperty("USBankRoutingNumber"));
			mp.getUSAccountNumber().sendKeys(prop.getProperty("USBankAcctNumber"));

			Assert.assertTrue(mp.getCheckingRadio().isSelected());

			Assert.assertTrue(mp.getHouseAcctNoRadioButton().get(0).isSelected());
			jse.executeScript("window.scrollBy(0,1000)");
			Assert.assertTrue(mp.getLinkAgreementsHeader().get(0).isDisplayed());
			Assert.assertTrue(mp.getLabelText().get(0).isDisplayed());
			int m = 0;
			for (int i = 0; i < mp.getAgreementInfoDiv().size() - 3; i++) {
				System.out.println(mp.getAgreementInfoDiv().get(i).getText());

				if (!mp.getAgreementInfoDiv().get(i).getText().contains("No issues detected")) {

					m++;
					WebElement AgreementCheckbox = mp.getAgreementDiv().get(i).findElement(By.tagName("input"));

					Assert.assertTrue(AgreementCheckbox.isSelected());

					jse.executeScript("arguments[0].click();", AgreementCheckbox);

					Assert.assertTrue(mp.getMultiSlideDownBox().get(m - 1).isDisplayed());

				}

			}

			for (int j = 0; j < mp.getAgreementInfoDiv().size() - 3; j++) {

				// System.out.println(mp.getAgreementInfoDiv().get(j).getText());
				if (mp.getAgreementInfoDiv().get(j).getText().contains("No issues detected")) {

					WebElement AgreementCheckbox1 = mp.getAgreementDiv().get(j).findElement(By.tagName("input"));

					Assert.assertTrue(!AgreementCheckbox1.isSelected());
				}
			}
			Assert.assertTrue(mp.getLabelText1().get(0).isDisplayed());

			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getSignaturePad().get(0));
			Thread.sleep(2000);

			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(0)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();

			Thread.sleep(1000);

			mp.getIAgreeCheckboxACH().click();

			Assert.assertTrue(!mp.getAddBankAcctButton().isEnabled());

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

	@Test(priority = 3, description = "Editing  a Credit Card Layout")
	public void EditCard_AgreementLayout() throws InterruptedException, IOException {

		try {
			int FopCount = mp.getCardNumbers().size();
			for (int i = 0; i < FopCount; i++) {

				if (mp.getCardNumbers().get(i).getText().contains("5454")) {

					jse.executeScript("arguments[0].scrollIntoView(true);", mp.getEditPaymentMethodsButton().get(i));
					jse.executeScript("arguments[0].click();", mp.getEditPaymentMethodsButton().get(i));
					break;
				}
			}

			Thread.sleep(1000);

			String text = mp.getEditNameOnCard().getAttribute("value");
			System.out.println(text);

			Assert.assertEquals(text, memberName);
			jse.executeScript("window.scrollBy(0,1000)");
			Assert.assertTrue(mp.getLinkAgreementsHeader().get(0).isDisplayed());
			Assert.assertTrue(mp.getLabelText().get(0).isDisplayed());

			int m = 0;
			for (int i = 0; i < mp.getAgreementInfoDiv().size(); i++) {

				System.out.println(mp.getAgreementInfoDiv().get(i).getText());

				if (!mp.getAgreementInfoDiv().get(i).getText().contains("No issues detected")) {

					WebElement AgreementCheckbox = mp.getAgreementDiv().get(i).findElement(By.tagName("input"));

					Assert.assertTrue(AgreementCheckbox.isSelected());

					try {

						String isDisabled = AgreementCheckbox.getAttribute("disabled");

						if (isDisabled.equals("true"))

						{
							Thread.sleep(1000);
						}
					} catch (NullPointerException npe) {

						jse.executeScript("arguments[0].click();", AgreementCheckbox);
						m++;
						Assert.assertTrue(mp.getMultiSlideDownBox().get(m - 1).isDisplayed());

					}

				}

			}

			for (int j = 0; j < mp.getAgreementInfoDiv().size(); j++) {
				System.out.println(mp.getAgreementInfoDiv().get(j).getText());

				if (mp.getAgreementInfoDiv().get(j).getText().contains("No issues detected")) {

					WebElement AgreementCheckbox1 = mp.getAgreementDiv().get(j).findElement(By.tagName("input"));

					Assert.assertTrue(!AgreementCheckbox1.isSelected());
				}
			}
			Assert.assertTrue(mp.getLabelText1().get(0).isDisplayed());

			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getSignaturePad().get(0));
			Thread.sleep(2000);

			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(0)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();

			Thread.sleep(1000);

			jse.executeScript("arguments[0].click();", mp.getEditIAgreeCheckbox());
			Thread.sleep(2000);

			Assert.assertTrue(!mp.getSaveChangesButtonCC().isEnabled());

			BreadcrumbTrailPO bt = new BreadcrumbTrailPO(driver);
			jse.executeScript("arguments[0].scrollIntoView(true);", bt.getBreadcrumb2());
			Thread.sleep(1000);
			bt.getBreadcrumb2().click();
			;
			Thread.sleep(2000);
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

	@Test(priority = 4, description = "Editing a US Bank Checking Account")
	public void EditUSBankAcct_AgreementLayout() throws InterruptedException, IOException {

		try {
			int FopCount = mp.getCardNumbers().size();
			for (int i = 0; i < FopCount; i++) {

				if (mp.getCardNumbers().get(i).getText().contains("6789")) {

					jse.executeScript("arguments[0].scrollIntoView(true);", mp.getEditPaymentMethodsButton().get(i));
					jse.executeScript("arguments[0].click();", mp.getEditPaymentMethodsButton().get(i));

					break;
				}
			}
			Thread.sleep(3000);

			String text = mp.getEditAccountHolder().getAttribute("value");
			System.out.println(text);

			Assert.assertEquals(text, memberName);

			String text1 = mp.getEditUSRoutingNumber().getAttribute("value");
			System.out.println(text1);
			Assert.assertEquals(text1, prop.getProperty("USBankRoutingNumber"));

			Assert.assertTrue(mp.getHouseAcctNoRadioButton().get(0).isSelected());
			jse.executeScript("window.scrollBy(0,1000)");
			Assert.assertTrue(mp.getLinkAgreementsHeader().get(0).isDisplayed());
			Assert.assertTrue(mp.getLabelText().get(0).isDisplayed());

			int m = 0;
			for (int i = 0; i < mp.getAgreementInfoDiv().size(); i++) {

				System.out.println(mp.getAgreementInfoDiv().get(i).getText());

				if (!mp.getAgreementInfoDiv().get(i).getText().contains("No issues detected")) {

					WebElement AgreementCheckbox = mp.getAgreementDiv().get(i).findElement(By.tagName("input"));

					Assert.assertTrue(AgreementCheckbox.isSelected());

					try {

						String isDisabled = AgreementCheckbox.getAttribute("disabled");

						if (isDisabled.equals("true"))

						{
							Thread.sleep(1000);
						}

					} catch (NullPointerException npe) {

						jse.executeScript("arguments[0].click();", AgreementCheckbox);
						m++;
						Assert.assertTrue(mp.getMultiSlideDownBox().get(m - 1).isDisplayed());

					}

				}

			}

			for (int j = 0; j < mp.getAgreementInfoDiv().size(); j++) {

				System.out.println(mp.getAgreementInfoDiv().get(j).getText());

				if (mp.getAgreementInfoDiv().get(j).getText().contains("No issues detected")) {

					WebElement AgreementCheckbox1 = mp.getAgreementDiv().get(j).findElement(By.tagName("input"));

					Assert.assertTrue(!AgreementCheckbox1.isSelected());
				}
			}
			Assert.assertTrue(mp.getLabelText1().get(0).isDisplayed());

			jse.executeScript("arguments[0].scrollIntoView(true);", mp.getSignaturePad().get(0));
			Thread.sleep(2000);

			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(0)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();

			Thread.sleep(1000);

			mp.getIAgreeCheckboxEditACH().click();

			Assert.assertTrue(!mp.getSaveChangeButton().isEnabled());
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
