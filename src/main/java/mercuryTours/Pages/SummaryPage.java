package mercuryTours.Pages;

import common.webdriver.core.UIElement;
import common.webdriver.core.UILocatorType;
import common.webdriver.core.UIType;
import common.webdriver.driver.TestDriver;
import mercuryTours.Util.CreditCardDetails;
import mercuryTours.Util.FlightSelection;
import mercuryTours.Util.JournyDetails;

public class SummaryPage extends BasePage {

	public SummaryPage() {
		TestDriver.setPageName("Summary Details");
	}

	public UIElement departureDetails = new UIElement(UIType.Label, UILocatorType.Xpath,
			"//*[contains(text(),'Departing')]/following::tr/td");
	public UIElement arrivalDetails = new UIElement(UIType.Label, UILocatorType.Xpath,
			"//*[contains(text(),'Returning')]/following::tr/td");

	public UIElement passengersDetails = new UIElement(UIType.Label, UILocatorType.Xpath,
			"//*[contains(text(),'Passengers')]/following::tr/td");
	public UIElement billedToDetails = new UIElement(UIType.Label, UILocatorType.Xpath,
			"//*[contains(text(),'Billed To')]/following::tr/td");
	public UIElement taxesDetails = new UIElement(UIType.Label, UILocatorType.Xpath,
			"//*[contains(text(),'Taxes')]/following::td");
	public UIElement totalamtDetails = new UIElement(UIType.Label, UILocatorType.Xpath,
			"//*[contains(text(),'Price')]/following::td");
	public UIElement logOutBtn = new UIElement(UIType.Button, UILocatorType.Xpath,
			"//img[@src='/images/forms/Logout.gif']");

	public void validateDetailsOnSummaryPage(JournyDetails journyDetails, FlightSelection flightSelection,
			CreditCardDetails creditCardDetails) {
		assertTextToBeContainsInElementIgnoreCase(departureDetails,
				journyDetails.getDepartureFrom() + " to " + journyDetails.getArrivingIn());
		assertTextToBeContainsInElementIgnoreCase(departureDetails, flightSelection.getOutFlightDepartureTime());
		assertTextToBeContainsInElementIgnoreCase(departureDetails, flightSelection.getOutFlightName());
		assertTextToBeContainsInElementIgnoreCase(departureDetails, journyDetails.getServiceClass());
		assertTextToBeContainsInElementIgnoreCase(departureDetails, flightSelection.getOutFlightPrice());

		assertTextToBeContainsInElementIgnoreCase(passengersDetails, journyDetails.getNoOfPassengers());
		assertTextToBeContainsInElementIgnoreCase(billedToDetails, creditCardDetails.getCcaddress());
		assertTextToBeContainsInElementIgnoreCase(taxesDetails, flightSelection.getTaxes());
		assertTextToBeContainsInElementIgnoreCase(totalamtDetails, flightSelection.getTotalPrice());

		if (!journyDetails.getTripType().contains("oneway")) {
			assertTextToBeContainsInElementIgnoreCase(arrivalDetails,
					journyDetails.getArrivingIn() + " to " + journyDetails.getDepartureFrom());
			assertTextToBeContainsInElementIgnoreCase(arrivalDetails, flightSelection.getInFlightDepartureTime());
			assertTextToBeContainsInElementIgnoreCase(arrivalDetails, flightSelection.getInFlightName());
			assertTextToBeContainsInElementIgnoreCase(arrivalDetails, journyDetails.getServiceClass());
			assertTextToBeContainsInElementIgnoreCase(arrivalDetails, flightSelection.getInFlightPrice());

		}
	}

	public void clickLogOutBtn() {
		click(logOutBtn);
	}

}
