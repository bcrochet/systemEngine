package com.redhat.qe.se.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class AdministrationTab {
    @FindBy(linkText="Administration")
    protected WebElement administrationTab
    
    @FindBy(xpath="//div[@id='list']")
    protected WebElement leftList
    
    protected def selectLeftListItem(def text) {
        WebElement item = findLeftListItem(text)
        item.click()
    }
    
    protected def findLeftListItem(def text) {
        return leftList.findElement( By.xpath("//div[starts-with(normalize-space(.),'${text}')]") )
    }    
}
