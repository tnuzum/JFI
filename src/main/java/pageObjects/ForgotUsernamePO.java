package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ForgotUsernamePO {

	public WebDriver driver;

// OBJECTS
		
	By pageHeader 				= By.xpath("//div[@id='loginForm']/form/h2");// displays "Forgot your Username?"
	By pageDescription 			= By.xpath("//div[@id='loginForm']/form/p");
	By cancelButton 			= By.xpath("//div[@id='loginForm']/form/div/div/div/a");
	By recoverUsernameButton 	= By.xpath("//div[@id='loginForm']/form/div/div/div[5]/input");//use .getAttribute("value") to get text
	By firstNameLabel			= By.xpath("//div[@id='loginForm']/form/div/div/label[1]");
	By firstNameInputbox		= By.xpath("//div[@id='loginForm']/form/div/div/div[1]/input");
	By firstNameRequiredMessage	= By.xpath("//div[@id='loginForm']/form/div/div/div[1]/span/span");
	By passwordLabel			= By.xpath("//div[@id='loginForm']/form/div/div/label[2]");
	By passwordInputbox			= By.xpath("//div[@id='loginForm']/form/div/div/div[2]/input");
	By passwordRequiredMessage	= By.xpath("//div[@id='loginForm']/form/div/div/div[2]/span/span");
	By phoneNumberLabel			= By.xpath("//div[@id='loginForm']/form/div/div/label[3]");
	By phoneNumberInputbox		= By.xpath("//div[@id='loginForm']/form/div/div/div[3]/input");
	By phoneNumberRequiredMessage = By.xpath("//div[@id='loginForm']/form/div/div/div[3]/span/span");
	By emailLabel				= By.xpath("//div[@id='loginForm']/form/div/div/label[4]");
	By emailInputbox			= By.xpath("//div[@id='loginForm']/form/div/div/div[4]/input");
	By emailRequiredMessage		= By.xpath("//div[@id='loginForm']/form/div/div/div[4]/span/span");
	
	
// CONSTRUCTOR
		
	public ForgotUsernamePO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
	}
	public WebElement getPageDescription()
	{
		return driver.findElement(pageDescription);
	}
	public WebElement getCancelButton()
	{
		return driver.findElement(cancelButton);
	}
	public WebElement getRecoverUsernameButton()
	{
		return driver.findElement(recoverUsernameButton);
	}
	
	public WebElement getFirstNameLabel()
	{
		return driver.findElement(firstNameLabel);
	}
	public WebElement getFirstNameInputbox()
	{
		return driver.findElement(firstNameInputbox);
	}
	public WebElement getFirstNameRequiredMessage()
	{
		return driver.findElement(firstNameRequiredMessage);
	}
	public WebElement getPasswordLabel()
	{
		return driver.findElement(passwordLabel);
	}
	public WebElement getPasswordInputbox()
	{
		return driver.findElement(passwordInputbox);
	}
	public WebElement getPasswordRequiredMessage()
	{
		return driver.findElement(passwordRequiredMessage);
	}
	public WebElement getPhoneNumberLabel()
	{
		return driver.findElement(phoneNumberLabel);
	}
	public WebElement getPhoneNumberInputbox()
	{
		return driver.findElement(phoneNumberInputbox);
	}
	public WebElement getPhoneNumberRequiredMessage()
	{
		return driver.findElement(phoneNumberRequiredMessage);
	}
	public WebElement getEmailLabel()
	{
		return driver.findElement(emailLabel);
	}
	public WebElement getEmailInputbox()
	{
		return driver.findElement(emailInputbox);
	}
	public WebElement getEmailRequiredMessage()
	{
		return driver.findElement(emailRequiredMessage);
	}

	
}
