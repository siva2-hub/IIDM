package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtils.App;
import libraries.QuotePages;

public class QuoteTestCases extends App{
	QuotePages quotes = new QuotePages();
	//@Test(enabled = false)
	@Test(priority=1)
	public void testCase1() throws Exception {
		boolean res = quotes.verifyCreateQuote();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=2)
	public void testCase2() throws Exception {
		App.login();
		boolean res = quotes.verifySelectItemToQuote();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=3)
	public void testCase3() throws Exception {
		App.login();
		boolean res = quotes.verifySubmitForInternalApproval();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=4)
	public void testCase4() throws Exception {
		App.login();
		boolean res = quotes.verifyApproveButton();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=5)
	public void testCase5() throws Exception {
		App.login();
		boolean res = quotes.verifySubmitForCustomerApproval();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=6)
	public void testCase6() throws Exception {
		App.login();
		boolean res = quotes.verifyQuoteWon(1);
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=7)
	public void testCase7() throws Exception {
		App.login();
		boolean res = quotes.verifyQuoteWon(2);
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=8)
	public void testCase8() throws Exception {
		App.login();
		boolean res = quotes.verifyReviseQuote();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=9)
	public void testCase9() throws Exception {
		App.login();
		boolean res = quotes.verifyPrintDownLoad();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=10)
	public void testCase10() throws Exception {
		App.login();
		boolean res = quotes.verifyTopSearchInQuoteListView("2023053100074", 1);
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=11)
	public void testCase11() throws Exception {
		App.login();
		boolean res = quotes.verifyTopSearchInQuoteListView("123 E Doty Corporation", 2);
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=12)
	public void testCase12() throws Exception {
		App.login();
		boolean res = quotes.verifyTopSearchInQuoteListView("Frontier", 3);
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=13)
	public void testCase13() throws Exception {
		App.login();
		boolean res = quotes.verifyTopSearchInQuoteListView("pete.soto@motion-ind.com", 4);
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=14)
	public void testCase14() throws Exception {
		App.login();
		boolean res = quotes.verifyFiltersInQuoteListView("Zummo Meat Co Inc", "Jeremy Morgan", "Approved", "Swetha Epi", 1);
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=15)
	public void testCase15() throws Exception {
		App.login();
		boolean res = quotes.verifyFiltersStateMaintanance("Zummo Meat Co Inc",	 "Jeremy Morgan", "Approved", "Swetha Epi", 1);
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=16)
	public void testCase16() throws Exception {
		App.login();
		boolean res = quotes.verifyResetandClearButtonInFiltersPage("Zummo Meat Co Inc", 1);
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=17)
	public void testCase17() throws Exception {
		App.login();
		boolean res = quotes.verifyClosenadReOpenButtons( 1);
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=18)
	public void testCase18() throws Exception {
		App.login();
		boolean res = quotes.verifyClosenadReOpenButtons( 2);
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=19)
	public void testCase19() throws Exception {
		App.login();
		boolean res = quotes.verifyDeleteIcon(1);
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=20)
	public void testCase20() throws Exception {
		App.login();
		boolean res = quotes.verifyDeleteIcon(2);
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=21)
	public void testCase21() throws Exception {
		App.login();
		boolean res = quotes.verifyAddOptionInQuoteDetailedView();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=22)
	public void testCase22() throws Exception {
		App.login();
		boolean res = quotes.verifyQuoteClone_QuotesForParts();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
	@Test(priority=23)
	public void testCase23() throws Exception {
		App.login();
		boolean res=quotes.quoteClone_QuoteForRepairs();
		Assert.assertTrue(res);
		App.logout();
	}
	//@Test(enabled = false)
		@Test(priority=24)
		public void testCase24() throws Exception {
			App.login();
			boolean res=quotes.verifyDeclineInQuoteDetailedView();
			Assert.assertTrue(res);
		}
}
