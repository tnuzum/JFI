package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AcctHistoryPO {

	WebDriver driver;

// OBJECTS

//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By receiptNumbers = By.xpath("//div[@class = 'col-lg-3 d-none d-md-block']//a");
	By receiptNumber = By.xpath("//div[@class = 'col-lg-3 d-none d-md-block']//a");
	By receiptNumberTable = By.xpath("//div[contains(@class,'col-lg-12')]");
	By searchField = By.xpath("//input[@placeholder='Search in table']");
	// By calendarIcons = By.xpath("//mat-datepicker-toggle[@class=
	By dateRangestartDate = By.xpath("//input[@id='mat-input-8']");
	By dateRangeendDate = By.xpath("//input[@id='mat-input-9']");
	By FirstcalendarIcons = By.xpath("//mat-datepicker-toggle[@class= 'mat-datepicker-toggle ng-tns-c64-18']");
	By SecondcalendarIcons = By.xpath("//mat-datepicker-toggle[@class= 'mat-datepicker-toggle ng-tns-c64-19']");
	// input[@id='mat-input-8']
	By firstCalendarDates = By.xpath("//div[contains(@class, 'cdk-overlay-pane')] //td");
	By secondCalendarDates = By.xpath("//div[contains(@class, 'cdk-overlay-pane')] //td");

	By calendarDates = By.xpath("//div[contains(@class,'mat-calendar-body-cell-content')]");
	By rightCalendarArrow = By.xpath("//button[@class='mat-calendar-next-button mat-icon-button']");
	By calendarMonthselected = By.xpath("//button[contains(@class, 'mat-calendar-period-button')]/span");
	// By columnNames = By.xpath("//div[@class = 'col-md-3']");
	By columnNames = By.xpath("//div[@class = 'col-lg-12']");

	By descriptionColumn = By.xpath("//div[@class = 'col-md-6']");
	By acctSummaryBox = By.xpath("//div[@class = 'row-box p-sm']");
	By unPaidInvoices = By.xpath("//h2[@class = 'at-accounthistorysummary-unpaidamount']");
	By creditOnFile = By.xpath("//h2[@class = 'at-accounthistorysummary-creditamount']");
	By balance = By.xpath("//h2[@class = 'at-accounthistorysummary-balance']");

	// By receiptPopup = By.xpath("//div[@class='modal-content']");
	// By receiptPopup = By.xpath("//body/div[2]/div[2]/div[1]/div[1]");
	By receiptPopup = By.xpath("//mat-dialog-container[contains(@class, 'mat-dialog-container')]");

	By receiptPopupPrint = By.xpath("//button[contains(text(), 'PRINT')]");
	By receiptPopupClose = By.xpath("//button[contains(text(), 'CLOSE')]");
	// By receiptHeader = By.xpath("//div[@class='modal-content']
	// //div[@class='modal-header']/div/h2");

	By receiptHeader = By.xpath("//mat-dialog-container[@id='mat-dialog-0']");
	By transactionDate = By.xpath("//h2[contains(text(),'Transaction Date:')]");

	// By dataColumns = By.xpath("//div[@class = 'modal-content']//div[@class =
	// 'col-md-4']");
	By dataColumns = By.xpath("//div[@class = 'row hidden-print']//div[@class ='col-lg-4']");
	// row d-none d-md-block hidden-print
	// By mediaHeadings = By.xpath("//div[@class = 'modal-content']//h2[@class
	// ='media-heading']");
	By adataColumns = By.xpath("//div[@class = 'col-lg-12']//div[@class ='col-lg-4']");

	// By mediaHeadings = By.xpath("//div[@class = 'container-fluid no-padding
	// ng-star-inserted']//h2[@class ='media-heading']");
	By mediaHeadings = By.xpath("//div[@class = 'panel-body']//h2[@class ='media-heading']");

	By invoiceAndCharges = By.xpath("//h2[contains(text(),'Invoice And Charges')]");
	By duesResponsibleTo = By.xpath("//div[contains(text(),'Dues Responsible To:')]");
	By lineItems = By.xpath("//div[@class = 'modal-content']//div[@class ='col-md-8']");
	By itemPrices = By.xpath("//div[@class = 'modal-content']//div[@class ='col-md-4 text-right']");
	By dueDates = By.xpath("//div[@class = 'modal-content']//div[@class ='col-md-2']");
	By duesResponsibleParties = By.xpath("//div[@class = 'modal-content']//div[@class = 'col-md-6']");
	By charges = By.xpath("//div[@class = 'modal-content']//div[@class ='col-md-6 text-right']");
	// By totalInvoiced = By.xpath("//div[@class = 'modal-content']//div[@class
	// ='col-xs-12 text-right']");
	By totalInvoiced = By.xpath("//div[@class = 'row']//div[@class ='col-12 text-right']");
//	By subTotal = By.xpath("//div[@class = 'modal-content']//div[@class ='row-box p-xs m-xs']//small");
	// By subTotal = By.xpath("//div[@class = 'col-lg-4 text-right']//div[@class
	// ='row-box p-xs m-xs']//small");

	By subTotal = By.xpath("//div[@class = 'col-12']//div[@class ='row-box p-xs m-xs']//small");
	By searchingAcctHistMessage = By.xpath("//div[@class = 'text-center']");

// CONSTRUCTOR

	public AcctHistoryPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getReceiptNumberTable() {
		return driver.findElement(receiptNumberTable);
	}

	public WebElement getSearchField() {
		return driver.findElement(searchField);
	}

	public List<WebElement> getReceiptNumbers() {
		return driver.findElements(receiptNumbers);
	}

	public WebElement getReceiptNumber() {
		return driver.findElement(receiptNumber);
	}

	public WebElement getFirstCalendarIcon() {
		return driver.findElement(FirstcalendarIcons);
	}

	public WebElement getSecondCalendarIcon() {
		return driver.findElement(SecondcalendarIcons);
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

	public WebElement getRightCalendarArrow() {
		return driver.findElement(rightCalendarArrow);
	}

	public WebElement getCalendarMonthselected() {
		return driver.findElement(calendarMonthselected);
	}

	public List<WebElement> getColumnNames() {
		return driver.findElements(columnNames);
	}

	public WebElement getDescriptionColumn() {
		return driver.findElement(descriptionColumn);
	}

	public WebElement getAcctSummaryBox() {
		return driver.findElements(acctSummaryBox).get(0);
	}

	public WebElement getUnPaidInvoices() {
		return driver.findElement(unPaidInvoices);
	}

	public WebElement getCreditOnFile() {
		return driver.findElement(creditOnFile);
	}

	public WebElement getBalance() {
		return driver.findElement(balance);
	}

	public WebElement getReceiptPopup() {
		return driver.findElement(receiptPopup);
	}

	public WebElement getReceiptPopupPrint() {
		return driver.findElement(receiptPopupPrint);

	}

	public WebElement getReceiptPopupClose() {
		return driver.findElement(receiptPopupClose);
	}

	public WebElement getReceiptHeader() {
		return driver.findElement(receiptHeader);
	}

	public WebElement getTransactionDate() {
		return driver.findElement(transactionDate);
	}

	public List<WebElement> getDataColumns() {
		return driver.findElements(dataColumns);
	}

	public List<WebElement> getaDataColumns() {
		return driver.findElements(adataColumns);
	}

	public List<WebElement> getMediaHeadings() {
		return driver.findElements(mediaHeadings);
	}

	public WebElement getInvoiceAndCharges() {
		return driver.findElement(invoiceAndCharges);
	}

	public WebElement getDuesResponsibleTo() {
		return driver.findElement(duesResponsibleTo);
	}

	public List<WebElement> getLineItems() {
		return driver.findElements(lineItems);
	}

	public List<WebElement> getItemPrices() {
		return driver.findElements(itemPrices);
	}

	public List<WebElement> getCharges() {
		return driver.findElements(charges);
	}

	public List<WebElement> getDueDates() {
		return driver.findElements(dueDates);
	}

	public List<WebElement> getDuesResponsibleParties() {
		return driver.findElements(duesResponsibleParties);
	}

	public WebElement getTotalInvoiced() {
		return driver.findElement(totalInvoiced);
	}

	public List<WebElement> getSubTotal() {
		return driver.findElements(subTotal);
	}

	public List<WebElement> getSearchingAcctHistMessage() {
		return driver.findElements(searchingAcctHistMessage);
	}

	public List<WebElement> getdateRangestartDate() {
		return driver.findElements(dateRangestartDate);
	}

	public List<WebElement> getdateRangeendDate() {
		return driver.findElements(dateRangeendDate);
	}

}
