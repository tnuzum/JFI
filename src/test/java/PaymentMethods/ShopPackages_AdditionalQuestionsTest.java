package PaymentMethods;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ShopPackagesPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ShopPackages_AdditionalQuestionsTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	public reusableWaits rw;
	public reusableMethods rm;
	public static DashboardPO d;
	public static PaymentMethodsPO PM;
	public static ShopPackagesPO sp;
	private static PurchaseConfirmationPO PP;

	public ShopPackages_AdditionalQuestionsTest() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

	@BeforeClass
	public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		d = new DashboardPO(driver); // Define the driver for Dash Board page Objects
		PM = new PaymentMethodsPO(driver);
		sp = new ShopPackagesPO(driver);
		PP = new PurchaseConfirmationPO(driver);

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		driver.get(prop.getProperty("EMELoginPage"));

		rm.activeMemberLogin("aqmember", "Testing1!"); // Login to EME
		rw.waitForDashboardLoaded();

	}

	@Test(priority = 1)
	public void verifyAdditionalQuestionsOnPurchaseWithNewCard() throws InterruptedException, IOException {
		try {
			rm.openSideMenuIfNotOpenedAlready();

			d.getMenuShopPackages().click();

			while (!sp.getPackagesList().isDisplayed()) {
				Thread.sleep(1000);
				System.out.println("Waiting for the packages to be displayed");
			}

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(sp.getPackagesList()));
			wait.until(ExpectedConditions.visibilityOf(sp.getWarningMsg()));
			sp.getKeyWord().sendKeys("Service V");

			for (int i = 0; i < sp.getPackageNames().size(); i++)

			{
				if (sp.getPackageNames().get(i).getText().equals("Service V"))

				{
					sp.getPurchaseButtons().get(i).click();
					break;
				}

			}

			wait.until(ExpectedConditions.textToBePresentInElement(PP.getShopPackageTotalAmount(), "$"));
			Thread.sleep(5000);

			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			PM.getNewCardButton().click();
			Thread.sleep(3000);

			String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			while (opacity.contains("1")) {
				PM.getNewCardButton().click();
				opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			}

			wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

			wait.until(ExpectedConditions.attributeToBe(PM.getPaymentButton(), "disabled", "true"));

			PM.getCardNumberField().sendKeys("4111111111111111");
			PM.getExpirationMonth().sendKeys("04");
			PM.getExpirationYear().sendKeys("22");
			PM.getSecurityCode().sendKeys("123");

			Assert.assertTrue(PM.getSaveCardQuestion().isDisplayed());
			PM.getMoreInfoSaveCard().click();
			Thread.sleep(500);
			Assert.assertEquals(PM.getAdditionalQuestionPopupTitle().getText(), "Save Card For Use On Site");
			PM.getAdditionalQuestionPopupClose().click();
			Thread.sleep(500);

			Assert.assertTrue(PM.getOnAccountQuestion().isDisplayed());
			PM.getMoreInfoOnAccount().click();
			Thread.sleep(500);
			Assert.assertEquals(PM.getAdditionalQuestionPopupTitle().getText(), "On Account Charges");
			PM.getAdditionalQuestionPopupClose().click();
			Thread.sleep(500);

			Assert.assertTrue(PM.getInClubQuestion().isDisplayed());
			PM.getMoreInfoUseInPos().click();
			Thread.sleep(500);
			Assert.assertEquals(PM.getAdditionalQuestionPopupTitle().getText(), "Card On File");
			PM.getAdditionalQuestionPopupClose().click();
			Thread.sleep(500);

			PM.getSaveCardYes().click();
			PM.getHouseAcctNo().click();
			PM.getInClubPurchaseNo().click();

			PM.getCheckBox().click();
			Thread.sleep(1000);

			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}

			// Clicks on the Pay button without signature
			PM.getPaymentButton().click();
			Thread.sleep(1000);

			System.out.println(PM.getPopupContent().getText());
			Assert.assertTrue(PM.getPopupContent().getText().contains("A signature is required to continue."));
			PM.getPopupOk().click();
			Thread.sleep(1000);

			Actions a = new Actions(driver);
			a.moveToElement(PM.getSignaturePad()).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10).release()
					.build().perform();

			PM.getPaymentButton().click();

			wait.until(ExpectedConditions.visibilityOf(PP.getPopupOKButton()));
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));

			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			rm.returnToDashboard();
			rm.memberLogout();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ae.getMessage(), ae);
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

	@Test(priority = 2, description = "Delete the Card in COG")
	public void deleteCardInCOG() throws InterruptedException, IOException {
		try {

			rm.deleteFOPInCOG("1143515", "Jonas Sports-Plex", "1111", "No", "");

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ae.getMessage(), ae);
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

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
