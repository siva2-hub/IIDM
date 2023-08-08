package libraries;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import commonUtils.App;

public class FiltersPages extends App
{
	WebDriverWait wait;
	Actions act ;
	QuotePages qp  = new QuotePages();
	public void filtersInPartsPucrhase(String tech, String urgency, String tcName, String env) throws Exception 
	{
		//Warning Pop Up
		App.displayPopUp(tcName);

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
			String actTech = App.getGridData(1);
			String actUrgency = App.getGridData(3);
			System.out.println("actual technician "+actTech +""+actUrgency);
			if (actTech.contains(tech) && actUrgency.contains(urgency)) 
			{
				Object status[] = {tcName, "Displayed Filter is "+actTech, "Applied Filter is "+tech, "PartsPurchasePage",
						"Passed", java.time.LocalDateTime.now().toString(), env};
				App.values1(status);
			} else {
				Object status[] = {tcName, "Displayed Filter is "+actTech, "Applied Filter is "+tech, "PartsPurchasePage",
						"Failed", java.time.LocalDateTime.now().toString(), env};
				App.values1(status);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Object status[] = {tcName, "Applied Filters not Available", "", "PartsPurchasePage", "Passed", java.time.LocalDateTime.now().toString()};
			App.values1(status);
		}
	}

	public void filtersInDiscountCodes(String tcName, String minQty, String env)throws Exception 
	{
		//Warning Pop Up
		App.displayPopUp(tcName);

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
			String actPOMinQty = App.getGridData(1);
			if (actPOMinQty.contains(minQty) ) 
			{
				Object status[] = {tcName, "Displayed Filter is "+actPOMinQty, "Applied Filter is "+minQty,
						"DiscountCodesPage", "Passed", java.time.LocalDateTime.now().toString(), env};
				App.values1(status);
			} else {
				Object status[] = {tcName, "Displayed Filter is "+actPOMinQty, "Applied Filter is "+minQty,
						"DiscountCodesPage", "Failed", java.time.LocalDateTime.now().toString(), env};
				App.values1(status);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Object status[] = {tcName, "Applied Filters not Available", "", "DiscountCodesPage", "Passed", java.time.LocalDateTime.now().toString(), env};
			App.values1(status);
		}
	}
	public void filtersInOrganizations(String tcName, String actType, String stats, int count, String env) throws Exception
	{
		//Warning Pop Up
		App.displayPopUp(tcName);

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
		act.sendKeys(Keys.ENTER).build().perform();
		//Clicking on apply button
		this.applyButton(); App.spinner();
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
				actAtType= App.getGridData(5);
			}
			String actStats = driver.findElement(By.xpath("//*[@class = 'ag-react-container']")).getText();
			if (actAtType.contains(actType) || actStats.equalsIgnoreCase(stats)) 
			{
				Object status[] = {tcName, "Displayed Filter is "+actAtType+", "+actStats,
						"Applied Filter is "+actType+", "+stats, moduleName+"Page", "Passed", java.time.LocalDateTime.now().toString(), env};
				App.values1(status);
			} else {
				Object status[] = {tcName, "Displayed Filter is "+actAtType+", "+actStats,
						"Applied Filter is "+actType+", "+stats, moduleName+"Page", "Failed", java.time.LocalDateTime.now().toString(), env};
				App.values1(status);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Object status[] = {tcName, "Applied Filters not Available", "", "OrganizationssPage", "Passed", java.time.LocalDateTime.now().toString(), env};
			App.values1(status);
		}
	}
	public void openFiltersInAllModules(String moduleName, int count) throws SQLException 
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
		driver.findElement(By.xpath(App.clickLabel("filter_btn"))).click();
	}
	public boolean filtersInPricingListView(String disCountCode, String env) throws Exception 
	{
		String dt = java.time.LocalDateTime.now().toString();
		PricingPages price = new PricingPages();
		price.pricingPage("Pricing");
		//Warning Pop Up
		App.displayPopUp("FILT_005_Verify_Filters_In_Pricing");

		Actions act = new Actions(driver);
		driver.findElement(By.xpath(App.clickLabel("filter_btn"))).click();
		Thread.sleep(2500);

		driver.findElements(By.xpath("//*[contains(@class, 'react-select__indicator')]")).get(2).click();
		act.sendKeys(disCountCode).build().perform(); act.sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(2000);
		QuotePages quotes = new QuotePages();
		quotes.selectDropDown(disCountCode);
		this.applyButton();
		Thread.sleep(1600);
		App.spinner();
		Thread.sleep(1500);
		String actDisCode = App.getGridData(3);
		driver.findElement(By.xpath(App.clickLabel("filter_clear"))).click();
//		java.time.LocalDate.now().toString()
		boolean res = false;
		if (actDisCode.equals(disCountCode)) {
			res = true;
			Object status[] = {"FILT_001_Verify_Filters_In_Pricing", "Displayed Filter is "+actDisCode,
					"Applied Filter is "+disCountCode, "PricingPage", "Passed", dt, env};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {"FILT_001_Verify_Filters_In_Pricing", "Displayed Filter is "+actDisCode,
					"Applied Filter is "+disCountCode, "PricingPage", "Failed", dt, env};
			qp.values(status);
		}
		return res;
	}
	public void filtersInAdminmModule(String tabName, String stats, String tcName, String env) throws Exception
	{
		Actions act = new Actions(driver);
		driver.findElement(By.xpath(App.clickLabel("click_admin"))).click();
		App.spinner(); Thread.sleep(1000);
		//Warning Pop Up
		App.displayPopUp(tcName);
		driver.findElement(By.xpath("//*[text() = '"+tabName+"']")).click();
		App.spinner(); String actText = "";
		App.clearFilter(); App.spinner();
		App.clearTopSearch(); App.spinner();
		App.spinner();
		driver.findElement(By.xpath(App.clickLabel("filter_btn"))).click();
		switch (tabName) {
			case "Territories":
				//Selecting the Branch in filters page
				driver.findElements(By.xpath("//*[contains(@class, 'react-select__indicator')]")).get(0).click();
				act.sendKeys(stats).build().perform();
				act.sendKeys(Keys.ENTER).build().perform();
				break;
			case "Zip Codes":
				//Selecting the Sales Person in filters page
				driver.findElements(By.xpath("//*[contains(@class, 'react-select__indicator')]")).get(2).click();
				act.sendKeys(stats).build().perform();
				act.sendKeys(Keys.ENTER).build().perform();
				break;
				
			default:
				//Selecting the Status in filters page
				driver.findElements(By.xpath("//*[contains(@class, 'react-select__indicator')]")).get(0).click();
				act.sendKeys(stats).build().perform();
				act.sendKeys(Keys.ENTER).build().perform();
				break;
		}
		this.applyButton(); App.spinner(); Thread.sleep(1200);
		App.horizentalScroll();
		if (tabName.equals("Territories")) {
			actText = App.getGridData(5);
		} else if(tabName.equals("Zip Codes")) {
			actText = App.getGridData(3);
		} else {
			actText = driver.findElement(By.xpath(App.clickLabel("status_text_grid"))).getText();
		}
		String expText = stats;
		if (actText.toLowerCase().equals(expText.toLowerCase())) {
			Object status[] = {tcName, "Displayed Filter is "+actText, "Applied Filter is "+expText, tabName+"Page",
					"Passed", java.time.LocalDateTime.now().toString(), env};
			App.values1(status);
		} else {
			Object status[] = {tcName, "Displayed Filter is "+actText, "Applied Filter is "+expText, tabName+"Page",
					"Failed", java.time.LocalDateTime.now().toString(), env};
			App.values1(status);
		}
	}
	public void applyButton() throws Exception
	{
		driver.findElement(By.xpath("//*[text() = 'Apply']")).click();
		Thread.sleep(1200);
		App.spinner();
		Thread.sleep(1200);
	}
}
