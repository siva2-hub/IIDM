package libraries;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import commonUtils.App;

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
	public boolean verifyRepairPermissionAsNone(String tcName, String itemName, String tabName, String labelName, int count, String env)  throws Exception
	{
		//Pop Up message
		App.displayPopUp(tcName);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		String actURL[] = this.repairPermissionAsNone(itemName, tabName, labelName, count);
		System.out.println("current url is "+actURL[0]);
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		try {
			driver.findElement(By.className("clear-text")).isDisplayed();
			driver.findElement(By.className("clear-text")).click();
		} catch (Exception e) {
			// TODO: handle exception
		}
		boolean check = false;
		System.out.println(labelName+" url is "+actURL[0].replace("users", actURL[1]));
		Thread.sleep(2000);String message = "";String expText = "";
		if (count==2) {
			message = driver.findElements(By.tagName("p")).get(0).getText();
			expText = "Sorry, you do not have permissions to access this page.";
			check = message.equalsIgnoreCase(expText);

		} else if(count==3) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'vendor_logo')]")));
			//PricingPages price = new PricingPages();
			driver.findElement(By.xpath("//*[text() = 'Create']")).click();
			Thread.sleep(1400);
			if(driver.findElement(By.xpath("//*[contains(@id,'ds--dropdown')]")).getText().contains("RMA")) {
				driver.findElement(By.xpath("//*[text() = 'Create']")).click();
				Thread.sleep(1400);
				message = "RMA displayed On Create";
			}else {
				message = driver.findElement(By.className("add-Icon")).getText();
			}
			expText = "Create RMA";
			check = !message.contains(expText);
		}
		System.out.println("paragraphs tags are "+driver.findElements(By.tagName("p")).size());
		boolean res = false;
		if (check) {
			res = true;
			Object status[] = {tcName, message, "Top displayed text is "+message, "Repairs_Permissions", "Passed", "", env};
			App.values1(status);
		} else {
			res = false;
			Object status[] = {tcName, message, "Top displayed text is "+message, "Repairs_Permissions", "Failed", "", env};
			App.values1(status);
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
	public boolean verifyAddToQuotePermissionAsYes(String tcName, String itemName, String tabName, String labelName, int count, String env)throws Exception
	{
		//Pop Up message
		App.displayPopUp(tcName);

		String actURL[] = this.addToQuotePermissionAsYes(itemName, tabName, labelName, count);
		System.out.println("current url is "+actURL[0]);
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		System.out.println("special pricing url is "+actURL[0].replace("users", actURL[1]));
		Thread.sleep(2000);
		System.out.println("paragraphs tags are "+driver.findElements(By.tagName("p")).size());
		String expText = "";
		RepairPages repair = new RepairPages();
		if (count==1) {
			repair.evaluateItem();
		} else {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'All Repairs Requests']")));
			Thread.sleep(1300);
			driver.findElement(By.xpath("//*[text() = 'All Repairs Requests']")).click();
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'Filters']")));
			Thread.sleep(1500);
			try {
				driver.findElement(By.xpath("//*[text()='Clear']")).isDisplayed();
				driver.findElement(By.xpath("//*[text()='Clear']")).click();
			} catch (Exception e) {
				System.out.println("try block error is "+e.getMessage());
			}
			App.spinner();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'Pending Quote']")));
			Thread.sleep(1600);
			driver.findElement(By.xpath("//*[text() = 'Pending Quote']")).click();
			App.spinner(); Thread.sleep(1000);
			driver.findElement(By.xpath("//*[@row-index = '0']")).findElements(By.xpath("//*[starts-with(@style, 'left')]")).get(5).click();
			App.spinner(); Thread.sleep(1200);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src, 'Toe-Tag-Scanner')]")));
			Thread.sleep(500);
		}
		Thread.sleep(1600);
		driver.findElement(By.xpath("//*[contains(@class,'check_box')]")).findElement(By.tagName("label")).click();
		Thread.sleep(1000);
		String actText = driver.findElement(By.id("repair-items")).findElement(By.xpath("//*[@class='cards-btns-group']")).getText();
		if (count==1) {
			expText = "Add items to quote";
		} else if(count==2) {
			expText = "";
		}
		boolean res = false;
		if (actText.contains(expText)) {
			res = true;
			Object status[] = {tcName, actText, "In Item displayed button is "+actText, "Repairs_Permissions", "Passed", "", env};
			App.values1(status);
		} else {
			res = false;
			Object status[] = {tcName, actText, "In Item displayed button is "+actText, "Repairs_Permissions", "Failed", "", env};
			App.values1(status);
		}

		return res;
	}
}
