package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import io.appium.java_client.MobileElement;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTests extends BaseTest {
    LoginPage loginPage;
    ProductsPage productsPage;

    @BeforeClass
    public void beforeClass(){

    }
    @AfterClass
    public void afterClass(){

    }
    @BeforeMethod
    public void beforeMethod(){
        loginPage = new LoginPage();

    }
    @AfterMethod
    public void afterMethod(){

    }

    @Test
    public void invalidUserName(){
        loginPage.enterUserName("invalidusername");
        loginPage.enterPassword("secret_sauce");
        loginPage.tapLoginButton();

        String actualErrTxt = loginPage.getErrText();
        String expectedErrTxt = "Username and password do not match any user in this service.";

        Assert.assertEquals(actualErrTxt, expectedErrTxt);
    }

    @Test
    public void invalidPassword(){
        loginPage.enterUserName("standard_user");
        loginPage.enterPassword("invalidpassword");
        loginPage.tapLoginButton();

        String actualErrTxt = loginPage.getErrText();
        String expectedErrTxt = "Username and password do not match any user in this service.";

        Assert.assertEquals(actualErrTxt, expectedErrTxt);
    }

    @Test
    public void successLogin(){
        loginPage.enterUserName("standard_user");
        loginPage.enterPassword("secret_sauce");
        productsPage = loginPage.tapLoginButton();

        String actualProductTitle = productsPage.getTitle();
        String expectedProductTitle = "PRODUCTS";

        Assert.assertEquals(actualProductTitle, expectedProductTitle);
    }
}
