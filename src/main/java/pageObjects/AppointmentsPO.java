package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AppointmentsPO {

	public WebDriver driver;

// OBJECTS
		
	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	
// CONSTRUCTOR
		
	public AppointmentsPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
	}
	
}
