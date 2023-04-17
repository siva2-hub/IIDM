package libraries;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
				break;
			}
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='Button-Icon-Display']")));
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
		driver.findElement(By.id("react-select-3-input")).sendKeys(listPrice);
		this.addButton("Add Product");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ag-center-cols-container']")));
		return stockCode;
	}
	public boolean verifyAddProduct(String stockCode, String discountCode, String listPrice) throws Exception
	{
		boolean res = false;
		String expStockCode = this.addProduct(stockCode, discountCode, listPrice);
		driver.findElement(By.xpath("")).sendKeys(stockCode);
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
	public void addDiscountCode() throws Exception 
	{
		this.pricingPage("Discount Codes");
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='Button-Icon-Display']")));
		this.addImportExporBtns("Add");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name='discount_code']")));
		driver.findElement(By.name("discount_code")).sendKeys("newdc1234");
		driver.findElement(By.id("react-select-3-input")).sendKeys(String.valueOf(java.time.LocalDate.now()));
		driver.findElement(By.id("react-select-3-input")).sendKeys(Keys.ENTER);
		driver.findElement(By.id("react-select-4-input")).click();
		driver.findElement(By.id("react-select-4-input")).sendKeys(Keys.ARROW_LEFT);
		driver.findElement(By.id("react-select-4-input")).sendKeys(Keys.ENTER);
		driver.findElement(By.id("react-select-5-input")).sendKeys("11");
		qp.selectDropDown("11");
		driver.findElement(By.id("react-select-6-input")).sendKeys("0.25");
		driver.findElement(By.id("react-select-7-input")).sendKeys("0.50");
		driver.findElement(By.id("react-select-8-input")).sendKeys("0.75");
		driver.findElement(By.id("react-select-9-input")).sendKeys("0.80");
	}
	public void addButton(String btnName) {
		List<WebElement> btns = driver.findElements(By.tagName("section"));
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
	
}
