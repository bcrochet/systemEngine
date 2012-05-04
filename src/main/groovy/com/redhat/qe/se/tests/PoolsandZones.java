package com.redhat.qe.ce10.tests;

import org.testng.annotations.Test;

import com.redhat.qe.ce10.base.SeleniumTestScript;
import com.redhat.qe.ce10.data.RandomData;

@Test(groups="testplan-CloudEngine:PoolsAndZones")
public class PoolsandZones extends SeleniumTestScript{
	
	@Test(description="Pool Edit", groups="pools")
	public void testedit(){
		RandomData r = new RandomData(5);
	    tasks.editPool(r+"_edit_pool");
	}
	
	@Test(description="Pool Delete", groups="pools")
	public void testdeletepool(){
		RandomData r = new RandomData(5);
	    tasks.deletePool(r+"_delete_pool");
	}
	
	@Test(description="New Pool", groups="pools")
	public void testnewpool(){
		RandomData r = new RandomData(5);
	    tasks.newPool(r+"_auto_pool");
	}
	
	@Test(description="New Pool with long name", groups="pools")
	public void testnewpoolLongName(){
		RandomData r = new RandomData(255);
	    tasks.newPool(r+"_long_name");
	}
	
	@Test(description="Default Pool Delete", groups="pools")
	public void deleteDefaultpool(){
	    tasks11.deleteDefaultPool();
	}
	
	@Test(description="Duplicate Pool", groups="pools")
	public void duplicatePool(){
		RandomData r = new RandomData(5);
	    tasks11.duplicatePool(r+"_dup_pool");
	}
}
