package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClassSignUpPO {

	public static WebDriver driver;

// OBJECTS
		
//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By selectDateThisWeekButton = By.xpath("//label[@id='date-week']");
	By calendarIcon = By.cssSelector(".mat-datepicker-toggle");
	By selectCategory = By.xpath("//classcourse[1]/mat-sidenav-container[1]/mat-sidenav-content[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[3]/div[1]/select[1]");
	By selectCoursesButton = By.xpath("//input[@id='rbcourses']");
	By firstAvailClassButton = By.xpath("//div[@id='classes']/div/div[2]/div[1]/div/div");
	By firstAvailClassNextDayButton = By.xpath("//div[@id='classes']/div[2]/div[2]/div[1]/div/div/div");
	By firstAvailCourseButton = By.xpath("//div[@id='courses']/div/div[2]/div[1]/div/div");
	By golfClasslabel = By.xpath("//span[contains(text(),'Golf Swing Class')]");
	By popupClassDesc = By.xpath("//classcoursedetail[@class='ng-star-inserted']/div/div[2]/div");
	By popupCancelButton = By.xpath("(//button[@type='button'])[5]");
	By popupSignUpButton = By.xpath("(//button[@type='button'])[6]");
//	By selectRatesAddSelButton = By.xpath("//button[@type='button']");
	By continueButton =  By.xpath("//a[contains(text(), 'Continue')]");
	By popupErrorMessage = By.xpath("//h2[@id='swal2-title']");
	By popupClose = By.xpath("//div[@class='swal2-actions']/button[1]");
//	By confirmationCheckout = By.xpath("(//a[@href='#/ShoppingCart'])[3]");
	By confirmationCheckout = By.xpath("//a[contains(text(), 'checkout')]");
	By schedulingConflictMessage = By.xpath("//div[contains(@class,'alert-danger')]/small/i");
	By classListHeader = By.xpath("//span[@class = 'class-list-header']");
	
// CONSTRUCTOR
		
	public ClassSignUpPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		ClassSignUpPO.driver = driver;
	}
// METHODS

	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
	}
	public WebElement getSelectDateThisWeekButton()
	{
		return driver.findElement(selectDateThisWeekButton);
	}
	public WebElement getCalendarIcon()
	{
		return driver.findElement(calendarIcon);
	}
	public WebElement getSelectCategory()
	{
		return driver.findElement(selectCategory);
	}
	public WebElement getSelectCoursesButton()
	{
		return driver.findElement(selectCoursesButton);
	}
	
	public WebElement getFirstAvailClassButton()
	{
		return driver.findElement(firstAvailClassButton);
	}
	public WebElement getfirstAvailClassNextDayButton()
	{
		return driver.findElement(firstAvailClassNextDayButton);
	}
	public WebElement getFirstAvailCourseButton()
	{
		return driver.findElement(firstAvailCourseButton);
	}
	public WebElement getGolfClasslabel()
	{
		return driver.findElement(golfClasslabel);
	}
	public WebElement getPopupClassDesc()
	{
		return driver.findElement(popupClassDesc);
	}
	public WebElement getPopupCancelButton()
	{
		return driver.findElement(popupCancelButton);
	}
	public WebElement getPopupSignUpButton()
	{
		return driver.findElement(popupSignUpButton);
	}
	public WebElement getContinueButton()
	{
		return driver.findElement(continueButton);
	}
	public WebElement getConfirmationCheckout()
	{
		return driver.findElement(confirmationCheckout);
	}
	public WebElement getSchedulingConflictMessage()
	{
		return driver.findElement(schedulingConflictMessage);
	}
	public WebElement getPopupErrorMessage()
	{
		return driver.findElement(popupErrorMessage);
	}
	public WebElement getPopupClose()
	{
		return driver.findElement(popupClose);
	}

	
}
