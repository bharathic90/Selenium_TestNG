package practiceProject.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
	
	int count = 0;
	int maxTry = 1;

	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if(count < maxTry) {
			count++;
			return true;
		}
		return false;
	}
	
	// when some testCase failed after reaching the onTestFailure() in Listeners class control comes to the above retry() method automatically
	// only when that particular method is marked as 
	// Also, this cannot be configured in testng.xml like Listeners. 
	// The testCase which is failing for no reason or flaky has to be marked to rerun

}
