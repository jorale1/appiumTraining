package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginPage extends BaseTest {
    //locate page elements
    @AndroidFindBy (accessibility = "test-Username")
    @iOSXCUITFindBy(id = "test-Username")
    private MobileElement usernameTxtfld;

    @AndroidFindBy (accessibility = "test-Password")
    @iOSXCUITFindBy(id = "test-Password")
    private MobileElement passwordTxtfield;

    @AndroidFindBy (accessibility = "test-LOGIN")
    @iOSXCUITFindBy(id = "test-LOGIN")
    private MobileElement loginBttn;

    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Error message\"]/child::XCUIElementTypeStaticText")
    private MobileElement errTxt;

    public LoginPage enterUserName(String username){
        clearField(usernameTxtfld);
        sendKeys(usernameTxtfld, username);
        return this;
    }

    public LoginPage enterPassword(String password){
        clearField(passwordTxtfield);
        sendKeys(passwordTxtfield, password);
        return this;
    }

    public ProductsPage tapLoginButton(){
        click(loginBttn);
        return new ProductsPage();
    }

    public String getErrText(){

        return getText(errTxt);
    }
}
