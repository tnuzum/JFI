package FamilyAppointments;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AppointmentsPO;
import pageObjects.CalendarPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ChangeByHoh_PerMember_Single_WithFees_PkgReqd extends base {

	private static String clubName1 = "Jonas Fitness";
	private static String clubName2 = "Studio Jonas";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook = "PerMbrChangeFee";
	private static String resourceName1 = "";
	private static String resourceName2 = "PT.Ramakers, Tanya";
	private static String startTime1 = null;
	private static String startTime2;
	private static AppointmentsPO ap;
	private static String familyMember = "Auto, Kidapptmbr";
	private static String familyMemberFirstName = "Kidapptmbr";

	public reusableWaits rw;
	public reusableMethods rm;

	public ChangeByHoh_PerMember_Single_WithFees_PkgReqd() {
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

		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());

		getEMEURL();

		ap = new AppointmentsPO(driver);
	}

	@Test
	public void ChangeAppointmentForFamilyMember() throws InterruptedException, IOException {

		try {
			rm.activeMemberLogin("appthoh", "Testing1!");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
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

			startTime1 = rm.BookApptWith2Resources(clubName1, productCategory, appointmentToBook, resourceName1,
					resourceName2);

			WebDriverWait wait = new WebDriverWait(driver, 30);

			CalendarPO cp = new CalendarPO(driver);

			rm.openSideMenuIfNotOpenedAlready();

			while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1")) {

				d.getMenuMyActivies().click();
				Thread.sleep(1000);
				d.getmenuMyActivitiesSubMenu().getAttribute("style");
				System.out.println(d.getmenuMyActivitiesSubMenu().getAttribute("style"));
			}

			WebDriverWait wait1 = new WebDriverWait(driver, 50);
			wait1.until(ExpectedConditions.elementToBeClickable(d.getMenuMyCalendar()));

			d.getMenuMyCalendar().click();
			wait1.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

			Calendar calendar = Calendar.getInstance();

			if (calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DATE)) {

				driver.findElements(By.xpath("//i[contains(@class, 'right')]")).get(0).click();

				wait1.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

				Thread.sleep(1000);

			}

			int memberSectionCount = cp.getMemberSections().size();

			for (int i = 0; i < memberSectionCount; i++) {

				if (cp.getMemberSections().get(i).getText().contains(familyMemberFirstName)) {

					if (cp.getMemberSections().get(i).getText().contains(tomorrowsDate))

					{

						if (cp.getMemberSections().get(i).getText().contains(startTime1)) {
							jse.executeScript("arguments[0].click();",
									cp.getMemberSections().get(i).findElement(By.tagName("i")));

							jse.executeScript("arguments[0].click();",
									cp.getMemberSections().get(i).findElements(By.tagName("a")).get(1));
							break;
						}
					}

				}

			}

			Thread.sleep(1000);

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
			Thread.sleep(2000);
			AppointmentsPO a = new AppointmentsPO(driver);
			Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
			wait.until(ExpectedConditions.visibilityOf(a.getEditApptChangeButton()));
			jse.executeScript("arguments[0].click();", ap.getEditApptChangeButton());
			Thread.sleep(1000);
			Assert.assertTrue(
					ap.getChangeFeeSection().getText().contains("If you proceed, you will be charged a Change Fee of"));

			if (appointmentToBook.contains("Grp")) {

				Assert.assertTrue(ap.getChangeFeeSection().getText().contains(
						" This will remove other participants from your appointment. You will need to add them again when you select your new appointment."));
			}
			jse.executeScript("arguments[0].click();", ap.getEditApptProceedButton1());

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}
			rm.selectClub(clubName2);
			rm.selectProductCategory(productCategory);
			rm.makeNewAppointmentSelections(appointmentToBook, "");
			rm.calendarDayAfterTomorrowClick();

			startTime2 = rm.selectAppointmentTime(resourceName2);

			Assert.assertTrue(ap.getPopup1Content().getText().contains(clubName2));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Time: " + dayAfter + " " + startTime2));
			Assert.assertTrue(ap.getPopup1Content().getText().contains("Product: " + appointmentToBook));
			Assert.assertTrue(ap.getPopup1Content().getText().contains(resourceName2));

			ap.getPopup1BookButton().click();
			Thread.sleep(3000);

			System.out.println(ap.getOldAppointmentBanner().getText());
			System.out.println(ap.getNewAppointmentBanner().getText());

			Assert.assertTrue(ap.getOldAppointmentBanner().isDisplayed());
			Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(clubName1));
			Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains("Old Appointment"));
			Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(appointmentToBook));
			Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(startTime1));
			Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(tomorrowsDate));

			Assert.assertTrue(ap.getNewAppointmentBanner().isDisplayed());
			Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(clubName2));
			Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains("New Appointment"));
			Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(appointmentToBook));
			Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(startTime2));
			Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(dayAfter));

			wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));
			Thread.sleep(1000);

			Assert.assertTrue(ap.getChangeFee().getText().contains("CHANGE FEE $4.00"));

			Select s5 = new Select(
					driver.findElement(By.xpath("//select[contains(@class, 'at-appointments-checkout-dropdown')]")));
			List<WebElement> UnitRates = s5.getOptions();
			String unitRate = UnitRates.get(0).getText();
			Assert.assertEquals(unitRate, "1 - $1.25/per");

			System.out.println(ap.getTotalAmount().getText());

			String[] totalAmt = ap.getTotalAmount().getText().split(": ");
			String FormatTotalAmt = totalAmt[1].trim();
			System.out.println(FormatTotalAmt);
			Assert.assertEquals("$5.40", FormatTotalAmt);
			// Verifies the Pay button contains the total amount

			Assert.assertTrue(ap.getPaymentButton().getText().contains(FormatTotalAmt));

			PaymentMethodsPO PM = new PaymentMethodsPO(driver);

			while (!PM.getNewCardButton().isDisplayed())

			{
				Thread.sleep(1000);
				;
			}

			// Click the Pay button
			while (!ap.getPaymentButton().isEnabled()) {
				Thread.sleep(1000);
			}
			jse.executeScript("arguments[0].click();", ap.getPaymentButton());

			rw.waitForAcceptButton();

			wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

//Verifies the success message
			Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
			ap.getPopup2OKButton().click();
			Thread.sleep(1000);
			ThankYouPO TY = new ThankYouPO(driver);

//Verifies the text on Thank You page and the links to navigate to Dashboard and other pages are displayed
			rm.ThankYouPageValidations();

//Note down the Receipt number
			String receiptNumber = TY.getReceiptNumber().getText();

			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			Assert.assertTrue(TY.getReceiptHeader().getText().contains(receiptNumber));

//Verifies the buttons on Print Receipt Popup
			rm.ReceiptPopupValidations();

			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
			Thread.sleep(2000);

//Navigate to Dashboard
			int linkcount = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < linkcount; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

				{
					// rw.linksToBeClickable();
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
					break;
				}

			}
			rw.waitForDashboardLoaded();

			rm.memberLogout();
			rm.ApptCheckinInCOG("Auto, Kidapptmbr", appointmentToBook, "appthoh", "1");

			rm.cancelAppointmentFromListViewByHohNoFee(dayAfter, startTime2, appointmentToBook, familyMemberFirstName);

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

	@AfterClass(enabled = true)
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}
}
