package EME;

import org.mozilla.javascript.tools.shell.Environment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



public class ExampleTest {

	public static void main(String[] args) throws InterruptedException {


		String up = System.getenv("USERPROFILE");
		String projectPath = System.getenv("ECLIPSE_HOME");
		System.out.println(projectPath);
		System.out.println(up);
//		System.setProperty("webdriver.gecko.driver", "C:\\webdrivers\\geckodriver.exe");
/*		System.setProperty("webdriver.gecko.driver", up + "\\Webdrivers\\geckodriver.exe");
				WebDriver driver = new FirefoxDriver();
				
				driver.navigate().to("https://www.google.com");
				System.out.println(driver.getTitle());
				
				driver.findElement(By.id("gb_70")).click(); // click sign-in
				System.out.println(driver.getTitle());	 */	
			

				
				
			}

	}
