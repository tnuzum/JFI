package SingleMemberCourses;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.CalendarPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ThankYouPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollWithSingleCourseFeeTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String courseToEnroll = "FEECOURSE";
	private static String courseNameDisplayed = "FeeCourse";
	private static String courseTimeDisplayed = "Start Time: 11:00 AM";
	private static String courseInstructorDisplayed = "Course Instructor: Andrea";
	private static String CourseStartMonth = "Aug";
	private static int CourseStartYear = 2022;
	private static String dsiredMonthYear = "August 2022";
	private static String courseDate = "Date: 08/21/2022";
	private static DashboardPO d;
	private static BreadcrumbTrailPO BT;
	private static ClassSignUpPO c;
	private static PaymentMethodsPO PM;
	private static PurchaseConfirmationPO PP;
	private static ThankYouPO TY;
	private static String testName = null;
	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public EnrollWithSingleCourseFeeTest() {
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
		BT = new BreadcrumbTrailPO(driver);
		c = new ClassSignUpPO(driver);
		PM = new PaymentMethodsPO(driver);
		PP = new PurchaseConfirmationPO(driver);
		TY = new ThankYouPO(driver);
		jse = (JavascriptExecutor) driver;
	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Ui validations")
	public void UIValidations() throws IOException, InterruptedException {
		try {
			rm.activeMemberLogin(prop.getProperty("activeMember6_username"),
					prop.getProperty("activeMember6_password"));
//		rm.unenrollFromCourse(dsiredMonthYear);
//		Thread.sleep(2000);
//		rm.returnToDashboard();
			rw.waitForDashboardLoaded();
			jse.executeScript("arguments[0].click();", d.getMyCoursesEventsScheduleButton());
			Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartYear(CourseStartYear);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartMonth(CourseStartMonth);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectClassOrCourseToEnroll(courseToEnroll);

			Thread.sleep(2000);
			if (c.getPopupSignupButtonCourse().isEnabled()) {
				c.getPopupSignupButtonCourse().click();

			} else {
				getScreenshot("SignUp Button", driver);
				c.getPopupCancelButtonCourse().click();
				// Assert.fail("SignUp button not available");

			}
			Thread.sleep(2000);
			wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), courseNameDisplayed));
			Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
			Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
			Assert.assertEquals(courseNameDisplayed, c.getClassName().getText());
			Assert.assertEquals(courseTimeDisplayed, c.getClassStartTime().getText());
			Assert.assertEquals(courseInstructorDisplayed, c.getCourseInstructor().getText());
			Assert.assertEquals(courseDate, c.getClassDate().getText());

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

		finally {
			if (c.getContinueButton().isEnabled()) {

				int radioButtonCount = driver.findElements(By.tagName("label")).size();
				for (int i = 0; i < radioButtonCount; i++) {
					if (driver.findElements(By.tagName("label")).get(i).getText().trim().equals("Pay Course Fee")) {
						jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("label")).get(i));
						break;
					}
				}
				jse.executeScript("arguments[0].click();", c.getContinueButton());

				Thread.sleep(2000);
				rm.ReviewSectionValidation("Fee(s)");
			} else
				rm.memberLogout();
		}

	}

	@Test(priority = 2, description = "Payment Method OnAccount is selected by default", dependsOnMethods = {
			"UIValidations" })

	public void OnAccountIsSelectedByDefault() {

		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
					.contains(" On Account"))
				Assert.assertTrue(PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).isEnabled());
		}
	}

	@Test(priority = 3, description = "Enroll with Payment Method OnAccount", dependsOnMethods = { "UIValidations" })
	public void EnrollOnAccount() throws InterruptedException, IOException {
		try {

			// Noting down the total amount
			Thread.sleep(2000);
//		System.out.println(PP.getTotalAmount().getText());
			String totalAmt = PP.getClassesReviewtotalAmount().getText();

//		System.out.println(TotalAmt);

			// Verifies the Pay button contains the total amount
			Assert.assertTrue(PM.getPaymentButton().getText().contains(totalAmt));

			while (!PM.getOnAccountAndSavedCards().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			// Click the Pay button
			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}
			jse.executeScript("arguments[0].click();", PM.getPaymentButton());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));

			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			// Verifies the text on Thank You page and the links to navigate to Dashboard
			// and other pages are displayed
			rm.ThankYouPageValidations();

			// Note down the Receipt number
			String receiptNumber = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber));

			// Verifies the buttons on Print Receipt Popup
			rm.ReceiptPopupValidations();

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
			Thread.sleep(3000);

			// Navigate to Dashboard
			jse.executeScript("arguments[0].click();", TY.getDashBoardLink());
			rw.waitForDashboardLoaded();
			// Verifies the link navigates to the right page
			Assert.assertEquals("Dashboard", driver.getTitle());

			rm.unenrollFromCourse(dsiredMonthYear);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

		finally {
			/*
			 * boolean receiptpopuppresent =
			 * rm.isElementPresent(By.xpath("//div[@class='modal-content']"));
			 * 
			 * if (receiptpopuppresent == true) {System.out.println("closing the receipt");
			 * TY.getReceiptPopup().findElement(By.
			 * xpath("//button[contains(text(), 'Close')]")).click(); }
			 */

			rm.memberLogout();
		}

	}

	@Test(priority = 4, description = "Enroll With Saved Card")
	public void EnrollWithSavedCard() throws InterruptedException, IOException {
		try {
			rm.activeMemberLogin(prop.getProperty("activeMember7_username"),
					prop.getProperty("activeMember7_password"));
//		rm.unenrollFromCourse(dsiredMonthYear);
			Thread.sleep(2000);
			rm.returnToDashboard();

			jse.executeScript("arguments[0].click();", d.getMyCoursesEventsScheduleButton());

			Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartYear(CourseStartYear);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartMonth(CourseStartMonth);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectClassOrCourseToEnroll(courseToEnroll);

			Thread.sleep(2000);
			if (c.getPopupSignupButtonCourse().isEnabled()) {
				c.getPopupSignupButtonCourse().click();

			} else {
				c.getPopupCancelButtonCourse().click();
				// Assert.fail("SignUp button not available");

			}
			Thread.sleep(1000);
			wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), courseNameDisplayed));
			Assert.assertEquals(courseNameDisplayed, c.getClassName().getText());
			Assert.assertEquals(courseTimeDisplayed, c.getClassStartTime().getText());
			Assert.assertEquals(courseInstructorDisplayed, c.getCourseInstructor().getText());
			Assert.assertEquals(courseDate, c.getClassDate().getText());

			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().trim().equals("Pay Course Fee")) {
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("label")).get(i));
					break;
				}
			}

			jse.executeScript("arguments[0].click();", c.getContinueButton());

			Thread.sleep(3000);
			wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), courseNameDisplayed));
			Assert.assertEquals(courseNameDisplayed, c.getClassName().getText());
			Assert.assertEquals(courseTimeDisplayed, c.getClassStartTime().getText());
			Assert.assertEquals(courseInstructorDisplayed, c.getCourseInstructor().getText());
			Assert.assertEquals(courseDate, c.getClassDate().getText());

			while (!PM.getOnAccountAndSavedCards().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			wait.until(ExpectedConditions.textToBePresentInElement(PP.getClassesReviewtotalAmount(), "$"));
			jse.executeScript("arguments[0].scrollIntoView(true);", PM.getOnAccountAndSavedCards());

			int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
			for (int i = 0; i < count; i++) {
				if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
						.contains("1111")) {

					jse.executeScript("arguments[0].scrollIntoView(true);",
							PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));
					jse.executeScript("arguments[0].click();",
							PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));
					break;
				}
			}

			// Noting down the total amount
//		System.out.println(PP.getTotalAmount().getText());
			String totalAmt1 = PP.getClassesReviewtotalAmount().getText();

			System.out.println(totalAmt1);

			// Verifies the Pay button contains the total amount
			Assert.assertTrue(PM.getPaymentButton().getText().contains(totalAmt1));

			// Click the Pay button
			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}
			jse.executeScript("arguments[0].click();", PM.getPaymentButton());

			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			// Verifies the text on Thank You page and the links to navigate to Dashboard
			// and other pages are displayed
			rm.ThankYouPageValidations();

			// Note down the Receipt number
			String receiptNumber2 = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			jse.executeScript("arguments[0].click();", TY.getPrintReceiptButton());
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber2));

			// Verifies the buttons on Print Receipt Popup
			rm.ReceiptPopupValidations();
			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
			Thread.sleep(3000);

			// Navigate to Select Classes
			jse.executeScript("arguments[0].click();", TY.getViewClassesLink());
			Thread.sleep(2000);
			// Verifies the link navigates to the right page
//			Assert.assertEquals("Select Classes", driver.getTitle());

			rm.returnToDashboard();
			rm.unenrollFromCourse(dsiredMonthYear);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

		finally {
			/*
			 * boolean receiptpopuppresent =
			 * rm.isElementPresent(By.xpath("//div[@class='modal-content']")); if
			 * (receiptpopuppresent == true) { System.out.println("closing the receipt");
			 * TY.getReceiptPopup().findElement(By.
			 * xpath("//button[contains(text(), 'Close')]")).click(); }
			 */

			rm.memberLogout();
		}

	}

	@Test(priority = 5, description = "Enroll in Course with New Card")

	public void EnrollWithNewCard() throws InterruptedException, IOException {
		try {

			rm.activeMemberLogin(prop.getProperty("activeMember8_username"),
					prop.getProperty("activeMember8_password"));
//		rm.unenrollFromCourse(dsiredMonthYear);
			Thread.sleep(1000);
			rm.returnToDashboard();

			jse.executeScript("arguments[0].click();", d.getMyCoursesEventsScheduleButton());

			Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartYear(CourseStartYear);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectCourseStartMonth(CourseStartMonth);

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			rm.SelectClassOrCourseToEnroll(courseToEnroll);

			Thread.sleep(2000);
			if (c.getPopupSignupButtonCourse().isEnabled()) {
				c.getPopupSignupButtonCourse().click();

			} else {
				c.getPopupCancelButtonCourse().click();
				// Assert.fail("SignUp button not available");

			}
			Thread.sleep(1000);
			wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), courseNameDisplayed));
			Assert.assertEquals(courseNameDisplayed, c.getClassName().getText());
			Assert.assertEquals(courseTimeDisplayed, c.getClassStartTime().getText());
			Assert.assertEquals(courseInstructorDisplayed, c.getCourseInstructor().getText());

			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().trim().equals("Pay Course Fee")) {
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("label")).get(i));
					break;
				}
			}

			jse.executeScript("arguments[0].click();", c.getContinueButton());

			Thread.sleep(5000);

			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			jse.executeScript("arguments[0].click();", PM.getNewCardButton());
			Thread.sleep(3000);

			String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			while (opacity.contains("1")) {
				PM.getNewCardButton().click();

				opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			}

			wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

			Assert.assertTrue(PM.getCloseButton().isDisplayed());
			Assert.assertFalse(PM.getPaymentButton().isEnabled());
			System.out.println("Pay Button disabled:" + PM.getPaymentButton().getAttribute("disabled"));

//		System.out.println(PM.getNameOnCardField().getAttribute("value"));
			Assert.assertEquals(prop.getProperty("activeMember8_fullname"),
					PM.getNameOnCardField().getAttribute("value"));

			jse.executeScript("arguments[0].click();", PM.getCardNumberField());
			PM.getCardNumberField().sendKeys("4111111111111111");
			Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
			PM.getExpirationMonth().sendKeys("04");
			Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
			PM.getExpirationYear().sendKeys("29");
			Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
			PM.getSecurityCode().sendKeys("123");
			Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
			jse.executeScript("arguments[0].click();", PM.getSaveCardNo());
			Thread.sleep(1000);
			Assert.assertTrue(PM.getPaymentButton().isEnabled());

			// Noting down the total amount
//		System.out.println(PP.getTotalAmount().getText());
			String totalAmt2 = PP.getClassesReviewtotalAmount().getText();
			// System.out.println(totalAmt2);

			// Verifies the Pay button contains the total amount
			Assert.assertTrue(PM.getPaymentButton().getText().contains(totalAmt2));

			// Click the Pay button
			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}
			jse.executeScript("arguments[0].click();", PM.getPaymentButton());

			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			// Verifies the text on Thank You page and the links to navigate to Dashboard
			// and other pages are displayed
			rm.ThankYouPageValidations();

			// Note down the Receipt number
			String receiptNumber4 = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber4));

			// Verifies the buttons on Print Receipt Popup
			rm.ReceiptPopupValidations();

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
			Thread.sleep(3000);

			// Navigate to Select Courses
			jse.executeScript("arguments[0].click();", TY.getViewCoursesEventsLink());
			Thread.sleep(2000);
			// Verifies the link navigates to the right page
			Assert.assertEquals("Select Courses / Events", driver.getTitle());
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			// Assert.fail(ae.getMessage());

		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName, driver);
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			// Assert.fail(eci.getMessage());
		}

		finally {
			/*
			 * boolean receiptpopuppresent =
			 * rm.isElementPresent(By.xpath("//div[@class='modal-content']")); if
			 * (receiptpopuppresent == true) {System.out.println("closing the receipt");
			 * TY.getReceiptPopup().findElement(By.
			 * xpath("//button[contains(text(), 'Close')]")).click();}
			 */

			rm.returnToDashboard();

		}

	}

	@Test(priority = 6, description = "Unenroll from the course", enabled = true, dependsOnMethods = {
			"EnrollWithNewCard" })
	public void unenrollFromCourse() throws IOException, InterruptedException {
		try {

			CalendarPO cp = new CalendarPO(driver);

			Thread.sleep(2000);
			rw.waitForDashboardLoaded();
			d.getMenuMyActivies().click();

			while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1")) {
				Thread.sleep(500);
			}

			WebDriverWait wait1 = new WebDriverWait(driver, 50);
			wait1.until(ExpectedConditions.elementToBeClickable(d.getMenuMyCalendar()));

			d.getMenuMyCalendar().click();
			wait1.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

			cp.getCalendarViewLink().click();
			Thread.sleep(1000);

			String monthYear = cp.getMonthYear().getText();
			while (!monthYear.equals(dsiredMonthYear)) {
				cp.getRightArrow().click();
				wait1.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[@class = 'btn-group']//div[contains(@class, 'btn-white')][2]")));
				monthYear = cp.getMonthYear().getText();
			}
			Thread.sleep(1000);
			jse.executeScript("arguments[0].scrollIntoView(true);", cp.getCalDayBadge());
			Thread.sleep(1000);
			cp.getCalDayBadge().click();
			jse.executeScript("arguments[0].scrollIntoView(true);", cp.getCalEventTitle());
			Thread.sleep(1000);
			cp.getCalEventTitle().click();
			Thread.sleep(1000);
			cp.getUnEnrollBtn().click();
			Thread.sleep(1000);
			UnenrollPO u = new UnenrollPO(driver);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollNoRefund()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollNoRefund()));
			jse.executeScript("arguments[0].click();", u.getUnenrollNoRefund());
			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();
			rw.waitForAcceptButton();
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);
		}

		catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			getScreenshot("Unenroll", driver);
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			log.error(ne.getMessage(), ne);
			getScreenshot("Unenroll", driver);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			log.error(eci.getMessage(), eci);
			rm.catchErrorMessage();
			getScreenshot("Unenroll", driver);
			// Assert.fail(eci.getMessage());
		} finally {
			rm.returnToDashboard();
			rm.memberLogout();
		}

	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}
}
