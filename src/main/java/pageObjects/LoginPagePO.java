package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPagePO {

	public WebDriver driver;

// OBJECTS
		
	By userEmail = By.id("Username");
	By userPassword = By.id("Password");
	By signinButton = By.cssSelector("button[type='submit']");
	By credentialsErrorMessage = By.xpath("//div[@id='loginForm']/form/div[1]/ul/li");
	By usernameRequiredMessage = By.xpath("//span[@id='Username-error']");
	By passwordRequiredMessage = By.xpath("//span[@id='Password-error']");
	By ForgotUsername = By.xpath("//a[@onclick='ForgotUsername()']");
	By ForgotPassword = By.xpath("//a[@onclick='ForgotPassword()']");
	
	
// CONSTRUCTOR
		
	public LoginPagePO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getuserEmail()
	{
		return driver.findElement(userEmail);
	}
	public WebElement getuserPassword()
	{
		return driver.findElement(userPassword);
	}
	public WebElement getsigninButton()
	{
		return driver.findElement(signinButton);
	}
	public WebElement getcredentialsErrorMessage()
	{
		return driver.findElement(credentialsErrorMessage);
	}
	public WebElement getusernameRequiredMessage()
	{
		return driver.findElement(usernameRequiredMessage);
	}
	public WebElement getpasswordRequiredMessage()
	{
		return driver.findElement(passwordRequiredMessage);
	}
	public WebElement getForgotUsername()
	{
		return driver.findElement(ForgotUsername);
		
	}
	public WebElement getForgotPassword()
	{
		return driver.findElement(ForgotPassword);
		
	}
}
