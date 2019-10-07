package reusableLibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import uimap.Assignment1_PageObj;

/**
 * @author gansures
 *
 */

public class ReusableClass {

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected Properties properties = new Properties();
	ExtentReports extent;
	ExtentTest logger;

	// More reusable objects can be added in future such as for reporting, database
	// connection, excel connection and so forth

	/**
	 * @author gansures
	 * @description Based on the values provided in the properties file the
	 *              respective browser will be opened ith the below method
	 * @createdDate 06-Oct-2019
	 * @lastModifiedBy
	 * @lastModifiedDate
	 * @modificationComments
	 */
	public void initializeBrowser() throws FileNotFoundException, IOException {
		properties
				.load(new java.io.FileInputStream(System.getProperty("user.dir") + "\\src\\GlobalSettings.properties"));
		String browser = properties.getProperty("browser");
		String browser_path = System.getProperty("user.dir") + properties.getProperty(browser);
		if (browser.contains("ie")) {
			WebDriverManager.iedriver().setup();
			// System.setProperty("webdriver.ie.driver", browser_path);
			driver = new InternetExplorerDriver();
			updateTestLog("Successfully launched the Internet explorer browser", Status.PASS);
		} else if (browser.contains("chrome")) {
			// WebDriverManager.chromedriver().setup();
			ChromeOptions chrome_ops = new ChromeOptions();
			chrome_ops.addArguments("--disable-notifications");
			System.setProperty("webdriver.chrome.driver", browser_path);
			driver = new ChromeDriver(chrome_ops);
			updateTestLog("Successfully launched the chrome browser", Status.PASS);
		} else if (browser.contains("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			// System.setProperty("webdriver.gecko.driver", browser_path);
			driver = new FirefoxDriver();
			updateTestLog("Successfully launched the firefox browser", Status.PASS);
		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
	}

	/**
	 * @author gansures
	 * @description This method opens either facebook/WalletHub URL's based on
	 *              WebpageURL name provided in the properties file.
	 * @createdDate 06-Oct-2019
	 * @lastModifiedBy
	 * @lastModifiedDate
	 * @modificationComments
	 */
	public void openURL() {
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.get(properties.getProperty(properties.getProperty("WebpageURL")));
		if (driver.getTitle().contains(properties.getProperty("WebpageURL")))
			updateTestLog(properties.getProperty("WebpageURL") + " page was opened successfully", Status.PASS);
		else
			updateTestLog(properties.getProperty("WebpageURL") + " page was not opened", Status.FAIL);
	}

	/**
	 * @author gansures
	 * @description This method logs out from facebook
	 * @createdDate 06-Oct-2019
	 * @lastModifiedBy
	 * @lastModifiedDate
	 * @modificationComments
	 */
	public void logoutFacebook() throws InterruptedException {
		click(Assignment1_PageObj.ACCOUNT_OPTIONS, "Account options");
		click(Assignment1_PageObj.LOGOUT_OPTION, "Log out option");
		if (driver.getTitle().contains("Facebook – log in or sign up"))
			updateTestLog("Logout was successful", Status.PASS);
		else
			updateTestLog("Logout was un-successful", Status.FAIL);
	}

	/**
	 * @author gansures
	 * @description This method validated if an element is displayed on screen
	 * @createdDate 06-Oct-2019
	 * @lastModifiedBy
	 * @lastModifiedDate
	 * @modificationComments
	 */
	public boolean isElementDisplayed(By elementID) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementID));
			driver.findElement(elementID);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @author gansures
	 * @description This method clicks on an element
	 * @createdDate 06-Oct-2019
	 * @lastModifiedBy
	 * @lastModifiedDate
	 * @modificationComments
	 */
	public void click(By locatorID, String ele) throws InterruptedException {
		if (isElementDisplayed(locatorID)) {
			driver.findElement(locatorID).click();
			updateTestLog("Successfully clicked on " + ele, Status.PASS);
		} else {
			updateTestLog("Click action failed on " + ele, Status.FAIL);
		}
	}

	/**
	 * @author gansures
	 * @description This method enters text on the locator specified
	 * @createdDate 06-Oct-2019
	 * @lastModifiedBy
	 * @lastModifiedDate
	 * @modificationComments
	 * @param locatorID
	 * @param value
	 */
	public void enterText(By locatorID, String value) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locatorID));
			// driver.findElement(locatorID).clear();
			driver.findElement(locatorID).sendKeys(value);
			updateTestLog("Successfully entered text: " + value, Status.PASS);
		} catch (Exception e) {
			updateTestLog("Failed to enter text: " + value, Status.FAIL);
		}
	}

	/**
	 * @author gansures
	 * @description This method starts the extent reporting
	 * @createdDate 06-Oct-2019
	 * @lastModifiedBy
	 * @lastModifiedDate
	 * @modificationComments
	 */
	public void startReport() {
		ExtentHtmlReporter reporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "\\results\\TestResult.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		logger = extent.createTest("Create post", "Create a 'Hello World' post on facebook");
	}

	/**
	 * @author gansures
	 * @description This method adds log to the report at every step used
	 * @createdDate 06-Oct-2019
	 * @lastModifiedBy
	 * @lastModifiedDate
	 * @modificationComments
	 * @param stepDetails
	 * @param Status
	 */
	public void updateTestLog(String stepDetails, Status Status) {
		try {
			switch (Status) {
			case FAIL:
				logger.log(Status, stepDetails);
				addScreenshotToReport();
				throw new Exception();
			case WARNING:
				logger.log(Status, stepDetails);
				break;
			case PASS:
				logger.log(Status, stepDetails);
				break;
			default:
				logger.log(Status, stepDetails);
			}
			;
		} catch (Exception e1) {
			logger.log(Status.INFO, "Error occured while taking screenshot");

		}
	}

	/**
	 * @author gansures
	 * @description This method takes screenshot
	 * @createdDate 06-Oct-2019
	 * @lastModifiedBy
	 * @lastModifiedDate
	 * @modificationComments
	 * @param screenshotPath
	 * @return File
	 */
	protected File takeScreenshot(String screenshotPath) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File dest = null;
		try {
			// now copy the screenshot to desired location using copyFile //method
			dest = new File(screenshotPath);
			FileUtils.copyFile(src, dest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dest;

	}

	/**
	 * @author gansures
	 * @description This method adds screenshot to the report
	 * @createdDate 06-Oct-2019
	 * @lastModifiedBy
	 * @lastModifiedDate
	 * @modificationComments
	 */
	protected void addScreenshotToReport() throws IOException {
		File imagepath = takeScreenshot(System.getProperty("user.dir") + "//results//screnshoots");
		logger.addScreenCaptureFromPath(imagepath.getAbsolutePath());
	}

	/**
	 * @author gansures
	 * @description This method ends the report by collating the log and generating
	 * @createdDate 06-Oct-2019
	 * @lastModifiedBy
	 * @lastModifiedDate
	 * @modificationComments
	 */
	protected void endReport() {
		extent.flush();
	}

}
