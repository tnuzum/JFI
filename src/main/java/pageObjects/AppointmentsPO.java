package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AppointmentsPO {

	public static WebDriver driver;

// OBJECTS
		
//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By loadingAvailabilityMessage = By.xpath("//div[@class='swal2-container.swal2-center']");
	By clubs = By.xpath("(//select[@name='clubs'])[1]");
	By bookableItemCategory = By.xpath("(//select[@name='bookableItemCategory'])");
	By bookableItem = By.xpath("(//select[@name='bookableItem'])");
	By groupApptsHeader = By.xpath("//appointmentresourceselection/div/div[4]/div[2]/div/div/h2");
	By groupMinPersons = By.xpath("//appointmentresourceselection/div/div[4]/div[2]/div/div[3]/div[1]/div/small[1]"); //minimum persons count
	By groupMaxPersons = By.xpath("//appointmentresourceselection/div/div[4]/div[2]/div/div[3]/div[2]/div/small[1]"); //maximum persons count
	By groupMemberSearchInput = By.xpath("//appointmentresourceselection/div/div[4]/div[2]/div/div[4]/div/input");
	By groupMemberSearchButton = By.xpath("//appointmentresourceselection/div/div[4]/div[2]/div/div[4]/div/span/a");
	By groupPopupAddButton1 = By.xpath("//appointmentmembersearch/div[2]/div[1]/a");// first add button
	By groupPopupAddButton2 = By.xpath("//appointmentmembersearch/div[2]/div[2]/a");// second add button
	
	By resourceType= By.xpath("(//select[@name='primaryResourceType'])");
	By monthSelectBackButton = By.xpath("//div[@class='btn-group']/div[1]/i");
	By monthSelectForwardButton = By.xpath("//div[@class='btn-group']/div[3]/i");
	By calendarTomorrow = By.xpath("(//mwl-calendar-month-cell[contains(@class,'future')])[1]");
	By calendarMonday1stFullWeek = By.xpath("//div[@class='cal-month-view']/div/div[2]/div/mwl-calendar-month-cell[2]");
	//By selectTimeMorningButton = By.xpath("//strong[contains(text(),'MORNING')]");
	By selectTimeMorningButton = By.xpath("//div[@class = 'tabs-container']/div/ul/li[1]/a[1]");
	By selectTimeMorningLabel1 = By.xpath("//div[@class='tabs-container']/ul/li[1]/a/small[1]");
	By selectTimeMorningLabel2 = By.xpath("//div[@class='tabs-container']/ul/li[1]/a/small[1]");
	By selectTime1stAvailable = By.xpath("//div[contains(@class,'tag-wrap')]/button[1]");
	By addlResourcesCancelButton = By.xpath("//appointmentsecondaryactioncomponent/div[1]/div[2]/div[1]/button[1]"); //cancel button
	By addlResourcesBookButton = By.xpath("//appointmentsecondaryactioncomponent/div[1]/div[2]/div[1]/button[2]"); // book button
	By packageRequiredContinueButton = By.xpath("//*[text()='CONTINUE']"); // Continue button
	
	//By popup1BookButton = By.xpath("(//button[@type='button'][4]");
	By popup1BookButton = By.xpath("//button[@class='swal2-confirm swal2-styled']");
	By popup2Title = By.xpath("//h2[@id='swal2-title']");
	//By popup2OKButton = By.xpath("(//button[@type='button'])[4]");
	By popup2OKButton = By.xpath("//button[@class='swal2-confirm swal2-styled']");
	
	By editApptPageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By editApptChangeButton = By.cssSelector("#button-change");
	By editApptCancelButton = By.xpath("//*[text()='cancel']"); //By.cssSelector("#button-cancel"); stopped working in 7.28
	By editApptProceedButton = By.xpath("//*[text()='proceed with cancel']"); //By.xpath("//div[@id='show-hide-cancel']/div/div/a"); stopped working in 7.28
	By editApptCancelYesButton = By.cssSelector("button[class*='confirm']");
	By editApptCancelNoButton = By.cssSelector("button[class*='cancel']");
	By editApptCanceledMessage = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/h2[1]");
	By editApptCanceledOKButton = By.cssSelector("button[class*='confirm']");

	// CONSTRUCTOR
		
	public AppointmentsPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		AppointmentsPO.driver = driver;
	}
// METHODS

	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
	}
	public WebElement getloadingAvailabilityMessage()
	{
		return driver.findElement(loadingAvailabilityMessage);
	}
	public WebElement getclubs()
	{
		return driver.findElement(clubs);
	}
	public WebElement getBookableItemCategory()
	{
		return driver.findElement(bookableItemCategory);
	}
	public WebElement getBookableItem()
	{
		return driver.findElement(bookableItem);
	}
	public WebElement getGroupApptsHeader()
	{
		return driver.findElement(groupApptsHeader);
	}
	public WebElement getGroupMinPersons()
	{
		return driver.findElement(groupMinPersons);
	}
	public WebElement getGroupMaxPersons()
	{
		return driver.findElement(groupMaxPersons);
	}
	public WebElement getGroupMemberSearchInput()
	{
		return driver.findElement(groupMemberSearchInput);
	}
	public WebElement getGroupMemberSearchButton()
	{
		return driver.findElement(groupMemberSearchButton);
	}
	
	public WebElement getGroupPopupAddButton1()
	{
		return driver.findElement(groupPopupAddButton1);
	}
	public WebElement getGroupPopupAddButton2()
	{
		return driver.findElement(groupPopupAddButton2);
	}
	
	public WebElement getResourceType()
	{
		return driver.findElement(resourceType);
	}
	public WebElement getMonthSelectBackButton()
	{
		return driver.findElement(monthSelectBackButton);
	}
	public WebElement getMonthSelectForwardButton()
	{
		return driver.findElement(monthSelectForwardButton);
	}
	public WebElement getCalendarTomorrow()
	{
		return driver.findElement(calendarTomorrow);
	}
	
	public WebElement getCalendarMonday1stFullWeek()
	{
		return driver.findElement(calendarMonday1stFullWeek);
	}
	public WebElement getSelectTimeMorningButton()
	{
		return driver.findElement(selectTimeMorningButton);
	}
	public WebElement getSelectTimeMorningLabel1()
	{
		return driver.findElement(selectTimeMorningLabel1);
	}
	public WebElement getSelectTimeMorningLabel2()
	{
		return driver.findElement(selectTimeMorningLabel2);
	}
	public WebElement getSelectTime1stAvailable()
	{
		return driver.findElement(selectTime1stAvailable);
	}
	public WebElement getAddlResourcesCancelButton()
	{
		return driver.findElement(addlResourcesCancelButton);
	}
	public WebElement getAddlResourcesBookButton()
	{
		return driver.findElement(addlResourcesBookButton);
	}
	public WebElement getPackageRequiredContinueButton()
	{
		return driver.findElement(packageRequiredContinueButton);
	}
	
	public WebElement getPopup1BookButton()
	{
		return driver.findElement(popup1BookButton);
	}
	public WebElement getPopup2Title()
	{
		return driver.findElement(popup2Title);
	}
	public WebElement getPopup2OKButton()
	{
		return driver.findElement(popup2OKButton);
	}
	public WebElement getEditApptPageHeader()
	{
		return driver.findElement(editApptPageHeader);
	}
	public WebElement getEditApptChangeButton()
	{
		return driver.findElement(editApptChangeButton);
	}
	public WebElement getEditApptCancelButton()
	{
		return driver.findElement(editApptCancelButton);
	}
	public WebElement getEditApptProceedButton()
	{
		return driver.findElement(editApptProceedButton);
	}
	public WebElement getEditApptCancelYesButton()
	{
		return driver.findElement(editApptCancelYesButton);
	}
	public WebElement getEditApptCancelNoButton()
	{
		return driver.findElement(editApptCancelNoButton);
	}
	public WebElement getEditApptCanceledMessage()
	{
		return driver.findElement(editApptCanceledMessage);
	}
	public WebElement getEditApptCanceledOKButton()
	{
		return driver.findElement(editApptCanceledOKButton);
	}
	
}
