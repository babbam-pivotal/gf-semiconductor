package io.pivotal.gemfire.domain;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

public class MasterKey implements PdxSerializable {

	private String timestamp;
	private String equipId;
	
	public MasterKey() {
	}
	
	public MasterKey(String timestamp, String equipId) {
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
		return "MasterKey [timestamp=" + timestamp + ", equipId=" + equipId + "]";
	}
	
	public void toData(PdxWriter writer) {
		writer.writeString("timestamp", timestamp)
		.writeString("equipId", equipId);
	}

	public void fromData(PdxReader reader) {
		timestamp = reader.readString(timestamp);
		equipId = reader.readString(equipId);
	}
}
