package pageObjectsEME;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPO {

	public static WebDriver driver;

// OBJECTS
		
	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By checkoutButton = By.xpath("//*[text()='CHECKOUT']"); // Checkout button
	By clearCart = By.xpath("//*[text()='CLEAR CART']");
	By MyCartLabel = By.xpath("//*[text()=' MY CART']");
	By CartSymbol = By.xpath("//h1[contains(text(),'MY CART')]//i[@class='fa fa-shopping-cart']");
	By PackagesLabel = By.xpath("//*[text() =' Packages']");
	By PackagesSymbol = By.xpath("//i[@class='fa fa-gift']");
	By SubTotalLabel = By.xpath("//*[text() = 'SUB-TOTAL: ']");
	By TaxLabel = By.xpath("//*[text() = 'TAX: '] ");
	
	
// CONSTRUCTOR
		
	public CartPO(WebDriver driver) {
		// TODO Auto-generated constructor stub
		CartPO.driver = driver;
	}
// METHODS

	public WebElement getPageHeader()
	{
		return driver.findElement(pageHeader);
	}
	public WebElement getCheckoutButton()
	{
		return driver.findElement(checkoutButton);
	}
	public WebElement getClearCart()
	{
		return driver.findElement(clearCart);
	}
	
	public WebElement getMyCartLabel()
	{
		return driver.findElement(MyCartLabel);
	}
	public WebElement getCartSymbol()
	{
		return driver.findElement(CartSymbol);
	}
	
	public WebElement getPackagesLabel()
	{
		return driver.findElement(PackagesLabel);
	}
	public WebElement getPackagesSymboll()
	{
		return driver.findElement(PackagesSymbol);
	}
	public WebElement getSubTotalLabel()
	{
		return driver.findElement(SubTotalLabel);
	}
	public WebElement getTaxLabel()
	{
		return driver.findElement(TaxLabel);
	}
}
