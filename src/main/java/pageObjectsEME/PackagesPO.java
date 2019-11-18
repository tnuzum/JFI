package pageObjectsEME;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PackagesPO {

	public static WebDriver driver;

// OBJECTS
		
	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	
// CONSTRUCTOR
		
	public PackagesPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		PackagesPO.driver = driver;
	}
// METHODS

	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
	}
	
}
