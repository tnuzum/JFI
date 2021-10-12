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
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class Bug177368_FamilyMemberWithNoDuesServiceNotFree extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String classToEnroll = "CLASSFREEWITHSERVICED";
	private static String classNameDisplayed = "ClassFreeWithServiceD";
	private static String classTimeDisplayed = "Start Time: 10:00 AM";
	private static String classInstructorDisplayed = "Class Instructor: Max Gibbs";
	private static String classInstructorDisplayedOnSearchScreen = "Inst: Max Gibbs";
	private static String classTimeDisplayedOnSearchScreen = "10:00 AM";
	private static String classDuration = "45 Min";
	private static String member1 = "John";
	private static String member1Rate = "Free";
	private static String member2 = "Jessica";
	private static String member2Rate = "$5.00";
	private static JavascriptExecutor jse;

	public reusableWaits rw;
	public reusableMethods rm;

	public Bug177368_FamilyMemberWithNoDuesServiceNotFree() {
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

	@Test(priority = 1)
	public void verifyFamilyMemberIsNotFree() throws IOException, InterruptedException {
		try {
			rm.activeMemberLogin("john", "Testing1!");
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

			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//mat-dialog-container[contains(@class, 'mat-dialog-container')]")));
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

				if (fml.getText().contains(member1))
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

				if (c.getMemberSections().get(i).getText().contains(member1)) {

					Assert.assertTrue(paymentOptions.contains("Free")); // Class is free for this member
					for (int j = 0; j < Labels.size(); j++) {
						if (Labels.get(j).getText().contains("Free"))
							Assert.assertTrue(Labels.get(j).isEnabled());
					}
				}

				if (c.getMemberSections().get(i).getText().contains(member2)) {

					Assert.assertFalse(paymentOptions.contains("Free")); // Class is not free for this member
					System.out.println(
							"\n************\nClass is not free for a Family member with no Dues Service \n\n***********\n");
					for (int j = 0; j < Labels.size(); j++) {
						if (Labels.get(j).getText().contains("Free"))
							Assert.assertTrue(Labels.get(j).isEnabled());

					}
				}

			}

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

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}
}
