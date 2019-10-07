package businessComponents;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import reusableLibrary.ReusableClass;
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
	 * @throws IOException
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
	public void loginToWalletHub() throws InterruptedException, IOException {
		enterText(Assignment2_PageObj.EMAIL_TEXTBOX, properties.getProperty("email_id"));
		enterText(Assignment2_PageObj.PASS_TEXTBOX, properties.getProperty("password2"));
		click(Assignment2_PageObj.REMEMBER_MY_EMAIL, "Remember my email checkbox");
		click(Assignment2_PageObj.LOGIN_BTN, "Log in button");
		wait.until(ExpectedConditions.visibilityOfElementLocated(Assignment2_PageObj.USER_FULLNAME));
		if (driver.findElement(Assignment2_PageObj.USER_FULLNAME).getText()
				.contains(properties.getProperty("user_fullname"))) {
			updateTestLog("Successfully logged into WalletHub", Status.PASS);
			addScreenshotToReport();
		} else
			updateTestLog("Failed to log into WalletHub", Status.FAIL);

	}

	/**
	 * @author gansures
	 * @throws IOException
	 * @description This test posts a review
	 * @createdDate 06-Oct-2019
	 * @lastModifiedBy
	 * @lastModifiedDate
	 * @modificationComments
	 */
	@Test
	public void postAReview() throws InterruptedException, IOException {
		driver.get(properties.getProperty("Reviewing_link"));
		if (driver.findElement(Assignment2_PageObj.USER_FULLNAME).getText()
				.contains(properties.getProperty("company"))) {
			updateTestLog("Opened the Test Insurance Company page", Status.PASS);
			addScreenshotToReport();
		} else
			updateTestLog("Test Insurance Company page was not opened", Status.FAIL);
		click(Assignment2_PageObj.WRITE_REVIEW_BTN, "Write a review button");
		starHoverOver();
		vaidateStarHoverOver();
		click(By.xpath("(//*[@height='" + properties.getProperty("height_of_the_star") + "'])[position()="
				+ properties.getProperty("star_rating") + "]"),
				"Click on the Star " + properties.getProperty("star_rating"));
		click(Assignment2_PageObj.POLICY_DROPDOWN, "Policy drop down");
		click(By.xpath("//*[@class='dropdown-item' and text()='" + properties.getProperty("policy") + "']"),
				properties.getProperty("policy"));
		enterText(Assignment2_PageObj.POST_INPUT_TEXTBOX, properties.getProperty("review_comment"));
		click(Assignment2_PageObj.SUBMIT_BTN, "Submit button");

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

		if (isElementDisplayed(Assignment2_PageObj.SUCCESS_MSG)) {
			updateTestLog("Successfully submitted the review", Status.PASS);
			addScreenshotToReport();
		} else
			updateTestLog("Failed to submit the review", Status.FAIL);
		String username = splitstring(properties.getProperty("email_id"), "@");
		driver.get("https://wallethub.com/profile/" + username + "/reviews/");
		if (isElementDisplayed(By.xpath("//a[text()='" + properties.getProperty("company") + "']"))) {
			click((By.xpath("//a[text()='" + properties.getProperty("company") + "']")), "Review");
			String comment_text = driver.findElement(By.xpath("//span[contains(text(),'" + username
					+ "')]/../../following-sibling::div[contains(@class,'text-select')]")).getText();
			Assert.assertTrue(properties.getProperty("review_comment").contains(comment_text),
					"The review comment is as posted in the previous page");
			updateTestLog("Review validation is successful", Status.PASS);
			addScreenshotToReport();
		} else
			updateTestLog("Review validation failed", Status.FAIL);
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
		driver.quit();
		endReport();
	}

}
