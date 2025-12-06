package practiceProject.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/main/resources", glue="practiceProject.cucumber", monochrome=true, tags="@Regression", plugin= {"html:reports/cucumber.html"})
public class TestNGCucumberTestRunner extends AbstractTestNGCucumberTests {

}

//Here, We need to provide many helper attributes annotated with @CucumberOptions
//features	- holds the path of the package that contains .feature file
//glue		- holds the path of the package that contains the StepDefinition class
//monochrome	- setting it to true will generate the report with more readability
//plugin		- holds the key: value pairs in which, key is the report format and           
//               value is the location of where the report needs to be present

// By default TestNG will not be able to run this TestRunner with Cucumber feature file
// To make it happen, we need extend the AbstractTestNGCucumberTests class
