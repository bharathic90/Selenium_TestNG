Feature: Purchase the order from eCommerce website

	Background:
		Given I landed on ECommerce Website
	
	@Regression
	Scenario Outline: Positive test of submitting the order
		Given Logged in with username <name> and password <password>
		When I add product to cart and check the added products
		And Checkout and submit the order by searching <searchKeyword> and choose the country <desiredCountry>
		Then "THANKYOU FOR THE ORDER." message is displayed on confirmation page
		
		Examples:
			| name					| password			| searchKeyword	| desiredCountry	|
			| bharbun@email.com		| Bharbun1			| unit			| United States		|
			| bharbun2@email.com	| Bharbun2			| ind			| India				|
 