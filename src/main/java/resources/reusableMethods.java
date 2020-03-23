package resources;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import pageObjects.AppointmentsPO;
import pageObjects.CalendarPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.ErrorMessagesPO;
import pageObjects.LoginPO;
import pageObjects.PackagesPO;
import pageObjects.PaymentPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ThankYouPO;
import pageObjects.UnenrollPO;
import resources.base;

public class reusableMethods extends base {

	public static String activeMember1Login() throws InterruptedException {
		reusableWaits.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember1_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember1_password"));
		l.getLoginButton().click();
		reusableWaits.waitForDashboardLoaded();
		return null;
	}

	public static String activeMember2Login() throws InterruptedException {
		reusableWaits.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember2_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember2_password"));
		l.getLoginButton().click();
		reusableWaits.waitForDashboardLoaded();
		return null;
	}

	public static String activeMember3Login() throws InterruptedException {
		reusableWaits.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember3_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember3_password"));
		l.getLoginButton().click();
		reusableWaits.waitForDashboardLoaded();
		return null;
	}

	public static String activeMember4Login() throws InterruptedException {
		reusableWaits.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember4_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember4_password"));
		l.getLoginButton().click();
		reusableWaits.waitForDashboardLoaded();
		return null;
	}

	public static String activeMember5Login() throws InterruptedException {
		reusableWaits.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember5_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember5_password"));
		l.getLoginButton().click();
		reusableWaits.waitForDashboardLoaded();
		return null;
	}
	public static String activeMember6Login() throws InterruptedException {
		reusableWaits.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember6_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember6_password"));
		l.getLoginButton().click();
		reusableWaits.waitForDashboardLoaded();
		return null;
	}
	public static String activeMember7Login() throws InterruptedException {
		reusableWaits.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember7_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember7_password"));
		l.getLoginButton().click();
		reusableWaits.waitForDashboardLoaded();
		return null;
	}
	public static String activeMember8Login() throws InterruptedException {
		reusableWaits.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember8_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember8_password"));
		l.getLoginButton().click();
		reusableWaits.waitForDashboardLoaded();
		return null;
	}
	public static String activeMember9Login() throws InterruptedException {
		reusableWaits.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys("NoOAMember");
		l.getuserPassword().sendKeys("Testing1!");
		l.getLoginButton().click();
		reusableWaits.waitForDashboardLoaded();
		return null;
	}
	public static String activeMember10Login() throws InterruptedException {
			reusableWaits.waitForLoginLoginButton();
			LoginPO l = new LoginPO(driver);
			l.getuserName().sendKeys("NoCCMember");
			l.getuserPassword().sendKeys("Testing1!");
			l.getLoginButton().click();
			reusableWaits.waitForDashboardLoaded();
			return null;
		}
	public static String activeMember11Login() throws InterruptedException {
				reusableWaits.waitForLoginLoginButton();
				LoginPO l = new LoginPO(driver);
				l.getuserName().sendKeys("NoOANoCCMember");
				l.getuserPassword().sendKeys("Testing1!");
				l.getLoginButton().click();
				reusableWaits.waitForDashboardLoaded();
				return null;
			}
	public static String activeMemberLogin(String username, String password) throws InterruptedException {
				reusableWaits.waitForLoginLoginButton();
				LoginPO l = new LoginPO(driver);
				l.getuserName().sendKeys(username);
				l.getuserPassword().sendKeys(password);
				l.getLoginButton().click();
				reusableWaits.waitForDashboardLoaded1();
				return null;
			}

	public static String collectionsMember1Login() throws InterruptedException {
		reusableWaits.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("collectionsMember1_username"));
		l.getuserPassword().sendKeys(prop.getProperty("collectionsMember1_password"));
		l.getLoginButton().click();
		reusableWaits.waitForDashboardLoaded1();
		return null;
	}

	public static String prospectMember1Login() throws InterruptedException {
		reusableWaits.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("prospectMember1_username"));
		l.getuserPassword().sendKeys(prop.getProperty("prospectMember1_password"));
		l.getLoginButton().click();
		reusableWaits.waitForDashboardLoaded1();
		return null;
	}

	public static String memberLogout() throws InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		d.getLogoutButton().click();
		reusableWaits.waitForLoginLoginButton();
		return null;
	}

	public static String returnToDashboard() throws InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(d.getDashboardButton()));
		d.getDashboardButton().click();
//		d.getBreadcrumbDashboard().click();
		reusableWaits.waitForDashboardLoaded();
		return null;
	}

	public static boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public static String unenrollFromClass() throws IOException, InterruptedException
	{	
	DashboardPO d = new DashboardPO(driver);

//		WebDriverWait wait = new WebDriverWait(driver, 10);
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//classescourses/div[1]/div[2]/div[1]/div[1]/a[1]/div[1]/div[3]/i[1]")));
//		Thread.sleep(2000);
		boolean enrolled = reusableMethods.isElementPresent(By.xpath("//classeswidget//div[@class='class-table-container']"));
//		System.out.println(enrolled);
		
		if (enrolled == true)
		{
			while (!d.getMyClassesClass1GearButton().isDisplayed()) 
			{
				Thread.sleep(1000);
				System.out.println("Sleeping for 1 second");
			}
			
	    WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(d.getMyClassesClass1GearButton()));	
		d.getMyClassesClass1GearButton().click();
		
		wait.until(ExpectedConditions.visibilityOf(d.getmyClassesUnenrollButton()));
		wait.until(ExpectedConditions.elementToBeClickable(d.getmyClassesUnenrollButton()));
		d.getmyClassesUnenrollButton().click();
		UnenrollPO u = new UnenrollPO(driver);
		wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));
		u.getUnenrollButton().click();
		wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
		u.getUnenrollConfirmYesButton().click();
		wait.until(ExpectedConditions.stalenessOf(u.getUnenrollConfirmYesButton()));
		wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
		Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
		u.getUnenrollConfirmYesButton().click();
			
		}
		else
		{
			System.out.println("Not enrolled already");
		}
		reusableMethods.returnToDashboard();
		return null;
	
}
	
	public static  Object unenrollFromCourse(String dsiredMonthYear) throws IOException, InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		CalendarPO cp = new CalendarPO(driver);
		
		Thread.sleep(2000);
		d.getMenuMyActivies().click();
		
		while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1"))
		{
			Thread.sleep(500);
		}
		
		d.getMenuMyCalendar().click();
		String monthYear = cp.getMonthYear().getText();
		while(!monthYear.equals(dsiredMonthYear))
		{
			cp.getRightArrow().click();
			monthYear = cp.getMonthYear().getText();
		}
		
		cp.getCalDayBadge().click();
		cp.getCalEventTitle().click();
		Thread.sleep(1000);
		cp.getUnEnrollBtn().click();
		UnenrollPO u = new UnenrollPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));
		u.getUnenrollButton().click();
		wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
		u.getUnenrollConfirmYesButton().click();
		wait.until(ExpectedConditions.stalenessOf(u.getUnenrollConfirmYesButton()));
		wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
		Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
		u.getUnenrollConfirmYesButton().click();
		
		reusableMethods.returnToDashboard();
		
		return null;
	}
	
	
	public static String useNewCard() throws InterruptedException {
		PaymentPO p = new PaymentPO(driver);
		p.getSelectPaymentNewCardButton().click();
		Thread.sleep(2000);
		p.getCardNumber().sendKeys(prop.getProperty("MastercardNumber"));
		p.getExpireMonth().sendKeys(prop.getProperty("MastercardExpireMonth"));
		p.getExpireYear().sendKeys(prop.getProperty("MastercardExpireYear"));
		p.getCVC().sendKeys(prop.getProperty("MastercardCVC"));
		p.getSaveCardNoRadio().click();
		Thread.sleep(2000);
		// p.getIAgreeCheckbox().click();//might not be shown if getSaveCardNoRadio is
		// used
		Thread.sleep(2000);
		p.getSubmitButton().click();
		Thread.sleep(5000);
		p.getPopupPayButton().click();
		return null;
	}

	private static boolean catchErrorMessagePrivate()// only used by catchErrorMessage method below; not available from
														// other classes
	{
		try {
			driver.findElement(By.xpath("//*[text()='An Error Has Occurred']"));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static String catchErrorMessage() throws InterruptedException {
		boolean e = reusableMethods.catchErrorMessagePrivate();
		if (e == true) {
			System.out.println("ERROR: An Error Has Occurred");
			ErrorMessagesPO er = new ErrorMessagesPO(driver);
			er.getOKButton().click();
			reusableMethods.returnToDashboard();
			Assert.assertFalse(e);
		}
		return null;
	}
	
	public static String getDateFormater() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String DateTime= dateFormat.format(date);
		return DateTime;
	}

	public static int getPackageUnits(String packageName) throws InterruptedException{
		
	PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);
	
	//Note the package units 
	PP.getMyPackagesButton().click();
	int IntUnitCount = 0;
	Thread.sleep(3000);
	int packagesCount = PP.getPackagesList().size();
	for (int j = 0; j < packagesCount; j++) {
		String text = PP.getPackagesList().get(j).getText();
		if (PP.getPackagesList().get(j).getText().contains(packageName)) {
			String[] unitCount = PP.getUnitsCount().get(j).getText().split(" ");
			String formattedUnitCount = unitCount[0].trim();
			IntUnitCount = Integer.parseInt(formattedUnitCount);
			PP.getMyPackagesButton().click();
			break;
		}
	}
	return IntUnitCount;
	}
	
	public static int getPackageUnitsForMember(String packageName, String memberName) throws InterruptedException{
		
		DashboardPO d = new DashboardPO(driver);
		PackagesPO pp= new PackagesPO(driver);
		
		//Note the package units 
		d.getMenuMyAccount().click();
		d.getMenuPackages().click();
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.className("ibox"))));
		int IntUnitCount = 0;
		for (int i = 0; i<pp.getMemberSections().size(); i++)
		{
			
			if (pp.getMemberSections().get(i).getText().contains(memberName.toUpperCase()))
			{
				List<WebElement> Packages = pp.getMemberSections().get(i).findElements(By.className("ng-star-inserted"));
				
								
			for (int k = 0; k<Packages.size(); k++)
			{
				if (Packages.get(k).getText().contains(packageName))
				{
					String[] text = Packages.get(k).getText().split("\n");
					String unitCount = text[2];
					IntUnitCount = Integer.parseInt(unitCount);
					break;
				}
				
			}
			break;
			}
		}
		d.getMenuMyAccount().click();
		reusableMethods.returnToDashboard();
		
		return IntUnitCount;
		}
	
	public static Object ThankYouPageValidations()
	{
	ThankYouPO TY = new ThankYouPO(driver);

	//Verifies the text on Thank You page and Print Receipt Popup
	Assert.assertEquals("THANK YOU FOR YOUR ORDER", (TY.getThankYouText().getText()));
	Assert.assertTrue(TY.getsmallText().getText().contains("The receipt # for this transaction is:"));
	Assert.assertTrue(TY.getsmallText().getText().contains("Have fun!"));
	Assert.assertTrue(
			TY.getsmallText().getText().contains("Everything was processed and you are all ready to go."));
	Assert.assertTrue(TY.getsmallText().getText().contains(
			"Participants with a valid email address on file will receive a confirmation email with details of this purchase."));
	
	//Verifies the links to navigate to Dashboard and other pages are displayed
			Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/Home']")));
			Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/ClassList']")));
			Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/CourseList']")));
			Assert.assertTrue(reusableMethods.isElementPresent(By.xpath("//a[@href = '#/Appointments']")));
	return null;
}
	
	public static Object ReceiptPopupValidations()
	{
	ThankYouPO TY = new ThankYouPO(driver);

	Assert.assertTrue(
			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'PRINT')]")).isDisplayed());
	Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'PRINT')]"))
			.getAttribute("type").equals("button"));
	Assert.assertTrue(
			TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]")).isDisplayed());
	Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'Close')]"))
			.getAttribute("type").equals("button"));
	return null;
}
	
	public static Object ReviewSectionValidation(String feesOrPackages) {

		PurchaseConfirmationPO pp = new PurchaseConfirmationPO(driver);
		Boolean ReviewLabelPresent = reusableMethods.isElementPresent(By.xpath("//div[@class = 'rate-box']/h2"));
		Assert.assertTrue(ReviewLabelPresent);
		Assert.assertEquals("Review", pp.getReviewLabel().getText());
		Boolean FeesPackagesLabelPresent = reusableMethods.isElementPresent(By.xpath("//small[contains(text(),feesOrPackages)]"));
		Assert.assertTrue(FeesPackagesLabelPresent);
		Boolean SubTotalLabelPresent = reusableMethods
				.isElementPresent(By.xpath("//strong[contains(text(),'SUB-TOTAL:')]"));
		Assert.assertTrue(SubTotalLabelPresent);
		Boolean TaxLabelPresent = reusableMethods.isElementPresent(By.xpath("//strong[contains(text(),'TAX:')]"));
		Assert.assertTrue(TaxLabelPresent);
		Boolean TotalLabelPresent = reusableMethods.isElementPresent(By.xpath("//h2[contains(text(),'TOTAL:')]"));
		Assert.assertTrue(TotalLabelPresent);
		return null;

	}
	
	public static Object SelectTomorrowDate() throws InterruptedException{
		
	ClassSignUpPO c = new ClassSignUpPO(driver);
	c.getCalendarIcon().click();
	Thread.sleep(2000);
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d yyyy");
	Calendar today = Calendar.getInstance();
	 today.add(Calendar.DAY_OF_YEAR, 1);
	 String tomorrowsMonthDateYear = dateFormat.format(today.getTime());
	
	 String[] monthDateYear = tomorrowsMonthDateYear.split(" ");
	 String monthYear = monthDateYear[0].toUpperCase() + " " + monthDateYear[2];
	 String tomorrowsDate = monthDateYear[1];
	 
	 String monthName = driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]")).getText();
	
		while (!monthName.contains(monthYear))
		{
			driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-next-button')]//div[contains(@class, 'mat-button-ripple')]")).click();
			monthName = driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]")).getText();
		}
		//button[@class='mat-calendar-next-button mat-icon-button']//div[contains(@class, 'mat-button-ripple')]

	int daycount = driver.findElements(By.tagName("td")).size(); // Get the daycount from the calendar
	for (int i = 0; i < daycount; i++) {
		String date = driver.findElements(By.tagName("td")).get(i).getText();
		if (date.contains(tomorrowsDate)) {
			driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
			break;
		}
		
	}
	
	return null;
	}
	
	public static Object ApptCheckinInCOG(String memberName, String appointmentName, String username) throws InterruptedException{
		
		driver.get("https://ess-web-future2.test-jfisoftware.com:8945/CompeteOnTheGo/home/index/236");
	
	driver.findElement(By.id("UserName")).sendKeys("bhagya");
	driver.findElement(By.id("Password")).sendKeys("111");
	driver.findElement(By.id("submit")).click();
	Thread.sleep(1000);
	Select s= new Select(driver.findElement(By.id("ddl_clubSelection")));
	s.selectByVisibleText("Studio Jonas");
	driver.findElement(By.id("submit")).click();
	Thread.sleep(3000);
	WebElement FrontDeskTile = driver.findElement(By.xpath("(//div[@class='tile'])[1]"));
	int count = FrontDeskTile.findElements(By.tagName("a")).size();
	for (int i = 0; i<count; i++)
	{System.out.println(FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href"));
		if (FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href").contains("CheckIn"))
			{
			Thread.sleep(1000);
			FrontDeskTile.findElements(By.tagName("a")).get(i).findElement(By.tagName("i")).click();
			break;
	}
	}
	
	driver.findElement(By.id("txt_searchLastName")).sendKeys(memberName);
	driver.findElement(By.id("btn_search")).click();
	driver.findElement(By.xpath("//i[@class='fa fa-check']")).click();
	driver.findElement(By.xpath("//i[@class='fa fa-thumbs-up mrs']")).click();
	
	List <WebElement> CheckInOptions = driver.findElements(By.tagName("tr"));
	int checkboxCount = CheckInOptions.size();
	for (int j= 0; j<checkboxCount; j++)
	{
		String Text = CheckInOptions.get(j).getText();
		if (Text.contains(appointmentName))
		{
			CheckInOptions.get(j).findElement(By.className("checkbox")).click();
			break;
		}
	}
	driver.findElement(By.xpath("//i[@class='fa fa-thumbs-up mrs']")).click();
	driver.findElement(By.xpath("//a[@href='/CompeteOnTheGo/Account/Logoff']")).click();
	driver.get(prop.getProperty("EMELoginPage"));
	reusableMethods.activeMemberLogin(username, "Testing1!");
	return null;
	}

	public static Object  ConfirmAndCancelAppointmentNoFee(String tomorrowsDate, String startTime, String appointmentToBook) throws IOException, InterruptedException
	{
		reusableWaits.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
		int appointmentsCount = d.getMyAppts().size();

		for (int i = 0; i < appointmentsCount; i++) {
			if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

			{

				if (d.getMyAppts().get(i).getText().contains(startTime)) {
					
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
		AppointmentsPO a = new AppointmentsPO(driver);
		Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
		a.getEditApptCancelButton().click();
		WebElement wait2 = a.getEditApptProceedButton();
		while (!wait2.isEnabled())// while button is NOT(!) enabled
		{
//			Thread.sleep(200);
		}
		a.getEditApptProceedButton().click();
		boolean result1 = reusableWaits.popupMessageYesButton();
		if (result1 == true) {
//				Thread.sleep(500);	
		}
		a.getEditApptCancelYesButton().click();
//		
		Thread.sleep(2000);
		Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
		
		return null;
	}
	
}