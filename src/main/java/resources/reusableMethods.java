package resources;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import pageObjects.PaymentPO;
import pageObjects.PurchaseConfirmationPO;
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

	public String activeMember1Login() throws InterruptedException {

		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember1_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember1_password"));
		l.getLoginButton().click();
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember2Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember2_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember2_password"));
		l.getLoginButton().click();
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember3Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember3_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember3_password"));
		l.getLoginButton().click();
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember4Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember4_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember4_password"));
		l.getLoginButton().click();
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember5Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember5_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember5_password"));
		l.getLoginButton().click();
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember6Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember6_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember6_password"));
		l.getLoginButton().click();
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember7Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember7_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember7_password"));
		l.getLoginButton().click();
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember8Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("activeMember8_username"));
		l.getuserPassword().sendKeys(prop.getProperty("activeMember8_password"));
		l.getLoginButton().click();
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember9Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys("NoOAMember");
		l.getuserPassword().sendKeys("Testing1!");
		l.getLoginButton().click();
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember10Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys("NoCCMember");
		l.getuserPassword().sendKeys("Testing1!");
		l.getLoginButton().click();
		rw.waitForDashboardLoaded();
		return null;
	}

	public String activeMember11Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys("NoOANoCCMember");
		l.getuserPassword().sendKeys("Testing1!");
		l.getLoginButton().click();
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
		rw.waitForDashboardLoaded1();
		return null;
	}

	public String collectionsMember1Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("collectionsMember1_username"));
		l.getuserPassword().sendKeys(prop.getProperty("collectionsMember1_password"));
		l.getLoginButton().click();
		rw.waitForDashboardLoaded1();
		return null;
	}

	public String prospectMember1Login() throws InterruptedException {
		rw.waitForLoginLoginButton();
		LoginPO l = new LoginPO(driver);
		l.getuserName().sendKeys(prop.getProperty("prospectMember1_username"));
		l.getuserPassword().sendKeys(prop.getProperty("prospectMember1_password"));
		l.getLoginButton().click();
		rw.waitForDashboardLoaded1();
		return null;
	}

	public String memberLogout() throws InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		// Actions a = new Actions(driver);
		d.getLogoutButton().click();
		// a.moveToElement(d.getLogoutButton()).click().build().perform();
		rw.waitForLoginLoginButton();
		return null;
	}

	public String returnToDashboard() throws InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		Thread.sleep(1000);
		String leftMenuOpen = d.getLeftMenu().getAttribute("ng-reflect-opened");
		while (leftMenuOpen.equals("false")) {
			d.getMenuButton().click();
			leftMenuOpen = d.getLeftMenu().getAttribute("ng-reflect-opened");
		}
		wait.until(ExpectedConditions.elementToBeClickable(d.getDashboardButton()));
		d.getDashboardButton().click();
//		d.getBreadcrumbDashboard().click();
		rw.waitForDashboardLoaded();
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

	public String unenrollFromClass() throws IOException, InterruptedException {
		DashboardPO d = new DashboardPO(driver);

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
				d.getMyClassesClass1GearButton().click();

				wait.until(ExpectedConditions.visibilityOf(d.getmyClassesUnenrollButton()));
				wait.until(ExpectedConditions.elementToBeClickable(d.getmyClassesUnenrollButton()));
				d.getmyClassesUnenrollButton().click();
				Thread.sleep(1000);
				UnenrollPO u = new UnenrollPO(driver);
				wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
				wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));
				u.getUnenrollButton().click();
				Thread.sleep(1000);
				wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
				u.getUnenrollConfirmYesButton().click();

				wait.until(ExpectedConditions.stalenessOf(u.getUnenrollConfirmYesButton()));
				wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
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
			// JavascriptExecutor jse = (JavascriptExecutor) driver;
			// jse.executeScript("arguments[0].click();", d.getMenuMyCalendar());
			// d.getMenuMyCalendar().click();
			log.info("Menu My Calendar clicked");
			System.out.println("Menu My Calendar clicked");
			wait1.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//div[@class = 'btn-group']//div[contains(@class, 'btn-white')][2]")));
			String monthYear = cp.getMonthYear().getText();
			while (!monthYear.equals(dsiredMonthYear)) {
				cp.getRightArrow().click();
				wait1.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[@class = 'btn-group']//div[contains(@class, 'btn-white')][2]")));
				monthYear = cp.getMonthYear().getText();
			}
			Thread.sleep(1000);

			cp.getCalDayBadge().click();
			Thread.sleep(1000);
			cp.getCalEventTitle().click();
			Thread.sleep(1000);
			cp.getUnEnrollBtn().click();
			Thread.sleep(1000);
			UnenrollPO u = new UnenrollPO(driver);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(u.getUnenrollButton()));
			wait.until(ExpectedConditions.elementToBeClickable(u.getUnenrollButton()));
			u.getUnenrollButton().click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
			u.getUnenrollConfirmYesButton().click();
			wait.until(ExpectedConditions.stalenessOf(u.getUnenrollConfirmYesButton()));
			wait.until(ExpectedConditions.visibilityOf(u.getPopupMessageBox()));
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

	public String catchErrorMessage() throws InterruptedException {
		boolean e = this.catchErrorMessagePrivate();
		if (e == true) {
			System.out.println("ERROR: An Error Has Occurred");
			ErrorMessagesPO er = new ErrorMessagesPO(driver);
			er.getOKButton().click();
			this.returnToDashboard();
			Assert.assertFalse(e);
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
		PP.getMyPackagesButton().click();
		int IntUnitCount = 0;
		Thread.sleep(3000);
		int packagesCount = PP.getPackagesList().size();
		for (int j = 0; j < packagesCount; j++) {
			String text = PP.getPackagesList().get(j).getText();
			if (text.contains(packageName)) {
				String[] unitCount = PP.getUnitsCount().get(j).getText().split(" ");
				String formattedUnitCount = unitCount[0].trim();
				IntUnitCount = Integer.parseInt(formattedUnitCount);
				PP.getMyPackagesButton().click();
				break;
			}
		}
		return IntUnitCount;
	}

	public int getPackageUnitsForMember(String packageName, String memberName) throws InterruptedException {

		DashboardPO d = new DashboardPO(driver);
		PackagesPO pp = new PackagesPO(driver);

		this.openSideMenuIfNotOpenedAlready();

		// Note the package units
		d.getMenuMyAccount().click();
		while (!d.getmenuMyAccountSubMenu().getAttribute("style").contains("1")) {
			Thread.sleep(1000);
		}
		d.getMenuPackages().click();
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.className("ibox"))));
		int IntUnitCount = 0;
		for (int i = 0; i < pp.getMemberSections().size(); i++) {

			if (pp.getMemberSections().get(i).getText().contains(memberName.toUpperCase())) {
				List<WebElement> Packages = pp.getMemberSections().get(i)
						.findElements(By.className("ng-star-inserted"));

				for (int k = 0; k < Packages.size(); k++) {
					if (Packages.get(k).getText().contains(packageName)) {
						String[] text = Packages.get(k).getText().split("\n");
						String unitCount = text[2];
						IntUnitCount = Integer.parseInt(unitCount);
						break;
					}

				}
				break;
			}
		}
		d.getMenuMyAccount().click();
		this.returnToDashboard();

		return IntUnitCount;
	}

	public Object ThankYouPageValidations() {
		ThankYouPO TY = new ThankYouPO(driver);

		// Verifies the text on Thank You page and Print Receipt Popup
		Assert.assertEquals("THANK YOU FOR YOUR ORDER", (TY.getThankYouText().getText()));
		Assert.assertTrue(TY.getsmallText().getText().contains("The receipt # for this transaction is:"));
		Assert.assertTrue(TY.getsmallText().getText().contains("Have fun!"));
		Assert.assertTrue(
				TY.getsmallText().getText().contains("Everything was processed and you are all ready to go."));
		Assert.assertTrue(TY.getsmallText().getText().contains(
				"Participants with a valid email address on file will receive a confirmation email with details of this purchase."));

		// Verifies the links to navigate to Dashboard and other pages are displayed
		Assert.assertTrue(this.isElementPresent(By.xpath("//a[@href = '#/Home']")));
		Assert.assertTrue(this.isElementPresent(By.xpath("//a[@href = '#/ClassList']")));
		Assert.assertTrue(this.isElementPresent(By.xpath("//a[@href = '#/CourseList']")));
		Assert.assertTrue(this.isElementPresent(By.xpath("//a[@href = '#/Appointments']")));
		return null;
	}

	public Object ReceiptPopupValidations() {
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

		WebElement MonthNames = driver.findElement(By.xpath("//div[@class='col-md-9']"));
		int monthCount = MonthNames.findElements(By.tagName("label")).size();
		for (int i = 0; i < monthCount; i++) {
			String monthName = MonthNames.findElements(By.tagName("label")).get(i).getText();
			if (monthName.equals(CourseStartMonth)) {
				MonthNames.findElements(By.tagName("label")).get(i).click();
				break;
			}

		}
		return null;
	}

	public Object SelectClassOrCourseToEnroll(String ClassOrCourseToEnroll) {
		int ClassOrCourseCount = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).size();
		for (int j = 0; j < ClassOrCourseCount; j++) {
			String ClassOrCourseName = driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j)
					.getText();

			if (ClassOrCourseName.contains(ClassOrCourseToEnroll)) {
				driver.findElements(By.xpath("//div[contains(@class, 'column2')]")).get(j).click(); // Click on the
																									// specific
																									// Course
				break;
			}
		}
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

	public Object ApptCheckinInCOG(String memberName, String appointmentName, String username)
			throws InterruptedException {

		driver.get(prop.getProperty("COGLoginPage"));

		driver.findElement(By.id("UserName")).sendKeys("bhagya");
		driver.findElement(By.id("Password")).sendKeys("111");
		driver.findElement(By.id("submit")).click();
		Thread.sleep(1000);
		Select s = new Select(driver.findElement(By.id("ddl_clubSelection")));
		s.selectByVisibleText("Studio Jonas");
		driver.findElement(By.id("submit")).click();
		Thread.sleep(3000);
		WebElement FrontDeskTile = driver.findElement(By.xpath("(//div[@class='tile'])[1]"));
		int count = FrontDeskTile.findElements(By.tagName("a")).size();
		for (int i = 0; i < count; i++) {
			System.out.println(FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href"));
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
				CheckInOptions.get(j).findElement(By.className("checkbox")).click();
				System.out.println("clicked");
				break;
			}

		}
		driver.findElement(By.xpath("//i[@class='fa fa-thumbs-up mrs']")).click();
		driver.findElement(By.xpath("//a[@href='/CompeteOnTheGo/Account/Logoff']")).click();
		driver.get(prop.getProperty("EMELoginPage"));
		this.activeMemberLogin(username, "Testing1!");
		return null;
	}

	public Object ConfirmAndCancelAppointmentNoFee(String Date, String startTime, String appointmentToBook)
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

					wait.until(ExpectedConditions
							.elementToBeClickable(d.getMyAppts().get(i).findElement(By.tagName("i"))));
					d.getMyAppts().get(i).findElement(By.tagName("i")).click();

					WebElement EditButton = d.getEditButton().get(i);

					wait.until(ExpectedConditions.visibilityOf(EditButton));
					wait.until(ExpectedConditions.elementToBeClickable(EditButton));

					EditButton.click();
					break;
				}
			}
		}

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='col-sm-12']/h2")));
		Thread.sleep(2000);
		AppointmentsPO a = new AppointmentsPO(driver);
		Assert.assertEquals(a.getEditApptPageHeader().getText(), "Edit Appointment");
		wait.until(ExpectedConditions.visibilityOf(a.getEditApptCancelButton()));
		a.getEditApptCancelButton().click();
		WebElement wait2 = a.getEditApptProceedButton();
		while (!wait2.isEnabled())// while button is NOT(!) enabled
		{
//			Thread.sleep(200);
		}
		a.getEditApptProceedButton().click();
		Thread.sleep(1000);
		boolean result1 = rw.popupMessageYesButton();
		if (result1 == true) {
//				Thread.sleep(500);	
		}
		a.getEditApptCancelYesButton().click();
//		
		Thread.sleep(2000);
		Assert.assertEquals(d.getPageHeader().getText(), "Dashboard");

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
		// DashboardPO p = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		String startTime = null;
		// p.getMyApptsScheduleButton().click();

		AppointmentsPO ap = new AppointmentsPO(driver);
		Thread.sleep(2000);

		this.catchErrorMessage();

		Select se = new Select(ap.getclubs());
		List<WebElement> Clubs = se.getOptions();

		while (!ap.getclubs().isEnabled()) {
			System.out.println("Waiting for Clubs drop down to not be blank");
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

		Select s1 = new Select(ap.getBookableItem());
		Thread.sleep(2000);
		List<WebElement> Products = s1.getOptions();

		int count1 = Products.size();
		System.out.println(count1);

		for (int j = 0; j < count1; j++) {
			String product = Products.get(j).getText();

			if (product.equals(appointmentToBook)) {
				s1.selectByVisibleText(product);
				break;
			}
		}

		WebElement rt = ap.getResourceType();

		Select s2 = new Select(rt);
		Thread.sleep(2000);
		List<WebElement> Resources = s2.getOptions();

		int count2 = Resources.size();
		System.out.println(count2);

		for (int k = 0; k < count2; k++) {
			String resource = Resources.get(k).getText();

			if (resource.equals(resourceName1)) {
				s2.selectByVisibleText(resource);
				break;
			}
		}
		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");
		Thread.sleep(2000);

		String classtext = ap.getCalendarTomorrow().getAttribute("class");

		if (classtext.contains("cal-out-month")) {
			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting");
				Thread.sleep(1000);
			}
			System.out.println("came out of the loop");
		}

		Actions a = new Actions(driver);
		a.click(ap.getCalendarTomorrow()).build().perform();
		System.out.println("Calendar date clicked for " + this.getClass().getSimpleName());
		log.info("Calendar Date Clicked for " + this.getClass().getSimpleName());
		// ap.getCalendarTomorrow().click();
		Thread.sleep(3000);
		rw.waitForSelectATimeToOpen();

		OpenSelectATimeDrawerIfNotOpenedInFirstAttempt(ap.getCalendarTomorrow());

		for (int i = 0; i < ap.getApptBox().size(); i++) {
			String bookName = ap.getApptBox().get(i).getText();
			if (bookName.contains(resourceName2)) {
				List<WebElement> TimeSlots = ap.getTimeSlotContainers().get(i).findElements(By.tagName("a"));
				WebElement MorningSlot = TimeSlots.get(0);
				wait.until(ExpectedConditions.elementToBeClickable(MorningSlot));
				while (!MorningSlot.isEnabled())// while button is NOT(!) enabled
				{
					System.out.println("Waiting for available times");
				}

				// MorningSlot.click();
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].click();", MorningSlot);

				WebElement MorningAvailableTimeContainer = ap.getTimeSlotContainers().get(i)
						.findElement(By.id("tab-1-0"));
				List<WebElement> MorningAvailableTimes = MorningAvailableTimeContainer
						.findElements(By.tagName("button"));
				WebElement firstAvailableTimeMorning = MorningAvailableTimes.get(0);
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
			ap.getPaymentButton().click();
		} else {
			ap.getPopup1BookButton().click();
		}

		wait.until(ExpectedConditions.stalenessOf(ap.getPopup2OKButton()));
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
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}
		this.catchErrorMessage();
		rw.waitForDashboardLoaded();
		return startTime;
	}

	public String BookGrpApptWith2Resources(String clubName, String productCategory, String appointmentToBook,
			String resourceName1, String resourceName2) throws IOException, InterruptedException {
		// DashboardPO p = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		String startTime = null;
		// p.getMyApptsScheduleButton().click();
		Thread.sleep(2000);

		this.catchErrorMessage();

		AppointmentsPO ap = new AppointmentsPO(driver);

		Select se = new Select(ap.getclubs());
		List<WebElement> Clubs = se.getOptions();

		while (!ap.getclubs().isEnabled()) {
			System.out.println("Waiting for Clubs drop down to not be blank");
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

		Select s1 = new Select(ap.getBookableItem());
		Thread.sleep(2000);
		List<WebElement> Products = s1.getOptions();

		int count1 = Products.size();
		System.out.println(count1);

		for (int j = 0; j < count1; j++) {
			String product = Products.get(j).getText();

			if (product.equals(appointmentToBook)) {
				s1.selectByVisibleText(product);
				break;
			}
		}

		Thread.sleep(1000);
		Assert.assertEquals(ap.getGroupApptsHeader().getText(), "Group Appointments");
		Assert.assertEquals(ap.getGroupMinPersons().getText(), "1");
		Assert.assertEquals(ap.getGroupMaxPersons().getText(), "2");
		ap.getGroupMemberSearchInput().sendKeys("auto");
		ap.getGroupMemberSearchButton().click();

		Thread.sleep(2000);

		int memberCount = ap.getGroupPopupAddButtons().size();
		for (int i = 0; i < memberCount; i++)

		{
			String text = ap.getGroupPopupMembers().get(i).getText();
			System.out.println(text);
			if (ap.getGroupPopupMembers().get(i).getText().contains("Daisy")) {
				wait.until(ExpectedConditions.elementToBeClickable(ap.getGroupPopupAddButtons().get(i)));
				ap.getGroupPopupAddButtons().get(i).click();
				break;
			}
		}

		WebElement rt = ap.getResourceType();

		Select s2 = new Select(rt);
		Thread.sleep(2000);
		List<WebElement> Resources = s2.getOptions();

		int count2 = Resources.size();
		System.out.println(count2);

		for (int k = 0; k < count2; k++) {
			String resource = Resources.get(k).getText();

			if (resource.equals(resourceName1)) {
				s2.selectByVisibleText(resource);
				break;
			}
		}
		while (ap.getloadingAvailabilityMessage().size() != 0) {
			System.out.println("waiting1");
			Thread.sleep(1000);
		}

		System.out.println("came out of the loop");
		Thread.sleep(2000);

		String classtext = ap.getCalendarTomorrow().getAttribute("class");

		if (classtext.contains("cal-out-month")) {
			driver.findElement(By.xpath("//i[contains(@class, 'right')]")).click();

			while (ap.getloadingAvailabilityMessage().size() != 0) {
				System.out.println("waiting");
				Thread.sleep(1000);
			}
			System.out.println("came out of the loop");
		}

		Actions a = new Actions(driver);
		a.click(ap.getCalendarTomorrow()).build().perform();
		System.out.println("Calendar date clicked for " + this.getClass().getSimpleName());
		log.info("Calendar Date Clicked for " + this.getClass().getSimpleName());
		// ap.getCalendarTomorrow().click();
		Thread.sleep(3000);

		rw.waitForSelectATimeToOpen();

		OpenSelectATimeDrawerIfNotOpenedInFirstAttempt(ap.getCalendarTomorrow());

		for (int i = 0; i < ap.getApptBox().size(); i++) {
			String bookName = ap.getApptBox().get(i).getText();
			if (bookName.contains(resourceName2)) {
				List<WebElement> TimeSlots = ap.getTimeSlotContainers().get(i).findElements(By.tagName("a"));
				WebElement MorningSlot = TimeSlots.get(0);
				wait.until(ExpectedConditions.elementToBeClickable(MorningSlot));
				while (!MorningSlot.isEnabled())// while button is NOT(!) enabled
				{
					System.out.println("Waiting for available times");
				}

				// MorningSlot.click();
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].click();", MorningSlot);

				WebElement MorningAvailableTimeContainer = ap.getTimeSlotContainers().get(i)
						.findElement(By.id("tab-1-0"));
				List<WebElement> MorningAvailableTimes = MorningAvailableTimeContainer
						.findElements(By.tagName("button"));
				WebElement firstAvailableTimeMorning = MorningAvailableTimes.get(0);
//				while (!st2.isEnabled())//while button is NOT(!) enabled
//				{
//				Thread.sleep(200);
//				}

				wait.until(ExpectedConditions.elementToBeClickable(firstAvailableTimeMorning));
				startTime = firstAvailableTimeMorning.getText();
				System.out.println(startTime);
				firstAvailableTimeMorning.click();
				break;
			}
		}
		Thread.sleep(2000);
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
			ap.getPaymentButton().click();
			System.out.println("clicked");
		} else {
			ap.getPopup1BookButton().click();
		}

		wait.until(ExpectedConditions.stalenessOf(ap.getPopup2OKButton()));
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
				driver.findElements(By.tagName("a")).get(i).click();
				break;
			}

		}

		this.catchErrorMessage();
		rw.waitForDashboardLoaded();
		return startTime;
	}

	public String openSideMenuIfNotOpenedAlready() {

		DashboardPO d = new DashboardPO(driver);

		String leftMenuOpen = d.getLeftMenu().getAttribute("ng-reflect-opened");
		while (leftMenuOpen.equals("false")) {
			d.getMenuButton().click();
			leftMenuOpen = d.getLeftMenu().getAttribute("ng-reflect-opened");
		}
		return null;

	}

	public String OpenSelectATimeDrawerIfNotOpenedInFirstAttempt(WebElement Element) {

		AppointmentsPO ap = new AppointmentsPO(driver);

		String selectATimeOpen = ap.getSelectATimeDrawer().getAttribute("ng-reflect-opened");

		while (selectATimeOpen.equals("false")) {
			Element.click();
			log.error("calendar date was clicked again");
			System.out.println("calendar date was clicked again");
			selectATimeOpen = ap.getSelectATimeDrawer().getAttribute("ng-reflect-opened");
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
				Assert.assertTrue(element.get(i).getAttribute("class").contains("active"));
				element.get(i).click();
				break;
			}
		}
		Thread.sleep(1000);
		return null;
	}

	public Object verifyFirstDateOfPreviousMonthIsSelectedByDefault(List<WebElement> element)
			throws InterruptedException {

		SimpleDateFormat df1 = new SimpleDateFormat("d");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, 1);
		Date firstDateOfPreviousMonth = cal.getTime();
		String date = df1.format(firstDateOfPreviousMonth);

		Assert.assertTrue(element.get(0).getAttribute("class").contains("selected"));

		element.get(0).click();

		int dayCount = element.size();

		for (int i = 0; i < dayCount; i++) {

			if (element.get(i).getAttribute("class").contains("selected")) {

				Assert.assertTrue(i == 0);
				element.get(i).click();
				break;
			}
		}

		return null;
	}
}