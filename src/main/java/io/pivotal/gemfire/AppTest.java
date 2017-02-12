package io.pivotal.gemfire;

import java.util.Random;

import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.pdx.PdxInstance;

import io.pivotal.gemfire.domain.Master;
import io.pivotal.gemfire.domain.MasterKey;
import io.pivotal.gemfire.domain.Trace;
import io.pivotal.gemfire.functions.FunctionInvoker;

public class AppTest {

	public AppTest() {
	}

	public static void main(String[] args) {
		
		ClientCacheFactory ccf = new ClientCacheFactory();
		ccf.set("cache-xml-file", "client.xml");
		ClientCache cache = ccf.create();
		Region<MasterKey, Master> region = cache.getRegion("MASTER");
		
		MasterKey key = new MasterKey("EquipId0", "STEP0"); // key must be domainclass
		/*
		PdxInstance value = (PdxInstance) masters.get(key);
		System.out.println(">>>" + value.getObject());
		System.out.println("####" + value.getField("param"));
		System.out.println("!!!" + value.getObject().toString());
		
		//region.
		 * 
		 */
	}
	
	
}
