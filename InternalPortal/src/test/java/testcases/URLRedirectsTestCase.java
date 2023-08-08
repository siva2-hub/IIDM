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
		String env = "";
		if (driver.getCurrentUrl().contains("staging")) {
			env = "Stage Instance";
		} else {
			env = "QA Instance";
		}
		all = new AllModules();
		all.linksRedirectsOrNot(env);
	}
	//@Test(enabled = false)
	@Test(priority = 2)
	public void testCase2(String env) throws Exception {
		all.repairsModule("NS23-TS01B-V2", env);
	}
	//@Test(enabled = false)
	@Test(priority = 3)
	public void testCase3(String env) throws Exception {
		all.quotesModule("Week(s)", "21", "32.23", env);
	}
	//@Test(enabled = false)
	@Test(priority = 4)
	public void testCase4() throws Exception {
		String env = "";
		if (driver.getCurrentUrl().contains("staging")) {
			env = "Stage Instance";
		} else {
			env = "QA Instance";
		}
		all.logoutCheckURLRedirectsOrNot(env);
	}
}
