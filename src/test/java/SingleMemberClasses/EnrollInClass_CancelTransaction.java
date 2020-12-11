package SingleMemberClasses;

import java.io.IOException;

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

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import resources.base;
import resources.reusableMethods;

public class EnrollInClass_CancelTransaction extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String classToEnroll = "BARRE COMBAT FUSION";
	private static String classNameDisplayed = "Barre Combat Fusion";
	private static String classTimeDisplayed = "Start Time: 5:00 PM";
	private static String classInstructorDisplayed = "Class Instructor: Andrea";
	private static JavascriptExecutor jse;

	public reusableMethods rm;

	public EnrollInClass_CancelTransaction() {
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);
		jse = (JavascriptExecutor) driver;
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
	}

	@Test(priority = 1, description = "Cancelling from the Rate Select Rates page")
	public void CancelFromSelectRatesPage() throws IOException, InterruptedException {
		rm.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));
		rm.unenrollFromClass();

		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		jse.executeScript("arguments[0].click();", d.getMyClassesScheduleButton());
		Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());

		ClassSignUpPO c = new ClassSignUpPO(driver);

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectTomorrowDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectClassOrCourseToEnroll(classToEnroll);

		Thread.sleep(2000);
		c.getPopupSignUpButton().click();
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

		jse.executeScript("arguments[0].click();", c.getCancelLink());

		Assert.assertEquals(c.getPageHeader().getText(), "Select Classes");

	}

	@Test(priority = 2, description = "Cancelling from the Rate Confirmation page")
	public void CancelFromConfirmationPage() throws IOException, InterruptedException {

		ClassSignUpPO c = new ClassSignUpPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		rm.SelectTomorrowDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j = 0; j < ClassCount; j++) {
			String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();

			if (className.contains(classToEnroll)) {
				jse.executeScript("arguments[0].click();",
						driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j)); // Click on the
				// specific class
				break;
			}
		}

		Thread.sleep(2000);
		c.getPopupSignUpButton().click();
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

		int radioButtonCount = driver.findElements(By.tagName("label")).size();
		for (int i = 0; i < radioButtonCount; i++) {
			if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Single Class Fee")) {
				jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("label")).get(i));
				break;
			}
		}

		jse.executeScript("arguments[0].click();", c.getContinueButton());
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

		// Click the Cancel button

		jse.executeScript("arguments[0].click();", PM.getCancelButton());

		Assert.assertEquals(c.getPageHeader().getText(), "Select Classes");

		rm.memberLogout();
	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}
}
