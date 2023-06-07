package libraries;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import commonUtils.App;

public class AllModules extends App
{
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
}
