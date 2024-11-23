package UI_Page_Objects;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Common_Utilities.Utility_Methods;
import UI_Common_Methods.UI_Building_Blocks;

public class Koha_Home_Page_Object extends UI_Building_Blocks {

	WebDriver driver;
	File logDir;
	Map<String, Object> yamlLocators;
	Map<String, Object> getKohaLocators;

	public Koha_Home_Page_Object(WebDriver driver, File logDir) {
		super(driver, logDir);
		this.driver = driver;
		this.logDir = logDir;
		PageFactory.initElements(driver, this);
		// Read data variables yaml
		String absolutePath = "D:\\Autothon\\Hackathon_Git\\testautothon2024-main\\src\\Resources\\Locators.yaml";
		yamlLocators = Utility_Methods.readYamlFile(absolutePath);
		getKohaLocators = (Map<String, Object>) yamlLocators.get("Koha_Locators");
	}

	// @FindBy(xpath = (String)
	// getAmazonLocators.get("Amazon_Home_Account_List_Hy_Link"))

	public void validate_Account_List_Hy_Links(String ListType, String logFile)
			throws InterruptedException, IOException {
		WebElement AccountList = Wait_Until_Page_Contains_Element(
				(String) getKohaLocators.get("Amazon_Home_Account_List_Hy_Link"), 5);
		Actions_Mouse_Over(AccountList, 5);
		List<WebElement> Hyperlinks = Wait_Until_Page_Contains_Elements("//div[text()=\"" + ListType + "\"]/..//a", 5,
				5);
		System.out.println(Hyperlinks.size());
		Actions_Open_Link_New_Tab(Hyperlinks, 5);
		Thread.sleep(10000);
		validate_broken_links(logFile);
	}
	
	public void logintoPage(String userId,String password,String logFile)
			throws InterruptedException, IOException {
		//Fetch WebElements
		WebElement WB_username = Wait_Until_Page_Contains_Element(
				(String) getKohaLocators.get("userId"), 5);
		WebElement WB_password = Wait_Until_Page_Contains_Element(
				(String) getKohaLocators.get("password"), 5);
		WebElement WB_submitBtn = Wait_Until_Page_Contains_Element(
				(String) getKohaLocators.get("submitBtn"), 5);
		
		Input_Text(WB_username,userId,6);
		Input_Text(WB_password,password,6);
		Click_Element(WB_submitBtn,6);

		
	}
	
	public String getInitials(String input) {
		

        // Split the string into words based on spaces
        String[] words = input.split(" ");

        // StringBuilder to store the initials
        StringBuilder initials = new StringBuilder();

        // Loop through each word and extract the first character
        for (String word : words) {
            // Ensure the word is not empty (to handle extra spaces)
            if (!word.isEmpty()) {
                // Append the first character as uppercase to the initials
                initials.append(Character.toUpperCase(word.charAt(0)));
            }
        }
        return initials.toString();
	}
	public void AddLibrary(String code,String name,String logFile)
			throws InterruptedException, IOException {
		//Fetch WebElements
		WebElement adminBtn = Wait_Until_Page_Contains_Element(
				(String) getKohaLocators.get("adminBtn"), 5);
		Click_Element(adminBtn,6);
		WebElement libraryLabel = Wait_Until_Page_Contains_Element(
				(String) getKohaLocators.get("libraryLabel"), 5);
		Click_Element(libraryLabel,6);
		WebElement libraryBtn = Wait_Until_Page_Contains_Element(
				(String) getKohaLocators.get("newLibraryBtn"), 5);
		Click_Element(libraryBtn,6);
		
		WebElement Wb_branchName = Wait_Until_Page_Contains_Element(
				(String) getKohaLocators.get("branchName"), 5);
		
		Input_Text(Wb_branchName,name,10);
		
		WebElement Wb_branchCode = Wait_Until_Page_Contains_Element(
				(String) getKohaLocators.get("branchCode"), 5);
		String bCode = code + getInitials(name);
		Input_Text(Wb_branchCode,bCode,10);
		
		WebElement librarySubmit = Wait_Until_Page_Contains_Element(
				(String) getKohaLocators.get("libraryBtn"), 20);
		Scroll_To_WebElement(librarySubmit,15);
		Scroll_Click_Element(librarySubmit,20);
					
	}


public void creationofLibrary(String value,String logFile)
		throws InterruptedException, IOException {
	//Fetch WebElements
	WebElement WB_patronSearch = Wait_Until_Page_Contains_Element(
			(String) getKohaLocators.get("patronSearch"), 5);
	WebElement WB_password = Wait_Until_Page_Contains_Element(
			(String) getKohaLocators.get("password"), 5);
	WebElement WB_submitBtn = Wait_Until_Page_Contains_Element(
			(String) getKohaLocators.get("submitBtn"), 5);
	
	Input_Text(WB_patronSearch,value,6);
	//Input_Text(WB_password,password,6);
	//Click_Element(WB_submitBtn,6);
	
}
}
