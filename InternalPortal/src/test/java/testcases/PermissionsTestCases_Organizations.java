package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtils.App;
import libraries.Permissions_Organizations;

public class PermissionsTestCases_Organizations extends App
{
	Permissions_Organizations org = new Permissions_Organizations();
	//	@Test(enabled = false)
	@Test(priority =1)
	public void testCase1() throws Exception {
		//Check the Organizations as None permission
		org.verifyOrganizationsPermissionsAsNone("PERMNS_078_Verify_Organizations_Permission_As_None", "Admin", "Users", "Organizations",2);
		//
		org.verifyOrganizationsPermissionsAsNone("PERMNS_079_Verify_Organizations_Permission_As_View", "Admin", "Users", "Organizations",3);
		//
		org.verifyOrganizationsPermissionsAsNone("PERMNS_080_Verify_Contacts_Permission_As_None", "Admin", "Users", "Contacts",2);
		//
		org.verifyOrganizationsPermissionsAsNone("PERMNS_081_Verify_Contacts_Permission_As_View", "Admin", "Users", "Contacts",3);
		//
		org.exportPermissionAsYesInContacts("PERMNS_082_Verify_Export_Permission_As_Yes_Contacts", "Admin", "Users", "contacts_export","contacts", "Export", "1");
		//
		org.exportPermissionAsYesInContacts("PERMNS_083_Verify_Export_Permission_As_No_Contacts", "Admin", "Users", "contacts_export","contacts", "Export", "0");
		//
		org.exportPermissionAsYesInContacts("PERMNS_084_Verify_Export_Permission_As_No_Organizations", "Admin", "Users", "organizations_export","organizations", "Export", "0");
		//
		org.exportPermissionAsYesInContacts("PERMNS_085_Verify_Export_Permission_As_Yes_Organizations", "Admin", "Users", "organizations_export","organizations", "Export", "1");
		//
		org.exportPermissionAsYesInContacts("PERMNS_086_Verify_Sync_Permission_As_Yes_Contacts", "Admin", "Users", "contacts_sync","contacts", "Sync", "1");
		//
		org.exportPermissionAsYesInContacts("PERMNS_087_Verify_Sync_Permission_As_No_Contacts", "Admin", "Users", "contacts_sync","contacts", "Sync", "0");
		//
		org.exportPermissionAsYesInContacts("PERMNS_088_Verify_Sync_Permission_As_Yes_Organizations", "Admin", "Users", "organizations_sync","organizations", "Sync", "1");
		//
		org.exportPermissionAsYesInContacts("PERMNS_089_Verify_Sync_Permission_As_No_Organizations", "Admin", "Users", "organizations_sync","organizations", "Sync", "0");
		//
		org.exportPermissionAsYesInContacts("PERMNS_090_Verify_Sync_Permission_As_No_Warehouse", "Admin", "Users", "warehouse_sync","warehouse", "Sync", "0");
		//
		org.exportPermissionAsYesInContacts("PERMNS_091_Verify_Sync_Permission_As_Yes_Warehouse", "Admin", "Users", "warehouse_sync","warehouse", "Sync", "1");
		//
		org.exportPermissionAsYesInContacts("PERMNS_092_Verify_Sync_Permission_As_No_ProductClass", "Admin", "Users", "product_class_sync","product_class", "Sync", "0");
		//
		org.exportPermissionAsYesInContacts("PERMNS_093_Verify_Sync_Permission_As_Yes_ProductClass", "Admin", "Users", "product_class_sync","product_class", "Sync", "1");
		//


		//		if(res) {
		//		}else {
		//			App.logout();
		//		}
		//		Assert.assertTrue(res);
		//		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =2)
	public void testCase2() throws Exception {
		App.login();
		boolean res = org.verifyOrganizationsPermissionsAsNone("PERMNS_079_Verify_Organizations_Permission_As_View", "Admin", "Users", "Organizations",3);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =3)
	public void testCase3() throws Exception {
		App.login();
		boolean res = org.verifyOrganizationsPermissionsAsNone("PERMNS_080_Verify_Contacts_Permission_As_None", "Admin", "Users", "Contacts",2);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =4)
	public void testCase4() throws Exception {
		App.login();
		boolean res = org.verifyOrganizationsPermissionsAsNone("PERMNS_081_Verify_Contacts_Permission_As_View", "Admin", "Users", "Contacts",3);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =5)
	public void testCase5() throws Exception {
		App.login();
		boolean res = org.exportPermissionAsYesInContacts("PERMNS_082_Verify_Export_Permission_As_Yes_Contacts", "Admin", "Users", "contacts_export","contacts", "Export", "1");
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =6)
	public void testCase6() throws Exception {
		App.login();
		boolean res = org.exportPermissionAsYesInContacts("PERMNS_083_Verify_Export_Permission_As_No_Contacts", "Admin", "Users", "contacts_export","contacts", "Export", "0");
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =7)
	public void testCase7() throws Exception {
		App.login();
		boolean res = org.exportPermissionAsYesInContacts("PERMNS_084_Verify_Export_Permission_As_No_Organizations", "Admin", "Users", "organizations_export","organizations", "Export", "0");
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =8)
	public void testCase8() throws Exception {
		App.login();
		boolean res = org.exportPermissionAsYesInContacts("PERMNS_085_Verify_Export_Permission_As_Yes_Organizations", "Admin", "Users", "organizations_export","organizations", "Export", "1");
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =9)
	public void testCase9() throws Exception {
		App.login();
		boolean res = org.exportPermissionAsYesInContacts("PERMNS_086_Verify_Sync_Permission_As_Yes_Contacts", "Admin", "Users", "contacts_sync","contacts", "Sync", "1");
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =10)
	public void testCase10() throws Exception {
		App.login();
		boolean res = org.exportPermissionAsYesInContacts("PERMNS_087_Verify_Sync_Permission_As_No_Contacts", "Admin", "Users", "contacts_sync","contacts", "Sync", "0");
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =11)
	public void testCase11() throws Exception {
		App.login();
		boolean res = org.exportPermissionAsYesInContacts("PERMNS_088_Verify_Sync_Permission_As_Yes_Organizations", "Admin", "Users", "organizations_sync","organizations", "Sync", "1");
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =12)
	public void testCase12() throws Exception {
		App.login();
		boolean res = org.exportPermissionAsYesInContacts("PERMNS_089_Verify_Sync_Permission_As_No_Organizations", "Admin", "Users", "organizations_sync","organizations", "Sync", "0");
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =13)
	public void testCase13() throws Exception {
		App.login();
		boolean res = org.exportPermissionAsYesInContacts("PERMNS_090_Verify_Sync_Permission_As_No_Warehouse", "Admin", "Users", "warehouse_sync","warehouse", "Sync", "0");
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =14)
	public void testCase14() throws Exception {
		App.login();
		boolean res = org.exportPermissionAsYesInContacts("PERMNS_091_Verify_Sync_Permission_As_Yes_Warehouse", "Admin", "Users", "warehouse_sync","warehouse", "Sync", "1");
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =15)
	public void testCase15() throws Exception {
		App.login();
		boolean res = org.exportPermissionAsYesInContacts("PERMNS_092_Verify_Sync_Permission_As_No_ProductClass", "Admin", "Users", "product_class_sync","product_class", "Sync", "0");
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
		App.logout();
	}
	@Test(enabled = false)
	//	@Test(priority =16)
	public void testCase16() throws Exception {
		App.login();
		boolean res = org.exportPermissionAsYesInContacts("PERMNS_093_Verify_Sync_Permission_As_Yes_ProductClass", "Admin", "Users", "product_class_sync","product_class", "Sync", "1");
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
	}
}
