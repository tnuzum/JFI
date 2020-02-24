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
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class FamilyCourseEnrollmentUIValidations extends base{
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String CourseStartMonth = "Dec";
	private static String courseToEnroll = "FAMILYENROLLCOURSE";
	private static String courseNameDisplayed = "FamilyEnrollCourse";
	private static String courseTimeDisplayed = "Start Time: 5:00 PM";
	private static String courseInstructorDisplayed = "Course Instructor: Max Gibbs";
	private static String courseInstructorDisplayedOnSearchScreen = "Inst: Max Gibbs";
	private static String courseTimeDisplayedOnSearchScreen = "5:00 PM";
	private static String courseDuration = "30 min";
	private static String buyPackageName = "Buy Day Pass";
	private static String defaultSelection = null;
	private static String unitsToBeSelected = "2 - $1.00/per";
	private static String courseCostInUnits = "Course Cost: 2 unit(s)";
	private static String member1 = "Cadmember";
	private static String member1Rate = "Not Eligible";
	private static String member2 = "Feemember";
	private static String member2Rate = "$9.00 or use package";
	private static String member3 = "Freemember";
	private static String member3Rate = "Free";
	private static String member4 = "Freeze";
	private static String member4Rate = "Not Eligible";
	private static String member5 = "Hoh";
	private static String member5Rate = "$9.00 or use package";
	private static String member6 = "Memberwithpunch";
	private static String member6Rate = "$9.00 or use package";
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
	
	@Test(priority = 1, description = "Course Search Screen Ui validations")
	public void SearchScreenUIValidations() throws IOException, InterruptedException {
	reusableMethods.activeMemberLogin("hoh", "Testing1!");
	//reusableMethods.unenrollFromCourse();
	//Thread.sleep(2000);
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
	WebDriverWait wait = new WebDriverWait(driver, 50);
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
	c.getSearchField().sendKeys("family");
	c.getCourseApplyFilters().click();
	wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

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

}
	@Test(priority = 2, description = "Course Details Pop Up Screen Ui validations")
	public void PopUpScreenUIValidations() throws IOException, InterruptedException {
		
		ClassSignUpPO c = new ClassSignUpPO(driver);
		Thread.sleep(2000);
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'modal-content')]")));
		while (c.getClasslabel().getText().isBlank())
		{
			Thread.sleep(500);
		}
		
		Assert.assertEquals(c.getClasslabel().getText(), courseNameDisplayed); // Verifies the course name
		int count = c.getFmlyMemberLabel().size();
		for (int i =0; i<count; i++)
		{
			
			WebElement fml =  c.getFmlyMemberLabel().get(i);
			WebElement fmc =  c.getFmlyMemberCheckBox().get(i);
			//System.out.println(fml.getText());
			//System.out.print(fmc.getAttribute("ng-reflect-is-disabled"));
			if (fml.getText().contains(member1))
			Assert.assertEquals(fmc.getAttribute("ng-reflect-is-disabled"), "true"); //verifies that the check box is disabled for this member
			
			if (fml.getText().contains(member2)) 
				Assert.assertEquals(fmc.getAttribute("ng-reflect-is-disabled"), "false"); //verifies that the check box is enabled for this member
			
			if (fml.getText().contains(member3)) 
				Assert.assertEquals(fmc.getAttribute("ng-reflect-is-disabled"), "false");
			
			if (fml.getText().contains(member4))
			Assert.assertEquals(fmc.getAttribute("ng-reflect-is-disabled"), "true");
			
			
			if (fml.getText().contains(member5)) {
			Assert.assertEquals(fmc.getAttribute("ng-reflect-is-disabled"), "false");
			Assert.assertEquals(fmc.getAttribute("ng-reflect-model"), "true");
			Assert.assertTrue(fmc.isSelected());                                     // Verifies that the check box is selected by default for the logged in HOH
			fml.click();                                                             // Unchecks the check box
			Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());                // Verifies that the Signup button now is disabled
			fml.click();                                                             // checks the box again
			Assert.assertTrue(c.getPopupSignupButtonCourse().isEnabled());                 // Verifies that the Signup button is enabled now
						
			}
			
			if (fml.getText().contains(member6)) 
				Assert.assertEquals(fmc.getAttribute("ng-reflect-is-disabled"), "false");

			if (fml.getText().contains(member7))
			Assert.assertEquals(fmc.getAttribute("ng-reflect-is-disabled"), "true");
			
			if (fml.getText().contains(member8))
			Assert.assertEquals(fmc.getAttribute("ng-reflect-is-disabled"), "true");
		}
			
		// Selects the other eligible members
			for ( int i =0; i<count; i++)
			{
				
				WebElement fml =  c.getFmlyMemberLabel().get(i);
				
									
			if (fml.getText().contains(member2)) 
				fml.click();   // Selects the member
				
			
			if (fml.getText().contains(member3)) 
			    fml.click();   // Selects the member
				
			
			if (fml.getText().contains(member6)) 
			    fml.click();   // Selects the member
			    
		}
			c.getPopupSignupButtonCourse().click();
				
}
	
	@Test(priority = 3, description = "Rates Screen Ui validations")
	public void RatesScreenUIValidations() throws IOException, InterruptedException {
		
		ClassSignUpPO c = new ClassSignUpPO(driver);
		
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
			List<WebElement> Labels = c.getMemberSections().get(i).findElements(By.tagName("label"));
														
			if (c.getMemberSections().get(i).getText().contains(member2))
					{
								
				for (int j= 0; j<Labels.size(); j++)
				{
					if (Labels.get(j).getText().contains("Pay Course Fee"))
						Labels.get(j).click();
				}
					}
			
			if (c.getMemberSections().get(i).getText().contains(member3))
			{
				
				Assert.assertTrue(paymentOptions.contains("Free"));  // Course is free for this member
				for (int j= 0; j<Labels.size(); j++)
				{
					if (Labels.get(j).getText().contains("Free"))
						Assert.assertTrue(Labels.get(j).isEnabled());
				}
			}
			
			if (c.getMemberSections().get(i).getText().contains(member5)) //This member has all the payment options
			{
				Assert.assertTrue(paymentOptions.contains("Use Existing Package"));
				Assert.assertTrue(paymentOptions.contains("Pay Course Fee"));  
				Assert.assertTrue(paymentOptions.contains(buyPackageName));
				for (int j= 0; j<Labels.size(); j++)
				{
					if (Labels.get(j).getText().contains(buyPackageName))
						Labels.get(j).click();
				}
				Assert.assertTrue(c.getClassCostinPunches().getText().contains(courseCostInUnits));
				WebElement W = driver.findElement(By.xpath("//div[@class='ibox-content']"));
				Select s = new Select(W.findElement(By.xpath("//select[contains(@class, 'form-control')]")));
				 defaultSelection = s.getFirstSelectedOption().getText().trim();
				    Assert.assertEquals(defaultSelection, unitsToBeSelected);
			}
			
			if (c.getMemberSections().get(i).getText().contains(member6)) //This member has all the payment options
			{
				Assert.assertTrue(paymentOptions.contains("Use Existing Package"));
				Assert.assertTrue(paymentOptions.contains("Pay Course Fee"));  
				Assert.assertTrue(paymentOptions.contains(buyPackageName));
				for (int j= 0; j<Labels.size(); j++)
				{
					if (Labels.get(j).getText().contains("Use Existing Package"))
						Assert.assertTrue(Labels.get(j).isEnabled());
				}
			}
			
						
			
	}
		c.getContinueButton().click();
				
	}
	@Test(priority = 4, description = "Review Screen Ui validations")
	public void ReviewScreenUIValidations() throws IOException, InterruptedException {
	

	PurchaseConfirmationPO pp = new PurchaseConfirmationPO(driver);
	ClassSignUpPO c = new ClassSignUpPO(driver);
	PaymentMethodsPO PM = new PaymentMethodsPO(driver);
	
	while (c.getClassName().getText().isBlank())
	{
		Thread.sleep(500);
	}
	
	System.out.println(pp.getMemberfeesSection().size());
	for (int i=0; i<pp.getMemberfeesSection().size(); i++)
	{
		String text = pp.getMemberfeesSection().get(i).getText();
		
		if (text.contains(member2))
			Assert.assertTrue(text.contains("Single Course Fee $9.00"));
		
		if (text.contains(member3))
			Assert.assertTrue(text.contains("Single Course Fee Free"));
		
		if (text.contains(member5)) {
			Assert.assertTrue(text.contains("Day Pass")); 
			Assert.assertTrue(text.contains("Package Unit(s): 2 X $1.00"));}
					
		if (text.contains(member6)) {
			Assert.assertTrue(text.contains("Day Pass")); 
			Assert.assertTrue(text.contains("Unit(s) deducted for this course: 2")); }
					
	}
	
	while (pp.getClassesReviewtotalAmount().getText().isBlank())
	{
		Thread.sleep(500);
	}
	
	String totalAmount = pp.getClassesReviewtotalAmount().getText();
	     
	Assert.assertTrue(PM.getPaymentButton().getText().contains(totalAmount));   //Verifies the Pay button contains the total amount
	
	}
//	@AfterTest
   @AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
	
	
 