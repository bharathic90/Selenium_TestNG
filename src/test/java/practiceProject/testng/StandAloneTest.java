package practiceProject.testng;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandAloneTest {
	
	public static void main(String[] args) {
		
		String url = "https://rahulshettyacademy.com/client/#/auth/login";
		List<String> productsNeeded = Arrays.asList("ZARA COAT 3", "IPHONE 13 PRO");
		
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		driver.get(url);
		
		// enter user name and password, click login
		driver.findElement(By.id("userEmail")).sendKeys("bharbun@email.com");
		driver.findElement(By.id("userPassword")).sendKeys("Bharbun1");
		driver.findElement(By.id("login")).click();
		
		
		List<WebElement> productsNamesList = driver.findElements(By.xpath("//section[@id='products']//div[@class='row']//div[@class='card']//div[@class='card-body']//b"));
		List<WebElement> addToCartButtonElements = driver.findElements(By.xpath("//section[@id='products']//div[@class='row']//div[@class='card']//div[@class='card-body']//button[text()=' Add To Cart']"));

		int count = 0;		
		
		for(int i=0; i<productsNamesList.size(); i++) {
			String productName = productsNamesList.get(i).getText();
			System.out.println(productName);
			
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ngx-spinner-overlay")));
			
			if (productsNeeded.contains(productName)) {
				addToCartButtonElements.get(i).click();
				
				count++;
				String msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-message"))).getText();
				System.out.println(productName + " - " + msg);
				
				if (count == productsNeeded.size()) {
					break;
				}
				
			}
			
		}
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ngx-spinner-overlay")));
		

		
		// Approach using Java Streams instead of the above commented code - for adding items to cart (xx not working xx)
//		@Nullable
//		List<WebElement> productsList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//section[@id='products']//div[@class='row']//div[@class='card']//div[@class='card-body']")));
//		for(WebElement p : productsList) {
//			System.out.println(p);
//		}
//				
//		for(String item : productsNeeded) {
//			WebElement product = productsList.stream().filter(p->p.findElement(By.tagName("b")).getText().contains(item)).findFirst().orElse(null);
//			System.out.println(product);
//			String productName = product.findElement(By.tagName("b")).getText();
//			System.out.println(productName);
//			if(product != null) {
//				// then click on the element when it is ready to receive a click
//				wait.until(ExpectedConditions.elementToBeClickable(product.findElement(By.xpath("//button[text()=' Add To Cart']")))).click();
//				//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Add To Cart']"))).click();
//				//product.findElement(By.xpath("//button[text()=' Add To Cart']")).click();
//				
//				String msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-message"))).getText();
//				System.out.println(productName + " - " + msg);
//			}
//			// wait until the overlay (which blocks all clicks) is gone
//			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("ngx-spinner-overlay")));
//		}
		
		
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-custom' and contains(text(),'Cart')]"))).click();
		
		List<WebElement> cartItems = driver.findElements(By.cssSelector(".cartSection h3"));
		
//		for (WebElement cartItem : cartItems) {
//			String item = cartItem.getText();
//			if(productsNeeded.contains(item)) {
//				System.out.println(item + " is present in cart");
//			}
//			else {
//				System.out.println(item + " is not present in cart");
//			}
//		}
		
		for(String item : productsNeeded) {
			boolean match = cartItems.stream().anyMatch(c->c.getText().contains(item));
			Assert.assertTrue(match, item + " is not added to cart");
		}
		
		// Scroll the page and click checkout
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,600)");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".totalRow button"))).click();
		//driver.findElement(By.cssSelector(".totalRow button")).click();
		
		// enter the country and place order
		
		driver.findElement(By.cssSelector("input[placeholder='Select Country']")).sendKeys("unit");
		List<WebElement> countrySearchElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//section[@class='ta-results list-group ng-star-inserted']//button")));
		WebElement matchingCountry = countrySearchElements.stream().filter(country->country.findElement(By.tagName("span")).getText().contains("United States")).findFirst().orElse(null);
		matchingCountry.click();
		driver.findElement(By.className("action__submit")).click();
		
		// grab the order id's
		List<WebElement> orderIds = driver.findElements(By.cssSelector("tr.ng-star-inserted td.em-spacer-1 label"));
		orderIds.stream().map(id->id.getText()).forEach(id->System.out.println(id));
		
		
		driver.quit();
	}

}
