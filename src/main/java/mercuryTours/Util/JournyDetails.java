package mercuryTours.Util;

public class JournyDetails {
	private String tripType;
	private String noOfPassengers;
	private String departureFrom;
	private String arrivingIn;
	private String departureMonth;
	private String departureDate;
	private String arrivingMonth;
	private String arrivingDate;
	private String airline;
	private String serviceClass;

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public String getNoOfPassengers() {
		return noOfPassengers;
	}

	public void setNoOfPassengers(String noOfPassengers) {
		this.noOfPassengers = noOfPassengers;
	}

	public String getArrivingIn() {
		return arrivingIn;
	}

	public void setArrivingIn(String arrivingIn) {
		this.arrivingIn = arrivingIn;
	}

	public String getDepartureMonth() {
		return departureMonth;
	}

	public void setDepartureMonth(String departureMonth) {
		this.departureMonth = departureMonth;
	}

	public String getDepartureFrom() {
		return departureFrom;
	}

	public void setDepartureFrom(String departureFrom) {
		this.departureFrom = departureFrom;
	}

	public String getArrivingDate() {
		return arrivingDate;
	}

	public void setArrivingDate(String arrivingDate) {
		this.arrivingDate = arrivingDate;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getArrivingMonth() {
		return arrivingMonth;
	}

	public void setArrivingMonth(String arrivingMonth) {
		this.arrivingMonth = arrivingMonth;
	}

	public String getServiceClass() {
		return serviceClass;
	}

	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

}
