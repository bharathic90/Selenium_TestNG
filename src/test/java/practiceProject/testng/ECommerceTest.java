package practiceProject.testng;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import practiceProject.base.BaseTest;
import practiceProject.pageObjects.CartPage;
import practiceProject.pageObjects.CheckoutPage;
import practiceProject.pageObjects.OrderHistoryPage;
import practiceProject.pageObjects.OrderInfoPage;
import practiceProject.pageObjects.ProductCatalogue;

public class ECommerceTest extends BaseTest {
	
	
	List<String> productsNeeded = Arrays.asList("ZARA COAT 3", "IPHONE 13 PRO");
	
	
	@DataProvider
	public Object[][] getData() throws IOException {
//		Object[][] data = new Object[][] { {"bharbun@email.com","Bharbun1","unit","United States"}, 
//											{"bharbun2@email.com","Bharbun2","ind","India"} };
											
		// The above approach will be messy if we work with large no. of data set
		// Also, it is difficult to catch in test all the n no. of data comma seperated.
		// To Overcome this, we can store them in HashMap and return it as below.
		
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("username", "bharbun@email.com");
//		map1.put("password", "Bharbun1");
//		map1.put("searchKeyword", "unit");
//		map1.put("desiredCountry", "United States");
//		
//		HashMap<String, String> map2 = new HashMap<String, String>();
//		map2.put("username", "bharbun2@email.com");
//		map2.put("password", "Bharbun2");
//		map2.put("searchKeyword", "ind");
//		map2.put("desiredCountry", "India");
//		
//		Object[][] data = new Object[][] { {map1}, {map2} };
		
		// fetching the data from the json file 
		String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\testData.json";
		List<HashMap<String,String>> testDataList = loadJsonTestData(new File(filePath));
		
		Object[][] data = new Object[][] { {testDataList.get(0)}, {testDataList.get(1)} };
		return data;
	}

	
	@Test(dataProvider="getData", groups= {"Purchase"})
//	public void submitOrder(String username, String password, String searchKeyword, String desiredCountry) throws IOException {
	public void submitOrder(HashMap<String, String> dataMap) throws IOException {	
	
		// enter user name and password, click login
//		ProductCatalogue productCatalogue = landingPage.loginApplication(username, password);
		ProductCatalogue productCatalogue = landingPage.loginApplication(dataMap.get("username"), dataMap.get("password"));
		
		// get the products list, cart buttons
		productCatalogue.getProducts();
		productCatalogue.grabCartButtons();
		productCatalogue.addProductsToCart(productsNeeded);
		CartPage cartPage = productCatalogue.viewCart();		// viewCart() is present in AbstractComponent class but still it can be called from its child class ProductCatalogue
		
		// check the items in cart and click checkout
		Map<String, Boolean> map = cartPage.checkCartItemsAgainstNeededItems(productsNeeded);
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			String item = iterator.next();
			Boolean match = map.get(item);
			Assert.assertTrue((boolean) match, item + " is not added to cart");	// Assert validations should be here and not in page objects
		}
		
		CheckoutPage checkoutPage = cartPage.clickCheckout();
		
		
		// enter the country, place order
		checkoutPage.chooseCountry(dataMap.get("searchKeyword"), dataMap.get("desiredCountry"));
		OrderInfoPage orderInfoPage = checkoutPage.placeOrder();
		
		// grab the order id's
		orderInfoPage.grabOrderIds();
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void checkInOrderHistory() {
		
		String username = "bharbun@email.com";
		String password = "Bharbun1";
		String searchKeyword = "unit";
		String desiredCountry = "United States";
		
		
		// enter user name and password, click login
		ProductCatalogue productCatalogue = landingPage.loginApplication(username, password);
		OrderHistoryPage orderHistoryPage = productCatalogue.viewOrders();
		Map<String, Boolean> map = orderHistoryPage.checkProductsInOrderHistoryAgainstNeededItems(productsNeeded);
		
		Set<String> keySet = map.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			String item = iterator.next();
			Boolean match = map.get(item);
			Assert.assertTrue((boolean) match, item + " is not displayed in Order History");
		}
	}
	
	
}
