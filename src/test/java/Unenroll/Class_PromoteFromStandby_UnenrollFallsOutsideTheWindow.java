package Unenroll;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
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
import org.testng.annotations.Test;

import pageObjects.CalendarPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class Class_PromoteFromStandby_UnenrollFallsOutsideTheWindow extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String ClassToEnroll = "STANDBYPROMOYESCLASS";
	private static String ClassNameDisplayed = "StandbyPromoYesClass";

	private static String YesRefundOnAccount = "This credit will be placed on your on account and be applied to your outstanding invoice.";
	private static String YesRefundOATaxInfo = "Plus applicable taxes.";
	private static String NoRefund = "This Class is non refundable";
	private static String member2 = "Standbypromo1";
	private static String member5 = "Standbyhoh";
	private static String testName = null;

	public reusableWaits rw;
	public reusableMethods rm;

	public Class_PromoteFromStandby_UnenrollFallsOutsideTheWindow() {
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
			rm.activeMemberLogin("standbyhoh", "Testing1!");

			rw.waitForDashboardLoaded();
			DashboardPO d = new DashboardPO(driver);

			d.getMyClassesScheduleButton().click();

			ClassSignUpPO c = new ClassSignUpPO(driver);
			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectTomorrowDate();

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			c.getCourseFilter().click();
			c.getCourseKeyword().click();
			c.getSearchField().sendKeys("stand");
			c.getClassApplyFilters().click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			int ClassCount = c.getClassTable().size();
			for (int j = 0; j < ClassCount; j++) {

				WebElement w = c.getClassTable().get(j);

				String ClassName = w.getText();

				if (ClassName.contains(ClassToEnroll)) {
					w.click(); // Click on the specific Class
					break;
				}
			}

			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'modal-content')]")));
			while (c.getClasslabel().getText().isBlank()) {
				Thread.sleep(500);
			}

			Assert.assertEquals(c.getClasslabel().getText(), ClassNameDisplayed); // Verifies the Class name
			int count = c.getFmlyMemberLabel().size();

			// Selects members
			for (int i = 0; i < count; i++) {

				WebElement fml = c.getFmlyMemberLabel().get(i);
				WebElement fmc = c.getFmlyMemberCheckBox().get(i);

				if (fml.getText().contains(member2))
					fml.click(); // Selects the member

				if (fml.getText().contains(member5))
					Assert.assertTrue(fmc.isSelected());

			}
			Thread.sleep(1000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", c.getPopupSignUpButton());
			Actions actions = new Actions(driver);
			actions.moveToElement(c.getPopupSignUpButton()).click().perform();

			while (c.getClassName().getText().isBlank()) {
				Thread.sleep(500);
			}

			Assert.assertTrue(c.getstandbyMessage().getText().contains("1 spot(s) left"));
			Assert.assertTrue(c.getstandbyMessage().getText().contains("Who should get it?"));

			for (int i = 0; i < c.getstandbySection().findElements(By.tagName("label")).size(); i++) {

				List<WebElement> Labels = c.getstandbySection().findElements(By.tagName("label"));
				List<WebElement> Chkbxs = c.getstandbySection().findElements(By.tagName("input"));

				if (Labels.get(i).getText().contains(member5))

					Chkbxs.get(i).click();

			}

			c.getRestOnStandby().click();

			for (int i = 0; i < c.getMemberSections().size(); i++) {

				List<WebElement> Labels = c.getMemberSections().get(i).findElements(By.tagName("label"));

				if (c.getMemberSections().get(i).getText().contains(member5)) {

					for (int j = 0; j < Labels.size(); j++) {
						if (Labels.get(j).getText().contains("Pay Single Class Fee")) {
							Labels.get(j).click();
							break;
						}
					}
				}
			}

			c.getContinueButton().click();

			Thread.sleep(3000);

			PaymentMethodsPO PM = new PaymentMethodsPO(driver);

			wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));
			PM.getPaymentButton().click();

			PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);
			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			// Navigate to Dashboard
			int count2 = driver.findElements(By.tagName("a")).size();
			for (int j = 0; j < count2; j++) {
				if (driver.findElements(By.tagName("a")).get(j).getText().equals("Dashboard"))

				{
					// rw.linksToBeClickable();
					driver.findElements(By.tagName("a")).get(j).click();
					break;
				}

			}
			rm.memberLogout();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 2)
	public void enrollAThirdMemberOnStandby() throws IOException, InterruptedException {
		try {
			rm.activeMemberLogin("standbypromo2", "Testing1!");

			DashboardPO d = new DashboardPO(driver);
			ClassSignUpPO c = new ClassSignUpPO(driver);
			PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);

			d.getMyClassesScheduleButton().click();

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectTomorrowDate();

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			c.getCourseFilter().click();
			c.getCourseKeyword().click();
			c.getSearchField().sendKeys("stand");
			c.getClassApplyFilters().click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

			rm.SelectClassOrCourseToEnroll(ClassToEnroll);

			Thread.sleep(2000);
			if (c.getPopupSignUpButton().isEnabled()) {
				c.getPopupSignUpButton().click();

			} else {
				c.getPopupCancelButton().click();
				Assert.fail("SignUp button not available");

			}
			Thread.sleep(2000);
			Assert.assertTrue(PP.getClassIsFull().getText().contains("Class is full"));
			Assert.assertTrue(PP.getStandbyQuestion().getText().contains("Would you like to be placed on Standby?"));

			PP.getGoOnStndby().click();

			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
			Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			rm.memberLogout();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 3)
	public void Unenrollself() throws InterruptedException, IOException {
		try {

			rm.activeMemberLogin("standbyhoh", "Testing1!");
			rm.myClassClickToUnenroll(ClassToEnroll);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), ClassNameDisplayed));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundOAText().getText().contains(YesRefundOnAccount));
			Assert.assertTrue(u.getRefundOAAmnt().getText().contains("$10.00"));
			Assert.assertTrue(u.getRefundOATaxInfo().getText().contains(YesRefundOATaxInfo));

			u.getUnenrollButton().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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

	@Test(priority = 4)
	public void VerifyMemberIsPromotedToEnrolled() throws InterruptedException, IOException {
		try {

			CalendarPO cp = new CalendarPO(driver);

			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class = 'btn-group']//div[contains(@class, 'btn-white')][2]")));

			rm.MyActivitiesTomorrowClick();

			int eventCount = cp.getCalEventTitles().size();

			for (int i = 0; i < eventCount; i++) {

				if (cp.getCalEventTitles().get(i).getText().contains(ClassNameDisplayed)) {

					cp.getCalEventTitles().get(i).click();
					break;
				}
			}

			Thread.sleep(1000);

			cp.getUnEnrollBtn().click();
			Thread.sleep(1000);

			UnenrollPO u = new UnenrollPO(driver);

			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), ClassNameDisplayed));

			Assert.assertFalse(rm.isElementPresent(By.xpath("//small[contains(text(),'On Standby')]")));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundOAText().getText().contains(YesRefundOnAccount));
			Assert.assertTrue(u.getRefundOAAmnt().getText().contains("$10.00"));
			Assert.assertTrue(u.getRefundOATaxInfo().getText().contains(YesRefundOATaxInfo));

			u.getUnenrollButton().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
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
		} finally {
			rm.memberLogout();
		}
	}

	@Test(priority = 5)
	public void verifyNowTheThirdMemberIsEnrolled() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("standbypromo2", "Testing1!");

			DashboardPO d = new DashboardPO(driver);

			int count = d.getClassInfoSections().size();
			for (int i = 0; i < count; i++) {

				if (d.getClassInfoSections().get(i).getText().contains(ClassToEnroll)) {
					Assert.assertFalse(d.getClassInfoSections().get(i).getText().contains("ON STANDBY"));
				}
			}

			rm.myClassClickToUnenroll(ClassNameDisplayed);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), ClassNameDisplayed));

			Assert.assertFalse(rm.isElementPresent(By.xpath("//small[contains(text(),'On Standby')]")));

			Assert.assertTrue(u.getRefundHeader().isDisplayed());
			Assert.assertTrue(u.getRefundOAText().getText().contains(YesRefundOnAccount));
			Assert.assertTrue(u.getRefundOAAmnt().getText().contains("$10.00"));
			Assert.assertTrue(u.getRefundOATaxInfo().getText().contains(YesRefundOATaxInfo));

			u.getUnenrollButton().click();

			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();

			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
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
		} finally {
			rm.memberLogout();
		}
	}

	// @AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}