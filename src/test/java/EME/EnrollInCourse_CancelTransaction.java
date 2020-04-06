package EME;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.CalendarPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ThankYouPO;
import pageObjects.UnenrollPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EnrollInCourse_CancelTransaction extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String courseToEnroll = "FEECOURSE";
	private static String courseNameDisplayed = "FeeCourse";
	private static String courseTimeDisplayed = "Start Time: 11:00 AM";
	private static String courseInstructorDisplayed = "Course Instructor: Andrea";
	private static String CourseStartMonth = "Dec";
	private static String dsiredMonthYear = "December 2020";


//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}
	
	@Test(priority = 1, description = "Cancelling from the Rate Select Rates page")
	public void CancelFromSelectRatesPage() throws IOException, InterruptedException {
		
		reusableMethods.activeMemberLogin(prop.getProperty("activeMember6_username"), prop.getProperty("activeMember6_password"));
		reusableWaits.waitForDashboardLoaded();
		
		DashboardPO d = new DashboardPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		
		d.getMyCoursesEventsScheduleButton().click();
		
		Assert.assertEquals("Select Courses / Events", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
				
		ClassSignUpPO c = new ClassSignUpPO(driver);
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
		
//		System.out.println(driver.findElement(By.xpath("//label[@id='dec']")).getText());
//		driver.findElement(By.xpath("//label[@id='dec']")).click();
		WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
		int monthCount = MonthNames.findElements(By.tagName("label")).size();
				for (int i = 0; i < monthCount; i++)
				{
					String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
					if (monthName.equals(CourseStartMonth))
					{
						 MonthNames.findElements(By.tagName("label")).get(i).click();
						 break;
					}
						
				}
					
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		

		int courseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j = 0; j < courseCount; j++) {
			String courseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();

			if (courseName.contains(courseToEnroll)) {
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																									// specific course
				break;
			}
		}

		Thread.sleep(2000);
		c.getPopupSignupButtonCourse().click();
		Thread.sleep(2000);
		Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
		Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
		Assert.assertEquals(courseNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(courseTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(courseInstructorDisplayed, c.getCourseInstructor().getText());
		
		c.getCancelLink().click();
		
		Assert.assertEquals(c.getPageHeader().getText(), "Select Courses / Events");
	}
	
	@Test(priority = 2, description = "Cancelling from the Confirmation page")
	public void CancelFromConfirmationPage() throws IOException, InterruptedException {

				
		ClassSignUpPO c = new ClassSignUpPO(driver);
		BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));
		
//		System.out.println(driver.findElement(By.xpath("//label[@id='dec']")).getText());
//		driver.findElement(By.xpath("//label[@id='dec']")).click();
		WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
		int monthCount = MonthNames.findElements(By.tagName("label")).size();
				for (int i = 0; i < monthCount; i++)
				{
					String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
					if (monthName.equals(CourseStartMonth))
					{
						 MonthNames.findElements(By.tagName("label")).get(i).click();
						 break;
					}
						
				}
					
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		

		int courseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j = 0; j < courseCount; j++) {
			String courseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).getText();

			if (courseName.contains(courseToEnroll)) {
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																									// specific course
				break;
			}
		}

		Thread.sleep(2000);
		c.getPopupSignupButtonCourse().click();
		Thread.sleep(2000);
		Assert.assertEquals("Select Rates", BT.getPageHeader().getText());
		Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
		Assert.assertEquals("Select Courses / Events", BT.getBreadcrumb2().getText());
		Assert.assertEquals("Select Rates", BT.getBreadcrumb3().getText());
		Assert.assertEquals(courseNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(courseTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(courseInstructorDisplayed, c.getCourseInstructor().getText());

				
		int radioButtonCount = driver.findElements(By.tagName("label")).size();
		for (int i=0; i<radioButtonCount; i++)
		{
			if (driver.findElements(By.tagName("label")).get(i).getText().equals("Pay Course Fee"))
					{
					driver.findElements(By.tagName("label")).get(i).click();
					break;
					}
		}

		c.getContinueButton().click();
		Thread.sleep(2000);
		reusableMethods.ReviewSectionValidation("Fee(s)");
		
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
					.contains(" On Account"))
				Assert.assertTrue(PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).isSelected());
		}
	
		
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);

		// Noting down the total amount
		Thread.sleep(2000);
//		System.out.println(PP.getTotalAmount().getText());
		String totalAmt = PP.getClassesReviewtotalAmount().getText();
		
//		System.out.println(TotalAmt);
		
		//Verifies the Pay button contains the total amount
		Assert.assertTrue(PM.getPaymentButton().getText().contains(totalAmt));
		
		//Click the Pay button
		
		PM.getCancelButton().click();

		Assert.assertEquals(c.getPageHeader().getText(), "Select Courses / Events");

		reusableMethods.memberLogout();

	}


   	
//	@AfterTest
    @AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
