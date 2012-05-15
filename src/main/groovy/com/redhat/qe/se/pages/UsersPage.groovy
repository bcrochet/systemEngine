package com.redhat.qe.se.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class UsersPage extends AdministrationTab {
    @FindBy(linkText="Users")
    private WebElement usersTab
    
    @FindBy(id="new")
    private WebElement newUserLink
    
    @FindBy(name="user[username]")
    private WebElement usernameField
    
    @FindBy(id="password_field")
    private WebElement passwordField
    
    @FindBy(id="confirm_field")
    private WebElement confirmField
    
    @FindBy(name="user[email]")
    private WebElement emailField
    
    @FindBy(id="save_user")
    private WebElement saveUserButton
    
    @FindBy(xpath="//nav[contains(@class,'subnav')]//li[@id='roles' and contains(@class, 'navigation_element')]/a")
    private WebElement userRolesTab
    
    @FindBy(xpath="//div[contains(@class,'available')]")
    private WebElement availableList
    
    @FindBy(id="save_roles")
    private WebElement saveRolesButton
    
    def navigate() {
        administrationTab.click()
        usersTab.click()
    }
    
    def selectUser(def username) {
        selectLeftListItem(username)
    }    
    
    def createNewUser(String username, String password, String email, String org) {
        newUserLink.click()
        usernameField.sendKeys(username)
        passwordField.sendKeys(password)
        confirmField.sendKeys(password)
        emailField.sendKeys(email)
        saveUserButton.click()
        sleep(2000)
    }
    
    def assignUserToRole(def username, def roleName) {
        selectUser(username)
        sleep(2000)
        userRolesTab.click()
        WebElement roleLink = availableList.findElement(By.xpath( "//li[.='${roleName}']//span[contains(@class,'ui-icon-plus')]"))    
        roleLink.click()
        saveRolesButton.click()
    }
}
