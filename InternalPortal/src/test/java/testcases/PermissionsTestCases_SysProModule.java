package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtils.App;
import libraries.Permissions_SysPro_Pages;

public class PermissionsTestCases_SysProModule extends App
{
	Permissions_SysPro_Pages sysPro = new Permissions_SysPro_Pages();
//	@Test(enabled = false)
	@Test(priority =1)
	public void testCase1() throws Exception {
		boolean res = sysPro.verifyInventoryPermissionAsNone("PERMNS_071_Verify_Inventory_Permission_As_None", "Admin", "Users", "Inventory", 2);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority =2)
	public void testCase2() throws Exception {
		App.login();
		boolean res = sysPro.verifyInventoryPermissionAsNone("PERMNS_072_Verify_Inventory_Permission_As_View", "Admin", "Users", "Inventory", 3);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority =3)
	public void testCase3() throws Exception {
		App.login();
		boolean res = sysPro.verifyJobsPermissionAsNone("PERMNS_073_Verify_Jobs_Permission_As_None", "Admin", "Users", "Jobs", 2);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority =4)
	public void testCase4() throws Exception {
		App.login();
		boolean res = sysPro.verifyJobsPermissionAsNone("PERMNS_074_Verify_Jobs_Permission_As_View", "Admin", "Users", "Jobs", 3);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority =5)
	public void testCase5() throws Exception {
		App.login();
		boolean res = sysPro.verifyOrdersPermissionAsNone("PERMNS_075_Verify_Orders_Permission_As_None", "Admin", "Users", "Orders", 2);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority =6)
	public void testCase6() throws Exception {
		App.login();
		boolean res = sysPro.verifyPartsPurchasePermissionAsNone("PERMNS_076_Verify_Part Purchase_Permission_As_None", "Admin", "Users", "Part Purchase", 2);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
//	@Test(enabled = false)
	@Test(priority =7)
	public void testCase7() throws Exception {
		App.login();
		boolean res = sysPro.verifyPartsPurchasePermissionAsNone("PERMNS_077_Part Purchase_Permission_As_View", "Admin", "Users", "Part Purchase", 3);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
	}
}
