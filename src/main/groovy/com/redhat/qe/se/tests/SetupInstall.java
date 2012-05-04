package com.redhat.qe.ce10.tests;

import groovy.mock.interceptor.Demand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.redhat.qe.auto.testng.Assert;
import com.redhat.qe.auto.testng.TestNGUtils;
import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.tools.RemoteFileTasks;
import com.redhat.qe.tools.SCPTools;
import com.redhat.qe.tools.SSHCommandRunner;

@Test(groups="testplan-CloudEngine:SetupInstall")
public class SetupInstall extends SeleniumTestScript{
	
	String key = System.getProperty("ce.auto.privatekey");
	String passphrase = System.getProperty("jon.server.sshkey.passphrase");
	String os = null;
	SSHCommandRunner sshCommandRunner;
	SCPTools scpTools;
	
	@BeforeClass
	public void initialize() throws IOException{
		sshCommandRunner = new SSHCommandRunner(ceServerHostname, "root", key, passphrase , "cat /etc/redhat-release | awk '{print $1}' ");
		scpTools = new SCPTools(ceServerHostname, "root", key, passphrase);
    	sshCommandRunner.run();
    	os = sshCommandRunner.getStdout().trim();
        log.info("operating system ="+ os);
        System.setProperty("ce.skipTearDown","1");
	}
	
	@Test(description="setupRepo,install,configure",alwaysRun=true)
	public void setupRepo() throws IOException{
		
		sshCommandRunner.runCommandAndWait("yum -y install wget");
		//beaker installs some custom ntp package.. this fixes it
		
		//workaround??
		//sshCommandRunner.runCommandAndWait("yum -y groupinstall virtualization ");
		//workaround for now
		//sshCommandRunner.runCommandAndWait("setenforce 0");
		
		/**
		 * -Dce.repo.external.prod=$useExternalProdRepo \
-Dce.repo.external.testing=$useExternalTestingRepo \
-Dce.repo.internal.testing=$useExternalTestingRepo \
-D=$useBrewRepo \
		 */
		
		String repoExternalProd = System.getProperty("ce.repo.external.prod", "false");
		String repoExternalTesting = System.getProperty("ce.repo.external.testing", "false");
		String repoInternalTesting = System.getProperty("ce.repo.internal.testing","false");
		String repoBrewNightly = System.getProperty("ce.repo.brew.nightly", "false");
		String repoBrewPuddle = System.getProperty("ce.repo.brew.puddle", "false");
		String repoMock = System.getProperty("ce.repo.mock", "false");
		String repoStage = System.getProperty("ce.repo.stage", "false");
		String repoCustom = System.getProperty("ce.repo.custom","");
		
		
		if(os.equalsIgnoreCase("Fedora")){
			//beaker installs install their own messed up version of ntp
			sshCommandRunner.runCommandAndWait("rpm -e ntp --nodeps");
			sshCommandRunner.runCommandAndWait("yum -y install ntp");
			sshCommandRunner.runCommandAndWait("/etc/init.d/ntpd stop");
			sshCommandRunner.runCommandAndWait("ntpdate clock.redhat.com");
			sshCommandRunner.runCommandAndWait("/etc/init.d/ntpd start");
			
			if(repoMock.equalsIgnoreCase("true"))
				sshCommandRunner.runCommandAndWait("wget -O /etc/yum.repos.d/prestage-mock.repo http://aeolus-fedora.virt.bos.redhat.com/prestaging-mock/testing/fedora-aeolus-testing.repo");
			if(repoStage.equalsIgnoreCase("true"))
				sshCommandRunner.runCommandAndWait("wget -O /etc/yum.repos.d/staging.repo http://aeolus-fedora.virt.bos.redhat.com/staging/testing/fedora-aeolus-testing.repo");
			if(repoExternalProd.equalsIgnoreCase("true"))
				//sshCommandRunner.runCommandAndWait("wget -O /etc/yum.repos.d/fedora-aeolus.repo http://repos.fedorapeople.org/repos/aeolus/packages/fedora-aeolus.repo");
				sshCommandRunner.runCommandAndWait("wget -O /etc/yum.repos.d/fedora-aeolus.repo http://repos.fedorapeople.org/repos/aeolus/conductor/fedora-aeolus.repo");
			if(repoExternalTesting.equalsIgnoreCase("true"))
				sshCommandRunner.runCommandAndWait("wget -O /etc/yum.repos.d/fedora-aeolus-testing.repo http://repos.fedorapeople.org/repos/aeolus/conductor/testing/fedora-aeolus-testing.repo");
			if(repoInternalTesting.equalsIgnoreCase("true"))
				sshCommandRunner.runCommandAndWait("wget -O /etc/yum.repos.d/fedora-aeolus-internal.repo http://aeolus-fedora.virt.bos.redhat.com/repo/fedora-aeolus-internal.repo");
			if(! repoCustom.isEmpty()){
				log.info("Custom Repo ="+ repoCustom);
				sshCommandRunner.runCommandAndWait("wget -O /etc/yum.repos.d/aeolus-custom.repo " + repoCustom);
			}
			
		}
		else{
			//ipv6 selinux denial bz 766184
			sshCommandRunner.runCommandAndWait("echo 1 > /proc/sys/net/ipv6/conf/all/disable_ipv6");
			//beaker installs install their own messed up version of ntp
			sshCommandRunner.runCommandAndWait("rpm -e ntp --nodeps");
			sshCommandRunner.runCommandAndWait("yum -y install http://download.devel.redhat.com/released/RHEL-6-Server/6.1/x86_64/os/Packages/ntp-4.2.4p8-2.el6.x86_64.rpm");
			sshCommandRunner.runCommandAndWait("/etc/init.d/ntpd stop");
			sshCommandRunner.runCommandAndWait("ntpdate clock.redhat.com");
			sshCommandRunner.runCommandAndWait("/etc/init.d/ntpd start");
			
			sshCommandRunner.runCommandAndWait("mv /etc/yum.repos.d/beaker-Server.repo /etc/yum.repos.d/QE-beaker-Server.repo");
			sshCommandRunner.runCommandAndWait("rm -Rf /etc/yum.repos.d/beaker*");
			sshCommandRunner.runCommandAndWait("mv /etc/yum.repos.d/QE-beaker-Server.repo /etc/yum.repos.d/beaker-Server.repo");
			sshCommandRunner.runCommandAndWait("yum clean all");
			
			
			if(repoExternalProd.equalsIgnoreCase("true"))
				//sshCommandRunner.runCommandAndWait("wget -O /etc/yum.repos.d/rhel-aeolus.repo  http://repos.fedorapeople.org/repos/aeolus/packages/rhel-aeolus.repo");
				sshCommandRunner.runCommandAndWait("wget -O /etc/yum.repos.d/rhel-aeolus.repo  http://repos.fedorapeople.org/repos/aeolus/conductor/rhel-aeolus.repo");
			if(repoExternalTesting.equalsIgnoreCase("true"))
				sshCommandRunner.runCommandAndWait("wget -O /etc/yum.repos.d/rhel-aeolus-testing.repo http://repos.fedorapeople.org/repos/aeolus/conductor/testing/rhel-aeolus-testing.repo");
			if(repoInternalTesting.equalsIgnoreCase("true"))
				sshCommandRunner.runCommandAndWait("wget -O /etc/yum.repos.d/rhel-aeolus-internal.repo http://aeolus-fedora.virt.bos.redhat.com/repo/rhel-aeolus-internal.repo");
			if(repoBrewNightly.equalsIgnoreCase("true"))
				scpTools.sendFile(System.getProperty("ce.prj.home")+"/scripts/aeolus-brew-nightly.repo", "/etc/yum.repos.d/");
			if(repoBrewPuddle.equalsIgnoreCase("true"))
				scpTools.sendFile(System.getProperty("ce.prj.home")+"/scripts/aeolus-brew-puddle.repo", "/etc/yum.repos.d/");
			if(! repoCustom.isEmpty()){
				log.info("Custom Repo ="+ repoCustom);
				sshCommandRunner.runCommandAndWait("wget -O /etc/yum.repos.d/aeolus-custom.repo " + repoCustom);
			}
		}
			
		    sshCommandRunner.runCommandAndWait("ls /etc/yum.repos.d");
			log.info(sshCommandRunner.getStdout());
		
			sshCommandRunner.runCommandAndWait("yum -y install aeolus-all --nogpgcheck");
			sshCommandRunner.runCommandAndWait("yum -y install ruby-debuginfo");
			
			//SEND CONFIG
			scpTools.sendFile(System.getProperty("ce.prj.home")+"/scripts/vsphere_configure", "/etc/aeolus-configure/nodes/");
			scpTools.sendFile(System.getProperty("ce.prj.home")+"/scripts/rhevm_configure", "/etc/aeolus-configure/nodes/");
			
			//CONFIG
			sshCommandRunner.runCommandAndWait("aeolus-configure -d -v -p mock,ec2,vsphere,rhevm");

			
			//FIREWALL
			sshCommandRunner.runCommandAndWait("iptables -I INPUT  -p tcp --dport 80 -j ACCEPT");
			sshCommandRunner.runCommandAndWait("iptables -I INPUT  -p tcp --dport 443 -j ACCEPT");
			sshCommandRunner.runCommandAndWait("iptables -I INPUT  -p tcp --dport 3002 -j ACCEPT");
			sshCommandRunner.runCommandAndWait("/etc/init.d/iptables save");
			
			//CONFIGURE SERVICES
			RemoteFileTasks.searchReplaceFile(sshCommandRunner, "/etc/imagefactory/imagefactory.conf","max_concurrent_local_sessions\": 1","max_concurrent_local_sessions\": 2");
			RemoteFileTasks.searchReplaceFile(sshCommandRunner, "/etc/security/limits.conf","#*               soft    core            0","*               soft    core            unlimited");
			sshCommandRunner.runCommandAndWait("ulimit -c unlimited");
/*			sshCommandRunner.runCommandAndWait("mkdir -p /home/var/lib/imagefactory/images");
			sshCommandRunner.runCommandAndWait("mkdir -p /home/var/lib/libvirt/images");
			sshCommandRunner.runCommandAndWait("mkdir -p /home/var/lib/oz");
			sshCommandRunner.runCommandAndWait("cp -Rv /var/lib/oz/* /home/var/lib/oz/");
		
			RemoteFileTasks.searchReplaceFile(sshCommandRunner, "/etc/imagefactory/imagefactory.conf", "var\\/lib\\/imagefactory\\/images", "home\\/var\\/lib\\/imagefactory\\/images");
			RemoteFileTasks.searchReplaceFile(sshCommandRunner, "/etc/oz/oz.cfg","var\\/lib\\/libvirt\\/images","home\\/var\\/lib\\/libvirt\\/images");
			RemoteFileTasks.searchReplaceFile(sshCommandRunner, "/etc/oz/oz.cfg","var\\/lib\\/oz","home\\/var\\/lib\\/oz");
			
			sshCommandRunner.runCommandAndWait("semanage fcontext --add --type virt_image_t --seuser system_u \"/home/var/lib/imagefactory(/.*)?\"");
			sshCommandRunner.runCommandAndWait("semanage fcontext --add --type virt_image_t --seuser system_u \"/home/var/lib/libvirt/images(/.*)?\"");
			sshCommandRunner.runCommandAndWait("semanage fcontext --add --type virt_var_lib_t --seuser system_u \"/home/var/lib/oz(/.*)?\"");
			sshCommandRunner.runCommandAndWait("semanage fcontext --add --type virt_content_t --seuser system_u \"/home/var/lib/oz/isos(/.*)?\"");
			sshCommandRunner.runCommandAndWait("restorecon -rv /home/var/lib/");
			//semanage fcontext --add --type virt_image_t --seuser system_u "/home/var/lib/imagefactory(/.*)?"
			//semanage fcontext --add --type virt_image_t --seuser system_u "/home/var/lib/libvirt/images(/.*)?"
			//chcon -Rv --type=virt_image_t /home/var/lib/libvirt/images/
			//chcon -Rv --type=virt_image_t /home/var/lib/imagefactory/
			// chcon -Rv --user=system_u /home/var/lib/imagefactory/
			//chcon -Rv --role=object_r /home/var/lib/imagefactory/

			//Assert.assertTrue(sshCommandRunner.getStdout().contains("donwload.devel"));
			//scpTools.sendFile(System.getProperty("ce.prj.home")+"/scripts/checkServices.rb", "/root/");
			sshCommandRunner.runCommandAndWait("/usr/sbin/aeolus-restart-services");
			sshCommandRunner.runCommandAndWait("/usr/bin/aeolus-check-services");*/
			//END CONFIGURE SERVICES
		}
	
	@Test(description="test dc_prepare_repos",dependsOnMethods="setupRepo",alwaysRun=true)
	public void testDC_Prepare_Repo(){
		sshCommandRunner.runCommandAndWait("cat /var/log/aeolus-configure/aeolus-configure.log | grep dc_prepare_repos");
		Assert.assertTrue(! sshCommandRunner.getStdout().contains("executed successfully"),"Asserted dc_prepare_repos is not currently exuecuted");
	}
	
	@Test(description="test configure",dependsOnMethods="setupRepo",dataProvider="deamonsAndProcesses",alwaysRun=true)
	public void testConfigure(String daemon, String process, String foundProcess){
		boolean deamonOK = false;
		sshCommandRunner.runCommandAndWait("/etc/init.d/"+daemon+ " status");
		if(sshCommandRunner.getStdout().contains("running") || sshCommandRunner.getStdout().contains("active (exited)")){
			deamonOK = true;
		}
		Assert.assertTrue(deamonOK,"Asserted daemon has started ");
		sshCommandRunner.runCommandAndWait("ps -ef | grep "+ process);
		Assert.assertTrue(sshCommandRunner.getStdout().contains(foundProcess),"Asserted process was found");

		
	}
	
	@Test(description="testDependencies ",dependsOnMethods="testConfigure",dataProvider="dependentDeamonsAndProcesses",alwaysRun=true)
	public void testConfigureDependentDaemons(String daemon, String process, String foundProcess){
		sshCommandRunner.runCommandAndWait("/etc/init.d/"+daemon+ " status");
		Assert.assertTrue(sshCommandRunner.getStdout().contains("running"),"Asserted daemon has started ");
		sshCommandRunner.runCommandAndWait("ps -ef | grep "+ process);
		Assert.assertTrue(sshCommandRunner.getStdout().contains(foundProcess),"Asserted process was found");
	}
	
	@Test(description="testProviders ",dependsOnMethods="testConfigureDependentDaemons",alwaysRun=true)
	public void testProviders(){
		sshCommandRunner.runCommandAndWait("psql -t -U aeolus -d conductor -c 'select name from providers'");
		log.info(sshCommandRunner.getStdout());
		sshCommandRunner.runCommandAndWait("psql -t -U aeolus -d conductor -c 'select name from providers' | grep us-east");
		Assert.assertTrue(sshCommandRunner.getStdout().contains("ec2-us-east-1"),"Asserted ec2-us-east provider has been created");
		sshCommandRunner.runCommandAndWait("psql -t -U aeolus -d conductor -c 'select name from providers' | grep us-west");
		Assert.assertTrue(sshCommandRunner.getStdout().contains("ec2-us-west-1"),"Asserted ec2-us-west provider has been created");
		sshCommandRunner.runCommandAndWait("psql -t -U aeolus -d conductor -c 'select name from providers' | grep mock");
		Assert.assertTrue(sshCommandRunner.getStdout().contains("mock"),"Asserted mock provider has been created");
		sshCommandRunner.runCommandAndWait("psql -t -U aeolus -d conductor -c 'select name from providers' | grep vsphere");
		Assert.assertTrue(sshCommandRunner.getStdout().contains("vsphere"),"Asserted vsphere provider has been created");
		sshCommandRunner.runCommandAndWait("psql -t -U aeolus -d conductor -c 'select name from providers' | grep rhevm");
		Assert.assertTrue(sshCommandRunner.getStdout().contains("rhevm"),"Asserted rhevm provider has been created");
	}
	
	@Test(description="testSElinux",dependsOnMethods="testProviders",alwaysRun=true)
	public void testSELinux(){
		sshCommandRunner.runCommandAndWait("/usr/sbin/getenforce");
		//Assert.assertTrue(sshCommandRunner.getStdout().contains("Enforcing"),"Asserted SELinux is enforcing");
		if(sshCommandRunner.getStdout().equalsIgnoreCase("Permissive")){
			log.log(Level.SEVERE,"SELinux is set to permissive");
		}
	}
	
	@Test(description="cleanup",dependsOnMethods="testSELinux",alwaysRun=true)
	public void cleanUp(){
		sshCommandRunner.runCommandAndWait("aeolus-cleanup -d -v");
		
	}
	
	@Test(description="test cleanup",dependsOnMethods="cleanUp",dataProvider="deamonsAndProcessesSTOP",alwaysRun=true)
	public void testCleanUp(String daemon, String process, String foundProcess){
		boolean deamonOK = true;
		sshCommandRunner.runCommandAndWait("/etc/init.d/"+daemon+ " status");
		if(sshCommandRunner.getStdout().contains("is stopped") || sshCommandRunner.getStdout().contains("inactive (dead)") 
				|| sshCommandRunner.getStdout().contains("failed")){
			deamonOK = false;
		}
		Assert.assertFalse(deamonOK,"Asserted daemon has stopped ");
		sshCommandRunner.runCommandAndWait("ps -ef | grep "+ process);
		Assert.assertTrue(!sshCommandRunner.getStdout().contains(foundProcess),"Asserted process not found");
	}
	
	@Test(description="testDependenciesAfterCleanup ",dependsOnMethods="testCleanUp",dataProvider="dependentDeamonsAndProcesses")
	public void testConfigureDependentDaemonsStillRunning(String daemon, String process, String foundProcess){
		boolean deamonOK = false;
		sshCommandRunner.runCommandAndWait("/etc/init.d/"+daemon+ " status");
		if(sshCommandRunner.getStdout().contains("running") || sshCommandRunner.getStdout().contains("active (exited)")){
			deamonOK = true;
		}
		Assert.assertTrue(deamonOK,"Asserted daemon has started ");
		sshCommandRunner.runCommandAndWait("ps -ef | grep "+ process);
		Assert.assertTrue(sshCommandRunner.getStdout().contains(foundProcess),"Asserted process was found");
	}
	
	@Test(description="final configure",dependsOnMethods="testCleanUp",alwaysRun=true)
	public void finalConfigure(){
		//CONFIG
		sshCommandRunner.runCommandAndWait("aeolus-configure -d -v -p mock,ec2,vsphere,rhevm");
		
	}
	
	@Test(description="final configure_test",dependsOnMethods="finalConfigure",dataProvider="deamonsAndProcesses",alwaysRun=true)
	public void finalConfigureTest(String daemon, String process, String foundProcess){
		boolean deamonOK = false;
		sshCommandRunner.runCommandAndWait("/etc/init.d/"+daemon+ " status");
		if(sshCommandRunner.getStdout().contains("running") || sshCommandRunner.getStdout().contains("active (exited)")){
			deamonOK = true;
		}
		Assert.assertTrue(deamonOK,"Asserted daemon has started ");
		sshCommandRunner.runCommandAndWait("ps -ef | grep "+ process);
		Assert.assertTrue(sshCommandRunner.getStdout().contains(foundProcess),"Asserted process was found");
		sshCommandRunner.runCommandAndWait("/usr/bin/aeolus-check-services");
	}
	
	/*@Test(description="proxy workAround",dependsOnMethods="finalConfigureTest",alwaysRun=true)
	public void proxyWorkAround(){
		sshCommandRunner.runCommandAndWait("/etc/init.d/aeolus-conductor stop");
		sshCommandRunner.runCommandAndWait("thin start -c /usr/share/aeolus-conductor -l /var/log/aeolus-conductor/thin.log -P /var/run/aeolus-conductor/thin.pid -a 0.0.0.0 -p 3000 -e production --user aeolus --group aeolus  -d --prefix=/conductor -A rails");
		
	}*/
	
	/*@Test
	public void testDeamons(){
		
		tasks.checkDeamons(tasks.deamons(), sshCommandRunner);
	}
	*/
	
	
	
	
	@DataProvider(name="deamonsAndProcesses")
    public Object[][] getDeamonsAs2dArray() {
        return TestNGUtils.convertListOfListsTo2dArray(tasks.deamonsStart());
        //daemons is found in assortedTasks ce10
    }
	
	@DataProvider(name="deamonsAndProcessesSTOP")
    public Object[][] getDeamonsSTOPAs2dArray() {
        return TestNGUtils.convertListOfListsTo2dArray(tasks.deamonsStop());
      //daemons is found in assortedTasks ce10
    }

    
    @DataProvider(name="dependentDeamonsAndProcesses")
    public Object[][] getDependentDeamonsAs2dArray() {
        return TestNGUtils.convertListOfListsTo2dArray(dependentDeamons());
    }
    protected List<List<Object>> dependentDeamons() {

        List<List<Object>> ll = new ArrayList<List<Object>>();
        ll.add(Arrays.asList(new Object[]{ "libvirtd", "libvirtd","libvirtd --daemon"} ));
        ll.add(Arrays.asList(new Object[]{ "postgresql", "postmaster","/usr/bin/postmaster -p 5432 -D /var/lib/pgsql/data"} ));
      
        //ll.add(Arrays.asList(new Object[]{ "httpd", "httpd","/usr/sbin/httpd"} )); // aeolus actually starts and stops httpd
 
        return ll;
    }
    
	public void tearDownSession() throws Exception {
		log.info("skip normal teardown");
	}
	
	@AfterSuite(groups = {"dcsetup"},  alwaysRun=true)
	public void tearDownDCSession() throws Exception {
		log.info("skip normal teardown");
	}
	
}
