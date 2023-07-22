package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtils.App;
import libraries.Permissions_RepairPages;

public class PermissionsTestCasesForRepairModule extends App
{
	Permissions_RepairPages repair = new Permissions_RepairPages();
	//	@Test(enabled = false)
	@Test(priority =1)
	public void testCase1() throws Exception {
		//Check the repair request permission as None
		repair.verifyRepairPermissionAsNone("PERMNS_049_Verify_Repair Request_Permission_As_None", "Admin", "Users", "Repair Request", 2);

		//Check the repair request permission as View
		repair.verifyRepairPermissionAsNone("PERMNS_050_Verify_Repair Request_Permission_As_View", "Admin", "Users", "Repair Request", 3);

		//Check Add to Quote permission as Yes in Repairs
		repair.verifyAddToQuotePermissionAsYes("PERMNS_051_Verify_Add To Quote_Permission_As_Yes_In_Repairs", "Admin", "Users", "Repair Request", 1);

		//Check Add to Quote permission as No in Repairs
		repair.verifyAddToQuotePermissionAsYes("PERMNS_052_Verify_Add To Quote_Permission_As_No_In_Repairs", "Admin", "Users", "Repair Request", 2);
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
		boolean res = repair.verifyRepairPermissionAsNone("PERMNS_050_Verify_Repair Request_Permission_As_View", "Admin", "Users", "Repair Request", 3);
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
		boolean res = repair.verifyAddToQuotePermissionAsYes("PERMNS_051_Verify_Add To Quote_Permission_As_Yes_In_Repairs", "Admin", "Users", "Repair Request", 1);
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
		boolean res = repair.verifyAddToQuotePermissionAsYes("PERMNS_052_Verify_Add To Quote_Permission_As_No_In_Repairs", "Admin", "Users", "Repair Request", 2);
		if(res) {
		}else {
			App.logout();
		}
		Assert.assertTrue(res);
	}
}
