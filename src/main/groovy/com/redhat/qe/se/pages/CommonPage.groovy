package com.redhat.qe.se.pages

import org.openqa.selenium.WebDriver

class CommonPage {
	protected final WebDriver driver
	protected final def tabs = [ "dashboard":"id", "content":"id", "systems":"id", "organizations":"id", "admin":"id" ]
	
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
	
	def navigateTo(def location) {
		driver.get(location)
	}
}
