package SingleMemberClasses;

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

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollClass_ClubSettings extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1, description = "View Classes Unchecked For Club won't display the Class Schedule button")

	public void ViewClassesUncheckedForClub() throws InterruptedException {

		reusableMethods.activeMemberLogin("CantCclasses", "Testing1!");
		reusableWaits.waitForDashboardLoaded1();
		Thread.sleep(2000);
		Assert.assertFalse(
				reusableMethods.isElementPresent(By.xpath("//button[contains(@class, 'at-widget-classschedule')]")));
		reusableMethods.memberLogout();

	}

	@Test(priority = 2, description = "Allow  Class Enrollment Unchecked For Club won't display the Class List")

	public void AllowClassEnrollmentUncheckedForClub() throws InterruptedException {

		reusableMethods.activeMemberLogin("CantnrollClass", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		d.getMyClassesScheduleButton().click();
		Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

		ClassSignUpPO c = new ClassSignUpPO(driver);

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		reusableMethods.SelectTomorrowDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		reusableMethods.SelectClassOrCourseToEnroll("CANNOTBEENROLLEDCLASS");

		Thread.sleep(2000);
		Assert.assertFalse(c.getPopupSignUpButton().isEnabled());
		c.getPopupCancelButton().click();
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
