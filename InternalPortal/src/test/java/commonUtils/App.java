package commonUtils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class App {
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static String url = "https://buzzworld-web-iidm.enterpi.com/pricing";	
	@BeforeTest
	public static void login() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		driver.findElement(By.id("username")).sendKeys("sivakrishna.d@enterpi.com");
		driver.findElement(By.id("password")).sendKeys("Test@4321");
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/form/div[3]/button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-center-cols-container']")));
	} 
	@AfterTest
	public static void logout() {
		driver.findElement(By.xpath("//*[@class='down-arrow']")).click();
		List<WebElement> btns = driver.findElements(By.xpath("//*[@role='menuitem']"));
		btns.get(1).click();
		driver.close();
	}
}
