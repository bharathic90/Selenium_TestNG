Feature: Error Validation in eCommerce website
	
	@ErrorValidation
	Scenario Outline: Negative test of validating error
		Given I landed on ECommerce Website
		When Logged in with username <name> and password <password>
		Then "Incorrect email or password." error message is displayed
		
		Examples:
			| name					| password			|
			| bharbun@email.com		| Bharbun11			|
			| bharbun2@email.com	| Bharbun22			|
 