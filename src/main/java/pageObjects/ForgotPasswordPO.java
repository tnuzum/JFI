package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ForgotPasswordPO {

	public WebDriver driver;

// OBJECTS
		
	By pageHeader = By.xpath("//div[@id='loginForm']/form/h2");// displays "Forgot your Username?"
	
// CONSTRUCTOR
		
	public ForgotPasswordPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
	}
	
}
