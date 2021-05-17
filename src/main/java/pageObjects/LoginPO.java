package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPO {

	WebDriver driver;

// OBJECTS

	By userName = By.name("username"); // text input field
	By userPassword = By.name("password"); // text input field
	By userNameLabel = By.xpath("//label[@for='username']");
	By userPasswordLabel = By.xpath("//label[@for='password']");
	By loginButton = By.cssSelector("button[type='submit']");
//	By loginButton = By.xpath("//button[contains(text(), 'Login')]");
	By credentialsErrorMessage = By.tagName("li");
	By usernameRequiredMessage = By.xpath("//span[@id='Username-error']");
	By passwordRequiredMessage = By.xpath("//span[@id='Password-error']");
	By ForgotUsername = By.xpath("//a[@href = '/forgotusername']"); // link
	By ForgotPassword = By.xpath("//a[@href='/forgotpassword']"); // link
	By RememberUsernameCheckbox = By.xpath("//input[contains(@class, 'at-login-remember-username')]");
	By RememberUsernameLabel = By.xpath("//div[contains(@class, 'checkbox')]//label");
	By dobMembers = By.xpath("//div[@class = 'col-10']");
	By dobInputFields = By.xpath("//div[@class = 'col-10'] // input");
	By continueButton = By.xpath("//button[contains(text(),'Continue')]");
// CONSTRUCTOR

	public LoginPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getuserName() {
		return driver.findElement(userName);
	}

	public WebElement getuserPassword() {
		return driver.findElement(userPassword);
	}

	public WebElement getLoginButton() {
		return driver.findElement(loginButton);
	}

	public WebElement getcredentialsErrorMessage() {
		return driver.findElement(credentialsErrorMessage);
	}

	public WebElement getusernameRequiredMessage() {
		return driver.findElement(usernameRequiredMessage);
	}

	public WebElement getpasswordRequiredMessage() {
		return driver.findElement(passwordRequiredMessage);
	}

	public WebElement getForgotUsername() {
		return driver.findElement(ForgotUsername);
	}

	public WebElement getForgotPassword() {
		return driver.findElement(ForgotPassword);
	}

	public WebElement getuserNameLabel() {
		return driver.findElement(userNameLabel);
	}

	public WebElement getuserPasswordLabel() {
		return driver.findElement(userPasswordLabel);
	}

	public WebElement getRememberUsernameLabel() {
		return driver.findElement(RememberUsernameLabel);
	}

	public WebElement getRememberUsernameCheckbox() {
		return driver.findElement(RememberUsernameCheckbox);
	}

	public List<WebElement> getDobMembers() {
		return driver.findElements(dobMembers);
	}

	public List<WebElement> getDobInputFields() {
		return driver.findElements(dobInputFields);
	}

	public WebElement getContinueButton() {
		return driver.findElement(continueButton);
	}

}
