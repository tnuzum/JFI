package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UnenrollPO {

	public static WebDriver driver;

// OBJECTS
		
	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By unenrollButton = By.xpath("//a[contains(@class,'unenroll')]");//button on Unenroll page
	By unenrollConfirmMessage1 = By.xpath("//h2[@id='swal2-title']");//displays "Unenrolled"
	By unenrollConfirmYesButton = By.xpath("//button[@class='swal2-confirm swal2-styled']");
	By notEligibleMessage = By.xpath("//div[contains(@class,'alert-danger')]"); // returns "We apologize, this class is not eligible for unenrollment."
	By cancelButton = By.xpath("//div[contains(@class,'alert-danger')]/div");

	
// CONSTRUCTOR
		
	public UnenrollPO(WebDriver driver) {
		UnenrollPO.driver = driver;
	}
// METHODS

	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
	}
	public WebElement getUnenrollButton()
	{
		return driver.findElement(unenrollButton);
	}
	public WebElement getUnenrollConfirmMessage1()
	{
		return driver.findElement(unenrollConfirmMessage1);
	}
	public WebElement getUnenrollConfirmYesButton()
	{
		return driver.findElement(unenrollConfirmYesButton);
	}
	public WebElement getNotEligibleMessage()
	{
		return driver.findElement(notEligibleMessage);
	}
	public WebElement getCancelButton()
	{
		return driver.findElement(cancelButton);
	}
	
}
