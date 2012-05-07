package com.redhat.qe.se.tests

import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeSuite
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import com.redhat.qe.se.tasks.KatelloTasks

@Test(groups = ["katello.roles"])
class RoleTests {
    @BeforeSuite
    void openSession() {
        KatelloTasks.startSession()
    }
    
	@Test(dataProvider = "roleData")
	void testRole(def arg) {        
		KatelloTasks.loginAdmin()
//		KatelloTasks.createRole(arg.role)
//		arg.permissions.each { permission -> addPermission(permission) }
//		createUser(arg.user)
//		assignRole(arg.role, arg.user)
//		allowed.each { op -> op() }
//		disallowed.each { op -> op() }
//        KatelloTasks.destroyRole(arg.role)	
	}	

	@DataProvider(name = "roleData")
	public Object[][] createRoleData() {
		return [ 
				[ [ role: [name: 'role1'] ] ]
			   ] as Object[][]
	}
	
    
    @AfterSuite
    void closeSession() {
        KatelloTasks.endSession()
    }
}
