package SingleMemberCourses;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import resources.Base;
import resources.reusableMethods;

public class CourseDetailsPopupUIValidation extends Base {
	private static Logger log = LogManager.getLogger(Base.class.getName());
	private static String CourseToEnroll = "COURSENEEDSPUNCHES";
	private static String CourseNameDisplayed = "CourseNeedsPunches";
	private static String CourseStartMonth = "Dec";
	private static DashboardPO d;
	private static ClassSignUpPO c;

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));

		d = new DashboardPO(driver);
		c = new ClassSignUpPO(driver);
	}

	@Test(priority = 1, description = "Ui validations")
	public void PopupUIValidations() throws IOException, InterruptedException {

		reusableMethods.activeMemberLogin(prop.getProperty("activeMember6_username"),
				prop.getProperty("activeMember6_password"));

		d.getMyCoursesEventsScheduleButton().click();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		reusableMethods.SelectCourseStartMonth(CourseStartMonth);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		reusableMethods.SelectClassOrCourseToEnroll(CourseToEnroll);

		Thread.sleep(2000);

		Assert.assertEquals(c.getClasslabel().getText(), CourseNameDisplayed);
		Assert.assertTrue(c.getDetailsPopup().getText().contains("Course Instructor: Andrea"));
		Assert.assertTrue(c.getDetailsPopup().getText().contains("Class Length: 60 min"));
		Assert.assertTrue(c.getDetailsPopup().getText().contains("Date: 12/21/2020 - 12/31/2020"));
		Assert.assertTrue(c.getDetailsPopup().getText().contains("Time: 11:00 AM"));
		Assert.assertTrue(c.getDetailsPopup().getText().contains("Days: MON, WED, THU, FRI"));
		Assert.assertTrue(c.getDetailsPopup().getText().contains("- COURSE DESCRIPTION -"));

		c.getPopupCancelButtonCourse().click();
		Thread.sleep(1000);
		reusableMethods.memberLogout();

	}

//	@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
