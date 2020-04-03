package EME;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.AppointmentsPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.ThankYouPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ChangeApptWithFee_ClubNotReqPackages_AdditionalResources extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Jonas Fitness";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook1 = "PT 60 Mins-ChangeWithFee1";
	private static String appointmentToBook2 = "PT 60 Mins-ChangeWithFee2";
	private static String resourceName1 = "FitExpert1";
	private static String resourceName2 = "PT.Shepard, Elliana";
	private static String resourceName3 = "All Resources";
	private static String resourceName4 = "FitExpert2";
	private static String resourceName5 = "Holmes, Jeff";
	private static String startTime1;
	private static String startTime2;
	private static String tomorrowsDate;
	private static String dayAfter;
	
//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1)
	public void ChangeAppointmentWithFee() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("apptmember6", "Testing1!");
		
		//Book an appointment and get the start time for the appointment
		startTime1 = reusableMethods.BookApptWith2Resources(clubName, productCategory, appointmentToBook1, resourceName1, resourceName2);
	
		
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
		int appointmentsCount = d.getMyAppts().size();
		
		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		tomorrowsDate = dateFormat1.format(today1.getTime());
		
		Calendar today2 = Calendar.getInstance();
		today2.add(Calendar.DAY_OF_YEAR, 2);
		dayAfter = dateFormat1.format(today2.getTime());



		for (int i = 0; i < appointmentsCount; i++) {
			if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

			{

				if (d.getMyAppts().get(i).getText().contains(startTime1)) {
					
					Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook1.toUpperCase()));
					wait.until(ExpectedConditions.elementToBeClickable(d.getMyAppts().get(i).findElement(By.tagName("i"))));
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
		
		AppointmentsPO ap = new AppointmentsPO(driver);
		ap.getEditApptChangeButton().click();
		Thread.sleep(1000);
		Assert.assertTrue(ap.getCancelFeeSection().getText().contains("There is a fee for changing this appointment."));
		Assert.assertTrue(ap.getCancelFeeSection().getText().contains("If you proceed, you will be charged a fee of:"));
		ap.getEditApptProceedButton1().click();
		
		while (ap.getloadingAvailabilityMessage().size()!=0)
		{
			System.out.println("waiting1");
			Thread.sleep(1000);
		}
		
		System.out.println("came out of the loop");
						
/*		Select se = new Select(ap.getclubs());
		List<WebElement> Clubs = se.getOptions();

		while (!ap.getclubs().isEnabled()) {
			System.out.println("Waiting for Clubs drop down to not be blank");
		}

		int count0 = Clubs.size();
		System.out.println("1 " + count0);

		for (int i = 0; i < count0; i++) {
			String category = Clubs.get(i).getText();

			if (category.equals(clubName)) {
				se.selectByVisibleText(category);
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
		}*/

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
		while (ap.getloadingAvailabilityMessage().size()!=0)
		{
			System.out.println("waiting1");
			Thread.sleep(1000);
		}
		
		System.out.println("came out of the loop");
	
		
		String classtext = ap.getCalendarDayAfterTomorrow().getAttribute("class");

		if (classtext.contains("cal-out-month"))
		{
			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();
			
			while (ap.getloadingAvailabilityMessage().size()!=0)
			{
				System.out.println("waiting1");
				Thread.sleep(1000);
			}
			
			System.out.println("came out of the loop");

			
		}
		
		ap.getCalendarDayAfterTomorrow().click();
		
		for (int m = 0; m < ap.getApptBox().size(); m++)
		{
		String bookName = ap.getApptBox().get(m).getText();
		if (bookName.contains(resourceName4))
		{
			List <WebElement> TimeSlots = ap.getTimeSlotContainers().get(m).findElements(By.tagName("a"));		
			WebElement AftrnunSlot = TimeSlots.get(1);
			wait.until(ExpectedConditions.elementToBeClickable(AftrnunSlot));
			while (!AftrnunSlot.isEnabled())// while button is NOT(!) enabled
			{
				System.out.println("Waiting for available times");
			}
			
			AftrnunSlot.click();
		
		
			WebElement AftrenoonAvailableTimeContainer = ap.getTimeSlotContainers().get(m).findElement(By.id("tab-2-1"));
					List <WebElement> AftrenoonAvailableTimes = AftrenoonAvailableTimeContainer.findElements(By.tagName("button"));	
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
		
//		ap.getPopup1BookButton().click();
		Thread.sleep(1000);
		
		System.out.println(ap.getOldAppointmentBanner().getText());
		System.out.println(ap.getNewAppointmentBanner().getText());
		
		Assert.assertTrue(ap.getOldAppointmentBanner().isDisplayed());
		Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(clubName));
		Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains("Old Appointment"));
		Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(appointmentToBook1));
		Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(startTime1));
		Assert.assertTrue(ap.getOldAppointmentBanner().getText().contains(tomorrowsDate));
		
		Assert.assertTrue(ap.getNewAppointmentBanner().isDisplayed());
		Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(clubName));
		Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains("New Appointment"));
		Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(appointmentToBook2));
		Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(startTime2));
		Assert.assertTrue(ap.getNewAppointmentBanner().getText().contains(dayAfter));
		
		int additionalResourcesCount = ap.getAdditionalResources().size();

		for (int n = 0; n < additionalResourcesCount; n++) {
			if (ap.getAdditionalResources().get(n).getText().contains(resourceName5))
				ap.getAdditionalResources().get(n).click();
		}
		
		wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));
		Assert.assertTrue(ap.getFeeSections().get(0).getText().contains("DUE AT TIME OF SERVICE $90.00"));
		Assert.assertTrue(ap.getFeeSections().get(1).getText().contains("CHANGE FEE $2.00"));
	
		System.out.println(ap.getTotalAmount().getText());

		String[] totalAmt = ap.getTotalAmount().getText().split(": ");
		String FormatTotalAmt = totalAmt[1].trim();
		System.out.println(FormatTotalAmt);
		// Verifies the Pay button contains the total amount

		Assert.assertTrue(ap.getPaymentButton().getText().contains(FormatTotalAmt));
		
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		
		int paymentMethodscount = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < paymentMethodscount; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
					.contains("5454"))
				{
				
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
		
		d.getMyAccountAccountHistory().click();
		AcctHistoryPO ahp = new AcctHistoryPO(driver);

	Thread.sleep(1000);

//Clicks on the Receiptnumber in Account History 

		ahp.getSearchField().sendKeys(receiptNumber);
		Thread.sleep(2000);
		ahp.getReceiptNumber().click();
		Thread.sleep(1000);


//Verifies the amount in the receipt is the same as it was displayed on the Purchase Packages page

		while (TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText().isBlank()) {
			Thread.sleep(500);
		}
		System.out.println(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText());
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
				.contains(FormatTotalAmt));
		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
		Thread.sleep(2000);
		reusableMethods.returnToDashboard();
	}
		@Test (priority = 2)
		public void ConfirmNewAppointmentIsScheduled() throws IOException, InterruptedException {
			
			reusableMethods.ConfirmAndCancelAppointmentNoFee(dayAfter, startTime2, appointmentToBook2);

		}
	 // @AfterTest
	 
/*	 @AfterClass 
	 public void teardown() throws InterruptedException {
	  driver.close(); driver = null; }*/
	

}