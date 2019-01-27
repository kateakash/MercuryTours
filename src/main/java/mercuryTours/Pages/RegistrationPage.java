package mercuryTours.Pages;

import common.webdriver.core.UIElement;
import common.webdriver.core.UILocatorType;
import common.webdriver.core.UIType;
import common.webdriver.driver.TestDriver;
import mercuryTours.Util.RegistrationDetails;

public class RegistrationPage extends BasePage {

	public RegistrationPage() {
		TestDriver.setPageName("Registration Page");
	}

	public UIElement firstName = new UIElement(UIType.TextBox, UILocatorType.Name, "firstName");
	public UIElement lastName = new UIElement(UIType.TextBox, UILocatorType.Name, "lastName");
	public UIElement phoneNumber = new UIElement(UIType.TextBox, UILocatorType.Name, "phone");
	public UIElement emailAddress = new UIElement(UIType.TextBox, UILocatorType.Name, "userName");
	public UIElement address1 = new UIElement(UIType.TextBox, UILocatorType.Name, "address1");
	public UIElement city = new UIElement(UIType.TextBox, UILocatorType.Name, "city");
	public UIElement state = new UIElement(UIType.TextBox, UILocatorType.Name, "state");
	public UIElement postalCode = new UIElement(UIType.TextBox, UILocatorType.Name, "postalCode");
	public UIElement country = new UIElement(UIType.ListBox, UILocatorType.Name, "country");
	public UIElement userName = new UIElement(UIType.TextBox, UILocatorType.ID, "email");
	public UIElement password = new UIElement(UIType.TextBox, UILocatorType.Name, "password");
	public UIElement confirmPassword = new UIElement(UIType.TextBox, UILocatorType.Name, "confirmPassword");
	public UIElement submitBtn = new UIElement(UIType.Button, UILocatorType.Name, "register");

	public void fillDetailsForRegistration(RegistrationDetails registrationInfo) {

		type(firstName, registrationInfo.getFirstName());
		type(lastName, registrationInfo.getLastName());
		type(phoneNumber, registrationInfo.getPhonenumber());
		type(emailAddress, registrationInfo.getEmail());
		type(address1, registrationInfo.getAddress1());
		type(city, registrationInfo.getCity());

		type(state, registrationInfo.getState());
		type(postalCode, registrationInfo.getPostalcode());
		select(country, registrationInfo.getCountry());
		type(userName, registrationInfo.getUserName());
		type(password, registrationInfo.getPassword());
		type(confirmPassword, registrationInfo.getConformPassword());
		// click(submitBtn);

	}

}
