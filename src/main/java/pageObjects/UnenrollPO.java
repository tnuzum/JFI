package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UnenrollPO {

	WebDriver driver;

// OBJECTS

//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By unenrollButton = By.xpath("//button[contains(@class,'unenroll-btn-on-account-only')]");// button on Unenroll page
	By unenrollNoRefund = By.xpath("//button[contains(@class,'unenroll-btn-no-refund')]");
	By unenrollConfirmMessage1 = By.xpath("//h2[@id='swal2-title']");// displays "Unenrolled"
	By unenrollConfirmYesButton = By.xpath("//button[contains(@class, 'swal2-confirm')]");
	By notEligibleMessage = By.xpath("//div[contains(@class,'alert-danger')]"); // returns "We apologize, this class is
																				// not eligible for unenrollment."
	By cancelButton = By.xpath("//button[contains(@class,'unenroll-btn-cancel')]");
	By popupMessageBox = By.xpath("//div[contains(@class, 'swal2-popup')]");
	By alertSuccess = By.xpath("//div[contains(@class, 'alert alert-success')]");
	By alertDanger = By.xpath("//div[contains(@class, 'alert alert-danger')]");
	By unenrollWithSavedCard = By.xpath("//a[contains(text(),'UNEROLL WITH SAVED CARD')]");
	By unenrollWithNewCard = By.xpath("//a[contains(text(),'UNENROLL WITH NEW CREDIT CARD')]");
	By paymentButton = By.xpath("//button[contains(text(), 'Pay')]");
	By refundButton = By.xpath("//button[contains(text(), 'Refund')]");
	By newCardButton = By.xpath("//span[contains( text(), 'Add New Card')]");
	By onAccountAndSavedCards = By.xpath("//div[contains(@class,'radio no-padding')]");
	By classNameTitle = By.xpath("//div[contains(@class,'ibox-content')]/h2");

	By refundHeader = By.xpath("//small[contains(text(),'Refund')]");

//	By refundOAInfo = By.xpath("//div[@class='rate-box unenroll-refund-on-account']");
//	By refundOAText = By.xpath("//div[@class='rate-box unenroll-refund-on-account']/strong");
//	By refundOAAmnt = By.xpath("//div[@class='rate-box unenroll-refund-on-account']/span");
//	By refundOATaxInfo = By.xpath("//div[@class='rate-box unenroll-refund-on-account']/small");
	By refundOAInfo = By.xpath("//div[contains(@class,'rate-box unenroll-refund-on-account')]");
	By refundOAText = By.xpath("//div[contains(@class,'rate-box unenroll-refund-on-account')]/strong");
	By refundOAAmnt = By.xpath("//div[contains(@class,'rate-box unenroll-refund-on-account')]/span");
	By refundOATaxInfo = By.xpath("//div[contains(@class,'rate-box unenroll-refund-on-account')]/small");

//	By refundCCInfo = By.xpath("//div[@class='rate-box unenroll-refund-item-price']");
//	By refundCCText = By.xpath("//div[@class='rate-box unenroll-refund-item-price']/strong");
//	By refundCCAmnt = By.xpath("//div[@class='rate-box unenroll-refund-item-price']/span");
	By refundCCInfo = By.xpath("//div[contains(@class,'rate-box unenroll-refund-item-price')]");
	By refundCCText = By.xpath("//div[contains(@class,'rate-box unenroll-refund-item-price')]/strong");
	By refundCCAmnt = By.xpath("//div[contains(@class,'rate-box unenroll-refund-item-price')]/span");

//	By refundUnitInfo = By.xpath("//div[@class='rate-box unenroll-refund-package-quantity']");
//	By refundUnitText = By.xpath("//div[@class='rate-box unenroll-refund-package-quantity']/strong");
//	By refundUnitNo = By.xpath("//div[@class='rate-box unenroll-refund-package-quantity']/span");
	By refundUnitInfo = By.xpath("//div[contains(@class,'rate-box unenroll-refund-package-quantity')]");
	By refundUnitText = By.xpath("//div[contains(@class,'rate-box unenroll-refund-package-quantity')]/strong");
	By refundUnitNo = By.xpath("//div[contains(@class,'rate-box unenroll-refund-package-quantity')]/span");

	By cancelHeader = By.xpath("//small[contains(text(),'Cancel Fee')]");
//	By cancelInfo = By.xpath("//div[@class='rate-box unenroll-cancel-fee']");
//	By cancelText = By.xpath("//div[@class='rate-box unenroll-cancel-fee']/strong");
//	By cancelAmnt = By.xpath("//div[@class='rate-box unenroll-cancel-fee']/span");
	By cancelInfo = By.xpath("//div[contains(@class,'rate-box unenroll-cancel-fee')]");
	By cancelText = By.xpath("//div[contains(@class,'rate-box unenroll-cancel-fee')]/strong");
	By cancelAmnt = By.xpath("//div[contains(@class,'rate-box unenroll-cancel-fee')]/span");

//	By stanbyUnenrollText = By
//			.xpath("//div[@class ='rate-box text-danger unenroll-not-eligible-for-unenrollment']//strong");
	By stanbyUnenrollText = By
			.xpath("//div[contains(@class,'rate-box text-danger unenroll-not-eligible-for-unenrollment')]//strong");

	By stanbyHeader = By.xpath("//small[contains(text(),'On Standby')]");

	By totalAmount = By.xpath("//h2[contains(@class,'text-uppercase')]");

//	By noRefund = By.xpath("//div[@class='rate-box unenroll-non-refundable']/strong");
	By noRefund = By.xpath("//div[contains(@class, 'rate-box unenroll-non-refundable')]/strong");
	By canNotCancelMsg = By.xpath("//div[contains(@class, 'unenroll-not-eligible-for-unenrollment')]");

	By type = By.xpath("//small[contains(text(),'Type:')]//following-sibling::strong");
	By date = By.xpath("//small[contains(text(),'Date:')]//following-sibling::strong");
	By startDate = By.xpath("//small[contains(text(),'Start Date:')]//following-sibling::strong");
	By endDate = By.xpath("//small[contains(text(),'End Date:')]//following-sibling::strong");
	By startTime = By.xpath("//small[contains(text(),'Start Time:')]//following-sibling::strong");
	By duration = By.xpath("//small[contains(text(),'Duration:')]//following-sibling::strong");
	By instructor = By.xpath("//small[contains(text(),'Instructor')]//following-sibling::strong");
	By location = By.xpath("//small[contains(text(),'Location:')]//following-sibling::strong");
	By category = By.xpath("//small[contains(text(),'Category:')]//following-sibling::strong");
	By status = By.xpath("//small[contains(text(),'Status:')]//following-sibling::strong");
	By searchingAcctHistMessage = By.xpath("//div[@class = 'text-center']");

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

	public WebElement getUnenrollNoRefund() {
		return driver.findElement(unenrollNoRefund);
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

	public WebElement getRefundHeader() {
		return driver.findElement(refundHeader);
	}

	public WebElement getRefundOAInfo() {
		return driver.findElement(refundOAInfo);
	}

	public WebElement getRefundOAText() {
		return driver.findElement(refundOAText);
	}

	public WebElement getRefundOAAmnt() {
		return driver.findElement(refundOAAmnt);
	}

	public WebElement getRefundOATaxInfo() {
		return driver.findElement(refundOATaxInfo);
	}

	public WebElement getRefundCCInfo() {
		return driver.findElement(refundCCInfo);
	}

	public WebElement getRefundCCText() {
		return driver.findElement(refundCCText);
	}

	public WebElement getRefundCCAmnt() {
		return driver.findElement(refundCCAmnt);
	}

	public WebElement getRefundUnitInfo() {
		return driver.findElement(refundUnitInfo);
	}

	public WebElement getRefundUnitText() {
		return driver.findElement(refundUnitText);
	}

	public WebElement getRefundUnitNo() {
		return driver.findElement(refundUnitNo);
	}

	public WebElement getCancelHeader() {
		return driver.findElement(cancelHeader);
	}

	public WebElement getCancelInfo() {
		return driver.findElement(cancelInfo);
	}

	public WebElement getCancelText() {
		return driver.findElement(cancelText);
	}

	public WebElement getCancelAmnt() {
		return driver.findElement(cancelAmnt);
	}

	public WebElement getTotalAmount() {
		return driver.findElement(totalAmount);
	}

	public WebElement getNoRefund() {
		return driver.findElement(noRefund);
	}

	public WebElement getCanNotCancelFMsg() {
		return driver.findElement(canNotCancelMsg);
	}

	public WebElement getStanbyHeader() {
		return driver.findElement(stanbyHeader);
	}

	public WebElement getType() {
		return driver.findElement(type);
	}

	public WebElement getDate() {
		return driver.findElement(date);
	}

	public WebElement getStartDate() {
		return driver.findElement(startDate);
	}

	public WebElement getEndDate() {
		return driver.findElement(endDate);
	}

	public WebElement getStartTime() {
		return driver.findElement(startTime);
	}

	public WebElement getDuration() {
		return driver.findElement(duration);
	}

	public WebElement getInstructor() {
		return driver.findElement(instructor);
	}

	public WebElement getLocation() {
		return driver.findElement(location);
	}

	public WebElement getCategory() {
		return driver.findElement(category);
	}

	public WebElement getStatus() {
		return driver.findElement(status);
	}

	public WebElement getStanbyUnenrollText() {
		return driver.findElement(stanbyUnenrollText);
	}

	public List<WebElement> getSearchingAcctHistMessage() {
		return driver.findElements(searchingAcctHistMessage);
	}

}
