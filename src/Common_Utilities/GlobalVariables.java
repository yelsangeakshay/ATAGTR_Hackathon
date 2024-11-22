package Common_Utilities;

import java.util.HashMap;

public class GlobalVariables {
	
	static HashMap<String, String> variable = new HashMap<String, String>();
	
	public static void setVariable(String varName, String varValue) {
		variable.put(varName+Thread.currentThread().getId(), varValue);
	}
	
	public static String getVariable(String varName) {
		return variable.get(varName+Thread.currentThread().getId());
	}

}
