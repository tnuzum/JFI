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

public class ChangeApptWithFee_ClubNotReqPackages_OnAccount extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Jonas Fitness";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook = "PT 60 Mins-ChangePTWithFee";
	private static String resourceName1 = "FitExpert1";
	private static String resourceName2 = "PT.Shepard, Elliana";
	private static String resourceName3 = "FitExpert2";
	private static String resourceName4 = "Holmes, Jeff";
	private static String startTime1;
	private static String startTime2;
	private static String tomorrowsDate;
	
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
		startTime1 = reusableMethods.BookApptWith2Resources(clubName, productCategory, appointmentToBook, resourceName1, resourceName2);
	
		
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
		int appointmentsCount = d.getMyAppts().size();
		
		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar today1 = Calendar.getInstance();
		today1.add(Calendar.DAY_OF_YEAR, 1);
		tomorrowsDate = dateFormat1.format(today1.getTime());


		for (int i = 0; i < appointmentsCount; i++) {
			if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

			{

				if (d.getMyAppts().get(i).getText().contains(startTime1)) {
					
					Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook.toUpperCase()));
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
		}*/

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
	}
	 // @AfterTest
	 
/*	 @AfterClass 
	 public void teardown() throws InterruptedException {
	  driver.close(); driver = null; }*/
	

}