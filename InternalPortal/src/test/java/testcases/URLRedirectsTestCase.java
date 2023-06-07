package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtils.App;
import libraries.AllModules;

public class URLRedirectsTestCase extends App
{
	AllModules all ;
	@Test
	public void testCase18() throws Exception {
		all = new AllModules();
		all.linksRedirectsOrNot();
		
	}
}
