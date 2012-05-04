package com.redhat.qe.ce10.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import com.redhat.qe.ce10.data.Catalog;
import com.redhat.qe.ce10.data.CatalogEntry;
import com.redhat.qe.ce10.data.DataList;
import com.redhat.qe.ce10.data.HardwareProfile;
import com.redhat.qe.ce10.data.Instance;
import com.redhat.qe.ce10.data.Templates;
import com.redhat.qe.ce10.data.Users;

public class InspectSerializedData {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		getCatalogEntries();
		
		
		
		
		

		}
	
	public static void getCatalogEntries() throws FileNotFoundException, IOException, ClassNotFoundException {
		//need to hardcode hostname in Catalog file for this to work
		
		DataList dl = new DataList();
		HashSet<Catalog> catalogs = dl.getListOfCreatedCatalogs();
		
		for(Catalog c:catalogs){
			System.out.println("CATALOG_NAME:"+c.getName());
			List<CatalogEntry> catalogEntryList = c.getListOfCatalogEntries();
				for(CatalogEntry ce:catalogEntryList){
					System.out.println(" CATALOG_ENTRY:"+ce.getCatalogEntryName());
					System.out.println(" CATALOG_ENTRY_URI:"+ce.getFullRemoteURI());
					List<Instance> instanceList = ce.getInstanceList();
					for(Instance i:instanceList){
						System.out.println("  ASSOCIATED_INSTANCES");
						System.out.println("  INSTANCE:"+i.getName());
							Templates myTemplate = i.getTemplate();
								System.out.println("   TEMPLATE_NAME:"+myTemplate.getName());
								System.out.println("    ARCH:"+myTemplate.getArchitecture());
								System.out.println("    TEMPLATE_XML:"+myTemplate.getTemplateXMLFile());
							HardwareProfile hwp = i.getHardwareProfile();
								System.out.println("   HWP_NAME:"+hwp.getName());
								System.out.println("    HWP_ARCH:"+hwp.getARCH());
								System.out.println("    HWP_CPU:"+hwp.getCPU());
								System.out.println("    HWP_MEMORY:"+hwp.getMEMORY());
							Users myUser = i.getOwner();
								System.out.println("    OWNER:"+myUser.username());
								System.out.println("    OWNER_EMAIL:"+myUser.email());

				}
			}
		}
	}
}
