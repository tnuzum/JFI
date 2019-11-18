package pageObjectsEME;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPO {

	public static WebDriver driver;

// OBJECTS
		
	By userName = By.id("Username"); //text input field
	By userPassword = By.id("Password"); //text input field
	By userNameLabel = By.xpath("//label[@for='Username']");
	By userPasswordLabel = By.xpath("//label[@for='Password']");
	By loginButton = By.cssSelector("button[type='submit']");
	By credentialsErrorMessage = By.xpath("//div[@id='loginForm']/form/div[1]/ul/li");
	By usernameRequiredMessage = By.xpath("//span[@id='Username-error']");
	By passwordRequiredMessage = By.xpath("//span[@id='Password-error']");
	By ForgotUsername = By.xpath("//a[@onclick='ForgotUsername()']"); //link
	By ForgotPassword = By.xpath("//a[@onclick='ForgotPassword()']"); //link
	By RememberUsernameCheckbox = By.xpath("//input[@name='RememberUsername']");
	By RememberUsernameLabel = By.xpath("//label[@for='RememberUsername']"); 
	
	
// CONSTRUCTOR
		
	public LoginPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		LoginPO.driver = driver;
	}
// METHODS

	public WebElement getuserName()
	{
		return driver.findElement(userName);
	}
	public WebElement getuserPassword()
	{
		return driver.findElement(userPassword);
	}
	public WebElement getLoginButton()
	{
		return driver.findElement(loginButton);
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
	public WebElement getuserNameLabel()
	{
		return driver.findElement(userNameLabel);
	}
	public WebElement getuserPasswordLabel()
	{
		return driver.findElement(userPasswordLabel);
	}
	public WebElement getRememberUsernameLabel()
	{
		return driver.findElement(RememberUsernameLabel);
	}
	public WebElement getRememberUsernameCheckbox()
	{
		return driver.findElement(RememberUsernameCheckbox);
	}
}
