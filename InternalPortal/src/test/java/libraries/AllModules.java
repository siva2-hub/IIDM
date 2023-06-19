package libraries;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
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
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='down-arrow']")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			Thread.sleep(2000);
			String openedURL = driver.getCurrentUrl();
			if (openURL.equals(openedURL)) {
				Object status[] = {"Check_"+url[i].toUpperCase()+"_URLs_Redirects_To_Same_Or_Not_After_Logout_0"+count, "Actual URL is "+openedURL, "Expected URL is "+openURL,
						url[i].toUpperCase(), "Passed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			} else {
				Object status[] = {"Check_"+url[i].toUpperCase()+"_URLs_Redirects_To_Same_Or_Not_After_Logout_0"+count, "Actual URL is "+openedURL, "Expected URL is "+openURL,
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
		driver.findElement(By.xpath("//*[text()='Repairs']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'vendor_logo')]")));
		String isSelected = driver.findElement(By.xpath("//*[@data-size='large']")).getAttribute("data-checked");
		System.out.println("Is Selected status is "+isSelected);
//		System.exit(0);
		if (isSelected=="true") {
			Object status[] = {"REPAIRS_001_Verify_Display All Items", "By default isSeleted status is "+isSelected, "", "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_001_Verify_Display All Items", "By default isSeleted status is "+isSelected, "", "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			driver.findElement(By.xpath("//*[@data-size='large']")).click();
		}
		//Create RMA
		repair.createRMA();
		String expText = "CHECK IN PENDING";
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			Object status[] = {"REPAIRS_002_VerifyCreateRMA", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"REPAIRS_002_VerifyCreateRMA", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Add Items To Repair
		actText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		driver.findElement(By.id("repair-items")).findElement(By.className("button-icon-text")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='side-drawer open']")));
		driver.findElement(By.xpath("//*[@placeholder='Search By Part Number']")).sendKeys(stCode);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1800);
		int tCount = 0;
		int eCount = 0;
		int qcCount1 = 0;
		int qcCount2 = 0;
		if (driver.findElements(By.xpath("//*[@aria-labelledby='tab-0']")).get(1).getText().contains("Items Not Found")) {
			Thread.sleep(1000);
			tCount = 7;
			eCount = 8;
			qcCount1 = 10;
			qcCount2 = 11;
			driver.findElement(By.className("second-msg")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Add New Part']")));
			driver.findElement(By.id("async-select-example")).sendKeys("WAGO");
			Thread.sleep(1200);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			quotes.selectDropDown("WAGO CORPORATION");
			driver.findElement(By.xpath("//*[@placeholder='Part Number']")).sendKeys(stCode);
			driver.findElement(By.xpath("//*[@placeholder='Serial No']")).sendKeys(java.time.LocalTime.now().toString().substring(0, 8).replace(":", ""));
			driver.findElement(By.xpath("//*[text()='Add New Part']")).click();
			Thread.sleep(1500);
		} else {
			tCount = 6;
			eCount = 7;
			qcCount1 = 9;
			qcCount2 = 10;
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
		Thread.sleep(1300);
		expText = "CHECKED-IN";
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			Object status[] = {"REPAIRS_004_VerifyAssignLocation", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_004_VerifyAssignLocation", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Assign Technician
		Thread.sleep(1200);
		driver.findElement(By.xpath("//*[contains(@class,'hides')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("react-select-"+tCount+"-input")).sendKeys(Keys.ARROW_UP);
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
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			Object status[] = {"REPAIRS_005_VerifyAssignTechnician", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_005_VerifyAssignTechnician", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Item Evaluation
		driver.findElement(By.className("hides")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("react-select-"+eCount+"-input")).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		driver.findElement(By.name("estimated_hrs")).sendKeys("23");
		driver.findElement(By.name("price")).sendKeys("198");
		btns = driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElements(By.tagName("button"));
		btns.get(7).click();
		System.out.println("count of btns are "+btns.size());
		for(int i=0;i<btns.size();i++) {
			System.out.println(btns.get(i).getText());
		}
		Thread.sleep(1300);
		expText = "PENDING QUOTE";
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			Object status[] = {"REPAIRS_006_VerifyEvaluateItem", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_006_VerifyEvaluateItem", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Add Repairable Items To Quote
		Thread.sleep(1600);
		driver.findElement(By.xpath("//*[contains(@class,'check_box')]")).findElement(By.tagName("label")).click();
		price.clickButton("Add items to quote");
		Thread.sleep(1000);
		repair.toastContainer("Accept");
		Thread.sleep(1500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2000);
		expText = "ADDED TO QUOTE";
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {

			Object status[] = {"REPAIRS_007_VerifyAddRepairableItemToQuote", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"REPAIRS_007_VerifyAddRepairableItemToQuote", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Go to Quotes for Repair Module
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
		act.click(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'email_invoices')]")));
		driver.findElement(By.xpath("//*[contains(@src,'email_invoices')]")).click();
		Thread.sleep(5000);
		String pastPriceText = driver.findElement(By.xpath("//*[@class='ag-center-cols-viewport']")).getText();
		System.out.println("Past Price Text "+pastPriceText);
		driver.findElements(By.xpath("//*[contains(@src,'cross')]")).get(1).click();
		if (pastPriceText.equals("") || pastPriceText.equals("Data Not Found")) {
			Object status[] = {"REPAIRS_009_Verify_Past_Repair_Prices", pastPriceText, "", "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			Thread.sleep(1500);
			driver.findElement(By.xpath("//*[contains(@src,'cross')]")).click();
		} else {
			price.closeIcon();
			Object status[] = {"REPAIRS_009_Verify_Past_Repair_Prices", pastPriceText, "", "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);

			//Update IIDM Cost
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'email_invoices')]")));
			Thread.sleep(1000);
			act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
			act.click(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Quote Price']")));
			Thread.sleep(1200);
			driver.findElement(By.xpath("//*[@placeholder='Quote Price']")).sendKeys("149.20");
			for(int s=0; s<driver.findElement(By.xpath("//*[@placeholder='List Price']")).getAttribute("value").length(); s++) {
				driver.findElement(By.xpath("//*[@placeholder='List Price']")).sendKeys(Keys.BACK_SPACE);
			}
			driver.findElement(By.xpath("//*[@placeholder='List Price']")).sendKeys("159.20");
			driver.findElement(By.xpath("//*[@placeholder='IIDM Cost']")).sendKeys("189.20");
			String beforeIIDMCost = driver.findElement(By.xpath("//*[@placeholder='IIDM Cost']")).getAttribute("value");
			act.doubleClick(driver.findElements(By.xpath("//*[text()='Edit Quote Item']")).get(1)).build().perform();
			Thread.sleep(2500);
			driver.findElement(By.xpath("//*[contains(@src,'IIDMCostIcon')]")).click();
			Thread.sleep(1500);
			String afterIIDMCost = driver.findElements(By.xpath("//*[@class='d-flex align-center g-16  ']")).get(1).findElement(By.tagName("h4")).getText();
			if (!beforeIIDMCost.equals(afterIIDMCost)) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'email_invoices')]")));
				Thread.sleep(1200);
				act.moveToElement(driver.findElement(By.xpath("//*[contains(@src,'email_invoices')]"))).build().perform();
				act.click(driver.findElement(By.xpath("//*[contains(@src,'email_invoices')]"))).build().perform();
				Thread.sleep(5000);
				List<WebElement> costValues = driver.findElements(By.xpath("//*[@style='left: 303px; width: 123px;']"));
				double val =0;
				for(int i=0; i<costValues.size(); i++) {
					Double val1 = val + Double.parseDouble(costValues.get(i).getText().replace("$", ""));
					val = val1;
				}
				double avgCostValue = val/costValues.size();
				System.out.println("avg cost value is "+avgCostValue);
				Thread.sleep(1200);
				price.closeIcon();
			} else {
				Object status1[] = {"REPAIRS_010_Verify_Update_IIDM_Cost", beforeIIDMCost, afterIIDMCost, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
				quotes.values(status1);
				Thread.sleep(1200);
				price.closeIcon();
			}
		}
		Thread.sleep(1000);
		//		price.clickButton("Submit for internal approval");
		act.moveToElement(driver.findElement(By.xpath("//*[text()='Submit for internal approval']"))).build().perform();
		act.click(driver.findElement(By.xpath("//*[text()='Submit for internal approval']"))).build().perform();
		Thread.sleep(2000);
		repair.toastContainer("Proceed");
		//		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div/div/div[3]/div[2]/button"), "Submit for internal approval"));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text()='Submit for internal approval']"), "Submit for internal approval"));
		Thread.sleep(1700);
		//		price.clickButton("Approve");
		act.moveToElement(driver.findElement(By.xpath("//*[text()='Approve']"))).build().perform();
		act.click(driver.findElement(By.xpath("//*[text()='Approve']"))).build().perform();
		Thread.sleep(1200);
		repair.toastContainer("Approve");
		//		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button"), "Approve"));
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text()='Approve']"), "Approve"));
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).findElement(By.tagName("button")).click();
		driver.findElement(By.xpath("//*[@class='link-icon-text']")).click();
		Thread.sleep(2000);
		repair.wonOrLostButton("Won");
		Thread.sleep(1200);
		repair.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button[1]"), "Won"));
		Thread.sleep(1400);
		driver.findElements(By.xpath("//*[contains(@class,'border-bottom')]")).get(1).findElement(By.tagName("h4")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2300);
		driver.findElement(By.xpath("//*[contains(@class,'hides')]")).click();
		repair.toastContainer("Accept");
		Thread.sleep(2200);
		expText = "PENDING QC";
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {

			Object status[] = {"REPAIRS_011_VerifyAssignToQC", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"REPAIRS_011_VerifyAssignToQC", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//QC CheckList
		driver.findElement(By.xpath("//*[contains(@src,'qc_checklist')]")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[text()='Save']")));
		Thread.sleep(1300);
		driver.findElements(By.xpath("//*[contains(@class,'react-select__indicator')]")).get(0).click();
		act.sendKeys("Quality control report");
//		driver.findElement(By.id("react-select-"+qcCount1+"-input")).sendKeys("Quality control report");
		Thread.sleep(1000);
		quotes.selectDropDown("Quality control report");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1000);
		driver.findElements(By.xpath("//*[contains(@class,'react-select__indicator')]")).get(0).click();
		act.sendKeys(Keys.TAB).build().perform();act.sendKeys(Keys.TAB).build().perform();act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys("Pass").build().perform();
		Thread.sleep(1000);
		quotes.selectDropDown("Pass");
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.xpath("//*[@type='submit']")).click();
		Thread.sleep(1400);
		expText = "COMPLETED";
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {

			Object status[] = {"REPAIRS_012_VerifyQCCheckList", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_012_VerifyQCCheckList", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Repair Report
		driver.findElement(By.xpath("//*[@style='padding-left: 8px;']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
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
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='button-icon-text ']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("customer_po_number")));
		Thread.sleep(1500);
		String stockItem = driver.findElement(By.xpath("/html/body/div/div/div[6]/div/div[1]/div/div[2]/div[3]")).getText();
		driver.findElement(By.name("customer_po_number")).sendKeys("PO1234");
		if (stockItem.contains("does not exist")) {
			driver.findElement(By.xpath("//*[@class='tooltip bottom']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			Thread.sleep(2500);
			driver.findElement(By.xpath("//*[@style='display: flex; gap: 16px;']")).click();
			Thread.sleep(5500);
			driver.findElement(By.xpath("/html/body/div[1]/div/div[6]/div/div[1]/div/div[3]/button")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			Thread.sleep(1600);
		} else {
			Thread.sleep(1300);
			driver.findElement(By.xpath("/html/body/div[1]/div/div[6]/div/div[1]/div/div[3]/button")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			Thread.sleep(1600);
		}
		String orderId = "";
		if(driver.findElements(By.xpath("//*[@class='side-drawer open']")).size()!=0) {
			String serverMsg = driver.findElement(By.className("server-msg")).getText();
			Object status[] = {"REPAIRS_014_VerifyCreateSalesOrder_FromRepair", serverMsg, "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			price.takesScreenShot("create_sales_order.png");
			driver.findElement(By.xpath("//*[@title='close']")).click();
		}else {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
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
		driver.findElements(By.xpath("//*[contains(@class,'border-bottom')]")).get(1).findElement(By.tagName("h4")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2300);
		if (driver.findElement(By.xpath("//*[contains(@src,'partspurchase')]")).isDisplayed()) {
			driver.findElement(By.xpath("//*[contains(@src,'partspurchase')]")).click();
			Thread.sleep(1200);
			price.closeIcon();
			Object status[] = {"REPAIRS_015_Verify_Parts_Purchase_Icon_isDisplayed_Or", "Parts Purchase Icon is Displayed", "", "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_015_Verify_Parts_Purchase_Icon_isDisplayed_Or", "Parts Purchase Icon is not Displayed ", "", "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Create Job from Repair
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div/div[6]")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1600);
		driver.findElement(By.xpath("//*[@class='button-icon-text']")).click();
		Thread.sleep(1500);
		driver.findElement(By.id("async-select-example")).sendKeys(orderId);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		quotes.selectDropDown(orderId);
		Thread.sleep(500);
		driver.findElements(By.xpath("//*[contains(@class,'react-select__indicator')]")).get(2).click();
		act.sendKeys(Keys.TAB);
		act.sendKeys(stCode);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1600);
		driver.findElement(By.name("job_description")).sendKeys("Test Job Description");
		Thread.sleep(800);
		act.doubleClick(driver.findElement(By.name("job_description"))).build().perform();
		Thread.sleep(800);
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(java.time.LocalDate.now().toString().substring(5, 7)).build().perform();
		Thread.sleep(800);
		act.sendKeys(Keys.ENTER).build().perform();
		price.clickButton("Create Job");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1400);
		if(driver.findElements(By.xpath("//*[@class='side-drawer open']")).size()!=0) 
		{
			String serverMsg = driver.findElement(By.className("server-msg")).getText();
			Object status[] = {"REPAIRS_016_VerifyCreateJobFromRepair", serverMsg, "", "JobsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			price.takesScreenShot("create_job.png");
			driver.findElement(By.xpath("//*[@title='close']")).click();
		}else 
		{
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			Thread.sleep(1600);
			String orderStatus = driver.findElement(By.xpath("//*[@title='[object Object]']")).getText();
			if (orderStatus.toLowerCase().equals("OPEN ORDER".toLowerCase())) {
				Object status[] = {"REPAIRS_016_VerifyCreateJobFromRepair", "Job Created with status is "+orderStatus, "", "JobsPage", "Passed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			} else {
				Object status[] = {"REPAIRS_016_VerifyCreateJobFromRepair", "Job Created with status is "+orderStatus, "", "JobsPage", "Failed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			}
		}
	}

	public void quotesModule(String leadTime, String leadValue, String discount) throws Exception
	{

		//Create Quote
		quotes.createQuote();
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		String expText = "OPEN";
		if (actText.toLowerCase().contains(expText.toLowerCase())) {

			Object status[] = {"QUOTES_001_VerifyCreateQuote", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"QUOTES_001_VerifyCreateQuote", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Add(Select) Items To Quote
		actText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
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
		//Check the Lead Time Displayed Or Not
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Add Options']")));
		Thread.sleep(1000);
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.click(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		Thread.sleep(1600);
		driver.findElement(By.id("react-select-7-input")).sendKeys(leadTime);
		quotes.selectDropDown(leadTime);
		driver.findElement(By.name("lead_time_value")).sendKeys(leadValue);
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
		driver.findElement(By.xpath("/html/body/div/div/div[3]/div[2]/button")).click();
		Thread.sleep(2000);
		repair.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div/div/div[3]/div[2]/button"), "Submit for internal approval"));
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
		Thread.sleep(1500);
		price.clickButton("Approve");
		Thread.sleep(1200);
		repair.toastContainer("Approve");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button"), "Approve"));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class='quote-num-and-status']")));
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
		repair.wonOrLostButton("Won");
		Thread.sleep(1200);
		repair.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button[1]"), "Won"));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class='quote-num-and-status']")));
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
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
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
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			Thread.sleep(1600);
			orderId = driver.findElement(By.className("id-num")).getText().replace("#", "");
			String orderStatus = driver.findElement(By.xpath("//*[@title='[object Object]']")).getText();
			if (orderStatus.toLowerCase().equals("OPEN ORDER".toLowerCase())) {
				Object status[] = {"QUOTES_010_VerifyCreateSalesOrder_FromQuote", "Sales Order "+orderId+" Created with Order status is "+orderStatus, "", "SalesOrderPage", "Passed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			} else {
				Object status[] = {"QUOTES_010_VerifyCreateSalesOrder_FromQuote", "Sales Order "+orderId+" Created with Order status is "+orderStatus, "", "SalesOrderPage", "Failed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			}
		}

		//Create Job from Quote
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div/div[6]")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1600);
		driver.findElement(By.xpath("//*[@class='button-icon-text']")).click();
		Thread.sleep(1500);
		driver.findElement(By.id("async-select-example")).sendKeys(orderId);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		quotes.selectDropDown(orderId);
		Thread.sleep(500);
		driver.findElement(By.id("react-select-18-input")).sendKeys(stockCode);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1600);
		driver.findElement(By.name("job_description")).sendKeys("Test Job Description");
		driver.findElement(By.name("job_description")).click();
		act.sendKeys(Keys.TAB).build().perform();act.sendKeys(Keys.ARROW_RIGHT).build().perform();
		act.sendKeys(Keys.ENTER).build().perform();
		price.clickButton("Create Job");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1400);
		if(driver.findElements(By.xpath("//*[@class='side-drawer open']")).size()!=0) 
		{
			String serverMsg = driver.findElement(By.className("server-msg")).getText();
			Object status[] = {"QUOTES_011_VerifyCreateJobFromQuote", serverMsg, "", "JobsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			price.takesScreenShot("create_job.png");
			driver.findElement(By.xpath("//*[@title='close']")).click();
		}else {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			Thread.sleep(1600);
			String orderStatus = driver.findElement(By.xpath("//*[@title='[object Object]']")).getText();
			if (orderStatus.toLowerCase().equals("OPEN ORDER".toLowerCase())) {
				Object status[] = {"QUOTES_011_VerifyCreateJobFromQuote", "Job Created with status is "+orderStatus, "", "JobsPage", "Passed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			} else {
				Object status[] = {"QUOTES_011_VerifyCreateJobFromQuote", "Job Created with status is "+orderStatus, "", "JobsPage", "Failed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			}
		}
	}
}
