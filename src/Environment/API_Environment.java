package Environment;

public class API_Environment {

	public static String Hostname() {
		String hostname = "https://reqres.in/";
		return hostname;
	}

	public static String Resource() {
		String resource = "api/users";
		return resource;
	}

	public static String HeaderName() {

		String headername = "Content-Type";
		return headername;
	}

	public static String HeaderValue() {

		String headervalue = "application/json";
		return headervalue;
	}
	
	public static String GetAPIResource() {
		String resource = "api/users?page=2";
		return resource;
	}

}
