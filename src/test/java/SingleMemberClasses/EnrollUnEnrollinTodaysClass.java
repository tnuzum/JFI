package SingleMemberClasses;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
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

public class EnrollUnEnrollinTodaysClass extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String classToEnroll;
	private static String classNameDisplayed;
	private static String classTimeDisplayed;
	private static String classInstructorDisplayed;
	private static DashboardPO d;
	private static BreadcrumbTrailPO BT;
	private static ClassSignUpPO c;
	private static PaymentMethodsPO PM;
	private static PurchaseConfirmationPO PP;
	private static ThankYouPO TY;
	private static JavascriptExecutor jse;
	public static String todaysDate = null;

	public reusableWaits rw;
	public reusableMethods rm;

	public EnrollUnEnrollinTodaysClass() {
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

		classToEnroll = prop.getProperty("classToEnroll");
		classNameDisplayed = prop.getProperty("classNameDisplayed");
		classTimeDisplayed = prop.getProperty("classTimeDisplayed");
		classInstructorDisplayed = prop.getProperty("classInstructorDisplayed");
	}

	@Test(priority = 1, description = "Ui validations")
	public void UIValidations() throws IOException, InterruptedException {

		rm.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));

		rm.unenrollFromClass();

		jse.executeScript("arguments[0].click();", d.getMyClassesScheduleButton());

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

//		rm.SelectTomorrowDate();

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

		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		todaysDate = dateFormat1.format(today1.getTime());

		Thread.sleep(2000);
		wait.until(ExpectedConditions.textToBePresentInElement(c.getClassName(), classNameDisplayed));
		Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
		Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
		Assert.assertEquals(classNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(classTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(classInstructorDisplayed, c.getClassInstructor().getText());
		Assert.assertEquals("Date: " + todaysDate, c.getClassDate().getText());

		int radioButtonCount = driver.findElements(By.tagName("label")).size();
		for (int i = 0; i < radioButtonCount; i++) {
			if (driver.findElements(By.tagName("label")).get(i).getText().trim().equals("Pay Single Class Fee")) {
				jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("label")).get(i));
				break;
			}
		}
		jse.executeScript("arguments[0].click();", c.getContinueButton());

		Thread.sleep(2000);
		rm.ReviewSectionValidation("Fee(s)");

	}

	@Test(priority = 2, description = "Payment Method OnAccount is selected by default", dependsOnMethods = {
			"UIValidations" })

	public void OnAccountIsSelectedByDefault() {

		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText().trim()
					.contains(" On Account"))
				Assert.assertTrue(PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).isEnabled());
		}
	}

	@Test(priority = 3, description = "Enroll with Payment Method OnAccount", dependsOnMethods = { "UIValidations" })
	public void EnrollOnAccount() throws InterruptedException, IOException {

		// Noting down the total amount
		Thread.sleep(2000);
//		System.out.println(PP.getTotalAmount().getText().trim());
		String totalAmt = PP.getClassesReviewtotalAmount().getText().trim();

//		System.out.println(TotalAmt);

		// Verifies the Pay button contains the total amount
		Assert.assertTrue(PM.getPaymentButton().getText().trim().contains(totalAmt));

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
		// rw.waitForAcceptButton();
		wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));

		// Verifies the success message
		Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText().trim());
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

		DashboardPO dp = new DashboardPO(driver);
		wait.until(
				ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[(contains@class, 'swal2-center')]")));
		jse.executeScript("arguments[0].click();", dp.getMyAccountAccountHistory());
		//rm.myProfileLogin(prop.getProperty("activeMember6_username"), "Testing1!");

		AcctHistoryPO ahp = new AcctHistoryPO(driver);

		while (ahp.getSearchingAcctHistMessage().size() != 0) {
			System.out.println("waiting for account history to display");
			Thread.sleep(1000);
		}

		while (!ahp.getReceiptNumberTable().isDisplayed()) {
			Thread.sleep(2000);
			System.out.println("waiting");
		}

		// Clicks on the Receiptnumber in Account History

		ahp.getSearchField().sendKeys(receiptNumber);

		Thread.sleep(2000);
//		wait.until(ExpectedConditions.textToBePresentInElement(ahp.getReceiptNumber(), receiptNumber));
//		jse.executeScript("arguments[0].click();", ahp.getReceiptNumbers().get(0));
//		Thread.sleep(3000);
//		jse.executeScript("arguments[0].scrollIntoView(true);",
//				TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-12 text-right']")));
//		// Verifies the Invoice amount
//		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-12 text-right']")).getText()
//				.contains(totalAmt));
//		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
//		Thread.sleep(1000);
		rm.returnToDashboard();

		rm.memberLogout();

	}

	@Test(priority = 3)

	public void classCheckInCOG() throws InterruptedException, IOException {
		rm.ClassCheckInCOG(classNameDisplayed, prop.getProperty("homeClubName"),
				prop.getProperty("activeMember6_username"));
		rm.unenrollFromClass();

	}

//	@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}
