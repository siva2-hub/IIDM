package libraries;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminPages extends Permissions
{
	WebDriverWait wait ;
	public void addAndEditsInAdminPages(String itemName, String tabName) throws Exception
	{
		wait= new WebDriverWait(driver, Duration.ofSeconds(30));
		this.headerMenu(itemName);
		this.adminLeftMenu(tabName);
		WebElement editIcon = driver.findElement(By.xpath("//*[contains(@src,'editicon')]"));
		editIcon.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Update']")));
		driver.findElement (By.xpath("//*[text()='Status']")).findElement(By.xpath("//*[container(@class,'container')]")).click();
		driver.findElement (By.xpath("//*[text()='InActive']")).click();
	}
}
