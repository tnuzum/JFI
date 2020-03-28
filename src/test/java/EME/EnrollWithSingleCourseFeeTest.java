package EME;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.CalendarPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ThankYouPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollWithSingleCourseFeeTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String courseToEnroll = "FEECOURSE";
	private static String courseNameDisplayed = "FeeCourse";
	private static String courseTimeDisplayed = "Start Time: 11:00 AM";
	private static String courseInstructorDisplayed = "Course Instructor: Andrea";
	private static String CourseStartMonth = "Dec";
	private static String dsiredMonthYear = "December 2020";


//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1, description = "Ui validations")
	public void UIValidations() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));
//		reusableMethods.unenrollFromCourse(dsiredMonthYear);
//		Thread.sleep(2000);
//		reusableMethods.returnToDashboard();
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
				
		ClassSignUpPO c = new ClassSignUpPO(driver);
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
		
//		System.out.println(driver.findElement(By.xpath("//label[@id='dec']")).getText());
//		driver.findElement(By.xpath("//label[@id='dec']")).click();
		WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
		int monthCount = MonthNames.findElements(By.tagName("label")).size();
				for (int i = 0; i < monthCount; i++)
				{
					String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
					if (monthName.equals(CourseStartMonth))
					{
						 MonthNames.findElements(By.tagName("label")).get(i).click();
						 break;
					}
						
				}
					
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		

		int courseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j = 0; j < courseCount; j++) {
			String courseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();

			if (courseName.contains(courseToEnroll)) {
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																									// specific course
				break;
			}
		}

		Thread.sleep(2000);
		c.getPopupSignupButtonCourse().click();
		Thread.sleep(2000);
		Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
		Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
		Assert.assertEquals(courseNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(courseTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(courseInstructorDisplayed, c.getCourseInstructor().getText());

				
		int radioButtonCount = driver.findElements(By.tagName("label")).size();
		for (int i=0; i<radioButtonCount; i++)
		{
			if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Course Fee"))
					{
					driver.findElements(By.tagName("label")).get(i).click();
					break;
					}
		}

		c.getContinueButton().click();
		Thread.sleep(2000);
		reusableMethods.ReviewSectionValidation("Fee(s)");
		
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
		Thread.sleep(1000);
		ThankYouPO TY = new ThankYouPO(driver);

		//Verifies the text on Thank You page and the links to navigate to Dashboard and other pages are displayed
				reusableMethods.ThankYouPageValidations();
				
		//Note down the Receipt number
		String receiptNumber = TY.getReceiptNumber().getText();
				
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
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[(contains@class, 'swal2-center')]")));
		dp.getMyAccountAccountHistory().click();
		
		AcctHistoryPO ahp = new AcctHistoryPO(driver);
		
		while(!ahp.getReceiptNumberTable().isDisplayed())
		{
			Thread.sleep(2000);	
			System.out.println("waiting");
		}
		
		//Clicks on the Receiptnumber in Account History 
		
		ahp.getSearchField().sendKeys(receiptNumber);
				
		Thread.sleep(2000);
		ahp.getReceiptNumber().click();
		Thread.sleep(1000);
		//Verifies the amount in the receipt is the same as it was displayed on the Purchase Packages page
//		System.out.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText());
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
				.contains(totalAmt));
		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
		Thread.sleep(2000);
		reusableMethods.returnToDashboard();
		reusableMethods.unenrollFromCourse(dsiredMonthYear);
		reusableMethods.memberLogout();
	}
	
	@Test(priority = 4, description = "Enroll With Saved Card")
	public void EnrollWithSavedCard() throws InterruptedException, IOException {
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember7_username"), prop.getProperty("activeMember7_password"));
//		reusableMethods.unenrollFromCourse(dsiredMonthYear);
		Thread.sleep(2000);
		reusableMethods.returnToDashboard();
		DashboardPO d = new DashboardPO(driver);
		
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		
		d.getMyCoursesEventsScheduleButton().click();
			
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
				
		ClassSignUpPO c = new ClassSignUpPO(driver);
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
		
//		System.out.println(driver.findElement(By.xpath("//label[@id='dec']")).getText());
//		driver.findElement(By.xpath("//label[@id='dec']")).click();
		WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
		int monthCount = MonthNames.findElements(By.tagName("label")).size();
				for (int i = 0; i < monthCount; i++)
				{
					String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
					if (monthName.equals(CourseStartMonth))
					{
						 MonthNames.findElements(By.tagName("label")).get(i).click();
						 break;
					}
						
				}
					
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));


		
		int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j = 0; j < CourseCount; j++) {
			String CourseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();

			if (CourseName.contains(courseToEnroll)) {
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																									// specific Course
				break;
			}
		}

		Thread.sleep(1000);
		c.getPopupSignupButtonCourse().click();
		Thread.sleep(1000);
		
		Assert.assertEquals(courseNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(courseTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(courseInstructorDisplayed, c.getCourseInstructor().getText());

				
		int radioButtonCount = driver.findElements(By.tagName("label")).size();
		for (int i=0; i<radioButtonCount; i++)
		{
			if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Course Fee"))
					{
					driver.findElements(By.tagName("label")).get(i).click();
					break;
					}
		}

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
		
		System.out.println(totalAmt1);
		
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
				Thread.sleep(1000);
				ThankYouPO TY = new ThankYouPO(driver);

				//Verifies the text on Thank You page and the links to navigate to Dashboard and other pages are displayed
				reusableMethods.ThankYouPageValidations();

				//Note down the Receipt number
				String receiptNumber2 = TY.getReceiptNumber().getText();
								
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
				
				Thread.sleep(2000);
				ahp.getReceiptNumber().click();
				Thread.sleep(1000);
				//Verifies the amount in the receipt is the same as it was displayed on the Purchase Packages page
//				System.out.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText());
				Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
						.contains(totalAmt1));
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
				Thread.sleep(2000);
				reusableMethods.returnToDashboard();
				reusableMethods.unenrollFromCourse(dsiredMonthYear);
				reusableMethods.memberLogout();

	}
	@Test(priority=5, description = "Enroll in Course with New Card")
	
	public void EnrollWithNewCard() throws InterruptedException, IOException {
	
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember8_username"), prop.getProperty("activeMember8_password"));
//		reusableMethods.unenrollFromCourse(dsiredMonthYear);
		Thread.sleep(1000);
		reusableMethods.returnToDashboard();
		DashboardPO d = new DashboardPO(driver);
		
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		
		d.getMyCoursesEventsScheduleButton().click();
			
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
	
		ClassSignUpPO c = new ClassSignUpPO(driver);
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
		
//		System.out.println(driver.findElement(By.xpath("//label[@id='dec']")).getText());
//		driver.findElement(By.xpath("//label[@id='dec']")).click();
		WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
		int monthCount = MonthNames.findElements(By.tagName("label")).size();
				for (int i = 0; i < monthCount; i++)
				{
					String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
					if (monthName.equals(CourseStartMonth))
					{
						 MonthNames.findElements(By.tagName("label")).get(i).click();
						 break;
					}
						
				}
					
	    wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));


		
		int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j = 0; j < CourseCount; j++) {
			String CourseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();

			if (CourseName.contains(courseToEnroll)) {
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																									// specific Course
				break;
			}
		}

		Thread.sleep(1000);
		c.getPopupSignupButtonCourse().click();
		Thread.sleep(1000);
		
		Assert.assertEquals(courseNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(courseTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(courseInstructorDisplayed, c.getCourseInstructor().getText());

				
		int radioButtonCount = driver.findElements(By.tagName("label")).size();
		for (int i=0; i<radioButtonCount; i++)
		{
			if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Course Fee"))
					{
					driver.findElements(By.tagName("label")).get(i).click();
					break;
					}
		}

		c.getContinueButton().click();
		
		Thread.sleep(3000);
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);
				
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		
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

//		System.out.println(PM.getNameOnCardField().getAttribute("value"));
 		Assert.assertEquals(prop.getProperty("activeMember8_fullname"),PM.getNameOnCardField().getAttribute("value"));
 		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click();", PM.getCardNumberField()); 
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
				Thread.sleep(1000);
				ThankYouPO TY = new ThankYouPO(driver);

				//Verifies the text on Thank You page and the links to navigate to Dashboard and other pages are displayed
				reusableMethods.ThankYouPageValidations();

				//Note down the Receipt number
				String receiptNumber4 = TY.getReceiptNumber().getText();
								
				Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
				TY.getPrintReceiptButton().click();
				Thread.sleep(2000);
				Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
				
				//Verifies the buttons on Print Receipt Popup
				reusableMethods.ReceiptPopupValidations();
				
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
				Thread.sleep(3000);

				//Navigate to Select Courses
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
				
				Thread.sleep(2000);
				ahp.getReceiptNumber().click();
				Thread.sleep(1000);
				//Verifies the amount in the receipt is the same as it was displayed on the Purchase Packages page
//				System.out.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText());
				Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
						.contains(totalAmt2));
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
				Thread.sleep(2000);
				reusableMethods.returnToDashboard();
	}
		
	@Test(priority = 6, description = "Unenroll from the course", enabled = true)
	public void unenrollFromCourse() throws IOException, InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		CalendarPO cp = new CalendarPO(driver);
		
		Thread.sleep(2000);
		reusableWaits.waitForDashboardLoaded();
		d.getMenuMyActivies().click();
		
		while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1"))
		{
			Thread.sleep(500);
		}
		
		d.getMenuMyCalendar().click();
		String monthYear = cp.getMonthYear().getText();
		while(!monthYear.equals(dsiredMonthYear))
		{
			cp.getRightArrow().click();
			monthYear = cp.getMonthYear().getText();
		}
		
		cp.getCalDayBadge().click();
		cp.getCalEventTitle().click();
		cp.getUnEnrollBtn().click();
		UnenrollPO u = new UnenrollPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));
		u.getUnenrollButton().click();
		wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
		u.getUnenrollConfirmYesButton().click();
		wait.until(ExpectedConditions.stalenessOf(u.getUnenrollConfirmYesButton()));
		wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
		Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
		u.getUnenrollConfirmYesButton().click();
		Thread.sleep(2000);
		
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
