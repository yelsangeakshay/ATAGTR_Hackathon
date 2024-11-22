package API_Test_Class;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import API_Common_Methods.API_Trigger;
import Common_Utilities.TestNGRetryAnalyzer;
import Common_Utilities.Utility_Methods;
import API_POJO.Pojo_GET_API;
import API_POJO.Pojo_Post_API;
import Environment.API_Environment;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;

public class TestNG_Annotation_NestedJSON_POJO_GET_API_RestAssured_Example {
	String Endpoint;
	File dir_name;
	Response response;
	Map<String, Object> yamlData;
	Map<String, Object> getApiVariable;

	@BeforeClass
	public void testSetUp() throws IOException {
		// Create Log Directory in default project Directory
		dir_name = Utility_Methods.CreateLogDirectory("GET_API_TC1");
		// Read data variables yaml
		yamlData = Utility_Methods.readYamlFile("Resources/Variable.yaml");
		Assert.assertNotNull(yamlData, "YAML data should not be null");
		getApiVariable = (Map<String, Object>) yamlData.get("GET_API_Variable");
		Endpoint = (String) getApiVariable.get("Hostname") + (String) getApiVariable.get("Resource");
	}

	@Test(retryAnalyzer = TestNGRetryAnalyzer.class, description = "Validate Response Status code For Post_Test_Script_TC1")
	@Description("Validate Response Status code For Post_Test_Script_TC1")
	@Epic("Autothon 2024")
	@Feature("API Feature")
	@Severity(SeverityLevel.NORMAL)
	public void validate_status_code() {
		response = API_Trigger.Get_trigger(Endpoint);
		int statuscode = response.statusCode();
		Assert.assertEquals(statuscode, (int) getApiVariable.get("ExpectedStatusCode"),
				"Expected status code not found");
		Allure.addAttachment("statuscode", "text/*", "" + statuscode);
	}

	@Test(dependsOnMethods = {
			"validate_status_code" }, description = "Validate the Response body parameters of Get API Test Case 1")
	@Description("Validate the Response body parameters of Get API Test Case 1")
	@Epic("Autothon 2024")
	@Feature("API Feature")
	@Severity(SeverityLevel.NORMAL)
	public void validate_response_body() {
		Pojo_GET_API res_body = response.as(Pojo_GET_API.class);
		System.out.println(res_body.getPer_page());
		System.out.println(res_body.getTotal());
		System.out.println(res_body.getTotal_pages());
		System.out.println(res_body.getData().size());
		System.out.println(res_body.getSupport().getText());
		System.out.println(res_body.getSupport().getUrl());
	}

	@AfterClass
	public void evidenceCreator() throws IOException {
		Utility_Methods.evidenceFileCreator(
				Utility_Methods.testLogName("TestNG_Annotation_NestedJSON_POJO_GET_API_RestAssured_Example"), dir_name,
				Endpoint, "GetAPI Hence Request Body is not needed", response.getHeader("Date"),
				response.getBody().asString());
	}

}
