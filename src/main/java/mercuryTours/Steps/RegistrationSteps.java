package mercuryTours.Steps;

import java.util.List;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import mercuryTours.Pages.LoginPage;
import mercuryTours.Pages.RegistrationPage;
import mercuryTours.Util.RegistrationDetails;
import mercuryTours.Util.TestContext;

public class RegistrationSteps {

	private TestContext testContext;

	public RegistrationSteps(TestContext context) {
		this.testContext = context;
	}

	RegistrationPage registrationPage = new RegistrationPage();
	LoginPage loginPage = new LoginPage();

	@Given("I enter new user details for registration$")
	public void fill_registration_details(List<RegistrationDetails> registrationDetails) {
		for (RegistrationDetails registration : registrationDetails) {
			registrationPage.fillDetailsForRegistration(registration);
			testContext.setRegistrationDetails(registration);
		}
	}

	@Then("I click on Register link$")
	public void click_register_link() {
		loginPage.clickRegisterLink();
	}

}
