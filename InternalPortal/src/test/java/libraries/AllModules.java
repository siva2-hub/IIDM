package libraries;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

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
//			int begIndex = 0;
//			if(driver.getCurrentUrl().contains("staging"))
//			{
//				begIndex = 37;
//			}else {
//				begIndex = 39;
//			}
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
	public void repairsModule() throws Exception
	{
		//Create RMA
		repair.createRMA();
		String expText = "CHECK IN PENDING";
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			Object status[] = {"REPAIRS_001_VerifyCreateRMA", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"REPAIRS_001_VerifyCreateRMA", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Add Items To Repair
		actText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		quotes.selectItemToQuote();
		Thread.sleep(2000);
		expText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		if (!actText.equals(expText)) {
			Object status[] = {"REPAIRS_002_VerifySelectItemToRepair", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_002_VerifySelectItemToRepair", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Assign Location
		List<WebElement> btn=  driver.findElement(By.xpath("//*[@id='repair-items']")).findElements(By.xpath("//*[contains(@class,'action-item icon-bg-hover')]"));
		btn.get(0).click();
		Thread.sleep(1000);
		Thread.sleep(1500);
		Actions act = new Actions(driver);
		WebElement editIcon = driver.findElement(By.xpath("//*[@class='quantity-parent']")).findElement(By.tagName("svg"));
		act.moveToElement(editIcon).perform();
		editIcon.click();
		Thread.sleep(1400);
		driver.findElement(By.name("storage")).sendKeys("New York");
		driver.findElement(By.name("serial_no")).sendKeys(java.time.LocalTime.now().toString().replace("-","").replace(":", "").replace(".", ""));
		driver.findElement(By.xpath("//*[@title='Save Changes']")).click();
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
		driver.findElement(By.id("react-select-6-input")).sendKeys(Keys.ARROW_UP);
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
		driver.findElement(By.id("react-select-7-input")).sendKeys(Keys.ARROW_DOWN);
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
		price.clickButton("Submit for internal approval");
		Thread.sleep(2000);
		repair.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div/div/div[3]/div[2]/button"), "Submit for internal approval"));
		Thread.sleep(1700);
		price.clickButton("Approve");
		Thread.sleep(1200);
		repair.toastContainer("Approve");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button"), "Approve"));
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).findElement(By.tagName("button")).click();
		driver.findElement(By.xpath("//*[@class='Button-Icon-Display']")).click();
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
		Thread.sleep(1000);
		repair.toastContainer("Accept");
		Thread.sleep(2200);
		expText = "PENDING QC";
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {

			Object status[] = {"REPAIRS_009_VerifyAssignToQC", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"REPAIRS_009_VerifyAssignToQC", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//QC CheckList
		driver.findElement(By.xpath("//*[@title='QC Checklist']")).click();
		Thread.sleep(1300);
		driver.findElement(By.id("react-select-9-input")).sendKeys("Quality control report");
		Thread.sleep(1000);
		quotes.selectDropDown("Quality control report");
		driver.findElement(By.id("react-select-10-input")).sendKeys("Pass");
		Thread.sleep(1000);
		quotes.selectDropDown("Pass");
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.xpath("//*[@type='submit']")).click();
		Thread.sleep(1400);
		expText = "COMPLETED";
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {

			Object status[] = {"REPAIRS_010_VerifyQCCheckList", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			Object status[] = {"REPAIRS_010_VerifyQCCheckList", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Go To Quote Module
		act.moveToElement(driver.findElement(By.xpath("//*[contains(@label,'2023')]"))).build().perform();
		driver.findElement(By.xpath("//*[contains(@label,'2023')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(1600);
		String stockCode = driver.findElement(By.xpath("//*[@class=' width-25 flexed']")).findElement(By.tagName("h4")).getText();
		//Create Sales Order from Repair
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
			Object status[] = {"QUOTES_010_VerifyCreateSalesOrder_FromRepair", serverMsg, "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			price.takesScreenShot("create_sales_order.png");
			driver.findElement(By.xpath("//*[@title='close']")).click();
		}else {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			Thread.sleep(1600);
			String orderStatus = driver.findElement(By.xpath("//*[@title='[object Object]']")).getText();
			orderId = driver.findElement(By.className("id-num")).getText().replace("#", "");
			if (orderStatus.toLowerCase().equals("OPEN ORDER".toLowerCase())) {
				Object status[] = {"QUOTES_011_VerifyCreateSalesOrder_FromRepair", "Sales Order "+orderId+" Created with status is "+orderStatus, "", "SalesOrderPage", "Passed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			} else {
				Object status[] = {"QUOTES_011_VerifyCreateSalesOrder_FromRepair", "Sales Order "+orderId+" Created with status is "+orderStatus, "", "SalesOrderPage", "Failed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			}
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
		driver.findElement(By.id("react-select-16-input")).sendKeys("+"+stockCode);
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
		if(driver.findElements(By.xpath("//*[@class='side-drawer open']")).size()!=0) {
			String serverMsg = driver.findElement(By.className("server-msg")).getText();
			Object status[] = {"QUOTES_012_VerifyCreateJobFromRepair", serverMsg, "", "JobsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			price.takesScreenShot("create_job.png");
			driver.findElement(By.xpath("//*[@title='close']")).click();
		}else {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			Thread.sleep(1600);
			String orderStatus = driver.findElement(By.xpath("//*[@title='[object Object]']")).getText();
			if (orderStatus.toLowerCase().equals("OPEN ORDER".toLowerCase())) {
				Object status[] = {"QUOTES_012_VerifyCreateJobFromRepair", "Job Created with status is "+orderStatus, "", "JobsPage", "Passed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			} else {
				Object status[] = {"QUOTES_012_VerifyCreateJobFromRepair", "Job Created with status is "+orderStatus, "", "JobsPage", "Failed", java.time.LocalDate.now().toString()};
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
		driver.findElement(By.xpath("//*[@title='Edit Item']")).click();
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
		Actions act = new Actions(driver);
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
		//Approve The Quote
		Thread.sleep(1500);
		price.clickButton("Approve");
		Thread.sleep(1200);
		repair.toastContainer("Approve");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button"), "Approve"));
		Thread.sleep(1800);
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		expText = "APPROVED";
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			Object status[] = {"QUOTES_006_VerifyApproveButton", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"QUOTES_006_VerifyApproveButton", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Send To Customer
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).findElement(By.tagName("button")).click();
		driver.findElement(By.xpath("//*[@role='menuitem']")).click();
		Thread.sleep(2500);
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		expText = "DELIVERED TO CUSTOMER";
		if (actText.toLowerCase().contains(expText.toLowerCase())) {

			Object status[] = {"QUOTES_007_VerifySubmitForCustomerApproval", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"QUOTES_007_VerifySubmitForCustomerApproval", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		//Quote Won
		repair.wonOrLostButton("Won");
		Thread.sleep(1200);
		repair.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button[1]"), "Won"));
		Thread.sleep(1400);
		expText = "WON";
		actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {

			Object status[] = {"QUOTES_008_VerifyQuoteWon", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			Object status[] = {"QUOTES_008_VerifyQuoteWon", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
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
			Object status[] = {"QUOTES_009_VerifyCreateSalesOrder", serverMsg, "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			price.takesScreenShot("create_sales_order.png");
			driver.findElement(By.xpath("//*[@title='close']")).click();
		}else {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			Thread.sleep(1600);
			orderId = driver.findElement(By.className("id-num")).getText().replace("#", "");
			String orderStatus = driver.findElement(By.xpath("//*[@title='[object Object]']")).getText();
			if (orderStatus.toLowerCase().equals("OPEN ORDER".toLowerCase())) {
				Object status[] = {"QUOTES_009_VerifyCreateSalesOrder_FromQuote", "Sales Order "+orderId+" Created with Order status is "+orderStatus, "", "SalesOrderPage", "Passed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			} else {
				Object status[] = {"QUOTES_009_VerifyCreateSalesOrder_FromQuote", "Sales Order "+orderId+" Created with Order status is "+orderStatus, "", "SalesOrderPage", "Failed", java.time.LocalDate.now().toString()};
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
			Object status[] = {"QUOTES_010_VerifyCreateJobFromQuote", serverMsg, "", "JobsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
			price.takesScreenShot("create_job.png");
			driver.findElement(By.xpath("//*[@title='close']")).click();
		}else {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			Thread.sleep(1600);
			String orderStatus = driver.findElement(By.xpath("//*[@title='[object Object]']")).getText();
			if (orderStatus.toLowerCase().equals("OPEN ORDER".toLowerCase())) {
				Object status[] = {"QUOTES_010_VerifyCreateJobFromQuote", "Job Created with status is "+orderStatus, "", "JobsPage", "Passed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			} else {
				Object status[] = {"QUOTES_010_VerifyCreateJobFromRepair", "Job Created with status is "+orderStatus, "", "JobsPage", "Failed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			}
		}
	}
}
