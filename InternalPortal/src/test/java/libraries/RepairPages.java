package libraries;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import commonUtils.App;

public class RepairPages extends App
{
	WebDriverWait wait;
	QuotePages quotes = new QuotePages();
	public void repairPage() 
	{
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div/div[4]")).click();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
	}
	public void createRMA() throws Exception 
	{
		this.repairPage();
		driver.findElement(By.xpath("//*[@class='Button-Icon-Display']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("async-select-example")).sendKeys("Integrated Systems Corporation");
		Thread.sleep(2300);
		quotes.selectDropDown("Integrated Systems Corporation");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1300);
		driver.findElement(By.id("react-select-3-input")).sendKeys("Standard");
		quotes.selectDropDown("Standard");
		Thread.sleep(2300);
		driver.findElement(By.id("react-select-4-input")).sendKeys("Lori Underhill");
		Thread.sleep(4500);
		quotes.selectDropDown("Lori Underhill");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
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
		String actText = driver.findElement(By.id("repair-items")).findElement(By.tagName("h4")).getText();
		driver.findElement(By.id("repair-items")).findElement(By.tagName("button")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='side-drawer open']")));
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.id("tab-2")).click();
		driver.findElement(By.id("async-select-example")).sendKeys("BACO CONTROLS INC");
		Thread.sleep(1000);
		quotes.selectDropDown("ABB");
		driver.findElement(By.name("custom_part_items.0.part_number")).sendKeys("PN12345");
		driver.findElement(By.name("custom_part_items.0.serial_number")).sendKeys("SN12346");
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
		List<WebElement> btn=  driver.findElement(By.xpath("//*[@id='repair-items']")).findElements(By.tagName("button"));
		btn.get(1).click();
		driver.findElement(By.xpath("//*[contains(@id,'ds--dropdown--')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("storage")).sendKeys("New York");
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();
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
	public void evaluateItem() throws Exception 	
	{
		this.assignLocation();
		List<WebElement> btn=  driver.findElement(By.xpath("//*[@id='repair-items']")).findElements(By.tagName("button"));
		btn.get(1).click();
		driver.findElement(By.xpath("//*[contains(@id,'ds--dropdown--')]")).click();
		Thread.sleep(1000);
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
		String expText = "EVALUATION";
		String actText = driver.findElement(By.xpath("//*[@class='quote-num-and-status']")).getText();
		if (actText.toLowerCase().contains(expText.toLowerCase())) {
			res = true;
			Object status[] = {"REPAIRS_005_VerifyEvaluateItem", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_005_VerifyEvaluateItem", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public void addRepairableItemToQuote(String data) throws Exception 
	{
		this.evaluateItem();
		driver.findElement(By.xpath("//*[@id='repair-items']")).findElement(By.xpath("//*[@class='cards-btns-group']")).click();
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
			Object status[] = {"REPAIRS_006_VerifyAddRepairableItemToQuote", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_006_VerifyAddRepairableItemToQuote", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public void createQuoteFromRepair() throws Exception 
	{
		this.addRepairableItemToQuote("Accept");
		Thread.sleep(1500);
		List<WebElement> btns = driver.findElements(By.tagName("h4"));
		for(int i=0;i<btns.size();i++)
		{
			String qId = btns.get(i).getText();
			String[] ids = qId.split(" ",3);
			if(qId.toLowerCase().contains("Quote ID".toLowerCase())) 
			{
				btns.get(i).click();
				System.out.println("Quote Id is "+qId);
				break;
			}
		}
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
			Object status[] = {"REPAIRS_007_VerifyCreateQuoteFromRepair", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_007_VerifyCreateQuoteFromRepair", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
	}
	public void assignToQC() throws Exception 
	{
		this.createQuoteFromRepair();
		WebElement rfq = driver.findElement(By.xpath("//*[@title='RFQ Received Date']"));
		WebElement qReqBy = driver.findElement(By.xpath("//*[@title='Quote Requested By']"));
		Actions act = new Actions(driver);
		act.moveToElement(rfq).build().perform();
		Thread.sleep(1000);
		List<WebElement> edits = driver.findElement(By.xpath("//*[@id='repair-info-id']")).findElements(By.className("pi-label-edit-icon"));
		edits.get(0).click();
		driver.findElement(By.xpath("//*[@id='repair-info-id']")).findElement(By.tagName("button")).click();
		driver.findElement(By.xpath("//*[@title='Save Changes']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("/html/body/div/div/div[3]/div[2]/button")).click();
		Thread.sleep(2000);
		this.toastContainer("Proceed");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("/html/body/div/div/div[3]/div[2]/button"), "Submit for internal approval"));
		Thread.sleep(1700);
		driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/button")).click();
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
		List<WebElement> btns = driver.findElements(By.tagName("h4"));
		for(int i=0;i<btns.size();i++)
		{
			String qId = btns.get(i).getText();
			String[] ids = qId.split(" ",3);
			if(qId.toLowerCase().contains("Repair ID".toLowerCase())) 
			{
				btns.get(i).click();
				System.out.println("repair id is "+qId);
				break;
			}
		}
		//		System.exit(0);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='repair-items']")));
		Thread.sleep(1500);
		List<WebElement> btn=  driver.findElement(By.xpath("//*[@id='repair-items']")).findElements(By.tagName("button"));
		btn.get(0).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@class='link-icon-text']")).click();
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
			Object status[] = {"REPAIRS_008_VerifyAssignToQC", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_008_VerifyAssignToQC", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
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
			Object status[] = {"REPAIRS_009_VerifyQCCheckList", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_009_VerifyQCCheckList", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
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
			Object status[] = {"REPAIRS_010_VerifyQCCheckListStatusAsFail", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_010_VerifyQCCheckListStatusAsFail", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
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
			Object status[] = {"fileUpload", actText, expText, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"fileUpload", actText, expText, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
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
		List<WebElement> txts = driver.findElement(By.xpath("//*[@class='ag-center-cols-container']")).findElements(By.xpath("//*[@row-index='0']"));
//		System.out.println("comp name is "+txts.size());
		List<WebElement> ls = txts.get(1).findElements(By.xpath("//*[contains(@class,'ag-cell ag-cell')]"));
		String actComp = ls.get(2).getText(); String actSp = ls.get(5).getText();
		String actStatus = ls.get(6).getText();
		if (compName.equalsIgnoreCase(actComp)&&salesP.equalsIgnoreCase(actSp)&&status1.equalsIgnoreCase(actStatus)) {
			res = true;
			Object status[] = {"REPAIRS_011_VerifyFilters", actComp+" "+actSp+" "+actStatus, compName+" "+salesP+" "+status1, "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_011_VerifyFilters", actComp+" "+actSp+" "+actStatus, compName+" "+salesP+" "+status1, "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		ele = driver.findElement(By.xpath("//*[@title='Filters Applied']"));
		if (ele.isDisplayed()) {
			res = true;
			Object status[] = {"REPAIRS_012_VerifyFilterStateMaintanance", "filters  are stabled after refresh the page", "", "RepairsPage", "Passed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		} else {
			res = false;
			Object status[] = {"REPAIRS_013_VerifyFilterStateMaintanance", "filters  are not stabled after refresh the page", "", "RepairsPage", "Failed", java.time.LocalDate.now().toString()};
			quotes.values(status);
		}
		return res;
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
