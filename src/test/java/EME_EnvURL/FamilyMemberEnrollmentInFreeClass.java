package EME_EnvURL;

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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class FamilyMemberEnrollmentInFreeClass extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String classToEnroll = "FREE CLASS AUTO";
	private static String classNameDisplayed = "Free Class Auto";
	private static String classTimeDisplayed = "Start Time: 10:00 AM";
	private static String classInstructorDisplayed = "Class Instructor: ";
	private static String classInstructorDisplayedOnSearchScreen = "Inst: ";
	private static String classTimeDisplayedOnSearchScreen = "10:00 AM";
	private static String classDuration = "60 Min";
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
	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public FamilyMemberEnrollmentInFreeClass() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	@Parameters({ "EMELoginPage" })
	public void initialize(String EMELoginPage) throws InterruptedException, IOException {
//		public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		jse = (JavascriptExecutor) driver;
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		// String EMELoginPage = prop.getProperty("EMELoginPage");
		driver.get(EMELoginPage);

	}

	@Test(priority = 1, description = "Family Member Enrollment")
	public void FamilyEnrollInFreeClass() throws IOException, InterruptedException {
		rm.activeMemberLogin("hoh", "Testing1!");
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
		c.getSearchField().sendKeys("free");
		Thread.sleep(2000);
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

				w.click(); // Click on the specific class
				break;
			}
		}

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'modal-content')]")));
		while (c.getClasslabel().getText().isBlank()) {
			Thread.sleep(500);
		}

		Assert.assertEquals(c.getClasslabel().getText(), classNameDisplayed); // Verifies the class name
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
		Thread.sleep(1000);
		Actions actions = new Actions(driver);
		actions.moveToElement(c.getPopupSignUpButton()).click().perform();

		while (c.getClassName().getText().isBlank()) {
			Thread.sleep(500);
		}

		Assert.assertEquals(classNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(classTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(classInstructorDisplayed, c.getClassInstructor().getText());
		Assert.assertEquals("Date: " + tomorrowsDate, c.getClassDate().getText());

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
		String receiptNumber1 = null;

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
		wait.until(
				ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[(contains@class, 'swal2-center')]")));
		jse.executeScript("arguments[0].click();", dp.getMyAccountAccountHistory());

		AcctHistoryPO ahp = new AcctHistoryPO(driver);

		while (ahp.getSearchingAcctHistMessage().size() != 0) {
			System.out.println("waiting for account history to display");
			Thread.sleep(1000);
		}
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("accountHistory")));

		/*
		 * while(!ahp.getReceiptNumberTable().isDisplayed()) { Thread.sleep(2000);
		 * System.out.println("waiting"); }
		 */

		// Clicks on the Receiptnumber in Account History

		ahp.getSearchField().sendKeys(receiptNumber);
		Thread.sleep(2000);
		wait.until(ExpectedConditions.textToBePresentInElement(ahp.getReceiptNumber(), receiptNumber));
		jse.executeScript("arguments[0].click();", ahp.getReceiptNumbers().get(0));
		Thread.sleep(2000);

		jse.executeScript("arguments[0].scrollIntoView(true);",
				TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-12 text-right']")));
		// Verifies the Invoice amount is $0.00
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-12 text-right']")).getText()
				.contains("$0.00"));
		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
		Thread.sleep(1000);
		rm.returnToDashboard();
		rm.unenrollFromClass();
		rm.memberLogout();
	}

	@Test(dataProvider = "getData", dependsOnMethods = { "FamilyEnrollInFreeClass" })
	public void FamilyMemberUnenroll(String username, String password) throws InterruptedException, IOException {
		rm.activeMemberLogin(username, password);
		rm.unenrollFromClass();
		rm.memberLogout();
	}

	@DataProvider
	public Object[][] getData()

	{
		Object[][] data = new Object[3][2];
		data[0][0] = "freemember";
		data[0][1] = "Testing1!";
		data[1][0] = "feemember";
		data[1][1] = "Testing1!";
		data[2][0] = "memberwithpunch";
		data[2][1] = "Testing1!";
		return data;
	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}
}
