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
import resources.Base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ClubNotReqPackages_BookAppt_ResourceNotSelected extends Base {
	private static Logger log = LogManager.getLogger(Base.class.getName());
	private static String startTime;
	private static int appointmentsCount;
	private static String appointmentToBook = "PT 60 Mins";

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1)
	public void ScheduleAppointment() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("scottauto", "Testing1!");
		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);
		AppointmentsPO ap = new AppointmentsPO(driver);
		WebElement bic = ap.getBookableItemCategory();

		Select s = new Select(bic);
		List<WebElement> ProductCategories = s.getOptions();

		int count = ProductCategories.size();
		System.out.println(count);

		for (int i = 0; i < count; i++) {
			String category = ProductCategories.get(i).getText();

			if (category.equals("Personal Training")) {
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

			if (product.equals(appointmentToBook)) {
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

			if (resource.equals("PT.Smith, Andrew")) {
				s2.selectByVisibleText(resource);
				break;
			}
		}
		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");

		String classtext = ap.getCalendarTomorrow().getAttribute("class");

		if (classtext.contains("cal-out-month")) {
			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();
			;

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");
		}

		ap.getCalendarTomorrow().click();
		Thread.sleep(1000);

		WebElement st1 = ap.getSelectTimeMorningButton();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(st1));
		while (!st1.isEnabled())// while button is NOT(!) enabled
		{
			System.out.println("Waiting for available times");
		}
		ap.getSelectTimeMorningButton().click();
		WebElement st2 = ap.getSelectTime1stAvailable();
//					while (!st2.isEnabled())//while button is NOT(!) enabled
//					{
//					Thread.sleep(200);
//					}
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		wait1.until(ExpectedConditions.elementToBeClickable(st2));
		startTime = st2.getText();
		System.out.println(startTime);
		st2.click();
		Thread.sleep(1000);
		WebElement p1 = ap.getPopup1BookButton();
		while (!p1.isEnabled())// while button is NOT(!) enabled
		{
//						Thread.sleep(200); 
		}

		ap.getPopup1BookButton().click();

		wait.until(ExpectedConditions.stalenessOf(ap.getPopup2OKButton()));
		wait.until(ExpectedConditions.elementToBeClickable(ap.getPopup2OKButton()));

		Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
		ap.getPopup2OKButton().click();
		Thread.sleep(1000);

		// Navigate to Dashboard
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
		// Verifies the link navigates to the right page
		Assert.assertEquals("Dashboard", driver.getTitle());
		Thread.sleep(2000);

	}

	@Test(priority = 2)
	public void ConfirmAppointmentIsScheduled() throws IOException, InterruptedException {
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
		int appointmentsCount = d.getMyAppts().size();

		for (int i = 0; i < appointmentsCount; i++) {
			if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

			{

				if (d.getMyAppts().get(i).getText().contains(startTime)) {
					Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook.toUpperCase()));
				}
			}
		}

	}

	@Test(priority = 3)
	public void CancelAppointment() throws IOException, InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);

		appointmentsCount = d.getMyAppts().size();

		for (int k = 0; k < appointmentsCount; k++) {
			if (d.getMyAppts().get(k).getText().contains(tomorrowsDate))

			{

				if (d.getMyAppts().get(k).getText().contains(startTime)) {
					wait.until(ExpectedConditions
							.elementToBeClickable(d.getMyAppts().get(k).findElement(By.tagName("i"))));
					d.getMyAppts().get(k).findElement(By.tagName("i")).click();

					WebElement EditButton = d.getEditButton().get(k);

					wait.until(ExpectedConditions.visibilityOf(EditButton));
					wait.until(ExpectedConditions.elementToBeClickable(EditButton));

					EditButton.click();
					break;
				}
			}
		}
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
		Thread.sleep(2000);
		AppointmentsPO a = new AppointmentsPO(driver);
		Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
		wait.until(ExpectedConditions.visibilityOf(a.getEditApptCancelButton()));
		a.getEditApptCancelButton().click();
		WebElement wait2 = a.getEditApptProceedButton();
		while (!wait2.isEnabled())// while button is NOT(!) enabled
		{
//			Thread.sleep(200);
		}
		a.getEditApptProceedButton().click();
		Thread.sleep(1000);
		boolean result1 = reusableWaits.popupMessageYesButton();
		if (result1 == true) {
//				Thread.sleep(500);	
		}
		a.getEditApptCancelYesButton().click();
//			boolean result2 = reusableWaits.popupMessageYesButton();
//			if (result2 == true)
//			{
//				Thread.sleep(500);	
//			}
//		a.getEditApptCanceledOKButton().click();
//		reusableWaits.waitForDashboardLoaded();
		Thread.sleep(2000);
		Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
		reusableMethods.memberLogout();
	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}

}
