package libraries;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Permissions_SysPro_Pages extends Permissions
{
	public boolean verifyInventoryPermissionAsNone(String tcName, String itemName, String tabName, String labelName, int count) throws Exception 
	{
		String actURL[] = this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, count);
		System.out.println("current url is "+actURL[0]);
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		Thread.sleep(2000);
		if (count==3) {
			driver.findElement(By.id("async-select-example")).sendKeys("CMT-G01");
			QuotePages quotes = new QuotePages();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			quotes.selectDropDown("CMT-G01");
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		String message = "";
		String actText = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div[2]")).getText();
		boolean sta = false;
		if (count==3) {
			int ct = driver.findElement(By.xpath("/html/body/div/div/div[4]/div/div/div/div[2]")).findElements(By.tagName("img")).size();
			sta = (ct==0);
		} else if(count==2){
			sta = actText.equals("");
			message = driver.findElements(By.tagName("p")).get(0).getText();
		}
		boolean res = false;
		if (sta) {
			res = true;
			Object status[] = {tcName, message, "Top displayed text is "+actText, "Permissions", "Passed"};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {tcName, message, "Top displayed text is "+actText, "Permissions", "Failed"};
			qp.values(status);
		}
		this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, 4);
		return res;
	}
	public boolean verifyJobsPermissionAsNone(String tcName, String itemName, String tabName, String labelName, int count) throws Exception 
	{
		String actURL[] = this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, count);
		System.out.println("current url is "+actURL[0]);
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		Thread.sleep(2000);
		String message = "";String expText = "";
		String actText = "";
		boolean sta = false;
		if (count==3) {
			actText = driver.findElement(By.xpath("//*[@class='add-Icon']")).getText();
			expText = actText;
			sta = actText.equals("");
			System.out.println("act Text "+actText);
			
		} else if(count==2){
			actText = driver.findElements(By.tagName("p")).get(0).getText();
			expText = driver.findElement(By.xpath("/html/body/div/div/div[3]/div/div[2]")).getText();
			message = "Sorry, you do not have permissions to access this page.";
			sta = actText.equals(message) && expText.equals("");
			System.out.println("act Text "+actText);
		}
		boolean res = false;
		if (sta) {
			res = true;
			Object status[] = {tcName, message, "Top displayed text is "+expText, "Permissions", "Passed"};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {tcName, message, "Top displayed text is "+expText, "Permissions", "Failed"};
			qp.values(status);
		}
		this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, 4);
		return res;
	}
	public boolean verifyOrdersPermissionAsNone(String tcName, String itemName, String tabName, String labelName, int count) throws Exception 
	{
		String actURL[] = this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, count);
		System.out.println("current url is "+actURL[0]);
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		Thread.sleep(2000);
		String expText = "Sorry, you do not have permissions to access this page.";
		String actText = driver.findElements(By.tagName("p")).get(0).getText();
		boolean res = false;
		if (actText.equals(expText)) {
			res = true;
			Object status[] = {tcName, actText, "Top displayed text is "+expText, "Permissions", "Passed"};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {tcName, actText, "Top displayed text is "+expText, "Permissions", "Failed"};
			qp.values(status);
		}
		this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, 4);
		return res;
	}
	public boolean verifyPartsPurchasePermissionAsNone(String tcName, String itemName, String tabName, String labelName, int count) throws Exception 
	{
		String actURL[] = this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, count);
		System.out.println("current url is "+actURL[0]);
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		Thread.sleep(2000);
		String expText = "";
		String actText = "";
		if(count==2) {
			expText = "Sorry, you do not have permissions to access this page.";
			actText = driver.findElements(By.tagName("p")).get(0).getText();
		}else if(count==3) {
			expText = "Filters";
			actText = driver.findElement(By.className("add-Icon")).getText();
		}
		boolean res = false;
		if (actText.equals(expText)) {
			res = true;
			Object status[] = {tcName, actText, "Top displayed text is "+expText, "Permissions", "Passed"};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {tcName, actText, "Top displayed text is "+expText, "Permissions", "Failed"};
			qp.values(status);
		}
		this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, 4);
		return res;
	}
}
