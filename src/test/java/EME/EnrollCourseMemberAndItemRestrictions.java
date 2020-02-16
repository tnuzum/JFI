package EME;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollCourseMemberAndItemRestrictions extends base {
	private static String CourseStartYear = "2019";
	private static String CourseStartMonth = "Jan";
	
//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	
	@Test(priority = 1, description = "Validating that Frozen member cannot enroll When the club settings won't allow")
	public void FrozenMemberCannotEnroll() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("freeze", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
		Thread.sleep(2000);
		
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
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
		Thread.sleep(2000);
		
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
		Thread.sleep(2000);
		
		ClassSignUpPO c = new ClassSignUpPO(driver);

		
		
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
		Thread.sleep(2000);
		
		ClassSignUpPO c = new ClassSignUpPO(driver);

		
		
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
		Thread.sleep(2000);
		
		ClassSignUpPO c = new ClassSignUpPO(driver);
		
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
		Thread.sleep(2000);
		
		ClassSignUpPO c = new ClassSignUpPO(driver);
		String year = driver.findElement(By.xpath("//span[contains(@class, 'btn-white')]")).getText();
		while (!year.contains(CourseStartYear))
		{
			driver.findElement(By.xpath("//i[contains(@class, 'double-left')]")).click();
			year = driver.findElement(By.xpath("//span[contains(@class, 'btn-white')]")).getText();
		}
		
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
			String courseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
										
			if (courseName.contains("COURSEENROLLMENTENDED"))
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

	@Test(priority = 7, description = "Validating that the course cannnot be enrolled due to due to Membership Type Time Restrictions")
	public void CourseOutsidePermittedHours() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("outpermtdhrs", "Testing1!");
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
	//	@AfterTest
	@AfterClass
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
}
