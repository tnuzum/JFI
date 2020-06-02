package EME;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import resources.ExtentReporterNG;
import resources.base;

public class listeners extends base implements ITestListener {

	resources.base b = new resources.base();
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	@Override
	public void onTestStart(ITestResult result) {

		test = extent.createTest(
				result.getTestClass().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName());
		extentTest.set(test);

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {

		extentTest.get().fail(result.getThrowable());
		WebDriver driver = null;
		result.getName();

		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			extentTest.get()
					.addScreenCaptureFromPath(b.getScreenshot(
							result.getTestClass().getRealClass().getSimpleName() + "." + result.getName(), driver),
							result.getName());
			// result.getMethod().getMethodName() can also be used in place of
			// result.getName()

			b.getScreenshot(result.getTestClass().getRealClass().getSimpleName() + "." + result.getName(), driver);
		} catch (IOException e) {

			e.printStackTrace();
		}

		catch (NullPointerException npe) {

			npe.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		/*
		 * result.getName(); try { b.getScreenshot(result.getName()); } catch
		 * (IOException e) {
		 * 
		 * e.printStackTrace(); }
		 */
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();

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
