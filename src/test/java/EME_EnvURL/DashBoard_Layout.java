package EME_EnvURL;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;

public class DashBoard_Layout extends base {

	public reusableMethods rm;

	public DashBoard_Layout() {
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	@Parameters({ "EMELoginPage" })
	public void initialize(String EMELoginPage) throws InterruptedException, IOException {
//		public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		// String EMELoginPage = prop.getProperty("EMELoginPage");
		driver.get(EMELoginPage);
		rm.activeMember1Login(); // Login to EME

	}

	@Test(priority = 1)
	public void VerifyHeaderSectionPresent() throws IOException, InterruptedException {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects

		// Verify the header text value
		Assert.assertTrue(d.getPageHeader().isDisplayed());
		String headerText = d.getPageHeader().getText();
		Assert.assertEquals(headerText, "Dashboard");

		// Verify My Packages button
		Assert.assertTrue(d.getMyPackagesButton().isDisplayed());
		String myPackages = d.getMyPackagesButton().getText();
		Assert.assertEquals(myPackages, "My Packages");

		// Verify Cart button is not present
		boolean cartPresent = rm.isElementPresent(By.xpath("//a[contains(text(),'Cart')]"));
		Assert.assertEquals(cartPresent, false);

		// Verify Logout button
		Assert.assertTrue(d.getLogoutButton().isDisplayed());
		String logoutButtonLabel = d.getLogoutButton().getText();
		Assert.assertEquals(logoutButtonLabel, "Log out");
	}

	@Test(priority = 2)
	public void VerifyMyAccountSection() {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects

		// My Account Section

		Assert.assertTrue(d.getMyAccountSectionLabel().isDisplayed());
		String myAccountSectionLabel = d.getMyAccountSectionLabel().getText();
		Assert.assertEquals(myAccountSectionLabel, "My Account");
		Assert.assertTrue(d.getMyAccountAccountHistory().isDisplayed());
		Assert.assertTrue(d.getMyAccountPayNow().isDisplayed());
		Assert.assertTrue(d.getMyAccountBalDuelabel().isDisplayed());
	}

	@Test(priority = 3)
	public void VerifyMyUpcomingAppointmentsSection() {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects

		// My Upcoming Appointments Section
		Assert.assertTrue(d.getMyApptsSectionLabel().isDisplayed());
		String mApptsSectionLabel = d.getMyApptsSectionLabel().getText();
		Assert.assertEquals(mApptsSectionLabel, "My Upcoming Appointments");
		Assert.assertTrue(d.getMyApptsScheduleButton().isDisplayed());
	}

	@Test(priority = 4)
	public void VerifyMyUpcomingClassesSection() {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects
		// My Upcoming Classes Section
		Assert.assertTrue(d.getMyClassesSectionLabel().isDisplayed());
		String myClassesSectionLabel = d.getMyClassesSectionLabel().getText();
		Assert.assertEquals(myClassesSectionLabel, "My Upcoming Classes");
		Assert.assertTrue(d.getMyClassesScheduleButton().isDisplayed());
	}

	@Test(priority = 5)
	public void VerifyMyUpcomingCoursesEventsSection() {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects

		// My Upcoming Courses/Events Section
		Assert.assertTrue(d.getMyCoursesEventsSectionLabel().isDisplayed());
		String myCoursesSectionLabel = d.getMyCoursesEventsSectionLabel().getText();
		Assert.assertEquals(myCoursesSectionLabel, "My Upcoming Courses / Events");
		Assert.assertTrue(d.getMyCoursesEventsScheduleButton().isDisplayed());
	}

	@Test(priority = 6)
	public void VerifyMyInfo() {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects

		// My Info Section
		Assert.assertTrue(d.getMyInfoSectionLabel().isDisplayed());
		String infoSectionLabel = d.getMyInfoSectionLabel().getText();
		Assert.assertEquals(infoSectionLabel, "My Info");
		Assert.assertTrue(d.getMyInfoMemberName().isDisplayed());
		Assert.assertTrue(d.getMyInfoAddress1().isDisplayed());
		Assert.assertTrue(d.getMyInfoAddress2().isDisplayed());
		Assert.assertTrue(d.getMyInfoEditButton().isDisplayed());
	}

	@Test(priority = 7)
	public void VerifyMyFamily() {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects

		// My Family Section

		Assert.assertTrue(d.getMyFamilySectionLabel().isDisplayed());
		String myFamilySectionLabel = d.getMyFamilySectionLabel().getText();
		Assert.assertEquals(myFamilySectionLabel, "My Family");
		Assert.assertTrue(d.getMyFamilyManageButton().isDisplayed());
		Assert.assertTrue(d.getMyFamilyMemberCount().isDisplayed());
	}

	@Test(priority = 8)
	public void VerifyVisitsSection() {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects

		// Visits By Month Section
		Assert.assertTrue(d.getMyVisitsSectionLabel().isDisplayed());
		String visitsSectionLabel = d.getMyVisitsSectionLabel().getText();
		Assert.assertEquals(visitsSectionLabel, "Visits By Month");
	}

	@Test(priority = 9)
	public void VerifyAdditionalLinksSection() {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects

		// Additional Links
		Assert.assertTrue(d.getAdditionalLinksSectionLabel().isDisplayed());
		String additionalLinksSectionLabel = d.getAdditionalLinksSectionLabel().getText();
		Assert.assertEquals(additionalLinksSectionLabel, "Additional Links");
	}

	@Test(priority = 10)
	public void VerifyPrinacyPolicyLinkPresent() {

		DashboardPO d = new DashboardPO(driver); // Define the driver for Dash Board page Objects

//			Privacy Policy Label and Link Present

		Assert.assertTrue(d.getPrivacyAndSecurityLabel().isDisplayed());
		Assert.assertTrue(d.getPrivacyPolicyLink().isDisplayed());

	}

//			@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}
