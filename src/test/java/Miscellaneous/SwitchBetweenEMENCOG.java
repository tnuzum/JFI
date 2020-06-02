package Miscellaneous;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import resources.base;
import resources.reusableMethods;
import resources.reusableWaits;

public class SwitchBetweenEMENCOG extends base {
	public reusableWaits rw;
	public reusableMethods rm;

	public SwitchBetweenEMENCOG() {
		rw = new reusableWaits();
		rm = new reusableMethods();

	}

	@BeforeClass
	public void initialize() throws IOException, InterruptedException {
		driver = initializeDriver();
		rm.setDriver(driver);
		rw.setDriver(driver);
		log.info("Driver Initialized for " + this.getClass().getSimpleName());
		driver.get(prop.getProperty("EMELoginPage"));
	}

	@Test
	public void SwitchingApplications() throws IOException, InterruptedException {
		rm.activeMemberLogin("bauto", "Testing1!");

		System.out.println("In EME");

		rm.memberLogout();
		driver.get("https://ess-web-future2.test-jfisoftware.com:8945/CompeteOnTheGo/home/index/236");
		driver.findElement(By.id("UserName")).sendKeys("bhagya");
		driver.findElement(By.id("Password")).sendKeys("111");
		driver.findElement(By.id("submit")).click();
		Thread.sleep(1000);
		Select s = new Select(driver.findElement(By.id("ddl_clubSelection")));
		s.selectByVisibleText("Studio Jonas");
		driver.findElement(By.id("submit")).click();
		Thread.sleep(3000);
		WebElement FrontDeskTile = driver.findElement(By.xpath("(//div[@class='tile'])[1]"));
		int count = FrontDeskTile.findElements(By.tagName("a")).size();
		for (int i = 0; i < count; i++) {
			System.out.println(FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href"));
			if (FrontDeskTile.findElements(By.tagName("a")).get(i).getAttribute("href").contains("CheckIn")) {
				FrontDeskTile.findElements(By.tagName("a")).get(i).click();
				break;
			}
		}

		driver.findElement(By.id("txt_searchLastName")).sendKeys("Auto, apptmember1");
		driver.findElement(By.id("btn_search")).click();
		driver.findElement(By.xpath("//i[@class='fa fa-check']")).click();
		driver.findElement(By.xpath("//i[@class='fa fa-thumbs-up mrs']")).click();

		List<WebElement> CheckInOptions = driver.findElements(By.tagName("tr"));
		int checkboxCount = CheckInOptions.size();
		for (int j = 0; j < checkboxCount; j++) {
			String Text = CheckInOptions.get(j).getText();
			if (Text.contains("PT 60 Mins-MultiResourcesNotSelected")) {
				CheckInOptions.get(j).findElement(By.className("checkbox")).click();
				break;
			}
		}
		driver.findElement(By.xpath("//i[@class='fa fa-thumbs-up mrs']")).click();
		driver.findElement(By.xpath("//a[@href='/CompeteOnTheGo/Account/Logoff']")).click();
	}

	@AfterClass
	public void teardown() throws InterruptedException {
		driver.close();
		driver = null;
	}
}
