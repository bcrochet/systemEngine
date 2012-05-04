package com.redhat.qe.ce10.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.crypto.Data;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.redhat.qe.auto.testng.TestNGUtils;
import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.ce10.data.Provider;
import com.redhat.qe.ce10.data.ProviderAccount;
import com.redhat.qe.ce10.data.ProviderAccount_ec2;
import com.redhat.qe.tools.SSHCommandRunner;

@Test(groups="testplan-CloudEngine:Providers")
public class ManageProviders extends SeleniumTestScript{
/*	
	ProviderAccount_ec2 provider_good;
	ProviderAccount provider_bad;
	String hostname;
	int port;
	String path;
	String url;

	Object[][] goodProviders = null;
	final String[] noExtraMessages = new String[]{};
	public static final boolean DONT_CLICK_TEST_BUTTON = false;
	public static final boolean CLICK_TEST_BUTTON = true;
	public static final String[] noChanges = null;
	public static final ProviderAccount noProviderChanges = null;
	Provider usEast1;
	SSHCommandRunner sshCommandRunner;
	String key = System.getProperty("ce.auto.privatekey");
	String passphrase = System.getProperty("jon.server.sshkey.passphrase");
	
	

	
	
	 * 
	 * 		Data Providers
	 * 
	 
	
	// used for remove provider test
	@DataProvider(name="getGoodProviderObjects")
	public Object[][] getGoodProviderAccountObjects() throws CloneNotSupportedException {
		return TestNGUtils.convertListOfListsTo2dArray(createGoodProviderObjects());
	}
	
	@DataProvider(name="getCharacterObjects")
	protected Object[][] createCharacterObjects() {
		//String[] good_characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890-_".split("");
		String[] good_characters = "-_".split("");
		String[] bad_characters = "`~!@#$%^&*()+=[]{}';:\"/.,<>?\\|".split("");
		
		List<List<Object>> ll = new ArrayList<List<Object>>();
		
		// good chars (create & edit)
		for (int i = 1; i<good_characters.length;i++) {
			ll.add(Arrays.asList(new Object[]{ "create_good_char("+good_characters[i]+")", tasks.ACTION_CREATE, good_characters[i] } ));
			ll.add(Arrays.asList(new Object[]{ "edit___good_char("+good_characters[i]+")", tasks.ACTION_EDIT,  good_characters[i] } ));
		} 	
		
		for (int i = 1; i<bad_characters.length;i++) {
			ll.add(Arrays.asList(new Object[]{ "create_bad_char("+bad_characters[i]+")", tasks.ACTION_CREATE, bad_characters[i] } ));
			ll.add(Arrays.asList(new Object[]{ "edit___bad_char("+bad_characters[i]+")", tasks.ACTION_EDIT,  bad_characters[i] } ));
		} 	
		
		ll.add(Arrays.asList(new Object[]{ "create_good_char( )", tasks.ACTION_CREATE, "test test" } ));
		ll.add(Arrays.asList(new Object[]{ "edit___good_char( )", tasks.ACTION_EDIT,  "test test test" } ));
		
		return TestNGUtils.convertListOfListsTo2dArray(ll);	
	}
	
	
	@DataProvider(name="getProviderTestObjects")
	protected Object[][] createProviderTestObjects() throws CloneNotSupportedException {
		
		ProviderAccount c_noProviderType = new ProviderAccount(tasks.generateRandomString(256), null);
		c_noProviderType.setName("c_noProviderType");
		c_noProviderType.setUrl(provider_good.getUrl());
		
		ProviderAccount_ec2 c_noUrl = provider_good.clone();
		c_noUrl.setName("c_noUrl");
		c_noUrl.setUrl("");
		
		ProviderAccount_ec2 c_noName = provider_good.clone();
		c_noName.setName("");
		
		ProviderAccount_ec2 c_badUrl_ip = provider_good.clone();
		c_badUrl_ip.setName("c_badUrl_ip");
		c_badUrl_ip.setUrl("http://1.1.1.1:"+this.port+this.path);
		
		ProviderAccount_ec2 c_badUrl_redirectPath = provider_good.clone();
		c_badUrl_redirectPath.setName("c_badUrl_redirectPath");
		c_badUrl_redirectPath.setUrl("http://"+this.hostname+":"+this.port);
		
		ProviderAccount_ec2 c_badUrl_path = provider_good.clone();
		c_badUrl_path.setName("badUrl_path");
		c_badUrl_path.setUrl("http://"+this.hostname+":"+this.port+"/BAD");
		
		ProviderAccount_ec2 c_badUrl_port = provider_good.clone();
		c_badUrl_port.setName("badUrl_port");
		c_badUrl_port.setUrl("http://"+this.hostname+":2323"+this.path);
		
		ProviderAccount_ec2 c_nameTooLong = provider_good.clone();
		c_nameTooLong.setName(tasks.generateRandomString(256));
		
		ProviderAccount_ec2 c_urlTooLong = provider_good.clone();
		c_urlTooLong.setName("c_urlTooLong");
		c_urlTooLong.setUrl("http://"+this.hostname+":2323"+this.path+ tasks.generateRandomString(255));
		
		// TODO:
		//
		// invalidURL  (urls can have special characters like %20, will that fail?)
		//		Have Selenium spin up a JAVA process listening on some port with some crazy paths under it?
		//
		// Name/URL > 100 chars should probably throw a UI error too, causes table to push url off screen  (Needs bug report)
		//
        // Need some extra valid URLs values
        //		ll.add(Arrays.asList(new Object[]{ "edit_goodUrlChange",			e_goodUrlChange } ));
        //		ll.add(Arrays.asList(new Object[]{ "edit_goodNameAndUrlChange",	e_goodNameAndUrlChange } ));
		//		ll.add(Arrays.asList(new Object[]{ "create_badUrl_redirectPath",  	c_badUrl_redirectPath,			null,							new String[]{ ProviderData.PROVIDER_CREATION_FAILURE, ProviderData.PROVIDER_NEED_VALID_URL } } ));
		
        List<List<Object>> ll = new ArrayList<List<Object>>();
        //										testname					test object            		edit (new_name, new_url)				messages
        ll.add(Arrays.asList(new Object[]{ "create_noUrl",					c_noUrl, 						null,  							noExtraMessages } ));
        ll.add(Arrays.asList(new Object[]{ "create_noName", 				c_noName,  						null,							noExtraMessages } ));
        
        
        ll.add(Arrays.asList(new Object[]{ "create_noNameOrUrl", 		  	new ProviderAccount("", null),			null,							noExtraMessages } ));
        ll.add(Arrays.asList(new Object[]{ "create_badUrl_path", 			c_badUrl_path,  				null,							new String[]{ ProviderAccount.PROVIDER_CREATION_FAILURE, ProviderAccount.PROVIDER_NEED_VALID_URL } } ));
        ll.add(Arrays.asList(new Object[]{ "create_badUrl_port", 			c_badUrl_port,  				null,							new String[]{ ProviderAccount.PROVIDER_CREATION_FAILURE, ProviderAccount.PROVIDER_NEED_VALID_URL } } ));
        ll.add(Arrays.asList(new Object[]{ "create_badUrl_ip", 				c_badUrl_ip,  					null,							new String[]{ ProviderAccount.PROVIDER_CREATION_FAILURE, ProviderAccount.PROVIDER_NEED_VALID_URL } } ));
        ll.add(Arrays.asList(new Object[]{ "create_nameTooLong", 			c_nameTooLong,  				null,							noExtraMessages } ));
        ll.add(Arrays.asList(new Object[]{ "create_urlTooLong", 			c_urlTooLong,  					null,							noExtraMessages } ));
        ll.add(Arrays.asList(new Object[]{ "edit_noUrlOrNoName",			provider_good.clone(), 		new String[]{ "", "" },				noExtraMessages } ));
        ll.add(Arrays.asList(new Object[]{ "edit_noUrl",					provider_good.clone(), 		new String[]{ null, "" },			noExtraMessages } ));
        ll.add(Arrays.asList(new Object[]{ "edit_noName",					provider_good.clone(), 		new String[]{ "", null },			noExtraMessages } ));
        ll.add(Arrays.asList(new Object[]{ "edit_goodNameChange",			provider_good.clone(), 		new String[]{ "nName", null },		noExtraMessages } ));

        String sNameTooLong = tasks.generateRandomString(256);
        ll.add(Arrays.asList(new Object[]{ "edit_nameTooLong",				provider_good.clone(), 	new String[]{ sNameTooLong,null},		noExtraMessages } ));
        String sUrlTooLong =  "http://127.0.0.1:3002/"+tasks.generateRandomString(255);
        ll.add(Arrays.asList(new Object[]{ "edit_urlTooLong",				provider_good.clone(), 	new String[]{ null,sUrlTooLong },		noExtraMessages } ));
        
        
         * 
         * 	Note:  So it is my assumption that the provider account form will be specific to individual providers meaning each provider needs to be tested separately.
         * 			When a new provider is supported, its account tests need to fall under a separate 'else if' statement.    - DJ 110112
         * 
         
        for (int i = 0; i < goodProviders.length; i++) {
			if (goodProviders[i][1] instanceof ProviderAccount_ec2) {
				ProviderAccount_ec2 ec2Provider = (ProviderAccount_ec2) goodProviders[i][1];
				ll.add(Arrays.asList(new Object[]{ "ec2_good",						ec2Provider, 	null,								new String[]{ ProviderAccount.PROVIDER_CREATION_SUCCESS } } ));		
			}
        }    
        return TestNGUtils.convertListOfListsTo2dArray(ll);
	}	
	
	@DataProvider(name="getTestProviderObjects")
	protected Object[][] createTestProviderObjects() throws CloneNotSupportedException {
		
		List<List<Object>> ll = new ArrayList<List<Object>>();
		for (int i = 0; i < goodProviders.length; i++) {
			if (goodProviders[i][1] instanceof ProviderAccount_ec2) {
				
				// clone good provider
				ProviderAccount_ec2 ec2Provider = (ProviderAccount_ec2) goodProviders[i][1];
				
				ProviderAccount_ec2 c_connectTest_fail = ec2Provider.clone();
				String sBadUrl = "http://"+this.hostname+":3005";
				c_connectTest_fail.setUrl(sBadUrl);
				
				ll.add(Arrays.asList(new Object[]{ "ec2_edit___connectTest_pass",	ec2Provider.clone(),	new String[]{ null,null },			new String[]{ ProviderAccount.PROVIDER_TEST_SUCCESS } } ));
				ll.add(Arrays.asList(new Object[]{ "ec2_edit___connectTest_fail",	ec2Provider.clone(),	new String[]{ null, sBadUrl },		new String[]{ ProviderAccount.PROVIDER_TEST_FAILURE, ProviderAccount.PROVIDER_INVALID_URL } } ));
				ll.add(Arrays.asList(new Object[]{ "ec2_create_connectTest_pass",	ec2Provider.clone(),	null,								new String[]{ ProviderAccount.PROVIDER_TEST_SUCCESS } } ));
				ll.add(Arrays.asList(new Object[]{ "ec2_create_connectTest_fail",	c_connectTest_fail,		null,								new String[]{ ProviderAccount.PROVIDER_TEST_FAILURE, ProviderAccount.PROVIDER_INVALID_URL } } ));
			}	
		}
		return TestNGUtils.convertListOfListsTo2dArray(ll);
	}	
	
	
	@DataProvider(name="getTestProviderAccountObjects")
	protected Object[][] createTestProviderAccountObjects() throws CloneNotSupportedException {
		
		List<List<Object>> ll = new ArrayList<List<Object>>();
		for (int i = 0; i < goodProviders.length; i++) {
						
			if (goodProviders[i][1] instanceof ProviderAccount_ec2) {
				ProviderAccount_ec2 ec2_good = (ProviderAccount_ec2) goodProviders[i][1];
				ProviderAccount_ec2 connectTest1 = ec2_good.clone();
				connectTest1.setUsername("HeyYouWoodChucks");
				ProviderAccount_ec2 connectTest2 = ec2_good.clone();
				connectTest2.setPassword("StopChuckingMyWood");
				
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_testFailOnBadId",	connectTest1, 	null,   		new String[]{ ProviderAccount.PROVR_ACCT_TEST_FAILED }} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_testFailOnBadPass",	connectTest2, 	null,   		new String[]{ ProviderAccount.PROVR_ACCT_TEST_FAILED }} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_testSuccess",		ec2_good,  		null,   		new String[]{ ProviderAccount.PROVR_ACCT_TEST_PASSED }} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_edit___testFailOnBadId",	ec2_good,		connectTest1,  	new String[]{ ProviderAccount.PROVR_ACCT_CRED_INVALID }} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_edit___testFailOnBadPass",	ec2_good,		connectTest2,  	new String[]{ ProviderAccount.PROVR_ACCT_CRED_INVALID }} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_edit___testSuccess",		ec2_good,		ec2_good,  		new String[]{ ProviderAccount.PROVR_ACCT_TEST_PASSED }} ));
			}
		}
		return TestNGUtils.convertListOfListsTo2dArray(ll);
	}	
	
	@DataProvider(name="getProviderAccountObjects")
	protected Object[][] createProviderAccountObjects() throws CloneNotSupportedException {
		
		List<List<Object>> ll = new ArrayList<List<Object>>();
		
		for (int i = 0; i < goodProviders.length; i++) {
						
			if (goodProviders[i][1] instanceof ProviderAccount_ec2) {
				
				ProviderAccount_ec2 ec2Provider = (ProviderAccount_ec2) goodProviders[i][1];
				
				ProviderAccount_ec2 blankAccount = ec2Provider.clone();
				blankAccount.clearAccountData();
				ProviderAccount_ec2 emptyName = ec2Provider.clone();
				emptyName.setAccountName("");
				ProviderAccount_ec2 emptyAcctNumber = ec2Provider.clone();
				emptyAcctNumber.setAccountNumber("");
				ProviderAccount_ec2 emptyPassword = ec2Provider.clone();
				emptyPassword.setPassword("");
				ProviderAccount_ec2 emptyUser = ec2Provider.clone();
				emptyUser.setUsername("");
				ProviderAccount_ec2 emptyPrivateKey = ec2Provider.clone();
				emptyPrivateKey.setx509_privateKey("");
				ProviderAccount_ec2 emptyPublicKey = ec2Provider.clone();
				emptyPublicKey.setx509_publicKey("");
				ProviderAccount_ec2 nameTooLong = ec2Provider.clone();
				nameTooLong.setAccountName("name2long_"+tasks.generateRandomString(256));
				ProviderAccount_ec2 acctNumberTooLong = ec2Provider.clone();
				acctNumberTooLong.setAccountNumber(tasks.generateRandomString(256));
				ProviderAccount_ec2 passwordTooLong = ec2Provider.clone();
				passwordTooLong.setPassword(tasks.generateRandomString(256));
				ProviderAccount_ec2 userTooLong = ec2Provider.clone();
				userTooLong.setUsername(tasks.generateRandomString(256));
				ProviderAccount_ec2 quotaTooLong = ec2Provider.clone();
				quotaTooLong.setQuota(tasks.generateRandomNumberString(500));
				ProviderAccount_ec2 quotaPastMax = ec2Provider.clone();
				quotaPastMax.setQuota("2147483648");
				ProviderAccount_ec2 quotaMax = ec2Provider.clone();
				quotaMax.setQuota("2147483647");
				ProviderAccount_ec2 textForQuota = ec2Provider.clone();
				textForQuota.setQuota("dj");

				ProviderAccount_ec2 nameBeginWhiteSp = ec2Provider.clone();
				nameBeginWhiteSp.setAccountName(" " + nameBeginWhiteSp.getAccountName());			
				ProviderAccount_ec2 userBeginWhiteSp = ec2Provider.clone();
				userBeginWhiteSp.setUsername(" " + userBeginWhiteSp.getUsername());
				ProviderAccount_ec2 passBeginWhiteSp = ec2Provider.clone();
				passBeginWhiteSp.setPassword(" " + passBeginWhiteSp.getPassword());
				ProviderAccount_ec2 acctNumBeginWhiteSp = ec2Provider.clone();
				acctNumBeginWhiteSp.setAccountNumber(" " + acctNumBeginWhiteSp.getAccountNumber());
				
				ProviderAccount_ec2 nameEndsWhiteSp = ec2Provider.clone();
				nameEndsWhiteSp.setAccountName(nameEndsWhiteSp.getAccountName() + " ");
				ProviderAccount_ec2 userEndWhiteSp = ec2Provider.clone();
				userEndWhiteSp.setPassword(userEndWhiteSp.getUsername() + " ");
				ProviderAccount_ec2 passEndWhiteSp = ec2Provider.clone();
				passEndWhiteSp.setPassword(passEndWhiteSp.getPassword() + " ");
				ProviderAccount_ec2 acctNumEndWhiteSp = ec2Provider.clone();
				acctNumEndWhiteSp.setPassword(acctNumEndWhiteSp.getAccountNumber() + " ");
				
				//									testname							test_object	 	   	edit			
				ll.add(Arrays.asList(new Object[]{ "ec2_create_blankAccount",			blankAccount, 			null  			 	} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_emptyName",				emptyName,    			null  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_emptyAcctNumber",		emptyAcctNumber,		null  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_emptyPassword",			emptyPassword,    		null  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_emptyUser",				emptyUser,    			null  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_emptyPrivateKey",		emptyPrivateKey,    	null  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_emptyPublicKey",			emptyPublicKey,			null  				} ));
		        // TODO: commented out because the pass due to bug# and it screws the ui table up
		        //ll.add(Arrays.asList(new Object[]{ "ec2_create_nameTooLong",			nameTooLong,    		null  				} ));
		        //ll.add(Arrays.asList(new Object[]{ "ec2_create_acctNumberTooLong",		acctNumberTooLong,		null 				} ));
		        //ll.add(Arrays.asList(new Object[]{ "ec2_create_userTooLong",			userTooLong,			null  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_passwordTooLong",		passwordTooLong,    	null  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_quotaTooLong",			quotaTooLong,    		null  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_quotaPastMax",			quotaPastMax,    		null  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_quotaMax",				quotaMax,    			null  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_textForQuota",			textForQuota,    		null  				} ));

		        
		        // TODO: commented out several of these stripper tests because they don't have valid verifications	
		        //ll.add(Arrays.asList(new Object[]{ "ec2_stripsNameBeginWhiteSp",		nameBeginWhiteSp,  		null  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_stripsUserNameBeginWhiteSp",	userBeginWhiteSp,  		null  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_stripsPasswordBeginWhiteSp",	passBeginWhiteSp,  		null 				} ));
		        //ll.add(Arrays.asList(new Object[]{ "ec2_stripsAcctNumBeginWhiteSp",acctNumBeginWhiteSp,		null  				} ));
		        //ll.add(Arrays.asList(new Object[]{ "ec2_stripsNameEndsWhiteSp",		nameEndsWhiteSp,  		null  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_stripsUserNameEndsWhiteSp",		userEndWhiteSp,  		null  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_stripsPasswordEndsWhiteSp",		passEndWhiteSp,  		null  				} ));
		        //ll.add(Arrays.asList(new Object[]{ "ec2_stripsAcctNumEndsWhiteSp",	acctNumEndWhiteSp,		null  				} ));
		        
		        ll.add(Arrays.asList(new Object[]{ "ec2_edit___blankAccount",			ec2Provider, 		blankAccount  			} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_edit___emptyName",				ec2Provider,    	emptyName  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_edit___emptyAcctNumber",		ec2Provider,		emptyAcctNumber  		} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_edit___emptyPassword", 			ec2Provider,    	emptyPassword  			} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_edit___emptyUser", 				ec2Provider,    	emptyUser  				} ));
		        // TODO: on edit, these are already empty, need tests that actually update the certs (how to verify though?)
		        //ll.add(Arrays.asList(new Object[]{ "ec2_edit___emptyPrivateKey", 		ec2Provider,    	emptyPrivateKey  		} ));
		        //ll.add(Arrays.asList(new Object[]{ "ec2_edit___emptyPublicKey", 		ec2Provider,		emptyPublicKey  		} ));
		        // TODO: commented out because the pass due to bug# and it screws the ui table up
		        //ll.add(Arrays.asList(new Object[]{ "ec2_edit___nameTooLong",			ec2Provider,    	nameTooLong  			} ));
		        //ll.add(Arrays.asList(new Object[]{ "ec2_edit___acctNumberTooLong",	ec2Provider,		acctNumberTooLong  	} ));
		        //ll.add(Arrays.asList(new Object[]{ "ec2_edit___userTooLong", 			ec2Provider,		userTooLong  			} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_edit___passwordTooLong", 		ec2Provider,    	passwordTooLong  		} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_edit___quotaTooLong",			ec2Provider,    	quotaTooLong  			} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_edit___quotaPastMax",			ec2Provider,    	quotaPastMax  			} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_edit___quotaMax", 				ec2Provider,    	quotaMax  				} ));
		        ll.add(Arrays.asList(new Object[]{ "ec2_edit___textForQuota",			ec2Provider,    	textForQuota			} ));

		        //  this needs to be the last interation of the data provider to ensure it is present after the test suite is finished
		        ll.add(Arrays.asList(new Object[]{ "ec2_create_goodAccount",			ec2Provider,    		null  				} ));
			}
		}
		return TestNGUtils.convertListOfListsTo2dArray(ll);
	}
	
	
	 * 
	 * 		general utility methods
	 * 
	 
	
	
	 * 	The @BeforeClass initialize() calls this to create the "good" provider objects that the suite will use.  When a new provider is supported, a object needs to be 
	 * 		created/added here. 		-DJ 110112
	 
	protected List<List<Object>> createGoodProviderObjects() {

		ProviderAccount_ec2   ec2_good = new ProviderAccount_ec2(Provider.EC2_US_E, usEast1 );
		//ProviderData_rhevm rhevm_good = new ProviderData_rhevm("rhevm_good" );

		List<List<Object>> ll = new ArrayList<List<Object>>();
        ll.add(Arrays.asList(new Object[]{ "ec2",   ec2_good } ));
        //ll.add(Arrays.asList(new Object[]{ "rhevm", rhevm_good } ));
        
        return ll;
	}

	private void providerAccountTestSetup(ProviderAccount providerAccount) {
		// verify providers are defined
		for (int i=0; i < goodProviders.length; i++) {
			ProviderAccount good_provider = (ProviderAccount)goodProviders[i][1];
			
		}
		
		// cleanup just incase a previous test failed
		Provider thisProvider =  providerAccount.getProvider();
		tasks.deleteAllProviderAccounts(thisProvider);
	}
	
	
	// TODO: FUTURE TESTS
	
	
    verifyErrorOnEditProviderAcctWithDuplicateName?   (cannot have multiple accounts under one provider, what about across multiple providers though)
    verifyErrorOnEditProviderAcctWithDuplicateAccount? (cannot have multiple accounts under one provider, what about across multiple providers though)
    verifyErrorOnEditProviderAcctWithDuplicateUser?  (cannot have multiple accounts under one provider, what about across multiple providers though)
	each key as a extra large text file
	each key as a binary file?
	multiple provider accounts
	cannot delete provider if instances/templates? exist
	cannot delete account if instances/templates? exist
	what happens if delete during building template or deploying instance

		*/
	
}
