package Mobile_Common_Utility;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.time.Duration;

import java.util.Properties;

public class mobile_Common_Utilities {


        public static AndroidDriver driver=null;

        public static WebDriverWait wait= null;
        public void AndroidDriverSetup(DesiredCapabilities caps, String AppiumServerURL)
        {

//                caps.setCapability("appPackage","com.instagram.android");
//                caps.setCapability("appActivity","com.instagram.mainactivity.MainActivity");
            String Appium_Server_URL="http://127.0.0.1:4723/wd/hub";
            try
            {
                 driver = new AndroidDriver(new URL(Appium_Server_URL), caps);
                wait=new WebDriverWait(driver,Duration.ofSeconds(3));
            }
            catch(Exception e){
                System.out.println("Driver is not initiated");
                e.printStackTrace();
            }

        }
        public DesiredCapabilities appiumCapabilities(String Device_UDID,String appPackage, String appActivity)
        {
            DesiredCapabilities caps=null;

            try
            {
                caps = new DesiredCapabilities();
                caps.setCapability("platformName","Android");
                caps.setCapability("appium:udid",Device_UDID);
                caps.setCapability("appium:appPackage", appPackage);
                caps.setCapability("appium:appActivity", appActivity);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            System.out.println(caps.toString());
            return caps;
        }


//        public void appiumFluentWait(AndroidDriver androidDriver, By element)
//        {
//            wait= new FluentWait(androidDriver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(5))
//                    .ignoring(NoSuchElementException.class).ignoring(TimeoutException.class);
//
//            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
//        }

        public static String readPropertyFile(String key,String filepath)
        {

            File configFile_main = new File(System.getProperty("user.dir") + filepath);
            FileReader reader_main;
            String value=null;
            try {
                reader_main = new FileReader(configFile_main);
                Properties props_main = new Properties();
                props_main.load(reader_main);
                value= props_main.getProperty(key);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return value;

        }

    public void clickOnElement(By element)
    {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();

    }
    public void enterData(By element,String value)
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element)).sendKeys(value);
    }

    public void androidDriverWait(By element){
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            }catch(Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }


    }
    }


