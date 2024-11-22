package API_Common_Methods;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class API_Trigger {

	static AllureRestAssured allureFilter = new AllureRestAssured().setRequestTemplate("my-http-request.ftl")
			.setResponseTemplate("my-http-response.ftl");

	@Attachment(value = "Response", type = "text/plain")
	private static String attachRequest(String request) {
		return request;

	}

	@Attachment(value = "Request", type = "text/plain")
	private static String attachResponse(String response) {
		return response;

	}

	@Attachment(value = "Request", type = "text/plain")
	private static String attachEndPoint(String endpoint) {
		return endpoint;

	}

	@Step("Post API Response")
	@Attachment(value = "Response", type = "text/plain")
	public static Response Post_trigger(String HeaderName, String HeaderValue, String reqbody, String Endpoint) {

		RequestSpecification requestSpec = RestAssured.given();

		requestSpec.header(HeaderName, HeaderValue);
		requestSpec.filter(allureFilter);
		requestSpec.body(reqbody);

		Response response = requestSpec.post(Endpoint);
		attachRequest(reqbody);
		attachResponse(response.getBody().asString());
		attachEndPoint(Endpoint);
		return response;

	}

	@Step("Put API Response")
	@Attachment(value = "Response", type = "text/plain")
	public static Response Put_trigger(String HeaderName, String HeaderValue, String reqbody, String Endpoint) {

		RequestSpecification requestSpec = RestAssured.given();

		requestSpec.header(HeaderName, HeaderValue);

		requestSpec.body(reqbody);
		requestSpec.filter(allureFilter);

		Response response = requestSpec.put(Endpoint);
		attachRequest(reqbody);
		attachResponse(response.getBody().asString());
		attachEndPoint(Endpoint);

		return response;

	}

	@Step("Patch API Response")
	@Attachment(value = "Response", type = "text/plain")
	public static Response Patch_trigger(String HeaderName, String HeaderValue, String reqbody, String Endpoint) {

		RequestSpecification requestSpec = RestAssured.given();

		requestSpec.header(HeaderName, HeaderValue);

		requestSpec.body(reqbody);
		requestSpec.filter(allureFilter);

		Response response = requestSpec.patch(Endpoint);
		attachRequest(reqbody);
		attachResponse(response.getBody().asString());
		attachEndPoint(Endpoint);

		return response;

	}

	@Step("Patch API Response")
	@Attachment(value = "Response", type = "text/plain")
	public static Response Get_trigger(String Endpoint) {

		RequestSpecification requestSpec = RestAssured.given();

		Response response = requestSpec.get(Endpoint);
		requestSpec.filter(allureFilter);
		attachResponse(response.getBody().asString());
		attachRequest(Endpoint);
		return response;

	}

}
