package practiceProject.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import practiceProject.pageObjects.LandingPage;

public class BaseTest {
	
	String url = "https://rahulshettyacademy.com/client/#/auth/login";
	public WebDriver driver;
	public LandingPage landingPage;
	public static Properties prop;			// properties is read-only here. So, static is safe
	
//	@BeforeSuite(alwaysRun=true)		// loading config once rather than for each test will improve the performance
	public void loadConfig() throws IOException {
		
		prop = new Properties();
		
		//FileInputStream fis = new FileInputStream("C:\\Users\\bhara\\eclipse-workspace\\Training2\\PracticeProject-SeleniumTestNG\\src\\main\\resources\\globalData.properties");
		// specifying the path of the file as above is discouraged since it works only in the local and 
		// if somebody else uses this code that line breaks.
				
		// To overcome this,
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\globalData.properties");
		// Here, user.dir -> will get the path until project & System.getProperty() -> will return the path
				
		prop.load(fis);				// this loads all the properties from the file & it accepts FileInputStream Object
		System.out.println("Properties loaded");
	}
	
	
	public List<HashMap<String,String>> loadJsonTestData(File file) throws IOException {
		String fileToString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		
		// needs Jackson DataBind dependency to be added in pom.xml for the below lines if needed
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> dataList = mapper.readValue(fileToString, new TypeReference<List<HashMap<String, String>>>() {});
		return dataList;
	}
	
	
	
	public String fetchProperty(String propertyName) throws IOException {
		loadConfig(); 					//load config first in order to get the value
		String mavenPropertyValue = System.getProperty("browser");
		String propertyValue = mavenPropertyValue!=null ? mavenPropertyValue : prop.getProperty(propertyName);		// using ternary operator
		// above line checks if any global data sent via maven commands, if so, it uses that else, it uses the data in the properties file
		System.out.println("property fetched");
		return propertyValue;
	}
	
	public void initializeDriver() throws IOException {
		
				
		String browserName = fetchProperty("Browser");
		
		if(browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			
			if(browserName.contains("headless")){
				options.addArguments("headless");
			}
			
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440,900));		// works on fullscreen so that all elements are fitted to the page & clickable
			System.out.println("using chrome");
		}
		else if(browserName.equalsIgnoreCase("edge")){
			driver = new EdgeDriver();
			System.out.println("using edge");
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			System.out.println("using firefox");
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	// alwaysRun=true is mandatory in case of execution requirements by specific groups. 
	// without it, even @BeforeMethod & @AfterMethod will not included in execution. 
	// Trying to skip this will lead to failures.
	
	@BeforeMethod(alwaysRun=true)	
	public LandingPage launchApplication() throws IOException {
		System.out.println("@BeforeMethod is called");
		//String url = fetchProperty("URL");
		initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo(url);
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() {
		driver.quit();
	}
	
	

}
