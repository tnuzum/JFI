package resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import pageObjects.PaymentMethodsPO;
import pageObjects.PaymentPO;
import pageObjects.PurchaseConfirmationPO;
import pageObjects.ShopPackagesPO;
import pageObjects.ThankYouPO;
import pageObjects.UnenrollPO;

public class reusableMethods extends base {
	public reusableWaits rw;

	public reusableMethods() {

		rw = new reusableWaits();

	}

	public void setDriver(WebDriver wd) {
		driver = wd;
		rw.setDriver(wd);
	}

	public String nonHohActiveEwaiver() throws InterruptedException {

		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("nonHohActiveEwaiver_username"));
		l.getuserPassword().sendKeys(prop.getProperty("nonHohActiveEwaiver_password"));
		l.getLoginButton().click();
		Thread.sleep(1000);

		return null;
	}

	public String HohActiveEwaiver() throws InterruptedException {

		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("HohActiveEwaiver_username"));
		l.getuserPassword().sendKeys(prop.getProperty("HohActiveEwaiver_password"));
		l.getLoginButton().click();
		Thread.sleep(1000);

		return null;
	}

	public String HohNOtActiveEwaiver() throws InterruptedException {

		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("HohNotActiveEwaiver_username"));
		l.getuserPassword().sendKeys(prop.getProperty("HohNotActiveEwaiver_password"));
		l.getLoginButton().click();
		Thread.sleep(1000);

		return null;
	}

	public String activeMember1Login() throws InterruptedException {

		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember1_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember1_password"));
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded(prop.getProperty("memberDOB"));
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember2Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember2_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember2_password"));
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded(prop.getProperty("memberDOB"));
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember3Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember3_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember3_password"));
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded(prop.getProperty("memberDOB"));
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember4Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember4_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember4_password"));
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded(prop.getProperty("memberDOB"));
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember5Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember5_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember5_password"));
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded(prop.getProperty("memberDOB"));
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember6Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember6_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember6_password"));
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded(prop.getProperty("memberDOB"));
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember7Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember7_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember7_password"));
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded(prop.getProperty("memberDOB"));
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember8Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember8_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember8_password"));
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded(prop.getProperty("memberDOB"));
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember9Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys("NoOAMember");
		l.getuserPassword().sendKeys("Testing1!");
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded(prop.getProperty("memberDOB"));
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember10Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys("NoCCMember");
		l.getuserPassword().sendKeys("Testing1!");
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded(prop.getProperty("memberDOB"));
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember11Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys("NoOANoCCMember");
		l.getuserPassword().sendKeys("Testing1!");
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded(prop.getProperty("memberDOB"));
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMemberLogin(String username, String password) throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().click();
		log.info("User name element clicked");
		l.getuserName().clear();
		log.info("User name element cleared");
		l.getuserName().sendKeys(username);
		log.info("User name entered");
		l.getuserPassword().sendKeys(password);
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded(prop.getProperty("memberDOB"));
		rw.waitForDashboardLoaded1();
		return null;
	}

	public String activeGridMemberLogin(String username, String password) throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().click();
		log.info("User name element clicked");
		l.getuserName().clear();
		log.info("User name element cleared");
		l.getuserName().sendKeys(username);
		log.info("User name entered");
		l.getuserPassword().sendKeys(password);
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded("01/01/2000");
		rw.waitForDashboardLoaded1();
		return null;
	}

	public void enterDOBIfNeeded(String DOB) {

		LoginPO l = new LoginPO(driver);
		int count = l.getDobMembers().size();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				l.getDobInputFields().get(i).click();
				driver.findElement(By.tagName("button"));
				l.getDobInputFields().get(i).sendKeys(DOB);
			}
			l.getContinueButton().click();
		}

	}

	public String collectionsMember1Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("collectionsMember1_username"));
		l.getuserPassword().sendKeys(prop.getProperty("collectionsMember1_password"));
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded(prop.getProperty("memberDOB"));
		rw.waitForDashboardLoaded1();
		return null;
	}

	public String prospectMember1Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("prospectMember1_username"));
		l.getuserPassword().sendKeys(prop.getProperty("prospectMember1_password"));
		l.getLoginButton().click();
		Thread.sleep(1000);
		this.enterDOBIfNeeded(prop.getProperty("memberDOB"));
		rw.waitForDashboardLoaded1();
		return null;
	}

	public String memberLogout() throws InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		// Actions a = new Actions(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", d.getLogoutButton());
		Thread.sleep(1000);
		jse.executeScript("arguments[0].click();", d.getLogoutButton());
		// a.moveToElement(d.getLogoutButton()).click().build().perform();
		rw.waitForLoginLoginButton();
		return null;
	}

	public String returnToDashboard() throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		Thread.sleep(1000);
		String leftMenuOpen = d.getLeftMenu().getAttribute("style");
		while (leftMenuOpen.contains("hidden")) {
			jse.executeScript("arguments[0].click();", d.getMenuButton());
			leftMenuOpen = d.getLeftMenu().getAttribute("style");
		}
		wait.until(ExpectedConditions.elementToBeClickable(d.getDashboardButton()));

		jse.executeScript("arguments[0].click();", d.getDashboardButton());

//		d.getBreadcrumbDashboard().click();
		rw.waitForDashboardLoaded1();
		return null;
	}

	public boolean isElementPresent(By by) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isWebElementPresent(List<WebElement> E) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		int elementCount = E.size();
		if (elementCount > 0) {
			return true;

		} else {
			return false;
		}
	}

	public String unenrollFromClass() throws IOException, InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		boolean enrolled = this.isElementPresent(By.xpath("//classeswidget//div[@class='class-table-container']"));
//		System.out.println(enrolled);

		if (enrolled == true) {
			try {
				while (!d.getMyClassesClass1GearButton().isDisplayed()) {
					Thread.sleep(1000);
					System.out.println("Sleeping for 1 second");
				}

				WebDriverWait wait = new WebDriverWait(driver, 60);
				wait.until(ExpectedConditions
						.invisibilityOfElementLocated(By.xpath("//div[(contains@class, 'swal2-header')]")));
				wait.until(ExpectedConditions.elementToBeClickable(d.getMyClassesClass1GearButton()));
				jse.executeScript("arguments[0].click();", d.getMyClassesClass1GearButton());

				wait.until(ExpectedConditions.visibilityOf(d.getmyClassesUnenrollButton()));
				wait.until(ExpectedConditions.elementToBeClickable(d.getmyClassesUnenrollButton()));
				jse.executeScript("arguments[0].click();", d.getmyClassesUnenrollButton());
				Thread.sleep(1000);
				UnenrollPO u = new UnenrollPO(driver);
				wait.until(ExpectedConditions.visibilityOf(u.getUnenrollNoRefund()));
				wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollNoRefund()));
				jse.executeScript("arguments[0].click();", u.getUnenrollNoRefund());
				Thread.sleep(1000);
				rw.waitForAcceptButton();
				u.getUnenrollConfirmYesButton().click();
				rw.waitForAcceptButton();
				Thread.sleep(1000);
				Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
				u.getUnenrollConfirmYesButton().click();
				Thread.sleep(2000);

			}

			catch (java.lang.AssertionError ae) {
				System.out.println("assertion error");
				ae.printStackTrace();
				log.error(ae.getMessage(), ae);
				getScreenshot("Unenroll", driver);
				Assert.fail(ae.getMessage());
			}

			catch (org.openqa.selenium.NoSuchElementException ne) {
				System.out.println("No element present");
				ne.printStackTrace();
				log.error(ne.getMessage(), ne);
				getScreenshot("Unenroll", driver);
				Assert.fail(ne.getMessage());
			}

			catch (org.openqa.selenium.ElementClickInterceptedException eci) {
				System.out.println("Element Click Intercepted");
				eci.printStackTrace();
				log.error(eci.getMessage(), eci);
				this.catchErrorMessage();
				getScreenshot("Unenroll", driver);
				Assert.fail(eci.getMessage());
			} finally {
				this.returnToDashboard();
			}
		} else
			System.out.println("Not enrolled");

		return null;

	}

	public String unenrollFromCourse(String dsiredMonthYear) throws IOException, InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		CalendarPO cp = new CalendarPO(driver);
		try {

			Thread.sleep(2000);
			this.openSideMenuIfNotOpenedAlready();
			d.getMenuMyActivies().click();

			while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1")) {
				Thread.sleep(500);
			}
			WebDriverWait wait1 = new WebDriverWait(driver, 50);
			wait1.until(ExpectedConditions.elementToBeClickable(d.getMenuMyCalendar()));

			Actions a = new Actions(driver);
			a.click(d.getMenuMyCalendar()).build().perform();
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			// jse.executeScript("arguments[0].click();", d.getMenuMyCalendar());
			// d.getMenuMyCalendar().click();
			log.info("Menu My Calendar clicked");
			System.out.println("Menu My Calendar clicked");
			wait1.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

			cp.getCalendarViewLink().click();
			Thread.sleep(1000);
			String monthYear = cp.getMonthYear().getText();
			while (!monthYear.equals(dsiredMonthYear)) {
				cp.getRightArrow().click();
				wait1.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[@class = 'btn-group']//div[contains(@class, 'btn-white')][2]")));
				monthYear = cp.getMonthYear().getText();
			}
			Thread.sleep(1000);

			jse.executeScript("arguments[0].scrollIntoView(true);", cp.getCalDayBadge());
			Thread.sleep(1000);
			cp.getCalDayBadge().click();
			jse.executeScript("arguments[0].scrollIntoView(true);", cp.getCalEventTitle());
			Thread.sleep(1000);
			cp.getCalEventTitle().click();
			Thread.sleep(1000);
			cp.getUnEnrollBtn().click();
			Thread.sleep(1000);
			UnenrollPO u = new UnenrollPO(driver);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			while (u.getSearchingAcctHistMessage().size() != 0) {
				System.out.println("waiting for enrollment details to display");
				Thread.sleep(1000);
			}
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollNoRefund()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollNoRefund()));
			jse.executeScript("arguments[0].click();", u.getUnenrollNoRefund());
			Thread.sleep(1000);
			rw.waitForAcceptButton();
			u.getUnenrollConfirmYesButton().click();
			rw.waitForAcceptButton();
			Thread.sleep(1000);
			Assert.assertEquals("Unenrolled", u.getUnenrollConfirmMessage1().getText());
			u.getUnenrollConfirmYesButton().click();
			Thread.sleep(2000);

		}

		catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			log.error(ae.getMessage(), ae);
			getScreenshot("Unenroll", driver);
			Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			log.error(ne.getMessage(), ne);
			getScreenshot("Unenroll", driver);
			Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			log.error(eci.getMessage(), eci);
			this.catchErrorMessage();
			getScreenshot("Unenroll", driver);
			Assert.fail(eci.getMessage());
		} finally {

			this.returnToDashboard();
		}

		return null;
	}

	public String useNewCard() throws InterruptedException {
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

	private boolean catchErrorMessagePrivate()// only used by catchErrorMessage method below; not available from
	// other classes
	{
		try {
			driver.findElement(By.xpath("//*[text()='An Error Has Occurred']"));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public String catchErrorMessage() throws InterruptedException, IOException {
		boolean e = this.catchErrorMessagePrivate();

		if (e == true) {
			System.out.println("ERROR: An Error Has Occurred");
			ErrorMessagesPO er = new ErrorMessagesPO(driver);
			log.error("ERROR: An Error Has Occurred");
			getScreenshot("catchErrorMessage", driver);
			er.getOKButton().click();
			e = this.catchErrorMessagePrivate();
			if (e == false) {
				this.returnToDashboard();
			} else {
				System.out.println("Error is not going away");
				log.error("Error is not going away");
			}
//			Assert.assertFalse(e);
		}

		return null;
	}

	public String getDateFormater() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String DateTime = dateFormat.format(date);
		return DateTime;
	}

	public int getPackageUnits(String packageName) throws InterruptedException {

		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);

		// Note the package units
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", PP.getMyPackagesButton());
		int IntUnitCount = 0;
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class = 'dropdown-item']")));
		int packagesCount = PP.getPackagesList().size();
		for (int j = 0; j < packagesCount; j++) {
			wait.until(ExpectedConditions.textToBePresentInElement(PP.getPackagesList().get(j), "Remaining"));
			String text = PP.getPackagesList().get(j).getText();
			if (text.contains(packageName)) {
				String[] unitCount = PP.getUnitsCount().get(j).getText().split(" ");
				String formattedUnitCount = unitCount[0].trim();
				IntUnitCount = Integer.parseInt(formattedUnitCount);
				jse.executeScript("arguments[0].click();", PP.getMyPackagesButton());
				break;
			}
		}
		return IntUnitCount;
	}

	public int getPackageUnitsForMember(String packageName, String memberName) throws InterruptedException {

		DashboardPO d = new DashboardPO(driver);
		PackagesPO pp = new PackagesPO(driver);

		JavascriptExecutor jse = ((JavascriptExecutor) driver);

		this.openSideMenuIfNotOpenedAlready();

		// Note the package units
		d.getMenuMyAccount().click();
		Thread.sleep(1000);
		while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
			Thread.sleep(1000);
		}
		d.getMenuPackages().click();

		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ibox")));
		Thread.sleep(3000);

		int IntUnitCount = 0;
		for (int i = 0; i < pp.getMemberSections().size(); i++) {

			if (pp.getMemberSections().get(i).getText().contains(memberName.toUpperCase())) {
				List<WebElement> Packages = pp.getMemberSections().get(i)
						.findElements(By.className("ng-star-inserted"));

				for (int k = 0; k < Packages.size(); k++) {
					if (Packages.get(k).getText().contains(packageName)) {

						jse.executeScript("arguments[0].scrollIntoView(true);", Packages.get(k));
						Thread.sleep(1000);
						String[] text = Packages.get(k).getText().split("\n");
						String unitCount = text[2];
						IntUnitCount = Integer.parseInt(unitCount);
						break;
					}

				}
				break;
			}
		}
		jse.executeScript("arguments[0].click();", d.getMenuMyAccount());

		this.returnToDashboard();

		return IntUnitCount;
	}

	public Object ThankYouPageValidations() throws InterruptedException {
		ThankYouPO TY = new ThankYouPO(driver);
		Thread.sleep(1000);
		// Verifies the text on Thank You page and Print Receipt Popup
		Assert.assertEquals("THANK YOU FOR YOUR ORDER", (TY.getThankYouText().getText()));
		Assert.assertTrue(TY.getsmallText().getText().contains("The receipt # for this transaction is:"));
		Assert.assertTrue(TY.getsmallText().getText().contains("Have fun!"));
		Assert.assertTrue(
				TY.getsmallText().getText().contains("Everything was processed and you are all ready to go."));
		Assert.assertTrue(TY.getsmallText().getText().contains(
				"Participants with a valid email address on file will receive a confirmation email with details of this purchase."));

		// Verifies the links to navigate to Dashboard and other pages are displayed
		Assert.assertTrue(this.isElementPresent(By.xpath("//div[@class = 'row']//a[@href = '/home']")));
		Assert.assertTrue(this.isElementPresent(By.xpath("//div[@class = 'row']//a[@href = '/ClassList']")));
		Assert.assertTrue(this.isElementPresent(By.xpath("//div[@class = 'row']//a[@href = '/CourseList']")));
		Assert.assertTrue(this.isElementPresent(By.xpath("//div[@class = 'row']//a[@href = '/Appointments']")));
		return null;
	}

	public Object ReceiptPopupValidations() {
		ThankYouPO TY = new ThankYouPO(driver);

		Assert.assertTrue(
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'PRINT')]")).isDisplayed());
//		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'PRINT')]"))
//				.getAttribute("type").equals("button"));
		Assert.assertTrue(
				TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).isDisplayed());
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]"))
				.getAttribute("type").equals("button"));
		return null;
	}

	public Object ReviewSectionValidation(String feesOrPackages) {

		PurchaseConfirmationPO pp = new PurchaseConfirmationPO(driver);
		Boolean ReviewLabelPresent = this.isElementPresent(By.xpath("//div[@class = 'rate-box']/h2"));
		Assert.assertTrue(ReviewLabelPresent);
		Assert.assertEquals("Review", pp.getReviewLabel().getText());
		Boolean FeesPackagesLabelPresent = this.isElementPresent(By.xpath("//small[contains(text(),feesOrPackages)]"));
		Assert.assertTrue(FeesPackagesLabelPresent);
		Boolean SubTotalLabelPresent = this.isElementPresent(By.xpath("//strong[contains(text(),'SUB-TOTAL:')]"));
		Assert.assertTrue(SubTotalLabelPresent);
		Boolean TaxLabelPresent = this.isElementPresent(By.xpath("//strong[contains(text(),'TAX:')]"));
		Assert.assertTrue(TaxLabelPresent);
		Boolean TotalLabelPresent = this.isElementPresent(By.xpath("//h2[contains(text(),'TOTAL:')]"));
		Assert.assertTrue(TotalLabelPresent);
		return null;

	}

	public Object SelectTomorrowDate() throws InterruptedException {

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

		String monthName = driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]"))
				.getText();

		while (!monthName.contains(monthYear)) {
			driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-next-button')]")).click();
			monthName = driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]"))
					.getText();
		}
		// button[@class='mat-calendar-next-button
		// mat-icon-button']//div[contains(@class, 'mat-button-ripple')]

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

	public Object SelectCourseStartMonth(String CourseStartMonth) throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-lg-9']"));
		int monthCount = MonthNames.findElements(By.tagName("label")).size();
		for (int i = 0; i < monthCount; i++) {
			String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
			if (monthName.equals(CourseStartMonth)) {
				jse.executeScript("arguments[0].click();", MonthNames.findElements(By.tagName("label")).get(i));
				break;
			}

		}
		return null;
	}

	public Object SelectCourseStartYear(int CourseStartYear) throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		Calendar today = Calendar.getInstance();
		System.out.println(today.getTime());
		String currentYear = df.format(today.getTime());

		int intcurrentYear = Integer.parseInt(currentYear);

		ClassSignUpPO c = new ClassSignUpPO(driver);
		String year = c.getYear().getText();

		int intYear = Integer.parseInt(year);

		while (intYear != CourseStartYear) {
			if (intcurrentYear < CourseStartYear) {
				jse.executeScript("arguments[0].click();", c.getYearRightButton());
				year = c.getYear().getText();
			} else {
				c.getYearLeftButton().click();
				year = c.getYear().getText();

			}
			intYear = Integer.parseInt(year);
		}

		return null;
	}

	public Object SelectClassOrCourseToEnroll(String ClassOrCourseToEnroll) throws InterruptedException {

		Thread.sleep(2000);

		int ClassOrCourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();

		for (int j = 0; j < ClassOrCourseCount; j++) {
			String ClassOrCourseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j)
					.getText();

			if (ClassOrCourseName.toLowerCase().contains(ClassOrCourseToEnroll.toLowerCase())) {

				JavascriptExecutor jse = ((JavascriptExecutor) driver);
				jse.executeScript("arguments[0].scrollIntoView(true);",
						driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j));

				Thread.sleep(4000);

				jse.executeScript("arguments[0].click();",
						driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j));

				break;
			}
		}
		Thread.sleep(4000);
		return null;
	}

	public Object SelectYesterdayDate() throws InterruptedException {

		ClassSignUpPO c = new ClassSignUpPO(driver);
		c.getCalendarIcon().click();
		Thread.sleep(2000);

		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d yyyy");
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, -1);
		String yesterdaysMonthDateYear = dateFormat.format(today.getTime());

		String[] monthDateYear = yesterdaysMonthDateYear.split(" ");
		String monthYear = monthDateYear[0].toUpperCase() + " " + monthDateYear[2];
		String yesterdaysDate = monthDateYear[1];

		String monthName = driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]"))
				.getText();

		while (!monthName.contains(monthYear)) {
			driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-previous-button')]")).click();
			monthName = driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]"))
					.getText();
		}
		// button[@class='mat-calendar-next-button
		// mat-icon-button']//div[contains(@class, 'mat-button-ripple')]

		int daycount = driver.findElements(By.tagName("td")).size(); // Get the daycount from the calendar
		for (int i = 0; i < daycount; i++) {
			String date = driver.findElements(By.tagName("td")).get(i).getText();
			if (date.contains(yesterdaysDate)) {
				driver.findElements(By.tagName("td")).get(i).click(); // click on the next day
				break;
			}

		}

		return null;
	}

	public Object loginCOG(String clubName) throws InterruptedException {

		// driver.get(prop.getProperty("COGLoginPage"));
		getCOGURL();

		driver.findElement(By.id("UserName")).sendKeys(prop.getProperty("COGUserName"));
		driver.findElement(By.id("Password")).sendKeys(prop.getProperty("COGPassword"));
		driver.findElement(By.id("submit")).click();
		Thread.sleep(1000);
		Select s = new Select(driver.findElement(By.id("ddl_clubSelection")));
		s.selectByVisibleText(clubName);
		driver.findElement(By.id("submit")).click();
		Thread.sleep(3000);

		return null;
	}

	public Object ApptCheckinInCOG(String memberName, String appointmentName, String username, String unitValue)
			throws InterruptedException {

		this.loginCOG(prop.getProperty("clubName"));
		WebElement FrontDeskTile = driver.findElement(By.xpath("(//div[@class='tile'])[1]"));
		int count = FrontDeskTile.findElements(By.tagName("a")).size();
		for (int i = 0; i < count; i++) {
			// System.out.println(FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href"));
			if (FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href").contains("CheckIn")) {
				Thread.sleep(1000);
				FrontDeskTile.findElements(By.tagName("a")).get(i).findElement(By.tagName("i")).click();
				break;
			}
		}

		driver.findElement(By.id("txt_searchLastName")).sendKeys(memberName);
		driver.findElement(By.id("btn_search")).click();
		boolean memberListPresent = this.isElementPresent(By.xpath("//div[@id='memberList']"));
		if (memberListPresent == true) {
			log.info("Member List preesent");
			System.out.println("Member List present");
			int memberCount = driver.findElements(By.tagName("tr")).size();

			for (int i = 1; i < memberCount; i++) {
				WebElement MemberRow = driver.findElements(By.tagName("tr")).get(i);
				List<WebElement> MemberRowSections = MemberRow.findElements(By.tagName("td"));

				if (MemberRowSections.get(1).getText().equals(memberName)) {
					driver.findElements(By.xpath("//i[@class='fa fa-check']")).get(i - 1).click();
					break;
				}
			}
		} else {
			log.info("Member List Not present");
			System.out.println("Member List Not present");
		}
		Thread.sleep(1000);
		driver.findElement(By.xpath("//i[@class='fa fa-thumbs-up mrs']")).click();
		Thread.sleep(1000);
		List<WebElement> CheckInOptions = driver.findElements(By.tagName("tr"));
		int checkboxCount = CheckInOptions.size();
		System.out.println(checkboxCount);
		for (int j = 0; j < checkboxCount; j++) {
			String Text = CheckInOptions.get(j).getText();

			if (Text.contains(appointmentName)) {
				System.out.println("appointment name present");
				CheckInOptions.get(j).findElement(By.className("icons")).click();
				// CheckInOptions.get(j).findElement(By.xpath("td//label//span[@class =
				// 'icons']")).click();
				System.out.println("clicked");
				Select s = new Select(CheckInOptions.get(j).findElement(By.id("ddlVisits")));
				s.selectByValue(unitValue);
				break;
			}

		}

		driver.findElement(By.xpath("//i[@class='fa fa-thumbs-up mrs']")).click();
		driver.findElement(By.xpath("//a[@href='/CompeteOnTheGo/Account/Logoff']")).click();
		// driver.get(prop.getProperty("EMELoginPage"));
		getEMEURL();
		this.activeMemberLogin(username, "Testing1!");
		return null;
	}

	public Object deleteFOPInCOG(String barcodeId, String clubName, String fopNumber, String agreementLinked,
			String agreement) throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		this.loginCOG(clubName);
		WebElement BackOfficeTile = driver.findElement(By.xpath("(//div[@class='tile'])[2]"));
		int count = BackOfficeTile.findElements(By.tagName("a")).size();
		for (int i = 0; i < count; i++) {
			// System.out.println(BackOfficeTile.findElements(By.tagName("a")).get(i).getAttribute("href"));
			if (BackOfficeTile.findElements(By.tagName("a")).get(i).getAttribute("href").contains("MemberManagement")) {
				Thread.sleep(1000);
				BackOfficeTile.findElements(By.tagName("a")).get(i).findElement(By.tagName("i")).click();
				break;
			}
		}

		driver.findElement(By.id("txt_barcodeId")).sendKeys(barcodeId);

		driver.findElement(By.id("btn_search")).click();
		driver.findElement(By.xpath("//i[@class='fa fa-cogs fa-2x']")).click();
		Thread.sleep(2000);

		if (agreementLinked.equals("Yes")) {
			int agrmntCount = driver
					.findElements(By.xpath("//section[@class='featureWhite']//table[@id='tbl_search'] //tr ")).size();
			System.out.println(agrmntCount);
			for (int i = 1; i < agrmntCount; i++) {
				WebElement AgrmntRow = driver
						.findElements(By.xpath("//section[@class='featureWhite']//table[@id='tbl_search'] //tr "))
						.get(i);
				List<WebElement> AgrmntRowSections = AgrmntRow.findElements(By.tagName("td"));
				System.out.println(AgrmntRowSections.get(1).getText());
				if (AgrmntRowSections.get(1).getText().equals(agreement)) {
					System.out.println(driver.findElements(By.xpath("//span[@class = 'hide-span']")).size());

					jse.executeScript("arguments[0].scrollIntoView(true);",
							driver.findElements(By.xpath("//span[@class = 'hide-span']")).get(i - 1));

					jse.executeScript("arguments[0].click();",
							driver.findElements(By.xpath("//span[@class = 'hide-span']")).get(i - 1));

					// driver.findElements(By.xpath("//span[@class = 'hide-span']")).get(i -
					// 1).click();

					int fopCount = driver.findElements(By.xpath("//table[@class='table table-striped'] //tr")).size();
					System.out.println(fopCount);
					for (int j = 1; j < fopCount; j++) {
						WebElement FOPRow = driver.findElements(By.xpath("//table[@class='table table-striped'] //tr"))
								.get(j);
						List<WebElement> FOPRowSections = FOPRow.findElements(By.tagName("td"));
						if (FOPRowSections.get(1).getText().equals("Master Card")
								&& FOPRowSections.get(2).getText().equals("5454")) {
							driver.findElements(By.id("btn_Activate")).get(j - 1).click();

							break;
						}
					}

					Thread.sleep(1000);
					driver.findElement(By.xpath("//a[@class='btn btn-lg btn-primary btn']")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath(
							"//a[@class='btn btn-lg btn-primary hidden-print'][contains(text(),'SAVE PLAN CHANGES')]"))
							.click();
					jse.executeScript("arguments[0].click();",
							driver.findElement(By.xpath("//button[@id='btnsaveCanges']")));
					Thread.sleep(1000);
					break;
				}
			}
		}

		int fopCount = driver.findElements(By.xpath("//section[@id='divPaymentSection'] //tr")).size();
		System.out.println(fopCount);
		for (int i = 3; i < fopCount; i++) {
			WebElement FOPRow = driver.findElements(By.xpath("//section[@id='divPaymentSection'] //tr")).get(i);
			List<WebElement> FOPRowSections = FOPRow.findElements(By.tagName("td"));
			if ((FOPRowSections.get(1).getText().equals("Visa") || FOPRowSections.get(1).getText().equals("Saving")
					|| FOPRowSections.get(1).getText().equals("Checking"))
					&& FOPRowSections.get(2).getText().equals(fopNumber)) {
				driver.findElements(By.xpath("//i[@class = 'fa fa-2x fa-times']")).get(i - 3).click();
				Thread.sleep(3000);
				jse.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@id='btn_ConfirmDel']")));
				break;
			}
		}
		Thread.sleep(3000);
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class, 'success')]")).isDisplayed());
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@href='/CompeteOnTheGo/Account/Logoff']")).click();
		// driver.get(prop.getProperty("EMELoginPage"));
		getEMEURL();

		return null;

	}

	public Object confirmPaymentInCOG(String barcodeId, String clubName, String date) throws InterruptedException {

		this.loginCOG(clubName);
		WebElement BackOfficeTile = driver.findElement(By.xpath("(//div[@class='tile'])[2]"));
		int count = BackOfficeTile.findElements(By.tagName("a")).size();
		for (int i = 0; i < count; i++) {
			// System.out.println(BackOfficeTile.findElements(By.tagName("a")).get(i).getAttribute("href"));
			if (BackOfficeTile.findElements(By.tagName("a")).get(i).getAttribute("href").contains("MemberManagement")) {
				Thread.sleep(1000);
				BackOfficeTile.findElements(By.tagName("a")).get(i).findElement(By.tagName("i")).click();
				break;
			}
		}

		driver.findElement(By.id("txt_barcodeId")).sendKeys(barcodeId);

		driver.findElement(By.id("btn_search")).click();
		driver.findElement(By.xpath("//i[@class='fa fa-cogs fa-2x']")).click();
		Thread.sleep(2000);

		List<WebElement> AccountingSections = driver
				.findElements(By.xpath("//div[@class = 'floatleft checkb25 mlm AccountingSection']"));
		int accountingSectionsCount = AccountingSections.size();

		for (int j = 0; j < accountingSectionsCount; j++) {
			if (AccountingSections.get(j).getText().contains("TRANSACTION HISTORY"))
				AccountingSections.get(j).findElement(By.tagName("i")).click();
		}

		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[@id='btn_search']")).click();

		List<WebElement> PaymentRow = driver.findElements(By.tagName("tr"));

		List<WebElement> PaymentColumns = PaymentRow.get(1).findElements(By.tagName("td"));

//		Assert.assertTrue(PaymentColumns.get(0).getText().contains(date));

//		Assert.assertTrue(PaymentColumns.get(1).getText().contains("Online Payment"));

//		Assert.assertTrue(PaymentColumns.get(2).getText().contains("($1.00)"));

		driver.findElement(By.xpath("//a[@href='/CompeteOnTheGo/Account/Logoff']")).click();
		return null;
	}

	public Object VerifyFOPNotSavedInCOG(String barcodeId, String clubName, String fopNumber)
			throws InterruptedException {

		this.loginCOG(clubName);
		WebElement BackOfficeTile = driver.findElement(By.xpath("(//div[@class='tile'])[2]"));
		int count = BackOfficeTile.findElements(By.tagName("a")).size();
		for (int i = 0; i < count; i++) {
			// System.out.println(BackOfficeTile.findElements(By.tagName("a")).get(i).getAttribute("href"));
			if (BackOfficeTile.findElements(By.tagName("a")).get(i).getAttribute("href").contains("MemberManagement")) {
				Thread.sleep(1000);
				BackOfficeTile.findElements(By.tagName("a")).get(i).findElement(By.tagName("i")).click();
				break;
			}
		}

		driver.findElement(By.id("txt_barcodeId")).sendKeys(barcodeId);

		driver.findElement(By.id("btn_search")).click();
		driver.findElement(By.xpath("//i[@class='fa fa-cogs fa-2x']")).click();
		Thread.sleep(2000);

		int fopCount = driver.findElements(By.xpath("//section[@id='divPaymentSection'] //tr")).size();
		System.out.println(fopCount);
		for (int i = 3; i < fopCount; i++) {
			WebElement FOPRow = driver.findElements(By.xpath("//section[@id='divPaymentSection'] //tr")).get(i);
			List<WebElement> FOPRowSections = FOPRow.findElements(By.tagName("td"));
			Assert.assertTrue(!FOPRowSections.get(2).getText().equals(fopNumber));
		}

		driver.findElement(By.xpath("//a[@href='/CompeteOnTheGo/Account/Logoff']")).click();
		// driver.get(prop.getProperty("EMELoginPage"));
		getEMEURL();

		return null;

	}

	public Object deleteStandbyCourseInCOG(String className, String classSellClub, String memberName1,
			String memberName2) throws InterruptedException {

		this.loginCOG(classSellClub);

		WebElement FrontDeskTile = driver.findElement(By.xpath("(//div[@class='tile'])[1]"));

		int count = FrontDeskTile.findElements(By.tagName("a")).size();

		for (int i = 0; i < count; i++) {
			// System.out.println(FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href"));
			if (FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href").contains("ClassCheckIn")) {
				Thread.sleep(1000);
				FrontDeskTile.findElements(By.tagName("a")).get(i).findElement(By.tagName("i")).click();
				break;
			}
		}

		driver.findElement(By.xpath("//i[@class='fa fa-calendar calenderbtn']")).click();
		Select monthDropdown = new Select(driver.findElement(By.xpath("//select[@class='ui-datepicker-month']")));
		monthDropdown.selectByVisibleText("Feb");
		Select yearDropdown = new Select(driver.findElement(By.xpath("//select[@class='ui-datepicker-year']")));
		yearDropdown.selectByVisibleText("2022");
		List<WebElement> Dates = driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar'] //td/a"));
		int dateCount = Dates.size();
		for (int j = 0; j < dateCount; j++) {
			if (Dates.get(j).getText().contains("21")) {
				Dates.get(j).click();
				break;
			}
		}
		Thread.sleep(2000);

		int classCount = driver.findElements(By.tagName("tr")).size();

		for (int i = 1; i < classCount; i++) {
			WebElement ClassRow = driver.findElements(By.tagName("tr")).get(i);
			List<WebElement> ClassRowSections = ClassRow.findElements(By.tagName("td"));
			String classNameText = ClassRowSections.get(0).getText();
			if (classNameText.equals(className)) {
				driver.findElements(By.xpath("//a[@role = 'button']")).get(i - 1).click();
				break;
			}
		}
		Thread.sleep(2000);

		int memberCount = driver.findElements(By.xpath("//div[@id='attendanceList'] //tr")).size();

		for (int i = 1; i < memberCount; i++) {
			WebElement MemberRow = driver.findElements(By.xpath("//div[@id='attendanceList'] //tr")).get(i);
			List<WebElement> MemberRowSections = MemberRow.findElements(By.tagName("td"));
			String memberNameText = MemberRowSections.get(2).getText();

			if (memberNameText.contains(memberName1)) {
				driver.findElements(By.xpath("//a[@data-enrollstatus = 'StandBy']")).get(i - 1).click();
				break;
			}
		}
		Thread.sleep(2000);

		for (int i = 1; i < memberCount; i++) {
			WebElement MemberRow = driver.findElements(By.xpath("//div[@id='attendanceList'] //tr")).get(i);
			List<WebElement> MemberRowSections = MemberRow.findElements(By.tagName("td"));
			String memberNameText = MemberRowSections.get(2).getText();

			if (memberNameText.contains(memberName2)) {
				driver.findElements(By.xpath("//a[@data-enrollstatus = 'StandBy']")).get(i - 1).click();
				break;
			}
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[@href='/CompeteOnTheGo/Account/Logoff']")).click();
		return null;
	}

	public Object deleteStandbyClassInCOG(String className, String classSellClub, String memberName1,
			String memberName2) throws InterruptedException {

		this.loginCOG(classSellClub);

		WebElement FrontDeskTile = driver.findElement(By.xpath("(//div[@class='tile'])[1]"));

		int count = FrontDeskTile.findElements(By.tagName("a")).size();

		for (int i = 0; i < count; i++) {
			// System.out.println(FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href"));
			if (FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href").contains("ClassCheckIn")) {
				Thread.sleep(1000);
				FrontDeskTile.findElements(By.tagName("a")).get(i).findElement(By.tagName("i")).click();
				break;
			}
		}

		driver.findElement(By.xpath("//input[@id='txtDate']")).clear();
		driver.findElement(By.xpath("//input[@id='txtDate']")).sendKeys(tomorrowsDate);
		driver.findElement(By.xpath("//input[@id='txtDate']")).sendKeys(Keys.ENTER);
		Thread.sleep(2000);

		int classCount = driver.findElements(By.tagName("tr")).size();

		for (int i = 1; i < classCount; i++) {
			WebElement ClassRow = driver.findElements(By.tagName("tr")).get(i);
			List<WebElement> ClassRowSections = ClassRow.findElements(By.tagName("td"));
			String classNameText = ClassRowSections.get(0).getText();
			if (classNameText.equals(className)) {
				driver.findElements(By.xpath("//a[@role = 'button']")).get(i - 1).click();
				break;
			}
		}
		Thread.sleep(2000);

		int memberCount = driver.findElements(By.xpath("//div[@id='attendanceList'] //tr")).size();

		for (int i = 1; i < memberCount; i++) {
			WebElement MemberRow = driver.findElements(By.xpath("//div[@id='attendanceList'] //tr")).get(i);
			List<WebElement> MemberRowSections = MemberRow.findElements(By.tagName("td"));
			String memberNameText = MemberRowSections.get(2).getText();

			if (memberNameText.contains(memberName1)) {
				driver.findElements(By.xpath("//a[@data-enrollstatus = 'StandBy']")).get(i - 1).click();
				break;
			}
		}
		Thread.sleep(2000);

		for (int i = 1; i < memberCount; i++) {
			WebElement MemberRow = driver.findElements(By.xpath("//div[@id='attendanceList'] //tr")).get(i);
			List<WebElement> MemberRowSections = MemberRow.findElements(By.tagName("td"));
			String memberNameText = MemberRowSections.get(2).getText();

			if (memberNameText.contains(memberName2)) {
				driver.findElements(By.xpath("//a[@data-enrollstatus = 'StandBy']")).get(i - 1).click();
				break;
			}
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[@href='/CompeteOnTheGo/Account/Logoff']")).click();
		return null;
	}

	public Object deleteEnrollInClassInCOG(String className, String classSellClub, String memberName)
			throws InterruptedException {

		this.loginCOG(classSellClub);

		WebElement FrontDeskTile = driver.findElement(By.xpath("(//div[@class='tile'])[1]"));

		int count = FrontDeskTile.findElements(By.tagName("a")).size();

		for (int i = 0; i < count; i++) {
			// System.out.println(FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href"));
			if (FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href").contains("ClassCheckIn")) {
				Thread.sleep(1000);
				FrontDeskTile.findElements(By.tagName("a")).get(i).findElement(By.tagName("i")).click();
				break;
			}
		}

		driver.findElement(By.xpath("//input[@id='txtDate']")).clear();
		driver.findElement(By.xpath("//input[@id='txtDate']")).sendKeys(tomorrowsDate);
		driver.findElement(By.xpath("//input[@id='txtDate']")).sendKeys(Keys.ENTER);
		Thread.sleep(2000);

		int classCount = driver.findElements(By.tagName("tr")).size();

		for (int i = 1; i < classCount; i++) {
			WebElement ClassRow = driver.findElements(By.tagName("tr")).get(i);
			List<WebElement> ClassRowSections = ClassRow.findElements(By.tagName("td"));
			String classNameText = ClassRowSections.get(0).getText();
			if (classNameText.equals(className)) {
				driver.findElements(By.xpath("//a[@role = 'button']")).get(i - 1).click();
				break;
			}
		}
		Thread.sleep(2000);

		int memberCount = driver.findElements(By.xpath("//div[@id='attendanceList'] //tr")).size();

		for (int i = 1; i < memberCount; i++) {
			WebElement MemberRow = driver.findElements(By.xpath("//div[@id='attendanceList'] //tr")).get(i);
			List<WebElement> MemberRowSections = MemberRow.findElements(By.tagName("td"));
			String memberNameText = MemberRowSections.get(2).getText();

			if (memberNameText.contains(memberName)) {
				driver.findElements(By.xpath("//a[@data-enrollstatus = 'Enrolled']")).get(i - 1).click();
				break;
			}
		}
		Thread.sleep(2000);

		driver.findElement(By.xpath("//a[@href='/CompeteOnTheGo/Account/Logoff']")).click();
		return null;
	}

	public Object ClassCheckInCOG(String className, String classSellClub, String username) throws InterruptedException {

		this.loginCOG(classSellClub);

		WebElement FrontDeskTile = driver.findElement(By.xpath("(//div[@class='tile'])[1]"));

		int count = FrontDeskTile.findElements(By.tagName("a")).size();

		for (int i = 0; i < count; i++) {
			// System.out.println(FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href"));
			if (FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href").contains("ClassCheckIn")) {
				Thread.sleep(1000);
				FrontDeskTile.findElements(By.tagName("a")).get(i).findElement(By.tagName("i")).click();
				break;
			}
		}

		Thread.sleep(3000);

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

		int classCount = driver.findElements(By.tagName("tr")).size();

		for (int i = 1; i < classCount; i++) {
			WebElement ClassRow = driver.findElements(By.tagName("tr")).get(i);
			List<WebElement> ClassRowSections = ClassRow.findElements(By.tagName("td"));
			String classNameText = ClassRowSections.get(0).getText();
			if (classNameText.equals(className)) {
				driver.findElements(By.xpath("//a[@role = 'button']")).get(i - 1).click();
				break;
			}
		}
		Thread.sleep(2000);

		try {

			driver.findElement(By.xpath("//tbody/tr[1]/td[1]/a[1]")).click();

		} catch (org.openqa.selenium.NoSuchElementException nse) {

			System.out.println("Check In button is not available");

		}

		driver.findElement(By.xpath("//a[@href='/CompeteOnTheGo/Account/Logoff']")).click();
		// driver.get(prop.getProperty("EMELoginPage"));
		getEMEURL();
		this.activeMemberLogin(username, "Testing1!");

		return null;

	}

	public Object deleteEnrollInCourseInCOG(String courseName, String classSellClub, String memberName)
			throws InterruptedException {

		this.loginCOG(classSellClub);

		WebElement FrontDeskTile = driver.findElement(By.xpath("(//div[@class='tile'])[1]"));

		int count = FrontDeskTile.findElements(By.tagName("a")).size();

		for (int i = 0; i < count; i++) {
			// System.out.println(FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href"));
			if (FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href").contains("ClassCheckIn")) {
				Thread.sleep(1000);
				FrontDeskTile.findElements(By.tagName("a")).get(i).findElement(By.tagName("i")).click();
				break;
			}
		}

		driver.findElement(By.xpath("//i[@class='fa fa-calendar calenderbtn']")).click();
		Select monthDropdown = new Select(driver.findElement(By.xpath("//select[@class='ui-datepicker-month']")));
		monthDropdown.selectByVisibleText("Feb");
		Select yearDropdown = new Select(driver.findElement(By.xpath("//select[@class='ui-datepicker-year']")));
		yearDropdown.selectByVisibleText("2022");
		List<WebElement> Dates = driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar'] //td/a"));
		int dateCount = Dates.size();
		for (int j = 0; j < dateCount; j++) {
			if (Dates.get(j).getText().contains("10")) {
				Dates.get(j).click();
				break;
			}
		}
		Thread.sleep(2000);

		int classCount = driver.findElements(By.tagName("tr")).size();

		for (int i = 1; i < classCount; i++) {
			WebElement ClassRow = driver.findElements(By.tagName("tr")).get(i);
			List<WebElement> ClassRowSections = ClassRow.findElements(By.tagName("td"));
			String courseNameText = ClassRowSections.get(0).getText();
			if (courseNameText.equals(courseName)) {
				driver.findElements(By.xpath("//a[@role = 'button']")).get(i - 1).click();
				break;
			}
		}
		Thread.sleep(2000);

		int memberCount = driver.findElements(By.xpath("//div[@id='attendanceList'] //tr")).size();

		for (int i = 1; i < memberCount; i++) {
			WebElement MemberRow = driver.findElements(By.xpath("//div[@id='attendanceList'] //tr")).get(i);
			List<WebElement> MemberRowSections = MemberRow.findElements(By.tagName("td"));
			String memberNameText = MemberRowSections.get(2).getText();

			if (memberNameText.contains(memberName)) {
				driver.findElements(By.xpath("//a[@data-enrollstatus = 'Enrolled']")).get(i - 1).click();
				break;
			}
		}
		Thread.sleep(2000);

		driver.findElement(By.xpath("//a[@href='/CompeteOnTheGo/Account/Logoff']")).click();
		return null;
	}

	public Object ConfirmAndCancelAppointmentNoFee(String Date, String startTime, String appointmentToBook)
			throws IOException, InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		rw.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);

		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
		int appointmentsCount = d.getMyAppts().size();

		for (int i = 0; i < appointmentsCount; i++) {
			if (d.getMyAppts().get(i).getText().contains(Date))

			{

				if (d.getMyAppts().get(i).getText().contains(startTime)) {

					Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook.toUpperCase()));

					wait.until(ExpectedConditions
							.elementToBeClickable(d.getMyAppts().get(i).findElement(By.tagName("i"))));
					jse.executeScript("arguments[0].click();", d.getMyAppts().get(i).findElement(By.tagName("i")));

					WebElement EditButton = d.getEditButton().get(i);

					wait.until(ExpectedConditions.visibilityOf(EditButton));
					wait.until(ExpectedConditions.elementToBeClickable(EditButton));

					jse.executeScript("arguments[0].click();", EditButton);
					break;
				}
			}
		}

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-md-12']/h2")));
		Thread.sleep(2000);
		AppointmentsPO a = new AppointmentsPO(driver);
		Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
		wait.until(ExpectedConditions.visibilityOf(a.getEditApptCancelButton()));
		jse.executeScript("arguments[0].click();", a.getEditApptCancelButton());
		Thread.sleep(1000);
		a.getEditApptCancelYesButton().click();
		Thread.sleep(2000);
		rw.waitForAcceptButton();
		a.getPopup2OKButton().click();
		Thread.sleep(2000);
		Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");

		return null;
	}

	public Object cancelAppointmentByHohNoFee(String appointmentToBook, String familyMember)
			throws IOException, InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		DashboardPO d = new DashboardPO(driver);
		CalendarPO cp = new CalendarPO(driver);

		this.openSideMenuIfNotOpenedAlready();

		while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1")) {

			d.getMenuMyActivies().click();
			Thread.sleep(1000);
			d.getmenuMyActivitiesSubMenu().getAttribute("style");
			System.out.println(d.getmenuMyActivitiesSubMenu().getAttribute("style"));
		}

		WebDriverWait wait1 = new WebDriverWait(driver, 50);
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMenuMyCalendar()));

		d.getMenuMyCalendar().click();
		wait1.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

		cp.getCalendarViewLink().click();
		Thread.sleep(1000);

		this.MyActivitiesTomorrowClick();

		/*
		 * Thread.sleep(1000); cp.getCalDayBadge().click();
		 */
		Thread.sleep(1000);

		int eventCount = cp.getCalEventTitles().size();

		for (int i = 0; i < eventCount; i++) {

			if (cp.getCalEventTitles().get(i).getText().contains(appointmentToBook)) {

				jse.executeScript("arguments[0].scrollIntoView(true);", cp.getCalEventTitles().get(i));
				Thread.sleep(1000);
				Actions a = new Actions(driver);
				a.moveToElement(cp.getCalEventTitles().get(i)).click().build().perform();
				break;
			}
		}

		Thread.sleep(1000);

		Assert.assertTrue(cp.getClassName().getText().contains(appointmentToBook));
		Assert.assertTrue(cp.getEnrolledMemberName().getText().contains(familyMember));
		cp.getEditAppointmentButton().click();
		Thread.sleep(1000);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-md-12']/h2")));
		Thread.sleep(2000);
		AppointmentsPO a = new AppointmentsPO(driver);
		Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
		wait.until(ExpectedConditions.visibilityOf(a.getEditApptCancelButton()));
		jse.executeScript("arguments[0].click();", a.getEditApptCancelButton());
		Thread.sleep(1000);
		a.getEditApptCancelYesButton().click();
		Thread.sleep(2000);
		rw.waitForAcceptButton();
		a.getPopup2OKButton().click();
		Thread.sleep(2000);
		Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");

		return null;
	}

	public Object cancelAppointmentFromListViewByHohNoFee(String date, String startTime, String appointmentToBook,
			String familyMember) throws IOException, InterruptedException, ParseException {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		DashboardPO d = new DashboardPO(driver);
		CalendarPO cp = new CalendarPO(driver);

		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		Calendar calendar = Calendar.getInstance();
		int lastDateOfCurrentMonth = calendar.getActualMaximum(Calendar.DATE);
		System.out.println(lastDateOfCurrentMonth);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date firstDateOfNextMonth = calendar.getTime();
		System.out.println(firstDateOfNextMonth);

		Date dt = df.parse(date);

		System.out.println(dt);

		this.openSideMenuIfNotOpenedAlready();

		while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1")) {

			d.getMenuMyActivies().click();
			Thread.sleep(1000);
			d.getmenuMyActivitiesSubMenu().getAttribute("style");
			System.out.println(d.getmenuMyActivitiesSubMenu().getAttribute("style"));
		}

		WebDriverWait wait1 = new WebDriverWait(driver, 50);
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMenuMyCalendar()));

		d.getMenuMyCalendar().click();
		wait1.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

		if (calendar.get(Calendar.DATE) == lastDateOfCurrentMonth || dt.equals(firstDateOfNextMonth)
				|| dt.after(firstDateOfNextMonth)) {

			driver.findElements(By.xpath("//i[contains(@class, 'right')]")).get(0).click();

			wait1.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

			Thread.sleep(1000);

		}

		int count = cp.getMemberSections().size();

		for (int i = 0; i < count; i++) {
			if (cp.getMemberSections().get(i).getText().contains(familyMember)) {

				if (cp.getMemberSections().get(i).getText().contains(date))

				{

					if (cp.getMemberSections().get(i).getText().contains(startTime)) {
						jse.executeScript("arguments[0].click();",
								cp.getMemberSections().get(i).findElement(By.tagName("i")));

						jse.executeScript("arguments[0].click();",
								cp.getMemberSections().get(i).findElements(By.tagName("a")).get(1));
						break;
					}
				}

			}

		}

		Thread.sleep(1000);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-md-12']/h2")));
		Thread.sleep(2000);
		AppointmentsPO a = new AppointmentsPO(driver);
		Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
		wait.until(ExpectedConditions.visibilityOf(a.getEditApptCancelButton()));
		jse.executeScript("arguments[0].click();", a.getEditApptCancelButton());
		Thread.sleep(1000);
		a.getEditApptCancelYesButton().click();
		Thread.sleep(2000);
		rw.waitForAcceptButton();
		a.getPopup2OKButton().click();
		Thread.sleep(2000);
		Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");

		return null;
	}

	public Object cancelAppointmentByHohWithFees(String date, String startTime, String appointmentToBook,
			String familyMember) throws IOException, InterruptedException, ParseException {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		DashboardPO d = new DashboardPO(driver);
		CalendarPO cp = new CalendarPO(driver);

		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		Calendar calendar = Calendar.getInstance();
		int todaysDate = calendar.get(Calendar.DATE);
		System.out.println(todaysDate);
		int lastDateOfCurrentMonth = calendar.getActualMaximum(Calendar.DATE);
		System.out.println(lastDateOfCurrentMonth);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date firstDateOfNextMonth = calendar.getTime();
		System.out.println(firstDateOfNextMonth);

		Date dt = df.parse(date);

		System.out.println(dt);

		this.openSideMenuIfNotOpenedAlready();

		while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1")) {

			d.getMenuMyActivies().click();
			Thread.sleep(1000);
			d.getmenuMyActivitiesSubMenu().getAttribute("style");
			System.out.println(d.getmenuMyActivitiesSubMenu().getAttribute("style"));
		}

		WebDriverWait wait1 = new WebDriverWait(driver, 50);
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMenuMyCalendar()));

		d.getMenuMyCalendar().click();
		wait1.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

		if (calendar.get(Calendar.DATE) == lastDateOfCurrentMonth || dt.equals(firstDateOfNextMonth)
				|| dt.after(firstDateOfNextMonth)) {

			driver.findElements(By.xpath("//i[contains(@class, 'right')]")).get(0).click();

			wait1.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

			Thread.sleep(1000);

		}

		int count = cp.getMemberSections().size();

		for (int i = 0; i < count; i++) {
			if (cp.getMemberSections().get(i).getText().contains(familyMember)) {

				if (cp.getMemberSections().get(i).getText().contains(date))

				{

					if (cp.getMemberSections().get(i).getText().contains(startTime)) {
						jse.executeScript("arguments[0].click();",
								cp.getMemberSections().get(i).findElement(By.tagName("i")));

						jse.executeScript("arguments[0].click();",
								cp.getMemberSections().get(i).findElements(By.tagName("a")).get(1));
						break;
					}
				}

			}

		}

		Thread.sleep(1000);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-md-12']/h2")));
		Thread.sleep(2000);
		AppointmentsPO a = new AppointmentsPO(driver);
		Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
		wait.until(ExpectedConditions.visibilityOf(a.getEditApptCancelButton()));
		jse.executeScript("arguments[0].click();", a.getEditApptCancelButton());
		Thread.sleep(1000);
		Assert.assertTrue(a.getCancelFeeSection().getText().contains("Appointment Cancellation Fee"));

		wait.until(ExpectedConditions.textToBePresentInElement(a.getTotalAmount(), "$"));
		System.out.println(a.getTotalAmount().getText());
		String[] totalAmt = a.getTotalAmount().getText().split(": ");
		String FormatTotalAmt = totalAmt[1].trim();
		System.out.println(FormatTotalAmt);

		Thread.sleep(3000);

		PaymentMethodsPO PM = new PaymentMethodsPO(driver);

		while (!PM.getNewCardButton().isDisplayed())

		{
			Thread.sleep(1000);

		}

		jse.executeScript("arguments[0].click();", PM.getNewCardButton());
		Thread.sleep(3000);

		String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
		while (opacity.contains("1")) {
			jse.executeScript("arguments[0].click();", PM.getNewCardButton());
			Thread.sleep(3000);
			opacity = driver.findElement(By.id("show-saved")).getAttribute("style");

		}

		wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

		Assert.assertTrue(PM.getCloseButton().isDisplayed());
		System.out.println("Pay Button disabled:" + PM.getPaymentButton().getAttribute("disabled"));
//		Assert.assertFalse(PM.getPaymentButton().isEnabled());

//			System.out.println(PM.getNameOnCardField().getAttribute("value"));

		PM.getCardNumberField().sendKeys("4111111111111111");
		PM.getExpirationMonth().sendKeys("04");
		PM.getExpirationYear().sendKeys("22");
		PM.getSecurityCode().sendKeys("123");
		jse.executeScript("arguments[0].click();", PM.getCheckBox());
		while (!PM.getPaymentButton().isEnabled()) {
			Thread.sleep(1000);
		}

		// Clicks on the Pay button without signature
		jse.executeScript("arguments[0].click();", PM.getPaymentButton());
		System.out.println(PM.getPopupContent().getText());
		Assert.assertTrue(PM.getPopupContent().getText().contains("A signature is required to continue."));
		PM.getPopupOk().click();
		Thread.sleep(1000);
		jse.executeScript("arguments[0].click();", PM.getSaveCardNo());
		Thread.sleep(1000);

		// Verifies the Pay button contains the total amount
		Assert.assertTrue(PM.getPaymentButton().getText().contains(FormatTotalAmt));
		// Click the Pay button
		while (!PM.getPaymentButton().isEnabled()) {
			Thread.sleep(1000);
		}
		jse.executeScript("arguments[0].click();", PM.getPaymentButton());

		rw.waitForAcceptButton();

		wait.until(ExpectedConditions.elementToBeClickable(a.getPopup2OKButton()));

		// Verifies the success message
		Assert.assertEquals(a.getPopup2Title().getText(), "Success");
		Assert.assertTrue(a.getPopup2Content().getText().contains("Your appointment has been cancelled."));
		a.getPopup2OKButton().click();
		Thread.sleep(1000);
		ThankYouPO TY = new ThankYouPO(driver);

		// Verifies the text on Thank You page and the links to navigate to Dashboard
		// and other pages are displayed
		this.ThankYouPageValidations();

		Assert.assertTrue(TY.getPrintReceiptButton().isDisplayed());
		TY.getPrintReceiptButton().click();
		Thread.sleep(2000);
		Assert.assertTrue(TY.getReceiptPopup().isDisplayed());

		// Verifies the buttons on Print Receipt Popup
		this.ReceiptPopupValidations();
		Assert.assertTrue(TY.getReceiptPopup().findElement(By.xpath("//div[@class='col-12 text-right']")).getText()
				.contains(FormatTotalAmt));

		TY.getReceiptPopup().findElement(By.xpath("//button[contains(text(), 'CLOSE')]")).click();
		Thread.sleep(2000);

		// Navigate to Dashboard
		int linkcount = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < linkcount; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

			{
				// rw.linksToBeClickable();
				jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
				break;
			}

		}
		rw.waitForDashboardLoaded();
		// Verifies the link navigates to the right page
		Assert.assertEquals("Dashboard", driver.getTitle());
		Thread.sleep(2000);

		return null;
	}

	public Object ConfirmAndCancelAppointmentNoFee1(String Date, String startTime, String appointmentToBook)
			throws IOException, InterruptedException {
		rw.waitForDashboardLoaded();
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//appointmentswidget//div[@class = 'class-table-container']")));
		int appointmentsCount = d.getMyAppts().size();

		for (int i = 0; i < appointmentsCount; i++) {
			if (d.getMyAppts().get(i).getText().contains(Date))

			{

				if (d.getMyAppts().get(i).getText().contains(startTime)) {

					Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook.toUpperCase()));
				}
			}
		}

		/*
		 * wait.until(ExpectedConditions.elementToBeClickable(d.getMyAppts().get(i).
		 * findElement(By.tagName("i"))));
		 * d.getMyAppts().get(i).findElement(By.tagName("i")).click();
		 * 
		 * WebElement EditButton = d.getEditButton().get(i);
		 * 
		 * wait.until(ExpectedConditions.visibilityOf(EditButton));
		 * wait.until(ExpectedConditions.elementToBeClickable(EditButton));
		 * 
		 * EditButton.click(); break; } } }
		 * 
		 * wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
		 * "//div[@class='col-sm-12']/h2"))); AppointmentsPO a = new
		 * AppointmentsPO(driver);
		 * Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
		 * wait.until(ExpectedConditions.visibilityOf(ap.getEditApptCancelButton()));
		 * a.getEditApptCancelButton().click(); WebElement wait2 =
		 * a.getEditApptProceedButton(); while (!wait2.isEnabled())// while button is
		 * NOT(!) enabled { // Thread.sleep(200); }
		 * a.getEditApptProceedButton().click(); boolean result1 =
		 * rw.popupMessageYesButton(); if (result1 == true) { // Thread.sleep(500); }
		 * a.getEditApptCancelYesButton().click(); // Thread.sleep(2000);
		 * Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");
		 */

		return null;
	}

	public String BookApptWith2Resources(String clubName, String productCategory, String appointmentToBook,
			String resourceName1, String resourceName2) throws IOException, InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		// DashboardPO p = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		String startTime = null;
		// jse.executeScript("arguments[0].click();", p.getMyApptsScheduleButton());

		AppointmentsPO ap = new AppointmentsPO(driver);
		Thread.sleep(2000);

		this.catchErrorMessage();

		this.selectClub(clubName);
		Thread.sleep(2000);

		this.selectProductCategory(productCategory);

		this.makeNewAppointmentSelections(appointmentToBook, resourceName1);

		this.calendarTomorrowClick();

		List<WebElement> TimeSlots;
		WebElement MorningSlot;
		WebElement MorningAvailableTimeContainer;
		List<WebElement> MorningAvailableTimes;
		WebElement firstAvailableTimeMorning;

		for (int i = 0; i < ap.getApptBox().size(); i++) {
			String bookName = ap.getApptBox().get(i).getText();
			if (bookName.contains(resourceName2)) {
				TimeSlots = ap.getTimeSlotContainers().get(i).findElements(By.tagName("a"));
				MorningSlot = TimeSlots.get(0);
				wait.until(ExpectedConditions.elementToBeClickable(MorningSlot));
				while (!MorningSlot.isEnabled())// while button is NOT(!) enabled
				{
					System.out.println("Waiting for available times");
				}

				// MorningSlot.click();

				jse.executeScript("arguments[0].click();", MorningSlot);

				MorningAvailableTimeContainer = ap.getTimeSlotContainers().get(i).findElement(By.id("tab-1-0"));
				MorningAvailableTimes = MorningAvailableTimeContainer.findElements(By.tagName("button"));
				firstAvailableTimeMorning = MorningAvailableTimes.get(0);
//					while (!st2.isEnabled())//while button is NOT(!) enabled
//					{
//					Thread.sleep(200);
//					}

				wait.until(ExpectedConditions.elementToBeClickable(firstAvailableTimeMorning));
				startTime = firstAvailableTimeMorning.getText();
				System.out.println(startTime);
				firstAvailableTimeMorning.click();
				break;
			}
		}
		Thread.sleep(2000);

		System.out.println("RMpopupSize = " + ap.getPopup1().size());
		log.info("RMpopupSize = " + ap.getPopup1().size());

		int k = 0;

		while (ap.getPopup1().size() == 0 && k < 2)

		{

			if (ap.getSelectATimeDrawer().getAttribute("ng-reflect-opened").equals("true")) {
				jse.executeScript("arguments[0].click();", ap.getCloseButton());
			}

			this.calendarTomorrowClick();

			for (int i = 0; i < ap.getApptBox().size(); i++) {
				String bookName = ap.getApptBox().get(i).getText();
				if (bookName.contains(resourceName2)) {
					TimeSlots = ap.getTimeSlotContainers().get(i).findElements(By.tagName("a"));
					MorningSlot = TimeSlots.get(0);
					wait.until(ExpectedConditions.elementToBeClickable(MorningSlot));
					while (!MorningSlot.isEnabled())// while button is NOT(!) enabled
					{
						System.out.println("Waiting for available times");
					}

					// MorningSlot.click();

					jse.executeScript("arguments[0].click();", MorningSlot);

					MorningAvailableTimeContainer = ap.getTimeSlotContainers().get(i).findElement(By.id("tab-1-0"));
					MorningAvailableTimes = MorningAvailableTimeContainer.findElements(By.tagName("button"));
					firstAvailableTimeMorning = MorningAvailableTimes.get(0);
//						while (!st2.isEnabled())//while button is NOT(!) enabled
//						{
//						Thread.sleep(200);
//						}

					wait.until(ExpectedConditions.elementToBeClickable(firstAvailableTimeMorning));
					startTime = firstAvailableTimeMorning.getText();
					System.out.println(startTime);
					firstAvailableTimeMorning.click();
					break;
				}
			}
			Thread.sleep(2000);

			ap.getPopup1().size();
			k++;
		}

		if (ap.getPopup1Content().getText().contains("This appointment requires a package purchase.")) {
			ap.getPopup1BookButton().click();
			Thread.sleep(2000);
			Select s4 = new Select(
					driver.findElement(By.xpath("//select[contains(@class, 'at-appointments-checkout-dropdown')]")));
			List<WebElement> UnitRates = s4.getOptions();

			int count4 = UnitRates.size();
			System.out.println("4 " + count4);

			for (int i = 0; i < count4; i++) {
				String unitRate = UnitRates.get(i).getText();
				System.out.println(unitRate);

				if (unitRate.contains("1 - $")) {
					s4.selectByVisibleText(unitRate);
					break;
				}
			}
			Thread.sleep(2000);
			wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));
			jse.executeScript("arguments[0].click();", ap.getPaymentButton());
		} else {
			ap.getPopup1BookButton().click();
		}

		rw.waitForAcceptButton();
		wait.until(ExpectedConditions.visibilityOf(ap.getPopup2OKButton()));

		Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
		ap.getPopup2OKButton().click();
		Thread.sleep(1000);

		// Navigate to Dashboard
		int linkcount = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < linkcount; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

			{
				// rw.linksToBeClickable();
				jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
				break;
			}

		}
		this.catchErrorMessage();
		rw.waitForDashboardLoaded();
		return startTime;

	}

	public String BookGrpApptWith2Resources(String clubName, String productCategory, String appointmentToBook,
			String resourceName1, String resourceName2, String groupMember) throws IOException, InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		// DashboardPO p = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		String startTime = null;
		// jse.executeScript("arguments[0].click();", p.getMyApptsScheduleButton());
		Thread.sleep(2000);

		this.catchErrorMessage();

		AppointmentsPO ap = new AppointmentsPO(driver);

		this.selectClub(clubName);
		Thread.sleep(2000);

		this.selectProductCategory(productCategory);

		this.makeNewGrpAppointmentSelections(groupMember, appointmentToBook, resourceName1);

		this.calendarTomorrowClick();

		List<WebElement> TimeSlots;
		WebElement MorningSlot;
		WebElement MorningAvailableTimeContainer;
		List<WebElement> MorningAvailableTimes;
		WebElement firstAvailableTimeMorning;

		for (int i = 0; i < ap.getApptBox().size(); i++) {
			String bookName = ap.getApptBox().get(i).getText();
			if (bookName.contains(resourceName2)) {
				TimeSlots = ap.getTimeSlotContainers().get(i).findElements(By.tagName("a"));
				MorningSlot = TimeSlots.get(0);
				wait.until(ExpectedConditions.elementToBeClickable(MorningSlot));
				while (!MorningSlot.isEnabled())// while button is NOT(!) enabled
				{
					System.out.println("Waiting for available times");
				}

				// MorningSlot.click();

				jse.executeScript("arguments[0].click();", MorningSlot);

				MorningAvailableTimeContainer = ap.getTimeSlotContainers().get(i).findElement(By.id("tab-1-0"));
				MorningAvailableTimes = MorningAvailableTimeContainer.findElements(By.tagName("button"));
				firstAvailableTimeMorning = MorningAvailableTimes.get(0);
//				while (!st2.isEnabled())//while button is NOT(!) enabled
//				{
//				Thread.sleep(200);
//				}

				wait.until(ExpectedConditions.elementToBeClickable(firstAvailableTimeMorning));
				startTime = firstAvailableTimeMorning.getText();
				System.out.println(startTime);
				jse.executeScript("arguments[0].click();", firstAvailableTimeMorning);
				break;
			}

		}

		Thread.sleep(2000);

		System.out.println("RMpopupSize = " + ap.getPopup1().size());
		log.info("RMpopupSize = " + ap.getPopup1().size());

		int k = 0;

		while (ap.getPopup1().size() == 0 && k < 2)

		{
			if (ap.getSelectATimeDrawer().getAttribute("ng-reflect-opened").equals("true")) {
				ap.getCloseButton().click();
			}

			this.calendarTomorrowClick();

			for (int i = 0; i < ap.getApptBox().size(); i++) {
				String bookName = ap.getApptBox().get(i).getText();
				if (bookName.contains(resourceName2)) {
					TimeSlots = ap.getTimeSlotContainers().get(i).findElements(By.tagName("a"));
					MorningSlot = TimeSlots.get(0);
					wait.until(ExpectedConditions.elementToBeClickable(MorningSlot));
					while (!MorningSlot.isEnabled())// while button is NOT(!) enabled
					{
						System.out.println("Waiting for available times");
					}

					// MorningSlot.click();

					jse.executeScript("arguments[0].click();", MorningSlot);

					MorningAvailableTimeContainer = ap.getTimeSlotContainers().get(i).findElement(By.id("tab-1-0"));
					MorningAvailableTimes = MorningAvailableTimeContainer.findElements(By.tagName("button"));
					firstAvailableTimeMorning = MorningAvailableTimes.get(0);
//						while (!st2.isEnabled())//while button is NOT(!) enabled
//						{
//						Thread.sleep(200);
//						}

					wait.until(ExpectedConditions.elementToBeClickable(firstAvailableTimeMorning));
					startTime = firstAvailableTimeMorning.getText();
					System.out.println(startTime);
					jse.executeScript("arguments[0].click();", firstAvailableTimeMorning);
					break;
				}
			}
			Thread.sleep(2000);

			ap.getPopup1().size();
			k++;
		}

		if (ap.getPopup1Content().getText().contains("This appointment requires a package purchase.")) {

			System.out.println("Went into the loop of Package required");

			ap.getPopup1BookButton().click();
			Thread.sleep(2000);
			Select s4 = new Select(
					driver.findElement(By.xpath("//select[contains(@class, 'at-appointments-checkout-dropdown')]")));
			List<WebElement> UnitRates = s4.getOptions();

			int count4 = UnitRates.size();
			System.out.println("4 " + count4);

			for (int i = 0; i < count4; i++) {
				String unitRate = UnitRates.get(i).getText();
				System.out.println(unitRate);

				if (unitRate.contains("1 - $")) {
					s4.selectByVisibleText(unitRate);
					break;
				}
			}
			Thread.sleep(2000);
			wait.until(ExpectedConditions.textToBePresentInElement(ap.getTotalAmount(), "$"));

			jse.executeScript("arguments[0].click();", ap.getPaymentButton());
			System.out.println("clicked");
		} else {
			ap.getPopup1BookButton().click();
		}

		rw.waitForAcceptButton();
		wait.until(ExpectedConditions.visibilityOf(ap.getPopup2OKButton()));

		Assert.assertEquals(ap.getPopup2Title().getText(), "Booked");
		ap.getPopup2OKButton().click();
		Thread.sleep(1000);

		// Navigate to Dashboard
		int linkcount = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < linkcount; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

			{
				// rw.linksToBeClickable();
				jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
				break;
			}

		}

		this.catchErrorMessage();
		rw.waitForDashboardLoaded();
		return startTime;

	}

	public String openSideMenuIfNotOpenedAlready() {

		DashboardPO d = new DashboardPO(driver);

		String leftMenuOpen = d.getLeftMenu().getAttribute("style");
		while (leftMenuOpen.contains("hidden")) {
			d.getMenuButton().click();
			leftMenuOpen = d.getLeftMenu().getAttribute("style");
		}
		return null;

	}

	public String OpenSelectATimeDrawerIfNotOpenedInFirstAttempt(WebElement Element) throws InterruptedException {

		AppointmentsPO ap = new AppointmentsPO(driver);

		String selectATimeOpen = ap.getSelectATimeDrawer().getAttribute("style");

		int i = 0;

		while (selectATimeOpen.contains("hidden") && i < 20) {

			Element.findElement(By.tagName("span")).click();
			Thread.sleep(2000);
			log.info("calendar date was clicked again");
			System.out.println("calendar date was clicked again");
			selectATimeOpen = ap.getSelectATimeDrawer().getAttribute("style");
			i++;
			System.out.println(i);
			log.info(i);
		}

		return null;

	}

	public Object verifyLowestNumberOfUnitsIsSelectedByDefault(String unitsToBeSelected) {
		Select s4 = new Select(
				driver.findElement(By.xpath("//select[contains(@class, 'at-appointments-checkout-dropdown')]")));
		String defaultSelection = s4.getFirstSelectedOption().getText().trim();

		Assert.assertEquals(defaultSelection, unitsToBeSelected);
		return null;
	}

	public Object verifyCurrentDateIsSelectedByDefault(List<WebElement> element) throws InterruptedException {

		SimpleDateFormat df1 = new SimpleDateFormat("d");
		Calendar today = Calendar.getInstance();
		String todaysMDate = df1.format(today.getTime());

		Thread.sleep(1000);
		int dayCount = element.size();

		for (int i = 0; i < dayCount; i++) {

			if (element.get(i).getText().equals(todaysMDate)) {

				Assert.assertTrue(element.get(i).getAttribute("class").contains("selected"));
				element.get(i).click();
				break;
			}
		}
		Thread.sleep(1000);
		return null;
	}

	public Object verifyNextMonthLastDateIsSelectedByDefault(List<WebElement> element) throws InterruptedException {

		SimpleDateFormat df1 = new SimpleDateFormat("MMM yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		String lastDate = Integer.toString(cal.getActualMaximum(Calendar.DATE));

		String nextMonthYear = df1.format(cal.getTime());

		String monthName = driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]"))
				.getText();

		Assert.assertTrue(monthName.contains(nextMonthYear.toUpperCase()));
		Thread.sleep(1000);
		int dayCount = element.size();

		for (int i = 0; i < dayCount; i++) {

			if (element.get(i).getText().equals(lastDate)) {
				Assert.assertTrue(element.get(i).getAttribute("class").contains("selected"));
				element.get(i).click();
				break;
			}
		}
		Thread.sleep(1000);
		return null;
	}

	public Object verifyFirstDateOfPreviousMonthIsSelectedByDefault(List<WebElement> element)
			throws InterruptedException {

		SimpleDateFormat df1 = new SimpleDateFormat("MMM yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);

		String previousMonthYear = df1.format(cal.getTime());

		String monthName = driver.findElement(By.xpath("//button[contains(@class, 'mat-calendar-period-button')]"))
				.getText();

		Assert.assertTrue(element.get(0).getAttribute("class").contains("selected"));

		Assert.assertTrue(monthName.contains(previousMonthYear.toUpperCase()));

		return null;
	}

	public String OpenNewcardFormIfNotOpenInFirstAttempt() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		PaymentPO p = new PaymentPO(driver);

		String ariaExpanded = p.getNewCardForm().getAttribute("aria-expanded");

		while (ariaExpanded.contains("false")) {
			jse.executeScript("arguments[0].click();", p.getSelectPaymentNewCardButton());
			log.error("NewCard Button was clicked again");
			System.out.println("NewCard Button was clicked again");
			ariaExpanded = p.getNewCardForm().getAttribute("aria-expanded");
		}
		return null;

	}

	public Object MyActivitiesTomorrowClick() throws InterruptedException {

		CalendarPO cp = new CalendarPO(driver);

		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		String classtext = null;

		try {
			classtext = cp.getCalendarTomorrow().getAttribute("class");

		} catch (org.openqa.selenium.NoSuchElementException ne) {
			jse.executeScript("arguments[0].click();",
					driver.findElements(By.xpath("//i[contains(@class, 'right')]")).get(1));

			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class = 'btn-group']//div[contains(@class, 'btn-white')][2]")));

			classtext = cp.getCalendarTomorrow().getAttribute("class");
		}

		if (classtext.contains("cal-out-month")) {
			jse.executeScript("arguments[0].click();",
					driver.findElements(By.xpath("//i[contains(@class, 'right')]")).get(1));

			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class = 'btn-group']//div[contains(@class, 'btn-white')][2]")));

			Thread.sleep(1000);
		}

		jse.executeScript("arguments[0].scrollIntoView(true);",
				cp.getCalendarTomorrow().findElement(By.tagName("span")));

		Thread.sleep(2000);

		Actions a = new Actions(driver);
		a.moveToElement(cp.getCalendarTomorrow().findElement(By.tagName("span"))).click().build().perform();

		Thread.sleep(1000);

		return null;

	}

	public Object calendarTomorrowClick1() throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		AppointmentsPO ap = new AppointmentsPO(driver);

		String classtext = null;

		try {
			classtext = ap.getCalendarTomorrow().getAttribute("class");

		} catch (org.openqa.selenium.NoSuchElementException ne) {
			jse.executeScript("arguments[0].click();", driver.findElement(By.xpath("//i[contains(@class, 'right')]")));
			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
				classtext = ap.getCalendarTomorrow().getAttribute("class");
			}
		}

		if (classtext.contains("cal-out-month")) {
			jse.executeScript("arguments[0].click();", driver.findElement(By.xpath("//i[contains(@class, 'right')]")));

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");
		}

		jse.executeScript("arguments[0].scrollIntoView(true);",
				ap.getCalendarTomorrow().findElement(By.tagName("span")));

		Thread.sleep(1000);

		Actions a = new Actions(driver);
		a.click(ap.getCalendarTomorrow().findElement(By.tagName("span"))).build().perform();

		System.out.println("Calendar date clicked for " + this.getClass().getSimpleName());
		log.info("Calendar Date Clicked for " + this.getClass().getSimpleName());

		Thread.sleep(1000);

		rw.waitForSelectATimeToOpen();

		this.OpenSelectATimeDrawerIfNotOpenedInFirstAttempt(ap.getCalendarTomorrow());

		return null;

	}

	public Object calendarDayAfterTomorrowClick1() throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		AppointmentsPO ap = new AppointmentsPO(driver);

		String classtext = null;

		try {
			classtext = ap.getCalendarDayAfterTomorrow().getAttribute("class");

		} catch (org.openqa.selenium.NoSuchElementException ne) {
			jse.executeScript("arguments[0].click();", driver.findElement(By.xpath("//i[contains(@class, 'right')]")));
			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
				classtext = ap.getCalendarDayAfterTomorrow().getAttribute("class");
			}
		}

		if (classtext.contains("cal-out-month")) {
			jse.executeScript("arguments[0].click();", driver.findElement(By.xpath("//i[contains(@class, 'right')]")));

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");
		}
		Thread.sleep(2000);
		// Actions a = new Actions(driver);
		// a.click(ap.getCalendarTomorrow()).build().perform();

		jse.executeScript("arguments[0].scrollIntoView(true);", ap.getCalendarDayAfterTomorrow());

		Thread.sleep(1000);

		Actions a = new Actions(driver);
		a.click(ap.getCalendarDayAfterTomorrow().findElement(By.tagName("span"))).build().perform();

		System.out.println("Calendar date clicked for " + this.getClass().getSimpleName());
		log.info("Calendar Date Clicked for " + this.getClass().getSimpleName());

		Thread.sleep(3000);

		rw.waitForSelectATimeToOpen();

		this.OpenSelectATimeDrawerIfNotOpenedInFirstAttempt(ap.getCalendarDayAfterTomorrow());

		return null;

	}

	public Object calendarTwoDaysDayAfterClick1() throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		AppointmentsPO ap = new AppointmentsPO(driver);
		String classtext = null;

		try {
			classtext = ap.getCalendarTwodaysAfter().getAttribute("class");

		} catch (org.openqa.selenium.NoSuchElementException ne) {
			jse.executeScript("arguments[0].click();", driver.findElement(By.xpath("//i[contains(@class, 'right')]")));
			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
				classtext = ap.getCalendarTwodaysAfter().getAttribute("class");
			}
		}

		if (classtext.contains("cal-out-month")) {
			jse.executeScript("arguments[0].click();", driver.findElement(By.xpath("//i[contains(@class, 'right')]")));

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");
		}

		jse.executeScript("arguments[0].scrollIntoView(true);", ap.getCalendarTwodaysAfter());

		Thread.sleep(1000);

		Actions a = new Actions(driver);
		a.click(ap.getCalendarTwodaysAfter().findElement(By.tagName("span"))).build().perform();

		System.out.println("Calendar date clicked for " + this.getClass().getSimpleName());
		log.info("Calendar Date Clicked for " + this.getClass().getSimpleName());

		Thread.sleep(1000);

		rw.waitForSelectATimeToOpen();

		this.OpenSelectATimeDrawerIfNotOpenedInFirstAttempt(ap.getCalendarTwodaysAfter());

		return null;

	}

	public Object calendarTomorrowClick() throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		AppointmentsPO ap = new AppointmentsPO(driver);
		String classtext = null;

		try {
			ap.getCalendarTomorrow().isDisplayed();

		} catch (org.openqa.selenium.NoSuchElementException ne) {

			System.out.println("XXXXXXXXXX");
			log.info("XXXXXXXXX");
			jse.executeScript("arguments[0].click();", driver.findElement(By.xpath("//i[contains(@class, 'right')]")));
			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);

			}
		}

		DateFormat df = new SimpleDateFormat("d");
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, 1);
		String TomorrowsDate = df.format(today.getTime());

		int count = ap.getCalendarCells().size();

		for (int i = 0; i < count; i++) {

			classtext = ap.getCalendarCells().get(i).getAttribute("class");

			if (!classtext.contains("past")) {

				String date = ap.getCalendarDates().get(i).getText();

				if (date.equals(TomorrowsDate)) {

					if (classtext.contains("cal-out-month") && classtext.contains("future")) {

						System.out.println("++++++++++");
						log.info("+++++++++++++");
						jse.executeScript("arguments[0].click();",
								driver.findElement(By.xpath("//i[contains(@class, 'right')]")));

						while (ap.getloadingAvailabilityMessage().size() != 0) {
							System.out.println("waiting1");
							Thread.sleep(1000);
						}

						System.out.println("came out of the loop");

						count = ap.getCalendarDates().size();

						for (int j = 0; j < count; j++) {

							classtext = ap.getCalendarCells().get(j).getAttribute("class");

							if (!classtext.contains("past")) {

								date = ap.getCalendarDates().get(j).getText();
								System.out.println(date);

								if (ap.getCalendarDates().get(j).getText().equals(TomorrowsDate)) {

									jse.executeScript("arguments[0].scrollIntoView(true);",
											ap.getCalendarCells().get(j));

									Thread.sleep(1000);

									Actions a = new Actions(driver);
									a.click(ap.getCalendarCells().get(j)).build().perform();

									break;
								}
							}
						}

					} else {
						System.out.println("************");
						log.info("***********");
						jse.executeScript("arguments[0].scrollIntoView(true);", ap.getCalendarCells().get(i));
						Thread.sleep(1000);

						Actions a = new Actions(driver);
						a.click(ap.getCalendarCells().get(i)).build().perform();
						break;

					}

					break;
				}

			} else {
				System.out.println("date is in the past");
			}
		}

		System.out.println("Calendar date clicked for " + this.getClass().getSimpleName());
		log.info("Calendar Date Clicked for " + this.getClass().getSimpleName());

		Thread.sleep(1000);

		rw.waitForSelectATimeToOpen();

		this.OpenSelectATimeDrawerIfNotOpenedInFirstAttempt(ap.getCalendarTomorrow());

		return null;

	}

	public Object calendarDayAfterTomorrowClick() throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		AppointmentsPO ap = new AppointmentsPO(driver);
		String classtext = null;

		try {
			ap.getCalendarDayAfterTomorrow().isDisplayed();

		} catch (org.openqa.selenium.NoSuchElementException ne) {

			System.out.println("XXXXXXXXXX");
			log.info("XXXXXXXXX");
			jse.executeScript("arguments[0].click();", driver.findElement(By.xpath("//i[contains(@class, 'right')]")));
			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);

			}
		}

		DateFormat df = new SimpleDateFormat("d");
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, 2);
		String dayAfterTomorrowDate = df.format(today.getTime());

		int count = ap.getCalendarCells().size();

		for (int i = 0; i < count; i++) {

			classtext = ap.getCalendarCells().get(i).getAttribute("class");

			if (!classtext.contains("past")) {

				String date = ap.getCalendarDates().get(i).getText();

				if (date.equals(dayAfterTomorrowDate)) {

					if (classtext.contains("cal-out-month") && classtext.contains("future")) {

						System.out.println("++++++++++");
						log.info("+++++++++++++");
						jse.executeScript("arguments[0].click();",
								driver.findElement(By.xpath("//i[contains(@class, 'right')]")));

						while (ap.getloadingAvailabilityMessage().size() != 0) {
							System.out.println("waiting1");
							Thread.sleep(1000);
						}

						System.out.println("came out of the loop");

						count = ap.getCalendarDates().size();

						for (int j = 0; j < count; j++) {

							classtext = ap.getCalendarCells().get(j).getAttribute("class");

							if (!classtext.contains("past")) {

								date = ap.getCalendarDates().get(j).getText();
								System.out.println(date);

								if (ap.getCalendarDates().get(j).getText().equals(dayAfterTomorrowDate)) {

									jse.executeScript("arguments[0].scrollIntoView(true);",
											ap.getCalendarCells().get(j));

									Thread.sleep(1000);

									Actions a = new Actions(driver);
									a.click(ap.getCalendarCells().get(j)).build().perform();

									break;
								}
							}
						}

					} else {
						System.out.println("************");
						log.info("***********");
						jse.executeScript("arguments[0].scrollIntoView(true);", ap.getCalendarCells().get(i));
						Thread.sleep(1000);

						Actions a = new Actions(driver);
						a.click(ap.getCalendarCells().get(i)).build().perform();
						break;

					}

					break;
				}

			} else {
				System.out.println("date is in the past");
			}
		}

		System.out.println("Calendar date clicked for " + this.getClass().getSimpleName());
		log.info("Calendar Date Clicked for " + this.getClass().getSimpleName());

		Thread.sleep(1000);

		rw.waitForSelectATimeToOpen();

		this.OpenSelectATimeDrawerIfNotOpenedInFirstAttempt(ap.getCalendarTomorrow());

		return null;

	}

	public Object calendarTwoDaysDayAfterClick() throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		AppointmentsPO ap = new AppointmentsPO(driver);
		String classtext = null;

		try {
			ap.getCalendarTwodaysAfter().isDisplayed();

		} catch (org.openqa.selenium.NoSuchElementException ne) {

			System.out.println("XXXXXXXXXX");
			log.info("XXXXXXXXX");
			jse.executeScript("arguments[0].click();", driver.findElement(By.xpath("//i[contains(@class, 'right')]")));
			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);

			}
		}

		DateFormat df = new SimpleDateFormat("d");
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, 3);
		String TwodaysAfterDate = df.format(today.getTime());

		int count = ap.getCalendarCells().size();

		for (int i = 0; i < count; i++) {

			classtext = ap.getCalendarCells().get(i).getAttribute("class");

			if (!classtext.contains("past")) {

				String date = ap.getCalendarDates().get(i).getText();

				if (date.equals(TwodaysAfterDate)) {

					if (classtext.contains("cal-out-month") && classtext.contains("future")) {

						System.out.println("++++++++++");
						log.info("+++++++++++++");
						jse.executeScript("arguments[0].click();",
								driver.findElement(By.xpath("//i[contains(@class, 'right')]")));

						while (ap.getloadingAvailabilityMessage().size() != 0) {
							System.out.println("waiting1");
							Thread.sleep(1000);
						}

						System.out.println("came out of the loop");

						count = ap.getCalendarDates().size();

						for (int j = 0; j < count; j++) {

							classtext = ap.getCalendarCells().get(j).getAttribute("class");

							if (!classtext.contains("past")) {

								date = ap.getCalendarDates().get(j).getText();
								System.out.println(date);

								if (ap.getCalendarDates().get(j).getText().equals(TwodaysAfterDate)) {

									jse.executeScript("arguments[0].scrollIntoView(true);",
											ap.getCalendarCells().get(j));

									Thread.sleep(1000);

									Actions a = new Actions(driver);
									a.click(ap.getCalendarCells().get(j)).build().perform();

									break;
								}
							}
						}

					} else {
						System.out.println("************");
						log.info("***********");
						jse.executeScript("arguments[0].scrollIntoView(true);", ap.getCalendarCells().get(i));
						Thread.sleep(1000);

						Actions a = new Actions(driver);
						a.click(ap.getCalendarCells().get(i)).build().perform();
						break;

					}

					break;
				}

			} else {
				System.out.println("date is in the past");
			}
		}

		System.out.println("Calendar date clicked for " + this.getClass().getSimpleName());
		log.info("Calendar Date Clicked for " + this.getClass().getSimpleName());

		Thread.sleep(1000);

		rw.waitForSelectATimeToOpen();

		this.OpenSelectATimeDrawerIfNotOpenedInFirstAttempt(ap.getCalendarTwodaysAfter());

		return null;

	}

	public Object enrollInClass(String classToEnroll, String paymentOption, String payMethod, String classFee)
			throws InterruptedException, IOException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		DashboardPO d = new DashboardPO(driver);
		ClassSignUpPO c = new ClassSignUpPO(driver);
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);

		jse.executeScript("arguments[0].click();", d.getMyClassesScheduleButton());

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		this.SelectTomorrowDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		this.SelectClassOrCourseToEnroll(classToEnroll.toUpperCase());

		Thread.sleep(2000);
		if (c.getPopupSignUpButton().isEnabled()) {
			c.getPopupSignUpButton().click();

		} else {
			getScreenshot("SignUp Button", driver);
			c.getPopupCancelButton().click();
			Assert.fail("SignUp button not available");

		}
		Thread.sleep(2000);
		if (!classFee.equalsIgnoreCase("Free")) {
			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().equals(paymentOption)) {
					jse.executeScript("arguments[0].click();",
							driver.findElements(By.tagName("label")).get(i).findElement(By.tagName("i")));
					break;
				}
			}
		}

		jse.executeScript("arguments[0].click();", c.getContinueButton());

		boolean e = this.catchErrorMessagePrivate();

		if (e == true) {
			System.out.println("ERROR: An Error Has Occurred");
			log.error("ERROR: An Error Has Occurred");
			getScreenshot("enrollInClass", driver);
			ErrorMessagesPO er = new ErrorMessagesPO(driver);
			er.getOKButton().click();
		}
		e = this.catchErrorMessagePrivate();
		if (e == true) {
			System.out.println("Error is not going away");
			log.error("Error is not going away");
			getScreenshot("enrollInClass", driver);
			this.returnToDashboard();
		} else {

			Thread.sleep(5000);
			if (!classFee.equalsIgnoreCase("Free")) {

				if (paymentOption.equalsIgnoreCase("Pay Single Class Fee")) {

					wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

					while (!PM.getOnAccountAndSavedCards().isDisplayed())

					{
						Thread.sleep(1000);
						;
					}

					wait.until(ExpectedConditions
							.presenceOfElementLocated(By.xpath("//i[contains(@class,'fal fa-edit')]")));
					if (payMethod.equalsIgnoreCase("Saved Card")) {

						int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
						for (int i = 0; i < count; i++) {
							if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
									.contains("5454")) {

								jse.executeScript("arguments[0].scrollIntoView(true);",
										PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));

								jse.executeScript("arguments[0].click();",
										PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));

								// PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).click();
								break;
							}
						}
					}
					while (!PM.getPaymentButton().isEnabled()) {
						Thread.sleep(1000);
					}

					jse.executeScript("arguments[0].click();", PM.getPaymentButton());

				}
			}
			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
//		Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(2000);

			int count1 = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count1; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

				{
					// rw.linksToBeClickable();
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
					break;
				}

			}
			rw.waitForDashboardLoaded();
		}

		return null;
	}

	public Object enrollInCourse(String courseToEnroll, String paymentOption, String payMethod, String courseFee,
			String CourseStartMonth, int CourseStartYear) throws InterruptedException, IOException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		DashboardPO d = new DashboardPO(driver);
		ClassSignUpPO c = new ClassSignUpPO(driver);
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);

		jse.executeScript("arguments[0].click();", d.getMyCoursesEventsScheduleButton());

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		this.SelectCourseStartYear(CourseStartYear);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		this.SelectCourseStartMonth(CourseStartMonth);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		this.SelectClassOrCourseToEnroll(courseToEnroll.toUpperCase());

		Thread.sleep(2000);
		if (c.getPopupSignupButtonCourse().isEnabled()) {
			c.getPopupSignupButtonCourse().click();

		} else {
			getScreenshot("SignUp Button", driver);
			c.getPopupCancelButtonCourse().click();
			Assert.fail("SignUp button not available");

		}
		Thread.sleep(2000);
		if (!courseFee.equalsIgnoreCase("Free")) {
			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().equals(paymentOption)) {
					jse.executeScript("arguments[0].click();",
							driver.findElements(By.tagName("label")).get(i).findElement(By.tagName("i")));
					break;
				}
			}
		}

		jse.executeScript("arguments[0].click();", c.getContinueButton());

		boolean e = this.catchErrorMessagePrivate();

		if (e == true) {
			System.out.println("ERROR: An Error Has Occurred");
			log.error("ERROR: An Error Has Occurred");
			getScreenshot("enrollInCourse", driver);
			ErrorMessagesPO er = new ErrorMessagesPO(driver);
			er.getOKButton().click();
		}
		e = this.catchErrorMessagePrivate();
		if (e == true) {
			System.out.println("Error is not going away");
			log.error("Error is not going away");
			getScreenshot("enrollInCourse", driver);
			this.returnToDashboard();
		} else {

			Thread.sleep(5000);
			if (!courseFee.equalsIgnoreCase("Free")) {

				if (paymentOption.equalsIgnoreCase("Pay Course Fee")) {

					wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

					while (!PM.getOnAccountAndSavedCards().isDisplayed())

					{
						Thread.sleep(1000);
						;
					}

					wait.until(ExpectedConditions
							.presenceOfElementLocated(By.xpath("//i[contains(@class,'fal fa-edit')]")));
					if (payMethod.equalsIgnoreCase("Saved Card")) {

						int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
						for (int i = 0; i < count; i++) {
							if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
									.contains("5454")) {

								jse.executeScript("arguments[0].scrollIntoView(true);",
										PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));

								jse.executeScript("arguments[0].click();",
										PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));

								// PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).click();
								break;
							}
						}
					}
					while (!PM.getPaymentButton().isEnabled()) {
						Thread.sleep(1000);
					}

					jse.executeScript("arguments[0].click();", PM.getPaymentButton());

				}
			}
			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
//		Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(2000);

			int count1 = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count1; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

				{
					// rw.linksToBeClickable();
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
					break;
				}

			}
			rw.waitForDashboardLoaded();
		}

		return null;
	}

	public Object myClassClickToUnenroll(String classEnrolled) throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		DashboardPO d = new DashboardPO(driver);
		int count = d.getClassInfoSections().size();
		for (int i = 0; i < count; i++) {

			if (d.getClassInfoSections().get(i).getText().contains(classEnrolled.toUpperCase())) {

				jse.executeScript("arguments[0].click();", d.getMyClassesClass1GearButtons().get(i));
				WebDriverWait wait = new WebDriverWait(driver, 30);
				wait.until(ExpectedConditions.visibilityOf(d.getmyClassesUnenrollButtons().get(i)));
				wait.until(ExpectedConditions.elementToBeClickable(d.getmyClassesUnenrollButtons().get(i)));
				jse.executeScript("arguments[0].click();", d.getmyClassesUnenrollButtons().get(i));
				Thread.sleep(1000);
				break;

			}

		}
		return null;

	}

	public Object myCourseClickToUnenroll(String dsiredMonthYear) throws InterruptedException, IOException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		DashboardPO d = new DashboardPO(driver);
		CalendarPO cp = new CalendarPO(driver);

		Thread.sleep(2000);
		rw.waitForDashboardLoaded();
		this.openSideMenuIfNotOpenedAlready();
		d.getMenuMyActivies().click();

		while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1")) {
			Thread.sleep(500);
		}

		WebDriverWait wait1 = new WebDriverWait(driver, 50);
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMenuMyCalendar()));

		d.getMenuMyCalendar().click();
		wait1.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));
		cp.getCalendarViewLink().click();
		Thread.sleep(1000);
		String monthYear = cp.getMonthYear().getText();
		while (!monthYear.equals(dsiredMonthYear)) {
			cp.getRightArrow().click();
			wait1.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class = 'btn-group']//div[contains(@class, 'btn-white')][2]")));
			monthYear = cp.getMonthYear().getText();
		}

		Thread.sleep(1000);

		try {
			jse.executeScript("arguments[0].scrollIntoView(true);", cp.getCalDayBadges().get(1));
			Thread.sleep(1000);
			cp.getCalDayBadges().get(1).click();
			Thread.sleep(1000);
			jse.executeScript("arguments[0].scrollIntoView(true);", cp.getCalEventTitle());
			Thread.sleep(1000);
			cp.getCalEventTitle().click();

			Thread.sleep(1000);
			cp.getUnEnrollBtn().click();
			Thread.sleep(1000);

		} catch (java.lang.IndexOutOfBoundsException iobe) {
			System.out.println("index out of bounds exception");
			log.error("index out of bounds exception");
			iobe.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(iobe.getMessage(), iobe);
			iobe.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot("myCourseClickToUnenroll", driver);
			log.error(eci.getMessage(), eci);
			this.catchErrorMessage();
			// Assert.fail(eci.getMessage());

		}
		return null;

	}

	public Object enrollFamilyMbrInClass(String classToEnroll, String paymentOption, String payMethod, String classFee,
			String familyMbrName) throws InterruptedException, IOException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		DashboardPO d = new DashboardPO(driver);
		ClassSignUpPO c = new ClassSignUpPO(driver);
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);

		jse.executeScript("arguments[0].click();", d.getMyClassesScheduleButton());

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		this.SelectTomorrowDate();

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("classes"))));

		this.SelectClassOrCourseToEnroll(classToEnroll.toUpperCase());

		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//mat-dialog-container[contains(@class, 'mat-dialog-container')]")));

		while (c.getClasslabel().getText().isBlank()) {
			Thread.sleep(500);
		}

		int fmlyMbrcount = c.getFmlyMemberLabel().size();

		for (int i = 0; i < fmlyMbrcount; i++) {

			WebElement fml = c.getFmlyMemberLabel().get(i);
			WebElement fmc = c.getFmlyMemberCheckBox().get(i);

			if (fmc.isSelected()) {
				jse.executeScript("arguments[0].scrollIntoView(true);", fml);
				fml.click(); // de-selects the hoh
				break;
			}
		}

		// Selects the falimy member
		for (int i = 0; i < fmlyMbrcount; i++) {

			WebElement fml = c.getFmlyMemberLabel().get(i);
			// WebElement fmc = c.getFmlyMemberCheckBox().get(i);

			if (fml.getText().contains(familyMbrName)) {

				jse.executeScript("arguments[0].scrollIntoView(true);", fml);
				Thread.sleep(2000);
				fml.click(); // Selects the member

				break;
			}
		}
		Actions actions = new Actions(driver);

		Thread.sleep(2000);
		if (c.getPopupSignUpButton().isEnabled()) {
			jse.executeScript("arguments[0].scrollIntoView(true);", c.getPopupSignUpButton());

			actions.moveToElement(c.getPopupSignUpButton()).click().perform();

		} else {
			getScreenshot("SignUp Button", driver);
			jse.executeScript("arguments[0].scrollIntoView(true);", c.getPopupCancelButton());
			actions.moveToElement(c.getPopupCancelButton()).click().perform();
			Assert.fail("SignUp button not available");

		}
		Thread.sleep(2000);
		if (!classFee.equalsIgnoreCase("Free")) {
			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().equals(paymentOption)) {
					jse.executeScript("arguments[0].click();",
							driver.findElements(By.tagName("label")).get(i).findElement(By.tagName("i")));
					break;
				}
			}
		}
		jse.executeScript("arguments[0].click();", c.getContinueButton());

		boolean e = this.catchErrorMessagePrivate();

		if (e == true) {
			System.out.println("ERROR: An Error Has Occurred");
			log.error("ERROR: An Error Has Occurred");
			getScreenshot("enrollFamilyMbrInClass", driver);
			ErrorMessagesPO er = new ErrorMessagesPO(driver);
			er.getOKButton().click();
		}
		e = this.catchErrorMessagePrivate();
		if (e == true) {
			System.out.println("Error is not going away");
			log.error("Error is not going away");
			getScreenshot("enrollFamilyMbrInClass", driver);
			this.returnToDashboard();
		} else {

			Thread.sleep(5000);
			if (!classFee.equalsIgnoreCase("Free")) {

				if (paymentOption.equalsIgnoreCase("Pay Single Class Fee")) {

					wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

					while (!PM.getOnAccountAndSavedCards().isDisplayed())

					{
						Thread.sleep(1000);
						;
					}

					wait.until(ExpectedConditions
							.presenceOfElementLocated(By.xpath("//i[contains(@class,'fal fa-edit')]")));
					if (payMethod.equalsIgnoreCase("Saved Card")) {

						int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
						for (int i = 0; i < count; i++) {
							if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
									.contains("5454")) {

								jse.executeScript("arguments[0].scrollIntoView(true);",
										PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));

								jse.executeScript("arguments[0].click();",
										PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));

								// PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).click();
								break;
							}
						}
					}
					while (!PM.getPaymentButton().isEnabled()) {
						Thread.sleep(1000);
					}
					jse.executeScript("arguments[0].click();", PM.getPaymentButton());

				}
			}
			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
//		Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			int count1 = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count1; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

				{
					// rw.linksToBeClickable();
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
					break;
				}

			}
			rw.waitForDashboardLoaded();
		}
		return null;
	}

	public Object enrollFamilyMbrInCourse(String courseToEnroll, String paymentOption, String payMethod,
			String courseFee, String CourseStartMonth, String familyMbrName, int CourseStartYear)
			throws InterruptedException, IOException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		DashboardPO d = new DashboardPO(driver);
		ClassSignUpPO c = new ClassSignUpPO(driver);
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);

		jse.executeScript("arguments[0].click();", d.getMyCoursesEventsScheduleButton());

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		this.SelectCourseStartYear(CourseStartYear);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		this.SelectCourseStartMonth(CourseStartMonth);

		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.id("courses"))));

		this.SelectClassOrCourseToEnroll(courseToEnroll.toUpperCase());

		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//mat-dialog-container[contains(@class, 'mat-dialog-container')]")));

		while (c.getClasslabel().getText().isBlank()) {
			Thread.sleep(500);
		}

		int fmlyMbrcount = c.getFmlyMemberLabel().size();

		for (int i = 0; i < fmlyMbrcount; i++) {

			WebElement fml = c.getFmlyMemberLabel().get(i);
			WebElement fmc = c.getFmlyMemberCheckBox().get(i);

			if (fmc.isSelected()) {
				jse.executeScript("arguments[0].scrollIntoView(true);", fml);
				fml.click(); // de-selects the hoh
				break;
			}
		}

		// Selects the falimy member
		for (int i = 0; i < fmlyMbrcount; i++) {

			WebElement fml = c.getFmlyMemberLabel().get(i);
			// WebElement fmc = c.getFmlyMemberCheckBox().get(i);

			if (fml.getText().contains(familyMbrName)) {
				jse.executeScript("arguments[0].scrollIntoView(true);", fml);
				fml.click(); // Selects the member
				break;
			}
		}
		Actions actions = new Actions(driver);

		Thread.sleep(2000);
		if (c.getPopupSignupButtonCourse().isEnabled()) {
			jse.executeScript("arguments[0].scrollIntoView(true);", c.getPopupSignupButtonCourse());

			actions.moveToElement(c.getPopupSignupButtonCourse()).click().perform();

		} else {
			getScreenshot("SignUp Button", driver);
			jse.executeScript("arguments[0].scrollIntoView(true);", c.getPopupCancelButtonCourse());
			actions.moveToElement(c.getPopupCancelButtonCourse()).click().perform();
			Assert.fail("SignUp button not available");

		}
		Thread.sleep(2000);
		if (!courseFee.equalsIgnoreCase("Free")) {
			int radioButtonCount = driver.findElements(By.tagName("label")).size();
			for (int i = 0; i < radioButtonCount; i++) {
				if (driver.findElements(By.tagName("label")).get(i).getText().equals(paymentOption)) {
					jse.executeScript("arguments[0].click();",
							driver.findElements(By.tagName("label")).get(i).findElement(By.tagName("i")));
					break;
				}
			}
		}

		jse.executeScript("arguments[0].click();", c.getContinueButton());

		boolean e = this.catchErrorMessagePrivate();

		if (e == true) {
			System.out.println("ERROR: An Error Has Occurred");
			log.error("ERROR: An Error Has Occurred");
			getScreenshot("enrollFamilyMbrInCourse", driver);
			ErrorMessagesPO er = new ErrorMessagesPO(driver);
			er.getOKButton().click();
		}
		e = this.catchErrorMessagePrivate();
		if (e == true) {
			System.out.println("Error is not going away");
			log.error("Error is not going away");
			getScreenshot("enrollFamilyMbrInCourse", driver);
			this.returnToDashboard();
		} else {

			Thread.sleep(5000);
			if (!courseFee.equalsIgnoreCase("Free")) {

				if (paymentOption.equalsIgnoreCase("Pay Course Fee")) {

					wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));
					while (!PM.getOnAccountAndSavedCards().isDisplayed())

					{
						Thread.sleep(1000);
						;
					}

					wait.until(ExpectedConditions
							.presenceOfElementLocated(By.xpath("//i[contains(@class,'fal fa-edit')]")));
					if (payMethod.equalsIgnoreCase("Saved Card")) {

						int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
						for (int i = 0; i < count; i++) {
							if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
									.contains("5454")) {

								jse.executeScript("arguments[0].scrollIntoView(true);",
										PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));

								jse.executeScript("arguments[0].click();",
										PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));

								// PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).click();
								break;
							}
						}
					}
					while (!PM.getPaymentButton().isEnabled()) {
						Thread.sleep(1000);
					}

					jse.executeScript("arguments[0].click();", PM.getPaymentButton());

				}
			}
			rw.waitForAcceptButton();
			wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));
			// Verifies the success message
//		Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
			PP.getPopupOKButton().click();
			Thread.sleep(1000);

			int count1 = driver.findElements(By.tagName("a")).size();
			for (int i = 0; i < count1; i++) {
				if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dashboard"))

				{
					// rw.linksToBeClickable();
					jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
					break;
				}

			}
			rw.waitForDashboardLoaded();
		}

		return null;
	}

	public Object familyClassClickToUnenroll(String classEnrolled, String enrolledMemberName)
			throws InterruptedException, IOException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		DashboardPO d = new DashboardPO(driver);
		CalendarPO cp = new CalendarPO(driver);

		Thread.sleep(2000);
		rw.waitForDashboardLoaded();
		this.openSideMenuIfNotOpenedAlready();

		while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1")) {

			d.getMenuMyActivies().click();
			Thread.sleep(1000);
			d.getmenuMyActivitiesSubMenu().getAttribute("style");
			System.out.println(d.getmenuMyActivitiesSubMenu().getAttribute("style"));
		}

		WebDriverWait wait1 = new WebDriverWait(driver, 50);
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMenuMyCalendar()));

		d.getMenuMyCalendar().click();
		wait1.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

		cp.getCalendarViewLink().click();
		Thread.sleep(1000);

		this.MyActivitiesTomorrowClick();

		/*
		 * Thread.sleep(1000); cp.getCalDayBadge().click();
		 */
		Thread.sleep(1000);
		try {
			int eventCount = cp.getCalEventTitles().size();

			for (int i = 0; i < eventCount; i++) {

				if (cp.getCalEventTitles().get(i).getText().contains(classEnrolled)) {

					jse.executeScript("arguments[0].scrollIntoView(true);", cp.getCalEventTitles().get(i));
					Thread.sleep(1000);
					Actions a = new Actions(driver);
					a.moveToElement(cp.getCalEventTitles().get(i)).click().build().perform();
					break;
				}
			}

			Thread.sleep(1000);

			Assert.assertTrue(cp.getClassName().getText().contains(classEnrolled));
			Assert.assertTrue(cp.getEnrolledMemberName().getText().contains(enrolledMemberName));
			cp.getUnEnrollBtn().click();
			Thread.sleep(1000);
		} catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			log.error("No Element present");
			ne.printStackTrace();
			getScreenshot("familyClassUnenroll", driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}
		return null;

	}

	public Object familyCourseClickToUnenroll(String dsiredMonthYear, String classEnrolled, String enrolledMemberName)
			throws InterruptedException, IOException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		DashboardPO d = new DashboardPO(driver);
		CalendarPO cp = new CalendarPO(driver);

		Thread.sleep(2000);
		rw.waitForDashboardLoaded();
		this.openSideMenuIfNotOpenedAlready();

		while (!d.getmenuMyActivitiesSubMenu().getAttribute("style").contains("1")) {

			d.getMenuMyActivies().click();
			Thread.sleep(1000);
			d.getmenuMyActivitiesSubMenu().getAttribute("style");
		}

		WebDriverWait wait1 = new WebDriverWait(driver, 50);
		wait1.until(ExpectedConditions.elementToBeClickable(d.getMenuMyCalendar()));

		d.getMenuMyCalendar().click();
		wait1.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]")));

		cp.getCalendarViewLink().click();
		Thread.sleep(1000);

		String monthYear = cp.getMonthYear().getText();
		while (!monthYear.equals(dsiredMonthYear)) {
			cp.getRightArrow().click();
			wait1.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class = 'btn-group']//div[contains(@class, 'btn-white')][2]")));
			monthYear = cp.getMonthYear().getText();
		}

		Thread.sleep(1000);

		try {

			jse.executeScript("arguments[0].scrollIntoView(true);", cp.getCalDayBadge());
			Thread.sleep(1000);
			cp.getCalDayBadge().click();

			Thread.sleep(1000);

			int eventCount = cp.getCalEventTitles().size();

			for (int i = 0; i < eventCount; i++) {

				if (cp.getCalEventTitles().get(i).getText().contains(classEnrolled)) {

					jse.executeScript("arguments[0].scrollIntoView(true);", cp.getCalEventTitles().get(i));
					Thread.sleep(1000);
					Actions a = new Actions(driver);
					a.moveToElement(cp.getCalEventTitles().get(i)).click().build().perform();
					break;
				}
			}

			Thread.sleep(1000);

			Assert.assertTrue(cp.getEnrolledMemberName().getText().contains(enrolledMemberName));
			Thread.sleep(1000);
			cp.getUnEnrollBtn().click();
			Thread.sleep(1000);

		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot("familyCourseClickToUnenroll", driver);
			log.error(ae.getMessage(), ae);
			ae.printStackTrace();
			// Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot("familyCourseClickToUnenroll", driver);
			log.error(ne.getMessage(), ne);
			// Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot("familyCourseClickToUnenroll", driver);
			log.error(eci.getMessage(), eci);
			this.catchErrorMessage();
			// Assert.fail(eci.getMessage());

		}
		return null;

	}

	public Object verifyOnAccountIsPresentAndSelectedByDefault() {
		UnenrollPO u = new UnenrollPO(driver);
		ArrayList<String> paymethods = new ArrayList<String>();
		int count = u.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();

		for (int i = 0; i < count; i++) {

			paymethods.add(u.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText().trim());
		}

		Assert.assertTrue(paymethods.contains("On Account"));

		for (int i = 0; i < count; i++) {

			if (u.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText()
					.contains(" On Account"))

			{
				Assert.assertTrue(u.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).isEnabled());
				break;
			}
		}

		return null;
	}

	public Object selectSavedcard() throws InterruptedException {

		PaymentMethodsPO PM = new PaymentMethodsPO(driver);

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.textToBePresentInElement(PM.getTotalAmount(), "$"));

		Thread.sleep(2000);

		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText().contains("5454")) {

				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].scrollIntoView(true);",
						PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));

				jse.executeScript("arguments[0].click();",
						PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));

				// PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).click();
				break;
			}
		}
		return null;
	}

	public Object selectNewcardToPay(String memberName) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		PaymentMethodsPO PM = new PaymentMethodsPO(driver);

		while (!PM.getNewCardButton().isDisplayed())

		{
			Thread.sleep(1000);

		}

		jse.executeScript("arguments[0].click();", PM.getNewCardButton());
		Thread.sleep(3000);

		String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
		while (opacity.contains("1")) {
			PM.getNewCardButton().click();
			Thread.sleep(3000);
			opacity = driver.findElement(By.id("show-saved")).getAttribute("style");

		}
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

		Assert.assertTrue(PM.getCloseButton().isDisplayed());
		Thread.sleep(2000);
		System.out.println("Pay Button disabled:" + PM.getPaymentButton().getAttribute("disabled"));
//		Assert.assertFalse(PM.getPaymentButton().isEnabled());

//			System.out.println(PM.getNameOnCardField().getAttribute("value"));
		Assert.assertEquals(memberName, PM.getNameOnCardField().getAttribute("value"));

		PM.getCardNumberField().sendKeys("4111111111111111");
		PM.getExpirationMonth().sendKeys("04");
		PM.getExpirationYear().sendKeys("22");
		PM.getSecurityCode().sendKeys("123");
		jse.executeScript("arguments[0].click();", PM.getCheckBox());
		while (!PM.getPaymentButton().isEnabled()) {
			Thread.sleep(1000);
		}

		// Clicks on the Pay button without signature
		jse.executeScript("arguments[0].click();", PM.getPaymentButton());
		System.out.println(PM.getPopupContent().getText());
		Assert.assertTrue(PM.getPopupContent().getText().contains("A signature is required to continue."));
		PM.getPopupOk().click();
		Thread.sleep(1000);
		jse.executeScript("arguments[0].click();", PM.getSaveCardNo());
		Thread.sleep(1000);
		return null;
	}

	public Object selectNewcardToRefund(String memberName) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		UnenrollPO u = new UnenrollPO(driver);
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);

		while (!u.getNewCardButton().isDisplayed())

		{
			Thread.sleep(1000);

		}

		jse.executeScript("arguments[0].click();", u.getNewCardButton());
		Thread.sleep(5000);

		String opacity = driver.findElement(By.id("show-saved")).getAttribute("style");
		while (opacity.contains("1")) {
			u.getNewCardButton().click();
			Thread.sleep(5000);
			opacity = driver.findElement(By.id("show-saved")).getAttribute("style");

		}
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("show-newcard")), "style", "1"));

		Assert.assertTrue(PM.getCloseButton().isDisplayed());
		Thread.sleep(2000);
		System.out.println("Refund Button disabled:" + u.getRefundButton().getAttribute("disabled"));
//		Assert.assertFalse(u.getRefundButton().isEnabled());

//			System.out.println(PM.getNameOnCardField().getAttribute("value"));
		Assert.assertEquals(memberName, PM.getNameOnCardField().getAttribute("value"));

		PM.getCardNumberField().sendKeys("4111111111111111");
		PM.getExpirationMonth().sendKeys("04");
		PM.getExpirationYear().sendKeys("22");
		PM.getSecurityCode().sendKeys("123");
		jse.executeScript("arguments[0].click();", PM.getCheckBox());
		while (!u.getRefundButton().isEnabled()) {
			Thread.sleep(1000);
		}

		// Clicks on the Pay button without signature
		jse.executeScript("arguments[0].click();", u.getRefundButton());
		System.out.println(PM.getPopupContent().getText());
		Assert.assertTrue(PM.getPopupContent().getText().contains("A signature is required to continue."));
		PM.getPopupOk().click();
		Thread.sleep(1000);
		jse.executeScript("arguments[0].click();", PM.getSaveCardNo());
		Thread.sleep(1000);
		return null;
	}

	public Object ValidatechangeAppointmentScreen(String startTime1, String appointmentToBook1)
			throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);

		int appointmentsCount = d.getMyAppts().size();

		for (int i = 0; i < appointmentsCount; i++) {
			if (d.getMyAppts().get(i).getText().contains(tomorrowsDate))

			{

				if (d.getMyAppts().get(i).getText().contains(startTime1)) {

					Assert.assertTrue(d.getMyAppts().get(i).getText().contains(appointmentToBook1.toUpperCase()));
					wait.until(ExpectedConditions
							.elementToBeClickable(d.getMyAppts().get(i).findElement(By.tagName("i"))));
					jse.executeScript("arguments[0].click();", d.getMyAppts().get(i).findElement(By.tagName("i")));

					WebElement EditButton = d.getEditButton().get(i);

					wait.until(ExpectedConditions.visibilityOf(EditButton));
					wait.until(ExpectedConditions.elementToBeClickable(EditButton));

					jse.executeScript("arguments[0].click();", EditButton);
					break;
				}
			}
		}

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-md-12']/h2")));
		Thread.sleep(2000);

		AppointmentsPO ap = new AppointmentsPO(driver);
		jse.executeScript("arguments[0].click();", ap.getEditApptChangeButton());
		Thread.sleep(1000);
		Assert.assertTrue(
				ap.getChangeFeeSection().getText().contains("If you proceed, you will be charged a Change Fee of"));

		if (appointmentToBook1.contains("Grp")) {

			Assert.assertTrue(ap.getChangeFeeSection().getText().contains(
					" This will remove other participants from your appointment. You will need to add them again when you select your new appointment."));
		}

		jse.executeScript("arguments[0].click();", ap.getEditApptProceedButton1());

		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");
		Thread.sleep(3000);
		return null;
	}

	public Object makeNewGrpAppointmentSelections(String participant, String appointmentToBook2, String resourceName3)
			throws InterruptedException {

		AppointmentsPO ap = new AppointmentsPO(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, 30);

		Select s1 = new Select(ap.getBookableItem());
		Thread.sleep(2000);
		List<WebElement> Products = s1.getOptions();

		int count1 = Products.size();
		System.out.println(count1);

		for (int j = 0; j < count1; j++) {
			String product = Products.get(j).getText();

			if (product.equals(appointmentToBook2)) {
				s1.selectByVisibleText(product);
				break;
			}
		}

		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");

		Thread.sleep(1000);
		Assert.assertEquals(ap.getGroupApptsHeader().getText(), "Group Appointments");
		Assert.assertEquals(ap.getGroupMinPersons().getText(), "1");
		Assert.assertEquals(ap.getGroupMaxPersons().getText(), "2");

		if (!participant.equals("none")) {
			ap.getGroupMemberSearchInput().sendKeys(participant);
			jse.executeScript("arguments[0].click();", ap.getGroupMemberSearchButton());

			Thread.sleep(2000);

			int memberCount = ap.getGroupPopupAddButtons().size();
			System.out.println(memberCount);
			for (int i = 0; i < memberCount; i++)

			{
				String text = ap.getGroupPopupMembers().get(i).getText();
				System.out.println(text);
				if (ap.getGroupPopupMembers().get(i).getText().contains(participant)) {
					wait.until(ExpectedConditions.elementToBeClickable(ap.getGroupPopupAddButtons().get(i)));
					ap.getGroupPopupAddButtons().get(i).click();
					break;
				}
			}

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");

		}

		WebElement rt = ap.getResourceType();

		Select s2 = new Select(rt);
		Thread.sleep(2000);
		if (!s2.getFirstSelectedOption().getText().equals("No Selection Required")) {
			List<WebElement> Resources = s2.getOptions();

			int count2 = Resources.size();
			System.out.println(count2);

			for (int k = 0; k < count2; k++) {
				String resource = Resources.get(k).getText();

				if (resource.equals(resourceName3)) {
					s2.selectByVisibleText(resource);
					break;
				}
			}
			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");
			Thread.sleep(3000);

		}
		return null;

	}

	public Object makeNewAppointmentSelections(String appointmentToBook2, String resourceName3)
			throws InterruptedException {

		AppointmentsPO ap = new AppointmentsPO(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, 30);

		Select s1 = new Select(ap.getBookableItem());
		Thread.sleep(2000);
		List<WebElement> Products = s1.getOptions();

		int count1 = Products.size();
		System.out.println(count1);

		for (int j = 0; j < count1; j++) {
			String product = Products.get(j).getText();

			if (product.equals(appointmentToBook2)) {
				s1.selectByVisibleText(product);
				break;
			}
		}

		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");

		WebElement rt = ap.getResourceType();

		Select s2 = new Select(rt);
		Thread.sleep(2000);

		if (!s2.getFirstSelectedOption().getText().equals("No Selection Required")) {
			List<WebElement> Resources = s2.getOptions();

			int count2 = Resources.size();
			System.out.println(count2);

			for (int k = 0; k < count2; k++) {
				String resource = Resources.get(k).getText();

				if (resource.equals(resourceName3)) {
					s2.selectByVisibleText(resource);
					break;
				}
			}
			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting1");
				Thread.sleep(1000);
			}

			System.out.println("came out of the loop");
			Thread.sleep(3000);
		}

		return null;
	}

	public Object selectClub(String clubName) throws InterruptedException {
		AppointmentsPO ap = new AppointmentsPO(driver);
		Select se = new Select(ap.getclubs());
		List<WebElement> Clubs = se.getOptions();
		int x = 0;
		while (!ap.getclubs().isEnabled() && x < 100) {
			System.out.println("Waiting for Clubs drop down to not be blank");
			x++;
		}

		int count0 = Clubs.size();
		System.out.println("1 " + count0);

		for (int i = 0; i < count0; i++) {
			String club = Clubs.get(i).getText();

			if (club.equals(clubName)) {
				se.selectByVisibleText(club);
				break;
			}
		}
		Thread.sleep(2000);

		return null;
	}

	public Object selectProductCategory(String productCategory) {
		AppointmentsPO ap = new AppointmentsPO(driver);
		WebElement bic = ap.getBookableItemCategory();

		Select s = new Select(bic);
		List<WebElement> ProductCategories = s.getOptions();

		int count = ProductCategories.size();
		System.out.println(count);

		for (int i = 0; i < count; i++) {
			String category = ProductCategories.get(i).getText();

			if (category.equals(productCategory)) {
				s.selectByVisibleText(category);
				break;
			}
		}
		return null;
	}

	public Object moveToElementAndClick(WebDriver driver, WebElement element) {

		Actions a = new Actions(driver);
		a.moveToElement(element).click().build().perform();

		return null;
	}

	public Object scrollIntoViewAndClick(WebDriver driver, WebElement element) throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(1000);
		jse.executeScript("arguments[0].click();", element);

		return null;
	}

	public Object scrollIntoView(WebDriver driver, WebElement element) throws InterruptedException {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", element);

		return null;
	}

	public String selectAppointmentTime(String resourceName) throws InterruptedException {
		String startTime = null;

		AppointmentsPO ap = new AppointmentsPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		for (int m = 0; m < ap.getApptBox().size(); m++) {
			String bookName = ap.getApptBox().get(m).getText();
			if (bookName.contains(resourceName)) {
				List<WebElement> TimeSlots = ap.getTimeSlotContainers().get(m).findElements(By.tagName("a"));
				WebElement AftrnunSlot = TimeSlots.get(1);
				wait.until(ExpectedConditions.elementToBeClickable(AftrnunSlot));
				while (!AftrnunSlot.isEnabled())// while button is NOT(!) enabled
				{
					System.out.println("Waiting for available times");
				}

				// AftrnunSlot.click();

				jse.executeScript("arguments[0].click();", AftrnunSlot);
				Thread.sleep(1000);

				WebElement AftrenoonAvailableTimeContainer = ap.getTimeSlotContainers().get(m)
						.findElement(By.id("tab-2-0"));
				List<WebElement> AftrenoonAvailableTimes = AftrenoonAvailableTimeContainer
						.findElements(By.tagName("button"));
				WebElement secondAvailableTimeAfternoon = AftrenoonAvailableTimes.get(1);
//				while (!st2.isEnabled())//while button is NOT(!) enabled
//				{
//				Thread.sleep(200);
//				}

				wait.until(ExpectedConditions.elementToBeClickable(secondAvailableTimeAfternoon));
				startTime = secondAvailableTimeAfternoon.getText();
				System.out.println(startTime);
				jse.executeScript("arguments[0].click();", secondAvailableTimeAfternoon);
				break;
			}
		}
		return startTime;
	}

	public String purchasePackage(String packageToBuy) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		DashboardPO d = new DashboardPO(driver);
		ShopPackagesPO sp = new ShopPackagesPO(driver);
		PaymentMethodsPO PM = new PaymentMethodsPO(driver);
		PurchaseConfirmationPO PP = new PurchaseConfirmationPO(driver);
		ThankYouPO TY = new ThankYouPO(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		this.openSideMenuIfNotOpenedAlready();

		d.getMenuShopPackages().click();

		while (!sp.getPackagesList().isDisplayed()) {
			Thread.sleep(1000);
			System.out.println("Waiting for the packages to be displayed");
		}
		wait.until(ExpectedConditions.visibilityOf(sp.getPackagesList()));
		wait.until(ExpectedConditions.visibilityOf(sp.getWarningMsg()));

//	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'row m-t-md']")));

		sp.getKeyWord().sendKeys(packageToBuy);

		for (int i = 0; i < sp.getPackageNames().size(); i++)

		{
			if (sp.getPackageNames().get(i).getText().equals(packageToBuy))

			{
				jse.executeScript("arguments[0].click();", sp.getPurchaseButtons().get(i));
				break;
			}

		}
		Thread.sleep(3000);

		wait.until(ExpectedConditions.textToBePresentInElement(PP.getShopPackageTotalAmount(), "$"));
		Thread.sleep(3000);

		while (!PM.getOnAccountAndSavedCards().isDisplayed())

		{
			Thread.sleep(1000);
			;
		}
		jse.executeScript("arguments[0].scrollIntoView(true);", PM.getOnAccountAndSavedCards());

		int count = PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).size();
		for (int i = 0; i < count; i++) {
			if (PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i).getText().contains("5454")) {

				jse.executeScript("arguments[0].click();",
						PM.getOnAccountAndSavedCards().findElements(By.tagName("label")).get(i));
				break;
			}
		}

		// Click the Pay button
		while (!PM.getPaymentButton().isEnabled()) {
			Thread.sleep(1000);
		}

		jse.executeScript("arguments[0].click();", PM.getPaymentButton());

		rw.waitForAcceptButton();
		wait.until(ExpectedConditions.elementToBeClickable(PP.getPopupOKButton()));

		// Verifies the success message
		Assert.assertEquals("Success", PP.getPopupSuccessMessage().getText());
		PP.getPopupOKButton().click();
		Thread.sleep(1000);

		// Note down the Receipt number
		String receiptNumber = TY.getReceiptNumber().getText();

		// Navigate to Select Classes
		int count1 = driver.findElements(By.tagName("a")).size();
		for (int i = 0; i < count1; i++) {
			if (driver.findElements(By.tagName("a")).get(i).getText().equals("Dasshboard"))

			{
				// rw.linksToBeClickable();
				jse.executeScript("arguments[0].click();", driver.findElements(By.tagName("a")).get(i));
				break;
			}

		}

		return receiptNumber;
	}

	public void deleteTempFolderFiles() {

		String path = ("C:\\Automation\\Delete.bat");

		try {
			Runtime.getRuntime().exec(path);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return;
	}

	public void runTerminalCommand(String command, String logText) {

		String path = System.getProperty("user.dir");
		String line;

		try {

			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd \"" + path + "\" && " + command);
			System.out.println("cd \"" + path + "\" && " + command);
			Process p = builder.start();
			Thread.sleep(15000);

			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			line = r.readLine();

			while (true) {
				line = r.readLine();

				if (line.contains(logText)) {
					Thread.sleep(5000);
					System.out.println(line);
					break;
				}

			}

		} catch (Exception e)

		{

			e.printStackTrace();
		}
		return;
	}

}