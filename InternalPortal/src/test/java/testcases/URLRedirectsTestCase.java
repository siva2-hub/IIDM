package testcases;

import org.testng.annotations.Test;

import commonUtils.App;
import libraries.AllModules;

public class URLRedirectsTestCase extends App
{
	AllModules all  = new AllModules();
	//@Test(enabled = false)
	@Test(priority = 1)
	public void testCase18() throws Exception {
		all = new AllModules();
		all.linksRedirectsOrNot();
	}
	//@Test(enabled = false)
	@Test(priority = 2)
	public void testCase2() throws Exception {
		all.repairsModule("NS23-TS01B-V2");
	}
	//@Test(enabled = false)
	@Test(priority = 3)
	public void testCase3() throws Exception {
		all.quotesModule("Week(s)", "21", "32.23");
	}
	//@Test(enabled = false)
	@Test(priority = 4)
	public void testCase4() throws Exception {
		all.logoutCheckURLRedirectsOrNot();
	}
}
