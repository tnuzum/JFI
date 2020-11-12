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
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollInCourse_CancelTransaction extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String courseToEnroll = "FEECOURSE";
	private static String courseNameDisplayed = "FeeCourse";
	private static String courseTimeDisplayed = "Start Time: 11:00 AM";
	private static String courseInstructorDisplayed = "Course Instructor: Andrea";
	private static String CourseStartMonth = "Jun";
	private static int CourseStartYear = 2021;

	public reusableWaits rw;
	public reusableMethods rm;

	public EnrollInCourse_CancelTransaction() {
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

	@Test(priority = 1, description = "Cancelling from the Rate Select Rates page")
	public void CancelFromSelectRatesPage() throws IOException, InterruptedException {

		rm.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));
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

		rm.SelectCourseStartYear(CourseStartYear);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectCourseStartMonth(CourseStartMonth);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectClassOrCourseToEnroll(courseToEnroll);

		Thread.sleep(2000);
		c.getPopupSignupButtonCourse().click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), courseNameDisplayed));
		Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
		Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
		Assert.assertEquals(courseNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(courseTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(courseInstructorDisplayed, c.getCourseInstructor().getText());

		c.getCancelLink().click();

		Assert.assertEquals(c.getPageHeader().getText(), "Select Courses / Events");
	}

	@Test(priority = 2, description = "Cancelling from the Confirmation page")
	public void CancelFromConfirmationPage() throws IOException, InterruptedException {

		ClassSignUpPO c = new ClassSignUpPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectCourseStartYear(CourseStartYear);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectCourseStartMonth(CourseStartMonth);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		rm.SelectClassOrCourseToEnroll(courseToEnroll);

		Thread.sleep(2000);
		c.getPopupSignupButtonCourse().click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), courseNameDisplayed));
		Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
		Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
		Assert.assertEquals(courseNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(courseTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(courseInstructorDisplayed, c.getCourseInstructor().getText());

		int radioButtonCount = driver.findElements(By.tagName("label")).size();
		for (int i = 0; i < radioButtonCount; i++) {
			if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Course Fee")) {
				driver.findElements(By.tagName("label")).get(i).click();
				break;
			}
		}

		c.getContinueButton().click();
		Thread.sleep(2000);
		rm.ReviewSectionValidation("Fee(s)");

		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
					.contains(" On Account"))
				Assert.assertTrue(PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).isSelected());
		}

		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);

		// Noting down the total amount
		Thread.sleep(2000);
//		System.out.println(PP.getTotalAmount().getText());
		String totalAmt = PP.getClassesReviewtotalAmount().getText();

//		System.out.println(TotalAmt);

		// Verifies the Pay button contains the total amount
		Assert.assertTrue(PM.getPaymentButton().getText().contains(totalAmt));

		// Click the Pay button

		PM.getCancelButton().click();

		Assert.assertEquals(c.getPageHeader().getText(), "Select Courses / Events");

		rm.memberLogout();

	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
