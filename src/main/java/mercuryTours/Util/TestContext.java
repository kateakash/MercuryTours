package mercuryTours.Util;

public class TestContext {
	private RegistrationDetails registrationDetails;
	private FlightSelection flightSelectionDetails;
	private JournyDetails journyDetails;
	private CreditCardDetails creditCardDetails;
	private PassengerDetails passengerDetails;

	/**
	 * @return the journyDetails
	 */
	public JournyDetails getJournyDetails() {
		return journyDetails;
	}

	/**
	 * @param journyDetails
	 *            the journyDetails to set
	 */
	public void setJournyDetails(JournyDetails journyDetails) {
		this.journyDetails = journyDetails;
	}

	/**
	 * @return the flightdetails
	 */
	public FlightSelection getFlightSelectionDetails() {
		return flightSelectionDetails;
	}

	/**
	 * @param flightdetails
	 *            the flightdetails to set
	 */
	public void setFlightSelectionDetails(FlightSelection flightdetails) {
		this.flightSelectionDetails = flightdetails;
	}

	/**
	 * @return the registrationDetails
	 */
	public RegistrationDetails getRegistrationDetails() {
		return registrationDetails;
	}

	/**
	 * @param registrationDetails
	 *            the registrationDetails to set
	 */
	public void setRegistrationDetails(RegistrationDetails registrationDetails) {
		this.registrationDetails = registrationDetails;
	}

	public CreditCardDetails getCreditCardDetails() {
		return creditCardDetails;
	}

	public void setCreditCardDetails(CreditCardDetails creditCardDetails) {
		this.creditCardDetails = creditCardDetails;
	}

	public PassengerDetails getPassengerDetails() {
		return passengerDetails;
	}

	public void setPassengerDetails(PassengerDetails passengerDetails) {
		this.passengerDetails = passengerDetails;
	}

}
