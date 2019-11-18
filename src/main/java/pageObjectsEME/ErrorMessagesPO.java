package pageObjectsEME;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ErrorMessagesPO {
	public static WebDriver driver;

	// OBJECTS
			
		By xMark = By.xpath("//span[@class='swal2-x-mark']");
//		By errorMessage = By.xpath("//h2[@id='swal2-title']");
		By errorMessage = By.xpath("//*[text()='An Error Has Occurred']");
		
		By tryAgainMessage = By.xpath("//div[@id='swal2-content']");
		By okButton = By.xpath("(//button[@type='button'])[4]");
		
		
	// CONSTRUCTOR
			
		public ErrorMessagesPO(WebDriver driver) {
			ErrorMessagesPO.driver = driver;
		}
	// METHODS

		public WebElement getxMark()
		{
			return driver.findElement(xMark);
		}
		public WebElement getErrorMessage()
		{
			return driver.findElement(errorMessage);
		}
		public WebElement getTryAgainMessage()
		{
			return driver.findElement(tryAgainMessage);
		}
		public WebElement getOKButton()
		{
			return driver.findElement(okButton);
		}
}
