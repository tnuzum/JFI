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

public class ClubReqPackages_BookAppt_NonMSS extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String clubName = "Studio Jonas";
	private static String productCategory = "Personal Training";
	private static String appointmentToBook = "PT 60 Mins-NonMSS";
	
//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 1)
	public void VerifyMessage() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("ccmember", "Testing1!");
		DashboardPO p = new DashboardPO(driver);
		p.getMyApptsScheduleButton().click();
		
		AppointmentsPO ap = new AppointmentsPO(driver);

		Select s = new Select(ap.getclubs());
		List<WebElement> Clubs = s.getOptions();

		while (!ap.getclubs().isEnabled()) {
			System.out.println("Waiting for Clubs drop down to not be blank");
		}

		int count0 = Clubs.size();
		System.out.println("1 " + count0);

		for (int i = 0; i < count0; i++) {
			String category = Clubs.get(i).getText();

			if (category.equals(clubName)) {
				s.selectByVisibleText(category);
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

		boolean result1 = reusableWaits.loadingAvailability();
		while (result1 == true) {
//						Thread.sleep(500);	
		}
		
		Assert.assertEquals(ap.getPopup1Content().getText(),"This appointment requires the purchase of a package, but this package cannot be purchased online. Please call the club to purchase the package." );
		ap.getPopup2OKButton().click();
		reusableMethods.returnToDashboard();
		reusableMethods.memberLogout();
	}
	 // @AfterTest
	 
	 @AfterClass 
	 public void teardown() throws InterruptedException {
	  driver.close(); driver = null; }
	

}