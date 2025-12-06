package practiceProject.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import practiceProject.abstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent {
	
	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="input[placeholder='Select Country']")
	WebElement countryAutoSuggestiveDropDown;
	
	@FindBy(xpath="//section[@class='ta-results list-group ng-star-inserted']//button")
	List<WebElement> countrySearchElements;
	
	@FindBy(className="action__submit")
	WebElement placeOrderButton;
	
	By countryDropDownSuggestions = By.xpath("//section[@class='ta-results list-group ng-star-inserted']//button");
	By desiredCountryLocator = By.tagName("span");
	
	public void chooseCountry(String searchKeyword, String desiredCountry) {
		countryAutoSuggestiveDropDown.sendKeys(searchKeyword);
		waitForAllElementsToAppear(countryDropDownSuggestions);
		
		WebElement matchingCountry = countrySearchElements.stream().filter(country->country.findElement(desiredCountryLocator).getText().contains(desiredCountry)).findFirst().orElse(null);
		matchingCountry.click();
	}
	
	public OrderInfoPage placeOrder() {
		placeOrderButton.click();
		return new OrderInfoPage(driver);
	}

}
