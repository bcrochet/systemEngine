package com.redhat.qe.se.base;

import java.util.logging.Level

import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeSuite

import com.redhat.qe.auto.instantiate.VersionedInstantiator
import com.redhat.qe.auto.selenium.ExtendedSelenium
import com.redhat.qe.auto.tcms.TCMSTestNGListener
import com.redhat.qe.auto.testng.TestScript
import com.redhat.qe.se.tasks.AssortedTasks
import com.redhat.qe.se.tasks.NavigationTasks
import com.thoughtworks.selenium.SeleniumException



public class SeleniumTestScript extends TestScript{
	
	public static ExtendedSelenium selenium = null;
	protected static String seleniumServerHostname;
	protected static int seleniumServerPort;
	protected static String browserPath;
	public static String ceServerURL;	
	public static String ceVersion;
	public static String dcVersion;
	public static String ceServerHostname;
	public static String ceServerPath;
	public static String ceServerProtocol;
	public static int ceServerPort;
	public static String dcServerPort;
	public static String ceAdminUser;
	public static String ceAdminPassword;
	public static String sshPrivateKey;
	public static String sshAgentUsername;
	public static String sshKeyPassphrase;
	public static String yumDownloadDir;
	public static def userAdmin;
	public static String dcServerPath;
	public static Boolean testProviderEC2;
	public static Boolean testProviderVSPHERE;
	public static Boolean testProviderRHEVM;
	public static Boolean testProviderMock;
	public static Boolean testUpstream;
	
	//Cloud Engine
	protected static AssortedTasks tasks		= null;
	protected static NavigationTasks nav		= null;
//	protected static UIElements UI			= null;
	
	public static VersionedInstantiator instantiator;
	public static VersionedInstantiator dc_instantiator;
	
	public SeleniumTestScript() {
		
		if (tasks != null) return; //this class only needs to be initialized once, even though there will be multple instances.
		
		//set up versioned class loading for tasks
		LinkedHashMap<String, String> versions = new LinkedHashMap<String, String>();
		versions.put("1.0", "ce10");
		versions.put("1.1", "ce11");
		versions.put("1.2", "ce12");
		
		LinkedHashMap<String, String> dcversions = new LinkedHashMap<String, String>();
		dcversions.put("0.8", "dc08");
		
		
		
		//CLOUD ENGINE SPECIFIC PROPERTIES
		ceVersion = System.getProperty("ce.version");
		ceAdminUser = System.getProperty("ce.admin.user");
		ceAdminPassword = System.getProperty("ce.admin.password");
		sshPrivateKey = System.getProperty("ce.auto.privatekey");
		sshAgentUsername = System.getProperty("jon.agent.user");
		sshKeyPassphrase = System.getProperty("jon.agent.sshkey.passphrase","dog8code");
		yumDownloadDir = System.getProperty("ce.yum.download.dir");
		dcServerPort = System.getProperty("dc.port");
		log.info("Running against Cloud Engine version '" + ceVersion + "'" );
		
		testProviderEC2 = Boolean.parseBoolean(System.getProperty("ce.provider.ec2", "true"));
		testProviderVSPHERE = Boolean.parseBoolean(System.getProperty("ce.provider.vsphere", "false"));
		testProviderRHEVM = Boolean.parseBoolean(System.getProperty("ce.provider.rhevm", "false"));
		testProviderMock = Boolean.parseBoolean(System.getProperty("ce.provider.mock", "false"));
		testUpstream = Boolean.parseBoolean(System.getProperty("ce.upstream", "false"));
		
		ceServerURL = System.getProperty("ce.server.url");
		if (ceServerURL == null) throw new RuntimeException("Cloud Engine Server URL must be specified by a system property 'qe.server.url'.");
		else {
			try {
				ceServerHostname = new URL(ceServerURL).getHost();
				ceServerPath = new URL(ceServerURL).getPath();
				ceServerProtocol = new URL(ceServerURL).getProtocol();
				ceServerPort = new URL(ceServerURL).getPort();
				if (ceServerPort == -1){
					ceServerPort = 443;
					ceServerProtocol = "https";
				}
				if(ceServerPort == 443){
					ceServerProtocol = "https";
				}
				System.out.println("PATH="+ceServerPath);

			}
			catch(MalformedURLException mue){
				throw new RuntimeException("Unable to parse URL for Cloud Engine Server: " + ceServerURL, mue);
			}
		}
		
		String seleniumServerAddress = System.getProperty("selenium.address");
		try {
			String[] split = seleniumServerAddress.split(":");
			seleniumServerHostname = split[0];
			seleniumServerPort = Integer.parseInt(split[1]);			
		}
		catch(Exception e){
			log.log(Level.SEVERE, "Could not determine selenium server hostname and port.  Property should be in format of '[hostname]:[port]'.", e);
		}
		browserPath = System.getProperty("selenium.browser.path");
		
		//Cloud Engine Version Instantiator
		instantiator = new VersionedInstantiator(versions, 3, ceVersion);
		tasks = (AssortedTasks)instantiator.getVersionedInstance(AssortedTasks.class);
//		UI = (UIElements)instantiator.getVersionedInstance(UIElements.class);
		nav = (NavigationTasks)instantiator.getVersionedInstance(NavigationTasks.class);
		
		try {
//			UI = (UIElements)UI;
			tasks = (AssortedTasks)tasks;
			
		}catch (ClassCastException cce){
			log.log(Level.FINEST, "Couldn't initialize ce10 objects, probably because we're running against an older version.", cce);
		}
		
		// END Cloud Engine Version Instantiator
		
		//TCMSTestNGListener.setBuild(ceVersion);
		//TCMSTestNGListener.setVersion(ceVersion);
		
		// Needed to hard-code in order to update the procedures due to the fact that we are
		//    still technically at v1.0 but the new UI is referenced as v1.1
		TCMSTestNGListener.setBuild("1.0");
		TCMSTestNGListener.setVersion("1.0");
//		userAdmin = new Users(true,5, "duser@aeolusproject.org", "admin", "aeolus", "user", "password", "", null);
//		Users.userAddToListOfAdmins(userAdmin);
	}
	

	protected void startSelenium() throws Exception {
		log.fine("Connecting to selenium server at " + seleniumServerHostname + ":" + seleniumServerPort);
		//can now pass the browser type either *chrome,firefox,googlechrome  as a property would be the best idea
		if (selenium == null) selenium = new ExtendedSelenium(seleniumServerHostname, seleniumServerPort, "*chrome "+browserPath, ceServerURL);
		selenium.start();
	}
	
	@BeforeSuite(groups = ["setup"] )
	public void setupSession() throws Exception{
		startSelenium();
		tasks.login(ceAdminUser, ceAdminPassword, ceServerPath);
		tasks.loginCE(ceAdminUser, ceAdminPassword);
	}
	

	@AfterSuite(groups = ["setup"],  alwaysRun=true)
	public void tearDownSession() throws Exception {
		String skipTeardown = System.getProperty("ce.skipTearDown","0"); //0=off,1=skip shutdown
		if(skipTeardown.equals("1")){
			log.info("******** SHUTTING DOWN ************");
			return;
		}
		else{
		
			if(System.getProperty("ce.auto.shutdown.instances").equalsIgnoreCase("1")){
				log.info("******** SHUTTING DOWN ALL INSTANCES ************");
				try{
				tasks.shutdownAllInstances(false);
				log.info("******** FINISHED SHUTTING DOWN ALL INSTANCES ***********");
				}
				catch(SeleniumException se){
					log.info("******** FAILED SHUTTING DOWN ALL INSTANCES ***********");
				}	
			}
		
		log.info("******** SHUTTING DOWN  ************");
		selenium.stop();
		log.info("********  SHUTDOWN, FIREFOX SHOULD BE KILLED ************");
		
		}
		
		
	}
	
	
	 public static String getStackTrace(Throwable t)
	    {
	        StringWriter sw = new StringWriter();
	        PrintWriter pw = new PrintWriter(sw, true);
	        t.printStackTrace(pw);
	        pw.flush();
	        sw.flush();
	        return sw.toString();
	    }
	 
	 
	

}
