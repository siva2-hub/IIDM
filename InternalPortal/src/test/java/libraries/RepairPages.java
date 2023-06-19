package libraries;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
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
	public void repairPage() 
	{
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div/div[4]")).click();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
	}
	public void createRMA() throws Exception 
	{
		this.repairPage();
		driver.findElement(By.xpath("//*[@class='Button-Icon-Display']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("async-select-example")).sendKeys("Zummo Meat Co Inc");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		quotes.selectDropDown("Zummo Meat Co Inc");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1000);
		driver.findElement(By.id("react-select-3-input")).sendKeys("Standard");
		quotes.selectDropDown("Standard");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		driver.findElement(By.id("react-select-4-input")).sendKeys("Luke Pelts");
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
		this.createRMA();
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
	public boolean verifyAddNewItem() throws Exception 
	{
		String actText = this.addNewItem();
		boolean res = false;
		Thread.sleep(2000);
		String expText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		if (!actText.equals(expText)) {
			res = true;
			Object status[] = {"REPAIRS_003_VerifyAddNewItem", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_003_VerifyAddNewItem", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
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
		driver.findElement(By.id("react-select-7-input")).sendKeys(Keys.ARROW_DOWN);
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
		driver.findElement(By.xpath("//*[contains(@class,'repair-item-checkbox')]")).click();
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
		this.addRepairableItemToQuote("Accept");
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[contains(@label,'2023')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
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
		driver.findElement(By.xpath("//*[contains(@label,'0')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(1500);
		List<WebElement> btn=  driver.findElement(By.xpath("//*[@id='repair-items']")).findElements(By.tagName("button"));
		//		btn.get(0).click();
		Thread.sleep(2300);
		//		System.exit(0);
		driver.findElement(By.xpath("//*[contains(@class,'hides')]")).click();
		this.toastContainer("Accept");
		Thread.sleep(1000);
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
		this.assignToQC();
		driver.findElement(By.xpath("//*[@title='QC Checklist']")).click();
		Thread.sleep(1300);
		driver.findElement(By.id("react-select-9-input")).sendKeys("QC Control Report");
		Thread.sleep(1000);
		quotes.selectDropDown("QC Control Report");
		driver.findElement(By.id("react-select-10-input")).sendKeys(data);
		Thread.sleep(1000);
		quotes.selectDropDown(data);
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.xpath("//*[@type='submit']")).click();
		Thread.sleep(1400);
	}
	public boolean verifyQCCheckList() throws Exception 
	{
		this.qcCheckList("Pass");
		boolean res = false;
		String expText = "COMPLETED";
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"REPAIRS_010_VerifyQCCheckList", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_010_VerifyQCCheckList", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
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
			Object status[] = {"REPAIRS_011_VerifyQCCheckListStatusAsFail", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_011_VerifyQCCheckListStatusAsFail", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public boolean fileUpload() throws Exception 
	{
		this.createRMA();
		driver.findElement(By.xpath("//*[@type='file']")).sendKeys("/home/enterpi/Pictures/Screenshot from 2023-04-18 10-35-23.png");
		Thread.sleep(2000);
		boolean res = false;
		String expText = " Documents Not Available";
		String actText = driver.findElement(By.xpath("//*[@id='repair-docs']")).getText();
		List<WebElement> boxes = driver.findElements(By.xpath("//*[@viewBox='0 0 18 18']"));
		boxes.get(9).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@viewBox='0 0 16 16']")).click();
		if (!actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"REPAIRS_012_VerifyFileUpload", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_012_VerifyFileUpload", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;

	}
	public void filters(String data1, String data2, String data3) throws Exception 
	{
		this.repairPage();
		driver.findElement(By.xpath("//*[@class='filter-text']")).click();
		Thread.sleep(1500);
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
	public boolean verifyFilters(String data, String data1, String data2) throws Exception 
	{
		String compName = "123 E Doty Corporation"; String salesP = "Dallas House";
		String status1 = "Check In Pending";boolean res = false;
		this.filters(compName, salesP, status1);
		System.out.println("grid text after apply the filter "+driver.findElement(By.xpath("//*[@class='ag-center-cols-viewport']")).getText());
		if (driver.findElement(By.xpath("//*[@class='ag-center-cols-viewport']")).getText().equalsIgnoreCase("")) {
			res = false;
			Object status[] = {"REPAIRS_013_VerifyFilters", "Repair Request Not Found", "", "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {

			List<WebElement> txts = driver.findElement(By.xpath("//*[@class='ag-center-cols-container']")).findElements(By.xpath("//*[@row-index='0']"));
			//		System.out.println("comp name is "+txts.size());
			List<WebElement> ls = txts.get(0).findElements(By.xpath("//*[contains(@class,'ag-cell ag-cell')]"));
			//			for(int i=0;i<ls.size();i++) 
			//			{
			//				System.out.println("for loop grid list are "+ls.get(i).getText());
			//			}
			String actComp = ls.get(2).getText(); String actSp = ls.get(4).getText();
			String actStatus = ls.get(5).getText();
			if (compName.equalsIgnoreCase(actComp)&&salesP.equalsIgnoreCase(actSp)&&status1.equalsIgnoreCase(actStatus)) {
				res = true;
				Object status[] = {"REPAIRS_013_VerifyFilters", actComp+" "+actSp+" "+actStatus, compName+" "+salesP+" "+status1, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			} else {
				res = false;
				Object status[] = {"REPAIRS_013_VerifyFilters", actComp+" "+actSp+" "+actStatus, compName+" "+salesP+" "+status1, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
				quotes.values(status);
			}
		}
		driver.findElement(By.xpath("//*[@title='Reset Filters ']")).click();
		return res;
	}
	public boolean verifyFilterStateMaintanance() throws Exception 
	{
		boolean res = false;
		this.filters("123 E Doty Corporation", "Dallas House", "Check In Pending");
		WebElement ele = driver.findElement(By.xpath("//*[@title='Filters Applied']"));
		driver.navigate().refresh();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-center-cols-container']")));
		ele = driver.findElement(By.xpath("//*[@title='Filters Applied']"));
		if (ele.isDisplayed()) {
			res = true;
			Object status[] = {"REPAIRS_014_VerifyFilterStateMaintanance", "filters  are stabled after refresh the page", "", "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_014_VerifyFilterStateMaintanance", "filters  are not stabled after refresh the page", "", "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
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
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@placeholder='Search By Part Number']")).click();
		act.sendKeys(Keys.TAB).build().perform();act.sendKeys(Keys.TAB).build().perform();act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(Keys.SPACE).build().perform();
		act.sendKeys(Keys.TAB).build().perform();act.sendKeys(Keys.TAB).build().perform();act.sendKeys(Keys.TAB).build().perform();
		act.sendKeys(Keys.SPACE).build().perform();
		Thread.sleep(1000);
		price.clickButton("Add Selected 2 Parts");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(1300);
		//Assign Location For First Item
		driver.findElement(By.xpath("//*[contains(@class,'hides')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='storage']")));
		driver.findElement(By.name("storage")).sendKeys("New York");
		WebElement editIcon = driver.findElement(By.xpath("//*[@class='quantity-parent']")).findElement(By.tagName("svg"));
		act.moveToElement(editIcon).perform();
		editIcon.click();
		driver.findElement(By.name("serial_no")).sendKeys(java.time.LocalTime.now().toString().replace("-","").replace(":", "").replace(".", ""));
		driver.findElement(By.xpath("//*[@title='Save Changes']")).click();
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();
		Thread.sleep(2000);
		//Assign Location For Second Item
		driver.findElement(By.xpath("//*[contains(@class,'hides')]")).click();
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
		driver.findElement(By.xpath("//*[contains(@class,'hides')]")).click();
		Thread.sleep(1500);
		driver.findElement(By.id("react-select-6-input")).sendKeys(Keys.ARROW_UP);
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		price.clickButton("Assign");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2000);
		//Assign Technician for Second Item
		driver.findElement(By.xpath("//*[contains(@class,'hides')]")).click();
		Thread.sleep(1500);
		driver.findElement(By.id("react-select-7-input")).sendKeys(Keys.ARROW_UP);
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		price.clickButton("Assign");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2000);
		//Evaluate First Item
		driver.findElement(By.xpath("//*[contains(@class,'hides')]")).click();
		Thread.sleep(1500);
		driver.findElement(By.id("react-select-8-input")).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(By.xpath("//*[contains(@class,'css-4mp3pp-menu')]")).click();
		driver.findElement(By.name("estimated_hrs")).sendKeys("23");
		driver.findElement(By.name("price")).sendKeys("198");
		price.clickButton("Update Evaluation");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(2000);
		//Evaluate Second Item
		driver.findElement(By.xpath("//*[contains(@class,'hides')]")).click();
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
		Thread.sleep(2000);
		driver.findElements(By.xpath("//*[@class = 'check_box']")).get(0).findElement(By.tagName("label")).click();
		Thread.sleep(1400);
		//Go to Quotes Module	
		driver.findElement(By.xpath("//*[contains(@label,'2023')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(1400);
		//Submit for Internal Approval
		price.clickButton("Submit for internal approval");
		Thread.sleep(2000);
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div/div/div[3]/div[2]/button"), "Submit for internal approval"));
		Thread.sleep(1700);
		//Quote Approve
		price.clickButton("Approve");
		Thread.sleep(1200);
		this.toastContainer("Approve");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button"), "Approve"));
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).findElement(By.tagName("button")).click();
		driver.findElement(By.xpath("//*[@class='link-icon-text']")).click();
		Thread.sleep(2000);
		//Quote Won
		this.wonOrLostButton("Won");
		Thread.sleep(1200);
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button[1]"), "Won"));
		Thread.sleep(1400);
		//Back to Repair Module
		driver.findElement(By.xpath("//*[contains(@label,'0')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
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
			Object status[] = {"REPAIRS_015_VerifyAssignToQCIsDisplayedOrNotWhenQuoteStatusHasWon", "act item status is "+itemStatus+"exp item status is "+expItemStatus,
					"act RMA status is "+rMAStatus+"exp RMA status is "+expRMAStatus, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_015_VerifyAssignToQCIsDisplayedOrNotWhenQuoteStatusHasWon", "act item status is "+itemStatus+"exp item status is "+expItemStatus,
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
		Actions act = new Actions(driver);
		for(int i=0;i<driver.findElements(By.xpath("//*[contains(@title,'2023')]")).size(); i++) 
		{
			System.out.println("count of quote related IDs "+driver.findElements(By.xpath("//*[contains(@title,'2023')]")).size());
		}
		act.moveToElement(driver.findElements(By.xpath("//*[contains(@title,'2023')]")).get(2)).build().perform();
		driver.findElements(By.xpath("//*[contains(@title,'2023')]")).get(2).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(1400);
		//Submit for Internal Approval
		price.clickButton("Submit for internal approval");
		Thread.sleep(2000);
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div/div/div[3]/div[2]/button"), "Submit for internal approval"));
		Thread.sleep(1700);
		//Quote Approve
		price.clickButton("Approve");
		Thread.sleep(1200);
		this.toastContainer("Approve");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button"), "Approve"));
		Thread.sleep(1900);
		price.clickButton("Revise Quote");
		Thread.sleep(1200);
		this.toastContainer("Proceed");
		Thread.sleep(2000);
		//Submit for Internal Approval
		price.clickButton("Submit for internal approval");
		Thread.sleep(2000);
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div/div/div[3]/div[2]/button"), "Submit for internal approval"));
		Thread.sleep(1700);
		//Quote Approve
		price.clickButton("Approve");
		Thread.sleep(1200);
		this.toastContainer("Approve");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button"), "Approve"));
		Thread.sleep(1500);
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
		driver.findElement(By.xpath("//*[contains(@label,'0')]")).click();
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
			Object status[] = {"REPAIRS_016_VerifyAssignToQC_WhenReviseTheQuote", "act item status is "+itemStatus+"exp item status is "+expItemStatus,
					"act RMA status is "+rMAStatus+"exp RMA status is "+expRMAStatus, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else{
			res = false;
			Object status[] = {"REPAIRS_016_VerifyAssignToQC_WhenReviseTheQuote", "act item status is "+itemStatus+"exp item status is "+expItemStatus,
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
