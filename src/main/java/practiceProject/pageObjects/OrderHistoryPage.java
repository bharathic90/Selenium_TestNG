package practiceProject.pageObjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import practiceProject.abstractComponents.AbstractComponent;

public class OrderHistoryPage extends AbstractComponent {
	
	WebDriver driver;

	public OrderHistoryPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//tbody//td[2]")
	List<WebElement> productNames;
	
	
	public Map<String, Boolean> checkProductsInOrderHistoryAgainstNeededItems(List<String> productsNeeded) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for(String item : productsNeeded) {
			boolean match = productNames.stream().anyMatch(n->n.getText().equalsIgnoreCase(item));
			map.put(item, match);
		}
		return map;
	}
	

}
