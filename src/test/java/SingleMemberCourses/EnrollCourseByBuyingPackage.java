package SingleMemberCourses;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

public class EnrollCourseByBuyingPackage extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String CourseToEnroll = "COURSENEEDSPUNCHES";
	private static String CourseNameDisplayed = "CourseNeedsPunches";
	private static String CourseTimeDisplayed = "Start Time: 11:00 AM";
	private static String CourseInstructorDisplayed = "Course Instructor: Andrea";
	private static String buyPackageName = "Buy Day Pass";
	private static String packageName = "Day Pass";
	private static String CourseStartMonth = "Dec";
	private static String dsiredMonthYear = "December 2020";
	private static String defaultSelection = null;
	private static String unitsToBeSelected = "2 - $1.00/per";
	private static String classCostInUnits = "Course Cost: 2 Unit(s) - Your Current Unit Value Is ";
	private static String CourseStartDate = "Date: 12/21/2020";
	private static int unitCount = 0;
	private static DashboardPO d;
	private static BreadcrumbTrailPO BT;
	private static ClassSignUpPO c;
	private static PaymentMethodsPO PM;
	private static PurchaseConfirmationPO PP;
	private static ThankYouPO TY;
	private static String testName = null;

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));

		d = new DashboardPO(driver);
		BT = new BreadcrumbTrailPO(driver);
		c = new ClassSignUpPO(driver);
		PM = new PaymentMethodsPO(driver);
		PP = new PurchaseConfirmationPO(driver);
		TY = new ThankYouPO(driver);
	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Ui validations")
	public void UIValidations() throws IOException, InterruptedException {
		try {
			reusableMethods.activeMemberLogin(prop.getProperty("activeMember6_username"),
					prop.getProperty("activeMember6_password"));
//		reusableMethods.unenrollFromCourse();
//		Thread.sleep(2000);
//		reusableMethods.returnToDashboard();

			d.getMyCoursesEventsScheduleButton().click();

			Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

//		System.out.println(driver.findElement(By.xpath("//label[@id='dec']")).getText());
//		driver.findElement(By.xpath("//label[@id='dec']")).click();
			WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
			int monthCount = MonthNames.findElements(By.tagName("label")).size();
			for (int i = 0; i < monthCount; i++) {
				String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
				if (monthName.equals(CourseStartMonth)) {
					MonthNames.findElements(By.tagName("label")).get(i).click();
					break;
				}

			}

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
			for (int j = 0; j < CourseCount; j++) {
				String CourseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j)
						.getText();

				if (CourseName.contains(CourseToEnroll)) {
					driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																										// specific
																										// Course
					break;
				}
			}

			Thread.sleep(2000);

			if (c.getPopupSignupButtonCourse().isEnabled()) {
				c.getPopupSignupButtonCourse().click();

			} else {
				c.getPopupCancelButtonCourse().click();
				Assert.fail("SignUp button not available");

			}
			Thread.sleep(2000);
			Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
			Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
			Assert.assertEquals(CourseNameDisplayed, c.getClassName().getText());
			Assert.assertEquals(CourseTimeDisplayed, c.getClassStartTime().getText());
			Assert.assertEquals(CourseStartDate, c.getClassDate().getText());
			Assert.assertEquals(CourseInstructorDisplayed, c.getCourseInstructor().getText());

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName);
			log.error(ae.getMessage(), ae);
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName);
			log.error(eci.getMessage(), eci);
			reusableMethods.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}

		finally {
			if (c.getContinueButton().isEnabled())

			{
				unitCount = reusableMethods.getPackageUnits(packageName);
				System.out.println(classCostInUnits + unitCount);
				System.out.println(c.getClassCostinPunches().getText());

				int radioButtonCount = driver.findElements(By.tagName("label")).size();
				for (int i = 0; i < radioButtonCount; i++) {
					if (driver.findElements(By.tagName("label")).get(i).getText().equals(buyPackageName)) {
						driver.findElements(By.tagName("label")).get(i).click();
						break;
					}
				}

				Assert.assertTrue(c.getClassCostinPunches().getText().contains(classCostInUnits + unitCount));
				WebElement W = driver.findElement(By.xpath("//div[@class='ibox-content']"));
				Select s = new Select(W.findElement(By.xpath("//select[contains(@class, 'form-control')]")));
				defaultSelection = s.getFirstSelectedOption().getText().trim();
				Assert.assertEquals(defaultSelection, unitsToBeSelected);

				c.getContinueButton().click();
				Thread.sleep(2000);
				reusableMethods.ReviewSectionValidation("Package(s)");

			} else
				reusableMethods.memberLogout();
		}

	}

	@Test(priority = 2, description = "Payment Method OnAccount is selected by default", dependsOnMethods = {
			"UIValidations" })

	public void OnAccountIsSelectedByDefault() {

		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
					.contains(" On Account"))
				Assert.assertTrue(PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).isSelected());
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
			PM.getPaymentButton().click();

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));

			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			// Verifies the text on Thank You page and the links to navigate to Dashboard
			// and other pages are displayed
			reusableMethods.ThankYouPageValidations();

			// Note down the Receipt number
			String receiptNumber = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber));

			// Verifies the buttons on Print Receipt Popup
			reusableMethods.ReceiptPopupValidations();

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
			Thread.sleep(3000);

			// Navigate to Dashboard
			int count = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

				{
					// reusableWaits.linksToBeClickable();
					driver.findElements(By.tagName("a")).get(i).click();
					break;
				}

			}
			reusableWaits.waitForDashboardLoaded();
			// Verifies the link navigates to the right page
			Assert.assertEquals("Dashboard", driver.getTitle());
			Thread.sleep(3000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName);
			log.error(ae.getMessage(), ae);
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName);
			log.error(eci.getMessage(), eci);
			reusableMethods.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}

		finally {
			/*
			 * boolean receiptpopuppresent =
			 * reusableMethods.isElementPresent(By.xpath("//div[@class='modal-content']"));
			 * 
			 * if (receiptpopuppresent == true) {System.out.println("closing the receipt");
			 * TY.getReceiptPopup().findElement(By.
			 * xpath("//button[contains(text(), 'Close')]")).click();
			 * reusableMethods.returnToDashboard();}
			 */
			reusableMethods.returnToDashboard();
			reusableMethods.unenrollFromCourse(dsiredMonthYear);
			reusableMethods.memberLogout();
		}

	}

	@Test(priority = 4, description = "Enroll With Saved Card")

	public void EnrollWithSavedCard() throws InterruptedException, IOException {
		try {
			reusableMethods.activeMemberLogin(prop.getProperty("activeMember7_username"),
					prop.getProperty("activeMember7_password"));
//		reusableMethods.unenrollFromCourse(dsiredMonthYear);
//		Thread.sleep(2000);
//		reusableMethods.returnToDashboard();

			unitCount = reusableMethods.getPackageUnitsForMember(packageName, "ccmember");

			d.getMyCoursesEventsScheduleButton().click();

			Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

//		System.out.println(driver.findElement(By.xpath("//label[@id='dec']")).getText());
//		driver.findElement(By.xpath("//label[@id='dec']")).click();
			WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
			int monthCount = MonthNames.findElements(By.tagName("label")).size();
			for (int i = 0; i < monthCount; i++) {
				String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
				if (monthName.equals(CourseStartMonth)) {
					MonthNames.findElements(By.tagName("label")).get(i).click();
					break;
				}

			}

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
			for (int j = 0; j < CourseCount; j++) {
				String CourseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j)
						.getText();

				if (CourseName.contains(CourseToEnroll)) {
					driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																										// specific
																										// Course
					break;
				}
			}

			Thread.sleep(500);

			if (c.getPopupSignupButtonCourse().isEnabled()) {
				c.getPopupSignupButtonCourse().click();

			} else {
				c.getPopupCancelButtonCourse().click();
				Assert.fail("SignUp button not available");

			}
			Thread.sleep(2000);

			Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
			Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
			Assert.assertEquals(CourseNameDisplayed, c.getClassName().getText());
			Assert.assertEquals(CourseTimeDisplayed, c.getClassStartTime().getText());
			Assert.assertEquals(CourseStartDate, c.getClassDate().getText());
			Assert.assertEquals(CourseInstructorDisplayed, c.getCourseInstructor().getText());

			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().equals(buyPackageName)) {
					driver.findElements(By.tagName("label")).get(i).click();
					break;
				}
			}

			Assert.assertTrue(c.getClassCostinPunches().getText().contains(classCostInUnits + unitCount));
			WebElement W = driver.findElement(By.xpath("//div[@class='ibox-content']"));
			Select s = new Select(W.findElement(By.xpath("//select[contains(@class, 'form-control')]")));
			defaultSelection = s.getFirstSelectedOption().getText().trim();
			Assert.assertEquals(defaultSelection, unitsToBeSelected);

			c.getContinueButton().click();

			Thread.sleep(3000);

			while (!PM.getOnAccountAndSavedCards().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}
			int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
			for (int i = 0; i < count; i++) {
				if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
						.contains("5454")) {

					PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).click();
					break;
				}
			}

			// Noting down the total amount
//		System.out.println(PP.getTotalAmount().getText());
			String totalAmt1 = PP.getClassesReviewtotalAmount().getText();

//		System.out.println(totalAmt1);

			// Verifies the Pay button contains the total amount
			Assert.assertTrue(PM.getPaymentButton().getText().contains(totalAmt1));

			// Click the Pay button
			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}
			PM.getPaymentButton().click();

			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();

			// Verifies the text on Thank You page and the links to navigate to Dashboard
			// and other pages are displayed
			reusableMethods.ThankYouPageValidations();

			// Note down the Receipt number
			String receiptNumber2 = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber2));

			// Verifies the buttons on Print Receipt Popup
			reusableMethods.ReceiptPopupValidations();
			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
			Thread.sleep(3000);

			// Navigate to Select Classes
			int count1 = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count1; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Classes"))

				{
					// reusableWaits.linksToBeClickable();
					driver.findElements(By.tagName("a")).get(i).click();
					break;
				}

			}
			Thread.sleep(2000);
			// Verifies the link navigates to the right page
			Assert.assertEquals("Select Classes", driver.getTitle());
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName);
			log.error(ae.getMessage(), ae);
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName);
			log.error(eci.getMessage(), eci);
			reusableMethods.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}

		finally {
			/*
			 * boolean receiptpopuppresent =
			 * reusableMethods.isElementPresent(By.xpath("//div[@class='modal-content']"));
			 * if (receiptpopuppresent == true) { System.out.println("closing the receipt");
			 * TY.getReceiptPopup().findElement(By.
			 * xpath("//button[contains(text(), 'Close')]")).click(); }
			 */

			reusableMethods.returnToDashboard();
			reusableMethods.unenrollFromCourse(dsiredMonthYear);
			reusableMethods.memberLogout();

		}

	}

	@Test(priority = 5, description = "Enroll in Course with New Card")

	public void EnrollWithNewCard() throws InterruptedException, IOException {
		try {

			reusableMethods.activeMemberLogin(prop.getProperty("activeMember8_username"),
					prop.getProperty("activeMember8_password"));
//		reusableMethods.unenrollFromCourse();
//		Thread.sleep(1000);
//		reusableMethods.returnToDashboard();

			unitCount = reusableMethods.getPackageUnitsForMember(packageName, "ncmember");

			d.getMyCoursesEventsScheduleButton().click();

			Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

//		System.out.println(driver.findElement(By.xpath("//label[@id='dec']")).getText());
//		driver.findElement(By.xpath("//label[@id='dec']")).click();
			WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
			int monthCount = MonthNames.findElements(By.tagName("label")).size();
			for (int i = 0; i < monthCount; i++) {
				String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
				if (monthName.equals(CourseStartMonth)) {
					MonthNames.findElements(By.tagName("label")).get(i).click();
					break;
				}

			}

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

			int CourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
			for (int j = 0; j < CourseCount; j++) {
				String CourseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j)
						.getText();

				if (CourseName.contains(CourseToEnroll)) {
					driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																										// specific
																										// Course
					break;
				}
			}

			Thread.sleep(2000);

			if (c.getPopupSignupButtonCourse().isEnabled()) {
				c.getPopupSignupButtonCourse().click();

			} else {
				c.getPopupCancelButtonCourse().click();
				Assert.fail("SignUp button not available");

			}
			Thread.sleep(2000);

			Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
			Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
			Assert.assertEquals(CourseNameDisplayed, c.getClassName().getText());
			Assert.assertEquals(CourseTimeDisplayed, c.getClassStartTime().getText());
			Assert.assertEquals(CourseStartDate, c.getClassDate().getText());
			Assert.assertEquals(CourseInstructorDisplayed, c.getCourseInstructor().getText());

			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().equals(buyPackageName)) {
					driver.findElements(By.tagName("label")).get(i).click();
					break;
				}
			}

			Assert.assertTrue(c.getClassCostinPunches().getText().contains(classCostInUnits + unitCount));
			WebElement W = driver.findElement(By.xpath("//div[@class='ibox-content']"));
			Select s = new Select(W.findElement(By.xpath("//select[contains(@class, 'form-control')]")));
			defaultSelection = s.getFirstSelectedOption().getText().trim();
			Assert.assertEquals(defaultSelection, unitsToBeSelected);

			c.getContinueButton().click();

			wait.until(ExpectedConditions.textToBePresentInElement(PP.getClassesReviewtotalAmount(), "$"));

			Thread.sleep(3000);

			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			PM.getNewCardButton().click();
			Thread.sleep(3000);

			String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			while (opacity.contains("1")) {
				PM.getNewCardButton().click();
				opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
			}

			Assert.assertTrue(PM.getCloseButton().isDisplayed());
			Assert.assertFalse(PM.getPaymentButton().isEnabled());
			System.out.println("Pay Button disabled:" + PM.getPaymentButton().getAttribute("disabled"));

//		System.out.println(PM.getNameOnCardField().getAttribute("value"));
			Assert.assertEquals(prop.getProperty("activeMember8_fullname"),
					PM.getNameOnCardField().getAttribute("value"));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", PM.getCardNumberField());
			PM.getCardNumberField().sendKeys("4111111111111111");
			Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
			PM.getExpirationMonth().sendKeys("12");
			Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
			PM.getExpirationYear().sendKeys("29");
			Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
			PM.getSecurityCode().sendKeys("123");
			Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");

			PM.getCheckBox().click();
			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}
			// Clicks on the Pay button without signature
			PM.getPaymentButton().click();
			System.out.println(PM.getPopupContent().getText());
			Assert.assertTrue(PM.getPopupContent().getText().contains("A signature is required to continue."));
			PM.getPopupOk().click();
			Thread.sleep(1000);
			PM.getSaveCardNo().click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.elementToBeClickable(PM.getPaymentButton()));
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
			PM.getPaymentButton().click();

			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();

			// Verifies the text on Thank You page and the links to navigate to Dashboard
			// and other pages are displayed
			reusableMethods.ThankYouPageValidations();

			// Note down the Receipt number
			String receiptNumber4 = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber4));

			// Verifies the buttons on Print Receipt Popup
			reusableMethods.ReceiptPopupValidations();

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
			Thread.sleep(3000);

			// Navigate to Select Courses
			int count1 = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count1; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Courses / Events"))

				{
					// reusableWaits.linksToBeClickable();
					driver.findElements(By.tagName("a")).get(i).click();
					break;
				}

			}
			Thread.sleep(2000);
			// Verifies the link navigates to the right page
			Assert.assertEquals("Select Courses / Events", driver.getTitle());
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName);
			log.error(ae.getMessage(), ae);
			Assert.fail(ae.getMessage());

		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName);
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName);
			log.error(eci.getMessage(), eci);
			reusableMethods.catchErrorMessage();
			Assert.fail(eci.getMessage());
		}

		finally {
			/*
			 * boolean receiptpopuppresent =
			 * reusableMethods.isElementPresent(By.xpath("//div[@class='modal-content']"));
			 * if (receiptpopuppresent == true) {System.out.println("closing the receipt");
			 * TY.getReceiptPopup().findElement(By.
			 * xpath("//button[contains(text(), 'Close')]")).click();}
			 */

			Thread.sleep(2000);
			reusableMethods.returnToDashboard();

		}

	}

	@Test(priority = 6, description = "Unenroll from the Course")
	public void unenrollFromCourse() throws IOException, InterruptedException {
		try {

			CalendarPO cp = new CalendarPO(driver);

			Thread.sleep(2000);
			reusableWaits.waitForDashboardLoaded();
			d.getMenuMyActivies().click();

			while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1")) {
				Thread.sleep(500);
			}

			d.getMenuMyCalendar().click();
			String monthYear = cp.getMonthYear().getText();
			while (!monthYear.equals(dsiredMonthYear)) {
				cp.getRightArrow().click();
				monthYear = cp.getMonthYear().getText();
			}

			Thread.sleep(1000);
			cp.getCalDayBadge().click();
			Thread.sleep(1000);
			cp.getCalEventTitle().click();
			Thread.sleep(1000);
			cp.getUnEnrollBtn().click();
			Thread.sleep(1000);
			UnenrollPO u = new UnenrollPO(driver);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));
			u.getUnenrollButton().click();
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			u.getUnenrollConfirmYesButton().click();
			wait.until(ExpectedConditions.stalenessOf(u.getUnenrollConfirmYesButton()));
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);
		}

		catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			log.error(ae.getMessage(), ae);
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			log.error(ne.getMessage(), ne);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			log.error(eci.getMessage(), eci);
			reusableMethods.catchErrorMessage();
			Assert.fail(eci.getMessage());
		} finally {
			reusableMethods.returnToDashboard();
			reusableMethods.memberLogout();
		}

	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
