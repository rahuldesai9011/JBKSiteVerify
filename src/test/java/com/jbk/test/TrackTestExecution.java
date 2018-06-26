package com.jbk.test;

import java.io.IOException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.LogStatus;

public class TrackTestExecution implements ITestListener {

	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestFailure(ITestResult result) {
		if (JbkSiteLinkVerify.yesno) {
			try {

				String path = TakeScreenShot
						.captureScreenshot(result.getName() + " Failed Link " + JbkSiteLinkVerify.linkName);
				String image = JbkSiteLinkVerify.logger.addScreenCapture(path);
				JbkSiteLinkVerify.logger.log(LogStatus.INFO, "Total Links Required: " + JbkSiteLinkVerify.rows);
				JbkSiteLinkVerify.logger.log(LogStatus.ERROR, "Sequence Mathed? : " + JbkSiteLinkVerify.sequence);
				JbkSiteLinkVerify.logger.log(LogStatus.ERROR,
						"Total Links Found: " + JbkSiteLinkVerify.page_links.size());
				JbkSiteLinkVerify.logger.log(LogStatus.ERROR, "Missing Links Are: " + JbkSiteLinkVerify.different);
				JbkSiteLinkVerify.logger.log(LogStatus.FAIL, "Link Verification Failed", image);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				JbkSiteLinkVerify.reports.endTest(JbkSiteLinkVerify.logger);
				JbkSiteLinkVerify.reports.flush();
			}
		} else {

			try {
				String path = TakeScreenShot
						.captureScreenshot(result.getName() + " Failed Link " + JbkSiteLinkVerify.linkName);
				String image = JbkSiteLinkVerify.logger.addScreenCapture(path);
				JbkSiteLinkVerify.logger.log(LogStatus.FAIL, image);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				JbkSiteLinkVerify.reports.endTest(JbkSiteLinkVerify.logger);
				JbkSiteLinkVerify.reports.flush();
			}
		}
	}

	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestSuccess(ITestResult result) {
		if (JbkSiteLinkVerify.yesno) {
			try {
				String path = TakeScreenShot
						.captureScreenshot(result.getName() + " Success Link " + JbkSiteLinkVerify.linkName);
				String image = JbkSiteLinkVerify.logger.addScreenCapture(path);
				JbkSiteLinkVerify.logger.log(LogStatus.INFO, "Sequence Mathed? : " + JbkSiteLinkVerify.sequence);
				JbkSiteLinkVerify.logger.log(LogStatus.INFO,
						JbkSiteLinkVerify.linkName + " link Successfully verified ");
				JbkSiteLinkVerify.logger.log(LogStatus.PASS, "Link Verification Successfull", image);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				JbkSiteLinkVerify.reports.endTest(JbkSiteLinkVerify.logger);
				JbkSiteLinkVerify.reports.flush();
			}
		} else {
			try {
				String path = TakeScreenShot
						.captureScreenshot(result.getName() + " Success Link " + JbkSiteLinkVerify.linkName);
				String image = JbkSiteLinkVerify.logger.addScreenCapture(path);
				JbkSiteLinkVerify.logger.log(LogStatus.PASS, image);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				JbkSiteLinkVerify.reports.endTest(JbkSiteLinkVerify.logger);
				JbkSiteLinkVerify.reports.flush();
			}
		}

	}

}
