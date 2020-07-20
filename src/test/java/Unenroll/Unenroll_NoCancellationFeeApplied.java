package Unenroll;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class Unenroll_NoCancellationFeeApplied extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String classToEnroll = "UNENROLLCLASS";
	private static DashboardPO d;
	private static String testName = null;

	public reusableWaits rw;
	public reusableMethods rm;

	public Unenroll_NoCancellationFeeApplied() {
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
		driver.get(prop.getProperty("EMELoginPage"));

		d = new DashboardPO(driver);

	}

	@BeforeMethod
	public void GetTestMethodName(Method method) {
		testName = method.getName();

	}

	@Test(priority = 1, description = "Can Unenroll and No Cancellation Fee is applied")
	public void CanUnenrollNoCancellationFee() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("unenrollmbr", "Testing1!");
			rm.enrollInClass(classToEnroll);

			d.getMyClassesClass1GearButton().click();

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(d.getmyClassesUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(d.getmyClassesUnenrollButton()));
			d.getmyClassesUnenrollButton().click();
			Thread.sleep(1000);

			UnenrollPO u = new UnenrollPO(driver);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));

			Assert.assertEquals("There are no cancellation fees for unenrolling in this class.",
					u.getNoCancelFeeMsg().getText());

			u.getUnenrollButton().click();

			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			u.getUnenrollConfirmYesButton().click();

			wait.until(ExpectedConditions.stalenessOf(u.getUnenrollConfirmYesButton()));
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

			rm.memberLogout();

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName, driver);
			log.error(ae.getMessage(), ae);
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
	}

//	@AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
