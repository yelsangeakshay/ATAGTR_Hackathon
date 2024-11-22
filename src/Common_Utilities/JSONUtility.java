package Common_Utilities;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JSONUtility {

	public static Map<String, String> readJSON(String jsonName) {

		File source_json = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\" + jsonName + ".json");
		String testMethodName = GlobalVariables.getVariable("TestMethodName");
		Map<String, String> map = new HashMap<String, String>();
		Object obj = null;

		try {
			obj = new JSONParser().parse(new FileReader(source_json));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		JSONObject jo = (JSONObject) obj;
		JSONObject ja = (JSONObject) jo.get(testMethodName);

		Set<String> keys = ja.keySet();

		for (String key : keys) {
			String value = ja.get(key).toString();
			map.put(key, value);
		}
		return map;
	}

	private static Map<String, String> readTestDataJSON() {
		return readJSON(GlobalVariables.getVariable("TestDataRepo"));
	}

	private static Map<String, String> readLocatorJSON() {
		return readJSON(GlobalVariables.getVariable("LocatorRepo"));
	}

	static Map<String, Map<String, String>> testDataMap = new HashMap<String, Map<String, String>>();
	static Map<String, Map<String, String>> locatorMap = new HashMap<String, Map<String, String>>();

	public static Map<String, Map<String, String>> setTestDataMap() {
		testDataMap.put(GlobalVariables.getVariable("TestMethodName"), readTestDataJSON());
		return testDataMap;
	}
	
	public static Map<String, Map<String, String>> setLocatorMap() {
		locatorMap.put(GlobalVariables.getVariable("TestMethodName"), readLocatorJSON());
		return locatorMap;
	}

	public static String getTestDataValue(String key) {
		return testDataMap.get(GlobalVariables.getVariable("TestMethodName")).get(key);
	}

	public static String getLocatorValue(String key) {
		return locatorMap.get(GlobalVariables.getVariable("TestMethodName")).get(key);
	}
}
