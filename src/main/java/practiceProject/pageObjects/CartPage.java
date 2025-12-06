package practiceProject.pageObjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import practiceProject.abstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {
	
	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".cartSection h3")
	List<WebElement> cartItems;
	
	@FindBy(css=".totalRow button")
	WebElement checkoutButton;
	
	By checkoutButtonLocator = By.cssSelector(".totalRow button");
	
	public Map<String, Boolean> checkCartItemsAgainstNeededItems(List<String> productsNeeded) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for(String item : productsNeeded) {
			boolean match = cartItems.stream().anyMatch(c->c.getText().contains(item));
			map.put(item, match);
		}
		return map;
	}
	
	public CheckoutPage clickCheckout() {
		scrollPageDown(900);					// yCoordinate - 500 is hard coded here which should not be done
		waitForElementToBeClickable(checkoutButtonLocator);
		checkoutButton.click();
		return new CheckoutPage(driver);
	}

}
