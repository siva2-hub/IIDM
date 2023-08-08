package testcases;

import java.sql.ResultSet;

import org.testng.annotations.Test;

import commonUtils.App;
import libraries.FiltersPages;
import libraries.PricingPages;

public class Filters_TestCases extends App
{
	FiltersPages filters = new FiltersPages();
	//@Test(enabled = false)
	@Test(priority = 1)
	public void testCase1() throws Exception 
	{
		String env = "";
		if (driver.getCurrentUrl().contains("staging")) {
			env = "Stage Instance";
		} else {
			env = "QA Instance";
		}
		//check filters in pricing list view
		try {
			filters.filtersInPricingListView("BACO44", env); 
		} catch (Exception e) {
			Object status[] = {"FILT_001_Verify_Filters_In_Pricing", "", "", "PricingPage", "Not Executed..", java.time.LocalDateTime.now().toString(), env};
			App.values1(status);
			PricingPages price = new PricingPages();
			try {				
				price.closeIcon();
			} catch (Exception e2) {}
		}
		System.exit(0);
		
		//check filters in parts purchase list view
		filters.filtersInPartsPucrhase("Braden Morris", "Emergency Breakdown",
				"FILT_003_VerifyingFiltersInPartsPurchaseListView", env);
		//check filters in discount codes List view
		filters.filtersInDiscountCodes("FILT_002_VerifyingFiltersInDiscountCodesListView", "1672236325", env);
		//check filters in organizations list view
		filters.filtersInOrganizations("FILT_004_VerifyingFiltersInOrganizationssListView"
				,"RS, Reseller or Broker", "Active", 1, env);
		//check filters in contacts list view
		try {
			filters.filtersInOrganizations("FILT_005_VerifyingFiltersInContactssListView"
					,"RS, Reseller or Broker", "Active", 2, env);
		} catch (Exception e) {
			Object status[] = {"FILT_005_VerifyingFiltersInContactsListView", "", "", "ContactsPage", "Not Executed..", java.time.LocalDateTime.now().toString(), env};
			App.values1(status);
			PricingPages price = new PricingPages();
			try {				
				price.closeIcon();
			} catch (Exception e2) {
			}
		}
		//check filters in Admin tabs list view
		ResultSet rs  = App.adminTabs();
		String sts = "";
		while(rs.next()) {
			String labelVal = rs.getString("admin_tab_names");
			String tcName = rs.getString("tc_name");
			if (labelVal.equals("Territories")) {
				sts = "Little Rock";
			} else if(labelVal.equals("Zip Codes")){
				sts = "Jack Carberry";
			}else {
				sts = "Active";
			}
			filters.filtersInAdminmModule(labelVal, sts, tcName, env);
		}
	}
}
