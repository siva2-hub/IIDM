package libraries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
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

public class QuotePages extends App
{
	RepairPages rp;
	PricingPages price ;
	public void createQuote() throws Exception 
	{	rp = new RepairPages();	
	driver.findElement(By.xpath("//*[text() = 'Quotes']")).click();
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	try {
		driver.findElement(By.xpath("//*[contains(@class,'Cross-svg close-icon-container')]")).isDisplayed();
		driver.findElement(By.xpath("//*[contains(@class,'Cross-svg close-icon-container')]")).click();
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
	App.spinner();
	try {
		driver.findElement(By.xpath("//*[text() = 'OPEN12']")).isDisplayed();
		driver.findElement(By.xpath("//*[text() = 'OPEN12']")).click();
	} catch (Exception e) 
	{
		String custName = "Motion Industries - Grand Prairie";
		driver.findElement(By.xpath("//*[@class='button-icon-text ']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='async-select-example']")));
		driver.findElement(By.xpath("//*[@id='async-select-example']")).sendKeys(custName);
		App.spinner();
		Thread.sleep(1200);
		driver.findElement(By.xpath("//*[contains(@class, 'css-4mp3pp-menu')]")).click();
		App.spinner();Thread.sleep(1000);
		driver.findElement(By.name("project_name")).sendKeys("Test");
		driver.findElement(By.xpath("//*[contains(@id,'react-select')]")).sendKeys("Parts Quote");
		Thread.sleep(2500);
		this.selectDropDown("Parts Quote");
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("repair-items")));
	}
	}
	public void selectItemToQuote() throws Exception {

		driver.findElement(By.id("repair-items")).findElement(By.className("button-icon-text")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='side-drawer open']")));
		driver.findElement(By.xpath("//*[@placeholder='Search By Part Number']")).sendKeys("0165009LS");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = '0165009LS']")));
		Thread.sleep(1800);
		//		driver.findElements(By.xpath("//*[contains(@class,'item-selection-grid')]")).get(0).findElement(By.tagName("label")).click();
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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("repair-items")));
		Thread.sleep(2600);
	}
	public boolean verifySelectItemToQuote() throws Exception {
		this.createQuote();
		boolean res = false;
		String actText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		this.selectItemToQuote();
		Thread.sleep(2000);
		String expText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		if (actText!=expText) {
			res = true;
			Object status[] = {"QUOTES_002_VerifySelectItemToQuote", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {"QUOTES_002_VerifySelectItemToQuote", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public boolean verifyCreateQuote() throws Exception {
		this.createQuote();
		boolean res = false;
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		String expText = "OPEN";
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"QUOTES_001_VerifyCreateQuote", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {"QUOTES_001_VerifyCreateQuote", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public void quoteApprove() throws Exception 
	{
		this.submitForInternalApproval();
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[text() = 'Approve']")).click();
		Thread.sleep(1200);
		rp.toastContainer("Approve");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Approve']"), "Approve"));
		Thread.sleep(1500);
	}
	public boolean verifyApproveButton() throws Exception{
		boolean res = false;
		this.quoteApprove();
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		String expText = "APPROVED";
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"QUOTES_004_VerifyApproveButton", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {"QUOTES_004_VerifyApproveButton", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public boolean verifySubmitForCustomerApproval() throws Exception {
		this.submitForCustomerApproval();
		boolean res = false;
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		String expText = "DELIVERED TO CUSTOMER";
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"QUOTES_005_VerifySubmitForCustomerApproval", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {"QUOTES_005_VerifySubmitForCustomerApproval", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public void submitForCustomerApproval() throws Exception {
		this.quoteApprove();
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).findElement(By.tagName("button")).click();
		driver.findElement(By.xpath("//*[@role='menuitem']")).click();
		App.spinner();
		Thread.sleep(1000);
	}
	public boolean verifyQuoteWon(int count) throws Exception {
		this.submitForCustomerApproval();
		Thread.sleep(1000);
		String won = "";
		String tcName = "";
		String expText = "";
		if (count==1) {
			won = "Won";
			tcName = "QUOTES_006_VerifyQuoteWon";
			expText = "WON";
		} else if(count==2) {
			won = "Lost";
			tcName = "QUOTES_012_Verify_Quote_Lost";
			expText = "LOST";
		}
		rp.wonOrLostButton(won);
		Thread.sleep(1200);
		rp.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Won']"), "Won"));
		Thread.sleep(1400);
		boolean res = false;
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {tcName, actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {tcName, actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		//Quote Clone from Quote for Parts
		this.verifyQuoteClone_QuotesForParts();
		return res;
	}

	public boolean verifySubmitForInternalApproval() throws Exception {
		this.submitForInternalApproval();
		Thread.sleep(2500);
		boolean res = false;
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		String expText = "PENDING APPROVAL";
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"QUOTES_003_VerifySubmitForInternalApproval", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {"QUOTES_003_VerifySubmitForInternalApproval", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public void submitForInternalApproval() throws Exception {
		this.createQuote();
		this.selectItemToQuote();
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
		Thread.sleep(2000);
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
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.click(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Quote Price']")));
		Thread.sleep(1200);
		//Update source
		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(1).click();
		Thread.sleep(1200);
		act.sendKeys(Keys.ENTER).build().perform();
		//Update leadTime
		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(2).click();
		Thread.sleep(1200);
		act.sendKeys("Week(s)").build().perform();
		act.sendKeys(Keys.ENTER).build().perform();
		driver.findElement(By.xpath("//*[@name='lead_time_value']")).sendKeys("3");
		act.doubleClick(driver.findElements(By.xpath("//*[text()='Edit Quote Item']")).get(1)).build().perform();
		Thread.sleep(2500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'delete-icon')]")));
		driver.findElement(By.xpath("//*[text() = 'Submit for Internal Approval']")).click();
		Thread.sleep(2000);
		rp.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text() = 'Submit for Internal Approval']"), "Submit for Internal Approval"));
	}
	public boolean verifyReviseQuote() throws Exception {
		this.quoteApprove();
		driver.findElement(By.xpath("/html/body/div/div/div[3]/div[2]/div[1]/button")).click();
		Thread.sleep(1000);
		rp.toastContainer("Proceed");
		Thread.sleep(2700);
		boolean res = false;
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		String expText = "V2\n"
				+ "OPEN";
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"QUOTES_008_VerifyReviseQuote", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {"QUOTES_008_VerifyReviseQuote", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;

	}
	public boolean verifyTopSearchInQuoteListView(String searchBy,int count) throws Exception {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[text()='Quotes']")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Quote ID / Company Name / Sales Person Name / Email']")));
		try {
			driver.findElement(By.xpath("//*[text()='Clear']")).click();
			Thread.sleep(2000);
		} catch (Exception e) {
		}
		//
		try {
			driver.findElement(By.xpath("//*[@class='Cross-svg close-icon-container']")).click();
		} catch (Exception e) {
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		String tcName = "";String path = "";boolean res = false;String actText = "";
		if (count == 1) {
			tcName = "QUOTES_014_VerifySearchByQuoteId";
			path = "/html/body/div[1]/div/div[4]/div[2]/div/div/div/div/div/div[2]/div[2]/div[3]/div[2]/div/div/div[1]/div[1]";
		} else if(count == 2) {
			tcName = "QUOTES_015_VerifySearchByCompanyName";
			path = "/html/body/div[1]/div/div[4]/div[2]/div/div/div/div/div/div[2]/div[2]/div[3]/div[2]/div/div/div[1]/div[2]/span/div[2]";
		}else if(count == 3) {
			tcName = "QUOTES_016_VerifySearchBySalesPersonName";
			path = "/html/body/div[1]/div/div[4]/div[2]/div/div/div/div/div/div[2]/div[2]/div[3]/div[2]/div/div/div[1]/div[7]/span/div[2]";
		}else if(count == 4) {
			tcName = "QUOTES_017_VerifySearchByEmail";

		}
		driver.findElement(By.xpath("//*[@placeholder='Quote ID / Company Name / Sales Person Name / Email']")).sendKeys(searchBy);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-center-cols-viewport']")));
		Thread.sleep(5000);
		String gridText = driver.findElement(By.xpath("//*[@class='ag-center-cols-viewport']")).getText();
		System.out.println("data of grid is "+gridText);
		Thread.sleep(2000);
		if (gridText=="") {
			res = false;
			Object status[] = {tcName, "No Quotes for Parts Found! for "+tcName, searchBy, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
			//			App.logout();
		} else {
			if (count==4) {
				driver.findElement(By.xpath("//*[@class='ag-react-container']")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("repair-items")));
				path = "/html/body/div/div/div[6]/div[1]/div/div[1]/div[1]/div[2]/div[5]/div/p";
				actText = driver.findElement(By.xpath(path)).getText();
				String act2 = actText;
				String act1 = searchBy;
				actText = act1;
				searchBy = act2.replace(" ", ".");
			}else {
				actText = driver.findElement(By.xpath(path)).getText();
			}
			if (actText.toLowerCase().contains(searchBy.toLowerCase())) {
				res = true;
				Object status[] = {tcName, actText, searchBy, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
				this.values(status);
			} else {
				res = false;
				Object status[] = {tcName, actText, searchBy, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
				this.values(status);
				//				App.logout();
			}
		}
		if (count==4) {
			//			driver.findElement(By.xpath("//*[@class='down-arrow']")).click();
			//			List<WebElement> btns = driver.findElements(By.xpath("//*[@role='menuitem']"));
			//			btns.get(1).click();
		} else {
			try {
				driver.findElement(By.className("Cross-svg")).click();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return res;
	}
	public boolean verifyResetandClearButtonInFiltersPage(String compName, int count) throws Exception {
		boolean res = false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		try {
			driver.findElement(By.xpath("//*[@style = 'padding: 10px 10px 10px 0px; display: flex; align-items: center; cursor: pointer;']")).isDisplayed();
			driver.findElement(By.xpath("//*[@style = 'padding: 10px 10px 10px 0px; display: flex; align-items: center; cursor: pointer;']")).click();
			Thread.sleep(1600);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			driver.findElement(By.xpath("//*[text()='Clear']")).isDisplayed();
			driver.findElement(By.xpath("//*[text()='Clear']")).click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		App.spinner();
		driver.findElement(By.className("filter-text")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='async-select-example']")));
		Actions act = new Actions(driver);
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(compName).build().perform();	
		Thread.sleep(3000);
		this.selectDropDown(compName);
		driver.findElement(By.xpath("//*[text()='Close & Reset']")).click();
		Thread.sleep(2000);
		driver.findElement(By.className("filter-text")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='async-select-example']")));
		Thread.sleep(1000);
		String compText1 = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div[2]/div[2]/div[4]/div[1]/div/div[2]/div/div[1]/div/div/div[1]/div[1]")).getText();
		driver.findElement(By.xpath("//*[@title='close']")).click();
		if (compText1.equalsIgnoreCase("Search By Company Name")) {
			res = true;
			Object status[] = {"QUOTES_020_VerifyResetandClearButtonInFiltersPage", compText1, "", "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {"QUOTES_020_VerifyResetandClearButtonInFiltersPage", compText1, "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public boolean verifyClosenadReOpenButtons(int count) throws Exception {
		boolean res = false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		try {
			driver.findElement(By.xpath("//*[contains(@class,'Cross-svg close-icon-container')]")).isDisplayed();
			driver.findElement(By.xpath("//*[contains(@class,'Cross-svg close-icon-container')]")).click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String xpath = ""; String tcName = "";String expText = "";
		if (count==1) {
			xpath = "//*[text() = 'Re Open']";
			tcName = "QUOTES_021_VerifyReOpen_ButtonInQuoteDetailePage";
			expText = "open";
		} else if(count==2) {
			xpath = "//*[text() = 'Close']";
			tcName = "QUOTES_022_VerifyClose_ButtonInQuoteDetailePage";
			expText = "closed";
			driver.findElement(By.xpath("//*[text()='Submit for Internal Approval']")).click();
			Thread.sleep(1500);
			rp.toastContainer("Proceed");
			//
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Approve']")));
			Thread.sleep(1500);
			driver.findElement(By.xpath("//*[text()='Approve']")).click();
			Thread.sleep(1500);
			driver.findElement(By.xpath("//*[text()='Decline']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[text() = 'Proceed']")).click();	
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'Re Open']")));
		Thread.sleep(500);
		driver.findElement(By.xpath(xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[text() = 'Proceed']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2000);
		String compText1 = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (compText1.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {tcName, compText1, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {tcName, compText1, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public boolean verifyFiltersStateMaintanance(String compName, String salesPerson, String status, String quotedBy, int count) throws Exception {
		boolean res = false;
		try {
			driver.findElement(By.xpath("//*[@style = 'padding: 10px 10px 10px 0px; display: flex; align-items: center; cursor: pointer;']")).isDisplayed();
			driver.findElement(By.xpath("//*[@style = 'padding: 10px 10px 10px 0px; display: flex; align-items: center; cursor: pointer;']")).click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-center-cols-container']")));
		driver.findElement(By.xpath("//*[text()='Quotes']")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		try {
			driver.findElement(By.xpath("//*[@class='Cross-svg close-icon-container']")).click();
			Thread.sleep(1600);
		} catch (Exception e) {}
		try {
			driver.findElement(By.xpath("//*[text()='Clear']")).click();
		} catch (Exception e) {}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		driver.findElement(By.className("filter-text")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='async-select-example']")));
		Actions act = new Actions(driver);
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(compName).build().perform();	
		Thread.sleep(3000);
		this.selectDropDown(compName);
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(salesPerson).build().perform();
		this.selectDropDown(salesPerson);
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(status).build().perform();
		this.selectDropDown(status);
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(quotedBy).build().perform();
		this.selectDropDown(quotedBy);
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys("0165009LS").build().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1500);
		//		this.selectDropDown("0165009LS");
		price = new PricingPages();
		price.clickButton("Apply");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Repairs']")));
		driver.findElement(By.xpath("//*[text()='Repairs']")).click();
		driver.findElement(By.xpath("//*[text()='Quotes']")).click();
		try {
			driver.findElement(By.xpath("//*[contains(@class,'Cross-svg close-icon-container')]")).isDisplayed();
			driver.findElement(By.xpath("//*[contains(@class,'Cross-svg close-icon-container')]")).click();
		} catch (Exception e) {
			// TODO: handle exception
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-center-cols-viewport']")));
		Thread.sleep(1600);
		boolean filterCheck = false;
		try {
			driver.findElement(By.xpath("//*[@title='Filters Applied']")).isDisplayed();
			filterCheck = true;
		} catch (Exception e) {
			filterCheck = false;
		}
		if (filterCheck) {
			res = true;
			Object status1[] = {"QUOTES_019_VerifyFiltersStateMaintanance", "Filter State Maintanance working.", "", "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status1);
		} else {
			res = true;
			Object status1[] = {"QUOTES_019_VerifyFiltersStateMaintanance", "Filter State Maintanance not working.!", "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status1);
		}
		return res;
	}
	public boolean verifyDeleteIcon(int count) throws Exception
	{
		Thread.sleep(2000);
		boolean res = false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		try {
			driver.findElement(By.xpath("//*[contains(@class,'Cross-svg close-icon-container')]")).isDisplayed();
			driver.findElement(By.xpath("//*[contains(@class,'Cross-svg close-icon-container')]")).click();
		} catch (Exception e) {
			// TODO: handle exception
		}
		//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		//		this.createQuote();
		//		Thread.sleep(1000);
		//		this.selectItemToQuote();
		String itemsCount = "";
		String expText = "";
		String tcName ="";
		if (count==1) {
			tcName = "QUOTES_023_VerifyDeleteIconInQuoteItems";
			itemsCount = driver.findElement(By.xpath("//*[@id='repair-items']")).findElement(By.tagName("h4")).getText();
			driver.findElement(By.xpath("//*[contains(@class, 'quote-item-del-icon')]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[text()='Yes']")).click();
			Thread.sleep(2700);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
			Thread.sleep(2500);
			expText = "Quote Items (0)";
		} else if(count==2){
			tcName = "QUOTES_024_VerifyEditIconInQuoteItems";
			driver.findElements(By.xpath("//*[contains(@src, 'themecolorEdit')]")).get(1).click();
			Thread.sleep(1000);
			WebElement qty = driver.findElement(By.xpath("//input[@name='quantity']"));
			System.out.println("lenght of qty is"+qty.getAttribute("value").length());
			for(int i=0; i<qty.getAttribute("value").length();i++)
			{
				qty.sendKeys(Keys.BACK_SPACE);
			}
			expText = "13";
			qty.sendKeys("13");
			Thread.sleep(1500);

			driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
			Thread.sleep(2000);	
			itemsCount = driver.findElements(By.xpath("//*[@class='d-flex align-center g-16  ']")).get(2).getText();
			System.out.println("items count "+itemsCount);
		}
		if (!itemsCount.equalsIgnoreCase(expText)||itemsCount.equalsIgnoreCase("13")) {
			res = true;
			Object status1[] = {tcName, itemsCount, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status1);
		} else {
			res = false;
			Object status1[] = {tcName,itemsCount, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status1);
		}
		return res;
	}
	public boolean verifyFiltersInQuoteListView(String compName, String salesPerson, String status, String quotedBy, int count) throws Exception 
	{
		boolean res = false;
		Actions act = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='filter-text']")));
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div/div[5]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		try {
			driver.findElement(By.xpath("//*[@style = 'padding: 10px 10px 10px 0px; display: flex; align-items: center; cursor: pointer;']")).click();
			Thread.sleep(1500);
		} catch (Exception e) {}
		try {
			driver.findElement(By.xpath("//*[text()='Clear']")).click();
		} catch (Exception e) {}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		driver.findElement(By.className("filter-text")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='async-select-example']")));
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(compName).build().perform();	
		Thread.sleep(3000);
		this.selectDropDown(compName);
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(salesPerson).build().perform();
		this.selectDropDown(salesPerson);
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(status).build().perform();
		this.selectDropDown(status);
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(quotedBy).build().perform();
		this.selectDropDown(quotedBy);
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys("0165009LS").build().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1500);
		//		this.selectDropDown("0165009LS");
		price = new PricingPages();
		price.clickButton("Apply");
		Thread.sleep(5000);
		String gridText = driver.findElement(By.xpath("//*[@class='ag-center-cols-viewport']")).getText();
		if (gridText=="") {
			res = false;
			Object status1[] = {"QUOTES_018_VerifyFiltersInQuotesListView", "No Quotes for Parts Found!", "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status1);
			Thread.sleep(1500);
			driver.findElement(By.xpath("//*[contains(@class,'clear-text')]")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
			//			App.logout();
		} else {
			String cName = driver.findElement(By.xpath("//*[contains(text(),'"+compName+"')]")).getText();
			System.out.println("After applying filter ");
			System.out.println("comp name "+cName);
			String sPerson = driver.findElement(By.xpath("//*[contains(text(),'"+salesPerson+"')]")).getText();
			System.out.println("Sales Person "+salesPerson);
			String sta = driver.findElement(By.xpath("//*[contains(text(),'"+status+"')]")).getText();
			System.out.println("status "+status);
			String qBy = driver.findElement(By.xpath("//*[contains(text(),'"+quotedBy+"')]")).getText();
			System.out.println("Quoted By"+quotedBy);
			if (cName.equalsIgnoreCase(compName)&& sPerson.equalsIgnoreCase(salesPerson)&& sta.equalsIgnoreCase(status)&& qBy.equalsIgnoreCase(quotedBy)) {
				res = true;
				Object status1[] = {"QUOTES_018_VerifyFiltersInQuotesListView", "Filter functionality working", "", "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
				this.values(status1);
			} else {
				res = false;
				Object status1[] = {"QUOTES_018_VerifyFiltersInQuotesListView", "Filter functionality not working.!", "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
				this.values(status1);
				Thread.sleep(1500);
				driver.findElement(By.xpath("//*[contains(@src,'filters-cancel')]")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
				//				App.logout();
			}
		}
		Thread.sleep(1700);


		return res;
	}
	public boolean verifyPrintDownLoad() throws Exception {
		//		this.createQuote();
		//		this.selectItemToQuote();
		boolean res = false;
		driver.findElement(By.xpath("//*[contains(@src,'print')]")).click();
		Thread.sleep(2300);
		Set<String> ids = driver.getWindowHandles();
		Object[] id1 = ids.toArray();
		driver.switchTo().window(id1[1].toString()).close();
		Thread.sleep(1000);
		driver.switchTo().window(id1[0].toString());
		if (ids.size()==2) {
			res = true;
			Object status[] = {"QUOTES_013_VerifyPrintFunctionality", "Print working in Quotes Detailed Page.", "", "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {"QUOTES_013_VerifyPrintFunctionality", "Print Not working..!,in Quotes Detailed Page", "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public boolean verifyAddOptionInQuoteDetailedView() throws Exception {
		boolean res = false;
		driver.findElement(By.xpath("//*[text()='Quotes']")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		try {
			driver.findElement(By.xpath("//*[contains(@class,'Cross-svg close-icon-container')]")).isDisplayed();
			driver.findElement(By.xpath("//*[contains(@class,'Cross-svg close-icon-container')]")).click();
		} catch (Exception e) {
			// TODO: handle exception
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		this.createQuote();
		this.selectItemToQuote();
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.click(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Quote Price']")));
		Thread.sleep(1200);
		//Update source
		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(1).click();
		Thread.sleep(1200);
		act.sendKeys(Keys.ENTER).build().perform();
		//Update leadTime
		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(2).click();
		Thread.sleep(1200);
		act.sendKeys(Keys.ENTER).build().perform();
		act.doubleClick(driver.findElements(By.xpath("//*[text()='Edit Quote Item']")).get(1)).build().perform();
		Thread.sleep(2500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'delete-icon')]")));
		driver.findElement(By.xpath("//*[text()='Add Options']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='I want to add products from scratch']")));
		driver.findElement(By.xpath("//*[text()='I want to add products from scratch']")).click();
		driver.findElement(By.xpath("//*[@placeholder='Search By Part Number']")).sendKeys("0165");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@style='animation-delay: 0ms;']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style='animation-delay: 0ms;']")));
		driver.findElement(By.xpath("//*[@placeholder='Search By Part Number']")).click();
		act.sendKeys(Keys.TAB).build().perform();act.sendKeys(Keys.TAB).build().perform();act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(Keys.SPACE).build().perform();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(text(), 'Add Selected')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("repair-items")));
		Thread.sleep(1600);
		String optionText = driver.findElement(By.xpath("//*[@id='repair-items']")).findElement(By.xpath("//*[@aria-setsize='2'][@aria-posinset='2']")).getText();
		if (optionText.contains("Option 2")) {
			res = true;
			Object status[] = {"QUOTES_025_Verify_AddOptionInQuoteDetailedView", optionText, "Option 2", "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {"QUOTES_025_Verify_AddOptionInQuoteDetailedView", optionText, "Option 2", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		//Decline Quote
		this.verifyDeclineInQuoteDetailedView();

		//Re Open Quote
		this.verifyClosenadReOpenButtons( 1);

		//Close Quote
		this.verifyClosenadReOpenButtons( 2);

		return res;
	}
	public String[] quoteClone_QuotesForParts() throws Exception 
	{
		//		this.submitForInternalApproval();
		//		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		//		Thread.sleep(1300);
		List<WebElement> repIds = driver.findElements(By.id("repair-info-id"));
		String beforeCloneRepText = repIds.get(0).getText();
		String beforeCloneCust = repIds.get(1).getText();
		String beforeCloneItems = driver.findElement(By.id("repair-items")).getText();
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[contains(@src,'clone')]")).click();
		Thread.sleep(1300);
		rp.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style='animation-delay: 0ms;']")));
		Thread.sleep(2000);
		String[] is = {beforeCloneRepText, beforeCloneItems, beforeCloneCust};
		return is;
	}
	public boolean verifyQuoteClone_QuotesForParts() throws Exception
	{
		String is[] = this.quoteClone_QuotesForParts();
		String beforeCloneRepText = is[0];
		String beforeCloneItems = is[1];
		String beforeCloneCust = is[2];
		List<WebElement> repIds = driver.findElements(By.id("repair-info-id"));
		String afterCloneRepText = repIds.get(0).getText();
		String afterCloneCust = repIds.get(1).getText();
		String afterCloneItems = driver.findElement(By.id("repair-items")).getText();
		String quoteType = driver.findElement(By.id("repair-info-id")).findElement(By.xpath("//*[contains(@class,'pi-label')]")).findElement(By.xpath("//*[@class='description']")).getText();
		boolean res = false;
		if (quoteType.equalsIgnoreCase("Parts Quote")) {
			String actText = driver.findElement(By.className("quote-num-and-status")).getText();
			String ExpText = "OPEN";
			if (actText.toLowerCase().contains(ExpText.toLowerCase())) {
				res = true;
				Object status[] = {"QUOTES_026_VerifyQuoteClone_QuotesForParts", actText, ExpText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
				this.values(status);
			} else {
				res = false;
				Object status[] = {"QUOTES_026_VerifyQuoteClone_QuotesForParts", actText, ExpText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
				this.values(status);
			}
		} else {
			System.out.println("Quote Cloned Failed.!");
			System.out.println("before clone rep info"+beforeCloneRepText);
			System.out.println("after clone  rep info "+afterCloneRepText);
			System.out.println("before clone item info "+beforeCloneItems);
			System.out.println("after clone  item info "+afterCloneItems);
			System.out.println("before clone cust info "+beforeCloneCust);
			System.out.println("after clone  cust info "+afterCloneCust);
			res = false;
			Object status[] = {"QUOTES_026_VerifyQuoteClone_QuotesForParts", "", "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public boolean quoteClone_QuoteForRepairs() throws Exception 
	{
		rp = new RepairPages();
		//		rp.createQuoteFromRepair();
		try {
			driver.findElement(By.xpath("//*[text() = 'Repairs']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'All Repairs Requests']")));
			driver.findElement(By.xpath("//*[text() = 'All Repairs Requests']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src, 'vendor_logo')]")));
			driver.findElement(By.xpath("//*[text() = 'Check In Pending1']")).isDisplayed();
			driver.findElement(By.xpath("//*[text() = 'Check In Pending']")).click();
		} catch (Exception e) {
			rp.createRMA();
			//Add Items To Repair
			driver.findElement(By.id("repair-items")).findElement(By.className("button-icon-text")).click();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='side-drawer open']")));
			driver.findElement(By.xpath("//*[@placeholder='Search By Part Number']")).sendKeys("0165");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@style='animation-delay: 0ms;']")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style='animation-delay: 0ms;']")));
			Thread.sleep(1800);
			//			int tCount = 0;
			//			int eCount = 0;
			//			//		int qcCount1 = 0;
			//			//		int qcCount2 = 0;
			if (driver.findElements(By.xpath("//*[@aria-labelledby='tab-0']")).get(1).getText().contains("Items Not Found")) {
				Thread.sleep(1000);
//				tCount = 7;
//				eCount = 8;
				//			qcCount1 = 10;
				//			qcCount2 = 11;
				driver.findElement(By.className("second-msg")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Add New Part']")));
				driver.findElement(By.id("async-select-example")).sendKeys("WAGO");
				Thread.sleep(1200);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style='animation-delay: 0ms;']")));
				this.selectDropDown("WAGO CORPORATION");
				driver.findElement(By.xpath("//*[@placeholder='Part Number']")).sendKeys("0165");
				driver.findElement(By.xpath("//*[@placeholder='Serial No']")).sendKeys(java.time.LocalTime.now().toString().substring(0, 8).replace(":", ""));
				driver.findElement(By.xpath("//*[text()='Add New Part']")).click();
				Thread.sleep(1500);
			} else {
//				tCount = 6;
//				eCount = 7;
				//			qcCount1 = 9;
				//			qcCount2 = 10;
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
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'Assign Location']")));
		Thread.sleep(2600);
		//Assign Location
		driver.findElement(By.xpath("//*[text() = 'Assign Location']")).click();
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
		//Assign Technician
		Thread.sleep(1200);
		driver.findElement(By.xpath("//*[contains(@class,'hides')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(@class, 'react-select__indicator')]")).click();
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
		//Item Evaluation
		driver.findElement(By.className("hides")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@class, 'auto__dropdown-indicator')]")).click();
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
		//Add Repairable Items To Quote
		Thread.sleep(1600);
		driver.findElement(By.xpath("//*[contains(@class,'check_box')]")).findElement(By.tagName("label")).click();
		driver.findElement(By.xpath("//*[text()='Add items to quote']")).click();
		Thread.sleep(1000);
		rp.toastContainer("Accept");
		Thread.sleep(1500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2000);
		//Go to Quotes for Repair Module
		Thread.sleep(2000);
		act.moveToElement(driver.findElement(By.xpath("//*[contains(@label,'2023')]"))).build().perform();
		driver.findElement(By.xpath("//*[contains(@label,'2023')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Submit for Internal Approval']")));
		price = new PricingPages();
		Thread.sleep(2500);
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.click(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Quote Price']")));
		Thread.sleep(1200);
		//Update source
		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(1).click();
		Thread.sleep(1200);
		act.sendKeys(Keys.ENTER).build().perform();
		//Update leadTime
		driver.findElements(By.xpath("//*[contains(@class,'dropdown-indicator')]")).get(2).click();
		Thread.sleep(1200);
		act.sendKeys(Keys.ENTER).build().perform();
		act.doubleClick(driver.findElements(By.xpath("//*[text()='Edit Quote Item']")).get(1)).build().perform();
		Thread.sleep(2500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'delete-icon')]")));

		price.clickButton("Submit for Internal Approval");
		Thread.sleep(1500);
		rp.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text()='Submit for Internal Approval']"), "Submit for Internal Approval"));
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[contains(@src,'clone')]")).click();
		Thread.sleep(1300);
		rp.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@style = 'animation-delay: 0ms;']")));
		Thread.sleep(1500);
		String quoteType = driver.findElement(By.id("repair-info-id")).findElement(By.xpath("//*[contains(@class,'pi-label')]")).findElement(By.xpath("//*[@class='description']")).getText();
		System.out.println("quote type is "+quoteType);
		List<WebElement> btms = driver.findElements(By.xpath("//*[contains(@class,'border-bottom')]"));
		String repRelText = btms.get(1).getText();
		String actText = driver.findElement(By.className("quote-num-and-status")).getText();
		System.out.println("repair related link id "+repRelText);
		System.out.println("count of bottoms are "+btms.size());boolean res = false;
		if (quoteType.toLowerCase().contains("parts quote")) {
			if(actText.toLowerCase().contains("OPEN".toLowerCase())) {
				res = true;
				Object status[] = {"QUOTES_027_VerifyQuoteClone_QuotesForRepairs", "Quote Type is "+quoteType, "Quote status is "+actText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
				this.values(status);
			}else {
				res = false;
				Object status[] = {"QUOTES_027_VerifyQuoteClone_QuotesForRepairs", "Quote Type is "+quoteType, "Quote status is "+actText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
				this.values(status);
			}
		} else {
			res = false;
			Object status[] = {"QUOTES_027_VerifyQuoteClone_QuotesForRepairs", "Quote Type is "+quoteType, "Quote status is "+actText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public void declineTheQuote() throws Exception 
	{
		this.submitForInternalApproval();
		PricingPages price = new PricingPages();
		Thread.sleep(2000);
		price.clickButton("Approve");
		RepairPages repair = new RepairPages();
		repair.toastContainer("Decline");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button"), "Approve"));
		Thread.sleep(1500);
	}
	public boolean verifyDeclineInQuoteDetailedView() throws Exception
	{
		//		this.declineTheQuote();
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
		Thread.sleep(2000);
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
		driver.findElement(By.xpath("//*[contains(@class,'check_box')]")).findElement(By.tagName("label")).click();
		//		driver.findElement(By.xpath("//*[@class='quote-option-del-icon edit-icon']")).click();
		driver.findElement(By.xpath("//*[@class='cards-btns-group']")).findElement(By.xpath("//*[contains(@src, 'delete-icon')]")).click();
		Thread.sleep(1000);
		driver.findElements(By.xpath("//*[text()='Yes']")).get(1).click();
		Thread.sleep(1500);
		//
		driver.findElement(By.xpath("//*[text()='Submit for Internal Approval']")).click();
		Thread.sleep(1500);
		rp.toastContainer("Proceed");
		//
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[text()='Approve']")));
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[text()='Approve']")).click();
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[text()='Decline']")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//*[text()='Proceed']")).click();
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Rejected']")));
			Thread.sleep(1500);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String actText = driver.findElement(By.className("quote-num-and-status")).getText();
		String expText = "REJECTED";boolean res = false;
		if (actText.contains(expText)) {
			res = true;
			Object status[] = {"QUOTES_028_Verify_Decline_QuotesForParts", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {"QUOTES_028_Verify_Decline_QuotesForParts", actText, expText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public void selectDropDown(String text) throws Exception
	{
		String dropDownText = driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).getText();
		System.out.println("Drop Down Text is "+dropDownText);

		if (!dropDownText.contains("No")) {
			List<WebElement> drops = driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).findElements(By.tagName("div"));
			System.out.println("count div tags are "+drops.size());
			Thread.sleep(500);
			for (int i = 0; i < drops.size(); i++) {
				System.out.println("values are "+drops.get(i).getText());
				if(drops.get(i).getText().equalsIgnoreCase(text)) {
					drops.get(i).click();
					break;
				}
				drops = driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).findElements(By.tagName("div"));
			}
		} else {}
	}
	public void values(Object data[]) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");  
		//personal laptop account details "jdbc:mysql://localhost:3306/demo","root","siva7661@"
		//office system details "jdbc:mysql://localhost:3306/testing","enterpi","enterpi@1234"
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","root","siva7661@");  
		Statement stmt=con.createStatement(); 
		String sql = "INSERT INTO buzzworld_automation_logs (test_case_name,actual_text,expected_text,page_name,status) "
				+ "VALUES ('"+ data[0]+ "',\""+ data[1] + "\",\""+ data[2] + "\",'" + data[3] + "','" + data[4]+ "')";
		stmt.executeUpdate(sql);  
	}
}
