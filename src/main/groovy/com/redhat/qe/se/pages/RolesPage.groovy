package com.redhat.qe.se.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class RolesPage extends AdministrationTab {
    @FindBy(id="new")
    private WebElement newRoleLink
    
    @FindBy(name="role[name]")
    private WebElement roleNameText
    
    @FindBy(name="role[description]")
    private WebElement roleDescriptionText
    
    @FindBy(id="role_save")
    private WebElement roleSaveButton
    
    @FindBy(linkText="Roles")
    private WebElement rolesTab
    
    def navigate() {
        administrationTab.click()
        rolesTab.click()
    }
    
    def createNewRole(def roleName, def roleDescription) {
        newRoleLink.click()
        roleNameText.sendKeys(roleName)
        roleDescriptionText.sendKeys(roleDescription)
        roleSaveButton.click()
        sleep(2000)
    }
    
    def selectRole(def roleName) {
        selectLeftListItem(roleName)
    }
}
