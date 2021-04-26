package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BreadcrumbTrailPO {

	WebDriver driver;

// OBJECTS

//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By breadcrumb1 = By.xpath("//li[contains(@class, 'breadcrumbs__item')][1]");
	By breadcrumb2 = By.xpath("//li[contains(@class, 'breadcrumbs__item')][2]");
	By breadcrumb3 = By.xpath("//li[contains(@class, 'breadcrumbs__item')][3]");
	By breadcrumbConfirmation = By.xpath("//span[@class='ng-star-inserted']");

// CONSTRUCTOR

	public BreadcrumbTrailPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getBreadcrumb1() {
		return driver.findElement(breadcrumb1);
	}

	public WebElement getBreadcrumb2() {
		return driver.findElement(breadcrumb2);
	}

	public WebElement getBreadcrumb3() {
		return driver.findElement(breadcrumb3);
	}

	public WebElement getBreadcrumbConfirmation() {
		return driver.findElement(breadcrumbConfirmation);
	}
}
