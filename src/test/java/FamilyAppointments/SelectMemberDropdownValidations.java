package FamilyAppointments;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AppointmentsPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class SelectMemberDropdownValidations extends base {

	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName1 = "Jonas Sports-Plex";
	private static String clubName2 = "Studio Jonas";
	private static String productCategory1 = "Spa";
	private static String productCategory2 = "Personal Training";
	private static String appointmentToBook = "PT 60 Mins-FamilyAppointment";
	private static String resourceName1 = "PT Smith, Andrew";
	private static String resourceName2 = "FitExpert1";
	private static String familyMbrName = "Auto, Kidapptmbr";
	private static Select se;
	private static Select s2;
	private static List<WebElement> Clubs;
	private static List<WebElement> ProductCategories;
	private static AppointmentsPO ap;

	public reusableWaits rw;
	public reusableMethods rm;

	public SelectMemberDropdownValidations() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

	@BeforeClass
	public void initialize() throws IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		driver.get(prop.getProperty("EMELoginPage"));

		ap = new AppointmentsPO(driver);
	}

	@Test(priority = 1)

	public void VerifySelectMemberOptionIsPresentForHOH() throws InterruptedException, IOException {

		rm.activeMemberLogin("appthoh", "Testing1!");
		rw.waitForDashboardLoaded();
		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);

		Assert.assertTrue(ap.getSelectMember().isDisplayed());

		rm.memberLogout();
	}

	@Test(priority = 2)

	public void VerifySelectMemberOptionIsNotPresentForHOHWithNoFamilyMember()
			throws InterruptedException, IOException {

		rm.activeMemberLogin("apptmember10", "Testing1!");
		rw.waitForDashboardLoaded();
		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);
		boolean SelectMemberDropdownPresent = rm.isElementPresent(By.xpath("//select[@name='familySelect']"));
		Assert.assertEquals(SelectMemberDropdownPresent, false);

		rm.memberLogout();

	}

	@Test(priority = 3)

	public void VerifySelectMemberOptionIsNotPresentForNonHOH() throws InterruptedException, IOException {

		rm.activeMemberLogin("fmlyapptmbr", "Testing1!");
		rw.waitForDashboardLoaded();
		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);
		boolean SelectMemberDropdownPresent = rm.isElementPresent(By.xpath("//select[@name='familySelect']"));
		Assert.assertEquals(SelectMemberDropdownPresent, false);

		rm.memberLogout();

	}

	@Test(priority = 4)

	public void VerifySelectMemberOptionIsNotPresentForHOHWhenClubDoesntAllow()
			throws InterruptedException, IOException {

		rm.activeMemberLogin("noccmember", "Testing1!");
		rw.waitForDashboardLoaded();
		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);
		boolean SelectMemberDropdownPresent = rm.isElementPresent(By.xpath("//select[@name='familySelect']"));
		Assert.assertEquals(SelectMemberDropdownPresent, false);

		rm.memberLogout();

	}

	@SuppressWarnings("unlikely-arg-type")
	@Test(priority = 5)

	public void VerifyOnlyAllowedClubsDisplayedForFamilyMember() throws InterruptedException, IOException {

		rm.activeMemberLogin("appthoh", "Testing1!");
		rw.waitForDashboardLoaded();
		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);

		Assert.assertTrue(ap.getSelectMember().isDisplayed());

		Select s = new Select(ap.getSelectMember());
		List<WebElement> Members = s.getOptions();
		int count = Members.size();
		for (int i = 0; i < count; i++) {
			String member = Members.get(i).getText();

			if (member.equals(familyMbrName)) {
				s.selectByVisibleText(member);
				break;
			}
		}
		Thread.sleep(2000);
		se = new Select(ap.getclubs());
		Clubs = se.getOptions();

		Assert.assertFalse(Clubs.contains(clubName1));
		Assert.assertEquals(se.getFirstSelectedOption().getText(), clubName2);

//		rm.memberLogout();

	}

	@SuppressWarnings("unlikely-arg-type")
	@Test(priority = 6, dependsOnMethods = { "VerifyOnlyAllowedClubsDisplayedForFamilyMember" })

	public void VerifyOnlyAllowedCategoriesDisplayedForFamilyMember() throws InterruptedException, IOException {

		WebElement bic = ap.getBookableItemCategory();

		s2 = new Select(bic);
		ProductCategories = s2.getOptions();

		Assert.assertFalse(ProductCategories.contains(productCategory1));

	}

	@Test(priority = 7, dependsOnMethods = { "VerifyOnlyAllowedCategoriesDisplayedForFamilyMember" })

	public void VerifyPackagePriceForFamilyMember() throws InterruptedException, IOException {

		int count = ProductCategories.size();
		System.out.println(count);

		for (int i = 0; i < count; i++) {
			String category = ProductCategories.get(i).getText();

			if (category.equals(productCategory2)) {
				s2.selectByVisibleText(category);
				break;
			}
		}

		Select s3 = new Select(ap.getBookableItem());
		Thread.sleep(2000);
		List<WebElement> Products = s3.getOptions();

		int count2 = Products.size();
		System.out.println(count2);

		for (int j = 0; j < count2; j++) {
			String product = Products.get(j).getText();

			if (product.equals(appointmentToBook)) {
				s3.selectByVisibleText(product);
				break;
			}
		}
		Thread.sleep(2000);
		WebElement rt = ap.getResourceType();

		Select s4 = new Select(rt);

		List<WebElement> Resources = s4.getOptions();

		int count3 = Resources.size();
		System.out.println(count3);

		for (int k = 0; k < count3; k++) {
			String resource = Resources.get(k).getText();

			if (resource.equals(resourceName1)) {
				s4.selectByVisibleText(resource);
				break;
			}
		}
		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");
		Thread.sleep(2000);

		String classtext = ap.getCalendarTomorrow().getAttribute("class");

		if (classtext.contains("cal-out-month")) {
			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting");
				Thread.sleep(1000);
			}
			System.out.println("came out of the loop");
		}

		ap.getCalendarTomorrow().click();
		Thread.sleep(3000);

		for (int i = 0; i < ap.getApptBox().size(); i++) {
			String bookName = ap.getApptBox().get(i).getText();
			if (bookName.contains(resourceName2)) {
				List<WebElement> TimeSlots = ap.getTimeSlotContainers().get(i).findElements(By.tagName("a"));
				WebElement MorningSlot = TimeSlots.get(0);
				WebDriverWait wait = new WebDriverWait(driver, 30);
				wait.until(ExpectedConditions.elementToBeClickable(MorningSlot));
				while (!MorningSlot.isEnabled())// while button is NOT(!) enabled
				{
					System.out.println("Waiting for available times");
				}

				MorningSlot.click();

				WebElement MorningAvailableTimeContainer = ap.getTimeSlotContainers().get(i)
						.findElement(By.id("tab-1-0"));
				List<WebElement> MorningAvailableTimes = MorningAvailableTimeContainer
						.findElements(By.tagName("button"));
				WebElement firstAvailableTimeMorning = MorningAvailableTimes.get(0);
//					while (!st2.isEnabled())//while button is NOT(!) enabled
//					{
//					Thread.sleep(200);
//					}

				wait.until(ExpectedConditions.elementToBeClickable(firstAvailableTimeMorning));
				String startTime = firstAvailableTimeMorning.getText();
				System.out.println(startTime);
				firstAvailableTimeMorning.click();
				break;
			}
		}
		Thread.sleep(2000);

		ap.getPopup1BookButton().click();
		Thread.sleep(2000);
		Select s5 = new Select(
				driver.findElement(By.xpath("//select[contains(@class, 'at-appointments-checkout-dropdown')]")));
		List<WebElement> UnitRates = s5.getOptions();
		String unitRate = UnitRates.get(0).getText();
		Assert.assertEquals(unitRate, "1 - $1.75/per");

		Assert.assertTrue(ap.getForMember().getText().contains(familyMbrName));

	}

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
