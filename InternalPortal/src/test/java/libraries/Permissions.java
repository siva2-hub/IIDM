package libraries;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import commonUtils.App;

public class Permissions extends App
{
	WebDriverWait wait;
	PricingPages price = new PricingPages();
	RepairPages rp = new RepairPages();
	QuotePages qp = new QuotePages();
	public void userTab(String itemName, String tabName) throws Exception
	{
		wait  = new WebDriverWait(driver, Duration.ofSeconds(30));
		this.headerMenu(itemName);
		this.adminLeftMenu(tabName);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));	
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@placeholder='Search']")).sendKeys("sivakrishna");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		driver.findElement(By.id("tab-1")).click();
		Thread.sleep(1600);
	}
	public String[] verifyAdminTabswithNonePermission(String itemName, String tabName, String labelName, int count) throws Exception
	{
		this.userTab(itemName, tabName);
		List<WebElement> labelsText = driver.findElement(By.xpath("//*[@class='permission-outer-border']")).findElements(By.xpath("//*[@class='permission']"));
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
	public boolean verifyAdminTabs_None(String tcName,String itemName, String tabName, String labelName, int count) throws Exception
	{
		String actURL[] = this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, count);
		System.out.println("current url is "+actURL[0]);
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		Thread.sleep(3000);
		System.out.println("paragraphs tags are "+driver.findElements(By.tagName("p")).size());
		String message = driver.findElements(By.tagName("p")).get(0).getText();
		String url = driver.getCurrentUrl();
		String actText = driver.findElement(By.xpath("/html/body/div/div/div[3]/div/div[2]")).getText();
		String expText = "";
		boolean res = false;
		if (message.equalsIgnoreCase("Sorry, you do not have permissions to access this page.") && actText.equals(expText)) {
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
	public boolean adminTabwithViewPermission(String tcName, String itemName, String tabName, String labelName, int count) throws Exception
	{
		this.userTab(itemName, tabName);
		List<WebElement> labelsText = driver.findElement(By.xpath("//*[@class='permission-outer-border']")).findElements(By.xpath("//*[@class='permission']"));
		String path = "";String  url = "";
		for(int i=0; i<labelsText.size(); i++) 
		{
//			System.out.println(labelsText.get(i).findElements(By.tagName("span")).get(0).getText());// edit=4 :: view=3 :: none=2
			if(labelsText.get(i).findElements(By.tagName("span")).get(0).getText().equalsIgnoreCase(labelName)) 
			{
				System.out.println("label name "+labelsText.get(i).findElements(By.tagName("span")).get(0).getText());
				path = labelsText.get(i).findElement(By.tagName("input")).getAttribute("name");
				labelsText.get(i).findElements(By.tagName("span")).get(count).click();
				if(labelName.equals("Warehouse")) {
					JavascriptExecutor js = (JavascriptExecutor)driver;
					js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//*[@name='warehouse_sync'][@value='0']")));
					Actions act = new Actions(driver);
					act.moveToElement(driver.findElement(By.xpath("//*[@name='warehouse_sync'][@value='0']"))).perform();
					act.doubleClick(driver.findElement(By.xpath("//*[@name='warehouse_sync'][@value='0']"))).perform();
				}
				price.clickButton("Save");
				Thread.sleep(1500);
				price.clickButton("Accept");
				url = driver.getCurrentUrl();
				break;
			}
		}
		String vals[] = {url, path};
		driver.navigate().to(vals[0].replace("users", vals[1]));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(2000);
		String expText = "";String actText = "";int editCount = 0;
		if (labelName.equals("PO Min Qty")) {
			actText = driver.findElement(By.className("add-Icon")).getText();
			editCount = driver.findElements(By.xpath("//*[@name='right'][@class='ag-pinned-right-cols-container']")).size();
		}else if(labelName.equals("Quote Approval")) {
			
			actText = driver.findElement(By.xpath("//*[@tabindex=-1]")).getAttribute("disabled");
			if(actText.equals("true")) {
				actText="";
			}
		}else if(labelName.equals("User Roles")) {
			actText = String.valueOf(driver.findElements(By.xpath("//*[contains(@src,'addIcon')]")).size());
			expText = "0";
		}else if(labelName.equals("Terms & Conditions")) {
			actText = driver.findElements(By.xpath("//*[@class='lead']")).get(3).findElement(By.xpath("//*[contains(@class,'ql-editor')]")).getAttribute("contenteditable");
			if(actText.equals("false")) {
				actText="";
			}
		} else if(labelName.equals("QC Control")) {
			Thread.sleep(1600);
			actText = driver.findElement(By.xpath("//*[@style='padding-left: 4px;']")).getText();
			expText = "";
		} else {
			expText = "Filters";
			actText = driver.findElement(By.className("add-Icon")).getText();
			editCount = driver.findElements(By.xpath("//*[@name='right'][@class='ag-pinned-right-cols-container']")).size();
		}		
		System.out.println("icons/actual Text is"+actText+"expected text is"+expText);
		Thread.sleep(2000);
		System.out.println("edit icons count are "+editCount);
		boolean res = false;
		if (tcName.contains("PERMNS_035")) {
			this.adminLeftMenu("Account Types");
			actText = driver.findElement(By.className("add-Icon")).getText();
			expText = "Add";
			if (actText.equals(expText)&& editCount==0) {
				res = true;
				Object status[] = {tcName, "actual displayed option is "+actText, "expected displayed option is "+expText+" edit count is "+editCount,
						"Permissions", "Passed"};
				
				qp.values(status);
			} else {
				res = false;
				Object status[] = {tcName, "actual displayed option is "+actText, "expected displayed option is "+expText+" edit count is "+editCount,
						"Permissions", "Failed"};
				qp.values(status);
			}
		} else {

			if (actText.equals(expText)&& editCount==0) {
				res = true;
				Object status[] = {tcName, "actual displayed option is "+actText, "expected displayed option is "+expText+" edit count is "+editCount,
						"Permissions", "Passed"};
				
				qp.values(status);
			} else {
				res = false;
				Object status[] = {tcName, "actual displayed option is "+actText, "expected displayed option is "+expText+" edit count is "+editCount,
						"Permissions", "Failed"};
				qp.values(status);
			}
			this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, 4);
		}
		return res;
	}
	
	public void headerMenu(String itemName) throws Exception
	{
		List<WebElement> headerList = driver.findElement(By.xpath("//*[@class='header-left']")).findElements(By.xpath("//*[contains(@class,'menu-item')]"));
		System.out.println("header list is "+headerList.size());
		for(int i=0; i<headerList.size(); i++) {

			if(headerList.get(i).getText().equalsIgnoreCase(itemName)) {
				headerList.get(i).click();
				Thread.sleep(2000);
				break;
			}
		}
	}

	public void adminLeftMenu(String tabName) throws Exception
	{
		List<WebElement> leftMenu = driver.findElement(By.xpath("//*[@class='left-menu']")).findElements(By.xpath("//*[contains(@class,'menu-item')]"));
		for(int i=0; i<leftMenu.size(); i++) {

			if(leftMenu.get(i).getText().equalsIgnoreCase(tabName)) {
				leftMenu.get(i).click();
				Thread.sleep(2000);
				break;
			}
		}
	}
}
