package EME_EnvURL;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ThankYouPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollClassByBuyingPackage extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String classToEnroll = "CLASSNEEDSPUNCHES";
	private static String classNameDisplayed = "ClassNeedsPunches";
	private static String classTimeDisplayed = "Start Time: 10:00 AM";
	private static String classInstructorDisplayed = "Class Instructor: Max Gibbs";
	private static String buyPackageName = "Buy Day Pass";
	private static String defaultSelection = null;
	private static String unitsToBeSelected = "2 - $1.00/per";
	private static String classCostInUnits = "Class Cost: 2 unit(s)";

//	@BeforeTest
	@BeforeClass
	@Parameters({"EMELoginPage"})
	public void initialize(String EMELoginPage) throws InterruptedException, IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(EMELoginPage);

	}


	@Test(priority = 1, description = "Ui validations")
	public void UIValidations() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));
		reusableMethods.unenrollFromClass();
		
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyClassesScheduleButton().click();
		Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
			
		ClassSignUpPO c = new ClassSignUpPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		reusableMethods.SelectTomorrowDate();
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j = 0; j < ClassCount; j++) {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();

			if (className.contains(classToEnroll)) {
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																									// specific class
				break;
			}
		}

		Thread.sleep(2000);
		c.getPopupSignUpButton().click();
		Thread.sleep(2000);
		Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
		Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
		Assert.assertEquals(classNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(classTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(classInstructorDisplayed, c.getClassInstructor().getText());

		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDayAndDate = dateFormat1.format(today1.getTime());

		Assert.assertEquals("Date: " + tomorrowsDayAndDate, c.getClassDate().getText());
		
		int radioButtonCount = driver.findElements(By.tagName("label")).size();
		for (int i=0; i<radioButtonCount; i++)
		{
			if (driver.findElements(By.tagName("label")).get(i).getText().equals(buyPackageName))
					{
					driver.findElements(By.tagName("label")).get(i).click();
					break;
					}
		}
		Assert.assertTrue(c.getClassCostinPunches().getText().contains(classCostInUnits));
		WebElement W = driver.findElement(By.xpath("//div[@class='ibox-content']"));
		Select s = new Select(W.findElement(By.xpath("//select[contains(@class, 'form-control')]")));
		 defaultSelection = s.getFirstSelectedOption().getText().trim();
		    Assert.assertEquals(defaultSelection, unitsToBeSelected);
		
		c.getContinueButton().click();
		Thread.sleep(2000);
		reusableMethods.ReviewSectionValidation("Package(s)");
		
	}

	@Test(priority = 2, description = "Payment Method OnAccount is selected by default")

	public void OnAccountIsSelectedByDefault() {
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
					.contains(" On Account"))
				Assert.assertTrue(PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).isSelected());
		}
	}

	@Test(priority = 3, description = "Enroll with Payment Method OnAccount")
	public void EnrollOnAccount() throws InterruptedException, IOException {

		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);

		// Noting down the total amount
		Thread.sleep(2000);
//		System.out.println(PP.getTotalAmount().getText());
		String totalAmt = PP.getClassesReviewtotalAmount().getText();
		
//		System.out.println(TotalAmt);
		
		//Verifies the Pay button contains the total amount
		Assert.assertTrue(PM.getPaymentButton().getText().contains(totalAmt));
		
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
		Thread.sleep(3000);

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
		Thread.sleep(3000);
		
		DashboardPO dp = new DashboardPO(driver);
		dp.getMyAccountAccountHistory().click();
		
		AcctHistoryPO ahp = new AcctHistoryPO(driver);
		
		while(!ahp.getReceiptNumberTable().isDisplayed())
		{
			Thread.sleep(2000);	
			System.out.println("waiting");
		}
		
		//Clicks on the Receiptnumber in Account History 
	
		ahp.getSearchField().sendKeys(receiptNumber);
					
		while(!ahp.getReceiptNumberTable().isDisplayed())
		{
			Thread.sleep(2000);	
			System.out.println("waiting");
		}
		
		for (int k = 0; k < ahp.getReceiptNumbers().size(); k++) {
			receiptNumber1 = ahp.getReceiptNumbers().get(k).getText().trim();

			if (receiptNumber1.equals(receiptNumber)) {
//				System.out.println(receiptNumber);
//				System.out.println(receiptNumber1);
				ahp.getReceiptNumbers().get(k).click();
				break;
			}
		}
		Thread.sleep(1000);
		//Verifies the amount in the receipt is the same as it was displayed on the Purchase Packages page
//		System.out.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText());
//		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
//				.contains(totalAmt));
		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
		Thread.sleep(2000);
		reusableMethods.returnToDashboard();
		reusableMethods.unenrollFromClass();
		reusableMethods.memberLogout();
	}
	
	@Test(priority = 4, description = "Enroll With Saved Card")
	public void EnrollWithSavedCard() throws InterruptedException, IOException {
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember7_username"), prop.getProperty("activeMember7_password"));
		reusableMethods.unenrollFromClass();
		
		DashboardPO d = new DashboardPO(driver);
		
		d.getMyClassesScheduleButton().click();
		
		ClassSignUpPO c = new ClassSignUpPO(driver);
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		reusableMethods.SelectTomorrowDate();
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j = 0; j < ClassCount; j++) {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();

			if (className.contains(classToEnroll)) {
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																									// specific class
				break;
			}
		}

		Thread.sleep(1000);
		c.getPopupSignUpButton().click();
		Thread.sleep(1000);
		
		Assert.assertEquals(classNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(classTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(classInstructorDisplayed, c.getClassInstructor().getText());

		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDayAndDate = dateFormat1.format(today1.getTime());

		

		Assert.assertEquals("Date: " + tomorrowsDayAndDate, c.getClassDate().getText());
		
		int radioButtonCount = driver.findElements(By.tagName("label")).size();
		for (int i=0; i<radioButtonCount; i++)
		{
			if (driver.findElements(By.tagName("label")).get(i).getText().equals(buyPackageName))
					{
					driver.findElements(By.tagName("label")).get(i).click();
					break;
					}
		}
		Assert.assertTrue(c.getClassCostinPunches().getText().contains(classCostInUnits));
		WebElement W = driver.findElement(By.xpath("//div[@class='ibox-content']"));
		Select s = new Select(W.findElement(By.xpath("//select[contains(@class, 'form-control')]")));
		defaultSelection = s.getFirstSelectedOption().getText().trim();
	        Assert.assertEquals(defaultSelection, unitsToBeSelected);

		c.getContinueButton().click();
		
		Thread.sleep(3000);
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);
				
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
		String totalAmt1 = PP.getClassesReviewtotalAmount().getText();
		
//		System.out.println(totalAmt1);
		
		//Verifies the Pay button contains the total amount
				Assert.assertTrue(PM.getPaymentButton().getText().contains(totalAmt1));
				
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
				Thread.sleep(3000);

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
				Thread.sleep(2000);
				
				DashboardPO dp = new DashboardPO(driver);
				dp.getMenuMyAccount().click();
				Thread.sleep(2000);
				dp.getMenuAccountHistory().click();
				
				AcctHistoryPO ahp = new AcctHistoryPO(driver);
				
				while(!ahp.getReceiptNumberTable().isDisplayed())
				{
					Thread.sleep(2000);	
					System.out.println("waiting");
				}
				
				//Clicks on the Receiptnumber in Account History 
				
				ahp.getSearchField().sendKeys(receiptNumber2);
				while(!ahp.getReceiptNumberTable().isDisplayed())
				{
					Thread.sleep(3000);	
				}
				for (int k = 0; k < ahp.getReceiptNumbers().size(); k++) {
					receiptNumber3 = ahp.getReceiptNumbers().get(k).getText().trim();

					if (receiptNumber3.equals(receiptNumber2)) {
						ahp.getReceiptNumbers().get(k).click();
						break;
					}
				}
				Thread.sleep(1000);
				//Verifies the amount in the receipt is the same as it was displayed on the Purchase Packages page
//				System.out.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText());
//				Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
//						.contains(totalAmt1));
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
				Thread.sleep(2000);
				reusableMethods.returnToDashboard();
				reusableMethods.unenrollFromClass();
				reusableMethods.memberLogout();

	}
	@Test(priority=5, description = "Enroll in class with New Card")
	
	public void EnrollWithNewCard() throws InterruptedException, IOException {
		Boolean CloseBtnPresent;
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember8_username"), prop.getProperty("activeMember8_password"));
		reusableMethods.unenrollFromClass();
		
		DashboardPO d = new DashboardPO(driver);
		
		d.getMyClassesScheduleButton().click();
		
		ClassSignUpPO c = new ClassSignUpPO(driver);
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		reusableMethods.SelectTomorrowDate();
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j = 0; j < ClassCount; j++) {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();

			if (className.contains(classToEnroll)) {
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																									// specific class
				break;
			}
		}

		Thread.sleep(1000);
		c.getPopupSignUpButton().click();
		Thread.sleep(1000);
		
		Assert.assertEquals(classNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(classTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(classInstructorDisplayed, c.getClassInstructor().getText());

		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDayAndDate = dateFormat1.format(today1.getTime());

		Assert.assertEquals("Date: " + tomorrowsDayAndDate, c.getClassDate().getText());
		
		int radioButtonCount = driver.findElements(By.tagName("label")).size();
		for (int i=0; i<radioButtonCount; i++)
		{
			if (driver.findElements(By.tagName("label")).get(i).getText().equals(buyPackageName))
					{
					driver.findElements(By.tagName("label")).get(i).click();
					break;
					}
		}
		Assert.assertTrue(c.getClassCostinPunches().getText().contains(classCostInUnits));
		WebElement W = driver.findElement(By.xpath("//div[@class='ibox-content']"));
		Select s = new Select(W.findElement(By.xpath("//select[contains(@class, 'form-control')]")));
		defaultSelection = s.getFirstSelectedOption().getText().trim();
	        Assert.assertEquals(defaultSelection, unitsToBeSelected);
		
		c.getContinueButton().click();
		
		Thread.sleep(3000);
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);
				
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		
		PM.getNewCardButton().click();
		Thread.sleep(3000);
		
		WebDriverWait wait1 = new WebDriverWait(driver, 10);
		wait1.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("show-newcard"))));
		
		CloseBtnPresent = reusableMethods.isElementPresent(By.xpath("//button[@id='close-button']"));
		while (CloseBtnPresent == false)
		{
			System.out.println("Close button not present");
			PM.getNewCardButton().click();
			 CloseBtnPresent = reusableMethods.isElementPresent(By.xpath("//button[@id='close-button']"));
		}
		Assert.assertTrue(PM.getCloseButton().isDisplayed());
		Assert.assertFalse(PM.getPaymentButton().isEnabled());
       System.out.println("Pay Button disabled:" + PM.getPaymentButton().getAttribute("disabled"));
		
//		System.out.println(PM.getNameOnCardField().getAttribute("value"));
 		Assert.assertEquals(prop.getProperty("activeMember8_fullname"),PM.getNameOnCardField().getAttribute("value"));
		PM.getCardNumberField().sendKeys("4111111111111111");
		Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
		PM.getExpirationMonth().sendKeys("12");
		Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
		PM.getExpirationYear().sendKeys("29");
		Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
		PM.getSecurityCode().sendKeys("123");
		Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
		PM.getSaveCardNo().click();
		Assert.assertTrue(PM.getPaymentButton().isEnabled());
	

		// Noting down the total amount
//		System.out.println(PP.getTotalAmount().getText());
		String totalAmt2 = PP.getClassesReviewtotalAmount().getText();
		//System.out.println(totalAmt2);
		
		//Verifies the Pay button contains the total amount
				Assert.assertTrue(PM.getPaymentButton().getText().contains(totalAmt2));
				
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
					if (driver.findElements(By.tagName("a")).get(i).getText().equals("Courses / Events"))

					{
						// reusableWaits.linksToBeClickable();
						driver.findElements(By.tagName("a")).get(i).click();
						break;
					}

				}
				Thread.sleep(2000);
				//Verifies the link navigates to the right page
				Assert.assertEquals("Select Courses / Events", driver.getTitle());
				Thread.sleep(2000);
				
				DashboardPO dp = new DashboardPO(driver);
				dp.getMenuMyAccount().click();
				Thread.sleep(2000);
				dp.getMenuAccountHistory().click();
				
				AcctHistoryPO ahp = new AcctHistoryPO(driver);
				
				while(!ahp.getReceiptNumberTable().isDisplayed())
				{
					Thread.sleep(2000);	
					System.out.println("waiting");
				}
				
				//Clicks on the Receiptnumber in Account History 
				
				ahp.getSearchField().sendKeys(receiptNumber4);

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
				Thread.sleep(1000);
				//Verifies the amount in the receipt is the same as it was displayed on the Purchase Packages page
//				System.out.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText());
//				Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
//						.contains(totalAmt2));
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
				Thread.sleep(2000);
				reusableMethods.returnToDashboard();
	}
		
	@Test(priority = 6, description = "Unenroll from the class")
	public void unenrollFromClass() throws IOException, InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		
		Thread.sleep(2000);
		reusableWaits.waitForDashboardLoaded();
		boolean enrolled = reusableMethods.isElementPresent(By.xpath("//classeswidget//div[@class='class-table-container']"));
//		System.out.println(enrolled);
		if (enrolled == true) {

			while (!d.getMyClassesClass1GearButton().isDisplayed()) 
			{
			Thread.sleep(1000);
			System.out.println("Sleeping for 1 second");
			}
		    WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(d.getMyClassesClass1GearButton()));	
			d.getMyClassesClass1GearButton().click();
			
			wait.until(ExpectedConditions.visibilityOf(d.getmyClassesUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(d.getmyClassesUnenrollButton()));
			d.getmyClassesUnenrollButton().click();
			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));
			u.getUnenrollButton().click();
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			u.getUnenrollConfirmYesButton().click();
			wait.until(ExpectedConditions.stalenessOf(u.getUnenrollConfirmYesButton()));
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			
		}

		else {
			System.out.println("enrollement not displayed");
		}
		reusableMethods.returnToDashboard();
		reusableMethods.memberLogout();

	}


   	
//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
