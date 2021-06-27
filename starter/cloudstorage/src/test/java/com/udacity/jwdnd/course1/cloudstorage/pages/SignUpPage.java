package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.boot.test.context.SpringBootTest;

public class SignUpPage {

    @FindBy(id = "inputFirstName")
    private WebElement userFirstName;

    @FindBy(id = "inputLastName")
    private WebElement userLastName;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    public SignUpPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void registerUser(User user){
        userFirstName.sendKeys(user.getFirstname());
        userLastName.sendKeys(user.getLastname());
        username.sendKeys(user.getUsername());
        password.sendKeys(user.getPassword());
        username.submit();
    }
}
