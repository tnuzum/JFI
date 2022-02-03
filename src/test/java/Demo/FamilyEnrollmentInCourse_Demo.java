package Demo;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class FamilyEnrollmentInCourse_Demo extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
//	private static String dsiredMonthYear = "May 2020";
	private static String courseToEnroll = "DEMO COURSE";
	private static String courseNameDisplayed = "Demo Course";
	private static String courseTimeDisplayed = "Start Time: 5:00 PM";
	private static String courseInstructorDisplayed = "Course Instructor: Max Gibbs";
	private static String courseInstructorDisplayedOnSearchScreen = "Inst: Max Gibbs";
	private static String courseTimeDisplayedOnSearchScreen = "5:00 PM";
	private static String courseDuration = "30 Min";
	private static String buyPackageName = "Buy Day Pass";
	private static String defaultSelection = null;
	private static String unitsToBeSelected = "2 - $10.00/per";
	private static String courseCostInUnits = "Course Cost: 2 unit(s)";
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

	public reusableWaits rw;
	public reusableMethods rm;

	public FamilyEnrollmentInCourse_Demo() {
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

	@Test(priority = 1, description = "Family Member Enrollment")
	public void FamilyMemberEnrollment() throws IOException, InterruptedException {
		rm.activeMemberLogin("hoh", "Testing1!");
		// rm.unenrollFromCourse();
		// Thread.sleep(2000);
		// rm.returnToDashboard();
		rw.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", d.getMyCoursesEventsScheduleButton());

		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

		ClassSignUpPO c = new ClassSignUpPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 50);
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

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'modal-content')]")));
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
		c.getPopupSignupButtonCourse().click();

		while (c.getClassName().getText().isBlank()) {
			Thread.sleep(500);
		}

		Assert.assertEquals(courseNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(courseTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(courseInstructorDisplayed, c.getCourseInstructor().getText());

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

			if (c.getMemberSections().get(i).getText().contains(member5)) // This member has all the payment options
			{
				Assert.assertTrue(paymentOptions.contains("Use Existing Package"));
				Assert.assertTrue(paymentOptions.contains("Pay Course Fee"));
				Assert.assertTrue(paymentOptions.contains(buyPackageName));
				for (int j = 0; j < Labels.size(); j++) {
					if (Labels.get(j).getText().contains(buyPackageName)) {
						Labels.get(j).click();
						break;
					}
				}
				Assert.assertTrue(c.getClassCostinPunches().getText().contains(courseCostInUnits));
				WebElement W = driver.findElement(By.xpath("//div[@class='ibox-content']"));
				Select s = new Select(W.findElement(By.xpath("//select[contains(@class, 'form-control')]")));
				defaultSelection = s.getFirstSelectedOption().getText().trim();
				Assert.assertEquals(defaultSelection, unitsToBeSelected);
			}

			if (c.getMemberSections().get(i).getText().contains(member6)) // This member has all the payment options
			{
				Assert.assertTrue(paymentOptions.contains("Use Existing Package"));
				Assert.assertTrue(paymentOptions.contains("Pay Course Fee"));
				Assert.assertTrue(paymentOptions.contains(buyPackageName));
				for (int j = 0; j < Labels.size(); j++) {
					if (Labels.get(j).getText().contains("Use Existing Package"))
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

			if (text.contains(member5)) {
				Assert.assertTrue(text.contains("Day Pass"));
				Assert.assertTrue(text.contains("Package Unit(s): 2 X $1.00"));
			}

			if (text.contains(member6)) {
				Assert.assertTrue(text.contains("Day Pass"));
				Assert.assertTrue(text.contains("Unit(s) deducted for this course: 2"));
			}

		}

		wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));
		jse.executeScript("arguments[0].scrollIntoView(true);", PM.getOnAccountAndSavedCards());

		int count1 = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count1; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText().contains("1111")) {

				PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).click();
				break;
			}
		}

		while (pp.getClassesReviewtotalAmount().getText().isBlank()) {
			Thread.sleep(500);
		}

		String totalAmount = pp.getClassesReviewtotalAmount().getText();

		Assert.assertTrue(PM.getPaymentButton().getText().contains(totalAmount)); // Verifies the Pay button contains
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
		String receiptNumber3 = null;

		Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
		TY.getPrintReceiptButton().click();
		Thread.sleep(2000);
		Assert.assertTrue(TY.getReceiptPopup().isDisplayed());

		// Verifies the buttons on Print Receipt Popup
		rm.ReceiptPopupValidations();
		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
		Thread.sleep(3000);

		// Navigate to Select Classes
		int count2 = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < count2; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Classes"))

			{
				// rw.linksToBeClickable();
				jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
				break;
			}

		}
		Thread.sleep(2000);
		// Verifies the link navigates to the right page
		Assert.assertEquals("Select Classes", driver.getTitle());
		Thread.sleep(2000);

		DashboardPO dp = new DashboardPO(driver);
		dp.getMenuMyAccount().click();
		Thread.sleep(2000);
		dp.getMenuAccountHistory().click();

		// Clicks on the Receiptnumber in Account History
		AcctHistoryPO ahp = new AcctHistoryPO(driver);
		ahp.getSearchField().sendKeys(receiptNumber2);
		Thread.sleep(2000);
		wait.until(ExpectedConditions.textToBePresentInElement(ahp.getReceiptNumber(), receiptNumber2));
		jse.executeScript("arguments[0].click();", ahp.getReceiptNumbers().get(1));
		Thread.sleep(3000);
		jse.executeScript("arguments[0].scrollIntoView(true);",
				TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-12 text-right']")));
		// Verifies the amount in the receipt is the same as it was displayed on the
		// Purchase Packages page
//	System.out.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-12 text-right']")).getText());
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-12 text-right']")).getText()
				.contains(totalAmount));
		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
		Thread.sleep(2000);
		rm.returnToDashboard();

		// rm.memberLogout();

	}

	/*
	 * @Test(dataProvider = "getData") public void FamilyMemberUnenroll(String
	 * username, String password) throws InterruptedException, IOException {
	 * rm.activeMemberLogin(username, password);
	 * rm.unenrollFromCourse(dsiredMonthYear); rm.memberLogout(); }
	 * 
	 * @DataProvider public Object [][] getData()
	 * 
	 * { Object[][] data = new Object[4][2]; data[0][0] = "freemember"; data[0][1] =
	 * "Testing1!"; data[1][0] = "feemember"; data[1][1] = "Testing1!"; data[2][0] =
	 * "memberwithpunch"; data[2][1] = "Testing1!"; data[3][0] = "hoh"; data[3][1] =
	 * "Testing1!"; return data; } // @AfterTest
	 * 
	 * @AfterClass public void teardown() throws InterruptedException {
	 * driver.quit(); driver = null; }
	 */

}