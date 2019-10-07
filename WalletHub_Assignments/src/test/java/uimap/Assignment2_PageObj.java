package uimap;

import org.openqa.selenium.By;

public class Assignment2_PageObj {

	public static final By EMAIL_TEXTBOX = By.xpath("//input[@placeholder='Email Address']");
	public static final By PASS_TEXTBOX = By.xpath("//input[@placeholder='Password']");
	public static final By REMEMBER_MY_EMAIL = By.xpath("//input[@type='checkbox' and @ng-model='fields.remember']/..");
	public static final By LOGIN_BTN = By.xpath("//span[text()='Login']/..");
	public static final By USER_FULLNAME = By.xpath("//h1[@class='profile-name']");
	public static final By WRITE_REVIEW_BTN = By.xpath("//button[contains(text(),'Write a Review')]");
	public static final By POLICY_DROPDOWN = By
			.xpath("//span[contains(text(),'Select')]/../..//div[@class='dropdown second']");
	public static final By POST_INPUT_TEXTBOX = By.xpath("//textarea[contains(@placeholder,'Write your review')]");
	public static final By SUBMIT_BTN = By.xpath("//div[text()='Submit']");
	public static final By SUCCESS_MSG = By.xpath("//*[contains(text(),'Your review has been posted')]");
	public static final By LOGOUT_OPTION = By.xpath("//input[@id='logout-button']");

}
