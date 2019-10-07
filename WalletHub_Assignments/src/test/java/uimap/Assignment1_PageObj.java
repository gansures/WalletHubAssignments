package uimap;

import org.openqa.selenium.By;

public class Assignment1_PageObj{

	public static final By USERNAME_TEXTBOX = By.xpath("//input[@name='email' and contains(@class,'inputtext')]");
	public static final By PASS_TEXTBOX = By.xpath("//input[@name='pass' and contains(@class,'inputtext')]");
	public static final By LOGIN_BTN = By.xpath("//input[@value='Log In' and @type='submit']");
	public static final By USER_FULLNAME = By.xpath("//a[contains(@data-testid,'left_nav_item')]//div");
	public static final By WRITE_POST_TEXTAREA = By.xpath("//*[contains(@title,'Write something here')]");
	public static final By POST_INPUT_TEXTBOX = By.xpath("//*[contains(@aria-label,'Write something here')]//span");
	public static final By POST_BTN = By.xpath("//span[contains(text(),'Post')]/../../button");
	public static final By CLOSE_BTN = By.xpath("//a[text()='Close']");
	public static final By STORY_OPTIONS = By.xpath("//p[contains(text(),'Hello World')]/../../../..//a[@aria-label='Story options']");
	public static final By DEL_OPT = By.xpath("//a//span[text()='Delete']");
	public static final By DEL_BTN = By.xpath("//button[text()='Delete']");
	public static final By ACCOUNT_OPTIONS = By.xpath("//div[@id='logoutMenu']/a");
	public static final By LOGOUT_OPTION =By.xpath("//*[contains(text(),'Log Out')]/../..");
	public static final By CHAT_OPTION =By.xpath("//a[@aria-label='Options' and @role='button']");

}
