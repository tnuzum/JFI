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

import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;

public class ClassDetailsPopupUIValidation extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String classToEnroll = "CLASSNEEDSPUNCHES";
	private static String classNameDisplayed = "ClassNeedsPunches";
	private static DashboardPO d;
	private static ClassSignUpPO c;

	public reusableMethods rm;

	public ClassDetailsPopupUIValidation() {

		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();

		d = new DashboardPO(driver);
		c = new ClassSignUpPO(driver);
	}

	@Test(priority = 1, description = "Ui validations")
	public void PopupUIValidations() throws IOException, InterruptedException {

		rm.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));

		d.getMyClassesScheduleButton().click();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectTomorrowDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectClassOrCourseToEnroll(classToEnroll);

		Thread.sleep(2000);

		Assert.assertEquals(c.getClasslabel().getText(), classNameDisplayed);
		Assert.assertTrue(c.getDetailsPopup().getText().contains("Class Instructor: Max Gibbs"));
		Assert.assertTrue(c.getDetailsPopup().getText().contains("Class Length: 45 min"));
		Assert.assertTrue(c.getDetailsPopup().getText().contains(tomorrowsDate));
		Assert.assertTrue(c.getDetailsPopup().getText().contains("Time: 10:00 AM"));
		Assert.assertTrue(c.getDetailsPopup().getText().contains("- CLASS DESCRIPTION -"));

		c.getPopupCancelButton().click();
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
