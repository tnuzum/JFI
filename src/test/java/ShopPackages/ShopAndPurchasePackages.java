package ShopPackages;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ShopPackagesPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ShopAndPurchasePackages extends base {
	
	private static DashboardPO d;
	private static ShopPackagesPO sp;
	private static PaymentMethodsPO PM;
	private static PurchaseConfirmationPO PP;
	private static ThankYouPO TY;
	private static AcctHistoryPO ahp;
	private static String testName = null;
	
	@BeforeClass
	public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
		d = new DashboardPO(driver);
		sp = new ShopPackagesPO(driver);
		PM = new PaymentMethodsPO(driver);
		PP = new PurchaseConfirmationPO(driver);
		TY = new ThankYouPO(driver);
		ahp = new AcctHistoryPO(driver);

	}
	 @BeforeMethod
	public void GetTestMethodName(Method method)
		    {
		         testName = method.getName(); 
		        
		    }

	@Test(priority = 1, description = "Confirming Add To Cart button text changed to Purchase and Page Header name Validation")
	public void PurchaseBtnNameCheck() throws IOException, InterruptedException {
		try {

		reusableMethods.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));
		Thread.sleep(2000);
		
		d.getMenuShopPackages().click();
				
		while (!sp.getPackagesList().isDisplayed())
		{
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
		Assert.assertEquals("Dashboard", sp.getBreadcrumbDashboard().getText());
		Assert.assertEquals("Shop", sp.getBreadcrumbShop().getText());

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName);
			log.error(ae.getMessage(), ae);
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName);
			log.error(eci.getMessage(), eci);
			reusableMethods.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}

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
			if (sp.getPackageNames().get(i).getText().contains("ServiceOA"))

			{
				sp.getPurchaseButtons().get(i).click();
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
		Assert.assertEquals("Dashboard", PP.getBreadcrumbDashboard().getText());
		Assert.assertEquals("Shop", PP.getBreadcrumbShop().getText());
		Assert.assertEquals("Confirm", PP.getBreadcrumbConfirm().getText());
		Boolean ReviewLabelPresent = reusableMethods.isElementPresent(By.xpath("//div[@class = 'rate-box']/h2"));
		Assert.assertTrue(ReviewLabelPresent);
		Assert.assertEquals("Review", PP.getReviewLabel().getText());
		Boolean FeesLabelPresent = reusableMethods.isElementPresent(By.xpath("//small[contains(text(),'Fee(s)')]"));
		Assert.assertTrue(FeesLabelPresent);
		Boolean SubTotalLabelPresent = reusableMethods
				.isElementPresent(By.xpath("//strong[contains(text(),'SUB-TOTAL:')]"));
		Assert.assertTrue(SubTotalLabelPresent);
		Boolean TaxLabelPresent = reusableMethods.isElementPresent(By.xpath("//strong[contains(text(),'TAX:')]"));
		Assert.assertTrue(TaxLabelPresent);
		Boolean TotalLabelPresent = reusableMethods.isElementPresent(By.xpath("//h2[contains(text(),'TOTAL:')]"));
		Assert.assertTrue(TotalLabelPresent);
		

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName);
			log.error(ae.getMessage(), ae);
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName);
			log.error(eci.getMessage(), eci);
			reusableMethods.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}

	}

	@Test(priority = 5, description = "Payment Method OnAccount is selected by default")

	public void OnAccountIsSelectedByDefault() {
		
		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
					.contains(" On Account"))
				Assert.assertTrue(PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).isSelected());
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

		//Noting down the Package Units before purchasing
		int IntUnitCountBefore = 0;
		int IntUnitCountAfter = 0;

		IntUnitCountBefore = reusableMethods.getPackageUnits("ServiceOA");
//		System.out.println(IntUnitCountBefore);

		//Verifies the Pay button contains the total amount
				Assert.assertTrue(PM.getPaymentButton().getText().contains(FormatTotalAmt));
				
				while(!PM.getOnAccountAndSavedCards().isDisplayed())
					
				{
					Thread.sleep(1000);;
				}
				
				//Click the Pay button
				while (!PM.getPaymentButton().isEnabled())
				{
					Thread.sleep(1000);
				}
				PM.getPaymentButton().click();

				wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));

		//Verifies the success message
		Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
		PP.getPopupOKButton().click();
		Thread.sleep(1000);
				
		//Verifies the text on Thank You page and the links to navigate to Dashboard and other pages are displayed
		reusableMethods.ThankYouPageValidations();

		//Note down the Receipt number
		String receiptNumber = TY.getReceiptNumber().getText();
				
		Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
		TY.getPrintReceiptButton().click();
		Thread.sleep(2000);
		Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
		Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber));
		
		//Verifies the buttons on Print Receipt Popup
		reusableMethods.ReceiptPopupValidations();

		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
		Thread.sleep(2000);

		
		//Navigate to Dashboard
		int count = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < count; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

			{
				// reusableWaits.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}
		reusableWaits.waitForDashboardLoaded();
		//Verifies the link navigates to the right page
		Assert.assertEquals("Dashboard", driver.getTitle());
		Thread.sleep(2000);
		
		//Note the package units after purchase
		IntUnitCountAfter = reusableMethods.getPackageUnits("ServiceOA");
//		System.out.println(IntUnitCountAfter);
			
		//Verifies the package units is now incremented by one unit
		IntUnitCountBefore++;
		Assert.assertEquals(IntUnitCountBefore, IntUnitCountAfter); // verifies the unit count of the Package

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[(contains@class, 'swal2-center')]")));
		d.getMyAccountAccountHistory().click();
		Thread.sleep(3000);
			
		while(!ahp.getReceiptNumberTable().isDisplayed())
		{
			Thread.sleep(2000);	
			System.out.println("waiting");
		}
		
		wait.until(ExpectedConditions.visibilityOf(ahp.getReceiptNumberTable()));
		
		//Clicks on the Receiptnumber in Account History 
		
		ahp.getSearchField().sendKeys(receiptNumber);
		
		Thread.sleep(3000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-md-3 hidden-sm hidden-xs']//a")));
		ahp.getReceiptNumber().click();
		Thread.sleep(1000);

		//Verifies the amount in the receipt is the same as it was displayed on the Purchase Packages page
		
		while (TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText().isBlank())
		{
			Thread.sleep(500);
		}
		System.out.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText());
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
				.contains(FormatTotalAmt));
		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
		Thread.sleep(2000);
		
		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName);
			log.error(ae.getMessage(), ae);
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName);
			log.error(eci.getMessage(), eci);
			reusableMethods.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}
		
		finally {
		reusableMethods.memberLogout();
		}
	}

	@Test(priority = 7, description = "Payment Method is Stored Card")
	public void PurchaseStoredCard() throws InterruptedException, IOException {
		try {
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember7_username"), prop.getProperty("activeMember7_password"));
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, 30);
				
		
		d.getMenuShopPackages().click();
		
		while (!sp.getPackagesList().isDisplayed())
		{
			Thread.sleep(1000);
			System.out.println("Waiting for the packages to be displayed");
		}
		wait.until(ExpectedConditions.visibilityOf(sp.getPackagesList()));
		wait.until(ExpectedConditions.visibilityOf(sp.getWarningMsg()));

//		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'row m-t-md']")));
		
		sp.getKeyWord().sendKeys("ServiceCC");
		
				
		for (int i = 0; i < sp.getPackageNames().size(); i++)

		{
			if (sp.getPackageNames().get(i).getText().equals("ServiceCC"))

			{
				sp.getPurchaseButtons().get(i).click();
				break;
			}

		}
		
		
		wait.until(ExpectedConditions.textToBePresentInElement(PP.getShopPackageTotalAmount(), "$"));
		Thread.sleep(3000);
		Assert.assertEquals("ServiceCC", PP.getPackageName().getText());
		
		while(!PM.getOnAccountAndSavedCards().isDisplayed())
			
		{
			Thread.sleep(1000);;
		}
		
		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
					.contains("5454"))
				{
				
					PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).click();
					break;
				}
		}
	

		// Noting down the total amount
		
		wait.until(ExpectedConditions.textToBePresentInElement(PP.getShopPackageTotalAmount(), "$"));
//		System.out.println(PP.getTotalAmount().getText());
		String[] totalAmt1 = PP.getShopPackageTotalAmount().getText().split(": ");
		String FormatTotalAmt1 = totalAmt1[1].trim();
		System.out.println(FormatTotalAmt1);

		//Noting down the Package Units before purchasing
		int IntUnitCountBefore1 = 0;
		int IntUnitCountAfter1 = 0;

		IntUnitCountBefore1 = reusableMethods.getPackageUnits("ServiceCC");
//		System.out.println(IntUnitCountBefore1);

		//Verifies the Pay button contains the total amount
		Assert.assertTrue(PM.getPaymentButton().getText().contains(FormatTotalAmt1));
		
		//Click the Pay button
		while (!PM.getPaymentButton().isEnabled())
		{
			Thread.sleep(1000);
		}
		PM.getPaymentButton().click();

		
		wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));

				//Verifies the success message
				Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
				PP.getPopupOKButton().click();
				Thread.sleep(1000);
								
				//Verifies the text on Thank You page and the links to navigate to Dashboard and other pages are displayed
				reusableMethods.ThankYouPageValidations();

				//Note down the Receipt number
				String receiptNumber2 = TY.getReceiptNumber().getText();
								
				Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
				TY.getPrintReceiptButton().click();
				Thread.sleep(2000);
				Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
				Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber2));
				
				//Verifies the buttons on Print Receipt Popup
				reusableMethods.ReceiptPopupValidations();

				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
				Thread.sleep(2000);

				
				//Navigate to Select Classes
				int count1 = driver.findElements(By.tagName("a")).size();
				for (int i = 0; i < count1; i++) {
					if (driver.findElements(By.tagName("a")).get(i).getText().equals("Classes"))

					{
						// reusableWaits.linksToBeClickable();
						driver.findElements(By.tagName("a")).get(i).click();
						break;
					}

				}
				Thread.sleep(2000);
				//Verifies the link navigates to the right page
				Assert.assertEquals("Select Classes", driver.getTitle());
								
				//Note the package units after purchase
				IntUnitCountAfter1 = reusableMethods.getPackageUnits("ServiceCC");
//				System.out.println(IntUnitCountAfter1);
				
								
				//Verifies the package units is now incremented by one unit
				IntUnitCountBefore1++;
				Assert.assertEquals(IntUnitCountBefore1, IntUnitCountAfter1); // verifies the unit count of the Package

				
				d.getMenuMyAccount().click();
				Thread.sleep(1000);
				d.getMenuAccountHistory().click();
				Thread.sleep(3000);
								
				while(!ahp.getReceiptNumberTable().isDisplayed())
				{
					Thread.sleep(2000);	
					System.out.println("waiting");
				}
				wait.until(ExpectedConditions.visibilityOf(ahp.getReceiptNumberTable()));
				//Clicks on the receiptnumber in Account History 
				
				ahp.getSearchField().sendKeys(receiptNumber2);
				
				Thread.sleep(3000);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-md-3 hidden-sm hidden-xs']//a")));
				ahp.getReceiptNumber().click();
				Thread.sleep(1000);

				//Verifies the amount in the receipt is the same as it was displayed on the Purchase Packages page
				while (TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText().isBlank())
				{
					Thread.sleep(500);
				}
				
				System.out.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText());
				Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
						.contains(FormatTotalAmt1));
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
				Thread.sleep(2000);
				
		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName);
			log.error(ae.getMessage(), ae);
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName);
			log.error(eci.getMessage(), eci);
			reusableMethods.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}
		finally {
				reusableMethods.memberLogout();
		}

	}

	@Test(priority = 8, description = "Payment Method is New Card")
	public void PurchaseNewCard() throws InterruptedException, IOException {
		try {
				
	reusableMethods.activeMemberLogin(prop.getProperty("activeMember8_username"), prop.getProperty("activeMember8_password"));
	Thread.sleep(2000);
		
	
	d.getMenuShopPackages().click();
	
	WebDriverWait wait1 = new WebDriverWait(driver, 30);
//	wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'row m-t-md']")));
	
		
	while (!sp.getPackagesList().isDisplayed())
	{
		Thread.sleep(1000);
		System.out.println("Waiting for the packages to be displayed");
	}
	
	wait1.until(ExpectedConditions.visibilityOf(sp.getPackagesList()));
	wait1.until(ExpectedConditions.visibilityOf(sp.getWarningMsg()));
	sp.getKeyWord().sendKeys("ServiceNC");
	
			
	for (int i = 0; i < sp.getPackageNames().size(); i++)

	{
		if (sp.getPackageNames().get(i).getText().equals("ServiceNC"))

		{
			sp.getPurchaseButtons().get(i).click();
			break;
		}

	}
	
	wait1.until(ExpectedConditions.textToBePresentInElement(PP.getShopPackageTotalAmount(), "$"));
	Thread.sleep(3000);
	Assert.assertEquals("ServiceNC", PP.getPackageName().getText());
	
		
	while(!PM.getNewCardButton().isDisplayed())
		
	{
		Thread.sleep(1000);;
	}
	
			
				PM.getNewCardButton().click();
				Thread.sleep(3000);
						
				String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
				while (opacity.contains("1")) {
					PM.getNewCardButton().click();
					opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
				}

				Assert.assertTrue(PM.getCloseButton().isDisplayed());
				Assert.assertFalse(PM.getPaymentButton().isEnabled());
				System.out.println("Pay Button disabled:" + PM.getPaymentButton().getAttribute("disabled"));

//				System.out.println(PM.getNameOnCardField().getAttribute("value"));
	     		Assert.assertEquals(prop.getProperty("activeMember8_fullname"),PM.getNameOnCardField().getAttribute("value"));
	     		JavascriptExecutor jse = (JavascriptExecutor)driver;
	    		jse.executeScript("arguments[0].click();", PM.getCardNumberField()); 
				PM.getCardNumberField().sendKeys("4111111111111111");
				PM.getExpirationMonth().sendKeys("12");
				PM.getExpirationYear().sendKeys("29");
				PM.getSecurityCode().sendKeys("123");
				PM.getCheckBox().click();
				while (!PM.getPaymentButton().isEnabled()) {
					Thread.sleep(1000);
				}
				
				//Clicks on the Pay button without signature
				PM.getPaymentButton().click();
				System.out.println(PM.getPopupContent().getText());
				Assert.assertTrue(PM.getPopupContent().getText().contains("A signature is required to continue."));
				PM.getPopupOk().click();
				Thread.sleep(1000);
				PM.getSaveCardNo().click();
			
				
	
	// Noting down the total amount
	wait1.until(ExpectedConditions.textToBePresentInElement(PP.getShopPackageTotalAmount(), "$"));
//	System.out.println(PP.getTotalAmount().getText());
	String[] totalAmt2 = PP.getShopPackageTotalAmount().getText().split(": ");
	String FormatTotalAmt2 = totalAmt2[1].trim();
//	System.out.println(FormatTotalAmt2);

	//Noting down the Package Units before purchasing
	int IntUnitCountBefore2 = 0;
	int IntUnitCountAfter2 = 0;

	IntUnitCountBefore2 = reusableMethods.getPackageUnits("ServiceNC");
	//Click the Pay button
			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}

			//Verifies the Pay button contains the total amount
			Assert.assertTrue(PM.getPaymentButton().getText().contains(FormatTotalAmt2));
			//Clicks the Pay button
			PM.getPaymentButton().click();

			wait1.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));

			//Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);
			
			//Verifies the text on Thank You page and the links to navigate to Dashboard and other pages are displayed
			reusableMethods.ThankYouPageValidations();
			
			//Note down the Receipt number
			String receiptNumber4 = TY.getReceiptNumber().getText();
						
			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber4));
			
			//Verifies the buttons on Print Receipt Popup
			reusableMethods.ReceiptPopupValidations();
			
			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
			Thread.sleep(3000);

			//Navigate to Select Classes
			int count1 = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count1; i++) {
//				System.out.println(driver.findElements(By.tagName("a")).get(i).getText());
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Courses / Events" ))

				{
					// reusableWaits.linksToBeClickable();
					driver.findElements(By.tagName("a")).get(i).click();
					break;
				}

			}
			Thread.sleep(2000);
			//Verifies the link navigates to the right page
			Assert.assertEquals("Select Courses / Events", driver.getTitle());
			Thread.sleep(3000);
			
			//Note the package units after purchase
			IntUnitCountAfter2 = reusableMethods.getPackageUnits("ServiceNC");
			
			//Verifies the package units is now incremented by one unit
			IntUnitCountBefore2++;
			Assert.assertEquals(IntUnitCountBefore2, IntUnitCountAfter2); // verifies the unit count of the Package

			
			d.getMenuMyAccount().click();
			Thread.sleep(1000);
			d.getMenuAccountHistory().click();
			Thread.sleep(3000);
			
						
			while(!ahp.getReceiptNumberTable().isDisplayed())
			{
				Thread.sleep(2000);	
				System.out.println("waiting");
			}
			
			wait1.until(ExpectedConditions.visibilityOf(ahp.getReceiptNumberTable()));
			
			//Clicks on the Receiptnumber in Account History 
			
			ahp.getSearchField().sendKeys(receiptNumber4);
			
			Thread.sleep(3000);
			wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-md-3 hidden-sm hidden-xs']//a")));
			ahp.getReceiptNumber().click();
			Thread.sleep(1000);
			//Verifies the amount in the receipt is the same as it was displayed on the Purchase Packages page
			while (TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText().isBlank())
			{
				Thread.sleep(500);
			}
			System.out.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText());
			Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
					.contains(FormatTotalAmt2));
			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
			Thread.sleep(2000);
			
	} catch (java.lang.AssertionError ae) {
		System.out.println("assertion error");
		ae.printStackTrace();
		getScreenshot(testName);
		log.error(ae.getMessage(), ae);
		Assert.fail(ae.getMessage());
	}

	catch (org.openqa.selenium.NoSuchElementException ne) {
		System.out.println("No element present");
		ne.printStackTrace();
		getScreenshot(testName);
		log.error(ne.getMessage(), ne);
		Assert.fail(ne.getMessage());
	}

	catch (org.openqa.selenium.ElementClickInterceptedException eci) {
		System.out.println("Element Click Intercepted");
		eci.printStackTrace();
		getScreenshot(testName);
		log.error(eci.getMessage(), eci);
		reusableMethods.catchErrorMessage();
		Assert.fail(eci.getMessage());
	}
		finally {
			reusableMethods.memberLogout();
		}

	}

	@Test(priority = 9, description = "OnAccount Payment Method is not available for this Member")
	
	public void OnAccountNotAvailable() throws InterruptedException, IOException {
		try {
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember9_username"), prop.getProperty("activeMember9_password"));
		Thread.sleep(2000);
		
	
		d.getMenuShopPackages().click();
		
//		WebDriverWait wait1 = new WebDriverWait(driver, 30);
//		wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'row m-t-md']")));
				
		while (!sp.getPackagesList().isDisplayed())
		{
			Thread.sleep(1000);
			System.out.println("Waiting for the packages to be displayed");
		}
		sp.getKeyWord().sendKeys("Service");
		
				
		for (int i = 0; i < sp.getPackageNames().size(); i++)

		{
			if (sp.getPackageNames().get(i).getText().equals("ServiceNC"))

			{
				sp.getPurchaseButtons().get(i).click();
				break;
			}

		}
		Thread.sleep(5000);
		
		Assert.assertEquals("ServiceNC", PP.getPackageName().getText());
		
				
		List<String> Labels = new ArrayList<String>();
		
		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
		String label = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText();
		Labels.add(label);
		}
		Assert.assertFalse(Labels.contains(" On Account"));
		Thread.sleep(2000);
		
		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName);
			log.error(ae.getMessage(), ae);
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName);
			log.error(eci.getMessage(), eci);
			reusableMethods.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}
		
		finally {
		reusableMethods.memberLogout();
		}

	}

	@Test(priority = 10, description = "Stored Card does not exist for this Member")
	
	public void StoredCardNotAvailable() throws InterruptedException, IOException {
		
		try {
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember10_username"), prop.getProperty("activeMember10_password"));
		Thread.sleep(2000);
		
		d.getMenuShopPackages().click();
		
//		WebDriverWait wait1 = new WebDriverWait(driver, 30);
//		wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'row m-t-md']")));
				
		while (!sp.getPackagesList().isDisplayed())
		{
			Thread.sleep(1000);
			System.out.println("Waiting for the packages to be displayed");
		}
		sp.getKeyWord().sendKeys("Service");
		
				
		for (int i = 0; i < sp.getPackageNames().size(); i++)

		{
			if (sp.getPackageNames().get(i).getText().equals("ServiceNC"))

			{
				sp.getPurchaseButtons().get(i).click();
				break;
			}

		}
		Thread.sleep(5000);
		
		Assert.assertEquals("ServiceNC", PP.getPackageName().getText());
		
			
		List<String> Labels = new ArrayList<String>();
		
		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
		String label = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText();
		Labels.add(label);
		}
		Assert.assertFalse(Labels.contains("card"));
		Thread.sleep(2000);
		
		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName);
			log.error(ae.getMessage(), ae);
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName);
			log.error(eci.getMessage(), eci);
			reusableMethods.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}
		
		finally{
			reusableMethods.memberLogout();
		}


	}

    @Test(priority = 11, description = "Stored Card does not exist for this Member and On Account is not available")
	
	public void NoOANoStoredCardAvailable() throws InterruptedException, IOException {
    	try {
    	
    	reusableMethods.activeMemberLogin(prop.getProperty("activeMember11_username"), prop.getProperty("activeMember11_password"));
		Thread.sleep(2000);
				
	
		d.getMenuShopPackages().click();
		
//		WebDriverWait wait1 = new WebDriverWait(driver, 30);
//		wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'row m-t-md']")));
				
		while (!sp.getPackagesList().isDisplayed())
		{
			Thread.sleep(1000);
			System.out.println("Waiting for the packages to be displayed");
		}
		sp.getKeyWord().sendKeys("Service");
		
				
		for (int i = 0; i < sp.getPackageNames().size(); i++)

		{
			if (sp.getPackageNames().get(i).getText().equals("ServiceNC"))

			{
				sp.getPurchaseButtons().get(i).click();
				break;
			}

		}
		Thread.sleep(5000);
		
		Assert.assertEquals("ServiceNC", PP.getPackageName().getText());
		
		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		Assert.assertEquals(0, count);
		Thread.sleep(2000);
		
    	} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName);
			log.error(ae.getMessage(), ae);
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName);
			log.error(eci.getMessage(), eci);
			reusableMethods.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}
		
		finally{
			reusableMethods.memberLogout();
		}


	}
    
    
	 @AfterClass
	 public void teardown() throws InterruptedException
	 {
	     
		 driver.close(); 
		 driver=null;
	     
	 }
	 
}