package com.redhat.qe.ce10.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.redhat.qe.api.base.RhevmApiClient;
import com.redhat.qe.auto.testng.TestNGUtils;
import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.ce10.data.Catalog;
import com.redhat.qe.ce10.data.CatalogEntry;
import com.redhat.qe.ce10.data.ConfigData;
import com.redhat.qe.ce10.data.ConfigServer;
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
import com.redhat.qe.ce10.data.Templates;
import com.redhat.qe.ce10.data.Users;
import com.redhat.qe.tools.SCPTools;
import com.redhat.qe.tools.SSHCommandRunner;

@Test(groups = "testplan-CloudEngine:ConductorConfigServer")
public class ManageConfigServer extends SeleniumTestScript{
	
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
	Realm mockRealm;
	Realm eastRealm;
	Realm west;
	Realm vsphereRealm;
	Realm rhevmRealm;
	HardwareProfile hwpMockLarge;
	HardwareProfile hwpEC2;
	HardwareProfile hwpVSphere;
	HardwareProfile hwpRHEVM;
	List<Deployment> listOfDeployments = new ArrayList<Deployment>();
	
	
	
	String key = System.getProperty("ce.auto.privatekey");
	String passphrase = System.getProperty("jon.server.sshkey.passphrase", "dog8code");
	SSHCommandRunner sshCommandRunner;
	String executeSSHCommands = System.getProperty("ce.useSSH", "1");
	SCPTools scpTools;
	String remoteFilePath = "/root/";
	String deploymentFile = "deployment02";
	String deployment01Path = System.getProperty("ce.prj.home") + "/yum/"
			+ deploymentFile;
	String deploymentDir = "/var/www/html/ce/";
	Catalog myCatalog;
	
	
	@BeforeClass
	public void initialize() throws IOException {
		
			sshCommandRunner = new SSHCommandRunner(ceServerHostname, "root",
					key, passphrase,
					"cat /etc/redhat-release | awk '{print $1}' ");
			scpTools = new SCPTools(ceServerHostname, "root", key, passphrase);
			sshCommandRunner.run();
			//tasks.checkDeamons(tasks.deamonsStart(), sshCommandRunner);
			
			
			myCatalog = new Catalog("AudreyCatalog", true);
		
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
			mockRealm = new Realm("realm-mock",mockProviderAccount);
			hwpMockLarge = new HardwareProfile(ProviderAccount_Mock.hwProfile_x86_64LARGE, true);
			ConfigServer configServerMock = new ConfigServer(mockProviderAccount,System.getProperty("audrey.server.hostname"), System.getProperty("audrey.oauth.key"), System.getProperty("audrey.oauth.secret"));
			configServerMock.verify();
		}
		
		if (testProviderEC2) {
			usEast1 = new Provider(Provider.EC2_US_E, Provider.EC2_US_E_URL, Provider.EC2_ProviderType);
			usWest1 = new Provider(Provider.EC2_US_W, Provider.EC2_US_W_URL, Provider.EC2_ProviderType);

			tasks.deleteAllProviderAccounts(usEast1);
			tasks.deleteAllProviderAccounts(usWest1);
			ec2EastProviderAccount = new ProviderAccount_ec2(usEast1.getName(),	usEast1);
			ec2WestProviderAccount = new ProviderAccount_ec2(usWest1.getName(),	usWest1);
			
			eastRealm = new Realm("realm-ec2-us-east", ec2EastProviderAccount);
			west = new Realm("realm-ec2-us-west", ec2WestProviderAccount);
			hwpEC2 = new HardwareProfile(ProviderAccount_ec2.hwProfile_m1_large, true);
			ConfigServer configServerEc2East = new ConfigServer(ec2EastProviderAccount,System.getProperty("audrey.server.ec2.hostname"), System.getProperty("audrey.ec2.oauth.key"), System.getProperty("audrey.ec2.oauth.secret"));
			configServerEc2East.verify();
			
			ConfigServer configServerEc2West = new ConfigServer(ec2WestProviderAccount,System.getProperty("audrey.server.ec2.hostname"), System.getProperty("audrey.ec2.oauth.key"), System.getProperty("audrey.ec2.oauth.secret"));
			configServerEc2West.verify();
			
		}
		if (testProviderVSPHERE) {
			vsphere = new Provider(Provider.VSPHERE, Provider.VSPHERE_URL, Provider.VSPHRE_ProviderType);
			tasks.deleteAllProviderAccounts(vsphere);
			vsphereProviderAccount = new ProviderAccount_Vsphere(vsphere.getName(), vsphere);
			vsphereRealm = new Realm("realm-vsphere", vsphereProviderAccount);
			hwpVSphere = new HardwareProfile(ProviderAccount_Vsphere.hwProfile_default, true);
			ConfigServer configServerVsphere = new ConfigServer(vsphereProviderAccount,System.getProperty("audrey.server.hostname"), System.getProperty("audrey.oauth.key"), System.getProperty("audrey.oauth.secret"));
			configServerVsphere.verify();
		}
		
		if (testProviderRHEVM) {
			rhevm = new Provider(Provider.RHEVM, Provider.RHEVM_URL, Provider.RHEVM_ProviderType);
			tasks.deleteAllProviderAccounts(rhevm);
			rhevmProviderAccount = new ProviderAccount_RHEVM(rhevm.getName(), rhevm);
			rhevmRealm = new Realm("realm-rhevm", rhevmProviderAccount);
			hwpRHEVM = new HardwareProfile(ProviderAccount_RHEVM.hwProfile_default, true);
			ConfigServer configServerRhevm = new ConfigServer(rhevmProviderAccount,System.getProperty("audrey.server.hostname"), System.getProperty("audrey.oauth.key"), System.getProperty("audrey.oauth.secret"));
			configServerRhevm.verify();
		}
	}
	
	

	@Test(description = "need to setup a valid provider and provider account",dataProvider="getDevTemplateDataObjects",dependsOnMethods="setupProvider")
	public void setupConfigServer(String testName, Templates thisTemplate, ProviderAccount thisProviderAccount, String instanceName, HardwareProfile hwp, String audreyServiceName, Realm thisRealm) throws IOException {
		Users.loginAsAdmin();
		log.info(thisProviderAccount.getAccountName());
		Assert.assertTrue(thisTemplate.build(sshCommandRunner,scpTools, thisProviderAccount),"Assertered image template was built successfully");;
		Assert.assertTrue(thisTemplate.push(sshCommandRunner,scpTools, thisProviderAccount),"Asserted image was pushed successfully to the provider");;
		Instance testInstance = new Instance(instanceName,thisTemplate, hwp,	SeleniumTestScript.userAdmin);
		
/*
		HashMap<String,String> initialConfigMap = new HashMap<String,String>();
		HashMap<String,String> runTimeConfigMap = new HashMap<String,String>();
		initialConfigMap.put("auto_service_param1", "value 2");
		runTimeConfigMap.put("auto_service_param1", "value 40");
*/
		//for (HashMap<String, Object> data : generateServices()) {
			HashMap<String, String> runTimeConfigMap = new HashMap<String, String>();
			runTimeConfigMap.put("auto_service_param1", "value 40");
			ConfigData config = new ConfigData(generateServices(), runTimeConfigMap, "", "/etc/cloud-qe.cfg");
			// this only works for one instance!
			testInstance.setConfigData(config);
		//}
/*
		if (testProviderEC2) {
			config = new ConfigData("myServerName",initialConfigMap, runTimeConfigMap, "http://ec2-50-18-103-246.us-west-1.compute.amazonaws.com/configserver/cloud-qe-script01.sh", "/etc/cloud-qe.cfg");
		}
		else{
			config = new ConfigData("myServerName",initialConfigMap, runTimeConfigMap, "http://whayutin.rdu.redhat.com/configserver/cloud-qe-script01.sh", "/etc/cloud-qe.cfg");
		}
		*/
		
		
		CatalogEntry testCatalogEntry = new CatalogEntry(myCatalog, true, sshCommandRunner, scpTools, testInstance);
		Deployment testDeployment = new Deployment(testCatalogEntry, thisRealm);
		Assert.assertTrue(testDeployment.start(),"Asserted the deployment started");
		Assert.assertTrue(testDeployment.sshInAndVerify(true, false, false),"Asserted ssh to running instance ");
		Assert.assertTrue(testDeployment.sshInAndVerify(false, true, false), "Assert get list of packages");
		Assert.assertTrue(testDeployment.sshInAndVerify(false, false, true), "Asserted config server laid down file and echo'd config");
		listOfDeployments.add(testDeployment);
			
	}
	
	@AfterClass
	public void finalDeamonCheck() throws Exception{
		// tasks.checkDeamons(tasks.deamonsStart(), sshCommandRunner);
		//	Instance.stopAll(true);
			
			
			try{
				
				for(Deployment d:listOfDeployments){
					for(Instance i:d.getInstanceList()){
						Templates thisTemplate = i.getTemplate();
						Set<ProviderAccount> setOfProviderAccounts = thisTemplate.whereWasIPushed();
						for(ProviderAccount pa:setOfProviderAccounts){
							if(pa.getClass() == ProviderAccount_RHEVM.class){
								//ideally I should get the provider object and w/ the provider
								//account object dynamically fill out the RhevmApiClient
								// This will be needed because we can be set up for more than one
								//rhevm provider, and provider account
								RhevmApiClient rhevm = new RhevmApiClient("qeblade26.rhq.lab.eng.bos.redhat.com", "8443", "admin@internal", System.getProperty("ce.rhevm.password"));
								String providerIDHash = thisTemplate.getProviderImageID();
								String myTemplateIDByName = rhevm.getTemplateIdByName(providerIDHash);
								rhevm.deleteTemplate(myTemplateIDByName, "true");
								log.info("break");
							}
						}
						
					
					//String id = rhevm.getVmIdByName(i.getFullRHEVName());
					//rhevm.deleteVm(id);
					}
				}
			}
			catch(NullPointerException npe){
				log.log(Level.FINEST, "RHEVM instances is null, you may not be testing rhevm");
				log.log(Level.FINEST, npe.getMessage());
			}
			
	}
	

	@DataProvider(name = "getDevTemplateDataObjects")
	private Object[][] getDevTemplateDataForPlatformsAs2dArray() throws IOException {
		return TestNGUtils.convertListOfListsTo2dArray(getDevTemplateDataObjects());
	}
	private List<List<Object>> getDevTemplateDataObjects() throws IOException {
		File audreyFedoraEC2 = new File(System.getProperty("ce.prj.home")	+ "/templates/Audrey/audrey-client-fedora-template.tpl");
		File audreyRHEL = new File(System.getProperty("ce.prj.home")	+ "/templates/Audrey/audrey-client-RHEL6-template.tpl");
		File audreyRHEL_EC2 = new File(System.getProperty("ce.prj.home")	+ "/templates/Audrey/audrey-client-RHEL6-EC2-template.tpl");
		
		File audreyFedora_Vsphere = new File(System.getProperty("ce.prj.home")	+ "/templates/Audrey/audrey-client-fedora-vsphere.tpl");
		//File audreyRHEL_Vsphere = new File(System.getProperty("ce.prj.home")	+ "/templates/Audrey/audrey-client-RHEL6-vsphere.tpl");
		File audreyRHEL_Vsphere = new File(System.getProperty("ce.prj.home")	+ "/templates/Audrey/rhel-x86_64-server-6-development-tools.xml");

		
		File audreyFedora_RHEVM = new File(System.getProperty("ce.prj.home")	+ "/templates/Audrey/audrey-client-fedora-rhevm.tpl");
		//File audreyRHEL_RHEVM = new File(System.getProperty("ce.prj.home")	+ "/templates/Audrey/audrey-client-RHEL6-rhevm.tpl");
		File audreyRHEL_RHEVM = new File(System.getProperty("ce.prj.home")	+ "/templates/Audrey/rhel-x86_64-server-6-development-tools.xml");

		Templates templateMockFedora = new Templates(audreyFedoraEC2, remoteFilePath);
		Templates templateMockRHEL = new Templates(audreyRHEL,remoteFilePath);
		Templates templateEC2Fedora = new Templates(audreyFedoraEC2,remoteFilePath);
		Templates templateEC2RHEL = new Templates(audreyRHEL_EC2,remoteFilePath);
		Templates templateVsphereFedora = new Templates(audreyFedora_Vsphere,remoteFilePath);
		Templates templateVsphereRHEL = new Templates(audreyRHEL_Vsphere,remoteFilePath);
		Templates templateRHEVMFedora = new Templates(audreyFedora_RHEVM,remoteFilePath);
		Templates templateRHEVMRHEL = new Templates(audreyRHEL_RHEVM,remoteFilePath);
		
		
		List<List<Object>> ll = new ArrayList<List<Object>>();

		// Element table, String columnName
		
		if (testProviderMock){
			ll.add(Arrays.asList(new Object[] { "basic mock audrey fedora template",	templateMockFedora, mockProviderAccount, "fedoraMockInstance", hwpMockLarge, "Mock Fedora", mockRealm }));
			ll.add(Arrays.asList(new Object[] { "basic mock audrey RHEL template",	templateMockRHEL, mockProviderAccount, "RHELMockInstance", hwpMockLarge, "Mock RHEL", mockRealm }));
		}
		
		if (testProviderEC2) {
			//ll.add(Arrays.asList(new Object[] { "basic ec2 audrey fedora template",	templateEC2Fedora, ec2EastProviderAccount, "fedoraEC2Instance", hwpEC2, "EC2 Fedora", eastRealm }));
			ll.add(Arrays.asList(new Object[] { "basic ec2 audrey RHEL template",	templateEC2RHEL, ec2EastProviderAccount, "RHELEC2Instance", hwpEC2, "EC2 RHEL", eastRealm }));
		}
		
		if (testProviderVSPHERE) {
			//ll.add(Arrays.asList(new Object[] { "basic vsphere audrey fedora template",	templateVsphereFedora, vsphereProviderAccount, "fedoraVsphereInstance", hwpVSphere, "Vsphere Fedora", vsphereRealm }));
			ll.add(Arrays.asList(new Object[] { "basic vsphere audrey RHEL template",	templateVsphereRHEL, vsphereProviderAccount, "RHELVsphereInstance", hwpVSphere, "Vsphere RHEL", vsphereRealm }));
		}
		
		if (testProviderRHEVM) {
			//ll.add(Arrays.asList(new Object[] { "basic rhevm audrey fedora template",	templateRHEVMFedora, rhevmProviderAccount, "fedoraRHEVMInstance", hwpRHEVM, "RHEVM Fedora", rhevmRealm }));
			ll.add(Arrays.asList(new Object[] { "basic rhevm audrey RHEL template",	templateRHEVMRHEL, rhevmProviderAccount, "RHELRHEVMInstance", hwpRHEVM, "RHEVM RHEL", rhevmRealm }));
		}
		return ll;
	}
	private ArrayList<HashMap<String, Object>> generateServices() {
		// Wrap inside ArrayList so we got multiple deployables?
		// OR DataProvider()
		
		ArrayList<HashMap<String, Object>> services = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> service = new HashMap<String, Object>();
		
		service.put("serviceName", "proxy");
		service.put("executableURL", "http://www.aeolusproject.org/redmine/attachments/download/169/start_simple.sh");

		ArrayList<HashMap<String, String>> files = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> file = new HashMap<String, String>();
		file.put("url", "http://example.com/path/to/proxy_conf_file_1");
		files.add(file);

		file.put("url", "http://example.com/path/to/proxy_conf_file_2");
		files.add(file);

		service.put("files", files);

		ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("name", "foo");
		param.put("value", "bar");
		param.put("type", "scalar");
		params.add(param);

		service.put("files", files);
		service.put("parameters", params);
		
		services.add(service);
		return services;
	}
}
