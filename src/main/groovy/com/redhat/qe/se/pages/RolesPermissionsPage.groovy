package com.redhat.qe.se.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.Select

class RolesPermissionsPage {
    @FindBy(id="add_permission")
    private WebElement addPermissionLink
    
    @FindBy(id="role_permissions")
    private WebElement permissionsLink
    
    @FindBy(id="roles_tree")
    private WebElement roleList
    
    @FindBy(name="permission[resource_type_attributes[name]]")
    private WebElement resourceType
    
    @FindBy(id="next_button")
    private WebElement nextButton
    
    @FindBy(name="permission[verb_values][]")
    private WebElement verbs
    
    @FindBy(name="permission[name]")
    private WebElement permissionName
    
    @FindBy(id="save_permission_button")
    private WebElement savePermissionButton
    
    def addPermission(def permission) {
        sleep(2000)
        drillDownPermissions()
        selectOrganization(permission.org)
        addPermissionLink.click()
        Select selectResourceType = new Select(resourceType)
        selectResourceType.selectByVisibleText(permission.permissionFor)
        nextButton.click()
        Select selectVerbs = new Select(verbs)
        selectVerbs.deselectAll()
        permission.verbs.each { verb -> selectVerbs.selectByVisibleText( verb ) }
        nextButton.click()
        permissionName.sendKeys( permission.name )
        savePermissionButton.click()
    }
    
    private def selectOrganization(def org) {
        WebElement orgLink = roleList.findElement( By.xpath("//li[@class='slide_link' and starts-with(normalize-space(.),'${org}')]") )
        orgLink.click()
    }

    private def drillDownPermissions() {
        permissionsLink.click()        
    }
}
