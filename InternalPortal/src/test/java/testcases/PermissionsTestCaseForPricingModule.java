package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtils.App;
import libraries.Permissions;
import libraries.Permissions_PricingPages;

public class PermissionsTestCaseForPricingModule extends App
{
	Permissions_PricingPages p = new Permissions_PricingPages();
	//	@Test(enabled = false)
	@Test(priority = 1)
	public void testCase1() throws Exception{
		boolean res = p.verifyPricingermissionsAsNone("PERMNS_036_Verify_Pricing_Permission_As_None", "Admin", "Users", "Pricing", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	//	@Test(enabled = false)
	@Test(priority = 2)
	public void testCase2() throws Exception{
		App.login();
		boolean res = p.verifyPricingermissionsAsNone("PERMNS_037_Verify_Discount Codes_Permission_As_None", "Admin", "Users", "Discount Codes", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	//	@Test(enabled = false)
	@Test(priority = 3)
	public void testCase3() throws Exception{
		App.login();
		boolean res = p.verifyPricingermissionsAsNone("PERMNS_038_Verify_Non Standard Pricing_Permission_As_None", "Admin", "Users", "Non Standard Pricing", 2);
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
		boolean res = p.verifyPricingExportImportPermissions("PERMNS_039_Verify_Export_Permission_As_Yes_In_Pricing", "Admin", "Users", "Pricing", 1, 3, "Export", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 5)
	public void testCase5() throws Exception {
		App.login();
		boolean res = p.verifyPricingExportImportPermissions("PERMNS_040_Verify_Export_Permission_As_NoIn_Pricing", "Admin", "Users", "Pricing", 2, 3, "Export", 2);
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
		App.login();
		boolean res = p.verifyPricingExportImportPermissions("PERMNS_041_Verify_Import_Permission_As_Yes_In_Pricing", "Admin", "Users", "Pricing", 1, 3, "Import", 3);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 7)
	public void testCase7() throws Exception {
		App.login();
		boolean res = p.verifyPricingExportImportPermissions("PERMNS_042_Verify_Import_Permission_As_No_In_Pricing", "Admin", "Users", "Pricing", 2, 3, "Import", 3);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 8)
	public void testCase8() throws Exception {
		App.login();
		boolean res = p.verifyPricingExportImportPermissions("PERMNS_043_Verify_Export_Permission_As_Yes_In_Discount Codes", "Admin", "Users", "Discount Codes", 1, 1, "Export", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority = 9)
	public void testCase9() throws Exception {
		App.login();
		boolean res = p.verifyPricingExportImportPermissions("PERMNS_044_Verify_Export_Permission_As_No_In_Discount Codes", "Admin", "Users", "Discount Codes", 2, 1, "Export", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority =10)
	public void testCase10() throws Exception {
		App.login();
		boolean res = p.verifyPricingExportImportPermissions("PERMNS_044_Verify_Export_Permission_As_Yes_In_Non Standard Pricing", "Admin", "Users", "Non Standard Pricing", 1, 2, "Export", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority =11)
	public void testCase11() throws Exception {
		App.login();
		boolean res = p.verifyPricingExportImportPermissions("PERMNS_045_Verify_Export_Permission_As_No_In_Non Standard Pricing", "Admin", "Users", "Non Standard Pricing", 2, 2, "Export", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority =12)
	public void testCase12() throws Exception {
		App.login();
		boolean res = p.verifyPricingPermissionsAsView("PERMNS_046_Verify_Pricing_Permission_As_View", "Admin", "Users", "Pricing",3);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority =13)
	public void testCase13() throws Exception {
		App.login();
		boolean res = p.verifyPricingPermissionsAsView("PERMNS_047_Verify_Discount Codes_Permission_As_View", "Admin", "Users", "Discount Codes", 3);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority =14)
	public void testCase14() throws Exception {
		App.login();
		boolean res = p.verifyPricingPermissionsAsView("PERMNS_048_Verify_Non Standard Pricing_Permission_As_View", "Admin", "Users", "Non Standard Pricing", 3);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
//		App.logout();
	}
}
