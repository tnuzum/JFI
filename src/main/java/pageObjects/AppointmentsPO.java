package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AppointmentsPO {

	WebDriver driver;

// OBJECTS

//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By loadingAvailabilityMessage = By.xpath("//div[contains(@class, 'swal2-loading')]");
	By selectMember = By.xpath("//select[@name='familySelect']");
	By clubs = By.xpath("(//select[@name='clubSelect'])");
	By bookableItemCategory = By.xpath("(//select[@name='bookableItemCategory'])");
	By bookableItem = By.xpath("(//select[@name='bookableItem'])");
//	By groupApptsHeader = By.xpath("//appointmentresourceselection/div/div[5]/div[2]/div/div/h2");
	By groupApptsHeader = By.xpath("//div[@id='group-activity'] //h2");

	By groupMinMaxPersons = By.xpath("//small[contains(@class, 'class-list-subheader')]"); // minimum maximum persons
																							// count
	By groupMinPersons = By.xpath("//small[contains(@class, 'at-bookappointment-group-product-min')]"); // minimum
																										// persons count
	By groupMaxPersons = By.xpath("//small[contains(@class, 'at-bookappointment-group-product-max')]");// maximum
																										// persons count
	By groupMemberSearchInput = By.xpath("//div[@id = 'group-activity'] //input[contains(@class, 'form-control')]");
	By groupMemberSearchButton = By.xpath("//div[@id = 'group-activity'] //a[@class='btn btn-primary']");
	By groupPopupAddButton1 = By.xpath("//appointmentmembersearch/div[2]/div[1]/a");// first add button
	By groupPopupAddButton2 = By.xpath("//appointmentmembersearch/div[2]/div[2]/a");// second add button
	By groupPopupMembers = By.xpath("//div[@class = 'modal-content'] //div[@class = 'ng-star-inserted']");
	By groupPopupAddButtons = By.xpath("//div[@class = 'modal-content'] //div[@class = 'ng-star-inserted']/a");

	By resourceType = By.xpath("(//select[@name='primaryResourceType'])");
	By monthSelectBackButton = By.xpath("//div[@class='btn-group']/div[1]/i");
	By monthSelectForwardButton = By.xpath("//div[@class='btn-group']/div[3]/i");
	By calendarTomorrow = By.xpath("(//mwl-calendar-month-cell[contains(@class,'future')])[1]");
	By calendarDayAfterTomorrow = By.xpath("(//mwl-calendar-month-cell[contains(@class,'future')])[2]");
	By calendarTwodaysAfter = By.xpath("(//mwl-calendar-month-cell[contains(@class,'future')])[3]");
	By calendarMonday1stFullWeek = By.xpath("//div[@class='cal-month-view']/div/div[2]/div/mwl-calendar-month-cell[2]");
	// By selectTimeMorningButton =
	// By.xpath("//strong[contains(text(),'MORNING')]");
	By selectTimeMorningButton = By.xpath("//div[contains(@class, 'tabs-container')]/ul/li[1]/a[1]");
	By selectTimeMorningLabel1 = By.xpath("//div[@class='tabs-container']/ul/li[1]/a/small[1]");
	By selectTimeMorningLabel2 = By.xpath("//div[@class='tabs-container']/ul/li[1]/a/small[1]");
	By selectTime1stAvailable = By.xpath("//div[contains(@class,'tag-wrap')]/button[1]");
	By booksNames = By.xpath("//div[contains(@class,'widget-callout p-xs clearfix')]");
	By addlResourcesCancelButton = By.xpath("//appointmentsecondaryactioncomponent/div[1]/div[2]/div[1]/button[1]"); // cancel
																														// button
	By addlResourcesBookButton = By.xpath("//appointmentsecondaryactioncomponent/div[1]/div[2]/div[1]/button[2]"); // book
																													// button
	By packageRequiredContinueButton = By.xpath("//*[text()='CONTINUE']"); // Continue button
	By bookButton = By.xpath("//button[@type = 'submit']");
	By apptBox = By.xpath("//div[contains(@class, 'appt-box')]");
	By timeSlotContainers = By.xpath("//div[contains(@class, 'tabs-container')]");
	By selectATimeDrawer = By.xpath("//mat-sidenav[contains(@class, 'mat-drawer-over')]");

	// By popup1BookButton = By.xpath("(//button[@type='button'][4]");
	By popup1BookButton = By.xpath("//button[@class='swal2-confirm swal2-styled']");
	By popup1CancelButton = By.xpath("//button[@class='swal2-cancel swal2-styled']");
	By popup1Content = By.xpath("//div[@id='swal2-content']");
	By popup1Title = By.xpath("//h2[@id='swal2-title']");
	By paymentButton = By.xpath("//button[contains(text(), 'pay')]");
	By cancelButton = By.xpath("//button[contains(text(), 'cancel')]");
	By popup2Title = By.xpath("//h2[@id='swal2-title']");
	// By popup2OKButton = By.xpath("(//button[@type='button'])[4]");
	By popup2OKButton = By.xpath("//button[@class='swal2-confirm swal2-styled']");
	By popup2Content = By.xpath("//div[@id='swal2-content']");
	By apptCheckout = By.xpath("//div[@class='row ng-star-inserted']");

	By appointmentName = By.xpath("//div[contains(@class, 'widget')]/h2");
	By clubName = By.xpath("//span[contains(@class, 'float')]/small[1]");
	By appointmentTime = By.xpath("//span[contains(@class, 'float')]/small[2]");
	By appointmentDate = By.xpath("//span[contains(@class, 'float')]/small[3]");
	By group = By.xpath("//span[contains(@class, 'float')]/small[4]");
	By rateBox = By.xpath("//div[@class='col-md-12'] //div[@class = 'rate-box']");
	By reviewSection = By.xpath("//div[@class='col-md-12']");
	By totalAmount = By.xpath("//h2[@class='text-uppercase text-danger']");
	By additionalResources = By.xpath("//div[@class = 'mat-radio-label-content']");
	By oldAppointmentBanner = By.xpath("//div[@class='widget widget-callout ng-star-inserted']");
	By newAppointmentBanner = By.xpath("//div[@class='widget widget-callout']");
	By dueAtTimeOfService = By.xpath("//div[contains(@class, 'appointment-secondaryaction-dueattimeofservice')]");
	By changeFee = By.xpath("//div[contains(@class, 'appointment-secondaryaction-changefee')]");
	By forMember = By.xpath("//div[@class = 'text-right ']/span[2]");

	By editApptPageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By editApptChangeButton = By.xpath("//button[contains(text(), 'change')]");
	By editApptCancelButton = By.xpath("//*[text()='cancel']"); // By.cssSelector("#button-cancel"); stopped working in
																// 7.28
	By editApptProceedButton = By.xpath("//*[text()='proceed with cancel']"); // By.xpath("//div[@id='show-hide-cancel']/div/div/a");
																				// stopped working in 7.28
	By editApptProceedButton1 = By.xpath("//*[text()='proceed with change']");
	By editApptCancelYesButton = By.cssSelector("button[class*='confirm']");
	By editApptCancelNoButton = By.cssSelector("button[class*='cancel']");
	By editApptCanceledMessage = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/h2[1]");
	By editApptCanceledOKButton = By.cssSelector("button[class*='confirm']");
	By cancelFeeSection = By.xpath("//div[contains(@class,'alert-danger')]");
	By noFeeSection = By.xpath("//div[contains(@class,'alert-success')]");

	// CONSTRUCTOR

	public AppointmentsPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public List<WebElement> getloadingAvailabilityMessage() {
		return driver.findElements(loadingAvailabilityMessage);
	}

	public WebElement getSelectMember() {
		return driver.findElement(selectMember);
	}

	public WebElement getclubs() {
		return driver.findElement(clubs);
	}

	public WebElement getBookableItemCategory() {
		return driver.findElement(bookableItemCategory);
	}

	public WebElement getBookableItem() {
		return driver.findElement(bookableItem);
	}

	public WebElement getGroupApptsHeader() {
		return driver.findElement(groupApptsHeader);
	}

	public WebElement getGroupMinPersons() {
		return driver.findElement(groupMinPersons);
	}

	public WebElement getGroupMaxPersons() {
		return driver.findElement(groupMaxPersons);
	}

	public WebElement getGroupMemberSearchInput() {
		return driver.findElement(groupMemberSearchInput);
	}

	public WebElement getGroupMemberSearchButton() {
		return driver.findElement(groupMemberSearchButton);
	}

	public WebElement getGroupPopupAddButton1() {
		return driver.findElement(groupPopupAddButton1);
	}

	public WebElement getGroupPopupAddButton2() {
		return driver.findElement(groupPopupAddButton2);
	}

	public List<WebElement> getGroupPopupMembers() {
		return driver.findElements(groupPopupMembers);
	}

	public List<WebElement> getGroupPopupAddButtons() {
		return driver.findElements(groupPopupAddButtons);
	}

	public WebElement getResourceType() {
		return driver.findElement(resourceType);
	}

	public WebElement getMonthSelectBackButton() {
		return driver.findElement(monthSelectBackButton);
	}

	public WebElement getMonthSelectForwardButton() {
		return driver.findElement(monthSelectForwardButton);
	}

	public WebElement getCalendarTomorrow() {
		return driver.findElement(calendarTomorrow);
	}

	public WebElement getCalendarDayAfterTomorrow() {
		return driver.findElement(calendarDayAfterTomorrow);
	}

	public WebElement getCalendarTwodaysAfter() {
		return driver.findElement(calendarTwodaysAfter);
	}

	public WebElement getCalendarMonday1stFullWeek() {
		return driver.findElement(calendarMonday1stFullWeek);
	}

	public WebElement getSelectTimeMorningButton() {
		return driver.findElement(selectTimeMorningButton);
	}

	public WebElement getSelectTimeMorningLabel1() {
		return driver.findElement(selectTimeMorningLabel1);
	}

	public WebElement getSelectTimeMorningLabel2() {
		return driver.findElement(selectTimeMorningLabel2);
	}

	public WebElement getSelectTime1stAvailable() {
		return driver.findElement(selectTime1stAvailable);
	}

	public WebElement getBooksNames() {
		return driver.findElement(booksNames);
	}

	public WebElement getAddlResourcesCancelButton() {
		return driver.findElement(addlResourcesCancelButton);
	}

	public WebElement getAddlResourcesBookButton() {
		return driver.findElement(addlResourcesBookButton);
	}

	public List<WebElement> getApptBox() {
		return driver.findElements(apptBox);

	}

	public List<WebElement> getTimeSlotContainers() {
		return driver.findElements(timeSlotContainers);

	}

	public WebElement getSelectATimeDrawer() {
		return driver.findElement(selectATimeDrawer);

	}

	public WebElement getPackageRequiredContinueButton() {
		return driver.findElement(packageRequiredContinueButton);
	}

	public WebElement getPopup1BookButton() {
		return driver.findElement(popup1BookButton);
	}

	public WebElement getPopup1CancelButton() {
		return driver.findElement(popup1CancelButton);
	}

	public WebElement getPopup1Content() {
		return driver.findElement(popup1Content);
	}

	public WebElement getPopup1Title() {
		return driver.findElement(popup1Title);
	}

	public WebElement getPaymentButton() {
		return driver.findElement(paymentButton);
	}

	public WebElement getCancelButton() {
		return driver.findElement(cancelButton);
	}

	public WebElement getApptCheckout() {
		return driver.findElement(apptCheckout);
	}

	public WebElement getPopup2Title() {
		return driver.findElement(popup2Title);
	}

	public WebElement getPopup2OKButton() {
		return driver.findElement(popup2OKButton);
	}

	public WebElement getPopup2Content() {
		return driver.findElement(popup2Content);
	}

	public WebElement getAppointmentTime() {
		return driver.findElement(appointmentTime);
	}

	public WebElement getAppointmentDate() {
		return driver.findElement(appointmentDate);
	}

	public WebElement getGroup() {
		return driver.findElement(group);
	}

	public WebElement getAppointmentName() {
		return driver.findElement(appointmentName);
	}

	public WebElement getClubName() {
		return driver.findElement(clubName);
	}

	public List<WebElement> getReviewSection() {
		return driver.findElements(reviewSection);
	}

	public WebElement getRateBox() {
		return driver.findElement(rateBox);
	}

	public WebElement getTotalAmount() {
		return driver.findElement(totalAmount);
	}

	public List<WebElement> getAdditionalResources() {
		return driver.findElements(additionalResources);
	}

	public WebElement getbookButton() {
		return driver.findElement(bookButton);
	}

	public WebElement getOldAppointmentBanner() {
		return driver.findElement(oldAppointmentBanner);
	}

	public WebElement getNewAppointmentBanner() {
		return driver.findElement(newAppointmentBanner);
	}

	public WebElement getDueAtTimeOfService() {
		return driver.findElement(dueAtTimeOfService);
	}

	public WebElement getChangeFee() {
		return driver.findElement(changeFee);
	}

	public WebElement getForMember() {
		return driver.findElement(forMember);
	}

	public WebElement getEditApptPageHeader() {
		return driver.findElement(editApptPageHeader);
	}

	public WebElement getEditApptChangeButton() {
		return driver.findElement(editApptChangeButton);
	}

	public WebElement getEditApptCancelButton() {
		return driver.findElement(editApptCancelButton);
	}

	public WebElement getCancelFeeSection() {
		return driver.findElement(cancelFeeSection);
	}

	public WebElement getNoFeeSection() {
		return driver.findElement(noFeeSection);
	}

	public WebElement getEditApptProceedButton() {
		return driver.findElement(editApptProceedButton);
	}

	public WebElement getEditApptProceedButton1() {
		return driver.findElement(editApptProceedButton1);
	}

	public WebElement getEditApptCancelYesButton() {
		return driver.findElement(editApptCancelYesButton);
	}

	public WebElement getEditApptCancelNoButton() {
		return driver.findElement(editApptCancelNoButton);
	}

	public WebElement getEditApptCanceledMessage() {
		return driver.findElement(editApptCanceledMessage);
	}

	public WebElement getEditApptCanceledOKButton() {
		return driver.findElement(editApptCanceledOKButton);
	}

}
