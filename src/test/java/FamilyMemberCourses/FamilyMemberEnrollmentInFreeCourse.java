package FamilyMemberCourses;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class FamilyMemberEnrollmentInFreeCourse extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static int CourseStartYear = 2021;
	private static String CourseStartMonth = "Jun";
	private static String dsiredMonthYear = "June 2021";
	private static String courseToEnroll = "FREE COURSE AUTO";
	private static String courseNameDisplayed = "Free Course Auto";
	private static String courseTimeDisplayed = "Start Time: 4:30 PM";
	private static String courseInstructorDisplayed = "Course Instructor: Jillian S";
	private static String courseInstructorDisplayedOnSearchScreen = "Inst: Jillian S";
	private static String courseTimeDisplayedOnSearchScreen = "4:30 PM";
	private static String courseDuration = "60 Min";
	private static String member1 = "Cadmember";
	private static String member1Rate = "Not Eligible";
	private static String member2 = "Feemember";
	private static String member2Rate = "Free";
	private static String member3 = "Freemember";
	private static String member3Rate = "Free";
	private static String member4 = "FreezeMember";
	private static String member4Rate = "Not Eligible";
	private static String member5 = "Hoh";
	private static String member5Rate = "Free";
	private static String member6 = "Memberwithpunch";
	private static String member6Rate = "Free";
	private static String member7 = "Outpermtdhrs";
	private static String member7Rate = "Not Eligible";
	private static String member8 = "Terminate";
	private static String member8Rate = "Not Eligible";

	public reusableWaits rw;
	public reusableMethods rm;

	public FamilyMemberEnrollmentInFreeCourse() {
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

	@Test(priority = 1, description = "Family Member Enrollment")
	public void FamilyEnrollInFreeCourse() throws IOException, InterruptedException {
		try {
			rm.activeMemberLogin("hoh", "Testing1!");
			// rm.unenrollFromClass();
			// Thread.sleep(2000);
			// rm.returnToDashboard();
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

			c.getCourseFilter().click();
			c.getCourseKeyword().click();
			c.getSearchField().sendKeys("free");
			Thread.sleep(2000);
			c.getCourseApplyFilters().click();
			Thread.sleep(2000);

			int CourseCount = c.getClassTable().size();
			for (int j = 0; j < CourseCount; j++) {

				WebElement w = c.getClassTable().get(j);
				WebElement w1 = c.getClassTimeAndDuration().get(j);
				String courseName = w.getText();
				String courseTimeAndDuration = w1.getText();

				if (courseName.contains(courseToEnroll)) {
					Assert.assertTrue(courseName.contains(courseInstructorDisplayedOnSearchScreen));
					Assert.assertTrue(courseTimeAndDuration.contains(courseTimeDisplayedOnSearchScreen));
					Assert.assertTrue(courseTimeAndDuration.contains(courseDuration));

					List<WebElement> getMemberRate = w.findElements(By.className("ng-star-inserted"));

					int count = getMemberRate.size();

					for (int k = 0; k < count; k++) {

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

			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'modal-content')]")));
			while (c.getClasslabel().getText().isBlank()) {
				Thread.sleep(500);
			}

			Assert.assertEquals(c.getClasslabel().getText(), courseNameDisplayed); // Verifies the class name
			int count = c.getFmlyMemberLabel().size();

			// Selects the other eligible members
			for (int i = 0; i < count; i++) {

				WebElement fml = c.getFmlyMemberLabel().get(i);
				WebElement fmc = c.getFmlyMemberCheckBox().get(i);

				if (fml.getText().contains(member2))
					fml.click(); // Selects the member

				if (fml.getText().contains(member3))
					fml.click(); // Selects the member

				if (fml.getText().contains(member5))
					Assert.assertTrue(fmc.isSelected());

				if (fml.getText().contains(member6))
					fml.click(); // Selects the member

			}
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					c.getPopupSignupButtonCourse());
			Actions actions = new Actions(driver);
			actions.moveToElement(c.getPopupSignupButtonCourse()).click().perform();
			Thread.sleep(2000);

			while (c.getClassName().getText().isBlank()) {
				Thread.sleep(500);
			}

			Assert.assertEquals(courseNameDisplayed, c.getClassName().getText());
			Assert.assertEquals(courseTimeDisplayed, c.getClassStartTime().getText());
			Assert.assertEquals(courseInstructorDisplayed, c.getCourseInstructor().getText());

			for (int i = 0; i < c.getMemberSections().size(); i++) {
				String paymentOptions = c.getMemberSections().get(i).getText();
				WebElement Label = c.getMemberSections().get(i).findElement(By.tagName("label"));

				if (paymentOptions.contains(member2)) {
					Assert.assertTrue(Label.getText().contains("Free"));
					Assert.assertTrue(Label.isEnabled());
				}

				if (paymentOptions.contains(member3)) {
					Assert.assertTrue(Label.getText().contains("Free"));
					Assert.assertTrue(Label.isEnabled());
				}

				if (c.getMemberSections().get(i).getText().contains(member5)) // This member has all the payment options
				{
					Assert.assertTrue(Label.getText().contains("Free"));
					Assert.assertTrue(Label.isEnabled());
				}

				if (c.getMemberSections().get(i).getText().contains(member6)) // This member has all the payment options
				{
					Assert.assertTrue(Label.getText().contains("Free"));
					Assert.assertTrue(Label.isEnabled());
				}

			}
			c.getContinueButton().click();
			wait.until(ExpectedConditions.visibilityOf(c.getPopupClose()));
			wait.until(ExpectedConditions.elementToBeClickable(c.getPopupClose()));
			Assert.assertEquals("Success", c.getPopupMessage().getText());
			c.getPopupClose().click();
			Thread.sleep(1000);
			ThankYouPO TY = new ThankYouPO(driver);

			// Verifies the text on Thank You page and the links to navigate to Dashboard
			// and other pages are displayed
			rm.ThankYouPageValidations();

			// Note down the Receipt number
			String receiptNumber = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());

			// Verifies the buttons on Print Receipt Popup
			rm.ReceiptPopupValidations();

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
			Thread.sleep(1000);

			// Navigate to Dashboard
			int count1 = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count1; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

				{
					// rw.linksToBeClickable();
					driver.findElements(By.tagName("a")).get(i).click();
					break;
				}

			}
			rw.waitForDashboardLoaded();
			// Verifies the link navigates to the right page
			Assert.assertEquals("Dashboard", driver.getTitle());
			Thread.sleep(1000);
			DashboardPO dp = new DashboardPO(driver);
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.xpath("//div[(contains@class, 'swal2-center')]")));
			dp.getMyAccountAccountHistory().click();

			AcctHistoryPO ahp = new AcctHistoryPO(driver);

			while (!ahp.getReceiptNumberTable().isDisplayed()) {
				Thread.sleep(2000);
				System.out.println("waiting");
			}
			// Clicks on the Receiptnumber in Account History

			ahp.getSearchField().sendKeys(receiptNumber);

			Thread.sleep(2000);
			wait.until(ExpectedConditions.textToBePresentInElement(ahp.getReceiptNumber(), receiptNumber));
			ahp.getReceiptNumber().click();
			Thread.sleep(1000);
			// Verifies the Invoice amount is $0.00
			Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']"))
					.getText().contains("$0.00"));
			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
			Thread.sleep(1000);
			rm.returnToDashboard();
			rm.memberLogout();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(this.getClass().getSimpleName(), driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}
	}

	@Test(dataProvider = "getData", dependsOnMethods = { "FamilyEnrollInFreeCourse" })
	public void FamilyMemberUnenroll(String username, String password) throws InterruptedException, IOException {
		rm.activeMemberLogin(username, password);
		rw.waitForDashboardLoaded();
		rm.unenrollFromCourse(dsiredMonthYear);
		rm.memberLogout();
	}

	@DataProvider
	public Object[][] getData()

	{
		Object[][] data = new Object[4][2];
		data[0][0] = "freemember";
		data[0][1] = "Testing1!";
		data[1][0] = "feemember";
		data[1][1] = "Testing1!";
		data[2][0] = "memberwithpunch";
		data[2][1] = "Testing1!";
		data[3][0] = "hoh";
		data[3][1] = "Testing1!";
		return data;
	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
