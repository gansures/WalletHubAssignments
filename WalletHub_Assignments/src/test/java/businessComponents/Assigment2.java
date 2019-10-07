package businessComponents;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import reusableLibrary.ReusableClass;
import uimap.Assignment1_PageObj;
import uimap.Assignment2_PageObj;

public class Assigment2 extends ReusableClass {

		@BeforeSuite
		public void launchWalletHub() throws FileNotFoundException, IOException {
			startReport();
			initializeBrowser();
			openURL();
		}

		/**
		 * @author gansures
		 * @description This method logs the user into WalletHub. credentials can be
		 *              changed from the properties file
		 * @createdDate 06-Oct-2019
		 * @lastModifiedBy
		 * @lastModifiedDate
		 * @modificationComments
		 * @Comments Waits(implicit, explicit and fluent) aren't working, hence the hard
		 *           wait
		 */
		@BeforeTest
		public void loginToWalletHub() throws InterruptedException {
			enterText(Assignment2_PageObj.EMAIL_TEXTBOX, properties.getProperty("email_id"));
			enterText(Assignment2_PageObj.PASS_TEXTBOX, properties.getProperty("password2"));
			click(Assignment2_PageObj.REMEMBER_MY_EMAIL, "Remember my email checkbox");
			click(Assignment1_PageObj.LOGIN_BTN, "Log in button");
			/*
			 * ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			 * public Boolean apply(WebDriver driver) { return ((JavascriptExecutor)
			 * driver).executeScript("return document.readyState").toString().equals(
			 * "complete"); } }; wait.until(expectation);
			 */
			Thread.sleep(7000);
		}

		/**
		 * @author gansures
		 * @description This test performs the post
		 * @createdDate 06-Oct-2019
		 * @lastModifiedBy
		 * @lastModifiedDate
		 * @modificationComments
		 */
		@Test
		public void postHelloWorld() throws InterruptedException {
			if (isElementDisplayed(Assignment1_PageObj.STORY_OPTIONS)) {
				click(Assignment1_PageObj.STORY_OPTIONS, "Story options");
				click(Assignment1_PageObj.DEL_OPT, "Delete option");
				click(Assignment1_PageObj.DEL_BTN, "Delete button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(Assignment1_PageObj.STORY_OPTIONS));
			}
			click(Assignment1_PageObj.WRITE_POST_TEXTAREA, "Write a new post");
			enterText(Assignment1_PageObj.POST_INPUT_TEXTBOX, properties.getProperty("post_message"));
			click(Assignment1_PageObj.POST_BTN, "Post button");

		}

		/**
		 * @author gansures
		 * @description This method validates if the post was successful
		 * @createdDate 06-Oct-2019
		 * @lastModifiedBy
		 * @lastModifiedDate
		 * @modificationComments
		 */
		@AfterTest
		public void validatePost() throws InterruptedException, IOException {
			if (isElementDisplayed(Assignment1_PageObj.STORY_OPTIONS)) {
				updateTestLog("Successfully created a post", Status.PASS);
				File imagepath = takeScreenshot("./" + "//results//screnshoots");
				addScreenshotToReport();
			} else
				updateTestLog("Failed to create a post", Status.FAIL);
		}

		/**
		 * @author gansures
		 * @description This method closes facebook and browser
		 * @createdDate 06-Oct-2019
		 * @lastModifiedBy
		 * @lastModifiedDate
		 * @modificationComments
		 */
		@AfterSuite
		public void closeFacebook() throws InterruptedException {
			logoutFacebook();
			driver.quit();
			endReport();
		}

	}
