package FamilyMemberClasses;

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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollClassMemberAndItemRestrictions extends base {
	private static String classStartMonth = "DEC";
	private static String classStartDate = "22";
	public static SoftAssert softAssertion = new SoftAssert();
//	@BeforeTest
	@BeforeClass
 	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	
	@Test(priority = 1, description = "Validating that Frozen member cannot enroll When the club settings won't allow")
	public void FrozenMemberCannotEnroll() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("freezemember", "Testing1!");
		reusableWaits.waitForDashboardLoaded1();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyClassesScheduleButton().click();
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
		
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
		
		ClassSignUpPO c = new ClassSignUpPO(driver);

		reusableMethods.SelectTomorrowDate();
      
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
						
		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j= 0; j<ClassCount; j++)
		 {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
							
			if (className.contains("FAMILYENROLLCLASS"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
		 }
		

		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		softAssertion.assertEquals("Membership restrictions have limited enrollment into this class.", c.getPopUpErrorMessage().getText().trim());
		softAssertion.assertFalse(c.getPopupSignUpButton().isEnabled());
		c.getPopupCancelButton().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();

}
	@Test(priority = 2, description = "Validating that Terminated member cannot enroll When the club settings won't allow")
	public void TerminatedMemberCannotEnroll() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("terminate", "Testing1!");
		reusableWaits.waitForDashboardLoaded1();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyClassesScheduleButton().click();
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
				
		ClassSignUpPO c = new ClassSignUpPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		reusableMethods.SelectTomorrowDate();
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));				
		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j= 0; j<ClassCount; j++)
		 {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
							
			if (className.contains("FAMILYENROLLCLASS"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
		 }
		
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		softAssertion.assertEquals("Membership restrictions have limited enrollment into this class.", c.getPopUpErrorMessage().getText().trim());
		softAssertion.assertFalse(c.getPopupSignUpButton().isEnabled());
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
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
				
		ClassSignUpPO c = new ClassSignUpPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		reusableMethods.SelectTomorrowDate();
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
		
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
		softAssertion.assertEquals("The online enrollment window for this class has closed.", c.getPopUpErrorMessage().getText().trim());
		softAssertion.assertFalse(c.getPopupSignUpButton().isEnabled());
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
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
				
		ClassSignUpPO c = new ClassSignUpPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		reusableMethods.SelectTomorrowDate();
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
		
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
		softAssertion.assertEquals("Online enrollment for this class is not allowed.", c.getPopUpErrorMessage().getText().trim());
		softAssertion.assertFalse(c.getPopupSignUpButton().isEnabled());
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
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
				
		ClassSignUpPO c = new ClassSignUpPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		c.getCalendarIcon().click();
		Thread.sleep(2000);
		
		String monthName = driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]")).getText();
		
				while (!monthName.contains(classStartMonth))
				{
					driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-next-button')]")).click();;
					monthName = driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]")).getText();
				}
					
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));	
					
		int daycount = driver.findElements(By.tagName("td")).size(); // Get the daycount from the calendar
		for (int i = 0; i < daycount; i++) {
			String date = driver.findElements(By.tagName("td")).get(i).getText();
			if (date.contains(classStartDate)) {
				driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
				break;
			}
		}
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
		
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
		softAssertion.assertEquals("Online enrollment for this class is currently closed.", c.getPopUpErrorMessage().getText().trim());
		softAssertion.assertFalse(c.getPopupSignUpButton().isEnabled());
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
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
				
		ClassSignUpPO c = new ClassSignUpPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
		
		reusableMethods.SelectYesterdayDate();
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
							
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
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		softAssertion.assertEquals("The online enrollment window for this class has closed.", c.getPopUpErrorMessage().getText().trim());
		softAssertion.assertFalse(c.getPopupSignUpButton().isEnabled());
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
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
		Thread.sleep(2000);
		
		ClassSignUpPO c = new ClassSignUpPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
		
		reusableMethods.SelectTomorrowDate();
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
							
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
		softAssertion.assertEquals("Membership restrictions have limited enrollment into this class.", c.getPopUpErrorMessage().getText().trim());
		softAssertion.assertFalse(c.getPopupSignUpButton().isEnabled());
		c.getPopupCancelButton().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();

}
	
	@Test(priority = 8, description = "Validating that the class cannnot be enrolled due to Membership Type Restrictions at the club")
	public void ClubAccessDenied() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("hoh", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyClassesScheduleButton().click();
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
				
		ClassSignUpPO c = new ClassSignUpPO(driver);
		
		WebDriverWait wait = new WebDriverWait(driver, 50);
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
		
		reusableMethods.SelectTomorrowDate();
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
											
		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j= 0; j<ClassCount; j++)
		 {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
							
			if (className.contains("FAMILYENROLLCLASS"))
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
				softAssertion.assertTrue(c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText().contains("Membership restrictions have limited class enrollment at this club."));
			}
		}
		
		c.getPopupCancelButton().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();

}
	
	@Test(priority = 9, description = "Validating that the class cannnot be enrolled due to Scheduling Conflict")
	public void ClassSchedulingConflict() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("hoh", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyClassesScheduleButton().click();
		softAssertion.assertEquals("Select Classes", BT.getPageHeader().getText());
		softAssertion.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		softAssertion.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
				
		ClassSignUpPO c = new ClassSignUpPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
		
		reusableMethods.SelectTomorrowDate();
				
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));				
		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j= 0; j<ClassCount; j++)
		 {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
							
			if (className.contains("FAMILYENROLLCLASS"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
				 break;
			}
		 }
		
		Thread.sleep(500);
		c.getPopupSignUpButton().click();
		Thread.sleep(500);
		c.getContinueButton().click();
		wait.until(ExpectedConditions.elementToBeClickable(c.getPopupClose()));
	softAssertion.assertEquals("Success", c.getPopupMessage().getText());
	c.getPopupClose().click();
	Thread.sleep(1000);
	
	//Navigate to Classes
	int count = driver.findElements(By.tagName("a")).size();
	for (int i = 0; i < count; i++) {
		if (driver.findElements(By.tagName("a")).get(i).getText().equals("Classes"))

		{
			// reusableWaits.linksToBeClickable();
			driver.findElements(By.tagName("a")).get(i).click();
			break;
		}

	}

	wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
	
	reusableMethods.SelectTomorrowDate();
	
	wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
	
	  for (int j= 0; j<ClassCount; j++)
		 {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
							
			if (className.contains("BARRE COMBAT FUSION"))
			{
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on a class at the same time as the member enrolled in other class
				 break;
			}
		 }
		int memberCount = c.getDetailsPopup().findElements(By.tagName("label")).size();
		for (int i = 0; i<memberCount; i++)
		{
			if (c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText().contains("hoh"))
			{
				softAssertion.assertTrue(c.getDetailsPopup().findElements(By.tagName("label")).get(i).getText().contains(" Scheduling Conflict"));
			}
		}
		
		c.getPopupCancelButton().click();
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