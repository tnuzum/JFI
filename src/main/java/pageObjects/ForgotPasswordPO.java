package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ForgotPasswordPO {

	WebDriver driver;

// OBJECTS

	By pageHeader = By.xpath("//div[@id='loginForm']/form/h2");// displays "Forgot your Username?"
	By pageDescription = By.xpath("//div[@id='loginForm']/form/p");
	By cancelButton = By.xpath("//div[@id='loginForm']/form/div/div/div/a");
	By resetPasswordButton = By.xpath("//input[@type='submit']");// use .getAttribute("value") to get text
	By userNameLabel = By.xpath("//div[@id='loginForm']/form/div/div/label");
	By userNameInputbox = By.xpath("//div[@id='loginForm']/form/div/div/div[1]/input");
	By userNameRequiredMessage = By.xpath("//div[@id='loginForm']/form/div/div/div[1]/span/span");
	By forgotYourUsernameLink = By.xpath("//div[@id='loginForm']/form/div/div/div/small");

// CONSTRUCTOR

	public ForgotPasswordPO(WebDriver driver) {
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getPageDescription() {
		return driver.findElement(pageDescription);
	}

	public WebElement getCancelButton() {
		return driver.findElement(cancelButton);
	}

	public WebElement getResetPasswordButton() {
		return driver.findElement(resetPasswordButton);
	}

	public WebElement getUserNameLabel() {
		return driver.findElement(userNameLabel);
	}

	public WebElement getUserNameInputbox() {
		return driver.findElement(userNameInputbox);
	}

	public WebElement getUserNameRequiredMessage() {
		return driver.findElement(userNameRequiredMessage);
	}

}
