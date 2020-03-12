package EME_EnvURL;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollCourseMemberAndItemRestrictions extends base {
	private static String CourseStartYear = "2019";
	private static String CourseStartMonth1 = "Jan";
	private static String CourseStartMonth2 = "Dec";
	private static String dsiredMonthYear = "December 2020";
	
//	@BeforeTest
	@BeforeClass
	@Parameters({"EMELoginPage"})
	public void initialize(String EMELoginPage) throws InterruptedException, IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(EMELoginPage);

	}
	
	@Test(priority = 1, description = "Validating that Frozen member cannot enroll When the club settings won't allow")
	public void FrozenMemberCannotEnroll() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("freezemember", "Testing1!");
		reusableWaits.waitForDashboardLoaded1();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
				
		ClassSignUpPO c = new ClassSignUpPO(driver);

				
		driver.findElement(By.xpath("(//div[contains(@class, 'column2')])[1]")).click();
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("Membership restrictions have limited enrollment into this course.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());
		c.getPopupCancelButtonCourse().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();

}
	@Test(priority = 2, description = "Validating that Terminated member cannot enroll When the club settings won't allow")
	public void TerminatedMemberCannotEnroll() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("terminate", "Testing1!");
		reusableWaits.waitForDashboardLoaded1();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
				
		ClassSignUpPO c = new ClassSignUpPO(driver);

				
		driver.findElement(By.xpath("(//div[contains(@class, 'column2')])[1]")).click();
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("Membership restrictions have limited enrollment into this course.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());
		c.getPopupCancelButtonCourse().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();

}
	@Test(priority = 3, description = "Validating that the course cannnot be enrolled in when the enrollment window has closed")
	public void CourseEnrollmentWindowClosed() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("feemember", "Testing1!");
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
		
		int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
				
		for (int j= 0; j<CourseCount; j++)
		 {
			String courseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
										
			if (courseName.contains("COURSEENROLLMENTCLOSED"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
			
		 }
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("The online enrollment window for this course has closed.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());
		c.getPopupCancelButtonCourse().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();
		 }
	
	@Test(priority = 4, description = "Validating that the course cannnot be enrolled due to Item Restrictions")
	public void CourseCannotBEnrolled() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("feemember", "Testing1!");
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
		
		int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
				
		for (int j= 0; j<CourseCount; j++)
		 {
			String courseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
										
			if (courseName.contains("COURSECANNOTBENROLLED"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
			
		 }
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("Online enrollment for this course is not allowed.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());
		c.getPopupCancelButtonCourse().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();
		 }
	@Test(priority = 5, description = "Validating that the course cannnot be enrolled due to the enrollment window is not open yet")
	public void CourseEnrollmentNotOpened() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("feemember", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
		
		WebDriverWait wait = new WebDriverWait(driver, 50);
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
		
		ClassSignUpPO c = new ClassSignUpPO(driver);
		
		WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
		int monthCount = MonthNames.findElements(By.tagName("label")).size();
				for (int i = 0; i < monthCount; i++)
				{
					String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
					if (monthName.equals(CourseStartMonth2))
					{
						 MonthNames.findElements(By.tagName("label")).get(i).click();
						 break;
					}
						
				}
					
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		
		
		int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
				
		for (int j= 0; j<CourseCount; j++)
		 {
			String courseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
										
			if (courseName.contains("COURSEENROLLMENTNOTOPENED"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
			
		 }
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("Online enrollment for this course is currently closed.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());
		c.getPopupCancelButtonCourse().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();
		 }
	
	@Test(priority = 6, description = "Validating that the course cannnot be enrolled as the course time is passed")
	public void CourseEnrollmentEnded() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("feemember", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
		
		ClassSignUpPO c = new ClassSignUpPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
		String year = driver.findElement(By.xpath("//span[contains(@class, 'btn-white')]")).getText();
		while (!year.contains(CourseStartYear))
		{
			driver.findElement(By.xpath("//i[contains(@class, 'double-left')]")).click();
			year = driver.findElement(By.xpath("//span[contains(@class, 'btn-white')]")).getText();
		}
		
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
		
		WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
		int monthCount = MonthNames.findElements(By.tagName("label")).size();
				for (int i = 0; i < monthCount; i++)
				{
					String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
					if (monthName.equals(CourseStartMonth1))
					{
						 MonthNames.findElements(By.tagName("label")).get(i).click();
						 break;
					}
						
				}
					
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
				
		int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
				
		for (int j= 0; j<CourseCount; j++)
		 {
			String courseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
										
			if (courseName.contains("COURSEENROLLMENTENDED"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
			
		 }
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("The online enrollment window for this course has closed.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());
		c.getPopupCancelButtonCourse().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();
		 }

	@Test(priority = 7, description = "Validating that the course cannnot be enrolled due to Membership Type Time Restrictions")
	public void CourseOutsidePermittedHours() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("outpermtdhrs", "Testing1!");
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
			
		int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
				
		for (int j= 0; j<CourseCount; j++)
		 {
			String courseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
										
			if (courseName.contains("COURSEWITHINELIGIBLETIME"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
			
		 }
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("Membership restrictions have limited enrollment into this course.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());
		c.getPopupCancelButtonCourse().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();
		 }
	
	@Test(priority = 8, description = "Validating that the course cannnot be enrolled due to Membership Type Restrictions at the club")
	public void ClubAccessDenied() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("hoh", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
				
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
		ClassSignUpPO c = new ClassSignUpPO(driver);
		
		WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
		int monthCount = MonthNames.findElements(By.tagName("label")).size();
				for (int i = 0; i < monthCount; i++)
				{
					String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
					if (monthName.equals(CourseStartMonth2))
					{
						 MonthNames.findElements(By.tagName("label")).get(i).click();
						 break;
					}
						
				}
			
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
				
		int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
				
		for (int j= 0; j<CourseCount; j++)
		 {
			String courseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
										
			if (courseName.contains("FAMILYENROLLCOURSE"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
			
		 }
		int memberCount = c.getDetailsPopup().findElements(By.tagName("label")).size();
		for (int i = 0; i<memberCount; i++)
		{
			if (c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText().contains("Cadmember"))
			{
				Assert.assertTrue(c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText().contains("Membership restrictions have limited course enrollment at this club."));
			}
		}
		c.getPopupCancelButtonCourse().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();
		 }
	
	@Test(priority = 9, description = "Validating that the course cannnot be enrolled due to Scheduling Conflict")
	public void CourseSchedulingConflict() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("hoh", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
				
		ClassSignUpPO c = new ClassSignUpPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
		
		WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
		int monthCount = MonthNames.findElements(By.tagName("label")).size();
				for (int i = 0; i < monthCount; i++)
				{
					String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
					if (monthName.equals(CourseStartMonth2))
					{
						 MonthNames.findElements(By.tagName("label")).get(i).click();
						 break;
					}
						
				}
				
				
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
			
		int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
				
		for (int j= 0; j<CourseCount; j++)
		 {
			String courseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
										
			if (courseName.contains("FAMILYENROLLCOURSE"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
			
		 }
		Thread.sleep(500);
		c.getPopupSignupButtonCourse().click();
		Thread.sleep(500);
		c.getContinueButton().click();
		wait.until(ExpectedConditions.elementToBeClickable(c.getPopupClose()));
		Assert.assertEquals("Success", c.getPopupMessage().getText());
		c.getPopupClose().click();
		
		//Navigate to Select Courses / Events
		int count1 = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < count1; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Courses / Events"))

			{
				// reusableWaits.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
		
		 MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
		 monthCount = MonthNames.findElements(By.tagName("label")).size();
		for (int i = 0; i < monthCount; i++)
		{
			String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
			if (monthName.equals(CourseStartMonth2))
			{
				 MonthNames.findElements(By.tagName("label")).get(i).click(); // Click on Decedmber month again
				 break;
			}
				
		}
		
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
		
		CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j= 0; j<CourseCount; j++)
		 {
			String courseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
										
			if (courseName.contains("CONFLICTCOURSE"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on a class at the same time as the member enrolled in other course
				 break;
			}
			
		 }
		int memberCount = c.getDetailsPopup().findElements(By.tagName("label")).size();
		for (int i = 0; i<memberCount; i++)
		{
			if (c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText().contains("hoh"))
			{
				Assert.assertTrue(c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText().contains("Scheduling Conflict"));
			}
		}
		c.getPopupCancelButtonCourse().click();
		Thread.sleep(500);
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
