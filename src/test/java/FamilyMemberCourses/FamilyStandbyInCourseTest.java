package FamilyMemberCourses;

import java.io.IOException;
import java.lang.reflect.Method;
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class FamilyStandbyInCourseTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String dsiredMonthYear = "December 2020";
	private static String CourseStartMonth = "Dec";
	private static String courseToEnroll = "DEMO STANDBY COURSE";
	private static String courseNameDisplayed = "Demo Standby Course";
	private static String courseTimeDisplayed = "Start Time: 4:00 PM";
	private static String courseInstructorDisplayed = "Course Instructor: Max Gibbs";
	private static String courseInstructorDisplayedOnSearchScreen = "Inst: Max Gibbs";
	private static String courseTimeDisplayedOnSearchScreen = "4:00 PM";
	private static String courseDuration = "30 min";
	private static String member1 = "Cadmember";
	private static String member1Rate = "Not Eligible";
	private static String member2 = "Feemember";
	private static String member2Rate = "$9.00 or use package";
	private static String member3 = "Freemember";
	private static String member3Rate = "Free";
	private static String member4 = "FreezeMember";
	private static String member4Rate = "Not Eligible";
	private static String member5 = "Hoh";
	private static String member5Rate = "$9.00 or use package";
	private static String member6 = "Memberwithpunch";
	private static String member6Rate = "$9.00 or use package";
	private static String member7 = "Outpermtdhrs";
	private static String member7Rate = "Not Eligible";
	private static String member8 = "Terminate";
	private static String member8Rate = "Not Eligible";
	private static String testName = null;

	public reusableWaits rw;
	public reusableMethods rm;

	public FamilyStandbyInCourseTest() {
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
		getEMEURL();
	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Family Member Enrollment")
	public void FamilyMemberEnrollment() throws IOException, InterruptedException {
		try {
			rm.activeMemberLogin("hoh", "Testing1!");
			// rm.unenrollFromCourse();
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
			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartMonth(CourseStartMonth);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			c.getCourseFilter().click();
			c.getCourseKeyword().click();
			c.getSearchField().sendKeys("demo");
			c.getCourseApplyFilters().click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			int CourseCount = c.getClassTable().size();
			for (int j = 0; j < CourseCount; j++) {

				WebElement w = c.getClassTable().get(j);
				WebElement w1 = c.getClassTimeAndDuration().get(j);
				String courseName = w.getText();
				String courseTimeAndDuration = w1.getText();

				if (courseName.contains(courseToEnroll))

				{
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

			Assert.assertEquals(c.getClasslabel().getText(), courseNameDisplayed); // Verifies the course name
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

			Assert.assertTrue(c.getstandbyMessage().getText().contains("2 spot(s) left"));
			Assert.assertTrue(c.getstandbyMessage().getText().contains("Who should get it?"));

			for (int i = 0; i < c.getstandbySection().findElements(By.tagName("label")).size(); i++) {

				List<WebElement> Labels = c.getstandbySection().findElements(By.tagName("label"));
				List<WebElement> Chkbxs = c.getstandbySection().findElements(By.tagName("input"));

				if (Labels.get(i).getText().contains(member2))

					Chkbxs.get(i).click();

				if (Labels.get(i).getText().contains(member3))

					Chkbxs.get(i).click();

			}

			c.getRestOnStandby().click();

			for (int i = 0; i < c.getMemberSections().size(); i++) {
				String paymentOptions = c.getMemberSections().get(i).getText();
				List<WebElement> Labels = c.getMemberSections().get(i).findElements(By.tagName("label"));

				if (c.getMemberSections().get(i).getText().contains(member2)) {

					for (int j = 0; j < Labels.size(); j++) {
						if (Labels.get(j).getText().contains("Pay Course Fee")) {
							Labels.get(j).click();
							break;
						}
					}
				}

				if (c.getMemberSections().get(i).getText().contains(member3)) {

					Assert.assertTrue(paymentOptions.contains("Free")); // Course is free for this member
					for (int j = 0; j < Labels.size(); j++) {
						if (Labels.get(j).getText().contains("Free"))
							Assert.assertTrue(Labels.get(j).isEnabled());
					}
				}

			}

			c.getContinueButton().click();

			PurchaseConfirmationPO pp = new PurchaseConfirmationPO(driver);

			PaymentMethodsPO PM = new PaymentMethodsPO(driver);
			PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);

			while (c.getClassName().getText().isBlank()) {
				Thread.sleep(500);
			}

			System.out.println(pp.getMemberfeesSection().size());
			for (int i = 0; i < pp.getMemberfeesSection().size(); i++) {
				String text = pp.getMemberfeesSection().get(i).getText();

				if (text.contains(member2))
					Assert.assertTrue(text.contains("Single Course Fee $9.00"));

				if (text.contains(member3))
					Assert.assertTrue(text.contains("Single Course Fee Free"));

				if (text.contains(member5))
					Assert.assertTrue(text.contains("Standby"));
				if (text.contains(member6))
					Assert.assertTrue(text.contains("Standby"));

			}

			int count1 = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
			for (int i = 0; i < count1; i++) {
				if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
						.contains("1111")) {

					PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).click();
					break;
				}
			}

			while (pp.getClassesReviewtotalAmount().getText().isBlank()) {
				Thread.sleep(500);
			}

			String totalAmount = pp.getClassesReviewtotalAmount().getText();

			Assert.assertTrue(PM.getPaymentButton().getText().contains(totalAmount)); // Verifies the Pay button
																						// contains
																						// the total amount

			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}
			PM.getPaymentButton().click();

			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);
			ThankYouPO TY = new ThankYouPO(driver);

			// Verifies the text on Thank You page and the links to navigate to Dashboard
			// and other pages are displayed
			rm.ThankYouPageValidations();

			// Note down the Receipt number
			String receiptNumber2 = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());

			// Verifies the buttons on Print Receipt Popup
			rm.ReceiptPopupValidations();
			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
			Thread.sleep(3000);

			// Navigate to Select Classes
			int count2 = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count2; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Classes"))

				{
					// rw.linksToBeClickable();
					driver.findElements(By.tagName("a")).get(i).click();
					break;
				}

			}
			Thread.sleep(2000);
			// Verifies the link navigates to the right page
			Assert.assertEquals("Select Classes", driver.getTitle());
			Thread.sleep(2000);

			rm.returnToDashboard();

			rm.memberLogout();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);ae. printStackTrace();
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}

	}

	@Test(priority = 2, dataProvider = "getData", dependsOnMethods = { "FamilyMemberEnrollment" })
	public void FamilyMemberUnenroll(String username, String password) throws InterruptedException, IOException {
		try {
			rm.activeMemberLogin(username, password);

			rm.unenrollFromCourse(dsiredMonthYear);
			rm.memberLogout();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);ae. printStackTrace();
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}
	}

	@Test(priority = 3, dependsOnMethods = { "FamilyMemberEnrollment" })
	public void FamilyMemberDeleteStandby() throws InterruptedException, IOException {
		try {
			rm.deleteStandbyCourseInCOG(courseNameDisplayed, "Jonas Sports-Plex", member5, member6);
		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);ae. printStackTrace();
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}
	}

	@DataProvider
	public Object[][] getData()

	{
		Object[][] data = new Object[2][2];
		data[0][0] = "freemember";
		data[0][1] = "Testing1!";
		data[1][0] = "feemember";
		data[1][1] = "Testing1!";

		return data;
	} // @AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}