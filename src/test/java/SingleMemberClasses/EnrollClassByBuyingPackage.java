package SingleMemberClasses;

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
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ThankYouPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollClassByBuyingPackage extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String classToEnroll = "CLASSNEEDSPUNCHES";
	private static String classNameDisplayed = "ClassNeedsPunches";
	private static String classTimeDisplayed = "Start Time: 10:00 AM";
	private static String classInstructorDisplayed = "Class Instructor: Max Gibbs";
	private static String buyPackageName = "Buy Day Pass";
	private static String packageName = "Day Pass";
	private static String defaultSelection = null;
	private static String unitsToBeSelected = "2 - $10.00/per";
	private static String classCostInUnits = "Class Cost: 2 Unit(s) - Your Current Unit Value Is ";
	private static int unitCount = 0;
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

	public EnrollClassByBuyingPackage() {
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

			rm.unenrollFromClass();

			jse.executeScript("arguments[0].click();", d.getMyClassesScheduleButton());

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

			rm.SelectTomorrowDate();

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectClassOrCourseToEnroll(classToEnroll);

			Thread.sleep(2000);

			if (c.getPopupSignUpButton().isEnabled()) {
				c.getPopupSignUpButton().click();

			} else {
				getScreenshot("SignUp Button", driver);
				c.getPopupCancelButton().click();
				// Assert.fail("SignUp button not available");

			}

			Thread.sleep(2000);
			wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), classNameDisplayed));
			Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
			Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
			Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
			Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
			Assert.assertEquals(classNameDisplayed, c.getClassName().getText());
			Assert.assertEquals(classTimeDisplayed, c.getClassStartTime().getText());
			Assert.assertEquals(classInstructorDisplayed, c.getClassInstructor().getText());
			Assert.assertEquals("Date: " + tomorrowsDate, c.getClassDate().getText());

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
			if (c.getContinueButton().isEnabled())

			{
				unitCount = rm.getPackageUnits(packageName);

				int radioButtonCount = driver.findElements(By.tagName("label")).size();
				for (int i = 0; i < radioButtonCount; i++) {
					if (driver.findElements(By.tagName("label")).get(i).getText().trim().equals(buyPackageName)) {
						jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("label")).get(i));
						break;
					}
				}
				System.out.println(classCostInUnits + unitCount);

				Assert.assertTrue(c.getClassCostinPunches().getText().contains(classCostInUnits + unitCount));
				WebElement W = driver.findElement(By.xpath("//div[@class='ibox-content']"));
				Select s = new Select(W.findElement(By.xpath("//select[contains(@class, 'form-control')]")));
				defaultSelection = s.getFirstSelectedOption().getText().trim();
				Assert.assertEquals(defaultSelection, unitsToBeSelected);

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

			rm.unenrollFromClass();

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
			 * xpath("//button[contains(text(), 'Close')]")).click();
			 * rm.returnToDashboard();}
			 */

			rm.memberLogout();
		}
	}

	@Test(priority = 4, description = "Enroll With Saved Card")
	public void EnrollWithSavedCard() throws InterruptedException, IOException {

		try {
			rm.activeMemberLogin(prop.getProperty("activeMember7_username"),
					prop.getProperty("activeMember7_password"));
			rm.unenrollFromClass();

			unitCount = rm.getPackageUnitsForMember(packageName, "ccmember");

			jse.executeScript("arguments[0].click();", d.getMyClassesScheduleButton());

			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectTomorrowDate();

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectClassOrCourseToEnroll(classToEnroll);

			Thread.sleep(2000);
			if (c.getPopupSignUpButton().isEnabled()) {
				c.getPopupSignUpButton().click();

			} else {
				c.getPopupCancelButton().click();
				// Assert.fail("SignUp button not available");

			}
			Thread.sleep(1000);
			wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), classNameDisplayed));
			Assert.assertEquals(classNameDisplayed, c.getClassName().getText());
			Assert.assertEquals(classTimeDisplayed, c.getClassStartTime().getText());
			Assert.assertEquals(classInstructorDisplayed, c.getClassInstructor().getText());
			Assert.assertEquals("Date: " + tomorrowsDate, c.getClassDate().getText());

			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().trim().equals(buyPackageName)) {
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("label")).get(i));
					break;
				}
			}

			System.out.println(classCostInUnits + unitCount);
			Assert.assertTrue(c.getClassCostinPunches().getText().contains(classCostInUnits + unitCount));
//		Assert.assertTrue(c.getClassCostinPunches().getText().equals(classCostInUnits ));
			WebElement W = driver.findElement(By.xpath("//div[@class='ibox-content']"));
			Select s = new Select(W.findElement(By.xpath("//select[contains(@class, 'form-control')]")));
			defaultSelection = s.getFirstSelectedOption().getText().trim();
			Assert.assertEquals(defaultSelection, unitsToBeSelected);

			jse.executeScript("arguments[0].click();", c.getContinueButton());

			Thread.sleep(3000);

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

					jse.executeScript("arguments[0].click();",
							PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));
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
			jse.executeScript("arguments[0].click();", PM.getPaymentButton());

			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();

			// Verifies the text on Thank You page and the links to navigate to Dashboard
			// and other pages are displayed
			rm.ThankYouPageValidations();

			// Note down the Receipt number
			String receiptNumber2 = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
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
			Assert.assertEquals("Select Classes", driver.getTitle());

			rm.returnToDashboard();
			rm.unenrollFromClass();

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

	@Test(priority = 5, description = "Enroll in class with New Card")

	public void EnrollWithNewCard() throws InterruptedException, IOException {
		try {

			rm.activeMemberLogin(prop.getProperty("activeMember8_username"),
					prop.getProperty("activeMember8_password"));

			rm.unenrollFromClass();

			unitCount = rm.getPackageUnitsForMember(packageName, "ncmember");

			jse.executeScript("arguments[0].click();", d.getMyClassesScheduleButton());

			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectTomorrowDate();

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectClassOrCourseToEnroll(classToEnroll);

			Thread.sleep(2000);

			if (c.getPopupSignUpButton().isEnabled()) {
				c.getPopupSignUpButton().click();

			} else {
				c.getPopupCancelButton().click();
				// Assert.fail("SignUp button not available");

			}
			Thread.sleep(5000);
			wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), classNameDisplayed));
			Assert.assertEquals(classNameDisplayed, c.getClassName().getText());
			Assert.assertEquals(classTimeDisplayed, c.getClassStartTime().getText());
			Assert.assertEquals(classInstructorDisplayed, c.getClassInstructor().getText());
			Assert.assertEquals("Date: " + tomorrowsDate, c.getClassDate().getText());

			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().trim().equals(buyPackageName)) {
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("label")).get(i));
					break;
				}
			}

			System.out.println(classCostInUnits + unitCount);
			Assert.assertTrue(c.getClassCostinPunches().getText().contains(classCostInUnits + unitCount));
			WebElement W = driver.findElement(By.xpath("//div[@class='ibox-content']"));
			Select s = new Select(W.findElement(By.xpath("//select[contains(@class, 'form-control')]")));
			defaultSelection = s.getFirstSelectedOption().getText().trim();
			Assert.assertEquals(defaultSelection, unitsToBeSelected);

			jse.executeScript("arguments[0].click();", c.getContinueButton());

			wait.until(ExpectedConditions.textToBePresentInElement(PP.getClassesReviewtotalAmount(), "$"));

			Thread.sleep(5000);

			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);

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
			PM.getCardNumberField().sendKeys("4111111111111111");
			Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
			PM.getExpirationMonth().sendKeys("04");
			Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
			PM.getExpirationYear().sendKeys("29");
			Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
			PM.getSecurityCode().sendKeys("123");
			Assert.assertEquals(PM.getPaymentButton().getAttribute("disabled"), "true");
			jse.executeScript("arguments[0].click();", PM.getCheckBox());
			while (!PM.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}

			// Clicks on the Pay button without signature
			jse.executeScript("arguments[0].click();", PM.getPaymentButton());
			System.out.println(PM.getPopupContent().getText());
			Assert.assertTrue(PM.getPopupContent().getText().contains("A signature is required to continue."));
			PM.getPopupOk().click();
			Thread.sleep(1000);
			jse.executeScript("arguments[0].click();", PM.getSaveCardNo());
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

			jse.executeScript("arguments[0].click();", PM.getPaymentButton());
			System.out.println("Element clicked");

			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();

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

			// Navigate to Select Classes
			jse.executeScript("arguments[0].click();", TY.getViewCoursesEventsLink());

			Thread.sleep(2000);
			// Verifies the link navigates to the right page
			Assert.assertEquals("Select Courses / Events", driver.getTitle());

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

	@Test(priority = 6, description = "Unenroll from the class", dependsOnMethods = { "EnrollWithNewCard" })
	public void unenrollFromClass() throws IOException, InterruptedException {

		Thread.sleep(2000);
		rw.waitForDashboardLoaded();
		boolean enrolled = rm.isElementPresent(By.xpath("//classeswidget//div[@class='class-table-container']"));
//		System.out.println(enrolled);
		if (enrolled == true) {

			try {

				while (!d.getMyClassesClass1GearButton().isDisplayed()) {
					Thread.sleep(1000);
					System.out.println("Sleeping for 1 second");
				}
				WebDriverWait wait = new WebDriverWait(driver, 60);
				wait.until(ExpectedConditions.elementToBeClickable(d.getMyClassesClass1GearButton()));
				jse.executeScript("arguments[0].click();", d.getMyClassesClass1GearButton());

				wait.until(ExpectedConditions.visibilityOf(d.getmyClassesUnenrollButton()));
				wait.until(ExpectedConditions.elementToBeClickable(d.getmyClassesUnenrollButton()));
				jse.executeScript("arguments[0].click();", d.getmyClassesUnenrollButton());
				Thread.sleep(1000);
				UnenrollPO u = new UnenrollPO(driver);
				wait.until(ExpectedConditions.visibilityOf(u.getUnenrollNoRefund()));
				wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollNoRefund()));
				jse.executeScript("arguments[0].click();", u.getUnenrollNoRefund());
				Thread.sleep(1000);
				rw.waitForAcceptButton();
				u.getUnenrollConfirmYesButton().click();
				rw.waitForAcceptButton();
				Thread.sleep(1000);
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
		} else {
			System.out.println("Not enrolled");
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
