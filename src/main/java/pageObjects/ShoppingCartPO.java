package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShoppingCartPO {
	public static WebDriver driver;

	// OBJECTS
			
		By pageHeader = By.xpath("//div[@class='col-sm-12']/h2");
		By clearCart = By.xpath("//shoppingcart[@class='ng-star-inserted']/div/div/div/div/div[3]/button[1]");
		By checkout = By.xpath("//shoppingcart[@class='ng-star-inserted']/div/div/div/div/div[2]/button[2]");
//		By checkout = By.xpath("//a[@class='btn.btn-primary.btn-sm.ng-star-inserted']");

		
	// CONSTRUCTOR
			
		public ShoppingCartPO(WebDriver driver) {
			ShoppingCartPO.driver = driver;
		}
	// METHODS

		public WebElement getPageHeader()
		{
			return driver.findElement(pageHeader);
		}
		public WebElement getClearCart()
		{
			return driver.findElement(clearCart);
		}
		public WebElement getCheckout()
		{
			return driver.findElement(checkout);
		}
		
}
