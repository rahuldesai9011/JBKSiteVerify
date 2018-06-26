package com.jbk.test;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class TakeScreenShot {

	public static String captureScreenshot(String shotName) throws IOException {

		TakesScreenshot screenShot = (TakesScreenshot) JbkSiteLinkVerify.driver;
		File source = screenShot.getScreenshotAs(OutputType.FILE);
		String dest = JbkSiteLinkVerify.properties.getProperty("screenshotpath") + shotName + ".jpg";
		File destination = new File(dest);
		FileUtils.copyFile(source, destination);
		System.out.println("Screenshot taken");
		return dest;

	}

}
