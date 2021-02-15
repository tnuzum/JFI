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
	By receiptPopup = By.xpath("//div[@class='modal-content']");
	By linksSection = By.xpath("//div[@class='rate-box']");
	By whatNextText = By.xpath("//div[@class='rate-box']/h2");
	By receiptPopupPrint = By.xpath("//button[contains(text(), 'PRINT')]");
	By receiptPopupClose = By.xpath("//button[contains(text(), 'CLOSE')]");
	By dashBoardLink = By.xpath("//a[@href = '#/Home']");
	By viewClassesLink = By.xpath("//a[@href = '#/ClassList']");
	By viewCoursesEventsLink = By.xpath("//a[@href = '#/CourseList']");
	By bookAppointmentsLink = By.xpath("//a[@href = '#/Appointments']");
	By receiptNumber = By.tagName("strong");
	By receiptHeader = By.xpath("//div[@class='modal-content'] //div[@class='modal-header']/div/h2");
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
