package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckInHistoryPO {

	WebDriver driver;

// OBJECTS

//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By calendarIcons = By.xpath("//mat-datepicker-toggle[@class= 'mat-datepicker-toggle']");
	By firstCalendarDates = By.xpath("//div[contains(@class, 'cdk-overlay-pane')] //td");
	By secondCalendarDates = By.xpath("//div[contains(@class, 'cdk-overlay-pane')] //td");
	By calendarDates = By.xpath("//div[contains(@class,'mat-calendar-body-cell-content')]");
	By visitsByMonthSection = By.xpath("//div[@class='ibox-content no-margins no-padding']");
	By checkinHistorySection = By.xpath("//div[@id='checkinHistory']");
	By rightCalendarArrow = By.xpath("//button[@class='mat-calendar-next-button mat-icon-button']");
	By noEntriesMessage = By.xpath("//div[@class='panel-body no-padding']");
	By calendarMonthselected = By.xpath("//button[contains(@class, 'mat-calendar-period-button')]/span");

// CONSTRUCTOR

	public CheckInHistoryPO(WebDriver driver) {
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getFirstCalendarIcon() {
		return driver.findElements(calendarIcons).get(0);
	}

	public WebElement getSecondCalendarIcon() {
		return driver.findElements(calendarIcons).get(1);
	}

	public List<WebElement> getFirstCalendarDates() {
		return driver.findElements(firstCalendarDates);
	}

	public List<WebElement> getSecondCalendarDates() {
		return driver.findElements(secondCalendarDates);
	}

	public List<WebElement> getCalendarDates() {
		return driver.findElements(calendarDates);
	}

	public WebElement getVisitsByMonthSection() {
		return driver.findElement(visitsByMonthSection);
	}

	public WebElement getCheckinHistorySection() {
		return driver.findElement(checkinHistorySection);
	}

	public WebElement getRightCalendarArrow() {
		return driver.findElement(rightCalendarArrow);
	}

	public WebElement getNoEntriesMessage() {
		return driver.findElement(noEntriesMessage);
	}

	public WebElement getCalendarMonthselected() {
		return driver.findElement(calendarMonthselected);
	}
}
