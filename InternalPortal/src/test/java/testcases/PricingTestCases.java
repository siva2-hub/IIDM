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
		boolean res = price.verifyAddProduct("newsc_1236", "BACO55", "120.12");
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
//	@Test(priority = 2)
//	public void testCase2() throws Exception {
//		App.login();
//		price.addDiscountCode();
//		//			Assert.assertTrue(res);
//		App.logout();
//	}
}
