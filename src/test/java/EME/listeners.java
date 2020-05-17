package EME;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import resources.Base;

public class listeners extends Base implements ITestListener {

	resources.Base b = new resources.Base();

	@Override
	public void onTestStart(ITestResult result) {

	}

	@Override
	public void onTestSuccess(ITestResult result) {

	}

	@Override
	public void onTestFailure(ITestResult result) {

		result.getName();
		try {
			b.getScreenshot(result.getName());
		} catch (IOException e) {

			e.printStackTrace();
		}

		catch (NullPointerException npe) {

			npe.printStackTrace();
		}
	}

	/*
	 * @Override public void onTestSkipped(ITestResult result) {
	 * 
	 * result.getName(); try { b.getScreenshot(result.getName()); } catch
	 * (IOException e) {
	 * 
	 * e.printStackTrace(); } }
	 */
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onFinish(ITestContext context) {

	}
	/*
	 * @Override public void onTestFailure(ITestResult result) {
	 * if(ITestResult.FAILURE==result.getStatus()){ WebElement username =
	 * driver.findElement(By.xpath("//a[@class='at-topnavbar-logout']")); // element
	 * which displays if user is logged in if(username.isDisplayed()) // steps to
	 * logout will go here
	 * driver.findElement(By.xpath("//a[@class='at-topnavbar-logout']")).click(); }
	 * }
	 */
}
