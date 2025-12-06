package practiceProject.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import practiceProject.abstractComponents.AbstractComponent;

public class OrderInfoPage extends AbstractComponent {
	
	WebDriver driver;
	
	public OrderInfoPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="tr.ng-star-inserted td.em-spacer-1 label")
	List<WebElement> orderIds;
	
	@FindBy(css="tr h1")
	WebElement confirmationMessage;
	
	public void grabOrderIds() {
		orderIds.stream().map(id->id.getText()).forEach(id->System.out.println(id));
	}

	public String getConfirmationMessage() {
		// TODO Auto-generated method stub
		return confirmationMessage.getText();
	}
	

}
