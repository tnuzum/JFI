package FamilyAppointments;

import java.io.IOException;
import java.util.List;

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

public class HOH_BookGrpAppointmentForFamilyMember_NoPackage extends base {
	private static String clubName = "Jonas Fitness";
	private static String productCategory = "Personal Training 1";
	private static String appointmentToBook = "PT 60 Mins-FamilyGrpAppointment";
	private static String resourceName1 = "PT.Smith, Andrew-Grp";
	private static String resourceName2 = "FitExpert1-Grp";
	private static String startTime;
	private static AppointmentsPO ap;

	public reusableWaits rw;
	public reusableMethods rm;

	public HOH_BookGrpAppointmentForFamilyMember_NoPackage() {
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
		driver.get(prop.getProperty("EMELoginPage"));

		ap = new AppointmentsPO(driver);
	}

	@Test
	public void BookGrpAppointmentForFamilyMember() throws InterruptedException, IOException {

		rm.activeMemberLogin("appthoh", "Testing1!");
		rw.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		d.getMyApptsScheduleButton().click();
		Thread.sleep(2000);

		Select s = new Select(ap.getSelectMember());
		List<WebElement> Members = s.getOptions();
		int count = Members.size();
		for (int i = 0; i < count; i++) {
			String member = Members.get(i).getText();

			if (member.equals("Auto, Fmlyapptmbr")) {
				s.selectByVisibleText(member);
				break;
			}
		}

		startTime = rm.BookGrpApptWith2Resources(clubName, productCategory, appointmentToBook, resourceName1,
				resourceName2, "Donald");
		rm.memberLogout();
		rm.activeMemberLogin("fmlyapptmbr", "Testing1!");
		rm.ConfirmAndCancelAppointmentNoFee(tomorrowsDate, startTime, appointmentToBook);
		rm.memberLogout();

	}

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
