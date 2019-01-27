package mercuryTours.Steps;

import java.util.List;

import cucumber.api.java.en.Then;
import mercuryTours.Pages.FlightFinderPage;
import mercuryTours.Util.JournyDetails;
import mercuryTours.Util.TestContext;

public class FlightFinderSteps {

	private TestContext testContext;

	public FlightFinderSteps(TestContext context) {
		this.testContext = context;
	}

	FlightFinderPage flightFinder = new FlightFinderPage();

	@Then("I enter flight details along with perferences$")
	public void enter_flight_details_along_with_preferencessss(List<JournyDetails> journyDetails) {
		for (JournyDetails journy : journyDetails) {
			flightFinder.fillFlightDetails(journy);
			testContext.setJournyDetails(journy);
		}
	}

}
