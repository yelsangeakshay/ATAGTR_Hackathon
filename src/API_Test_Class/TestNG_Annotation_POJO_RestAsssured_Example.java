package API_Test_Class;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
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
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import API_POJO.Pojo_Post_API;

public class TestNG_Annotation_POJO_RestAsssured_Example {

	String requestBody;
	String Endpoint;
	File dir_name;
	Response response;
	ObjectMapper objectMapper;
	API_POJO.Pojo_Post_API pojoobject;
	Map<String, Object> yamlData;
	Map<String, Object> postApiVariable;

	@BeforeClass
	public void testSetUp() throws IOException {
		objectMapper = new ObjectMapper();
		pojoobject = new Pojo_Post_API();
		System.out.println("Before Test Method Called");
		dir_name = Utility_Methods.CreateLogDirectory("Post_API_Logs");
		// Read data variables yaml
		yamlData = Utility_Methods.readYamlFile("Resources/Variable.yaml");
		Assert.assertNotNull(yamlData, "YAML data should not be null");
		postApiVariable = (Map<String, Object>) yamlData.get("POST_API_Variable");
		Endpoint = (String) postApiVariable.get("Hostname") + (String) postApiVariable.get("Resource");
		pojoobject.setName((String) postApiVariable.get("Req_Body_Name"));
		pojoobject.setJob((String) postApiVariable.get("Req_Body_Job"));
		requestBody = objectMapper.writeValueAsString(pojoobject);
		System.out.println(requestBody);
	}

	@Test(retryAnalyzer = TestNGRetryAnalyzer.class, description = "Validate the response body parameters of Post API Test Case 1")
	public void validator() {
		response = API_Trigger.Post_trigger((String) postApiVariable.get("Headername"),
				(String) postApiVariable.get("Headervalue"), requestBody, Endpoint);

		int statuscode = response.statusCode();

		System.out.println(response.getBody().asString());

		Pojo_Post_API res_body = response.as(Pojo_Post_API.class);

		String res_name = res_body.getName();
		String res_job = res_body.getJob();
		String res_id = res_body.getid();
		String res_createdAt = res_body.getcreatedAt();
		res_createdAt = res_createdAt.substring(0, 11);

		// Set the expected results
		JsonPath jsp_req = new JsonPath(requestBody);
		String req_name = jsp_req.getString("name");
		String req_job = jsp_req.getString("job");

		LocalDateTime currentdate = LocalDateTime.now();
		String expecteddate = currentdate.toString().substring(0, 11);

		// Validate the response parameters
		Assert.assertEquals(response.statusCode(), 201);
		Assert.assertEquals(res_name, req_name);
		Assert.assertEquals(res_job, req_job);
		Assert.assertNotNull(res_id);
		Assert.assertEquals(res_createdAt, expecteddate);
	}

	@AfterClass
	public void evidenceCreator() throws IOException {
		System.out.println("After Test Method Called");
		Utility_Methods.evidenceFileCreator(Utility_Methods.testLogName("Test_Case_1"), dir_name, Endpoint, requestBody,
				response.getHeader("Date"), response.getBody().asString());
	}

}
