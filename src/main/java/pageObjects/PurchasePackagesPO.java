package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PurchasePackagesPO {

	public static WebDriver driver;

// OBJECTS
		
//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By packageName = By.xpath("//div[@class='widget widget-callout']/h2");
	By breadcrumbDashboard = By.xpath("//li[@ng-reflect-klass = 'breadcrumbs__item'][1]");
	By breadcrumbShop = By.xpath("//li[@ng-reflect-klass = 'breadcrumbs__item'][2]");
	By breadcrumbConfirm = By.xpath("//li[@ng-reflect-klass = 'breadcrumbs__item'][3]");
	By reviewLabel = By.xpath("//div[@class = 'rate-box']/h2");
	By popupSuccessMessage = By.xpath("//div[@class='swal2-header']");
	By popupOKButton = By.xpath("//div[@class='swal2-actions']/button[1]");
	By myPackagesButton = By.xpath("//button[contains(@class,'btn btn-outline-secondary text-muted')]");
	By packagesList = By.xpath("//a[@class = 'dropdown-item']");
	By unitsCount = By.xpath("//span[@class = 'pull-right text-muted']");
	By totalAmount = By.xpath("//h2[@class='text-uppercase text-danger']");		
	
	
// CONSTRUCTOR
		
	public PurchasePackagesPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		PurchasePackagesPO.driver = driver;
	}
// METHODS

	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
	}
	
	public WebElement getPackageName()
	{
		return driver.findElement(packageName);
	}
	public WebElement getBreadcrumbDashboard()
	{
		return driver.findElement(breadcrumbDashboard);
	}
	public WebElement getBreadcrumbShop()
	{
		return driver.findElement(breadcrumbShop);
	}
	public WebElement getBreadcrumbConfirm()
	{
		return driver.findElement(breadcrumbConfirm);
	}
	public WebElement getReviewLabel()
	{
		return driver.findElement(reviewLabel);
	}
	public WebElement getPopupSuccessMessage()
	{
		return driver.findElement(popupSuccessMessage);
	}
	public WebElement getPopupOKButton()
	{
		return driver.findElement(popupOKButton);
	}
	public WebElement getMyPackagesButton()
	{
		return driver.findElement(myPackagesButton);
	}
	public List<WebElement> getPackagesList()
	{
		return driver.findElements(packagesList);
	}
	public List<WebElement> getUnitsCount()
	{
		return driver.findElements(unitsCount);
	}
	public WebElement getTotalAmount()
	{
		return driver.findElement(totalAmount);
	}
}
