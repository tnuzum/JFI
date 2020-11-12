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

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollCourse_ClubSettings extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	public reusableWaits rw;
	public reusableMethods rm;

	public EnrollCourse_ClubSettings() {
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
	}

	@Test(priority = 1, description = "View Courses / Events Unchecked For Club won't display the Courses / Events Schedule button")

	public void ViewCoursesUncheckedForClub() throws InterruptedException {

		rm.activeMemberLogin("CantCcourses", "Testing1!");
		rw.waitForDashboardLoaded1();
		Thread.sleep(2000);
		Assert.assertFalse(rm.isElementPresent(By.xpath("//button[contains(@class, 'at-widget-courseschedule')]")));

	}

	@Test(priority = 2, description = " Verify Classes Button Present And Classes Dispalyed", dependsOnMethods = {
			"ViewCoursesUncheckedForClub" })
	public void VerifyClassesButtonPresentAndClassesDispalyed() throws InterruptedException {

		Assert.assertTrue(rm.isElementPresent(By.xpath("//button[contains(@class, 'at-widget-classschedule')]")));
		DashboardPO d = new DashboardPO(driver);

		d.getMyClassesScheduleButton().click();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
		Assert.assertTrue(driver.findElements(By.className("column2")).size() > 0);

		rm.memberLogout();

	}

	@Test(priority = 3, description = "Allow  Class Enrollment Unchecked For Club won't display the Class List")

	public void AllowCourseEnrollmentUncheckedForClub() throws InterruptedException {

		rm.activeMemberLogin("CantnrollCourse", "Testing1!");
		rw.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyCoursesEventsScheduleButton().click();
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

		ClassSignUpPO c = new ClassSignUpPO(driver);

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectClassOrCourseToEnroll("CANNOTBEENROLLEDCOURSE");

		Thread.sleep(2000);
		Assert.assertFalse(c.getPopupSignupButtonCourse().isEnabled());
		c.getPopupCancelButtonCourse().click();
		Thread.sleep(2000);
		rm.memberLogout();
	}

//	@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
