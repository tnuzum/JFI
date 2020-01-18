package EME;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.ThankYouPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;


public class EnrollInFreeCourseTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());


//	@BeforeTest
	@BeforeClass
		public void initialize() throws IOException, InterruptedException
		{
			 driver = initializeDriver();
			 log.info("Driver Initialized");
			 driver.get(prop.getProperty("EMELoginPage"));
		}
		
	@Test (priority = 1, description = "Enroll in free course")
		
	public void EnrollInZeroDollarCourse() throws IOException, InterruptedException
		{	
		reusableMethods.activeMemberLogin("MemberWithPunch", "Testing1!");
		//reusableMethods.unenrollFromCourse();
		//Thread.sleep(1000);
		//reusableMethods.returnToDashboard();
		reusableWaits.waitForDashboardLoaded();
			DashboardPO d = new DashboardPO(driver);
			BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		
		 d.getMyCoursesEventsScheduleButton().click();
		 Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
			Thread.sleep(2000);
			
			ClassSignUpPO c = new ClassSignUpPO(driver);
	
/*			c.getCourseFilter().click();
			c.getCourseKeyword().click();
			c.getSearchField().sendKeys("FREE COURSE AUTO");
			c.getApplyFilters().click();
*/			
			int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
			for (int j= 0; j<CourseCount; j++)
			 {
				String CourseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
								
				if (CourseName.contains("FREE COURSE AUTO"))
				{
					driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific Course
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
			Assert.assertEquals("Free Course Auto", c.getClassName().getText());
			Assert.assertEquals("Start Time: 04:30 PM", c.getClassStartTime().getText());
			Assert.assertEquals("Instructor: Jillian S", c.getClassInstructor().getText());

					
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
		ahp.getSearchField().sendKeys("Free Course Auto");
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
		reusableMethods.memberLogout();
		}

	/*@Test (priority = 2, description = "Unenroll from the course")
		
	public void unenrollFromCourse() throws IOException, InterruptedException
		{	
		DashboardPO d = new DashboardPO(driver);

//		
			boolean enrolled = reusableMethods.isElementPresent(By.xpath("//div[@class='class-table-container']"));
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
				AssertJUnit.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
				u.getUnenrollConfirmYesButton().click();
				
				reusableMethods.returnToDashboard();
				reusableMethods.memberLogout();
				}
		 
			else
				{
		System.out.println("enrollment not displayed");
				}
		
	}*/
	
	@Test (priority = 3, description = "Enroll In course Free Due to Existing Punches") //Bug 155892 has been created
	
	public void EnrollInCourseFreeWithExistingPunches() throws IOException, InterruptedException
	{	
	reusableMethods.activeMember6Login();
//	reusableMethods.unenrollFromCourse();
//	Thread.sleep(1000);
//	reusableMethods.returnToDashboard();
	reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		
		//Noting down the Package Units before enrolling in Course
				int IntPackageCountBefore = 0;
				int IntPackageCountAfter = 0;

				IntPackageCountBefore = reusableMethods.getPackageUnits("ServiceOA");
	
	 d.getMyCoursesEventsScheduleButton().click();
	 Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
		Thread.sleep(2000);
		
		ClassSignUpPO c = new ClassSignUpPO(driver);

			 
		int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j= 0; j<CourseCount; j++)
		 {
			String CourseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
							
			if (CourseName.contains("COURSEFREEWITHEXISTINGPUNCHES"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific Course
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
		Assert.assertEquals("CourseFreeWithExistingPunches", c.getClassName().getText());
		Assert.assertEquals("Start Time: 12:00 AM", c.getClassStartTime().getText());
		Assert.assertEquals("Instructor: Max Gibbs", c.getClassInstructor().getText());

				
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
	ahp.getSearchField().sendKeys("CourseFreeWithExistingPunches");
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
	
	//Note the package units after enrolling
			IntPackageCountAfter = reusableMethods.getPackageUnits("ServiceOA");
//			System.out.println(IntUnitCountAfter);
				
			//Verifies the package units is now decremented by one unit
			IntPackageCountBefore--;
			Assert.assertEquals(IntPackageCountBefore, IntPackageCountAfter); 
//	reusableMethods.unenrollFromCourse();
	reusableMethods.memberLogout();
	}
	
	@Test (priority = 4, description = "Enroll In Course Free Due to Service D") 
	
	public void EnrollInCourseFreeWithServiceD() throws IOException, InterruptedException
	{	
	reusableMethods.activeMember3Login();
//	reusableMethods.unenrollFromCourse();
//	Thread.sleep(1000);
//	reusableMethods.returnToDashboard();
	reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
	
	 d.getMyCoursesEventsScheduleButton().click();
	 Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
		Thread.sleep(2000);
		
		ClassSignUpPO c = new ClassSignUpPO(driver);

				 
		int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j= 0; j<CourseCount; j++)
		 {
			String CourseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
							
			if (CourseName.contains("COURSEFREEWITHSERVICED"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific Course
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
		Assert.assertEquals("CourseFreeWithServiceD", c.getClassName().getText());
		Assert.assertEquals("Start Time: 12:00 AM", c.getClassStartTime().getText());
		Assert.assertEquals("Instructor: Max Gibbs", c.getClassInstructor().getText());
		
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
	ahp.getSearchField().sendKeys("CourseFreeWithServiceD");
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
//	reusableMethods.unenrollFromCourse();
	reusableMethods.memberLogout();
	}

//	@AfterTest
/*	@AfterClass
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}*/
}
