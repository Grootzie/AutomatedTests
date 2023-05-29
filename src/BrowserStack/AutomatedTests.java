package BrowserStack;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AutomatedTests {
	WebDriver driver;

	// open the website
	@Test(priority = 1)
	public void openWebsite() {
		// location of my chromedriver file on the disk
		System.setProperty("webdriver.chrome.driver",
				"D:\\Users\\Sekhm\\Documents\\chromedriver_win32\\bin\\chromedriver.exe");
		driver = new ChromeDriver();

		// opening the website target
		driver.get("https://react-redux.realworld.io/");
	}

	// sign in with email/password
	@Test(priority = 2, dependsOnMethods = {"openWebsite"}) 
	public void signIn() {
		// opening the "Sign in" tab
		WebElement signInLink = driver.findElement(By.linkText("Sign in"));
		signInLink.click();

		// get every field of the form when they are available, timeout after 3 secs
		WebElement emailInput = new WebDriverWait(driver, Duration.ofSeconds(3))
				.until(tempDriver -> tempDriver.findElement(By.cssSelector("input[type='email']")));
		WebElement passwordInput = new WebDriverWait(driver, Duration.ofSeconds(3))
				.until(tempDriver -> tempDriver.findElement(By.cssSelector("input[type='password']")));
		WebElement submitSignInButton = new WebDriverWait(driver, Duration.ofSeconds(3))
				.until(tempDriver -> tempDriver.findElement(By.cssSelector("button[type='submit']")));

		// fill the form with values and submit to sign in
		emailInput.sendKeys("jeremy.desnous@gmail.com");
		passwordInput.sendKeys("654321");
		submitSignInButton.click();
	}

	// delete every existing article 
	@Test(priority = 3, dependsOnMethods = {"signIn"})
	public void deleteArticles () throws Exception {
		while (true) {
			try {
				// opening the profil of the user connected
				WebElement profilLink = new WebDriverWait(driver, Duration.ofSeconds(3))
						.until(tempDriver -> tempDriver.findElement(By.linkText("Grootz")));
				profilLink.click();

				Thread.sleep(2000);

				// checking if there is an article, if yes : open it, if not, break
				WebElement articleLink = driver.findElement(By.cssSelector("a.preview-link"));
				articleLink.click();

				// delete the article
				WebElement deleteButton = new WebDriverWait(driver, Duration.ofSeconds(3))
						.until(
								tempDriver -> tempDriver.findElement(By.cssSelector("button[class='btn btn-outline-danger btn-sm']")));
				deleteButton.click();

				Thread.sleep(2000);

			} catch (org.openqa.selenium.NoSuchElementException e) {
				break;
			}
		}

	}

	// create a new post
	@Test(priority = 4, dependsOnMethods = {"signIn"})
	public void createPost() throws Exception {
		// opening the "New Post" tab when available
		WebElement newPostLink = new WebDriverWait(driver, Duration.ofSeconds(3))
				.until(tempDriver -> tempDriver.findElement(By.linkText("New Post")));
		newPostLink.click();

		// get every field of the form when they are available, timeout after 3 secs
		WebElement titleInput = new WebDriverWait(driver, Duration.ofSeconds(3))
				.until(tempDriver -> tempDriver.findElement(By.cssSelector("input[placeholder='Article Title']")));
		WebElement aboutInput = new WebDriverWait(driver, Duration.ofSeconds(3))
				.until(
						tempDriver -> tempDriver.findElement(By.cssSelector("input[placeholder='What\\'s this article about?']")));
		WebElement textAreaInput = new WebDriverWait(driver, Duration.ofSeconds(3))
				.until(tempDriver -> tempDriver.findElement(By.cssSelector("textarea[class='form-control']")));
		WebElement tagsInput = new WebDriverWait(driver, Duration.ofSeconds(3))
				.until(tempDriver -> tempDriver.findElement(By.cssSelector("input[placeholder='Enter tags']")));
		WebElement submitArticleButton = new WebDriverWait(driver, Duration.ofSeconds(3))
				.until(tempDriver -> tempDriver.findElement(By.cssSelector("button[type='button']")));

		// fill the form with values and submit to post the article
		titleInput.sendKeys("Titre de l'article");
		aboutInput.sendKeys("Article talking about testing");
		textAreaInput.sendKeys(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit.");
		tagsInput.sendKeys("tag1 tag2 tag3 tag4 tag5 tag6");
		submitArticleButton.click();

		Thread.sleep(2000);
	}

	// add the created post in favorite
	@Test(priority = 5, dependsOnMethods = {"signIn", "createPost"})
	public void addFavorite() throws Exception {
		// opening the profil of the user connected
		WebElement profilLink = new WebDriverWait(driver, Duration.ofSeconds(3))
				.until(tempDriver -> tempDriver.findElement(By.linkText("Grootz")));
		profilLink.click();

		// adding to favorite the first article of the list
		WebElement favButton = new WebDriverWait(driver, Duration.ofSeconds(3))
				.until(tempDriver -> tempDriver.findElement(By.cssSelector("button[class='btn btn-sm btn-outline-primary']")));
		favButton.click();

		Thread.sleep(5000);
	}

	@BeforeMethod
	public void beforeMethod() {
		System.out.println("Starting Methog On Chrome Browser");
	}

	@AfterMethod
	public void afterMethod() {
		System.out.println("Finished Method On Chrome Browser");
	}

	@AfterSuite
	public void afterSuite() {
		driver.quit();
		System.out.println("Finished Test On Chrome Browser");
	}
}
