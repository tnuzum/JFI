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
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.ThankYouPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;


public class EnrollInFreeClassTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());


//	@BeforeTest
	@BeforeClass
	@Parameters({"EMELoginPage"})
	public void initialize(String EMELoginPage) throws InterruptedException, IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(EMELoginPage);
		}
		
	@Test (priority = 1, description = "Enroll in free class")
		public void EnrollInZeroDollarClass() throws IOException, InterruptedException
		{	
		reusableMethods.activeMemberLogin("MemberWithPunch", "Testing1!");
		reusableMethods.unenrollFromClass();
		Thread.sleep(1000);
		reusableMethods.returnToDashboard();
			DashboardPO d = new DashboardPO(driver);
			BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		
		 d.getMyClassesScheduleButton().click();
		 Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
			Thread.sleep(2000);
			
			ClassSignUpPO c = new ClassSignUpPO(driver);
	
			c.getCalendarIcon().click();
			Thread.sleep(2000);
			DateFormat dateFormat = new SimpleDateFormat("d");
			Calendar today = Calendar.getInstance();
			 today.add(Calendar.DAY_OF_YEAR, 1);
			 String tomorrowsDate = dateFormat.format(today.getTime());
			 
			 int daycount = driver.findElements(By.tagName("td")).size(); //Get the daycount from the calendar
			 for (int i= 0; i<daycount; i++)
			 {
				String date = driver.findElements(By.tagName("td")).get(i).getText();
				if (date.contains(tomorrowsDate))
				{
					 driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
					 break;
				}
			 }
			 
			int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
			for (int j= 0; j<ClassCount; j++)
			 {
				String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
								
				if (className.contains("FREE CLASS AUTO"))
				{
					driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
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
			Assert.assertEquals("Free Class Auto", c.getClassName().getText());
			Assert.assertEquals("Start Time: 10:00 AM", c.getClassStartTime().getText());
			Assert.assertEquals("Class Instructor: ", c.getClassInstructor().getText());

			DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
			Calendar today1 = Calendar.getInstance();
			today1.add(Calendar.DAY_OF_YEAR, 1);
			String tomorrowsDayAndDate = dateFormat1.format(today1.getTime());

			Assert.assertEquals("Date: " + tomorrowsDayAndDate, c.getClassDate().getText());
			
			Assert.assertEquals(c.getHowYouWishToPay().getText(), "Free");
			Assert.assertTrue(c.getHowYouWishToPay().isEnabled());
			
			
		c.getContinueButton().click();
			Thread.sleep(2000);
		Assert.assertEquals("Success", c.getPopupMessage().getText());
		c.getPopupClose().click();
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
		Thread.sleep(1000);

		
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
		Thread.sleep(1000);
		DashboardPO dp = new DashboardPO(driver);
		dp.getMyAccountAccountHistory().click();
		
		//Clicks on the Receiptnumber in Account History 
		AcctHistoryPO ahp = new AcctHistoryPO(driver);
		ahp.getSearchField().sendKeys(receiptNumber);
		for (int k = 0; k < ahp.getReceiptNumbers().size(); k++) {
			receiptNumber1 = ahp.getReceiptNumbers().get(k).getText().trim();

			if (receiptNumber1.equals(receiptNumber)) {
				ahp.getReceiptNumbers().get(k).click();
				break;
			}
		}
		Thread.sleep(1000);
		//Verifies the Invoice amount is $0.00
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
				.contains("$0.00"));
		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
		Thread.sleep(1000);
		reusableMethods.returnToDashboard();
		}

	@Test (priority = 2, description = "Unenroll from the class")
		public void unenrollFromClass() throws IOException, InterruptedException
		{	
		DashboardPO d = new DashboardPO(driver);

//		
			boolean enrolled = reusableMethods.isElementPresent(By.xpath("//classeswidget//div[@class='class-table-container']"));
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='class-table-container']")));
			if (enrolled == true)
			{
			
			while (!d.getMyClassesClass1GearButton().isDisplayed())
			{
				Thread.sleep(1000);
				System.out.println("Sleeping for 1 second");
			}
		d.getMyClassesClass1GearButton().click();
			Thread.sleep(2000);
		d.getmyClassesUnenrollButton().click();
			Thread.sleep(1000);
			UnenrollPO u = new UnenrollPO(driver);
				u.getUnenrollButton().click();
					Thread.sleep(2000);
				u.getUnenrollConfirmYesButton().click();
				Thread.sleep(1000);
				Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
				u.getUnenrollConfirmYesButton().click();
				
				reusableMethods.returnToDashboard();
				reusableMethods.memberLogout();
				}
		 
			else
				{
		System.out.println("enrollement not displayed");
				}
		
	}
	
	@Test (priority = 3, description = "Enroll In Class Free Due to Existing Punches") //Bug 155892 has been created
	public void EnrollInClassFreeWithExistingPunches() throws IOException, InterruptedException
	{	
	reusableMethods.activeMember8Login();
	reusableMethods.unenrollFromClass();
	Thread.sleep(1000);
	reusableMethods.returnToDashboard();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		
		//Noting down the Package Units before enrolling in Course
		int IntPackageCountBefore = 0;
		int IntPackageCountAfter = 0;

		IntPackageCountBefore = reusableMethods.getPackageUnits("ServiceNC");
	
	 d.getMyClassesScheduleButton().click();
	 Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
		Thread.sleep(2000);
		
		ClassSignUpPO c = new ClassSignUpPO(driver);

		c.getCalendarIcon().click();
		Thread.sleep(2000);
		DateFormat dateFormat = new SimpleDateFormat("d");
		Calendar today = Calendar.getInstance();
		 today.add(Calendar.DAY_OF_YEAR, 1);
		 String tomorrowsDate = dateFormat.format(today.getTime());
		 
		 int daycount = driver.findElements(By.tagName("td")).size(); //Get the daycount from the calendar
		 for (int i= 0; i<daycount; i++)
		 {
			String date = driver.findElements(By.tagName("td")).get(i).getText();
			if (date.contains(tomorrowsDate))
			{
				 driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
				 break;
			}
		 }
		 
		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j= 0; j<ClassCount; j++)
		 {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
							
			if (className.contains("CLASSFREEWITHEXISTINGPUNCHES"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
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
		Assert.assertEquals("ClassFreeWithExistingPunches", c.getClassName().getText());
		Assert.assertEquals("Start Time: 10:00 AM", c.getClassStartTime().getText());
		Assert.assertEquals("Class Instructor: Max Gibbs", c.getClassInstructor().getText());

		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDayAndDate = dateFormat1.format(today1.getTime());

		Assert.assertEquals("Date: " + tomorrowsDayAndDate, c.getClassDate().getText());
		
		int radioButtonCount = driver.findElements(By.tagName("label")).size();
		for (int i=0; i<radioButtonCount; i++)
		{
			if (driver.findElements(By.tagName("label")).get(i).getText().equals("Use Existing Package"))
					{
					Assert.assertTrue(driver.findElements(By.tagName("label")).get(i).isEnabled());
					driver.findElements(By.tagName("label")).get(i).click();
					break;
					}
		}
		
		
	c.getContinueButton().click();
		Thread.sleep(2000);
	Assert.assertEquals("Success", c.getPopupMessage().getText());
	c.getPopupClose().click();
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
	Thread.sleep(1000);

	
	//Navigate to Appointments Page
	int count = driver.findElements(By.tagName("a")).size();
	for (int i = 0; i < count; i++) {
		if (driver.findElements(By.tagName("a")).get(i).getText().equals("Appointments"))

		{
			// reusableWaits.linksToBeClickable();
			driver.findElements(By.tagName("a")).get(i).click();
			break;
		}

	}
	
	//Verifies the link navigates to the right page
	Assert.assertEquals("Appointments", driver.getTitle());
	Thread.sleep(1000);
	DashboardPO dp = new DashboardPO(driver);
	dp.getMenuMyAccount().click();
	Thread.sleep(2000);
	dp.getMenuAccountHistory().click();

	
	//Clicks on the Receiptnumber in Account History 
	AcctHistoryPO ahp = new AcctHistoryPO(driver);
	ahp.getSearchField().sendKeys(receiptNumber2);
	for (int k = 0; k < ahp.getReceiptNumbers().size(); k++) {
		receiptNumber3 = ahp.getReceiptNumbers().get(k).getText().trim();

		if (receiptNumber3.equals(receiptNumber2)) {
			ahp.getReceiptNumbers().get(k).click();
			break;
		}
	}
    Thread.sleep(1000);
	//Verifies the Invoice amount is $0.00
	Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
			.contains("$0.00"));
	TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
	Thread.sleep(1000);
	reusableMethods.returnToDashboard();
	
	//Note the package units after enrolling
	IntPackageCountAfter = reusableMethods.getPackageUnits("ServiceNC");
//	System.out.println(IntUnitCountAfter);
		
	//Verifies the package units is now decremented by one unit
	IntPackageCountBefore--;
	Assert.assertEquals(IntPackageCountBefore, IntPackageCountAfter); 
	reusableMethods.unenrollFromClass();
	reusableMethods.memberLogout();
	}
	
	@Test (priority = 4, description = "Enroll In Class Free Due to Service D") 
	public void EnrollInClassFreeWithServiceD() throws IOException, InterruptedException
	{	
	reusableMethods.activeMember3Login();
	reusableMethods.unenrollFromClass();
	Thread.sleep(1000);
	reusableMethods.returnToDashboard();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
	
	 d.getMyClassesScheduleButton().click();
	 Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
		Thread.sleep(2000);
		
		ClassSignUpPO c = new ClassSignUpPO(driver);

		c.getCalendarIcon().click();
		Thread.sleep(2000);
		DateFormat dateFormat = new SimpleDateFormat("d");
		Calendar today = Calendar.getInstance();
		 today.add(Calendar.DAY_OF_YEAR, 1);
		 String tomorrowsDate = dateFormat.format(today.getTime());
		 
		 int daycount = driver.findElements(By.tagName("td")).size(); //Get the daycount from the calendar
		 for (int i= 0; i<daycount; i++)
		 {
			String date = driver.findElements(By.tagName("td")).get(i).getText();
			if (date.contains(tomorrowsDate))
			{
				 driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
				 break;
			}
		 }
		 
		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j= 0; j<ClassCount; j++)
		 {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
							
			if (className.contains("CLASSFREEWITHSERVICED"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
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
		Assert.assertEquals("ClassFreeWithServiceD", c.getClassName().getText());
		Assert.assertEquals("Start Time: 10:00 AM", c.getClassStartTime().getText());
		Assert.assertEquals("Class Instructor: Max Gibbs", c.getClassInstructor().getText());

		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		String tomorrowsDayAndDate = dateFormat1.format(today1.getTime());

		Assert.assertEquals("Date: " + tomorrowsDayAndDate, c.getClassDate().getText());
		
		Assert.assertEquals(c.getHowYouWishToPay().getText(), "Free");
		Assert.assertTrue(c.getHowYouWishToPay().isEnabled());
		
		
	c.getContinueButton().click();
		Thread.sleep(2000);
	Assert.assertEquals("Success", c.getPopupMessage().getText());
	c.getPopupClose().click();
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
	Thread.sleep(1000);

	
	//Navigate to Appointments Page
	int count = driver.findElements(By.tagName("a")).size();
	for (int i = 0; i < count; i++) {
		if (driver.findElements(By.tagName("a")).get(i).getText().equals("Appointments"))

		{
			// reusableWaits.linksToBeClickable();
			driver.findElements(By.tagName("a")).get(i).click();
			break;
		}

	}
	
	//Verifies the link navigates to the right page
	Assert.assertEquals("Appointments", driver.getTitle());
	Thread.sleep(1000);
	DashboardPO dp = new DashboardPO(driver);
	dp.getMenuMyAccount().click();
	Thread.sleep(2000);
	dp.getMenuAccountHistory().click();
	
	//Clicks on the Receiptnumber in Account History 
	AcctHistoryPO ahp = new AcctHistoryPO(driver);
	ahp.getSearchField().sendKeys(receiptNumber4);
	for (int k = 0; k < ahp.getReceiptNumbers().size(); k++) {
		receiptNumber5 = ahp.getReceiptNumbers().get(k).getText().trim();

		if (receiptNumber5.equals(receiptNumber4)) {
			ahp.getReceiptNumbers().get(k).click();
			break;
		}
	}
    Thread.sleep(1000);
	//Verifies the Invoice amount is $0.00
	Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
			.contains("$0.00"));
	TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
	Thread.sleep(1000);
	reusableMethods.returnToDashboard();
	reusableMethods.unenrollFromClass();
	reusableMethods.memberLogout();
	}

//	@AfterTest
	@AfterClass
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
}
