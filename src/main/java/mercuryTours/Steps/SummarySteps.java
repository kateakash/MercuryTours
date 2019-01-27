package mercuryTours.Steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import mercuryTours.Pages.SummaryPage;
import mercuryTours.Util.TestContext;

public class SummarySteps {

	private TestContext testContext;

	public SummarySteps(TestContext context) {
		this.testContext = context;
	}

	SummaryPage summaryPage = new SummaryPage();

	@Then("I validate all details on summary page$")
	public void validate_details_on_summary_page() {
		summaryPage.validateDetailsOnSummaryPage(testContext.getJournyDetails(),
				testContext.getFlightSelectionDetails(), testContext.getCreditCardDetails());
	}

	@And("I click on Logg Off button$")
	public void click_logoff_btn() {
		summaryPage.clickLogOutBtn();
	}

}
