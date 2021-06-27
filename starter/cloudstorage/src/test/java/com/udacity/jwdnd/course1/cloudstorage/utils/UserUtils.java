package com.udacity.jwdnd.course1.cloudstorage.utils;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import org.openqa.selenium.WebDriver;

public class UserUtils {

    public static void signupAndLoginUser(WebDriver driver, int port){
        driver.get("http://localhost:" + port + "/signup");
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.registerUser(new User(null, "ak2020", null, "Hello1234", "Akhil", "Tiwari"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginUser("ak2020", "Hello1234");
    }
}
