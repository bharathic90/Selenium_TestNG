package practiceProject.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import practiceProject.abstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {
	
//	List<WebElement> productsNamesList = driver.findElements(By.xpath("//section[@id='products']//div[@class='row']//div[@class='card']//div[@class='card-body']//b"));
//	List<WebElement> addToCartButtonElements = driver.findElements(By.xpath("//section[@id='products']//div[@class='row']//div[@class='card']//div[@class='card-body']//button[text()=' Add To Cart']"));

	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//section[@id='products']//div[@class='row']//div[@class='card']//div[@class='card-body']//b")
	List<WebElement> productsNamesList;
	
	@FindBy(xpath="//section[@id='products']//div[@class='row']//div[@class='card']//div[@class='card-body']//button[text()=' Add To Cart']")
	List<WebElement> addToCartButtonElements;
	
	@FindBy(className="toast-message")
	WebElement toastMessage;
	
	
	By spinnerOverlayLocator = By.className("ngx-spinner-overlay");
	By toastMessageLocator = By.className("toast-message");
	
	
	public List<WebElement> getProducts() {
		return productsNamesList;
	}
	
	public List<WebElement> grabCartButtons(){
		return addToCartButtonElements;
	}
	
	public void addProductsToCart(List<String> productsNeeded) {
		int count = 0;		
		
		for(int i=0; i<productsNamesList.size(); i++) {
			String productName = productsNamesList.get(i).getText();
			System.out.println(productName);
			
			waitForElementToDisappear(spinnerOverlayLocator);
			
			if (productsNeeded.contains(productName)) {
				addToCartButtonElements.get(i).click();
				
				count++;
				waitForElementToAppear(toastMessageLocator);
				String msg = toastMessage.getText();
				System.out.println(productName + " - " + msg);
				
				if (count == productsNeeded.size()) {
					break;
				}
			}
		}
		
		waitForElementToDisappear(spinnerOverlayLocator);
	}

	
}
