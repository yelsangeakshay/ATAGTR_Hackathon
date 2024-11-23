package UI_Test_Class;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import static org.hamcrest.Matchers.lessThan;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Common_Utilities.Utility_Methods;
import Common_Utilities.TestNGRetryAnalyzer;
import UI_Common_Methods.InstantiateWebDriver;
import UI_Page_Objects.Koha_Home_Page_Object;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TC_Koha_Hackathon_3 {

	static WebDriver driver;
	File logDir;
	String testname;
	Koha_Home_Page_Object pageob;
	Map<String, Object> yamlData;
	Map<String, Object> getKohaVariable;

	@BeforeClass
	public void testSetUp() throws IOException, InterruptedException {
		// Set the test name
		testname = "TC_Koha_Hackathon_3";
		  String excelFilePath = "PatronCreationData.xlsx";
	        FileInputStream fis = new FileInputStream(new File(excelFilePath));
	        Workbook workbook = new XSSFWorkbook(fis);
	        Sheet sheet = workbook.getSheetAt(0);

	        // Iterate through rows
	        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	            Row row = sheet.getRow(i);

	            String creationMode = row.getCell(0).getStringCellValue();

	            // Generate random test data
	            String name = generateRandomString(8);
	            String email = name.toLowerCase() + "@example.com";
	            String phone = "9" + generateRandomDigits(9);
	            String libraryCard = generateLibraryCard("TML");

	            if (creationMode.equalsIgnoreCase("OPAC UI")) {
	                createViaOpacUI(driver, name, email, phone, libraryCard);
	            } else if (creationMode.equalsIgnoreCase("Librarian UI")) {
	                createViaLibrarianUI(driver, name, email, phone, libraryCard);
	            } else if (creationMode.equalsIgnoreCase("API")) {
	                createViaAPI(name, email, phone, libraryCard);
	            }

	            // Save data back to the Excel file
	            row.createCell(1).setCellValue(name);
	            row.createCell(2).setCellValue(email);
	            row.createCell(3).setCellValue(phone);
	            row.createCell(4).setCellValue(libraryCard);
	        }

	        // Save the updated Excel file
	        fis.close();
	        FileOutputStream fos = new FileOutputStream(new File(excelFilePath));
	        workbook.write(fos);
	        workbook.close();
	        fos.close();

	}

	@Test(retryAnalyzer = TestNGRetryAnalyzer.class, description = "Validate the URL Is Launched")
	@Description("Validate the URL Is Launched")
	@Epic("Autothon 2024")
	@Feature("WEB Feature")
	@Severity(SeverityLevel.NORMAL)
	public void launchURL() throws IOException {
		InstantiateWebDriver.launchURL(driver, (String) getKohaVariable.get("URL"), 200);
		pageob.SaveTextInWordFile(testname, "Koha Launched");
		pageob.SaveImageInWordFile(testname);
	}
	@Test(dependsOnMethods = { "launchURL" }, description = "Login to Web Page")
	@Description("Login")
	@Epic("Autothon 2024")
	@Feature("WEB Feature")
	@Severity(SeverityLevel.NORMAL)
	public void KohaTc() throws InterruptedException, IOException {
		String Username = (String) getKohaVariable.get("Username");
		String Password = (String) getKohaVariable.get("Password");
		String Code =  (String) getKohaVariable.get("librarycode");
		String Name =  (String) getKohaVariable.get("branchName");
		pageob.logintoPage(Username,Password,testname);
		pageob.AddLibrary(Code,Name,testname);
		
	}
	
	 // Generate a random string
    private static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }
    // Generate random digits
    private static String generateRandomDigits(int length) {
        String digits = "0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result.append(digits.charAt(random.nextInt(digits.length())));
        }
        return result.toString();
    }

    // Generate library card number
    private static String generateLibraryCard(String libraryInitials) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return libraryInitials + "_" + timestamp;
    }

    // Create Patron via OPAC UI
    private static void createViaOpacUI(WebDriver driver, String name, String email, String phone, String libraryCard) throws InterruptedException {
        driver.get("https://opac-library.com/registration"); // Replace with actual URL
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("phone")).sendKeys(phone);
        driver.findElement(By.id("libraryCard")).sendKeys(libraryCard);
        driver.findElement(By.id("submit")).click();
        Thread.sleep(2000); // Wait for processing
    }

    // Create Patron via Librarian UI
    private static void createViaLibrarianUI(WebDriver driver, String name, String email, String phone, String libraryCard) throws InterruptedException {
        driver.get("https://librarian-library.com/login"); // Replace with actual URL
        driver.findElement(By.id("username")).sendKeys("librarian"); // Replace with actual credentials
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("login")).click();

        driver.get("https://librarian-library.com/create-patron"); // Replace with actual URL
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("phone")).sendKeys(phone);
        driver.findElement(By.id("libraryCard")).sendKeys(libraryCard);
        driver.findElement(By.id("submit")).click();
        Thread.sleep(2000); // Wait for processing
    }

    // Create Patron via API
    private static void createViaAPI(String name, String email, String phone, String libraryCard) {
        RestAssured.baseURI = "https://api-library.com"; // Replace with actual base URI

        // Create JSON body
        String requestBody = "{"
                + "\"name\":\"" + name + "\","
                + "\"email\":\"" + email + "\","
                + "\"phone\":\"" + phone + "\","
                + "\"libraryCard\":\"" + libraryCard + "\""
                + "}";

        // Make API call
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("/create-patron");

        // Validate response
        response.then().statusCode(201); // Check status code
        response.then().time(lessThan(2000L)); // Check response time
        System.out.println("Response: " + response.asString());
    }
	
	
	
	
	
/*
	@Test(dependsOnMethods = { "launchURL" }, description = "Validate the Broken Links for Amazon Your Lists")
	@Description("Validate the Broken Links for Amazon Your Lists")
	@Epic("Autothon 2024")
	@Feature("WEB Feature")
	@Severity(SeverityLevel.NORMAL)
	public void validateListHyperlinks() throws InterruptedException, IOException {
		pageob.validate_Account_List_Hy_Links((String) getKohaVariable.get("List_To_Verify"), testname);
	}

	@AfterClass
	public void cleanup() {
		driver.quit();
	}
*/
}
