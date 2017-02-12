package io.pivotal.gemfire.listener;

import java.util.Properties;

import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.util.CacheListenerAdapter;

public class TraceCacheListener extends CacheListenerAdapter implements Declarable {
	private ClientCache cache;
	private Region<?, ?> region;
	
	public TraceCacheListener() {
	}

	public void init(Properties props) {
		cache = new ClientCacheFactory().set("name", "ClientWorker").set("cache-xml-file", "client.xml")
				.create();
		region = cache.getRegion("TRACE_GPDB");
	}

	public void afterCreate(EntryEvent event) {
		//event.g
		
		
		String eKey = event.getKey().toString();
		String eVal = event.getNewValue().toString();
		
		System.out.println("TraceCacheListener.afterCreate: key=" + eKey + ", val=" + eVal);
		
		
	}
}
