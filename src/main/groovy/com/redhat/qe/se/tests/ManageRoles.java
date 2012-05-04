package com.redhat.qe.ce10.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.redhat.qe.auto.selenium.Element;
import com.redhat.qe.auto.selenium.ExtendedSelenium;
import com.redhat.qe.auto.testng.Assert;
import com.redhat.qe.auto.testng.TestNGUtils;
import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.ce10.data.ProviderAccount;

@Test(groups="testplan-CloudEngine:Roles")
public class ManageRoles extends SeleniumTestScript{
	
	/*public final static String SCOPE = "Scope";
	
	public final static String TT_REALM = "Realm";
	public final static String TT_PROVIDER_ACCT = "ProviderAccount";
	public final static String TT_BASE_PERM = "BasePermissionObject";
	public final static String TT_TEMPLATE = "Template";
	public final static String TT_POOL_FAM = "PoolFamily";
	public final static String TT_USER = "User";
	public final static String TT_PROVIDER = "Provider";
	public final static String TT_POOL = "Pool";
	public final static String TT_HW_PROFILE = "HardwareProfile";
	public final static String TT_INSTANCE = "Instance";
	public final static String TT_QUOTA = "Quota";
	
	public final static String ACTION_VIEW = "view";
	public final static String ACTION_USE = "use";
	public final static String ACTION_MODIFY = "modify";
	public final static String ACTION_CREATE = "create";
	public final static String ACTION_VIEW_PERMS = "view_perms";
	public final static String ACTION_SET_PERMS = "set_perms";
	
	// default roles
	public final static String DR_ADMIN = "Administrator";
	public final static String DR_HWP_ADMIN = "HWP Administrator";
	public final static String DR_INSTANCE_CNTLR = "Instance Controller";
	public final static String DR_INSTANCE_OWNER = "Instance Owner";
	public final static String DR_POOL_ADMIN = "Pool Administrator";
	public final static String DR_POOL_CREATOR = "Pool Creator";
	public final static String DR_POOL_FAM_OWNER = "Pool Family Owner";
	public final static String DR_POOL_FAM_USER = "Pool Family User";
	public final static String DR_POOL_OWNER = "Pool Owner";
	public final static String DR_POOL_USER = "Pool User";
	public final static String DR_PROV_ACCT_OWNER = "Provider Account Owner";
	public final static String DR_PROV_ACCT_USER = "Provider Account User";
	public final static String DR_PROV_ADMIN = "Provider Administrator";
	public final static String DR_PROV_CREATOR = "Provider Creator";
	public final static String DR_PROV_OWNER = "Provider Owner";
	public final static String DR_REALM_ADMIN = "Realm Administrator";
	public final static String DR_TEMPLATE_ADMIN = "Template Administrator";
	public final static String DR_TEMPLATE_OWNER = "Template Owner";
	public final static String DR_TEMPLATE_USER = "Template User";
	 
	public Map<String, HashMap<String,String[]>> roleMappings = new HashMap<String,HashMap<String,String[]>>();

	public final static String ROLE_SAVED_SUCCESSFULLY = "Role successfully saved!";
	public final static String ROLE_UPDATED_SUCCESSFULLY = "Role updated successfully!";
	
	
	
	 * 
	 * 		Test Setup
	 * 
	 
	
	
	@BeforeClass (description="Initialization for test run")
	public void intialize(){
		populateDefaultRoles();
		
		// make sure we are admin
		tasks.ifNotAdminLogoutAndInAsAdmin();
	}
	
		
	
	 * 
	 * 		Role Tests
	 * 
	 
	
	@Test(description="verify default actions", groups={"defaultRoleActions"}, dataProvider="getDefaultRoles" )	
	public void verifyDefaultRoles(String sRoleName) {
		
		HashMap<String,String[]> roleMetaData = roleMappings.get(sRoleName);
		
		// Test 1: verify no unknown target types exist on role
		TreeSet<String> uiList = tasks11.getRolesUniqueTargetTypes(sRoleName);
		TreeSet<String> knownList = new TreeSet<String>(roleMetaData.keySet());
		knownList.remove("Scope");    
		Assert.assertTrue(uiList.equals(knownList), "UI list ("+uiList+") matches known list ("+knownList+")");

		Iterator it = roleMetaData.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        String sKeyName = (String) pairs.getKey();
	        String[] sValue = (String[]) pairs.getValue();
	        
	        if (sKeyName.equalsIgnoreCase("scope")) {
	        	//Test 2: verify default's roles scope object
	        	Assert.assertTrue(tasks11.getRoleScope(sRoleName).equalsIgnoreCase(sValue[0]), sRoleName +" role has correct scope (" + sValue[0] + ")");
	        } else {
	        	//Test 3: verify count of actions for specific target type defined on a role
	        	int actionCount = tasks11.getNumberOfActionsPerTargetType(sRoleName,sKeyName);
	        	Assert.assertTrue(actionCount == sValue.length, "target type "+sKeyName+" expects "+sValue.length+" actions, found "+actionCount);
	        	
	        	//Test 4: verify specific action for specific target type defined on a role
	        	for (int z = 0; z < actionCount; z++) {
	        		Assert.assertTrue(tasks11.doesRoleHaveTargetAction(sRoleName, sKeyName, sValue[z]), "role has " + sValue[z] + " action on target type " + sKeyName);
	        	}
	        }
	    }
	}
	
	//
	// TODO:  This isn't quite complete as many feedback messages are missing from the UI.
	//
	@Test(description="field check role form", groups={"roleForm"}, dataProvider="getRoleTestObjects" )	
	public void verifyRoleForm(String testname, String sRoleName, boolean bCreate, String sNewName, boolean bRemove, boolean bSave, boolean bReset, boolean bCancel, boolean bCleanUp, String[] messages){
	
		// cleanup just incase a test failed previously
		//tasks11.deleteAllRoles();
		
		// create tests
		if (bCreate) {
			
			tasks11.createRole(sRoleName, bSave, bReset, bCancel);
			
			if (bReset) {
				Assert.assertTrue(sel().getValue(UI11.roleNameField).equals(""), "name field resetting back to empty");
			} else if (bCancel) {
				Assert.assertTrue(!tasks11.doesRoleExist(sRoleName), "role does not exist");
			}
		}	
		
		// edit tests
		if (sNewName != null) {
			tasks11.createRole(sRoleName, true, false, false);
			tasks11.editRole(sRoleName, sNewName, bSave, bReset, bCancel);
			
			if (bReset) {
				Assert.assertTrue(sel().getValue(UI11.roleNameField).equals(sRoleName), "name field resetting back to original value");
			} else if (bCancel) {
				Assert.assertTrue(tasks11.doesRoleExist(sRoleName), "role does exist");
				Assert.assertTrue(!tasks11.doesRoleExist(sNewName), "role does not exist");
			}	
		} 
		
		// remove tests
		if (bRemove) {
			tasks11.deleteRoles(sRoleName);
			Assert.assertTrue(!tasks11.doesRoleExist(sRoleName), "role does not exist");
		}
		
		// verify results
		tasks11.checkExpectedMessages(messages);
		Assert.assertTrue(! sel().isTextPresent("PGError: ERROR:") , "Postgres error not found");
			
		// cleanup
		if (bCleanUp) {
			if (tasks11.doesRoleExist(sRoleName)) {	tasks11.deleteRoles(sRoleName); }
			if ((sNewName != null) && tasks11.doesRoleExist(sNewName)) {	tasks11.deleteRoles(sNewName); }
		}
	}
	
	@Test(description="verify role search box", groups={"search"}, dataProvider="getSearchStrings" )
	public void verifyRoleSearch(String sTestName, String sSearchString, int iExpectedCount, String[] expectedProfilesToBeFound) {
		nav11.roles(true);
		
		tasks11.search(sSearchString);
		
		// check returned result count
		int uiCount = tasks11.getNumberOfRoles();
		Assert.assertTrue(uiCount == iExpectedCount, "expected value (" + Integer.toString(iExpectedCount)+ ") matches ui value (" + Integer.toString(uiCount) + ")");
		
		// verify found profile names
		if (iExpectedCount > 0) {
			for (int x=0; x < expectedProfilesToBeFound.length; x++) {
				Assert.assertTrue(sel().isElementPresent(new Element(UI.link, expectedProfilesToBeFound[x])), "search found profile named: " + expectedProfilesToBeFound[x]);
			}
		//} else {
		//	// check for error message?
		}
	}
	
	//  delete multiple custom roles
	//  error on duplicate name - create
	//  error on duplicate name - edit
	//  error on delete when nothing selected
	//  delete default roles
	//  special characters in role name?
	
	
	 * 
	 * 		Data Providers
	 * 
	 
	

	@DataProvider(name="getDefaultRoles")
	public Object[][] getDefaultRoles() {
		return TestNGUtils.convertListOfListsTo2dArray(listDefaultRoles());
	}
	protected List<List<Object>> listDefaultRoles() {
		
		List<List<Object>> ll = new ArrayList<List<Object>>();
		
		for (Entry<String, HashMap<String, String[]>> entry : roleMappings.entrySet()) {
			ll.add(Arrays.asList(new Object[]{ entry.getKey() } ));
		}
		
		return ll;	
	}
	
	
	@DataProvider(name="getRoleTestObjects")
	public Object[][] getRoleTestObjects() {
		return TestNGUtils.convertListOfListsTo2dArray(createRoleTestObjects());
	}
	protected List<List<Object>> createRoleTestObjects() {
		
		List<List<Object>> ll = new ArrayList<List<Object>>();
		String sLongName = "auto_" + tasks.generateRandomString(251);
		
        //										testname					rolename              		create   	edit (new_name)		remove   save		reset    cancel		cleanup   messages
		ll.add(Arrays.asList(new Object[]{ "create_good_role",				"auto_good", 				true,   	null,  				false,   true,   	false,   false, 	true,		new String[]{ ROLE_SAVED_SUCCESSFULLY } } ));
        ll.add(Arrays.asList(new Object[]{ "create_blankName",				"", 						true,   	null,  				false,   true,   	false,   false, 	false,		new String[]{ "" } } ));
        ll.add(Arrays.asList(new Object[]{ "create_nameTooLong", 			sLongName,  				true,   	null,				false,   true,   	false,   false,		true,		new String[]{ "" } } ));
        ll.add(Arrays.asList(new Object[]{ "edit_blankName",				"auto_role1", 				false,   	"",  				false,   true,   	false,   false, 	true,		new String[]{ "" } } ));
        ll.add(Arrays.asList(new Object[]{ "edit_nameTooLong", 				"auto_edit_nameTooLong",	false,   	sLongName,			false,   true,   	false,   false,		true,		new String[]{ "" } } ));        
        ll.add(Arrays.asList(new Object[]{ "cancel_create",					"auto_role2",				true,   	null,  				false,   false,   	false,   true, 		false,		new String[]{ "" } } ));
        ll.add(Arrays.asList(new Object[]{ "cancel_edit",					"auto_role3",				false,   	"auto_rename",		false,   false,   	false,   true, 		true,		new String[]{ "" } } ));
        ll.add(Arrays.asList(new Object[]{ "reset_create",					"auto_role4",				true,   	null,  				false,   false,   	true,    false, 	true,		new String[]{ "" } } ));
        ll.add(Arrays.asList(new Object[]{ "reset_edit",					"auto_role6",				false,   	"auto_rename",		false,   false,   	true,    false, 	true,		new String[]{ "" } } ));
        ll.add(Arrays.asList(new Object[]{ "remove",						"auto_role2remove",			true,   	null,				true,    true,   	false,   false, 	true,		new String[]{ "" } } ));
        ll.add(Arrays.asList(new Object[]{ "successfulRename", 				"auto_firstName",			true,   	"auto_renamed",		false,   true,   	false,   false,		true,		new String[]{ ROLE_UPDATED_SUCCESSFULLY } } ));
		
		return ll;	
	}
	
	@DataProvider(name="getSearchStrings")
	public Object[][] getSearchStrings() {
		return TestNGUtils.convertListOfListsTo2dArray(createSearchStrings());
	}
	protected List<List<Object>> createSearchStrings() {
		
		List<List<Object>> ll = new ArrayList<List<Object>>();
		
		ll.add(Arrays.asList(new Object[]{ "byFullName", 	  	"Realm Administrator",    	1, new String[]{ "Realm Administrator"}  } ));
		ll.add(Arrays.asList(new Object[]{ "caseInsensitive", 	"realm administrator",    	1, new String[]{ "Realm Administrator"}  } ));
		ll.add(Arrays.asList(new Object[]{ "byPartialName1",  	"use", 						4, new String[]{ "Pool User", "Pool Family User", "Template User", "Provider Account User"} } ));
		ll.add(Arrays.asList(new Object[]{ "byPartialName2",  	"pool", 					6, new String[]{ "Pool User", "Pool Owner", "Pool Family User", "Pool Creator", "Pool Family Owner", "Pool Administrator" } } ));
		ll.add(Arrays.asList(new Object[]{ "containsAllWords1",	"realm pool", 				0, null } ));
		ll.add(Arrays.asList(new Object[]{ "containsAllWords2",	"provider owner", 			2, new String[]{ "Provider Owner", "Provider Account Owner"} } ));
		ll.add(Arrays.asList(new Object[]{ "scopeNotIncluded",	"BasePermissionObject",		0, null } ));
		
//		List<String> allRoles = new ArrayList<String>();
//		for (Entry<String, HashMap<String, String[]>> entry : roleMappings.entrySet()) {
//			allRoles.add( entry.getKey() );
//		}		
//		ll.add(Arrays.asList(new Object[]{ "emptyReturnsAll",	"", 						9, (String[]) allRoles.toArray() } ));
		
		
		//String sTooLong = tasks.generateRandomString(256);
		//ll.add(Arrays.asList(new Object[]{ "valueTooLong",		sTooLong, 			0, "expected_profile_names", "expected_error_message?" } ));
		
		// special characters don't blow it up
		// by provider?
		
		return ll;	
	}
	
	
	 * 
	 * 		Misc methods
	 * 
	 
	private void populateDefaultRoles() {
		
		HashMap<String,String[]> dr_admin_hashmap = new HashMap<String,String[]>();
		dr_admin_hashmap.put(SCOPE, 				new String[]{TT_BASE_PERM});
		dr_admin_hashmap.put(TT_REALM, 				new String[]{ACTION_USE, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_admin_hashmap.put(TT_PROVIDER_ACCT, 		new String[]{ACTION_VIEW, ACTION_USE, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_admin_hashmap.put(TT_BASE_PERM, 			new String[]{ACTION_MODIFY, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_admin_hashmap.put(TT_TEMPLATE, 			new String[]{ACTION_VIEW, ACTION_USE, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_admin_hashmap.put(TT_POOL_FAM, 			new String[]{ACTION_VIEW, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_admin_hashmap.put(TT_USER, 				new String[]{ACTION_VIEW, ACTION_MODIFY, ACTION_CREATE});
		dr_admin_hashmap.put(TT_PROVIDER, 			new String[]{ACTION_VIEW, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_admin_hashmap.put(TT_POOL, 				new String[]{ACTION_VIEW, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_admin_hashmap.put(TT_HW_PROFILE, 		new String[]{ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_admin_hashmap.put(TT_INSTANCE, 			new String[]{ACTION_VIEW, ACTION_USE, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_admin_hashmap.put(TT_QUOTA, 				new String[]{ACTION_VIEW, ACTION_MODIFY});
		roleMappings.put(DR_ADMIN, dr_admin_hashmap);
		
		HashMap<String,String[]> dr_hwpadmin_hashmap = new HashMap<String,String[]>();
		dr_hwpadmin_hashmap.put(SCOPE, 				new String[]{TT_BASE_PERM});
		dr_hwpadmin_hashmap.put(TT_HW_PROFILE, 		new String[]{ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		roleMappings.put(DR_HWP_ADMIN, dr_hwpadmin_hashmap);
		
		HashMap<String,String[]> dr_instCtrllr_hashmap = new HashMap<String,String[]>();
		dr_instCtrllr_hashmap.put(SCOPE, 			new String[]{TT_INSTANCE});
		dr_instCtrllr_hashmap.put(TT_INSTANCE, 		new String[]{ACTION_VIEW, ACTION_USE});
		roleMappings.put(DR_INSTANCE_CNTLR, dr_instCtrllr_hashmap);
		
		HashMap<String,String[]> dr_instOwner_hashmap = new HashMap<String,String[]>();
		dr_instOwner_hashmap.put(SCOPE, 			new String[]{TT_INSTANCE});
		dr_instOwner_hashmap.put(TT_INSTANCE, 		new String[]{ACTION_VIEW, ACTION_USE, ACTION_MODIFY, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		roleMappings.put(DR_INSTANCE_OWNER, dr_instOwner_hashmap);
		
		HashMap<String,String[]> dr_poolAdmin_hashmap = new HashMap<String,String[]>();
		dr_poolAdmin_hashmap.put(SCOPE, 			new String[]{TT_BASE_PERM});
		dr_poolAdmin_hashmap.put(TT_POOL, 			new String[]{ACTION_VIEW, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_poolAdmin_hashmap.put(TT_POOL_FAM, 		new String[]{ACTION_VIEW, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_poolAdmin_hashmap.put(TT_INSTANCE, 		new String[]{ACTION_VIEW, ACTION_USE, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_poolAdmin_hashmap.put(TT_QUOTA, 			new String[]{ACTION_VIEW, ACTION_MODIFY});
		roleMappings.put(DR_POOL_ADMIN, dr_poolAdmin_hashmap);

		HashMap<String,String[]> dr_poolCreator_hashmap = new HashMap<String,String[]>();
		dr_poolCreator_hashmap.put(SCOPE, 			new String[]{TT_BASE_PERM});
		dr_poolCreator_hashmap.put(TT_POOL, 		new String[]{ACTION_CREATE});
		roleMappings.put(DR_POOL_CREATOR, dr_poolCreator_hashmap);
		
		HashMap<String,String[]> dr_poolFamOwner_hashmap = new HashMap<String,String[]>();
		dr_poolFamOwner_hashmap.put(SCOPE, 			new String[]{TT_POOL_FAM});
		dr_poolFamOwner_hashmap.put(TT_POOL_FAM, 	new String[]{ACTION_VIEW, ACTION_MODIFY, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_poolFamOwner_hashmap.put(TT_POOL, 		new String[]{ACTION_VIEW, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		roleMappings.put(DR_POOL_FAM_OWNER, dr_poolFamOwner_hashmap);
		
		HashMap<String,String[]> dr_poolFamUser_hashmap = new HashMap<String,String[]>();
		dr_poolFamUser_hashmap.put(SCOPE, 			new String[]{TT_POOL_FAM});
		dr_poolFamUser_hashmap.put(TT_POOL, 		new String[]{ACTION_VIEW});
		roleMappings.put(DR_POOL_FAM_USER, dr_poolFamUser_hashmap);

		HashMap<String,String[]> dr_poolOwner_hashmap = new HashMap<String,String[]>();
		dr_poolOwner_hashmap.put(SCOPE, 			new String[]{TT_POOL});
		dr_poolOwner_hashmap.put(TT_POOL, 			new String[]{ACTION_VIEW, ACTION_MODIFY, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_poolOwner_hashmap.put(TT_INSTANCE, 		new String[]{ACTION_VIEW, ACTION_USE, ACTION_MODIFY, ACTION_CREATE});
		dr_poolOwner_hashmap.put(TT_QUOTA, 			new String[]{ACTION_VIEW});
		roleMappings.put(DR_POOL_OWNER, dr_poolOwner_hashmap);
		
		HashMap<String,String[]> dr_poolUser_hashmap = new HashMap<String,String[]>();
		dr_poolUser_hashmap.put(SCOPE, 				new String[]{TT_POOL});
		dr_poolUser_hashmap.put(TT_POOL, 			new String[]{ACTION_VIEW});
		dr_poolUser_hashmap.put(TT_INSTANCE, 		new String[]{ACTION_CREATE});
		dr_poolUser_hashmap.put(TT_QUOTA, 			new String[]{ACTION_VIEW});
		roleMappings.put(DR_POOL_USER, dr_poolUser_hashmap);

		HashMap<String,String[]> dr_provAcctOwner_hashmap = new HashMap<String,String[]>();
		dr_provAcctOwner_hashmap.put(SCOPE, 		new String[]{TT_PROVIDER_ACCT});
		dr_provAcctOwner_hashmap.put(TT_PROVIDER_ACCT, new String[]{ACTION_VIEW, ACTION_USE, ACTION_MODIFY, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		roleMappings.put(DR_PROV_ACCT_OWNER, dr_provAcctOwner_hashmap);
		
		HashMap<String,String[]> dr_provAcctUser_hashmap = new HashMap<String,String[]>();
		dr_provAcctUser_hashmap.put(SCOPE, 			new String[]{TT_PROVIDER_ACCT});
		dr_provAcctUser_hashmap.put(TT_PROVIDER_ACCT, 	new String[]{ACTION_VIEW, ACTION_USE});
		roleMappings.put(DR_PROV_ACCT_USER, dr_provAcctUser_hashmap);
		
		HashMap<String,String[]> dr_provAdmin_hashmap = new HashMap<String,String[]>();
		dr_provAdmin_hashmap.put(SCOPE, 			new String[]{TT_BASE_PERM});
		dr_provAdmin_hashmap.put(TT_PROVIDER_ACCT,		new String[]{ACTION_VIEW, ACTION_USE, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_provAdmin_hashmap.put(TT_PROVIDER, 		new String[]{ACTION_VIEW, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		roleMappings.put(DR_PROV_ADMIN, dr_provAdmin_hashmap);
		
		HashMap<String,String[]> dr_provCreator_hashmap = new HashMap<String,String[]>();
		dr_provCreator_hashmap.put(SCOPE, 			new String[]{TT_BASE_PERM});
		dr_provCreator_hashmap.put(TT_PROVIDER, 	new String[]{ACTION_CREATE});
		roleMappings.put(DR_PROV_CREATOR, dr_provCreator_hashmap);
		
		HashMap<String,String[]> dr_provOwner_hashmap = new HashMap<String,String[]>();
		dr_provOwner_hashmap.put(SCOPE, 			new String[]{TT_PROVIDER});
		dr_provOwner_hashmap.put(TT_PROVIDER_ACCT,		new String[]{ACTION_VIEW, ACTION_USE, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		dr_provOwner_hashmap.put(TT_PROVIDER, 		new String[]{ACTION_VIEW, ACTION_MODIFY, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		roleMappings.put(DR_PROV_OWNER, dr_provOwner_hashmap);
		
		HashMap<String,String[]> dr_realmAdmin_hashmap = new HashMap<String,String[]>();
		dr_realmAdmin_hashmap.put(SCOPE, 			new String[]{TT_BASE_PERM});
		dr_realmAdmin_hashmap.put(TT_REALM, 		new String[]{ACTION_USE, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		roleMappings.put(DR_REALM_ADMIN, dr_realmAdmin_hashmap);
		
		HashMap<String,String[]> dr_templAdmin_hashmap = new HashMap<String,String[]>();
		dr_templAdmin_hashmap.put(SCOPE, 			new String[]{TT_BASE_PERM});
		dr_templAdmin_hashmap.put(TT_TEMPLATE, 		new String[]{ACTION_VIEW, ACTION_USE, ACTION_MODIFY, ACTION_CREATE, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		roleMappings.put(DR_TEMPLATE_ADMIN, dr_templAdmin_hashmap);
		
		HashMap<String,String[]> dr_templOwner_hashmap = new HashMap<String,String[]>();
		dr_templOwner_hashmap.put(SCOPE, 			new String[]{TT_TEMPLATE});
		dr_templOwner_hashmap.put(TT_TEMPLATE, 		new String[]{ACTION_VIEW, ACTION_USE, ACTION_MODIFY, ACTION_VIEW_PERMS, ACTION_SET_PERMS});
		roleMappings.put(DR_TEMPLATE_OWNER, dr_templOwner_hashmap);
		
		HashMap<String,String[]> dr_templUser_hashmap = new HashMap<String,String[]>();
		dr_templUser_hashmap.put(SCOPE, 			new String[]{TT_TEMPLATE});
		dr_templUser_hashmap.put(TT_TEMPLATE, 		new String[]{ACTION_VIEW, ACTION_USE});
		roleMappings.put(DR_TEMPLATE_USER, dr_templUser_hashmap);	
	}
	
	public ExtendedSelenium sel(){
		return SeleniumTestScript.selenium;
	}*/
}
