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
import resources.Base;
import resources.reusableMethods;
import resources.reusableWaits;

public class HOH_BookAppointmentForFamilyMember_NoPackage extends Base {

	private static String clubName = "Jonas Fitness";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook = "PT 60 Mins-FamilyAppointment";
	private static String resourceName1 = "PT.Smith, Andrew";
	private static String resourceName2 = "FitExpert1";
	private static String startTime;
	private static AppointmentsPO ap;

	@BeforeClass
	public void initialize() throws IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));

		ap = new AppointmentsPO(driver);
	}

	@Test
	public void BookAppointmentForFamilyMember() throws InterruptedException, IOException {

		reusableMethods.activeMemberLogin("appthoh", "Testing1!");
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		d.getMyApptsScheduleButton().click();
		Thread.sleep(2000);

		Select s = new Select(ap.getSelectMember());
		List<WebElement> Members = s.getOptions();
		int count = Members.size();
		for (int i = 0; i < count; i++) {
			String member = Members.get(i).getText();

			if (member.equals("Auto, Kidapptmbr")) {
				s.selectByVisibleText(member);
				break;
			}
		}

		startTime = reusableMethods.BookApptWith2Resources(clubName, productCategory, appointmentToBook, resourceName1,
				resourceName2);
		reusableMethods.memberLogout();
		reusableMethods.activeMemberLogin("kidapptmbr", "Testing1!");
		reusableMethods.ConfirmAndCancelAppointmentNoFee(tomorrowsDate, startTime, appointmentToBook);
		reusableMethods.memberLogout();

	}

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
