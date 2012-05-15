package com.redhat.qe.se.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class CommonPage {
	protected final WebDriver driver
	protected final def tabs = [ "dashboard":"Dashboard", "content":"Content Management", "systems":"Systems", "organizations":"Organizations", "admin":"Administration", "roles": "Roles" ]
	
	CommonPage(WebDriver driver) {
		this.driver = driver	
	}
	
	def logout() {
		def logout = driver.findElement(By.id(""))
		logout.click()
	}
	
	def goToLoginPage() {
		driver.get("https://bcrochet-katello.usersys.redhat.com/katello")
	}
	
    // Takes a tab key from the list of tabs
	def navigateToTab(def location) {
        WebElement tab = driver.findElement(By.linkText( tabs."${location}" ))
        tab.click()
	}
}
