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
		all.repairsModule();
	}
	//@Test(enabled = false)
	@Test(priority = 3)
	public void testCase3() throws Exception {
		all.quotesModule("Weeks", "21", "32.23");
	}
	
}
	