package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DashboardPO {

	public static WebDriver driver;

// OBJECTS
	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By menuButton = By.xpath("//nav[@class='navbar navbar-static-top']/div/a/i");
	By myPackagesButton = By.xpath("//nav[@class='navbar navbar-static-top']/ul/li/div/button");
	By myPackagesShopPackages = By.linkText("Shop Packages");
	By cartButton = By.xpath("//nav[@class='navbar navbar-static-top']/ul/li[2]/a");
	By logoutButton = By.linkText("Log out"); 
	
	By menuDashboardButton = By.xpath("//mat-nav-list[@class='mat-nav-list']/a[1]/div");
	By menuMyActivies = By.xpath("//mat-nav-list[@class='mat-nav-list']/mat-list-item[1]/div/span");
	By menuMyAccount = By.xpath("//mat-nav-list[@class='mat-nav-list']/mat-list-item[2]/div/span");
	By menuShopPackages = By.xpath("//mat-nav-list[@class='mat-nav-list']/a[2]/div");
	By menuCart = By.xpath("//mat-nav-list[@class='mat-nav-list']/a[3]/div");
	By menuLogOut = By.xpath("//mat-nav-list[@class='mat-nav-list']/a[4]/div");
	By menuClassSignup = By.xpath("(//mat-nav-list[@class='mat-nav-list'])/div[1]/a[1]");
	By menuBookAppointment = By.xpath("(//mat-nav-list[@class='mat-nav-list'])/div[1]/a[2]");
	By menuMyCalendar = By.xpath("(//mat-nav-list[@class='mat-nav-list'])/div[1]/a[3]");
	By menuPayBalance = By.xpath("(//mat-nav-list[@class='mat-nav-list'])/div[2]/a[1]");
	By menuManagePmntMethods = By.xpath("(//mat-nav-list[@class='mat-nav-list'])/div[2]/a[2]");
	By menuManageProfile = By.xpath("(//mat-nav-list[@class='mat-nav-list'])/div[2]/a[3]");
	By menuManageFamily = By.xpath("(//mat-nav-list[@class='mat-nav-list'])/div[2]/a[4]");
	By menuAccountHistory = By.xpath("(//mat-nav-list[@class='mat-nav-list'])/div[2]/a[5]");
	By menuPackages = By.xpath("(//mat-nav-list[@class='mat-nav-list'])/div[2]/a[6]");
	By menuCheckInHistory = By.xpath("(//mat-nav-list[@class='mat-nav-list'])/div[2]/a[7]");
	
	By myAccountSectionLabel = By.xpath("//div[@class='homeComponent']//memberbalance/div/div[1]");
	By myAccountAccountHistoryButton = By.xpath("//div[@class='homeComponent']//memberbalance/div/div[3]/a[1]");
	By myAccountPayNowButton = By.xpath("//div[@class='homeComponent']//memberbalance/div/div[3]/a[2]");
	By myAccountBalance = By.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/h2");
	By myAccountBalDuelabel = By.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/div/small");
	By myAccountTotChargesAmount = By.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/small/strong/span");
	By myAccountLastPaymentDate = By.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/small[2]");
	
	By myInfoSectionLabel = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[1]/h5");
	//By myInfoSectionLabel = By.xpath("//h5[contains(text(),'My Info')]");
	//By myInfoMemberName = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/h2"); change in Future 2 on 10/15
	//By myInfoMemberName = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/div/div[2]/h3");
	By myInfoMemberName = By.xpath("//h3[@class='no-margins']");
	
	By myInfoAddress1 = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/div/div[2]/div/small[1]");
	By myInfoAddress2 = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/div/div[2]/div/div/small[1]");
	By myInfoEditButton = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[3]/a[1]");

	By myApptsSectionLabel = By.xpath("//div[@class='homeComponent']//appointmentswidget/div/div[1]");
	
	By myApptsScheduleButton = By.xpath("//button[contains(text(),'book appointment')]");
	By myApptsAppt1GearButton = By.xpath("//appointmentswidget/div/div[2]/div[1]/div[1]/div/a/div/div[3]/i");
	By myApptsNoApptsMessage = By.xpath("//strong[contains(text(),'You have no scheduled appointments.')]");
	
	By myApptsAddtoCalendarButton = By.xpath("//appointmentswidget/div/div[2]/div/div/div/div/div/div[1]/button");
	By myApptsEditButton = By.xpath("//appointmentswidget/div/div[2]/div[1]/div[1]/div/div/div/div/div[2]/button");
	By myApptsAppt1Title = By.xpath("//appointmentswidget/div/div[2]/div[1]/div/div/a/div/div[2]/span/strong");
	By myApptsAppt2Title = By.xpath("//appointmentswidget/div/div[2]/div[2]/div/div/a/div/div[2]/span/strong");
	By myApptsAppt3Title = By.xpath("//appointmentswidget/div/div[2]/div[3]/div/div/a/div/div[2]/span/strong");
	
	By myClassesSectionLabel = By.xpath("//div[@class='homeComponent']//classeswidget/div/div[1]");
	By myClassesScheduleButton = By.xpath("//button[contains(text(),'class schedule')]");	
	By myClassesClass1GearButton = By.xpath("//classescourses/div[1]/div[2]/div[1]/div[1]/a[1]/div[1]/div[3]/i[1]");
	By myClassesUnenrollButton = By.xpath("//button[@title='UNENROLL']");//button on dashboard after clicking the gear button
	
	By myCoursesEventsSectionLabel = By.xpath("//div[@class='homeComponent']//courseswidget/div/div[1]");
	By myCoursesEventsScheduleButton = By.xpath("//button[contains(text(),'Courses/Events Schedule')]");
	
	By myVisitsSectionLabel = By.xpath("//div[@class='homeComponent']//checkinchart/div/div[1]");
	
	By myFamilySectionLabel = By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]");
	By myFamilyFamilyMemberCount = By.xpath("//div[@class='homeComponent']//familymembercount//div//div//h2");
	By myFamilyFamilyMemberCount2 = By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]");
	
	By myFamilyManageButton = By.xpath("//div[@class='homeComponent']//familymembercount/div/div[3]/a[1]");
	
	By AdditionalLinksSectionLabel = By.xpath("//div[@class='homeComponent']//externallinks/div/div[1]/div[1]");
	
// CONSTRUCTOR
	public DashboardPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		DashboardPO.driver = driver;
	}

// METHODS
	//Top Row Buttons
	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
	}
	public WebElement getMenuButton()
	{
		return driver.findElement(menuButton);
	}
	public WebElement getMyPackagesButton()
	{
		return driver.findElement(myPackagesButton);
	}
	public WebElement getMyPackagesShopPackages()
	{
		return driver.findElement(myPackagesShopPackages);
	}
	public WebElement getCartButton()
	{
		return driver.findElement(cartButton);
	}
	public WebElement getLogoutButton()
	{
		return driver.findElement(logoutButton);
	}
	
	//Menu - Left Pane
	public WebElement getMenuMyActivies()
	{
		return driver.findElement(menuMyActivies);
	}
	public WebElement getDashboardButton()
	{
		return driver.findElement(menuDashboardButton);
	}
	public WebElement getMenuMyAccount()
	{
		return driver.findElement(menuMyAccount);
	}
	public WebElement getMenuShopPackages()
	{
		return driver.findElement(menuShopPackages);
	}
	public WebElement getMenuCart()
	{
		return driver.findElement(menuCart);
	}
	public WebElement getMenuLogOut()
	{
		return driver.findElement(menuLogOut);
	}
	public WebElement getMenuClassSignup()
	{
		return driver.findElement(menuClassSignup);
	}
	public WebElement getMenuBookAppointment()
	{
		return driver.findElement(menuBookAppointment);
	}
	public WebElement getMenuMyCalendar()
	{
		return driver.findElement(menuMyCalendar);
	}
	public WebElement getMenuPayBalance()
	{
		return driver.findElement(menuPayBalance);
	}
	public WebElement getMenuManagePmntMethods()
	{
		return driver.findElement(menuManagePmntMethods);
	}
	public WebElement getMenuManageProfile()
	{
		return driver.findElement(menuManageProfile);
	}
	public WebElement getMenuManageFamily()
	{
		return driver.findElement(menuManageFamily);
	}
	public WebElement getMenuAccountHistory()
	{
		return driver.findElement(menuAccountHistory);
	}
	public WebElement getMenuPackages()
	{
		return driver.findElement(menuPackages);
	}
	public WebElement getMenuCheckInHistory()
	{
		return driver.findElement(menuCheckInHistory);
	}
	
	//My Account
	public WebElement getMyAccountSectionLabel()
	{
		return driver.findElement(myAccountSectionLabel);
	}
	public WebElement getMyAccountAccountHistory()
	{
		return driver.findElement(myAccountAccountHistoryButton);
	}
	public WebElement getMyAccountPayNow()
	{
		return driver.findElement(myAccountPayNowButton);
	}
	public WebElement getMyAccountBalance()
	{
		return driver.findElement(myAccountBalance);
	}
	public WebElement getMyAccountBalDuelabel()
	{
		return driver.findElement(myAccountBalDuelabel);
	}
	public WebElement getMyAccountTotChargesAmount()
	{
		return driver.findElement(myAccountTotChargesAmount);
	}
	public WebElement getMyAccountLastPaymentDate()
	{
		return driver.findElement(myAccountLastPaymentDate);
	}
	
	//My Info
	public WebElement getMyInfoSectionLabel()
	{
		return driver.findElement(myInfoSectionLabel);
	}
	public WebElement getMyInfoMemberName()
	{
		return driver.findElement(myInfoMemberName);
	}
	public WebElement getMyInfoAddress1()
	{
		return driver.findElement(myInfoAddress1);
	}
	public WebElement getMyInfoAddress2()
	{
		return driver.findElement(myInfoAddress2);
	}
	public WebElement getMyInfoEditButton()
	{
		return driver.findElement(myInfoEditButton);
	}
	
	//My Appointments
	public WebElement getMyApptsSectionLabel()
	{
		return driver.findElement(myApptsSectionLabel);
	}
	public WebElement getMyApptsScheduleButton()
	{
		return driver.findElement(myApptsScheduleButton);
	}
	public WebElement getMyApptsNoApptsMessage()
	{
		return driver.findElement(myApptsNoApptsMessage);
	}
	public WebElement getMyApptsAppt1Title()
	{
		return driver.findElement(myApptsAppt1Title);
	}
	public WebElement getMyApptsAppt2Title()
	{
		return driver.findElement(myApptsAppt2Title);
	}
	public WebElement getMyApptsAppt3Title()
	{
		return driver.findElement(myApptsAppt3Title);
	}
	public WebElement getMyApptsAppt1GearButton()
	{
		return driver.findElement(myApptsAppt1GearButton);
	}
	public WebElement getMyApptsAddtoCalendarButton()
	{
		return driver.findElement(myApptsAddtoCalendarButton);
	}
	public WebElement getMyApptsEditButton()
	{
		return driver.findElement(myApptsEditButton);
	}
	
	//My Classes
	public WebElement getMyClassesSectionLabel()
	{
		return driver.findElement(myClassesSectionLabel);
	}
	public WebElement getMyClassesScheduleButton()
	{
		return driver.findElement(myClassesScheduleButton);
	}
	public WebElement getMyClassesClass1GearButton()
	{
		return driver.findElement(myClassesClass1GearButton);
	}
	public WebElement getmyClassesUnenrollButton()
	{
		return driver.findElement(myClassesUnenrollButton);
	}

	//My Courses/Events
		public WebElement getMyCoursesEventsSectionLabel()
		{
			return driver.findElement(myCoursesEventsSectionLabel);
		}
		public WebElement getMyCoursesEventsScheduleButton()
		{
			return driver.findElement(myCoursesEventsScheduleButton);
		}
	
	//My Visits
	public WebElement getMyVisitsSectionLabel()
	{
		return driver.findElement(myVisitsSectionLabel);
	}
	
	//My Family
	public WebElement getMyFamilySectionLabel()
	{
		return driver.findElement(myFamilySectionLabel);
	}
	public WebElement getMyFamilyMemberCount()
	{
		return driver.findElement(myFamilyFamilyMemberCount);
	}
	public WebElement getMyFamilyMemberCount2()
	{
		return driver.findElement(myFamilyFamilyMemberCount2);
	}
	public WebElement getMyFamilyManageButton()
	{
		return driver.findElement(myFamilyManageButton);
	}

	//AdditionalLinks
	public WebElement getAdditionalLinksSectionLabel()
	{
		return driver.findElement(AdditionalLinksSectionLabel);
	}
	
}
