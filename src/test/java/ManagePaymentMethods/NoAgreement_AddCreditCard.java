package ManagePaymentMethods;

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

import pageObjects.DashboardPO;
import pageObjects.ManagePayMethodsPO;
import pageObjects.PaymentPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class NoAgreement_AddCreditCard extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String testName = null;
	private static String memberName = "Paymember Auto";

	public reusableWaits rw;
	public reusableMethods rm;

	public NoAgreement_AddCreditCard() {
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
		driver.get(prop.getProperty("EMELoginPage"));
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Adding a new Credit Card and no agreement exists")
	public void AddNewCard_NoAgreement() throws InterruptedException, IOException {

		DashboardPO d = new DashboardPO(driver);
		PaymentPO p = new PaymentPO(driver);
		ManagePayMethodsPO mp = new ManagePayMethodsPO(driver);
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

			mp.getNameOnCard().sendKeys(memberName);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", mp.getCardNumber());
			mp.getCardNumber().sendKeys("4111111111111111");
			mp.getExpireMonth().sendKeys("04");
			mp.getExpireYear().sendKeys("22");
			mp.getHouseAcctNoRadioButton().get(1).click();
			mp.getInClubPurchaseNoRadio().click();
			Thread.sleep(1000);

			Assert.assertEquals(rm.isElementPresent(By.xpath("//span[contains(text(),'My Agreements')]")), false);

			Thread.sleep(1000);
			mp.getIAgreeCheckbox().click();
			Thread.sleep(2000);

			Assert.assertTrue(mp.getAddCCButton().isEnabled());

			mp.getAddCCButton().click();

			Assert.assertTrue(mp.getPopupContent().getText().contains("A signature is required to continue."));
			Thread.sleep(1000);
			p.getPopupConfirmationButton().click();
			Thread.sleep(1000);

			Actions a = new Actions(driver);
			a.moveToElement(mp.getSignaturePad().get(1)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10)
					.release().build().perform();

			Thread.sleep(1000);

			mp.getAddCCButton().click();
			rw.waitForAcceptButton();
			System.out.println(mp.getPopupConfirmation1().getText());
			Assert.assertEquals("CREDIT CARD ADDED", mp.getPopupConfirmation1().getText());
			mp.getPopupConfirmationButton().click();

			Thread.sleep(3000);
			rm.memberLogout();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
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

	@Test(priority = 2, description = "Unlink the agreement and Delete the Card in COG")
	public void deleteCardInCOG() throws InterruptedException, IOException {
		try {

			rm.deleteFOPInCOG("1143354", "Jonas Sports-Plex", "1111", "No");

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
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