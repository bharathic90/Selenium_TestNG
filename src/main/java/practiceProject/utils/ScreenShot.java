package practiceProject.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import practiceProject.base.BaseTest;

public class ScreenShot {
	
	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
	
		String destPath = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
		File dest = new File(destPath);
		FileUtils.copyFile(src, dest);
		return destPath;
	}

}
