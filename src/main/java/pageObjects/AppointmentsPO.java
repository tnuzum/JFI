package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AppointmentsPO {

	public WebDriver driver;

// OBJECTS
		
	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By clubs = By.xpath("(//select[@name='clubs'])[1]");
	By bookableItemCategory = By.xpath("(//select[@name='bookableItemCategory'])");
	By bookableItem = By.xpath("(//select[@name='bookableItem'])");
	By resourceType= By.xpath("(//select[@name='primaryResourceType'])");
	By monthSelectBackButton = By.xpath("//div[@class='btn-group']/div[1]/i");
	By monthSelectForwardButton = By.xpath("//div[@class='btn-group']/div[3]/i");
	By calendarMonday1stFullWeek = By.xpath("//div[@class='cal-month-view']/div/div[2]/div/mwl-calendar-month-cell[2]");
	By selectTimeMorningButton = By.xpath("//div[@class='tabs-container']/ul/li[1]");
	By selectTimeMorningLabel1 = By.xpath("//div[@class='tabs-container']/ul/li[1]/a/small[1]");
	By selectTimeMorningLabel2 = By.xpath("//div[@class='tabs-container']/ul/li[1]/a/small[1]");
	By selectTime1stAvailable = By.xpath("(//div[@class='tag-wrap'])[1]/button[1]");
	By popup1BookButton = By.xpath("(//button[@type='button'])[4]");
	By popup2Title = By.xpath("//h2[@class='swal2-title']");
	By popup2OKButton = By.xpath("(//button[@type='button'])[4]");
	
// CONSTRUCTOR
		
	public AppointmentsPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
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
	
}
