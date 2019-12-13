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
}

