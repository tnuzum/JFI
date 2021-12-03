package manageProfile;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;
import pageObjects.DashboardPO;
import pageObjects.ManageProfilePO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class ChangeInterest extends base {

	private static String interest;

	public reusableMethods rm;
	public reusableWaits rw;

	public ChangeInterest() {
		rm = new reusableMethods();
		rw = new reusableWaits();
	}

	@BeforeClass
	public void initialize() throws Exception, IOException {

		driver = initializeDriver();

		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		System.out.println("Driver Initialized for " + this.getClass().getSimpleName());

		rm.setDriver(driver);
		rw.setDriver(driver);

		interest = prop.getProperty("interest");

		getEMEURL();

	}

	@Test(priority = 1)
	public void changeInterest() throws IOException, InterruptedException {

		rm.activeMemberLogin("rauto", "Testing1!");
		rm.openSideMenuIfNotOpenedAlready();
		ManageProfilePO m = new ManageProfilePO(driver);
		DashboardPO d = new DashboardPO(driver);
		d.getMenuMyAccount().click();
		Thread.sleep(1000);

		d.getMenuManageProfile().click();
		Thread.sleep(1000);
		rm.myProfileLogin("rauto", "Testing1!");
		m.getinterestsButton().click();
		m.getSelectAllThaApply().click();
		System.out.println(m.getInterestCheckBoxes().size());

		for (int i = 0; i < m.getInterestCheckBoxes().size(); i++) {

			System.out.println(m.getInterestCheckBoxes().get(i).findElement(By.tagName("span")).getText());

			if (m.getInterestCheckBoxes().get(i).findElement(By.tagName("span")).getText().equals(interest)) {
				m.getInterestCheckBoxes().get(i).findElement(By.tagName("mat-pseudo-checkbox")).click();
				break;
			}

		}

		driver.findElement(By.xpath("//body")).click();

//		JavascriptExecutor jse = (JavascriptExecutor) driver;
//		jse.executeScript("arguments[0].click();", m.getSaveInterests());
		m.getSaveInterests().click();

		rw.waitForAcceptButton();

		Assert.assertEquals(m.getPopupText().getText(), "Saved!");

		m.getPopupConfirmationButton().click();

		rm.returnToDashboard();
		rw.waitForDashboardLoaded();
	}

	@Test(priority = 2)
	public void verifyInterest() throws IOException, InterruptedException {

		rm.openSideMenuIfNotOpenedAlready();
		ManageProfilePO m = new ManageProfilePO(driver);
		DashboardPO d = new DashboardPO(driver);

		while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
			d.getMenuMyAccount().click();
		}

		Thread.sleep(1000);
		d.getMenuManageProfile().click();
		Thread.sleep(1000);
		rm.myProfileLogin("rauto", "Testing1!");
		m.getinterestsButton().click();

		m.getSelectAllThaApply().click();
		System.out.println(m.getInterestCheckBoxes().size());

		for (int i = 0; i < m.getInterestCheckBoxes().size(); i++) {

			System.out.println(m.getInterestCheckBoxes().get(i).findElement(By.tagName("span")).getText());

			if (m.getInterestCheckBoxes().get(i).findElement(By.tagName("span")).getText().equals(interest)) {
				// verify that Interest is saved
				Assert.assertTrue(
						m.getInterestCheckBoxes().get(i).findElement(By.tagName("mat-pseudo-checkbox")).isEnabled());

				// De-select the Interest
				m.getInterestCheckBoxes().get(i).findElement(By.tagName("mat-pseudo-checkbox")).click();
				break;
			}

		}

		driver.findElement(By.xpath("//body")).click();

		m.getSaveInterests().click();

		rw.waitForAcceptButton();

		Assert.assertEquals(m.getPopupText().getText(), "Saved!");

		m.getPopupConfirmationButton().click();

		rm.memberLogout();
	}

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}
}
