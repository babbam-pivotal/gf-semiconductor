package io.pivotal.gemfire.listener;

import java.util.Properties;

import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.util.CacheListenerAdapter;

public class TraceCacheListener extends CacheListenerAdapter implements Declarable {

	public TraceCacheListener() {
	}

	public void init(Properties props) {
	}

	public void afterCreate(EntryEvent event) {
		//event.g
		
		
		String eKey = event.getKey().toString();
		String eVal = event.getNewValue().toString();
		
		System.out.println("TraceCacheListener.afterCreate: key=" + eKey + ", val=" + eVal);
		
		
	}
}
