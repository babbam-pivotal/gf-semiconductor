package io.pivotal.gemfire.functions;

import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.partition.PartitionRegionHelper;
import org.apache.geode.pdx.PdxInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class MakeArrayFunction implements Function, Declarable {
	private static final long serialVersionUID = 1L;
	public static final String ID = "MakeArrayFunction";
	
	public void execute(FunctionContext context) {
		System.out.println(Thread.currentThread().getName() + ": Executing " + getId());
		RegionFunctionContext rfc = (RegionFunctionContext) context;
		Region localRegion = PartitionRegionHelper.getLocalDataForContext(rfc); // TRACE
		
		Map<String, Region<?, ?>> regions = PartitionRegionHelper.getLocalColocatedRegions(rfc);
		
		Map buffer = new HashMap();
		Region transformeds = regions.get("/TRACE_TRANSFORMED");
		
		String res = "";
		//res = localRegion.keySet().toString();
		
		Iterator<PdxInstance> iter = localRegion.keySet().iterator();
		Map<Object, Set> equipIds = new HashMap<Object, Set>();
		while (iter.hasNext()) {
			PdxInstance traceKey = iter.next();
			Set<PdxInstance> set = equipIds.get(traceKey.getField("equipId"));
			if (set == null) {
				set = new HashSet<PdxInstance>();
			}
			set.add(traceKey);
			equipIds.put(traceKey.getField("equipId"), set);
		}
		
		Iterator<Object> it = equipIds.keySet().iterator();
		while (it.hasNext()) {
			Object equipId = iter.next();
			Set keys = equipIds.get(equipId);
			Map traces = localRegion.getAll(keys);
			List<Double> vals = new ArrayList<Double>();
			
			Iterator trace = traces.entrySet().iterator();
			while (trace.hasNext()) {
				PdxInstance value = (PdxInstance) trace.next();
				vals.add(Double.valueOf(value.getField("val").toString()));
			}
			Collections.sort(vals); // object sort
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
