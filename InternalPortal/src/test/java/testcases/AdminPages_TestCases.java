package testcases;

import org.testng.annotations.Test;

import commonUtils.App;
import libraries.AdminPages;

public class AdminPages_TestCases extends App
{
	AdminPages admin = new AdminPages();
	
	//@Test(enabled = false)
		@Test(priority = 1)
		public void testCase1() throws Exception {
			
			admin.addAndEditsInAdminPages("Admin", "Account Types");
		}
}
