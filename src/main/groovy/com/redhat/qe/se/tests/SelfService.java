package com.redhat.qe.ce10.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.redhat.qe.auto.testng.Assert;
import com.redhat.qe.auto.testng.TestNGUtils;
import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.ce10.data.HardwareProfile;
import com.redhat.qe.ce10.data.Instance;
import com.redhat.qe.ce10.data.RandomData;
import com.redhat.qe.ce10.data.Roles;
import com.redhat.qe.ce10.data.Templates;
import com.redhat.qe.ce10.data.DataList;
import com.redhat.qe.ce10.data.Users;
import com.redhat.qe.tools.SSHCommandRunner;
import com.thoughtworks.selenium.SeleniumException;
@Test(groups="testplan-CloudEngine:SelfService")
public class SelfService extends SeleniumTestScript {
    /*
    private DataList temp_list_class = new DataList();
    private HashSet<Templates> list;
    private Roles role = new Roles(Roles.Administrator);
    private Users nonAdminUser01 = new Users("selfService",5, role);
    private HashSet<Templates> listTemplates;
    private HashSet<HardwareProfile> listHWP;
    
    private RandomData random = new RandomData(5);
    private Instance instanceTest;
    private int numOfInstancesAllowed = 1;
    private int counter = 0;
    private String installedPackages;
    
    @BeforeClass(alwaysRun=true)
    public void getLaunchableTemplates()  throws ClassNotFoundException{
    	tasks.loginAsAdmin();
        try {
			listTemplates = temp_list_class.getListOfBuiltTemplates();
			listHWP = temp_list_class.getListOfCreatedHWP();
		} catch (FileNotFoundException e) {
			log.log(Level.SEVERE,SeleniumTestScript.getStackTrace(e));
			throw new SeleniumException("Templates and Hardware Profiles must be built and serialized before running this class");
		} catch (IOException e) {
			log.log(Level.SEVERE,SeleniumTestScript.getStackTrace(e));
			throw new SeleniumException("Templates and Hardware Profiles must be built and serialized before running this class");
		}
          
          
		tasks.shutdownAllInstances(false);
    }
    
    @Test(description="login As NonAdmin User And Launch each template in list", groups="self_service",dataProvider="getTemplateDataObjects")
    public void loginAsNonAdminUserAndLaunch(TemplateData template){
        counter = 0;
        tasks.setSeflServiceQuota(String.valueOf(list.size()));
        tasks.createSelfServiceUser(nonAdminUser01);
        instanceTest = new InstanceData(template , random.newRandom());
        tasks.launchInstance(instanceTest.getTemplateName(),instanceTest.getInstanceName(), instanceTest.getTemplateArch());
        tasks.verifyInstanceInRow(instanceTest.getInstanceName());
        tasks.waitForInstance(instanceTest);
        tasks.instanceDetails(instanceTest, true);
        tasks.instanceShutdown(instanceTest.getInstanceName()); 
    }
    
    @Test(description="Quota Test")
    public void selfServiceQuotaTest(){
        Users nonAdminUser02 = new Users("selfServiceQuota",6, role);
        boolean instanceCreated = true;
    	int quota = list.size() -1 ;
        counter = 0;
        tasks.setSeflServiceQuota(String.valueOf(quota));
        //tasks.createUser(true, nonAdminUser02, true, false, false);
        
        Iterator<Templates> interator = list.iterator();
        Templates thisTemplate = interator.next();
        
        for(int i=0;i<=list.size();i++) {
            instanceTest = new Instance(random.newRandom(),thisTemplate , null, userAdmin );
            instanceCreated =  tasks.launchInstance(instanceTest.getTemplateName(),instanceTest.getName(), instanceTest.getTemplateArch(), null);
            if(counter >= (quota)) {
                Assert.assertTrue(!instanceCreated,"Asserted warning message about quota exceeded is visible");
                
            }
            else {
                tasks.verifyInstanceInRow(instanceTest.getName());
                tasks.waitForInstance(instanceTest.getName(), Instance.STATE_RUNNING, 30);
                tasks.instanceDetails(instanceTest, null, true);
               
            }
            counter++;
        }
        
    }
    
    @Test(description="login As NonAdmin User And Launch and ssh into instance",dataProvider="getTemplateDataObjects")
    public void loginAsNonAdminUserAndLaunchAndSSH(Templates template) throws IOException{
        Users nonAdminUser03 = new Users("selfServiceSSH",7, role);
        counter = 0;
        tasks.setSeflServiceQuota(String.valueOf(list.size()));
       // tasks.createUser(true, nonAdminUser03, true, false, false);
        instanceTest = new Instance(random.newRandom(),template , null, userAdmin);
        tasks.launchInstance(instanceTest.getTemplateName(),instanceTest.getName(), instanceTest.getTemplateArch(), null);
        tasks.verifyInstanceInRow(instanceTest.getName());
        tasks.waitForInstance(instanceTest.getName(), Instance.STATE_RUNNING, 30);
        tasks.instanceDetails(instanceTest, null, true);
        SSHCommandRunner sshCommandRunner = new SSHCommandRunner(instanceTest.get_PublicAddress(), "root", instanceTest.get_InstanceKeyLocation(), "", "cat /etc/motd");
        sshCommandRunner.run();
        String out = sshCommandRunner.getStdout();
        log.info("SYSTEM_OUT = "+ out);
        Assert.assertTrue(out.contains(instanceTest.getTemplateName()),"Asserted instances MOTD contains template name");
        Assert.assertTrue(out.contains("localhost.localdomain"),"Asserted hostname = localhost.localdomain");
        
        sshCommandRunner.reset();
        sshCommandRunner.runCommand("rpm -qa");
        installedPackages = sshCommandRunner.getStdout();
        log.info("Installed Packages:  rpm -qa");
        log.info(installedPackages);
       // instanceTest.setInstalledPackages(installedPackages);
 
    }
    
    
    @DataProvider(name="getTemplateDataObjects")
    public Object[][] getTemplateDataForPlatformsAs2dArray() {
        return TestNGUtils.convertListOfListsTo2dArray(getObject());
    }
    protected List<List<Object>> getObject() {
        
        List<List<Object>> ll = new ArrayList<List<Object>>();
        //                                  Element table,              String columnName
        for(Templates template:list) {
            ll.add(Arrays.asList(new Object[]{ template} ));
        }
        return ll;
    }
    
    */
}


