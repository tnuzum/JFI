package pageObjectsJOL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SelectPlanPagePO {

	public static WebDriver driver;

// OBJECTS
		
	By selectClubLink = By.xpath("//a[@href='/CompeteJOL/236']");
		
	
// CONSTRUCTOR
		
	public SelectPlanPagePO(WebDriver driver) {
		SelectPlanPagePO.driver = driver;
	}
// METHODS

	public WebElement getSelectClubLink()
	{
		return driver.findElement(selectClubLink);
	}
	
}
