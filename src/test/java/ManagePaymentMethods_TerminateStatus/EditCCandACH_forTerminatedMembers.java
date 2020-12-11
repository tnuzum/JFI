package ManagePaymentMethods_TerminateStatus;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import pageObjects.ManagePayMethodsPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class EditCCandACH_forTerminatedMembers extends base {
	public reusableWaits rw;
	public reusableMethods rm;

	public static DashboardPO d;
	public static ManagePayMethodsPO mp;

	public EditCCandACH_forTerminatedMembers() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

	@BeforeTest
	public void initialize() throws Exception, IOException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		d = new DashboardPO(driver);
		mp = new ManagePayMethodsPO(driver);
		getEMEURL();
	}

	@Test(priority = 1, description = "Editing payment method for Terminated member")
	public void EditCC_ForTerminatedMembers() throws IOException, InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		rm.activeMemberLogin("Seema", "June@123");
		rm.openSideMenuIfNotOpenedAlready();
		Assert.assertTrue(d.getMenuMyAccount().isDisplayed());
		d.getMenuMyAccount().click();
		d.getMenuManagePmntMethods().click();
		jse.executeScript("arguments[0].click();", mp.getEditPaymentMethodsButton().get(0));
		mp.getEditNameOnCard().clear();
		mp.getEditNameOnCard().sendKeys("Seem");
		Thread.sleep(2000);
		mp.geteditExpiremonth().clear();
		mp.geteditExpiremonth().sendKeys("10");
		Thread.sleep(2000);
		mp.geteditExpireyear().clear();
		mp.geteditExpireyear().sendKeys("25");

		jse.executeScript("arguments[0].scrollIntoView(true);", mp.getSignaturePad().get(0));
		Thread.sleep(2000);
		Actions a = new Actions(driver);
		a.moveToElement(mp.getSignaturePad().get(0)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10).release()
				.build().perform();
		Thread.sleep(2000);
		jse.executeScript("arguments[0].click();", mp.getEditIAgreeCheckbox());
		jse.executeScript("arguments[0].click();", mp.getSaveChangesButtonCC());

		rw.waitForAcceptButton();
		System.out.println(mp.getPopupConfirmation1().getText());
		Assert.assertEquals("CARD UPDATED", mp.getPopupConfirmation1().getText());

		mp.getPopupConfirmationButton().click();
		Thread.sleep(2000);

	}

	@Test(priority = 2, description = "Editing ACH for Terminated members")
	public void EditACH_ForTerminatedMembers() throws IOException, InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		jse.executeScript("arguments[0].scrollIntoView(true);", mp.getEditPaymentMethodsButton().get(1));
		mp.getEditPaymentMethodsButton().get(1).click();
		mp.getEditAccountHolder().clear();
		mp.getEditAccountHolder().sendKeys("Seema1");
		mp.getEditUSRoutingNumber().clear();
		mp.getEditUSRoutingNumber().sendKeys(prop.getProperty("USBankRoutingNumber"));

		jse.executeScript("arguments[0].scrollIntoView(true);", mp.getSignaturePad().get(0));
		Thread.sleep(2000);
		Actions a = new Actions(driver);
		a.moveToElement(mp.getSignaturePad().get(0)).clickAndHold().moveByOffset(30, 10).moveByOffset(80, 10).release()
				.build().perform();
		Thread.sleep(2000);

		jse.executeScript("arguments[0].click();", mp.getIAgreeCheckboxEditACH());
		jse.executeScript("arguments[0].click();", mp.getSaveChangeButton());
		rw.waitForAcceptButton();
		Assert.assertEquals("BANK ACCOUNT UPDATED", mp.getPopupConfirmation1().getText());
		System.out.println(mp.getPopupConfirmation1().getText());
		mp.getPopupConfirmationButton().click();

	}

	@Test(priority = 3, description = "Deleting CC and ACH for terminated members")
	public void DeleteFOPsFromCOG() throws IOException, InterruptedException {

		rm.deleteFOPInCOG("1147810", "Jonas Health and Wellness", "1111", "No", "");
		rm.deleteFOPInCOG("1147810", "Jonas Health and Wellness", "3210", "No", "");

	}

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
