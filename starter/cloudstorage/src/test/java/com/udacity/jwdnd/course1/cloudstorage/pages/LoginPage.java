package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "inputUsername")
    private WebElement loginUsername;

    @FindBy(id = "inputPassword")
    private WebElement loginPassword;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void loginUser(String username, String password){
        loginUsername.sendKeys(username);
        loginPassword.sendKeys(password);
        loginUsername.submit();
    }
}
