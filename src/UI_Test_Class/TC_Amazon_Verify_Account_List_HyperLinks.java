package UI_Test_Class;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Common_Utilities.Utility_Methods;
import Common_Utilities.TestNGRetryAnalyzer;
import UI_Common_Methods.InstantiateWebDriver;
import UI_Page_Objects.Amazon_Home_Page_Object;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class TC_Amazon_Verify_Account_List_HyperLinks {

	static WebDriver driver;
	File logDir;
	String testname;
	Amazon_Home_Page_Object pageob;
	Map<String, Object> yamlData;
	Map<String, Object> getAmazonVariable;

	@BeforeClass
	public void testSetUp() throws IOException {
		// Set the test name
		testname = "TC_Amazon_Verify_Account_List_HyperLinks";
		// Read data variables yaml
		String absolutePath = "D:\\Autothon\\Hackathon_Git\\testautothon2024-main\\src\\Resources\\Variable.yaml";
		yamlData = Utility_Methods.readYamlFile(absolutePath);
		getAmazonVariable = (Map<String, Object>) yamlData.get("Amazon_Variables");
		// Instantiate web driver
		driver = InstantiateWebDriver.InstantiateWebBrowser("Chrome");
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
	public void launchAmazon() throws IOException {
		InstantiateWebDriver.launchURL(driver, (String) getAmazonVariable.get("URL"), 200);
		pageob.SaveTextInWordFile(testname, "Amazon Launched");
		pageob.SaveImageInWordFile(testname);
	}

	@Test(dependsOnMethods = { "launchAmazon" }, description = "Validate the Broken Links for Amazon Your Lists")
	@Description("Validate the Broken Links for Amazon Your Lists")
	@Epic("Autothon 2024")
	@Feature("WEB Feature")
	@Severity(SeverityLevel.NORMAL)
	public void validateListHyperlinks() throws InterruptedException, IOException {
		pageob.validate_Account_List_Hy_Links((String) getAmazonVariable.get("List_To_Verify"), testname);
	}

	@AfterClass
	public void cleanup() {
		driver.quit();
	}

}
