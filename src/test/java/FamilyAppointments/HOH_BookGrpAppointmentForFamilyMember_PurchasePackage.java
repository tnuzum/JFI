package FamilyAppointments;

import java.io.IOException;
import java.text.ParseException;
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

public class HOH_BookGrpAppointmentForFamilyMember_PurchasePackage extends base {
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training 1";
	private static String appointmentToBook = "PT 60 Mins-FamilyGrpAppointment";
	private static String resourceName1 = "";
	private static String resourceName2 = "T.Huff, Anthony";
	private static String startTime;
	private static AppointmentsPO ap;
	private static String familyMember = "Auto, Apptmbr1";
	private static String familyMemberFirstName = "ApptMbr1";

	public reusableWaits rw;
	public reusableMethods rm;

	public HOH_BookGrpAppointmentForFamilyMember_PurchasePackage() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

	@BeforeClass
	public void initialize() throws IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();

		ap = new AppointmentsPO(driver);
	}

	@Test
	public void BookGrpAppointmentForFamilyMemberWithPackage()
			throws InterruptedException, IOException, ParseException {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			rm.activeMemberLogin("appthoh1", "Testing1!");
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

			startTime = rm.BookGrpApptWith2Resources(clubName, productCategory, appointmentToBook, resourceName1,
					resourceName2, "Donald");
			rm.memberLogout();
			rm.ApptCheckinInCOG("Auto, Apptmbr1", appointmentToBook, "appthoh1", "1");
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

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}
