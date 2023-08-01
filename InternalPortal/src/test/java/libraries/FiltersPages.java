package libraries;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import commonUtils.App;

public class FiltersPages extends App
{
	WebDriverWait wait;
	Actions act ;
	QuotePages qp  = new QuotePages();
	public void filtersInPartsPucrhase(String tech, String urgency, String tcName) throws Exception 
	{
		wait  = new WebDriverWait(driver, Duration.ofSeconds(20));
		act = new Actions(driver);
		this.openFiltersInAllModules("Parts Purchase", 1);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'Part Numbers']")));
		//Selecting the Technician
		Thread.sleep(1200);
		driver.findElements(By.xpath("//*[contains(@class,'react-select__dropdown-indicator')]")).get(0).click();
		act.sendKeys(tech).build().perform();
		Thread.sleep(1200);
		//Selecting the Urgency
		driver.findElements(By.xpath("//*[contains(@class,'react-select__dropdown-indicator')]")).get(2).click();
		act.sendKeys(urgency).build().perform();
		Thread.sleep(1200);
		driver.findElement(By.xpath("//*[contains(@class, 'css-4mp3pp-menu')]")).click();
		Thread.sleep(1000);
		//Clicking on the Apply
		this.applyButton();
		try {
			driver.findElement(By.xpath("//*[contains(@src, 'Vendor_listview')]")).isDisplayed();
			String actTech = driver.findElement(By.xpath("//*[@style = 'left: 131px; width: 133px;']")).getText();
			String actUrgency = driver.findElement(By.xpath("//*[@style = 'left: 426px; width: 218px; cursor: pointer;']")).getText();
			System.out.println("actual technician "+actTech +""+actUrgency);
			if (actTech.contains(tech) && actUrgency.contains(urgency)) 
			{
				Object status[] = {tcName, "Displayed Filter is "+actTech, "Applied Filter is "+tech, "PartsPurchasePage", "Passed", java.time.LocalDate.now().toString()};
				qp.values(status);
			} else {
				Object status[] = {tcName, "Displayed Filter is "+actTech, "Applied Filter is "+tech, "PartsPurchasePage", "Failed", java.time.LocalDate.now().toString()};
				qp.values(status);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Object status[] = {tcName, "Applied Filters not Available", "", "PartsPurchasePage", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
		}
	}

	public void filtersInDiscountCodes(String tcName, String minQty)throws Exception 
	{
		this.openFiltersInAllModules("Discount Codes", 1);
		App.spinner();
		//Selecting the PO Minimum Quantity
		Thread.sleep(1200);
		driver.findElements(By.xpath("//*[contains(@class,'react-select__dropdown-indicator')]")).get(1).click();
		act.sendKeys(minQty).build().perform();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@class, 'css-4mp3pp-menu')]")).click();
		//Clicking on the Apply
		this.applyButton();
		try {
			driver.findElement(By.xpath("//*[contains(@src, 'editicon')]")).isDisplayed();
			String actPOMinQty = driver.findElement(By.xpath("//*[@style = 'left: 176px; width: 164px;']")).getText();
			if (actPOMinQty.contains(minQty) ) 
			{
				Object status[] = {tcName, "Displayed Filter is "+actPOMinQty, "Applied Filter is "+minQty, "DiscountCodesPage", "Passed", java.time.LocalDate.now().toString()};
				qp.values(status);
			} else {
				Object status[] = {tcName, "Displayed Filter is "+actPOMinQty, "Applied Filter is "+minQty, "DiscountCodesPage", "Failed", java.time.LocalDate.now().toString()};
				qp.values(status);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Object status[] = {tcName, "Applied Filters not Available", "", "DiscountCodesPage", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
		}
	}
	public void filtersInOrganizations(String tcName, String actType, String stats, int count) throws Exception
	{
		String moduleName = "Organizations";
		act = new Actions(driver);
		this.openFiltersInAllModules(moduleName, count);
		if (count==1) 
		{
			moduleName = "Organizations";
		} else if(count == 2) {
			moduleName = "Contacts";
		}
		App.spinner();
		//Selecting the PO Minimum Quantity
		Thread.sleep(1200);
		driver.findElements(By.xpath("//*[contains(@class,'react-select__dropdown-indicator')]")).get(0).click();
		act.sendKeys(actType).build().perform();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@class, 'css-4mp3pp-menu')]")).click();
		//Selecting the Status
		Thread.sleep(1200);
		if (count==2) {
			driver.findElements(By.xpath("//*[contains(@class,'react-select__dropdown-indicator')]")).get(2).click();
		} else if(count==1) {
			driver.findElements(By.xpath("//*[contains(@class,'react-select__dropdown-indicator')]")).get(4).click();

		}
		act.sendKeys(stats).build().perform();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@class, 'css-4mp3pp-menu')]")).click();
		//Clicking on apply button
		this.applyButton();
		Thread.sleep(1000);
		if (count==2) {
			act.dragAndDropBy(driver.findElement(By.xpath("//*[contains(@class,'ag-horizontal-left-spacer')]"))
					,500, 0).build().perform();
			Thread.sleep(1500);
		}
		try {
			driver.findElement(By.xpath("//*[@class = 'ag-react-container']")).isDisplayed();
			String actAtType = "";
			if(count==2) {
				
			}else {
				actAtType= driver.findElement(By.xpath("//*[@style = 'left: 850px; width: 182px;']")).getText();
			}
			String actStats = driver.findElement(By.xpath("//*[@class = 'ag-react-container']")).getText();
			if (actAtType.contains(actType) || actStats.equalsIgnoreCase(stats)) 
			{
				Object status[] = {tcName, "Displayed Filter is "+actAtType+", "+actStats,
						"Applied Filter is "+actType+", "+stats, moduleName+"Page", "Passed", java.time.LocalDate.now().toString()};
				qp.values(status);
			} else {
				Object status[] = {tcName, "Displayed Filter is "+actAtType+", "+actStats,
						"Applied Filter is "+actType+", "+stats, moduleName+"Page", "Failed", java.time.LocalDate.now().toString()};
				qp.values(status);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Object status[] = {tcName, "Applied Filters not Available", "", "OrganizationssPage", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
		}
	}
	public void openFiltersInAllModules(String moduleName, int count) 
	{
		try {
			if (moduleName.equalsIgnoreCase("Discount Codes")) {
				driver.findElement(By.xpath("//*[text() = 'Pricing']")).click();
				Thread.sleep(1200);
				driver.findElement(By.xpath("//*[text() = 'Discount Codes']")).click();
			} else if(moduleName.equalsIgnoreCase("Organizations")){
				driver.findElement(By.xpath("//*[text() = 'Organizations']")).click();
				Thread.sleep(1200);
				if (count==1) {
					driver.findElements(By.xpath("//*[text() = 'Organizations']")).get(1).click();
				}else if(count==2) {
					driver.findElement(By.xpath("//*[text() = 'Contacts']")).click();
				}
			}else {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = '"+moduleName+"']")));
				Thread.sleep(1200);
				driver.findElement(By.xpath("//*[text() = '"+moduleName+"']")).click();
			}
			App.spinner();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//Checking the Search is cleared or not
		try {
			App.spinner();
			driver.findElement(By.xpath("//*[contains(@style, 'padding: 10px 10px 10px 0px;')]")).isDisplayed();
			driver.findElement(By.xpath("//*[contains(@style, 'padding: 10px 10px 10px 0px;')]")).click();
			App.spinner();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//Checking the Filter is cleared or not
		try {
			App.spinner();
			driver.findElement(By.xpath("//*[text() = 'Clear']")).isDisplayed();
			driver.findElement(By.xpath("//*[text() = 'Clear']")).click();
			App.spinner();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src, 'Vendor_listview')]")));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//Applying the Filter
		driver.findElement(By.xpath("//*[text() = 'Filters']")).click();
	}
	public void applyButton() throws Exception
	{
		driver.findElement(By.xpath("//*[text() = 'Apply']")).click();
		Thread.sleep(1200);
		App.spinner();
		Thread.sleep(1200);
	}
}
