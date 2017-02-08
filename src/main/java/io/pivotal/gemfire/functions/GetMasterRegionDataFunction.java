package io.pivotal.gemfire.functions;

import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.partition.PartitionRegionHelper;
import org.apache.geode.pdx.PdxInstance;
import org.apache.geode.pdx.WritablePdxInstance;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class GetMasterRegionDataFunction implements Function, Declarable {
	private static final long serialVersionUID = 1L;
	public static final String ID = "GetMasterRegionDataFunction";
	
	public void execute(FunctionContext context) {
		System.out.println(Thread.currentThread().getName() + ": Executing " + getId());
		RegionFunctionContext rfc = (RegionFunctionContext) context;
		Region localRegion = PartitionRegionHelper.getLocalDataForContext(rfc);
		
		Map<String, Region<?, ?>> regions = PartitionRegionHelper.getLocalColocatedRegions(rfc);
		
		Map buffer = new HashMap();
		
		Region masters = regions.get("/Master");
		Region masterIndices = regions.get("/MasterIndex");
		Iterator<String> iter = localRegion.keySet().iterator();
		/*
		while (iter.hasNext()) {
			String equipId = iter.next().toString();
			PdxInstance trace = (PdxInstance) localRegion.get(equipId);
			if (masterIndices.get(equipId) != null) {
				String timestamp = masterIndices.get(equipId).toString();
				
				PdxInstance key = (new CacheFactory()).create()
						.createPdxInstanceFactory("io.pivotal.gemfire.domain.MasterKey")
						.writeString("timestamp", timestamp)
						.writeString("equipId", equipId)
						.create();
				PdxInstance value = (PdxInstance) masters.get(key);
				String param = value.getField("param").toString();
				WritablePdxInstance trace1 = trace.createWriter();
				trace1.setField("param", param);
				//buffer.put(equipId, trace1);
				localRegion.put(equipId, trace1);
			}
		}
		*/
		//localRegion.putAll(buffer);
		/*
		int numLocalEntries = localRegion.size();

		// Destroy each entry
		long start = 0, end = 0;
		start = System.currentTimeMillis();
		localRegion.removeAll(localRegion.keySet());
		end = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName() + ": Cleared " + numLocalEntries + " entries in "
				+ (end - start) + " ms");
		*/
		context.getResultSender().lastResult(Boolean.valueOf(localRegion.containsKey("EquipId0")).toString());
	}

	public String getId() {
		return getClass().getSimpleName();
	}

	public boolean optimizeForWrite() {
		return true;
	}

	public boolean hasResult() {
		return true;
	}

	public boolean isHA() {
		return true;
	}

	public void init(Properties properties) {
	}
}
