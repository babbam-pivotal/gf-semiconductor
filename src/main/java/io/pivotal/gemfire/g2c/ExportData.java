package io.pivotal.gemfire.g2c;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;

import io.pivotal.gemfire.domain.Trace;
import io.pivotal.gemfire.functions.FunctionInvoker;

public class ExportData {

	public ExportData() {
	}

	public static void main(String[] args) {
		ClientCacheFactory ccf = new ClientCacheFactory();
		ccf.set("cache-xml-file", "client.xml");
		ClientCache cache = ccf.create();
		Region<String, Trace> region = cache.getRegion("Trace");

		FunctionInvoker.executeImportFromGemfireToGPDB(region);
	}

}
