package resources;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import pageObjects.DashboardPO;
import pageObjects.ErrorMessagesPO;
import pageObjects.LoginPO;
import pageObjects.PaymentPO;
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
		boolean enrolled = reusableMethods.isElementPresent(By.xpath("//div[@class='class-table-container']"));
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


}
