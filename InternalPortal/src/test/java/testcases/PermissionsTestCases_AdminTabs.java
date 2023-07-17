package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtils.App;
import libraries.Permissions;

public class PermissionsTestCases_AdminTabs extends App
{
	Permissions p = new Permissions();
	//	@Test(enabled = false)
	@Test(priority = 1)
	public void testCase1() throws Exception{
		//Check the Account Types as None permission
        
		p.verifyAdminTabs_None("PERMNS_001_Verify_AccountType_Permission_As_None", "Admin", "Users", "Account Type", 2);
		//
		p.verifyAdminTabs_None("PERMNS_002_Verify_Branches_Permission_As_None", "Admin", "Users", "Branches", 2);
		//
		p.verifyAdminTabs_None("PERMNS_003_Verify_Classification_Permission_As_None", "Admin", "Users", "Classification", 2);
		//
		p.verifyAdminTabs_None("PERMNS_004_Verify_Industry_Permission_As_None", "Admin", "Users", "Industry", 2);
		//
		p.verifyAdminTabs_None("PERMNS_005_Verify_Po Min Qty_Permission_As_None", "Admin", "Users", "Po Min Qty", 2);
		//
		p.verifyAdminTabs_None("PERMNS_006_Verify_Product Class_Permission_As_None", "Admin", "Users", "Product Class", 2);
		//
		p.verifyAdminTabs_None("PERMNS_007_Verify_QC Control_Permission_As_None", "Admin", "Users", "QC Control", 2);
		//
		p.verifyAdminTabs_None("PERMNS_008_Verify_Quote Approval_Permission_As_None", "Admin", "Users", "Quote Approval", 2);
		//
		p.verifyAdminTabs_None("PERMNS_009_Verify_Quote Types_Permission_As_None", "Admin", "Users", "Quote Types", 2);
		//
		p.verifyAdminTabs_None("PERMNS_010_Verify_Regions_Permission_As_None", "Admin", "Users", "Regions", 2);
		//
		p.verifyAdminTabs_None("PERMNS_011_Verify_Sales Potential_Permission_As_None", "Admin", "Users", "Sales Potential", 2);
		//
		p.verifyAdminTabs_None("PERMNS_012_Verify_Terms & Conditions_Permission_As_None", "Admin", "Users", "Terms & Conditions", 2);
		//
		p.verifyAdminTabs_None("PERMNS_013_Verify_Territories_Permission_As_None", "Admin", "Users", "Territories", 2);
		//
		p.verifyAdminTabs_None("PERMNS_014_Verify_User Roles_Permission_As_None", "Admin", "Users", "User Roles", 2);
		//
		p.verifyAdminTabs_None("PERMNS_015_Verify_Vendors_Permission_As_None", "Admin", "Users", "Vendors", 2);
		//
		p.verifyAdminTabs_None("PERMNS_016_Verify_Warehouse_Permission_As_None", "Admin", "Users", "Warehouse", 2);
		//
		p.verifyAdminTabs_None("PERMNS_017_Verify_Zip Codes_Permission_As_None", "Admin", "Users", "Zip Codes", 2);
		//
		p.verifyAdminTabs_None("PERMNS_096_Verify_Product Category_Permission_As_None", "Admin", "Users", "Product Category", 2);

		//Check the Account Types as View permission
		p.adminTabwithViewPermission("PERMNS_018_Verify_Account Type_Permission_As_View","Admin", "Users", "Account Type", 3);
		//
		p.adminTabwithViewPermission("PERMNS_019_Verify_Branches_Permission_As_View","Admin", "Users", "Branches", 3);
		//
		p.adminTabwithViewPermission("PERMNS_020_Verify_Regions_Permission_As_View","Admin", "Users", "Regions", 3);
		//
		p.adminTabwithViewPermission("PERMNS_021_Verify_Territories_Permission_As_View","Admin", "Users", "Territories", 3);
		//
		p.adminTabwithViewPermission("PERMNS_022_Verify_Zip Codes_Permission_As_View","Admin", "Users", "Zip Codes", 3);
		//
		p.adminTabwithViewPermission("PERMNS_023_Verify_Warehouse_Permission_As_View","Admin", "Users", "Warehouse", 3);
		//
		p.adminTabwithViewPermission("PERMNS_024_Verify_Classifications_Permission_As_View","Admin", "Users", "Classification", 3);
		//
		p.adminTabwithViewPermission("PERMNS_025_Verify_Contact Types_Permission_As_View","Admin", "Users", "Contact Types", 3);
		//
		p.adminTabwithViewPermission("PERMNS_026_Verify_Industries_Permission_As_View","Admin", "Users", "Industry", 3);
		//
		p.adminTabwithViewPermission("PERMNS_027_Verify_PO Min Qty_Permission_As_View","Admin", "Users", "PO Min Qty", 3);
		//
		p.adminTabwithViewPermission("PERMNS_028_Verify_Quote Types_Permission_As_View","Admin", "Users", "Quote Types", 3);
		//
		p.adminTabwithViewPermission("PERMNS_029_Verify_Sales Potential_Permission_As_View","Admin", "Users", "Sales Potential", 3);
		//
		p.adminTabwithViewPermission("PERMNS_030_Verify_Vendors_Permission_As_View","Admin", "Users", "Vendors", 3);
		//
		p.adminTabwithViewPermission("PERMNS_031_Verify_Quote Approval_Permission_As_View","Admin", "Users", "Quote Approval", 3);
		//
		p.adminTabwithViewPermission("PERMNS_032_Verify_User Roles_Permission_As_View","Admin", "Users", "User Roles", 3);
		//
		p.adminTabwithViewPermission("PERMNS_033_Verify_Terms & Conditions_Permission_As_View","Admin", "Users", "Terms & Conditions", 3);
		//
		p.adminTabwithViewPermission("PERMNS_034_Verify_QC Control_Permission_As_View","Admin", "Users", "QC Control", 3);
		//
		p.adminTabwithViewPermission("PERMNS_094_Verify_Product Category_Permission_As_View","Admin", "Users", "Product Category", 3);
		

		//		if(res) {
		//
		//		}else {
		//			App.logout();
		//		}
		//		Assert.assertTrue(res);
		//		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 2)
	public void testCase2() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_002_Verify_Branches_Permission_As_None", "Admin", "Users", "Branches", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 3)
	public void testCase3() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_003_Verify_Classification_Permission_As_None", "Admin", "Users", "Classification", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 4)
	public void testCase4() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_004_Verify_Industry_Permission_As_None", "Admin", "Users", "Industry", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 5)
	public void testCase5() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_005_Verify_Po Min Qty_Permission_As_None", "Admin", "Users", "Po Min Qty", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 6)
	public void testCase6() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_006_Verify_Product Class_Permission_As_None", "Admin", "Users", "Product Class", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 7)
	public void testCase7() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_007_Verify_QC Control_Permission_As_None", "Admin", "Users", "QC Control", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 8)
	public void testCase8() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_008_Verify_Quote Approval_Permission_As_None", "Admin", "Users", "Quote Approval", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 9)
	public void testCase9() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_009_Verify_Quote Types_Permission_As_None", "Admin", "Users", "Quote Types", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 10)
	public void testCase10() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_010_Verify_Regions_Permission_As_None", "Admin", "Users", "Regions", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 11)
	public void testCase11() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_011_Verify_Sales Potential_Permission_As_None", "Admin", "Users", "Sales Potential", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 12)
	public void testCase12() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_012_Verify_Terms & Conditions_Permission_As_None", "Admin", "Users", "Terms & Conditions", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 13)
	public void testCase13() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_013_Verify_Territories_Permission_As_None", "Admin", "Users", "Territories", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 14)
	public void testCase14() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_014_Verify_User Roles_Permission_As_None", "Admin", "Users", "User Roles", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 15)
	public void testCase15() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_015_Verify_Vendors_Permission_As_None", "Admin", "Users", "Vendors", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 16)
	public void testCase16() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_016_Verify_Warehouse_Permission_As_None", "Admin", "Users", "Warehouse", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 17)
	public void testCase17() throws Exception{
		App.login();
		boolean res= p.verifyAdminTabs_None("PERMNS_017_Verify_Zip Codes_Permission_As_None", "Admin", "Users", "Zip Codes", 2);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 18)
	public void testCase18() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_018_Verify_Account Type_Permission_As_View","Admin", "Users", "Account Type", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 19)
	public void testCase19() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_019_Verify_Branches_Permission_As_View","Admin", "Users", "Branches", 3);
		if(res) {

		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 20)
	public void testCase20() throws Exception{
		App.login();
		boolean res = p.adminTabwithViewPermission("PERMNS_020_Verify_Regions_Permission_As_View","Admin", "Users", "Regions", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 21)
	public void testCase21() throws Exception{
		App.login();
		boolean res = p.adminTabwithViewPermission("PERMNS_021_Verify_Territories_Permission_As_View","Admin", "Users", "Territories", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 22)
	public void testCase22() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_022_Verify_Zip Codes_Permission_As_View","Admin", "Users", "Zip Codes", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 23)
	public void testCase23() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_023_Verify_Warehouse_Permission_As_View","Admin", "Users", "Warehouse", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 24)
	public void testCase24() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_024_Verify_Classifications_Permission_As_View","Admin", "Users", "Classification", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 25)
	public void testCase25() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_025_Verify_Contact Types_Permission_As_View","Admin", "Users", "Contact Types", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 26)
	public void testCase26() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_026_Verify_Industries_Permission_As_View","Admin", "Users", "Industry", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 27)
	public void testCase27() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_027_Verify_PO Min Qty_Permission_As_View","Admin", "Users", "PO Min Qty", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 28)
	public void testCase28() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_028_Verify_Quote Types_Permission_As_View","Admin", "Users", "Quote Types", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 29)
	public void testCase29() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_029_Verify_Sales Potential_Permission_As_View","Admin", "Users", "Sales Potential", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 30)
	public void testCase30() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_030_Verify_Vendors_Permission_As_View","Admin", "Users", "Vendors", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 31)
	public void testCase31() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_031_Verify_Quote Approval_Permission_As_View","Admin", "Users", "Quote Approval", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 32)
	public void testCase32() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_032_Verify_User Roles_Permission_As_View","Admin", "Users", "User Roles", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 33)
	public void testCase33() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_033_Verify_Terms & Conditions_Permission_As_View","Admin", "Users", "Terms & Conditions", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority = 34)
	public void testCase34() throws Exception{
		App.login(); 
		boolean res = p.adminTabwithViewPermission("PERMNS_034_Verify_QC Control_Permission_As_View","Admin", "Users", "QC Control", 3);
		Assert.assertTrue(res);
		App.logout();
	}

}
