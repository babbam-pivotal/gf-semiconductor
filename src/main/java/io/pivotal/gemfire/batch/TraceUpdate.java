package io.pivotal.gemfire.batch;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.execute.Execution;
import org.apache.geode.cache.execute.FunctionService;
import org.apache.geode.cache.execute.ResultCollector;

import io.pivotal.gemfire.functions.FunctionInvoker;
import io.pivotal.gemfire.functions.PRBResultCollector;

public class TraceUpdate {
	private ClientCache cache;
	private Region<?, ?> traces;
	private Region<?, ?> masters;
	
	public TraceUpdate() {
	}

	public static void main(String[] args) {
		TraceUpdate update = new TraceUpdate();
		update.getCache();
		update.getRegions();
		update.printInfo();
		update.closeCache();
	}
	
	public void closeCache() {
		cache.close();
	}

	public void getCache() {
		this.cache = new ClientCacheFactory().set("name", "ClientWorker").set("cache-xml-file", "client.xml")
				.create();
	}

	public void getRegions() {
		masters = cache.getRegion("MASTER");
		System.out.println("Got the Master Region: " + masters);
		traces = cache.getRegion("TRACE");
		System.out.println("Got the Trace Region: " + traces);
	}
	
	public void printInfo() {
		FunctionInvoker.executeGetMasterRegionData(traces);
	}
}
