package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtils.App;
import libraries.Permissions_QuotesPages;

public class PermissionsTestCases_QuotesModule extends App
{
	Permissions_QuotesPages quotes = new Permissions_QuotesPages();
////	@Test(enabled = false)
//	@Test(priority =1)
//	public void testCase1() throws Exception {
////		App.login();
//		boolean res = quotes.verifyQuotesPermissionAsNone("PERMNS_001_Verify_QuoteForParts_Permission_As_None", "Admin", "Users", "Quote for Parts/Repairs",3, 2);
//		if(res) {
//		}else {
//			App.logout();
//		}
//		Assert.assertTrue(res);
//		App.logout();
//	}
////	@Test(enabled = false)
//	@Test(priority =2)
//	public void testCase2() throws Exception {
//		App.login();
//		boolean res = quotes.verifyQuotesPermissionAsNone("PERMNS_002_Verify_QuoteForParts_Permission_As_View", "Admin", "Users", "Quote for Parts/Repairs",3, 3);
//		if(res) {
//		}else {
//			App.logout();
//		}
//		Assert.assertTrue(res);
//		App.logout();
//	}
//	@Test(enabled = false)
	@Test(priority = 3)
	public void testCase3() throws Exception {
//		App.login();
		boolean res = quotes.verifyCreateSalesOrderPermissionAsYes("PERMNS_003_Verify_CreateSalesOrder_Permission_As_Yes_Quotes", "Admin", "Users", "Quote for Parts/Repairs",2, 2);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 4)
	public void testCase4() throws Exception {
		App.login();
		boolean res = quotes.verifyCreateSalesOrderPermissionAsYes("PERMNS_004_Verify_CreateSalesOrder_Permission_As_No_Quotes", "Admin", "Users", "Quote for Parts/Repairs",2, 1);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
//		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 5)
	public void testCase5() throws Exception {
		App.login();
		boolean res = quotes.verifyCreateSalesOrderPermissionAsYes("PERMNS_004_Verify_CreateSalesOrder_Permission_As_No_Quotes", "Admin", "Users", "Quote for Parts/Repairs",2, 1);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 6)
	public void testCase6() throws Exception {
//		App.login();
		boolean res = quotes.verifyQuoteClosePermissionAsYes_Quotes("PERMNS_004_Verify_CreateSalesOrder_Permission_As_No_Quotes", "Admin", "Users", "Quote for Parts/Repairs",5, 2);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
//		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 7)
	public void testCase7() throws Exception {
//		App.login();
		boolean res = quotes.verifyQuoteClosePermissionAsYes_Quotes("PERMNS_004_Verify_CreateSalesOrder_Permission_As_No_Quotes", "Admin", "Users", "Quote for Parts/Repairs",5, 1);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
//		App.logout();
	}

}
