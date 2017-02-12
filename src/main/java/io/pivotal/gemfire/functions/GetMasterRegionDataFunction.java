package io.pivotal.gemfire.functions;

import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.partition.PartitionRegionHelper;
import org.apache.geode.pdx.PdxInstance;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class GetMasterRegionDataFunction implements Function, Declarable {
	private static final long serialVersionUID = 1L;
	public static final String ID = "GetMasterRegionDataFunction";
	
	public void execute(FunctionContext context) {
		System.out.println(Thread.currentThread().getName() + ": Executing " + getId());
		RegionFunctionContext rfc = (RegionFunctionContext) context;
		Region localRegion = PartitionRegionHelper.getLocalDataForContext(rfc); // TRACE
		
		Map<String, Region<?, ?>> regions = PartitionRegionHelper.getLocalColocatedRegions(rfc);
		
		Map buffer = new HashMap();
		Region masters = regions.get("/MASTER");
		Region transformeds = regions.get("/TRACE_TRANSFORMED");
		Iterator<PdxInstance> iter = localRegion.keySet().iterator();
		
		String res = "";
		//res = localRegion.keySet().toString();
		
		while (iter.hasNext()) {
			PdxInstance traceKey = iter.next();
			PdxInstance trace = (PdxInstance) localRegion.get(traceKey);
			
			if (trace != null) {
				PdxInstance masterKey = (new CacheFactory()).create()
						.createPdxInstanceFactory("io.pivotal.gemfire.domain.MasterKey")
						.writeString("equipId", trace.getField("equipId").toString())
						.writeString("step", trace.getField("step").toString())
						.create();
				PdxInstance master = (PdxInstance) masters.get(masterKey);
				if (master != null) {
					String param = master.getField("param").toString();
					//TraceTransformed value = new TraceTransformed(trace, param);
					PdxInstance value = (new CacheFactory()).create()
							.createPdxInstanceFactory("io.pivotal.gemfire.domain.TraceTransformed")
							.writeString("timestamp", trace.getField("timestamp").toString())
							.writeString("lotId", trace.getField("lotId").toString())
							.writeString("equipId", trace.getField("equipId").toString())
							.writeString("step", trace.getField("step").toString())
							.writeString("val", trace.getField("val").toString())
							.writeString("param", param)
							.create();
					transformeds.put(traceKey, value);
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
