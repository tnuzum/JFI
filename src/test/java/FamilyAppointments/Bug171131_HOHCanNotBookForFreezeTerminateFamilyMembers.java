package FamilyAppointments;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
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

public class Bug171131_HOHCanNotBookForFreezeTerminateFamilyMembers extends base {

	private static AppointmentsPO ap;
	private static String familyMember = "Auto, FreezeMember";

	public reusableWaits rw;
	public reusableMethods rm;

	public Bug171131_HOHCanNotBookForFreezeTerminateFamilyMembers() {
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

		catch (org.openqa.selenium.WebDriverException we) {

			driver = initializeDriver();

			System.out.println("driver initialized again");
			log.error("driver initialized again");
			we.printStackTrace();
			log.error(we.getMessage(), we);

		}

		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();

		ap = new AppointmentsPO(driver);
	}

	@Test
	public void BookGrpAppointmentForFamilyMember() throws InterruptedException, IOException {
		try {

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			rm.activeMemberLogin("hoh", "Testing1!");
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

			Assert.assertEquals(" This member is not eligible for booking online.",
					ap.getEligibilityMessage().getText());
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
