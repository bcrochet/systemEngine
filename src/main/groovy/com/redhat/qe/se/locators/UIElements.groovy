package com.redhat.qe.se.locators;


import com.redhat.qe.auto.selenium.Element
import com.redhat.qe.auto.selenium.LocatorStrategy
import com.redhat.qe.auto.selenium.LocatorTemplate
import com.redhat.qe.auto.selenium.TabElement
import com.redhat.qe.se.base.SeleniumTestScript

public class UIElements extends UILocatorStrategies{
    
    public static final String arch_i386 = "i386";
    public static final String arch_x86_64 = "x86_64";
    public static final String fedora13 = "Fedora 13";
    public static final String fedora14 = "Fedora 14";
    
    //GENERIC
    public Element checkboxGeneric = new Element(type,"checkbox");
    
    // used to recover from significant errors like a pgError exception
    public static final String url_base = SeleniumTestScript.ceServerPath;
	
	//Login Page
	public Element LoginUserName = new Element(name,"login");
	public Element LoginUserPasswd = new Element(name,"password");
	//public Element Login = new Element(id,"user_session_submit");
	public Element Login = new Element(button,"Login");
	public Element SelfServiceCreateAccount = new Element("//a[@class='actionlink']");
	
	public Element SSLUnderstandRisks = new Element("//*[normalize-space(@id)='expertContentHeading']");
	public Element SSLExceptionAccept = new Element("//*[normalize-space(@id)='exceptionDialogButton']");
	//End Login Page
	
	
	//Dashboard 
	public TabElement Dashboard = new TabElement(notSelectedNavLink, selectedNavLink, "Dashboard");
	//public TabElement InstanceManagement = new TabElement(notSelectedNavLink, selectedNavLink,"Instance Management");
	public Element Templates_static = new Element(link,"Templates");
	//public TabElement Templates = new TabElement(notSelectedNavLink, selectedNavLink,"Deployables");
	public TabElement PoolsZones = new TabElement(notSelectedNavLink, selectedNavLink,"Pools & Zones");
	public TabElement Settings = new TabElement(notSelectedNavLink, selectedNavLink,"System Settings");
	public TabElement Builds = new TabElement(notSelectedNavLink, selectedNavLink,"Builds");
	
	//public Element Summary = new Element(link,"Summary");
	//public Element Alerts = new Element(link,"Alerts");
	//public Element ServiceQuality = new Element(link,"Service Quality");
	//public Element QuotaUsage = new Element(link,"Quota Usage");
	//public Element Billing = new Element(link,"Billing");
	//public Element HelpTickets = new Element(link,"Help Tickets");
	//End Dashboard
	
	
	//Settings
	//public Element manageUsersURL = new Element(home + "users");
	//public TabElement manageUsers = new TabElement(notSelectedNavLink, selectedNavLink,"Manage Users");
	public TabElement permissions = new TabElement(notSelectedNavLink, selectedNavLink,"Permissions");
	//public TabElement manageProviders = new TabElement(notSelectedNavLink, selectedNavLink,"Manage Providers");
	//public Element selfServiceSettings = new Element(nav_href,"subnav", url_base + "settings/self_service");
	
	//End Settings
	
	//Permissions
	public Element quota_self_service = new Element(id,"self_service_default_quota_maximum_running_instances");
	//End Permissions
	
	//Manage Users
	//public Element createUser = new Element(link,"create");
	public Element chooseUsername = new Element(id,"user_login");
	public Element choosePassword = new Element(id,"user_password");
	public Element confirmPassword = new Element(id,"user_password_confirmation");
	public Element firstName = new Element(id,"user_first_name");
	public Element lastName = new Element(id,"user_last_name");
	public Element email = new Element(id,"user_email");
	public Element user_save = new Element(id,"user_submit");
	public Element user_quota_set = new Element(id,"user_quota_attributes_maximum_running_instances");
	
	public LocatorStrategy lsUserTableCell = new LocatorTemplate('lsUserTableCell','''//table[@id='users_table']/tbody/tr/td/a[normalize-space(.)='$1']/../../td[position()='$2']''');
	public Element accountManage = new Element("//li[@Class='account']");
	public Element logout = new Element(link,"Log out");
	public Element myAccount = new Element(link,"My Account");
	public Element userCardusername = new Element("//div[@Class='user-card-content']/dl/dd[2]");
	
	//public Element deleteUser = new Element(button,"delete");
	
	
	//End Manage Users
	
	//Manage Providers
	public Element ProviderAccount_Provider = new Element(id,"provider_account_provider_id");
	public Element providerAcct_h1 = new Element(h1,"PROVIDER ACCOUNTS");
	//public Element addProvider = new Element(id_button,"providers_nav","Add");
	//public Element editProvider = new Element(id_link,"providers_nav","Edit");
	public Element saveProvider = new Element(id_button,"details","Edit");
	public Element providerSummary = new  Element(link,"Provider Summary");
	//public Element providerAccounts = new Element(link,"Provider Accounts");
	public Element providerAccount = new Element(link,"Provider Account");
    public Element providerName = new Element(id,"provider_name");
    public Element providerUrl = new Element(id,"provider_url");
    public Element testConnection = new Element(id,"test_connection");
    public Element testAccount = new Element(button,"Test Account");
    //public Element addProviderSubmit = new Element(id,"add_provider");
    //public Element saveProviderSubmit = new Element(id,"save_provider");
    public Element providerAccountName = new Element(id,"provider_account_label");
    public Element providerUserName = new Element(name,"provider_account[credentials_hash][username]");
    public Element providerUserPasswd = new Element(id,"provider_account_credentials_hash_password");
    public Element providerQuota = new Element(id,"quota_instances");
    public Element providerAccountNumber = new Element(id,"provider_account_credentials_hash_account_id");
    public Element cloudAccountX509Private = new Element(id,"provider_account_credentials_hash_x509private");
    public Element cloudAccountX509Public = new Element(id,"provider_account_credentials_hash_x509public");
    //public Element addProviderAccount = new Element(id_button,"details","Add");
    //public Element removeProviderAccount = new Element(id_link,"details","Remove");
    //public Element AddAccount = new Element(link ,"Add");
	public Element deleteProviderAccount = new Element(link,"Delete");
	//public Element deleteButton = new Element("//input[@type='submit' and @value='delete']");
	public Element editButton = new Element("//input[@type='submit' and @value='Edit']");
	//public Element genericProviderNameLink   = new Element("//div[@id='providers_nav']/dl/dd/a");
    
	//Templates
	//public Element TemplatesList = new Element(table,"list of templates");
	//public Element TemplateCheckBox = new Element(checkboxNextToText, "test");
	public Element templateSelectedPackage = new Element(link_class, "packagename");
	
	
	//Template Creation Page
	public Element templateName = new Element(id,"tpl_name");
	public Element templateDescription = new Element(id,"tpl_summary");
	public Element platformChoice = new Element(id,"tpl_platform");
	public Element platformChoiceVersion = new Element(id,"tpl_platform_version");
	public Element platformArch = new Element(id,"tpl_architecture");
	public Element templateGroup 	= new Element(id,"template_group");
	public Element save = new Element(button,"Save");
	
	//public Element tempAction_Delete = new Element(icon,"delete"); 
	//public Element Edit = new Element(icon,"edit");
	public Element tempAction_Build = new Element(link,"Builds");
	public Element tempAction_Template = new Element(icon,"new_template");
	public Element tempAction_Assembly = new Element(icon,"assembly");
	public Element tempAction_DeploymentDef = new Element(icon,"deployment_definition");
	// Groups and packages
	public Element templateAddSoftware   = new Element(id,"add_software_button");
	public Element templateAddSelected = new Element(id,"do_add_software");
	public Element packageGroup_base = new Element(id,"group_base");
	public Element noSelectedPackages = new Element(divWithMessage,"grid_16 alpha omega","No selected packages");
	public Element collections = new Element(id,"collections");
	public Element searchTxtBox = new Element(id,"package_search");
	public Element searchButton = new Element(id,"package_search_button");
	public Element noPackagesFound = new Element(divWithMessage,"pageinfo","No packages found");
	public Element searchResults = new Element("//div[@id='metagrouppackages']/h4");
	public Element addingPackagesNotification = new Element("//span[@id='do_add_software' and @class='loading fl']");
	
	//Templates
	//public Element create = new Element(value,"Create");
	
	//Template Build Page
	public Element buildTarget = new Element(id,"target");
	public Element buildGO = new Element(value,"Go");
	
	//Instance Page
	public Element imageWareHouseStatus_green = new Element(div_span, "grid_3 suffix_6 omega", "good");
	public Element imageWareHouseStatus_red = new Element(div_span, "grid_3 suffix_6 omega", "bad"); //guessing
	
	
	//Instance Management
	public Element launchInstance = new Element(link,"Launch Instance");
	public Element instanceName = new Element(id,"instance_name");
	public Element instanceHardwareProfile = new Element(id,"instance_hardware_profile_id");
	public Element instanceRealm = new Element(id,"instance_realm_id");
	public Element launchInstanceCancell = new Element(button,"Cancel");
	public Element launchInstanceLaunch = new Element(button,"Launch");
	public Element quotaExceeded = new Element(notification,"Quota Exceeded: Instance will not start until you have free quota");
	
	//Instance Action
	//public Element instanceControl_Shutdown = new Element(icon,"shutdown"); 
	public Element instanceControl_Details = new Element(icon,"instance_details"); 
	//public Element instanceControl_Failed = new Element(icon,"remove_failed");
	public Element back = new Element(button,"Back");
	
	//pools and Zones
	public Element editPools = new Element(button,"edit");
	//public Element deletePools = new Element(button,"delete");
	public Element newPools = new Element(button,"New Pool");
	
	


	
	
	// ADDING 1.1 ELEMENTS
	
	public Element createUser = new Element(id,"add_user_button");
	//public Element manageProviders = new TabElement(notSelectedNavLink, selectedNavLink,"Providers");
	//public Element genericProviderNameLink   = new Element("//table[@id='providers_table']/tbody/input");
	public Element Templates = new TabElement(notSelectedNavLink, selectedNavLink,"Templates");
	public Element genericProviderNameLink   = new Element("//table[@id='providers_table']//input/following-sibling::a");
	public Element addProvider = new Element(link, "Create");
	public Element addProviderSubmit = new Element(id,"provider_submit");
	public Element saveProviderSubmit = new Element(id,"provider_submit");
	public Element editProvider = new Element(link, "Edit");
	//public Element AddAccount = new Element(link ,"New Account");
	//public Element deleteButton = new Element(id, "delete_button");
	public Element removeProviderAccount = new Element(value, "Delete");
	public Element manageUsers = new TabElement(notSelectedNavLink, selectedNavLink,"Users");
	public Element tempAction_Delete = new Element(value,"Delete");
	public Element create = new Element(link,"Create");
	public Element Edit = new Element(link,"Edit");
	public Element deleteUser = new Element(value,"Delete");
    
	public Element deletePools = new Element(button,"Destroy");
	public Element InstanceManagement = new TabElement(notSelectedNavLink, selectedNavLink,"Instances");
	public Element instanceControl_Shutdown = new Element(id,"stop_selected_instances"); 
	public Element instanceControl_Failed = new Element(value,"Remove failed");
	
	public LocatorStrategy providerAccountCheckbox = new LocatorTemplate("providerAccountCheckbox", '''//a[normalize-space(.)='$1']/preceding-sibling::input[@type='checkbox']''');
	public LocatorStrategy providerAccountNameLink = new LocatorTemplate("providerAccountNameLink", '''//a[normalize-space(.)='$1']''');
	
	
	//NAVIGATION
	public TabElement administration = new TabElement(notSelectedNavLink, selectedNavLink,"Administration");
	public TabElement resourceManagent = new TabElement(notSelectedNavLink, selectedNavLink,"Resource Management");
	public TabElement imageFactory = new TabElement(notSelectedNavLink, selectedNavLink,"Image Factory");
	public TabElement providerAccounts = new TabElement(notSelectedNavLink, selectedNavLink,"Provider Account");
	public TabElement roles = new TabElement(notSelectedNavLink, selectedNavLink,"Roles");
	public TabElement hwProfiles = new TabElement(notSelectedNavLink, selectedNavLink,"Hardware Profiles");
	public TabElement imageImports = new TabElement(notSelectedNavLink, selectedNavLink,"Image Imports");
	public Element resourceManagement = new Element(link,"Resource Management");
	public Element genericProAcctCheckbox = new Element(name, "accounts_selected[]");
	public Element selectAll = new Element(link, "All");
	public Element selectAllCheckBox = new Element(id, "select_all");
	public Element checkAll = new Element(id, "select_all");
	

	public Element image = new Element(link,"Image");
	public Element editButton_link = new Element(link, "Edit");
	public Element cancelButton_link = new Element(link, "Cancel");
	public Element properties = new Element(link,"Properties");
	
	//Templates
	public Element templateBuilds = new Element(link,"Builds");
	public Element templateArch = new Element(link,"ARCH");
	
	//Pools
	public Element Pool = new Element(link,"Pool");
	public Element new_Pool = new Element(link,"New Pool");
	public Element edit_Pools = new Element(link,"Edit");
	public Element newPool = new Element(id,"pool_name");
	public Element pool_save = new Element(button,"Save");

	//Roles
	public LocatorStrategy roleNameLink = new LocatorTemplate("roleNameLink", '''//table[@id='roles_table']//a[normalize-space(.)='$1']''');
	public LocatorStrategy roleTargetAction = new LocatorTemplate("roleTargetAction", '''//tr/td[normalize-space(.)='$1']/following-sibling::td[normalize-space(.)='$2']''');
	public LocatorStrategy genericActionRoleTarget = new LocatorTemplate("genericActionRoleTarget", '''//tr/td/following-sibling::td[normalize-space(.)='$1']''');
	public LocatorStrategy roleScope = new LocatorTemplate("roleScope", '''//h2[normalize-space(.)='$1']/following-sibling::strong[normalize-space(.)='Scope:']/following-sibling::span''');
	public LocatorStrategy roleCheckbox = new LocatorTemplate("roleCheckbox", '''//a[normalize-space(.)='$1']/preceding-sibling::input[@type='checkbox']''');
	public LocatorStrategy getRoleActionTargetByRowIndex = new LocatorTemplate("getRoleActionTargetByRowIndex", '''//tr[$1]/td/following-sibling::td''');
	public Element genericRoleNameLink = new Element("//table[@id='roles_table']//a");
	public String roleRow_string = "//table[@id='roles_table']//input[@name='role_selected[]']";
	public Element createNewRole = new Element(link, "New Role");
	public Element deleteRole = new Element(id, "delete_button");
	public Element roleNameField = new Element(id, "role_name");
	public Element roleFormSave = new Element("//input[@value='Save']");
	public Element roleFormReset = new Element("//input[@value='Reset']");
	public Element genericRoleCheckbox = new Element(name, "role_selected[]");
	public Element genericActionGenericRoleTarget = new Element("//tr/td/following-sibling::td");
	
	//hardware profiles
	public String sHwpMainTableNameHeaderText = "Hardware Profile Name";
	public String sHwpDetailsTableNameHeaderText = "Name";
	public String sHwpProviderTableNameHeaderText = "Provider Name";
	public String sHwpMainContainerXpath = "//div[@id='list-view']";
	public String sHwpDetailsContainerXpath = "//div[@id='details-selected']";
	public LocatorStrategy lsHwpProviderHeading = new LocatorTemplate("lsHwpProviderHeading", '''//h3[normalize-space(.)='$1']''');
	public LocatorStrategy lsHwpProviderContainer = new LocatorTemplate("lsHwpProviderContainer", '''//div[@id='$1']''');
	public LocatorStrategy lsHwpGeneralHeading = new LocatorTemplate("hwpGeneralHeading", '''$1/h3''');
	public LocatorStrategy lsHwpGeneralTableRow = new LocatorTemplate("hwpTableRow", '''$1//table/tbody/tr''');
	public LocatorStrategy lsHwpGeneralTableHeaderCell = new LocatorTemplate("hwpTableHeaderCell", '''$1//table/thead/tr/th''');
	public LocatorStrategy lsHwpSpecificHeadingContains = new LocatorTemplate("hwpSpecificHeadingContains", '''$1/h3[contains(.,'$2')]''');		// comment
	public LocatorStrategy lsHwpSpecificTableHeaderCell = new LocatorTemplate("hwpTableHeaderCell", '''$1//table/thead/tr/th[$2]''');
	public LocatorStrategy lsHwpSpecificTableCell = new LocatorTemplate("hwpSpecificTableCell", '''$1//table/tbody/tr[$2]/td[$3]''');
	public Element eHwpProvidersButton = new Element(link, "Matching Provider Hardware Profiles");
	
	public Element hardwareProfile_new = new Element(id,"new_hardware_profile_button");
	public Element hardwareProfile_name = new Element(id,"hardware_profile_name");
	public Element hardwareProfile_memory = new Element(id,"hardware_profile_memory_attributes_value");
	public Element hardwareProfile_cpu = new Element(id,"hardware_profile_cpu_attributes_value");
	public Element hardwareProfile_storage = new Element(id,"hardware_profile_storage_attributes_value");
	public Element hardwareProfile_architecture = new Element(id,"hardware_profile_architecture_attributes_value");
	public Element hardwareProfile_checkMatches = new Element(value,"Check Matches");
	public Element hardwareProfile_save = new Element(value,"Save");
	
	public Element eHwpCreateButton = new Element(link, "New Hardware Profile");
	public Element eHwpCreateNameField = new Element(id, "hardware_profile_name");
	public Element eHwpCreateMemoryField = new Element(id, "hardware_profile_memory_attributes_value");
	public Element eHwpCreateCpuField = new Element(id, "hardware_profile_cpu_attributes_value");
	public Element eHwpCreateStorageField = new Element(id, "hardware_profile_storage_attributes_value");
	public Element eHwpCreateArchField = new Element(id, "hardware_profile_architecture_attributes_value");
	public Element eHwpCheckMatches = new Element(value, "Check Matches");
	public Element eHwpSaveButton = new Element(value, "Save");
	
	// search box
	public Element eSearchTextField = new Element(id, "q");
	public Element eSearchSubmit = new Element(value, "Search");
	
	// providers
	public Element eProviderTypeSelectBox = new Element(id, "provider_provider_type_id");
	
	
	//instances
	public Element createSubmit = new Element(value,"Create");
	public Element instancePoolSelect = new Element(id,"instance_pool_id");
	
	//Image Import
	public Element ImageId = new Element(id,"ami_id");
	public Element Import = new Element(value,"Import");
	public Element ProviderAccountID = new Element(id,"provider_account_id");
	
	//Generic
	public Element checkBoxBlank = new Element("//input[@Type='checkbox']");
	
	// End Adding 1.1 Elements

	
	// Begin Adding 1.2 Elements
	

	public Element  deleteButton = new Element(id,"delete_button");
	public Element saveButton = new Element(id,"save");
	public Element notificaton = new Element("//img[@alt='Notices']");
	public Element Errors = new Element(imgAlt,"Errors");
	//note to check error/notification message
	//public LocatorStrategy notificationMessage = new LocatorTemplate("notification","//li[normalize-space(.)=$1");
	
	
	//NAVIGATION
	//public TabElement administer = new TabElement(notSelectedNavLink, selectedNavLink,"Administer");
	public Element administer = new Element(link,"Administer");
	public Element administerSelected = new Element("//a[@Class='selected' and normalize-space(.)='Administer']");
	public Element designDeploy = new Element(href,"/conductor/legacy_templates");
	
	//Administer
	public Element cloudProviders = new Element(link,"Cloud Resource Providers");
	public Element cloudProvidersUpstream = new Element(link,"Cloud Providers");
	public Element cloudProvidersChooseAProvider = new Element("//span[@Class='label light' and normalize-space(.)='Choose a provider:']");
	public Element usersAndGroups = new Element("//nav[@id='administer_nav']//a[@href='/conductor/users']");
	public Element usersHeader = new Element("//h2[@Class='users']");
	public Element rolesHeader = new Element("//h1[@Class='roles']");
	public Element providersHeader = new Element("//h1[@Class='providers']");
	public Element providerAccountHeader = new Element("//h1[@Class='provider_accounts']");
	public Element hardwareProfileHeader = new Element("//h1[@Class='hardware_profiles']");
	public Element realmsHeader = new Element("//h1[@Class='realms']");
	public Element poolFamiliesHeader = new Element("//h1[@Class='pool_families']");
	public Element settingsHeader = new Element("//h1[@Class='settings']");
	public Element catalogEntriesHeader = new Element("//h1[@Class='catalog_entries']");
	public Element catalogHeader = new Element("//h1[@Class='catalogs']");
	public Element content = new Element(link,"Content");
	public Element hardware = new Element(id,"details_hardware_profiles");
	public Element realms = new Element(id_alt,"details_realms");
	
	//Providers and Providers Accounts
	public Element providerAccountNew = new Element(id,"new_provider_account");
	public Element manageProviders = new TabElement(notSelectedNavLink, selectedNavLink,"Providers");
	public Element providerAccountsList = new Element(id_alt,"details_accounts");
	public Element providerAccountsListLink = new Element(link,"Accounts");
	
	
	
	//Realms
	public Element realmNew = new Element(id,"new_realm_button");
	public Element realmName = new Element(id,"frontend_realm_name");
	public Element realmAddMappingToRealm = new Element(id,"mapping_to_realm_button");
	public Element realmAddMappingToProvider = new Element(id,"mapping_to_provider_button");
	public Element realmMapping = new Element(select_id,"realm_backend_target_realm_or_provider_id");
	
	// Catalog Entry List

	public Element DeployableNew = new Element(id,"new_deployable_button");



	public Element catalogEntryName = new Element(id_alt,"deployable_name");
	public Element catalogEntryFromURL = new Element(id_alt,"from_url");
	public Element catalogEntryUpload = new Element(id_alt,"upload");
	public Element catalogEditXML = new Element(id_alt,"edit_xml");
	public Element catalogEntryDescription = new Element(id_alt,"deployable_description");
	public Element catalogEntryURL = new Element(id_alt,"url");

	public Element catalogEntryID = new Element(id,"catalog_entry_catalog_id");
	
	//Catalogs
	public Element catalogs = new Element(id,"details_catalogs");
	public Element catalogNew = new Element(id,"new_catalog_button");
	public Element catalogName = new Element(id,"catalog_name");
	public Element catalogPool = new Element(id,"catalog_pool_id");
	
	
	//Monitor
	public Element monitorTab = new Element(link,"Monitor");
	public Element defaultPool = new Element(link,"Default");
	public Element filterView = new Element(id,"filter_view");
	public Element instancesList = new Element(id,"details_instances");
	public Element instanceStopSelected = new Element(id,"stop_selected_instances");
	

	
	//Deployments
	public Element deploymentNew = new Element(id,"new_deployment_button");
	public Element deploymentName = new Element(id,"deployment_name");
	public Element deploymentDefinition = new Element(id,"deployable_id");
	public Element deploymentRealm = new Element(id,"deployment_frontend_realm_id");
	public Element Next = new Element(value,"Next");
	public Element deploymentLaunch = new Element(id,"launch_deployment_button");
	
	
	//Environments and Pools
	public Element clouds = new Element(link,"Clouds");
	
	//Config Server
	public Element configServerAdd = new Element(link,"Add");
	public Element configServerEndpoint = new Element(id,"config_server_endpoint");
	//public Element configServerPort = new Element(id,"config_server_port");
	public Element configServerConsumerKey = new Element(id,"config_server_key");
	public Element configServerConsumerSecret = new Element(id,"config_server_secret");
	public Element configServerCertificate = new Element(id,"config_server_certificate");
	
	public Element configLaunchTimeHeader = new Element("//h2[normalize-space(.)='Configure launch-time parameters for your application:']");
	public Element configFinalize = new Element(id,"submit_params");
	public Element configServerTest = new Element(link,"Test");
	
	
	
	
	
	
	
	

}
