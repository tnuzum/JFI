package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaymentPO {

	WebDriver driver;

// This is the page for the link in the left pane under My Account labels Pay Balance

// OBJECTS

//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By amountDueLabel = By.xpath("//enterpaymentamount/div/div/div/div/div/h2");
	By amountRadioButton1 = By.xpath("(//div[@class='radio'])[1]/input");
	By amountLabel1 = By.xpath("(//div[@class='radio'])[1]/label");
	By amountRadioButton2 = By.xpath("(//div[@class='radio'])[2]/input");
	By amountLabel2 = By.xpath("(//div[@class='radio'])[2]/label");
//	By amountRadioButton3 = By.xpath("(//div[@class='radio'])[3]/input");
	By amountRadioButton3 = By.xpath("//input[@id='radio3']");
	By amountLabel3 = By.xpath("(//div[@class='radio'])[3]/label");
	By payWithThisMethodButton1 = By.xpath("//button[contains(@class, 'at-paybalance-button-savedcard-houseacct')]");
	By zeroPaymentMessage = By.xpath("//div[@id='swal2-content']");
	By customAmountInput = By.xpath("//input[@name='selectedPaymentAmount']");
	By paymentAmountInput = By.xpath("//input[@name='payment']");
	By nameOnCard = By.xpath("//input[@name='nameCard']");
	By cardNumber = By.xpath("//input[@name='cardNumber']");
	By expireMonth = By.xpath("//input[@name='ExpireMonth']");
	By expireYear = By.xpath("//input[@name='ExpireYear']");
	By cvc = By.xpath("//input[@name='CVC']");
	By saveCardNoRadio = By.xpath("//input[@id='sc-no']");
	By saveCardYesRadio = By.xpath("//input[@id='sc-yes']");
	By moreInfoSaveCard = By.xpath("//div[contains(@class, ' at-additionalquestions-moreinfosavecard')]/a");
	By houseAcctNoRadio = By.xpath("//input[@id='ac-no']");
	By houseAcctYesRadio = By.xpath("//input[@id='ac-yes']");
	By moreInfoOnAccount = By.xpath("//div[contains(@class, ' at-additionalquestions-moreinfoonaccount')]/a");
	By inClubPurchaseNoRadio = By.xpath("//input[@id='ic-no']");
	By inClubPurchaseYesRadio = By.xpath("//input[@id='ic-yes']");
	By moreInfoUseInPos = By.xpath("//div[contains(@class, ' at-additionalquestions-moreinfouseinpos')]/a");
	By iAgreeCheckbox = By.xpath("//div[contains(@class, 'at-paybalance-checkbox-tandc')]/input");
	By submitButton = By.xpath("//button[contains(@class, 'at-paybalance-button-newcard-submit')]");
	By popupPayButton = By.xpath("//div[@class='swal2-actions']/button[1]");
	By popupConfirmation1 = By.xpath("//div[@class='swal2-header']/h2");// displays Payment Made! message
	By popupConfirmationButton = By.xpath("//div[@class='swal2-actions']/button[1]");
	By popupText = By.xpath("//h2[@id ='swal2-title']");
	By confirmPageThankYou = By.xpath("//h1[@class='text-capitalize']");
	By selectPaymentOnAccountButton = By.xpath("//div[@id='accordion']/div[1]/div[1]/a/h5");
//	By selectPaymentOnAccountPayWithButton = By.xpath("//button[@class='btn btn-primary btn-sm use-method make-payment text-capitalize']");
	By selectPaymentOnAccountPayWithButton = By
			.xpath("//div[@id = 'collapseOne']/cartpaymentonaccount/div/div[2]/div/button");
	By selectPaymentStoredCardsButton = By.xpath("//div[@id='accordion']/div[2]/div[1]/a/h5");
	By selectPaymentNewCardButton = By.xpath("//h5[contains(text(),'NEW CREDIT CARD')]");
	By storedCards = By.xpath("//div[@class = 'payment-card']");
	By signaturePad = By.xpath("//div[@class='m-signature-pad--body']");
	By popupContent = By.xpath("//div[@id = 'swal2-content']");
	By checkboxes = By.xpath("//input[contains(@class, 'checkbox')]");
	By linkAgreementsHeader = By.xpath("//span[contains(text(),'My Agreements')]");
	By labelText = By.xpath(
			"//label[contains(text(),'Would you like to use this form of payment for paying existing Membership/Service Plan(s)?')]");
	By labelText1 = By.xpath("//div[contains(text(),'A Selection is Required')]");
	By firstAgreement = By.xpath("//label[contains(text(),'Balance Weight Loss 12 Week')]/preceding-sibling::input");
	By agreementCheckBox = By.xpath("//input[contains(@class, 'at-foplinkagreement-agreement')]");
	By agreementLabel = By.xpath("//input[contains(@class, 'at-foplinkagreement-agreement')]/following-sibling::label");
	By noThanks = By.xpath("//label[contains(text(),'No Thanks')]/preceding-sibling::input");
	By areYouSure = By.xpath("//input[contains(@class,'at-foplinkagreement-agreement-areyousure')]");
	By slideDownBox = By.xpath("//div[@class='rate-box']");
	By additionalQuestionsSection = By.xpath("//additionalquestions[contains(@class,'ng-tns')]/div");
	By saveCardQuestion = By.xpath("//label[contains(text(),'Save this card for future use on this site?')]");
	By onAccountCardQuestion = By
			.xpath("//label[contains(text(),'Would you like to use this card for your On Accoun')]");
	By onAccountBankQuestion = By
			.xpath("//label[contains(text(),'Would you like to use this bank account for your O')]");
	By inClubQuestion = By.xpath("//label[contains(text(),'Would you like to use this card at the club?')]");
	By additionalQuestionPopupTitle = By.xpath("//h1[@class='modal-title text-center']");
	By additionalQuestionPopupClose = By.xpath("//button[@class='btn btn-primary btn-outline']");

// CONSTRUCTOR

	public PaymentPO(WebDriver driver) {
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getAmountDueLabel() {
		return driver.findElement(amountDueLabel);
	}

	public WebElement getAmountRadioButton1() {
		return driver.findElement(amountRadioButton1);
	}

	public WebElement getAmountLabel1() {
		return driver.findElement(amountLabel1);
	}

	public WebElement getAmountRadioButton2() {
		return driver.findElement(amountRadioButton2);
	}

	public WebElement getAmountLabel2() {
		return driver.findElement(amountLabel2);
	}

	public WebElement getAmountRadioButton3() {
		return driver.findElement(amountRadioButton3);
	}

	public WebElement getAmountLabel3() {
		return driver.findElement(amountLabel3);
	}

	public WebElement getPayWithThisMethodButton1() {
		return driver.findElement(payWithThisMethodButton1);
	}

	public List<WebElement> getStoredCards() {
		return driver.findElements(storedCards);
	}

	public WebElement getZeroPaymentMessage() {
		return driver.findElement(zeroPaymentMessage);
	}

	public WebElement getCustomAmountInput() {
		return driver.findElement(customAmountInput);
	}

	public WebElement getPaymentAmountInput() {
		return driver.findElement(paymentAmountInput);
	}

	public WebElement getNameOnCard() {
		return driver.findElement(nameOnCard);
	}

	public WebElement getCardNumber() {
		return driver.findElement(cardNumber);
	}

	public WebElement getExpireMonth() {
		return driver.findElement(expireMonth);
	}

	public WebElement getExpireYear() {
		return driver.findElement(expireYear);
	}

	public WebElement getCVC() {
		return driver.findElement(cvc);
	}

	public WebElement getSaveCardNoRadio() {
		return driver.findElement(saveCardNoRadio);
	}

	public WebElement getSaveCardYesRadio() {
		return driver.findElement(saveCardYesRadio);
	}

	public WebElement getHouseAcctNoRadioButton() {
		return driver.findElement(houseAcctNoRadio);
	}

	public WebElement getHouseAcctYesRadio() {
		return driver.findElement(houseAcctYesRadio);
	}

	public WebElement getInClubPurchaseNoRadio() {
		return driver.findElement(inClubPurchaseNoRadio);
	}

	public WebElement getInClubPurchaseYesRadio() {
		return driver.findElement(inClubPurchaseYesRadio);
	}

	public WebElement getSignaturePad() {
		return driver.findElement(signaturePad);
	}

	public WebElement getSubmitButton() {
		return driver.findElement(submitButton);
	}

	public WebElement getIAgreeCheckbox() {
		return driver.findElement(iAgreeCheckbox);
	}

	public WebElement getPopupPayButton() {
		return driver.findElement(popupPayButton);
	}

	public WebElement getPopupConfirmation1() {
		return driver.findElement(popupConfirmation1);
	}

	public WebElement getPopupConfirmationButton() {
		return driver.findElement(popupConfirmationButton);
	}

	public WebElement getPopupText() {
		return driver.findElement(popupText);
	}

	public WebElement getSelectPaymentOnAccountButton() {
		return driver.findElement(selectPaymentOnAccountButton);
	}

	public WebElement getSelectPaymentStoredCardsButton() {
		return driver.findElement(selectPaymentStoredCardsButton);
	}

	public WebElement getSelectPaymentNewCardButton() {
		return driver.findElement(selectPaymentNewCardButton);
	}

	public WebElement getSelectPaymentOnAccountPayWithButton() {
		return driver.findElement(selectPaymentOnAccountPayWithButton);
	}

	public WebElement getConfirmPageThankYou() {
		return driver.findElement(confirmPageThankYou);
	}

	public WebElement getPopupContent() {
		return driver.findElement(popupContent);
	}

	public List<WebElement> getCheckboxes() {
		return driver.findElements(checkboxes);
	}

	public WebElement getLinkAgreementsHeader() {
		return driver.findElement(linkAgreementsHeader);
	}

	public WebElement getLabelText() {
		return driver.findElement(labelText);
	}

	public WebElement getLabelText1() {
		return driver.findElement(labelText1);
	}

	public WebElement getFirstAgreement() {
		return driver.findElement(firstAgreement);
	}

	public List<WebElement> getAgreementCheckBox() {
		return driver.findElements(agreementCheckBox);
	}

	public List<WebElement> getAgreementLabel() {
		return driver.findElements(agreementLabel);
	}

	public WebElement getNoThanks() {
		return driver.findElement(noThanks);
	}

	public WebElement getAreYouSure() {
		return driver.findElement(areYouSure);
	}

	public WebElement getSlideDownBox() {
		return driver.findElement(slideDownBox);
	}

	public List<WebElement> getAdditionalQuestionsSection() {
		return driver.findElements(additionalQuestionsSection);
	}

	public WebElement getSaveCardQuestion() {
		return driver.findElement(saveCardQuestion);
	}

	public WebElement getOnAccountCardQuestion() {
		return driver.findElement(onAccountCardQuestion);
	}

	public WebElement getOnAccountBankQuestion() {
		return driver.findElement(onAccountBankQuestion);
	}

	public WebElement getInClubQuestion() {
		return driver.findElement(inClubQuestion);
	}

	public WebElement getMoreInfoSaveCard() {
		return driver.findElement(moreInfoSaveCard);
	}

	public List<WebElement> getMoreInfoOnAccount() {
		return driver.findElements(moreInfoOnAccount);
	}

	public WebElement getMoreInfoUseInPos() {
		return driver.findElement(moreInfoUseInPos);
	}

	public WebElement getAdditionalQuestionPopupTitle() {
		return driver.findElement(additionalQuestionPopupTitle);
	}

	public WebElement getAdditionalQuestionPopupClose() {
		return driver.findElement(additionalQuestionPopupClose);
	}

}
