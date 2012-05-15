package com.redhat.qe.se.locators;

import com.redhat.qe.auto.selenium.Element;
import com.redhat.qe.auto.selenium.LocatorStrategy;
import com.redhat.qe.auto.selenium.LocatorTemplate;



public class UILocatorStrategies extends com.redhat.qe.auto.selenium.UILocatorStrategies{
    
    protected static final String NEXT_TO_XPATH_PREFIX = "//td[(normalize-space(.)='";
    
  //Locator Strategies
    
    
    
    //GENERIC
    public LocatorStrategy type = new LocatorTemplate("type", '''//*[@type='$1']''');
    
    // dashboard
    public LocatorStrategy selectedNavLink    = new LocatorTemplate("selectedNavLink",    '''//a[@class = 'selected' and normalize-space(.)='$1']''');
    public LocatorStrategy notSelectedNavLink = new LocatorTemplate("notSelectedNavLink", '''//a[not(@class='selected') and normalize-space(.)='$1']''');
    
    
    //messages
    public LocatorStrategy notificationMessage = new LocatorTemplate("notification",'''//li[normalize-space(.)=$1''');
    
    
}
