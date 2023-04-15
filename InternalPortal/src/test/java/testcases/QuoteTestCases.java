package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtils.App;
import libraries.QuotePages;

public class QuoteTestCases extends App{
	QuotePages quotes = new QuotePages();
//	@Test(priority=1)
//	public void testCase1() throws Exception {
//		boolean res = quotes.verifyCreateQuote();
//		Assert.assertTrue(res);
//	}
	@Test(priority=2)
	public void testCase2() throws Exception {
		boolean res = quotes.verifySelectItemToQuote();
		Assert.assertTrue(res);
	}
//	@Test(priority=3)
//	public void testCase3() throws Exception {
//		boolean res = quotes.verifySubmitForInternalApproval();
//		Assert.assertTrue(res);
//	}
//	@Test(priority=4)
//	public void testCase4() throws Exception {
//		boolean res = quotes.verifyApproveButton();
//		Assert.assertTrue(res);
//	}
//	@Test(priority=5)
//	public void testCase5() throws Exception {
//		boolean res = quotes.verifySubmitForCustomerApproval();
//		Assert.assertTrue(res);
//	}
//	@Test(priority=6)
//	public void testCase6() throws Exception {
//		boolean res = quotes.verifyQuoteWon(1);
//		Assert.assertTrue(res);
//	}
//	@Test(priority=7)
//	public void testCase7() throws Exception {
//		boolean res = quotes.verifyQuoteWon(2);
//		Assert.assertTrue(res);
//	}
//	@Test(priority=8)
//	public void testCase8() throws Exception {
//		boolean res = quotes.verifyReviseQuote();
//		Assert.assertTrue(res);
//	}
//	@Test(priority=9)
//	public void testCase9() throws Exception {
//		boolean res = quotes.verifyPrintDownLoad();
//		Assert.assertTrue(res);
//	}
//	@Test(priority=10)
//	public void testCase10() throws Exception {
//		boolean res = quotes.verifyTopSearchInQuoteListView("2023031500024", 1);
//		Assert.assertTrue(res);
//	}
//	@Test(priority=11)
//	public void testCase11() throws Exception {
//		boolean res = quotes.verifyTopSearchInQuoteListView("123 E Doty Corporation", 2);
//		Assert.assertTrue(res);
//	}
//	@Test(priority=12)
//	public void testCase12() throws Exception {
//		boolean res = quotes.verifyTopSearchInQuoteListView("Ramsay Taiym", 3);
//		Assert.assertTrue(res);
//	}
//	@Test(priority=13)
//	public void testCase13() throws Exception {
//		boolean res = quotes.verifyTopSearchInQuoteListView("pete.soto@motion-ind.com", 4);
//		Assert.assertTrue(res);
//	}
//	@Test(priority=14)
//	public void testCase14() throws Exception {
//		boolean res = quotes.verifyFiltersInQuoteListView("Zummo Meat Co Inc", "Jeremy Morgan", "Approved", "Swetha Epi", 1);
//		Assert.assertTrue(res);
//	}
//	@Test(priority=15)
//	public void testCase15() throws Exception {
//		boolean res = quotes.verifyFiltersStateMaintanance("Zummo Meat Co Inc", "Jeremy Morgan", "Approved", "Swetha Epi", 1);
//		Assert.assertTrue(res);
//	}
//	@Test(priority=16)
//	public void testCase16() throws Exception {
//		boolean res = quotes.verifyResetandClearButtonInFiltersPage("Zummo Meat Co Inc", 1);
//		Assert.assertTrue(res);
//	}
//	@Test(priority=17)
//	public void testCase17() throws Exception {
//		boolean res = quotes.verifyClosenadReOpenButtons( 1);
//		Assert.assertTrue(res);
//	}
//	@Test(priority=18)
//	public void testCase18() throws Exception {
//		boolean res = quotes.verifyClosenadReOpenButtons( 2);
//		Assert.assertTrue(res);
//	}
//	@Test(priority=19)
//	public void testCase19() throws Exception {
//		boolean res = quotes.verifyDeleteIcon(1);
//		Assert.assertTrue(res);
//	}
//	@Test(priority=20)
//	public void testCase20() throws Exception {
//		boolean res = quotes.verifyDeleteIcon(2);
//		Assert.assertTrue(res);
//	}
//	@Test(priority=21)
//	public void testCase21() throws Exception {
//		boolean res = quotes.verifyAddOptionInQuoteDetailedView();
//		Assert.assertTrue(res);
//	}
}
