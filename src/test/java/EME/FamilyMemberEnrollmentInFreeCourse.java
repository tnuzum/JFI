package EME;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class FamilyMemberEnrollmentInFreeCourse extends base{
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String CourseStartMonth = "Dec";
	private static String dsiredMonthYear = "December 2020";
	private static String courseToEnroll = "FREE COURSE AUTO";
	private static String courseNameDisplayed = "Free Course Auto";
	private static String courseTimeDisplayed = "Start Time: 4:30 PM";
	private static String courseInstructorDisplayed = "Course Instructor: Jillian S";
	private static String courseInstructorDisplayedOnSearchScreen = "Inst: Jillian S";
	private static String courseTimeDisplayedOnSearchScreen = "4:30 PM";
	private static String courseDuration = "60 min";
	private static String member1 = "Cadmember";
	private static String member1Rate = "Not Eligible";
	private static String member2 = "Feemember";
	private static String member2Rate = "Free";
	private static String member3 = "Freemember";
	private static String member3Rate = "Free";
	private static String member4 = "Freeze";
	private static String member4Rate = "Not Eligible";
	private static String member5 = "Hoh";
	private static String member5Rate = "Free";
	private static String member6 = "Memberwithpunch";
	private static String member6Rate = "Free";
	private static String member7 = "Outpermtdhrs";
	private static String member7Rate = "Not Eligible";
	private static String member8 = "Terminate";
	private static String member8Rate = "Not Eligible";
	

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}
	
	@Test(priority = 1, description = "Family Member Enrollment")
	public void FamilyEnrollInFreeCourse() throws IOException, InterruptedException {
	reusableMethods.activeMemberLogin("hoh", "Testing1!");
	//reusableMethods.unenrollFromClass();
	//Thread.sleep(2000);
	//reusableMethods.returnToDashboard();
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
	
	c.getCourseFilter().click();
	c.getCourseKeyword().click();
	c.getSearchField().sendKeys("free");
	c.getCourseApplyFilters().click();

	int CourseCount = c.getClassTable().size();
	for (int j = 0; j < CourseCount; j++) {
		
		WebElement w = c.getClassTable().get(j);
		WebElement w1 = c.getClassTimeAndDuration().get(j);
		String courseName = w.getText();
		String courseTimeAndDuration = w1.getText();
        
		if (courseName.contains(courseToEnroll)) 
		{
			Assert.assertTrue(courseName.contains(courseInstructorDisplayedOnSearchScreen));
			Assert.assertTrue(courseTimeAndDuration.contains(courseTimeDisplayedOnSearchScreen));
			Assert.assertTrue(courseTimeAndDuration.contains(courseDuration));
			
			List<WebElement> getMemberRate = w.findElements(By.className("ng-star-inserted"));
		
			int count = getMemberRate.size();
			
			for (int k = 0; k<count; k++)
			{
						
				if (getMemberRate.get(k).getText().contains(member1))
					Assert.assertTrue(getMemberRate.get(k).getText().contains(member1Rate));
				if (getMemberRate.get(k).getText().contains(member2))
					Assert.assertTrue(getMemberRate.get(k).getText().contains(member2Rate));
				if (getMemberRate.get(k).getText().contains(member3))
					Assert.assertTrue(getMemberRate.get(k).getText().contains(member3Rate));
				if (getMemberRate.get(k).getText().contains(member4))
					Assert.assertTrue(getMemberRate.get(k).getText().contains(member4Rate));
				if (getMemberRate.get(k).getText().contains(member5))
					Assert.assertTrue(getMemberRate.get(k).getText().contains(member5Rate));
				if (getMemberRate.get(k).getText().contains(member6))
					Assert.assertTrue(getMemberRate.get(k).getText().contains(member6Rate));
				if (getMemberRate.get(k).getText().contains(member7))
					Assert.assertTrue(getMemberRate.get(k).getText().contains(member7Rate));
				if (getMemberRate.get(k).getText().contains(member8))
						Assert.assertTrue(getMemberRate.get(k).getText().contains(member8Rate));
					
			}
				
			w.click(); // Click on the specific course
			break;
		}
	}

	
						
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'modal-content')]")));
		while (c.getClasslabel().getText().isBlank())
		{
			Thread.sleep(500);
		}
		
		Assert.assertEquals(c.getClasslabel().getText(), courseNameDisplayed); // Verifies the class name
		int count = c.getFmlyMemberLabel().size();
				
		// Selects the other eligible members
			for ( int i =0; i<count; i++)
			{
				
				WebElement fml =  c.getFmlyMemberLabel().get(i);
				WebElement fmc =  c.getFmlyMemberCheckBox().get(i);
						
			if (fml.getText().contains(member2)) 
				fml.click();   // Selects the member
				
			
			if (fml.getText().contains(member3)) 
			    fml.click();   // Selects the member
			
			if (fml.getText().contains(member5))
				Assert.assertTrue(fmc.isSelected()); 
				
			
			if (fml.getText().contains(member6)) 
			    fml.click();   // Selects the member
			    
		}
			c.getPopupSignupButtonCourse().click();
		
		while (c.getClassName().getText().isBlank())
		{
			Thread.sleep(500);
		}

		Assert.assertEquals(courseNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(courseTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(courseInstructorDisplayed, c.getCourseInstructor().getText());

				
		for (int i = 0; i<c.getMemberSections().size(); i++)
		{
			String paymentOptions = c.getMemberSections().get(i).getText();
			WebElement Label = c.getMemberSections().get(i).findElement(By.tagName("label"));
														
			if (paymentOptions.contains(member2))
					{
				       Assert.assertTrue(Label.getText().contains("Free"));
					   Assert.assertTrue(Label.isEnabled());
					}
			
			if (paymentOptions.contains(member3))
			{
				   Assert.assertTrue(Label.getText().contains("Free"));
				   Assert.assertTrue(Label.isEnabled());
			}
			
			if (c.getMemberSections().get(i).getText().contains(member5)) //This member has all the payment options
			{
				 Assert.assertTrue(Label.getText().contains("Free"));
				   Assert.assertTrue(Label.isEnabled());
			}
			
			if (c.getMemberSections().get(i).getText().contains(member6)) //This member has all the payment options
			{
				 Assert.assertTrue(Label.getText().contains("Free"));
				   Assert.assertTrue(Label.isEnabled());
			}
							
				}
		c.getContinueButton().click();
		wait.until(ExpectedConditions.elementToBeClickable(c.getPopupClose()));
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
		int count1 = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < count1; i++) {
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
	
	@Test(dataProvider = "getData", dependsOnMethods = {"FamilyEnrollInFreeCourse"})
	public void FamilyMemberUnenroll(String username, String password) throws InterruptedException, IOException {
		reusableMethods.activeMemberLogin(username, password);
		reusableMethods.unenrollFromCourse(dsiredMonthYear);
		reusableMethods.memberLogout();
		}
	@DataProvider
	public Object [][] getData()

		{
			Object[][] data = new Object[4][2];
			data[0][0] = "freemember";
			data[0][1] = "Testing1!";
			data[1][0] = "feemember";
			data[1][1] = "Testing1!";
			data[2][0] = "memberwithpunch";
			data[2][1] = "Testing1!";
			data[3][0] = "hoh";
			data[3][1] = "Testing1!";
			return data;
		}
				
//	@AfterTest
    @AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
	
	
 