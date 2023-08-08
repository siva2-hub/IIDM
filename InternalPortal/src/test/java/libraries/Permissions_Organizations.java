package libraries;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import commonUtils.App;

public class Permissions_Organizations extends Permissions
{
	public boolean verifyOrganizationsPermissionsAsNone(String tcName, String itemName, String tabName, String labelName, int count, String env) throws Exception
	{
		//Warning Pop Up
		App.displayPopUp(tcName);

		String actURL[] = this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, count);
		System.out.println("current url is "+actURL[0]);
		driver.navigate().to(actURL[0].replace("users", actURL[1]));
		App.spinner(); Thread.sleep(1200);
		System.out.println("paragraphs tags are "+driver.findElements(By.tagName("p")).size());
		String message = "";String expText = "";boolean sta = false;
		if (count==2) {
			expText = "Sorry, you do not have permissions to access this page.";
			try {
				driver.findElement(By.xpath("//*[contains(text(), '"+expText+"')]")).isDisplayed();
				message = driver.findElement(By.xpath("//*[contains(text(), '"+expText+"')]")).getText();
			} catch (Exception e) {
				// TODO: handle exception
			}
			sta = message.equals(expText);
		} else if(count==3) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='Button-Icon-Display']")));
			message = driver.findElement(By.xpath("//*[@class='Button-Icon-Display']")).getText();
			expText = "Save View";
			sta = !message.contains(expText);
		}
		boolean res = false;
		if (sta) {
			res = true;
			Object status[] = {tcName, message, "Top displayed text is "+message, labelName+"_Permissions", "Passed", "", env};
			App.values1(status);
		} else {
			res = false;
			Object status[] = {tcName, message, "Top displayed text is "+message, labelName+"_Permissions", "Failed", "", env};
			App.values1(status);
		}
		this.verifyAdminTabswithNonePermission(itemName, tabName, labelName, 4);
		return res;
	}
	public boolean exportPermissionAsYesInContacts(String tcName, String itemName, String tabName, String xpathName, String urlName, String expText, String yes, String env) throws Exception
	{
		//Warning Pop Up
		App.displayPopUp(tcName);

		this.userTab(itemName, tabName);
		PricingPages price = new PricingPages();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		Actions act = new Actions(driver);
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.name(xpathName)));
		Thread.sleep(1500);
		act.moveToElement(driver.findElement(By.xpath("//*[@name='"+xpathName+"'][@value='"+yes+"']"))).perform();
		Thread.sleep(1500);
		act.doubleClick(driver.findElement(By.xpath("//*[@name='"+xpathName+"'][@value='"+yes+"']"))).perform();

		price.clickButton("Save");
		Thread.sleep(1500);
		price.clickButton("Accept");
		driver.navigate().to(driver.getCurrentUrl().replace("users", urlName));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1000);
		Thread.sleep(1500);boolean sta = false;String  actText = "";
		if (urlName.equals("warehouse") || urlName.equals("product_class")) {
			actText = driver.findElement(By.className("add-Icon")).getText();
		} else {

			actText = driver.findElement(By.className("Button-Icon-Display")).getText();
		}
		if (yes.equals("0")) {
			sta = !actText.contains(expText);
		} else if (yes.equals("1")){
			sta = actText.contains(expText);
		}
		boolean res = false;
		if (sta) {
			res = true;
			Object status[] = {tcName, "Top displayed text is "+actText, actText, urlName+"_Permissions", "Passed", "", env};
			App.values1(status);
		} else {
			res = false;
			Object status[] = {tcName, "Top displayed text is "+actText, actText, urlName+"_Permissions", "Failed", "", env};
			App.values1(status);
		}
		return res;
	}

}
