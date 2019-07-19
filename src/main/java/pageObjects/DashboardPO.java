package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DashboardPO {

	public WebDriver driver;

// OBJECTS
	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By menuButton = By.xpath("//nav[@class='navbar navbar-static-top']/div/a/i");
	By myPackagesButton = By.xpath("//nav[@class='navbar navbar-static-top']/ul/li/div/button");
	By myPackagesShopPackages = By.xpath("//nav[@class='navbar navbar-static-top']/ul/li/div/ul/li[5]/div/div/div");
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
	
	By myInfoSectionLabel = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[1]");
	By myInfoMemberName = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/h2");
	By myInfoAddress1 = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/div/small[1]");
	By myInfoAddress2 = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/div/small[2]");
	By myInfoEditButton = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[3]/a[1]");

	By myApptsSectionLabel = By.xpath("//div[@class='homeComponent']//appointmentswidget/div/div[1]");
	By myApptsScheduleButton = By.xpath("//div[@class='homeComponent']//appointmentswidget/div/div[3]/a[1]");
	
	By myClassesSectionLabel = By.xpath("//div[@class='homeComponent']//classescourses/div/div[1]");
	By myClassesScheduleButton = By.xpath("//div[@class='homeComponent']//classescourses/div/div[3]/a[1]");
	
	By myVisitsSectionLabel = By.xpath("//div[@class='homeComponent']//checkinchart/div/div[1]");
	
	By myFamilySectionLabel = By.xpath("//div[@class='homeComponent']//familymembercount/div/div[1]");
	By myFamilyFamilyMemberCount = By.xpath("//div[@class='homeComponent']//familymembercount");
	By myFamilyManageButton = By.xpath("//div[@class='homeComponent']//familymembercount/div/div[3]/a[1]");
	
	By AdditionalLinksSectionLabel = By.xpath("//div[@class='homeComponent']//externallinks/div/div[1]/div[1]");
	
// CONSTRUCTOR
	public DashboardPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
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
	
	//My Info
	public WebElement getMyInfoSectionLabel()
	{
		return driver.findElement(myInfoMemberName);
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
	
	//My Classes
	public WebElement getMyClassesSectionLabel()
	{
		return driver.findElement(myClassesSectionLabel);
	}
	public WebElement getMyClassesScheduleButton()
	{
		return driver.findElement(myClassesScheduleButton);
	}
	
	//My Visits
	public WebElement getMyVisitsSectionLabel()
	{
		return driver.findElement(myVisitsSectionLabel);
	}
	
	//My Family
	public WebElement myFamilySectionLabel()
	{
		return driver.findElement(myFamilySectionLabel);
	}
	public WebElement getFamilyMemberCount()
	{
		return driver.findElement(myFamilyFamilyMemberCount);
	}
	public WebElement getmyFamilyManageButton()
	{
		return driver.findElement(myFamilyManageButton);
	}

	//AdditionalLinks
	public WebElement getAdditionalLinksSectionLabel()
	{
		return driver.findElement(AdditionalLinksSectionLabel);
	}
	
}
