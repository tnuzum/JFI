package EME;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import pageObjects.DashboardPO;
import pageObjects.PaymentPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;


public class MakePaymentTest extends base {
	private static Logger log = LogManager.getLogger(base.class.getName());
	private static String testName = null;
	

//	@BeforeTest
	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("Driver Initialized");
		driver.get(prop.getProperty("EMELoginPage"));
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@BeforeMethod
	public void GetTestMethodName(Method method)
		    {
		         testName = method.getName(); 
		        
		    }

	@Test(priority = 1, description = "Adding $1.00 to member's account")
	public void MakePayment() throws InterruptedException, IOException {
		reusableMethods.activeMemberLogin("hoh", "Testing1!");
		DashboardPO d = new DashboardPO(driver);
		PaymentPO p = new PaymentPO(driver);
		d.getMyAccountPayNow().click();
		
		try {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(
			ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[@class='text-center']")));

		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click();", p.getAmountRadioButton3()); 
//  	Thread.sleep(5000);
//  	p.getAmountRadioButton3().click();
		
		Thread.sleep(500);
		int variable = 1;
		while (variable < 13) {
			p.getCustomAmountInput().sendKeys(Keys.BACK_SPACE);
			variable++;
					}
		p.getCustomAmountInput().sendKeys("1.00");
		Thread.sleep(300);
		
		p.getPayWithThisMethodButton1().click();
				      
		reusableWaits.waitForAcceptButton();
		p.getPopupConfirmationButton().click();
		reusableWaits.waitForAcceptButton();
		System.out.println(p.getPopupText().getText());
		Assert.assertEquals("Payment Made!", p.getPopupText().getText());
		p.getPopupConfirmationButton().click();
//		reusableMethods.returnToDashboard();
		Thread.sleep(3000);
		
		} catch (java.lang.AssertionError ae) {
			System.out.println("assertion error");
			ae.printStackTrace();
			getScreenshot(testName);
			log.error(ae.getMessage(), ae);
			//Assert.fail(ae.getMessage());
		}

		catch (org.openqa.selenium.NoSuchElementException ne) {
			System.out.println("No element present");
			ne.printStackTrace();
			getScreenshot(testName);
			log.error(ne.getMessage(), ne);
			//Assert.fail(ne.getMessage());
		}

		catch (org.openqa.selenium.ElementClickInterceptedException eci) {
			System.out.println("Element Click Intercepted");
			eci.printStackTrace();
			getScreenshot(testName);
			log.error(eci.getMessage(), eci);
			reusableMethods.catchErrorMessage();
			//Assert.fail(eci.getMessage());
		}
		
		finally {
		d.getBreadcrumbDashboard().click();
		}
		
	}

	@Test(priority = 2, description = "Confirming payment is applied", dependsOnMethods = {"MakePayment"} )
	public void ConfirmPaymentApplied() throws InterruptedException {
		DashboardPO d = new DashboardPO(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='homeComponent']//memberbalance/div/div[2]/small[1]")));
		while (d.getMyAccountLastPaymentDate().getText().equalsIgnoreCase("Last Payment:")) {
			Thread.sleep(500);
			System.out.println("Sleeping for 500ms");
		}
		DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
		Date date = new Date();
		String DateTime = dateFormat.format(date);
		Assert.assertEquals("Last Payment: " + DateTime, d.getMyAccountLastPaymentDate().getText());
	}

//	@AfterTest
	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
