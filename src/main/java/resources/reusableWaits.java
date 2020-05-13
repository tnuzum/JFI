package resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.AppointmentsPO;
import pageObjects.DashboardPO;
import pageObjects.LoginPO;
import pageObjects.PaymentPO;

public class reusableWaits extends base {

	static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	static Date date = new Date();
	static String DateTime = dateFormat.format(date);

	public static String waitForDashboardLoaded() throws InterruptedException {

		boolean error = reusableMethods.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));

		if (error == true) {
			driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
			System.out.println("Error was present");
		}

		// Check 1: wait for MEMBER NAME element
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
//		wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/div/div[2]/h3")));
		wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//memberinfo//h3[@class = 'no-margins']")));
//		System.out.println(DateTime+" INFO: MEMBER NAME Element is now present");
		// Check 2: wait for MEMBER NAME element to not be blank
		DashboardPO d = new DashboardPO(driver);
		WebElement wait2 = d.getMyInfoMemberName();
		while (wait2.getText().isBlank()) {
//			System.out.println(DateTime+" INFO:Dashboard Wait - Waiting 500ms for MEMBER NAME element to populate");
			Thread.sleep(500);
			wait2.getText();
		}
		// Check 3: wait for TOTAL CHARGES element

		wait1.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/h2")));
//		System.out.println(DateTime+" INFO: TOTAL CHARGES Element is now present");
		// Check 4: wait for TOTAL CHARGES element to not be blank
		WebElement wait4 = d.getMyAccountBalance();
		while (wait4.getText().isBlank()) {
//			System.out.println(DateTime+" INFO: Waiting 500ms for TOTAL CHARGES element to populate");
			Thread.sleep(500);
			wait4.getText();
		}
//		wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id = 'swal2-content']")));
//		wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[(contains@class, 'swal2-center')]")));
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMyAccountAccountHistory()));
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMyApptsScheduleButton()));
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMyClassesScheduleButton()));
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMyCoursesEventsScheduleButton()));
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMyAccountPayNow()));
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMyInfoEditButton()));
		error = reusableMethods.isElementPresent(By.xpath("//div[@class='swal2-actions']/button[1]"));

		if (error == true) {
			driver.findElement(By.xpath("//div[@class='swal2-actions']/button[1]")).click();
			System.out.println("Error was present");
		}

		return null;

	}

	public static String waitForDashboardLoaded1() throws InterruptedException {
		// Check 1: wait for MEMBER NAME element
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		wait1.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/div/div[2]/h3")));
//			wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[@class='no-margins']")));
//			System.out.println(DateTime+" INFO: MEMBER NAME Element is now present");
		// Check 2: wait for MEMBER NAME element to not be blank
		DashboardPO d = new DashboardPO(driver);
		WebElement wait2 = d.getMyInfoMemberName();
		while (wait2.getText().isBlank()) {
//				System.out.println(DateTime+" INFO:Dashboard Wait - Waiting 500ms for MEMBER NAME element to populate");
			Thread.sleep(500);
			wait2.getText();
		}
		// Check 3: wait for TOTAL CHARGES element
		WebDriverWait wait3 = new WebDriverWait(driver, 10);
		wait3.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/h2")));
//			System.out.println(DateTime+" INFO: TOTAL CHARGES Element is now present");
		// Check 4: wait for TOTAL CHARGES element to not be blank
		WebElement wait4 = d.getMyAccountBalance();
		while (wait4.getText().isBlank()) {
//				System.out.println(DateTime+" INFO: Waiting 500ms for TOTAL CHARGES element to populate");
			Thread.sleep(500);
			wait4.getText();
		}

		return null;

	}

	public static String waitForFamilyCount() throws InterruptedException {
		// Check 1: wait for member name element
		WebDriverWait wait1 = new WebDriverWait(driver, 10);
		wait1.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//div[@class='homeComponent']//familymembercount//div//div//h2")));
//		System.out.println(DateTime+" INFO: Element is now present");
		// Check 2: wait for member name element to not be blank
		DashboardPO d = new DashboardPO(driver);
		WebElement wait = d.getMyFamilyMemberCount();
		while (wait.getText().isBlank()) {
//			System.out.println(DateTime+" INFO: Waiting 500ms for Family Member Count element to populate");
			Thread.sleep(500);
			wait.getText();
		}
		return null;

	}

	public static String waitForPaymentSubmitButton() throws InterruptedException {
		// Check 1: wait for member name element
		WebDriverWait wait1 = new WebDriverWait(driver, 10);
		wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='submit']")));
//		System.out.println(DateTime+" INFO: Element is now present");
		// Check 2: wait for member name element to not be blank
		PaymentPO p = new PaymentPO(driver);
		WebElement wait = p.getSubmitButton();
		while (!wait.isEnabled()) {
//			System.out.println(DateTime+" INFO: Waiting 500ms for Submit button to be enabled");
			Thread.sleep(500);
			wait.getText();
		}
		return null;

	}

	public static String waitForLoginLoginButton() throws InterruptedException {
		LoginPO l = new LoginPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);

		wait.until(ExpectedConditions.visibilityOf(l.getLoginButton()));
		wait.until(ExpectedConditions.elementToBeClickable(l.getuserName()));

		log.info("Login button visible");
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[type='submit']")));
//			System.out.println(DateTime+" INFO: Element is now present");

		WebElement n = l.getLoginButton();
		while (!n.isEnabled())// while button is NOT(!) enabled
		{
			Thread.sleep(500);
//				System.out.println(DateTime+" INFO: waiting 500ms for element to be enabled");
		}
		return null;
	}

	public static String waitForAcceptButton() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='swal2-actions']/button[1]")));
		System.out.println(DateTime + " INFO: Element is now present");
		/*
		 * PaymentPO p = new PaymentPO(driver); WebElement n =
		 * p.getPopupConfirmationButton(); while (!n.isDisplayed())//while button is
		 * NOT(!) enabled { Thread.sleep(500); System.out.println(
		 * DateTime+" INFO: waiting 500ms for Accept Button to be enabled"); }
		 */
		return null;
	}

	public static boolean loadingAvailability() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			AppointmentsPO a = new AppointmentsPO(driver);
			a.getloadingAvailabilityMessage();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static boolean popupMessageYesButton() {
		try {
			AppointmentsPO a = new AppointmentsPO(driver);
			a.getEditApptCancelYesButton();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static String linksToBeClickable() {

		int count = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < count; i++) {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElements(By.tagName("a")).get(i)));
		}
		return null;

	}

	public static String DashboardlinksToBeClickable() {

		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMyAccountAccountHistory()));
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMyApptsScheduleButton()));
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMyClassesScheduleButton()));
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMyCoursesEventsScheduleButton()));
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMyAccountPayNow()));
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMyInfoEditButton()));
		return null;

	}

}
