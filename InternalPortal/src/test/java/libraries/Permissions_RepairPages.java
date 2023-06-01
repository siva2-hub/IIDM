package libraries;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class Permissions_RepairPages extends Permissions
{
	public String[] repairPermissionAsNone(String itemName, String tabName, String labelName, int count) throws Exception
	{
		this.userTab(itemName, tabName);
		List<WebElement> labelsText = driver.findElements(By.xpath("//*[@class='permission-outer-border']")).get(1).findElements(By.xpath("//*[@class='permission']"));
		String path = "";String  url = "";
		for(int i=0; i<labelsText.size(); i++) 
		{
			//System.out.println(labelsText.get(i).findElements(By.tagName("span")).get(0).getText());// edit=4 :: view=3 :: none=2
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
	public boolean verifyRepairPermissionAsNone(String tcName, String itemName, String tabName, String labelName, int count)  throws Exception
	{
		String actURL[] = this.repairPermissionAsNone(itemName, tabName, labelName, count);
		System.out.println("current url is "+actURL[0]);
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		System.out.println(labelName+" url is "+actURL[0].replace("users", actURL[1]));
		Thread.sleep(2000);String message = "";String expText = "";
		if (count==2) {
			message = driver.findElements(By.tagName("p")).get(0).getText();
			expText = "Sorry, you do not have permissions to access this page.";
			
		} else if(count==3) {
			
			PricingPages price = new PricingPages();
			price.clickButton("Create");
			if(driver.findElement(By.xpath("//*[contains(@id,'ds--dropdown')]")).getText().contains("RMA")) {
				price.clickButton("Create");
				message = "RMA displayed On Create";
			}else {
				message = driver.findElement(By.className("add-Icon")).getText();
			}
			expText = "Filters";
		}
		System.out.println("paragraphs tags are "+driver.findElements(By.tagName("p")).size());
		boolean res = false;
		if (message.equalsIgnoreCase(expText)) {
			res = true;
			Object status[] = {tcName, message, "Top displayed text is "+message, "Permissions", "Passed"};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {tcName, message, "Top displayed text is "+message, "Permissions", "Failed"};
			qp.values(status);
		}
		this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, 4);
		return res;
	}
	public String[] addToQuotePermissionAsYes(String itemName, String tabName, String labelName, int count)throws Exception 
	{
		this.userTab(itemName, tabName);
		List<WebElement> labelsText = driver.findElements(By.xpath("//*[@class='permission-outer-border']")).get(4).findElements(By.xpath("//*[@class='permission']"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String xpath1 = "";String path = "";String url = "";
		for(int i=0; i<labelsText.size(); i++) 
		{
			if(labelsText.get(i).findElements(By.tagName("span")).get(0).getText().equalsIgnoreCase(labelName)) 
			{
				js.executeScript("arguments[0].scrollIntoView();", labelsText.get(i).findElements(By.tagName("span")).get(0));
				System.out.println("label name "+labelsText.get(i).findElements(By.tagName("span")).get(0).getText());
				Thread.sleep(1500);
				path = labelsText.get(i).findElement(By.tagName("input")).getAttribute("name");
				xpath1 = "/html/body/div/div/div[4]/div[2]/div[2]/div/div/div/div[3]/div[1]/div/div/div[2]/div/div/div[2]/div[2]/div[2]/span[2]/div/div/div/label[1]";
				String xpath = "/html/body/div/div/div[4]/div[2]/div[2]/div/div/div/div[3]/div[1]/div/div/div[2]/div/div/div[2]/div[2]/div[2]/span[2]/div/div/div/label["+count+"]";
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
	public boolean verifyAddToQuotePermissionAsYes(String tcName, String itemName, String tabName, String labelName, int count)throws Exception
	{
		String actURL[] = this.addToQuotePermissionAsYes(itemName, tabName, labelName, count);
		System.out.println("current url is "+actURL[0]);
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		System.out.println("special pricing url is "+actURL[0].replace("users", actURL[1]));
		Thread.sleep(2000);
		System.out.println("paragraphs tags are "+driver.findElements(By.tagName("p")).size());
		String expText = "";
		RepairPages repair = new RepairPages();
		repair.evaluateItem();
		driver.findElement(By.name("checkbox0")).click();
		String actText = driver.findElement(By.id("repair-items")).findElement(By.xpath("//*[@class='cards-btns-group']")).getText();
		if (count==1) {
			expText = "Add items to quote";
		} else if(count==2) {
			expText = "";
		}
		boolean res = false;
		if (actText.contains(expText)) {
			res = true;
			Object status[] = {tcName, actText, "In Item displayed button is "+actText, "Permissions", "Passed"};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {tcName, actText, "In Item displayed button is "+actText, "Permissions", "Failed"};
			qp.values(status);
		}
		
		return res;
	}
}
