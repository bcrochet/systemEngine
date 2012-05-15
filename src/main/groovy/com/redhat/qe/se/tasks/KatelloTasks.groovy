package com.redhat.qe.se.tasks

import java.util.concurrent.TimeUnit

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.PageFactory

import com.redhat.qe.se.pages.LoginPage
import com.redhat.qe.se.pages.RolesPage
import com.redhat.qe.se.pages.RolesPermissionsPage
import com.redhat.qe.se.pages.UsersPage

class KatelloTasks {
	static private WebDriver driver = new FirefoxDriver()

    static private def waitForAjax(def timeout = 5000) {
        def jsCondition = "selenium.browserbot.getCurrentWindow().jQuery.active == 0"
        
        //driver.wait_for_condition(js_condition, timeout)
	}
    
    static def startSession() {
        driver.manage().timeouts().implicitlyWait( 10000, TimeUnit.MILLISECONDS )
        driver.get("https://bcrochet-katello.usersys.redhat.com/katello")
    }
    
	static def loginAdmin() {
		def loginPage = PageFactory.initElements(driver, LoginPage.class)
		loginPage.loginAs("admin", "admin")
	}
	
	static def createRole(def role) {
        def rolesPage = PageFactory.initElements(driver, RolesPage.class)
        rolesPage.navigate()
        rolesPage.createNewRole(role.name, role.description) 
	}
    
    static def destroyRole(def role) {
        driver.get("https://bcrochet-katello.usersys.redhat.com/katello/roles")
        WebElement roleElement = driver.findElementByXPath("//div[@id='list']//div[starts-with(normalize-space(.),'${role.name}')]")
        //WebElement roleElement = driver.findElementByXPath( "//span/text()[normalize-space()='${role.name}']/parent::node()/parent::node()" )
        roleElement.click()
        WebElement deleteDiv = driver.findElementById( "remove_role" )
        deleteDiv.click()
        def yesButton = driver.findElementByXPath("//span[@class='ui-button-text']='Yes'")
        yesButton.click()
    }
    
    static def selectRole(def role) {
        def rolesPage = PageFactory.initElements( driver, RolesPage.class )
        rolesPage.selectRole(role.name)
    }
    
    static def addPermission(def permission) {
        def rolesPermissionsPage = PageFactory.initElements( driver, RolesPermissionsPage.class )
        rolesPermissionsPage.addPermission(permission)
    }
    
    static def createUser(def user) {
        def usersPage = PageFactory.initElements( driver, UsersPage.class )
        usersPage.navigate()
        usersPage.createNewUser(user.name, user.password, user.email, user.org)
    }
    
    static def assignRole(def user, def role) {
        def usersPage = PageFactory.initElements( driver, UsersPage.class )
        usersPage.navigate()
        usersPage.assignUserToRole(user.name, role.name)
    }
    
    static def endSession() {
        driver.close()
    }
    
    static def selectOrg = { org ->
        // Select the org
    }
    
    static def selectOrgSwitcher = { org ->
        // Select the org from the switcher
    }
    
    static def navigateToAdministrationTab = { ->
        // Navigate to Administration tab
    }
    
    static def navigateToSystemsTab = { ->
        // Navigate to Systems tab
    }
}

