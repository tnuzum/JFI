package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ManagePayMethodsPO {

	WebDriver driver;

// OBJECTS

//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By noSavedCardMessage = By.xpath("//h1[@class='text-capitalize']");
	By bankAccountLink = By.xpath("//a[@href = '#collapseOne'] //i");
	By managePMBreadcrumb = By.xpath("//a[contains(text(),'Manage Payment Methods')]");
	By nameOnCard = By.xpath("//input[contains(@class, 'at-managepayments-addcc-input-nameoncard')]");
	By cardNumber = By.xpath("//input[contains(@class, 'at-managepayments-addcc-input-cardnum')]");
	By expireMonth = By.xpath("//input[contains(@class, 'at-managepayments-addcc-input-exp-month')]");
	By expireYear = By.xpath("//input[contains(@class, 'at-managepayments-addcc-input-exp-year')]");
	By houseAcctNoRadio = By.xpath("//input[@id='ac-no']");
	By houseAcctYesRadio = By.xpath("//input[@id='ac-yes']");
	By moreInfoOnAccount = By.xpath("//div[contains(@class, ' at-additionalquestions-moreinfoonaccount')]/a");
	By inClubPurchaseNoRadio = By.xpath("//input[@id='ic-no']");
	By inClubPurchaseYesRadio = By.xpath("//input[@id='ic-yes']");
	By moreInfoUseInPos = By.xpath("//div[contains(@class, ' at-additionalquestions-moreinfouseinpos')]/a");
	By iAgreeCheckbox = By.xpath("//input[contains(@class, 'at-managepayments-addcc-checkbox-tandc')]");
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
	By noThanksLabel = By.xpath("//label[contains(text(),'No Thanks')]");
	By areYouSure = By.xpath("//input[contains(@class,'at-foplinkagreement-agreement-areyousure')]");
	By addCCButton = By.xpath("//button[contains(@class, 'at-managepayments-addcc-button-addcard')]");
	By popupConfirmation1 = By.xpath("//div[@class='swal2-header']/h2");// displays Payment Made! message
	By popupConfirmationButton = By.xpath("//div[@class='swal2-actions']/button[1]");
	By popupText = By.xpath("//h2[@id ='swal2-title']");
	By slideDownBox = By.xpath("//div[@class='rate-box']");
	By agreementDiv = By.xpath("//div[contains(@class, 'm-l-lg')]/preceding-sibling::div");
	By agreementInfoDiv = By.xpath("//div[contains(@class, 'm-l-lg')]");

	By accountHolder = By.xpath("//input[contains(@class, 'at-managepayments-addbankaccount-input-accountholder')]");
	By usBankRadio = By.xpath("//input[contains(@class, 'at-managepayments-addbankaccount-radio-usbank')]");
	By canadianBankRadio = By.xpath("//input[contains(@class, 'at-managepayments-addbankaccount-radio-canadian')]");
	By usRoutingNumber = By.xpath("//input[contains(@class, 'at-managepayments-addbankaccount-input-usrouting')]");
	By canadaRoutingOne = By.xpath("//input[contains(@class, 'at-managepayments-addbankaccount-input-canroutingone')]");
	By canadaRoutingTwo = By.xpath("//input[contains(@class, 'at-managepayments-addbankaccount-input-canroutingtwo')]");
	By usAccountNumber = By.xpath("//input[contains(@class, 'at-managepayments-addbankaccount-input-usacct')]");
	By canadaAccountNumber = By.xpath("//input[contains(@class, 'at-managepayments-addbankaccount-input-canacct')]");
	By checkingRadio = By.xpath("//input[@id='at-checking']");
	By savingsRadio = By.xpath("//input[@id='at-savings']");
	By addBankAcctButton = By.xpath("//button[contains(@class, 'at-managepayments-addbankaccount-button-addbank')]");
	By iAgreeCheckboxACH = By.xpath("//input[contains(@class, 'at-managepayments-addbankaccount-checkbox-tandc')]");

	By cardNumbers = By.xpath("//div[@class='payment-card']  //h2");
	By editPaymentMethodsButton = By.xpath("//a[contains(@class, 'at-managepayments-cardedit-button')]");
	By editAccountHolder = By.xpath("//input[contains(@class, 'at-managepayments-editbankaccount-input-acctholder')]");
	By editCanAccountHolder = By
			.xpath("//input[contains(@class, 'at-managepayments-editbankaccount-input-cancctholder')]");
	By editUSRoutingNumber = By.xpath("//input[contains(@class, 'at-managepayments-editbankaccount-radio-usrouting')]");
	By editCanadaRoutingOne = By
			.xpath("//input[contains(@class, 'at-managepayments-editbankaccount-input-canroutingone')]");
	By editCanadaRoutingTwo = By
			.xpath("//input[contains(@class, 'at-managepayments-editbankaccount-input-canroutingtwo')]");
	By editCanadianBankRadio = By.xpath("//input[contains(@class, 'at-managepayments-editbankaccount-radio-canbank')]");
	By editCheckingRadio = By.xpath("//input[contains(@class, 'at-managepayments-editbankaccount-radio-uschecking')]");
	By editSavingsRadio = By.xpath("//input[contains(@class, 'at-managepayments-editbankaccount-radio-ussavings')]");
	By editCheckingRadio1 = By
			.xpath("//input[contains(@class, 'at-managepayments-editbankaccount-radio-canchecking')]");
	By editSavingsRadio1 = By.xpath("//input[contains(@class, 'at-managepayments-editbankaccount-radio-cansavings')]");
	By saveChangeButton = By.xpath("//button[contains(@class, 'at-managepayments-editbankaccount-button-save')]");
	By iAgreeCheckboxEditACH = By
			.xpath("//input[contains(@class, 'at-managepayments-editbankaccount-checkbox-tandc')]");
	By editNameOnCard = By.xpath("//input[contains(@class, 'at-managepayments-editcc-input-name')]");
	By editIAgreeCheckbox = By.xpath("//input[contains(@class, 'at-managepayments-editcc-tandc')]");
	By saveChangesButtonCC = By.xpath("//button[contains(@class, 'at-managepayments-editcc-button-save')]");
	By editExpiremonth = By.xpath("//input[contains(@class, 'at-managepayments-editcc-input-expmonth')]");
	By editExpireyear = By.xpath("//input[contains(@class, 'at-managepayments-editcc-input-expyear')]");

// CONSTRUCTOR

	public ManagePayMethodsPO(WebDriver driver) {
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getNoSavedCardMessage() {
		return driver.findElement(noSavedCardMessage);
	}

	public WebElement getBankAccountLink() {
		return driver.findElement(bankAccountLink);
	}

	public WebElement getManagePMBreadcrumb() {
		return driver.findElement(managePMBreadcrumb);
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

	public List<WebElement> getHouseAcctNoRadioButton() {
		return driver.findElements(houseAcctNoRadio);
	}

	public List<WebElement> getHouseAcctYesRadio() {
		return driver.findElements(houseAcctYesRadio);
	}

	public WebElement getInClubPurchaseNoRadio() {
		return driver.findElement(inClubPurchaseNoRadio);
	}

	public WebElement getInClubPurchaseYesRadio() {
		return driver.findElement(inClubPurchaseYesRadio);
	}

	public List<WebElement> getSignaturePad() {
		return driver.findElements(signaturePad);
	}

	public WebElement getPopupContent() {
		return driver.findElement(popupContent);
	}

	public List<WebElement> getCheckboxes() {
		return driver.findElements(checkboxes);
	}

	public List<WebElement> getLinkAgreementsHeader() {
		return driver.findElements(linkAgreementsHeader);
	}

	public List<WebElement> getLabelText() {
		return driver.findElements(labelText);
	}

	public List<WebElement> getLabelText1() {
		return driver.findElements(labelText1);
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

	public List<WebElement> getNoThanks() {
		return driver.findElements(noThanks);
	}

	public List<WebElement> getNoThanksLabel() {
		return driver.findElements(noThanksLabel);
	}

	public WebElement getAreYouSure() {
		return driver.findElement(areYouSure);
	}

	public List<WebElement> getMultiAreYouSure() {
		return driver.findElements(areYouSure);
	}

	public WebElement getSlideDownBox() {
		return driver.findElement(slideDownBox);
	}

	public List<WebElement> getMultiSlideDownBox() {
		return driver.findElements(slideDownBox);
	}

	public WebElement getAddCCButton() {
		return driver.findElement(addCCButton);
	}

	public List<WebElement> getMoreInfoOnAccount() {
		return driver.findElements(moreInfoOnAccount);
	}

	public WebElement getMoreInfoUseInPos() {
		return driver.findElement(moreInfoUseInPos);
	}

	public WebElement getIAgreeCheckbox() {
		return driver.findElement(iAgreeCheckbox);
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

	public WebElement getAccountHolder() {
		return driver.findElement(accountHolder);
	}

	public List<WebElement> getAgreementInfoDiv() {
		return driver.findElements(agreementInfoDiv);
	}

	public List<WebElement> getAgreementDiv() {
		return driver.findElements(agreementDiv);
	}

	public WebElement getUSBankRadio() {
		return driver.findElement(usBankRadio);
	}

	public WebElement getCanadianBankRadio() {
		return driver.findElement(canadianBankRadio);
	}

	public WebElement getUSRoutingNumber() {
		return driver.findElement(usRoutingNumber);
	}

	public WebElement getCanadaRoutingOne() {
		return driver.findElement(canadaRoutingOne);
	}

	public WebElement getCanadaRoutingTwo() {
		return driver.findElement(canadaRoutingTwo);
	}

	public WebElement getUSAccountNumber() {
		return driver.findElement(usAccountNumber);
	}

	public WebElement getCanadaAccountNumber() {
		return driver.findElement(canadaAccountNumber);
	}

	public WebElement getCheckingRadio() {
		return driver.findElement(checkingRadio);
	}

	public WebElement getSavingsradio() {
		return driver.findElement(savingsRadio);
	}

	public WebElement getAddBankAcctButton() {
		return driver.findElement(addBankAcctButton);
	}

	public WebElement getIAgreeCheckboxACH() {
		return driver.findElement(iAgreeCheckboxACH);
	}

	public List<WebElement> getCardNumbers() {
		return driver.findElements(cardNumbers);
	}

	public List<WebElement> getEditPaymentMethodsButton() {
		return driver.findElements(editPaymentMethodsButton);
	}

	public WebElement getEditAccountHolder() {
		return driver.findElement(editAccountHolder);
	}

	public WebElement getEditCanAccountHolder() {
		return driver.findElement(editCanAccountHolder);
	}

	public WebElement getEditUSRoutingNumber() {
		return driver.findElement(editUSRoutingNumber);
	}

	public WebElement getEditCanadaRoutingOne() {
		return driver.findElement(editCanadaRoutingOne);
	}

	public WebElement getEditCanadaRoutingTwo() {
		return driver.findElement(editCanadaRoutingTwo);
	}

	public WebElement getEditCanadianBankRadio() {
		return driver.findElement(editCanadianBankRadio);
	}

	public WebElement getEditCheckingRadio() {
		return driver.findElement(editCheckingRadio);
	}

	public WebElement getEditSavingsRadio() {
		return driver.findElement(editSavingsRadio);
	}

	public WebElement getEditCheckingRadio1() {
		return driver.findElement(editCheckingRadio1);
	}

	public WebElement getEditSavingsRadio1() {
		return driver.findElement(editSavingsRadio1);
	}

	public WebElement getSaveChangeButton() {
		return driver.findElement(saveChangeButton);
	}

	public WebElement getIAgreeCheckboxEditACH() {
		return driver.findElement(iAgreeCheckboxEditACH);
	}

	public WebElement getEditNameOnCard() {
		return driver.findElement(editNameOnCard);
	}

	public WebElement getEditIAgreeCheckbox() {
		return driver.findElement(editIAgreeCheckbox);
	}

	public WebElement getSaveChangesButtonCC() {
		return driver.findElement(saveChangesButtonCC);
	}

	public WebElement geteditExpiremonth() {
		return driver.findElement(editExpiremonth);
	}

	public WebElement geteditExpireyear() {
		return driver.findElement(editExpireyear);
	}
}
