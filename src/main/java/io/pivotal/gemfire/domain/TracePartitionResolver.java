package io.pivotal.gemfire.domain;

import java.util.Properties;

import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.EntryOperation;
import org.apache.geode.cache.PartitionResolver;
import org.apache.geode.pdx.PdxInstance;

public class TracePartitionResolver implements PartitionResolver, Declarable {
	private static final long serialVersionUID = 1L;
	
	public TracePartitionResolver() {
	}

	public void close() {
	}

	public void init(Properties props) {
	}

	public Object getRoutingObject(EntryOperation opDetails) {
		//MasterKey key = (MasterKey) opDetails.getKey();
		//return key.getEquipId();
		
		PdxInstance key = (PdxInstance) opDetails.getKey();
		return key.getField("equipId");
	}

	public String getName() {
		return this.getClass().getName() + "PartitionResolver";
	}
}