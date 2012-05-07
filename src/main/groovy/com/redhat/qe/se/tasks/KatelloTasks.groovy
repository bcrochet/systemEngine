package com.redhat.qe.se.tasks

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.PageFactory

import com.redhat.qe.se.pages.LoginPage

class KatelloTasks {
	static private WebDriver driver = new FirefoxDriver()

    static private def waitForAjax(def timeout = 5000) {
        def jsCondition = "selenium.browserbot.getCurrentWindow().jQuery.active == 0"
        
        //driver.wait_for_condition(js_condition, timeout)
	}
    
    static def startSession() {
        driver.get("https://bcrochet-katello.usersys.redhat.com/katello")
    }
    
	static def loginAdmin() {
		def loginPage = PageFactory.initElements(driver, LoginPage.class)
		loginPage.loginAs("admin", "admin")
	}
	
	static def createRole(def role) {
		//WebElement rolesTab = driver.findElement(By.xpath("//a[@href='/katello/roles']"))
		//rolesTab.click()
		driver.get("https://bcrochet-katello.usersys.redhat.com/katello/roles")
		WebElement newRoleLink = driver.findElementByCssSelector("a#new.block.fr")
		newRoleLink.click()
        System.sleep( 3000 )
		WebElement newRoleName = driver.findElementById("role_name")
		newRoleName.sendKeys(role.name)
		WebElement newRoleSave = driver.findElementById("role_save")
        newRoleSave.click()
        System.sleep( 3000 )
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
    
    static def endSession() {
        driver.close()
    }
}

