import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;



@RunWith(Suite.class)
@SuiteClasses({})
public class AllTests {
	static WebDriver driver;
	@BeforeAll
	/*
	 * Initialize the web driver and go to the Marks and Spicy home page
	 */
	public static void initDriver() {
		System.setProperty("webdriver.chrome.driver",".\\Driver\\chromedriver.exe");//Setting the driver executable
		driver = new ChromeDriver();//Maximize window
		driver.manage().window().maximize();
		
	}
	
	@Test
	/*
	 * Enter username and password and check for errors
	 */
	public void validLogin() {
		String userName = "test@test.com";
		String password = "ThisIs@T3st";
		String url = "https://marksandspicy.com";
		driver.get(url);//Open browser with desired URL
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login")));
		driver.findElement(By.className("login")).click();//Go to login page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
		driver.findElement(By.id("email")).sendKeys(userName);
		//driver.findElement(By.id("email")).sendKeys("erlg");
		driver.findElement(By.id("passwd")).sendKeys(password);
		//driver.findElement(By.id("passwd")).sendKeys("33");
		driver.findElement(By.id("SubmitLogin")).click();
		WebElement unError = null;
		WebElement pwError = null;
		try {
			unError = driver.findElement(By.xpath("//*[@id='login_form']/div/div[1][contains(@class,'form-error')]"));
			pwError = driver.findElement(By.xpath("//*[@id='login_form']/div/div[2][contains(@class,'form-error')]"));
		} catch (NoSuchElementException e) {
			
		}
		int count = 0;
		if (unError != null) {
			System.out.println("Task #1: Username error");
			count += 1;
		} 
		if (pwError != null) {
			System.out.println("Task #1: Password error");
			count += 1;
		}
		if (count == 0) {
			System.out.println("Task #1: No errors");
		}
		assertEquals(unError,null);
		assertEquals(pwError,null);
	}
	
	@Test
	/*
	 * Enter an invalid username and verify the error
	 */
	public void invalidUn(){
		String userName = "test";
		String expectedToolTipMsg = "Please include an '@' in the email address. '" + userName + "' is missing an '@'.";
		String url = "https://marksandspicy.com";
		driver.get(url);//Open browser with desired URL
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login")));
		driver.findElement(By.className("login")).click();
		driver.findElement(By.id("email")).sendKeys(userName);
		WebElement unElem = driver.findElement(By.id("email"));
		String tooltipMsg = unElem.getAttribute("validationMessage");
		if (tooltipMsg.equals(expectedToolTipMsg)) {
			System.out.println("Task #2: Tool tip verification passed");
		} else {
			System.out.println("Task #2: Tool tip verification failed");
		}
		assertEquals(tooltipMsg,expectedToolTipMsg);
	}
	
	
	@Test
	/*
	 * Enter empty username and password and verify the errors
	 */
	public void emptyUnPw(){
		String userName = "";
		String password = "";
		String url = "https://marksandspicy.com";
		driver.get(url);//Open browser with desired URL
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login")));
		driver.findElement(By.className("login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
		driver.findElement(By.id("email")).sendKeys(userName);
		driver.findElement(By.id("passwd")).sendKeys(password);
		driver.findElement(By.id("email")).sendKeys(userName);
		WebElement unError = null;
		WebElement pwError = null;
		try {
			unError = driver.findElement(By.xpath("//*[@id='login_form']/div/div[1][contains(@class,'form-error')]"));
			pwError = driver.findElement(By.xpath("//*[@id='login_form']/div/div[2][contains(@class,'form-error')]"));
		} catch (NoSuchElementException e) {
			
		}
		int count = 0;
		if (unError == null) {
			System.out.println("Task #3: No username error");
		} else {
			count += 1;
		}
		if (pwError == null) {
			System.out.println("Task #3: No password error");
		} else {
			count += 1;
		}
		if (count == 2) {
			System.out.println("Task #3: Errors are as expected");
		}
		assertNotEquals(unError, null);
		assertNotEquals(pwError, null);
	}
	
	
	@Test
	/*
	 * Make sure city is populated after zip is entered - Apparently does not work
	 * Make sure registration is successfull - Apparently no (also, 'Continue' button link shows javascript:void(0)) 
	 */
	public void registration(){
		String email = "testy@testy.com";
		String pass = "12345";
		String name = "John";
		String surname = "Smith";
		String day = "1";
		String month = "2";
		String year = "1920";
		String street = "Epiphenomenal Avenue";
		String zipCode = "55416";
		String mobilePhone = "0612345678";
		String registrationUrl = "https://marksandspicy.com/create-account.html";
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login")));
		driver.findElement(By.className("login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_create")));
		driver.findElement(By.id("email_create")).sendKeys(email);
		driver.findElement(By.id("SubmitCreate")).click();
		wait.until(ExpectedConditions.urlToBe(registrationUrl));
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(pass);
		driver.findElement(By.id("password2")).sendKeys(pass);
		driver.findElement(By.cssSelector("label[for='CivMr']")).click();
		driver.findElement(By.id("nom")).sendKeys(name);
		driver.findElement(By.id("prenom")).sendKeys(surname);
		driver.findElement(By.id("dateJour")).sendKeys(day);
		driver.findElement(By.id("dateMois")).sendKeys(month);
		driver.findElement(By.id("dateAnnee")).sendKeys(year);
		driver.findElement(By.id("adresse")).sendKeys(street);
		driver.findElement(By.id("codePostal")).sendKeys(zipCode);
		driver.findElement(By.id("telephonePortable")).sendKeys(mobilePhone);
		String city = driver.findElement(By.id("ville")).getAttribute("value");
		if (city.isEmpty()) {
			System.out.println("Task #4: City is empty");
		} else {
			System.out.println("Task #4: City is populated ");
		}
		
		driver.findElement(By.id("BtnCreationSubmit")).click();
		String newUrl = driver.getCurrentUrl();
		if (registrationUrl.equals(newUrl)) {
			System.out.println("Task #4: After registration - Still on the same page");
		} else {
			System.out.println("Task #4: After registration - Moved to different page ");
		}
		assertNotEquals(city,"");
		assertNotEquals(registrationUrl,newUrl);
	}
	

	
	
	@AfterAll
	public static void tearDown() {
		driver.quit();
	}	
}