package io.pivotal.gemfire;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.pdx.PdxInstance;
import io.pivotal.gemfire.functions.FunctionInvoker;

public class GfLoader {
	static int BULK_COUNT = 1000;
	
	public GfLoader() {
	}
	
	public static void main(String[] args) {
		int count = 20;
		if (args.length > 0) {
			count = Integer.parseInt(args[0]);
		}
		if (count <= BULK_COUNT) {
			BULK_COUNT = count;
		}
		
		ClientCacheFactory ccf = new ClientCacheFactory();
		ccf.set("cache-xml-file", "client.xml");
		ClientCache cache = ccf.create();
		Region<?, ?> masters = cache.getRegion("MASTER");
		Region<?, ?> traces = cache.getRegion("TRACE");
		Region<?, ?> transformed = cache.getRegion("TRACE_TRANSFORMED");
		
		Map buffer = new HashMap();
		FunctionInvoker.executeClearRegionRemoveAll(transformed);
		
		if (FunctionInvoker.executeClearRegionRemoveAll(traces)) {
			for (int i = 0; i < count; i++) {
				String timestamp = new Timestamp(System.nanoTime()).toString();
				String lotId = "LotId" + i;
				String equipId = "EquipId" + (i % BULK_COUNT);
				String step = "STEP" + (i % (count / BULK_COUNT));
				double val = randomInRange(0, 10);
				
				PdxInstance key = cache.createPdxInstanceFactory("io.pivotal.gemfire.domain.TraceKey")
						.writeString("timestamp", timestamp)
						.writeString("equipId", equipId)
						.create();
				PdxInstance trace = cache.createPdxInstanceFactory("io.pivotal.gemfire.domain.Trace")
						.writeString("timestamp", timestamp)
						.writeString("lotId", lotId)
						.writeString("equipId", equipId)
						.writeString("step", step)
						.writeDouble("val", val)
						.create();
				
				buffer.put(key, trace);
				if ((i % BULK_COUNT == BULK_COUNT-1)) {
					traces.putAll(buffer);
					buffer.clear();
				}
			}
			if (!buffer.isEmpty()) {
				traces.putAll(buffer);
				//System.out.println("##### buffer is not empty. region.putAll buffer.size: " + buffer.size());
			}
			System.out.println("### " + count + " data loaded in Trace region.");
		} else {
			System.out.println("Some Trace data are not removed.");
		}
		
		if (FunctionInvoker.executeClearRegionRemoveAll(masters)) {
			buffer.clear();
			
			for (int i = 0; i < count; i++) {
				String equipId = "EquipId" + (i % BULK_COUNT);
				String step = "STEP" + (i % (count / BULK_COUNT));
				PdxInstance key = cache.createPdxInstanceFactory("io.pivotal.gemfire.domain.MasterKey")
						.writeString("equipId", equipId)
						.writeString("step", step)
						.create();
				PdxInstance master = cache.createPdxInstanceFactory("io.pivotal.gemfire.domain.Master")
						.writeString("equipId", equipId)
						.writeString("step", step)
						.writeString("param", "PARAM" + i)
						.create();
				
				buffer.put(key, master);
			}
			if (!buffer.isEmpty()) {
				masters.putAll(buffer);
				//System.out.println("##### buffer is not empty. region.putAll buffer.size: " + buffer.size());
			}
			System.out.println("### " + count + " data loaded in Master region.");
		} else {
			System.out.println("Some Master data are not removed.");
		}
	}
	
	public static double randomInRange(double min, double max) {
		double range = max - min;
		double scaled = new Random().nextDouble() * range;
		return (scaled + min);
	}
}
