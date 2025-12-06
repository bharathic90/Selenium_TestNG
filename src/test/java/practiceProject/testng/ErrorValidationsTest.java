package practiceProject.testng;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import practiceProject.base.BaseTest;
import practiceProject.pageObjects.CartPage;
import practiceProject.pageObjects.CheckoutPage;
import practiceProject.pageObjects.LandingPage;
import practiceProject.pageObjects.OrderInfoPage;
import practiceProject.pageObjects.ProductCatalogue;
import practiceProject.utils.Retry;

public class ErrorValidationsTest extends BaseTest {
	
	
	@Test(groups= {"NegativeTestCase"})
	public void loginErrorValidation() throws IOException {
		
		String username = "bharbun2@email.com";
		String password = "Bharbun22";
		
		// enter user name and password, click login
		landingPage.loginApplication(username, password);
		String errorMessage = landingPage.getErrorMessage();
		Assert.assertEquals(errorMessage, "Incorrect email or password.");
		
	}
	
	@Test(retryAnalyzer=Retry.class)
	public void productErrorValidation() {
		
		String username = "bharbun@email.com";
		String password = "Bharbun1";
		String searchKeyword = "unit";
		String desiredCountry = "United States";
		
		List<String> productsNeeded = Arrays.asList("ZARA COAT 3", "IPHONE 13 PRO");
		
		
		// enter user name and password, click login
		ProductCatalogue productCatalogue = landingPage.loginApplication(username, password);
				
		// get the products list, cart buttons
		productCatalogue.getProducts();
		productCatalogue.grabCartButtons();
		productCatalogue.addProductsToCart(productsNeeded);
		CartPage cartPage = productCatalogue.viewCart();						// viewCart() is present in AbstractComponent class but still it can be called from its child class ProductCatalogue
				
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

}
