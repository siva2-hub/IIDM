package libraries;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import commonUtils.App;

public class PricingPages extends App
{
	WebDriverWait wait;
	QuotePages qp = new QuotePages();
	public void pricingPage(String linkName) throws Exception 
	{
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div/div[3]/button")).click();
		Thread.sleep(1300);
		List<WebElement> pricingMenu = driver.findElements(By.xpath("//*[@role='menuitem']"));
		for(int i=0;i<pricingMenu.size();i++) 
		{
			if (pricingMenu.get(i).getText().equals(linkName)) 
			{
				Thread.sleep(2000);
				pricingMenu.get(i).click();
				Thread.sleep(1500);
				
				break;
			}
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
	}
	public String addProduct(String stockCode, String discountCode, String listPrice) throws Exception
	{
		this.pricingPage("Pricing");
		Thread.sleep(2000);
		this.addImportExporBtns("Add");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("stock_code")));
		Thread.sleep(2000);
		driver.findElement(By.name("stock_code")).sendKeys(stockCode);
		driver.findElement(By.id("react-select-3-input")).sendKeys(discountCode);
		Thread.sleep(1200);
		qp.selectDropDown(discountCode);
		driver.findElement(By.name("list_price")).sendKeys(listPrice);
		Thread.sleep(1200);
		this.addButton("Add Product");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-center-cols-container']")));
		return stockCode;
	}
	public boolean verifyAddProduct(String discountCode, String listPrice) throws Exception
	{
		boolean res = false;
		String stockCode = this.getTime();
		String expStockCode = this.addProduct(stockCode, discountCode, listPrice);
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[@class='css-wxvfrp']")).sendKeys(stockCode);
		Thread.sleep(4000);
		List<WebElement> txts = driver.findElement(By.xpath("//*[@class='ag-center-cols-container']")).findElements(By.xpath("//*[@row-index='0']"));
		//		System.out.println("comp name is "+txts.size());
		List<WebElement> ls = txts.get(1).findElements(By.xpath("//*[contains(@class,'ag-cell ag-cell')]"));
		String actStockCode = ls.get(0).getText();
		if (expStockCode.equalsIgnoreCase(actStockCode)) {
			res = true;
			Object status[] = {"PRICING_001_VerifyAddProduct", actStockCode, expStockCode, "PricingPage", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {"PRICING_001_VerifyAddProduct", actStockCode, expStockCode, "PricingPage", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
		}
		return res;
	}
	public String updateProduct() throws Exception 
	{
		this.pricingPage("Pricing");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'edit-icon-cell')]")));
		Thread.sleep(1300);
		driver.findElement(By.xpath("//*[contains(@class,'edit-icon-cell')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("list_price")));
		String text = driver.findElement(By.name("list_price")).getAttribute("value");
		double lp=Double.parseDouble(text);
		for(int i=0;i<text.length();i++) 
		{
			driver.findElement(By.name("list_price")).sendKeys(Keys.BACK_SPACE);
		}
		driver.findElement(By.name("list_price")).sendKeys(String.valueOf(lp+10));
		Thread.sleep(1300);
		this.addButton("Update Product");
		return text;
	}
	public boolean verifyUpdateProduct() throws Exception 
	{
		boolean res = false;
		String beforeEdit = this.updateProduct();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[contains(@class,'edit-icon-cell')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("list_price")));
		Thread.sleep(2500);
		String afterEdit = driver.findElement(By.name("list_price")).getAttribute("value");
		this.closeIcon();
		if (!beforeEdit.equalsIgnoreCase(afterEdit)) {
			res = true;
			Object status[] = {"PRICING_002_VerifyUpdateProduct", "before update List Price is "+beforeEdit, "after update List Price is "+afterEdit, "PricingPage", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {"PRICING_002_VerifyUpdateProduct", "before update List Price is "+beforeEdit, "after update List Price is "+afterEdit, "PricingPage", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
		}
		return res;
	}
	public String addDiscountCode() throws Exception 
	{
		this.pricingPage("Discount Codes");
		Thread.sleep(2000);
		String dc = "DC"+this.getTime();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='Button-Icon-Display']")));
		this.addImportExporBtns("Add");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='discount_code']")));
		driver.findElement(By.name("discount_code")).sendKeys(dc);
		driver.findElement(By.id("react-select-3-input")).sendKeys(Keys.ARROW_UP.ARROW_RIGHT);
		driver.findElement(By.id("react-select-3-input")).sendKeys(Keys.ENTER);
		driver.findElement(By.id("react-select-4-input")).sendKeys("8");
		driver.findElement(By.id("react-select-4-input")).sendKeys(Keys.ENTER);
		driver.findElement(By.id("react-select-5-input")).sendKeys("11");
		qp.selectDropDown("11");
		driver.findElement(By.xpath("//*[@placeholder='Our Price']")).sendKeys("0.25");
		driver.findElement(By.xpath("//*[@placeholder='MRO']")).sendKeys("0.50");
		driver.findElement(By.xpath("//*[@placeholder='OEM']")).sendKeys("0.75");
		driver.findElement(By.xpath("//*[@placeholder='RS']")).sendKeys("0.80");
		Thread.sleep(1500);
		this.addButton("Add Discount Code");
		Thread.sleep(7000);
		return dc;
	}
	
	public boolean verifyAddDiscountCode() throws Exception 
	{
		String dc = this.addDiscountCode();boolean res = false;
		System.out.println("dc was added.......!");
		driver.findElement(By.xpath("//*[@class='quote-search-width']")).findElement(By.xpath("//*[@class='css-wxvfrp']")).sendKeys(dc);
		Thread.sleep(4000);
		List<WebElement> txts = driver.findElement(By.xpath("//*[@class='ag-center-cols-container']")).findElements(By.xpath("//*[@row-index='0']"));
		//		System.out.println("comp name is "+txts.size());
		List<WebElement> ls = txts.get(1).findElements(By.xpath("//*[contains(@class,'ag-cell ag-cell')]"));
		String actStockCode = ls.get(0).getText();
		if (dc.equalsIgnoreCase(actStockCode)) {
			res = true;
			Object status[] = {"PRICING_003_VerifyAddDiscountCode", actStockCode, dc, "PricingPage", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {"PRICING_003_VerifyAddDiscountCode", actStockCode, dc, "PricingPage", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
		}
		return res;
	}
	public String updateDiscountCode() throws Exception 
	{
		this.pricingPage("Discount Codes");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'edit-icon-cell')]")));
		Thread.sleep(1300);
		driver.findElement(By.xpath("//*[contains(@class,'edit-icon-cell')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Our Price']")));
		WebElement ourPrice = driver.findElement(By.xpath("//*[@placeholder='Our Price']"));
		String text = ourPrice.getAttribute("value");
		float op=Float.parseFloat(text); 
		for(int i=0;i<text.length();i++) 
		{
			ourPrice.sendKeys(Keys.BACK_SPACE);
		}
		ourPrice.sendKeys(String.valueOf(op+1));
		Thread.sleep(1400);
		this.addButton("Update Discount Code");
		return text;
	}
	public boolean verifyUpdateDiscountCode() throws Exception 
	{
		String beforeUpdate = this.updateDiscountCode();boolean res = false;
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[contains(@class,'edit-icon-cell')]")).click();
		Thread.sleep(2500);
		String afterUpdate = driver.findElement(By.xpath("//*[@placeholder='Our Price']")).getAttribute("value");
		this.closeIcon();
		if (!beforeUpdate.equalsIgnoreCase(afterUpdate)) {
			res = true;
			Object status[] = {"PRICING_004_VerifyUpdateDiscountCode", "before update Our Price value is "+beforeUpdate,
					"after update Our Price value is "+afterUpdate, "PricingPage", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {"PRICING_004_VerifyUpdateDiscountCode", "before update Our Price value is "+beforeUpdate, 
					"after update Our Price value is "+afterUpdate, "PricingPage", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
		}
		return res;
	}
	public boolean importFile() throws Exception 
	{
		String dcFile = "/home/enterpi/Downloads/apr2023PricingFile/Saginaw DiscountCodeImport2023.csv";
		String pricingFile = "/home/enterpi/Downloads/apr2023PricingFile/Saginaw PriceList-Import-2023.csv";
		this.pricingPage("Pricing");
		driver.findElement(By.className("sideList-Search")).findElement(By.className("css-wxvfrp")).sendKeys("SAGI001");
		Thread.sleep(3000);
		WebElement supName1 = driver.findElement(By.className("left-menu")).findElement(By.xpath("//*[@class='active menu-item-single']"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@src,'editicon')]")));
		Thread.sleep(2400);
		supName1 = driver.findElement(By.className("left-menu")).findElement(By.xpath("//*[@class='active menu-item-single']"));
		String supName = supName1.getText();
		supName1.click();
		Thread.sleep(10000);
		this.addImportExporBtns("Import");
		Thread.sleep(2300);
		WebElement vSel = driver.findElement(By.id("react-select-3-input"));
		vSel.sendKeys(supName);
		vSel.sendKeys(Keys.ENTER);
		List<WebElement> files = driver.findElements(By.xpath("//*[contains(@class,'file-import-box')]"));
		List<WebElement> fs = driver.findElements(By.xpath("//input[@type='file']"));
		System.out.println("count files type is "+files.size());
		files.get(0).findElement(By.name("checkbox")).click();
		Thread.sleep(2000);
		fs.get(0).sendKeys(dcFile);
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		files.get(1).findElement(By.name("checkbox")).click();
		Thread.sleep(3000);
		fs.get(1).sendKeys(pricingFile);
		Thread.sleep(5000);
		this.addButton("Import");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(2000);
		String summaryText = driver.findElement(By.xpath("//*[contains(@class,'Upload-files')]")).getText();
		System.out.println("summary text is "+summaryText);
		TakesScreenshot ts = (TakesScreenshot)driver;
		File SrcFile = ts.getScreenshotAs(OutputType.FILE);
		File destFile = new File("summary_image.png");
		FileUtils.copyFile(SrcFile, destFile);
		String expText = "Imported Data Successfully";
		boolean res = false;
		if (summaryText.equalsIgnoreCase("Summary")) {
			driver.findElement(By.id("react-select-5-input")).sendKeys("5");
			driver.findElement(By.id("react-select-5-input")).sendKeys(Keys.ENTER);
			driver.findElement(By.id("react-select-6-input")).sendKeys("10");
			driver.findElement(By.id("react-select-6-input")).sendKeys(Keys.ENTER);
			driver.findElement(By.xpath("//*[@role='dialog']")).findElement(By.tagName("button")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
			Thread.sleep(2000);
			String actText = driver.findElement(By.xpath("//*[@role='dialog']")).findElement(By.tagName("h3")).getText();
			if (actText.equals(expText)) {
				res = true;
				Object status[] = {"PRICING_005_VerifyImport", actText, expText, "PricingPage", "Passed", java.time.LocalDate.now().toString()};
				qp.values(status);
				this.closeIcon();
			} else {
				res = false;
				Object status[] = {"PRICING_005_VerifyImport", actText, expText, "PricingPage", "Failed", java.time.LocalDate.now().toString()};
				qp.values(status);
				this.closeIcon();
			}
		} else {
			res = false;
			String errorMsg = driver.findElement(By.xpath("//*[contains(@class,'error-msg')]")).getText();
			Object status[] = {"PRICING_005_VerifyImport", summaryText, errorMsg, "PricingPage", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
			this.closeIcon();
		}
		return res;
	}
	public void specialPricing() throws Exception{
		this.pricingPage("Non Standard Pricing");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		this.clickButton("Configure");
		Thread.sleep(2000);
		driver.findElement(By.id("async-select-example")).sendKeys("ABB");
		Thread.sleep(2000);
		qp.selectDropDown("ABB");
		WebElement dtRange = driver.findElement(By.name("date_range"));
		Thread.sleep(2000);
		dtRange.sendKeys(Keys.ENTER);
		dtRange.sendKeys(Keys.ARROW_DOWN);
		dtRange.sendKeys(Keys.ENTER);
		
	}
	public void clickButton(String btnName) {
		List<WebElement> btns = driver.findElements(By.tagName("button"));
		for(int i=0;i<btns.size();i++) {
			if(btns.get(i).getText().equals(btnName)) {
				btns.get(i).click();
				break;
			}
		}
	}
	public void addButton(String btnName) {
		List<WebElement> btns = driver.findElement(By.tagName("section")).findElements(By.tagName("button"));
		for(int i=0;i<btns.size();i++) 
		{
			if (btns.get(i).getText().equals(btnName)) 
			{
				btns.get(i).click();
				break;
			}
		}
	}
	public void addImportExporBtns(String btnName) 
	{
		List<WebElement> btns = driver.findElement(By.xpath("//*[@class='Button-Icon-Display']")).findElements(By.className("link-icon-text"));
		for(int i=0;i<btns.size();i++) 
		{
			if (btns.get(i).getText().equals(btnName)) 
			{
				btns.get(i).click();
				break;
			}
		}
	}
	public String getTime() {
		LocalTime time = LocalTime.now();
		System.out.println(String.valueOf(time));
		String t1 =String.valueOf(time).replace(":", "");
		String t2 = t1.replace(".", "");
		
		System.out.println("Current time: "+t2);
		return t2;
	}
	public void closeIcon() 
	{
		driver.findElement(By.xpath("//*[@title='close']")).click();
	}
}
