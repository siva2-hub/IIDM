package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtils.App;
import libraries.RepairPages;

public class RepairTestCases extends App
{
	RepairPages repairs = new RepairPages();
	//@Test(enabled = false)
	@Test(priority = 1)
	public void testCase1() throws Exception {
		boolean res = repairs.verifyCreateRMA();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 2)
	public void testCase2() throws Exception {
		App.login();
		boolean res = repairs.verifySelectItemsToRepair();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 3)
	public void testCase3() throws Exception {
		App.login();
		boolean res = repairs.verifyAddNewItem();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 4)
	public void testCase4() throws Exception {
		App.login();
		boolean res = repairs.verifyAssignLocation();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 5)
	public void testCase5() throws Exception {
		App.login();
		boolean res = repairs.verifyAssignTechnician();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 6)
	public void testCase6() throws Exception {
		App.login();
		boolean res = repairs.verifyEvaluateItem();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 7)
	public void testCase7() throws Exception {
		App.login();
		boolean res = repairs.verifyAddRepairableItemToQuote();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 8)
	public void testCase8() throws Exception {
		App.login();
		boolean res = repairs.verifyCreateQuoteFromRepair();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 9)
	public void testCase9() throws Exception {
 		App.login();
		boolean res = repairs.verifyAssignToQC();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 10)
	public void testCase10() throws Exception {
		App.login();
		boolean res = repairs.verifyQCCheckList();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 11)
	public void testCase11() throws Exception {
		App.login();
		boolean res = repairs.verifyQCCheckListStatusAsFail();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 12)
	public void testCase12() throws Exception {
		App.login();
		boolean res = repairs.fileUpload();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 13)
	public void testCase13() throws Exception {
		App.login();
		boolean res = repairs.verifyFilters("123 E Doty Corporation", "Dallas House", "Check In Pending");
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority = 14)
	public void testCase14() throws Exception {
		App.login();
		boolean res = repairs.verifyFilterStateMaintanance();
		Assert.assertTrue(res);
		App.logout();
	}
}
