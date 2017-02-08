package io.pivotal.gemfire;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.pdx.PdxInstance;

import io.pivotal.gemfire.domain.Master;
import io.pivotal.gemfire.domain.MasterKey;
import io.pivotal.gemfire.domain.Trace;
import io.pivotal.gemfire.functions.FunctionInvoker;

public class GfLoader {
	static int BULK_COUNT = 1000;
	
	public GfLoader() {
	}
	
	public static void main(String[] args) {
		int count = 3000;
		if (args.length > 0) {
			count = Integer.parseInt(args[0]);
		}
		if (count <= BULK_COUNT) {
			BULK_COUNT = count;
		}
		
		ClientCacheFactory ccf = new ClientCacheFactory();
		ccf.set("cache-xml-file", "client.xml");
		ClientCache cache = ccf.create();
		Region<?, ?> masters = cache.getRegion("Master");
		Region<?, ?> masterIndices = cache.getRegion("MasterIndex");
		Region<?, ?> traces = cache.getRegion("Trace");
		
		Map buffer = new HashMap();
		if (FunctionInvoker.executeClearRegionRemoveAll(traces)) {
			for (int i = 0; i < BULK_COUNT; i++) {
				String equipId = "EquipId" + i;
				PdxInstance trace = cache.createPdxInstanceFactory("io.pivotal.gemfire.domain.Trace")
						.writeString("equipId", equipId)
						.writeString("param", "")
						.create();
				
				buffer.put(equipId, trace);
				if ((i % BULK_COUNT == BULK_COUNT-1)) {
					traces.putAll(buffer);
					buffer.clear();
				}
			}
			if (!buffer.isEmpty()) {
				traces.putAll(buffer);
				//System.out.println("##### buffer is not empty. region.putAll buffer.size: " + buffer.size());
			}
			System.out.println("### " + BULK_COUNT + " data loaded in Trace region.");
		} else {
			System.out.println("Some Trace data are not removed.");
		}
		
		if (FunctionInvoker.executeClearRegionRemoveAll(masters)) {
			Map buffer1 = new HashMap();
			buffer.clear();
			
			for (int i = 0; i < count; i++) {
				String timestamp = new Timestamp(System.nanoTime()).toString();
				String equipId = "EquipId" + (i % BULK_COUNT);
				PdxInstance key = cache.createPdxInstanceFactory("io.pivotal.gemfire.domain.MasterKey")
						.writeString("timestamp", timestamp)
						.writeString("equipId", equipId)
						.create();
				PdxInstance master = cache.createPdxInstanceFactory("io.pivotal.gemfire.domain.Master")
						.writeString("timestamp", timestamp)
						.writeString("equipId", equipId)
						.writeString("lotId", "LotId" + i)
						.writeString("param", "PARAM" + i)
						.create();
				
				buffer.put(key, master);
				buffer1.put(equipId, key);
				if ((i % BULK_COUNT == BULK_COUNT-1)) {
					masters.putAll(buffer);
					masterIndices.putAll(buffer1);
					buffer.clear();
					buffer1.clear();
				}
			}
			if (!buffer.isEmpty()) {
				masters.putAll(buffer);
				masterIndices.putAll(buffer1);
				//System.out.println("##### buffer is not empty. region.putAll buffer.size: " + buffer.size());
			}
			System.out.println("### " + count + " data loaded in Master region.");
		} else {
			System.out.println("Some Master data are not removed.");
		}
	}
}
