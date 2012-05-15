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
		KatelloTasks.createRole(arg.role)
        KatelloTasks.selectRole(arg.role)
		arg.permissions.each { permission -> KatelloTasks.addPermission(permission) }
		KatelloTasks.createUser(arg.user)
        KatelloTasks.assignRole(arg.user, arg.role)
        // Add code to wrap each call to catch and log exception
		arg.allowed.each { op -> op() }
		arg.disallowed.each { op -> op() }
        KatelloTasks.destroyRole(arg.role)	
	}	

	@DataProvider(name = "roleData")
	public Object[][] createRoleData() {
		return [ 
				[
                 [ role: [name: 'role1', description: 'role1 description'], 
                   permissions: [ 
                                 [
                                  name: 'perm1',
                                  org: "Global Permissions", 
                                  permissionFor: "Organizations", 
                                  verbs: [ "Read Organization"] 
                                 ] 
                                ],
                   user: [name:'user1', password: 'password', email: 'user1@example.com', org: ''],
                   allowed: [ 
                              KatelloTasks.selectOrg.curry("MyOrg"), 
                              KatelloTasks.selectOrgSwitcher.curry("MyOrg") 
                            ],
                   disallowed: [
                                 KatelloTasks.navigateToAdministrationTab,
                                 KatelloTasks.navigateToSystemsTab                               
                               ]
                 ]
                ]
			   ] as Object[][]
	}
	
    
    @AfterSuite
    void closeSession() {
        KatelloTasks.endSession()
    }
}
