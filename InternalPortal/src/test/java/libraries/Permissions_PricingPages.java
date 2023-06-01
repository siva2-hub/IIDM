package libraries;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class Permissions_PricingPages extends Permissions
{
	public void pricingPermissionAsNone(String itemName, String tabName, String labelName, int count) throws Exception 
	{
		this.adminLeftMenu("Users");
		String actURL[] = this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, count);
		System.out.println("current url is "+actURL[0]);

	}
	public String[] pricingPermissions(String itemName, String tabName, String labelName, int count) throws Exception 
	{
		this.userTab(itemName, tabName);
		List<WebElement> labelsText = driver.findElements(By.xpath("//*[@class='permission-outer-border']")).get(4).findElements(By.xpath("//*[@class='permission']"));
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
	public boolean verifyPricingermissionsAsNone(String tcName, String itemName, String tabName, String labelName, int count) throws Exception
	{

		String actURL[] = this.pricingPermissions(itemName, tabName, labelName, count);
		System.out.println("current url is "+actURL[0]);
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		System.out.println("special pricing url is "+actURL[0].replace("users", actURL[1]));
		Thread.sleep(2000);
		System.out.println("paragraphs tags are "+driver.findElements(By.tagName("p")).size());
		String message = driver.findElements(By.tagName("p")).get(0).getText();
		String expText = "";
		boolean res = false;
		if (message.equalsIgnoreCase("Sorry, you do not have permissions to access this page.")) {
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
	public String[] pricingChildPermissions(String itemName, String tabName, String labelName, int count, int xpCount, int btnCount) throws Exception 
	{
		this.userTab(itemName, tabName);
		List<WebElement> labelsText = driver.findElements(By.xpath("//*[@class='permission-outer-border']")).get(4).findElements(By.xpath("//*[@class='permission']"));
		String path = "";String  url = "";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String xpath1 = "";
		for(int i=0; i<labelsText.size(); i++) 
		{
			if(labelsText.get(i).findElements(By.tagName("span")).get(0).getText().equalsIgnoreCase(labelName)) 
			{
				js.executeScript("arguments[0].scrollIntoView();", labelsText.get(i).findElements(By.tagName("span")).get(0));
				System.out.println("scrolling functionality is working fine");

				System.out.println("label name "+labelsText.get(i).findElements(By.tagName("span")).get(0).getText());
				path = labelsText.get(i).findElement(By.tagName("input")).getAttribute("name");
				Thread.sleep(1500);
				String xpath = "/html/body/div/div/div[4]/div[2]/div[2]/div/div/div/div[3]/div[1]/div/div/div[5]/div/div/div[2]/div["+xpCount+"]/div["+btnCount+"]/span[2]/div/div/div/label["+count+"]";
				xpath1 = "/html/body/div/div/div[4]/div[2]/div[2]/div/div/div/div[3]/div[1]/div/div/div[5]/div/div/div[2]/div["+xpCount+"]/div["+btnCount+"]/span[2]/div/div/div/label[1]";
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
	public boolean verifyPricingExportImportPermissions(String tcName, String itemName, String tabName, String labelName, int count, int xpCount, String btnName, int btnCount) throws Exception
	{
		String actURL[] = this.pricingChildPermissions(itemName, tabName, labelName, count, xpCount, btnCount);
		System.out.println("current url is "+actURL[0]);
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		System.out.println("special pricing url is "+actURL[0].replace("users", actURL[1]));
		Thread.sleep(2000);
		System.out.println("paragraphs tags are "+driver.findElements(By.tagName("p")).size());
		String actText = "";
		if (labelName.equals("Non Standard Pricing")) {
			driver.findElement(By.className("card-title")).click();
			Thread.sleep(2000);
			actText = driver.findElement(By.xpath("//*[@style='padding: 8px 41px;']")).getText();
			driver.findElement(By.xpath("//*[@title='close']")).click();
		} else {
			actText = driver.findElement(By.className("Button-Icon-Display")).getText();
		}
		String expText = "";
		if(count==1) {
			expText = btnName;
		}else {
			expText = "";
		}
		boolean res = false;
		if (actText.contains(expText)) {
			res = true;
			Object status[] = {tcName, actText, "Top displayed text is "+actText, "Permissions", "Passed"};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {tcName, actText, "Top displayed text is "+actText, "Permissions", "Failed"};
			qp.values(status);
		}
		this.clickYesButton(itemName, tabName, labelName, count, xpCount, btnCount);
		return res;
	}
	public boolean verifyPricingPermissionsAsView(String tcName, String itemName, String tabName, String labelName, int count) throws Exception{
		String actURL[] = this.pricingPermissions(itemName, tabName, labelName, count);
		System.out.println("current url is "+actURL[0]);
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		System.out.println(labelName+" url is "+actURL[0].replace("users", actURL[1]));
		Thread.sleep(2000);
		System.out.println("paragraphs tags are "+driver.findElements(By.tagName("p")).size());
		String actText = "";
		String expText = "";
		if (labelName.equals("Non Standard Pricing")) {
			actText = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div[2]")).getText();
			expText = "Configure";
		} else {

			actText = driver.findElement(By.className("Button-Icon-Display")).getText();
			expText = "Add";
		}
		boolean res = false;
		if (!actText.contains(expText)) {
			res = true;
			Object status[] = {tcName, actText, "Top displayed text is "+actText, "Permissions", "Passed"};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {tcName, actText, "Top displayed text is "+actText, "Permissions", "Failed"};
			qp.values(status);
		}
		this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, 4);
		return res;
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void clickYesButton(String itemName, String tabName, String labelName, int count, int xpCount, int btnCount) throws Exception{
		this.userTab(itemName, tabName);
		List<WebElement> labelsText = driver.findElements(By.xpath("//*[@class='permission-outer-border']")).get(4).findElements(By.xpath("//*[@class='permission']"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String xpath1 = "";
		for(int i=0; i<labelsText.size(); i++) 
		{
			if(labelsText.get(i).findElements(By.tagName("span")).get(0).getText().equalsIgnoreCase(labelName)) 
			{
				js.executeScript("arguments[0].scrollIntoView();", labelsText.get(i).findElements(By.tagName("span")).get(0));
				System.out.println("label name "+labelsText.get(i).findElements(By.tagName("span")).get(0).getText());
				Thread.sleep(1500);
				xpath1 = "/html/body/div/div/div[4]/div[2]/div[2]/div/div/div/div[3]/div[1]/div/div/div[5]/div/div/div[2]/div["+xpCount+"]/div["+btnCount+"]/span[2]/div/div/div/label[1]";
				driver.findElement(By.xpath(xpath1)).click();
				price.clickButton("Save");
				Thread.sleep(1500);
				price.clickButton("Accept");
				break;
			}
		}
	}

}
