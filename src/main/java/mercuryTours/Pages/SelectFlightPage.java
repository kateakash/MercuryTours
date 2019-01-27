package mercuryTours.Pages;

import java.util.regex.Pattern;

import common.webdriver.core.UIElement;
import common.webdriver.core.UILocatorType;
import common.webdriver.core.UIType;
import common.webdriver.driver.TestDriver;
import mercuryTours.Util.FlightSelection;
import mercuryTours.Util.JournyDetails;

public class SelectFlightPage extends BasePage {

	FlightSelection flightDetails;

	public SelectFlightPage() {
		TestDriver.setPageName("Select Flight Page");
	}

	public UIElement outFlightRdBtn = new UIElement(UIType.RadioButton, UILocatorType.Name, "outFlight");
	public UIElement inFlightRdBtn = new UIElement(UIType.RadioButton, UILocatorType.Name, "inFlight");

	public UIElement continueBtn = new UIElement(UIType.Button, UILocatorType.Name, "reserveFlights");

	public void clickOnContinueBtn(FlightSelection flightDetails, JournyDetails journyDetails) {
		String outFlight = getValueAttribute(outFlightRdBtn);
		String outFlightValues[] = outFlight.split(Pattern.quote("$"));
		if (outFlightValues.length == 4) {
			flightDetails.setOutFlightName(outFlightValues[0] + " " + outFlightValues[1]);
			flightDetails.setOutFlightPrice(outFlightValues[2]);
			flightDetails.setOutFlightDepartureTime(outFlightValues[3]);
		} else {
			flightDetails.setOutFlightName(outFlightValues[0]);
			flightDetails.setOutFlightPrice(outFlightValues[1]);
			flightDetails.setOutFlightDepartureTime(outFlightValues[1]);
		}

		if (!journyDetails.getTripType().contains("oneway")) {
			String inFlight = getValueAttribute(inFlightRdBtn);
			String inFlightValues[] = inFlight.split(Pattern.quote("$"));
			if (inFlightValues.length == 4) {
				flightDetails.setInFlightName(inFlightValues[0] + " " + inFlightValues[1]);
				flightDetails.setInFlightPrice(inFlightValues[2]);
				flightDetails.setInFlightDepartureTime(inFlightValues[3]);
			} else {
				flightDetails.setInFlightName(inFlightValues[0]);
				flightDetails.setInFlightPrice(inFlightValues[1]);
				flightDetails.setInFlightDepartureTime(inFlightValues[2]);
			}
		}
		click(continueBtn);
		assertTitleContains("Book a Flight: Mercury Tours");
	}

}
