package EME_EnvURL;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.AcctHistoryPO;
import pageObjects.AppointmentsPO;
import pageObjects.CartPO;
import pageObjects.ClassSignUpPO;
import pageObjects.DashboardPO;
import pageObjects.ForgotPasswordPO;
import pageObjects.ForgotUsernamePO;
import pageObjects.LoginPO;
import pageObjects.ManageFamilyPO;
import pageObjects.ManageProfilePO;
import pageObjects.PaymentPO;
import pageObjects.ShopPackagesPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class PageLaunchTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	DashboardPO d = new DashboardPO(driver);

	public reusableWaits rw;
	public reusableMethods rm;

	public PageLaunchTest() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

//	@BeforeTest
	@BeforeClass
	@Parameters({ "EMELoginPage" })
	public void initialize(String EMELoginPage) throws InterruptedException, IOException {
//		public void initialize() throws InterruptedException, IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized");
		// String EMELoginPage = prop.getProperty("EMELoginPage");
		driver.get(EMELoginPage);
		rm.activeMember1Login();
		rw.waitForDashboardLoaded();
	}

	@Test(priority = 20)
	public void MyPackagesButtonTest() throws InterruptedException {
		d.getMyPackagesButton().click();
		d.getMyPackagesShopPackages().click();
		ShopPackagesPO p = new ShopPackagesPO(driver);
		Assert.assertEquals(p.getPageHeader().getText(), "Shop Packages");
		log.info("Shop Packages Page Header Verified");
		rm.returnToDashboard();
	}

	@Test(priority = 25, description = "This test is no longer valid as Cart is removed from EME", enabled = false)
	public void CartButtonTest() throws InterruptedException {
		d.getCartButton().click();
		CartPO c = new CartPO(driver);
		Assert.assertEquals(c.getPageHeader().getText(), "Shopping Cart");
		log.info("Shopping Cart Page Header Verified");
		rm.returnToDashboard();
	}

	@Test(priority = 30)
	public void AcctHistoryButtonTest() throws InterruptedException {
		d.getMyAccountAccountHistory().click();
		AcctHistoryPO a = new AcctHistoryPO(driver);
		Assert.assertEquals(a.getPageHeader().getText(), "Account History");
		log.info("Account History Page Header Verified");
		rm.returnToDashboard();
	}

	@Test(priority = 35)
	public void PayNowButtonTest() throws InterruptedException {
		d.getMyAccountPayNow().click();
		PaymentPO pb = new PaymentPO(driver);
		Assert.assertEquals(pb.getPageHeader().getText(), "Pay Balance");
		log.info("Pay Balance Page Header Verified");
		rm.returnToDashboard();
	}

	@Test(priority = 40)
	public void ScheduleClassesButtonTest() throws InterruptedException {
		d.getMyClassesScheduleButton().click();// Accessing from Dashboard
		ClassSignUpPO cs = new ClassSignUpPO(driver);
		Assert.assertEquals(cs.getPageHeader().getText(), "Select Classes");
		log.info("Select Classes Page Header Verified");
//		d.getDashboardButton().click();
		rm.returnToDashboard();
		d.getMenuMyActivies().click();// Accessing from left pane menu
		d.getMenuClassSchedule().click();

		Assert.assertEquals(cs.getPageHeader().getText(), "Select Classes");
		log.info("Manage Profile Page Header Verified");
		rm.returnToDashboard();
	}

	@Test(priority = 45)
	public void ScheduleApptsButtonTest() throws InterruptedException {
//		d.getMyApptsScheduleButton().click();//Accessing from Dashboard
//			rm.catchErrorMessage();
		AppointmentsPO a = new AppointmentsPO(driver);
//		Assert.assertEquals(a.getPageHeader().getText(),"Appointments");
//		log.info("Appointments Page Header Verified");
//		d.getDashboardButton().click();
		d.getMenuMyActivies().click();// Accessing from left pane menu

		d.getMenuBookAppointment().click();
// The pageHeader changed in 7.28
//		Assert.assertEquals(a.getPageHeader().getText(),"Appointments");
		Assert.assertEquals(a.getPageHeader().getText(), "Appointments");
		log.info("Appointments Page Header Verified");
		rm.returnToDashboard();
		rw.waitForDashboardLoaded();
	}

	@Test(priority = 50)
	public void ManageFamilyButtonTest() throws InterruptedException {
		rw.waitForDashboardLoaded();
		d.getMyFamilyManageButton().click();
		ManageFamilyPO a = new ManageFamilyPO(driver);
		WebElement w = a.getPageHeader();
		while (!w.getText().equals("Manage Family")) {
			Thread.sleep(2000);
		}
		Assert.assertEquals(a.getPageHeader().getText(), "Manage Family");
		log.info("Manage Family Page Header Verified");
		rm.returnToDashboard();
		rw.waitForDashboardLoaded();
	}

	@Test(priority = 55)
	public void EditMyInfoButtonTest() throws InterruptedException {
		d.getMyInfoEditButton().click();
		ManageProfilePO a = new ManageProfilePO(driver);
		Assert.assertEquals(a.getPageHeader().getText(), "Manage Profile");
		log.info("Manage Profile Page Header Verified");
		rm.returnToDashboard();
		rw.waitForDashboardLoaded();
	}

	@Test(priority = 60)
	public void PrivacyPolicyLinkTest() throws InterruptedException {
		rw.waitForDashboardLoaded();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(d.getPrivacyPolicyLink()));
		log.info("element is clickable");
		System.out.println("element is clickable");

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", d.getPrivacyPolicyLink());
		/*
		 * Actions a = new Actions(driver);
		 * a.moveToElement(d.getPrivacyPolicyLink()).click().build().perform();
		 */
		log.info("element is clicked");
		System.out.println("element is clicked");
		Thread.sleep(3000);
		Assert.assertEquals(driver.getWindowHandles().size(), 2);
		Set<String> ids = driver.getWindowHandles();
		Iterator<String> it = ids.iterator();
		String parentid = it.next();
		String childid = it.next();
		driver.switchTo().window(childid); // Switch to Privacy Policy window
		Thread.sleep(1000);
		System.out.println(driver.getTitle());
		Assert.assertEquals(driver.getTitle(), "Privacy Policy - Jonas Fitness");
		driver.switchTo().window(parentid); // Switch back to EME window
		Thread.sleep(1000);
		Assert.assertEquals(driver.getTitle(), "Dashboard");

	}

	@Test(priority = 65)
	public void ForgotUsernameButtonTest() throws InterruptedException {
		d.getLogoutButton().click();
		Thread.sleep(2000);
		LoginPO l = new LoginPO(driver);
		l.getForgotUsername().click();

		ForgotUsernamePO fu = new ForgotUsernamePO(driver);
		WebElement w = fu.getPageHeader();
		while (!w.isDisplayed()) {
			Thread.sleep(2000);
		}
		Assert.assertEquals(fu.getPageHeader().getText(), "Forgot your Username?");
		log.info("Forgot Username Page Header Verified");
		fu.getCancelButton().click();
	}

	@Test(priority = 70)
	public void ForgotPasswordButtonTest() throws InterruptedException {
		LoginPO l = new LoginPO(driver);
		l.getForgotPassword().click();
		ForgotPasswordPO fp = new ForgotPasswordPO(driver);
		WebElement w = fp.getPageHeader();
		while (!w.isDisplayed()) {
			Thread.sleep(2000);
		}
		Assert.assertEquals(fp.getPageHeader().getText(), "Forgot your Password?");
		log.info("Forgot Password Page Header Verified");
		fp.getCancelButton().click();
	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}

}
