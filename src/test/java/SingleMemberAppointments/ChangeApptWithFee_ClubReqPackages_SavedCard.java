package SingleMemberAppointments;

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
import pageObjects.PaymentMethodsPO;
import pageObjects.ThankYouPO;
import resources.Base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ChangeApptWithFee_ClubReqPackages_SavedCard extends Base {
	private static Logger log = LogManager.getLogger(Base.class.getName());
	private static String clubName1 = "Jonas Fitness";
	private static String clubName2 = "Studio Jonas";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook1 = "PT 60 Mins-ChangeWithFee1";
	private static String appointmentToBook2 = "PT 60 Mins-ChangeWithFee2";
	private static String resourceName1 = "FitExpert1";
	private static String resourceName2 = "PT.Shepard, Elliana";
	private static String resourceName3 = "FitExpert2";
	private static String resourceName4 = "PT Smith, Andrew";
	private static String unitsToBeSelected = "1 - $5.00/per";
	private static String startTime1;
	private static String startTime2;

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1)
	public void ChangeAppointmentWithFee() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("apptmember11", "Testing1!");

		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		d.getMyApptsScheduleButton().click();
		Thread.sleep(2000);

		// Book an appointment and get the start time for the appointment
		startTime1 = reusableMethods.BookApptWith2Resources(clubName1, productCategory, appointmentToBook1,
				resourceName1, resourceName2);

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
		int appointmentsCount = d.getMyAppts().size();

		for (int i = 0; i < appointmentsCount; i++) {
			if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

			{

				if (d.getMyAppts().get(i).getText().contains(startTime1)) {

					Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook1.toUpperCase()));
					wait.until(ExpectedConditions
							.elementToBeClickable(d.getMyAppts().get(i).findElement(By.tagName("i"))));
					d.getMyAppts().get(i).findElement(By.tagName("i")).click();

					WebElement EditButton = d.getEditButton().get(i);

					wait.until(ExpectedConditions.visibilityOf(EditButton));
					wait.until(ExpectedConditions.elementToBeClickable(EditButton));

					EditButton.click();
					break;
				}
			}
		}

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
		Thread.sleep(2000);
		AppointmentsPO ap = new AppointmentsPO(driver);
		ap.getEditApptChangeButton().click();
		Thread.sleep(1000);
		Assert.assertTrue(ap.getCancelFeeSection().getText().contains("There is a fee for changing this appointment."));
		Assert.assertTrue(ap.getCancelFeeSection().getText().contains("If you proceed, you will be charged a fee of:"));
		ap.getEditApptProceedButton1().click();

		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");
		Thread.sleep(2000);

		Select se = new Select(ap.getclubs());
		List<WebElement> Clubs = se.getOptions();

		while (!ap.getclubs().isEnabled()) {
			System.out.println("Waiting for Clubs drop down to not be blank");
		}

		int count0 = Clubs.size();
		System.out.println("1 " + count0);

		for (int i = 0; i < count0; i++) {
			String club = Clubs.get(i).getText();

			if (club.equals(clubName2)) {
				se.selectByVisibleText(club);
				break;
			}
		}
		Thread.sleep(2000);

		WebElement bic = ap.getBookableItemCategory();

		Select s = new Select(bic);
		List<WebElement> ProductCategories = s.getOptions();

		int count = ProductCategories.size();
		System.out.println(count);

		for (int i = 0; i < count; i++) {
			String category = ProductCategories.get(i).getText();

			if (category.equals(productCategory)) {
				s.selectByVisibleText(category);
				break;
			}
		}

		Select s1 = new Select(ap.getBookableItem());
		Thread.sleep(2000);
		List<WebElement> Products = s1.getOptions();

		int count1 = Products.size();
		System.out.println(count1);

		for (int j = 0; j < count1; j++) {
			String product = Products.get(j).getText();

			if (product.equals(appointmentToBook2)) {
				s1.selectByVisibleText(product);
				break;
			}
		}

		WebElement rt = ap.getResourceType();

		Select s2 = new Select(rt);
		Thread.sleep(2000);
		List<WebElement> Resources = s2.getOptions();

		int count2 = Resources.size();
		System.out.println(count2);

		for (int k = 0; k < count2; k++) {
			String resource = Resources.get(k).getText();

			if (resource.equals(resourceName3)) {
				s2.selectByVisibleText(resource);
				break;
			}
		}
		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");

		String classtext = ap.getCalendarDayAfterTomorrow().getAttribute("class");

		if (classtext.contains("cal-out-month")) {
			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");

		}

		ap.getCalendarDayAfterTomorrow().click();

		for (int m = 0; m < ap.getApptBox().size(); m++) {
			String bookName = ap.getApptBox().get(m).getText();
			if (bookName.contains(resourceName4)) {
				List<WebElement> TimeSlots = ap.getTimeSlotContainers().get(m).findElements(By.tagName("a"));
				WebElement AftrnunSlot = TimeSlots.get(1);
				wait.until(ExpectedConditions.elementToBeClickable(AftrnunSlot));
				while (!AftrnunSlot.isEnabled())// while button is NOT(!) enabled
				{
					System.out.println("Waiting for available times");
				}

				AftrnunSlot.click();
				Thread.sleep(1000);
				WebElement AftrenoonAvailableTimeContainer = ap.getTimeSlotContainers().get(m)
						.findElement(By.id("tab-2-1"));
				List<WebElement> AftrenoonAvailableTimes = AftrenoonAvailableTimeContainer
						.findElements(By.tagName("button"));
				WebElement secondAvailableTimeAfternoon = AftrenoonAvailableTimes.get(1);
//					while (!st2.isEnabled())//while button is NOT(!) enabled
//					{
//					Thread.sleep(200);
//					}

				wait.until(ExpectedConditions.elementToBeClickable(secondAvailableTimeAfternoon));
				startTime2 = secondAvailableTimeAfternoon.getText();
				System.out.println(startTime2);
				secondAvailableTimeAfternoon.click();
				break;
			}
		}
		Thread.sleep(2000);

		System.out.println(ap.getPopup1Content().getText());
		System.out.println("Time: " + dayAfter + " " + startTime2);
		System.out.println("Product: " + appointmentToBook2);
		System.out.println("Resource: " + resourceName2);

		Assert.assertTrue(ap.getPopup1Content().getText().contains(clubName2));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: " + dayAfter + " " + startTime2));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: " + appointmentToBook2));
		Assert.assertTrue(ap.getPopup1Content().getText().contains(resourceName3));
		Assert.assertTrue(ap.getPopup1Content().getText().contains(resourceName4));

		Assert.assertEquals(ap.getPopup1Title().getText(), "Package Required");

		Assert.assertTrue(ap.getPopup1Content().getText().contains("This appointment requires a package purchase."));
		Assert.assertTrue(ap.getPopup1Content().getText().contains("Would you like to continue?"));

		ap.getPopup1BookButton().click();
		Thread.sleep(2000);

		System.out.println(ap.getOldAppointmentBanner().getText());
		System.out.println(ap.getNewAppointmentBanner().getText());

		Assert.assertTrue(ap.getOldAppointmentBanner().isDisplayed());
		Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(clubName1));
		Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains("Old Appointment"));
		Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(appointmentToBook1));
		Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(startTime1));
		Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(tomorrowsDate));

		Assert.assertTrue(ap.getNewAppointmentBanner().isDisplayed());
		Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(clubName2));
		Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains("New Appointment"));
		Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(appointmentToBook2));
		Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(startTime2));
		Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(dayAfter));

		for (int i = 0; i < ap.getReviewSection().size(); i++) {
			if (ap.getReviewSection().get(i).getText().contains("REVIEW"))

			{
				Assert.assertTrue(ap.getReviewSection().get(i).getText().contains("PACKAGE REQUIRED"));
				Assert.assertTrue(
						ap.getReviewSection().get(i).getText().contains("This appointment requires a package."));
				Assert.assertTrue(ap.getReviewSection().get(i).getText().contains(
						"We noticed you do not have an existing package that satisfies this appointment so we have included the correct package for you."));
			}
		}

		wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));

		Assert.assertTrue(ap.getChangeFee().getText().contains("CHANGE FEE $2.00"));

		wait.until(ExpectedConditions.textToBePresentInElement(ap.getRateBox(), appointmentToBook2.toUpperCase()));
		System.out.println(ap.getRateBox().getText());

		Select s4 = new Select(
				driver.findElement(By.xpath("//select[contains(@class, 'at-appointments-checkout-dropdown')]")));
		List<WebElement> UnitRates = s4.getOptions();

		int count4 = UnitRates.size();
		System.out.println("4 " + count4);

		for (int i = 0; i < count4; i++) {
			String unitRate = UnitRates.get(i).getText();
			System.out.println(unitRate);

			if (unitRate.contains(unitsToBeSelected)) {
				s4.selectByVisibleText(unitRate);
				break;
			}
		}

		Thread.sleep(2000);

		System.out.println(ap.getTotalAmount().getText());

		String[] totalAmt = ap.getTotalAmount().getText().split(": ");
		String FormatTotalAmt = totalAmt[1].trim();
		System.out.println(FormatTotalAmt);
		// Verifies the Pay button contains the total amount

		Assert.assertTrue(ap.getPaymentButton().getText().contains(FormatTotalAmt));

		PaymentMethodsPO PM = new PaymentMethodsPO(driver);

		while (!PM.getOnAccountAndSavedCards().isDisplayed())

		{
			Thread.sleep(1000);
			;
		}

		int paymentMethodscount = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < paymentMethodscount; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText().contains("5454")) {

				PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).click();
				break;
			}
		}

		// Click the Pay button
		while (!ap.getPaymentButton().isEnabled()) {
			Thread.sleep(1000);
		}
		ap.getPaymentButton().click();

		wait.until(ExpectedConditions.stalenessOf(ap.getPopup2OKButton()));

		wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

//Verifies the success message
		Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
		ap.getPopup2OKButton().click();
		Thread.sleep(1000);
		ThankYouPO TY = new ThankYouPO(driver);

//Verifies the text on Thank You page and the links to navigate to Dashboard and other pages are displayed
		reusableMethods.ThankYouPageValidations();

//Note down the Receipt number
		String receiptNumber = TY.getReceiptNumber().getText();

		Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
		TY.getPrintReceiptButton().click();
		Thread.sleep(2000);
		Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
		Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber));

//Verifies the buttons on Print Receipt Popup
		reusableMethods.ReceiptPopupValidations();

		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
		Thread.sleep(2000);

//Navigate to Dashboard
		int linkcount = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < linkcount; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

			{
				// reusableWaits.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}
		reusableWaits.waitForDashboardLoaded();
		reusableMethods.memberLogout();
	}

	@Test(priority = 2)
	public void ConfirmNewAppointmentIsScheduled() throws IOException, InterruptedException {

		reusableMethods.ApptCheckinInCOG("Auto, apptmember11", appointmentToBook2, "apptmember11"); // Check In the
																									// Member
																									// to the
																									// appointment

		reusableMethods.ConfirmAndCancelAppointmentNoFee(dayAfter, startTime2, appointmentToBook2);

	}
	// @AfterTest

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}