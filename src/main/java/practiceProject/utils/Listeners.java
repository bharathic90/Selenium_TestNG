package practiceProject.utils;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Listeners implements ITestListener{
	
	ExtentTest test;
	ExtentReports extent = new ExtentReport().reportConfig();
	
	ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<ExtentTest>();
	//threadLocal sets and gets the test variable which is of type ExtentTest. 
	

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestStart(result);
		System.out.println("listeners invoked");
		
		test = extent.createTest(result.getMethod().getMethodName());	// result will have all the info abt the executing test method. So grabbing the methodName from it.
		threadLocal.set(test);								
		// setting the test along with the name of testCase invoked this, in order to assign a separate thread for that particular testCase
		// This will create a unique thread id for that test case and stores them in the form of map.
		// so, that can be retrieved later when needed with the help of thread id automatically by calling threadLocal.get()
		
		// once the test is set, then replace all the test variable used here with threadLocal.get() in order for the test and testCases invoking test are to be aligned to the same thread
		
		// This mechanism is useful especially in case of parallel runs in which 
		// 		mis-alignment of testCase which invokes test and creates it & 
		//		testCase which invokes test in different method say, failure() 
		//		which leads to generating wrong reports is common
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSuccess(result);
		
//		test.log(Status.PASS, "Test Passed Successfully..");
		threadLocal.get().log(Status.PASS, "Test Passed Successfully..");	// replacing test with threadLocal.get()
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailure(result);
		
		threadLocal.get().fail(result.getThrowable());				// result variable gets hold of the exception thrown by the failed method and getThrowable() throws that back to report
		
		
		
		WebDriver driver = null;
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Screenshot, Attach to report
		String testCaseName = result.getMethod().getMethodName();
		String path = null;
		
		ScreenShot ss = new ScreenShot();
		
		try {
			path = ss.getScreenShot(testCaseName, driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		threadLocal.get().addScreenCaptureFromPath(path, testCaseName);
		System.out.println("screenshot taken");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onFinish(context);
		extent.flush();
	}
	
	

}
