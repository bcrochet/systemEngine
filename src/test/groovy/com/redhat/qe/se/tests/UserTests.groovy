package com.redhat.qe.se.tests


class UserTests {
	def testCreateUser() { 
		createUser {
			uniqueify "autouser"
		}
	}
	
}
