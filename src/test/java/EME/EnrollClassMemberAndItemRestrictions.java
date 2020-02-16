package EME;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
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

public class EnrollClassMemberAndItemRestrictions extends base {
	private static String classStartMonth = "DEC";
	private static String classStartDate = "22";
	
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

		int daycount = driver.findElements(By.tagName("td")).size(); // Get the daycount from the calendar
		for (int i = 0; i < daycount; i++) {
			String date = driver.findElements(By.tagName("td")).get(i).getText();
			if (date.contains(tomorrowsDate)) {
				driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
				break;
			}
		}
		
		driver.findElement(By.xpath("(//div[contains(@class, 'column2')])[1]")).click();
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("Membership restrictions have limited enrollment into this class.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignUpButton().isEnabled());
		c.getPopupCancelButton().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();

}
	@Test(priority = 2, description = "Validating that Terminated member cannot enroll When the club settings won't allow")
	public void TerminatedMemberCannotEnroll() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("terminate", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
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

		int daycount = driver.findElements(By.tagName("td")).size(); // Get the daycount from the calendar
		for (int i = 0; i < daycount; i++) {
			String date = driver.findElements(By.tagName("td")).get(i).getText();
			if (date.contains(tomorrowsDate)) {
				driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
				break;
			}
		}
		
		driver.findElement(By.xpath("(//div[contains(@class, 'column2')])[1]")).click();
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("Membership restrictions have limited enrollment into this class.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignUpButton().isEnabled());
		c.getPopupCancelButton().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();

}
	
	@Test(priority = 3, description = "Validating that the class cannnot be enrolled due to the enrollment window has closed")
	public void ClassEnrollmentWindowClosed() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("feemember", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
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

		int daycount = driver.findElements(By.tagName("td")).size(); // Get the daycount from the calendar
		for (int i = 0; i < daycount; i++) {
			String date = driver.findElements(By.tagName("td")).get(i).getText();
			if (date.contains(tomorrowsDate)) {
				driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
				break;
			}
		}
		
		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j= 0; j<ClassCount; j++)
		 {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
							
			if (className.contains("CLASSENROLLMENTCLOSED"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
		 }
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("The online enrollment window for this class has closed.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignUpButton().isEnabled());
		c.getPopupCancelButton().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();

}
	@Test(priority = 4, description = "Validating that the class cannnot be enrolled due to Item Restrictions")
	public void ClassCannotBEnrolled() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("feemember", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
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

		int daycount = driver.findElements(By.tagName("td")).size(); // Get the daycount from the calendar
		for (int i = 0; i < daycount; i++) {
			String date = driver.findElements(By.tagName("td")).get(i).getText();
			if (date.contains(tomorrowsDate)) {
				driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
				break;
			}
		}
		
		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j= 0; j<ClassCount; j++)
		 {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
							
			if (className.contains("CLASSCANNOTBENROLLED"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
		 }
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("Online enrollment for this class is not allowed.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignUpButton().isEnabled());
		c.getPopupCancelButton().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();

}
	@Test(priority = 5, description = "Validating that the class cannnot be enrolled due to  the enrollment window is not open yet")
	public void ClassEnrollmentNotOpened() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("feemember", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
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
		
		String monthName = driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]")).getText();
		
				while (!monthName.contains(classStartMonth))
				{
					driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-next-button')]")).click();;
					monthName = driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]")).getText();
				}
					
					
					
		int daycount = driver.findElements(By.tagName("td")).size(); // Get the daycount from the calendar
		for (int i = 0; i < daycount; i++) {
			String date = driver.findElements(By.tagName("td")).get(i).getText();
			if (date.contains(classStartDate)) {
				driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
				break;
			}
		}
		
		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j= 0; j<ClassCount; j++)
		 {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
							
			if (className.contains("CLASSENROLLMENTNOTOPENED"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
		 }
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("Online enrollment for this class is currently closed.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignUpButton().isEnabled());
		c.getPopupCancelButton().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();

}
	@Test(priority = 6, description = "Validating that the class cannnot be enrolled as the class time is passed for the day")
	public void ClassEnrollmentEnded() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("feemember", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyClassesScheduleButton().click();
		Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
		Thread.sleep(2000);
		
		ClassSignUpPO c = new ClassSignUpPO(driver);
							
		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j= 0; j<ClassCount; j++)
		 {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
							
			if (className.contains("CLASSENROLLMENTENDED"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
		 }
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("The online enrollment window for this course has closed.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignUpButton().isEnabled());
		c.getPopupCancelButton().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();

}
	@Test(priority = 7, description = "Validating that the class cannnot be enrolled due to Membership Type Time Restrictions")
	public void ClassOutsidePermittedHours() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("outpermtdhrs", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyClassesScheduleButton().click();
		Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
		Thread.sleep(2000);
		
		ClassSignUpPO c = new ClassSignUpPO(driver);
							
		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j= 0; j<ClassCount; j++)
		 {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
							
			if (className.contains("CLASSWITHINELIGIBLETIME"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
		 }
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("Membership restrictions have limited enrollment into this class.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignUpButton().isEnabled());
		c.getPopupCancelButton().click();
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
