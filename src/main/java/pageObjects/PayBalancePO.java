package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PayBalancePO {

	public WebDriver driver;

// OBJECTS
		
	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	
	By amountRadioButton1 = By.xpath("(//div[@class='radio'])[1]/input");
	By amountLabel1	= By.xpath("(//div[@class='radio'])[1]/label");
	By amountRadioButton2 = By.xpath("(//div[@class='radio'])[2]/input");
	By amountLabel2	= By.xpath("(//div[@class='radio'])[2]/label");
	By amountRadioButton3 = By.xpath("(//div[@class='radio'])[3]/input");
	By amountLabel3	= By.xpath("(//div[@class='radio'])[3]/label");
	By payWithThisMethodButton1 = By.xpath("(//div[@class='payment-card'])[1]/div[3]/div/button");
	By zeroPaymentMessage	= By.xpath("//div[@id='swal2-content']");
	By customAmountInput	= By.xpath("//input[@name='selectedPaymentAmount']");
	By popupPayButton = By.xpath("//div[@class='swal2-actions']/button[1]");
	By popupConfirmation1 = By.xpath("//div[@class='swal2-header']/h2");//displays Payment Made!
	By popupConfirmationButton = By.xpath("//div[@class='swal2-actions']/button[1]");
		
// CONSTRUCTOR
		
	public PayBalancePO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
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
	
}
