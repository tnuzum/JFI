package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DashboardPO {

	WebDriver driver;

// OBJECTS
//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By menuButton = By.xpath("//nav[@class='navbar navbar-static-top']/div/a/i");
	By myPackagesButton = By.xpath("//button[@class='at-topnavbar-mypackages']");
	By myPackagesShopPackages = By.xpath("//a[contains(@class, 'at-topnavbar-shoppackages-button')]");
	// By.linkText("Shop Packages");
	By cartButton = By.xpath("//a[contains(text(),'Cart')]");
	// "//nav[@class='navbar navbar-static-top']/ul/li[2]/a");
	By logoutButton = By.xpath("//button[contains(@class,'at-topnavbar-logout')]");
	// By.linkText("Log out");

	By leftMenu = By.xpath("//mat-sidenav[contains(@class, 'mat-sidenav')]");
	By menuDashboardButton = By.xpath("//a[contains(@class, 'at-mainnav-dashboard')]");
	// By.xpath("//mat-nav-list[@class='mat-nav-list']/a[1]/div");
	By menuMyActivies = By.xpath("//mat-nav-list[@class='mat-nav-list']/mat-list-item[1]/div/span");
	By menuMyActivitiesSubMenu = By.xpath("//mat-nav-list[@class='mat-nav-list']/div[1]");
	// By menuMyAccount =
	// By.xpath("//mat-nav-list[@class='mat-nav-list']/mat-list-item[2]/div/span");

	By menuMyAccount = By.xpath("//span[contains(text(),'My Account')]");

//	By menuMyAccountSubMenu = By.xpath("//mat-nav-list[@class='mat-nav-list']/div[2]");
	By menuMyAccountSubMenu = By.xpath("//div[@class='matnav-submenu ng-trigger ng-trigger-slideInOut'][2]");

	By menuShopPackages = By.xpath("//a[contains(@class, 'at-mainnav-shoppackage')]");
	// By.xpath("//mat-nav-list[@class='mat-nav-list']/a[2]/div[1]/span");
	By menuCart = By.xpath("//a[contains(@class, 'at-mainnav-cart')]");
	By menuLogOut = By.xpath("//a[contains(@class, 'at-mainnav-logout')]");
	// By.xpath("//mat-nav-list[@class='mat-nav-list']/a[4]/div[1]/span");
	By menuClassSchedule = By.xpath("//a[contains(@class, 'at-mainnav-classschedule')]");
	// By.xpath("//mat-nav-list[@class='mat-nav-list']/div[1]/a[1]/div[1]/span");
	By menuCourseEventsSchedule = By.xpath("//a[contains(@class, 'at-mainnav-courseschedule')]");
	// By.xpath("//mat-nav-list[@class='mat-nav-list']/div[1]/a[2]");
	By menuBookAppointment = By.xpath("//a[contains(@class, 'at-mainnav-bookappointment')]");
	// By.xpath("//mat-nav-list[@class='mat-nav-list']/div[1]/a[3]");
	By menuMyCalendar = By.xpath("//a[contains(@class, 'at-mainnav-mycalendar')]");
	// By.xpath("//mat-nav-list[@class='mat-nav-list']/div[1]/a[4]");
	By menuPayBalance = By.xpath("//a[contains(@class, 'at-mainnav-paybalance')]");
	// By.xpath("//mat-nav-list[@class='mat-nav-list']/div[2]/a[1]");
	By menuManagePmntMethods = By.xpath("//a[contains(@class, 'at-mainnav-managepaymentmethods')]");
	// By.xpath("//mat-nav-list[@class='mat-nav-list']/div[2]/a[2]");
	By menuManageProfile = By.xpath("//a[contains(@class, 'at-mainnav-manageprofile')]");
	// By.xpath("//mat-nav-list[@class='mat-nav-list']/div[2]/a[3]");
	By menuManageFamily = By.xpath("//a[contains(@class, 'at-mainnav-managefamily')]");
	// By.xpath("//mat-nav-list[@class='mat-nav-list']/div[2]/a[4]");
	By menuAccountHistory = By.xpath("//a[contains(@class, 'at-mainnav-accounthistory')]");
	// mat-nav-list[@class='mat-nav-list']/div[2]/a[5]");
	By menuPackages = By.xpath("//a[contains(@class, 'at-mainnav-packages')]");
	// By.xpath("//mat-nav-list[@class='mat-nav-list']/div[2]/a[6]");
	By menuCheckInHistory = By.xpath("//a[contains(@class, 'at-mainnav-checkinhistory')]");

	By myAccountSectionLabel = By.xpath("//div[@class='homeComponent']//memberbalance/div/div[1]");
//	By myAccountAccountHistoryButton = By.xpath("//div[@class='homeComponent']//memberbalance/div/div[3]/a[1]");
	By myAccountAccountHistoryButton = By.xpath("//button[contains(@class,'at-widget-accounthistory')]");
//	By myAccountPayNowButton = By.xpath("//div[@class='homeComponent']//memberbalance/div/div[3]/a[2]");
	By myAccountPayNowButton = By.xpath("//button[contains(@class, 'at-widget-paynow')]");
	By myAccountBalance = By.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/h2");
	By myAccountBalDuelabel = By.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/div/small");
	By myAccountTotChargesAmount = By
			.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/small/strong/span");
	By myAccountLastPaymentDate = By.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/small[2]");

	By myInfoSectionLabel = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[1]/h5");
	// By myInfoSectionLabel = By.xpath("//h5[contains(text(),'My Info')]");
	// By myInfoMemberName =
	// By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/h2"); change
	// in Future 2 on 10/15
	// By myInfoMemberName =
	// By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/div/div[2]/h3");
	By myInfoMemberName = By.xpath("//h3[@class='no-margins']");

	By myInfoAddress1 = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/div/div[2]/div/small[1]");
	By myInfoAddress2 = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/div/div[2]/div/div/small[1]");
//	By myInfoEditButton = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[3]/a[1]");
	By myInfoEditButton = By.xpath("//button[contains(@class, 'at-widget-editmyinfo')]");

	By myApptsSectionLabel = By.xpath("//div[@class='homeComponent']//appointmentswidget/div/div[1]");

//	By myApptsScheduleButton = By.xpath("//button[contains(text(),'book appointment')]");
	By myApptsScheduleButton = By.xpath("//button[contains(@class, 'at-widget-bookappointment')]");
	By myApptsAppt1GearButton = By.xpath("//appointmentswidget/div/div[2]/div[1]/div[1]/div/a/div/div[3]/i");
	By myApptsNoApptsMessage = By.xpath("//strong[contains(text(),'You have no scheduled appointments.')]");

	By myApptsAddtoCalendarButton = By.xpath("//appointmentswidget/div/div[2]/div/div/div/div/div/div[1]/button");
	By myApptsEditButton = By.xpath("//appointmentswidget/div/div[2]/div[1]/div[1]/div/div/div/div/div[2]/button");
	By myApptsAppt1Title = By.xpath("//appointmentswidget/div/div[2]/div[1]/div/div/a/div/div[2]/span/strong");
	By myApptsAppt2Title = By.xpath("//appointmentswidget/div/div[2]/div[2]/div/div/a/div/div[2]/span/strong");
	By myApptsAppt3Title = By.xpath("//appointmentswidget/div/div[2]/div[3]/div/div/a/div/div[2]/span/strong");
	By myAppts = By.xpath("//appointmentswidget//div[@class = 'box-clickable']");
	By editButton = By.xpath("//appointmentswidget//div[@class = 'box-clickable']//button[contains(text(), 'edit')]");

	By myClassesSectionLabel = By.xpath("//div[@class='homeComponent']//classeswidget/div/div[1]");
//	By myClassesScheduleButton = By.xpath("//button[contains(text(),'class schedule')]");	
	By myClassesScheduleButton = By.xpath("//button[contains(@class, 'at-widget-classschedule')]");
//	By myClassesClass1GearButton = By.xpath("//classescourses/div[1]/div[2]/div[1]/div[1]/a[1]/div[1]/div[3]/i[1]");
	By myClassesClass1GearButton = By.xpath("//classeswidget//div[@class = 'class-table-container']/div[3]/i[1]");
	By myClassesUnenrollButton = By.xpath("//button[@title='UNENROLL']");// button on dashboard after clicking the gear
																			// button
	By classInfoSections = By.xpath("//classeswidget//div[@class = 'class-table-container']");
	By myClassesClass1GearButtons = By.xpath("//classeswidget//div[@class = 'class-table-container']/div[3]/i");

	By myCoursesEventsSectionLabel = By.xpath("//div[@class='homeComponent']//courseswidget/div/div[1]");
//	By myCoursesEventsScheduleButton = By.xpath("//button[contains(text(),'Courses/Events Schedule')]");
	By myCoursesEventsScheduleButton = By.xpath("//button[contains(@class, 'at-widget-courseschedule')]");

	By myVisitsSectionLabel = By.xpath("//div[@class='homeComponent']//checkinchart/div/div[1]");

	By myFamilySectionLabel = By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]");
	By myFamilyFamilyMemberCount = By.xpath("//div[@class='homeComponent']//familymembercount//div//div//h2");
	By myFamilyFamilyMemberCount2 = By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]");

//	By myFamilyManageButton = By.xpath("//div[@class='homeComponent']//familymembercount/div/div[3]/a[1]");
	By myFamilyManageButton = By.xpath("//a[contains(@class, 'at-widget-managefamily')]");

	By AdditionalLinksSectionLabel = By.xpath("//div[@class='homeComponent']//externallinks/div/div[1]/div[1]");
//	By BreadcrumbDashboard = By.xpath("//a[@class='ng-star-inserted']");
	By BreadcrumbDashboard = By.xpath("//a[contains(text(),'Dashboard')]");

	By privacyPolicyLink = By.linkText("Privacy Policy");
	By privacyAndSecurityLabel = By.xpath("//h4[contains(text(),'Privacy & Security')]");
	By showAllbutton = By.xpath("//button[contains(text(),'Show All')]");
	By hideAllbutton = By.xpath("//button[contains(text(),'Hide All')]");

// CONSTRUCTOR
	public DashboardPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}

// METHODS
	// Top Row Buttons
	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getMenuButton() {
		return driver.findElement(menuButton);
	}

	public WebElement getMyPackagesButton() {
		return driver.findElement(myPackagesButton);
	}

	public WebElement getMyPackagesShopPackages() {
		return driver.findElement(myPackagesShopPackages);
	}

	public WebElement getCartButton() {
		return driver.findElement(cartButton);
	}

	public WebElement getLogoutButton() {
		return driver.findElement(logoutButton);
	}

	// Menu - Left Pane
	public WebElement getMenuMyActivies() {
		return driver.findElement(menuMyActivies);
	}

	public WebElement getmenuMyActivitiesSubMenu() {
		return driver.findElement(menuMyActivitiesSubMenu);
	}

	public WebElement getLeftMenu() {
		return driver.findElement(leftMenu);
	}

	public WebElement getDashboardButton() {
		return driver.findElement(menuDashboardButton);
	}

	public WebElement getMenuMyAccount() {
		return driver.findElement(menuMyAccount);
	}

	public WebElement getmenuMyAccountSubMenu() {
		return driver.findElement(menuMyAccountSubMenu);
	}

	public WebElement getMenuShopPackages() {
		return driver.findElement(menuShopPackages);
	}

	public WebElement getMenuCart() {
		return driver.findElement(menuCart);
	}

	public WebElement getMenuLogOut() {
		return driver.findElement(menuLogOut);
	}

	public WebElement getMenuClassSchedule() {
		return driver.findElement(menuClassSchedule);
	}

	public WebElement getMenuCourseEventSchedule() {
		return driver.findElement(menuCourseEventsSchedule);
	}

	public WebElement getMenuBookAppointment() {
		return driver.findElement(menuBookAppointment);
	}

	public WebElement getMenuMyCalendar() {
		return driver.findElement(menuMyCalendar);
	}

	public WebElement getMenuPayBalance() {
		return driver.findElement(menuPayBalance);
	}

	public WebElement getMenuManagePmntMethods() {
		return driver.findElement(menuManagePmntMethods);
	}

	public WebElement getMenuManageProfile() {
		return driver.findElement(menuManageProfile);
	}

	public WebElement getMenuManageFamily() {
		return driver.findElement(menuManageFamily);
	}

	public WebElement getMenuAccountHistory() {
		return driver.findElement(menuAccountHistory);
	}

	public WebElement getMenuPackages() {
		return driver.findElement(menuPackages);
	}

	public WebElement getMenuCheckInHistory() {
		return driver.findElement(menuCheckInHistory);
	}

	// My Account
	public WebElement getMyAccountSectionLabel() {
		return driver.findElement(myAccountSectionLabel);
	}

	public WebElement getMyAccountAccountHistory() {
		return driver.findElement(myAccountAccountHistoryButton);
	}

	public WebElement getMyAccountPayNow() {
		return driver.findElement(myAccountPayNowButton);
	}

	public WebElement getMyAccountBalance() {
		return driver.findElement(myAccountBalance);
	}

	public WebElement getMyAccountBalDuelabel() {
		return driver.findElement(myAccountBalDuelabel);
	}

	public WebElement getMyAccountTotChargesAmount() {
		return driver.findElement(myAccountTotChargesAmount);
	}

	public WebElement getMyAccountLastPaymentDate() {
		return driver.findElement(myAccountLastPaymentDate);
	}

	// My Info
	public WebElement getMyInfoSectionLabel() {
		return driver.findElement(myInfoSectionLabel);
	}

	public WebElement getMyInfoMemberName() {
		return driver.findElement(myInfoMemberName);
	}

	public WebElement getMyInfoAddress1() {
		return driver.findElement(myInfoAddress1);
	}

	public WebElement getMyInfoAddress2() {
		return driver.findElement(myInfoAddress2);
	}

	public WebElement getMyInfoEditButton() {
		return driver.findElement(myInfoEditButton);
	}

	// My Appointments
	public WebElement getMyApptsSectionLabel() {
		return driver.findElement(myApptsSectionLabel);
	}

	public WebElement getMyApptsScheduleButton() {
		return driver.findElement(myApptsScheduleButton);
	}

	public WebElement getMyApptsNoApptsMessage() {
		return driver.findElement(myApptsNoApptsMessage);
	}

	public WebElement getMyApptsAppt1Title() {
		return driver.findElement(myApptsAppt1Title);
	}

	public WebElement getMyApptsAppt2Title() {
		return driver.findElement(myApptsAppt2Title);
	}

	public WebElement getMyApptsAppt3Title() {
		return driver.findElement(myApptsAppt3Title);
	}

	public WebElement getMyApptsAppt1GearButton() {
		return driver.findElement(myApptsAppt1GearButton);
	}

	public WebElement getMyApptsAddtoCalendarButton() {
		return driver.findElement(myApptsAddtoCalendarButton);
	}

	public WebElement getMyApptsEditButton() {
		return driver.findElement(myApptsEditButton);
	}

	public List<WebElement> getMyAppts() {
		return driver.findElements(myAppts);
	}

	public List<WebElement> getEditButton() {
		return driver.findElements(editButton);
	}

	// My Classes
	public WebElement getMyClassesSectionLabel() {
		return driver.findElement(myClassesSectionLabel);
	}

	public WebElement getMyClassesScheduleButton() {
		return driver.findElement(myClassesScheduleButton);
	}

	public WebElement getMyClassesClass1GearButton() {
		return driver.findElement(myClassesClass1GearButton);
	}

	public WebElement getmyClassesUnenrollButton() {
		return driver.findElement(myClassesUnenrollButton);
	}

	public List<WebElement> getmyClassesUnenrollButtons() {
		return driver.findElements(myClassesUnenrollButton);
	}

	public List<WebElement> getClassInfoSections() {
		return driver.findElements(classInfoSections);
	}

	public List<WebElement> getMyClassesClass1GearButtons() {
		return driver.findElements(myClassesClass1GearButtons);
	}

	// My Courses/Events
	public WebElement getMyCoursesEventsSectionLabel() {
		return driver.findElement(myCoursesEventsSectionLabel);
	}

	public WebElement getMyCoursesEventsScheduleButton() {
		return driver.findElement(myCoursesEventsScheduleButton);
	}

	// My Visits
	public WebElement getMyVisitsSectionLabel() {
		return driver.findElement(myVisitsSectionLabel);
	}

	// My Family
	public WebElement getMyFamilySectionLabel() {
		return driver.findElement(myFamilySectionLabel);
	}

	public WebElement getMyFamilyMemberCount() {
		return driver.findElement(myFamilyFamilyMemberCount);
	}

	public WebElement getMyFamilyMemberCount2() {
		return driver.findElement(myFamilyFamilyMemberCount2);
	}

	public WebElement getMyFamilyManageButton() {
		return driver.findElement(myFamilyManageButton);
	}

	// AdditionalLinks
	public WebElement getAdditionalLinksSectionLabel() {
		return driver.findElement(AdditionalLinksSectionLabel);

	}

	public WebElement getBreadcrumbDashboard() {
		return driver.findElement(BreadcrumbDashboard);
	}

	public WebElement getPrivacyPolicyLink() {
		return driver.findElement(privacyPolicyLink);
	}

	public WebElement getPrivacyAndSecurityLabel() {
		return driver.findElement(privacyAndSecurityLabel);
	}

	public WebElement getshowAllbutton() {
		return driver.findElement(showAllbutton);
	}

	public WebElement gethideAllbutton() {
		return driver.findElement(hideAllbutton);
	}

}
