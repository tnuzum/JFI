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
	By receiptNumbers = By.xpath("//div[@class='col-md-3 hidden-sm hidden-xs']//a");
	By receiptNumber = By.xpath("//div[@class='col-md-3 hidden-sm hidden-xs']//a");
	By receiptNumberTable = By.xpath("//div[contains(@class,'col-md-12')]");
	By searchField = By.xpath("//input[@placeholder='Search in table']");
	By calendarIcons = By.xpath("//mat-datepicker-toggle[@class= 'mat-datepicker-toggle']");
	By firstCalendarDates = By.xpath("//div[contains(@class, 'cdk-overlay-pane')] //td");
	By secondCalendarDates = By.xpath("//div[contains(@class, 'cdk-overlay-pane')] //td");
	By calendarDates = By.xpath("//div[contains(@class,'mat-calendar-body-cell-content')]");
	By rightCalendarArrow = By.xpath("//button[@class='mat-calendar-next-button mat-icon-button']");
	By calendarMonthselected = By.xpath("//button[contains(@class, 'mat-calendar-period-button')]/span");
	By columnNames = By.xpath("//div[@class = 'col-md-3']");
	By descriptionColumn = By.xpath("//div[@class = 'col-md-6']");
	By acctSummaryBox = By.xpath("//div[@class = 'row-box p-sm']");
	By unPaidInvoices = By.xpath("//h2[@class = 'at-accounthistorysummary-unpaidamount']");
	By creditOnFile = By.xpath("//h2[@class = 'at-accounthistorysummary-creditamount']");
	By balance = By.xpath("//h2[@class = 'at-accounthistorysummary-balance']");

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
}
