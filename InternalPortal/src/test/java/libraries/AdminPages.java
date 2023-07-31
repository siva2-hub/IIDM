package libraries;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminPages extends Permissions
{
	WebDriverWait wait ;
	Actions act ;
	QuotePages quotes;
	RepairPages repair;
	public boolean addAndEditsInAdminPages(String itemName, String tabName, String tcCount) throws Exception
	{
		wait= new WebDriverWait(driver, Duration.ofSeconds(30));
		act = new Actions(driver);
		quotes = new QuotePages();
		price = new PricingPages();
		repair = new RepairPages();
		this.headerMenu(itemName);
		this.adminLeftMenu(tabName);
		if (driver.findElement(By.xpath("//*[contains(@placeholder,'Search By')]")).getAttribute("value").length()==0) {

		} else {
			driver.findElement(By.xpath("//*[@class='Cross-svg close-icon-container']")).click();
			Thread.sleep(1000);
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'editicon')]")));
		Thread.sleep(1500);
		WebElement editIcon = driver.findElement(By.xpath("//*[contains(@src,'editicon')]"));
		editIcon.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Update']")));
		Thread.sleep(1400);
		List<WebElement> stats = driver.findElements(By.xpath("//*[contains(@class,'singleValue')]"));
		boolean res = false;
		for(int i=0; i<stats.size(); i++) 
		{
			if(stats.get(i).getText().equals("Active")) 
			{
				stats.get(i).click();
				String fName = "";
				if (tabName.equals("Zip Codes")) {
					fName = "zipcode";
				} else {
					fName = "name";
				}
				String name = driver.findElement(By.name(fName)).getAttribute("value");
				System.out.println("search with name is "+name);
				Thread.sleep(2000);
				quotes.selectDropDown("InActive");
				driver.findElement(By.xpath("//*[text()='Update']")).click();
				Thread.sleep(2000);
				if (driver.findElements(By.xpath("//*[contains(@role,'dialog')]")).size()==0) 
				{
					if (driver.findElement(By.xpath("//*[contains(@placeholder,'Search By')]")).getAttribute("value").length()==0) {

						driver.findElement(By.xpath("//*[contains(@placeholder,'Search By')]")).sendKeys(name);
						Thread.sleep(1600);
					} else {
						driver.findElement(By.xpath("//*[@class='Cross-svg close-icon-container']")).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath("//*[contains(@placeholder,'Search By')]")).sendKeys(name);
						Thread.sleep(1600);
					}
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'editicon')]")));
					Thread.sleep(3000);
					editIcon = driver.findElement(By.xpath("//*[contains(@src,'editicon')]"));
					editIcon.click();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Update']")));
					Thread.sleep(1400);
					stats = driver.findElements(By.xpath("//*[contains(@class,'singleValue')]"));
					String actText = stats.get(i).getText();
					String expText = "Active";
					stats.get(i).click();
					Thread.sleep(2000);
					quotes.selectDropDown("Active");
					driver.findElement(By.xpath("//*[text()='Update']")).click();
					if (actText.equals("InActive")) {
						res = true;
						Object status[] = {"ADMIN_"+tcCount+"_Verify_Update_"+tabName+" Name is "+name, "After Update "+actText,
								"Before Update "+expText, "AdminPage", "Passed", java.time.LocalDate.now().toString()};
						qp.values(status);
					} else {
						res = false;
						Object status[] = {"ADMIN_"+tcCount+"_Verify_Update_"+tabName+" Name is "+name, "After Update "+actText,
								"Before Update "+expText, "AdminPage", "Failed", java.time.LocalDate.now().toString()};
						qp.values(status);
					}
				} else {
					String serMsg = driver.findElement(By.className("server-msg")).getText();
					res = false;
					Object status[] = {"ADMIN_"+tcCount+"_Verify_Update_"+tabName, "Update Not working ", "Displayed Text is  "+serMsg
							, "AdminPage", "Failed", java.time.LocalDate.now().toString()};
					qp.values(status);
				}
				break;
			}else if(stats.get(i).getText().equals("InActive")) {
				stats.get(i).click();String fName = "";
				if (tabName.equals("Zip Codes")) {
					fName = "zipcode";
				} else {
					fName = "name";
				}
				String name = driver.findElement(By.name(fName)).getAttribute("value");
				Thread.sleep(2000);
				quotes.selectDropDown("Active");
				driver.findElement(By.xpath("//*[text()='Update']")).click();
				Thread.sleep(2000);
				if (driver.findElements(By.xpath("//*[contains(@role,'dialog')]")).size()==0) {
					if (driver.findElement(By.xpath("//*[contains(@placeholder,'Search By')]")).getAttribute("value").length()==0) {

						driver.findElement(By.xpath("//*[contains(@placeholder,'Search By')]")).sendKeys(name);
						Thread.sleep(1600);
					} else {
						driver.findElement(By.xpath("//*[@class='Cross-svg close-icon-container']")).click();
						Thread.sleep(1000);
						driver.findElement(By.xpath("//*[contains(@placeholder,'Search By')]")).sendKeys(name);
						Thread.sleep(1600);
					}
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'editicon')]")));
					Thread.sleep(3000);
					editIcon = driver.findElement(By.xpath("//*[contains(@src,'editicon')]"));
					editIcon.click();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Update']")));
					Thread.sleep(1400);
					stats = driver.findElements(By.xpath("//*[contains(@class,'singleValue')]"));
					String actText = stats.get(i).getText();
					String expText = "InActive";
					price.closeIcon();
					if (actText.equals("Active")) {
						res = true;
						Object status[] = {"ADMIN_"+tcCount+"_Verify_Update_"+tabName+" Name is "+name, "After Update "+actText,
								"Before Update "+expText, "AdminPage", "Passed", java.time.LocalDate.now().toString()};
						qp.values(status);
					} else {
						res = false;
						Object status[] = {"ADMIN_"+tcCount+"_Verify_Update_"+tabName+" Name is "+name, "After Update "+actText,
								"Before Update "+expText, "AdminPage", "Failed", java.time.LocalDate.now().toString()};
						qp.values(status);
					}
				} else {
					String serMsg = driver.findElement(By.className("server-msg")).getText();
					res = false;
					Object status[] = {"ADMIN_"+tcCount+"_Verify_Update_"+tabName, "Update Not working ", "Displayed Text is  "+serMsg
							, "AdminPage", "Failed", java.time.LocalDate.now().toString()};
					qp.values(status);
				}
				break;
			}
			editIcon = driver.findElement(By.xpath("//*[contains(@src,'editicon')]"));
		}
		return res;
	}
	public boolean verifyQuoteApproval(String itemName, String tabName, String tcCount, String btnName) throws Exception
	{
		this.headerMenu(itemName);
		this.adminLeftMenu(tabName);
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='"+btnName+"']"))));
		driver.findElement(By.xpath("//*[text()='"+btnName+"']")).click();
		boolean sta = false; boolean res = false;String message = "";
		Thread.sleep(700);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='Updated Successfully']"))));
		message = driver.findElement(By.xpath("//*[text()='Updated Successfully']")).getText();
		if (message.equals("Updated Successfully1")) {
			res = true;
			Object status[] = {"ADMIN_"+tcCount+"_Verify_Update_"+tabName, "Displayed Text is Updated Successfully",
					"", "AdminPage", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {"ADMIN_"+tcCount+"_Verify_Update_"+tabName, "Not Update "+tabName+"!",
					"", "AdminPage", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
		}
		return res;
	}
}
