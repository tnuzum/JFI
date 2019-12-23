package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaymentMethodsPO {

	public static WebDriver driver;

// This is the page for the link in the left pane under My Account labels Pay Balance
	
// OBJECTS
		
	By onAccountAndSavedCards = By.xpath("//div[@class='radio no-padding']");
	By paymentButton = By.xpath("//button[@type = 'submit']");
	By newCardButton = By.xpath("//span[contains( text(), 'Add New Card')]");
	By closeButton = By.xpath("//button[@id='close-button']");
	By nameOnCardField = By.xpath("//input[@id='nameOnCard']");
	By cardNumberField = By.xpath("//input[@id='cardNumber']");
	By expirationMonth = By.xpath("//input[@name='ExpireMonth']");
	By expirationYear = By.xpath("//input[@name='ExpireYear']");
	By securityCode = By.xpath("//input[@id='securityCode']");
	By saveCardNo = By.xpath("//label[@for = 'sc-no']");
		
// CONSTRUCTOR
		
	public PaymentMethodsPO(WebDriver driver) {
		PaymentMethodsPO.driver = driver;
	}
// METHODS

	public WebElement getOnAccountAndSavedCards()
	{
		return driver.findElement(onAccountAndSavedCards);
	}

	public WebElement getPaymentButton()
	{
		return driver.findElement(paymentButton);
	}
	public WebElement getNewCardButton()
	{
		return driver.findElement(newCardButton);
	}
	public WebElement getCloseButton()
	{
		return driver.findElement(closeButton);
	}
	public WebElement getNameOnCardField()
	{
		return driver.findElement(nameOnCardField);
	}
	public WebElement getCardNumberField()
	{
		return driver.findElement(cardNumberField);
	}
	public WebElement getExpirationMonth()
	{
		return driver.findElement(expirationMonth);
	}
	public WebElement getExpirationYear()
	{
		return driver.findElement(expirationYear);
	}
	public WebElement getSecurityCode()
	{
		return driver.findElement(securityCode);
	}
	public WebElement getSaveCardNo()
	{
		return driver.findElement(saveCardNo);
	}
	
}