package com.redhat.qe.ce10.tests;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.tools.SSHCommandRunner;

@Test(groups="testplan-CloudEngine:CleanupConfigure")
public class CleanupConfigure extends SeleniumTestScript {
	
	
	String key = System.getProperty("ce.auto.privatekey");
	String passphrase = System.getProperty("jon.server.sshkey.passphrase");
    SSHCommandRunner sshCommandRunner;
	
	@Test
	public void cleanupConfigure() throws IOException{
		
		sshCommandRunner = new SSHCommandRunner(ceServerHostname, "root", key, passphrase , "aeolus-cleanup");
		sshCommandRunner.runCommandAndWait("aeolus-cleanup");
		tasks.checkDeamons(tasks.deamonsStop(), sshCommandRunner);
    	sshCommandRunner.runCommandAndWait("aeolus-configure");
    	sshCommandRunner.runCommandAndWait("/etc/init.d/aeolus-conductor stop");
		sshCommandRunner.runCommandAndWait("thin start -c /usr/share/aeolus-conductor -l /var/log/aeolus-conductor/thin.log -P /var/run/aeolus-conductor/thin.pid -a 0.0.0.0 -p 3000 -e production --user aeolus --group aeolus  -d --prefix=/conductor -A rails");
		tasks.checkDeamons(tasks.deamonsStart(), sshCommandRunner);
	}

}
