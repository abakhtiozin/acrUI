package test.java.base;

import static main.java.util.LogUtils.*;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import test.java.base.TestHelper.Log;

public class TestExecutionListener implements ITestListener {
	
	@Override
	public void onTestStart(ITestResult result) {
		Log.info("Test " + result.getMethod().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName() + " has stated; ");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Log.info("Test " + result.getMethod().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName() + " has succeed; ");
	}
	@Override
	public void onTestFailure(ITestResult result) {
		Log.error("Test " + result.getMethod().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName() + " has failed; ");
		takeScreenshot("Failure screenshot");	
		takePageSource("Failure page source");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) { 
		// TODO Auto-generated method stub
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}
