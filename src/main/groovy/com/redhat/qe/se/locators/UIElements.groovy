package com.redhat.qe.se.locators


import java.lang.reflect.Array

import com.redhat.qe.auto.selenium.Element
import com.redhat.qe.auto.selenium.LocatorStrategy
import com.redhat.qe.auto.selenium.LocatorTemplate
import com.redhat.qe.auto.selenium.TabElement
import com.redhat.qe.se.base.SeleniumTestScript

public class UIElements extends UILocatorStrategies{
    //NAVIGATION
    //    def tabs = [ "Dashboard", "Content Management", "Systems", "Organizations", "Administration"]
    //    TabElement dashboard = new TabElement(notSelectedNavLink, selectedNavLink, "Adminstration")
    //    def administrationSubtabs = [ "Users", "Roles" ]

    def roles = [ 
        newRole : "//a[@id='new']",
        newRoleNameText : "role[name]",
        newRoleDescriptionText : "role[description]",
        saveRole : "role_save",
        saveUserEdit : "save_password",
        roleUsers : "role_users",
        "next" : "next_button",
        permissionResourceTypeSelect : "permission[resource_type_attributes[name]]",
        permissionVerbSelect : "permission[verb_values][]",
        permissionTagSelect : "tags",
        permissionNameText : "permission[name]",
        permissionDescriptionText : "permission[description]",
        savePermission : "save_permission_button",
        removeRole : "remove_role",
        addPermission : "add_permission"
    ]

    def navigation = new NodeBuilder().topLevel() {
        dashboardTab(element: new Element(new LocatorTemplate("Dashboard", '''link="$1"''')))
        contentManagementTab(element: new Element(new LocatorTemplate("Content Management", '''link="$1"'''))) {
            contentProvidersTab() {
                customContentProvidersTab() {
                    newProviderPage()
                    namedProviderPage() {
                        providerProductsReposPage() {
                            namedProductPage()
                            namedRepoPage()
                        }
                        providerDetailsPage()
                        providerSubscriptionsPage()
                    }
                }
                redhatProviderTab()
            }
            syncManagementPage() {
                syncStatusPage()
                syncPlansPage() {
                    namedSyncPlanPage()
                    newSyncPlanPage()
                }
                syncSchedulePage()
            }
            promotionsPage() {
                namedEnvironmentPromotionsPage() { namedChangesetPromotionsPage() }
            }
            systemTemplatesPage() {
                namedSystemTemplatePage()
                newSystemTemplatePage()
            }
        }
        systemsTab(element: new Element(new LocatorTemplate("Systems", '''link="$1"'''))) {
            systemsAllPage() {
                systemsByEnvironmentPage() {
                    namedSystemsPage() { systemSubscriptionsPage() }
                }
            }
            activationKeysPage() {
                namedActivationKeyPage()
                newActivationKeyPage()
            }
            systemsEnvironmentPage() { namedSystemEnvironmentPage() }
        }
        organizationsTab(element: new Element( new LocatorTemplate("Organizations", '''link="$1"'''))) {
            newOrganizationPage()
            namedOrganizationPage() {
                newEnvironmentPage()
                namedEnvironmentPage()
            }
        }
        administrationTab(element: new Element( new LocatorTemplate("Administration", '''link="$1"'''))) {
            usersTab() {
                namedUserPage() {
                    userEnvironmentsPage()
                    userRolesPermissionsPage()
                }
            }
            rolesTab(element: new Element( new LocatorTemplate("Roles", '''link="$1"'''))) {
                namedRolePage() {
                    namedRoleUsersPage()
                    namedRolePermissionsPage()
                }
            }
        }
    }

    def template = { descr, templ ->
        { args ->
            new Element(new LocatorTemplate(descr, templ), args.asType( Array.class ))
        } 
    }
    
//    def defineStrategies = { Map m ->
//        m.each { key, value ->
//            this.metaClass. "${key}" = template(key, value)
//        }
//    }
    
    def addRepository = template("Add Repository", '''//div[@id='products']//div[contains(.,'$1')]/..//div[normalize-space(.)='Add Repository' and contains(@class, 'button')]''')
    def buttonDiv = template("Button", '''//div[contains(@class,'button') and normalize-space(.)='$1']''')

    //         changeset ["Changeset"
    //                    "//div[starts-with(@id,'changeset_') and normalize-space(.)='$1']"]
    //         changeset-status ["Changeset status"  "//span[.='$1']/..//span[@class='changeset_status']"]
    //         editable ["Editable" "//div[contains(@class, 'editable') and descendant::text()[substring(normalize-space(),2)='$1']]"]
    //
    //         environment-link ["Environment"
    //                           "//div[contains(@class,'jbreadcrumb')]//a[normalize-space(.)='$1']"]
    //
    //         link ["" "link=$1"]
    //         notification-close-index ["Notification close button"
    //                                   "xpath=(//div[contains(@class,'jnotify-notification-error')]//a[@class='jnotify-close'])[$1]"]
    //         org-switcher ["Org switch list" "//div[@id='orgbox']//a[.='$1']"]
    //         permission-org ["Permission Org" "//li[@class='slide_link' and starts-with(normalize-space(.),'$1')]"]
    //
    //         plus-icon ["Plus icon" "//li[.='$1']//span[contains(@class,'ui-icon-plus')]"]
    //         product-edit ["Product edit"
    //                       "//div[@id='products']//div[contains(@data-url, 'edit') and contains(.,'$1')]"]
    //         product-expand ["Expand product"
    //                         "//div[@id='products']//div[contains(@data-url,'products') and contains(.,'$1')]/..//img[@alt='Expand']"]
    //         product-schedule ["Schedule for product" "//div[normalize-space(.)='$1']/following-sibling::div[1]"]
    //         schedule ["Product to schedule" "//div[normalize-space(.)='$1']"]
    //         promotion-add-content-item ["Add Content Item"
    //                                     "//a[@data-display_name='$1' and contains(.,'Add')]"]
    //         promotion-content-category ["Content Category" "//div[@id='$1']"]
    //         promotion-content-item-n ["Content item by index"
    //                                  "//div[@id='list']//li[$1]//div[contains(@class,'simple_link')]/descendant::text()[(position()=0 or parent::span) and string-length(normalize-space(.))>0]"]
    //         promotion-remove-content-item ["Remove Content Item"
    //                                        "//a[@data-display_name='$1' and contains(.,'Remove')]"]
    //         provider-sync-checkbox ["Provider sync checkbox"
    //                                 "//table[@id='products_table']//label[normalize-space(.)='$1']/..//input"]
    //         provider-sync-progress ["Provider progress"
    //                                 "//tr[td/label[normalize-space(.)='$1']]/td[5]"]
    //         repo-enable-checkbox ["Repo enable checkbox" "//table[@id='products_table']//label[normalize-space(.)='$1']/..//input"]
    //         role-action ["Role action" "//li[.//span[@class='sort_attr' and .='$2']]//a[.='$1']"]
    //         slide-link ["Slide Link" "//li[contains(@class,'slide_link') and normalize-space(.)='$1']"]
    //         subscription-checkbox ["Subscription checkbox" "//div[@id='panel-frame']//td[contains(normalize-space(.),'$1')]//input[@type='checkbox']"]
    //         sync-plan ["Sync Plan" "//div[@id='plans']//div[normalize-space(.)='$1']"]
    //         tab ["Tab" "link=$1"]
    //         template-product ["Template product" "//span[contains(@class, 'custom-product-sprite')]/following-sibling::span/text()[contains(.,'$1')]"]
    //         template-action ["Template content action" "//a[@data-name='$2' and .='$1']"]
    //         template-eligible-category ["Template category" "//div[@id='content_tree']//div[normalize-space()='$1']"]
    //         textbox ["" "xpath=//*[self::input[(@type='text' or @type='password' or @type='file') and @name='$1'] or self::textarea[@name='$1']]"]
    //         user ["User" "//div[@id='list']//div[contains(@class,'column_1') and normalize-space(.)='$1']"]
    //         username-field ["Username field" "//div[@id='users']//div[normalize-space(.)='$1']"]
    //         left-pane-field-list ["Left pane item#" "xpath=(//div[contains(@class,'ellipsis')])[$1]"]})
    //

    //GENERIC
    public Element checkboxGeneric = new Element(type,"checkbox")

    // used to recover from significant errors like a pgError exception
    public static final String url_base = SeleniumTestScript.ceServerPath

    //Login Page
    public Element LoginUserName = new Element(name,"username")
    public Element LoginUserPasswd = new Element(name,"password")
    //public Element Login = new Element(id,"user_session_submit");
    public Element Login = new Element(button,"Login")
    public Element SelfServiceCreateAccount = new Element("//a[@class='actionlink']")

    public Element SSLUnderstandRisks = new Element("//*[normalize-space(@id)='expertContentHeading']")
    public Element SSLExceptionAccept = new Element("//*[normalize-space(@id)='exceptionDialogButton']")
    //End Login Page


    //Dashboard
    public TabElement Dashboard = new TabElement(notSelectedNavLink, selectedNavLink, "Dashboard")
    //public TabElement InstanceManagement = new TabElement(notSelectedNavLink, selectedNavLink,"Instance Management");
    public Element Templates_static = new Element(link,"Templates")
    //public TabElement Templates = new TabElement(notSelectedNavLink, selectedNavLink,"Deployables");
    public TabElement PoolsZones = new TabElement(notSelectedNavLink, selectedNavLink,"Pools & Zones")
    public TabElement Settings = new TabElement(notSelectedNavLink, selectedNavLink,"System Settings")
    public TabElement Builds = new TabElement(notSelectedNavLink, selectedNavLink,"Builds")

    //public Element Summary = new Element(link,"Summary");
    //public Element Alerts = new Element(link,"Alerts");
    //public Element ServiceQuality = new Element(link,"Service Quality");
    //public Element QuotaUsage = new Element(link,"Quota Usage");
    //public Element Billing = new Element(link,"Billing");
    //public Element HelpTickets = new Element(link,"Help Tickets");
    //End Dashboard

    //Permissions
    public Element quota_self_service = new Element(id,"self_service_default_quota_maximum_running_instances")
    //End Permissions

    //Manage Users
    //public Element createUser = new Element(link,"create");
    public Element chooseUsername = new Element(id,"user_login")
    public Element choosePassword = new Element(id,"user_password")
    public Element confirmPassword = new Element(id,"user_password_confirmation")
    public Element firstName = new Element(id,"user_first_name")
    public Element lastName = new Element(id,"user_last_name")
    public Element email = new Element(id,"user_email")
    public Element user_save = new Element(id,"user_submit")
    public Element user_quota_set = new Element(id,"user_quota_attributes_maximum_running_instances")

    public LocatorStrategy lsUserTableCell = new LocatorTemplate('lsUserTableCell','''//table[@id='users_table']/tbody/tr/td/a[normalize-space(.)='$1']/../../td[position()='$2']''')
    public Element accountManage = new Element("//li[@Class='account']")
    public Element logout = new Element(link,"Log out")
    public Element myAccount = new Element(link,"My Account")
    public Element userCardusername = new Element("//div[@Class='user-card-content']/dl/dd[2]")

    //public Element deleteUser = new Element(button,"delete");


    //End Manage Users

    //Roles
    public LocatorStrategy roleNameLink = new LocatorTemplate("roleNameLink", '''//table[@id='roles_table']//a[normalize-space(.)='$1']''')
    public LocatorStrategy roleTargetAction = new LocatorTemplate("roleTargetAction", '''//tr/td[normalize-space(.)='$1']/following-sibling::td[normalize-space(.)='$2']''')
    public LocatorStrategy genericActionRoleTarget = new LocatorTemplate("genericActionRoleTarget", '''//tr/td/following-sibling::td[normalize-space(.)='$1']''')
    public LocatorStrategy roleScope = new LocatorTemplate("roleScope", '''//h2[normalize-space(.)='$1']/following-sibling::strong[normalize-space(.)='Scope:']/following-sibling::span''')
    public LocatorStrategy roleCheckbox = new LocatorTemplate("roleCheckbox", '''//a[normalize-space(.)='$1']/preceding-sibling::input[@type='checkbox']''')
    public LocatorStrategy getRoleActionTargetByRowIndex = new LocatorTemplate("getRoleActionTargetByRowIndex", '''//tr[$1]/td/following-sibling::td''')
    public Element genericRoleNameLink = new Element("//table[@id='roles_table']//a")
    public String roleRow_string = "//table[@id='roles_table']//input[@name='role_selected[]']"
    public Element createNewRole = new Element(link, "New Role")
    public Element deleteRole = new Element(id, "delete_button")
    public Element roleNameField = new Element(id, "role_name")
    public Element roleFormSave = new Element("//input[@value='Save']")
    public Element roleFormReset = new Element("//input[@value='Reset']")
    public Element genericRoleCheckbox = new Element(name, "role_selected[]")
    public Element genericActionGenericRoleTarget = new Element("//tr/td/following-sibling::td")
}
