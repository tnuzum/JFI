package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShopPackagesPO {

	WebDriver driver;

// OBJECTS

//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By keyWord = By.xpath("//input[@type = 'text']");
	By packageName = By.xpath("//span[contains(@class, 'shop-header')]");
	By packagesection = By.xpath("//div[contains(@class, 'col-lg-12')]");
	// By purchaseButton = By.xpath("//div[@class= 'text-right ']/a");
	By purchaseButton = By.xpath("//a[@class = 'btn btn-primary btn-sm at-shoppackage-button-purchase']");
	By breadcrumbDashboard = By.xpath("//li[@ng-reflect-klass = 'breadcrumbs__item'][1]");
	By breadcrumbShop = By.xpath("//li[@ng-reflect-klass = 'breadcrumbs__item'][2]");
	By packagesList = By.xpath("//div[@class = 'row m-t-md']");
	By warningMsg = By.xpath("//div[contains(@class, 'alert-warning')]");
// CONSTRUCTOR

	public ShopPackagesPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getKeyWord() {
		return driver.findElement(keyWord);
	}

	public List<WebElement> getPackageNames() {
		return driver.findElements(packageName);
	}

	public List<WebElement> getPackagesections() {
		return driver.findElements(packagesection);
	}

	public List<WebElement> getPurchaseButtons() {
		return driver.findElements(purchaseButton);
	}

	public WebElement getBreadcrumbDashboard() {
		return driver.findElement(breadcrumbDashboard);
	}

	public WebElement getBreadcrumbShop() {
		return driver.findElement(breadcrumbShop);
	}

	public WebElement getPackagesList() {
		return driver.findElement(packagesList);
	}

	public WebElement getWarningMsg() {
		return driver.findElement(warningMsg);
	}
}
