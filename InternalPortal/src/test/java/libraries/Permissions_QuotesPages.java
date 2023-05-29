package libraries;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Permissions_QuotesPages extends Permissions
{
	public String[] quoteForPartsPermissionAsNone(String itemName, String tabName, String labelName,  int tabCount ,int count) throws Exception
	{
		this.userTab(itemName, tabName);
		List<WebElement> labelsText = driver.findElements(By.xpath("//*[@class='permission-outer-border']")).get(tabCount).findElements(By.xpath("//*[@class='permission']"));
		String path = "";String  url = "";
		for(int i=0; i<labelsText.size(); i++) 
		{
			//			System.out.println(labelsText.get(i).findElements(By.tagName("span")).get(0).getText());// edit=4 :: view=3 :: none=2
			if(labelsText.get(i).findElements(By.tagName("span")).get(0).getText().equalsIgnoreCase(labelName)) 
			{
				System.out.println("label name "+labelsText.get(i).findElements(By.tagName("span")).get(0).getText());
				path = labelsText.get(i).findElement(By.tagName("input")).getAttribute("name");
				labelsText.get(i).findElements(By.tagName("span")).get(count).click();
				price.clickButton("Save");
				Thread.sleep(1500);
				price.clickButton("Accept");
				url = driver.getCurrentUrl();
				break;
			}
		}
		String vals[] = {url, path};
		return vals;
	}
	public boolean verifyQuotesPermissionAsNone(String tcName, String itemName, String tabName, String labelName,  int tabCount ,int count) throws Exception
	{
		String actURL[] = this.quoteForPartsPermissionAsNone(itemName, tabName, labelName, tabCount, count);
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		System.out.println("special pricing url is "+actURL[0].replace("users", actURL[1]));
		Thread.sleep(2000);
		String message = "";String expText = "";
		System.out.println("paragraphs tags are "+driver.findElements(By.tagName("p")).size());
		if(count==2) {
			message = driver.findElements(By.tagName("p")).get(0).getText();
			expText = "Sorry, you do not have permissions to access this page.";
			
		}else {
			message = driver.findElement(By.className("add-Icon")).getText();
			expText = "Filters";
		}
		boolean res = false;
		if (message.equalsIgnoreCase(expText)) {
			res = true;
			Object status[] = {tcName, message, "Top displayed text is "+message, "Permissions", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {tcName, message, "Top displayed text is "+message, "Permissions", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
		}
		this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, 4);
		return res;
	}
	public String[] createSalesOrderPermissionAsYes(String itemName, String tabName, String labelName, int childCount, int count) throws Exception
	{
		this.userTab(itemName, tabName);
		List<WebElement> labelsText = driver.findElements(By.xpath("//*[@class='permission-outer-border']")).get(3).findElements(By.xpath("//*[@class='permission']"));
		String path = "";String  url = "";String xpath1 = "";
		for(int i=0; i<labelsText.size(); i++) 
		{
			//			System.out.println(labelsText.get(i).findElements(By.tagName("span")).get(0).getText());// edit=4 :: view=3 :: none=2
			if(labelsText.get(i).findElements(By.tagName("span")).get(0).getText().equalsIgnoreCase(labelName)) 
			{
				System.out.println("label name "+labelsText.get(i).findElements(By.tagName("span")).get(0).getText());
				path = labelsText.get(i).findElement(By.tagName("input")).getAttribute("name");
				xpath1 = "/html/body/div/div/div[4]/div[2]/div[2]/div/div/div/div[3]/div[1]/div/div/div[4]/div/div/div[2]/div/div["+childCount+"]/span[2]/div/div/div/label["+count+"]";
				String xpath = "/html/body/div/div/div[4]/div[2]/div[2]/div/div/div/div[3]/div[1]/div/div/div[4]/div/div/div[2]/div/div["+childCount+"]/span[2]/div/div/div/label["+count+"]";
				driver.findElement(By.xpath(xpath)).click();
				price.clickButton("Save");
				Thread.sleep(1500);
				price.clickButton("Accept");
				url = driver.getCurrentUrl();
				break;
			}
		}
		String vals[] = {url, path, xpath1};
		return vals;
	}
	public boolean verifyCreateSalesOrderPermissionAsYes(String tcName, String itemName, String tabName, String labelName, int childCount, int count) throws Exception
	{
		String actURL[] =this.createSalesOrderPermissionAsYes(itemName, tabName, labelName, childCount, count);
		QuotePages quotes = new QuotePages();RepairPages repair = new RepairPages();
		quotes.submitForCustomerApproval();
		PricingPages price = new PricingPages();
		price.clickButton("Won");
		repair.toastContainer("Proceed");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1500);
		String message = "";String expText = "";
		if(childCount==2) {
			message = driver.findElement(By.xpath("/html/body/div/div/div[3]/div[2]")).getText();
			if(count==1) 
			{
				
				expText = "Create Sales Order";
			}else {
				expText = "";
			}
			
		}else if(childCount==5) {
			message = driver.findElement(By.className("add-Icon")).getText();
			expText = "Filters";
		}
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		System.out.println("special pricing url is "+actURL[0].replace("users", actURL[1]));
		Thread.sleep(2000);
		System.out.println("paragraphs tags are "+driver.findElements(By.tagName("p")).size());
		boolean res = false;
		if (message.equalsIgnoreCase(expText)) {
			res = true;
			Object status[] = {tcName, message, "Top displayed text is "+message, "Permissions", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {tcName, message, "Top displayed text is "+message, "Permissions", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
		}
		this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, 4);
		return res;
	}
	public boolean verifyQuoteClosePermissionAsYes_Quotes(String tcName, String itemName, String tabName, String labelName, int childCount, int count) throws Exception
	{
		String actURL[] =this.createSalesOrderPermissionAsYes(itemName, tabName, labelName, childCount, count);
		QuotePages quotes = new QuotePages();RepairPages repair = new RepairPages();
		quotes.submitForInternalApproval();
		PricingPages price = new PricingPages();
		Thread.sleep(1600);
		price.clickButton("Approve");
		repair.toastContainer("Decline");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1500);
		String message = "";String expText = "";boolean sta = false;
		if(count==1) {
			message = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]")).getText();
			expText = "Close";
			sta = message.contains(expText);
			
		}else {
			message = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]")).getText();
			expText = "Close";
			sta = !message.contains(expText);
		}
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		System.out.println("special pricing url is "+actURL[0].replace("users", actURL[1]));
		Thread.sleep(2000);
		System.out.println("paragraphs tags are "+driver.findElements(By.tagName("p")).size());
		boolean res = false;
		if (sta) {
			res = true;
			Object status[] = {tcName, message, "Top displayed text is "+message, "Permissions", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {tcName, message, "Top displayed text is "+message, "Permissions", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
		}
		this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, 4);
		return res;
	}

}
