package testcases;

import org.testng.annotations.Test;

import commonUtils.App;
import libraries.FiltersPages;

public class Filters_TestCases extends App
{
	FiltersPages filters = new FiltersPages();
	//@Test(enabled = false)
	@Test(priority = 1)
	public void testCase1() throws Exception 
	{
		//check filters in parts purchase list view
		filters.filtersInPartsPucrhase("Braden Morris", "Emergency Breakdown",
				"FILT_001_VerifyingFiltersInPartsPurchaseListView");
		//check filters in discount codes List view
		filters.filtersInDiscountCodes("FILT_002_VerifyingFiltersInDiscountCodesListView", "1672236325");
		//check filters in organizations list view
		filters.filtersInOrganizations("FILT_003_VerifyingFiltersInOrganizationssListView"
				,"RS, Reseller or Broker", "Active", 1);
		//check filters in contacts list view
		filters.filtersInOrganizations("FILT_004_VerifyingFiltersInContactssListView"
				,"RS, Reseller or Broker", "Active", 2);
	}

}
