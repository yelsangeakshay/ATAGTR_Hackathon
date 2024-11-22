package UI_Test_Class;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Common_Utilities.TestNGRetryAnalyzer;
import Common_Utilities.Utility_Methods;
import UI_Common_Methods.InstantiateWebDriver;
import UI_Page_Objects.Amazon_Home_Page_Object;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class TC_Linkedin {
	
	WebDriver driver;
	File logDir;
	String testname;
	Amazon_Home_Page_Object pageob;
	Map<String, Object> yamlData;
	Map<String, Object> getLinkedINVariable;
	
	@BeforeTest
	public void testSetUp() throws IOException {
		// Set the test name
		testname = "TC_Amazon_Verify_Account_List_HyperLinks";
		// Read data variables yaml
		yamlData = Utility_Methods.readYamlFile("Resources/Variable.yaml");
		getLinkedINVariable = (Map<String, Object>) yamlData.get("LinkedIN_Variables");
		// Instantiate web driver
		driver = InstantiateWebDriver.InstantiateWebBrowser("ChromeHeadless");
		// Create Log Directory in default project Directory
		logDir = Utility_Methods.CreateLogDirectory(testname);
		// Create the object of page object class
		pageob = new Amazon_Home_Page_Object(driver, logDir);
		// Create word file log
		pageob.CreateWordLogFile(testname);
	}
	
	@Test(retryAnalyzer = TestNGRetryAnalyzer.class, description = "Validate the URL Is Launched")
	@Description("Validate the URL Is Launched")
	@Epic("Autothon 2024")
	@Feature("WEB Feature")
	@Severity(SeverityLevel.NORMAL)
	public void launchLinkedIN() throws IOException {
		InstantiateWebDriver.launchURL(driver, (String) getLinkedINVariable.get("URL"), 200);
		pageob.SaveTextInWordFile(testname, "Amazon Launched");
		pageob.SaveImageInWordFile(testname);
	}

}
