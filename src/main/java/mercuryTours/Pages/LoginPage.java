package mercuryTours.Pages;

import common.webdriver.core.UIElement;
import common.webdriver.core.UILocatorType;
import common.webdriver.core.UIType;
import common.webdriver.driver.TestDriver;

public class LoginPage extends BasePage {
	
	public LoginPage() {
		TestDriver.setPageName("Login Page");
	}
	public UIElement registerLink = new UIElement(UIType.Link, UILocatorType.Xpath, "//a[contains(text(),'REGISTER')]");
	
	public UIElement usernameTextBox = new UIElement(UIType.TextBox, UILocatorType.Xpath, "//input[@name='userName']");
	public UIElement passwordTextBox = new UIElement(UIType.TextBox, UILocatorType.Xpath, "//input[@name='password']");
	public UIElement loginBtn = new UIElement(UIType.TextBox, UILocatorType.Xpath, "//input[@name='login']");
	public UIElement signON = new UIElement(UIType.Link,UILocatorType.Xpath, "//a[contains(text(),'SIGN-ON')]");
	public UIElement signOFF = new UIElement(UIType.Link,UILocatorType.Xpath, "//a[contains(text(),'SIGN-OFF')]");
	
	public void doLogin(String userName, String password) {
		type(usernameTextBox, userName);
		type(passwordTextBox, password);
		click(loginBtn);
		assertTitleContains("Find a Flight: Mercury Tours:");
	}

	public void openPage(String url) {
		openURL(url);
		assertTitleContains("Welcome: Mercury Tours");
	}
	
	public void clickRegisterLink() {
		click(registerLink);
		assertTitleContains("Register: Mercury Tours");
	}

	public void clickSignOnLink() {
		click(signON);
	}
	
}
