package SingleMemberAppointments;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AppointmentsPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ClubReqPackages_BookAppt_NonMSS extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook = "PT 60 Mins-NonMSS";

	public reusableMethods rm;
	public reusableWaits rw;

	public ClubReqPackages_BookAppt_NonMSS() {

		rm = new reusableMethods();
		rw = new reusableWaits();

	}

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
	}

	@Test(priority = 1)
	public void VerifyMessage() throws IOException, InterruptedException {

		try {
			rm.activeMemberLogin("ccmember", "Testing1!");
			rw.waitForDashboardLoaded();

			DashboardPO p = new DashboardPO(driver);
			p.getMyApptsScheduleButton().click();
			Thread.sleep(2000);

			rm.catchErrorMessage();

			AppointmentsPO ap = new AppointmentsPO(driver);

			Select s = new Select(ap.getclubs());
			List<WebElement> Clubs = s.getOptions();

			int x = 0;
			while (!ap.getclubs().isEnabled() && x < 100) {
				System.out.println("Waiting for Clubs drop down to not be blank");
				x++;
			}

			int count0 = Clubs.size();
			System.out.println("1 " + count0);

			for (int i = 0; i < count0; i++) {
				String club = Clubs.get(i).getText();

				if (club.equals(clubName)) {
					s.selectByVisibleText(club);
					break;
				}
			}

			WebElement bic = ap.getBookableItemCategory();

			Thread.sleep(2000);

			Select s1 = new Select(bic);
			List<WebElement> ProductCategories = s1.getOptions();

			int count = ProductCategories.size();
			System.out.println("2 " + count);

			for (int i = 0; i < count; i++) {
				String category = ProductCategories.get(i).getText();

				if (category.equals(productCategory)) {
					s1.selectByVisibleText(category);
					break;
				}
			}

			Select s2 = new Select(ap.getBookableItem());
			// Thread.sleep(2000);

			while (!ap.getBookableItem().isEnabled()) {
				System.out.println("Waiting for Product drop down to not be blank");
			}
			List<WebElement> Products = s2.getOptions();

			int count1 = Products.size();
			System.out.println(count1);

			for (int j = 0; j < count1; j++) {
				String product = Products.get(j).getText();

				if (product.equals(appointmentToBook)) {
					s2.selectByVisibleText(product);
					break;
				}
			}

			/*
			 * WebElement rt = ap.getResourceType();
			 * 
			 * while (!rt.isEnabled())// while button is NOT(!) enabled {
			 * System.out.println("Waiting for Resource drop down to not be blank"); }
			 * Select s3 = new Select(rt); // Thread.sleep(2000); List<WebElement> Resources
			 * = s3.getOptions();
			 * 
			 * int count2 = Resources.size(); System.out.println(count2);
			 * 
			 * for (int k = 0; k < count2; k++) { String resource =
			 * Resources.get(k).getText();
			 * 
			 * if (resource.equals(resourceName)) { s3.selectByVisibleText(resource); break;
			 * } }
			 */

//		Thread.sleep(4000);

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");
			Thread.sleep(2000);

			Assert.assertEquals(ap.getPopup1Content().getText(),
					"This appointment requires the purchase of a package, but this package cannot be purchased online. Please call the club to purchase the package.");
			ap.getPopup2OKButton().click();
			rm.returnToDashboard();
			rm.memberLogout();

		} catch (Exception e) {
			log.error(e.getMessage(), e);e.printStackTrace();
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		} catch (java.lang.AssertionError ae) {
			log.error(ae.getMessage(), ae);ae. printStackTrace();
			log.error("Appointment is not booked");
			getScreenshot(this.getClass().getSimpleName(), driver);
		}

	}
	// @AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}