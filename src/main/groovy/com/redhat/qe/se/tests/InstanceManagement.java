package com.redhat.qe.ce10.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.redhat.qe.auto.selenium.Element;
import com.redhat.qe.auto.testng.Assert;
import com.redhat.qe.auto.testng.TestNGUtils;
import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.ce10.data.Catalog;
import com.redhat.qe.ce10.data.CatalogEntry;
import com.redhat.qe.ce10.data.DataList;
import com.redhat.qe.ce10.data.Deployment;
import com.redhat.qe.ce10.data.HardwareProfile;
import com.redhat.qe.ce10.data.Instance;
import com.redhat.qe.ce10.data.Provider;
import com.redhat.qe.ce10.data.ProviderAccount_Mock;
import com.redhat.qe.ce10.data.ProviderAccount_RHEVM;
import com.redhat.qe.ce10.data.ProviderAccount_Vsphere;
import com.redhat.qe.ce10.data.ProviderAccount_ec2;
import com.redhat.qe.ce10.data.RandomData;
import com.redhat.qe.ce10.data.Realm;
import com.redhat.qe.ce10.data.Templates;
import com.redhat.qe.tools.SCPTools;
import com.redhat.qe.tools.SSHCommandRunner;

@Test(groups="testplan-CloudEngine:Instances")
public class InstanceManagement extends SeleniumTestScript{
    

    Instance instanceSimple;
    Instance instanceComplex;
    Set<String> myPackageGroups = new TreeSet<String>();
    String installedPackages;


    List<CatalogEntry> listOfCatalogEntries;
    RandomData random = new RandomData(5);
    public Templates templateSimple;
    public Templates templateComplex;
    Element failedTemplate;
    HardwareProfile hp;
    Catalog catalog;
    HashSet<CatalogEntry> catalogEntryList;
    HashSet<Realm> realms;
    List<Templates> templates;
    Realm myRealm;
    int instanceScale = 1;
    String key = System.getProperty("ce.auto.privatekey");
	String passphrase = System.getProperty("jon.server.sshkey.passphrase");
	SSHCommandRunner sshCommandRunner;
	SCPTools scpTools;
    
    
    RandomData r = new RandomData(5);
    
    @BeforeClass(alwaysRun=true)
    public void getLaunchableTemplates()  throws ClassNotFoundException, FileNotFoundException, IOException{
    	HashSet<Catalog> catalogs = DataList.getListOfCreatedCatalogs();
    	realms = DataList.getListOfCreatedRealms();
    	templates = DataList.getListOfBuiltTemplates();
    	
		for(Catalog c:catalogs){
			listOfCatalogEntries = c.getListOfCatalogEntries();
		}
		for(Realm r:realms){
			myRealm = r;
		}
		
		if (testProviderMock) {
			instanceScale = 40;
		}
		else if(testProviderEC2) {
			instanceScale = 20;
		}
		else{
			instanceScale = 5;
		}
		
		sshCommandRunner = new SSHCommandRunner(ceServerHostname, "root",
				key, passphrase,
				"cat /etc/redhat-release | awk '{print $1}' ");
		scpTools = new SCPTools(ceServerHostname, "root", key, passphrase);
		sshCommandRunner.run();
		
		//tasks.shutdownAllInstances(false);
    }
    
    @Test(description="launch quickly",alwaysRun=true)
    public void launchInstancesQuickly() throws IOException{
    	boolean sshWorked = false;
    	List<Instance> instanceList = new ArrayList<Instance>();
    	for(CatalogEntry ce:listOfCatalogEntries){
    		log.info("catalogEntry Name="+ce.getCatalogEntryName());
    		Deployment myDeployment = new Deployment(ce, myRealm);
    		log.info("deployment Name="+myDeployment.getName());
    		myDeployment.start();
    		myDeployment.sshInAndVerify(true, true, false);
    	}     
   }
    
    @Test(description="launch copious amounts of instances")
    public void launchLotsInstances() throws IOException{
    	boolean sshWorked = false;
    	CatalogEntry ce = listOfCatalogEntries.get(0);
    	for(int i=0;i<instanceScale;i++){
    		log.info("catalogEntry Name="+ce.getCatalogEntryName());
    		Deployment myDeployment = new Deployment(ce, myRealm);
    		log.info("deployment Name="+myDeployment.getName());
    		myDeployment.start();
    		myDeployment.sshInAndVerify(true, true, false);
    		log.info("successfully launched "+i+"/"+instanceScale+" instances");
    	}     
   }
    
    @Test(description="launch instance w/ every HWP",dataProvider="getHWP")
    public void launchInstancesInEveryHWP(String testName, HashMap<String,String> hwp) throws IOException{
    	boolean sshWorked = false;
    	Templates myTemplate = templates.get(0);
    	CatalogEntry ce = listOfCatalogEntries.get(0);
    	Catalog thisCatalog = ce.getCatalog();
    	HardwareProfile thisHWP = new HardwareProfile(hwp, true);
    	Instance frontEnd = new Instance("testInstanceFrontEnd",myTemplate, thisHWP, SeleniumTestScript.userAdmin);
    	CatalogEntry thisCatalogEntry = new CatalogEntry(thisCatalog, true, sshCommandRunner, scpTools, frontEnd);
    
		Deployment myDeployment = new Deployment(ce, myRealm);
		log.info("deployment Name="+myDeployment.getName());
		myDeployment.start();
		myDeployment.sshInAndVerify(true, true, false);
    	   
   }
    
    
	@DataProvider(name="getHWP")
	public Object[][] getHWP() {
		return TestNGUtils.convertListOfListsTo2dArray(hwpEC2());
	}
	protected List<List<Object>> hwpEC2() {
		
		List<List<Object>> ll = new ArrayList<List<Object>>();
		
		if(testProviderEC2) {
			ll.add(Arrays.asList(new Object[]{ "ec2 t1 micro", ProviderAccount_ec2.hwProfile_t1_micro  } ));
			ll.add(Arrays.asList(new Object[]{ "ec2 m1 small", ProviderAccount_ec2.hwProfile_m1_small  } ));
			ll.add(Arrays.asList(new Object[]{ "ec2 c1 medium", ProviderAccount_ec2.hwProfile_c1_medium  } ));
			ll.add(Arrays.asList(new Object[]{ "ec2 m1 large", ProviderAccount_ec2.hwProfile_m1_large  } ));
			ll.add(Arrays.asList(new Object[]{ "ec2 c1 xlarge", ProviderAccount_ec2.hwProfile_c1_xlarge  } ));
			ll.add(Arrays.asList(new Object[]{ "ec2 m1 xlarge", ProviderAccount_ec2.hwProfile_m1_xlarge} ));
			ll.add(Arrays.asList(new Object[]{ "ec2 m2 xlarge", ProviderAccount_ec2.hwProfile_m2_2xlarge } ));
			ll.add(Arrays.asList(new Object[]{ "ec2 m2 xlarge", ProviderAccount_ec2.hwProfile_m2_xlarge} ));
			ll.add(Arrays.asList(new Object[]{ "ec2 m2 4xlarge", ProviderAccount_ec2.hwProfile_m2_4xlarge  } ));
		}
		else{
			ll.add(Arrays.asList(new Object[]{ "vsphere default", ProviderAccount_Vsphere.hwProfile_default } ));
			ll.add(Arrays.asList(new Object[]{ "rhevm default", ProviderAccount_RHEVM.hwProfile_default } ));
			ll.add(Arrays.asList(new Object[]{ "mock default", ProviderAccount_Mock.hwProfile_x86_64LARGE } ));
		}
		
		
		return ll;	
	
    }
    

     
  
    
}
