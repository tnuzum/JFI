package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ManageProfilePO {

	WebDriver driver;

// OBJECTS

//	By pageHeader 			= By.xpath("//div[@class='col-sm-12']/h2");
	By pageHeader = By.xpath("//h2[@class = 'at-breadcrumb-title']");
	By question = By.xpath("//div[@class='ibox']/div[1]/h5");
	By usernameButton = By.xpath("//div[@class='ibox']/div[2]/div/div/div[1]/div[1]/h5/a");
	By passwordButton = By.xpath("//div[@class='ibox']/div[2]/div/div/div[2]/div/h4/a");
	By generalInfoButton = By.xpath("//div[@class='ibox']/div[2]/div/div/div[3]/div/h4/a");
	By groupActivityOptionsButton = By.xpath("//div[@class='ibox']/div[2]/div/div/div[4]/div/h4/a");
	By interestsButton = By.xpath("//div[@class='ibox']/div[2]/div/div/div[5]/div/h4/a");
	By currentUsernameLabel = By.xpath("//div[@id='collapseOne']/editusernamecomponent/div/form/div[1]/label");
//** i'm having trouble locating labels for New Username and Confirm New Username **
	By usernameInput = By.xpath("//div[@id='collapseOne']/editusernamecomponent/div/form/div[1]/input");
	By newUsernameInput = By.xpath("//div[@id='collapseOne']/editusernamecomponent/div/form/div[2]/div[1]/input");
	By confirmNewUsernameInput = By
			.xpath("//div[@id='collapseOne']/editusernamecomponent/div/form/div[2]/div[2]/input");

	By menuDashboardButton = By.xpath("//a[contains(@class, 'at-mainnav-dashboard')]");
	By myAccount1 = By.xpath("//mat-nav-list[@class='mat-nav-list']/mat-list-item[2]/div/span");
	// By manageProfile1 = By.xpath("/a[contains(test(),'ManageProfile')]");
	By manageProfile1 = By.xpath("//a[contains(@class, 'at-mainnav-manageprofile')]");
	By userName1 = By.xpath("//i[@class = 'fad fa-user m-r-sm']"); // updated by Bhagya
	By currentUSerName1 = By.xpath("//input[@formcontrolname = 'fbCurrentUsername']"); // updated by Bhagya
	By newUSerName1 = By.xpath("//input[@formcontrolname = 'fbNewUsername']"); // updated by Bhagya
	By confirmUSerName1 = By.xpath("//input[@formcontrolname = 'fbConfirmNewUsername']"); // updated by Bhagya
	By changeUSerName1 = By.xpath("//button[contains(text(),'change username')]");
	By saveUsernmae = By.xpath("//button[contains(text(),'OK')]");

// CONSTRUCTOR

	public ManageProfilePO(WebDriver driver) {
		this.driver = driver;
	}
// METHODS

	public WebElement getPageHeader() {
		return driver.findElement(pageHeader);
	}

	public WebElement getQuestion() {
		return driver.findElement(question);
	}

	public WebElement getUsernameButton() {
		return driver.findElement(usernameButton);
	}

	public WebElement getPasswordButton() {
		return driver.findElement(passwordButton);
	}

	public WebElement getGeneralInfoButton() {
		return driver.findElement(generalInfoButton);
	}

	public WebElement getGroupActivityOptionsButton() {
		return driver.findElement(groupActivityOptionsButton);
	}

	public WebElement getinterestsButton() {
		return driver.findElement(interestsButton);
	}

	public WebElement getCurrentUsernameLabel() {
		return driver.findElement(currentUsernameLabel);
	}

	public WebElement getUsernameInput() {
		return driver.findElement(usernameInput);
	}

	public WebElement getNewUsernameInput() {
		return driver.findElement(newUsernameInput);
	}

	public WebElement getConfirmNewUsernameInput() {
		return driver.findElement(confirmNewUsernameInput);
	}

	public WebElement getmenuDashboardButton() {
		return driver.findElement(menuDashboardButton);
	}

	public WebElement getmyAccount() {
		return driver.findElement(myAccount1);
	}

	public WebElement getmanageProfile() {
		return driver.findElement(manageProfile1);
	}

	public WebElement getuserName() {
		return driver.findElement(userName1);
	}

	public WebElement getcurrentUSerName() {
		return driver.findElement(currentUSerName1);
	}

	public WebElement getnewUSerName() {
		return driver.findElement(newUSerName1);
	}

	public WebElement getconfirmUSerName() {
		return driver.findElement(confirmUSerName1);
	}

	public WebElement getchangeUSerName() {
		return driver.findElement(changeUSerName1);
	}

	public WebElement getsaveUsernmae() {
		return driver.findElement(saveUsernmae);
	}
}
