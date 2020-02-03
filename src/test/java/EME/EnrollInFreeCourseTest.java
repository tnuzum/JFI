package EME;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.CalendarPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.ThankYouPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;


public class EnrollInFreeCourseTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());

private static String CourseStartMonth = "Dec";
private static String dsiredMonthYear = "December 2020";


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
		//reusableMethods.unenrollFromCourse(dsiredMonthYear);
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
//			System.out.println(driver.findElement(By.xpath("//label[@id='dec']")).getText());
//			driver.findElement(By.xpath("//label[@id='dec']")).click();
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
//		reusableMethods.memberLogout();
		}

	@Test (priority = 2, description = "Unenroll from the course")
		
	public void unenrollFromCourse() throws IOException, InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		CalendarPO cp = new CalendarPO(driver);
		
		Thread.sleep(2000);
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
		u.getUnenrollButton().click();
		Thread.sleep(2000);
		u.getUnenrollConfirmYesButton().click();
		Thread.sleep(2000);
		Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
		u.getUnenrollConfirmYesButton().click();
		
		reusableMethods.returnToDashboard();
		reusableMethods.memberLogout();
		

	}
	
	@Test (priority = 3, description = "Enroll In course Free Due to Existing Punches") //Bug 155892 has been created
	
	public void EnrollInCourseFreeWithExistingPunches() throws IOException, InterruptedException
	{	
	reusableMethods.activeMember6Login();
//	reusableMethods.unenrollFromCourse(dsiredMonthYear);
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
		Thread.sleep(2000);
			 
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
		Assert.assertEquals("Start Time: 08:00 PM", c.getClassStartTime().getText());
		Assert.assertEquals("Instructor: Andrea", c.getClassInstructor().getText());

				
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
			
	reusableMethods.unenrollFromCourse(dsiredMonthYear);
	reusableMethods.memberLogout();
	}
	
	@Test (priority = 4, description = "Enroll In Course Free Due to Service D") 
	
	public void EnrollInCourseFreeWithServiceD() throws IOException, InterruptedException
	{	
	reusableMethods.activeMember3Login();
//	reusableMethods.unenrollFromCourse(dsiredMonthYear);
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
		Thread.sleep(2000);

				 
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
		Assert.assertEquals("Start Time: 06:30 PM", c.getClassStartTime().getText());
		Assert.assertEquals("Instructor: Andrea", c.getClassInstructor().getText());
		
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
	reusableMethods.unenrollFromCourse(dsiredMonthYear);
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