package com.redhat.qe.se.tasks;

import java.util.Map.Entry
import java.util.logging.Level
import java.util.logging.Logger

import com.redhat.qe.auto.selenium.Element
import com.redhat.qe.auto.selenium.ExtendedSelenium
import com.redhat.qe.auto.testng.Assert
import com.redhat.qe.tools.SCPTools
import com.redhat.qe.tools.SSHCommandRunner
import com.thoughtworks.selenium.SeleniumException

public class AssortedTasks {
	
//	protected static UIElements UI = (UIElements)SeleniumTestScript.instantiator.getVersionedInstance(UIElements.class);
	protected static NavigationTasks nav = (NavigationTasks)SeleniumTestScript.instantiator.getVersionedInstance(NavigationTasks.class);
	protected static Logger log = Logger.getLogger(AssortedTasks.class.getName());
	protected int templateBuildCounter = 0;
	protected int templateUploadCounter = 0;
	public ExtendedSelenium sel(){
		return SeleniumTestScript.selenium;
	}
	
	String key = System.getProperty("ce.auto.privatekey");
	String passphrase = System.getProperty("jon.server.sshkey.passphrase");
	
	//Enumeration of general actions for forms
	public static final String FORM_ACTION_SAVE = "FORM_ACTION_SAVE";
	public static final String FORM_ACTION_RESET = "FORM_ACTION_RESET";
	public static final String FORM_ACTION_CANCEL = "FORM_ACTION_CANCEL";
	
	public static final String USER_LOGOUT = "USER_LOGOUT";
	public static final String USER_LOGIN = "USER_LOGIN";
	
	//Enumeration of general actions for objects
	public static final String ACTION_CREATE = "ACTION_CREATE";
	public static final String ACTION_EDIT = "ACTION_EDIT";
	public static final String ACTION_DUPLICATE = "ACTION_DUPLICATE";
	public static final String ACTION_DELETE = "ACTION_DELETE";
	
	
	

	
	public boolean isElementPresent(Element e) {
	    return sel().isElementPresent(e,Level.FINE);
	}
	
	public boolean isTextPresent(String txt){
		return sel().isTextPresent(txt, Level.FINE);
	}
	
	public void takeScreenShot() throws Exception{
		sel().screenCapture();
	}

	
	/*public Templates editTemplateAddPackages(Templates template, String... packages) throws CloneNotSupportedException{
		Templates clone = template.clone(); 
		nav.templateEdit(template);
		try{
			addPackage(packages);
		}
		catch(SeleniumException se){
			log.severe(se.getMessage());
			log.severe("Some packages may not have been added");
		}
		template.addPackage(packages);
		removePackage(template.getPackageBlackList());
		sel().clickAndWait(UI.save);
		return clone;
	}
	
	public Templates editTemplateAddGroups(Templates template,boolean CollectionGroup) throws CloneNotSupportedException{
		Templates clone = template.clone(); 
		nav.templateEdit(template);
		if(CollectionGroup){
			addCollectionGroup(template);
		}
		else{
			addGroup(template);
		}
		removePackage(template.getPackageBlackList());
		sel().clickAndWait(UI.save);
		return clone;
	}
	
	
	
	
	
	public Templates editTemplateRemovePackages(Templates template, String... packages) throws CloneNotSupportedException{
		Templates clone = template.clone(); 
		nav.templateEdit(template);
		removePackage(packages);
		sel().clickAndWait(UI.save);
		return clone;
	}
	*/
	
	protected void addGroup(Templates template) {
		setupPackageSelection();
		if(!template.getYumPackageGroups().isEmpty()){
			for(String s:template.getYumPackageGroups()){
				Element group = new Element(UI.id,"group_"+s);
				sel().check(group);
			}
		}
		addSelected();
	}
	
	protected void addCollectionGroup(Templates template){
		//if(!template.getPackageGroups().isEmpty()){
			for(String s:template.getCloudEngineYumPackageGroups()){
				
				String sCollectionName = null;
				String sGroupName = null;
				
				if (s.indexOf(":") > 0) {
					sCollectionName = s.split(":")[0];
					sGroupName = s.split(":")[1];
				} else {		
					
					sCollectionName = YumData.getCollectionForGroup(s);
					sGroupName = 	YumData.getYumGroupForCloudEngineYumGroup(s);
					System.out.println("sGroupName ="+ sGroupName);
				}
				
				setupPackageSelection();
				//String collectionString = YumData.getCollectionForGroup(s);
				Element collectionElement = new Element(UI.value,sCollectionName);
				Element group = new Element(UI.templateCollectionGroupCheckBox,sGroupName);
				log.info(group.toString());
				sel().click(collectionElement);
				//comment
				sel().waitForVisible(group, "60000");
				sel().check(group);
				addSelected();
			}
			
		
	}
	
	
	
	protected boolean searchForPackageOrGroup(String packageOrGroup){
		boolean found = false;
		sel().setText(UI.searchTxtBox, packageOrGroup);
		sel().click(UI.searchButton);
		sel().waitForElement(UI.searchResults, "6000");
		if(!sel().isVisible(UI.noPackagesFound)){
			Element e = new Element(UI.value,packageOrGroup);
			if(sel().isElementPresent(e)){
				found = true;
			}	
		}
		return found;
	}
	
	protected void addPackage(String... packagesToAdd){
		//add template to param and add package info to template
		setupPackageSelection();
		for(int x=0;x<packagesToAdd.length;x++){
			if(searchForPackageOrGroup(packagesToAdd[x])){
				Element e = new Element(UI.value,"pkg_"+packagesToAdd[x]);
				sel().check(e);
			}
		
		}
		addSelected();
	}
	
	protected void removePackage(String... packagesToRemove){
		for(int x=0;x<packagesToRemove.length;x++){
			Element e = new Element(UI.templateSelectedPackageName,"remove_package_"+packagesToRemove[x]);
			if(sel().isElementPresent(e)){
				sel().click(e);
			}
		}
	}
	
	protected void removePackage(Set<String> packages){
		for(String p:packages){
			Element e = new Element(UI.templateSelectedPackageName,"remove_package_"+p);
			log.info("Looking for: "+p);
			if(sel().isElementPresent(e)){
				sel().highlight(e);  // TODO: doesn't work
				sel().click(e);
				log.info("Removed package: ** "+ p + " **");
			}
			else{
				log.info("Package: **"+ p + " ** was not found in the selected packages");
			}
		}
	}
	
	protected void setupPackageSelection(){
		if(sel().isVisible(UI.templateAddSoftware)){
			sel().click(UI.templateAddSoftware);
			sel().waitForVisible(UI.collections, "60000");
		}
		if(sel().isElementPresent(UI.collections)){
			sel().click(UI.collections);
		}
		
	    sel().waitForElement(UI.templateAddSelected, "60000");
		sel().waitForElement(UI.packageGroup_base, "60000");
	}
	
	protected void addSelected(){
		sel().click(UI.templateAddSelected);
		//sel().waitForVisible(UI.templateAddSelected, "60000");
		for(int x=0;x<75;x++) {
			    if(sel().isElementPresent(UI.addingPackagesNotification)){
			        log.info("Adding Packages and Groups, sleep 2 seconds ");
			        sel().sleep(2000);
		    }
		    else {
		      break;
		    }
		}
	}
	
	
	


	/*
	public Set<String> getTemplateRpmList(Templates template) {
		Set<String> rpmList = new TreeSet<String>();
			
		nav.templateEdit(template);
			
		int iTotalPackages = sel().getXpathCount("//a[@class='packagename']").intValue();
		
		for(int i = 1; i<=iTotalPackages; i++) {
			//Element ePackage = new Element(UI.templateSelectedPackageName,Integer.toString(i));
			String sPkgName = sel().getText("//div[@class='packagewrap' and position()="+i+"]//a[@class='packagename']");
			//System.out.println("Template PackageName: " + sPkgName);
			rpmList.add(sPkgName);
		}	
			
		return rpmList;
	}*/
	 
	/*
	 * 
	 * 		Provider Tasks
	 * 
	 */
	
	public void createProvider(ProviderAccount provider) {
		providerForm(provider, true, false);
	}
	public void testProvider(ProviderAccount provider) {
		providerForm(provider, false, true);
	}
	

	
	public void editProvider(ProviderAccount provider, String sNewName, String sNewUrl, boolean bTest) {
	    nav.editProvider(provider);
	    
    	if (sNewUrl != null) {
    	  	sel().type(UI.providerUrl, sNewUrl);
    	}
	    	
	    if (bTest) {
	    	sel().clickAndWait(UI.testConnection);
	    } else {	
	    	
			if (sNewName != null) {
		   		sel().type(UI.providerName, sNewName);
		   	}	

	        sel().clickAndWait(UI.saveProviderSubmit);
	    	    
	        if (sel().isTextPresent(ProviderAccount.PROVIDER_UPDATED_SUCCESS)) {
	        	if (sNewName != null) {provider.setName(sNewName);}
	        	if (sNewUrl != null)  {provider.setUrl(sNewUrl);} 
	        }
	    } 
	}
	
	public void editProvider(ProviderAccount provider, ProviderAccount changes, boolean bTest) {
	    nav.editProvider(provider);
	    
	    String sNewUrl = changes.getUrl();
	    String sNewName = changes.getName();
	    
	    editProvider(provider, sNewName, sNewUrl, bTest);
	}
	

	
	
	
	/*protected void verifyProviderCreation(Provider provider){
		if(!sel().isTextPresent(Provider.PROVIDER_CREATION_SUCCESS)){
			provider.setCreated(false);
	    } else {
	    	provider.setCreated(true);	
	    }
	}*/
	
	public boolean providerTestExist(Provider provider) {
		nav.manageProviders();
		Element providerNameLink = new Element(UI.providerNameLink, provider.getName());
	    return sel().isElementPresent(providerNameLink);
	}
	
	public void createProviderAccount(ProviderAccount providerAccount) {
		nav.manageProviderAccount(providerAccount);
		sel().clickAndWait(UI.providerAccountNew);
		enterInProviderAccount(providerAccount, null, false);
	}
	
	
	public void editProviderAccount(ProviderAccount origProviderAccount, HashMap<Element, String> thisMap) {
		nav.editProviderAccount(origProviderAccount);
		enterInProviderAccount(origProviderAccount, thisMap, true);
	}
	
	
	protected void editFormOfStrings(HashMap<Element, String> thisMap){
		for(Map.Entry<Element, String> entry:thisMap.entrySet()){
			if(!sel().getValue(entry.getKey()).equals(entry.getValue())){
				sel().setText(entry.getKey(),entry.getValue());
			}
		}
	}

	protected void enterInProviderAccount(ProviderAccount origProvider, HashMap<Element, String> thisMap, boolean bEdit) {
		
	    if (bEdit) {
	    	//nav.editProviderAccount(origProvider);
	    	editFormOfStrings(thisMap);
	    	sel().clickAndWait(UI.editButton);	
	    }
		else{
			//nav.manageProviderAccount();
			//sel().clickAndWait(UI.providerAccountNew);
			//sel().select(UI.ProviderAccount_Provider, origProvider.getName());
		    //sel().sleep(1000);
			thisMap = origProvider.getFormData();
			editFormOfStrings(thisMap);
			sel().clickAndWait(UI.save);
		}
	}
	
	public void deleteAllProviderAccounts(Provider provider) {
		nav.manageProviders();
		Element thisProvider = new Element(UI.link,provider.getName());
		Element providerHeader = new Element(UI.providerHeader,provider.getName());
		
		if(sel().isElementPresent(providerHeader)){
			log.info("already on the right provider");
		}
		else if(sel().isElementPresent(thisProvider)){
			sel().click(thisProvider);
			sel().waitForElement(providerHeader, "60000");
		
		}
		else{
			throw new SeleniumException("Specified provider was not found, check your install");
		}
		sel().click(UI.providerAccountsList);
		sel().waitForElement(UI.providerAccountNew, "60000");
		if(sel().isElementPresent(UI.selectAllCheckBox)){
			sel().check(UI.selectAllCheckBox);
			sel().clickAndWait(UI.deleteButton);
			if(sel().isElementPresent(UI.Errors)){
				throw new SeleniumException("Error found while deleting provider account");
			}
/*			Element errorWarning = new Element("//img[@alt='Warnings']");
			sel().waitForElement(errorWarning, "60000");*/
		}
		
		
	}
	
	public void updateProviderAccountQuota(ProviderAccount provider, HashMap<Element, String> thisMap) throws CloneNotSupportedException {
		editProviderAccount(provider, thisMap);
	}
	

	
	public boolean doesProviderAccountExist(ProviderAccount providerAccount) {
		nav.manageProviderAccount(providerAccount);
		Element myPA = new Element(UI.checkboxNextToText,providerAccount.getName());
		return sel().isElementPresent(myPA);
	}
	

	
	
	/*
	 * 
	 * 		Instance Tasks
	 * 
	 */
	
	

	
	public void verifyInstanceInRow(String instanceName){
	    nav.InstanceManagement();
       // sel().clickAndWait(UI.InstanceManagement);
       // Assert.assertTrue(sel().isElementPresent(new Element(UI.checkboxNextToText,instanceName)),"found check box w/ " + instanceName);
        Element instanceInRow = new Element(UI.rowWithTextInColumnNumber,instanceName,Instance.COL_INSTANCE_VM_NAME);
        log.info(instanceInRow.toString());
        Assert.assertTrue(sel().isElementPresent(instanceInRow),"found instance "+instanceName+" in the pool");
	}
	public String checkInstanceState(String instanceName){
        nav.InstanceManagement();
        //sel().clickAndWait(UI.InstanceManagement);
        //cell match 1=table, 2=name,3=Column,4=status,5=Column
        Element instanceInRowWithStatus_new = new Element(UI.twoRelativeColumns,instanceName,Instance.COL_INSTANCE_VM_NAME,Instance.STATE_NEW,Instance.RELATIVE_COL_INSTANCE_STATUS);
        log.finest(instanceInRowWithStatus_new.toString());
        Element instanceInRowWithStatus_pending = new Element(UI.twoRelativeColumns,instanceName,Instance.COL_INSTANCE_VM_NAME,Instance.STATE_PENDING,Instance.RELATIVE_COL_INSTANCE_STATUS);
        log.finest(instanceInRowWithStatus_pending.toString());
        Element instanceInRowWithStatus_running = new Element(UI.twoRelativeColumns,instanceName,Instance.COL_INSTANCE_VM_NAME,Instance.STATE_RUNNING,Instance.RELATIVE_COL_INSTANCE_STATUS);
        log.finest(instanceInRowWithStatus_running.toString());
        Element instanceInRowWithStatus_stopped = new Element(UI.twoRelativeColumns,instanceName,Instance.COL_INSTANCE_VM_NAME,Instance.STATE_STOPPED,Instance.RELATIVE_COL_INSTANCE_STATUS);
        log.finest(instanceInRowWithStatus_stopped.toString());
        Element instanceInRowWithStatus_error = new Element(UI.twoRelativeColumns,instanceName,Instance.COL_INSTANCE_VM_NAME,Instance.STATE_ERROR,Instance.RELATIVE_COL_INSTANCE_STATUS);
        if(sel().isElementPresent(instanceInRowWithStatus_new)) {
            log.info(instanceName+" is in a NEW state");
            return Instance.STATE_NEW;
        }
        else if(sel().isElementPresent(instanceInRowWithStatus_pending)) {
            log.info(instanceName+" is in a PENDING state");
            return Instance.STATE_PENDING;
        }
        else if (sel().isElementPresent(instanceInRowWithStatus_running)){
            log.info(instanceName+" is in a RUNNING state");
            return Instance.STATE_RUNNING;
        }
        else if (sel().isElementPresent(instanceInRowWithStatus_stopped)){
            log.info(instanceName+" is STOPPED");
            return Instance.STATE_STOPPED;
        }
        else if (sel().isElementPresent(instanceInRowWithStatus_error)){
            log.info(instanceName+" has an ERROR status");
            return Instance.STATE_ERROR;
        }
        else{
        	throw new SeleniumException("INSTANCE IS IN AN UNKNOWN STATE");
            //return InstanceData.STATE_UNKNOWN;
        }
    }
	

	
    public boolean waitForInstance(String instanceName, String desiredState, int timeoutInMinutes){
    	boolean runningInstance = false;
    	int msDelay = 30000;
    	
    	String state = checkInstanceState(instanceName);
    	
    	if(state.equalsIgnoreCase(Instance.STATE_ERROR)) {
    		log.log(Level.SEVERE, "Bailing out, instance is in a error state");
    		runningInstance = false;
    	}
    	else if(state.equalsIgnoreCase(desiredState)){
    		runningInstance = true;
    		return runningInstance;
    	}
    	else if(!state.equalsIgnoreCase(desiredState)) {
            int counter = timeoutInMinutes * 2;
            for(int x=0;x<counter;x++) {
            	state = checkInstanceState(instanceName);
            	log.info("Remaining available checks = "+ (counter - x));
                if(state.equalsIgnoreCase(desiredState)) {
                	log.info("Instance is in desired state (" + desiredState + ")");
                	runningInstance = true;
                    break;
                }
                else if(state.equalsIgnoreCase(Instance.STATE_ERROR)) {
            		log.log(Level.SEVERE, "Bailing out, instance is in a error state");
            		runningInstance = false;
            		break;
            	}
                
                else if ( (counter - x) == 1) throw new SeleniumException("Instance is not at desired state (" + desiredState + ") in alloted time: " + timeoutInMinutes + " minutes");
                sel().sleep(msDelay);
            }
        }
    	
    	log.info("STATE ="+runningInstance);
    	return runningInstance;
    }
	

	
	/*
	 * 
	 * 		User Tasks
	 * 
	 */
	
	public void createSelfServiceUser(Users user){
		user.setSelfService(true);
		createUser(user);
	}
	
	/**
	 * UserData user
	 * String formAction - 
	 * options = AssortedTasks.FORM_ACTION_SAVE
	 * options = AssortedTasks.FORM_ACTION_CANCEL
	 * options = AssortedTasks.FORM_ACTION_RESET
	 */
	public void createUser(Users user){
		
		if (user.isSelfService()) {
			if(sel().isElementPresent(UI.logout)) {
	            sel().clickAndWait(UI.logout);
	        } else {
	            sel().open(UI.url_base+"/login");
	        }
			sel().clickAndWait(UI.SelfServiceCreateAccount);
			
		} else {
			
			if (! Users.isAdminLoggedIn()) {
				logout();
				loginAsAdmin();
				
			}
			
			nav.manageUsers();
			sel().clickAndWait(UI.createUser);
		}	
		
		userFillFormSave(user, Users.isAdminLoggedIn());
		
	}

	public void userFillFormSave(Users user, boolean asAdmin){
		finishUserForm(user, asAdmin);
		sel().clickAndWait(UI.user_save);
	}
	

	
	private void finishUserForm(Users user, boolean asAdmin) {
		HashMap<Element, String> thisMap = user.getFormData();
		if(asAdmin){
			editFormOfStrings(thisMap);
		}
		else{
			thisMap.remove(UI.user_quota_set);
			editFormOfStrings(thisMap);
		}
	}

	public boolean verifyUserInRow(String username, boolean userPresent){
		nav.manageUsers();
		if(userPresent){
			Assert.assertTrue(sel().isElementPresent(new Element(UI.checkboxNextToText,username)),"found check box w/ " + username);
			return true;
		}
		else{
			Assert.assertTrue(!sel().isElementPresent(new Element(UI.checkboxNextToText,username)),"found check box w/ " + username);
			return false;
		}
	}
	
	public void loginAsAdmin() {
	    logout();
	    //sel().sleep(60000);  //HOLY SHIT.. aeolus is not immediatly able to login after logging out.. see rails log
	    loginCE(System.getProperty("ce.admin.user"),System.getProperty("ce.admin.password"));
	    Users.currentlyLoggedInUser = System.getProperty("ce.admin.user");
	}
	
	public void login(String username, String password, String serverURLPath){
		 //Work Around for ssl certs
		   //final String tab = "9";
		   final String tab = String.valueOf(java.awt.event.KeyEvent.VK_TAB);
		   //final String space_bar = "32";
		   final String space_bar = String.valueOf(java.awt.event.KeyEvent.VK_SPACE);
			try{
				//System.out.println("timeout"+sel().getTimeout());
				//sel().setTimeout("120000");
				sel().open(serverURLPath);
				
			}
			catch(SeleniumException se){
				String currentSpeed = sel().getSpeed();
				sel().setSpeed("3000");
				if(sel().isElementPresent(UI.SSLUnderstandRisks)){
					sel().click(UI.SSLUnderstandRisks);
					sel().windowFocus();
				}
				
				if(sel().isElementPresent(UI.SSLExceptionAccept)){
					//sel().click(UI.SSLExceptionAccept);
					sel().keyPressNative(tab);
					sel().keyPressNative(tab);
					sel().keyPressNative(space_bar);
					sel().sleep(10000);
					sel().windowFocus();
					sel().keyPressNative(tab);
					sel().keyPressNative(tab);
					sel().keyPressNative(tab);
					sel().keyPressNative(tab);
					sel().keyPressNative(space_bar);
					sel().setSpeed(currentSpeed);
				}
				
			}
			if(sel().isTextPresent("No route matches "+serverURLPath+"/login\"")){
				sel().open("/login");
				//UIElements.url_base = "";
			}
			
	}
	
	public void loginCE(String username, String password){
			sel().open("/conductor/login");
			sel().waitForElement(UI.LoginUserName, "60000");
			sel().type(UI.LoginUserName, username);
			sel().type(UI.LoginUserPasswd, password);
			sel().clickAndWait(UI.Login);
			Users.currentlyLoggedInUser = username;
		
			//System.out.println("IT WORKS!");
	}
	
	public void setSeflServiceQuota(String instanceQuota) {
	    loginAsAdmin();
	    nav.permissions();
	    sel().type(UI.quota_self_service, instanceQuota);
	    sel().clickAndWait(UI.user_save);
	}

	
	public boolean doesUserExist(Users user) {
		nav.manageUsers();
		return sel().isElementPresent(new Element(UI.radioButtonNextToText,user.username()));
	}
	
	public void checkUserData(Users user) {
		ifNotAdminLogoutAndInAsAdmin();
		nav.manageUsers();
		Assert.assertTrue(sel().isElementPresent(new Element(UI.lsUserTableCell, user.username(), Users.COL_USER_ID)),"found user with name '" + user.username() +"'");
		Assert.assertTrue(sel().isElementPresent(new Element(UI.lsUserTableCell, user.username(), Users.COL_LAST_NAME)),"found user's last name '" + user.lastName() +"'");
		Assert.assertTrue(sel().isElementPresent(new Element(UI.lsUserTableCell, user.username(), Users.COL_FIRST_NAME)),"found user's first name '" + user.firstName() +"'");
		Assert.assertTrue(sel().isElementPresent(new Element(UI.lsUserTableCell, user.username(), Users.COL_EMAIL)),"found user's email address '" + user.email() +"'");
	}
	
	public void ifNotAdminLogoutAndInAsAdmin() {
		/*if (! isUserLoggedIn("admin")) {
			loginAsAdmin();
		}*/
	}


	

	

	
	public void loginAsNewUser(Users user){
		loginCE(user.username(), user.password());
		//Assert.assertTrue(sel().isTextPresent("Login successful!"));
	}
	



	


    
    public String generateRandomString(int iCharCount) {
    	RandomData rd1 = new RandomData(iCharCount);
        return rd1.toString();
    }
    

    
    public void checkExpectedMessages(String... messages) {
    	for (int i = 0; i<messages.length; i++) {
    		if (! messages[i].equals("")) {
    			Assert.assertTrue(sel().isTextPresent(messages[i]), "Expected message (" + messages[i] + ") found");
    		}	
    	}
    }
    
	public String generateRandomNumberString(int iLength) {
		String sQuota = "";
		Random randomGenerator = new Random();
	    for (int idx = 1; idx <= iLength; ++idx){
	        int randomInt = randomGenerator.nextInt(10);
	        sQuota = sQuota + Integer.toString(randomInt);
	    }
	    return sQuota;
	}
	
	
	/*
	 * 
	 * 		Hardware Profiles
	 * 
	 */
	
	
	private HashMap<String,Integer> getHeaderIndexes(String sContainerXpath) {
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		
		String sColumnCountXpath = new Element(UI.lsHwpGeneralTableHeaderCell, sContainerXpath).getLocator();
		int columnCount = sel().getXpathCount(sColumnCountXpath).intValue();
		
		for (int x = 1; x <= columnCount; x++) {
			String sHeaderText = sel().getText(new Element(UI.lsHwpSpecificTableHeaderCell, sContainerXpath, Integer.toString(x)));
			map.put(sHeaderText, x);
		}
		return map;
	}
	
	
	
	
	public void createHwp(String sName, HashMap<String,String> values, boolean bCheckMatches, boolean bSave) {
		
		if (sel().isElementPresent(UI.eHwpCreateNameField)) {
			;  // do nothing
		} else if (sel().isElementPresent(UI.eHwpCreateButton)) {
			sel().click(UI.eHwpCreateButton);
		} else {
			nav.hardwareProfiles(false);
			sel().click(UI.eHwpCreateButton);
			sel().waitForElement(UI.eHwpCreateNameField, "5000");
		}
			
		sel().type(UI.eHwpCreateNameField, sName);
		sel().type(UI.eHwpCreateMemoryField, values.get("MEMORY"));
		sel().type(UI.eHwpCreateStorageField, values.get("STORAGE"));
		sel().type(UI.eHwpCreateCpuField, values.get("CPU"));
		sel().type(UI.eHwpCreateArchField, values.get("ARCH"));
		
		if (bCheckMatches)
			sel().clickAndWait(UI.eHwpCheckMatches);
		else if (bSave) 
			sel().clickAndWait(UI.eHwpSaveButton);
		
	}
	

	
	
    
    
    public void aeolusImageBuild(String server, Templates template) throws IOException{
    	SSHCommandRunner sshCommandRunner = new SSHCommandRunner(server, "root", key, passphrase , '''cat /etc/redhat-release | awk '{print $1}' ''');
		SCPTools scpTools = new SCPTools(server, "root", key, passphrase);
		sshCommandRunner.run();
		
		scpTools.sendFile(System.getProperty("ce.prj.home")+"/scripts/RHEL61vmwareTools.tpl", "/root/");
		sshCommandRunner.runCommandAndWait("aeolus image build --target ec2 --template=/root/RHEL61vmwareTools.tpl");
		
    }
    
    /**
     * Taken out 
     * ll.add([ "conductor-delayed_job", "delayed_job","00:00:00 delayed_job"] ));
     * @return
     */
    public List<List<Object>> deamonsStart() {

        List<List<Object>> ll = new ArrayList<List<Object>>();
       
        ll.add([ "aeolus-conductor", "thin","thin server (127.0.0.1:3000)" ] ));
        ll.add([ "conductor-dbomatic", "dbomatic","/usr/share/aeolus-conductor/dbomatic/dbomatic"] ));
        ll.add([ "libvirtd", "libvirtd","libvirtd --daemon"] ));
        ll.add([ "httpd", "httpd","/usr/sbin/httpd"] ));
        ll.add([ "imagefactory", "imagefactory","/usr/bin/imagefactory --rest --debug"] ));
	    ll.add([ "qpidd", "qpidd","/usr/sbin/qpidd --data-dir /var/lib/qpidd --daemon"] ));
        //ll.add([ "deltacloud-core", "3002","/usr/bin/deltacloudd -i mock -e production --port 3002 -r localhost"] ));
        ll.add([ "deltacloud-core", "3002","thin server (localhost:3002) [deltacloud-mock]"] ));
        ll.add([ "iwhd", "iwhd","iwhd -c /etc/iwhd/conf.js -c /etc/iwhd/conf.js -l /var/log/iwhd.log -o -u /etc/iwhd/users.js"] ));
        ll.add([ "httpd", "httpd","/usr/sbin/httpd"] ));
        //ll.add([ "ntpd", "ntpd","ntpd -u ntp:ntp -p /var/run/ntpd.pid -g"] ));
 
        return ll;
    }
    
    public List<List<Object>> deamonsStop() {
    	//aeolus cleanup removes the deltacloud init scripts
        List<List<Object>> ll = new ArrayList<List<Object>>();
        ll.add([ "aeolus-conductor", "thin","thin server (127.0.0.1:3000)"] ));
        ll.add([ "conductor-dbomatic", "dbomatic","/usr/share/aeolus-conductor/dbomatic/dbomatic"] ));
        ll.add([ "httpd", "httpd","/usr/sbin/httpd"] ));
        ll.add([ "imagefactory", "imagefactory","/usr/bin/imagefactory --rest --debug"] ));
        ll.add([ "qpidd", "qpidd","/usr/sbin/qpidd --data-dir /var/lib/qpidd --daemon"] ));
        ll.add([ "iwhd", "iwhd","iwhd -c /etc/iwhd/conf.js -d localhost"] ));
        ll.add([ "deltacloud-core", "3002","thin server (localhost:3002) [deltacloud-mock]"] ));
        ll.add([ "httpd", "httpd","/usr/sbin/httpd"] ));
        

      
 
        return ll;
    }
    
    
    public void checkDeamons(List<List<Object>> thisList, SSHCommandRunner runner){
    	
    	Object[][] array = new Object[thisList.size()][];
		int i=0;
		for (List<Object> item: thisList){
			array[i] = item.toArray();
			i++;
		}
		for(int x=0;x<array.length;x++){
			String daemon = (array[x][0].toString());
			String processName = (array[x][1].toString());
			String processSig = (array[x][2].toString());
			runner.runCommandAndWait("/etc/init.d/"+daemon+ " status");
			if(!runner.getStdout().contains("is running")){
				log.log(Level.SEVERE,daemon+" IS NOT RUNNING");
				log.info("Starting Service "+ daemon);
				runner.runCommandAndWait("/etc/init.d/"+daemon+ " start");
			}
			runner.runCommandAndWait("ps -ef | grep "+ processName);
			if(!runner.getStdout().contains(processSig)){
				log.log(Level.SEVERE,processSig+" WAS NOT FOUND");
			}

		}
    }
    

    
	///  BEGIN 1.1 IMPORT

	public void deleteProvider(ProviderAccount provider){
	    nav.manageProviders();
	    Element providerNameLink = new Element(UI.providerNameLink, provider.getName());
	    	    
	    if(sel().isElementPresent(providerNameLink)){
	    	Element providerNameLinkCheckBox = new Element(UI.providerNameLinkCheckBox, provider.getName());
	    	sel().check(providerNameLinkCheckBox);
	 		sel().clickAndWait(UI.deleteButton);
	 		//Assert.assertTrue(sel().isTextPresent(ProviderData.PROVR_DELETED));
	    }
	}
	
	
	public void testDashboardLinks() {
		sel().click(UI.Dashboard);
		sel().clickAndWait(UI.resourceManagent);
		sel().clickAndWait(UI.imageFactory);
	}
	
	public void createTemplate(SSHCommandRunner runner, SCPTools scpTool, Templates template){
		
		templateBuild(runner, scpTool, template, null);
	}
	
	public void verifyTemplateInRow(String templateName, boolean isPresent){
		nav.Templates();
		if(isPresent)
		    Assert.assertTrue(sel().isElementPresent(new Element(UI.checkboxNextToText,templateName)),"found check box w/ " + templateName);
		if(isPresent == false)
		    Assert.assertTrue(!sel().isElementPresent(new Element(UI.checkboxNextToText,templateName)),"Did not find check box w/ " + templateName);
	}
	
	public void deleteTemplateInRow(String templateName) {
	    nav.Templates();
	    Element template = new Element(UI.checkboxNextToText,templateName);
	    if(sel().isElementPresent(template)){
	        sel().check(template);
	        sel().clickAndWait(UI.tempAction_Delete);
	    }
	    if(sel().isTextPresent("undefined local variable or method `errs' for #")){
	    	throw new SeleniumException("https://bugzilla.redhat.com/show_bug.cgi?id=673240");
	    }
	}
	

	
	public void removeProviderAccount(ProviderAccount providerAccount) {
		nav.manageProviderAccount(providerAccount);
    	
		Element acctCheckbox = new Element(UI.providerAccountCheckbox, providerAccount.getAccountName());
		sel().check(acctCheckbox);
		sel().clickAndWait(UI.removeProviderAccount);	
	}	
	
	protected void providerForm(ProviderAccount providerAccount, boolean bAdd, boolean bTest) {
	    nav.manageProviders();

    	sel().clickAndWait(UI.addProvider);
	    sel().type(UI.providerName, providerAccount.providerName());
	    sel().type(UI.providerUrl,providerAccount.providerURL());

		if (providerAccount instanceof ProviderAccount_ec2) {
			sel().select(UI.eProviderTypeSelectBox, "Amazon EC2" );
		}
	    
	    if (bAdd) {
	    	sel().clickAndWait(UI.addProviderSubmit);
	    } else if (bTest) {
	    	sel().clickAndWait(UI.testConnection);
	    }
	}
	
/*	protected void verifyProviderCreation(Provider provider){
		if(!sel().isTextPresent(Provider.PROVIDER_CREATION_SUCCESS)){
			provider.setCreated(false);
	    } else {
	    	provider.setCreated(true);	
	    }
	}*/
	
	protected String getProviderAccountUserName(ProviderAccount providerAccount) {
		
		nav.manageProviderAccount(providerAccount);
		Element providerUsername = new Element(UI.oneRelativeColumn,providerAccount.getAccountName(),"1","1");
		log.info(providerUsername.toString());
		String username = sel().getText(providerUsername);
		return username;
	
		/*String sHref = sel().getAttributes(new Element (UI.link, provider.getAccountName())).getProperty("href");
		
		String[] array = sHref.split("/");
		String sProviderAccountId = array[4];
		//     /deltacloud/providers/1/accounts/2/edit	
		
		return sProviderAccountId;*/
	}
	
	/*
	 * 
	 * 		Role Methods
	 * 
	 */
	
	public boolean doesRoleExist(String sRoleName) {
		nav.roles(false);
		return sel().isElementPresent(new Element(UI.roleNameLink, sRoleName));
		
	}
	
	public boolean doesRoleHaveTargetAction(String sRoleName, String sTarget, String sAction) {
		nav.roleDetails(sRoleName);
		return sel().isElementPresent(new Element(UI.roleTargetAction, sAction, sTarget));
	}
	
	public int getNumberOfRoles() {
		nav.roles(false);
		return sel().getXpathCount(UI.roleRow_string).intValue();
	}
	
	public int getNumberOfActionsPerTargetType(String sRoleName, String sTarget) {
		nav.roleDetails(sRoleName);
		//String s = new Element(UI.genericActionRoleTarget, sTarget).toString();
		String s = "//tr/td/following-sibling::td[normalize-space(.)='"+sTarget+"']";
		return sel().getXpathCount(s).intValue();
	}
	
	public String getRoleScope(String sRoleName) {
		nav.roleDetails(sRoleName);
		return sel().getText(new Element(UI.roleScope, sRoleName));
	}
	
	private boolean validateIPaddr(String ipaddr){
		try{
	        String [] parts = ipaddr.split ("\\.");
	        for (String s : parts)
	        {
	            int i = Integer.parseInt (s);
	            if (i < 0 || i > 255)
	            {
	                return false;
	            }
	        }
	        return true;
		}
		catch(Exception e){
			log.log(Level.SEVERE,"invalid ip addr detected");
			log.log(Level.FINEST,SeleniumTestScript.getStackTrace(e));
			return false;
		}
	    
	}
	
	
	public void instanceDetails(Instance instance, Realm realm, boolean running) {
	    /*
	     * name, status, ip_address, operating_system, provider, base_template, architecture,memory
         * storage, instantiation_time, uptime, current_alerts, console_connection, owner, shared_to
	     */
		Element public_address = new Element(UI.instanceDetailsGet,"public_addresses");
		String pub_address ="";
	    nav.InstanceManagement();
	    verifyInstanceInRow(instance.getFullName());
	    /*Element instanceInRow = new Element(UI.checkboxNextToText,instance.getInstanceName());
	    sel().check(instanceInRow);*/
	    Element name = new Element(UI.link,instance.getFullName());
	    sel().click(name);
	    Element nameDetails = new Element(UI.instanceDetailsVerify, "name", instance.getFullName());
	    sel().waitForVisible(nameDetails, "60000");
	    
	    Assert.assertTrue(sel().isElementPresent(nameDetails),"Asserted Name = "+instance.getFullName());
	    Provider thisProvider = realm.getProvider();
	    String providerType = thisProvider.getProviderType();
	    log.info("GETTING IP INFORMATION");
	    if(providerType.equals(Provider.MOCK_ProviderType)){
	    	log.info("mock instances will not have a resolvable ip");
	    	return;
	    }
	    else if(thisProvider.getProviderType().equals(Provider.EC2_ProviderType)){
	    	pub_address = sel().getText(public_address);
	    	instance.set_PublicAddress(pub_address);
		    Element instance_key = new Element(UI.instanceDetailsGetKey,"instance_key");
		    //String instance_key_string = sel().getAttribute("//label[@for='instance_key']/following-sibling::span[contains(.,'Download')]/a@href");
		    log.info("INSTANCE_KEY_URL ="+instance_key);
		    String instance_key_string = sel().getAttributes(instance_key).getProperty("href");
		    log.info("INSTANCE_KEY_URL ="+ instance_key_string);
		    instance.set_InstanceKeyAddress(instance_key_string);
		    log.info("INSTANCE_SSH_KEY ="+instance.get_InstanceKey());
	    }
	    else if(providerType.equals(Provider.VSPHRE_ProviderType) || providerType.equals(Provider.RHEVM_ProviderType)){
		    pub_address = sel().getText(public_address);
		    log.info("PUBLIC ADDRESS = "+ pub_address);
		    if(validateIPaddr(pub_address)){
		    	instance.set_PublicAddress(pub_address);
		    }
		    else{
		    	log.info("valid ip address not found, sleep 120 seconds");
		    	sel().sleep(120000);
		    	sel().refresh();
		    	if(validateIPaddr(sel().getText(public_address))){
		    		instance.set_PublicAddress(sel().getText(public_address));
		    	}
		    	else{
		    		throw new SeleniumException("Unable to get valid ip address");
		    	}
		    } 
	    }
	}
	
	public TreeSet<String> getRolesUniqueTargetTypes(String sRoleName) {
		TreeSet<String> hs = new TreeSet<String>();
		
		nav.roleDetails(sRoleName);
		int rowCount = sel().getXpathCount(UI.genericActionGenericRoleTarget.toString()).intValue();
		
		for (int x = 1; x <= rowCount; x++ ) {
			String sTarget = sel().getText(new Element(UI.getRoleActionTargetByRowIndex, Integer.toString(x)));
			if (! sTarget.equalsIgnoreCase("target type")) {
				hs.add(sTarget);
			}	
		}
		
		return hs;
	}
	
	public void createRole(String sRoleName, boolean bSave, boolean bReset, boolean bCancel) {
		nav.roles(false);
		sel().clickAndWait(UI.createNewRole);
		finishRoleForm(sRoleName, bSave, bReset, bCancel);
	}
	
	public void editRole(String sRoleName, String sNewName, boolean bSave, boolean bReset, boolean bCancel) {
		nav.roleDetails(sRoleName);
		sel().clickAndWait(UI.editButton_link);
		finishRoleForm(sNewName, bSave, bReset, bCancel);
	}
	
	private void finishRoleForm(String sRoleName, boolean bSave, boolean bReset, boolean bCancel) {
		if (sRoleName != null) {
			sel().type(UI.roleNameField, sRoleName);
		}
		
		if (bCancel) {
			sel().clickAndWait(UI.cancelButton_link);
		} else if (bReset) {
			sel().clickAndWait(UI.roleFormReset);
			// added assert because occasionally reset actually saves
			Assert.assertTrue(! sel().isTextPresent("Role successfully saved!"), "successfully saved msg not found");
			sel().waitForElement(UI.roleNameField, "5000");
		} else if (bSave) {
			sel().clickAndWait(UI.roleFormSave);
		}
	}
	
	public void deleteRoles(String... sRoleName) {
		nav.roles(false);
		
		for(int x=0;x<sRoleName.length;x++){
			sel().check(new Element(UI.roleCheckbox, sRoleName[x]));
		}
		
		sel().clickAndWait(UI.deleteRole);
	}
	
	public void deleteAllRole() {
		nav.roles(false);
		while (sel().isElementPresent(UI.genericRoleCheckbox)) {
			sel().clickAndWait(UI.selectAll);
			sel().clickAndWait(UI.deleteRole);
		}
	}
	
	/*
	 * 
	 * 		Search
	 * 
	 */
	
	public void search(String sSearchString) {
		if (sel().isElementPresent(UI.eSearchTextField)) {
			sel().type(UI.eSearchTextField, sSearchString);
			sel().clickAndWait(UI.eSearchSubmit);
		} else {
			throw new SeleniumException("Search box not found!");
		}
	}
	
	public void editUser(String originalUserName, Users user){
		nav.manageUsers();
		sel().clickAndWait(new Element(UI.link,originalUserName));
		sel().clickAndWait(UI.Edit);
		userFillFormSave(user, Users.isAdminLoggedIn());
	}
	

	
	public void deleteAllUsers(boolean bIncludingAdmin) {
		nav.manageUsers();
		
		int iRowCount = -1;
		boolean bDone = false;
		
		while(! bDone) {
			iRowCount = sel().getXpathCount("//table[@id='users_table']/tbody/tr").intValue();
			for (int i = 1; i <= iRowCount; i++) {
				String sUserName = sel().getText("//table[@id='users_table']/tbody/tr[" + i + "]/td[" + Users.COL_USER_ID + "]/a");
				
				if (sUserName.equals("admin")) {
					if (bIncludingAdmin) { 
						deleteUser(sUserName);
						break;
					}
					
				} else {
					deleteUser(sUserName);
					break;
				}
			}
			if (iRowCount == 1) bDone = true;
		}	
	}
	

	
	public void deleteUser(Users user){

		nav.manageUsers();
		sel().click(new Element(UI.checkboxNextToText,user.username()));
		sel().clickAndWait(UI.deleteUser);
		//nav.manageUsers();BZ #641871
		Assert.assertFalse(sel().isElementPresent(new Element(UI.rowWithTextInColumnNumber,user.username(),Users.COL_USER_ID)),"Could not found user with user name " + user.username());
		Assert.assertFalse(sel().isTextPresent(user.username()),"User not Found with user name " + user.username());
	}
	
	public void newPool(String poolname){
		nav.ResourceManagement();
    	sel().clickAndWait(UI.new_Pool);
    	sel().type(UI.newPool, poolname);
    	sel().clickAndWait(UI.pool_save);
    	Assert.assertTrue(sel().isElementPresent(new Element(UI.checkboxNextToText,poolname)),"found check box w/ " + poolname);
		Assert.assertTrue(sel().isTextPresent("Pool added."),"Pool added");
    }
	 public void editPool(String poolname){
		 newPool(poolname);
	     sel().clickAndWait(UI.edit_Pools);
	     sel().type(UI.newPool, poolname+"_edit");
	     sel().clickAndWait(UI.pool_save);
	     Assert.assertTrue(sel().isElementPresent(new Element(UI.checkboxNextToText,poolname+"_edit")),"found check box w/ " + poolname+"_edit");
		 Assert.assertTrue(sel().isTextPresent("Pool updated."),"pool edited");

	    }
	    public void deletePool(String poolname){
	    	newPool(poolname);
	    	sel().click(new Element(UI.checkboxNextToText,poolname));
	    	sel().clickAndWait(UI.deletePools);
			//nav.manageUsers();BZ #641871
			Assert.assertFalse(sel().isElementPresent(new Element(UI.checkboxNextToText,poolname)),"Could not found pool with pool name " + poolname);
			Assert.assertFalse(sel().isTextPresent(poolname),"Pool not Found with pool name " + poolname);
	    }
	    public void deleteDefaultPool(){
	    	nav.ResourceManagement();
	    	sel().click(new Element(UI.checkboxNextToText,"default_pool"));
	    	sel().clickAndWait(UI.deletePools);
	    	Assert.assertTrue(sel().isElementPresent(new Element(UI.checkboxNextToText,"default_pool")),"Found pool with pool name " + "default_pool");
			Assert.assertTrue(sel().isTextPresent("default_pool"),"Pool Found with pool name " + "default_pool");
			Assert.assertTrue(sel().isTextPresent("The default pool cannot be deleted"));
	    }
	    
	    public void duplicatePool(String poolname){
	    	newPool(poolname);
	    	sel().clickAndWait(UI.new_Pool);
	    	sel().type(UI.newPool, poolname);
	    	sel().clickAndWait(UI.pool_save);
	    	Assert.assertTrue(sel().isTextPresent("Name has already been taken"));
	    	Assert.assertTrue(sel().isElementPresent(UI.newPool));
	    }

	    
	    public boolean templateBuild(SSHCommandRunner runner, SCPTools scpTool, Templates template, ProviderAccount providerAccount){
	    	boolean buildCompleted = false;
	    	buildPushTemplate(runner, scpTool, template, 0, providerAccount);

	        //BUILD
	        String statusBuild = verifyTemplateIsBuilding(runner, scpTool, template);
	        while(statusBuild.equalsIgnoreCase(GrindData.BUILDING)) {
	            statusBuild = verifyTemplateIsBuilding(runner, scpTool, template);
	        }
	        if(statusBuild.equalsIgnoreCase(GrindData.COMPLETED)) {
	            buildCompleted = true;//,"Asserted template "+ template.getName() + " has been built successfully");
	            template.setBuiltSuccessfully(true);
	        }
	        else {
	            buildCompleted = false;//,"Asserted template "+ template.getName() + " has been built successfully");
	            if(statusBuild.equals(GrindData.FAILED))
	                    throw new SeleniumException("template build failed");
	        }
	        return buildCompleted;
	    }
	    
	    public boolean templatePush(SSHCommandRunner runner, SCPTools scpTool, Templates template, ProviderAccount providerAccount){  
	        //UPLOAD
	        buildPushTemplate(runner, scpTool, template, 1, providerAccount);
	        boolean complete = false;
	        while(!complete) {
	            String statusUpload = verifyTemplateIsUploading(runner, scpTool, template);
	            log.info("STATUS ="+statusUpload);
	            
	            if(statusUpload.equals(GrindData.PUSHING)){
	            	log.info(template.getName() + " is PUSHING");
	            }
	            else if(statusUpload.equals(GrindData.QUEUED)){
	            	log.info(template.getName() + " is QUEUED");
	            }
	            else if(statusUpload.equals(GrindData.COMPLETED)) {
		            complete = true;//,"Asserted template "+ template.getName() + " has been uploaded successfully");
		            template.setPushedSuccessfully(true, providerAccount);
		            break;
		        }
		        else if(statusUpload.equals(GrindData.FAILED)) {
		            //,"Asserted template "+ template.getName() + " has been uploaded successfully");
		            if(statusUpload.equals(GrindData.FAILED))
		            	throw new SeleniumException("template upload failed");
		            
		        }
		        else {
		            //"Asserted template "+ template.getName() + " has been upload successfully");
		            if(statusUpload.equals(GrindData.FAILED))
		                    throw new SeleniumException("template upload failed");
		        }
	       }
	        return complete;
	}
	    
		public void buildTemplate(Templates myTemplate, ProviderAccount providerAccount) {
			myTemplate.setProvider(providerAccount);
			//nav.templateBuild(myTemplate);
			sel().waitForElement(UI.buildTarget, "30000");
			sel().select(UI.buildTarget,providerAccount.getProviderDisplayName());
			sel().click(UI.buildGO);
	       // sel().select(provider.getBuildOptionElement(),provider.getDisplayName());

	        if(sel().isTextPresent("failed to upload template (code 0):")){
	            throw  new SeleniumException("Template build failed: failed to upload template (code 0): ");
		        
		    }
		}
		
		public String verifyTemplateIsBuilding(SSHCommandRunner runner, SCPTools scpTool, Templates template) {
			String status = getTemplateStatus(runner, scpTool, template, 0);
		    int sleeptime = 120000;
		    int attemptQueue = 30; //tmp until fix // we only build one template at a time currently, if we change this and build more than one template at a time this will have to be updated.
		    int attemptBuild = 30;
		    int attemptComplete = 35;
		    templateBuildCounter++;
		    log.info("Sleep for 2 minutes");
		    if(status.equals(GrindData.QUEUED) && templateBuildCounter < attemptQueue) {
		    	log.info("Template " + template.getName() + " is QUEUED");
		    	log.info("Attempts = " + templateBuildCounter + "/" + attemptQueue);
		    	sel().sleep(sleeptime);
		    	return GrindData.BUILDING;
		    } else if (status.equals(GrindData.NEW) && templateBuildCounter < attemptBuild) {
	    		log.info("Template " + template.getName() + " is BUILDING");
	    		log.info("Attempts = " + templateBuildCounter + "/" + attemptBuild);
		    	sel().sleep(sleeptime);
		    	return GrindData.BUILDING;
	    	} else if (status.equals(GrindData.BUILDING) && templateBuildCounter < attemptBuild) {
	    		log.info("Template " + template.getName() + " is BUILDING");
	    		log.info("Attempts = " + templateBuildCounter + "/" + attemptBuild);
		    	sel().sleep(sleeptime);
		    	return GrindData.BUILDING;
	    	} else if (status.equals(GrindData.COMPLETED) && templateBuildCounter < attemptComplete) {
	    		log.info("Template " + template.getName() + " has COMPLETED building");
		        return GrindData.OK;
		    }
		    else if(status.equals(GrindData.FAILED)) {
		        log.info("Template " + template.getName() + " has failed to build");
		        templateBuildCounter = 0;
		        return GrindData.FAILED;
		    }
		    else if(templateBuildCounter >= attemptComplete) {
		    	log.info("Template " + template.getName() + " has not progressed in the required time and has failed to build");
		    	templateBuildCounter = 0;
		        return GrindData.FAILED;
		    }
		    else {
		    	log.info("Template " + template.getName() + " encountered an unknown error, please investigate");
		    	try {
					sel().screenCapture();
				} catch (Exception e) {
					log.log(Level.SEVERE,SeleniumTestScript.getStackTrace(e));
				}
		    	templateBuildCounter = 0;
		        return GrindData.FAILED;
		    }
		}
		
		
		public String verifyTemplateIsUploading(SSHCommandRunner runner, SCPTools scpTool, Templates template) {
			String status = getTemplateStatus(runner, scpTool, template, 1);
		    
		    int sleeptime = 60000;
		    int attemptQueue = 20; // we only build one template at a time currently, if we change this and build more than one template at a time this will have to be updated.
		    int attemptBuild = 20;
		    int attemptComplete = 25;
		    templateUploadCounter++;
		    log.info("Sleep for 2 minutes");
		    //&& templateUploadCounter < attemptBuild
		    if(status.equals(GrindData.QUEUED)) {
		    	log.info("Template " + template.getName() + " is QUEUED");
		    	log.info("Attempts = " + templateUploadCounter + "/" + attemptQueue);
		    	sel().sleep(sleeptime);
		    	return GrindData.QUEUED;
		    } else if (status.equals(GrindData.NEW)) {
	    		log.info("Template " + template.getName() + " is PUSHING");
	    		log.info("Attempts = " + templateUploadCounter + "/" + attemptBuild);
		    	sel().sleep(sleeptime);
		    	return GrindData.PUSHING;
	    	} else if (status.equals(GrindData.PUSHING)) {
	    		log.info("Template " + template.getName() + " is PUSHING");
	    		log.info("Attempts = " + templateUploadCounter + "/" + attemptBuild);
		    	sel().sleep(sleeptime);
		    	return GrindData.PUSHING;
	    	} else if (status.equals(GrindData.COMPLETED)) {
	    		log.info("Template " + template.getName() + " has COMPLETED uploading");
		        template.setBuiltSuccessfully(true);
		        DataList.templateAdd(template);
		        DataList.writeTemplateData();
		        templateUploadCounter = 0;
	            return GrindData.COMPLETED;
		    }
		    else if(status.equals(GrindData.FAILED)) {
		        log.info("Template " + template.getName() + " has failed to build");
		        templateUploadCounter = 0;
		        return GrindData.FAILED;
		    }
		    else if(templateBuildCounter >= attemptComplete) {
		    	log.info("Template " + template.getName() + " has not progressed in the required time and has failed to build");
		    	templateUploadCounter = 0;
		        return GrindData.FAILED;
		    }
		    else {
		    	log.info("Template " + template.getName() + " encountered an unknown error, please investigate");
		    	templateUploadCounter = 0;
		        return GrindData.FAILED;
		    }
		}
		
		
		
		
		public boolean launchInstance(String templateName,String instanceName, String arch, HardwareProfile hwp) {
		    nav.launchInstance();
		    sel().clickAndWait(UI.createSubmit);
		    Element myInstance = new Element(UI.buttonWithParentHeader,templateName);
		    sel().clickAndWait(myInstance);
		    sel().type(UI.instanceName, instanceName);
		    sel().select(UI.instancePoolSelect,"default_pool");
		    sel().select(UI.instanceHardwareProfile, hwp.getName());
		   
		    //sel().select(UI.instanceRealm,"us-east-1a");
		    sel().clickAndWait(UI.launchInstanceLaunch);
		    if(sel().isElementPresent(UI.quotaExceeded)) {
		         try {
		            log.log(Level.SEVERE,"Quota has been exceeded!");
	                sel().testNGScreenCapture();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
		         return false;
		    }
		    else {
		        return true;
		    }
		       
		}
		
		public void catalogCreate(Catalog catalog){
			nav.catalogs();
			sel().clickAndWait(UI.catalogNew);
			sel().setText(UI.catalogName, catalog.getName());
			sel().select(UI.catalogPool, "Default");
			sel().clickAndWait(UI.save);
		}
		
		
		
		public void catalogEntryCreate(CatalogEntry myDeployable,Catalog catalog){
			nav.catalogEntries(catalog);
			Element thisCatalog = new Element(UI.link,catalog.getName());
			//sel().clickAndWait(thisCatalog);
			sel().clickAndWait(UI.DeployableNew);
			sel().click(UI.catalogEntryFromURL);
			sel().waitForElement(UI.catalogEntryURL, "6000");
			sel().setText(UI.catalogEntryName, myDeployable.getCatalogEntryName());
			sel().setText(UI.catalogEntryDescription, myDeployable.getCatalogEntryName());
			//sel().select(UI.catalogEntryID,catalog.getName());
			//String fullPath = "http://localhost"+myDeployable.getRemoteDir()+myDeployable.getFileName();
			String fullPath = "http://"+SeleniumTestScript.ceServerHostname+myDeployable.getRemoteDir()+myDeployable.getFileName();
			myDeployable.setFullRemoteURI(fullPath);
			sel().setText(UI.catalogEntryURL, fullPath);
			sel().clickAndWait(UI.save);	
		}
		
		public boolean catalogEntryExists(CatalogEntry myDeployable){
			Catalog thisCatalog = myDeployable.getCatalog();
		    nav.catalogEntries(thisCatalog	);
	        Element ce = new Element(UI.checkboxNextToText,myDeployable.getCatalogEntryName());
	        log.info(ce.toString());
	        return sel().isElementPresent(ce);
		}
		
		private void deploymentStart01(Deployment myDeployable, Realm myRealm){
			CatalogEntry myCatalogEntry = myDeployable.getCatalogEntry();
			Catalog myCatalog = myCatalogEntry.getCatalog();
			List<Instance> instances = new ArrayList<Instance>();
			instances = myCatalogEntry.getInstanceList();
			String autoDeployment = myDeployable.getName();
			nav.monitor();
			sel().clickAndWait(UI.defaultPool);
			sel().clickAndWait(UI.deploymentNew);
			sel().setText(UI.deploymentName, autoDeployment);
			
			sel().select(UI.deploymentDefinition, myCatalogEntry.getCatalogEntryName());
			sel().select(UI.deploymentRealm,myRealm.getName());
			sel().clickAndWait(UI.Next);
		}
		
	
		
		private void deploymentStart02(Deployment myDeployable, Realm myRealm){
			CatalogEntry myCatalogEntry = myDeployable.getCatalogEntry();
			Catalog myCatalog = myCatalogEntry.getCatalog();
			List<Instance> instances = new ArrayList<Instance>();
			instances = myCatalogEntry.getInstanceList();
			
			if(myCatalogEntry.hasConfigServices()){
				Assert.assertTrue(sel().isElementPresent(UI.configLaunchTimeHeader),"Found launch time param page");
				for(Instance i:instances){
					if(i.hasConfig()){
						ConfigData c = i.getConfigData();
						HashMap initial = c.getInitialConfigParamter();
						HashMap runTime = c.getRunTimeConfigParamter();
						java.util.Iterator iterator = runTime.entrySet().iterator();
						while(iterator.hasNext()){
							Map.Entry<String, String> pairs = (Map.Entry)iterator.next();
							//deployment_launch_parameters_RHEL-6-Audrey-Vsphere-client01_Vsphere_RHEL_auto_service_param1
							for (String serviceName : i.getServiceNames()) {
								Element key = new Element(UI.id,"deployment_launch_parameters_"+i.getName()+"_"+serviceName+"_"+pairs.getKey());
								log.info("Element = "+key.toString());
								if(sel().isElementPresent(key)){
									log.info("Woot found key param");
									log.info("Key Value in WEBUI="+sel().getValue(key));
									log.info("Execpted Key Value ="+initial.get(pairs.getKey()));

									log.info("Setting value to new value of "+runTime.get(pairs.getKey()));
									String newValue = (String) runTime.get(pairs.getKey());
									sel().setText(key, newValue);
									log.info("wes");
								}
							}
						}
					}
				}
				sel().clickAndWait(UI.configFinalize);
			}
		}
		
		private void deploymentStart03(Deployment myDeployable, Realm myRealm){
			CatalogEntry myCatalogEntry = myDeployable.getCatalogEntry();
			Catalog myCatalog = myCatalogEntry.getCatalog();
			List<Instance> instances = new ArrayList<Instance>();
			instances = myCatalogEntry.getInstanceList();
			String autoDeployment = myDeployable.getName();
			
			//ADD REALM SELECTION HERE
			if(sel().isElementPresent(UI.deploymentLaunch)){
				Properties prop = sel().getAttributes(UI.deploymentLaunch);
				if(!prop.containsValue("disabled")){
					sel().clickAndWait(UI.deploymentLaunch);
				}
				else{
					log.log(Level.SEVERE, "Deployable may have problems, the launch button is not enabled");
				}
				
			}
			
			
			for(Instance i:instances){
				String fullName=autoDeployment+"/"+i.getName();
				String rhevmApiName = autoDeployment+"-"+i.getName();
				i.setFullName(fullName);
				i.setFullRHEVMName(rhevmApiName);
				log.info("FULL NAME OF INSTANCE SHOULD BE: "+i.getFullName());
			}
			
		}
		
		public void deploymentStart(Deployment myDeployable, Realm myRealm ){		
			CatalogEntry myCatalogEntry = myDeployable.getCatalogEntry();
			Catalog myCatalog = myCatalogEntry.getCatalog();
			List<Instance> instances = new ArrayList<Instance>();
			instances = myCatalogEntry.getInstanceList();
			
			deploymentStart01(myDeployable, myRealm);
			if(myCatalogEntry.hasConfigServices()){
				deploymentStart02(myDeployable, myRealm);
			}
			deploymentStart03(myDeployable, myRealm);

		}
		
		
		/**
		 * deployment Verficiation, best to run one test at a time
		 * @param myDeployable
		 * @param myRealm
		 * @param assertVerification
		 * @param testPackages
		 * @param testAudreyConfig
		 * @return
		 * @throws IOException
		 */
		public boolean deploymentVerification(Deployment myDeployable, Realm myRealm, boolean assertVerification, boolean testPackages, boolean testAudreyConfig) throws IOException{
			//run one test at a time
			boolean sshWorked = false;
			CatalogEntry myCatalogEntry = myDeployable.getCatalogEntry();
			Catalog myCatalog = myCatalogEntry.getCatalog();
			List<Instance> instanceList = new ArrayList<Instance>();
			instanceList = myCatalogEntry.getInstanceList();
			
			for (Instance i : instanceList) {
				log.info(i.getName());
				instanceDetails(i, myRealm, true);
				try {
					sshWorked = sshAndTest(i, myRealm);
				} catch (IOException ioe) {
					log.log(Level.SEVERE,
							"Cloud Engine is reporting instance is available, but we are unable to ssh in");
					log.log(Level.SEVERE, "Sleep for 60 seconds and retry");
					sleep(60000);
				}
				if(assertVerification){
					sshWorked = sshAndTest(i, myRealm);//, "Asserted ssh to running instance "+ i.getName() + " " + i.get_PublicAddress() + " worked");
				}
				if(testPackages){
					sshWorked = sshAndTestPackages(i,myRealm);//, "Asserted packages found for "+ i.getName() + " " + i.get_PublicAddress());
				}
				if(testAudreyConfig){
					sshWorked = sshAndTestAudrey(i,myRealm);//, "Asserted Audrey log found for "+ i.getName() + " " + i.get_PublicAddress() + " and params present");
				}
				
			}
			return sshWorked;
		
		}
		
		private SSHCommandRunner sshToEC2(String address,String key, String passphrase, String command) throws IOException{
			SSHCommandRunner sshCommandRunner;
			try{
		    	sshCommandRunner = new SSHCommandRunner(address, "root", key, passphrase, command);
		    	}
		    	catch(IOException ioe){
		    		log.info("SSH FAILED: SSH to ec2 has race condition, sleep 60, try again");
		    		sleep(60);
		    		sshCommandRunner = new SSHCommandRunner(address, "root", key, passphrase, command);
		    		
		    	}
		    return sshCommandRunner;
		}
		
		
		public boolean sshAndTest(Instance id, Realm realm) throws IOException{
	    	boolean sshWorked = false;
	    	SSHCommandRunner sshCommandRunner;
	    	Provider thisProvider = realm.getProvider();
	    	log.info("Attempting to ssh into "+id.get_PublicAddress());
		    if (thisProvider.getProviderType().equals(Provider.EC2_ProviderType)){
		    	log.info("ec2 sshkey is here "+id.get_InstanceKeyLocation());
		    	sshCommandRunner = sshToEC2(id.get_PublicAddress(), id.get_InstanceKeyLocation(), passphrase, "echo sshConnectSuccess ");
		    }
		    else if(thisProvider.getProviderType().equals(Provider.MOCK_ProviderType)){
		    	return true;
		    }
		    else{
		    	sshCommandRunner = new SSHCommandRunner(id.get_PublicAddress(),"root", "redhat", "echo sshConnectSuccess");
		    }
		    sshCommandRunner.runCommand("cat /etc/redhat-release");
		    String out = sshCommandRunner.getStdout();
		    log.info(out);
		    if(out.matches(".*release .*")){
		    	sshWorked = true;
		    }
		    if(out.contains("release")){
		    	sshWorked = true;
		    }
	        return sshWorked;
	    }
		
		
		public boolean sshAndTestPackages(Instance id, Realm realm) throws IOException{
	    	boolean sshWorked = false;
	    	SSHCommandRunner sshCommandRunner;
	    	Provider thisProvider = realm.getProvider();
	    	log.info("Attempting to ssh into "+id.get_PublicAddress());
		    if (thisProvider.getProviderType().equals(Provider.EC2_ProviderType)){
		    	log.info("ec2 sshkey is here "+id.get_InstanceKeyLocation());
		    	sshCommandRunner = sshToEC2(id.get_PublicAddress(), id.get_InstanceKeyLocation(), passphrase, "echo sshConnectSuccess");
		    }
		    else if(thisProvider.getProviderType().equals(Provider.MOCK_ProviderType)){
		    	return true;
		    }
		    else{
		    	sshCommandRunner = new SSHCommandRunner(id.get_PublicAddress(),"root", "redhat", "echo sshConnectSuccess");
		    }
		   
	        sshCommandRunner.runCommand("rpm -qa");
	        String installedPackages = sshCommandRunner.getStdout();
	        log.info("Installed Packages:  rpm -qa");
	        log.info(installedPackages);
	        if(!installedPackages.isEmpty()){
	        	sshWorked = true;
	        }
	        return sshWorked;
	    }
		//testAudreyConfig
		
		public boolean sshAndTestAudrey(Instance id, Realm realm) throws IOException{
	    	boolean sshWorked = false;
	    	SSHCommandRunner sshCommandRunner;
	    	Provider thisProvider = realm.getProvider();
	    	log.info("Attempting to ssh into "+id.get_PublicAddress());
		    if (thisProvider.getProviderType().equals(Provider.EC2_ProviderType)){
		    	log.info("ec2 sshkey is here "+id.get_InstanceKeyLocation());
		    	sshCommandRunner = sshToEC2(id.get_PublicAddress(), id.get_InstanceKeyLocation(), passphrase, "echo sshConnectSuccess");
		    }
		    else if(thisProvider.getProviderType().equals(Provider.MOCK_ProviderType)){
		    	return true;
		    }
		    else{
		    	sshCommandRunner = new SSHCommandRunner(id.get_PublicAddress(),"root", "redhat", "echo sshConnectSuccess");
		    }
		    
		    ConfigData c = id.getConfigData();	
		    
	        sshCommandRunner.runCommand("cat /var/log/audrey.log");
	        String audreyLog = sshCommandRunner.getStdout();        
	        log.info("============ AUDREY LOG =============");
	        log.info(audreyLog);
	        log.info("============ AUDREY LOG =============");
	        
	        log.info("Getting all config params from instance");
	        log.info("============ AUDREY CONFIG FILE =============");
	        sshCommandRunner.runCommand("cat "+c.getAudreyConfigFile());
		    String audreyConfigFile = sshCommandRunner.getStdout();
		    log.info(audreyConfigFile);
		    log.info("============ AUDREY CONFIG FILE =============");

	        java.util.Iterator<Entry<String, String>> iterator = c.getRunTimeConfigParamter().entrySet().iterator();
	        while (iterator.hasNext()){
	        	Map.Entry<String, String> pair = (Map.Entry)iterator.next();
	        	log.info("KEY= "+ pair.getKey());
	        	log.info("VALUE=" + pair.getValue());
	        	log.info(pair.getKey() + pair.getValue());
	        	/* if(audreyLog.matches(".*auto_service_param1=![CDATA[value 2]].*")){
	 	        	sshWorked = true;
	 	        }*/
	 	        if(audreyConfigFile.contains(pair.getKey() +"="+ pair.getValue())){
	 	        	sshWorked = true;
	 	        }
	 	        else{
	 	        	break;
	 	        }
	        }

	        return sshWorked;
	    }
		
		
		public void instanceShutdown(Instance id, boolean throwErrorOnFailure) {
			String instanceName=id.getName();
		    boolean isStopped = instanceShutdown(id.getName());
		    boolean isRunning = !isStopped;
		    id.setFailedToShutdown(isRunning);
		    if(throwErrorOnFailure){
		    	Assert.assertTrue(isRunning,instanceName+" is stopped");
			}
		    
		}
		
		public void instanceShutdown(String instanceName, boolean throwErrorOnFailure){
			 boolean isStopped = instanceShutdown(instanceName);
			 boolean isRunning = !isStopped;
			 if(throwErrorOnFailure){
			    	Assert.assertTrue(isStopped,instanceName+" is stopped");
			 	}
		}
		
		private boolean instanceShutdown(String instanceName){
			nav.InstanceManagement();
		    Element instanceInRow = new Element(UI.checkboxNextToText,instanceName);
		    sel().check(instanceInRow);
		    sel().clickAndWait(UI.instanceControl_Shutdown);
		    //Element message = new Element(UI.divWithMessage,"success", instanceName+": stop action was successfully queued.");
		    String message = "stop action was successfully queued.";
		    Assert.assertTrue(sel().isTextPresent(message),"Asserted instance stop was successfully queued");
		    
		    return waitForInstance(instanceName, Instance.STATE_STOPPED, 10);
		    
		}
		
		public boolean isInstanceTemplateVisible(Templates template) {
			nav.launchInstance();
		    sel().clickAndWait(UI.createSubmit);
		    return sel().isElementPresent(new Element(UI.buttonWithParentHeader,template.getName()));
		}
		
		
		public String getProviderAccountId(ProviderAccount providerAccount) throws SeleniumException {
			
			nav.ProviderAccountDetails(providerAccount);
			String id = sel().getText("//p/label[normalize-space(.)='Account ID:']/..");
			log.info("account id="+id);
			
			
			return id;
		}
		
		public boolean isUserCreated(Users user){
			nav.manageUsers();
			if(sel().isElementPresent(new Element(UI.checkboxNextToText,user.username())))
				return true;
			else{
				return false;
			}
		}
		
		public void hwpCreate(HardwareProfile hw){
			nav.hardwareProfiles(true);
			sel().clickAndWait(UI.hardwareProfile_new);
			editFormOfStrings(hw.getFormData());
			sel().clickAndWait(UI.hardwareProfile_checkMatches);
			
			//Element myHWP = new Element(UI.twoRelativeColumns,hw.getName(),"2",hw.getARCH(),"1");
			//log.info(myHWP.toString());
			//if(sel().isElementPresent(myHWP)){
			//	log.info("FOUND PROFILE = "+hw.getName());
				sel().clickAndWait(UI.hardwareProfile_save);
		//	}
		
			
		}
		
		public void verifyHWPInRow(HardwareProfile hw, boolean found){
			nav.hardwareProfiles(true);
			Element myHWP = new Element(UI.checkboxNextToText,hw.getName());
			if(found)
				Assert.assertTrue(sel().isElementPresent(myHWP), "Asserted hardware profile "+hw.getName()+" is present");
			else{
				Assert.assertTrue(!sel().isElementPresent(myHWP), "Asserted hardware profile "+hw.getName()+" is NOT present");
			}
		}
		
		public void hwpDeleteAll(){
			nav.hardwareProfiles(true);
			if(sel().isElementPresent(UI.selectAllCheckBox)){
				sel().check(UI.selectAllCheckBox);
				sel().clickAndWait(UI.deleteButton);
				
			}
			else{
				log.info("no hardware profiles found");
			}
		}
		
		public void sleep(long time){
			log.info("sleep for "+ time + "milliseconds");
			sel().sleep(time);
		}
		
		
	
	
		
	
	
	
	////// END 1.1 IMPORT
		
		
		
		// BEGIN 1.2 IMPORT 
		
		public String currentLoggedInUser(){
			sel().click(UI.accountManage);
			sel().sleep(2000);
			sel().clickAndWait(UI.myAccount);
			return sel().getText(UI.userCardusername);
		}
		
		
		
		public void logout(){
		    sel().open("/conductor/logout");
		    Users.currentlyLoggedInUser = null;
		}
		
	
		
		public void deleteUser(String sUserName){
			String enter = String.valueOf(java.awt.event.KeyEvent.VK_ENTER);
			nav.manageUsers();
			sel().click(new Element(UI.checkboxNextToText,sUserName));
			sel().clickAndWait(UI.deleteUser);
			Assert.assertFalse(sel().isElementPresent(new Element(UI.checkboxNextToText, sUserName)),"User not Found with user name " + sUserName);
		}
		
		public void hwpDelete(HardwareProfile hwp){
			nav.hardwareProfiles(false);
			Element hwpRow = new Element(UI.checkboxNextToText,hwp.getName());
			if(sel().isElementPresent(hwpRow)){
				sel().check(new Element(UI.checkboxNextToText,hwp.getName()));
				sel().clickAndWait(UI.deleteButton);
			}
			Assert.assertFalse(sel().isElementPresent(new Element(UI.checkboxNextToText, hwp.getName())),"HWP not Found with user name " + hwp.getName());
			
		}
		
		public boolean hwpVerifyDetailsInRow(HardwareProfile hwp){
			nav.hardwareProfiles(false);
			Element thisRow = new Element(UI.fiveRelativeColumns,hwp.getName(),"2",hwp.getARCH(),hwp.getMEMORY(),hwp.getSTORAGE(),hwp.getCPU());
			log.info(thisRow.toString());
			if(sel().isElementPresent(thisRow)){
				return true;
			}
			else{
				return false;
			}
		}
		
		 public void shutdownAllInstances(boolean throwErrorOnFailure){
			
		    	log.info("******** SHUTTING DOWN ALL INSTANCES ************");
				nav.InstanceManagement();
				sel().check(UI.checkAll);
				sel().clickAndWait(UI.instanceControl_Shutdown);
				/*try{
					Element runningInstance = new Element(UI.checkboxNextToText,"running");
					LocatorStrategy start_running_instance = new LocatorTemplate("start_running_instance", "//tr/td[(normalize-space(.)='running')]/preceding-sibling::td[position()='2']");
					while(sel().isElementPresent(runningInstance)){
						Element thisRunningInstance = new Element(start_running_instance);
						String name = sel().getText(thisRunningInstance);
						log.info("Instance Name = "+ name);
						instanceShutdown(name, throwErrorOnFailure);
					}
					log.info("******** FINISHED SHUTTING DOWN ALL INSTANCES ***********");
				}
				catch(SeleniumException se){
					log.info("******** FAILED SHUTTING DOWN ALL INSTANCES ***********");
				}*/
			}
		 
		 public void realmCreate(Realm realm, ProviderAccount providerAccount){
			 Provider myProvider = providerAccount.getProvider();
			 nav.realms(false);
			 sel().clickAndWait(UI.realmNew);
			 sel().setText(UI.realmName, realm.getName());
			 sel().clickAndWait(UI.save);
			 sel().clickAndWait(UI.realmAddMappingToRealm);
			 log.info(providerAccount.getNativeRealm());
			 sel().select(UI.realmMapping, providerAccount.getNativeRealm());
			 log.info("current URL is "+ sel().getLocation());
			 sel().clickAndWait(UI.save);
			 String currentURL = sel().getLocation();
			 
			 if(sel().isElementPresent(UI.realmAddMappingToProvider)){
				 sel().clickAndWait(UI.realmAddMappingToProvider);
			 }
			 else{
				 sel().open(currentURL);
				 sel().waitForElement(UI.realmAddMappingToProvider, "60000");
				 sel().clickAndWait(UI.realmAddMappingToProvider);
			 }
			 sel().select(UI.realmMapping,providerAccount.getName());
			 sel().clickAndWait(UI.save); 
			 
			 
		 }
		 
		 public void realmDeleteAll(){
			 nav.realms(false);
			 if(sel().isElementPresent(UI.selectAllCheckBox)){
				 sel().check(UI.selectAllCheckBox);
				 sel().clickAndWait(UI.deleteButton);
			 }
		 }
		 
		 public void realmDelete(Realm thisRealm){
			 nav.realms(false);
			 Element realm = new Element(UI.checkboxNextToText,thisRealm.getName());
			 if(sel().isElementPresent(realm)){
				 sel().check(realm);
				 sel().clickAndWait(UI.deleteButton);
			 }
		 }
		 
		 
		 public boolean realmVerifyExist(Realm realm){
			 nav.realms(false);
			 Element myRealm = new Element(UI.checkboxNextToText,realm.getName());
			 return sel().isElementPresent(myRealm);
		 }
		 
		 public boolean catalogsVerifyExist(Catalog catalog){
			 nav.catalogs();
			 Element myCatalog = new Element(UI.checkboxNextToText,catalog.getName());
			 return sel().isElementPresent(myCatalog);
		 }
		 
		 public void catalogsDeleteAll(){
			 nav.catalogs();
			 if(sel().isElementPresent(UI.selectAllCheckBox)){
				 sel().check(UI.selectAllCheckBox);
				 sel().clickAndWait(UI.deleteButton);
				 //sel().waitForElement(UI.notificaton, "60000");
			 }
		 }
		 
		 public void catalogDelete(Catalog catalog){
			 nav.catalogs();
			 Element thisCatalog = new Element(UI.checkboxNextToText,catalog.getName());
			 if(sel().isElementPresent(thisCatalog)){
				 sel().check(thisCatalog);
				 sel().clickAndWait(UI.deleteButton);
			 }
		 }
		 
		 
		 /**
		  * Build or Push a template
		  * @param runner
		 * @param scpTool
		 * @param template
		 * @param providerAccount TODO
		  * @param command, 0=build, 1=push
		  */
		 protected void buildPushTemplate(SSHCommandRunner runner, SCPTools scpTool, Templates template, int command, ProviderAccount providerAccount ){
			 if(command==0){
				 Provider myProvider = providerAccount.getProvider();
				 scpTool.sendFile(template.getTemplateXMLFile(), "/root/");
				 //debug
				 log.info("DEBUG"+template.getTemplateXMLRemoteFilePath()+template.getTemplateXMLFileName());
				 runner.runCommandAndWait("aeolus image build --environment default --target "+myProvider.getProviderTarget()+" --template="+template.getTemplateXMLRemoteFilePath()+template.getTemplateXMLFileName());
				 String buildTxt = runner.getStdout();
				 Scanner scanner = new Scanner(buildTxt);
				 ArrayList<String> myBuildTxt = new ArrayList<String>();
				 while(scanner.hasNextLine()){
						String line = scanner.nextLine();
						myBuildTxt.add(line);
					}
					//In array target Image = 1, image = 2, build = 3, Status = 4, Percent = 5
					for(String myLine: myBuildTxt){
						log.log(Level.FINE,myLine);
					}
					
					template.setTargetImageID(myBuildTxt.get(2).split("\\s+")[2].trim());
					log.info("Target ID="+template.getTargetImageID());
							
					template.setImageID(myBuildTxt.get(2).split("\\s+")[0].trim());
					log.info("Image ID="+template.getImageID());
					
					template.setBuildID(myBuildTxt.get(2).split("\\s+")[1].trim());
					log.info("Build ID="+template.getBuildID());
					
					
			 }
			 else if(command==1){
				 Provider myProvider = providerAccount.getProvider();
				runner.runCommandAndWait("aeolus image push  --account "+ providerAccount.getAccountName() +
						 " --targetimage " + template.getTargetImageID());
				String pushTxt = runner.getStdout();
				Scanner scanner1 = new Scanner(pushTxt);
				ArrayList<String> myPushTxt = new ArrayList<String>();
				while(scanner1.hasNextLine()){
					String line = scanner1.nextLine();
					myPushTxt.add(line);
				}
				//In array target Image = 1, image = 2, build = 3, Status = 4, Percent = 5
				for(String myLine: myPushTxt){
					log.log(Level.FINEST,myLine);
				}
				
				//current work around because provider image has disappeared 
				
				template.setProviderImageID(myPushTxt.get(2).split("\\s+")[1].trim());
				log.info("Provider ID="+template.getProviderImageID());
 
			 }
			 else {
				 throw new SeleniumException("unrecongnized command flag for aeolus image, -b(0) or -p(1) only");
			 }
			 
		 }
		 
		 
		 public void importTemplateCLI(String templateID, SSHCommandRunner runner, Templates template, ProviderAccount providerAccount ){
				 Provider myProvider = providerAccount.getProvider();
				 //debug
				 //log.info("DEBUG"+template.getTemplateXMLRemoteFilePath()+template.getTemplateXMLFileName());
				 runner.runCommandAndWait("aeolus image import --account "+providerAccount.getAccountName()+" --id "+ templateID +" -E default"+ " --description '<image><name> "+template.getName() +" </name></image>'");
				 String buildTxt = runner.getStdout();
				 Scanner scanner = new Scanner(buildTxt);
				 ArrayList<String> myBuildTxt = new ArrayList<String>();
				 while(scanner.hasNextLine()){
						String line = scanner.nextLine();
						myBuildTxt.add(line);
					}
					//In array target Image = 1, image = 2, build = 3, Status = 4, Percent = 5
					for(String myLine: myBuildTxt){
						log.log(Level.FINEST,myLine);
					}
					
					template.setTargetImageID(myBuildTxt.get(2).split("\\s+")[2].trim());
					log.info("Target ID="+template.getTargetImageID());
							
					template.setImageID(myBuildTxt.get(2).split("\\s+")[0].trim());
					log.info("Image ID="+template.getImageID());
					
					template.setBuildID(myBuildTxt.get(2).split("\\s+")[1].trim());
					log.info("Build ID="+template.getBuildID());
					
					/*template.setTargetImageID(myBuildTxt.get(1).split(":")[2].trim());
					log.info("Target ID="+template.getTargetImageID());
					
					template.setImageID(myBuildTxt.get(2).split(":")[0].trim());
					log.info("Image ID="+template.getImageID());	
					
					template.setBuildID(myBuildTxt.get(3).split(":")[1].trim());
					log.info("Image ID="+template.getBuildID());*/
					
					if(runner.getExitCode() == 0){
						DataList.templateAdd(template);
						DataList.writeTemplateData();
					}
					else{
						throw new SeleniumException("Image import failed");
					}
		 }
		 
		 
		 
		 
		 /**
		  * Get status of template build or push
		  * @param runner
		 * @param scpTool TODO
		 * @param template
		 * @param command 0=build, 1=push
		  */
		 protected String getTemplateStatus(SSHCommandRunner runner, SCPTools scpTool, Templates template, int command ){
			 runner.runCommandAndWait("ls /root/getTemplateStatus.py");
			 if(runner.getExitCode() != 0){
				 log.info("did not find imgFactoryStatus script, will scp");
				 scpTool.sendFile(System.getProperty("ce.prj.home")+"/scripts/getTemplateStatus.py", "/root/");
				 runner.runCommandAndWait("chmod +x /root/getTemplateStatus.py");
			 }
			 String id ="";
			 String flag = "";
			 if(command==0){
				 log.info("testing template build status");
				 id = template.getTargetImageID();
				 flag = "-b";
			 }
			 else if(command==1){
				 log.info("testing template push status");
				 id = template.getProviderImageID();
				 flag = "-p";
			 }
			 else {
				 throw new SeleniumException("unrecongnized command flag for aeolus image, -b(0) or -p(1) only");
			 }
			 
			 runner.runCommandAndWait("/root/getTemplateStatus.py -i "+ id);
			 
			 String status = runner.getStdout();
			 Scanner scanner1 = new Scanner(status);
			
			 ArrayList<String> myStatusTxt = new ArrayList<String>();
			 while(scanner1.hasNextLine()){
				String line = scanner1.nextLine();
				myStatusTxt.add(line);
			 }
			 log.info("STATUS="+myStatusTxt.get(0).trim());
			 return myStatusTxt.get(0).trim();
			 
		 }
		 
	public void configServerCreate(ProviderAccount providerAccount, ConfigServer configServer){
		nav.ProviderAccountDetails(providerAccount);
		sel().clickAndWait(UI.configServerAdd);
		editFormOfStrings(configServer.getFormData());
		sel().clickAndWait(UI.save);
		Assert.assertTrue(sel().isTextPresent("Config server added."),"Asserted config server success message present");
	}
	
	public boolean configServerVerify(ProviderAccount providerAccount){
		nav.ProviderAccountDetails(providerAccount);
		sel().clickAndWait(UI.configServerTest);
		return sel().isTextPresent("Test successful");
	}
		
	
		// END 1.2 IMPORT
    
}
