package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AcctHistoryPO {

	WebDriver driver;

// OBJECTS

//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By receiptNumbers = By.xpath("//div[@class='col-md-3 hidden-sm hidden-xs']//a");
	By receiptNumber = By.xpath("//div[@class='col-md-3 hidden-sm hidden-xs']//a");
	By receiptNumberTable = By.xpath("//div[contains(@class,'col-md-12')]");
	By searchField = By.xpath("//input[@placeholder='Search in table']");
// CONSTRUCTOR

	public AcctHistoryPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getReceiptNumberTable() {
		return driver.findElement(receiptNumberTable);
	}

	public WebElement getSearchField() {
		return driver.findElement(searchField);
	}

	public List<WebElement> getReceiptNumbers() {
		return driver.findElements(receiptNumbers);
	}

	public WebElement getReceiptNumber() {
		return driver.findElement(receiptNumber);
	}
}
