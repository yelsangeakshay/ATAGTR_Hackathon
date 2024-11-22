package Common_Utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestNGRetryAnalyzer implements IRetryAnalyzer {

	private int countstart = 0;
	private int countend = 4;

	@Override
	public boolean retry(ITestResult result) {
		if (countstart < countend) {
			String testcasename = result.getName();
			System.out.println(testcasename + " failed in current iteration " + countstart + ", Hence retrying for "
					+ (countstart + 1));
			countstart ++;
			return true;
		}

		return false;
	}

}
