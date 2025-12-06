package practiceProject.abstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import practiceProject.pageObjects.CartPage;
import practiceProject.pageObjects.OrderHistoryPage;

public class AbstractComponent {
	
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	
	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.js = (JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="button[routerlink='/dashboard/cart']")
	WebElement cartLink;
	
	@FindBy(css="button[routerlink='/dashboard/myorders']")
	WebElement ordersLink;
	
	By cartLinkLocator = By.cssSelector("button[routerlink='/dashboard/cart']");
	By ordersLinkLocator = By.cssSelector("button[routerlink='/dashboard/myorders']");
	
	public void waitForElementToDisappear(By locator) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	
	public void waitForElementToAppear(By locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void waitForWebElementToAppear(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForAllElementsToAppear(By locator) {
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	public void waitForElementToBeClickable(By locator) {
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	public void scrollPageDown(int yCoordinate) {
		js.executeScript("window.scrollTo(0," + yCoordinate + ")");
	}
	
	public CartPage viewCart() {
		waitForElementToBeClickable(cartLinkLocator);
		cartLink.click();
		return new CartPage(driver);
	}
	
	public OrderHistoryPage viewOrders() {
		waitForElementToBeClickable(ordersLinkLocator);
		ordersLink.click();
		return new OrderHistoryPage(driver);
	}

}
