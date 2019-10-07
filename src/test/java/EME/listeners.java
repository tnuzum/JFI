package EME;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import resources.base;

public class listeners extends base implements ITestListener{
	
	resources.base b = new resources.base();
	
	@Override
	public void onTestStart(ITestResult result) {
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
	}
	/*
	@Override
	public void onTestFailure(ITestResult result) {

		result.getName();
		try {
			b.getScreenshot(result.getName());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		result.getName();
		try {
			b.getScreenshot(result.getName());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
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

}
