package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {
    WebDriver driver;

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    // Notes page specific buttons
    @FindBy(id = "nav-notes-tab")
    private WebElement navNotes;

    @FindBy(id = "add-notes-button")
    private WebElement addNotesButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInModal;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInModal;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmitButton;

    // Credentials Page specific Buttons
    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentials;

    @FindBy(id = "add-credentials-button")
    private WebElement addCredentialsButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlInModal;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInModal;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordInModal;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmitButton;

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void logoutUser(){
        logoutButton.submit();
    }

    public void createNotes(String noteTitle, String noteDescription){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.navNotes);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.addNotesButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + noteTitle + "';", this.noteTitleInModal);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + noteDescription + "';", this.noteDescriptionInModal);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.noteSubmitButton);
    }

    public Note getSavedNote(){
        WebDriverWait wait = new WebDriverWait(driver, 1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.navNotes);
        List<WebElement> noteTitles = wait.until(driver -> driver.findElements(By.className("saved-note-title")));
        if(noteTitles.size() > 0)
            wait.until(ExpectedConditions.elementToBeClickable(noteTitles.get(0))).getText();
        List<WebElement> noteDescriptions = wait.until(driver -> driver.findElements(By.className("saved-note-description")));
        if(noteTitles != null && noteTitles.size() > 0 && noteDescriptions != null && noteDescriptions.size() > 0){
            return new Note(null, noteTitles.get(0).getText(), noteDescriptions.get(0).getText(), null);
        }
        return null;
    }

    public void editNotes(String newNoteTitle, String newNoteDescription){
        WebDriverWait wait = new WebDriverWait(driver, 1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.navNotes);
        List<WebElement> editButtons = wait.until(driver -> driver.findElements(By.className("edit-note-button")));
        wait.until(ExpectedConditions.elementToBeClickable(editButtons.get(0)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButtons.get(0));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + newNoteTitle + "';", this.noteTitleInModal);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + newNoteDescription + "';", this.noteDescriptionInModal);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.noteSubmitButton);
    }

    public void deleteNotes(){
        WebDriverWait wait = new WebDriverWait(driver, 1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.navNotes);
        List<WebElement> deleteButtons = wait.until(driver -> driver.findElements(By.className("delete-note-button")));
        wait.until(ExpectedConditions.elementToBeClickable(deleteButtons.get(0)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButtons.get(0));
    }

    public void createCredential(String url, String username, String password){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.navCredentials);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.addCredentialsButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + url + "';", this.credentialUrlInModal);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", this.credentialUsernameInModal);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", this.credentialPasswordInModal);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.credentialSubmitButton);
    }

    public String getViewablePassword(){
        WebDriverWait wait = new WebDriverWait(driver, 1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.navCredentials);
        List<WebElement> editCredentialButtons = wait.until(driver -> driver.findElements(By.className("edit-credential-button")));
        wait.until(ExpectedConditions.elementToBeClickable(editCredentialButtons.get(0)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editCredentialButtons.get(0));
        wait.until(ExpectedConditions.elementToBeClickable(credentialPasswordInModal));
        return credentialPasswordInModal.getText();
    }

    public Credentials getSavedCredential(){
        WebDriverWait wait = new WebDriverWait(driver, 1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.navCredentials);
        List<WebElement> credentialUrls = wait.until(driver -> driver.findElements(By.className("saved-credential-url")));
        if(credentialUrls.size() > 0)
            wait.until(ExpectedConditions.elementToBeClickable(credentialUrls.get(0))).getText();
        List<WebElement> credentialUsernames = wait.until(driver -> driver.findElements(By.className("saved-credential-username")));
        List<WebElement> credentialPasswords = wait.until(driver -> driver.findElements(By.className("saved-credential-password")));
        if(credentialUrls != null && credentialUrls.size() > 0 && credentialUsernames != null && credentialUsernames.size() > 0 && credentialPasswords != null && credentialPasswords.size() > 0){
            return new Credentials(null, credentialUrls.get(0).getText(), credentialUsernames.get(0).getText(), null, credentialPasswords.get(0).getText(), null);
        }
        return null;
    }

    public void editCredentials(String newUrl, String newUsername, String newPassword) {
        WebDriverWait wait = new WebDriverWait(driver, 1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.navCredentials);
        List<WebElement> editCredentialButtons = wait.until(driver -> driver.findElements(By.className("edit-credential-button")));
        wait.until(ExpectedConditions.elementToBeClickable(editCredentialButtons.get(0)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editCredentialButtons.get(0));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + newUrl + "';", this.credentialUrlInModal);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + newUsername + "';", this.credentialUsernameInModal);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + newPassword + "';", this.credentialPasswordInModal);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.credentialSubmitButton);
    }

    public void deleteCredentials(){
        WebDriverWait wait = new WebDriverWait(driver, 1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.navCredentials);
        List<WebElement> deleteButtons = wait.until(driver -> driver.findElements(By.className("delete-credential-button")));
        wait.until(ExpectedConditions.elementToBeClickable(deleteButtons.get(0)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButtons.get(0));
    }
}
