@Registration 
Feature: User Registration 
	I fill all details for user and do registration
	
Scenario Outline: I fill all details for user and do registration 
	Given I launch website "<ApplicationURL>" 
	Then I click on Register link 
	And I enter new user details for registration 
		|firstName|lastName|phonenumber|email				|address1	|city	|state	|postalcode|country|userName|password|conformPassword|
		|akash	  |	   kate|1231231231 |kateakash@gmail.com |Banglore|Banglore|Banglore| 411019		|INDIA|kateakash|kateakash|kateakash	 |
		
		
	Examples: 
		|ApplicationURL|
		|http://newtours.demoaut.com/mercuryreservation.php|
		