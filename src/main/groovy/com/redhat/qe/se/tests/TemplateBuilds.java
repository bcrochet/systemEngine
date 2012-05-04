package com.redhat.qe.ce10.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.redhat.qe.auto.selenium.Element;
import com.redhat.qe.auto.testng.TestNGUtils;
import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.ce10.data.Instance;
import com.redhat.qe.ce10.data.Provider;
import com.redhat.qe.ce10.data.ProviderAccount;
import com.redhat.qe.ce10.data.ProviderAccount_ec2;
import com.redhat.qe.ce10.data.RandomData;
import com.redhat.qe.ce10.data.Templates;
import com.redhat.qe.ce10.data.DataList;


@Test(groups="testplan-CloudEngine:templateBuilds")
public class TemplateBuilds extends SeleniumTestScript{
/*
	
	 Instance instanceSimple;
	    Instance instanceComplex;
	    Set<String> myPackageGroups = new TreeSet<String>();
	    String installedPackages;

	    private HashSet<Templates> list;
	    private DataList temp_list_class = new DataList();
	    List<Instance> myList;
	    RandomData random = new RandomData(5);
	    public Templates templateSimple;
	    public Templates templateComplex;
	    Element failedTemplate;
	    
	    RandomData r = new RandomData(5);
	    Provider ec2UsEast1;
	    ProviderAccount_ec2 ec2ProviderAccount;
	    File xmlFile = new File(System.getProperty("ce.prj.home")+"/scripts/RHEL61.tpl");
	    
	    @BeforeTest(description="need to setup a valid provider and provider account")
		public void setupProvider(){
	    	ec2UsEast1 = new Provider(Provider.EC2_US_E,Provider.EC2_US_E_URL,Provider.EC2_ProviderTarget);
	    	tasks.loginAsAdmin();
			tasks.shutdownAllInstances(false);
			ec2ProviderAccount = new ProviderAccount_ec2(ec2UsEast1.getName(), ec2UsEast1);
			
			//tasks.deleteAllProviders();
			//tasks.deleteAllProviderAccounts();
			//tasks.createProvider(ec2Provider);
			tasks.createProviderAccount(ec2ProviderAccount);	
		}
	    
	    
	    @Test(description="Create a template",dataProvider="getTemplateDataObjects")
	    public void createABasicTemplateAndBuild(String testname,Templates td){
	       tasks.templateBuild(null, null, null, null);
	    }
	    	    
	    
	    @DataProvider(name="getTemplateDataObjects")
	    private Object[][] getTemplateDataForPlatformsAs2dArray() {
	        return TestNGUtils.convertListOfListsTo2dArray(getTemplateDataObjects());
	    }
	    private List<List<Object>> getTemplateDataObjects() {
	    	List<List<Object>> ll = new ArrayList<List<Object>>();
	      
	    	
	        templateSimple = new Templates(xmlFile);
	        templateSimple.set_PackageGroup_Base();
	        templateComplex = new Templates(xmlFile);
	        templateComplex.set_PackageGroup_Base();
	        templateComplex.addPackageGroup(false, "base-x");
	    	
	        //                                  Element table,              String columnName
	        ll.add(Arrays.asList(new Object[]{"Template w/ core package group", templateSimple} ));
	        ll.add(Arrays.asList(new Object[]{ "Template w/ core + base-x package group", templateComplex} ));
	     
	        return ll;
	    }
	     */
	
	
	
}
