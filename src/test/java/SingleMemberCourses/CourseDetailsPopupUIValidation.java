package SingleMemberCourses;

import java.io.IOException;
import java.time.Duration;

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
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class CourseDetailsPopupUIValidation extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String CourseToEnroll = "COURSENEEDSPUNCHES";
	private static String CourseNameDisplayed = "CourseNeedsPunches";
	private static String CourseStartMonth = "Jun";
	private static int CourseStartYear = 2021;
	private static DashboardPO d;
	private static ClassSignUpPO c;

	public reusableWaits rw;
	public reusableMethods rm;

	public CourseDetailsPopupUIValidation() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();

		d = new DashboardPO(driver);
		c = new ClassSignUpPO(driver);
	}

	@Test(priority = 1, description = "Ui validations")
	public void PopupUIValidations() throws IOException, InterruptedException {

		rm.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));

		d.getMyCoursesEventsScheduleButton().click();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectCourseStartYear(CourseStartYear);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectCourseStartMonth(CourseStartMonth);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectClassOrCourseToEnroll(CourseToEnroll);

		Thread.sleep(2000);

		Assert.assertEquals(c.getClasslabel().getText(), CourseNameDisplayed);
		Assert.assertTrue(c.getDetailsPopup().getText().contains("Course Instructor: Andrea"));
		Assert.assertTrue(c.getDetailsPopup().getText().contains("Class Length: 60 min"));
		Assert.assertTrue(c.getDetailsPopup().getText().contains("Date: 06/21/2021 - 06/30/2021"));
		Assert.assertTrue(c.getDetailsPopup().getText().contains("Time: 11:00 AM"));
		Assert.assertTrue(c.getDetailsPopup().getText().contains("Days: MON, WED, THU, FRI"));
		Assert.assertTrue(c.getDetailsPopup().getText().contains("- COURSE DESCRIPTION -"));

		c.getPopupCancelButtonCourse().click();
		Thread.sleep(1000);
		rm.memberLogout();

	}

//	@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
