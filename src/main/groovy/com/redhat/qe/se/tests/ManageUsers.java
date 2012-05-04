package com.redhat.qe.ce10.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.redhat.qe.auto.testng.Assert;
import com.redhat.qe.auto.testng.TestNGUtils;
import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.ce10.data.Roles;
import com.redhat.qe.ce10.data.Users;

@Test(groups="testplan-CloudEngine:users")
public class ManageUsers extends SeleniumTestScript{
	
Roles role = new Roles(Roles.Instance_Owner);

	
	@Test(description = "test w/ dataProvider",dataProvider="getUserDataObjects")
	public void createUser(String testName, Users user){
		user.create();
		if(user.isError()){
			for(String s:user.getCreateEditErrors()){
				Assert.assertTrue(selenium.isTextPresent(s),"Asserted error message"+ s +"found"); 
			}
		}
	}
	
	@Test(description = "test w/ dataProvider",dataProvider="getUserDataObjects")
	public void editUser(String testName, Users user){
		Users origUser = new Users(5, role, true);
		origUser.editUser(user);
		if(origUser.isError()){
			for(String s:origUser.getCreateEditErrors()){
				Assert.assertTrue(selenium.isTextPresent(s),"Asserted error message"+ s +"found");
			}
		}
	}
	
	

    @DataProvider(name="getUserDataObjects")
    public Object[][] getEditUserDataObjectsAs2dArray() {
        return TestNGUtils.convertListOfListsTo2dArray(getEditUserObject());
    }
    protected List<List<Object>> getEditUserObject() {
    	Users control = new Users(5, role, false);
    	Users passwdconfm = new Users(5, role, false);
    		passwdconfm.set_password("password");
    		passwdconfm.set_confirmPassword("asdf");
    	Users email = new Users(5,"asdf.com",role,false);
    	
        
        List<List<Object>> ll = new ArrayList<List<Object>>();
        //                                  
        ll.add(Arrays.asList(new Object[]{ "control, edit works", control} ));
        ll.add(Arrays.asList(new Object[]{ "fail passwd confirmation", passwdconfm} ));
        ll.add(Arrays.asList(new Object[]{ "malformed email", email} ));
        return ll;
    }
    

   
}