package testcases;

import org.testng.annotations.Test;

import commonUtils.App;
import libraries.AllModules;

public class CreateJobs_PartsPurchases extends App
{
	AllModules all = new AllModules();
	//@Test(enabled = false)
	@Test(priority = 1)
	public void testCase1(String env) throws Exception {
		boolean res = all.verifyCreateJob("JOBS_001_VerifyCreateJob", "223834", 1, env);
		if (res) {
			org.testng.Assert.assertTrue(res);
			App.logout();
		} else {

			App.logout();
		}
	}
	//@Test(enabled = false)
	@Test(priority = 2)
	public void testCase2(String env) throws Exception {
		App.login();
		all.createPartsPurchase("PARTSPURCHASE_001_VerifyCreate_PartsPurchase", "65785", 1, env);
	}
}
