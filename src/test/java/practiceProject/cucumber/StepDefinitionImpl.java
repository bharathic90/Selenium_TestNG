package practiceProject.cucumber;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import practiceProject.base.BaseTest;
import practiceProject.pageObjects.CartPage;
import practiceProject.pageObjects.CheckoutPage;
import practiceProject.pageObjects.LandingPage;
import practiceProject.pageObjects.OrderInfoPage;
import practiceProject.pageObjects.ProductCatalogue;

public class StepDefinitionImpl extends BaseTest {
	
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public CartPage cartPage;
	public CheckoutPage checkoutPage;
	public OrderInfoPage orderInfoPage;
	
	List<String> productsNeeded = Arrays.asList("ZARA COAT 3", "IPHONE 13 PRO");
	
	@Given("I landed on ECommerce Website")
	public void i_landed_on_ecommerce_website() throws IOException {
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_with_username_and_password(String username, String password) {
		productCatalogue = landingPage.loginApplication(username, password);
	}
	
	@When("I add product to cart and check the added products")
	public void i_add_product_to_cart_and_check_the_added_products() {
		productCatalogue.getProducts();
		productCatalogue.grabCartButtons();
		productCatalogue.addProductsToCart(productsNeeded);
		
		cartPage = productCatalogue.viewCart();		// viewCart() is present in AbstractComponent class but still it can be called from its child class ProductCatalogue
		
		// check the items in cart and click checkout
		Map<String, Boolean> map = cartPage.checkCartItemsAgainstNeededItems(productsNeeded);
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			String item = iterator.next();
			Boolean match = map.get(item);
			Assert.assertTrue((boolean) match, item + " is not added to cart");	// Assert validations should be here and not in page objects
		}
	}
	
	@And("^Checkout and submit the order by searching (.+) and choose the country (.+)$")
	public void checkout_and_submit_the_order_by_serching_and_choose_the_country(String searchKeyword, String desiredCountry){
		checkoutPage = cartPage.clickCheckout();
		
		// enter the country, place order
		checkoutPage.chooseCountry(searchKeyword, desiredCountry);
		orderInfoPage = checkoutPage.placeOrder();
	}
	
	@Then("{string} message is displayed on confirmation page")
	public void message_is_displayed_on_confirmation_page(String string) {
		// grab the order id's
		orderInfoPage.grabOrderIds();
		String confirmationMessage = orderInfoPage.getConfirmationMessage();
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase(string));
		driver.quit();
	}
	
	@Then("{string} error message is displayed")
	public void error_message_is_displayed(String string) {
		String errorMessage = landingPage.getErrorMessage();
		Assert.assertEquals(errorMessage, string);
		driver.quit();
	}

}
