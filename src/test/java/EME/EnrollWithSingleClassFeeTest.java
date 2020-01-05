package EME;

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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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

public class EnrollWithSingleClassFeeTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1, description = "Ui validations")
	public void UIValidations() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember5_username"), prop.getProperty("activeMember5_password"));
		reusableMethods.unenrollFromClass();
		Thread.sleep(2000);
		reusableMethods.returnToDashboard();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyClassesScheduleButton().click();
		Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Shop", BT.getBreadcrumb2().getText());
		Thread.sleep(2000);
		ClassSignUpPO c = new ClassSignUpPO(driver);

		c.getCalendarIcon().click();
		Thread.sleep(2000);
		DateFormat dateFormat = new SimpleDateFormat("d");
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDate = dateFormat.format(today.getTime());

		int daycount = driver.findElements(By.tagName("td")).size(); // Get the daycount from the calendar
		for (int i = 0; i < daycount; i++) {
			String date = driver.findElements(By.tagName("td")).get(i).getText();
			if (date.contains(tomorrowsDate)) {
				driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
				break;
			}
		}

		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j = 0; j < ClassCount; j++) {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();

			if (className.contains("BARRE Combat Fusion")) {
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
		Assert.assertEquals("BARRE Combat Fusion", c.getClassName().getText());
		Assert.assertEquals("Start Time: 05:00 PM", c.getClassStartTime().getText());
		Assert.assertEquals("Instructor: Andrea", c.getClassInstructor().getText());

		DateFormat dateFormat11 = new SimpleDateFormat("E");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDay = dateFormat11.format(today1.getTime());

		DateFormat dateFormat1 = new SimpleDateFormat("mm/dd/yyyy");
		Calendar today11 = Calendar.getInstance();
		today11.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDate1 = dateFormat1.format(today11.getTime());

		Assert.assertEquals("Date: " + tomorrowsDay + "day " + tomorrowsDate1, c.getClassDate());
		
		int radioButtonCount = driver.findElements(By.tagName("label")).size();
		for (int i=0; i<radioButtonCount; i++)
		{
			if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Single Class Fee"))
					{
					driver.findElements(By.tagName("label")).get(i).click();
					break;
					}
		}

		c.getContinueButton().click();
		PurchaseConfirmationPO pp = new PurchaseConfirmationPO(driver);
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
		System.out.println(PP.getTotalAmount().getText());
		String[] totalAmt = PP.getTotalAmount().getText().split(": ");
		String FormatTotalAmt = totalAmt[1].trim();
		System.out.println(FormatTotalAmt);
		
		//Verifies the Pay button contains the total amount
		Assert.assertTrue(PM.getPaymentButton().getText().contains(FormatTotalAmt));
		
		//Click the Pay button
		while (!PM.getPaymentButton().isEnabled())
		{
			Thread.sleep(1000);
		}
		PM.getPaymentButton().click();

		Thread.sleep(2000);

		//Verifies the success message
		Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
		PP.getPopupOKButton().click();
		ThankYouPO TY = new ThankYouPO(driver);

		//Verifies the text on Thank You page and Print Receipt Popup
		Assert.assertEquals("THANK YOU FOR YOUR ORDER", (TY.getThankYouText().getText()));
		Assert.assertTrue(TY.getsmallText().getText().contains("The receipt # for this transaction is:"));
		Assert.assertTrue(TY.getsmallText().getText().contains("Have fun!"));
		Assert.assertTrue(
				TY.getsmallText().getText().contains("Everything was processed and you are all ready to go."));
		Assert.assertTrue(TY.getsmallText().getText().contains(
				"Participants with a valid email address on file will receive a confirmation email with details of this purchase."));
		//Note down the Receipt number
		String receiptNumber = TY.getReceiptNumber().getText();
		String receiptNumber1 = null;
		Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
		TY.getPrintReceiptButton().click();
		Thread.sleep(2000);
		Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
		Assert.assertTrue(
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'PRINT')]")).isDisplayed());
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'PRINT')]"))
				.getAttribute("type").equals("button"));
		Assert.assertTrue(
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).isDisplayed());
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]"))
				.getAttribute("type").equals("button"));
		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
		Thread.sleep(3000);

		//Verifies the links to navigate to Dashboard and other pages are displayed
		Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/Home']")));
		Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/ClassList']")));
		Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/CourseList']")));
		Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/Appointments']")));

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
		
		//Clicks on the Receiptnumber in Account History 
		AcctHistoryPO ahp = new AcctHistoryPO(driver);
		for (int k = 0; k < ahp.getReceiptNumbers().size(); k++) {
			receiptNumber1 = ahp.getReceiptNumbers().get(k).getText().trim();

			if (receiptNumber1.equals(receiptNumber)) {
				ahp.getReceiptNumbers().get(k).click();
				break;
			}
		}

		//Verifies the amount in the receipt is the same as it was displayed on the Purchase Packages page
		System.out.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText());
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
				.contains(FormatTotalAmt));
		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
		Thread.sleep(2000);
		reusableMethods.returnToDashboard();
		reusableMethods.unenrollFromClass();
		reusableMethods.memberLogout();
	}
	
	@Test(priority = 4, description = "Enroll With Saved Card")
	public void EnrollWithSavedCard() throws InterruptedException, IOException {
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember5_username"), prop.getProperty("activeMember5_password"));
		reusableMethods.unenrollFromClass();
		Thread.sleep(2000);
		reusableMethods.returnToDashboard();
		DashboardPO d = new DashboardPO(driver);
		
		d.getMyClassesScheduleButton().click();
		
		Thread.sleep(1000);
		ClassSignUpPO c = new ClassSignUpPO(driver);

		c.getCalendarIcon().click();
		Thread.sleep(1000);
		DateFormat dateFormat = new SimpleDateFormat("d");
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDate = dateFormat.format(today.getTime());

		int daycount = driver.findElements(By.tagName("td")).size(); // Get the daycount from the calendar
		for (int i = 0; i < daycount; i++) {
			String date = driver.findElements(By.tagName("td")).get(i).getText();
			if (date.contains(tomorrowsDate)) {
				driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
				break;
			}
		}

		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j = 0; j < ClassCount; j++) {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();

			if (className.contains("BARRE Combat Fusion")) {
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																									// specific class
				break;
			}
		}

		Thread.sleep(1000);
		c.getPopupSignUpButton().click();
		Thread.sleep(1000);
		
		Assert.assertEquals("BARRE Combat Fusion", c.getClassName().getText());
		Assert.assertEquals("Start Time: 05:00 PM", c.getClassStartTime().getText());
		Assert.assertEquals("Instructor: Andrea", c.getClassInstructor().getText());

		DateFormat dateFormat11 = new SimpleDateFormat("E");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDay = dateFormat11.format(today1.getTime());

		DateFormat dateFormat1 = new SimpleDateFormat("mm/dd/yyyy");
		Calendar today11 = Calendar.getInstance();
		today11.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDate1 = dateFormat1.format(today11.getTime());

		Assert.assertEquals("Date: " + tomorrowsDay + "day " + tomorrowsDate1, c.getClassDate());
		
		int radioButtonCount = driver.findElements(By.tagName("label")).size();
		for (int i=0; i<radioButtonCount; i++)
		{
			if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Single Class Fee"))
					{
					driver.findElements(By.tagName("label")).get(i).click();
					break;
					}
		}

		c.getContinueButton().click();
		
		Thread.sleep(2000);
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
		System.out.println(PP.getTotalAmount().getText());
		String[] totalAmt1 = PP.getTotalAmount().getText().split(": ");
		String FormatTotalAmt1 = totalAmt1[1].trim();
		System.out.println(FormatTotalAmt1);
		
		//Verifies the Pay button contains the total amount
				Assert.assertTrue(PM.getPaymentButton().getText().contains(FormatTotalAmt1));
				
				//Click the Pay button
				while (!PM.getPaymentButton().isEnabled())
				{
					Thread.sleep(1000);
				}
				PM.getPaymentButton().click();

				Thread.sleep(2000);
				//Verifies the success message
				Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
				PP.getPopupOKButton().click();
				ThankYouPO TY = new ThankYouPO(driver);

				//Verifies the text on Thank You page and Print Receipt Popup
				Assert.assertEquals("THANK YOU FOR YOUR ORDER", (TY.getThankYouText().getText()));
				Assert.assertTrue(TY.getsmallText().getText().contains("The receipt # for this transaction is:"));
				Assert.assertTrue(TY.getsmallText().getText().contains("Have fun!"));
				Assert.assertTrue(
						TY.getsmallText().getText().contains("Everything was processed and you are all ready to go."));
				Assert.assertTrue(TY.getsmallText().getText().contains(
						"Participants with a valid email address on file will receive a confirmation email with details of this purchase."));
				
				//Note down the Receipt number
				String receiptNumber2 = TY.getReceiptNumber().getText();
				String receiptNumber3 = null;
				Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
				TY.getPrintReceiptButton().click();
				Thread.sleep(2000);
				Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
				Assert.assertTrue(
						TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'PRINT')]")).isDisplayed());
				Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'PRINT')]"))
						.getAttribute("type").equals("button"));
				Assert.assertTrue(
						TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).isDisplayed());
				Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]"))
						.getAttribute("type").equals("button"));
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
				Thread.sleep(3000);

				//Verifies the links to navigate to Dashboard and other pages are displayed
				Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/Home']")));
				Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/ClassList']")));
				Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/CourseList']")));
				Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/Appointments']")));

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
				
				//Clicks on the Receiptnumber in Account History 
				AcctHistoryPO ahp = new AcctHistoryPO(driver);
				ahp.getSearchField().sendKeys("BARRE Combat Fusion");
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
				System.out.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText());
				Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
						.contains(FormatTotalAmt1));
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
				Thread.sleep(2000);
				reusableMethods.returnToDashboard();
				reusableMethods.unenrollFromClass();
				reusableMethods.memberLogout();

	}
	@Test(priority=5, description = "Enroll in class with New Card")
	
	public void EnrollWithNewCard() throws InterruptedException, IOException {
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember5_username"), prop.getProperty("activeMember5_password"));
		reusableMethods.unenrollFromClass();
		Thread.sleep(1000);
		reusableMethods.returnToDashboard();
		DashboardPO d = new DashboardPO(driver);
		
		d.getMyClassesScheduleButton().click();
		
		Thread.sleep(1000);
		ClassSignUpPO c = new ClassSignUpPO(driver);

		c.getCalendarIcon().click();
		Thread.sleep(1000);
		DateFormat dateFormat = new SimpleDateFormat("d");
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDate = dateFormat.format(today.getTime());

		int daycount = driver.findElements(By.tagName("td")).size(); // Get the daycount from the calendar
		for (int i = 0; i < daycount; i++) {
			String date = driver.findElements(By.tagName("td")).get(i).getText();
			if (date.contains(tomorrowsDate)) {
				driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
				break;
			}
		}

		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j = 0; j < ClassCount; j++) {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();

			if (className.contains("BARRE Combat Fusion")) {
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																									// specific class
				break;
			}
		}

		Thread.sleep(1000);
		c.getPopupSignUpButton().click();
		Thread.sleep(1000);
		
		Assert.assertEquals("BARRE Combat Fusion", c.getClassName().getText());
		Assert.assertEquals("Start Time: 05:00 PM", c.getClassStartTime().getText());
		Assert.assertEquals("Instructor: Andrea", c.getClassInstructor().getText());

		DateFormat dateFormat11 = new SimpleDateFormat("E");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDay = dateFormat11.format(today1.getTime());

		DateFormat dateFormat1 = new SimpleDateFormat("mm/dd/yyyy");
		Calendar today11 = Calendar.getInstance();
		today11.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDate1 = dateFormat1.format(today11.getTime());

		Assert.assertEquals("Date: " + tomorrowsDay + "day " + tomorrowsDate1, c.getClassDate());
		
		int radioButtonCount = driver.findElements(By.tagName("label")).size();
		for (int i=0; i<radioButtonCount; i++)
		{
			if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Single Class Fee"))
					{
					driver.findElements(By.tagName("label")).get(i).click();
					break;
					}
		}

		c.getContinueButton().click();
		
		Thread.sleep(2000);
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);
				
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		
		PM.getNewCardButton().click();
		Assert.assertTrue(PM.getCloseButton().isDisplayed());
		Assert.assertFalse(PM.getPaymentButton().isEnabled());
		System.out.println(PM.getNameOnCardField().getAttribute("value"));
 		Assert.assertEquals(prop.getProperty("activeMember8_fullname"),PM.getNameOnCardField().getAttribute("value"));
		PM.getCardNumberField().sendKeys("4111111111111111");
		PM.getExpirationMonth().sendKeys("12");
		PM.getExpirationYear().sendKeys("29");
		PM.getSecurityCode().sendKeys("123");
		PM.getSaveCardNo().click();
	

		// Noting down the total amount
		System.out.println(PP.getTotalAmount().getText());
		String[] totalAmt1 = PP.getTotalAmount().getText().split(": ");
		String FormatTotalAmt1 = totalAmt1[1].trim();
		System.out.println(FormatTotalAmt1);
		
		//Verifies the Pay button contains the total amount
				Assert.assertTrue(PM.getPaymentButton().getText().contains(FormatTotalAmt1));
				
				//Click the Pay button
				while (!PM.getPaymentButton().isEnabled())
				{
					Thread.sleep(1000);
				}
				PM.getPaymentButton().click();

				Thread.sleep(2000);
				//Verifies the success message
				Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
				PP.getPopupOKButton().click();
				ThankYouPO TY = new ThankYouPO(driver);

				//Verifies the text on Thank You page and Print Receipt Popup
				Assert.assertEquals("THANK YOU FOR YOUR ORDER", (TY.getThankYouText().getText()));
				Assert.assertTrue(TY.getsmallText().getText().contains("The receipt # for this transaction is:"));
				Assert.assertTrue(TY.getsmallText().getText().contains("Have fun!"));
				Assert.assertTrue(
						TY.getsmallText().getText().contains("Everything was processed and you are all ready to go."));
				Assert.assertTrue(TY.getsmallText().getText().contains(
						"Participants with a valid email address on file will receive a confirmation email with details of this purchase."));
				
				//Note down the Receipt number
				String receiptNumber2 = TY.getReceiptNumber().getText();
				String receiptNumber3 = null;
				Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
				TY.getPrintReceiptButton().click();
				Thread.sleep(2000);
				Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
				Assert.assertTrue(
						TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'PRINT')]")).isDisplayed());
				Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'PRINT')]"))
						.getAttribute("type").equals("button"));
				Assert.assertTrue(
						TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).isDisplayed());
				Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]"))
						.getAttribute("type").equals("button"));
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
				Thread.sleep(3000);

				//Verifies the links to navigate to Dashboard and other pages are displayed
				Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/Home']")));
				Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/ClassList']")));
				Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/CourseList']")));
				Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/Appointments']")));

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
				
				//Clicks on the Receiptnumber in Account History 
				AcctHistoryPO ahp = new AcctHistoryPO(driver);
				ahp.getSearchField().sendKeys("BARRE Combat Fusion");
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
				System.out.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText());
				Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
						.contains(FormatTotalAmt1));
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
				Thread.sleep(2000);
				reusableMethods.returnToDashboard();
	}
		
	@Test(priority = 3, description = "Unenroll from the class")
	public void unenrollFromClass() throws IOException, InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		
		Thread.sleep(2000);
		boolean enrolled = reusableMethods.isElementPresent(By.xpath("//div[@class='class-table-container']"));
		System.out.println(enrolled);
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='class-table-container']")));
		if (enrolled == true) {

			while (!d.getMyClassesClass1GearButton().isDisplayed()) {
				Thread.sleep(1000);
				System.out.println("Sleeping for 1 second");
			}
			d.getMyClassesClass1GearButton().click();
			Thread.sleep(2000);
			d.getmyClassesUnenrollButton().click();
			Thread.sleep(2000);
			UnenrollPO u = new UnenrollPO(driver);
			u.getUnenrollButton().click();
			Thread.sleep(2000);
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);
			AssertJUnit.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
		}

		else {
			System.out.println("enrollement not displayed");
		}
int m = reusableMethods.getPackageUnits("ServiceNC");
	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
