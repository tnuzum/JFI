package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPO {

	public static WebDriver driver;

// OBJECTS
		
	By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
	By checkoutButton = By.xpath("//*[text()='CHECKOUT']"); // Checkout button
	By clearCart = By.xpath("//*[text()='CLEAR CART']");
	
	
	
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
	
}
