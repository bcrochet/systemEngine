package com.redhat.qe.ce10.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.redhat.qe.auto.testng.TestNGUtils;
import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.ce10.data.Catalog;
import com.redhat.qe.ce10.data.CatalogEntry;
import com.redhat.qe.ce10.data.Deployment;
import com.redhat.qe.ce10.data.HardwareProfile;
import com.redhat.qe.ce10.data.Instance;
import com.redhat.qe.ce10.data.Provider;
import com.redhat.qe.ce10.data.ProviderAccount_ec2;
import com.redhat.qe.ce10.data.Realm;
import com.redhat.qe.ce10.data.Templates;
import com.redhat.qe.tools.SCPTools;
import com.redhat.qe.tools.SSHCommandRunner;

@Test(groups="testplan-CloudEngine:ImageImport")
public class ImageImport extends SeleniumTestScript{
	
String key = System.getProperty("ce.auto.privatekey");
String passphrase = System.getProperty("jon.server.sshkey.passphrase");
SSHCommandRunner sshCommandRunner;
String executeSSHCommands = System.getProperty("ce.useSSH", "1");
SCPTools scpTools;

Provider usEast1;
ProviderAccount_ec2 ec2EastProviderAccount;
HardwareProfile hwpEC2;
Realm eastRealm;
Catalog thisCatalog;

@BeforeClass
public void initialize() throws IOException {
	if (executeSSHCommands == "1") {
		sshCommandRunner = new SSHCommandRunner(ceServerHostname, "root",
				key, passphrase,
				"cat /etc/redhat-release | awk '{print $1}' ");
		scpTools = new SCPTools(ceServerHostname, "root", key, passphrase);
		sshCommandRunner.run();
		tasks.checkDeamons(tasks.deamonsStart(), sshCommandRunner);
		usEast1 = new Provider(Provider.EC2_US_E, Provider.EC2_US_E_URL, Provider.EC2_ProviderType);
		tasks.deleteAllProviderAccounts(usEast1);
		ec2EastProviderAccount = new ProviderAccount_ec2(usEast1.getName(),	usEast1);
		HardwareProfile.deleteAll();
		hwpEC2 = new HardwareProfile(ProviderAccount_ec2.hwProfile_m1_large, true);
		Realm.deleteAll();
		eastRealm = new Realm("realm-ec2-us-east", ec2EastProviderAccount);
		thisCatalog = new Catalog("importCatalog", true);
		
	}
}
	
@Test(description="import a ami",dataProvider="getEC2Amis")
public void import_AMI(String testname, String ami, String platform, String arch){

	Templates importTemplate = new Templates(ami, ami+platform+arch, platform, arch);
	importTemplate.importTemplate(sshCommandRunner, ec2EastProviderAccount);
	
	log.info(importTemplate.getBuildID());
	log.info(importTemplate.getImageID());
	
	Instance myServer = new Instance(ami, importTemplate, hwpEC2, userAdmin);
	CatalogEntry thisCatalogEntry = new CatalogEntry(thisCatalog, true, sshCommandRunner, scpTools, myServer);
	Deployment thisDeployment = new Deployment(thisCatalogEntry, eastRealm);
	thisDeployment.start();
	
	}


@DataProvider(name="getEC2Amis")
public Object[][] getEC2Amis() {
	return TestNGUtils.convertListOfListsTo2dArray(list());
}
protected List<List<Object>> list() {
	
	List<List<Object>> ll = new ArrayList<List<Object>>();
	
	ll.add(Arrays.asList(new Object[]{ "RHEL 6.1,ami-31d41658 ", "ami-31d41658", Templates.PLATFORM_RHEL, Templates.arch_x86_64 } ));
	ll.add(Arrays.asList(new Object[]{ "Fedora15 ami-c31cd8aa ", "ami-c31cd8aa", Templates.PLATFORM_FEDORA, Templates.arch_x86_64 } ));
	//ll.add(Arrays.asList(new Object[]{ "Fedora16 ami-2f69a446 ", "ami-2f69a446", Templates.PLATFORM_FEDORA, Templates.arch_x86_64 } ));
	return ll;	
}




}
