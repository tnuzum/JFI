package ShopPackages;

import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ShopPackagesPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ShopAndPurchasePackagesOnAccount extends base {

	private static DashboardPO d;
	private static ShopPackagesPO sp;
	private static PaymentMethodsPO PM;
	private static PurchaseConfirmationPO PP;
	private static ThankYouPO TY;
	private static BreadcrumbTrailPO bt;
	private static AcctHistoryPO ahp;
	private static String testName = null;
	private static JavascriptExecutor jse;
	private static String packageName;

	public reusableWaits rw;
	public reusableMethods rm;

	public ShopAndPurchasePackagesOnAccount() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

	@BeforeClass
	public void initialize() throws InterruptedException, IOException {

		try {

			driver = initializeDriver();

		} catch (java.lang.NullPointerException npe) {

			driver = initializeDriver();

			System.out.println("driver initialized again");
			log.error("driver initialized again");
			npe.printStackTrace();
			log.error(npe.getMessage(), npe);

		}

		catch (org.openqa.selenium.WebDriverException we) {

			driver = initializeDriver();

			System.out.println("driver initialized again");
			log.error("driver initialized again");
			we.printStackTrace();
			log.error(we.getMessage(), we);

		}

		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
		d = new DashboardPO(driver);
		sp = new ShopPackagesPO(driver);
		PM = new PaymentMethodsPO(driver);
		PP = new PurchaseConfirmationPO(driver);
		TY = new ThankYouPO(driver);
		ahp = new AcctHistoryPO(driver);
		bt = new BreadcrumbTrailPO(driver);
		jse = (JavascriptExecutor) driver;

		packageName = prop.getProperty("packageName");

	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Confirming Add To Cart button text changed to Purchase and Page Header name Validation")
	public void PurchaseBtnNameCheck() throws IOException, InterruptedException {

		rm.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));
		rw.waitForDashboardLoaded();
		Thread.sleep(2000);

		rm.openSideMenuIfNotOpenedAlready();

		d.getMenuShopPackages().click();

		while (!sp.getPackagesList().isDisplayed()) {
			Thread.sleep(1000);
			System.out.println("Waiting for the packages to be displayed");
		}

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(sp.getPackagesList()));
		wait.until(ExpectedConditions.visibilityOf(sp.getWarningMsg()));

//		   System.out.println(sp.getPurchaseButtons().size());
		for (int i = 0; i < sp.getPurchaseButtons().size(); i++) {
			Assert.assertEquals("Purchase", sp.getPurchaseButtons().get(i).getText());
		}
		Assert.assertEquals("Shop Packages", sp.getPageHeader().getText());
		Assert.assertEquals("Dashboard", bt.getBreadcrumb1().getText()); // sp.getBreadcrumbDashboard().getText());
		Assert.assertEquals("Shop", bt.getBreadcrumb2().getText()); // sp.getBreadcrumbShop().getText());

	}

	@Test(priority = 2, description = "Confirming Filter works")
	public void KeywordFilterCheck() throws IOException, InterruptedException {

		sp.getKeyWord().sendKeys("ServiceOA");
		/*
		 * System.out.println(sp.getPackageNames().size());
		 * System.out.println(sp.getPackagesections().size());
		 */
		for (int i = 0; i < sp.getPackageNames().size(); i++) {
			Assert.assertTrue(sp.getPackageNames().get(i).getText().contains("Service"));
		}

	}

	@Test(priority = 3, description = "Confirming correct package is selected")
	public void SelectPackage() throws InterruptedException {

		for (int i = 0; i < sp.getPackageNames().size(); i++)

		{
			if (sp.getPackageNames().get(i).getText().contains(packageName))

			{
				jse.executeScript("arguments[0].click();", sp.getPurchaseButtons().get(i));
				break;
			}

		}

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.textToBePresentInElement(PP.getShopPackageTotalAmount(), "$"));
		Thread.sleep(3000);
		Assert.assertEquals("ServiceOA", PP.getPackageName().getText());
	}

	@Test(priority = 4, description = "Page Layout Validation")

	public void PageLayoutValidation() throws IOException, InterruptedException {

		try {
			Assert.assertEquals("Dashboard", bt.getBreadcrumb1().getText());
			Assert.assertEquals("Shop", bt.getBreadcrumb2().getText());
			Assert.assertEquals("Confirm", bt.getBreadcrumb3().getText());
			Boolean ReviewLabelPresent = rm.isElementPresent(By.xpath("//div[@class = 'rate-box']/h2"));
			Assert.assertTrue(ReviewLabelPresent);
			Assert.assertEquals("Review", PP.getReviewLabel().getText());
			Boolean FeesLabelPresent = rm.isElementPresent(By.xpath("//small[contains(text(),'Fee(s)')]"));
			Assert.assertTrue(FeesLabelPresent);
			Boolean SubTotalLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'SUB-TOTAL:')]"));
			Assert.assertTrue(SubTotalLabelPresent);
			Boolean TaxLabelPresent = rm.isElementPresent(By.xpath("//strong[contains(text(),'TAX:')]"));
			Assert.assertTrue(TaxLabelPresent);
			Boolean TotalLabelPresent = rm.isElementPresent(By.xpath("//h2[contains(text(),'TOTAL:')]"));
			Assert.assertTrue(TotalLabelPresent);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			//// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			//// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			//// Assert.fail(eci.getMessage());
		}

	}

	@Test(priority = 5, description = "Payment Method OnAccount is selected by default")

	public void OnAccountIsSelectedByDefault() {

		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
					.contains(" On Account"))
				Assert.assertTrue(PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).isEnabled());
		}
	}

	@Test(priority = 6, description = "Payment Method is OnAccount")
	public void PurchaseOnAccount() throws InterruptedException, IOException {

		WebDriverWait wait = new WebDriverWait(driver, 30);

		try {

			// Noting down the total amount
//		System.out.println(PP.getTotalAmount().getText());
			wait.until(ExpectedConditions.textToBePresentInElement(PP.getShopPackageTotalAmount(), "$"));
			String[] totalAmt = PP.getShopPackageTotalAmount().getText().split(": ");
			String FormatTotalAmt = totalAmt[1].trim();
//		System.out.println(FormatTotalAmt);

			// Noting down the Package Units before purchasing
			int IntUnitCountBefore = 0;
			int IntUnitCountAfter = 0;

			IntUnitCountBefore = rm.getPackageUnits("ServiceOA");
			System.out.println("Before: " + IntUnitCountBefore);

			// Verifies the Pay button contains the total amount
			Assert.assertTrue(PM.getPaymentButton().getText().contains(FormatTotalAmt));

			while (!PM.getOnAccountAndSavedCards().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			// Click the Pay button
			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}

			jse.executeScript("arguments[0].click();", PM.getPaymentButton());

			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));

			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			// Verifies the text on Thank You page and the links to navigate to Dashboard
			// and other pages are displayed
			rm.ThankYouPageValidations();

			// Note down the Receipt number
			String receiptNumber = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber));

			// Verifies the buttons on Print Receipt Popup
			rm.ReceiptPopupValidations();

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
			Thread.sleep(2000);

			// Navigate to Dashboard
			int count = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

				{
					// rw.linksToBeClickable();
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
					break;
				}

			}
			rw.waitForDashboardLoaded();
			// Verifies the link navigates to the right page
			Assert.assertEquals("Dashboard", driver.getTitle());
			Thread.sleep(2000);

			// Note the package units after purchase
			IntUnitCountAfter = rm.getPackageUnits("ServiceOA");
			System.out.println("after: " + IntUnitCountAfter);

			// Verifies the package units is now incremented by one unit
			IntUnitCountBefore++;
			Assert.assertEquals(IntUnitCountBefore, IntUnitCountAfter); // verifies the unit count of the Package

			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.xpath("//div[(contains@class, 'swal2-center')]")));
			jse.executeScript("arguments[0].click();", d.getMyAccountAccountHistory());
			rm.myProfileLogin(prop.getProperty("activeMember6_username"), "Testing1!");

			while (ahp.getSearchingAcctHistMessage().size() != 0) {
				System.out.println("waiting for account history to display");
				Thread.sleep(1000);
			}
			Thread.sleep(3000);

			while (!ahp.getReceiptNumberTable().isDisplayed()) {
				Thread.sleep(2000);
				System.out.println("waiting");
			}

			wait.until(ExpectedConditions.visibilityOf(ahp.getReceiptNumberTable()));

			// Clicks on the Receiptnumber in Account History

			ahp.getSearchField().sendKeys(receiptNumber);

			Thread.sleep(3000);
//			wait.until(ExpectedConditions.textToBePresentInElement(ahp.getReceiptNumber(), receiptNumber));
//			jse.executeScript("arguments[0].click();", ahp.getReceiptNumbers().get(0));
//			Thread.sleep(3000);
//			jse.executeScript("arguments[0].scrollIntoView(true);",
//					TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-12 text-right']")));
//
//			// Verifies the amount in the receipt is the same as it was displayed on the
//			// Purchase Packages page
//
//			while (TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-12 text-right']")).getText()
//					.isBlank()) {
//				Thread.sleep(500);
//			}
//			System.out
//					.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-12 text-right']")).getText());
//			Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-12 text-right']")).getText()
//					.contains(FormatTotalAmt));
//			jse.executeScript("window.scrollBy(0,500)");
//			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
//			Thread.sleep(2000);
			rm.memberLogout();
		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			//// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			//// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			//// Assert.fail(eci.getMessage());

		}
	}

	@AfterClass

	public void teardown() throws InterruptedException {

		driver.quit();
		driver = null;

	}

}