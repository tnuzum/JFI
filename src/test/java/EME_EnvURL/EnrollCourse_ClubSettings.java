package EME_EnvURL;

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
import org.testng.annotations.Parameters;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollCourse_ClubSettings extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

//	@BeforeTest
	@BeforeClass
	@Parameters({"EMELoginPage"})
	public void initialize(String EMELoginPage) throws InterruptedException, IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(EMELoginPage);
	}

	
@Test(priority = 1, description = "View Courses / Events Unchecked For Club won't display the Courses / Events Schedule button")
	
   	public void ViewCoursesUncheckedForClub() throws InterruptedException {
       	
       	reusableMethods.activeMemberLogin("CantCcourses", "Testing1!");
       	reusableWaits.waitForDashboardLoaded();
   		Thread.sleep(2000);
   		Assert.assertFalse(reusableMethods.isElementPresent(By.xpath("//button[contains(@class, 'at-widget-courseschedule')]")));
   		reusableMethods.memberLogout();

   	}

@Test(priority = 2, description = "Allow  Class Enrollment Unchecked For Club won't display the Class List")

	public void AllowCourseEnrollmentUncheckedForClub() throws InterruptedException {
   	
   	reusableMethods.activeMemberLogin("CantnrollCourse", "Testing1!");
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
	for (int j = 0; j < CourseCount; j++) {
		String courseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();

		if (courseName.contains("CANNOTBEENROLLEDCOURSE")) {
			driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																								// specific class
			break;
		}
	}

	Thread.sleep(2000);
	Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());
	c.getPopupCancelButtonCourse().click();
	Thread.sleep(2000);
	reusableMethods.memberLogout();
	}
   	
//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
