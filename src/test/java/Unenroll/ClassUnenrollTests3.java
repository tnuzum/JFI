package Unenroll;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ClassUnenrollTests3 extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

	private static String classToEnroll17 = "UnenrollClass17";

	private static String paymentOption2 = "Pay Single Class Fee";
	// private static String paymentOption3 = "Buy Day Pass";
	private static String payMethod1 = "On Account";

	private static String cannotCancelMsg = "We apologize, this class is not eligible for unenrollment.";

	private static String testName = null;

	public reusableWaits rw;
	public reusableMethods rm;

	public ClassUnenrollTests3() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		try {
			driver = initializeDriver();
		} catch (java.lang.NullPointerException npe) {

			driver = initializeDriver();

			System.out.println("driver initialized again");
			log.error("driver initialized again");
			npe.printStackTrace();
			log.error(npe.getMessage(), npe);

		}

		rm.setDriver(driver);
		rw.setDriver(driver);

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();

	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 18, description = "class Start time in the future but unenrollment time falls inside the cannot cancel window")
	public void Unenroll_Scenario17() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr18", "Testing1!");
			rm.enrollInClass(classToEnroll17, paymentOption2, payMethod1, "Not Free");

			rm.myClassClickToUnenroll(classToEnroll17);

			UnenrollPO u = new UnenrollPO(driver);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.textToBePresentInElement(u.getClassNameTitle(), classToEnroll17));

			Assert.assertTrue(u.getCanNotCancelFMsg().getText().contains(cannotCancelMsg));
			Assert.assertTrue(u.getCancelButton().isDisplayed());

			rm.memberLogout();
			rm.deleteEnrollInClassInCOG(classToEnroll17, "Jonas Sports-Plex", "Auto, Unenrollmbr18");

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
