package Mobile_Test_Class;

import Mobile_Common_Utility.mobile_Common_Method;
import Mobile_Common_Utility.mobile_Common_Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Mobile_Test_Class extends mobile_Common_Utilities{


    public String deviceUDID="ZD2222KLYZ";
    String appPackage="com.android.settings";
    String appActivity="com.android.settings.Settings";
    String appium_Server_URL="http://127.0.0.1:4723/wd/hub";

//    ####################################LOCATORS ######################################

    By appsLabel_input=By.xpath("//android.widget.AutoCompleteTextView[@resource-id=\"android:id/search_src_text\"]");
    By searchSetting_textBox=By.xpath("//android.widget.TextView[@text='Search settings']");
    By apps_Label=By.xpath("//android.widget.TextView[@text='Apps']");


//    ######################################## TESTS######################################################

    @BeforeTest
    public void startup()
    {
        System.out.println("test cases started");
    }
    @Test(description = "Verify User should be abel to Open Mobile setting and Change APN setting ")
   public void openBrowser() throws InterruptedException {
        DesiredCapabilities caps = appiumCapabilities(deviceUDID, appPackage, appActivity);
        AndroidDriverSetup(caps, appium_Server_URL);
        clickOnElement(searchSetting_textBox);
        enterData(appsLabel_input, "Apps");
        clickOnElement(apps_Label);

    }


    @AfterTest(description = "Verify user should clean data created during Automated Test")
    public void teardown()
    {if (driver != null) {
        driver.quit(); // Ensure the driver quits after the test
    }
    }
}
