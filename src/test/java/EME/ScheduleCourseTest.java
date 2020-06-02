package EME;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;

public class ScheduleCourseTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	public reusableMethods rm;

	public ScheduleCourseTest() {

		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1)
	public void scheduleCourse() throws IOException, InterruptedException {
		rm.activeMember2Login();
		DashboardPO d = new DashboardPO(driver);
		d.getMyClassesScheduleButton().click();
		Thread.sleep(4000);
		ClassSignUpPO c = new ClassSignUpPO(driver);
		c.getSelectDateThisWeekButton().click();
		Thread.sleep(4000);
		Actions act = new Actions(driver);
		act.moveToElement(c.getSelectCoursesButton()).click().perform();
		Thread.sleep(2000);
		c.getFirstAvailCourseButton().click();
		Thread.sleep(2000);
		if (!c.getPopupSignUpButton().isEnabled()) {
			c.getPopupCancelButton().click();
			Thread.sleep(2000);
			rm.returnToDashboard();
		} else {
			c.getPopupSignUpButton().click();
			Thread.sleep(2000);
			Assert.assertEquals(driver.getTitle(), "Confirmation");
			rm.returnToDashboard();
		}
	}

	@Test(priority = 2)
	public void unenrollFromCourse() throws IOException, InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		/*
		 * Thread.sleep(4000); if (!d.getMyClassesClass1GearButton().isDisplayed()) {
		 * Thread.sleep(1000); System.out.println("looking for gear button"); }
		 */
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//classescourses/div[1]/div[2]/div[1]/div[1]/a[1]/div[1]/div[3]/i[1]")));
		while (!d.getMyClassesClass1GearButton().isDisplayed()) {
			Thread.sleep(1000);
			System.out.println("Sleeping for 1 second");
		}
		d.getMyClassesClass1GearButton().click();
		Thread.sleep(2000);
		d.getmyClassesUnenrollButton().click();
		Thread.sleep(4000);
		UnenrollPO u = new UnenrollPO(driver);
		u.getUnenrollButton().click();
		Thread.sleep(2000);
		u.getUnenrollConfirmYesButton().click();
		Thread.sleep(2000);
		Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
		u.getUnenrollConfirmYesButton().click();
	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		Thread.sleep(5000);
		driver.close();
		driver = null;
	}

}
