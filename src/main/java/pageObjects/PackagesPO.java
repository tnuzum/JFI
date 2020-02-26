package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PackagesPO {

	public static WebDriver driver;

// OBJECTS
		
//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By memberSections = By.xpath("//div[contains(@class, 'row-box')]");
	
	
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
	
	public List<WebElement> getMemberSections()
	{
		return driver.findElements(memberSections);
	}
	
}
