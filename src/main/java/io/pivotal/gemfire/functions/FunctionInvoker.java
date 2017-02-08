package io.pivotal.gemfire.functions;

import java.util.List;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.execute.FunctionService;
import org.apache.geode.cache.execute.ResultCollector;
import org.apache.geode.internal.cache.tier.sockets.VersionedObjectList.Iterator;

import io.pivotal.gemfire.domain.Trace;

public class FunctionInvoker {
	//ClientCache cache;
	
	public FunctionInvoker() {
	}

	public static void executeImportFromGemfireToGPDB(Region<?, ?> region) {
		ResultCollector<?, ?> rc = FunctionService.onRegion(region).execute("ImportFromGemfireToGPDBFunction");
		Object result = rc.getResult();

		System.out.println(result.toString());
	}
	
	public static boolean executeClearRegionRemoveAll(Region<?, ?> region) {
		ResultCollector<?, ?> rc = FunctionService.onRegion(region).execute("ClearRegionRemoveAllFunction");
		Object result = rc.getResult();
		// Array size seems to be server node count.
		//System.out.println("executeClearRegionRemoveAll.result: " + result);
		String str[] =result.toString().split(",");
		for (int i=0; i<str.length; i++) {
			if ("false".equals(str[i])) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void executeGetMasterRegionData(Region<?, ?> region) {
		ResultCollector<?, ?> rc = FunctionService.onRegion(region).execute("GetMasterRegionDataFunction");
		Object result = rc.getResult();
		// Array size seems to be node count.
		System.out.println("executeGetMasterRegionData.result: " + result);
	}
}
