package EME;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ShopPackagesPO;
import pageObjects.ThankYouPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollClass_ClubSettings extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	
@Test(priority = 1, description = "View Classes Unchecked For Club won't display the Class Schedule button")
	
   	public void ViewClassesUncheckedForClub() throws InterruptedException {
       	
       	reusableMethods.activeMemberLogin("CantCclasses", "Testing1!");
       	reusableWaits.waitForDashboardLoaded();
   		Thread.sleep(2000);
   		Assert.assertFalse(reusableMethods.isElementPresent(By.xpath("//button[contains(@class, 'at-widget-classschedule')]")));
   		reusableMethods.memberLogout();

   	}

@Test(priority = 2, description = "Allow  Class Enrollment Unchecked For Club won't display the Class List")

	public void AllowClassEnrollmentUncheckedForClub() throws InterruptedException {
   	
   	reusableMethods.activeMemberLogin("CantnrollClass", "Testing1!");
   	reusableWaits.waitForDashboardLoaded();
   	DashboardPO d = new DashboardPO(driver);
	BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
	d.getMyClassesScheduleButton().click();
	Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
	Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
	Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
	Thread.sleep(2000);
	
	ClassSignUpPO c = new ClassSignUpPO(driver);

	c.getCalendarIcon().click();
	Thread.sleep(2000);
	DateFormat dateFormat = new SimpleDateFormat("d");
	Calendar today = Calendar.getInstance();
	today.add(Calendar.DAY_OF_YEAR, 1);
	String tomorrowsDate = dateFormat.format(today.getTime());

	int daycount = driver.findElements(By.tagName("td")).size(); // Get the daycount from the calendar
	for (int i = 0; i < daycount; i++) {
		String date = driver.findElements(By.tagName("td")).get(i).getText();
		if (date.contains(tomorrowsDate)) {
			driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
			break;
		}
	}

	int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
	for (int j = 0; j < ClassCount; j++) {
		String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();

		if (className.contains("CANNOTBEENROLLEDCLASS")) {
			driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																								// specific class
			break;
		}
	}

	Thread.sleep(2000);
	Assert.assertFalse(c.getPopupSignUpButton().isEnabled());
	c.getPopupCancelButton().click();
	Thread.sleep(2000);
	reusableMethods.memberLogout();
	}
   	
//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
