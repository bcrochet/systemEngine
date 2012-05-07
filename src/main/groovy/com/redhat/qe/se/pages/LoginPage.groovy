package com.redhat.qe.se.pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class LoginPage extends CommonPage {
    @FindBy(name = "username")
	private WebElement username
    
    @FindBy(name = "password")
	private WebElement password

//	LoginPage(WebDriver driver) {
//		super(driver)
//		
//		if ( ! driver.title == "Login" ) {
//			logout()
//			goToLoginPage()
//		}
//	}	
		
	def loginAs(String u, String p) {
		username.sendKeys(u)
		password.sendKeys(p)
		username.submit()
	}
}
