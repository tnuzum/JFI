package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ManagePayMethodsPO {

	WebDriver driver;

// OBJECTS

//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By noSavedCardMessage = By.xpath("//h1[@class='text-capitalize']");
	By bankAccountLink = By.xpath("//a[@href = '#collapseOne'] //i");
	By managePMBreadcrumb = By.xpath("//a[contains(text(),'Manage Payment Methods')]");

// CONSTRUCTOR

	public ManagePayMethodsPO(WebDriver driver) {
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getNoSavedCardMessage() {
		return driver.findElement(noSavedCardMessage);
	}

	public WebElement getBankAccountLink() {
		return driver.findElement(bankAccountLink);
	}

	public WebElement getManagePMBreadcrumb() {
		return driver.findElement(managePMBreadcrumb);
	}

}
