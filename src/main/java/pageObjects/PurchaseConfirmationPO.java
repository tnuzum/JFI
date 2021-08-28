package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PurchaseConfirmationPO {

	WebDriver driver;

// OBJECTS

//	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By packageName = By.xpath("//div[@class='widget widget-callout']/h1");
	By breadcrumbDashboard = By.xpath("//li[@ng-reflect-klass = 'breadcrumbs__item'][1]");
	By breadcrumbShop = By.xpath("//li[@ng-reflect-klass = 'breadcrumbs__item'][2]");
	By breadcrumbConfirm = By.xpath("//li[@ng-reflect-klass = 'breadcrumbs__item'][3]");
	By reviewLabel = By.xpath("//div[@class = 'rate-box']/h2");
	By popupSuccessMessage = By.xpath("//div[@class='swal2-header']");
	By popupOKButton = By.xpath("//div[@class='swal2-actions']/button[1]");
	By myPackagesButton = By.xpath("//button[contains(@class,'at-topnavbar-mypackages')]");
	By packagesList = By.xpath("//div[@class = 'dropdown-item']");
	By unitsCount = By.xpath("//span[@class = 'float-right text-muted']");
	By shopPackageTotalAmount = By.xpath("//h2[contains(@class,'at-shoppackage-text-total')]");
	By classesReviewtotalAmount = By.xpath("//span[contains(@class,'At-classes-review-text-total')]");
	By memberFeesSection = By.xpath("//div[@class = 'text-right']");
	By classIsFull = By.xpath("//span[contains(text(), ' Class is full ')]");
	By courseEventIsFull = By.xpath("//span[contains(text(), ' Course/Event is full ')]");
	By goOnStndby = By.linkText("Yes, Go On Standby");
	By standbyQuestion = By.xpath("//strong[contains(text(),'Would you like to be placed on Standby?')]");

// CONSTRUCTOR

	public PurchaseConfirmationPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getPackageName() {
		return driver.findElement(packageName);
	}

	public WebElement getBreadcrumbDashboard() {
		return driver.findElement(breadcrumbDashboard);
	}

	public WebElement getBreadcrumbShop() {
		return driver.findElement(breadcrumbShop);
	}

	public WebElement getBreadcrumbConfirm() {
		return driver.findElement(breadcrumbConfirm);
	}

	public WebElement getReviewLabel() {
		return driver.findElement(reviewLabel);
	}

	public WebElement getPopupSuccessMessage() {
		return driver.findElement(popupSuccessMessage);
	}

	public WebElement getPopupOKButton() {
		return driver.findElement(popupOKButton);
	}

	public WebElement getMyPackagesButton() {
		return driver.findElement(myPackagesButton);
	}

	public List<WebElement> getPackagesList() {
		return driver.findElements(packagesList);
	}

	public List<WebElement> getUnitsCount() {
		return driver.findElements(unitsCount);

	}

	public WebElement getShopPackageTotalAmount() {
		return driver.findElement(shopPackageTotalAmount);
	}

	public WebElement getClassesReviewtotalAmount() {
		return driver.findElement(classesReviewtotalAmount);
	}

	public List<WebElement> getMemberfeesSection()

	{
		return driver.findElements(memberFeesSection);
	}

	public WebElement getClassIsFull() {
		return driver.findElement(classIsFull);
	}

	public WebElement getCourseEventIsFull() {
		return driver.findElement(courseEventIsFull);
	}

	public WebElement getGoOnStndby() {
		return driver.findElement(goOnStndby);
	}

	public WebElement getStandbyQuestion() {
		return driver.findElement(standbyQuestion);
	}
}
