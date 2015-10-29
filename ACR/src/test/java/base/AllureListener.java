package test.java.base;

import main.java.ApplicationStorage;
import main.java.core.ACR;
import main.java.db.SqlReseller;
import org.joda.time.DateTime;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static main.java.utils.Utils.*;

/**
 * Created by Andrii.Bakhtiozin on 21.05.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class AllureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("Test " + iTestResult.getMethod().getMethodName() + " has finished with FAILURE!!!");
        makeScreenshot("Failure screenshot");
        response("response", getTestStartTime(iTestResult));
        request("request", getTestStartTime(iTestResult));
        shortInfo("request/response log", getTestStartTime(iTestResult));
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("Test TN" + Thread.currentThread().getId() + " " + iTestResult.getMethod().getMethodName() + " has started.");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("Test " + iTestResult.getMethod().getMethodName() + " has finished");
        makeScreenshot("success screenshot");
        addBookingId("booking Id: " + getBookingId());
        response("response", getTestStartTime(iTestResult));
        request("request", getTestStartTime(iTestResult));
        shortInfo("request/response log", getTestStartTime(iTestResult));
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("Test " + iTestResult.getMethod().getMethodName() + " has SKIPPED!!!");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test " + iTestResult.getMethod().getMethodName() + " has finished with FAILURE!!!");
        makeScreenshot("Failure screenshot");
        response("response_responsetime" + SqlReseller.getLastResellerLogExecutionTime(ApplicationStorage.getInstance().getReseller()), getTestStartTime(iTestResult));
        request("request_requesttime" + SqlReseller.getLastResellerLogExecutionTime(ApplicationStorage.getInstance().getReseller()), getTestStartTime(iTestResult));
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("Tests running on " + ACR.getInstance().getSiteURL() + " " + new DateTime(iTestContext.getStartDate().getTime()).minusHours(3).toString("yyyy-MM-dd HH:mm"));
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("Test suites have finished");
    }

    private String getTestStartTime(ITestResult iTestResult) {
        return new DateTime(iTestResult.getStartMillis()).minusHours(3).toString("yyyy-MM-dd HH:mm");
    }
}
