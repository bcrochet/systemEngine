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
    
    // Providers
    //public LocatorStrategy providerNameLink =   new LocatorTemplate("providerNameLink", "//input[@name='provider_selected[]']/following-sibling::a[normalize-space(.)='$1']");    //  this is specific to the new ui and may really be overkill  - DJ- 110106
    
    public LocatorStrategy providerNameLink =   new LocatorTemplate("providerNameLink", '''//a[normalize-space(.)='$1']''');
    public LocatorStrategy providerNameLinkCheckBox =   new LocatorTemplate("providerNameLinkCheckBox", '''//a[normalize-space(.)='$1']/preceding-sibling::input[@name='provider_selected[]']''');
    public LocatorStrategy providerAccountEditLink =   new LocatorTemplate("providerAccountEditLink",   '''//td[normalize-space(.)='$1']/..//a[normalize-space(.)='Edit']''');
    public LocatorStrategy providerAccountDeleteLink = new LocatorTemplate("providerAccountDeleteLink", '''//td[normalize-space(.)='$1']/..//a[normalize-space(.)='Delete']''');
    
    // Templates
    public LocatorStrategy templateTableContainer                   = new LocatorTemplate("templateTableContainer"                   ,'''//div[@class='$1']''');
    public LocatorStrategy templateFilterActionCheckBox             = new LocatorTemplate("templateFilterActionCheckBox"             ,'''//ul[@class='filters']/li/input[@id='$1']''');
    public LocatorStrategy templateSelectedPackageName              = new LocatorTemplate("templateSelectedPackageName"              ,'''//input[@class='remove_package' and @id='$1']'''); //$1="remove_package_pkg"
    		//"//a[normalize-space(@id)='package_$1']"); // Replace "//div[@class='packagewrap' and position()='$1']/a[@class='packagename']''');
    public LocatorStrategy templateCollectionGroupCheckBox          = new LocatorTemplate("templateCollectionGroupCheckBox"          ,'''//label[normalize-space(.)='$1']/../input[@type='checkbox']''');
    public LocatorStrategy templateCollectionGroupRpmMemberCheckBox = new LocatorTemplate("templateCollectionGroupRpmMemberCheckBox" ,'''//label[normalize-space(.)='$1']/../following-sibling::ul/li/input[@value='$2']''');
    
    
    //Image Building
    public LocatorStrategy runningBuildRow = new LocatorTemplate("runningBuildRow",'''//h1[text()='Running']/following-sibling::table//tr[td[normalize-space(.)='$1' and position()='$2']]'''); 
    public LocatorStrategy completedBuildRow = new LocatorTemplate("completedBuildRow",'''//h1[text()='Completed']/following-sibling::table//tr[td[normalize-space(.)='$1' and position()='$2']]''');
    public LocatorStrategy failedBuildRow = new LocatorTemplate("failedBuildRow",'''//h1[text()='Failed']/following-sibling::table//tr[td[normalize-space(.)='$1' and position()='$2']]''');
    
    public LocatorStrategy runningBuildRowTwoColumn = new LocatorTemplate("runningBuildRow",'''//h1[text()='Running']/following-sibling::table//tr[td[normalize-space(.)='$1' and position()='$2'] and td[normalize-space(.)='$3' and position()='$4']]'''); 
    public LocatorStrategy completedBuildRowTwoColumn = new LocatorTemplate("completedBuildRow",'''//h1[text()='Completed']/following-sibling::table//tr[td[normalize-space(.)='$1' and position()='$2'] and td[normalize-space(.)='$3' and position()='$4']]''');
    public LocatorStrategy failedBuildRowTwoColumn = new LocatorTemplate("failedBuildRow",'''//h1[text()='Failed']/following-sibling::table//tr[td[normalize-space(.)='$1' and position()='$2'] and td[normalize-space(.)='$3' and position()='$4']]''');
    
    //public LocatorStrategy instanceBuildStatus = new LocatorTemplate("instanceBuildStatus","//div[@id='details-view']//tr/td[1 and normalize-space(.)='$1']/following-sibling::td[4 and normalize-space(.)='$2']");
    //public LocatorStrategy instanceBuildStatus = new LocatorTemplate("instanceBuildStatus","//div[@id='ui-tabs-1']//tr/td[normalize-space(.)='$1' and position()='$2']/following-sibling::td[normalize-space(.)='$3' and position()='$4']");
    public LocatorStrategy templateBuildStatus = new LocatorTemplate("instanceBuildStatus",'''//div[@id='ui-tabs-2']/ul/li/form[normalize-space(.)='$1']''');
    public LocatorStrategy templateUploadStatus = new LocatorTemplate("instanceBuildStatus",'''//div[@id='ui-tabs-2']/ul/li/ul/li/form[normalize-space(.)='$1']''');
    public LocatorStrategy twoRelativeColumns = new LocatorTemplate("twoRelativeColumns",'''//tr/td[normalize-space(.)='$1' and position()='$2']/following-sibling::td[normalize-space(.)='$3' and position()='$4']''');
    public LocatorStrategy oneRelativeColumn = new LocatorTemplate("oneRelativeColumn",'''//tr/td[normalize-space(.)='$1' and position()='$2']/following-sibling::td[position()='$3']''');
    public LocatorStrategy fiveRelativeColumns = new LocatorTemplate("fiveRelativeColumns",'''//tr/td[normalize-space(.)='$1' and position()='$2']''' +
    		'''/following-sibling::td[normalize-space(.)='$3' and position()='1']'''+
    		'''/following-sibling::td[normalize-space(.)='$4' and position()='1']'''+
    		'''/following-sibling::td[normalize-space(.)='$5' and position()='1']'''+
    		'''/following-sibling::td[normalize-space(.)='$6' and position()='1']''');
    
    //Instance Details
    /*
     * Locator for instance details:
     * name, status, ip_address, operating_system, provider, base_template, architecture,memory
     * storage, instantiation_time, uptime, current_alerts, console_connection, owner, shared_to
     */
    public LocatorStrategy instanceDetailsVerify = new LocatorTemplate("instanceDetails",'''//label[@for='$1']/following-sibling::span[contains(.,'$2')]''');
    public LocatorStrategy instanceDetailsGet = new LocatorTemplate("instanceDetails",'''//label[@for='$1']/following-sibling::span''');
    public LocatorStrategy instanceDetailsGetKey = new LocatorTemplate("instanceDetails",'''//label[@for='$1']/following-sibling::span/a''');
    
    //providers
    public LocatorStrategy providerHeader = new LocatorTemplate("providerHeader",'''//h1[@Class='enabled' and normalize-space(.)='$1']''');
    
    //Catalog Entries
    public LocatorStrategy ceHeader = new LocatorTemplate("ceHeader",'''//h1[@Class='catalogs' and normalize-space(.)='$1']''');
    
    //messages
    public LocatorStrategy notificationMessage = new LocatorTemplate("notification",'''//li[normalize-space(.)=$1''');
    
    
}
