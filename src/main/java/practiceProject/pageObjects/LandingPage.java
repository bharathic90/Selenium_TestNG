package practiceProject.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import practiceProject.abstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {
	
	// PageObject classes can contain only variables and action methods, and cannot have any hard coded values/Assert validations.
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver){
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//driver.findElement(By.id("userEmail")).sendKeys("bharbun@email.com");
	//driver.findElement(By.id("userPassword")).sendKeys("Bharbun1");
	//driver.findElement(By.id("login")).click();
	
	//The above 3 lines can be rewritten using PageFactory model as below

	// PageFactory
	@FindBy(id="userEmail")
	WebElement username;				// This will implicitly use the driver & construct the code like driver.findElement(...)
										// by initElements() in constructor by using the driver
	@FindBy(id="userPassword")
	WebElement password;
	
	@FindBy(id="login")
	WebElement submitButton;
	
	@FindBy(className="toast-message")
	WebElement toastMessage;
	
	public void goTo(String url) {
		driver.get(url);
	}
	
	// defining action class
	public ProductCatalogue loginApplication(String userId, String pwd) {
		username.sendKeys(userId);
		password.sendKeys(pwd);
		submitButton.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}
	
	
	public String getErrorMessage() {
		waitForWebElementToAppear(toastMessage);
		return toastMessage.getText();
	}
	
	
	
	
}
