package resources;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import pageObjects.CalendarPO;
import pageObjects.DashboardPO;
import pageObjects.ErrorMessagesPO;
import pageObjects.LoginPO;
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
				reusableWaits.waitForDashboardLoaded();
				return null;
			}

	public static String collectionsMember1Login() throws InterruptedException {
		reusableWaits.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("collectionsMember1_username"));
		l.getuserPassword().sendKeys(prop.getProperty("collectionsMember1_password"));
		l.getLoginButton().click();
		reusableWaits.waitForDashboardLoaded();
		return null;
	}

	public static String prospectMember1Login() throws InterruptedException {
		reusableWaits.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("prospectMember1_username"));
		l.getuserPassword().sendKeys(prop.getProperty("prospectMember1_password"));
		l.getLoginButton().click();
		reusableWaits.waitForDashboardLoaded();
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
		System.out.println(enrolled);
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='class-table-container']")));
		if (enrolled == true)
		{
		
			d.getMyClassesClass1GearButton().click();
		Thread.sleep(2000);
		d.getmyClassesUnenrollButton().click();
		Thread.sleep(2000);
		UnenrollPO u = new UnenrollPO(driver);
			u.getUnenrollButton().click();
				Thread.sleep(2000);
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			}
		else
		{
			System.out.println("Not enrolled already");
		}
	 
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
		u.getUnenrollButton().click();
		Thread.sleep(2000);
		u.getUnenrollConfirmYesButton().click();
		Thread.sleep(2000);
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
		if (PP.getPackagesList().get(j).getText().contains(packageName)) {
			String[] unitCount = PP.getUnitsCount().get(j).getText().split(" ");
			String formattedUnitCount = unitCount[0].trim();
			IntUnitCount = Integer.parseInt(formattedUnitCount);
			PP.getMyPackagesButton().click();
		}
	}
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

}