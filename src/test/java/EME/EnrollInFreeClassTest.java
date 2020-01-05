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
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentPO;
import pageObjects.ShoppingCartPO;
import pageObjects.ThankYouPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;


public class EnrollInFreeClassTest extends base{
private static Logger log =LogManager.getLogger(base.class.getName());


//	@BeforeTest
	@BeforeClass
		public void initialize() throws IOException, InterruptedException
		{
			 driver = initializeDriver();
			 log.info("Driver Initialized");
			 driver.get(prop.getProperty("EMELoginPage"));
		}
		
	@Test (priority = 1)
		public void EnrollInClass() throws IOException, InterruptedException
		{	
		reusableMethods.activeMember5Login();
		reusableMethods.unenrollFromClass();
		Thread.sleep(1000);
		reusableMethods.returnToDashboard();
			DashboardPO d = new DashboardPO(driver);
		
		 d.getMyClassesScheduleButton().click();
			Thread.sleep(2000);
			ClassSignUpPO c = new ClassSignUpPO(driver);
	
			c.getCalendarIcon().click();
			Thread.sleep(2000);
			DateFormat dateFormat = new SimpleDateFormat("d");
			Calendar today = Calendar.getInstance();
			 today.add(Calendar.DAY_OF_YEAR, 1);
			 String tomorrowsDate = dateFormat.format(today.getTime());
			 
			 int daycount = driver.findElements(By.tagName("td")).size(); //Get the daycount from the calendar
			 for (int i= 0; i<daycount; i++)
			 {
				String date = driver.findElements(By.tagName("td")).get(i).getText();
				if (date.contains(tomorrowsDate))
				{
					 driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
					 break;
				}
			 }
			 
			int ClassCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
			for (int j= 0; j<ClassCount; j++)
			 {
				String className = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();
								
				if (className.contains("FREE CLASS"))
				{
					driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); //Click on the specific class
					 break;
				}
			 }
			

			
			Thread.sleep(1000);
		c.getPopupSignUpButton().click();
			Thread.sleep(1000);
		c.getContinueButton().click();
			Thread.sleep(1000);
		Assert.assertEquals("Success", c.getPopupMessage().getText());
		c.getPopupClose().click();
		ThankYouPO TY = new ThankYouPO(driver);

		//Verifies the text on Thank You page and Print Receipt Popup
		Assert.assertEquals("THANK YOU FOR YOUR ORDER", (TY.getThankYouText().getText()));
		Assert.assertTrue(TY.getsmallText().getText().contains("The receipt # for this transaction is:"));
		Assert.assertTrue(TY.getsmallText().getText().contains("Have fun!"));
		Assert.assertTrue(
				TY.getsmallText().getText().contains("Everything was processed and you are all ready to go."));
		Assert.assertTrue(TY.getsmallText().getText().contains(
				"Participants with a valid email address on file will receive a confirmation email with details of this purchase."));
		//Note down the Receipt number
		String receiptNumber = TY.getReceiptNumber().getText();
		String receiptNumber1 = null;
		//Verifies the Invoice amount is $0.00
		
		Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
		TY.getPrintReceiptButton().click();
		Thread.sleep(2000);
		Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
				.contains("$0.00"));
		Assert.assertTrue(
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'PRINT')]")).isDisplayed());
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'PRINT')]"))
				.getAttribute("type").equals("button"));
		Assert.assertTrue(
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).isDisplayed());
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]"))
				.getAttribute("type").equals("button"));
		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
		Thread.sleep(1000);

		//Verifies the links to navigate to Dashboard and other pages are displayed
		Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/Home']")));
		Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/ClassList']")));
		Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/CourseList']")));
		Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/Appointments']")));

		//Navigate to Dashboard
		int count = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < count; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

			{
				// reusableWaits.linksToBeClickable();
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}
		reusableWaits.waitForDashboardLoaded();
		//Verifies the link navigates to the right page
		Assert.assertEquals("Dashboard", driver.getTitle());
		Thread.sleep(1000);
		DashboardPO dp = new DashboardPO(driver);
		dp.getMyAccountAccountHistory().click();
		
		//Clicks on the Receiptnumber in Account History 
		AcctHistoryPO ahp = new AcctHistoryPO(driver);
		for (int k = 0; k < ahp.getReceiptNumbers().size(); k++) {
			receiptNumber1 = ahp.getReceiptNumbers().get(k).getText().trim();

			if (receiptNumber1.equals(receiptNumber)) {
				ahp.getReceiptNumbers().get(k).click();
				break;
			}
		}

		//Verifies the Invoice amount is $0.00
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
				.contains("$0.00"));
		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
		Thread.sleep(1000);
		reusableMethods.returnToDashboard();
		}

	@Test (priority = 2, dependsOnMethods = {"scheduleClass"})
		public void unenrollFromClass() throws IOException, InterruptedException
		{	
		DashboardPO d = new DashboardPO(driver);

//		
			boolean enrolled = reusableMethods.isElementPresent(By.xpath("//div[@class='class-table-container']"));
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='class-table-container']")));
			if (enrolled == true)
			{
			
			while (!d.getMyClassesClass1GearButton().isDisplayed())
			{
				Thread.sleep(1000);
				System.out.println("Sleeping for 1 second");
			}
		d.getMyClassesClass1GearButton().click();
			Thread.sleep(2000);
		d.getmyClassesUnenrollButton().click();
			Thread.sleep(1000);
			UnenrollPO u = new UnenrollPO(driver);
				u.getUnenrollButton().click();
					Thread.sleep(2000);
				u.getUnenrollConfirmYesButton().click();
				Thread.sleep(1000);
				AssertJUnit.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
				u.getUnenrollConfirmYesButton().click();
				}
		 
			else
				{
		System.out.println("enrollement not displayed");
				}
		
	}

//	@AfterTest
	@AfterClass
		public void teardown() throws InterruptedException
		{
			driver.close();
			driver=null;
		}
}
