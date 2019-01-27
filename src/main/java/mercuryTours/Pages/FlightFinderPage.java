package mercuryTours.Pages;

import common.webdriver.core.UIElement;
import common.webdriver.core.UILocatorType;
import common.webdriver.core.UIType;
import common.webdriver.driver.TestDriver;
import mercuryTours.Util.JournyDetails;

public class FlightFinderPage extends BasePage {

	public FlightFinderPage() {
		TestDriver.setPageName("Flight Finder");
	}

	public UIElement flightDetailsLabl = new UIElement(UIType.Label, UILocatorType.Xpath, "//form[@name='findflight']");

	public UIElement oneWayRdBtn = new UIElement(UIType.RadioButton, UILocatorType.Xpath, "//input[@value='oneway']");
	public UIElement roundRdBtn = new UIElement(UIType.RadioButton, UILocatorType.Xpath, "//input[@value='roundtrip']");

	public UIElement passengerDrpDwn = new UIElement(UIType.ListBox, UILocatorType.Xpath,
			"//select[@name='passCount']");
	public UIElement departingFrmDrpDwn = new UIElement(UIType.ListBox, UILocatorType.Xpath,
			"//select[@name='fromPort']");
	public UIElement onMonth = new UIElement(UIType.ListBox, UILocatorType.Xpath, "//select[@name='fromMonth']");
	public UIElement onDate = new UIElement(UIType.ListBox, UILocatorType.Xpath, "//select[@name='fromDay']");
	public UIElement arrivingInDrpDwn = new UIElement(UIType.ListBox, UILocatorType.Xpath, "//select[@name='toPort']");
	public UIElement returingMonth = new UIElement(UIType.ListBox, UILocatorType.Xpath, "//select[@name='toMonth']");
	public UIElement returingDate = new UIElement(UIType.ListBox, UILocatorType.Xpath, "//select[@name='toDay']");

	public UIElement economyClassRdBtn = new UIElement(UIType.RadioButton, UILocatorType.Xpath,
			"//input[@value='Coach']");
	public UIElement businessClassRdBtn = new UIElement(UIType.RadioButton, UILocatorType.Xpath,
			"//input[@value='Business']");
	public UIElement fristClassRdBtn = new UIElement(UIType.RadioButton, UILocatorType.Xpath,
			"//input[@value='First']");

	public UIElement arilineDrpDwn = new UIElement(UIType.ListBox, UILocatorType.Xpath, "//select[@name='airline']");
	public UIElement continueBtn = new UIElement(UIType.Button, UILocatorType.Xpath, "//input[@name='findFlights']");

	public void fillFlightDetails(JournyDetails tour) {
		if (tour.getTripType().contains("oneway"))
			click(oneWayRdBtn);
		else
			click(roundRdBtn);
		select(passengerDrpDwn, tour.getNoOfPassengers());
		select(departingFrmDrpDwn, tour.getDepartureFrom());
		select(onMonth, tour.getDepartureMonth());
		select(onDate, tour.getDepartureDate());
		select(arrivingInDrpDwn, tour.getArrivingIn());
		select(returingMonth, tour.getArrivingMonth());
		select(returingDate, tour.getArrivingDate());

		if (tour.getServiceClass().contains("coach")) {
			click(economyClassRdBtn);
		} else if (tour.getServiceClass().contains("business")) {
			click(businessClassRdBtn);
		} else if (tour.getServiceClass().contains("first")) {
			click(fristClassRdBtn);
		}
		select(arilineDrpDwn, tour.getAirline());

		click(continueBtn);
		assertTitleContains("Select a Flight: Mercury Tours");
	}
}