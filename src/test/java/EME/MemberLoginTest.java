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
	private static Logger log = LogManager.getLogger(base.class.getName());

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test(priority = 10, description = "Login Active Adult HOH Member")
	public void activeMember1Login() throws InterruptedException {
		reusableMethods.activeMember1Login();
		reusableWaits.waitForFamilyCount();
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember1_fullname"));
		Assert.assertEquals(d.getMyFamilyMemberCount().getText(), "5");
		reusableMethods.memberLogout();
	}

	@Test(priority = 15, description = "Login Active Adult non-HOH Member")
	public void activeMember2Login() throws InterruptedException {
		reusableMethods.activeMember2Login();
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember2_fullname"));
		// confirm My Family section is not shown
		Assert.assertFalse(reusableMethods
				.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));
		reusableMethods.memberLogout();
	}

	@Test(priority = 20, description = "Login Active Minor Member")
	public void activeMember3Login() throws InterruptedException {
		reusableMethods.activeMember3Login();
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember3_fullname"));
		// confirm My Family section is not shown
		Assert.assertEquals(false, reusableMethods
				.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));
		reusableMethods.memberLogout();
	}

	@Test(priority = 25, description = "Login Active Adult No Family Member")
	public void activeMember4Login() throws InterruptedException {
		reusableMethods.activeMember4Login();
		reusableWaits.waitForFamilyCount();
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember4_fullname"));
		Assert.assertEquals(d.getMyFamilyMemberCount().getText(), "0");
		reusableMethods.memberLogout();
	}

	@Test(priority = 35, description = "Login Active Adult one Family Member")
	public void activeMember5Login() throws InterruptedException {
		reusableMethods.activeMember5Login();
		reusableWaits.waitForFamilyCount();
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("activeMember5_fullname"));
		Assert.assertEquals(d.getMyFamilyMemberCount().getText(), "3");
		reusableMethods.memberLogout();
	}

	@Test(priority = 30, description = "Login Member in Collections")
	public void collectionsMember1Login() throws InterruptedException {
		reusableMethods.collectionsMember1Login();
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("collectionsMember1_fullname"));
//		Assert.assertEquals(false, reusableMethods.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));//confirm My Family section is not shown
		reusableMethods.memberLogout();
	}

	@Test(priority = 40, description = "Login Prospect Member")
	public void prospectMember1Login() throws InterruptedException {
		reusableMethods.prospectMember1Login();
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), prop.getProperty("prospectMember1_fullname"));
//      This assert was checking for no My Family section, but since 7.28 that section is showing for prospects too
//		Assert.assertEquals(false, reusableMethods.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));//confirm My Family section is not shown
		reusableMethods.memberLogout();
	}

	@Test(priority = 45, description = "Login Prospect Member")
	public void freezeMember1Login() throws InterruptedException {
		reusableMethods.activeMemberLogin("freezemember", "Testing1!");
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), "FreezeMember Auto");
		// confirm My Family section is not shown
		Assert.assertEquals(false, reusableMethods
				.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));
		reusableMethods.memberLogout();
	}

	@Test(priority = 50, description = "Login Prospect Member")
	public void terminatedMember1Login() throws InterruptedException {
		reusableMethods.activeMemberLogin("terminate", "Testing1!");
		DashboardPO d = new DashboardPO(driver);
		Assert.assertEquals(d.getMyInfoMemberName().getText(), "Terminate Auto");
		// confirm My Family section is not shown
		Assert.assertEquals(false, reusableMethods
				.isElementPresent(By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]")));
		reusableMethods.memberLogout();
	}

//	@AfterTest
	@AfterClass
	public void teardown() {
		driver.close();
		driver = null;
	}

}
