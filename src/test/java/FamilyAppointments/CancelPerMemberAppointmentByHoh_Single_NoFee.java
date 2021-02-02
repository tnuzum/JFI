package FamilyAppointments;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AppointmentsPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class CancelPerMemberAppointmentByHoh_Single_NoFee extends base {

	private static String clubName = "Jonas Fitness";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook = "PerMbrNoFees";
	private static String resourceName1 = "";
	private static String resourceName2 = "PT.Ramakers, Tanya";
	private static String startTime;
	private static AppointmentsPO ap;
	private static String familyMember = "Auto, Kidapptmbr";
	private static String familyMemberFirstName = "Kidapptmbr";

	public reusableWaits rw;
	public reusableMethods rm;

	public CancelPerMemberAppointmentByHoh_Single_NoFee() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

	@BeforeClass
	public void initialize() throws IOException {
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

		ap = new AppointmentsPO(driver);
	}

	@Test
	public void BookAppointmentForFamilyMember() throws InterruptedException, IOException {

		try {
			rm.activeMemberLogin("appthoh", "Testing1!");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			rw.waitForDashboardLoaded();
			DashboardPO d = new DashboardPO(driver);
			jse.executeScript("arguments[0].click();", d.getMyApptsScheduleButton());
			Thread.sleep(2000);

			Select s = new Select(ap.getSelectMember());
			List<WebElement> Members = s.getOptions();
			int count = Members.size();
			for (int i = 0; i < count; i++) {
				String member = Members.get(i).getText();

				if (member.equals(familyMember)) {
					s.selectByVisibleText(member);
					break;
				}
			}

			startTime = rm.BookApptWith2Resources(clubName, productCategory, appointmentToBook, resourceName1,
					resourceName2);

			rm.cancelAppointmentFromListViewByHohNoFee(tomorrowsDate, startTime, appointmentToBook,
					familyMemberFirstName);
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

	@AfterClass(enabled = true)
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}
}
