package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UnenrollPO {

	WebDriver driver;

// OBJECTS

	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By unenrollButton = By.xpath("//a[contains(@class,'unenroll')]");// button on Unenroll page
	By unenrollConfirmMessage1 = By.xpath("//h2[@id='swal2-title']");// displays "Unenrolled"
	By unenrollConfirmYesButton = By.xpath("//button[contains(@class, 'swal2-confirm')]");
	By notEligibleMessage = By.xpath("//div[contains(@class,'alert-danger')]"); // returns "We apologize, this class is
																				// not eligible for unenrollment."
	By cancelButton = By.xpath("//div[contains(@class,'alert-danger')]/div");
	By popupMessageBox = By.xpath("//div[contains(@class, 'swal2-popup')]");
	By alertSuccess = By.xpath("//div[contains(@class, 'alert alert-success')]");
	By alertDanger = By.xpath("//div[contains(@class, 'alert alert-danger')]");
	By unenrollWithSavedCard = By.xpath("//a[contains(text(),'UNEROLL WITH SAVED CARD')]");
	By unenrollWithNewCard = By.xpath("//a[contains(text(),'UNENROLL WITH NEW CREDIT CARD')]");
	By paymentButton = By.xpath("//button[contains(text(), 'Pay')]");
	By refundButton = By.xpath("//button[contains(text(), 'Refund')]");
	By newCardButton = By.xpath("//span[contains( text(), 'Add New Card')]");
	By onAccountAndSavedCards = By.xpath("//div[@class='radio no-padding']");
	By classNameTitle = By.xpath("//div[@class='ibox-content']/h2");
// CONSTRUCTOR

	public UnenrollPO(WebDriver driver) {
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getUnenrollButton() {
		return driver.findElement(unenrollButton);
	}

	public WebElement getUnenrollConfirmMessage1() {
		return driver.findElement(unenrollConfirmMessage1);
	}

	public WebElement getUnenrollConfirmYesButton() {
		return driver.findElement(unenrollConfirmYesButton);
	}

	public WebElement getNotEligibleMessage() {
		return driver.findElement(notEligibleMessage);
	}

	public WebElement getCancelButton() {
		return driver.findElement(cancelButton);
	}

	public WebElement getPopupMessageBox() {
		return driver.findElement(popupMessageBox);
	}

	public WebElement getNoCancelFeeMsg() {
		return driver.findElements(alertSuccess).get(0);
	}

	public WebElement getCancelFeeMsg() {
		return driver.findElements(alertDanger).get(0);
	}

	public WebElement getRefundMsg() {
		return driver.findElements(alertSuccess).get(1);
	}

	public WebElement getNoRefundMSg() {
		return driver.findElements(alertDanger).get(1);
	}

	public WebElement getUnenrollWithSavedCard() {
		return driver.findElement(unenrollWithSavedCard);
	}

	public WebElement getUnenrollWithNewCard() {
		return driver.findElement(unenrollWithNewCard);
	}

	public WebElement getPaymentButton() {
		return driver.findElement(paymentButton);
	}

	public WebElement getRefundButton() {
		return driver.findElement(refundButton);
	}

	public WebElement getNewCardButton() {
		return driver.findElement(newCardButton);
	}

	public WebElement getOnAccountAndSavedCards() {
		return driver.findElement(onAccountAndSavedCards);
	}

	public WebElement getClassNameTitle() {
		return driver.findElement(classNameTitle);
	}
}
