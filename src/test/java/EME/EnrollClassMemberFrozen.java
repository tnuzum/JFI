package EME;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollClassMemberFrozen extends base {
	
//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	
	@Test(priority = 1, description = "Validating that Frozen member cannot enroll")
	public void FrozenMemberCannotEnroll() throws IOException, InterruptedException {
		reusableMethods.activeMemberLogin("freeze", "Testing1!");
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
		
		driver.findElement(By.xpath("(//div[contains(@class, 'column2')])[1]")).click();
		Thread.sleep(1000);
		System.out.println(c.getPopUpErrorMessage().getText().trim());
		Assert.assertEquals("Membership restrictions have limited enrollment into this class.", c.getPopUpErrorMessage().getText().trim());
		Assert.assertFalse(c.getPopupSignUpButton().isEnabled());

}
	
//	@AfterTest
	@AfterClass
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
}
