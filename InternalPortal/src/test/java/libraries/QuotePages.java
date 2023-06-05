package libraries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.Duration;
import java.util.Iterator;
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
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div/div[5]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		driver.findElement(By.xpath("//*[@class='button-icon-text ']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='async-select-example']")));
		driver.findElement(By.xpath("//*[@id='async-select-example']")).sendKeys("Motion Industries - Grand Prairie");
		Thread.sleep(4700);
//		Motion Industries - Grand Prairie
		this.selectDropDown("Motion Industries - Grand Prairie");
		driver.findElement(By.name("project_name")).sendKeys("Test");
		driver.findElement(By.xpath("//*[contains(@id,'react-select')]")).sendKeys("Parts Quote");
		Thread.sleep(2500);
		this.selectDropDown("Parts Quote");
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("repair-items")));
	}
	public void selectItemToQuote() throws Exception {
		driver.findElement(By.id("repair-items")).findElement(By.className("button-icon-text")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='side-drawer open']")));
		driver.findElement(By.xpath("//*[@placeholder='Search By Part Number']")).sendKeys("0165");
		Thread.sleep(4000);
		driver.findElements(By.xpath("//*[contains(@class,'item-selection-grid')]")).get(0).findElement(By.tagName("label")).click();
		Thread.sleep(2000);
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
		driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button")).click();
		Thread.sleep(1200);
		rp.toastContainer("Approve");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button"), "Approve"));
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
		Thread.sleep(2500);
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
			 tcName = "QUOTES_007_VerifyQuoteLost";
			 expText = "LOST";
		}
		rp.wonOrLostButton(won);
		Thread.sleep(1200);
		rp.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button[1]"), "Won"));
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
		driver.findElement(By.xpath("/html/body/div/div/div[3]/div[2]/button")).click();
		Thread.sleep(2000);
		rp.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div/div/div[3]/div[2]/button"), "Submit for internal approval"));
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
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div/div[5]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		String tcName = "";String path = "";boolean res = false;String actText = "";
		if (count == 1) {
			tcName = "QUOTES_010_VerifySearchByQuoteId";
			path = "/html/body/div[1]/div/div[4]/div[2]/div/div/div/div/div/div[2]/div[2]/div[3]/div[2]/div/div/div[1]/div[1]";
		} else if(count == 2) {
			tcName = "QUOTES_011_VerifySearchByCompanyName";
			path = "/html/body/div[1]/div/div[4]/div[2]/div/div/div/div/div/div[2]/div[2]/div[3]/div[2]/div/div/div[1]/div[2]/span/div[2]";
		}else if(count == 3) {
			tcName = "QUOTES_012_VerifySearchBySalesPersonName";
			path = "/html/body/div[1]/div/div[4]/div[2]/div/div/div/div/div/div[2]/div[2]/div[3]/div[2]/div/div/div[1]/div[7]/span/div[2]";
		}else if(count == 4) {
			tcName = "QUOTES_013_VerifySearchByEmail";
			
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
			App.logout();
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
				App.logout();
			}
		}
		if (count==4) {
//			driver.findElement(By.xpath("//*[@class='down-arrow']")).click();
			List<WebElement> btns = driver.findElements(By.xpath("//*[@role='menuitem']"));
			btns.get(1).click();
		} else {
			driver.findElement(By.className("Cross-svg")).click();
		}
		return res;
	}
	public boolean verifyResetandClearButtonInFiltersPage(String compName, int count) throws Exception {
		boolean res = false;
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		driver.findElement(By.className("filter-text")).click();
		Thread.sleep(2300);
//		System.out.println("before filtersText "+compText1);
		driver.findElement(By.id("async-select-example")).sendKeys(compName);
		Thread.sleep(3000);
		this.selectDropDown(compName);
		driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div[2]/div[2]/div[4]/div[1]/div/div[3]/button[1]")).click();
		Thread.sleep(2000);
		driver.findElement(By.className("filter-text")).click();
		Thread.sleep(2000);
		String compText1 = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div[2]/div[2]/div[4]/div[1]/div/div[2]/div/div[1]/div/div/div[1]/div[1]")).getText();
		driver.findElement(By.xpath("//*[@title='close']")).click();
		if (compText1.equalsIgnoreCase("Search By Company Name")) {
			res = true;
			Object status[] = {"QUOTES_016_VerifyResetandClearButtonInFiltersPage", compText1, "", "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {"QUOTES_016_VerifyResetandClearButtonInFiltersPage", compText1, "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public boolean verifyClosenadReOpenButtons(int count) throws Exception {
		boolean res = false;
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		this.submitForInternalApproval();
		Thread.sleep(2000);
		driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("/html/body/div/div/div[5]/div[3]/button[2]")).click();
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button"), "Approve"));
		Thread.sleep(1500);
		String xpath = ""; String tcName = "";String expText = "";
		if (count==1) {
			xpath = "/html/body/div/div/div[3]/div[2]/button[1]";
			tcName = "QUOTES_017_VerifyReOpen_ButtonInQuoteDetailePage";
			expText = "open";
		} else if(count==2) {
			xpath = "/html/body/div/div/div[3]/div[2]/button[2]";
			tcName = "QUOTES_018_VerifyClose_ButtonInQuoteDetailePage";
			expText = "closed";
		}
		driver.findElement(By.xpath(xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("/html/body/div/div/div[5]/div[3]/button[1]/span")).click();
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
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-center-cols-container']")));
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div/div[5]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		driver.findElement(By.className("filter-text")).click();
//		System.exit(0);
		Thread.sleep(1500);
		List<WebElement> ins = driver.findElements(By.id("async-select-example"));
		ins.get(0).sendKeys(compName);
		Thread.sleep(3000);
		this.selectDropDown(compName);
		driver.findElement(By.id("react-select-3-input")).sendKeys(salesPerson);
		this.selectDropDown(salesPerson);
		driver.findElement(By.id("react-select-4-input")).sendKeys(status);
		this.selectDropDown(status);
		driver.findElement(By.id("react-select-5-input")).sendKeys(quotedBy);
		this.selectDropDown(quotedBy);
		ins.get(1).sendKeys("0165009LS");
		Thread.sleep(1600);
		this.selectDropDown("0165009LS");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div[2]/div[2]/div[4]/div[1]/div/div[3]/button[2]")).click();
		Thread.sleep(5000);
		driver.navigate().refresh();
		Thread.sleep(5000);
		if (driver.findElement(By.xpath("//*[contains(@title,'Reset Filters')]")).isDisplayed()) {
			res = true;
			Object status1[] = {"QUOTES_015_VerifyFiltersStateMaintanance", "Filter State Maintanance working.", "", "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status1);
		} else {
			res = true;
			Object status1[] = {"QUOTES_015_VerifyFiltersStateMaintanance", "Filter State Maintanance not working.!", "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status1);
		}
		driver.findElement(By.xpath("//*[contains(@title,'Reset Filters')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		return res;
	}
	public boolean verifyDeleteIcon(int count) throws Exception
	{
		Thread.sleep(2000);
		boolean res = false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		this.createQuote();
		Thread.sleep(1000);
		this.selectItemToQuote();
		String itemsCount = "";
		String expText = "";
		String tcName ="";
		if (count==1) {
			tcName = "QUOTES_019_VerifyDeleteIconInQuoteItems";
			itemsCount = driver.findElement(By.xpath("//*[@id='repair-items']")).findElement(By.tagName("h4")).getText();
			driver.findElement(By.xpath("//*[@title='Delete Item']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div/div/div[6]/div[1]/div/div[1]/div[6]/div[2]/div[2]/div[3]/button[1]")).click();
			Thread.sleep(2700);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
			Thread.sleep(2500);
			expText = "Quote Items (0)";
		} else if(count==2){
			tcName = "QUOTES_020_VerifyEditIconInQuoteItems";
			driver.findElement(By.xpath("//*[@title='Edit Item']")).click();
			Thread.sleep(1000);
			WebElement qty = driver.findElement(By.xpath("//input[@name='quantity']"));
			System.out.println("lenght of qty is"+qty.getAttribute("value").length());
			for(int i=0; i<qty.getAttribute("value").length();i++)
			{
				qty.sendKeys(Keys.BACK_SPACE);
			}
			expText = "200";
			qty.sendKeys("200");
			Thread.sleep(1500);
			driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
			Thread.sleep(2000);
			itemsCount = driver.findElement(By.xpath("/html/body/div/div/div[6]/div[1]/div/div[1]/div[6]/div[2]/div[1]/div/div[2]/div[2]/div[3]/div[1]/h4")).getText();
			System.out.println("items count "+itemsCount);
		}
		if (!itemsCount.equalsIgnoreCase(expText)||itemsCount.equalsIgnoreCase("200")) {
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
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='filter-text']")));
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div/div[5]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		driver.findElement(By.className("filter-text")).click();
//		System.exit(0);
		Thread.sleep(2000);
		List<WebElement> ins = driver.findElements(By.id("async-select-example"));
		ins.get(0).sendKeys(compName);
		Thread.sleep(3000);
		this.selectDropDown(compName);
		driver.findElement(By.id("react-select-3-input")).sendKeys(salesPerson);
		this.selectDropDown(salesPerson);
		driver.findElement(By.id("react-select-4-input")).sendKeys(status);
		this.selectDropDown(status);
		driver.findElement(By.id("react-select-5-input")).sendKeys(quotedBy);
		this.selectDropDown(quotedBy);
		ins.get(1).sendKeys("0165009LS");
		Thread.sleep(1600);
		this.selectDropDown("0165009LS");
//		driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div[2]/div[2]/div[4]/div[1]/div/div[3]/button[2]")).click();
		price = new PricingPages();
		price.clickButton("Apply");
		Thread.sleep(5000);
		String gridText = driver.findElement(By.xpath("//*[@class='ag-center-cols-viewport']")).getText();
		if (gridText=="") {
			res = false;
			Object status1[] = {"QUOTES_014_VerifyFiltersInQuotesListView", "No Quotes for Parts Found!", "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status1);
			Thread.sleep(1500);
			driver.findElement(By.xpath("//*[contains(@class,'clear-text')]")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
			App.logout();
		} else {
			String actText1 = driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div[2]/div/div/div/div/div/div[2]/div[2]/div[3]/div[2]/div/div/div[1]/div[2]/span/div[2]")).getText();
			String actText2 = driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div[2]/div/div/div/div/div/div[2]/div[2]/div[3]/div[2]/div/div/div[1]/div[6]/span/div[2]")).getText();
			String actText3 = driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div[2]/div/div/div/div/div/div[2]/div[2]/div[3]/div[2]/div/div/div[1]/div[7]")).getText();
			String actText4 = driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div[2]/div/div/div/div/div/div[2]/div[2]/div[3]/div[2]/div/div/div[1]/div[4]/span/div[2]")).getText();
			if (actText1.equalsIgnoreCase(compName)&& actText2.equalsIgnoreCase(salesPerson)&& actText3.equalsIgnoreCase(status)&& actText4.equalsIgnoreCase(quotedBy)) {
				res = true;
				Object status1[] = {"QUOTES_014_VerifyFiltersInQuotesListView", "Filter functionality working", "", "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
				this.values(status1);
			} else {
				res = false;
				Object status1[] = {"QUOTES_014_VerifyFiltersInQuotesListView", "Filter functionality not working.!", "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
				this.values(status1);
				Thread.sleep(1500);
				driver.findElement(By.xpath("//*[contains(@src,'filters-cancel')]")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
				App.logout();
			}
		}
		Thread.sleep(1700);
		
		
		return res;
	}
	public boolean verifyPrintDownLoad() throws Exception {
		this.createQuote();
		this.selectItemToQuote();
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
			Object status[] = {"QUOTES_009_VerifyPrintFunctionality", "Print working in Quotes Detailed Page.", "", "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {"QUOTES_009_VerifyPrintFunctionality", "Print Not working..!,in Quotes Detailed Page", "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public boolean verifyAddOptionInQuoteDetailedView() throws Exception {
		boolean res = false;
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div/div[5]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		this.createQuote();
		this.selectItemToQuote();
		List<WebElement> btns = driver.findElement(By.id("repair-items")).findElements(By.tagName("button"));
		System.out.println("count of btns in repair items tab is "+btns.size());
		btns.get(0).click();
		List<WebElement> lnks = driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElements(By.className("css-4rxcpg"));
		lnks.get(1).click();
		driver.findElement(By.xpath("//*[@placeholder='Search By Part Number']")).sendKeys("0165");
		Thread.sleep(4000);
		driver.findElement(By.id("tab-0-tab")).findElement(By.xpath("//*[@name='checkbox1'][@type='checkbox']")).click();
		Thread.sleep(2000);
		List<WebElement> btn = driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElements(By.tagName("button"));
		for(int i=0;i<btn.size();i++) 
		{
			if (btn.get(i).getText().equalsIgnoreCase("Add Selected 1 Items")) {
				btn.get(i).click();
				break;
			} 
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("repair-items")));
		Thread.sleep(1600);
		String optionText = driver.findElement(By.xpath("//*[@id='repair-items']")).findElement(By.xpath("//*[@aria-setsize='2'][@aria-posinset='2']")).getText();
		if (optionText.contains("Option 2")) {
			res = true;
			Object status[] = {"QUOTES_021_Verify_AddOptionInQuoteDetailedView", optionText, "Option 2", "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {"QUOTES_021_Verify_AddOptionInQuoteDetailedView", optionText, "Option 2", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public String[] quoteClone_QuotesForParts() throws Exception 
	{
		this.submitForInternalApproval();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1300);
		List<WebElement> repIds = driver.findElements(By.id("repair-info-id"));
		String beforeCloneRepText = repIds.get(0).getText();
		String beforeCloneCust = repIds.get(1).getText();
		String beforeCloneItems = driver.findElement(By.id("repair-items")).getText();
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[contains(@src,'clone')]")).click();
		Thread.sleep(1300);
		rp.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
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
				Object status[] = {"QUOTES_022_VerifyQuoteClone_QuotesForParts", actText, ExpText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
				this.values(status);
			} else {
				res = false;
				Object status[] = {"QUOTES_022_VerifyQuoteClone_QuotesForParts", actText, ExpText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
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
			Object status[] = {"QUOTES_022_VerifyQuoteClone_QuotesForParts", "", "", "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
			this.values(status);
		}
		return res;
	}
	public boolean quoteClone_QuoteForRepairs() throws Exception 
	{
		rp = new RepairPages();
		rp.createQuoteFromRepair();
		price = new PricingPages();
//		driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[1]/div/div[2]/div[1]/div/div[2]/div/div/div/h4/div/div")).click();
		Thread.sleep(2000);
		price.clickButton("Submit for internal approval");
		Thread.sleep(1500);
		rp.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div/div/div[3]/div[2]/button"), "Submit for internal approval"));
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[contains(@src,'clone')]")).click();
		Thread.sleep(1300);
		rp.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
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
				Object status[] = {"QUOTES_023_VerifyQuoteClone_QuotesForRepairs", "Quote Type is "+quoteType, "Quote status is "+actText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
				this.values(status);
			}else {
				res = false;
				Object status[] = {"QUOTES_023_VerifyQuoteClone_QuotesForRepairs", "Quote Type is "+quoteType, "Quote status is "+actText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
				this.values(status);
			}
		} else {
			res = false;
			Object status[] = {"QUOTES_023_VerifyQuoteClone_QuotesForRepairs", "Quote Type is "+quoteType, "Quote status is "+actText, "QuotesPage", "Failed", java.time.LocalDate.now().toString()};
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
		this.declineTheQuote();
		String actText = driver.findElement(By.className("quote-num-and-status")).getText();
		String expText = "REJECTED";boolean res = false;
		if (actText.contains(expText)) {
			res = true;
			Object status[] = {"QUOTES_024_Verify_Decline_QuotesForParts", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
			this.values(status);
		} else {
			res = false;
			Object status[] = {"QUOTES_024_Verify_Decline_QuotesForParts", actText, expText, "QuotesPage", "Passed", java.time.LocalDate.now().toString()};
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
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","enterpi","enterpi@1234");  
		Statement stmt=con.createStatement(); 
		String sql = "INSERT INTO buzzworld_automation_logs (test_case_name,actual_text,expected_text,page_name,status) "
				+ "VALUES ('"+ data[0]+ "','"+ data[1] + "','"+ data[2] + "','" + data[3] + "','" + data[4]+ "')";
		stmt.executeUpdate(sql);  
	}
}
