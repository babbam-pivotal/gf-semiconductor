package io.pivotal.gemfire.domain;

import java.util.Properties;

import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.EntryOperation;
import org.apache.geode.cache.PartitionResolver;
import org.apache.geode.pdx.PdxInstance;
import org.apache.geode.pdx.WritablePdxInstance;

public class TracePartitionResolver<V> implements PartitionResolver<PdxInstance, V>, Declarable {
	private static final long serialVersionUID = 1L;
	
	public TracePartitionResolver() {
	}

	public void close() {
	}

	public void init(Properties props) {
	}

	public Object getRoutingObject(EntryOperation<PdxInstance, V> opDetails) {
		WritablePdxInstance key = (WritablePdxInstance) opDetails.getKey().createWriter();
		key.setField("seq", 0l);
		
		return key;
		/*
		StringBuffer sb = new StringBuffer();
		sb.append(key.getField("eqpIndex"));
		sb.append("|" + key.getField("unitIndex"));
		sb.append("|" + key.getField("paramIndex"));
		sb.append("|" + key.getField("lotId"));
		sb.append("|" + key.getField("ppId"));
		sb.append("|" + key.getField("recipeId"));
		sb.append("|" + key.getField("stepSeq"));
		sb.append("|" + key.getField("pairId"));
		sb.append("|" + key.getField("processId"));
		sb.append("|" + key.getField("waferId"));
		sb.append("|" + key.getField("waferNo"));
		sb.append("|" + key.getField("lotType"));
		sb.append("|" + key.getField("statusTf"));
		
		return sb;
		*/
	}

	public String getName() {
		return this.getClass().getName() + "PartitionResolver";
	}
}