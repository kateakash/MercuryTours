package mercuryTours.Steps;

import java.util.List;

import cucumber.api.java.en.And;
import mercuryTours.Pages.PaymentPage;
import mercuryTours.Util.CreditCardDetails;
import mercuryTours.Util.PassengerDetails;
import mercuryTours.Util.TestContext;

public class PaymentSteps {

	private TestContext testContext;

	public PaymentSteps(TestContext context) {
		this.testContext = context;
	}

	PaymentPage paymentPage = new PaymentPage();

	@And("I enter details of passengers$")
	public void enter_passengers_details(List<PassengerDetails> passengerDetails) {
		for (PassengerDetails passenger : passengerDetails) {
			paymentPage.fillPassengerDetails(passenger, testContext.getFlightSelectionDetails());
			testContext.setPassengerDetails(passenger);
		}
	}

	@And("I enter details for CC and Billing address and secure booking$")
	public void enter_ccdetails_and_billingaddress(List<CreditCardDetails> creditCardDetails) {
		for (CreditCardDetails card : creditCardDetails) {
			paymentPage.fillCreditCardDetails(card);
			testContext.setCreditCardDetails(card);
		}
	}

}
