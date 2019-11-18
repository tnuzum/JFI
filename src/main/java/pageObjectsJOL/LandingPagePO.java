package pageObjectsJOL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LandingPagePO {

	public static WebDriver driver;

// OBJECTS
		
	By pageHeader = By.xpath("//a[@href='/CompeteJOL/236']");
	By filterByLocationLabel = By.xpath("//div[@class='selectCity contentCenterFix']");
	By selectCityDropdown = By.id("ddlFilter");
	By club1Name = By.xpath("//div[@id='clublist-result']/div[1]/div[1]/div[1]/div[1]/div/h2");
	By club1Location = By.xpath("//div[@id='clublist-result']/div[1]/div[1]/div[1]/div[2]/div/h2");
	By club1AddressTitle = By.xpath("//div[@id='clublist-result']/div[1]/div[1]/div[2]/div[2]/div/h3");
	By club1Address = By.xpath("//div[@id='clublist-result']/div[1]/div[1]/div[2]/div[2]/div");
	
	By club2Name = By.xpath("//div[@id='clublist-result']/div[2]/div[1]/div[1]/div[1]/div/h2");
	By club2Location = By.xpath("//div[@id='clublist-result']/div[2]/div[1]/div[1]/div[2]/div/h2");
	By club2AddressTitle = By.xpath("//div[@id='clublist-result']/div[2]/div[1]/div[2]/div[2]/div/h3");
	By club2Address = By.xpath("//div[@id='clublist-result']/div[2]/div[1]/div[2]/div[2]/div");
	
// CONSTRUCTOR
		
	public LandingPagePO(WebDriver driver) {
		LandingPagePO.driver = driver;
	}
// METHODS

	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
	}
	public WebElement getFilterByLocationLabel()
	{
		return driver.findElement(filterByLocationLabel);
	}
	public WebElement getSelectCityDropdown()
	{
		return driver.findElement(selectCityDropdown);
	}
	public WebElement getClub1Name()
	{
		return driver.findElement(club1Name);
	}
	public WebElement getClub2Name()
	{
		return driver.findElement(club2Name);
	}
	public WebElement getClub1Location()
	{
		return driver.findElement(club1Location);
	}
	public WebElement getClub2Location()
	{
		return driver.findElement(club2Location);
	}
	public WebElement getClub1AddressTitle()
	{
		return driver.findElement(club1AddressTitle);
	}
	public WebElement getClub2AddressTitle()
	{
		return driver.findElement(club2AddressTitle);
	}
	public WebElement getClub1Address()
	{
		return driver.findElement(club1Address);
	}
	public WebElement getClub2Address()
	{
		return driver.findElement(club2Address);
	}
}
