package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ManageFamilyPO {

	WebDriver driver;

// OBJECTS

//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By pageText = By.xpath("//h2[contains(text(),'Who Would You Like To Manage?')]");
	By familyMemberNames = By.tagName("h3");
	By memberName = By.xpath("//h2[contains(text(),'FreeMember Auto')]");
	By memberOptionsLabels = By.xpath("//strong[contains(text(), '- MEMBER OPTIONS -')]");
	By payNowButtons = By.xpath("//button[contains(@class, 'at-managefamily-button-paynow')]");
	By barcodeIds = By.xpath("//strong[contains(text(), 'Barcode ID: ')]");
	By hohOnOffSwitchLabels = By.xpath("//label[@class= 'onoffswitch-label']");
// CONSTRUCTOR

	public ManageFamilyPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getPageText() {
		return driver.findElement(pageText);
	}

	public List<WebElement> getFamilyMemberNames() {
		return driver.findElements(familyMemberNames);
	}

	public WebElement getMemberName() {
		return driver.findElement(memberName);
	}

	public List<WebElement> getMemberOptionsLabels() {
		return driver.findElements(memberOptionsLabels);
	}

	public List<WebElement> getPayNowButtons() {
		return driver.findElements(payNowButtons);
	}

	public List<WebElement> getBarcodeIds() {
		return driver.findElements(barcodeIds);
	}

	public List<WebElement> getHohOnOffSwitchLabels() {
		return driver.findElements(hohOnOffSwitchLabels);
	}
}
