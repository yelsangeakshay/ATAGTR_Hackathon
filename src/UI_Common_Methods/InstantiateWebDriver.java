package UI_Common_Methods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;

import java.net.HttpURLConnection;
import java.net.URL;

public class InstantiateWebDriver {

	public static WebDriver InstantiateWebBrowser(String Browser) {

		WebDriver Driver;

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		System.out.println("driver started by instantiate method");

		switch (Browser) {
		case "Chrome":
			Driver = new ChromeDriver(options);
			System.out.println(Driver);
			break;
		case "Edge":
			Driver = new EdgeDriver();
			break;
		case "Firefox":
			Driver = new FirefoxDriver();
			break;
		case "ChromeHeadless":
			ChromeOptions coptions = new ChromeOptions();
			coptions.addArguments("--headless");
			Driver = new ChromeDriver(coptions);
			break;
		case "FirefoxHeadless":
			FirefoxOptions foptions = new FirefoxOptions();
			foptions.addArguments("--headless");
			Driver = new FirefoxDriver(foptions);
			break;
		case "EdgeHeadless":
			EdgeOptions eoptions = new EdgeOptions();
			eoptions.addArguments("--headless");
			Driver = new EdgeDriver(eoptions);
			break;
		default:
			Driver = new ChromeDriver();
		}
		return Driver;

	}

	public static void launchURL(WebDriver driver, String URL, int expstatuscode) {
		driver.get(URL);
		int statuscode = getHttpStatusCode(URL);
		Assert.assertEquals(statuscode, expstatuscode, "Webpage is not launched properly , since : " + statuscode
				+ " , is not equal to expected status code :" + expstatuscode);
	}

	public static int getHttpStatusCode(String urlString) {
		int statusCode = -1;
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			statusCode = connection.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusCode;
	}
}
