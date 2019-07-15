package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DashboardPO {

	public WebDriver driver;

// OBJECTS

	By LogoutButton = By.linkText("Log out");
	By memberName = By.xpath("//div[@class='homeComponent']//memberinfo/div/div[2]/h2");

	
// CONSTRUCTOR
		
	public DashboardPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS
	public WebElement getLogoutButton()
	{
		return driver.findElement(LogoutButton);
	}
	public WebElement getMemberName()
	{
		return driver.findElement(memberName);
	}
	
}
