package com.redhat.qe.ce10.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.redhat.qe.auto.testng.Assert;
import com.redhat.qe.auto.testng.TestNGUtils;
import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.ce10.data.HardwareProfile;
import com.redhat.qe.ce10.data.ProviderAccount_RHEVM;
import com.redhat.qe.ce10.data.ProviderAccount_Vsphere;
import com.redhat.qe.ce10.data.ProviderAccount_ec2;

@Test(groups="testplan-CloudEngine:HwProfiles")
public class ManageHwProfiles extends SeleniumTestScript{
	
	@BeforeTest
	public void init(){
		HardwareProfile.deleteAll();
	}
	
	@Test(description = "create a basic hardware profile",dataProvider="getHWP")
	public void createHWP(String testname, HashMap<String, String> hwp){
		HardwareProfile thisHWP = new HardwareProfile(hwp, true);
		Assert.assertTrue(thisHWP.verifyDetailsInRow(),"Asserted hwp "+thisHWP.getName()+" has the expected values");
		thisHWP.delete();
		Assert.assertTrue(!thisHWP.verifyDetailsInRow(),"Asserted hwp "+thisHWP.getName()+" has the expected values");
	}
	

	
	@DataProvider(name="getHWP")
	public Object[][] getHWP() {
		return TestNGUtils.convertListOfListsTo2dArray(hwpEC2());
	}
	protected List<List<Object>> hwpEC2() {
		
		List<List<Object>> ll = new ArrayList<List<Object>>();
		
		ll.add(Arrays.asList(new Object[]{ "ec2 t1 micro", ProviderAccount_ec2.hwProfile_t1_micro  } ));
		ll.add(Arrays.asList(new Object[]{ "ec2 m1 small", ProviderAccount_ec2.hwProfile_m1_small  } ));
		ll.add(Arrays.asList(new Object[]{ "ec2 c1 medium", ProviderAccount_ec2.hwProfile_c1_medium  } ));
		ll.add(Arrays.asList(new Object[]{ "ec2 m1 large", ProviderAccount_ec2.hwProfile_m1_large  } ));
		ll.add(Arrays.asList(new Object[]{ "ec2 c1 xlarge", ProviderAccount_ec2.hwProfile_c1_xlarge  } ));
		ll.add(Arrays.asList(new Object[]{ "ec2 m1 xlarge", ProviderAccount_ec2.hwProfile_m1_xlarge} ));
		ll.add(Arrays.asList(new Object[]{ "ec2 m2 xlarge", ProviderAccount_ec2.hwProfile_m2_2xlarge } ));
		ll.add(Arrays.asList(new Object[]{ "ec2 m2 xlarge", ProviderAccount_ec2.hwProfile_m2_xlarge} ));
		ll.add(Arrays.asList(new Object[]{ "ec2 m2 4xlarge", ProviderAccount_ec2.hwProfile_m2_4xlarge  } ));
		ll.add(Arrays.asList(new Object[]{ "vsphere default", ProviderAccount_Vsphere.hwProfile_default } ));
		ll.add(Arrays.asList(new Object[]{ "rhevm default", ProviderAccount_RHEVM.hwProfile_default } ));
		
		
		return ll;	
	}
	
	
}	
	


	
	
	/*
	 * 
	 * 		Test Setup
	 * 
	 */
	/*
	public Map<String, HashMap<String,HashMap<String,String>>> defaultProfiles = new HashMap<String,HashMap<String,HashMap<String,String>>>();
	public HashMap<String,Integer> hwpMainTableColumnIndexes = new HashMap<String,Integer>();
	public HashMap<String,Integer> hwpDetailsTableColumnIndexes = new HashMap<String,Integer>();
	public HashMap<String,Integer> hwpProviderTableColumnIndexes = new HashMap<String,Integer>();
	
	
	
	
	@BeforeClass (description="Initialization for test run")
	public void intialize(){
		
		// make sure we are admin
		tasks.ifNotAdminLogoutAndInAsAdmin();
		
		// populate data structures
		populateDefaultProfiles();
		hwpMainTableColumnIndexes = tasks11.hwpMainList_getTableHeaderIndexes();
	    
	    // build good providers and stash them for use in this test suite
		if (goodProviders == null) {
			// set a global object which is pulled in for the provider accounts data provider
			Object[][] temp = TestNGUtils.convertListOfListsTo2dArray(createGoodProviderObjects());
			goodProviders = temp.clone();
		}
		
		// create good providers and account if needed
		for (int i = 0; i < goodProviders.length; i++) {
			ProviderData provider = (ProviderData) goodProviders[i][1];
			
			if (! tasks.doesProviderExist(provider)) {
				System.out.println("Provider (" + provider.getName() + ") not found, creating it..." );
				tasks.createProvider(provider);
			} else {
				System.out.println("Provider (" + provider.getName() + ") exists, using existing..." );
			}
		
			if (! tasks.doesProviderAccountExist(provider)) {
				System.out.println("Provider Account (" + provider.getAccountName() + ") not found, creating it..." );
				tasks.createProviderAccount(provider, false);
			} else {
				System.out.println("Provider Account (" + provider.getAccountName() + ") exists, using existing..." );
			}
		}
	}
	
	*/
	/*
	 * 
	 * 		Profile Tests
	 * 
	 */
	

//	@Test(description="verify main profile list values", groups={"defaultProfiles"}, dataProvider="getDefaultProfiles" )
//	public void verifyMainListValues(String sProfileName) {
//		
//		nav11.hardwareProfiles(true);
//		HashMap<String,HashMap<String,String>> profileComponents = defaultProfiles.get(sProfileName);
//        int iProfileRowIndex = tasks11.hwpMainList_getRowIndex(sProfileName, UI11.sHwpMainTableNameHeaderText);
//        
//        Iterator tableColumnIterator = hwpMainTableColumnIndexes.entrySet().iterator();
//        while (tableColumnIterator.hasNext()) {
//        	Map.Entry pairs = (Map.Entry)tableColumnIterator.next();
//	        String headerText = (String) pairs.getKey();
//	        int iColumnIndex = (Integer) pairs.getValue();
//        	
//	        if (profileComponents.containsKey(headerText)) {
//	        	String sCellValue = tasks11.hwpMainList_getCell(Integer.toString(iProfileRowIndex), Integer.toString(iColumnIndex));
//	        	String sExpectedValue = profileComponents.get(headerText).get(DEFAULT_VALUE);
//	        	Assert.assertTrue(sCellValue.equalsIgnoreCase(sExpectedValue), "expected value (" + sExpectedValue+ ") matches ui value (" + sCellValue + ")");
//	        }
//        }
//	}
//	
//	@Test(description="verify provider list values", groups={"defaultProfiles"}, dataProvider="getProviderTestObjects" )
//	public void verifyProviderListValues(String sTestName, String sProviderName, String sProfileName) {
//		
//		nav11.hardwareProfiles(true);
//		if (hwpProviderTableColumnIndexes.isEmpty()) {
//			hwpProviderTableColumnIndexes = tasks11.hwpProviderList_getTableHeaderIndexes(sProfileName);
//		}
//		
//		String sProviderContainerId = nav11.hwpProviderDetails(sProfileName);
//		
//		HashMap<String,HashMap<String,String>> profileComponents = defaultProfiles.get(sProfileName);
//        int iProviderRowIndex = tasks11.hwpProviderList_getRowIndex(sProviderContainerId, sProfileName, sProviderName, UI11.sHwpProviderTableNameHeaderText);
//        
//        Iterator tableColumnIterator = hwpProviderTableColumnIndexes.entrySet().iterator();
//        while (tableColumnIterator.hasNext()) {
//        	Map.Entry pairs = (Map.Entry)tableColumnIterator.next();
//	        String headerText = (String) pairs.getKey();
//	        int iColumnIndex = (Integer) pairs.getValue();
//        	
//	        if (profileComponents.containsKey(headerText)) {
//	        	String sCellValue = tasks11.hwpProviderList_getCell(sProviderContainerId, Integer.toString(iProviderRowIndex), Integer.toString(iColumnIndex));
//	        	String sExpectedValue = profileComponents.get(headerText).get(DEFAULT_VALUE);
//	        	Assert.assertTrue(sCellValue.equalsIgnoreCase(sExpectedValue), "expected value (" + sExpectedValue+ ") matches ui value (" + sCellValue + ")");
//	        }
//        }
//	}
//
//	@Test(description="verify default profile values", groups={"defaultProfiles"}, dataProvider="getDefaultProfiles" )
//	public void verifyDefaultProfileValues(String sProfileName) {
//		
//		if (hwpDetailsTableColumnIndexes.isEmpty()) {
//			hwpDetailsTableColumnIndexes = tasks11.hwpDetailsList_getTableHeaderIndexes(sProfileName);
//		}
//		
//		nav11.hwpProfileDetails(sProfileName);
//		
//		HashMap<String,HashMap<String,String>> profileComponents = defaultProfiles.get(sProfileName);
//		
//        Iterator profileComponentIterator = profileComponents.entrySet().iterator();
//        while (profileComponentIterator.hasNext()) {
//        	Map.Entry pairs = (Map.Entry)profileComponentIterator.next();
//	        String sComponent = (String) pairs.getKey();
//	        HashMap<String,String> componentValues = (HashMap<String,String>) pairs.getValue();
//	        
//	        int iProfileComponentRowIndex = -1;
//	        if (sComponent.toLowerCase().equalsIgnoreCase(VIRTUAL_CPU.toLowerCase())) {
//	        	iProfileComponentRowIndex = tasks11.hwpDetailsList_getRowIndex(sProfileName, "cpu", UI11.sHwpDetailsTableNameHeaderText);
//	        } else {
//	        	iProfileComponentRowIndex = tasks11.hwpDetailsList_getRowIndex(sProfileName, sComponent.toLowerCase(), UI11.sHwpDetailsTableNameHeaderText);
//	        }
//        	
//	        Iterator componentValuesIterator = componentValues.entrySet().iterator();
//	        while (componentValuesIterator.hasNext()) {
//	        	Map.Entry pairs2 = (Map.Entry)componentValuesIterator.next();
//	        	String sAttribute = (String) pairs2.getKey();
//	        	
//	        	if (hwpDetailsTableColumnIndexes.containsKey(sAttribute)) {
//	        		String sColumnIndex = hwpDetailsTableColumnIndexes.get(sAttribute).toString();
//	        	
//	        		if (profileComponents.get(sComponent).containsKey(sAttribute)) {
//	        			String sCellValue = tasks11.hwpDetailsList_getCell(Integer.toString(iProfileComponentRowIndex), sColumnIndex);
//	        			String sExpectedValue = profileComponents.get(sComponent).get(sAttribute);
//	        			Assert.assertTrue(sCellValue.equalsIgnoreCase(sExpectedValue), "expected value (" + sExpectedValue+ ") matches ui value (" + sCellValue + ")");
//	        		}
//	        	}	
//	        }    
//        }
//	}
//	
//	@Test(description="verify hwp search box", groups={"search"}, dataProvider="getSearchStrings" )
//	public void verifyHwProfileSearch(String sTestName, String sSearchString, int iExpectedCount, String[] expectedProfilesToBeFound) {
//		nav11.hardwareProfiles(true);
//		
//		tasks11.search(sSearchString);
//		
//		// check returned result count
//		int uiCount = tasks11.hwpMainList_getRowCount();
//		Assert.assertTrue(uiCount == iExpectedCount, "expected value (" + Integer.toString(iExpectedCount)+ ") matches ui value (" + Integer.toString(uiCount) + ")");
//		
//		// verify found profile names
//		if (iExpectedCount > 0) {
//			for (int x=0; x < expectedProfilesToBeFound.length; x++) {
//				Assert.assertTrue(sel().isElementPresent(new Element(UI.link, expectedProfilesToBeFound[x])), "search found profile named: " + expectedProfilesToBeFound[x]);
//			}
//		//} else {
//		//	// check for error message?
//		}
//	}
	
	/*
	 * 
	 * 		Data Providers
	 * 
	 */
	/*
	@DataProvider(name="getDefaultProfiles")
	public Object[][] getDefaultProfiles() {
		return TestNGUtils.convertListOfListsTo2dArray(listDefaultProfiles());
	}
	protected List<List<Object>> listDefaultProfiles() {
		
		List<List<Object>> ll = new ArrayList<List<Object>>();
		
		//  Added these and commented out the for loop for passing tests while updating tcms
		//ll.add(Arrays.asList(new Object[]{ "t1.micro" } ));
		//ll.add(Arrays.asList(new Object[]{ "m1.small" } ));
		//ll.add(Arrays.asList(new Object[]{ "m1.large" } ));
		//ll.add(Arrays.asList(new Object[]{ "m1.xlarge" } ));
		
		for (Entry<String, HashMap<String, HashMap<String, String>>> entry : defaultProfiles.entrySet()) {
			ll.add(Arrays.asList(new Object[]{ entry.getKey() } ));
		}
		
		return ll;	
	}
	
	@DataProvider(name="getSearchStrings")
	public Object[][] getSearchStrings() {
		return TestNGUtils.convertListOfListsTo2dArray(createSearchStrings());
	}
	protected List<List<Object>> createSearchStrings() {
		
		List<List<Object>> ll = new ArrayList<List<Object>>();
		
		ll.add(Arrays.asList(new Object[]{ "byFullName", 	  	"m2.4xlarge",    	1, new String[]{ "m2.4xlarge"}  } ));
		ll.add(Arrays.asList(new Object[]{ "byPartialName1",  	"m1", 				3, new String[]{ "m1.small", "m1.large", "m1.xlarge"} } ));
		ll.add(Arrays.asList(new Object[]{ "byPartialName2",  	"large", 			6, new String[]{ "m1.large", "m1.xlarge", "c1.xlarge", "m2.xlarge", "m2.2xlarge", "m2.4xlarge"} } ));
		ll.add(Arrays.asList(new Object[]{ "byFullArch1", 	  	"i386", 			3, new String[]{ "t1.micro", "m1.small", "c1.medium"} } ));
		ll.add(Arrays.asList(new Object[]{ "byFullArch2", 	  	"x86_64", 			6, new String[]{ "m1.large", "m1.xlarge", "c1.xlarge", "m2.xlarge", "m2.2xlarge", "m2.4xlarge"} } ));
		ll.add(Arrays.asList(new Object[]{ "byMemValue", 	  	"850.0", 			2, new String[]{ "m1.large", "m2.2xlarge"} } ));
		ll.add(Arrays.asList(new Object[]{ "byStorageValue",  	"1690.0", 			3, new String[]{ "m1.xlarge", "c1.xlarge", "m2.4xlarge"} } ));
		ll.add(Arrays.asList(new Object[]{ "byCpuValue1", 	  	"1.0", 				2, new String[]{ "t1.micro", "m1.small" } } ));
		ll.add(Arrays.asList(new Object[]{ "byCpuValue2", 	  	"6.5", 				1, new String[]{ "m2.xlarge" } } ));
		ll.add(Arrays.asList(new Object[]{ "emptyReturnsAll",	"", 				9, new String[]{ "m1.large", "m1.xlarge", "c1.xlarge", "m2.xlarge", "m2.2xlarge", "m2.4xlarge", "m1.small", "c1.medium"} } ));
		ll.add(Arrays.asList(new Object[]{ "caseInsensitive", 	"T1.MICRO",    		1, new String[]{ "t1.micro" } } ));
		ll.add(Arrays.asList(new Object[]{ "containsAllWords1",	"m2 4x", 			1, new String[]{ "m2.4xlarge" } } ));
		ll.add(Arrays.asList(new Object[]{ "containsAllWords2",	"m3 4x", 			0, null } ));
		
		//  No error @ 256, probably should try 10000
		//String sTooLong = tasks.generateRandomString(256);
		//ll.add(Arrays.asList(new Object[]{ "valueTooLong",		sTooLong, 			0, "expected_profile_names", "expected_error_message?" } ));
		
		// special characters don't blow it up
		// by provider?
		
		return ll;	
	}
	
	protected List<List<Object>> createGoodProviderObjects() {

		ProviderData_ec2   ec2_good = new ProviderData_ec2("ec2" );
		//ProviderData_rhevm rhevm_good = new ProviderData_rhevm("rhevm_good" );

		List<List<Object>> ll = new ArrayList<List<Object>>();
        ll.add(Arrays.asList(new Object[]{ "ec2",   ec2_good } ));
        //ll.add(Arrays.asList(new Object[]{ "rhevm", rhevm_good } ));
        
        return ll;
	}
	
	@DataProvider(name="getProviderTestObjects")
	public Object[][] getProviderTestObjects() {
		return TestNGUtils.convertListOfListsTo2dArray(createProviderTestObjects());
	}
	protected List<List<Object>> createProviderTestObjects() {
		List<List<Object>> ll = new ArrayList<List<Object>>();

        for (int i = 0; i < goodProviders.length; i++) {
			
			// is there a better way to do this?
			String sProviderClass = goodProviders[i][1].getClass().toString();
			
			if (sProviderClass.indexOf("ProviderData_ec2") > 0) {
				
				// good provider
				ProviderData_ec2 ec2Provider = (ProviderData_ec2) goodProviders[i][1];
				String sProviderName = ec2Provider.getName();
				
				ll.add(Arrays.asList(new Object[]{ "(" +sProviderName+ ") t1.micro", sProviderName, "t1.micro" } ));
				ll.add(Arrays.asList(new Object[]{ "(" +sProviderName+ ") m1.small", sProviderName, "m1.small" } ));
				ll.add(Arrays.asList(new Object[]{ "(" +sProviderName+ ") m1.large", sProviderName, "m1.large" } ));
				ll.add(Arrays.asList(new Object[]{ "(" +sProviderName+ ") m1.xlarge", sProviderName, "m1.xlarge" } ));
				ll.add(Arrays.asList(new Object[]{ "(" +sProviderName+ ") c1.medium", sProviderName, "c1.medium" } ));
				ll.add(Arrays.asList(new Object[]{ "(" +sProviderName+ ") c1.xlarge", sProviderName, "c1.xlarge" } ));
				ll.add(Arrays.asList(new Object[]{ "(" +sProviderName+ ") m2.xlarge", sProviderName, "m2.xlarge" } ));
				ll.add(Arrays.asList(new Object[]{ "(" +sProviderName+ ") m2.2xlarge", sProviderName, "m2.2xlarge" } ));
				ll.add(Arrays.asList(new Object[]{ "(" +sProviderName+ ") m2.4xlarge", sProviderName, "m2.4xlarge" } ));
			}
        }    
        return ll;	
	}
	
	
	 * 
	 * 		Misc methods
	 * 
	 

	private HashMap<String,String> componentHashMaker(String sValue, String sUnit) {
		
		HashMap<String,String> hashmap = new HashMap<String,String>();
		hashmap.put(KIND, FIXED);
		hashmap.put(RANGE_FIRST, NA);
		hashmap.put(RANGE_LAST, NA);
		hashmap.put(ENUM_ENTRIES, NA);
		hashmap.put(DEFAULT_VALUE, sValue);
		hashmap.put(UNIT, sUnit);
		return hashmap;
	}
	
	private void populateDefaultProfiles() {
		
		HashMap<String,HashMap<String,String>> t1_micro_profile = new HashMap<String,HashMap<String,String>>();
		t1_micro_profile.put(MEMORY, componentHashMaker("645.12", "MB"));
		t1_micro_profile.put(VIRTUAL_CPU, componentHashMaker("1.0", "count"));
		t1_micro_profile.put(STORAGE, componentHashMaker("160.0", "GB"));
		t1_micro_profile.put(ARCHITECTURE, componentHashMaker("i386", "label"));
		defaultProfiles.put("t1.micro", t1_micro_profile);
		
		HashMap<String,HashMap<String,String>> m1_small_profile = new HashMap<String,HashMap<String,String>>();
		m1_small_profile.put(MEMORY, componentHashMaker("1740.8", "MB"));
		m1_small_profile.put(VIRTUAL_CPU, componentHashMaker("1.0", "count"));
		m1_small_profile.put(STORAGE, componentHashMaker("160.0", "GB"));
		m1_small_profile.put(ARCHITECTURE, componentHashMaker("i386", "label"));
		defaultProfiles.put("m1.small", m1_small_profile);
		
		HashMap<String,HashMap<String,String>> m1_large_profile = new HashMap<String,HashMap<String,String>>();
		m1_large_profile.put(MEMORY, componentHashMaker("7680.0", "MB"));
		m1_large_profile.put(VIRTUAL_CPU, componentHashMaker("4.0", "count"));
		m1_large_profile.put(STORAGE, componentHashMaker("850.0", "GB"));
		m1_large_profile.put(ARCHITECTURE, componentHashMaker("x86_64", "label"));
		defaultProfiles.put("m1.large", m1_large_profile);
		
		HashMap<String,HashMap<String,String>> m1_xlarge_profile = new HashMap<String,HashMap<String,String>>();
		m1_xlarge_profile.put(MEMORY, componentHashMaker("15360.0", "MB"));
		m1_xlarge_profile.put(VIRTUAL_CPU, componentHashMaker("8.0", "count"));
		m1_xlarge_profile.put(STORAGE, componentHashMaker("1690.0", "GB"));
		m1_xlarge_profile.put(ARCHITECTURE, componentHashMaker("x86_64", "label"));
		defaultProfiles.put("m1.xlarge", m1_xlarge_profile);
		
		HashMap<String,HashMap<String,String>> c1_medium_profile = new HashMap<String,HashMap<String,String>>();
		c1_medium_profile.put(MEMORY, componentHashMaker("1740.8", "MB"));
		c1_medium_profile.put(VIRTUAL_CPU, componentHashMaker("5.0", "count"));
		c1_medium_profile.put(STORAGE, componentHashMaker("350.0", "GB"));
		c1_medium_profile.put(ARCHITECTURE, componentHashMaker("i386", "label"));
		defaultProfiles.put("c1.medium", c1_medium_profile);
		
		HashMap<String,HashMap<String,String>> c1_xlarge_profile = new HashMap<String,HashMap<String,String>>();
		c1_xlarge_profile.put(MEMORY, componentHashMaker("7168.0", "MB"));
		c1_xlarge_profile.put(VIRTUAL_CPU, componentHashMaker("20.0", "count"));
		c1_xlarge_profile.put(STORAGE, componentHashMaker("1690.0", "GB"));
		c1_xlarge_profile.put(ARCHITECTURE, componentHashMaker("x86_64", "label"));
		defaultProfiles.put("c1.xlarge", c1_xlarge_profile);
		
		HashMap<String,HashMap<String,String>> m2_xlarge_profile = new HashMap<String,HashMap<String,String>>();
		m2_xlarge_profile.put(MEMORY, componentHashMaker("17510.4", "MB"));
		m2_xlarge_profile.put(VIRTUAL_CPU, componentHashMaker("6.5", "count"));
		m2_xlarge_profile.put(STORAGE, componentHashMaker("420.0", "GB"));
		m2_xlarge_profile.put(ARCHITECTURE, componentHashMaker("x86_64", "label"));
		defaultProfiles.put("m2.xlarge", m2_xlarge_profile);
		
		HashMap<String,HashMap<String,String>> m2_2xlarge_profile = new HashMap<String,HashMap<String,String>>();
		m2_2xlarge_profile.put(MEMORY, componentHashMaker("35020.8", "MB"));
		m2_2xlarge_profile.put(VIRTUAL_CPU, componentHashMaker("13.0", "count"));
		m2_2xlarge_profile.put(STORAGE, componentHashMaker("850.0", "GB"));
		m2_2xlarge_profile.put(ARCHITECTURE, componentHashMaker("x86_64", "label"));
		defaultProfiles.put("m2.2xlarge", m2_2xlarge_profile);
		
		HashMap<String,HashMap<String,String>> m2_4xlarge_profile = new HashMap<String,HashMap<String,String>>();
		m2_4xlarge_profile.put(MEMORY, componentHashMaker("70041.6", "MB"));
		m2_4xlarge_profile.put(VIRTUAL_CPU, componentHashMaker("26.0", "count"));
		m2_4xlarge_profile.put(STORAGE, componentHashMaker("1690.0", "GB"));
		m2_4xlarge_profile.put(ARCHITECTURE, componentHashMaker("x86_64", "label"));
		defaultProfiles.put("m2.4xlarge", m2_4xlarge_profile);
	}
	
	public ExtendedSelenium sel(){
		return SeleniumTestScript.selenium;
	}
	
*/

