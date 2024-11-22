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

public class Amazon_Home_Page_Object extends UI_Building_Blocks {

	WebDriver driver;
	File logDir;
	Map<String, Object> yamlLocators;
	Map<String, Object> getAmazonLocators;

	public Amazon_Home_Page_Object(WebDriver driver, File logDir) {
		super(driver, logDir);
		this.driver = driver;
		this.logDir = logDir;
		PageFactory.initElements(driver, this);
		// Read data variables yaml
		String absolutePath = "D:\\Autothon\\Hackathon_Git\\testautothon2024-main\\src\\Resources\\Locators.yaml";
		yamlLocators = Utility_Methods.readYamlFile(absolutePath);
		getAmazonLocators = (Map<String, Object>) yamlLocators.get("Amazon_Locators");
	}

	// @FindBy(xpath = (String)
	// getAmazonLocators.get("Amazon_Home_Account_List_Hy_Link"))

	public void validate_Account_List_Hy_Links(String ListType, String logFile)
			throws InterruptedException, IOException {
		WebElement AccountList = Wait_Until_Page_Contains_Element(
				(String) getAmazonLocators.get("Amazon_Home_Account_List_Hy_Link"), 5);
		Actions_Mouse_Over(AccountList, 5);
		List<WebElement> Hyperlinks = Wait_Until_Page_Contains_Elements("//div[text()=\"" + ListType + "\"]/..//a", 5,
				5);
		System.out.println(Hyperlinks.size());
		Actions_Open_Link_New_Tab(Hyperlinks, 5);
		Thread.sleep(10000);
		validate_broken_links(logFile);
	}

}
