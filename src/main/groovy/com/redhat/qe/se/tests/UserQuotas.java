package com.redhat.qe.ce10.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.redhat.qe.auto.testng.TestNGUtils;
import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.ce10.data.HardwareProfile;
import com.redhat.qe.ce10.data.Instance;
import com.redhat.qe.ce10.data.ProviderAccount_ec2;
import com.redhat.qe.ce10.data.RandomData;
import com.redhat.qe.ce10.data.Templates;
import com.redhat.qe.ce10.data.DataList;
import com.redhat.qe.ce10.data.Users;
import com.thoughtworks.selenium.SeleniumException;



@Test(groups="testplan-CloudEngine:Quotas")
public class UserQuotas extends SeleniumTestScript{
/*	
	private ArrayList<Templates> listTemplates;
	private HashSet<HardwareProfile> listHWP;
    private DataList temp_list_class = new DataList();
    RandomData r = new RandomData(5);
	
	
	 @BeforeClass(alwaysRun=true)
	    public void getLaunchableTemplates()  throws ClassNotFoundException{
	    	//listOfInstances = new ArrayList<InstanceData>();
	    	tasks.loginAsAdmin();
	        try {
				listHWP = temp_list_class.getListOfCreatedHWP();
				listTemplates.addAll(temp_list_class.getListOfBuiltTemplates());
			} catch (FileNotFoundException e) {
				log.log(Level.SEVERE,SeleniumTestScript.getStackTrace(e));
				throw new SeleniumException("Templates and Hardware Profiles must be built and serialized before running this class");
			} catch (IOException e) {
				log.log(Level.SEVERE,SeleniumTestScript.getStackTrace(e));
				throw new SeleniumException("Templates and Hardware Profiles must be built and serialized before running this class");
			}
	        

			tasks.shutdownAllInstances(false);
	    }
	 
	 @Test(description="test basic user quota",dataProvider="getProviderUserQuotaTests",alwaysRun=true)
		public void basicUserQuota(String testName, Templates template, HardwareProfile hwp, Users user){
			beginInstancetest(template, hwp, user);
			// Add quota+1 instance and check for error
			Instance id = new Instance("EXCEEDED_QUOTA_INSTANCE"+r.newRandom(), template, hwp, userAdmin);
			Assert.assertTrue(!tasks.launchInstance(id.getTemplateName(),id.getName(), id.getTemplateArch(), id.getHardwareProfile()),"Asserted that Quota Warning is visible; "+UI.quotaExceeded);
			tasks.shutdownAllInstances(true);

		}
		

		@Test(description="test basic user increasing the quota, with pending instances",dataProvider="getProviderUserQuotaTests",alwaysRun=true)
		public void basicUserIncreaseQuota(String testName, Templates template, HardwareProfile hwp, Users user){
			int userQuota = Integer.getInteger(user.get_quota());
			beginInstancetest(template, hwp, user);
			// Add quota+1 instance and check for error
			Instance id = new Instance("PENDING QUOTA"+r.newRandom(), template, hwp, user );
			Assert.assertTrue(!tasks.launchInstance(id.getTemplateName(),id.getName(), id.getTemplateArch(), id.getHardwareProfile()),"Asserted that Quota Warning is visible; "+UI.quotaExceeded);
			
			
			tasks.updateUserQuota(user, String.valueOf(userQuota+1));
			Assert.assertTrue(tasks.waitForInstance(id.getName(), Instance.STATE_RUNNING, 10),"Asserted intance"+id.getName()+" is now in a running state after quota was increased");
			tasks.shutdownAllInstances(true);
		}
		
		@Test(description="test basic user increasing the quota, with pending instances",dataProvider="getProviderUserQuotaTests",alwaysRun=true)
		public void basicUserIncreaseQuota2(String testName, Templates template, HardwareProfile hwp, Users user){
			int userQuota = Integer.getInteger(user.get_quota());
			beginInstancetest(template, hwp, user);
			// Add quota+1 instance and check for error
			Instance id = new Instance("PENDING QUOTA"+r.newRandom(),template, hwp, user);
			Assert.assertTrue(!tasks.launchInstance(id.getTemplateName(),id.getName(), id.getTemplateArch(), id.getHardwareProfile()),"Asserted that Quota Warning is visible; "+UI.quotaExceeded);
			Instance id2 = new Instance("PENDING QUOTA_2"+r.newRandom(), template, hwp, user );
			Assert.assertTrue(!tasks.launchInstance(id2.getTemplateName(),id2.getName(), id2.getTemplateArch(), id2.getHardwareProfile()),"Asserted that Quota Warning is visible; "+UI.quotaExceeded);
			
			
			tasks.updateUserQuota(user, String.valueOf(userQuota+1));
			Assert.assertTrue(tasks.waitForInstance(id.getName(), Instance.STATE_RUNNING, 10),"Asserted intance"+id.getName()+" is now in a running state after quota was increased");
			Assert.assertTrue(!tasks.waitForInstance(id2.getName(), Instance.STATE_RUNNING, 10),"Asserted intance"+id2.getName()+" is NOT in a running state after quota was increased");
			tasks.shutdownAllInstances(true);
		}*/
		
		
	//Other tests..
	/*
	 * 1.verify percentages
	 * 2. other users
	 */

    
/*
	private Instance beginInstancetest(Templates template, HardwareProfile hwp, Users user){
		int userQuota = Integer.getInteger(user.get_quota());
		tasks.updateUserQuota(user, user.get_quota());
		Instance id = null;
		for(int i=0;i<=userQuota;i++){
			id  = new Instance("UserQuota"+r.newRandom(),template, hwp, user);
			 if(!user.isQuotaExceeded()){
				 tasks.launchInstance(id.getTemplateName(),id.getName(), id.getTemplateArch(), id.getHardwareProfile());
			     tasks.verifyInstanceInRow(id.getName());
			     tasks.waitForInstance(id.getName(), Instance.STATE_RUNNING, 10);
			     log.info(user.username()+ " has " + user.getInstanceCount() + " instances running");
			     }
		    	 
		     }
		return id;
	}*/




/*	@DataProvider(name="getProviderUserQuotaTests")
	public Object[][] createProviderQuotaTests() {
		List<List<Object>> ll = new ArrayList<List<Object>>();
		
		Users myAdmin = SeleniumTestScript.userAdmin;
		myAdmin.set_quota("2");
		
		Templates myTemplate = listTemplates.get(0);	
		Assert.assertTrue(tasks11.isInstanceTemplateVisible(myTemplate));
		HardwareProfile myHWP = new HardwareProfile(ProviderAccount_ec2.hwProfile_m1_large);
			  
	      		
		//InstanceData instance = new InstanceData(myTemplate, myHWP, myAdmin, "instance"+r.newRandom());
		
		ll.add(Arrays.asList(new Object[]{"basic x-large ec2 instance",  myTemplate,myHWP, myAdmin} ));
	      		
		return TestNGUtils.convertListOfListsTo2dArray(ll);	
	}
	
	*/
	
	
	/*	@Test(description="test basic user quota",dataProvider="getProviderUserQuotaTests",alwaysRun=true)
	public void basicUserQuota(String testName, TemplateData template, HardwareProfile hwp, UserData user){
		int userQuota = Integer.getInteger(user.get_quota());
		tasks.updateUserQuota(user, user.get_quota());
		
		for(int i=0;i<=userQuota;i++){
			InstanceData id = new InstanceData(template, hwp, user, "UserQuota"+r.newRandom());
			 if(!user.isQuotaExceeded()){
				 tasks.launchInstance(id.getTemplateName(),id.getInstanceName(), id.getTemplateArch(), id.getHardwareProfile());
			     tasks.verifyInstanceInRow(id.getInstanceName());
			     tasks.waitForInstance(id.getInstanceName(), InstanceData.STATE_RUNNING, 10);
			     }
		    	 
		     }
		// Add quota+1 instance and check for error
		InstanceData id = new InstanceData(template, hwp, user, "EXCEEDED_QUOTA_INSTANCE"+r.newRandom());
		Assert.assertTrue(!tasks.launchInstance(id.getTemplateName(),id.getInstanceName(), id.getTemplateArch(), id.getHardwareProfile()),"Asserted that Quota Warning is visible; "+UI.quotaExceeded);

	}
	

	@Test(description="test basic user increasing the quota, with pending instances",dataProvider="getProviderUserQuotaTests",alwaysRun=true)
	public void basicUserIncreaseQuota(String testName, TemplateData template, HardwareProfile hwp, UserData user){
		int userQuota = Integer.getInteger(user.get_quota());
		tasks.updateUserQuota(user, user.get_quota());
		
		for(int i=0;i<=userQuota;i++){
			InstanceData id = new InstanceData(template, hwp, user, "UserQuota"+r.newRandom());
			 if(!user.isQuotaExceeded()){
				 tasks.launchInstance(id.getTemplateName(),id.getInstanceName(), id.getTemplateArch(), id.getHardwareProfile());
			     tasks.verifyInstanceInRow(id.getInstanceName());
			     tasks.waitForInstance(id.getInstanceName(), InstanceData.STATE_RUNNING, 10);
			     }
		    	 
		     }
		// Add quota+1 instance and check for error
		InstanceData id = new InstanceData(template, hwp, user, "PENDING QUOTA"+r.newRandom());
		Assert.assertTrue(!tasks.launchInstance(id.getTemplateName(),id.getInstanceName(), id.getTemplateArch(), id.getHardwareProfile()),"Asserted that Quota Warning is visible; "+UI.quotaExceeded);
		
		
		tasks.updateUserQuota(user, String.valueOf(userQuota+1));
		Assert.assertTrue(tasks.waitForInstance(id.getInstanceName(), InstanceData.STATE_RUNNING, 10),"Asserted intance"+id.getInstanceName()+" is now in a running state after quota was increased");

	}
*/



}
