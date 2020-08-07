package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CalendarPO {

	WebDriver driver;

// OBJECTS

//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By monthYear = By.xpath("//div[@class = 'btn-group']//div[contains(@class, 'btn-white')][2]");
	By leftArrow = By.xpath("//div[@class = 'btn-group']//div[contains(@class, 'btn-white')][1]");
	By rightArrow = By.xpath("//div[@class = 'btn-group']//div[contains(@class, 'btn-white')][3]");
	By calDayBadge = By.xpath("//span[contains(@class, 'cal-day-badge')]");
	By calEventTitle = By.xpath("//span[contains(@class, 'cal-event-title')]");
	By unEnrollBtn = By.xpath("//div[contains(text(), 'unenroll')]");
	By calendarTomorrow = By.xpath("(//mwl-calendar-month-cell[contains(@class,'future')])[1]");

// CONSTRUCTOR

	public CalendarPO(WebDriver driver) {
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getMonthYear() {
		return driver.findElement(monthYear);
	}

	public WebElement getLeftArrow() {
		return driver.findElement(leftArrow);
	}

	public WebElement getRightArrow() {
		return driver.findElement(rightArrow);
	}

	public WebElement getCalDayBadge() {
		return driver.findElement(calDayBadge);
	}

	public WebElement getCalEventTitle() {
		return driver.findElement(calEventTitle);
	}

	public WebElement getUnEnrollBtn() {
		return driver.findElement(unEnrollBtn);
	}

	public WebElement getCalendarTomorrow() {
		return driver.findElement(calendarTomorrow);
	}

}
