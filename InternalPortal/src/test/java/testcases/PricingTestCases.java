package testcases;

import org.testng.annotations.Test;

import commonUtils.App;
import libraries.PricingPages;

public class PricingTestCases extends App
{

	PricingPages price = new PricingPages();
	//	@Test(enabled = false)
	@Test(priority = 1)
	public void testCase1() throws Exception {
		String env = "";
		if (driver.getCurrentUrl().contains("staging")) {
			env = "Stage Instance";
		} else {
			env = "QA Instance";
		}
		//
		price.verifyAddProduct( "BACO55", "120.12", "BA05", env);
		//	
		price.verifyAddDiscountCode(env);
		//	
		price.verifyUpdateProduct(env);
		//	
		price.verifyUpdateDiscountCode(env);
		//
		price.importFile(env);
		//
		price.verifyBuyPrice_SellPrice_InSpecialPricing("Markup", 49, "34", "","", 1, env);
		//VerifyBuyPrice_SellPrice_InSpecialPricing_BuyPrice_PurchaseDiscounts_Null
		price.verifyBuyPrice_SellPrice_InSpecialPricing("Markup", 29, "", "","", 2, env);
		//VerifyBuyPrice_SellPrice_InSpecialPricing_BuyPrice_PurchaseDiscounts_Not_Null
		price.verifyBuyPrice_SellPrice_InSpecialPricing("Markup", 19, "39", "","267", 3, env);
		//VerifyBuyPrice_SellPrice_InSpecialPricing_BuyPrice_PurchaseDiscounts_Not_Null
		price.verifyBuyPrice_SellPrice_InSpecialPricing("Discount", 27, "39", "","123", 4, env);
		//
		price.verifyaAddSPItemsToQuotewithAccountType("PRICING_010_VerifyaAddSPItemsToQuotewithAccountType_withFixedPrice","Markup", 17, "43", "201.23","", env);
		//
		price.verifyaAddSPItemsToQuotewithAccountType("PRICING_011_VerifyaAddSPItemsToQuotewithAccountType_withOutFixedPrice","Discount", 17, "43", "","", env);
		//
		price.verifyAAddProduct_DuplicateStockCode(1, "0165029SS", "BACO55", "178.9", "BA05", env);
		//
		price.verifyAAddProduct_DuplicateStockCode(2, "", "BACO55", "178.9", "BA05", env);
		//
		price.verifyAAddProduct_DuplicateStockCode(3, "0165029SS", "jhdfjdshfjbds", "178.9", "BA05", env);
		//
		price.verifyAAddProduct_DuplicateStockCode(4, "0165029SS", "BACO55", "", "BA05", env);
		//
		price.verifyAAddProduct_DuplicateStockCode(5, "0165029SS", "BACO55", "fdsfbdsfjbds", "BA05", env);
		//
		price.verifyAAddProduct_DuplicateStockCode(6, "0165029SS", "BACO55", "125.23", "krishna naidu", env);
		//
		price.verifyUpdateProductValidations(1, env);
		//
		price.verifyUpdateProductValidations(2, env);
		//
		price.isDifferentPricing("PRICING_020_Verify_isDifferentPricing_CheckBox_Yes_InVendors", true, env);
		//
		price.isDifferentPricing("PRICING_021_Verify_isDifferentPricing_CheckBox_No_InVendors", false, env);

	}
	
	//	@Test(enabled = false)
	//	//	@Test(priority = 2)
	//	public void testCase2() throws Exception {
	//		App.login();
	//		boolean res = price.verifyAddDiscountCode(env);
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 3)
	//	public void testCase3() throws Exception {
	//		App.login();
	//		boolean res = price.verifyUpdateProduct();
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 4)
	//	public void testCase4() throws Exception {
	//		App.login();
	//		boolean res = price.verifyUpdateDiscountCode();
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 5)
	//	public void testCase5() throws Exception {
	//		//		App.login();
	//		boolean res = price.importFile();
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 6)
	//	public void testCase6() throws Exception {
	//		App.login();
	//		//VerifyBuyPrice_SellPrice_InSpecialPricing_BuyPrice_Null_PurchaseDiscount_Not_Null
	//		boolean res =price.verifyBuyPrice_SellPrice_InSpecialPricing("Markup", 49, "34", "","", 1);
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//
	//	@Test(enabled = false)
	//	//	@Test(priority = 7)
	//	public void testCase7() throws Exception {
	//		//		App.login();
	//		//VerifyBuyPrice_SellPrice_InSpecialPricing_BuyPrice_PurchaseDiscounts_Null
	//		boolean res =price.verifyBuyPrice_SellPrice_InSpecialPricing("Markup", 29, "", "","", 2);
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//
	//	@Test(enabled = false)
	//	//	@Test(priority = 8)
	//	public void testCase8() throws Exception {
	//		App.login();
	//		//VerifyBuyPrice_SellPrice_InSpecialPricing_BuyPrice_PurchaseDiscounts_Not_Null
	//		boolean res =price.verifyBuyPrice_SellPrice_InSpecialPricing("Markup", 19, "39", "","267", 3);
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//
	//	@Test(enabled = false)
	//	//	@Test(priority = 9)
	//	public void testCase9() throws Exception {
	//		App.login();
	//		//VerifyBuyPrice_SellPrice_InSpecialPricing_BuyPrice_PurchaseDiscounts_Not_Null
	//		boolean res =price.verifyBuyPrice_SellPrice_InSpecialPricing("Discount", 27, "39", "","123", 4);
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 10)
	//	public void testCase10() throws Exception {
	//		//		App.login();
	//		boolean res = price.verifyaAddSPItemsToQuotewithAccountType("PRICING_010_VerifyaAddSPItemsToQuotewithAccountType_withFixedPrice","Markup", 17, "43", "201.23","");
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 11)
	//	public void testCase11() throws Exception {
	//		App.login();
	//		boolean res = price.verifyaAddSPItemsToQuotewithAccountType("PRICING_011_VerifyaAddSPItemsToQuotewithAccountType_withOutFixedPrice","Discount", 17, "43", "","");
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 12)
	//	public void testCase12() throws Exception {
	//		App.login();
	//		boolean res = price.verifyAAddProduct_DuplicateStockCode(1, "0165029SS", "BACO55", "178.9", "BA05");
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 13)
	//	public void testCase13() throws Exception {
	//		App.login();
	//		boolean res = price.verifyAAddProduct_DuplicateStockCode(2, "", "BACO55", "178.9", "BA05");
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 14)
	//	public void testCase14() throws Exception {
	//		App.login();
	//		boolean res = price.verifyAAddProduct_DuplicateStockCode(3, "0165029SS", "jhdfjdshfjbds", "178.9", "BA05");
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 15)
	//	public void testCase15() throws Exception {
	//		App.login();
	//		boolean res = price.verifyAAddProduct_DuplicateStockCode(4, "0165029SS", "BACO55", "", "BA05");
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 16)
	//	public void testCase16() throws Exception {
	//		App.login();
	//		boolean res = price.verifyAAddProduct_DuplicateStockCode(5, "0165029SS", "BACO55", "fdsfbdsfjbds", "BA05");
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 17)
	//	public void testCase17() throws Exception {
	//		App.login();
	//		boolean res = price.verifyAAddProduct_DuplicateStockCode(6, "0165029SS", "BACO55", "125.23", "krishna naidu");
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 18)
	//	public void testCase18() throws Exception {
	//		App.login();
	//		boolean res = price.verifyUpdateProductValidations(1);
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 19)
	//	public void testCase19() throws Exception {
	//		App.login();
	//		boolean res = price.verifyUpdateProductValidations(2);
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 20)
	//	public void testCase20() throws Exception {
	//		App.login();
	//		boolean res = price.isDifferentPricing("PRICING_020_Verify_isDifferentPricing_CheckBox_Yes_InVendors", true);
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
	//	@Test(enabled = false)
	//	//	@Test(priority = 21)
	//	public void testCase21() throws Exception {
	//		App.login();
	//		boolean res = price.isDifferentPricing("PRICING_021_Verify_isDifferentPricing_CheckBox_No_InVendors", false);
	//		Assert.assertTrue(res);
	//		App.logout();
	//	}
}
