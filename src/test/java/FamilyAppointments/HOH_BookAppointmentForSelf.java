package FamilyAppointments;

import java.io.IOException;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AppointmentsPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class HOH_BookAppointmentForSelf extends base {

	private static String clubName = "Jonas Fitness";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook = "PT 60 Mins-FamilyAppointment";
	private static String resourceName1 = "PT.Smith, Andrew";
	private static String resourceName2 = "FitExpert1";
	private static String startTime;

	@BeforeClass
	public void initialize() throws IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));

	}

	@Test
	public void HohBookAppointmentForSelf() throws InterruptedException, IOException {

		reusableMethods.activeMemberLogin("appthoh", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		d.getMyApptsScheduleButton().click();
		Thread.sleep(2000);

		AppointmentsPO ap = new AppointmentsPO(driver);

		Assert.assertTrue(ap.getSelectMember().isDisplayed());

		startTime = reusableMethods.BookApptWith2Resources(clubName, productCategory, appointmentToBook, resourceName1,
				resourceName2);

		reusableMethods.ConfirmAndCancelAppointmentNoFee(tomorrowsDate, startTime, appointmentToBook);
		reusableMethods.memberLogout();

	}

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}