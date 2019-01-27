package mercuryTours.Steps;

import cucumber.api.java.en.Then;
import mercuryTours.Pages.SelectFlightPage;
import mercuryTours.Util.FlightSelection;
import mercuryTours.Util.TestContext;

public class SelectFlightSteps {

	private TestContext testContext;
	SelectFlightPage selectFlight = new SelectFlightPage();

	public SelectFlightSteps(TestContext context) {
		this.testContext = context;
	}

	@Then("I select flight and click on continue button$")
	public void select_default_flight() {
		FlightSelection flightSelection = new FlightSelection();
		selectFlight.clickOnContinueBtn(flightSelection, testContext.getJournyDetails());
		testContext.setFlightSelectionDetails(flightSelection);
	}

}
