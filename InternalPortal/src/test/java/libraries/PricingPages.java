package libraries;

import java.io.File;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import commonUtils.App;

public class PricingPages extends App
{
	WebDriverWait wait;
	QuotePages qp = new QuotePages();
	RepairPages rp;
	public void pricingPage(String linkName) throws Exception 
	{
		rp = new RepairPages();
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
	public String addProduct(String stockCode, String discountCode, String listPrice, String productClass) throws Exception
	{
		this.pricingPage("Pricing");
		Thread.sleep(2000);
//		this.addImportExporBtns("Add");
		driver.findElement(By.className("button-icon-text")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("stock_code")));
		Thread.sleep(2000);
		driver.findElement(By.name("stock_code")).sendKeys(stockCode);
		driver.findElement(By.id("react-select-3-input")).sendKeys(discountCode);
		Thread.sleep(1200);
		qp.selectDropDown(discountCode);
		driver.findElement(By.name("list_price")).sendKeys(listPrice);
		Thread.sleep(1200);
		driver.findElement(By.id("react-select-4-input")).sendKeys(productClass);
		Thread.sleep(1200);
		qp.selectDropDown(productClass);
		this.addButton("Add Product");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-center-cols-container']")));
		return stockCode;
	}
	public boolean verifyAddProduct(String discountCode, String listPrice, String productClass) throws Exception
	{
		boolean res = false;
		String stockCode = this.getTime();
		String expStockCode = this.addProduct(stockCode, discountCode, listPrice, productClass);
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[contains(@placeholder,'Stock Code / Description')]")).sendKeys(stockCode);
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
//		this.addImportExporBtns("Add");
		driver.findElement(By.className("button-icon-text")).click();
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
		driver.findElement(By.xpath("//*[@class='quote-search-width']")).findElement(By.xpath("//*[@placeholder='Search By Discount Code']")).sendKeys(dc);
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
		driver.findElement(By.className("sideList-Search")).findElement(By.xpath("//*[@placeholder='Search']")).sendKeys("SAGI001");
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
		WebElement vSel = driver.findElement(By.id("async-select-example"));
		vSel.sendKeys(supName);
		Thread.sleep(2000);
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
		this.takesScreenShot("summary_page.png");
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
	public Object[] specialPricing(String type, int typeVal, String purchaseDiscount, String fprice, String buyPrice) throws Exception
	{
		String compName = "1155 Distributor Partners-Dallas, LLC";
		String item = "ZZ52BQ7";
		this.pricingPage("Non Standard Pricing");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		this.clickButton("Configure");
		Thread.sleep(2000);
		List<WebElement> inputs = driver.findElements(By.id("async-select-example"));
		inputs.get(0).sendKeys(compName);
		System.out.println("count of unput tags are "+inputs.size());
		Thread.sleep(2000);
		qp.selectDropDown(compName);
		WebElement dtRange = driver.findElement(By.name("date_range"));
		Thread.sleep(2000);
		dtRange.sendKeys(Keys.ENTER);
		dtRange.sendKeys(Keys.ARROW_DOWN);
		dtRange.sendKeys(Keys.ENTER);
		driver.findElement(By.name("quote_number")).sendKeys("TestNewQN9090");
		driver.findElements(By.id("async-select-example")).get(1).sendKeys("BACO CONTROLS INC");
		Thread.sleep(1500);
		qp.selectDropDown("BACO CONTROLS INC");
//		System.exit(0);
		driver.findElement(By.id("react-select-10-input")).sendKeys(type);
		Thread.sleep(1500);
		qp.selectDropDown(type);
		driver.findElement(By.name("pricing_rules.0.type_value")).sendKeys(String.valueOf(typeVal));
		driver.findElement(By.id("react-select-9-input")).sendKeys("Specific Item");
		Thread.sleep(1500);
		qp.selectDropDown("Specific Item");
		Thread.sleep(1500);
		driver.findElements(By.id("async-select-example")).get(2).sendKeys(item);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1500);
		qp.selectDropDown(item);
		driver.findElement(By.xpath("//*[@placeholder='Purchase Discount']")).sendKeys(purchaseDiscount);
		driver.findElement(By.xpath("//*[@placeholder='Buy Price']")).sendKeys(buyPrice);
		driver.findElement(By.xpath("//*[@placeholder='Fixed Sales Price']")).sendKeys(fprice);
		String orgName = driver.findElement(By.xpath("//*[contains(@class,'react-select__single-value')]")).getText();
		this.clickButton("Preview Items");
		Thread.sleep(1300);
		rp.toastContainer("Override");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1500);
		List<WebElement> txts = driver.findElement(By.xpath("//*[@class='ag-center-cols-container']")).findElements(By.xpath("//*[@row-index='0']"));
		List<WebElement> ls = txts.get(0).findElements(By.xpath("//*[contains(@class,'ag-cell ag-cell')]"));
		double actBuyPrice = 0.0;
		if(purchaseDiscount.equals("") && buyPrice.equals("")) {
			actBuyPrice = 0.0;
		}else {
			actBuyPrice = Double.parseDouble(ls.get(4).getText().replace("$", ""));
			
		}
		//System.out.println("actual buy price "+actBuyPrice);
		Double actSellPrice =  Double.parseDouble(ls.get(10).getText().replace("$", ""));
		Double actFixedPrice = 0.0;
		if(ls.get(9).getText().equals("")) {
			actFixedPrice = 0.0;
		}else {
			actFixedPrice = Double.parseDouble(ls.get(9).getText().replace("$", ""));
		}
		System.out.println("actual buy price "+actBuyPrice);
		System.out.println("actual sell price "+actSellPrice);
		Thread.sleep(2500);
		driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div/div[1]/div/div[3]/button")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1500);
		this.pricingPage("Pricing");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		driver.findElement(By.xpath("//*[contains(@placeholder,'Stock Code / Description')]")).sendKeys(item);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(6000);
		txts = driver.findElement(By.xpath("//*[@class='ag-center-cols-container']")).findElements(By.xpath("//*[@row-index='0']"));
		ls = txts.get(1).findElements(By.xpath("//*[contains(@class,'ag-cell ag-cell')]"));
		String listPrice = ls.get(2).getText();
		String ourPrice = ls.get(8).getText();
		String op1 = ourPrice.replace("$", "");
		Double op = Double.parseDouble(op1);
		String lp1 = listPrice.replace("$", "");
		Double lp = Double.parseDouble(lp1);
		DecimalFormat decfor = new DecimalFormat("0.00");
//		String ebp = decfor.format(lp-((lp*Integer.parseInt(purchaseDiscount))/100));
		Double expBuyPrice= 0.0;
		if (buyPrice.equals("")) {
			 
		} else {

			 expBuyPrice = Double.parseDouble(buyPrice);
		}
		double sellPrice = 0.0;
		double bPrice = 0.0;
		if (type.equals("Discount")) {
			String sp = decfor.format(lp-(lp*typeVal/100));
			sellPrice = Double.parseDouble(sp);
		}if(type.equals("Markup")) {
			if(buyPrice.equals("")&&purchaseDiscount.equals("")) {
				
				String sp = decfor.format(op+(op*typeVal/100));
				sellPrice = Double.parseDouble(sp);
				
			}if(buyPrice.equals("") && !purchaseDiscount.equals("")) 
			{
				double val1 = Integer.parseInt(purchaseDiscount);
				bPrice = (lp-(lp*val1/100));
				expBuyPrice = bPrice;
				double val2 = (bPrice*typeVal/100);
				double val3 = val2+bPrice;
				String sp = decfor.format(val3);
				sellPrice = Double.parseDouble(sp);
			}if(!buyPrice.equals("") && !purchaseDiscount.equals("")) 
			{
				double val1 = Integer.parseInt(buyPrice);
				double val2 = (val1*typeVal/100);
				double val3 = val1+val2;
				String sp = decfor.format(val3);
				sellPrice = Double.parseDouble(sp);
			}
		}
		System.out.println("expected buy price "+expBuyPrice);
		System.out.println("expected sell price "+sellPrice);
		Object data[] = {actBuyPrice, expBuyPrice, actSellPrice, sellPrice, actFixedPrice, orgName, lp, item};
		return data;
	}
	public boolean verifyBuyPrice_SellPrice_InSpecialPricing(String type, int typeValue, String purchaseDiscount, String fprice, String buyPrice, int count) throws Exception
	{
		Object vals[] = this.specialPricing(type, typeValue, purchaseDiscount, fprice, buyPrice);
		Object abp = vals[0];
		Object ebp = vals[1];
		Object asp = vals[2];
		Object esp = vals[3];
		boolean res = false;String tcName= "";
		if(count==1) {
			tcName = "PRICING_006_VerifyBuyPrice_SellPrice_InSpecialPricing_BuyPrice_Null_PurchaseDiscount_Not_Null";
		}if(count==2) {
			tcName = "PRICING_007_VerifyBuyPrice_SellPrice_InSpecialPricing_BuyPrice_PurchaseDiscount_Null";
		}if(count==3) {
			tcName = "PRICING_008_VerifyBuyPrice_SellPrice_InSpecialPricing_BuyPrice_PurchaseDiscount_Not_Null";
		}if(count==4) {
			tcName = "PRICING_009_VerifyBuyPrice_SellPrice_InSpecialPricing_Type_As_Discount";
		}
		if (abp.equals(abp) && asp.equals(esp)) {
			res = true;
			Object status[] = {tcName, "actual buy price "+abp+" expected buy price "+ebp,
					"actual sell price "+asp+" expected sell price "+esp, "NonStandardPricing", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {tcName, "actual buy price "+abp+" expected buy price "+ebp,
					"actual sell price "+asp+" expected sell price "+esp, "NonStandardPricing", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
		}
		return res;
	}
	public Object[] addSPItemsToQuotewithAccountType(String type, int typeValue, String purchaseDiscount, String fprice, String buyPrice) throws Exception
	{
		Object vals[] = this.specialPricing(type, typeValue, purchaseDiscount, fprice, buyPrice);
		Object actSellPrice = vals[2];
		Object actFixedPrice = vals[4];
		String orgName = vals[5].toString();
		Object listPrice = vals[6];
		String item = vals[7].toString();
		this.clickButton("Organizations");
		Thread.sleep(1500);
		for(int i=0;i<driver.findElements(By.className("link-icon-text")).size();i++) {
			if(driver.findElements(By.className("link-icon-text")).get(i).getText().equals("Organizations")) {
				driver.findElements(By.className("link-icon-text")).get(i).click();
			}
		}
//		driver.findElements(By.className("link-icon-text")).get(3).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(2500);
		String searchText = driver.findElement(By.xpath("//*[@placeholder='Name / Company Name / Account Number / Owner']")).getAttribute("value");
		for(int i=0;i<searchText.length();i++) {
			driver.findElement(By.xpath("//*[@placeholder='Name / Company Name / Account Number / Owner']")).sendKeys(Keys.BACK_SPACE);
		}
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@placeholder='Name / Company Name / Account Number / Owner']")).sendKeys(orgName);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(6600);
		List<WebElement> txts = driver.findElement(By.xpath("//*[@class='ag-center-cols-container']")).findElements(By.xpath("//*[@row-index='0']"));
		List<WebElement> ls = txts.get(0).findElements(By.xpath("//*[contains(@class,'ag-cell ag-cell')]"));
		String at  = ls.get(4).getText();
		System.out.println("account type "+at);
		driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/div[1]/div/div[2]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@placeholder,'Search By Name')]")));
		Thread.sleep(2500);
		if(driver.findElement(By.xpath("//*[contains(@placeholder,'Search By Name')]")).getAttribute("value")!="") 
		{	
			driver.findElement(By.xpath("//*[@class='Cross-svg close-icon-container']")).click();
		} 
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[contains(@placeholder,'Search By Name')]")).sendKeys(at);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(3600);
		txts = driver.findElement(By.xpath("//*[@class='ag-center-cols-container']")).findElements(By.xpath("//*[@row-index='0']"));
		ls = txts.get(0).findElements(By.xpath("//*[contains(@class,'ag-cell ag-cell')]"));
		String atm  = ls.get(3).getText();
		this.pricingPage("Pricing");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		driver.findElement(By.xpath("//*[contains(@placeholder,'Stock Code / Description')]")).sendKeys(item);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(6000);
		this.clickColoumns();
		txts = driver.findElement(By.xpath("//*[@class='ag-center-cols-container']")).findElements(By.xpath("//*[@row-index='0']"));
		ls = txts.get(1).findElements(By.xpath("//*[contains(@class,'ag-cell ag-cell')]"));
		System.out.println("gird Data is ");
		System.out.println("count of grid id "+ls.size());
		for(int i=0;i<ls.size();i++) {
			System.out.println(ls.get(i).getText());
		}
		Object accountType = 0.0;
		Object expQuotePrice = 0.0;
		Object expSuggestedPrice = 0.0;
		Actions act = new Actions(driver);
		act.dragAndDrop(driver.findElement(By.xpath("//*[contains(@class,'ag-horizontal-left-spacer')]"))
				, driver.findElement(By.xpath("//*[contains(@class,'ag-horizontal-right-spacer')]"))).build().perform();
		Thread.sleep(1500);
		if(atm.equals("MRO")) {
//			accountType = Double.parseDouble(ls.get(9).getText().replace("$", ""));
			accountType = Double.parseDouble(driver.findElement(By.xpath("//*[@style='left: 1731px; width: 180px;']")).getText().replace("$", ""));
		}
		if(atm.equals("PO")) {
//			accountType = Double.parseDouble(ls.get(8).getText().replace("$", ""));
			accountType = Double.parseDouble(driver.findElement(By.xpath("//*[@style='left: 1551px; width: 180px;']")).getText().replace("$", ""));
		}if(atm.equals("OEM")) {
//			accountType = Double.parseDouble(ls.get(10).getText().replace("$", ""));
			accountType = Double.parseDouble(driver.findElement(By.xpath("//*[@style='left: 1911px; width: 180px;']")).getText().replace("$", ""));
		}if(atm.equals("RS")) {
//			accountType = Double.parseDouble(ls.get(11).getText().replace("$", ""));
			accountType = Double.parseDouble(driver.findElement(By.xpath("//*[@style='left: 2091px; width: 180px;']")).getText().replace("$", ""));
		}
		System.out.println("act type is "+atm+ " price value "+accountType);
		System.out.println("no act type fixed price value "+actFixedPrice);
		//		}
		if (actFixedPrice.equals(0.0)) {
			expQuotePrice = actSellPrice;
		} else {
			expQuotePrice = actFixedPrice;
		}
		if (at.equals("")) {
			expSuggestedPrice = listPrice;
		} else {
			expSuggestedPrice = accountType;
		}
		System.out.println("fixed sales price value "+actFixedPrice);
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/div/div[5]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-react-container']")));
		driver.findElement(By.xpath("//*[@class='button-icon-text ']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='async-select-example']")));
		driver.findElement(By.xpath("//*[@id='async-select-example']")).sendKeys(orgName);
		Thread.sleep(4700);
		qp.selectDropDown(orgName);
		driver.findElement(By.name("project_name")).sendKeys("Test");
		driver.findElement(By.id("react-select-18-input")).sendKeys("Parts Quote");
		Thread.sleep(2500);
		qp.selectDropDown("Parts Quote");
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@class='side-drawer open']")).findElement(By.tagName("button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("repair-items")));
//		QuotePages quotes = new QuotePages();
		driver.findElement(By.id("repair-items")).findElement(By.className("button-icon-text")).click();
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='side-drawer open']")));
		driver.findElement(By.xpath("//*[@placeholder='Search By Part Number']")).sendKeys("ZZ52BQ7");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1800);
//		driver.findElements(By.xpath("//*[contains(@class,'item-selection-grid')]")).get(0).findElement(By.tagName("label")).click();
//		Actions act = new Actions(driver);
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
		Double actSugestedPrice = Double.parseDouble(driver.findElement(By.xpath("//*[@id=\"repair-items\"]/div[2]/div[1]/div/div[2]/div[2]/div[2]/div[2]/h4")).getText().replace("$", ""));
		Double actListPrice = Double.parseDouble(driver.findElement(By.xpath("//*[@id=\"repair-items\"]/div[2]/div[1]/div/div[2]/div[2]/div[3]/div[2]/h4")).getText().replace("$", ""));
		Double actQuotePrice = Double.parseDouble(driver.findElement(By.xpath("//*[@id=\"repair-items\"]/div[2]/div[1]/div/div[2]/div[2]/div[2]/div[1]/h4")).getText().replace("$", ""));
		System.out.println("act sugested price "+actSugestedPrice);
		System.out.println("act list price "+actListPrice);
		System.out.println("act quote price "+actQuotePrice);

		Object expListPrice = listPrice;
		System.out.println("exp sugested price "+expSuggestedPrice);
		System.out.println("exp list price "+expListPrice);
		System.out.println("exp quote price "+expQuotePrice);
		Object data[] = {actSugestedPrice, expSuggestedPrice, actListPrice, expListPrice, actQuotePrice, expQuotePrice};
		return data;
	}
	public boolean verifyaAddSPItemsToQuotewithAccountType(String tcName, String type, int typeValue, String purchaseDiscount, String fprice, String buyPrice) throws Exception
	{
		Object values[] = this.addSPItemsToQuotewithAccountType(type, typeValue, purchaseDiscount, fprice, buyPrice);
		Object actSp = values[0];
		Object expSp = values[1];
		Object actlp = values[2];
		Object expLp = values[3];
		Object actQp = values[4];
		Object expQp = values[5];
		boolean res = false;
		if (actSp.equals(expSp) && actlp.equals(expLp) && actQp.equals(expQp)) {
			res = true;
			Object status[] = {tcName, "actual Sugested price "+actSp+" expected Sugested price "+expSp,
					"actual quote price "+actQp+" expected quote price "+expQp, "NonStandardPricing", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {tcName, "actual Sugested price "+actSp+" expected Sugested price "+expSp,
					"actual quote price "+actQp+" expected quote price "+expQp, "NonStandardPricing", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
		}
		return res;
	}
	public boolean verifyAAddProduct_DuplicateStockCode(int count, String stockCode, String discountCode, String listPrice, String productClass) throws Exception 
	{
		this.addProduct(stockCode, discountCode, listPrice, productClass);
		Thread.sleep(1600);
		String actText = "";
		String tcName = "";
		String expText = "";
		if(count==1) {
			tcName = "PRICING_012_VerifyAddProduct_DuplicateStockCode";
			expText = "The Stock Code already exists.";
			actText = driver.findElement(By.xpath("//*[@class='server-msg']")).getText();
		}
		if(count==2) {
			tcName = "PRICING_013_VerifyAddProduct_EmptyStockCode";
			expText = "Please enter Stock Code";
			actText = driver.findElement(By.xpath("//*[@class='css-4rxcpg']")).getText();
		}
		if(count==3) {
			tcName = "PRICING_014_VerifyAddProduct_EmptyDiscountCode";
			expText = "Please select Discount Code";
			actText = driver.findElement(By.xpath("//*[@class='form-error-msg']")).getText();
		}
		if(count==4) {
			tcName = "PRICING_015_VerifyAddProduct_EmptyListPrice";
			expText = "Please enter List Price";
			actText = driver.findElement(By.xpath("//*[@class='css-4rxcpg']")).getText();
		}
		if(count==5) {
			tcName = "PRICING_016_VerifyAddProduct_InvalidListPrice";
			expText = "Please enter valid number";
			actText = driver.findElement(By.xpath("//*[@class='css-4rxcpg']")).getText();
		}if(count==6) {
			tcName = "PRICING_017_VerifyAddProduct_EmptyProductClass";
			expText = "Please select Product Class";
			actText = driver.findElement(By.xpath("//*[@class='form-error-msg']")).getText();
		}
		boolean res = false;
		if (actText.equals(expText)) {
			res = true;
			Object status[] = {tcName, actText, expText, "PricingPage", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
			this.closeIcon();
		} else {
			res = false;
			Object status[] = {tcName, actText, expText, "PricingPage", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
			this.closeIcon();
		}
		return res;
	}
	public boolean verifyUpdateProductValidations(int count) throws Exception{
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
		String tcName = "";String actText = "";String expText = "";
		if(count==1) {
			tcName = "PRICING_018_VerifyUpdateProduct_EmptyListPrice";
			driver.findElement(By.name("list_price")).sendKeys("");
			this.addButton("Update Product");
			actText = driver.findElement(By.xpath("//*[@class='css-4rxcpg']")).getText();
			expText = "Please enter List Price";
		}
		if(count==2) {
			tcName = "PRICING_019_VerifyUpdateProduct_InvalidListPrice";
			driver.findElement(By.name("list_price")).sendKeys("krishna");
			this.addButton("Update Product");
			actText = driver.findElement(By.xpath("//*[@class='css-4rxcpg']")).getText();
			expText = "Please enter valid number";
		}
		Thread.sleep(1300);
		boolean res = false;
		if (actText.equals(expText)) {
			res = true;
			Object status[] = {tcName, actText, expText, "PricingPage", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
			this.closeIcon();
		} else {
			res = false;
			Object status[] = {tcName, actText, expText, "PricingPage", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
			this.closeIcon();
		}
		return res;
	}
	public boolean isDifferentPricing() throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		Permissions per = new Permissions();
		per.headerMenu("Admin");
		per.adminLeftMenu("Vendors");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder='Search By Name']")));
		Thread.sleep(2200);
		if(!driver.findElement(By.xpath("//*[@placeholder='Search By Name']")).getAttribute("value").equals("")) {
			driver.findElement(By.xpath("//*[contains(@class,'Cross-svg')]")).click();
			System.out.println("search ahving data previously");
		}
		driver.findElement(By.xpath("//*[@placeholder='Search By Name']")).sendKeys("BACO001");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		driver.findElement(By.xpath("//*[contains(@src,'editicon')]")).click();
		Thread.sleep(1500);
		WebElement checkBox = driver.findElement(By.name("is_different_pricing"));
		if (checkBox.getAttribute("aria-checked").equalsIgnoreCase("true")) {
		} else {
			checkBox.click();
		}
		this.clickButton("Update");
		this.pricingPage("Pricing");
		driver.findElement(By.className("sideList-Search")).findElement(By.xpath("//*[@placeholder='Search']")).sendKeys("BACO001");
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
		WebElement vSel = driver.findElement(By.id("async-select-example"));
		vSel.sendKeys("BACO001");
		Thread.sleep(2000);
		vSel.sendKeys(Keys.ENTER);
		Thread.sleep(1500);
		String sectionText = driver.findElement(By.className("message")).getText();
		System.out.println("count of section tags are "+driver.findElements(By.tagName("section")).size());
		System.out.println(sectionText);
		String vendorText = "This vendor has different pricing by Branch";
		boolean res = false;
		if (sectionText.toLowerCase().contains(vendorText.toLowerCase())) {
			res = true;
			Object status[] = {"PRICING_020_Verify_isDifferentPricing_CheckBox_InVendors", "is different pricing option applied", vendorText, "PricingPage", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
			this.closeIcon();
		} else {
			res = false;
			Object status[] = {"PRICING_020_Verify_isDifferentPricing_CheckBox_InVendors", "is different pricing option  not applied", vendorText, "PricingPage", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
			this.closeIcon();
		}
		return res;
	}
	public boolean filters(String disCountCode) throws Exception {
		this.pricingPage("Pricing");
		driver.findElement(By.className("filter-text")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("react-select-3-input")).sendKeys(disCountCode);
		
		QuotePages quotes = new QuotePages();
		quotes.selectDropDown(disCountCode);
		this.clickButton("Appy");
		Thread.sleep(1600);
		WebDriverWait js = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@viewBox='0 0 16 16']")));
		Thread.sleep(1500);
		List<WebElement> txts = driver.findElement(By.xpath("//*[@class='ag-center-cols-container']")).findElements(By.xpath("//*[@row-index='0']"));
		List<WebElement> ls = txts.get(1).findElements(By.xpath("//*[contains(@class,'ag-cell ag-cell')]"));
		String actDisCode = ls.get(3).getText();
		driver.findElement(By.className("clear-text")).click();
		boolean res = false;
		if (actDisCode.equals(disCountCode)) {
			res = true;
			Object status[] = {"PRICING_021_Verify_Filters_In_Pricing", "Displayed Filter is "+actDisCode, "Applied Filter is "+disCountCode, "PricingPage", "Passed", java.time.LocalDate.now().toString()};
			qp.values(status);
		} else {
			res = false;
			Object status[] = {"PRICING_021_Verify_Filters_In_Pricing", "Displayed Filter is "+actDisCode, "Applied Filter is "+disCountCode, "PricingPage", "Failed", java.time.LocalDate.now().toString()};
			qp.values(status);
		}
		return res;
	}
	public void clickColoumns() throws Exception{
//		driver.findElement(By.className("ag-side-button-label")).click();
//		Thread.sleep(1400);
//		driver.findElements(By.xpath("//*[@aria-label='Press SPACE to toggle visibility (visible)']")).get(1).click();
//		driver.findElements(By.xpath("//*[@aria-label='Press SPACE to toggle visibility (visible)']")).get(3).click();
//		driver.findElements(By.xpath("//*[@aria-label='Press SPACE to toggle visibility (visible)']")).get(5).click();
//		driver.findElements(By.xpath("//*[@aria-label='Press SPACE to toggle visibility (visible)']")).get(4).click();
//		driver.findElement(By.className("ag-side-button-label")).click();
//		Thread.sleep(1400);
	}
	public void clickButton(String btnName) throws Exception{
		List<WebElement> btns = driver.findElements(By.tagName("button"));
		Actions act = new Actions(driver);
		for(int i=0;i<btns.size();i++) {
			if(btns.get(i).getText().equals(btnName)) {
				act.moveToElement(btns.get(i)).build().perform();
				Thread.sleep(600);
				act.click(btns.get(i)).build().perform();
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
		List<WebElement> btns = driver.findElement(By.xpath("//*[contains(@class,'Button-Icon-Display')]")).findElements(By.className("link-icon-text"));
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
	public void takesScreenShot(String fileName) throws Exception
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File SrcFile = ts.getScreenshotAs(OutputType.FILE);
		File destFile = new File(fileName);
		FileUtils.copyFile(SrcFile, destFile);
	}
	public void closeIcon() 
	{
		driver.findElement(By.xpath("//*[@title='close']")).click();
	}
}
