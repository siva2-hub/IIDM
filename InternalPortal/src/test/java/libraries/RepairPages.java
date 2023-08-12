package libraries;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import commonUtils.App;

public class RepairPages extends App
{
	WebDriverWait wait;
	QuotePages quotes = new QuotePages();
	PricingPages price = new PricingPages();
	public void repairPage() throws InterruptedException 
	{
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div/div[4]")).click();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'All Repairs Requests']")));
		driver.findElement(By.xpath("//*[text() = 'All Repairs Requests']")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'Filters']")));
		Thread.sleep(1500);
		try {
			driver.findElement(By.xpath("//*[text()='Clear']")).isDisplayed();
			driver.findElement(By.xpath("//*[text()='Clear']")).click();
		} catch (Exception e) {
			// TODO: handle exception
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
	}
	public void createRMA() throws Exception 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		this.repairPage(); Actions act = new Actions(driver);
		driver.findElement(By.xpath("//*[text() = 'Create RMA']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("async-select-example")).sendKeys("Zummo Meat Co Inc");
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Zummo Meat Co Inc')]")));
		quotes.selectDropDown("Zummo Meat Co Inc");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@class, 'opacity-on-load')]")));
		Thread.sleep(1500);
		driver.findElement(By.id("async-select-example")).click();
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys("Standard").build().perform();;
		quotes.selectDropDown("Standard");
		Thread.sleep(1500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys("Luke Pelts").build().perform();;
		Thread.sleep(1500);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		quotes.selectDropDown("Luke Pelts");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(1600);
	}
	public boolean verifyCreateRMA() throws Exception {
		this.createRMA();
		boolean res = false;
		String expText = "CHECK IN PENDING";
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"REPAIRS_001_VerifyCreateRMA", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_001_VerifyCreateRMA", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public String selectItemToRepair() throws Exception {

		this.createRMA();
		String actText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		quotes.selectItemToQuote();
		return actText;
	}
	public boolean verifySelectItemsToRepair() throws Exception 
	{
		String actText = this.selectItemToRepair();
		boolean res = false;
		Thread.sleep(2000);
		String expText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		if (!actText.equals(expText)) {
			res = true;
			Object status[] = {"REPAIRS_002_VerifySelectItemToRepair", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_002_VerifySelectItemToRepair", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public String addNewItem() throws Exception
	{
		//		this.createRMA();
		String serialNumber = price.getTime();
		String actText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		driver.findElement(By.id("repair-items")).findElement(By.className("Button-Icon-Display")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='side-drawer open']")));
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.id("tab-2")).click();
		driver.findElement(By.id("async-select-example")).sendKeys("BACO CONTROLS INC");
		Thread.sleep(1000);
		quotes.selectDropDown("BACO CONTROLS INC");
		driver.findElement(By.name("custom_part_items.0.part_number")).sendKeys("PN12345");
		driver.findElement(By.name("custom_part_items.0.serial_number")).sendKeys(serialNumber);
		driver.findElement(By.name("custom_part_items.0.description")).sendKeys("Test Description");
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		return actText;
	}
	public boolean verifyAddNewItem(String env) throws Exception 
	{
		String actText = this.addNewItem();
		boolean res = false;
		Thread.sleep(2000);
		String expText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		if (!actText.equals(expText)) {
			res = true;
			Object status[] = {"REPAIRS_020_VerifyAddNewItem", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString(), env};
			App.values1(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_020_VerifyAddNewItem", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString(), env};
			App.values1(status);
		}
		return res;
	}
	public void assignLocation() throws Exception 
	{
		this.selectItemToRepair();
		List<WebElement> btn=  driver.findElement(By.xpath("//*[@id='repair-items']")).findElements(By.xpath("//*[contains(@class,'action-item icon-bg-hover')]"));
		btn.get(0).click();
		Thread.sleep(1000);
		Thread.sleep(1500);
		Actions act = new Actions(driver);
		WebElement editIcon = driver.findElement(By.xpath("//*[@class='quantity-parent']")).findElement(By.tagName("svg"));
		act.moveToElement(editIcon).perform();
		editIcon.click();
		Thread.sleep(1400);
		//		driver.findElements(By.xpath("//*[@title='Edit']")).get(2).click();
		driver.findElement(By.name("storage")).sendKeys("New York");
		driver.findElement(By.name("serial_no")).sendKeys(java.time.LocalTime.now().toString().replace("-","").replace(":", "").replace(".", ""));
		driver.findElement(By.xpath("//*[@title='Save Changes']")).click();
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Assign Technician']")));
		Thread.sleep(1300);
	}
	public boolean verifyAssignLocation() throws Exception {
		this.assignLocation();
		boolean res = false;
		String expText = "CHECKED-IN";
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"REPAIRS_004_VerifyAssignLocation", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_004_VerifyAssignLocation", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public void assignTechnician() throws Exception 
	{
		this.assignLocation();
		driver.findElement(By.xpath("//*[text()='Assign Technician']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(@class,'react-select__indicator')]")).click();
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		//		List<WebElement> btns = driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElements(By.tagName("button"));
		//		for(int i=0;i<btns.size();i++) {
		//			if(btns.get(i).getText().equalsIgnoreCase("Assign")) {
		//				btns.get(i).click();
		//				break;
		//			}
		//		}
		driver.findElement(By.xpath("//*[text()='Assign']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2000);
	}
	public boolean verifyAssignTechnician() throws Exception {
		this.assignTechnician();
		boolean res = false;
		String expText = "PENDING EVALUATION";
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"REPAIRS_005_VerifyAssignTechnician", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_005_VerifyAssignTechnician", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public void evaluateItem() throws Exception 	
	{
		this.assignTechnician();
		List<WebElement> btn=  driver.findElement(By.xpath("//*[@id='repair-items']")).findElements(By.xpath("//*[contains(@class,'action-item icon-bg-hover')]"));
		//		btn.get(0).click();
		driver.findElement(By.className("hides")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[contains(@class,'auto__dropdown-indicator')]")).click();
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		driver.findElement(By.name("estimated_hrs")).sendKeys("23");
		driver.findElement(By.name("price")).sendKeys("198");
		List<WebElement> btns = driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElements(By.tagName("button"));
		btns.get(7).click();
		System.out.println("count of btns are "+btns.size());
		for(int i=0;i<btns.size();i++) {
			System.out.println(btns.get(i).getText());
		}
		Thread.sleep(1300);
	}
	public boolean verifyEvaluateItem() throws Exception {
		this.evaluateItem();
		boolean res = false;
		String expText = "PENDING QUOTE";
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"REPAIRS_006_VerifyEvaluateItem", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_006_VerifyEvaluateItem", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public void addRepairableItemToQuote(String data) throws Exception 
	{
		this.evaluateItem();
		Thread.sleep(1600);
		driver.findElement(By.xpath("//*[contains(@class,'check_box')]")).findElement(By.tagName("label")).click();
		price.clickButton("Add items to quote");
		Thread.sleep(1000);
		List<WebElement> btns = driver.findElement(By.className("toast-action")).findElements(By.tagName("button"));
		for(int i=0;i<btns.size();i++) 
		{
			if(btns.get(i).getText().toLowerCase().contains(data.toLowerCase())) 
			{
				btns.get(i).click();
				break;
			}
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2000);
	}
	public boolean verifyAddRepairableItemToQuote() throws Exception 
	{
		this.addRepairableItemToQuote("Accept");
		boolean res = false;
		String expText = "ADDED TO QUOTE";
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"REPAIRS_007_VerifyAddRepairableItemToQuote", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_007_VerifyAddRepairableItemToQuote", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public void createQuoteFromRepair() throws Exception 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		Actions act = new Actions(driver);
		this.addRepairableItemToQuote("Accept");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src, 'eyeIcon')]")));
		Thread.sleep(2000);
		act.moveToElement(driver.findElements(By.xpath("//*[contains(text(),'2023')]")).get(1)).build().perform();
		act.doubleClick(driver.findElements(By.xpath("//*[contains(text(),'2023')]")).get(1)).build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Submit for internal approval']")));
		Thread.sleep(2000);
	}
	public boolean verifyCreateQuoteFromRepair() throws Exception 
	{
		this.createQuoteFromRepair();
		boolean res = false;
		String expText = "OPEN";
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"REPAIRS_008_VerifyCreateQuoteFromRepair", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_008_VerifyCreateQuoteFromRepair", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public void assignToQC() throws Exception 
	{
		this.createQuoteFromRepair();
		Thread.sleep(2500);
		Actions act = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'delete-icon')]")));
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.click(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Quote Price']")));
		Thread.sleep(1200);
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
		Thread.sleep(2000);
		price.clickButton("Submit for internal approval");
		Thread.sleep(2000);
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text()='Submit for internal approval']"), "Submit for internal approval"));
		Thread.sleep(1700);
		price.clickButton("Approve");
		Thread.sleep(1200);
		this.toastContainer("Approve");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text()='Approve']"), "Approve"));
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).findElement(By.tagName("button")).click();
		driver.findElement(By.xpath("//*[text()='Delivered to Customer']")).click();
		Thread.sleep(2000);
		this.wonOrLostButton("Won");
		Thread.sleep(1200);
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text()='Won']"), "Won"));
		Thread.sleep(1400);
		driver.findElements(By.xpath("//*[contains(@class,'border-bottom')]")).get(1).findElement(By.tagName("h4")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Assign to QC']")));
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[text()='Assign to QC']")).click();
		Thread.sleep(1500);
		this.toastContainer("Accept");
		Thread.sleep(2200);
	}
	public boolean verifyAssignToQC() throws Exception 
	{
		this.assignToQC();
		boolean res = false;
		String expText = "PENDING QC";
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"REPAIRS_009_VerifyAssignToQC", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_009_VerifyAssignToQC", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public void qcCheckList(String data) throws Exception 
	{
		//		this.assignToQC(); 
		Actions act = new Actions(driver);
		driver.findElement(By.xpath("//*[contains(@src,'qc_checklist')]")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[text()='Save']")));
		Thread.sleep(1300);
		driver.findElements(By.xpath("//*[contains(@class,'react-select__indicator')]")).get(0).click();
		act.sendKeys("Quality control report");
		Thread.sleep(1000);
		act.sendKeys(Keys.ENTER).build().perform();
		//		quotes.selectDropDown("Quality control report");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1000);
		driver.findElements(By.xpath("//*[contains(@class,'react-select__indicator')]")).get(3).click();
		act.sendKeys(data).build().perform();
		act.sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(1000);
		//		quotes.selectDropDown("Pass");
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.xpath("//*[@type='submit']")).click();
		App.spinner();
		Thread.sleep(1400);	
	}
	public boolean verifyQCCheckList(String data, String env) throws Exception 
	{
		this.qcCheckList(data);
		boolean res = false;
		String expText = "QC FAILED";
		String actText = driver.findElement(By.xpath("//*[@style = 'white-space: nowrap; max-width: 100%; text-overflow: ellipsis;']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"REPAIRS_010_VerifyQCCheckList_as_"+data, actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString(), env};
			App.values1(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_010_VerifyQCCheckList_"+data, actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString(), env};
			App.values1(status);
		}
		return res;
	}
	public boolean verifyQCCheckListStatusAsFail() throws Exception 
	{
		this.qcCheckList("Fail");
		String actText = "";boolean res = false;
		List<WebElement> stats = driver.findElement(By.xpath("//*[@id='repair-items']")).findElements(By.tagName("h4"));
		for(int i=0;i<stats.size();i++) 
		{
			if(stats.get(i).getText().toLowerCase().contains("QC".toLowerCase())) 
			{
				actText = stats.get(i).getText();
				break;
			}
		}
		String expText = "QC FAILED";
		if (actText.toLowerCase().equals(expText.toLowerCase())) {
			res = true;
			Object status[] = {"REPAIRS_017_VerifyQCCheckListStatusAsFail", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_017_VerifyQCCheckListStatusAsFail", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public boolean fileUpload(String env) throws Exception 
	{
		//		this.createRMA();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Upload']")));
		driver.findElement(By.xpath("//*[@type='file']")).sendKeys("/home/enterpi/Pictures/Screenshot from 2023-04-18 10-35-23.png");
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//*[text()='Upload']")));
		Thread.sleep(1500);
		boolean res = false; boolean sta = false;
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='image_Container']")));
			driver.findElement(By.xpath("//*[@class='image_Container']")).isDisplayed();
			sta = true;
			price.takesScreenShot("FileUploading_Passed_Repair.png");
		} catch (Exception e) {
			price.takesScreenShot("FileUploading_Failed_Repair.png");
		}
		if (sta) {
			res = true;
			Object status[] = {"REPAIRS_017_VerifyFileUpload_Repairs", "Document Uploaded successfully", "", "RepairsPage", "Passed", java.time.LocalDate.now().toString(), env};
			App.values1(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_017_VerifyFileUpload_Repairs", "Document Uploading Failed.!", "", "RepairsPage", "Failed", java.time.LocalDate.now().toString(), env};
			App.values1(status);
		}
		return res;

	}
	public void filters(String data1, String data2, String data3) throws Exception 
	{
		this.repairPage();
		try {
			driver.findElement(By.xpath("//*[contains(@class,'Cross-svg close-icon-container')]")).isDisplayed();
			driver.findElement(By.xpath("//*[contains(@class,'Cross-svg close-icon-container')]")).click();
		} catch (Exception e) {
			// TODO: handle exception
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='filter-text']")));
		driver.findElement(By.xpath("//*[@class='filter-text']")).click();
		App.spinner();
		List<WebElement> inputs = driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElements(By.xpath("//*[@type='text'][@autocapitalize='none']"));
		System.out.println("count of input tags are "+inputs.size());
		String[] data = {data1, data2, data3};
		for(int i=0;i<inputs.size();i++) 
		{
			inputs.get(i).sendKeys(data[i]);
			Thread.sleep(2300);
			quotes.selectDropDown(data[i]);
			inputs = driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElements(By.xpath("//*[@type='text'][@autocapitalize='none']"));
		}
		List<WebElement> btns = driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElements(By.tagName("button"));
		btns.get(1).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		Thread.sleep(2400);
	}
	public boolean verifyFilters(String data, String data1, String data2, String env) throws Exception 
	{
		String compName = "123 E Doty Corporation"; String salesP = "Dallas House";
		String status1 = "Receiving";boolean res = false;
		this.filters(compName, salesP, status1);
		System.out.println("grid text after apply the filter "+driver.findElement(By.xpath("//*[@class='ag-center-cols-viewport']")).getText());
		if (driver.findElement(By.xpath("//*[@class='ag-center-cols-viewport']")).getText().equalsIgnoreCase("")) {
			res = false;
			Object status[] = {"REPAIRS_019_VerifyFilters", "Repair Request Not Found", "", "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			List<WebElement> txts = driver.findElement(By.xpath("//*[@class='ag-center-cols-container']")).findElements(By.xpath("//*[@row-index='0']"));
			List<WebElement> ls = txts.get(0).findElements(By.xpath("//*[contains(@class,'ag-cell ag-cell')]"));
			String actComp = driver.findElement(By.xpath("//*[text()='"+compName+"']")).getText();
			String actSp = driver.findElement(By.xpath("//*[text()='"+salesP+"']")).getText();
			String actStatus = driver.findElement(By.xpath("//*[text()='"+status1+"']")).getText();
			if (compName.equalsIgnoreCase(actComp)&&salesP.equalsIgnoreCase(actSp)&&status1.equalsIgnoreCase(actStatus)) {
				res = true;
				Object status[] = {"REPAIRS_019_VerifyFilters", actComp+" "+actSp+" "+actStatus, compName+" "+salesP+" "+status1, "RepairsPage", "Passed", java.time.LocalDate.now().toString(), env};
				App.values1(status);
			} else {
				res = false;
				Object status[] = {"REPAIRS_019_VerifyFilters", actComp+" "+actSp+" "+actStatus, compName+" "+salesP+" "+status1, "RepairsPage", "Failed", java.time.LocalDate.now().toString(), env};
				App.values1(status);
			}
		}
		driver.findElement(By.xpath("//*[@title='Reset Filters ']")).click();
		return res;
	}
	public boolean verifyFilterStateMaintanance(String env) throws Exception 
	{

		boolean res = false;
		this.filters("123 E Doty Corporation", "Dallas House", "Check In Pending");
		WebElement ele = driver.findElement(By.xpath("//*[@title='Filters Applied']"));
		driver.navigate().refresh();
		App.spinner();
		Thread.sleep(1000);
		ele = driver.findElement(By.xpath("//*[text() = 'Clear']"));
		if (ele.isDisplayed()) {
			res = true;
			Object status[] = {"REPAIRS_020_VerifyFilterStateMaintanance", "filters  are stabled after refresh the page",
					"", "RepairsPage", "Passed", java.time.LocalDate.now().toString(), env};
			App.values1(status);
		} else 
		{
			res = false;
			Object status[] = {"REPAIRS_020_VerifyFilterStateMaintanance", "filters  are not stabled after refresh the page",
					"", "RepairsPage", "Failed", java.time.LocalDate.now().toString(), env};
			App.values1(status);
		}
		return res;
	}
	public void assignToQC_SO_Created() throws Exception
	{
		this.createRMA();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2000);
		PricingPages price = new PricingPages();Actions act = new Actions(driver);
		driver.findElement(By.className("Button-Icon-Display")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='side-drawer open']")));
		driver.findElement(By.xpath("//*[@placeholder='Search By Part Number']")).sendKeys("0165");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@placeholder='Search By Part Number']")).click();
		act.sendKeys(Keys.TAB).build().perform(); act.sendKeys(Keys.TAB).build().perform(); act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(Keys.SPACE).build().perform();
		act.sendKeys(Keys.TAB).build().perform(); act.sendKeys(Keys.TAB).build().perform(); act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(Keys.SPACE).build().perform();
		Thread.sleep(1000);
		price.clickButton("Add Selected 2 Parts");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(1300);
		//Assign Location For First Item
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Assign Location']")));
		act.moveToElement(driver.findElement(By.xpath("//*[text()='Assign Location']"))).build().perform();
		driver.findElement(By.xpath("//*[text()='Assign Location']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='storage']")));
		driver.findElement(By.name("storage")).sendKeys("C12");
		WebElement editIcon = driver.findElement(By.xpath("//*[@class='quantity-parent']")).findElement(By.tagName("svg"));
		act.moveToElement(editIcon).perform();
		editIcon.click();
		driver.findElement(By.name("serial_no")).sendKeys(java.time.LocalTime.now().toString().replace("-","").replace(":", "").replace(".", ""));
		driver.findElement(By.xpath("//*[@title='Save Changes']")).click();
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();
		Thread.sleep(2000);
		//Assign Location For Second Item
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Assign Location']")));
		driver.findElement(By.xpath("//*[text()='Assign Location']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='storage']")));
		driver.findElement(By.name("storage")).sendKeys("New York");
		editIcon = driver.findElement(By.xpath("//*[@class='quantity-parent']")).findElement(By.tagName("svg"));
		act.moveToElement(editIcon).perform();
		editIcon.click();
		Thread.sleep(1400);
		driver.findElement(By.name("serial_no")).sendKeys(java.time.LocalTime.now().toString().replace("-","").replace(":", "").replace(".", ""));
		driver.findElement(By.xpath("//*[@title='Save Changes']")).click();
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();
		Thread.sleep(2000);
		//Assign Technician for First Item
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Assign Technician']")));
		driver.findElement(By.xpath("//*[text()='Assign Technician']")).click();
		Thread.sleep(1500);
		driver.findElement(By.id("react-select-6-input")).sendKeys(Keys.ARROW_UP);
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		price.clickButton("Assign");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2000);
		//Assign Technician for Second Item
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Assign Technician']")));
		driver.findElement(By.xpath("//*[text()='Assign Technician']")).click();
		Thread.sleep(1500);
		driver.findElement(By.id("react-select-7-input")).sendKeys(Keys.ARROW_UP);
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		price.clickButton("Assign");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2000);
		//Evaluate First Item
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Evaluate']")));
		driver.findElement(By.xpath("//*[text()='Evaluate']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("react-select-8-input")).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		driver.findElement(By.name("estimated_hrs")).sendKeys("23");
		driver.findElement(By.name("price")).sendKeys("198");
		price.clickButton("Update Evaluation");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2000);
		//Evaluate Second Item
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Evaluate']")));
		driver.findElement(By.xpath("//*[text()='Evaluate']")).click();
		Thread.sleep(1500);
		driver.findElement(By.id("react-select-10-input")).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		driver.findElement(By.name("estimated_hrs")).sendKeys("23");
		driver.findElement(By.name("price")).sendKeys("198");
		price.clickButton("Update Evaluation");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2000);
		//Add REPAIRABLE Items To Quote
		driver.findElements(By.xpath("//*[@class = 'check_box']")).get(0).findElement(By.tagName("label")).click();
		price.clickButton("Add items to quote");
		Thread.sleep(700);
		this.toastContainer("Accept");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), '"+java.time.LocalDate.now().toString().replace("-", "")+"')]")));
		//Go to Quotes Module
		Thread.sleep(1400);
		driver.findElement(By.xpath("//*[contains(text(), '"+java.time.LocalDate.now().toString().replace("-", "")+"')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Submit for internal approval']")));
		Thread.sleep(1400);
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.click(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Quote Price']")));
		Thread.sleep(1200);
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
		Thread.sleep(2000);
		//Submit for Internal Approval
		price.clickButton("Submit for internal approval");
		Thread.sleep(2000);
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text()='Submit for internal approval']"), "Submit for internal approval"));
		Thread.sleep(1700);
		//Quote Approve
		price.clickButton("Approve");
		Thread.sleep(1200);
		this.toastContainer("Approve");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text()='Approve']"), "Approve"));
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).findElement(By.tagName("button")).click();
		driver.findElement(By.xpath("//*[@class='link-icon-text']")).click();
		Thread.sleep(2000);
		//Quote Won
		this.wonOrLostButton("Won");
		Thread.sleep(1200);
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text()='Won']"), "Won"));
		Thread.sleep(1400);
		//Back to Repair Module
		driver.findElements(By.xpath("//*[contains(@class,'border-bottom')]")).get(1).findElement(By.tagName("h4")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Assign to QC']")));
		Thread.sleep(2500);
	}
	public boolean verifyAssignToQCIsDisplayedOrNotWhenQuoteStatusHasWon() throws Exception 
	{
		this.assignToQC_SO_Created();
		String rMAStatus = driver.findElement(By.className("quote-num-and-status")).getText();
		String expRMAStatus = "IN PROGRESS";String expItemStatus = "Assign to QC";boolean res =  false;
		String itemStatus = driver.findElement(By.xpath("//*[contains(@class,'action-item icon-bg-hover')]")).getText();
		if (rMAStatus.contains(expRMAStatus) && itemStatus.contains(expItemStatus)) {
			res = true;
			Object status[] = {"REPAIRS_021_VerifyAssignToQCIsDisplayedOrNotWhenQuoteStatusHasWon", "act item status is "+itemStatus+"exp item status is "+expItemStatus,
					"act RMA status is "+rMAStatus+"exp RMA status is "+expRMAStatus, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_021_VerifyAssignToQCIsDisplayedOrNotWhenQuoteStatusHasWon", "act item status is "+itemStatus+"exp item status is "+expItemStatus,
					"act RMA status is "+rMAStatus+"exp RMA status is "+expRMAStatus, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public void assignToQC_WhenReviseTheQuote() throws Exception
	{
		this.assignToQC_SO_Created();
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class = 'check_box']")).findElement(By.tagName("label")).click();
		//Add The Remaining Item to Quote
		price.clickButton("Add items to quote");
		Thread.sleep(700);
		this.toastContainer("Accept");
		Thread.sleep(1900);
		//Go to Quotes Module
		JavascriptExecutor js = (JavascriptExecutor)driver; Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//*[text()= 'Assign to QC']"))).build().perform();
		for (int i=0 ;i<15; i++) {
			act.sendKeys(Keys.ARROW_UP).build().perform();
		}
		Thread.sleep(1500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), '"+java.time.LocalDate.now().toString().replace("-", "")+"')]")));
		Thread.sleep(1400);
		driver.findElements(By.xpath("//*[contains(@title, '"+java.time.LocalDate.now().toString().replace("-", "")+"')]")).get(1).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Submit for internal approval']")));
		Thread.sleep(1400);
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		act.click(driver.findElements(By.xpath("//*[contains(@src,'themecolorEdit')]")).get(1)).build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Quote Price']")));
		Thread.sleep(1200);
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
		Thread.sleep(2000);
		//Submit for Internal Approval
		driver.findElement(By.xpath("//*[text()='Submit for internal approval']")).click();
		Thread.sleep(2000);
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text()='Submit for internal approval']"), "Submit for internal approval"));
		Thread.sleep(1700);
		//Revise The Quote
		Thread.sleep(1900);
		price.clickButton("Revise Quote");
		Thread.sleep(1200);
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Submit for internal approval']")));
		Thread.sleep(1500);

		//Submit for Internal Approval
		driver.findElement(By.xpath("//*[text()='Submit for internal approval']")).click();
		Thread.sleep(2000);
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text()='Submit for internal approval']"), "Submit for internal approval"));
		Thread.sleep(1700);
		//Quote Approve
		price.clickButton("Approve");
		Thread.sleep(1200);
		this.toastContainer("Approve");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[text()='Approve']"), "Approve"));
		Thread.sleep(2200);
		driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).findElements(By.tagName("button")).get(1).click();
		driver.findElement(By.xpath("//*[@class='link-icon-text']")).click();
		Thread.sleep(2000);
		//Quote Won
		this.wonOrLostButton("Won");
		Thread.sleep(1200);
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button[1]"), "Won"));
		Thread.sleep(1400);
		//Back to Repair Module
		driver.findElements(By.xpath("//*[contains(@class,'border-bottom')]")).get(1).findElement(By.tagName("h4")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2500);

	}
	public boolean verifyAssignToQC_WhenReviseTheQuote() throws Exception 
	{
		this.assignToQC_WhenReviseTheQuote();
		String rMAStatus = driver.findElement(By.className("quote-num-and-status")).getText();
		String expRMAStatus = "IN PROGRESS";String expItemStatus = "Assign to QC";boolean res =  false;
		Thread.sleep(1500);
		String itemStatus = driver.findElements(By.xpath("//*[contains(@class,'action-item icon-bg-hover')]")).get(3).getText();
		if (rMAStatus.contains(expRMAStatus) && itemStatus.contains(expItemStatus)) {
			res = true;
			Object status[] = {"REPAIRS_022_VerifyAssignToQC_WhenReviseTheQuote", "act item status is "+itemStatus+"exp item status is "+expItemStatus,
					"act RMA status is "+rMAStatus+"exp RMA status is "+expRMAStatus, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else{
			res = false;
			Object status[] = {"REPAIRS_022_VerifyAssignToQC_WhenReviseTheQuote", "act item status is "+itemStatus+"exp item status is "+expItemStatus,
					"act RMA status is "+rMAStatus+"exp RMA status is "+expRMAStatus, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public void repairsModule() throws Exception
	{
		//Create RMA
		this.createRMA();
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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Assign Technician')]")));
		driver.findElement(By.xpath("//*[contains(text(),'Assign Technician')]")).click();
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
		this.toastContainer("Accept");
		//		btns = driver.findElement(By.className("toast-action")).findElements(By.tagName("button"));
		//		for(int i=0;i<btns.size();i++) 
		//		{
		//			if(btns.get(i).getText().toLowerCase().contains("Accept".toLowerCase())) 
		//			{
		//				btns.get(i).click();
		//				break;
		//			}
		//		}
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
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div/div/div[3]/div[2]/button"), "Submit for internal approval"));
		Thread.sleep(1700);
		price.clickButton("Approve");
		Thread.sleep(1200);
		this.toastContainer("Approve");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button"), "Approve"));
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).findElement(By.tagName("button")).click();
		driver.findElement(By.xpath("//*[@class='Button-Icon-Display']")).click();
		Thread.sleep(2000);
		this.wonOrLostButton("Won");
		Thread.sleep(1200);
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button[1]"), "Won"));
		Thread.sleep(1400);
		driver.findElements(By.xpath("//*[contains(@class,'border-bottom')]")).get(1).findElement(By.tagName("h4")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2300);
		driver.findElement(By.xpath("//*[contains(@class,'hides')]")).click();
		this.toastContainer("Accept");
		Thread.sleep(1000);
		this.toastContainer("Accept");
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
		driver.findElement(By.xpath("//*[contains(@src, 'qc_checklist')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("react-select-9-input")));
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
	}

	//..................................................................................................................
	public void toastContainer(String data) throws InterruptedException 
	{
		List<WebElement> btns = driver.findElement(By.className("toast-action")).findElements(By.tagName("button"));
		for(int i=0;i<btns.size();i++) 
		{
			Thread.sleep(1200);
			if(btns.get(i).getText().toLowerCase().contains(data.toLowerCase())) 
			{
				btns.get(i).click();
				break;
			}
		}
	}
	public void wonOrLostButton(String data) 
	{
		List<WebElement> wons = driver.findElements(By.tagName("button"));
		for(int w=0;w<wons.size();w++) 
		{
			if(wons.get(w).getText().toLowerCase().contains(data.toLowerCase())) 
			{
				wons.get(w).click();
				break;
			}
		}
	}
}
