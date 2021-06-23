package com.qa;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected static AppiumDriver driver;
    protected static Properties props;
    protected static String platform;
    InputStream inputStream;
 //constructor to initialize all the pages we define in pages
    public BaseTest(){

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Parameters({"platformName", "platformVersion", "deviceName"})
    @BeforeTest
    public void beforeTest(String platformName, String platformVersion, String deviceName) throws Exception {
        platform = platformName;
        URL url;
        props = new Properties();
        String propFileName="config.properties";
        inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        props.load(inputStream);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        caps.setCapability("platformVersion", platformVersion);
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);

        switch (platformName){
            case "Android":
                caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("androidAutomationName"));
                caps.setCapability("appPackage", props.getProperty("androidAppPackage"));
                caps.setCapability("appActivity", props.getProperty("androidAppActivity"));
                //URL androidAppUrl = getClass().getClassLoader().getResource(props.getProperty("androidApplocation"));
                String androidAppUrl = getClass().getResource(props.getProperty("androidApplocation")).getFile();
                caps.setCapability(MobileCapabilityType.APP, androidAppUrl);

                url = new URL(props.getProperty("appiumURL"));
                driver = new AndroidDriver(url, caps);
                break;

            case "iOS":
                caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("iOSAutomationName"));
                //URL iOSAppUrl = getClass().getClassLoader().getResource(props.getProperty("iOSApplocation"));
                String iOSAppUrl = getClass().getResource(props.getProperty("iOSAppLocation")).getFile();
                caps.setCapability(MobileCapabilityType.APP, iOSAppUrl);

                url = new URL(props.getProperty("appiumURL"));
                driver = new IOSDriver(url, caps);
                break;

            default:
                throw new Exception("Invalid Platform - " + platformName);

        }

    }
//this is method for explicit wait
    public void waitForVisibility(MobileElement e){
        WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void clearField(MobileElement e){
        waitForVisibility(e);
        e.clear();
    }
//then create the methods for the rest of the actions
    public void click(MobileElement e){
        waitForVisibility(e);
        e.click();
    }
    public void sendKeys(MobileElement e, String txt){
        waitForVisibility(e);
        e.sendKeys(txt);
    }
    public String getAttribute(MobileElement e, String attribute){
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }
    public String getText(MobileElement e){
        switch (platform){
            case "Android":
                return getAttribute(e,"text");
            case "iOS":
                return getAttribute(e,"label");
        }
        return null;
    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }
}
