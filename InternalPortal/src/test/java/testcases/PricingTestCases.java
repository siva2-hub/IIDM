package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtils.App;
import libraries.PricingPages;

public class PricingTestCases extends App
{
	PricingPages price = new PricingPages();
	//@Test(enabled = false)
	@Test(priority = 1)
	public void testCase1() throws Exception {
		boolean res = price.verifyAddProduct( "BACO55", "120.12");
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 2)
	public void testCase2() throws Exception {
		App.login();
		boolean res = price.verifyAddDiscountCode();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 3)
	public void testCase3() throws Exception {
		App.login();
		boolean res = price.verifyUpdateProduct();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 4)
	public void testCase4() throws Exception {
		App.login();
		boolean res = price.verifyUpdateDiscountCode();
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//@Test(priority = 5)
	public void testCase5() throws Exception {
		App.login();
		price.importFile();
		//		Assert.assertTrue(res);
		App.logout();
	}

}
