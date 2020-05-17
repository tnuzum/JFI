package EME_EnvURL;

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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.BreadcrumbTrailPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.PaymentMethodsPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ThankYouPO;
import resources.Base;
import resources.reusableMethods;
import resources.reusableWaits;

public class FamilyMemberClassEnrollment extends Base{
	private static Logger log = LogManager.getLogger(Base.class.getName());
	private static String classToEnroll = "FAMILYENROLLCLASS";
	private static String classNameDisplayed = "FamilyEnrollClass";
	private static String classTimeDisplayed = "Start Time: 5:00 PM";
	private static String classInstructorDisplayed = "Class Instructor: Max Gibbs";
	private static String classInstructorDisplayedOnSearchScreen = "Inst: Max Gibbs";
	private static String classTimeDisplayedOnSearchScreen = "5:00 PM";
	private static String classDuration = "30 min";
	private static String buyPackageName = "Buy Day Pass";
	private static String packageName = "Day Pass";
	private static String defaultSelection = null;
	private static String unitsToBeSelected = "2 - $1.00/per";
	private static String classCostInUnits = "Class Cost: 2 Unit(s) - Your Current Unit Value Is ";
	private static String member1 = "Cadmember";
	private static String member1Rate = "Not Eligible";
	private static String member2 = "Feemember";
	private static String member2Rate = "$9.00 or use package";
	private static String member3 = "Freemember";
	private static String member3Rate = "Free";
	private static String member4 = "FreezeMember";
	private static String member4Rate = "Not Eligible";
	private static String member5 = "Hoh";
	private static String member5Rate = "$9.00 or use package";
	private static String member6 = "Memberwithpunch";
	private static String member6Rate = "$9.00 or use package";
	private static String member7 = "Outpermtdhrs";
	private static String member7Rate = "Not Eligible";
	private static String member8 = "Terminate";
	private static String member8Rate = "Not Eligible";
	

//	@BeforeTest
	@BeforeClass
	@Parameters({"EMELoginPage"})
	public void initialize(String EMELoginPage) throws InterruptedException, IOException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(EMELoginPage);

	}
	
	@Test(priority = 1, description = "Family Member Enrollment")
	public void FamilyMemberEnrollment() throws IOException, InterruptedException {
	reusableMethods.activeMemberLogin("hoh", "Testing1!");
	//reusableMethods.unenrollFromClass();
	//Thread.sleep(2000);
	//reusableMethods.returnToDashboard();
	reusableWaits.waitForDashboardLoaded();
	DashboardPO d = new DashboardPO(driver);
	BreadcrumbTrailPO BT = new BreadcrumbTrailPO(driver);
	
	int IntPackageCountBefore = 0;
	int IntPackageCountAfter = 0;
	
	//Note the package units before enrolling the member with existing Package
	IntPackageCountBefore = reusableMethods.getPackageUnitsForMember(packageName,member6);
	int unitCount = reusableMethods.getPackageUnitsForMember(packageName,member5);
	System.out.println("Before "+IntPackageCountBefore);
	
	d.getMyClassesScheduleButton().click();
	
	Assert.assertEquals("Select Classes", BT.getPageHeader().getText());
	Assert.assertEquals("Dashboard", BT.getBreadcrumb1().getText());
	Assert.assertEquals("Select Classes", BT.getBreadcrumb2().getText());
		
	ClassSignUpPO c = new ClassSignUpPO(driver);
	WebDriverWait wait = new WebDriverWait(driver, 30);
	wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

	reusableMethods.SelectTomorrowDate();
	
	wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));
	
	c.getCourseFilter().click();
	c.getCourseKeyword().click();
	c.getSearchField().sendKeys("family");
	c.getClassApplyFilters().click();

	int ClassCount = c.getClassTable().size();
	for (int j = 0; j < ClassCount; j++) {
		
		WebElement w = c.getClassTable().get(j);
		WebElement w1 = c.getClassTimeAndDuration().get(j);
		String className = w.getText();
		String classTimeAndDuration = w1.getText();
        
		if (className.contains(classToEnroll)) 
		
		{
			Assert.assertTrue(className.contains(classInstructorDisplayedOnSearchScreen));
			Assert.assertTrue(classTimeAndDuration.contains(classTimeDisplayedOnSearchScreen));
			Assert.assertTrue(classTimeAndDuration.contains(classDuration));
			
			List<WebElement> getMemberRate = w.findElements(By.className("ng-star-inserted"));
		
			int count = getMemberRate.size();
			
			for (int k = 0; k<count; k++)
			{
						
				if (getMemberRate.get(k).getText().contains(member1))
					Assert.assertTrue(getMemberRate.get(k).getText().contains(member1Rate));
				if (getMemberRate.get(k).getText().contains(member2))
					Assert.assertTrue(getMemberRate.get(k).getText().contains(member2Rate));
				if (getMemberRate.get(k).getText().contains(member3))
					Assert.assertTrue(getMemberRate.get(k).getText().contains(member3Rate));
				if (getMemberRate.get(k).getText().contains(member4))
					Assert.assertTrue(getMemberRate.get(k).getText().contains(member4Rate));
				if (getMemberRate.get(k).getText().contains(member5))
					Assert.assertTrue(getMemberRate.get(k).getText().contains(member5Rate));
				if (getMemberRate.get(k).getText().contains(member6))
					Assert.assertTrue(getMemberRate.get(k).getText().contains(member6Rate));
				if (getMemberRate.get(k).getText().contains(member7))
					Assert.assertTrue(getMemberRate.get(k).getText().contains(member7Rate));
				if (getMemberRate.get(k).getText().contains(member8))
						Assert.assertTrue(getMemberRate.get(k).getText().contains(member8Rate));
					
			}
				
			w.click(); // Click on the specific class
			break;
		}
	}
		
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'modal-content')]")));
		while (c.getClasslabel().getText().isBlank())
		{
			Thread.sleep(500);
		}
		
		Assert.assertEquals(c.getClasslabel().getText(), classNameDisplayed); // Verifies the class name
		int count = c.getFmlyMemberLabel().size();
				
		// Selects the other eligible members
			for ( int i =0; i<count; i++)
			{
				
				WebElement fml =  c.getFmlyMemberLabel().get(i);
				WebElement fmc =  c.getFmlyMemberCheckBox().get(i);
						
			if (fml.getText().contains(member2)) 
				fml.click();   // Selects the member
				
			
			if (fml.getText().contains(member3)) 
			    fml.click();   // Selects the member
			
			if (fml.getText().contains(member5))
				Assert.assertTrue(fmc.isSelected());
				
			
			if (fml.getText().contains(member6)) 
			    fml.click();   // Selects the member
			    
		}
			c.getPopupSignUpButton().click();
		
			while (c.getClassName().getText().isBlank())
		{
			Thread.sleep(500);
		}

		Assert.assertEquals(classNameDisplayed, c.getClassName().getText());
		Assert.assertEquals(classTimeDisplayed, c.getClassStartTime().getText());
		Assert.assertEquals(classInstructorDisplayed, c.getClassInstructor().getText());
		Assert.assertEquals("Date: " + tomorrowsDate, c.getClassDate().getText());
		
		for (int i = 0; i<c.getMemberSections().size(); i++)
		{
			String paymentOptions = c.getMemberSections().get(i).getText();
			List<WebElement> Labels = c.getMemberSections().get(i).findElements(By.tagName("label"));
														
			if (c.getMemberSections().get(i).getText().contains(member2))
					{
							
				for (int j= 0; j<Labels.size(); j++)
				{
					if (Labels.get(j).getText().contains("Pay Single Class Fee"))
						{Labels.get(j).click();
						break;}
						}
					
				}
					
			
			if (c.getMemberSections().get(i).getText().contains(member3))
			{
				
				Assert.assertTrue(paymentOptions.contains("Free"));  // Class is free for this member
				for (int j= 0; j<Labels.size(); j++)
				{
					if (Labels.get(j).getText().contains("Free"))
						Assert.assertTrue(Labels.get(j).isEnabled());
				}
			}
			
			if (c.getMemberSections().get(i).getText().contains(member5)) //This member has all the payment options
			{
				Assert.assertTrue(paymentOptions.contains("Use Existing Package"));
				Assert.assertTrue(paymentOptions.contains("Pay Single Class Fee"));  
				Assert.assertTrue(paymentOptions.contains(buyPackageName));
				for (int j= 0; j<Labels.size(); j++)
				{
					if (Labels.get(j).getText().contains(buyPackageName))
						{Labels.get(j).click();
						break;
						}
				}
				
				Assert.assertTrue(paymentOptions.contains(classCostInUnits+unitCount));
				WebElement W = driver.findElement(By.xpath("//div[@class='ibox-content']"));
				Select s = new Select(W.findElement(By.xpath("//select[contains(@class, 'form-control')]")));
				 defaultSelection = s.getFirstSelectedOption().getText().trim();
				    Assert.assertEquals(defaultSelection, unitsToBeSelected);
			}
			
			if (c.getMemberSections().get(i).getText().contains(member6)) //This member has all the payment options
			{
				Assert.assertTrue(paymentOptions.contains("Use Existing Package"));
				Assert.assertTrue(paymentOptions.contains("Pay Single Class Fee"));  
				Assert.assertTrue(paymentOptions.contains(buyPackageName));
				for (int j= 0; j<Labels.size(); j++)
				{
					if (Labels.get(j).getText().contains("Use Existing Package"))
						Assert.assertTrue(Labels.get(j).isEnabled());
				}
			}
			
						
			
	}
		c.getContinueButton().click();
		
	PurchaseConfirmationPO pp = new PurchaseConfirmationPO(driver);
	
	PaymentMethodsPO PM = new PaymentMethodsPO(driver);
	
	while (c.getClassName().getText().isBlank())
	{
		Thread.sleep(500);
	}
	
	System.out.println(pp.getMemberfeesSection().size());
	for (int i=0; i<pp.getMemberfeesSection().size(); i++)
	{
		String text = pp.getMemberfeesSection().get(i).getText();
		
		if (text.contains(member2))
			Assert.assertTrue(text.contains("Single Class Fee $9.00"));
		
		if (text.contains(member3))
			Assert.assertTrue(text.contains("Single Class Fee Free"));
		
		if (text.contains(member5)) {
			Assert.assertTrue(text.contains("Day Pass")); 
			Assert.assertTrue(text.contains("Package Unit(s): 2 X $1.00"));}
					
		if (text.contains(member6)) {
			Assert.assertTrue(text.contains("Day Pass")); 
			Assert.assertTrue(text.contains("Unit(s) deducted for this class: 2")); }
					
	}
	
	while (pp.getClassesReviewtotalAmount().getText().isBlank())
	{
		Thread.sleep(500);
	}
	
	String totalAmount = pp.getClassesReviewtotalAmount().getText();
	     
	Assert.assertTrue(PM.getPaymentButton().getText().contains(totalAmount));   //Verifies the Pay button contains the total amount
	
	PM.getPaymentButton().click();
	wait.until(ExpectedConditions.elementToBeClickable(c.getPopupClose()));
	Assert.assertEquals("Success", c.getPopupMessage().getText());
	c.getPopupClose().click();
	ThankYouPO TY = new ThankYouPO(driver);

	//Verifies the text on Thank You page and the links to navigate to Dashboard and other pages are displayed
			reusableMethods.ThankYouPageValidations();

			//Note down the Receipt number
			String receiptNumber = TY.getReceiptNumber().getText();
			String receiptNumber1 = null;
			
			Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
			TY.getPrintReceiptButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(TY.getReceiptPopup().isDisplayed());
			
			//Verifies the buttons on Print Receipt Popup
			reusableMethods.ReceiptPopupValidations();
			
	TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
	Thread.sleep(1000);

	
	//Navigate to Dashboard
	int count1 = driver.findElements(By.tagName("a")).size();
	for (int i = 0; i < count1; i++) {
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
	
	AcctHistoryPO ahp = new AcctHistoryPO(driver);
	
	while(!ahp.getReceiptNumberTable().isDisplayed())
	{
		Thread.sleep(2000);	
		System.out.println("waiting");
	}
	
	//Clicks on the Receiptnumber in Account History 
	
	ahp.getSearchField().sendKeys(receiptNumber);
	
	while(!ahp.getReceiptNumberTable().isDisplayed())
	{
		Thread.sleep(2000);	
		System.out.println("waiting");
	}
	for (int k = 0; k < ahp.getReceiptNumbers().size(); k++) {
		
		receiptNumber1 = ahp.getReceiptNumbers().get(k).getText().trim();

		if (receiptNumber1.equals(receiptNumber)) {
			ahp.getReceiptNumbers().get(k).click();
			break;
		}
	}
	Thread.sleep(1000);
	//Verifies the Invoice amount 
	Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-xs-6 text-right']")).getText()
			.contains(totalAmount));
	TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).click();
	Thread.sleep(1000);
	reusableMethods.returnToDashboard();
	
	//Note the package units after enrolling the member with existing package
	IntPackageCountAfter = reusableMethods.getPackageUnitsForMember(packageName,member6);
	System.out.println("After "+ IntPackageCountAfter);
	
	//Verifies the package units is now decremented by two units
	IntPackageCountBefore  = IntPackageCountBefore-2;
	Assert.assertEquals(IntPackageCountBefore, IntPackageCountAfter); 
	reusableMethods.unenrollFromClass();
	reusableMethods.memberLogout();
}

@Test(dataProvider = "getData", dependsOnMethods = {"FamilyMemberEnrollment"})
public void FamilyMemberUnenroll(String username, String password) throws InterruptedException, IOException {
	reusableMethods.activeMemberLogin(username, password);
	reusableMethods.unenrollFromClass();
	reusableMethods.memberLogout();
	}
@DataProvider
public Object [][] getData()

	{
		Object[][] data = new Object[3][2];
		data[0][0] = "freemember";
		data[0][1] = "Testing1!";
		data[1][0] = "feemember";
		data[1][1] = "Testing1!";
		data[2][0] = "memberwithpunch";
		data[2][1] = "Testing1!";
		return data;
	}
//	@AfterTest
    @AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
	
	
 