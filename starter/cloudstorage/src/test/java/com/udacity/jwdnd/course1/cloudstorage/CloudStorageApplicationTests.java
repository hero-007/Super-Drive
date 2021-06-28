package com.udacity.jwdnd.course1.cloudstorage;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import com.udacity.jwdnd.course1.cloudstorage.utils.UserUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void unauthorizedUserCannotAccessHomePage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void simpleUserSignUpLoginLogoutFlow() {
		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signUpPage = new SignUpPage(driver);
		Assertions.assertEquals("Sign Up", driver.getTitle());
		signUpPage.registerUser(new User(null, "ak2020", null, "Hello1234", "Akhil", "Tiwari"));
		Assertions.assertEquals("Login", driver.getTitle());
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginUser("ak2020", "Hello1234");
		Assertions.assertEquals("Home", driver.getTitle());
		HomePage homePage = new HomePage(driver);
		homePage.logoutUser();
		driver.get("http://localhost:" + this.port + "/");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testNotesCreation() {
		UserUtils.signupAndLoginUser(driver, port);
		driver.get("http://localhost:" + this.port + "/");
		HomePage homePage = new HomePage(driver);
		homePage.createNotes("Sample Note Title", "Sample Note Description");
		Note note = homePage.getSavedNote();
		Assertions.assertNotNull(note);
		Assertions.assertEquals("Sample Note Title", note.getNoteTitle());
		Assertions.assertEquals("Sample Note Description", note.getNoteDescription());
	}

	@Test
	public void testNoteEdit() {
		UserUtils.signupAndLoginUser(driver, port);
		driver.get("http://localhost:" + this.port + "/");
		HomePage homePage = new HomePage(driver);
		homePage.createNotes("Sample Note Title", "Sample Note Description");
		homePage.editNotes("New note title", "New note description");
		Note note = homePage.getSavedNote();
		Assertions.assertNotNull(note);
		Assertions.assertEquals("New note title", note.getNoteTitle());
		Assertions.assertEquals("New note description", note.getNoteDescription());
	}

	@Test
	public void testNoteDelete(){
		UserUtils.signupAndLoginUser(driver, port);
		driver.get("http://localhost:" + this.port + "/");
		HomePage homePage = new HomePage(driver);
		homePage.createNotes("Sample Note Title", "Sample Note Description");
		Note note = homePage.getSavedNote();
		Assertions.assertNotNull(note);
		homePage.deleteNotes();
		Note noteDeleted = homePage.getSavedNote();
		Assertions.assertNull(noteDeleted);
	}

	@Test
	public void testCredentialCreation(){
		UserUtils.signupAndLoginUser(driver, port);
		driver.get("http://localhost:" + this.port + "/");
		HomePage homePage = new HomePage(driver);
		homePage.createCredential("http://abc.com", "username", "password");
		Credentials createdCredentials = homePage.getSavedCredential();
		Assertions.assertNotNull(createdCredentials);
		Assertions.assertEquals("http://abc.com", createdCredentials.getUrl());
		Assertions.assertEquals("username", createdCredentials.getUsername());
		Assertions.assertNotEquals("password", createdCredentials.getPassword());
	}

	@Test
	public void testCredentialEdit(){
		UserUtils.signupAndLoginUser(driver, port);
		driver.get("http://localhost:" + this.port + "/");
		HomePage homePage = new HomePage(driver);
		homePage.createCredential("http://abc.com", "username", "password");
		Credentials createdCredentials = homePage.getSavedCredential();
		Assertions.assertNotEquals("password", createdCredentials.getPassword());
		homePage.editCredentials("http://xyz.com", "newUsername", "newPassword");
		Credentials editedCredentials = homePage.getSavedCredential();
		Assertions.assertEquals("http://xyz.com", editedCredentials.getUrl());
		Assertions.assertEquals("newUsername", editedCredentials.getUsername());
		Assertions.assertNotEquals("newPassword", editedCredentials.getPassword());
	}

	@Test
	public void testDeleteCredential(){
		UserUtils.signupAndLoginUser(driver, port);
		driver.get("http://localhost:" + this.port + "/");
		HomePage homePage = new HomePage(driver);
		homePage.createCredential("http://abc.com", "username", "password");
		Credentials createdCredentials = homePage.getSavedCredential();
		Assertions.assertEquals("http://abc.com", createdCredentials.getUrl());
		homePage.deleteCredentials();
		Credentials deletedCredentials = homePage.getSavedCredential();
		Assertions.assertNull(deletedCredentials);
	}
}
