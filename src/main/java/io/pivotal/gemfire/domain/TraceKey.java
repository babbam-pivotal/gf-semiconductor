package io.pivotal.gemfire.domain;

import org.apache.geode.pdx.PdxInstance;
import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

public class TraceKey implements PdxSerializable {

	private String timestamp;
	private String equipId;
	
	public TraceKey() {
	}
	
	public TraceKey(PdxInstance pdxI) {
		this.timestamp = pdxI.getField("timestamp").toString();
		this.equipId = pdxI.getField("equipId").toString();
	}
	
	public TraceKey(String timestamp, String equipId) {
		this.timestamp = timestamp;
		this.equipId = equipId;
	}
	
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	
	@Override
	public String toString() {
		return "TraceKey [timestamp=" + timestamp + ", equipId=" + equipId + "]";
	}

	public void toData(PdxWriter writer) {
		writer.writeString("timestamp", timestamp)
		.writeString("equipId", equipId);
	}

	public void fromData(PdxReader reader) {
		timestamp = reader.readString("timestamp");
		equipId = reader.readString("equipId");
	}
}
