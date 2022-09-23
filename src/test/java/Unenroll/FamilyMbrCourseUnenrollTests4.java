package Unenroll;

import java.io.IOException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class FamilyMbrCourseUnenrollTests4 extends base {

	private static String courseToEnroll17 = "UnenrollCourse17";
	private static int CourseStartYear = 2022;
	private static String CourseStartMonth = "Aug";
	private static String dsiredMonthYear = "August 2022";

	private static String paymentOption2 = "Pay Course Fee";
	// private static String paymentOption3 = "Buy Day Pass";
	private static String payMethod1 = "On Account";

	private static String cannotCancelMsg = "We apologize, this course is not eligible for unenrollment.";

	private static String testName = null;

	public reusableWaits rw;
	public reusableMethods rm;

	public FamilyMbrCourseUnenrollTests4() {
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

		rm.activeMemberLogin("unenrollhoh2", "Testing1!");
		rw.waitForDashboardLoaded();

	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 18, description = "class Start time in the future but unenrollment time falls inside the cannot cancel window")
	public void Unenroll_Scenario17() throws IOException, InterruptedException {

		try {

			rm.enrollFamilyMbrInCourse(courseToEnroll17, paymentOption2, payMethod1, "Not Free", CourseStartMonth,
					"Unenrollmbr18", CourseStartYear);

			rm.familyCourseClickToUnenroll(dsiredMonthYear, courseToEnroll17, "Unenrollmbr18");

			UnenrollPO u = new UnenrollPO(driver);

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), courseToEnroll17));

			Assert.assertTrue(u.getCanNotCancelFMsg().getText().contains(cannotCancelMsg));
			Assert.assertTrue(u.getCancelButton().isDisplayed());

			rm.memberLogout();
			rm.deleteEnrollInCourseInCOG(courseToEnroll17, "Jonas Sports-Plex", "Auto, Unenrollmbr18");

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
		} catch (java.lang.IndexOutOfBoundsException iobe) {
			System.out.println("index out of bounds exception");
			log.error("index out of bounds exception");
			iobe.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(iobe.getMessage(), iobe);
			iobe.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.TimeoutException te) {
			System.out.println("Time out exception");
			log.error("Time out exception");
			te.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(te.getMessage(), te);
			te.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

	}

//	@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}
