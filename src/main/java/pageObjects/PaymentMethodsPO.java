package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaymentMethodsPO {

	WebDriver driver;

// This is the page for the link in the left pane under My Account labels Pay Balance

// OBJECTS

	By onAccountAndSavedCards = By.xpath("//div[@class='radio no-padding']");
//	By paymentButton = By.xpath("//button[@type = 'submit']");
	By paymentButton = By.xpath("//button[contains(text(), 'Pay')]");
	By newCardButton = By.xpath("//span[contains( text(), 'Add New Card')]");
//	By newCardButton = By.xpath("//a[@class = 'at-paymethods-link-addnewcard']");
	By closeButton = By.xpath("//button[@id='close-button']");
	By nameOnCardField = By.xpath("//input[@id='nameOnCard']");
	By cardNumberField = By.cssSelector("#cardNumber");
	// .xpath("//input[contains(@class, 'at-paymethods-input-cardnumber')]");
	By expirationMonth = By.xpath("//input[@name='ExpireMonth']");
	By expirationYear = By.xpath("//input[@name='ExpireYear']");
	By securityCode = By.xpath("//input[@id='securityCode']");
//	By saveCardNo = By.xpath("//label[@for = 'sc-no']");
	By saveCardNo = By.xpath("//input[@id='sc-no']");
	By saveCardYes = By.xpath("//input[@id='sc-yes']");
	By moreInfoSaveCard = By.xpath("//div[contains(@class, ' at-additionalquestions-moreinfosavecard')]/a");
	By houseAcctNo = By.xpath("//input[@id='ac-no']");
	By houseAcctYes = By.xpath("//input[@id='ac-yes']");
	By moreInfoOnAccount = By.xpath("//div[contains(@class, ' at-additionalquestions-moreinfoonaccount')]/a");
	By inClubPurchaseNo = By.xpath("//input[@id='ic-no']");
	By inClubPurchaseYes = By.xpath("//input[@id='ic-yes']");
	By moreInfoUseInPos = By.xpath("//div[contains(@class, ' at-additionalquestions-moreinfouseinpos')]/a");
	By checkBox = By.id("checkTermsPaymentMethods");
	By cancelButton = By.xpath("//button[contains(text(), 'Cancel')]");
	By popupContent = By.xpath("//div[@id = 'swal2-content']");
	By popupOk = By.xpath("//button[contains(@class,  'swal2-confirm')]");
	By popupElement = By.xpath("//div[contains(@class, 'swal2-popup')]");
	By additionalQuestionsSection = By.xpath("//additionalquestions[contains(@class,'ng-tns')]/div");
	By saveCardQuestion = By.xpath("//label[contains(text(),'Save this card for future use on this site?')]");
	By onAccountQuestion = By.xpath("//label[contains(text(),'Would you like to use this card for your On Accoun')]");
	By inClubQuestion = By.xpath("//label[contains(text(),'Would you like to use this card at the club?')]");
	By additionalQuestionPopupTitle = By.xpath("//h1[@class='modal-title text-center']");
	By additionalQuestionPopupClose = By
			.xpath("//button[@class='btn btn-primary btn-outline'][contains(text(),'close')]");
	By signaturePad = By.xpath("//div[@class='m-signature-pad--body']");
//	By signaturePad = By.xpath("//signature-pad[@class='at-paymethods-signaturepad']");
	By totalAmount = By.xpath("//h2[contains(@class,'text-uppercase text')]");
	By sigPadInOut = By.id("SignaturePadPaymentMethods");

// CONSTRUCTOR

	public PaymentMethodsPO(WebDriver driver) {
		this.driver = driver;
	}
// METHODS

	public WebElement getOnAccountAndSavedCards() {
		return driver.findElement(onAccountAndSavedCards);
	}

	public WebElement getPaymentButton() {
		return driver.findElement(paymentButton);
	}

	public WebElement getNewCardButton() {
		return driver.findElement(newCardButton);
	}

	public WebElement getCloseButton() {
		return driver.findElement(closeButton);
	}

	public WebElement getNameOnCardField() {
		return driver.findElement(nameOnCardField);
	}

	public WebElement getCardNumberField() {
		return driver.findElement(cardNumberField);
	}

	public WebElement getExpirationMonth() {
		return driver.findElement(expirationMonth);
	}

	public WebElement getExpirationYear() {
		return driver.findElement(expirationYear);
	}

	public WebElement getSecurityCode() {
		return driver.findElement(securityCode);
	}

	public WebElement getSaveCardNo() {
		return driver.findElement(saveCardNo);
	}

	public WebElement getSaveCardYes() {
		return driver.findElement(saveCardYes);
	}

	public WebElement getMoreInfoSaveCard() {
		return driver.findElement(moreInfoSaveCard);
	}

	public WebElement getHouseAcctNo() {
		return driver.findElement(houseAcctNo);
	}

	public WebElement getHouseAcctYes() {
		return driver.findElement(houseAcctYes);
	}

	public WebElement getMoreInfoOnAccount() {
		return driver.findElement(moreInfoOnAccount);
	}

	public WebElement getInClubPurchaseNo() {
		return driver.findElement(inClubPurchaseNo);
	}

	public WebElement getInClubPurchaseYes() {
		return driver.findElement(inClubPurchaseYes);
	}

	public WebElement getMoreInfoUseInPos() {
		return driver.findElement(moreInfoUseInPos);
	}

	public WebElement getCheckBox() {
		return driver.findElement(checkBox);
	}

	public WebElement getPopupContent() {
		return driver.findElement(popupContent);
	}

	public WebElement getPopupElement() {
		return driver.findElement(popupElement);
	}

	public WebElement getPopupOk() {
		return driver.findElement(popupOk);
	}

	public WebElement getCancelButton() {
		return driver.findElement(cancelButton);
	}

	public WebElement getAdditionalQuestionsSection() {
		return driver.findElement(additionalQuestionsSection);
	}

	public WebElement getSaveCardQuestion() {
		return driver.findElement(saveCardQuestion);
	}

	public List<WebElement> getSaveCardQuestions() {
		return driver.findElements(saveCardQuestion);
	}

	public WebElement getOnAccountQuestion() {
		return driver.findElement(onAccountQuestion);
	}

	public List<WebElement> getOnAccountQuestions() {
		return driver.findElements(onAccountQuestion);
	}

	public WebElement getInClubQuestion() {
		return driver.findElement(inClubQuestion);
	}

	public List<WebElement> getInClubQuestions() {
		return driver.findElements(inClubQuestion);
	}

	public WebElement getAdditionalQuestionPopupTitle() {
		return driver.findElement(additionalQuestionPopupTitle);
	}

	public WebElement getAdditionalQuestionPopupClose() {
		return driver.findElement(additionalQuestionPopupClose);
	}

	public WebElement getSignaturePad() {
		return driver.findElement(signaturePad);
	}

	public WebElement getSigPadInOut() {
		return driver.findElement(sigPadInOut);
	}

	public WebElement getTotalAmount() {
		return driver.findElement(totalAmount);
	}
}
