package FamilyAppointments;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class NonHOH_BookGrpAppointmentForSelf extends base {

	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training 1";
	private static String appointmentToBook = "PT 60 Mins-FamilyGrpAppointment";
	private static String resourceName1 = "Holmes, Jeff-Grp";
	private static String resourceName2 = "FitExpert1-Grp";
	private static String startTime;

	public reusableWaits rw;
	public reusableMethods rm;

	public NonHOH_BookGrpAppointmentForSelf() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

	@BeforeClass
	public void initialize() throws IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test
	public void VerifyNonHohBookGrpAppointment() throws InterruptedException, IOException {

		rm.activeMemberLogin("kidapptmbr", "Testing1!");

		rw.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		d.getMyApptsScheduleButton().click();
		Thread.sleep(2000);

		startTime = rm.BookGrpApptWith2Resources(clubName, productCategory, appointmentToBook, resourceName1,
				resourceName2);

		rm.ApptCheckinInCOG("Auto, Kidapptmbr", appointmentToBook, "kidapptmbr");
		rm.ConfirmAndCancelAppointmentNoFee(tomorrowsDate, startTime, appointmentToBook);

		rm.memberLogout();
	}

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
