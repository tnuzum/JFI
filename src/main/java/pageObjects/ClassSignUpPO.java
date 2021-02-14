package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClassSignUpPO {

	WebDriver driver;

// OBJECTS

//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By selectDateThisWeekButton = By.xpath("//label[@id='date-week']");
	By calendarIcon = By.cssSelector(".mat-datepicker-toggle");
	By calendarDates = By.xpath("//div[contains(@class,'mat-calendar-body-cell-content')]");
	By classClubDropdown = By.xpath("//div[contains(@class, 'row-box')]/div[2]/div[2]/div/select");
	By courseClubDropdown = By.xpath("//div[contains(@class, 'row-box')]/div[3]/div[1]/div[1]/select");
	By selectClassCategory = By.xpath("//div[contains(@class, 'row-box')]/div[2]/div[3]/select");
	By selectCourseCategory = By.xpath("//div[contains(@class, 'row-box')]/div[3]/div[2]/select");
	By dateSelection = By.xpath("//div[@class= 'col-md-4'][1]");
	By dayOfButton = By.id("date-day");
	By weekOfButton = By.id("date-week");
	By monthHeader = By.xpath("//div[@class='panel-heading font-bold']");
	By dayHeader = By.xpath("//div[@class='col-xs-6']");
	By dateHeader = By.xpath("//div[@class='col-xs-6 text-right']");
	By year = By.xpath("//span[contains(@class, 'btn-white')]");
	By yearLeftButton = By.xpath("//i[contains(@class, 'fa-chevron-double-left')]");
	By yearRightButton = By.xpath("//i[contains(@class, 'fa-chevron-double-right')]");
	By selectCoursesButton = By.xpath("//input[@id='rbcourses']");
	By firstAvailClassButton = By.xpath("//div[@id='classes']/div/div[2]/div[1]/div/div");
	By firstAvailClassNextDayButton = By.xpath("//div[@id='classes']/div[2]/div[2]/div[1]/div/div/div");
	By firstAvailCourseButton = By.xpath("//div[@id='courses']/div/div[2]/div[1]/div/div");
	By golfClasslabel = By.xpath("//span[contains(text(),'Golf Swing Class')]");
	By classlabel = By.xpath("//h1[contains(@class, 'modal-title')]");
	By popupClassDesc = By.xpath("//classcoursedetail[@class='ng-star-inserted']/div/div[2]/div");
	By popupCancelButton = By.xpath("//button[contains(text(),'cancel')]");
	By popupCancelButtonCourse = By.xpath("//button[contains(text(),'cancel')]");
	By popupSignUpButton = By.xpath("//button[contains(text(),'sign up')]");
	By popupSignupButtonCourse = By.xpath("//button[contains(text(),'sign up')]");
	By selectRatesAddSelButton = By.xpath("//button[@type='button']");
	By continueButton = By.xpath("//button[contains(text(), 'Continue')]");
	By popupMessage = By.xpath("//h2[@id='swal2-title']");
	By popupClose = By.xpath("//div[@class='swal2-actions']/button[1]");
//	By confirmationCheckout = By.xpath("(//a[@href='#/ShoppingCart'])[3]");
	By confirmationCheckout = By.xpath("//a[contains(text(), 'checkout')]");
	By schedulingConflictMessage = By.xpath("//div[contains(@class,'alert-danger')]/small/i");
	By popUpErrorMessage = By.xpath("//small[contains(@class, 'font')]");
	By classListHeader = By.xpath("//span[@class = 'class-list-header']");
	By className = By.xpath("//div[contains(@class, 'widget')]/h2");
	By classStartTime = By.xpath("//span[contains(@class, 'float')]/small[1]");
	By classDate = By.xpath("//span[contains(@class, 'float')]/small[2]");
	By classInstructor = By.xpath("//span[contains(@class, 'float')]/small[3]");
	By courseInstructor = By.xpath("//span[contains(@class, 'float')]/small[4]");
	By howYouWishToPay = By.xpath("//label[@for = 'radio0_0']");
	By classCostinPunches = By.xpath("//div[@class='p-xs']//div[1]/small[1]");
//	By unitsDropdown = By.xpath("//select[contains(@class, 'ng-dirty')]");
	By courseFilter = By.xpath("//a[@class='add-filters']");
	By courseKeyword = By.xpath("//h5[contains(text(),'Keyword')]");
	By searchField = By.xpath("//input[@type = 'text']");
	By courseApplyFilters = By.xpath("//button[contains(text(),'Apply Filters')]");
	By fmlyMemberLabel = By.xpath("//div[@class='modal-body'] //label");
	By fmlyMemberCheckBox = By.xpath("//div[@class='modal-body'] //input");
	By ratesOptions = By.xpath("//div[contains(@class, 'row-box')] //label");
	By enrollingMemberNames = By.xpath("//div[@class = 'float-left']");
	By detailsPopup = By.xpath("//div[@class='modal-body']");
	By classTable = By.xpath("//div[@class='column2']");
	By classTimeAndDuration = By.xpath("//div[@class='column2']/preceding-sibling::div[1]");
	By classApplyFilters = By.xpath("//button[contains(text(), 'apply filters')]");
	By memberSections = By.xpath("//div[contains(@class, 'row-box')]");
	By standbyMessage = By.xpath("//span[@class='float-left']");
	By standbySection = By.xpath("//div[contains(@class, 'row-box-red')]");
	By restOnStandby = By.xpath("//input[@id='rest_on_standby']");
	By cancelLink = By.xpath("//a[@class='btn btn-sm btn-primary btn-outline ng-star-inserted']");
	By virtualClassSearch = By.xpath("//span[contains(@class, 'at-class-search-virtual')]");
	By virtualCourseSearch = By.xpath("//span[contains(@class, 'at-course-search-virtual')]");
	By virtualDetails = By.xpath("//div[contains(@class, 'at-class-course-details-virtual')]");
	By virtualRates = By.xpath("//div[contains(@class, 'at-class-course-rates-virtual')]");
	By virtualReview = By.xpath("//div[contains(@class, 'at-class-course-review-virtual')]");

// CONSTRUCTOR

	public ClassSignUpPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getSelectDateThisWeekButton() {
		return driver.findElement(selectDateThisWeekButton);
	}

	public WebElement getCalendarIcon() {
		return driver.findElement(calendarIcon);
	}

	public List<WebElement> getCalendarDates() {
		return driver.findElements(calendarDates);
	}

	public WebElement getDateSelection() {
		return driver.findElement(dateSelection);
	}

	public WebElement getDayOfButton() {
		return driver.findElement(dayOfButton);
	}

	public WebElement getweekOfButton() {
		return driver.findElement(weekOfButton);
	}

	public WebElement getmonthHeader() {
		return driver.findElement(monthHeader);
	}

	public List<WebElement> getDayHeader() {
		return driver.findElements(dayHeader);
	}

	public List<WebElement> getDateHeader() {
		return driver.findElements(dateHeader);
	}

	public WebElement getYear() {
		return driver.findElement(year);
	}

	public WebElement getClassClubDropdown() {
		return driver.findElement(classClubDropdown);
	}

	public WebElement getCourseClubDropdown() {
		return driver.findElement(courseClubDropdown);
	}

	public WebElement getSelectClassCategory() {
		return driver.findElement(selectClassCategory);
	}

	public WebElement getSelectCourseCategory() {
		return driver.findElement(selectCourseCategory);
	}

	public WebElement getSelectCoursesButton() {
		return driver.findElement(selectCoursesButton);
	}

	public WebElement getFirstAvailClassButton() {
		return driver.findElement(firstAvailClassButton);
	}

	public WebElement getfirstAvailClassNextDayButton() {
		return driver.findElement(firstAvailClassNextDayButton);
	}

	public WebElement getFirstAvailCourseButton() {
		return driver.findElement(firstAvailCourseButton);
	}

	public WebElement getGolfClasslabel() {
		return driver.findElement(golfClasslabel);
	}

	public WebElement getPopupClassDesc() {
		return driver.findElement(popupClassDesc);
	}

	public WebElement getPopupCancelButton() {
		return driver.findElement(popupCancelButton);
	}

	public WebElement getPopupSignUpButton() {
		return driver.findElement(popupSignUpButton);
	}

	public WebElement getContinueButton() {
		return driver.findElement(continueButton);
	}

	public WebElement getConfirmationCheckout() {
		return driver.findElement(confirmationCheckout);
	}

	public WebElement getSchedulingConflictMessage() {
		return driver.findElement(schedulingConflictMessage);
	}

	public WebElement getPopupMessage() {
		return driver.findElement(popupMessage);
	}

	public WebElement getPopupClose() {
		return driver.findElement(popupClose);
	}

	public WebElement getClassName() {
		return driver.findElement(className);
	}

	public WebElement getClassStartTime() {
		return driver.findElement(classStartTime);
	}

	public WebElement getClassDate() {
		return driver.findElement(classDate);
	}

	public WebElement getClassInstructor() {
		return driver.findElement(classInstructor);
	}

	public WebElement getCourseInstructor() {
		return driver.findElement(courseInstructor);
	}

	public WebElement getHowYouWishToPay() {
		return driver.findElement(howYouWishToPay);
	}

	public WebElement getClassCostinPunches() {
		return driver.findElement(classCostinPunches);
	}

	public WebElement getCourseFilter() {
		return driver.findElement(courseFilter);
	}

	public WebElement getCourseKeyword() {
		return driver.findElement(courseKeyword);
	}

	public WebElement getSearchField() {
		return driver.findElement(searchField);
	}

	public WebElement getCourseApplyFilters() {
		return driver.findElement(courseApplyFilters);
	}

	public WebElement getPopupCancelButtonCourse() {
		return driver.findElement(popupCancelButtonCourse);
	}

	public WebElement getPopupSignupButtonCourse() {
		return driver.findElement(popupSignupButtonCourse);
	}

	public WebElement getPopUpErrorMessage()

	{
		return driver.findElement(popUpErrorMessage);
	}

	public WebElement getClasslabel()

	{
		return driver.findElement(classlabel);
	}

	public List<WebElement> getFmlyMemberLabel()

	{
		return driver.findElements(fmlyMemberLabel);
	}

	public List<WebElement> getFmlyMemberCheckBox()

	{
		return driver.findElements(fmlyMemberCheckBox);
	}

	public List<WebElement> getRatesOptions()

	{
		return driver.findElements(ratesOptions);
	}

	public List<WebElement> getEnrollingMemberNames()

	{
		return driver.findElements(enrollingMemberNames);
	}

	public WebElement getDetailsPopup()

	{
		return driver.findElement(detailsPopup);
	}

	public List<WebElement> getClassTable()

	{
		return driver.findElements(classTable);
	}

	public List<WebElement> getClassTimeAndDuration()

	{
		return driver.findElements(classTimeAndDuration);
	}

	public WebElement getClassApplyFilters()

	{
		return driver.findElement(classApplyFilters);
	}

	public List<WebElement> getMemberSections()

	{
		return driver.findElements(memberSections);
	}

	public WebElement getstandbyMessage()

	{
		return driver.findElement(standbyMessage);
	}

	public WebElement getstandbySection()

	{
		return driver.findElement(standbySection);
	}

	public WebElement getRestOnStandby()

	{
		return driver.findElement(restOnStandby);
	}

	public WebElement getCancelLink()

	{
		return driver.findElement(cancelLink);
	}

	/*
	 * public WebElement getUnitsDropdown() { return
	 * driver.findElement(unitsDropdown); }
	 */
	public WebElement getVirtualClassSearch() {
		return driver.findElement(virtualClassSearch);
	}

	public WebElement getVirtualCourseSearch() {
		return driver.findElement(virtualCourseSearch);
	}

	public WebElement getVirtualDetails() {
		return driver.findElement(virtualDetails);
	}

	public WebElement getVirtualRates() {
		return driver.findElement(virtualRates);
	}

	public WebElement getVirtualReview() {
		return driver.findElement(virtualReview);
	}

	public WebElement getYearLeftButton() {
		return driver.findElement(yearLeftButton);
	}

	public WebElement getYearRightButton() {
		return driver.findElement(yearRightButton);
	}
}
