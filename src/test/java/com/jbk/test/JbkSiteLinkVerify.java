package com.jbk.test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement; 
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class JbkSiteLinkVerify {
	public static WebDriver driver;
	public static int rows;
	public static String linkName;
	public static ExtentReports reports;
	public static ExtentTest logger;
	public static Properties properties = new Properties();
	public static List<WebElement> links;
	public static ArrayList<String> page_links;
	public static ArrayList<String> excel_links;
	public static Logger log4j;
	public static Collection<String> similar;
	public static Collection<String> different;
	public static String sequence;
	public static String status;
	public static boolean yesno;
	
	
	@BeforeSuite
	public void setupEnvironment() throws Exception {
		FileInputStream finput = new FileInputStream("input.properties");
		properties.load(finput);

		log4j = Logger.getLogger("JbkSiteLinkVerify");
		PropertyConfigurator.configure(properties.getProperty("log4jpath"));

		reports = new ExtentReports(properties.getProperty("reportpath"), true);
		
		//System.setProperty(properties.getProperty("chromekey"), properties.getProperty("chromevalue"));
		driver = new HtmlUnitDriver();
		log4j.info("Chrome browser opened");

		driver.get(properties.getProperty("applicationpath"));
		log4j.info("Application Launched");

		// driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		String spath = properties.getProperty("excelpath");
		ExcelDataConfig.setExcelFile(spath, properties.getProperty("sheet"));
		rows = ExcelDataConfig.getRowCount();
		excel_links = new ArrayList<>();
		for (int i = 0; i < rows; i++) {
			String s = ExcelDataConfig.getCellData(i, Integer.parseInt(properties.getProperty("columnno")));
			excel_links.add(s);
		}
		
		status=properties.getProperty("Y");
		yesno=(boolean)Boolean.parseBoolean(status);
	}
	
	
	@Test(dataProvider = "verifyLinks", priority = 1)
	public void verifyJBKSite(String link) {
		linkName=link;
		logger = reports.startTest(link);
		driver.get(properties.getProperty("applicationpath"));
		driver.findElement(By.linkText(link)).click();
		log4j.info(link + " Clicked");
		String title = driver.getTitle();
		System.out.println(title);

		links = driver.findElements(By.xpath(properties.getProperty("linkxpath")));
		page_links = new ArrayList<>();
		for (int i = 0; i < links.size(); i++) {
			String s = links.get(i).getText();
			page_links.add(s);
		}

		similar = new HashSet<String>(excel_links);
		different = new HashSet<String>();
		different.addAll(excel_links);
		different.addAll(page_links);
		similar.retainAll(page_links);
		different.removeAll(similar);

		if (different.size() > 0) {
			if (excel_links.size() > page_links.size()) {
				log4j.info("Missing links on " + link + " page: " + different);
				sequence="NOT";
			} else {
				log4j.info("Extra links on " + link + " page: " + different);
				sequence="NOT";
			}
			Assert.assertTrue(false);
		} else if (page_links.size() != rows) {
			log4j.info("Links mismathed Required: " + rows + " Found: " + page_links.size());
			Assert.assertTrue(false);
		} else {
			System.out.println("Expected links: " + excel_links.size());
			System.out.println("Actual links: " + page_links.size());
			log4j.info("Expected Links count: " + excel_links.size() + " Actual links size: " + page_links.size());
			for (int i = 0; i < rows; i++) {
				if ((excel_links.get(i).equals(page_links.get(i)))) {	
					sequence="YES";
					Assert.assertTrue(true);
				} else {
					log4j.info(
							"sequence not matched. Found: " + excel_links.get(i) + " Required: " + page_links.get(i));
					sequence="NOT";
					Assert.assertTrue(false);
				}
			}
			System.out.println("=================================================");
		}

	}

	@DataProvider(name = "verifyLinks")
	public Object[][] passData() throws Exception {
		int columnno = (int) Integer.parseInt(properties.getProperty("columnno"));
		Object[][] obj = new Object[rows][1];
		for (int i = 0; i < rows; i++) {
			obj[i][0] = ExcelDataConfig.getCellData(i, columnno);
		}
		return obj;
	}

	@AfterSuite
	public void tearDown() {
		driver.get(properties.getProperty("reportpath"));
	}

}
