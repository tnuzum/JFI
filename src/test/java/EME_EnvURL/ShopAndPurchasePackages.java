package EME_EnvURL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ShopPackagesPO;
import pageObjects.ThankYouPO;
import resources.Base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ShopAndPurchasePackages extends Base {
	
//	@BeforeTest
	@BeforeClass
	@Parameters({"EMELoginPage"})
	public void initialize(String EMELoginPage) throws InterruptedException, IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(EMELoginPage);

	}

	@Test(priority = 1, description = "Confirming Add To Cart button text changed to Purchase and Page Header name Validation")
	public void PurchaseBtnNameCheck() throws IOException, InterruptedException {

		reusableMethods.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));
		Thread.sleep(2000);
		DashboardPO d = new DashboardPO(driver);
		d.getMenuShopPackages().click();
		ShopPackagesPO sp = new ShopPackagesPO(driver);
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'row m-t-md']")));
//		   System.out.println(sp.getPurchaseButtons().size());
		for (int i = 0; i < sp.getPurchaseButtons().size(); i++) {
			Assert.assertEquals("Purchase", sp.getPurchaseButtons().get(i).getText());
		}
		Assert.assertEquals("Shop Packages", sp.getPageHeader().getText());
		Assert.assertEquals("Dashboard", sp.getBreadcrumbDashboard().getText());
		Assert.assertEquals("Shop", sp.getBreadcrumbShop().getText());

	}

	@Test(priority = 2, description = "Confirming Filter works")
	public void KeywordFilterCheck() throws IOException, InterruptedException {

		ShopPackagesPO sp = new ShopPackagesPO(driver);
		sp.getKeyWord().sendKeys("Service");
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

		ShopPackagesPO sp = new ShopPackagesPO(driver);

		for (int i = 0; i < sp.getPackageNames().size(); i++)

		{
			if (sp.getPackageNames().get(i).getText().contains("ServiceOA"))

			{
				sp.getPurchaseButtons().get(i).click();
				break;
			}

		}
		Thread.sleep(5000);
		PurchaseConfirmationPO pp = new PurchaseConfirmationPO(driver);
		Assert.assertEquals("ServiceOA", pp.getPackageName().getText());
	}

	@Test(priority = 4, description = "Page Layout Validation")

	public void PageLayoutValidation() {

		PurchaseConfirmationPO pp = new PurchaseConfirmationPO(driver);
		Assert.assertEquals("Dashboard", pp.getBreadcrumbDashboard().getText());
		Assert.assertEquals("Shop", pp.getBreadcrumbShop().getText());
		Assert.assertEquals("Confirm", pp.getBreadcrumbConfirm().getText());
		Boolean ReviewLabelPresent = reusableMethods.isElementPresent(By.xpath("//div[@class = 'rate-box']/h2"));
		Assert.assertTrue(ReviewLabelPresent);
		Assert.assertEquals("Review", pp.getReviewLabel().getText());
		Boolean FeesLabelPresent = reusableMethods.isElementPresent(By.xpath("//small[contains(text(),'Fee(s)')]"));
		Assert.assertTrue(FeesLabelPresent);
		Boolean SubTotalLabelPresent = reusableMethods
				.isElementPresent(By.xpath("//strong[contains(text(),'SUB-TOTAL:')]"));
		Assert.assertTrue(SubTotalLabelPresent);
		Boolean TaxLabelPresent = reusableMethods.isElementPresent(By.xpath("//strong[contains(text(),'TAX:')]"));
		Assert.assertTrue(TaxLabelPresent);
		Boolean TotalLabelPresent = reusableMethods.isElementPresent(By.xpath("//h2[contains(text(),'TOTAL:')]"));
		Assert.assertTrue(TotalLabelPresent);

	}

	@Test(priority = 5, description = "Payment Method OnAccount is selected by default")

	public void OnAccountIsSelectedByDefault() {
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
					.contains(" On Account"))
				Assert.assertTrue(PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).isSelected());
		}
	}

	@Test(priority = 6, description = "Payment Method is OnAccount")
	public void PurchaseOnAccount() throws InterruptedException {

		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);

		// Noting down the total amount
//		System.out.println(PP.getTotalAmount().getText());
		while (PP.getShopPackageTotalAmount().getText().isBlank())
		{
			Thread.sleep(500);
		}
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
				
				//Click the Pay button
				while (!PM.getPaymentButton().isEnabled())
				{
					Thread.sleep(1000);
				}
				PM.getPaymentButton().click();

				WebDriverWait wait = new WebDriverWait(driver, 30);
				wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));

		//Verifies the success message
		Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
		PP.getPopupOKButton().click();
		ThankYouPO TY = new ThankYouPO(driver);
		
		//Verifies the text on Thank You page and the links to navigate to Dashboard and other pages are displayed
		reusableMethods.ThankYouPageValidations();

		//Note down the Receipt number
		String receiptNumber = TY.getReceiptNumber().getText();
		String receiptNumber1 = null;
		
		Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
		TY.getPrintReceiptButton().click();
		Thread.sleep(2000);
		Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
		
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

		DashboardPO dp = new DashboardPO(driver);
		dp.getMyAccountAccountHistory().click();
		
		AcctHistoryPO ahp = new AcctHistoryPO(driver);
		
		while(!ahp.getReceiptNumberTable().isDisplayed())
		{
			Thread.sleep(2000);	
			System.out.println("waiting");
		}
		
		//Clicks on the Receiptnumber in Account History 
		
		ahp.getSearchField().sendKeys("ServiceOA");
		
		while(!ahp.getReceiptNumberTable().isDisplayed())
		{
			Thread.sleep(2000);	
			System.out.println("waiting");
		}
		for (int k = 0; k < ahp.getReceiptNumbers().size(); k++) {
			receiptNumber1 = ahp.getReceiptNumbers().get(k).getText().trim();

			if (receiptNumber1.equals(receiptNumber)) {
				ahp.getReceiptNumbers().get(k).click();
				break;
			}
		}

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
		reusableMethods.memberLogout();
	}

	@Test(priority = 7, description = "Payment Method is Stored Card")
	public void PurchaseStoredCard() throws InterruptedException {
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember7_username"), prop.getProperty("activeMember7_password"));
		Thread.sleep(2000);
		DashboardPO d = new DashboardPO(driver);
		d.getMenuShopPackages().click();
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'row m-t-md']")));
		
		ShopPackagesPO sp = new ShopPackagesPO(driver);
		sp.getKeyWord().sendKeys("Service");
		
				
		for (int i = 0; i < sp.getPackageNames().size(); i++)

		{
			if (sp.getPackageNames().get(i).getText().equals("ServiceCC"))

			{
				sp.getPurchaseButtons().get(i).click();
				break;
			}

		}
		Thread.sleep(3000);
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);
		Assert.assertEquals("ServiceCC", PP.getPackageName().getText());
		
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
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
				ThankYouPO TY = new ThankYouPO(driver);
				
				//Verifies the text on Thank You page and the links to navigate to Dashboard and other pages are displayed
				reusableMethods.ThankYouPageValidations();

				//Note down the Receipt number
				String receiptNumber2 = TY.getReceiptNumber().getText();
				String receiptNumber3 = null;
				
				Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
				TY.getPrintReceiptButton().click();
				Thread.sleep(2000);
				Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
				
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

				DashboardPO dp = new DashboardPO(driver);
				dp.getMenuMyAccount().click();
				Thread.sleep(1000);
				dp.getMenuAccountHistory().click();
				
				AcctHistoryPO ahp = new AcctHistoryPO(driver);
				
				while(!ahp.getReceiptNumberTable().isDisplayed())
				{
					Thread.sleep(2000);	
					System.out.println("waiting");
				}
				
				//Clicks on the Receiptnumber in Account History 
				
				ahp.getSearchField().sendKeys("ServiceCC");
				
				while(!ahp.getReceiptNumberTable().isDisplayed())
				{
					Thread.sleep(2000);	
				}
				for (int k = 0; k < ahp.getReceiptNumbers().size(); k++) {
					receiptNumber3 = ahp.getReceiptNumbers().get(k).getText().trim();

					if (receiptNumber3.equals(receiptNumber2)) {
						ahp.getReceiptNumbers().get(k).click();
						break;
					}
				}

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
				reusableMethods.memberLogout();

	}

	@Test(priority = 8, description = "Payment Method is New Card")
	public void PurchaseNewCard() throws InterruptedException {
		Boolean CloseBtnPresent;
		
	reusableMethods.activeMemberLogin(prop.getProperty("activeMember8_username"), prop.getProperty("activeMember8_password"));
	Thread.sleep(2000);
	DashboardPO d = new DashboardPO(driver);
	d.getMenuShopPackages().click();
	
	WebDriverWait wait1 = new WebDriverWait(driver, 30);
	wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'row m-t-md']")));
	
	ShopPackagesPO sp = new ShopPackagesPO(driver);
	sp.getKeyWord().sendKeys("Service");
	
			
	for (int i = 0; i < sp.getPackageNames().size(); i++)

	{
		if (sp.getPackageNames().get(i).getText().equals("ServiceNC"))

		{
			sp.getPurchaseButtons().get(i).click();
			break;
		}

	}
	Thread.sleep(3000);
	PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);
	Assert.assertEquals("ServiceNC", PP.getPackageName().getText());
	
	PaymentMethodsPO PM = new PaymentMethodsPO(driver);
	
			
				PM.getNewCardButton().click();
				Thread.sleep(3000);
				
				WebDriverWait wait = new WebDriverWait(driver, 10);
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("show-newcard"))));
				
				CloseBtnPresent = reusableMethods.isElementPresent(By.xpath("//button[@id='close-button']"));
				while (CloseBtnPresent == false)
				{
					System.out.println("Close button not present");
					PM.getNewCardButton().click();
					 CloseBtnPresent = reusableMethods.isElementPresent(By.xpath("//button[@id='close-button']"));
				}
				Assert.assertTrue(PM.getCloseButton().isDisplayed());
				Assert.assertFalse(PM.getPaymentButton().isEnabled());
//				System.out.println(PM.getNameOnCardField().getAttribute("value"));
	     		Assert.assertEquals(prop.getProperty("activeMember8_fullname"),PM.getNameOnCardField().getAttribute("value"));
				PM.getCardNumberField().sendKeys("4111111111111111");
				PM.getExpirationMonth().sendKeys("12");
				PM.getExpirationYear().sendKeys("29");
				PM.getSecurityCode().sendKeys("123");
				PM.getSaveCardNo().click();
			
				
	
	// Noting down the total amount
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
			ThankYouPO TY = new ThankYouPO(driver);

			//Verifies the text on Thank You page and the links to navigate to Dashboard and other pages are displayed
			reusableMethods.ThankYouPageValidations();
			
			//Note down the Receipt number
			String receiptNumber4 = TY.getReceiptNumber().getText();
			String receiptNumber5 = null;
			
			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			
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

			DashboardPO dp = new DashboardPO(driver);
			dp.getMenuMyAccount().click();
			Thread.sleep(1000);
			dp.getMenuAccountHistory().click();
			
			AcctHistoryPO ahp = new AcctHistoryPO(driver);
			
			while(!ahp.getReceiptNumberTable().isDisplayed())
			{
				Thread.sleep(2000);	
				System.out.println("waiting");
			}
			
			//Clicks on the Receiptnumber in Account History 
			
			ahp.getSearchField().sendKeys("ServiceNC");
			
			while(!ahp.getReceiptNumberTable().isDisplayed())
			{
				Thread.sleep(2000);	
			}
			for (int k = 0; k < ahp.getReceiptNumbers().size(); k++) {
				receiptNumber5 = ahp.getReceiptNumbers().get(k).getText().trim();

				if (receiptNumber5.equals(receiptNumber4)) {
					ahp.getReceiptNumbers().get(k).click();
					break;
				}
			}

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
			reusableMethods.memberLogout();

	}

	@Test(priority = 9, description = "OnAccount Payment Method is not available for this Member")
	
	public void OnAccountNotAvailable() throws InterruptedException {
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember9_username"), prop.getProperty("activeMember9_password"));
		Thread.sleep(2000);
		DashboardPO d = new DashboardPO(driver);
		d.getMenuShopPackages().click();
		
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'row m-t-md']")));
		ShopPackagesPO sp = new ShopPackagesPO(driver);
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
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);
		Assert.assertEquals("ServiceNC", PP.getPackageName().getText());
		
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		
		List<String> Labels = new ArrayList<String>();
		
		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
		String label = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText();
		Labels.add(label);
		}
		Assert.assertFalse(Labels.contains(" On Account"));
		Thread.sleep(2000);
		reusableMethods.memberLogout();

	}

	@Test(priority = 10, description = "Stored Card does not exist for this Member")
	
	public void StoredCardNotAvailable() throws InterruptedException {
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember10_username"), prop.getProperty("activeMember10_password"));
		Thread.sleep(2000);
		DashboardPO d = new DashboardPO(driver);
		d.getMenuShopPackages().click();
		
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'row m-t-md']")));
		ShopPackagesPO sp = new ShopPackagesPO(driver);
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
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);
		Assert.assertEquals("ServiceNC", PP.getPackageName().getText());
		
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		
		List<String> Labels = new ArrayList<String>();
		
		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
		String label = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText();
		Labels.add(label);
		}
		Assert.assertFalse(Labels.contains("card"));
		Thread.sleep(2000);
		reusableMethods.memberLogout();


	}

    @Test(priority = 11, description = "Stored Card does not exist for this Member and On Account is not available")
	
	public void NoOANoStoredCardAvailable() throws InterruptedException {
    	
    	reusableMethods.activeMemberLogin(prop.getProperty("activeMember11_username"), prop.getProperty("activeMember11_password"));
		Thread.sleep(2000);
		DashboardPO d = new DashboardPO(driver);
		d.getMenuShopPackages().click();
		
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'row m-t-md']")));
		ShopPackagesPO sp = new ShopPackagesPO(driver);
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
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);
		Assert.assertEquals("ServiceNC", PP.getPackageName().getText());
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		Assert.assertEquals(0, count);
		Thread.sleep(2000);
		reusableMethods.memberLogout();


	}
    
    
	 @AfterClass
	 public void teardown() throws InterruptedException
	 {
	     
		 driver.close(); 
		 driver=null;
	     
	 }
	 
}