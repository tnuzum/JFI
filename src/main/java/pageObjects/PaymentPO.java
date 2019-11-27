package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaymentPO {

	public static WebDriver driver;

// This is the page for the link in the left pane under My Account labels Pay Balance
	
// OBJECTS
		
//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By amountDueLabel = By.xpath("//enterpaymentamount/div/div/div/div/div/h2");
	By amountRadioButton1 = By.xpath("(//div[@class='radio'])[1]/input");
	By amountLabel1	= By.xpath("(//div[@class='radio'])[1]/label");
	By amountRadioButton2 = By.xpath("(//div[@class='radio'])[2]/input");
	By amountLabel2	= By.xpath("(//div[@class='radio'])[2]/label");
//	By amountRadioButton3 = By.xpath("(//div[@class='radio'])[3]/input");
	By amountRadioButton3 = By.xpath("//input[@id='radio3']");
	By amountLabel3	= By.xpath("(//div[@class='radio'])[3]/label");
	By payWithThisMethodButton1 = By.xpath("//*[text()='pay with this method']");
	By zeroPaymentMessage	= By.xpath("//div[@id='swal2-content']");
	By customAmountInput	= By.xpath("//input[@name='selectedPaymentAmount']");
	By paymentAmountInput	= By.xpath("//input[@name='payment']");
	By nameOnCard = By.xpath("//input[@name='nameCard']");
	By cardNumber = By.xpath("//input[@name='cardNumber']");
	By expireMonth = By.xpath("//input[@name='ExpireMonth']");
	By expireYear = By.xpath("//input[@name='ExpireYear']");
	By cvc = By.xpath("//input[@name='CVC']");
	By saveCardNoRadio = By.xpath("//input[@id='sc-no']");
	By saveCardYesRadio = By.xpath("//input[@id='sc-yes']");
	By houseAcctNoRadio = By.xpath("//input[@id='ac-no']");
	By houseAcctYesRadio = By.xpath("//input[@id='ac-yes']");
	By inClubPurchaseNoRadio = By.xpath("//input[@id='ic-no']");
	By inClubPurchaseYesRadio = By.xpath("//input[@id='ic-yes']");
	By iAgreeCheckbox = By.xpath("(//input[@type='checkbox'])[2]");
	By submitButton = By.xpath("//button[@type='submit']");
	By popupPayButton = By.xpath("//div[@class='swal2-actions']/button[1]");
	By popupConfirmation1 = By.xpath("//div[@class='swal2-header']/h2");//displays Payment Made! message
	By popupConfirmationButton = By.xpath("//div[@class='swal2-actions']/button[1]");
	By confirmPageThankYou = By.xpath("//h1[@class='text-capitalize']");
	By selectPaymentOnAccountButton = By.xpath("//div[@id='accordion']/div[1]/div[1]/a/h5");
//	By selectPaymentOnAccountPayWithButton = By.xpath("//button[@class='btn btn-primary btn-sm use-method make-payment text-capitalize']");
	By selectPaymentOnAccountPayWithButton = By.xpath("//div[@id = 'collapseOne']/cartpaymentonaccount/div/div[2]/div/button");
	By selectPaymentStoredCardsButton = By.xpath("//div[@id='accordion']/div[2]/div[1]/a/h5");
	By selectPaymentNewCardButton = By.xpath("//div[@id='accordion']/div[3]/div[1]/a/h5");
		
// CONSTRUCTOR
		
	public PaymentPO(WebDriver driver) {
		PaymentPO.driver = driver;
	}
// METHODS

	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
	}
	public WebElement getAmountDueLabel()
	{
		return driver.findElement(amountDueLabel);
	}
	public WebElement getAmountRadioButton1()
	{
		return driver.findElement(amountRadioButton1);
	}
	public WebElement getAmountLabel1()
	{
		return driver.findElement(amountLabel1);
	}
	public WebElement getAmountRadioButton2()
	{
		return driver.findElement(amountRadioButton2);
	}
	public WebElement getAmountLabel2()
	{
		return driver.findElement(amountLabel2);
	}
	public WebElement getAmountRadioButton3()
	{
		return driver.findElement(amountRadioButton3);
	}
	public WebElement getAmountLabel3()
	{
		return driver.findElement(amountLabel3);
	}
	public WebElement getPayWithThisMethodButton1()
	{
		return driver.findElement(payWithThisMethodButton1);
	}
	public WebElement getZeroPaymentMessage()
	{
		return driver.findElement(zeroPaymentMessage);
	}
	public WebElement getCustomAmountInput()
	{
		return driver.findElement(customAmountInput);
	}
	public WebElement getPaymentAmountInput()
	{
		return driver.findElement(paymentAmountInput);
	}
	public WebElement getNameOnCard()
	{
		return driver.findElement(nameOnCard);
	}
	public WebElement getCardNumber()
	{
		return driver.findElement(cardNumber);
	}
	public WebElement getExpireMonth()
	{
		return driver.findElement(expireMonth);
	}
	public WebElement getExpireYear()
	{
		return driver.findElement(expireYear);
	}
	public WebElement getCVC()
	{
		return driver.findElement(cvc);
	}
	public WebElement getSaveCardNoRadio()
	{
		return driver.findElement(saveCardNoRadio);
	}
	public WebElement getSaveCardYesRadio()
	{
		return driver.findElement(saveCardYesRadio);
	}
	public WebElement getHouseAcctNoRadioButton()
	{
		return driver.findElement(houseAcctNoRadio);
	}
	public WebElement getHouseAcctYesRadio()
	{
		return driver.findElement(houseAcctYesRadio);
	}
	public WebElement getInClubPurchaseNoRadio()
	{
		return driver.findElement(inClubPurchaseNoRadio);
	}
	public WebElement getInClubPurchaseYesRadio()
	{
		return driver.findElement(inClubPurchaseYesRadio);
	}
	public WebElement getSubmitButton()
	{
		return driver.findElement(submitButton);
	}
	public WebElement getIAgreeCheckbox()
	{
		return driver.findElement(iAgreeCheckbox);
	}
	
	public WebElement getPopupPayButton()
	{
		return driver.findElement(popupPayButton);
	}
	public WebElement getPopupConfirmation1()
	{
		return driver.findElement(popupConfirmation1);
	}
	public WebElement getPopupConfirmationButton()
	{
		return driver.findElement(popupConfirmationButton);
	}
	public WebElement getSelectPaymentOnAccountButton()
	{
		return driver.findElement(selectPaymentOnAccountButton);
	}
	public WebElement getSelectPaymentStoredCardsButton()
	{
		return driver.findElement(selectPaymentStoredCardsButton);
	}
	public WebElement getSelectPaymentNewCardButton()
	{
		return driver.findElement(selectPaymentNewCardButton);
	}
	public WebElement getSelectPaymentOnAccountPayWithButton()
	{
		return driver.findElement(selectPaymentOnAccountPayWithButton);
	}
	public WebElement getConfirmPageThankYou()
	{
		return driver.findElement(confirmPageThankYou);
	}
	
	
	
}
