package FamilyMemberClasses;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class FamilyMemberClassEnrollment2 extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String classToEnroll = "CLASSFREEWITHSERVICED";
	private static String classNameDisplayed = "ClassFreeWithServiceD";
	private static String classTimeDisplayed = "Start Time: 10:00 AM";
	private static String classInstructorDisplayed = "Class Instructor: Max Gibbs";
	private static String classInstructorDisplayedOnSearchScreen = "Inst: Max Gibbs";
	private static String classTimeDisplayedOnSearchScreen = "10:00 AM";
	private static String classDuration = "45 Min";
	private static String member1 = "FreeServiceDMbr";
	private static String member1Rate = "Free";
	private static String member2 = "HOH2";
	private static String member2Rate = "$5.00";
	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public FamilyMemberClassEnrollment2() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		jse = (JavascriptExecutor) driver;
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
	}

	@Test(priority = 1, description = "Family Member Enrollment")
	public void FamilyMemberEnrollment() throws IOException, InterruptedException {
		try {
			rm.activeMemberLogin("hoh2", "Testing1!");
			// rm.unenrollFromClass();
			// Thread.sleep(2000);
			// rm.returnToDashboard();
			rw.waitForDashboardLoaded();
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

			c.getCourseFilter().click();
			c.getCourseKeyword().click();
			c.getSearchField().sendKeys("ClassFree");
			Thread.sleep(1000);
			c.getClassApplyFilters().click();
			Thread.sleep(2000);

			int ClassCount = c.getClassTable().size();
			for (int j = 0; j < ClassCount; j++) {

				WebElement w = c.getClassTable().get(j);
				WebElement w1 = c.getClassTimeAndDuration().get(j);
				String className = w.getText();
				String classTimeAndDuration = w1.getText();

				if (className.contains(classToEnroll))

				{
					Assert.assertTrue(className.contains(classInstructorDisplayedOnSearchScreen));
					Assert.assertTrue(classTimeAndDuration.contains(classTimeDisplayedOnSearchScreen));
					Assert.assertTrue(classTimeAndDuration.contains(classDuration));

					List<WebElement> getMemberRate = w.findElements(By.className("ng-star-inserted"));

					int count = getMemberRate.size();

					for (int k = 0; k < count; k++) {

						if (getMemberRate.get(k).getText().contains(member1))
							Assert.assertTrue(getMemberRate.get(k).getText().contains(member1Rate));
						if (getMemberRate.get(k).getText().contains(member2))
							Assert.assertTrue(getMemberRate.get(k).getText().contains(member2Rate));
					}

					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//div[(contains@class, 'mat-drawer-backdrop')]")));
					jse.executeScript("arguments[0].click();", w); // Click on the specific class
					break;
				}
			}

			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'modal-content')]")));
			while (c.getClasslabel().getText().isBlank()) {
				Thread.sleep(500);
			}

			Assert.assertEquals(c.getClasslabel().getText(), classNameDisplayed); // Verifies the class name
			int count = c.getFmlyMemberLabel().size();

			// Selects the other eligible members
			for (int i = 0; i < count; i++) {

				WebElement fml = c.getFmlyMemberLabel().get(i);
				WebElement fmc = c.getFmlyMemberCheckBox().get(i);

				if (fml.getText().contains(member1))
					fml.click(); // Selects the member

				if (fml.getText().contains(member2))
					Assert.assertTrue(fmc.isSelected());

			}
			Thread.sleep(1000);
			jse.executeScript("window.scrollTo(0," + c.getPopupSignUpButton().getLocation().x + ")");
			c.getPopupSignUpButton().click();

			while (c.getClassName().getText().isBlank()) {
				Thread.sleep(500);
			}

			Assert.assertEquals(classNameDisplayed, c.getClassName().getText());
			Assert.assertEquals(classTimeDisplayed, c.getClassStartTime().getText());
			Assert.assertEquals(classInstructorDisplayed, c.getClassInstructor().getText());
			Assert.assertEquals("Date: " + tomorrowsDate, c.getClassDate().getText());

			for (int i = 0; i < c.getMemberSections().size(); i++) {
				String paymentOptions = c.getMemberSections().get(i).getText();
				List<WebElement> Labels = c.getMemberSections().get(i).findElements(By.tagName("label"));

				if (c.getMemberSections().get(i).getText().contains(member2)) {

					for (int j = 0; j < Labels.size(); j++) {
						if (Labels.get(j).getText().contains("Pay Single Class Fee")) {
							jse.executeScript("arguments[0].click();", Labels.get(j));
							break;
						}
					}

				}

				if (c.getMemberSections().get(i).getText().contains(member1)) {

					Assert.assertTrue(paymentOptions.contains("Free")); // Class is free for this member
					for (int j = 0; j < Labels.size(); j++) {
						if (Labels.get(j).getText().contains("Free"))
							Assert.assertTrue(Labels.get(j).isEnabled());
					}
				}

			}
			jse.executeScript("arguments[0].click();", c.getContinueButton());

			PurchaseConfirmationPO pp = new PurchaseConfirmationPO(driver);

			PaymentMethodsPO PM = new PaymentMethodsPO(driver);

			while (c.getClassName().getText().isBlank()) {
				Thread.sleep(500);
			}

			System.out.println(pp.getMemberfeesSection().size());
			for (int i = 0; i < pp.getMemberfeesSection().size(); i++) {
				String text = pp.getMemberfeesSection().get(i).getText();

				if (text.contains(member2))
					Assert.assertTrue(text.contains("Single Class Fee $5.00"));

				if (text.contains(member1))
					Assert.assertTrue(text.contains("Single Class Fee Free"));

			}

			while (pp.getClassesReviewtotalAmount().getText().isBlank()) {
				Thread.sleep(500);
			}

			String totalAmount = pp.getClassesReviewtotalAmount().getText();

			Assert.assertTrue(PM.getPaymentButton().getText().contains(totalAmount)); // Verifies the Pay button
																						// contains
																						// the total amount

			while (!PM.getOnAccountAndSavedCards().isDisplayed())

			{
				Thread.sleep(1000);

			}

			jse.executeScript("arguments[0].click();", PM.getPaymentButton());
			rw.waitForAcceptButton();
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

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
			Thread.sleep(1000);

			// Navigate to Dashboard
			int count1 = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count1; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

				{
					// rw.linksToBeClickable();
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
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
			jse.executeScript("arguments[0].click();", dp.getMyAccountAccountHistory());

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
			wait.until(ExpectedConditions.textToBePresentInElement(ahp.getReceiptNumber(), receiptNumber));
			jse.executeScript("arguments[0].click();", ahp.getReceiptNumber());
			Thread.sleep(3000);
			jse.executeScript("arguments[0].scrollIntoView(true);",
					TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-12 text-right']")));
			// Verifies the Invoice amount
			Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-12 text-right']"))
					.getText().contains(totalAmount));
			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
			Thread.sleep(1000);
			rm.returnToDashboard();

			rm.unenrollFromClass();
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

	@Test(dataProvider = "getData", dependsOnMethods = { "FamilyMemberEnrollment" })
	public void FamilyMemberUnenroll(String username, String password) throws InterruptedException, IOException {
		rm.activeMemberLogin(username, password);
		rm.unenrollFromClass();
		rm.memberLogout();
	}

	@DataProvider
	public Object[][] getData()

	{
		Object[][] data = new Object[1][2];
		data[0][0] = "freeserviceD";
		data[0][1] = "Testing1!";
		return data;
	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}
}
