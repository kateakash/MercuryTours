package mercuryTours.Pages;

import common.webdriver.core.UIElement;
import common.webdriver.core.UILocatorType;
import common.webdriver.core.UIType;
import common.webdriver.driver.TestDriver;
import mercuryTours.Util.CreditCardDetails;
import mercuryTours.Util.FlightSelection;
import mercuryTours.Util.PassengerDetails;

public class PaymentPage extends BasePage {

	public PaymentPage() {
		TestDriver.setPageName("Payment Page");
	}

	public UIElement summaryLabl = new UIElement(UIType.Label, UILocatorType.Xpath, "//form[@name='bookflight']");

	public UIElement firstName = new UIElement(UIType.TextBox, UILocatorType.Xpath, "//input[@name='passFirst0']");
	public UIElement lastName = new UIElement(UIType.TextBox, UILocatorType.Xpath, "//input[@name='passLast0']");
	public UIElement meal = new UIElement(UIType.ListBox, UILocatorType.Xpath, "//select[@name='pass.0.meal']");

	public UIElement cardType = new UIElement(UIType.ListBox, UILocatorType.Xpath, "//select[@name='creditCard']");
	public UIElement number = new UIElement(UIType.TextBox, UILocatorType.Xpath, "//input[@name='creditnumber']");
	public UIElement expirationMonth = new UIElement(UIType.ListBox, UILocatorType.Xpath,
			"//select[@name='cc_exp_dt_mn']");
	public UIElement expirationYear = new UIElement(UIType.ListBox, UILocatorType.Xpath,
			"//select[@name='cc_exp_dt_yr']");
	public UIElement firstNameCC = new UIElement(UIType.TextBox, UILocatorType.Xpath, "//input[@name='cc_frst_name']");
	public UIElement lastNameCC = new UIElement(UIType.TextBox, UILocatorType.Xpath, "//input[@name='cc_last_name']");
	public UIElement middleNameCC = new UIElement(UIType.TextBox, UILocatorType.Xpath, "//input[@name='cc_mid_name']");

	public UIElement address1 = new UIElement(UIType.TextBox, UILocatorType.Xpath, "//input[@name='billAddress1']");
	public UIElement city = new UIElement(UIType.TextBox, UILocatorType.Xpath, "//input[@name='billCity']");
	public UIElement state = new UIElement(UIType.TextBox, UILocatorType.Xpath, "//input[@name='billState']");
	public UIElement postalCode = new UIElement(UIType.TextBox, UILocatorType.Name, "billZip");
	public UIElement country = new UIElement(UIType.ListBox, UILocatorType.Name, "billCountry");

	public UIElement sameAsBillingAddress = new UIElement(UIType.CheckBox, UILocatorType.Name, "ticketLess");
	public UIElement travelTicketless = new UIElement(UIType.CheckBox, UILocatorType.Name, "ticketLess");
	public UIElement securePurchaseBtn = new UIElement(UIType.Button, UILocatorType.Name, "buyFlights");

	public UIElement taxesLbl = new UIElement(UIType.Label, UILocatorType.Xpath,
			"//*[contains(text(),'Taxes')]/following::td");
	public UIElement totalPaymentLbl = new UIElement(UIType.Label, UILocatorType.Xpath,
			"//*[contains(text(),'Total Price')]/following::td");

	public void fillPassengerDetails(PassengerDetails passengerDetails, FlightSelection flightSelection) {

		flightSelection.setTaxes(getText(taxesLbl));
		flightSelection.setTotalPrice(getText(totalPaymentLbl));

		type(firstName, passengerDetails.getFirstName());
		type(lastName, passengerDetails.getLastName());
		select(meal, passengerDetails.getMeal());
	}

	public void fillCreditCardDetails(CreditCardDetails ccDetails) {
		select(cardType, ccDetails.getCardType());
		type(number, ccDetails.getCardNumber());
		select(expirationMonth, ccDetails.getExpMonth());
		select(expirationYear, ccDetails.getExpYear());

		type(firstNameCC, ccDetails.getCcfirstName());
		type(lastNameCC, ccDetails.getCclastName());
		type(middleNameCC, ccDetails.getCcmiddleName());

		type(address1, ccDetails.getCcaddress());
		type(city, ccDetails.getCccity());
		type(state, ccDetails.getCcstate());
		type(postalCode, ccDetails.getCcpostalcode());
		select(country, ccDetails.getCccountry());

		click(sameAsBillingAddress);
		click(securePurchaseBtn);

		assertTitleContains("Flight Confirmation: Mercury Tours");
	}

}
