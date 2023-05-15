package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtils.App;
import libraries.PricingPages;

public class PricingTestCases extends App
{
	PricingPages price = new PricingPages();
//	@Test(enabled = false)
	@Test(priority = 1)
	public void testCase1() throws Exception {
		boolean res = price.verifyAddProduct( "BACO55", "120.12");
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 2)
	public void testCase2() throws Exception {
		App.login();
		boolean res = price.verifyAddDiscountCode();
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 3)
	public void testCase3() throws Exception {
		App.login();
		boolean res = price.verifyUpdateProduct();
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 4)
	public void testCase4() throws Exception {
		App.login();
		boolean res = price.verifyUpdateDiscountCode();
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 5)
	public void testCase5() throws Exception {
		App.login();
		boolean res = price.importFile();
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 6)
	public void testCase6() throws Exception {
		App.login();
		boolean res =price.verifyBuyPrice_SellPrice_InSpecialPricing("Discount", 89, "9", "");
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 7)
	public void testCase7() throws Exception {
		App.login();
		boolean res = price.verifyaAddSPItemsToQuotewithAccountType("PRICING_007_VerifyaAddSPItemsToQuotewithAccountType_withFixedPrice","Markup", 17, "43", "201.23");
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 8)
	public void testCase8() throws Exception {
		App.login();
		boolean res = price.verifyaAddSPItemsToQuotewithAccountType("PRICING_008_VerifyaAddSPItemsToQuotewithAccountType_withOutFixedPrice","Discount", 17, "43", "");
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 9)
	public void testCase9() throws Exception {
		App.login();
		boolean res = price.verifyAAddProduct_DuplicateStockCode(1, "0165029SS", "BACO55", "178.9");
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 10)
	public void testCase10() throws Exception {
		App.login();
		boolean res = price.verifyAAddProduct_DuplicateStockCode(2, "", "BACO55", "178.9");
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 11)
	public void testCase11() throws Exception {
		App.login();
		boolean res = price.verifyAAddProduct_DuplicateStockCode(3, "0165029SS", "jhdfjdshfjbds", "178.9");
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 12)
	public void testCase12() throws Exception {
		App.login();
		boolean res = price.verifyAAddProduct_DuplicateStockCode(4, "0165029SS", "BACO55", "");
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 13)
	public void testCase13() throws Exception {
		App.login();
		boolean res = price.verifyAAddProduct_DuplicateStockCode(5, "0165029SS", "BACO55", "dnsfjnds");
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 14)
	public void testCase14() throws Exception {
		App.login();
		boolean res = price.verifyUpdateProductValidations(1);
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 15)
	public void testCase15() throws Exception {
		App.login();
		boolean res = price.verifyUpdateProductValidations(2);
		Assert.assertTrue(res);
		App.logout();
	}
}
