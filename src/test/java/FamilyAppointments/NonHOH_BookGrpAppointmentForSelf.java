package FamilyAppointments;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class NonHOH_BookGrpAppointmentForSelf extends base {

	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training 1";
	private static String appointmentToBook = "PT 60 Mins-FamilyGrpAppointment";
	private static String resourceName1 = "Holmes, Jeff";
	private static String resourceName2 = "FitExpert1";
	private static String startTime;

	@BeforeClass
	public void initialize() throws IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test
	public void VerifyNonHohBookAppointment() throws InterruptedException, IOException {

		reusableMethods.activeMemberLogin("kidapptmbr", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
		startTime = reusableMethods.BookGrpApptWith2Resources(clubName, productCategory, appointmentToBook,
				resourceName1, resourceName2);

		reusableMethods.ApptCheckinInCOG("Auto, Kidapptmbr", appointmentToBook, "kidapptmbr");
		reusableMethods.ConfirmAndCancelAppointmentNoFee(tomorrowsDate, startTime, appointmentToBook);

		reusableMethods.memberLogout();
	}

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
