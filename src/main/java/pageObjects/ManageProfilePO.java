package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ManageProfilePO {

	public static WebDriver driver;

// OBJECTS
		
	By pageHeader 			= By.xpath("//div[@class='col-sm-12']/h2");
	By question 			= By.xpath("//div[@class='ibox']/div[1]/h5");
	By usernameButton		= By.xpath("//div[@class='ibox']/div[2]/div/div/div[1]/div[1]/h5/a");
	By passwordButton		= By.xpath("//div[@class='ibox']/div[2]/div/div/div[2]/div/h4/a");
	By generalInfoButton 	= By.xpath("//div[@class='ibox']/div[2]/div/div/div[3]/div/h4/a");
	By groupActivityOptionsButton = By.xpath("//div[@class='ibox']/div[2]/div/div/div[4]/div/h4/a");
	By interestsButton			= By.xpath("//div[@class='ibox']/div[2]/div/div/div[5]/div/h4/a");	
	By currentUsernameLabel		= By.xpath("//div[@id='collapseOne']/editusernamecomponent/div/form/div[1]/label");
//** i'm having trouble locating labels for New Username and Confirm New Username **
	By usernameInput		= By.xpath("//div[@id='collapseOne']/editusernamecomponent/div/form/div[1]/input");
	By newUsernameInput		= By.xpath("//div[@id='collapseOne']/editusernamecomponent/div/form/div[2]/div[1]/input");
	By confirmNewUsernameInput	= By.xpath("//div[@id='collapseOne']/editusernamecomponent/div/form/div[2]/div[2]/input");
	
	
	
// CONSTRUCTOR
		
	public ManageProfilePO(WebDriver driver) {
		ManageProfilePO.driver = driver;
	}
// METHODS

	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
	}
	public WebElement getQuestion()
	{
		return driver.findElement(question);
	}	
	public WebElement getUsernameButton()
	{
		return driver.findElement(usernameButton);
	}
	public WebElement getPasswordButton()
	{
		return driver.findElement(passwordButton);
	}
	public WebElement getGeneralInfoButton()
	{
		return driver.findElement(generalInfoButton);
	}
	public WebElement getGroupActivityOptionsButton()
	{
		return driver.findElement(groupActivityOptionsButton);
	}
	public WebElement getinterestsButton()
	{
		return driver.findElement(interestsButton);
	}
	public WebElement getCurrentUsernameLabel()
	{
		return driver.findElement(currentUsernameLabel);
	}
	public WebElement getUsernameInput()
	{
		return driver.findElement(usernameInput);
	}
	public WebElement getNewUsernameInput()
	{
		return driver.findElement(newUsernameInput);
	}
	public WebElement getConfirmNewUsernameInput()
	{
		return driver.findElement(confirmNewUsernameInput);
	}
}
