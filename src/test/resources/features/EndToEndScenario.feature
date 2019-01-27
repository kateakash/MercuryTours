@EndToEnd 
Feature: Flight Booking 
	Do Flight booking with Round Trip Flight for one passengers from London to Paris on dates 18 Jan Departure 
	and 28th Jan Arrival and with Economy class
	
Scenario: 
	I book flight Round Trip Flight for one passengers from London to Paris on dates 18 Jan Departure and 28th Jan Arrival and with Economy class 
	Given I launch website for Mercury Tours 
	
	Then I click on SIGN ON link 
	And I enter username and password and click on continue button 
		|Username|Password|
		|kateakash|kateakash|
		
	Then I enter flight details along with perferences 
		|tripType|noOfPassengers|departureFrom|arrivingIn|departureMonth|departureDate|arrivingMonth|arrivingDate|airline		|serviceClass|
		|roundTrip|1			|London		  | Paris	 |January		|18			  |January		|28			 |Unified Airlines| coach|	
		
	Then I select flight and click on continue button 
	
	Then I enter details of passengers 
		|firstName|lastName|meal|
		|akash|kate|Vegetarian|
		
	And I enter details for CC and Billing address and secure booking 
		|cardType|cardNumber|expMonth|expYear|ccfirstName|ccmiddleName|cclastName|ccaddress|cccity|ccstate|ccpostalcode|cccountry|
		|Visa	 |123123123 |10		 |2010	|Soham		 |Akash		  |Kate		 |Banglore |Banglore|Banglore|212323|INDIA		|
		
	Then I validate all details on summary page 
	And I click on Logg Off button 
	
	
	
Scenario: 
	I book flight Round Trip Flight for one passengers from New York to Sydney on dates 24 Jan Departure and 31th Jan Arrival and with First class 
	Given I launch website for Mercury Tours 
	
	Then I click on SIGN ON link 
	And I enter username and password and click on continue button 
		|Username|Password|
		|kateakash|kateakash|
		
	Then I enter flight details along with perferences 
		|tripType|noOfPassengers|departureFrom|arrivingIn|departureMonth|departureDate|arrivingMonth|arrivingDate|airline		|serviceClass|
		|roundTrip|1			|New York		  | Sydney	 |January		|24			  |January		|31			 |Blue Skies Airlines| first|	
		
	Then I select flight and click on continue button 
	
	Then I enter details of passengers 
		|firstName|lastName|meal|
		|vaishali|kate|Vegetarian|
		
	And I enter details for CC and Billing address and secure booking 
		|cardType|cardNumber|expMonth|expYear|ccfirstName|ccmiddleName|cclastName|ccaddress|cccity|ccstate|ccpostalcode|cccountry|
		|Visa	 |123123123 |10		 |2010	|Akash		 |Anil		  |Kate		 |Pune |Pune|Maharashtra|212323|INDIA		|
		
	Then I validate all details on summary page 
	And I click on Logg Off button