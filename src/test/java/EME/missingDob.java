package EME;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import pageObjects.LoginPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class missingDob extends base {

	public reusableWaits rw;
	public reusableMethods rm;

	public missingDob() {
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

	@Test(priority = 1, description = "nonHohMActiveEwaiver will not show family members")
	public void nonHohMeberWithMissingDob() throws InterruptedException {
		rm.nonHohActiveEwaiver();
		LoginPO l = new LoginPO(driver);

		// prop.getProperty("nonHohActiveEwaiver_username"));
		Assert.assertEquals(l.getmissingDobMemberName().getText(), "Dandekar3, Seema3");
		l.getcancelButton().click();
	}

	@Test(priority = 2, description = "HohMActiveEwaiver will show family members")
	public void HohMeberWithMissingDob() throws InterruptedException {
		rm.HohActiveEwaiver();
		LoginPO l = new LoginPO(driver);
		System.out.println(l.getmissingDobMemberNames().size());
		// prop.getProperty("nonHohActiveEwaiver_username"));
		Assert.assertEquals(l.getmissingDobMemberNames().size(), 3);
		l.getcancelButton().click();
	}

	@Test(priority = 3, description = "Hoh NoActiveEwaiver On member's home club will not display missingDOB window", enabled = false)
	public void HohNotActiveEwaiverMeberWithMissingDob() throws InterruptedException {
		rm.HohNOtActiveEwaiver();
		DashboardPO d = new DashboardPO(driver);
		// System.out.println(l.getmissingDobMemberNames().size());
		// prop.getProperty("nonHohActiveEwaiver_username"));
		// Assert.assertEquals(l.getmissingDobMemberNames().size(), 3);
		// l.getcancelButton().click();
		Assert.assertEquals(d.getDashboardTitle().getText(), "Dashboard");
		log.info("Dashboard title verified");
	}

	@AfterClass
	public void teardown() {
		driver.quit();
		driver = null;
	}

}
