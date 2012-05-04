package com.redhat.qe.ce10.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Sets.SetView;
import com.redhat.qe.auto.selenium.Element;
import com.redhat.qe.auto.tcms.ImplementsNitrateTest;
import com.redhat.qe.auto.testng.Assert;
import com.redhat.qe.auto.testng.TestNGUtils;
import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.ce10.data.Provider;
import com.redhat.qe.ce10.data.ProviderAccount;
import com.redhat.qe.ce10.data.ProviderAccount_ec2;
import com.redhat.qe.ce10.data.Templates;
import com.redhat.qe.ce10.locators.UIElements;
import com.thoughtworks.selenium.SeleniumException;

@Test(groups="testplan-CloudEngine:ImageTemplates")
public class ManageTemplates extends SeleniumTestScript {
	/*
	private Templates Fedora_01;
	private Templates Fedora_02;
	private Templates Fedora_03;
	public Templates RHEL_01;
	Set<String> myPackageGroups = new TreeSet<String>();
	File xmlFile = new File(System.getProperty("ce.prj.home")+"/scripts/RHEL61.tpl");



	// TODO:	
	//			verifyErrorWhenNoEc2ProviderExists
	
	
	 * 
	 * 		Create template tests
	 * 
	 
	
	@BeforeTest(description="need to setup a valid provider and provider account")
	public void setupProvider(){
		tasks.loginAsAdmin();
		tasks.shutdownAllInstances(false);
		Provider ec2UsEast1 = new Provider(Provider.EC2_US_E,Provider.EC2_US_E_URL,Provider.EC2_ProviderTarget);
		ProviderAccount_ec2 ec2ProviderAccount = new ProviderAccount_ec2(ec2UsEast1.getName(), ec2UsEast1);
		
		
		
		if (! tasks.doesProviderAccountExist(ec2ProviderAccount)){
			tasks.deleteAllProviderAccounts(ec2ProviderAccount);
			tasks.createProviderAccount(ec2ProviderAccount);
		}
		
	}
	
	@Test(description="Create a template", dataProvider="getTemplateDataObjects")
	public void createABasicTemplate(String testname, Templates templ) throws Exception {
	    tasks.createTemplate(null, null, null);
    	tasks.verifyTemplateInRow(templ.getName(), true);
	}


	@Test(description="Verify error on name >255 characters")
	public void verifyNameTooLongError() throws Exception {
		Templates templ = new Templates(xmlFile);
		templ.set_PackageGroup_Base();
		tasks.createTemplate(null, null, null);
        
		Assert.assertTrue(selenium.isTextPresent("Name is too long"),"Asserted correct error message found");
		
	    selenium.open(UIElements.url_base);
	}


	@Test(description="Verify long name in table wraps")
	public void verifyLongNamesWrapInTable() throws Exception {
		Templates templ = new Templates(xmlFile);
		templ.set_PackageGroup_Base();
        tasks.createTemplate(null, null, null);

		String sColumnWidth = selenium.getEval("this.browserbot.getCurrentWindow().document.getElementsByTagName('th')[1].offsetWidth");
		// Now that we have the width, lets cleanup before we assert the value
		tasks.deleteTemplateInRow(templ.getName());
		
		// assert
        Assert.assertTrue(Integer.parseInt(sColumnWidth) < 500, "Column width("+sColumnWidth+") less than 500");
	}


	@Test(description="duplicate name error on create")
	public void verifyCreateTemplateNameErrorWithDuplicateName() throws Exception {
		String sOriginalName = "dupNameTest_"+tasks.generateRandomString(5);
		
		// first create a single template
		Templates templateOne = new Templates(xmlFile);
		templateOne.set_PackageGroup_Base();
		tasks.createTemplate(null,null, null);
        
        // create second template with duplicate name
		tasks.createTemplate(null, null, null);

		// verification
		Assert.assertTrue(selenium.isTextPresent(Templates.NAME_TAKEN), "Asserted the notification,"+Templates.NAME_TAKEN+", is visible");
        tasks.verifyTemplateInRow(templateOne.getName(), true);
        
	}	

	@Test(description="template name cannot be blank")
	public void verifyErrorOnBlankName() throws Exception {
		Templates templateOne = new Templates(xmlFile);
		templateOne.set_PackageGroup_Base();
		tasks.createTemplate(null, null, null);
		Assert.assertTrue(selenium.isTextPresent(Templates.BLANK_NAME), "Asserted the notification, "+Templates.BLANK_NAME+", is visible");
	}
	
	@Test(description="Create a basic template and verify correct packages", dataProvider="getTemplateDataObjects")
	public void createBasicTemplateCheckPackages(String testname, Templates template) throws Exception {
		//String templateName = "rpmCheck"+tasks.generateRandomString(5);
		//TemplateData template = new TemplateData(templateName,TemplateData.FEDORA,TemplateData.arch_x86_64);
		//template.set_PackageGroup_Base();

		tasks.createTemplate(null, null, null);
        tasks.verifyTemplateInRow(template.getName(), true);
		Set<String> packagesInTemplate = template.getPackages();
		//Set<String> rpmsFoundInWebUI = tasks.getTemplateRpmList(template);
		
		//comparePackages(packagesInTemplate, rpmsFoundInWebUI);
 
        
	}
	
	
	


	
	private void printSet(Set<String> mySet){
		StringBuffer myStringBuffer = new StringBuffer();
		for(String s:mySet ){
			myStringBuffer.append(s+" ");
		}
		log.info(myStringBuffer.toString());
	}
	

	

	
	
	
	 * 
	 * 		Data Provider
	 * 
	 
	
	@DataProvider(name="getTemplateDataObjects")
    public Object[][] getTemplateDataForPlatformsAs2dArray() {
        return TestNGUtils.convertListOfListsTo2dArray(getObject());
    }
    protected List<List<Object>> getObject() {
        
        Fedora_01 = new Templates(xmlFile);
        Fedora_01.addPackageGroup(true, "base","web-server");
       // RHEL_01 = new TemplateData("RHELTest01",TemplateData.RHEL,"5", TemplateData.arch_x86_64);
        Fedora_02 = new Templates(xmlFile);
        Fedora_02.addPackageGroup(true,"base", "base-x");
        Fedora_03 = new Templates(xmlFile);
        Fedora_03.addPackageGroup(true,"base", "gnome-desktop");
       
        List<List<Object>> ll = new ArrayList<List<Object>>();
        //                                  Element table,              String columnName
        ll.add(Arrays.asList(new Object[]{ "packages = base ", Fedora_01} ));
        ll.add(Arrays.asList(new Object[]{ "packages = base + base-x", Fedora_02} ));
        ll.add(Arrays.asList(new Object[]{ "packages = base + gnome-desktop", Fedora_03} ));
//        ll.add(Arrays.asList(new Object[]{ new TemplateData("FedoraTest04",TemplateData.FEDORA,"13", TemplateData.arch_x86_64)} ));
//        ll.add(Arrays.asList(new Object[]{ new TemplateData("FedoraTest05",TemplateData.FEDORA,"13", TemplateData.arch_x86_64)} ));
//        ll.add(Arrays.asList(new Object[]{ new TemplateData("FedoraTest06",TemplateData.FEDORA,"13", TemplateData.arch_x86_64)} ));
//        ll.add(Arrays.asList(new Object[]{ new TemplateData("FedoraTest07",TemplateData.FEDORA,"13", TemplateData.arch_x86_64)} ));
//        ll.add(Arrays.asList(new Object[]{ new TemplateData("FedoraTest08",TemplateData.FEDORA,"13", TemplateData.arch_x86_64)} ));
//        ll.add(Arrays.asList(new Object[]{ new TemplateData("FedoraTest09",TemplateData.FEDORA,"13", TemplateData.arch_x86_64)} ));
       // ll.add(Arrays.asList(new Object[]{ RHEL_01} ));
        return ll;
    }
    
    
     * 
     * 		Private Methods
     * 
     
    
    private void deleteTemplateWrapper(Templates templ) {
        tasks.verifyTemplateInRow(templ.getName(), true);
        tasks.deleteTemplateInRow(templ.getName());
        tasks.verifyTemplateInRow(templ.getName(), false);
    }
    
	private void comparePackages(Set<String> packagesInTemplate, Set<String> rpmsFoundInWebUI){
		SetView<String> intersection =  com.google.common.collect.Sets.intersection(packagesInTemplate, rpmsFoundInWebUI);
		SetView<String> union =  com.google.common.collect.Sets.union(packagesInTemplate, rpmsFoundInWebUI);
		Set<String> diff = com.google.common.collect.Sets.difference(union, intersection);
		log.info("BEGIN Object: Packages listed in the template Object.. ie.. what we're expecting");
		printSet(packagesInTemplate);
		log.info("END Object: Packages listed in the template Object.. ie.. what we're expecting");
		
		log.info("BEGIN WEBUI: Packages listed in the webui template page");
		printSet(rpmsFoundInWebUI);
		log.info("BEGIN WEBUI: Packages listed in the webui template page");
	
		log.info("BEGIN DIFF: Differrence between the object template and the template listed in cloud engine");
		printSet(diff);
		log.info("END DIFF: Differrence between the object template and the template listed in cloud engine");
		
		Assert.assertTrue(diff.isEmpty(),"Template's RPM list match expected values");
	}*/
}
