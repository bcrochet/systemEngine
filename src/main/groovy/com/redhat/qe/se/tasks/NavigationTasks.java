package com.redhat.qe.ce10.tasks;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.redhat.qe.auto.selenium.Element;
import com.redhat.qe.auto.selenium.ExtendedSelenium;
import com.redhat.qe.auto.selenium.TabElement;
import com.redhat.qe.auto.testng.Assert;
import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.ce10.data.Provider;
import com.redhat.qe.ce10.data.ProviderAccount;
import com.redhat.qe.ce10.data.Catalog;
import com.redhat.qe.ce10.locators.UIElements;
import com.thoughtworks.selenium.SeleniumException;

public class NavigationTasks {

	protected static UIElements UI = (UIElements) SeleniumTestScript.instantiator
			.getVersionedInstance(UIElements.class);
	protected static AssortedTasks tasks = (AssortedTasks) SeleniumTestScript.instantiator
			.getVersionedInstance(AssortedTasks.class);
	protected static Logger log = Logger.getLogger(NavigationTasks.class
			.getName());

	String timeout = "60000";

	protected void forceTabElementClick(TabElement tabElement) {
		if (sel().isElementPresent(tabElement)) {
			if (sel().isElementSelected(tabElement)) {
				sel().clickAndWait(tabElement.getSelectedElement());
			} else {
				sel().clickAndWait(tabElement);
			}
		} else {
			throw new SeleniumException(
					"Can't force a click on a non-existent tab element!  ("
							+ tabElement + ")");

		}
	}

	// pass a boolean for create or edit or select account.. boo
	public void manageProviderAccount(ProviderAccount providerAccount) {
		adminSettings();
		log.info("Navigate to Provider Accounts");
		proxyErrorWorkAround();
		manageProviders();
		Provider thisProvider = providerAccount.getProvider();
		Element providerName = new Element(UI.link,thisProvider.getName());
		Element myProviderHeader = new Element(UI.providerHeader,thisProvider.getName());
		if(sel().isElementPresent(myProviderHeader)){
			log.info("Provider already selected");
			sel().click(UI.providerAccountsList);
			sel().waitForElement(UI.providerAccountNew, timeout);
		}
		else{
			sel().click(providerName);
			sel().waitForElement(myProviderHeader, timeout);
			sel().click(UI.providerAccountsList);
			if(sel().isElementPresent(UI.providerAccountNew)){
				sel().waitForElement(UI.providerAccountNew, timeout);
			}
			else{
				log.info("UI.providerAccountsList not working");
				sel().refresh();
				sel().click(UI.providerAccountsListLink);
				sel().waitForElement(UI.providerAccountNew, timeout);
			}
		}
	}

	public void editProvider(ProviderAccount providerAccount) {
		Element providerLink = new Element(UI.providerNameLink,	providerAccount.getName());
		manageProviders();

		if (!sel().isElementPresent(providerLink)) {
			throw new SeleniumException("Provider (" + providerAccount.getName()
					+ ") not found to edit");
		} else {
			sel().clickAndWait(providerLink);
			sel().click(UI.editProvider);
			sel().waitForElement(UI.providerUrl, "5000");
		}
	}

	public void ProviderAccountDetails(ProviderAccount providerAccount) {
		manageProviderAccount(providerAccount);
		Element thisProviderAccount = new Element(UI.link,providerAccount.getAccountName());
		sel().clickAndWait(thisProviderAccount);
	}

	public void editProviderAccount(ProviderAccount providerAccount) {
		ProviderAccountDetails(providerAccount);
		sel().click(UI.filterView);
	}

	protected void proxyErrorWorkAround() {
		// work around for BZ#651562
		if (sel().isTextPresent("Proxy Error")) {
			log.log(Level.SEVERE,
					"******* Proxy Error detected, closing session *********");

			// sel().refresh();
			try {
				sel().testNGScreenCapture();
				log.info("******** SHUTTING DOWN SELENIUM ************");
				sel().stop();
				log.info("******** SELENIUM SHUTDOWN, FIREFOX SHOULD BE KILLED ************");
				log.info("******** Starting SELENIUM ************");
				sel().start();
				tasks.login(SeleniumTestScript.ceAdminUser,
						SeleniumTestScript.ceAdminPassword,
						SeleniumTestScript.ceServerPath);
				log.info("******** Finished Starting SELENIUM ************");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.log(Level.SEVERE, SeleniumTestScript.getStackTrace(e));
			}
		}
	}

	public void Templates() {
		throw new SeleniumException("using old version of cloud engine");
	}

	public void permissions() {
		adminSettings();
		if (!sel().isElementSelected(UI.permissions)) {
			sel().clickAndWait(UI.permissions);
		}
	}

	public void hwpProfileDetails(String sProfileName) {
		if (!sel().isElementPresent(
				new Element(UI.lsHwpSpecificHeadingContains,
						UI.sHwpDetailsContainerXpath, sProfileName))) {
			if (!sel().isElementSelected(UI.hwProfiles)) {
				sel().clickAndWait(UI.hwProfiles);
			}

			sel().click(new Element(UI.link, sProfileName));
			try {
				sel().waitForElement(new Element(UI.sHwpDetailsContainerXpath),
						"5000");
			} catch (SeleniumException e) {
				Assert.assertTrue(false, "hw profile details container found");
			}
		}

	}

	public String hwpProviderDetails(String sProfileName) {

		String sId = null;
		if (!sel().isElementPresent(
				new Element(UI.lsHwpProviderHeading, sProfileName))) {
			hwpProfileDetails(sProfileName);
			sel().click(UI.eHwpProvidersButton);
			sel().waitForElement(
					new Element(UI.lsHwpProviderHeading, sProfileName), "5000");
			sId = sel().getAttributes(UI.eHwpProvidersButton)
					.getProperty("href").substring(1);
		} else {
			sId = sel().getAttributes(UI.eHwpProvidersButton)
					.getProperty("href").substring(1);
		}

		return sId;
	}

	public ExtendedSelenium sel() {
		return SeleniumTestScript.selenium;
	}

	// begin add 1.1 nav

	public void InstanceManagement() {
		if(sel().isElementPresent(UI.instanceStopSelected)){
			log.info("already on the correct page");
		}
		else{
			monitor();
			sel().click(UI.filterView);
			sel().waitForElement(UI.instancesList, "60000");
			sel().click(UI.instancesList);
			sel().waitForElement(UI.instanceStopSelected, "60000");
		}

	}

	public void launchInstance() {
		InstanceManagement();
	}

	public void roleDetails(String sRoleName) {

		Element scope = new Element(UI.roleScope, sRoleName);
		if (!sel().isElementPresent(scope)) {
			roles(true);
			sel().click(new Element(UI.roleNameLink, sRoleName));
			sel().waitForElement(scope, "5000");
		}
	}

	public void ResourceManagement() {
		if (sel().isElementPresent(UI.resourceManagent)) {
			sel().clickAndWait(UI.resourceManagement);
		} else {
			if (sel().isElementPresent(UI.resourceManagement)) {
				sel().clickAndWait(UI.resourceManagement);
			}
		}
	}

	// end add 1.1 nav
	// begin add 1.2 nav
	public void adminSettings() {
		if(sel().isElementPresent(UI.administerSelected)){
			log.info("Administer already selected");
		}
		else{
			if(sel().isElementPresent(UI.administer)){
				sel().clickAndWait(UI.administer);
			}
			else{
				try {
					sel().screenCapture();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

	public void manageUsers() {
		adminSettings();
		sel().clickAndWait(UI.usersAndGroups);
		sel().waitForElement(UI.usersHeader, timeout);

	}

	public void roles(boolean bRefresh) {
		throw new SeleniumException("not implemented");
	}

	public void manageProviders() {
		if (sel().isElementPresent(UI.cloudProvidersChooseAProvider)) {
			log.info("Already on providers page");
		} else {
			adminSettings();
			if(SeleniumTestScript.testUpstream)
				sel().clickAndWait(UI.cloudProvidersUpstream);
			else
				sel().clickAndWait(UI.cloudProviders);
			sel().waitForElement(UI.cloudProvidersChooseAProvider, timeout);
		}

	}

	
	public void content(){
		adminSettings();
		sel().clickAndWait(UI.content);
		
	}
	
	public void hardwareProfiles(boolean bRefresh) {
		content();
		sel().click(UI.hardware);
		sel().waitForElement(UI.hardwareProfile_new, "60000");
		
	}

	// pass in provider
	public void realms(boolean bRefresh) {
		content();
		if(sel().isElementPresent(UI.realms)){
			sel().click(UI.realms);
		}
		else{
			//holy crap this should not happen
			adminSettings();
			content();
			sel().click(UI.realms);
		}
		sel().waitForElement(UI.realmNew, "60000");
		
	}

	// pass in catalog entry object
	public void catalogEntries(Catalog catalog) {
		adminSettings();
		catalogs();
		Element thisCatalog = new Element(UI.link,catalog.getName());
		Element thisCatalogEntryHeader = new Element(UI.ceHeader, catalog.getName());
		if(sel().isElementPresent(thisCatalogEntryHeader)){
			log.info("Already in Catalog Entry, "+catalog.getName());
		}
		else{
			sel().clickAndWait(thisCatalog);	
		}
		

	}

	public void catalogs() {
		if (sel().isElementPresent(UI.catalogNew)) {
			log.info("Already on catalog page");
		} else {
			content();
			if(sel().isElementPresent(UI.catalogs)){
				sel().click(UI.catalogs);
			}
			else{
				try {
					sel().screenCapture();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			sel().waitForElement(UI.catalogNew, timeout);
		}
	}

	public void pools() {
		adminSettings();
		sel().clickAndWait(UI.clouds);
	}

	public void monitor() {
		sel().clickAndWait(UI.monitorTab);
	}

	public void settings() {
		throw new SeleniumException("Not Implemented");
	}

	// end add 1.2 nav

}
