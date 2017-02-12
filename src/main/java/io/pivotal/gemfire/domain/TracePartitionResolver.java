package io.pivotal.gemfire.domain;

import java.util.Properties;

import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.EntryOperation;
import org.apache.geode.cache.PartitionResolver;
import org.apache.geode.pdx.PdxInstance;

public class TracePartitionResolver<V> implements PartitionResolver<PdxInstance, V>, Declarable {
	private static final long serialVersionUID = 1L;
	
	public TracePartitionResolver() {
	}

	public void close() {
	}

	public void init(Properties props) {
	}

	public Object getRoutingObject(EntryOperation<PdxInstance, V> opDetails) {
		PdxInstance key = (PdxInstance) opDetails.getKey();
		return key.getField("equipId");
		/*
		if (opDetails.getKey() instanceof MasterKey) {
			MasterKey key = (MasterKey) opDetails.getKey();
			return key.getEquipId();
		} else if (opDetails.getKey() instanceof TraceKey) {
			TraceKey key = (TraceKey) opDetails.getKey();
			return key.getEquipId();
		} else if (opDetails.getKey() instanceof PdxInstance) {
			PdxInstance key = (PdxInstance) opDetails.getKey();
			return key.getField("equipId");
		}
		return "";
		*/
	}

	public String getName() {
		return this.getClass().getName() + "PartitionResolver";
	}
}