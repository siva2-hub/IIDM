package libraries;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import commonUtils.App;

public class AllModules extends App
{
	QuotePages quotes = new QuotePages();   RepairPages repair = new RepairPages();   PricingPages price = new PricingPages();
	public void logoutCheckURLRedirectsOrNot() throws Exception 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		String url[] = {"quote_for_parts","organizations", "contacts", "pricing", "discount-codes", "special-pricing", "repair-request", "jobs", "orders"
				, "part-purchase", "inventory", "account-type", "branches", "classification", "contact_types", "industry", "po_min_qty", "product_class"
				, "qc_control", "quote-approval", "quote-type", "regions", "sales_potential", "terms-conditions", "territory", "users", "user_roles", 
				"vendors", "warehouse", "zipcodes", "past-due-invoices", "point-of-sales"};
		int count =1;
		for(int i=0;i<url.length; i++) 
		{
			//Warning Pop Up
			App.displayPopUp("Checking "+url[i]+" Page redirects or not after logout and login");

			String openURL = driver.getCurrentUrl().replace(driver.getCurrentUrl().substring(39,driver.getCurrentUrl().length()), url[i]);
			driver.navigate().to(openURL);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			Thread.sleep(2500);
			driver.findElement(By.xpath("//*[@class='down-arrow']")).click();
			List<WebElement> btns = driver.findElements(By.xpath("//*[@role='menuitem']"));
			btns.get(1).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
			driver.findElement(By.id("username")).sendKeys(mail);
			driver.findElement(By.id("password")).sendKeys(pwd);
			price.clickButton("Sign In");
			App.spinner();
			Thread.sleep(1000);
			String openedURL = driver.getCurrentUrl();
			if (openURL.equals(openedURL)) {
				Object status[] = {"0"+count+"_Check_"+url[i].toUpperCase()+"_URLs_Redirects_To_Same_Or_Not_After_Logout_and_Login", "Actual URL is "+openedURL, "Expected URL is "+openURL,
						url[i].toUpperCase(), "Passed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			} else {
				Object status[] = {"0"+count+"_Check_"+url[i].toUpperCase()+"_URLs_Redirects_To_Same_Or_Not_After_Logout_and_Login", "Actual URL is "+openedURL, "Expected URL is "+openURL,
						url[i].toUpperCase(), "Failed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			}
			count++;
		}
	}
	public void linksRedirectsOrNot() throws Exception 
	{
		QuotePages qp = new QuotePages();
		String url[] = {"organizations", "contacts", "pricing", "discount-codes", "special-pricing", "repair-request", "quote_for_parts", "jobs", "orders"
				, "part-purchase", "inventory", "account-type", "branches", "classification", "contact_types", "industry", "po_min_qty", "product_class"
				, "qc_control", "quote-approval", "quote-type", "regions", "sales_potential", "terms-conditions", "territory", "users", "user_roles", 
				"vendors", "warehouse", "zipcodes", "past-due-invoices", "point-of-sales"};
		String data = "quote_for_parts";int count =1;
		for(int i=0; i<url.length; i++) 
		{
			String expURL = driver.getCurrentUrl().replace(data, url[i]);
			driver.navigate().to(expURL);
			data = url[i];
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			Thread.sleep(2500);
			String actURL = driver.getCurrentUrl();
			if (actURL.equals(expURL)) {
				Object status[] = {"Check_URLS_0"+count, "Actual URL is "+actURL, "Expected URL is "+expURL, url[i].toUpperCase(), "Passed", java.time.LocalDate.now().toString()};
				qp.values(status);
			} else {
				Object status[] = {"Check_URLS_0"+count, "Actual URL is "+actURL, "Expected URL is "+expURL, url[i].toUpperCase(), "Failed", java.time.LocalDate.now().toString()};
				qp.values(status);
			}
			count++;
		}
	}
	public void repairsModule(String stCode) throws Exception
	{
		//Display All Items Check-box
		//Warning Pop Up
		App.displayPopUp("REPAIRS_001_Verify_Display All Items");

		driver.findElement(By.xpath("//*[text()='Repairs']")).click();
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
			System.out.println(e.getMessage());
		}
		App.spinner();
		Thread.sleep(1200);
		boolean isSelected = false;
		try {

			if (driver.findElement(By.xpath("//*[@data-size = 'large']")).getAttribute("data-checked").equalsIgnoreCase("true")) {
				isSelected = false;
			}
		} catch (Exception e) {
			isSelected = true;
		}
		System.out.println("Is Selected status is "+isSelected);
		if (isSelected) {
			Object status[] = {"REPAIRS_001_Verify_Display All Items", "By default isSeleted status is "+isSelected, "", "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_001_Verify_Display All Items", "By default isSeleted status is "+isSelected, "", "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			driver.findElement(By.xpath("//*[@data-size='large']")).click();
		}
		//Filters In Repair List View
		//Warning Pop Up
		App.displayPopUp("REPAIRS_019_VerifyFilters");

		RepairPages repair = new RepairPages();
		repair.verifyFilters("123 E Doty Corporation", "Dallas House", "Check In Pending");

		//Filter's State Maintenance
		//Warning Pop Up
		App.displayPopUp("REPAIRS_020_VerifyFilterStateMaintanance");

		repair.verifyFilterStateMaintanance();

		//Create RMA
		//Warning Pop Up
		App.displayPopUp("REPAIRS_002_VerifyCreateRMA ");

		repair.createRMA();
		String expText = "CHECK IN PENDING";
		String repairId = driver.findElement(By.xpath("//*[@class ='id-num']")).getText().replace("#", "");
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			Object status[] = {"REPAIRS_002_VerifyCreateRMA "+repairId, actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"REPAIRS_002_VerifyCreateRMA "+repairId, actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//File Upload in Repair Detailed View
		//Warning Pop Up
		//		App.displayPopUp("");
		//				repair.fileUpload();
		//Add New Item
		repair.verifyAddNewItem();
		//Delete Row option in Add New Item Page
		//Warning Pop Up
		App.displayPopUp("REPAIRS_024_Verify_Delete_Row_In Add New Items");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Add Items']")));
		driver.findElement(By.xpath("//*[text()='Add Items']")).click();
		driver.findElement(By.xpath("//*[text()='Add New Items']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Serial No']")));
		driver.findElement(By.xpath("//*[text()='Delete Row']")).click();
		boolean sta = false;
		try {
			driver.findElement(By.xpath("//*[@placeholder='Serial No']")).isDisplayed();
			sta = false;
		} catch (Exception e) {
			sta = true;
		}
		if (sta) {
			Object status[] = {"REPAIRS_024_Verify_Delete_Row_In Add New Items", "Row Deleted Successfully", "", "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"REPAIRS_024_Verify_Delete_Row_In Add New Items", "Row Deleting Failed.!", "", "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		Thread.sleep(1500);
		//Delete Row option in Add New Item Page
		//Warning Pop Up
		App.displayPopUp("REPAIRS_025_Verify_Add_Another_Row_In Add New Items");

		driver.findElement(By.xpath("//*[text()='Add another row']")).click();
		Thread.sleep(1000);
		try {
			driver.findElement(By.xpath("//*[@placeholder='Serial No']")).isDisplayed();
			sta = true;
		} catch (Exception e) {
			sta = false;
		}
		if (sta) {
			Object status[] = {"REPAIRS_025_Verify_Add_Another_Row_In Add New Items", "Row Added Successfully", "", "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"REPAIRS_025_Verify_Add_Another_Row_In Add New Items", "Row Adding Failed.!", "", "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		price.closeIcon();
		Thread.sleep(1600);
		//Add Items To Repair
		//Warning Pop Up
		App.displayPopUp("REPAIRS_003_VerifySelectItemToRepair");

		actText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		driver.findElement(By.id("repair-items")).findElement(By.className("button-icon-text")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='side-drawer open']")));
		driver.findElement(By.xpath("//*[@placeholder='Search By Part Number']")).sendKeys(stCode);
		try {
			Thread.sleep(4500);
			driver.findElement(By.xpath("//*[text() = '? Click here to add them']")).isDisplayed();
			Thread.sleep(400);
			driver.findElement(By.xpath("//*[text() = '? Click here to add them']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Part Number']")));
			Thread.sleep(500);
			driver.findElement(By.id("async-select-example")).sendKeys("WAGO");
			Thread.sleep(1200);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'WAGO')]")));
			quotes.selectDropDown("WAGO CORPORATION");
			Thread.sleep(500);
			driver.findElement(By.xpath("//*[@placeholder='Part Number']")).sendKeys(stCode);
			driver.findElement(By.xpath("//*[@placeholder='Serial No']")).sendKeys(java.time.LocalTime.now().toString().substring(0, 8).replace(":", ""));
			driver.findElement(By.xpath("//*[text()='Add New Part']")).click();
			Thread.sleep(1500);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Actions act = new Actions(driver);
			driver.findElement(By.xpath("//*[@placeholder='Search By Part Number']")).click();
			act.sendKeys(Keys.TAB).build().perform();act.sendKeys(Keys.TAB).build().perform();act.sendKeys(Keys.TAB).build().perform();
			act.sendKeys(Keys.SPACE).build().perform();
			Thread.sleep(1000);
			List<WebElement> btn = driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElements(By.tagName("button"));
			for(int i=0;i<btn.size();i++) 
			{
				if(btn.get(i).getText().toLowerCase().contains("Add Selected".toLowerCase())) {
					btn.get(i).click();
					break;
				}
			}
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("repair-items")));
		Thread.sleep(2600);
		expText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		if (!actText.equals(expText)) {
			Object status[] = {"REPAIRS_003_VerifySelectItemToRepair", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_003_VerifySelectItemToRepair", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Assign Location
		//Warning Pop Up
		App.displayPopUp("REPAIRS_004_VerifyAssignLocation");

		List<WebElement>  btn=  driver.findElement(By.xpath("//*[@id='repair-items']")).findElements(By.xpath("//*[contains(@class,'action-item icon-bg-hover')]"));
		btn.get(0).click();
		Thread.sleep(1000);
		Thread.sleep(1500);
		Actions act = new Actions(driver);
		WebElement editIcon = driver.findElement(By.xpath("//*[@class='quantity-parent']")).findElement(By.tagName("svg"));
		act.moveToElement(editIcon).perform();
		editIcon.click();
		Thread.sleep(1400);
		if (driver.findElement(By.name("serial_no")).getAttribute("value").length()==0) {

			driver.findElement(By.name("serial_no")).sendKeys(java.time.LocalTime.now().toString().substring(0, 8).replace(":", ""));
			driver.findElement(By.xpath("//*[@title='Save Changes']")).click();
		} else {
			driver.findElement(By.xpath("//*[@title='Undo Changes']")).click();
		}
		driver.findElement(By.name("storage")).sendKeys("New York");
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();
		App.spinner();
		Thread.sleep(1200);
		expText = "CHECKED-IN";
		actText = driver.findElement(By.xpath("//*[@style = 'white-space: nowrap; max-width: 100%; text-overflow: ellipsis;']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			Object status[] = {"REPAIRS_004_VerifyAssignLocation", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_004_VerifyAssignLocation", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Assign Technician
		//Warning Pop Up
		App.displayPopUp("REPAIRS_005_VerifyAssignTechnician");

		Thread.sleep(1200);
		driver.findElement(By.xpath("//*[text()='Assign Technician']")).click();
		Thread.sleep(2000);
		driver.findElement(By.name("storage")).click();
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(Keys.SPACE).build().perform();
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		List<WebElement> btns = driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElements(By.tagName("button"));
		for(int i=0;i<btns.size();i++) {
			if(btns.get(i).getText().equalsIgnoreCase("Assign")) {
				btns.get(i).click();
				break;
			}
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2000);
		expText = "PENDING EVALUATION";
		actText = driver.findElement(By.xpath("//*[@style = 'white-space: nowrap; max-width: 100%; text-overflow: ellipsis;']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			Object status[] = {"REPAIRS_005_VerifyAssignTechnician", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_005_VerifyAssignTechnician", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Item Evaluation
		//Warning Pop Up
		App.displayPopUp("REPAIRS_006_VerifyEvaluateItem");

		driver.findElement(By.className("hides")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@class,'auto__dropdown-indicator')]")).click();
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		driver.findElement(By.name("estimated_hrs")).sendKeys("23");
		driver.findElement(By.name("price")).sendKeys("198");
		btns = driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElements(By.tagName("button"));
		btns.get(7).click();
		System.out.println("count of btns are "+btns.size());
		for(int i=0;i<btns.size();i++) {
			System.out.println(btns.get(i).getText());
		}
		App.spinner();
		Thread.sleep(1200);
		expText = "REPAIRABLE";
		actText = driver.findElement(By.xpath("//*[@style = 'white-space: nowrap; max-width: 100%; text-overflow: ellipsis;']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			Object status[] = {"REPAIRS_006_VerifyEvaluateItem", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_006_VerifyEvaluateItem", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Add Repairable Items To Quote
		//Warning Pop Up
		App.displayPopUp("REPAIRS_007_VerifyAddRepairableItemToQuote");

		Thread.sleep(1600);
		driver.findElement(By.xpath("//*[contains(@class,'check_box')]")).findElement(By.tagName("label")).click();
		price.clickButton("Add items to quote");
		Thread.sleep(1000);
		repair.toastContainer("Accept");
		App.spinner();
		Thread.sleep(1500);
		expText = "ADDED TO QUOTE";
		actText = driver.findElement(By.xpath("//*[@style = 'white-space: nowrap; max-width: 100%; text-overflow: ellipsis;']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			Object status[] = {"REPAIRS_007_VerifyAddRepairableItemToQuote", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_007_VerifyAddRepairableItemToQuote", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Go to Quotes for Repair Module
		//Warning Pop Up
		App.displayPopUp("REPAIRS_008_VerifyCreateQuoteFromRepair");

		Thread.sleep(2000);
		act.moveToElement(driver.findElement(By.xpath("//*[contains(@label,'2023')]"))).build().perform();
		driver.findElement(By.xpath("//*[contains(@label,'2023')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		expText = "OPEN";
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			Object status[] = {"REPAIRS_008_VerifyCreateQuoteFromRepair", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_008_VerifyCreateQuoteFromRepair", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Assign To QC
		Thread.sleep(2000);
		//Past Repair Prices
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'delete-icon')]")));
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.click(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Quote Price']")));
		Thread.sleep(1200);
		//Update source
		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(1).click();
		Thread.sleep(1200);
		act.sendKeys(Keys.ENTER).build().perform();
		if(driver.findElement(By.name("list_price")).getAttribute("value").equals("0.00")) {
			driver.findElement(By.xpath("//*[@placeholder='Quote Price']")).sendKeys("1795");
			for(int s=0; s<driver.findElement(By.xpath("//*[@placeholder='List Price']")).getAttribute("value").length(); s++) {
				driver.findElement(By.xpath("//*[@placeholder='List Price']")).sendKeys(Keys.BACK_SPACE);
			}
			driver.findElement(By.xpath("//*[@placeholder='List Price']")).sendKeys(Keys.CONTROL, "a");
			driver.findElement(By.xpath("//*[@placeholder='List Price']")).sendKeys("1795");
			driver.findElement(By.xpath("//*[@placeholder='IIDM Cost']")).sendKeys("189.20");
		}
		//Update leadTime
		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(2).click();
		Thread.sleep(1200);
		act.sendKeys(Keys.ENTER).build().perform();
		act.click(driver.findElements(By.xpath("//*[text()='Edit Quote Item']")).get(1)).build().perform();
		Thread.sleep(2500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'delete-icon')]")));
		//Warning Pop Up
		App.displayPopUp("REPAIRS_009_Verify_Past_Repair_Prices");

		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.click(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Quote Price']")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'email_invoices')]")));
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@src,'email_invoices')]")).click();
		Thread.sleep(4500);
		String pastPriceText = driver.findElement(By.xpath("//*[contains(@class, 'past-repair-invoice-grid')]")).getText();
		System.out.println("Past Price Text "+pastPriceText);
		driver.findElements(By.xpath("//*[contains(@src, 'cross')]")).get(1).click();
		Thread.sleep(1500);
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		//Past repair prices
		Thread.sleep(2000);	
		if (pastPriceText.equals("") || pastPriceText.contains("Data Not Found") || pastPriceText.contains("Sorry, you do not have permissions to access this page.")) {
			Object status[] = {"REPAIRS_009_Verify_Past_Repair_Prices", pastPriceText, "", "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			Thread.sleep(1500);
			driver.findElements(By.xpath("//*[contains(@src,'cross')]")).get(0).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Submit for Internal Approval']")));
		} else {
			Object status[] = {"REPAIRS_009_Verify_Past_Repair_Prices", pastPriceText, "", "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			driver.findElements(By.xpath("//*[contains(@src,'cross')]")).get(0).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Submit for Internal Approval']")));
		}
		Thread.sleep(1000);
		//		price.clickButton("Submit for internal approval");
		act.moveToElement(driver.findElement(By.xpath("//*[text()='Submit for Internal Approval']"))).build().perform();
		act.click(driver.findElement(By.xpath("//*[text()='Submit for Internal Approval']"))).build().perform();
		Thread.sleep(2000);
		repair.toastContainer("Proceed");
		//		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div/div/div[3]/div[2]/button"), "Submit for internal approval"));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text()='Submit for Internal Approval']"), "Submit for Internal Approval"));
		Thread.sleep(1700);
		//		price.clickButton("Approve");
		act.moveToElement(driver.findElement(By.xpath("//*[text()='Approve']"))).build().perform();
		act.click(driver.findElement(By.xpath("//*[text()='Approve']"))).build().perform();
		Thread.sleep(1200);
		repair.toastContainer("Approve");
		//		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button"), "Approve"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Submit for Customer Approval']")));
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).findElement(By.tagName("button")).click();
		driver.findElement(By.xpath("//*[@role='menuitem']")).click();
		App.spinner();
		Thread.sleep(1300);
		//		driver.findElement(By.xpath("//*[text()='Won']")).click();
		price.clickButton("Won");
		Thread.sleep(1200);
		repair.toastContainer("Proceed");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'Create Sales Order']")));
		App.spinner();
		//scroll into repair id
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//*[text()='Related to']")));
		Thread.sleep(1200);
		act.moveToElement(driver.findElement(By.xpath("//*[text() = '"+repairId+"']"))).build().perform();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[text() = '"+repairId+"']")).click();
		App.spinner();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src, 'repair_summary')]")));
		Thread.sleep(2300);
		//Repair Summary
		driver.findElement(By.xpath("//*[contains(@src, 'repair_summary')]")).click();
		App.spinner();
		driver.findElement(By.xpath("//*[contains(@class,'dropdown-indicator')]")).click();
		act.sendKeys(Keys.ENTER).build().perform();
		driver.findElement(By.xpath("//*[text() = 'Save']")).click();
		//Assign to QC
		//Warning Pop Up
		App.displayPopUp("REPAIRS_011_VerifyAssignToQC");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'Assign to QC']")));
		act.moveToElement(driver.findElement(By.xpath("//*[text() = 'Assign to QC']"))).build().perform();
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[text() = 'Assign to QC']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(@class,'dropdown-indicator')]")).click();
		act.sendKeys(Keys.ENTER).build().perform();
		driver.findElement(By.xpath("//*[text() = 'Assign']")).click();
		//		repair.toastContainer("Accept");
		Thread.sleep(2200);
		expText = "PENDING QC";
		actText = driver.findElement(By.xpath("//*[@style = 'white-space: nowrap; max-width: 100%; text-overflow: ellipsis;']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			Object status[] = {"REPAIRS_011_VerifyAssignToQC", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_011_VerifyAssignToQC", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//QC CheckList status Fail
		//Warning Pop Up
		App.displayPopUp("REPAIRS_010_VerifyQCCheckList_as_Fail");

		repair.verifyQCCheckList("Fail");
		//QC CheckList
		//Warning Pop Up
		App.displayPopUp("REPAIRS_012_VerifyQCCheckList");

		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[contains(@src,'qc_checklist')]")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[text()='Save']")));
		Thread.sleep(1300);
		driver.findElements(By.xpath("//*[contains(@class,'react-select__indicator')]")).get(0).click();
		act.sendKeys("Quality control report");
		Thread.sleep(1000);
		act.sendKeys(Keys.ENTER).build().perform();
		//		quotes.selectDropDown("Quality control report");
		App.spinner();
		Thread.sleep(1000);
		driver.findElements(By.xpath("//*[contains(@class,'react-select__indicator')]")).get(2).click();
		//		act.sendKeys(Keys.TAB).build().perform();act.sendKeys(Keys.TAB).build().perform();act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(1000);
		//		quotes.selectDropDown("Pass");
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.xpath("//*[@type='submit']")).click();
		Thread.sleep(1400);
		expText = "QC PASSED";
		actText = driver.findElement(By.xpath("//*[@style = 'white-space: nowrap; max-width: 100%; text-overflow: ellipsis;']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {

			Object status[] = {"REPAIRS_012_VerifyQCCheckList", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_012_VerifyQCCheckList", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Repair Report
		//Warning Pop Up
		App.displayPopUp("REPAIRS_013_Verify_Repair_Report");

		driver.findElement(By.xpath("//*[@style='padding-left: 8px;']")).findElement(By.tagName("button")).click();

		Thread.sleep(3500);
		Set<String> wCount = driver.getWindowHandles();
		Object[] ids = wCount.toArray();
		if (wCount.size()==2) {
			driver.switchTo().window(ids[1].toString());
			Thread.sleep(1500);
			price.takesScreenShot("repair_report.png");
			driver.close();
			Object status[] = {"REPAIRS_013_Verify_Repair_Report", "Repair Report Working!", "Opened Tab count is "+wCount.size(), "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			driver.switchTo().window(ids[0].toString());
			Thread.sleep(1700);
		} else {
			Object status[] = {"REPAIRS_013_Verify_Repair_Report", "Repair Report Not Working!", "Opened Tab count is "+wCount.size(), "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		System.out.println(wCount.size());
		//
		//Go To Quote Module
		act.moveToElement(driver.findElement(By.xpath("//*[contains(@label,'2023')]"))).build().perform();
		driver.findElement(By.xpath("//*[contains(@label,'2023')]")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class='quote-num-and-status']")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=' width-25 flexed']")));
		Thread.sleep(2600);
		String stockCode = driver.findElement(By.xpath("//*[@class=' width-25 flexed']")).findElement(By.tagName("h4")).getText();
		//Create Sales Order from Repair
		//Warning Pop Up
		App.displayPopUp("REPAIRS_014_VerifyCreateSalesOrder_FromRepair");

		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='button-icon-text ']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("customer_po_number")));
		Thread.sleep(1500);
		boolean test = false;
		try {
			Thread.sleep(1500);
			driver.findElement(By.xpath("//*[text() = 'item_notes']")).isDisplayed();
			test = true;
		} catch (Exception e) {

		}
		driver.findElement(By.name("customer_po_number")).sendKeys("PO1234");
		if (test) {
			driver.findElement(By.xpath("//*[@class='tooltip bottom']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
			Thread.sleep(2500);
			driver.findElement(By.xpath("//*[@style='display: flex; gap: 16px;']")).click();
			Thread.sleep(5500);
			driver.findElements(By.xpath("//*[text() = 'Create']")).get(1).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
			Thread.sleep(1600);
		} else {
			Thread.sleep(1300);
			driver.findElements(By.xpath("//*[text() = 'Create']")).get(1).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
			Thread.sleep(1600);
		}
		String orderId = "";
		String serverMsg = "";boolean server = false;
		try {
			Thread.sleep(1600);
			driver.findElement(By.className("server-msg")).isDisplayed();
			serverMsg= driver.findElement(By.className("server-msg")).getText();
			server = true;
		} catch (Exception e) {
			server = false;
		}
		if(server) {
			Object status[] = {"REPAIRS_014_VerifyCreateSalesOrder_FromRepair", serverMsg, "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			price.takesScreenShot("create_sales_order.png");
			driver.findElement(By.xpath("//*[@title='close']")).click();
		}else {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
			Thread.sleep(1600);
			String orderStatus = driver.findElement(By.xpath("//*[@title='[object Object]']")).getText();
			orderId = driver.findElement(By.className("id-num")).getText().replace("#", "");
			if (orderStatus.toLowerCase().equals("OPEN ORDER".toLowerCase())) {
				Object status[] = {"REPAIRS_014_VerifyCreateSalesOrder_FromRepair", "Sales Order "+orderId+" Created with status is "+orderStatus, "", "SalesOrderPage", "Passed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			} else {
				Object status[] = {"REPAIRS_014_VerifyCreateSalesOrder_FromRepair", "Sales Order "+orderId+" Created with status is "+orderStatus, "", "SalesOrderPage", "Failed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			}
		}
		//Back To Repair Module for check the Parts Purchase Icon is displayed or not
		//Warning Pop Up
		App.displayPopUp("REPAIRS_016_VerifyCreateJobFromRepair");
		List<WebElement> tabIds = driver.findElement(By.xpath("//*[@style = 'margin-left: 24px;']")).findElements(By.tagName("h4"));
		String jobId = "";
		if (tabIds.size()==3) {
			jobId = tabIds.get(2).getText();
			Object status[] = {"REPAIRS_016_VerifyCreateJobFromRepair", "Job created with Job Id is "+jobId, "", "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			tabIds.get(0).click();
		} else {
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		boolean partsPurchseIcon = false;
		Thread.sleep(2300);
		try {
			driver.findElement(By.xpath("//*[contains(@src,'partspurchase')]")).isDisplayed();
			partsPurchseIcon = true;
		} catch (Exception e) {
			partsPurchseIcon = false;
		}
		//Warning Pop Up
		App.displayPopUp("REPAIRS_015_Verify_Parts_Purchase_Icon_isDisplayed_OrNot");

		if (partsPurchseIcon) {
			driver.findElement(By.xpath("//*[contains(@src,'partspurchase')]")).click();
			Thread.sleep(1200);
			//			price.closeIcon();
			Object status[] = {"REPAIRS_015_Verify_Parts_Purchase_Icon_isDisplayed_OrNot", "Parts Purchase Icon is Displayed", "", "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			//Create Job from Repair
			if (tabIds.size()==3) {
				this.createPartsPurchase("PARTSPURCHASE_001_VerifyCreate_PartsPurchase", jobId,1);
			}else {
				this.verifyCreateJob("REPAIRS_016_VerifyCreateJobFromRepair", orderId, 2);
			}
		} else {
			Object status[] = {"REPAIRS_015_Verify_Parts_Purchase_Icon_isDisplayed_OrNot", "Parts Purchase Icon is not Displayed ", "", "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			//Create Job from Repair
			if (tabIds.size()==3) {

			}else {

				this.verifyCreateJob("REPAIRS_016_VerifyCreateJobFromRepair", orderId, 2);
			}
		}

	}
	public void clearButtonTopSearch() {
		try {
			driver.findElement(By.xpath("//*[@style = 'padding: 10px 10px 10px 0px; display: flex; align-items: center; cursor: pointer;']")).isDisplayed();
			driver.findElement(By.xpath("//*[@style = 'padding: 10px 10px 10px 0px; display: flex; align-items: center; cursor: pointer;']")).click();
			App.spinner();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void quotesModule(String leadTime, String leadValue, String discount) throws Exception
	{
		this.clearButtonTopSearch();
		//Search Functionality
		try {
			driver.findElement(By.xpath("//*[text() = 'Clear']")).isDisplayed();
			driver.findElement(By.xpath("//*[text() = 'Clear']")).click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//Search with Quote Id
		//Warning Pop Up
		App.displayPopUp("QUOTES_014_VerifySearchByQuoteId");
		quotes.verifyTopSearchInQuoteListView("2023053100074", 1);
		this.clearButtonTopSearch();
		//Search with Company Name
		//Warning Pop Up
		App.displayPopUp("QUOTES_015_VerifySearchByCompanyName");
		quotes.verifyTopSearchInQuoteListView("123 E Doty Corporation", 2);
		this.clearButtonTopSearch();
		//Search with Sales Person
		//Warning Pop Up
		App.displayPopUp("QUOTES_016_VerifySearchBySalesPersonName");
		quotes.verifyTopSearchInQuoteListView("Frontier", 3);
		this.clearButtonTopSearch();
		//Search with Email
		//Warning Pop Up
		App.displayPopUp("QUOTES_017_VerifySearchByEmail");
		quotes.verifyTopSearchInQuoteListView("pete.soto@motion-ind.com", 4);
		this.clearButtonTopSearch();
		//Filters In Quote List View
		//Warning Pop Up
		App.displayPopUp("QUOTES_018_VerifyFiltersInQuotesListView");
		quotes.verifyFiltersInQuoteListView("Zummo Meat Co Inc", "Jeremy Morgan", "Approved", "Swetha Epi", 1);
		//Filter's State Maintenance
		//Warning Pop Up
		App.displayPopUp("QUOTES_019_VerifyFiltersStateMaintanance");
		quotes.verifyFiltersStateMaintanance("Zummo Meat Co Inc", "Jeremy Morgan", "Approved", "Swetha Epi", 1);
		//Reset and Clear Buttons in Filter's Page
		//Warning Pop Up
		App.displayPopUp("QUOTES_020_VerifyResetandClearButtonInFiltersPage");
		quotes.verifyResetandClearButtonInFiltersPage("Zummo Meat Co Inc", 1);
		//Create Quote
		//Warning Pop Up
		App.displayPopUp("QUOTES_001_VerifyCreateQuote");
		quotes.createQuote();
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		String expText = "OPEN";
		if (actText.toLowerCase().contains(expText.toLowerCase())) 
		{
			Object status[] = {"QUOTES_001_VerifyCreateQuote", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"QUOTES_001_VerifyCreateQuote", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Add(Select) Items To Quote
		//Warning Pop Up
		App.displayPopUp("QUOTES_023_VerifyDeleteIconInQuoteItems");
		actText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		quotes.selectItemToQuote();
		//Delete Icon
		quotes.verifyDeleteIcon(1);
		actText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		//Warning Pop Up
		App.displayPopUp("QUOTES_002_VerifySelectItemToQuote");
		quotes.selectItemToQuote();
		expText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		if (actText!=expText) {

			Object status[] = {"QUOTES_002_VerifySelectItemToQuote", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"QUOTES_002_VerifySelectItemToQuote", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		String stockCode = driver.findElement(By.xpath("//*[@class=' width-25 flexed']")).findElement(By.tagName("h4")).getText();

		//Print and Download
		//Warning Pop Up
		//		App.displayPopUp("QUOTES_013_VerifyPrintFunctionality");
		//		quotes.verifyPrintDownLoad();

		//Check the Lead Time Displayed Or Not
		//Warning Pop Up
		App.displayPopUp("QUOTES_003_VerifyLeadTimeDisplayedOrNot");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Add Options']")));
		Thread.sleep(1000);
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.click(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		Thread.sleep(1600);
		driver.findElement(By.xpath("//*[@placeholder='Enter Gross Profit']")).click();
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(leadTime).build().perform();
		Thread.sleep(1500);
		//		driver.findElement(By.id("react-select-7-input")).sendKeys(leadTime);
		quotes.selectDropDown(leadTime);
		driver.findElement(By.name("lead_time_value")).sendKeys(leadValue);
		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(1).click();
		Thread.sleep(1200);
		act.sendKeys(Keys.ENTER).build().perform();
		price.clickButton("Edit Quote Item");Thread.sleep(2000);
		actText = driver.findElements(By.xpath("//*[@class='d-flex align-center g-16 ']")).get(1).findElement(By.tagName("h4")).getText();
		expText = leadValue+" "+leadTime;
		if (actText.contains(expText)) {

			Object status[] = {"QUOTES_003_VerifyLeadTimeDisplayedOrNot", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"QUOTES_003_VerifyLeadTimeDisplayedOrNot", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Edit Icon 
		//Warning Pop Up
		App.displayPopUp("QUOTES_004_VerifyBulkEdit");
		quotes.verifyDeleteIcon(2);
		//Bulk Edit
		driver.findElement(By.xpath("//*[contains(@class,'check_box')]")).findElement(By.tagName("label")).click();
		driver.findElement(By.xpath("//*[@class='quote-option-del-icon edit-icon']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("discount")));
		Thread.sleep(1800);
		driver.findElement(By.name("discount")).sendKeys(discount);
		price.clickButton("Edit Quote Items");Thread.sleep(2000);
		actText = driver.findElements(By.xpath("//*[contains(@class,'d-flex align-center g-8 ')]")).get(1).findElement(By.tagName("h4")).getText();
		expText = discount+" %";
		if (actText.equals(expText)) {

			Object status[] = {"QUOTES_004_VerifyBulkEdit", "Displayed Discount is "+actText, "Applied Discount is "+expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"QUOTES_004_VerifyBulkEdit", "Displayed Discount is "+actText, "Applied Discount is "+expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Proceed To Submit For Internal Approval
		//Warning Pop Up
		App.displayPopUp("QUOTES_005_VerifySubmitForInternalApproval");
		Thread.sleep(2400);
		WebElement rfq = driver.findElement(By.xpath("//*[@title='RFQ Received Date']"));
		Thread.sleep(1500);
		act.moveToElement(rfq).build().perform();
		Thread.sleep(1000);
		List<WebElement> edits = driver.findElement(By.xpath("//*[@id='repair-info-id']")).findElements(By.className("pi-label-edit-icon"));
		edits.get(0).click();
		driver.findElement(By.xpath("//*[@id='repair-info-id']")).findElement(By.tagName("button")).click();
		driver.findElement(By.xpath("//*[@title='Save Changes']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@title='Quote Requested By']")));
		Thread.sleep(1700);
		WebElement qReqBy = driver.findElement(By.xpath("//*[@title='Quote Requested By']"));
		Actions action = new Actions(driver);
		action.moveToElement(qReqBy).build().perform();
		Thread.sleep(1000);
		edits.get(2).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@class,'react-select__control')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@title='Save Changes']")).click();
		Thread.sleep(2500);
		driver.findElement(By.xpath("//*[text() ='Submit for Internal Approval']")).click();
		Thread.sleep(2000);
		repair.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[text() ='Submit for Internal Approval']")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class='quote-num-and-status']")));
		Thread.sleep(2500);
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		expText = "PENDING APPROVAL";
		if (actText.toLowerCase().contains(expText.toLowerCase())) {

			Object status[] = {"QUOTES_005_VerifySubmitForInternalApproval", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"QUOTES_005_VerifySubmitForInternalApproval", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Display the Revise Quote When Quote As Pending Approval
		//Warning Pop Up
		App.displayPopUp("QUOTES_006_Verify_Display_TheReviseQuote_When_Quote_Is_Pending_Approval");
		boolean revise = false;
		try {
			revise = driver.findElement(By.xpath("//*[text()='Revise Quote']")).isDisplayed();	
		} catch (Exception e) {
			revise = false;
		}
		System.out.println("Revise Quote button is Displayed ..? "+revise);
		if (revise) {

			Object status[] = {"QUOTES_006_Verify_Display_TheReviseQuote_When_Quote_Is_Pending_Approval", "Revise Quote button is Displayed ..? "+revise, "", "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"QUOTES_006_Verify_Display_TheReviseQuote_When_Quote_Is_Pending_Approval", "Revise Quote button is Displayed ..? "+revise, "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Approve The Quote
		//Warning Pop Up
		App.displayPopUp("QUOTES_007_VerifyApproveButton");
		Thread.sleep(1500);
		price.clickButton("Approve");
		Thread.sleep(1200);
		repair.toastContainer("Approve");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Approve']"), "Approve"));
		Thread.sleep(2800);
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		expText = "APPROVED";
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			Object status[] = {"QUOTES_007_VerifyApproveButton", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"QUOTES_007_VerifyApproveButton", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Send To Customer
		//Warning Pop Up
		App.displayPopUp("QUOTES_008_VerifySubmitForCustomerApproval");
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).findElement(By.tagName("button")).click();
		driver.findElement(By.xpath("//*[@role='menuitem']")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class='quote-num-and-status']")));
		Thread.sleep(2500);
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		expText = "DELIVERED TO CUSTOMER";
		if (actText.toLowerCase().contains(expText.toLowerCase())) {

			Object status[] = {"QUOTES_008_VerifySubmitForCustomerApproval", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"QUOTES_008_VerifySubmitForCustomerApproval", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Quote Won
		//Warning Pop Up
		App.displayPopUp("QUOTES_009_VerifyQuoteWon");
		repair.wonOrLostButton("Won");
		Thread.sleep(1200);
		repair.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Won']"), "Won"));
		Thread.sleep(1900);
		expText = "WON";
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {

			Object status[] = {"QUOTES_009_VerifyQuoteWon", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"QUOTES_009_VerifyQuoteWon", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Create Sales Order
		//Warning Pop Up
		App.displayPopUp("QUOTES_010_VerifyCreateSalesOrder_FromQuote");
		driver.findElement(By.xpath("//*[@class='button-icon-text ']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("customer_po_number")));
		Thread.sleep(1200);
		driver.findElement(By.name("customer_po_number")).sendKeys("PO1234");
		String stockItem = driver.findElement(By.xpath("/html/body/div/div/div[6]/div/div[1]/div/div[2]/div[3]")).getText();
		if (stockItem.equals("Stock Code "+stockCode+" does not exist")) {
			System.exit(0);
			driver.findElement(By.xpath("//*[@class='tooltip bottom']")).click();
		} else {
			Thread.sleep(1300);
			driver.findElement(By.xpath("/html/body/div[1]/div/div[6]/div/div[1]/div/div[3]/button")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
			Thread.sleep(1600);
		}
		String orderId = "";
		if(driver.findElements(By.xpath("//*[@class='side-drawer open']")).size()!=0) {
			String serverMsg = driver.findElement(By.className("server-msg")).getText();
			Object status[] = {"QUOTES_010_VerifyCreateSalesOrder", serverMsg, "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			price.takesScreenShot("create_sales_order.png");
			driver.findElement(By.xpath("//*[@title='close']")).click();
		}else {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
			Thread.sleep(1600);
			orderId = driver.findElement(By.className("id-num")).getText().replace("#", "");
			String orderStatus = driver.findElement(By.xpath("//*[@title='[object Object]']")).getText();
			if (orderStatus.toLowerCase().equals("OPEN ORDER".toLowerCase())) {
				//Warning Pop Up
				App.displayPopUp("QUOTES_011_VerifyCreateJobFromQuote");
				Object status[] = {"QUOTES_010_VerifyCreateSalesOrder_FromQuote", "Sales Order "+orderId+" Created with Order status is "+orderStatus, "", "SalesOrderPage", "Passed", java.time.LocalDate.now().toString()};
				quotes.values(status);
				//Create Job from Quote
				this.verifyCreateJob("QUOTES_011_VerifyCreateJobFromQuote", orderId, 2);
			} else {
				Object status[] = {"QUOTES_010_VerifyCreateSalesOrder_FromQuote", "Sales Order "+orderId+" Created with Order status is "+orderStatus, "", "SalesOrderPage", "Failed", java.time.LocalDate.now().toString()};
				quotes.values(status);
				//Create Job from Quote
				this.verifyCreateJob("QUOTES_011_VerifyCreateJobFromQuote", orderId, 2);
			}
		}
	}
	public Object[] createJob(String orderId) throws Exception
	{
		Actions act = new Actions(driver); PricingPages price = new PricingPages();
		driver.findElement(By.xpath("//*[text()= 'Jobs']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='button-icon-text']")));
		driver.findElement(By.xpath("//*[@class= 'button-icon-text']")).click();
		Thread.sleep(1500);
		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(0).click();
		act.sendKeys(orderId).build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(). '"+orderId+"')]")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		act.sendKeys(Keys.ENTER).build().perform();
		//		quotes.selectDropDown(orderId);
		Thread.sleep(500);
		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(1).click();
		Thread.sleep(1300);
		act.sendKeys(Keys.ENTER).build().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		Thread.sleep(1600);
		driver.findElement(By.name("job_description")).sendKeys("Test Job Description");
		driver.findElement(By.name("job_description")).click();
		act.sendKeys(Keys.TAB).build().perform();act.sendKeys(Keys.ARROW_RIGHT).build().perform();
		act.sendKeys(Keys.ENTER).build().perform();
		price.clickButton("Create Job");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		boolean createJob = false; String serverMsg = "";
		try {
			driver.findElement(By.xpath("//*[@class='side-drawer open']")).isDisplayed();
			price.takesScreenShot("CreateJob_Failed.png");
			driver.findElement(By.xpath("//*[@class='server-msg']")).isDisplayed();
			serverMsg = driver.findElement(By.xpath("//*[@class='server-msg']")).getText();
			createJob = false;
		} catch (Exception e) {
			createJob = true;
		}
		Object[] vals = {serverMsg, createJob};
		return vals;
	}
	public boolean verifyCreateJob(String tcName ,String salesOrderId, int count) throws Exception
	{
		String orderId = "";
		if (count==1) {
			driver.findElement(By.xpath("//*[text()= 'Orders']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@placeholder,'Sales Order ID')]")));
			driver.findElement(By.xpath("//*[contains(@placeholder,'Sales Order ID')]")).sendKeys(salesOrderId);
			Thread.sleep(1500);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='OPEN ORDER']")));
			Thread.sleep(1500);
			driver.findElement(By.xpath("//*[text()='OPEN ORDER']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='id-num']")));
			orderId = driver.findElement(By.xpath("//*[@class='id-num']")).getText().replace("#", "");
		} else {
			orderId = salesOrderId;
		}
		Object val[] = this.createJob(orderId); boolean res = false;
		if ((Boolean) val[1]) {
			String actJobStatus = driver.findElement(By.xpath("//*[@title='[object Object]']")).getText();
			String expJobStatus = "Completed";
			if (actJobStatus.toLowerCase().equals(expJobStatus.toLowerCase())) {
				res = true;
				Object status[] = {tcName, actJobStatus, expJobStatus, "JobsPage", "Passed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			} else {
				res = false;
				Object status[] = {tcName, actJobStatus, expJobStatus, "JobsPage", "Failed", java.time.LocalDate.now().toString()};
				quotes.values(status);
				price.closeIcon();
			}
		} else {
			res = false;
			Object status[] = {tcName, val[0], "", "JobsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			price.closeIcon();
		}
		return res;
	}
	public void createPartsPurchase(String tcName, String jobId, int count) throws Exception 
	{
		Actions act = new Actions(driver);
		//		if (count==1) {
		//			driver.findElement(By.xpath("//*[text()= 'Parts Purchase']")).click();
		//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()= 'Create Parts Purchase']")));
		//			driver.findElement(By.xpath("//*[text()= 'Create Parts Purchase']")).click();
		//		} else {
		//		}
		//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		//		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		//		//Requested By
		//		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(0).click();
		//		act.sendKeys(Keys.ARROW_DOWN).build().perform();
		//		act.sendKeys(Keys.ENTER).build().perform();
		//		//Technician
		//		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(1).click();
		//		act.sendKeys(Keys.ARROW_DOWN).build().perform();
		//		act.sendKeys(Keys.ENTER).build().perform();
		//		//Urgency
		//		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(2).click();
		//		act.sendKeys("Standard").build().perform();
		//		act.sendKeys(Keys.ENTER).build().perform();
		driver.findElement(By.xpath("//*[text()= 'Next']")).click();

		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(2).click();
		act.sendKeys("omron").build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'OMRON')]")));
		act.sendKeys(Keys.ENTER).build().perform();
		driver.findElement(By.name("vendor_contact_name")).sendKeys("testAbb");
		//		driver.findElement(By.name("email")).sendKeys("test@enterpi.com");
		//		driver.findElement(By.name("vendor_phone")).sendKeys("1234567898");
		//		driver.findElement(By.name("vendor_quote_number")).sendKeys("98948h3");
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[text()= 'Item Information']")).click();
		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(3).click();
		act.sendKeys("omron").build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'omron')]")));
		act.sendKeys(Keys.ENTER).build().perform();
		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(3).click();
		act.sendKeys("BACO").build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		act.sendKeys(Keys.ENTER).build().perform();
		act.sendKeys("omron").build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'OMRON')]")));
		act.sendKeys(Keys.ENTER).build().perform();

		//		driver.findElement(By.xpath("//*[@placeholder= 'Enter Mfg Part Number']")).sendKeys("baco14524");
		//		driver.findElement(By.xpath("//*[@placeholder= 'Enter Quantity']")).sendKeys("1");
		driver.findElement(By.xpath("//*[@placeholder= 'Enter Cost']")).sendKeys("138");
		driver.findElement(By.xpath("//*[@placeholder= 'Enter Vendor Part Number']")).sendKeys("2321354");

		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(4).click();
		act.sendKeys(jobId).build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		act.sendKeys(Keys.ENTER).build().perform();
		act.moveToElement(driver.findElement(By.xpath("//*[text()= 'Create']"))).build().perform();
		//		act.doubleClick(driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div[2]/div[5]/div/div[1]/div/div[2]/div/div/div[4]/div[1]/div/button"))).build().perform();
		driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div[2]/div[5]/div/div[1]/div/div[2]/div/div/div[4]/div[1]/div/button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		boolean createPp = false; String serverMsg = "";
		try {
			driver.findElement(By.xpath("//*[@class='side-drawer open']")).isDisplayed();
			price.takesScreenShot("CreatePartsPurchase_Failed.png");
			driver.findElement(By.xpath("//*[@style='color: red;']")).isDisplayed();
			serverMsg = driver.findElement(By.xpath("//*[@style='color: red;']")).getText();
			createPp = false;
		} catch (Exception e) {
			createPp = true;
		}
		if (createPp) {
			String actPpStatus = driver.findElement(By.xpath("//*[@title='[object Object]']")).getText();
			String expPpStatus = "Completed";
			if (actPpStatus.toLowerCase().equals(actPpStatus.toLowerCase())) {
				Object status[] = {tcName, actPpStatus, expPpStatus, "PartsPurchasePage", "Passed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			} else {
				Object status[] = {tcName, actPpStatus, expPpStatus, "PartsPurchasePage", "Failed", java.time.LocalDate.now().toString()};
				quotes.values(status);
				price.closeIcon();
			}
		} else {
			Object status[] = {tcName, serverMsg, "", "PartsPurchasePage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			price.closeIcon();
		}
	}
}
