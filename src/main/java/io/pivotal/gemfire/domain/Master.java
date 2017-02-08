package io.pivotal.gemfire.domain;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

public class Master extends MasterKey implements PdxSerializable {

	private String lotId;
	private String param;
	
	public Master() {
	}
	
	public Master(String timestamp, String equipId, String lotId, String param) {
		setTimestamp(timestamp);
		setEquipId(equipId);
		this.lotId = lotId;
		this.param = param;
	}
	
	public String getLotId() {
		return lotId;
	}

	public void setLotId(String lotId) {
		this.lotId = lotId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Override
	public String toString() {
		return "Master [timestamp=" + getTimestamp() + ", equipId=" + getEquipId() + ", lotId=" + lotId + ", param=" + param + "]";
	}
	
	public void toData(PdxWriter writer) {
		writer.writeString("timestamp", getTimestamp())
		.writeString("equipId", getEquipId())
		.writeString("lotId", lotId)
		.writeString("param", param);
	}

	public void fromData(PdxReader reader) {
		setTimestamp(reader.readString(getTimestamp()));
		setEquipId(reader.readString(getEquipId()));
		lotId = reader.readString(lotId);
		param = reader.readString(param);
	}
}
