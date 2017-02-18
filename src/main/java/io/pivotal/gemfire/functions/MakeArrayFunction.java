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

import io.pivotal.gemfire.domain.TraceComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
		
		// Index Map
		Map<PdxInstance, Set> index = new HashMap<PdxInstance, Set>();
		//res = localRegion.keySet().toString();
		Iterator<PdxInstance> iter = localRegion.keySet().iterator();
		while (iter.hasNext()) {
			PdxInstance traceKey = iter.next();
			WritablePdxInstance indexKey = traceKey.createWriter();
			indexKey.setField("seq", 0l);
			Set<PdxInstance> set = index.get(indexKey);
			
			if (set == null) {
				set = new HashSet<PdxInstance>();
			}
			set.add(traceKey);
			index.put(indexKey, set);
			//res += "|" + new Integer(set.size()).toString();
		}
		//res = new Integer(index.size()).toString();
		
		TraceComparator comparator = new TraceComparator();
		Iterator<PdxInstance> it = index.keySet().iterator();
		while (it.hasNext()) {
			PdxInstance indexKey = it.next();
			Set keys = index.get(indexKey);
			Map<PdxInstance, PdxInstance> traces = comparator.sort(localRegion.getAll(keys)); // ts, vl, sl
			PdxInstance value = squeeze(traces.entrySet());
			transformeds.put(indexKey, value);
		}
		context.getResultSender().lastResult(res);
	}
	
	public PdxInstance squeeze(Set<Entry<PdxInstance, PdxInstance>> set) {
		WritablePdxInstance pdxI = null;
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			PdxInstance value = (PdxInstance) ((Entry)iter.next()).getValue();
			if (pdxI == null) {
				pdxI = value.createWriter();
				pdxI.setField("eqpIndex", value.getField("eqpIndex"));
				pdxI.setField("unitIndex", value.getField("unitIndex"));
				pdxI.setField("paramIndex", value.getField("paramIndex"));
				pdxI.setField("lotId", value.getField("lotId"));
				pdxI.setField("ppId", value.getField("ppId"));
				pdxI.setField("recipeId", value.getField("recipeId"));
				pdxI.setField("stepSeq", value.getField("stepSeq"));
				pdxI.setField("pairId", value.getField("pairId"));
				pdxI.setField("processId", value.getField("processId"));
				pdxI.setField("waferId", value.getField("waferId"));
				pdxI.setField("waferNo", value.getField("waferNo"));
				pdxI.setField("lotType", value.getField("lotType"));
				pdxI.setField("statusTf", value.getField("statusTf"));
				pdxI.setField("ts", value.getField("ts"));
				pdxI.setField("vl", value.getField("vl"));
				pdxI.setField("ls", value.getField("ls"));
				pdxI.setField("us", value.getField("us"));
				pdxI.setField("sl", value.getField("sl").toString());
			} else {
				pdxI.setField("ts", pdxI.getField("ts").toString() + "," + value.getField("ts").toString());
				pdxI.setField("vl", pdxI.getField("vl").toString() + "," + value.getField("vl").toString());
				pdxI.setField("sl", pdxI.getField("sl").toString() + "," + value.getField("sl").toString());
			}
		}
		
		return pdxI;
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
