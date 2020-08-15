package pageObjects;

import java.util.List;

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
	By cancelBtn = By.xpath("//div[contains(text(), 'cancel')]");
	By addToCalendarBtn = By.xpath("//button[contains(text(), 'add to calendar')]");
	By calendarTomorrow = By.xpath("(//mwl-calendar-month-cell[contains(@class,'future')])[1]");
	By enrolledMemberName = By.xpath("//div[@class='modal-header']//div[@class='row text-center']//small[2]");
	By classDate = By.xpath("//div[@class='modal-header']//div[@class='row text-center']//small[1]");
	By className = By.xpath("//div[@class='modal-header']//div[@class='row text-center']//h1[1]");
	By classTime = By.xpath("//div[@class='modal-header']//div[@class='row text-center']//h2[1]");
	By category = By.xpath("//div[@class='modal-body']//small[1]");
	By duration = By.xpath("//div[@class='modal-body']//small[2]");
	By type = By.xpath("//div[@class='modal-body']//small[3]");
	By instructor = By.xpath("//div[@class='modal-body']//small[4]");
	By club = By.xpath("//div[@class='modal-body']//small[5]");

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

	public List<WebElement> getCalEventTitles() {
		return driver.findElements(calEventTitle);
	}

	public WebElement getUnEnrollBtn() {
		return driver.findElement(unEnrollBtn);
	}

	public WebElement getCalendarTomorrow() {
		return driver.findElement(calendarTomorrow);
	}

	public WebElement getEnrolledMemberName() {
		return driver.findElement(enrolledMemberName);
	}

	public WebElement getClassName() {
		return driver.findElement(className);
	}

	public WebElement getClassTime() {
		return driver.findElement(classTime);
	}

	public WebElement getClassDate() {
		return driver.findElement(classDate);
	}

	public WebElement getCategory() {
		return driver.findElement(category);
	}

	public WebElement getDuration() {
		return driver.findElement(duration);
	}

	public WebElement getType() {
		return driver.findElement(type);
	}

	public WebElement getInstructor() {
		return driver.findElement(instructor);
	}

	public WebElement getClub() {
		return driver.findElement(club);
	}

	public WebElement getCancelBtn() {
		return driver.findElement(cancelBtn);
	}

	public WebElement getAddToCalendarBtn() {
		return driver.findElement(addToCalendarBtn);
	}
}
