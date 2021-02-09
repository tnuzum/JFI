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
	By monthYearListView = By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][2]");
	By leftArrowListView = By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][1]");
	By rightArrowListView = By.xpath("//div[@class = 'btn-group']//button[contains(@class, 'btn-white')][3]");
	By calDayBadge = By.xpath("//span[contains(@class, 'cal-day-badge')]");
	By calEventTitle = By.xpath("//span[contains(@class, 'cal-event-title')]");
	By classDetailPopup = By.xpath("//div[@class='modal-content']");
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
	By status = By.xpath("//div[@class='modal-body']//small[6]");
	By calendar = By.xpath("//div[@ng-reflect-ng-switch = 'month']");
	By calendarList = By.xpath("//div[@class= 'panel panel-default m-t-md ng-star-inserted']");
	By calendarHistory = By.xpath("//div[@class= 'panel panel-default m-t-md ng-star-inserted']");
	By calendarViewLink = By.xpath("//li[contains(@class , 'nav-item')]/a");
	By calendarListViewLink = By.xpath("//li[contains(@class , 'nav-item')]/a");
	By calendarHistoryLink = By.xpath("//li[contains(@class , 'nav-item')]/a");
	By monthButton = By.xpath("//div[@id='monthViewBtn']");
	By weekButton = By.xpath("//div[contains(text(), 'Week')]");
	By clubDropdown = By.xpath("//div[contains(@class,'row-box p-sm')]//select[@name='clubs']");
	By additionalFilters = By.xpath("//a[@class='add-filters']//div");
	By classCourseApptChkBoxes = By.xpath("//div[@class = 'checkbox checkbox-primary']");
	By familyMemberNames = By.id("checkbox-colored-list");
	By selectFamilyLabel = By.xpath("//small[contains(text(),'SELECT FAMILY')]");
	By applyFiltersLink = By.linkText("Apply Filters");
	By memberClassDetails = By.xpath("//div[@class='column2']");
	By classGearButton = By.xpath("//i[@class='fa fa-gear pull-right m-r-xs ng-star-inserted']");
	By addToCalButtonListView = By.xpath("//button[contains(text(), 'ADD TO CALENDAR')]");
	By unenrollListview = By.xpath("//a[contains(text(), 'UNENROLL')]");
	By memberSections = By.xpath("//div[@class = 'ng-star-inserted'] ");
	By editAppointmentListView = By.xpath("//a[contains(text(),'EDIT APPOINTMENT')]");
	By editAppointmentButton = By.xpath("//div[contains(text(),'edit appointment')]");
	// div[@class='panel panel-default ng-star-inserted']

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

	public WebElement getMonthYearListView() {
		return driver.findElement(monthYearListView);
	}

	public WebElement getLeftArrowListView() {
		return driver.findElement(leftArrowListView);
	}

	public WebElement getRightArrowListView() {
		return driver.findElement(rightArrowListView);
	}

	public WebElement getCalDayBadge() {
		return driver.findElement(calDayBadge);
	}

	public List<WebElement> getCalDayBadges() {
		return driver.findElements(calDayBadge);
	}

	public WebElement getCalEventTitle() {
		return driver.findElement(calEventTitle);
	}

	public WebElement getClassDetailPopup() {
		return driver.findElement(classDetailPopup);
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

	public WebElement getCalendar() {
		return driver.findElement(calendar);
	}

	public WebElement getCalendarList() {
		return driver.findElements(calendarList).get(0);
	}

	public WebElement getCalendarHistory() {
		return driver.findElements(calendarHistory).get(1);
	}

	public WebElement getCalendarViewLink() {
		return driver.findElements(calendarViewLink).get(1);
	}

	public WebElement getCalendarListViewLink() {
		return driver.findElements(calendarListViewLink).get(0);
	}

	public WebElement getCalendarHistoryLink() {
		return driver.findElements(calendarHistoryLink).get(2);
	}

	public WebElement getMonthButton() {
		return driver.findElement(monthButton);
	}

	public WebElement getWeekButton() {
		return driver.findElement(weekButton);
	}

	public WebElement getAdditionalFilters() {
		return driver.findElement(additionalFilters);
	}

	public WebElement getClubDropdown() {
		return driver.findElement(clubDropdown);
	}

	public List<WebElement> getClassCourseApptChkBoxes() {
		return driver.findElements(classCourseApptChkBoxes);
	}

	public List<WebElement> getFamilyMemberNames() {
		return driver.findElements(familyMemberNames);
	}

	public WebElement getSelectFamilyLabel() {
		return driver.findElement(selectFamilyLabel);
	}

	public WebElement getApplyFiltersLink() {
		return driver.findElement(applyFiltersLink);
	}

	public WebElement getMemberClassDetails() {
		return driver.findElement(memberClassDetails);
	}

	public WebElement getClassGearButton() {
		return driver.findElement(classGearButton);
	}

	public WebElement getAddToCalButtonListView() {
		return driver.findElement(addToCalButtonListView);
	}

	public WebElement getUnenrollListview() {
		return driver.findElement(unenrollListview);
	}

	public List<WebElement> getMemberSections() {
		return driver.findElements(memberSections);
	}

	public WebElement getStatus() {
		return driver.findElement(status);
	}

	public WebElement getEditAppointmentListView() {
		return driver.findElement(editAppointmentListView);
	}

	public WebElement getEditAppointmentButton() {
		return driver.findElement(editAppointmentButton);
	}
}
