package Messages;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.DashboardPO;
import pageObjects.ManagePayMethodsPO;
import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class messageCenter extends base {

	public reusableMethods rm;
	public reusableWaits rw;
	public static DashboardPO d;
	public static ManagePayMethodsPO mp;
	public static JavascriptExecutor js;

	public messageCenter() {
		rm = new reusableMethods();
		rw = new reusableWaits();
	}

	@BeforeClass
	public void initialize() throws Exception, IOException {

		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		d = new DashboardPO(driver);
		mp = new ManagePayMethodsPO(driver);
		js = (JavascriptExecutor) driver;
		getEMEURL();
	}

	@Test(priority = 1, description = "Message Widget")
	public void verifyMessagecenter() throws IOException, InterruptedException {
		rm.activeMemberLogin("Seema", "June@123");

		d.getshowAllbutton().click();

		js.executeScript("window.scrollBy(0,500)");
		Thread.sleep(2000);

		int count = driver.findElements(By.tagName("a")).size();
		System.out.println(count);

		for (int i = 0; i < count; i++) {
			String messageName1 = driver
					.findElements(By.xpath("//a[contains(@class, 'at-widget-externallink')]/parent::div")).get(i)
					.getText();

			if (messageName1.contains("Meesage for new club")) {
				System.out.println(messageName1);

				js.executeScript("arguments[0].click();",
						driver.findElements(By.xpath("//a[contains(@class, 'at-widget-externallink')]")).get(i));
				// System.out.println("Clicked");
				break;
			}
		}
		Assert.assertEquals(driver.getWindowHandles().size(), 2);
		Set<String> ids = driver.getWindowHandles();
		Iterator<String> it = ids.iterator();
		String parentid = it.next();
		String childid = it.next();
		driver.switchTo().window(childid); // Switch to child window
		Thread.sleep(2000);
		System.out.println(driver.getTitle());
		Assert.assertTrue(driver.getTitle().contains("Google"));
		driver.switchTo().window(parentid); // Switch back to EME window
		Thread.sleep(1000);
		Assert.assertEquals(driver.getTitle(), "Dashboard");
		js.executeScript("window.scrollBy(0,500)");
		Thread.sleep(2000);
		d.gethideAllbutton().click();

	}

	@AfterClass()
	public void teardown() throws InterruptedException {
		driver.quit();
		driver = null;
	}
}
