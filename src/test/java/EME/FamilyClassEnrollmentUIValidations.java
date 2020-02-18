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
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class FamilyClassEnrollmentUIValidations extends base{
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String classToEnroll = "FAMILYENROLLCLASS";
	private static String classNameDisplayed = "FamilyEnrollClass";
	private static String classTimeDisplayed = "Start Time: 5:00 PM";
	private static String classInstructorDisplayed = "Class Instructor: Max Gibbs";
	private static String classInstructorDisplayedOnSearchScreen = "Inst: Max Gibbs";
	private static String classTimeDisplayedOnSearchScreen = "5:00 PM";
	private static String classDuration = "30 min";
	private static String buyPackageName = "Buy Day Pass";
	private static String defaultSelection = null;
	private static String unitsToBeSelected = "2 - $1.00/per";
	private static String classCostInUnits = "Class Cost: 2 unit(s)";
	private static String member1 = "Cadmember";
	private static String member1Eligibilty = "Not Eligible";
	private static String member2 = "Feemember";
	private static String member2Eligibilty = "$9.00";
	private static String member3 = "Freemember";
	private static String member3Eligibilty = "Free";
	private static String member4 = "Freeze";
	private static String member4Eligibilty = "Not Eligible";
	private static String member5 = "Hoh";
	private static String member5Eligibilty = "$9.00 or use package";
	private static String member6 = "Memberwithpunch";
	private static String member6Eligibilty = "$9.00 or use package";
	private static String member7 = "Outpermtdhrs";
	private static String member7Eligibilty = "Not Eligible";
	private static String member8 = "Terminate";
	private static String member8Eligibilty = "Not Eligible";
	

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}
	
	@Test(priority = 1, description = "Class Search Screen Ui validations")
	public void SearchScreenUIValidations() throws IOException, InterruptedException {
	reusableMethods.activeMemberLogin("hoh", "Testing1!");
	//reusableMethods.unenrollFromClass();
	//Thread.sleep(2000);
	//reusableMethods.returnToDashboard();
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
	c.getCourseFilter().click();
	c.getCourseKeyword().click();
	c.getSearchField().sendKeys("family");
	c.getClassApplyFilters().click();

	int ClassCount = c.getClassTable().size();
	for (int j = 0; j < ClassCount; j++) {
		
		WebElement w = c.getClassTable().get(j);
		WebElement w1 = c.getClassTimeAndDuration().get(j);
		String className = w.getText();
		String classTimeAndDuration = w1.getText();
        
		if (className.contains(classToEnroll)) 
		
		{
			Assert.assertTrue(className.contains(classInstructorDisplayedOnSearchScreen));
			Assert.assertTrue(classTimeAndDuration.contains(classTimeDisplayedOnSearchScreen));
			Assert.assertTrue(classTimeAndDuration.contains(classDuration));
			
			List<WebElement> getMemberEligibility = w.findElements(By.className("ng-star-inserted"));
		
			int count = getMemberEligibility.size();
			
			for (int k = 0; k<count; k++)
			{
						
				if (getMemberEligibility.get(k).getText().contains(member1))
					Assert.assertTrue(getMemberEligibility.get(k).getText().contains(member1Eligibilty));
				if (getMemberEligibility.get(k).getText().contains(member2))
					Assert.assertTrue(getMemberEligibility.get(k).getText().contains(member2Eligibilty));
				if (getMemberEligibility.get(k).getText().contains(member3))
					Assert.assertTrue(getMemberEligibility.get(k).getText().contains(member3Eligibilty));
				if (getMemberEligibility.get(k).getText().contains(member4))
					Assert.assertTrue(getMemberEligibility.get(k).getText().contains(member4Eligibilty));
				if (getMemberEligibility.get(k).getText().contains(member5))
					Assert.assertTrue(getMemberEligibility.get(k).getText().contains(member5Eligibilty));
				if (getMemberEligibility.get(k).getText().contains(member6))
					Assert.assertTrue(getMemberEligibility.get(k).getText().contains(member6Eligibilty));
				if (getMemberEligibility.get(k).getText().contains(member7))
					Assert.assertTrue(getMemberEligibility.get(k).getText().contains(member7Eligibilty));
				if (getMemberEligibility.get(k).getText().contains(member8))
						Assert.assertTrue(getMemberEligibility.get(k).getText().contains(member8Eligibilty));
					
			}
				
			w.click(); // Click on the specific class
			break;
		}
	}

}
	@Test(priority = 2, description = "Class Details Pop Up Screen Ui validations")
	public void PopUpScreenUIValidations() throws IOException, InterruptedException {
		
		
		
	}
}
	
