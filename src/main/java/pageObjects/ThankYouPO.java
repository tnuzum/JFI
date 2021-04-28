package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ThankYouPO {

	WebDriver driver;

// This is the page for the link in the left pane under My Account labels Pay Balance

// OBJECTS

	By thankYouText = By.xpath("//h1[@class='text-uppercase']");
	By smallText = By.xpath("//div[@class='col-sm-12 p-lg']");
	By printReceiptButton = By.xpath("//button[contains(text(), 'Print Receipt')]");
	By receiptPopup = By.xpath("//mat-dialog-container[contains(@class, 'mat-dialog-container')]");
	By linksSection = By.xpath("//div[@class='row']");
	By whatNextText = By.xpath("//div[@class='row']/h2");
	By receiptPopupPrint = By.xpath("//button[contains(text(), 'PRINT')]");
	By receiptPopupClose = By.xpath("//button[contains(text(), 'CLOSE')]");
	By dashBoardLink = By.xpath("//div[@class = 'row']//a[@href = '/home']");
	By viewClassesLink = By.xpath("//div[@class = 'row']//a[@href = '/ClassList']");
	By viewCoursesEventsLink = By.xpath("//div[@class = 'row']//a[@href = '/CourseList']");
	By bookAppointmentsLink = By.xpath("//div[@class = 'row']//a[@href = '/Appointments']");
	By receiptNumber = By.tagName("strong");
	By receiptHeader = By.xpath(
			"//mat-dialog-container[contains(@class, 'mat-dialog-container')]//div[@class = 'col-8 text-center']//h2");
// CONSTRUCTOR

	public ThankYouPO(WebDriver driver) {
		this.driver = driver;
	}
// METHODS

	public WebElement getThankYouText() {
		return driver.findElement(thankYouText);
	}

	public WebElement getsmallText() {
		return driver.findElement(smallText);
	}

	public WebElement getPrintReceiptButton() {
		return driver.findElement(printReceiptButton);
	}

	public WebElement getReceiptPopup() {
		return driver.findElement(receiptPopup);
	}

	public WebElement getLinksSection() {
		return driver.findElement(linksSection);
	}

	public WebElement getWhatNextText() {
		return driver.findElement(whatNextText);
	}

	public WebElement getReceiptPopupPrint() {
		return driver.findElement(receiptPopupPrint);

	}

	public WebElement getReceiptPopupClose() {
		return driver.findElement(receiptPopupClose);
	}

	public WebElement getDashBoardLink() {
		return driver.findElement(dashBoardLink);
	}

	public WebElement getViewClassesLink() {
		return driver.findElement(viewClassesLink);
	}

	public WebElement getViewCoursesEventsLink() {
		return driver.findElement(viewCoursesEventsLink);
	}

	public WebElement getBookAppointmentsLink() {
		return driver.findElement(bookAppointmentsLink);
	}

	public WebElement getReceiptNumber() {
		return driver.findElement(receiptNumber);
	}

	public WebElement getReceiptHeader() {
		return driver.findElement(receiptHeader);
	}

}
