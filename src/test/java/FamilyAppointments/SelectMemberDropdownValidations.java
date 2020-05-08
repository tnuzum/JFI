package FamilyAppointments;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AppointmentsPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class SelectMemberDropdownValidations extends base {

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

	@Test(priority = 1)

	public void VerifySelectMemberOptionIsPresentForHOH() throws InterruptedException, IOException {

		reusableMethods.activeMemberLogin("appthoh", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);
		// WebDriverWait wait = new WebDriverWait(driver, 30);
		AppointmentsPO ap = new AppointmentsPO(driver);

		Assert.assertTrue(ap.getSelectMember().isDisplayed());

		reusableMethods.memberLogout();
	}

	/*
	 * @AfterClass public void teardown() throws InterruptedException {
	 * driver.close(); driver = null; }
	 */

}
