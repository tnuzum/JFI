package EME;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class MemberLoginTest extends base {

	public reusableWaits rw;
	public reusableMethods rm;

	public MemberLoginTest() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

	private static Logger log = LogManager.getLogger(base.class.getName());

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());
		getEMEURL();
	}

	@Test(priority = 10, description = "Login Active Adult HOH Member")
	public void activeMember1Login() throws InterruptedException {
		rm.activeMember1Login();
		rw.waitForFamilyCount();
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember1_fullname"));
		Assert.assertEquals(d.getMyFamilyMemberCount().getText(), "5");
		rm.memberLogout();
	}

	@Test(priority = 15, description = "Login Active Adult non-HOH Member")
	public void activeMember2Login() throws InterruptedException {
		rm.activeMember2Login();
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember2_fullname"));
		// confirm My Family section is not shown
		Assert.assertFalse(
				rm.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));
		rm.memberLogout();
	}

	@Test(priority = 20, description = "Login Active Minor Member")
	public void activeMember3Login() throws InterruptedException {
		rm.activeMember3Login();
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember3_fullname"));
		// confirm My Family section is not shown
		Assert.assertEquals(false,
				rm.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));
		rm.memberLogout();
	}

	@Test(priority = 25, description = "Login Active HOH No Family Member")
	public void activeMember4Login() throws InterruptedException {
		rm.activeMember4Login();
		rw.waitForFamilyCount();
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember4_fullname"));
		// Assert.assertEquals(d.getMyFamilyMemberCount().getText(), "0");// This is no
		// more Valid as per PBI 171296
		Assert.assertEquals(false,
				rm.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));// as per
																												// PBI
																												// 171296
		rm.memberLogout();
	}

	@Test(priority = 35, description = "Login Active Adult one Family Member")
	public void activeMember5Login() throws InterruptedException {
		rm.activeMember5Login();
		rw.waitForFamilyCount();
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember5_fullname"));
		Assert.assertEquals(d.getMyFamilyMemberCount().getText(), "3");
		rm.memberLogout();
	}

	@Test(priority = 30, description = "Login Member in Collections")
	public void collectionsMember1Login() throws InterruptedException {
		rm.collectionsMember1Login();
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("collectionsMember1_fullname"));
//		Assert.assertEquals(false, rm.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));//confirm My Family section is not shown
		rm.memberLogout();
	}

	@Test(priority = 40, description = "Login Prospect Member")
	public void prospectMember1Login() throws InterruptedException {
		rm.prospectMember1Login();
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("prospectMember1_fullname"));
//      This assert was checking for no My Family section, but since 7.28 that section is showing for prospects too
//		Assert.assertEquals(false, rm.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));//confirm My Family section is not shown
		rm.memberLogout();
	}

	@Test(priority = 45, description = "Login Freeze Member")
	public void freezeMember1Login() throws InterruptedException {
		rm.activeMemberLogin("freezemember", "Testing1!");
		log.info("Freeze member logged in");
		System.out.println("Freeze member logged in");
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), "FreezeMember Auto");
		// confirm My Family section is not shown
		Assert.assertEquals(false,
				rm.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));
		rm.memberLogout();
	}

	@Test(priority = 50, description = "Login Terminate Member")
	public void terminatedMember1Login() throws InterruptedException {
		rm.activeMemberLogin("terminate", "Testing1!");
		log.info("Terminated member logged in");
		System.out.println("Terminated member logged in");
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), "Terminate Auto");
		// confirm My Family section is not shown
		Assert.assertEquals(false,
				rm.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));
		rm.memberLogout();
	}

//	@AfterTest
	@AfterClass
	public void teardown() {
		driver.close();
		driver = null;
	}

}
