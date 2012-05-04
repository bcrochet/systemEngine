package com.redhat.qe.ce10.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.redhat.qe.api.base.RhevmApiClient;
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
import com.redhat.qe.ce10.data.ProviderAccount;
import com.redhat.qe.ce10.data.ProviderAccount_Mock;
import com.redhat.qe.ce10.data.ProviderAccount_RHEVM;
import com.redhat.qe.ce10.data.ProviderAccount_Vsphere;
import com.redhat.qe.ce10.data.ProviderAccount_ec2;
import com.redhat.qe.ce10.data.RandomData;
import com.redhat.qe.ce10.data.Realm;
import com.redhat.qe.ce10.data.Roles;
import com.redhat.qe.ce10.data.Templates;
import com.redhat.qe.ce10.data.Users;
import com.redhat.qe.tools.SCPTools;
import com.redhat.qe.tools.SSHCommandRunner;

@Test(groups = "testplan-CloudEngine:DeveloperTest")
public class DevTest extends SeleniumTestScript {

	List<Instance> myList;
	RandomData random = new RandomData(5);
	public Templates RHEL61_x86_64;

	RandomData r = new RandomData(5);
	Provider usEast1;
	Provider usWest1;
	Provider vsphere;
	Provider rhevm;
	Provider mock;
	ProviderAccount_ec2 ec2EastProviderAccount;
	ProviderAccount_ec2 ec2WestProviderAccount;
	ProviderAccount_Vsphere vsphereProviderAccount;
	ProviderAccount_RHEVM rhevmProviderAccount;
	ProviderAccount_Mock mockProviderAccount;
	HardwareProfile hwpEC2;
	HardwareProfile hwpVSphere;
	HardwareProfile hwpRHEVM;
	HardwareProfile hwpMockSmall;
	HardwareProfile hwpMockLarge;
	Realm eastRealm;
	Realm west;
	Realm vsphereRealm;
	Realm rhevmRealm;
	Realm mockRealm;

	String key = System.getProperty("ce.auto.privatekey");
	String passphrase = System.getProperty("jon.server.sshkey.passphrase");
	SSHCommandRunner sshCommandRunner;
	String executeSSHCommands = System.getProperty("ce.useSSH", "1");
	SCPTools scpTools;
	String remoteFilePath = "/root/";
	String deploymentFile = "deployment02";
	String deployment01Path = System.getProperty("ce.prj.home") + "/yum/"
			+ deploymentFile;
	String deploymentDir = "/var/www/html/ce/";
	Catalog myCatalog;
	Catalog importCatalog;
	Deployment myDeployment;
	CatalogEntry myCatalogEntryEC2RHEL6;
	CatalogEntry myCatalogEntryEC2RHEL5;
	CatalogEntry myCatalogEntryVSphereRHEL6;
	CatalogEntry myCatalogEntryVSphereRHEL5;
	CatalogEntry myCatalogEntryRHEVMRHEL6;
	CatalogEntry myCatalogEntryRHEVMRHEL5;
	CatalogEntry myCatalogEntryMockRHEL6;
	CatalogEntry myCatalogEntryMockRHEL5;
	Templates templateBasicRHEL6;
	Templates templateBasicRHEL5;
	Templates templateVmwareRHEL6;
	Templates templateVmwareRHEL5;
	Templates templateRHEVMRHEL6;
	Templates templateRHEVMRHEL5;
	Deployment myDeploymentMockRHEL6;
	Deployment myDeploymentMockRHEL5;
	Deployment myDeploymentEC2RHEL6;
	Deployment myDeploymentEC2RHEL5;
	Deployment myDeploymentVSphereRHEL6;
	Deployment myDeploymentVSphereRHEL5;
	Deployment myDeploymentRHEVMRHEL6;
	Deployment myDeploymentRHEVMRHEL5;
	

	@BeforeClass
	public void initialize() throws IOException {
		if (executeSSHCommands == "1") {
			sshCommandRunner = new SSHCommandRunner(ceServerHostname, "root",
					key, passphrase,
					"cat /etc/redhat-release | awk '{print $1}' ");
			scpTools = new SCPTools(ceServerHostname, "root", key, passphrase);
			sshCommandRunner.run();
			//tasks.checkDeamons(tasks.deamonsStart(), sshCommandRunner);
			RandomData r = new RandomData(5);
			
			myCatalog = new Catalog("test_catalog_"+r, false);
		}
	}

	@Test(description = "need to setup a valid provider and provider account")
	public void setupProvider() {
		// ProviderAccount.deleteAll();

		// currently providers should be setup by the aeolus-configure script.
		// setup object verify provider is there and accurate
		if (testProviderMock) {
			mock = new Provider(Provider.MOCK,Provider.MOCK_URL,Provider.MOCK_ProviderType);
			tasks.deleteAllProviderAccounts(mock);
			mockProviderAccount = new ProviderAccount_Mock(mock.getName(), mock);
		}
		
		if (testProviderEC2) {
			usEast1 = new Provider(Provider.EC2_US_E, Provider.EC2_US_E_URL, Provider.EC2_ProviderType);
			usWest1 = new Provider(Provider.EC2_US_W, Provider.EC2_US_W_URL, Provider.EC2_ProviderType);

			tasks.deleteAllProviderAccounts(usEast1);
			tasks.deleteAllProviderAccounts(usWest1);
			ec2EastProviderAccount = new ProviderAccount_ec2(usEast1.getName(),	usEast1);
			ec2WestProviderAccount = new ProviderAccount_ec2(usWest1.getName(),	usWest1);
			
		}
		if (testProviderVSPHERE) {
			vsphere = new Provider(Provider.VSPHERE, Provider.VSPHERE_URL, Provider.VSPHRE_ProviderType);
			tasks.deleteAllProviderAccounts(vsphere);
			vsphereProviderAccount = new ProviderAccount_Vsphere(vsphere.getName(), vsphere);
		}
		
		if (testProviderRHEVM) {
			rhevm = new Provider(Provider.RHEVM, Provider.RHEVM_URL, Provider.RHEVM_ProviderType);
			tasks.deleteAllProviderAccounts(rhevm);
			rhevmProviderAccount = new ProviderAccount_RHEVM(rhevm.getName(), rhevm);
			
		}

	}

	@Test(description = "create hwp", dependsOnMethods = "setupProvider", dataProvider="getHWPDataObjects")
	public void createHardWareProfile(String testName, HardwareProfile hwp) {
		hwp.delete();
		hwp.createInUI();
	}

	@Test(description = "create realm", dependsOnMethods = "createHardWareProfile")
	public void createRealm() {
		
		if (testProviderMock){
			mockRealm = new Realm("realm-mock",mockProviderAccount);
		}
		if (testProviderEC2) {
			eastRealm = new Realm("realm-ec2-us-east", ec2EastProviderAccount);
			west = new Realm("realm-ec2-us-west", ec2WestProviderAccount);
		}
		if (testProviderVSPHERE) {
			vsphereRealm = new Realm("realm-vsphere", vsphereProviderAccount);
		}
		if (testProviderRHEVM) {
			rhevmRealm = new Realm("realm-rhevm", rhevmProviderAccount);
		}
	}
	
	@Test(description="importSetup",dependsOnMethods="createRealm")
	public void importSetup(){
		Users.loginAsAdmin();
		importCatalog = new Catalog("importCatalog"+r, true);
	}
	
	@Test(description="import a ami",dataProvider="getEC2Amis",dependsOnMethods = "importSetup",alwaysRun=true)
	public void import_AMI(String testname, String ami, String platform, String arch) throws IOException{
		Users.loginAsAdmin();
		
		if (testProviderEC2) {
			Templates importTemplate = new Templates(ami, ami+platform+arch, platform, arch);
			importTemplate.importTemplate(sshCommandRunner, ec2EastProviderAccount);
			
			log.info(importTemplate.getBuildID());
			log.info(importTemplate.getImageID());
			
			Instance myServer = new Instance(ami, importTemplate, hwpEC2, userAdmin);
			CatalogEntry thisCatalogEntry = new CatalogEntry(importCatalog, true, sshCommandRunner, scpTools, myServer);
			Deployment thisDeployment = new Deployment(thisCatalogEntry, eastRealm);
			
			Roles thisRole = new Roles("myRole");
			Users origUser = new Users(5, thisRole, true);
			
			SeleniumTestScript.userAdmin.logout();
			Users.loginAsUser(origUser);
			Assert.assertTrue(thisDeployment.start(),"Asserted the deployment started");
			Assert.assertTrue(thisDeployment.sshInAndVerify(true, false, false),"Asserted ssh is successful to instance");
		}

		
	}
	

	@Test(description = "build template", dependsOnMethods = "createRealm", dataProvider="getDevTemplateDataObjects", alwaysRun=true)
	public void buildTemplate(String testName, Templates thisTemplate, ProviderAccount thisProviderAccount) {
		
			Assert.assertTrue(thisTemplate.build(sshCommandRunner,scpTools, thisProviderAccount),"Asserted image built successfully");
			Assert.assertTrue(thisTemplate.push(sshCommandRunner,scpTools, thisProviderAccount),"Asserted image was pushed to the provider");
		
	}

	@Test(description = "create catalog", dependsOnMethods = "buildTemplate")
	public void createCatalog() {
		Users.loginAsAdmin();
		myCatalog.createInUI();

	}

	@Test(description = "create catalog entry", dependsOnMethods = "createCatalog",dataProvider="getCatalogDataObjects")
	public void createCatalogEntry(String testName, CatalogEntry catalogEntry) {
		
		catalogEntry.createInUI(myCatalog, sshCommandRunner, scpTools);
		

	}

	@Test(description = "test serialization", dependsOnMethods = "createCatalogEntry")
	public void getCatalogEntries() throws IOException, ClassNotFoundException {
		HashSet<Catalog> catalogs;
		final DataList readInData = new DataList();
		catalogs = readInData.getListOfCreatedCatalogs();
		for (Catalog c : catalogs) {
			List<CatalogEntry> catalogEntryList = new ArrayList<CatalogEntry>();
			catalogEntryList = c.getListOfCatalogEntries();
			for (CatalogEntry ce : catalogEntryList) {
				List<Instance> instanceList = ce.getInstanceList();
				for (Instance i : instanceList) {
					log.info(i.getName());
					
					// log.info(i.get_PublicAddress());
				}
			}
		}
	}

	@Test(description = "start deployable", dependsOnMethods = "getCatalogEntries", dataProvider="getDeployableDataObjects")
	public void startDeployable(String testName, Deployment thisDeployment) throws IOException{
		Users.loginAsAdmin();
		Roles thisRole = new Roles("myRole");
		Users origUser = new Users(5, thisRole, true);
		
		SeleniumTestScript.userAdmin.logout();
		Users.loginAsUser(origUser);
		Assert.assertTrue(thisDeployment.start(),"Asserted the deployment started");
		Assert.assertTrue(thisDeployment.sshInAndVerify(true, false, false),"Asserted ssh is successful to instance");
		Assert.assertTrue(thisDeployment.sshInAndVerify(true, true, false),"Asserted rpm packages of instance");
		
	}
	
	
	
	
	
	
	
	@AfterClass
	public void finalDeamonCheck() throws Exception{
/*		// tasks.checkDeamons(tasks.deamonsStart(), sshCommandRunner);
		Users.loginAsAdmin();
		Instance.stopAll(true);
			
		RhevmApiClient rhevm = new RhevmApiClient("qeblade26.rhq.lab.eng.bos.redhat.com", "8443", "admin@internal", System.getProperty("ce.rhevm.password"));
		try{
			List<Instance> rhevmInstances = myDeploymentRHEVMRHEL6.getInstanceList();
			for(Instance i:rhevmInstances){
				Templates thisTemplate = i.getTemplate();
				String providerIDHash = thisTemplate.getProviderImageID();
				String myTemplateIDByName = rhevm.getTemplateIdByName(providerIDHash);
				rhevm.deleteTemplate(myTemplateIDByName, "true");
				
				//String id = rhevm.getVmIdByName(i.getFullRHEVName());
				//rhevm.deleteVm(id);
			}
		}
		catch(NullPointerException npe){
			log.log(Level.FINEST, "RHEVM instances is null, you may not be testing rhevm");
			log.log(Level.FINEST, npe.getMessage());
		}*/
			
	}


	@DataProvider(name = "getDevTemplateDataObjects")
	private Object[][] getDevTemplateDataForPlatformsAs2dArray() throws IOException {
		return TestNGUtils.convertListOfListsTo2dArray(getDevTemplateDataObjects());
	}
	private List<List<Object>> getDevTemplateDataObjects() throws IOException {
		String ce_temp_basic_rhel6 = System.getProperty("ce.prj.home")+ "/templates/Basic/RHEL62.tpl" ;
		String ce_temp_basic_rhel5 = System.getProperty("ce.prj.home")+ "/templates/Basic/RHEL57.tpl";
		String ce_temp_vmware_rhel6 = System.getProperty("ce.prj.home")+ "/templates/Basic/RHEL61vmwareTools.tpl";
		String ce_temp_vmware_rhel5 = System.getProperty("ce.prj.home")+ "/templates/Basic/RHEL57vmwareTools.tpl";
		String ce_temp_rhevm_rhel6 = System.getProperty("ce.prj.home")+ "/templates/Basic/RHEL62RHEVMTools.tpl";
		String ce_temp_rhevm_rhel5 = System.getProperty("ce.prj.home")+ "/templates/Basic/RHEL57RHEVMTools.tpl";
		
		/*File xmlFileBasicRHEL6 = new File(System.getProperty("ce.temp.basic.rhel6",ce_temp_basic_rhel6));
		File xmlFileBasicRHEL5 = new File(System.getProperty("ce.temp.basic.rhel5",ce_temp_basic_rhel5));
		File xmlFileVMWareRHEL6 = new File(System.getProperty("ce.temp.vmware.rhel6",ce_temp_vmware_rhel6));
		File xmlFileVMWareRHEL5 = new File(System.getProperty("ce.temp.vmware.rhel5",ce_temp_vmware_rhel5));
		File xmlFileRHEVMRHEL6 = new File(System.getProperty("ce.temp.rhevm.rhel6",ce_temp_rhevm_rhel6));
		File xmlFileRHEVMRHEL5 = new File(System.getProperty("ce.temp.rhevm.rhel5",ce_temp_rhevm_rhel5));*/
		
		String xmlFileBasicRHEL6 = new String(System.getProperty("ce.temp.basic.rhel6",ce_temp_basic_rhel6));
		String xmlFileBasicRHEL5 = new String(System.getProperty("ce.temp.basic.rhel5",ce_temp_basic_rhel5));
		String xmlFileVMWareRHEL6 = new String(System.getProperty("ce.temp.vmware.rhel6",ce_temp_vmware_rhel6));
		String xmlFileVMWareRHEL5 = new String(System.getProperty("ce.temp.vmware.rhel5",ce_temp_vmware_rhel5));
		String xmlFileRHEVMRHEL6 = new String(System.getProperty("ce.temp.rhevm.rhel6",ce_temp_rhevm_rhel6));
		String xmlFileRHEVMRHEL5 = new String(System.getProperty("ce.temp.rhevm.rhel5",ce_temp_rhevm_rhel5));
		
		
		templateBasicRHEL6 = new Templates(xmlFileBasicRHEL6,remoteFilePath);
		templateBasicRHEL5 = new Templates(xmlFileBasicRHEL5,remoteFilePath);
		templateVmwareRHEL6 = new Templates(xmlFileVMWareRHEL6,remoteFilePath);
		templateVmwareRHEL5 = new Templates(xmlFileVMWareRHEL5,remoteFilePath);
		templateRHEVMRHEL6 = new Templates(xmlFileRHEVMRHEL6,remoteFilePath);
		templateRHEVMRHEL5 = new Templates(xmlFileRHEVMRHEL5,remoteFilePath);
		
		
		List<List<Object>> ll = new ArrayList<List<Object>>();

		// Element table, String columnName
		
		if (testProviderMock){
			ll.add(Arrays.asList(new Object[] { "basic RHEL6 template",	templateBasicRHEL6, mockProviderAccount  }));
			ll.add(Arrays.asList(new Object[] { "basic RHEL5 template",	templateBasicRHEL5, mockProviderAccount  }));
		}
		if (testProviderEC2) {
			ll.add(Arrays.asList(new Object[] { "basic RHEL6 template",	templateBasicRHEL6, ec2EastProviderAccount }));
			ll.add(Arrays.asList(new Object[] { "basic RHEL5 template",	templateBasicRHEL5, ec2EastProviderAccount }));
		}
		if (testProviderVSPHERE) {
			ll.add(Arrays.asList(new Object[] { "basic RHEL6 Vsphere template",	templateVmwareRHEL6, vsphereProviderAccount }));
			ll.add(Arrays.asList(new Object[] { "basic RHEL5 Vsphere template",	templateVmwareRHEL5, vsphereProviderAccount }));
		}
		if (testProviderRHEVM) {
			ll.add(Arrays.asList(new Object[] { "basic RHEL6 RHEVM template",	templateRHEVMRHEL6, rhevmProviderAccount }));
			ll.add(Arrays.asList(new Object[] { "basic RHEL5 RHEVM template",	templateRHEVMRHEL5, rhevmProviderAccount }));
		}
		
		return ll;
	}
	
	
	
	
	@DataProvider(name = "getCatalogDataObjects")
	private Object[][] getCatalogDataForPlatformsAs2dArray() {
		return TestNGUtils.convertListOfListsTo2dArray(getCatalogDataObjects());
	}
	private List<List<Object>> getCatalogDataObjects() {
		Instance frontEndRHEL6 = new Instance("testInstanceFrontEnd",templateBasicRHEL6, hwpEC2, SeleniumTestScript.userAdmin);
		Instance backEndRHEL6 = new Instance("testInstanceBackEnd",templateBasicRHEL6, hwpEC2, SeleniumTestScript.userAdmin);
		List<Instance> instanceList = new ArrayList<Instance>();
		instanceList.add(frontEndRHEL6);
		instanceList.add(backEndRHEL6);

		List<List<Object>> ll = new ArrayList<List<Object>>();

		// Element table, String columnName
		
		if (testProviderMock){
			Instance mockOneRHEL6 = new Instance("testMock",templateBasicRHEL6, hwpMockLarge,	SeleniumTestScript.userAdmin);
			Instance mockOneRHEL5 = new Instance("testMock",templateBasicRHEL5, hwpMockLarge,	SeleniumTestScript.userAdmin);
			myCatalogEntryMockRHEL6 = new CatalogEntry(myCatalog, false, sshCommandRunner, scpTools, mockOneRHEL6);
			myCatalogEntryMockRHEL5 = new CatalogEntry(myCatalog, false, sshCommandRunner, scpTools, mockOneRHEL5);
			ll.add(Arrays.asList(new Object[] { "mock RHEL6 catalogEntry",	myCatalogEntryMockRHEL6 }));
			ll.add(Arrays.asList(new Object[] { "mock RHEL5 catalogEntry",	myCatalogEntryMockRHEL5 }));
		}
		if (testProviderEC2) {
			myCatalogEntryEC2RHEL6 = new CatalogEntry(myCatalog, false,sshCommandRunner, scpTools, instanceList);
			Instance frontEndRHEL5 = new Instance("testInstanceFrontEnd",templateBasicRHEL5, hwpEC2, SeleniumTestScript.userAdmin);
			Instance backEndRHEL5 = new Instance("testInstanceBackEnd",templateBasicRHEL5, hwpEC2, SeleniumTestScript.userAdmin);
			myCatalogEntryEC2RHEL5 = new CatalogEntry(myCatalog, false,sshCommandRunner, scpTools, frontEndRHEL5,backEndRHEL5);
			ll.add(Arrays.asList(new Object[] { "ec2 RHEL6 catalogEntry",	myCatalogEntryEC2RHEL6 }));
			ll.add(Arrays.asList(new Object[] { "ec2 RHEL5 catalogEntry",	myCatalogEntryEC2RHEL5 }));
		}
		if (testProviderVSPHERE) {
			Instance vsphereOneRHEL6 = new Instance("testVsphere",templateVmwareRHEL6, hwpVSphere,	SeleniumTestScript.userAdmin);
			Instance vsphereOneRHEL5 = new Instance("testVsphere",templateVmwareRHEL5, hwpVSphere,	SeleniumTestScript.userAdmin);
			myCatalogEntryVSphereRHEL6 = new CatalogEntry(myCatalog,	false, sshCommandRunner, scpTools, vsphereOneRHEL6);
			myCatalogEntryVSphereRHEL5 = new CatalogEntry(myCatalog,	false, sshCommandRunner, scpTools, vsphereOneRHEL5);
			ll.add(Arrays.asList(new Object[] { "vsphere RHEL6 catalogEntry",	myCatalogEntryVSphereRHEL6 }));
			ll.add(Arrays.asList(new Object[] { "vsphere RHEL5 catalogEntry",	myCatalogEntryVSphereRHEL5 }));
		}
		if (testProviderRHEVM) {
			Instance rhevmOneRHEL6 = new Instance("testRHEVM",templateRHEVMRHEL6, hwpRHEVM, SeleniumTestScript.userAdmin);
			Instance rhevmOneRHEL5 = new Instance("testRHEVM",templateRHEVMRHEL5, hwpRHEVM, SeleniumTestScript.userAdmin);
			myCatalogEntryRHEVMRHEL6 = new CatalogEntry(myCatalog,	false, sshCommandRunner, scpTools, rhevmOneRHEL6);
			myCatalogEntryRHEVMRHEL5 = new CatalogEntry(myCatalog,	false, sshCommandRunner, scpTools, rhevmOneRHEL5);
			ll.add(Arrays.asList(new Object[] { "rhevm catalogEntry",	myCatalogEntryRHEVMRHEL6 }));
			ll.add(Arrays.asList(new Object[] { "rhevm catalogEntry",	myCatalogEntryRHEVMRHEL5 }));
		}
		
		
		return ll;
	}

	@DataProvider(name = "getDeployableDataObjects")
	private Object[][] getDeployableDataForPlatformsAs2dArray() {
		return TestNGUtils
				.convertListOfListsTo2dArray(getDeployableDataObjects());
	}
	private List<List<Object>> getDeployableDataObjects() {

		List<List<Object>> ll = new ArrayList<List<Object>>();

		// Element table, String columnName
		if (testProviderMock){
			myDeploymentMockRHEL6 = new Deployment(myCatalogEntryMockRHEL6, mockRealm);
			myDeploymentMockRHEL5 = new Deployment(myCatalogEntryMockRHEL5, mockRealm);
			ll.add(Arrays.asList(new Object[] { "mock RHEL6 Deployment",	myDeploymentMockRHEL6 }));
			ll.add(Arrays.asList(new Object[] { "mock RHEL5 Deployment",	myDeploymentMockRHEL5 }));
		}
		if (testProviderEC2) {
			myDeploymentEC2RHEL6 = new Deployment(myCatalogEntryEC2RHEL6,eastRealm);
			myDeploymentEC2RHEL5 = new Deployment(myCatalogEntryEC2RHEL5,eastRealm);
			ll.add(Arrays.asList(new Object[] { "ec2 RHEL6 Deployment",	myDeploymentEC2RHEL6 }));
			ll.add(Arrays.asList(new Object[] { "ec2 RHEL5 Deployment",	myDeploymentEC2RHEL5 }));
		}
		if (testProviderVSPHERE) {
			myDeploymentVSphereRHEL6 = new Deployment(myCatalogEntryVSphereRHEL6, vsphereRealm);
			myDeploymentVSphereRHEL5 = new Deployment(myCatalogEntryVSphereRHEL5, vsphereRealm);
			ll.add(Arrays.asList(new Object[] { "VSphere RHEL6 Deployment",	myDeploymentVSphereRHEL6 }));
			ll.add(Arrays.asList(new Object[] { "VSphere RHEL5 Deployment",	myDeploymentVSphereRHEL5 }));
		}
		if (testProviderRHEVM) {
			myDeploymentRHEVMRHEL6 = new Deployment(myCatalogEntryRHEVMRHEL6, rhevmRealm);
			myDeploymentRHEVMRHEL5 = new Deployment(myCatalogEntryRHEVMRHEL5, rhevmRealm);
			ll.add(Arrays.asList(new Object[] { "RHEVM RHEL6 Deployment",	myDeploymentRHEVMRHEL6 }));
			ll.add(Arrays.asList(new Object[] { "RHEVM RHEL5 Deployment",	myDeploymentRHEVMRHEL5 }));
		}
		
		
		return ll;
	}
	
	@DataProvider(name = "getHWPDataObjects")
	private Object[][] getHWPDataForPlatformsAs2dArray() {
		return TestNGUtils.convertListOfListsTo2dArray(getHWPDataObjects());
	}
	private List<List<Object>> getHWPDataObjects() {
		hwpMockLarge = new HardwareProfile(ProviderAccount_Mock.hwProfile_x86_64LARGE, false);
		hwpEC2 = new HardwareProfile(ProviderAccount_ec2.hwProfile_m1_large, false);
		hwpVSphere = new HardwareProfile(ProviderAccount_Vsphere.hwProfile_default, false);
		hwpRHEVM = new HardwareProfile(ProviderAccount_RHEVM.hwProfile_default, false);
		
		List<List<Object>> ll = new ArrayList<List<Object>>();

		// Element table, String columnName
		
		if (testProviderMock){
			ll.add(Arrays.asList(new Object[] { "mock hwp",	hwpMockLarge }));
		}
		if (testProviderEC2) {
			ll.add(Arrays.asList(new Object[] { "ec2 hwp",	hwpEC2 }));
		}
		if (testProviderVSPHERE) {
			ll.add(Arrays.asList(new Object[] { "VSphere hwp",	hwpVSphere }));
		}
		if (testProviderRHEVM) {
			ll.add(Arrays.asList(new Object[] { "RHEVM hwp",	hwpRHEVM }));
		}
		
		
		return ll;
	}
	
	
	
	@DataProvider(name="getEC2Amis")
	public Object[][] getEC2Amis() {
		return TestNGUtils.convertListOfListsTo2dArray(list());
	}
	protected List<List<Object>> list() {
		
		List<List<Object>> ll = new ArrayList<List<Object>>();
		
		ll.add(Arrays.asList(new Object[]{ "RHEL 6.1,ami-31d41658 ", "ami-31d41658", Templates.PLATFORM_RHEL, Templates.arch_x86_64 } ));
		ll.add(Arrays.asList(new Object[]{ "RHEL 6.1,ami-31d41658 ", "ami-31d41658", Templates.PLATFORM_RHEL, Templates.arch_i386 } ));
		ll.add(Arrays.asList(new Object[]{ "Fedora15 ami-c31cd8aa ", "ami-c31cd8aa", Templates.PLATFORM_FEDORA, Templates.arch_x86_64 } ));
		ll.add(Arrays.asList(new Object[]{ "Microsoft Windows Server 2008 Base ami-603ee209 ", "ami-603ee209", Templates.PLATFORM_WINDOWS, Templates.arch_x86_64 } ));
		//ll.add(Arrays.asList(new Object[]{ "Fedora16 ami-0316d86a ", "ami-0316d86a", Templates.PLATFORM_FEDORA, Templates.arch_x86_64 } ));
		return ll;	
	}
	
	
	
	

	/*@DataProvider(name = "getInstanceDataObjects")
	private Object[][] getInstanceDataAs2dArray() {
		return TestNGUtils
				.convertListOfListsTo2dArray(getInstanceDataObjects());
	}

	private List<List<Object>> getInstanceDataObjects() {
		List<List<Object>> ll = new ArrayList<List<Object>>();

		Instance id = new Instance("instance" + r.newRandom(), RHEL61_x86_64,
				hwpEC2, userAdmin);
		//
		ll.add(Arrays.asList(new Object[] { "test", id }));
		return ll;

	}
*/
	/**
	 * 
	 <test name="DeveloperTest"> <packages> <package
	 * name="com.redhat.qe.ce10.tests" /> <package
	 * name="com.redhat.qe.ce10.base" /> </packages> <groups> <run> <include
	 * name="setup" /> <include name="testplan-CloudEngine:DeveloperTest" />
	 * <exclude name="knownBug"/> </run> </groups> </test>
	 */

}
