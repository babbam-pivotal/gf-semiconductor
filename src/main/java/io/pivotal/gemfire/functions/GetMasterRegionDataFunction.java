package io.pivotal.gemfire.functions;

import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.partition.PartitionRegionHelper;
import org.apache.geode.pdx.PdxInstance;
import org.apache.geode.pdx.WritablePdxInstance;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class GetMasterRegionDataFunction implements Function, Declarable {
	private static final long serialVersionUID = 1L;
	public static final String ID = "GetMasterRegionDataFunction";
	
	// TRACE --> TRACE
	public void execute(FunctionContext context) {
		System.out.println(Thread.currentThread().getName() + ": Executing " + getId());
		RegionFunctionContext rfc = (RegionFunctionContext) context;
		Region localRegion = PartitionRegionHelper.getLocalDataForContext(rfc); // TRACE
		
		Map<String, Region<?, ?>> regions = PartitionRegionHelper.getLocalColocatedRegions(rfc);
		Region masters = regions.get("/MASTER");
		Region transformed = regions.get("/TRACE_TRANSFORMED");
		Iterator<PdxInstance> iter = transformed.keySet().iterator();
		
		String res = "";
		//res = regions.keySet().toString();
		//res = localRegion.keySet().toString();
		
		while (iter.hasNext()) {
			PdxInstance traceKey = iter.next();
			PdxInstance trace = (PdxInstance) transformed.get(traceKey);
			
			if (trace != null) {
				WritablePdxInstance key = traceKey.createWriter();
				key.setField("seq", 0l);
				
				PdxInstance master = (PdxInstance) masters.get(key);
				if (master != null) {
					String ls = master.getField("ls").toString();
					String us = master.getField("us").toString();
					WritablePdxInstance value = trace.createWriter();
					value.setField("ls", ls);
					value.setField("us", us);
					transformed.remove(key);
					transformed.put(key, value);
				}
			}
		}
		context.getResultSender().lastResult(res);
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
